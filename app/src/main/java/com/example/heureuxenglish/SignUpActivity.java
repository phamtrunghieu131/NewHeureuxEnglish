package com.example.heureuxenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    TextView login_text_view;
    EditText nameText,emailText,passWordText,confirmPassWordText;
    Button signUpButton;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        create();
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        login_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void register(){
        final String name = nameText.getText().toString();
        final String email = emailText.getText().toString();
        String password = passWordText.getText().toString();
        String confirmPassword = confirmPassWordText.getText().toString();

        if(name.isEmpty()){
            nameText.setError("Không được để trống");
            nameText.requestFocus();
            return;
        }
        if(email.isEmpty()){
            emailText.setError("Không được để trống");
            emailText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Email không hợp lệ");
            emailText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passWordText.setError("Không được để trống");
            passWordText.requestFocus();
            return;
        }
        if(password.length() < 6){
            passWordText.setError("Mật khẩu phải trên 6 chữ số");
            passWordText.requestFocus();
        }
        if (!confirmPassword.equals(password)){
            confirmPassWordText.setError("Không trùng khớp!");
            confirmPassWordText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name,email);
                            FirebaseDatabase.getInstance().getReference("user")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(intent);
                        } else {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void create(){
        login_text_view = (TextView) findViewById(R.id.login_text_view);
        nameText = findViewById(R.id.username_edit_text);
        emailText = findViewById(R.id.email_edit_text);
        passWordText = findViewById(R.id.password_edit_text);
        confirmPassWordText = findViewById(R.id.confirm_password_edit_text);
        signUpButton = findViewById(R.id.sign_up_button);
        progressBar = findViewById(R.id.progressBar);
    }
}