package com.example.abdoulayekaloga.finalyear.UserModel;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdoulayekaloga.finalyear.MainActivity;
import com.example.abdoulayekaloga.finalyear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText resetEmail;
    private Button resetBtn;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetEmail= findViewById(R.id.resetEmail);
        resetBtn= findViewById(R.id.resetBtn);
        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = resetEmail.getText().toString().trim();
                if (useremail.isEmpty()) {
                    resetEmail.setError("Please enter a correct email");
                    resetEmail.requestFocus();

                }else if (!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
                        resetEmail.setError("Please enter a correct email");
                        resetEmail.requestFocus();

                }else {
                    loadingBar.setTitle("Resetting ");
                    loadingBar.setMessage("Please wait, while we are resetting your account...");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(true);
                    mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(ResetPasswordActivity.this,"Password reset sent in your email address",Toast.LENGTH_LONG).show();
                                            finish();
                                            startActivity(new Intent(ResetPasswordActivity.this,MainActivity.class));
                                            loadingBar.dismiss();
                                        }else {
                                            Toast.makeText(ResetPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(ResetPasswordActivity.this,"Error in sending password to your Email",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
