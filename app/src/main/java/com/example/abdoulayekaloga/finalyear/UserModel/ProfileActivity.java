package com.example.abdoulayekaloga.finalyear.UserModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdoulayekaloga.finalyear.MainActivity;
import com.example.abdoulayekaloga.finalyear.R;
import com.example.abdoulayekaloga.finalyear.firebaseClasses.RecentBookingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView userName, userFullName, userMobile;
    private TextView userMail;
    private ImageView editUserInformation;
    private Button deleteUserInformationBtn;
    private DatabaseReference profileUserRef;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private String currentUserId;
    FirebaseUser currentUser;
    ScrollView scrollView;

    //firebase
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initialise views

        profileImage = findViewById(R.id.userProfileImage);
        userName = findViewById(R.id.userName);
        userMail = findViewById(R.id.userMail);
        userFullName = findViewById(R.id.userFullName);
        userMobile = findViewById(R.id.userMobile);
        editUserInformation = findViewById(R.id.editUserInformation);
        deleteUserInformationBtn = findViewById(R.id.deleteUserInformationBtn);
        scrollView = findViewById(R.id.scrollView);

        setupFirebaseListener();
        //final FirebaseUser currentUser1 = FirebaseAuth.getInstance().getCurrentUser();

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        profileUserRef = FirebaseDatabase.getInstance().getReference().child("users");

        editUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToUpdateProfile();

            }
        });

        deleteUserInformationBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileActivity.this);
                dialog.setTitle("Are you Sure?");
                dialog.setMessage("Deleting this account will result in completely removing your account " +
                        " from the system and you won't be able to access the app. ");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
//

                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

                                    db.removeValue();

                                    FirebaseAuth.getInstance().signOut();

                                    View  view = findViewById(R.id.deleteUserInformationBtn);
                                    Snackbar snackbar = Snackbar.make(view,"Deleted out !",Snackbar.LENGTH_INDEFINITE);
//                            .setAction("DISMISS", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            });
                                    snackbar.show();
                                   // Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   // startActivity(intent);
                                    Toast.makeText(ProfileActivity.this, "Account Deleted", Toast.LENGTH_LONG).show();


                                }else{
                                    Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });

                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });
       if (currentUser != null  && currentUser.isEmailVerified()) {
           currentUserId = currentUser.getUid();
           profileUserRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if (dataSnapshot.exists()) {

                       String myFullName = dataSnapshot.child("fullname").getValue().toString();
                       String myUserName = dataSnapshot.child("username").getValue().toString();
                       String myPhoneNumber = dataSnapshot.child("phoneNumber").getValue().toString();
                       if (dataSnapshot.hasChild("profileimage")) {
                           String image = dataSnapshot.child("profileimage").getValue().toString();
                           Picasso.get().load(image).placeholder(R.drawable.registration).into(profileImage);

                       }
                       userMail.setText("Email: " + currentUser.getEmail());
                       userFullName.setText("~ "+myFullName +" ~");
                       userName.setText("@ " + myUserName);
                       userMobile.setText("Mobile: " + myPhoneNumber);

                   }

               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
       }

        if (currentUser == null) {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(ProfileActivity.this, "Your are not register,Please register or log in", Toast.LENGTH_LONG).show();
        }


    }

    private void sendUserToUpdateProfile() {
        Intent intent = new Intent(ProfileActivity.this, Update_user_profileActivity.class);
        startActivity(intent);
    }

    private void setupFirebaseListener(){
       // Log.d(TAG, "setupFirebaseListener: setting up the auth state listener.");
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                 //   Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                }else{
                   // Log.d(TAG, "onAuthStateChanged: signed_out");

                    Toast.makeText(ProfileActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }

}
