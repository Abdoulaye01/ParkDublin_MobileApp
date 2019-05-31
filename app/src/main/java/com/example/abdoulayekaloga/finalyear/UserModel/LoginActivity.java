package com.example.abdoulayekaloga.finalyear.UserModel;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdoulayekaloga.finalyear.MainActivity;
import com.example.abdoulayekaloga.finalyear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn, btn_signup;
    private EditText loginMail, loginPassword;
    private TextView reset_Password;
    private FirebaseAuth mAuth;
    private CheckBox showMePassword;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginMail = findViewById(R.id.loginMail);
        loginPassword = findViewById(R.id.loginPassword);
        showMePassword = (AppCompatCheckBox) findViewById(R.id.showMePassword);
        btn_signup = findViewById(R.id.btn_signup);
        loadingBar = new ProgressDialog(this);


        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();


        btn_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent regActivity = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(regActivity);

            }
        });

        reset_Password = findViewById(R.id.reset_password);
        reset_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        showMePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    //show password
                    loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //hide password
                    loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setVisibility(View.VISIBLE);
                String email = loginMail.getText().toString();
                String password = loginPassword.getText().toString();


                if (email.isEmpty()) {
                    loginMail.setError("Please enter a correct email");
                    loginMail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    loginMail.setError("Please enter a correct email");
                    loginMail.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please write your password...", Toast.LENGTH_SHORT).show();
                    reset_Password.requestFocus();
                } else {
                    loadingBar.setTitle("Login");
                    loadingBar.setMessage("Please wait, while we are allowing you to login into your Account...");
                    loadingBar.setCanceledOnTouchOutside(true);
                    loadingBar.show();


                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        if (mAuth.getCurrentUser().isEmailVerified()) {
                                            updateUI();

                                            Toast.makeText(LoginActivity.this, "you are Logged In successfully!  ", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        }else{
                                            Toast.makeText(LoginActivity.this, "Please verify your email address!", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        String message = task.getException().getMessage();
                                        Toast.makeText(LoginActivity.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
            updateUI();
        }
    }


    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(homeActivity);
        this.finish();
    }
    private void SendUserToLoginActivity()
    {
        Intent mainIntent = new Intent(LoginActivity.this, LoginActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

}
