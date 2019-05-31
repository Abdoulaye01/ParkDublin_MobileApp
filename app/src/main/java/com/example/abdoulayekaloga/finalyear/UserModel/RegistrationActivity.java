package com.example.abdoulayekaloga.finalyear.UserModel;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdoulayekaloga.finalyear.MainActivity;
import com.example.abdoulayekaloga.finalyear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegistrationActivity extends AppCompatActivity   {

    private Button createAccountButton;
    private EditText  userMail, userPassword, confirmPassword;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private CheckBox  showMePassword ;


    @Override
protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        //initialise views
        userMail = findViewById(R.id.userMail);
        userPassword = findViewById(R.id.userPassword);
        showMePassword = (AppCompatCheckBox) findViewById(R.id.showMePassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        createAccountButton = findViewById(R.id.createAccountButton);

        loadingBar  = new ProgressDialog(this);


        createAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                CreateNewAccount();

            }
            });

        showMePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    //show password
                    userPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }else{
                    //hide password
                    userPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
    }


    private  void updateUI(){
        Intent homeActivity = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(homeActivity);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
            updateUI();
        }
    }



    private void CreateNewAccount(){

        final String email = userMail.getText().toString();
        final String password = userPassword.getText().toString();
        final String userconfirmPassword = confirmPassword.getText().toString();


        if (email.isEmpty()) {
            userMail.setError("Please enter a correct email");
            userMail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userMail.setError("Please enter a correct email");
            userMail.requestFocus();
        } else if (password.isEmpty()) {
            userPassword.setError("Please enter your password");
            userPassword.requestFocus();
        }else if (password.length() < 6) {
            userPassword.setError("password must greater 6 character");
            userPassword.requestFocus();
        }else if (!password.equals(userconfirmPassword)) {
            userPassword.setError("Password are not identical");
        }else{
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait, while we are creating your account");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendUserToSetupActivity();
                                Toast.makeText(RegistrationActivity.this, "You are authenticated...", Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            }else{
                                String message = task.getException().getMessage();
                                Toast.makeText(RegistrationActivity.this, "Error Occurred..."+ message, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }


    }

    private void sendUserToSetupActivity(){
        Intent setupIntent = new Intent(RegistrationActivity.this, SetupActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }

}
