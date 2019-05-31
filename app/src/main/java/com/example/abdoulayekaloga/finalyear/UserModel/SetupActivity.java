package com.example.abdoulayekaloga.finalyear.UserModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import android.widget.Toast;

import com.example.abdoulayekaloga.finalyear.MainActivity;
import com.example.abdoulayekaloga.finalyear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private Button saveUserInformationBtn;
    private CircleImageView profileImage;
    private EditText userName, userFullName, userMobile;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private StorageReference userProfileImageRef;
    //IMAGE HOLD URI
    @Nullable
    Uri imageHoldUri = null;

    private String currentUserId;
    private final static int Gallery_Pick =1;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        //initialiase Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //initialise views
        profileImage = findViewById(R.id.profileImage);
        userName = findViewById(R.id.userName);
        userFullName = findViewById(R.id.userFullName);
        userMobile =findViewById(R.id.userMobile);
        saveUserInformationBtn = findViewById(R.id.saveUserInformationBtn);
        loadingBar = new ProgressDialog(this);


        if(currentUser != null) {
            currentUserId = currentUser.getUid();
            userProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");
            usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
            saveUserInformationBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    savedAccountSetupInformation();
                }
            });

        }else{
            updateUI();
        }

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent galleryIntent = new Intent();
              galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
              galleryIntent.setType("image/*");
              startActivityForResult(galleryIntent, Gallery_Pick);
            }
        });


        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.hasChild("profileimage")){
                        String image = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.registration).into(profileImage);

                    }else{
                        Toast.makeText(SetupActivity.this, "Please select profile image first.",Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
           // Uri imageUri = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        // cropping the image
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {


                    loadingBar.setTitle("Image Profile");
                    loadingBar.setMessage("Please wait, while we are updating your profile image...");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(true);

                    Uri resultUri = result.getUri();

                    StorageReference filePath = userProfileImageRef.child(currentUserId + ".jpg");

                    filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(SetupActivity.this, "Profile Image stored successfully to Firebase storage...", Toast.LENGTH_SHORT).show();
                                Task<Uri> result = task.getResult().getMetadata().getReference().getDownloadUrl();

                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(@NonNull Uri uri) {
                                        final String downloadUrl = uri.toString();

                                        usersRef.child("profileimage").setValue(downloadUrl)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Intent selfIntent = new Intent(SetupActivity.this, SetupActivity.class);
                                                            startActivity(selfIntent);
                                                            Toast.makeText(SetupActivity.this, "Profile Image stored to Firebase Database Successfully...", Toast.LENGTH_SHORT).show();
                                                            loadingBar.dismiss();
                                                        } else {
                                                            String message = task.getException().getMessage();
                                                            Toast.makeText(SetupActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                            loadingBar.dismiss();
                                                        }
                                                    }
                                                });
                                    }
                                });
                            }


                        }
                    });

            }


            else {
               Toast.makeText(this, "Error Occured: Image can not be cropped. Please Try Again.", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }
    }


    private  void updateUI(){
        Intent homeActivity = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(homeActivity);
        finish();
    }

    private void savedAccountSetupInformation(){

        final String username = userName.getText().toString();
        final String fullName = userFullName.getText().toString();
        final String phoneNumber = userMobile.getText().toString();

        if (profileImage.getDrawable() != null) {
            Toast.makeText(this, "Profile Image Must be inserted.  Please Try Again.", Toast.LENGTH_SHORT).show();
            profileImage.requestFocus();

        }

            if (username.isEmpty()) {
            userFullName.setError("Please enter first name");
            userFullName.requestFocus();
            return;
        }
        if (fullName.isEmpty()) {
            userFullName.setError("Please enter last name");
            userFullName.requestFocus();
            return;
        }


        if (phoneNumber.isEmpty()) {
            userMobile.setError("Please enter your phone number");
            userMobile.requestFocus();
            return;
        }

        if (phoneNumber.length() != 10) {
            userMobile.setError("Phone number must 10 digits long");
            userMobile.requestFocus();
        }else{
            loadingBar.setTitle("Saving Information");
            loadingBar.setMessage("Please wait, while we are creating your new Account");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            HashMap userMap = new HashMap();
            userMap.put("username", username);
            userMap.put("fullname",fullName);
            userMap.put("phoneNumber",phoneNumber);

            usersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    sendUserToMainActivity();
                                    Toast.makeText(SetupActivity.this, "Your Account is created successfully. Please check your email for verification", Toast.LENGTH_LONG).show();
                                    loadingBar.dismiss();
                                }else {
                                    Toast.makeText(SetupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }

                            }
                        });

                    }else{
                        String message = task.getException().getMessage();
                        Toast.makeText(SetupActivity.this, "Error Occurred," + message, Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }


    }

    private void sendUserToMainActivity(){
        Intent mainIntent = new Intent(SetupActivity.this, LoginActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }





}
