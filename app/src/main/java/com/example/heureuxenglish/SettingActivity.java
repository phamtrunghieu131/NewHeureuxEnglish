package com.example.heureuxenglish;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {

    ImageView changePasswordImg,backButton;
    Button logOutButton;
    Switch notificationSwitch;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

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

    }
        
    private void creat() {
        changePasswordImg = findViewById(R.id.change_passwordImg);
        notificationSwitch = findViewById(R.id.turnOn_switch);
        backButton = findViewById(R.id.back_button_inSetting);
        logOutButton = findViewById(R.id.btnLogout);
    }
}