package com.example.heureuxenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {

    EditText nameText,ageText;
    TextView emailText;
    Button saveButton;
    ImageView changePasswordImg,backButton;
    Button logOutButton;
    Switch notificationSwitch;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_profile);
        creat();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,10);
        calendar.set(Calendar.SECOND,0);

        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        notificationSwitch.setChecked(sharedPreferences.getBoolean("value",true));

        notificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notificationSwitch.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    notificationSwitch.setChecked(true);

                    Intent intent = new Intent(SettingActivity.this, AlarmReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(SettingActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

                }else {
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    notificationSwitch.setChecked(false);

                    Intent intent = new Intent(SettingActivity.this, AlarmReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(SettingActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(pendingIntent);
                }
            }
        });

        changePasswordImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,NewPasswordActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,MainActivity.class);
                intent.putExtra("number",4);
                startActivity(intent);
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        nameText.setText(user.getName());
                        ageText.setText(user.getAge()+"");
                        emailText.setText(user.getEmail());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String age = ageText.getText().toString();
                mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name")
                        .setValue(name);
                mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("age")
                        .setValue(Integer.parseInt(age));
                Toast.makeText(SettingActivity.this,"Lưu thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }
        
    private void creat() {
        changePasswordImg = findViewById(R.id.change_passwordImg);
        notificationSwitch = findViewById(R.id.turnOn_switch);
        backButton = findViewById(R.id.back_button_inSetting);
        logOutButton = findViewById(R.id.btnLogout);
        nameText = findViewById(R.id.name_settingAct);
        ageText = findViewById(R.id.age_settingAct);
        emailText = findViewById(R.id.email_settingAct);
        saveButton = findViewById(R.id.save_button_setting);
    }
}