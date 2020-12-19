package com.example.heureuxenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NewPasswordActivity extends AppCompatActivity {

    ImageView backButton;
    Button saveButton;
    EditText oldPasswordText,newPasswordText,confirmPasswordText;
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        creat();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewPasswordActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String oldPassword = oldPasswordText.getText().toString();
                final String newPassword = newPasswordText.getText().toString();
                String confirmNewPassword = confirmPasswordText.getText().toString();

                if(oldPassword.isEmpty()){
                    oldPasswordText.setError("Không được để trống");
                    oldPasswordText.requestFocus();
                    return;
                }

                if(newPassword.isEmpty()){
                    newPasswordText.setError("Không được để trống");
                    newPasswordText.requestFocus();
                    return;
                }

                if(confirmNewPassword.isEmpty()){
                    confirmPasswordText.setError("Không được để trống");
                    confirmPasswordText.requestFocus();
                    return;
                }

                if(!confirmNewPassword.equals(newPassword)){
                    confirmPasswordText.setError("Không trùng khớp");
                    confirmPasswordText.requestFocus();
                    return;
                }

                AuthCredential credential = EmailAuthProvider.getCredential(email,oldPassword);
                mUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Task<Void> voidTask = mUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(NewPasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(NewPasswordActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            oldPasswordText.setError("Mật khẩu không chính xác");
                            oldPasswordText.requestFocus();
                        }
                    }
                });
            }
        });
    }

    private void creat() {
        backButton = findViewById(R.id.back_button_inNewPassword);
        saveButton = findViewById(R.id.save_button_newPassword);
        oldPasswordText = findViewById(R.id.oldPassword);
        newPasswordText = findViewById(R.id.newPassword);
        confirmPasswordText = findViewById(R.id.confirm_newPassword);
    }
}