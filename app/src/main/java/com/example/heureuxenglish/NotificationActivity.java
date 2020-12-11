package com.example.heureuxenglish;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {

    TextView notificationText,newWordText;
    Button mbutton;
    boolean pass;
    int count;
    int target;
    int hardDay;
    int wordToday;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        creat();

        Intent intent = getIntent();
        pass = intent.getBooleanExtra("pass",true);
        count = intent.getIntExtra("count",10);

        mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wordToday")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        wordToday = snapshot.getValue(Integer.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("hardDay")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        hardDay = snapshot.getValue(Integer.class);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("target")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        target = snapshot.getValue(Integer.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        if(pass){
            notificationText.setText("Chúc mừng bạn đã học được một từ mới");
            mbutton.setText("Tiếp tục học từ mới");
            newWordText.setVisibility(View.VISIBLE);
            mDatabase.child("word").child(count+"").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Word word = snapshot.getValue(Word.class);
                    newWordText.setText(word.getWordInEnglish()+": "+word.getWordInVietnamese() );
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LatestDay latestDay = new LatestDay(localDate.getDayOfMonth(),localDate.getMonthValue(),localDate.getYear());

            FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("countLearnedWord").setValue(count+1);
            FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("lastTimeLearning").setValue(latestDay);
        }else {
            notificationText.setText("Bạn đã làm sai 1 số câu hỏi, bạn sẽ phải học lại từ này :((");
            mbutton.setText("Học lại");
        }

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass){
                    if(wordToday+1 == target){
                        FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("hardDay").setValue(hardDay + 1);
                    }
                    FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("wordToday").setValue(wordToday + 1);
                }
                Intent intent = new Intent(NotificationActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void creat() {
        notificationText = findViewById(R.id.textView11);
        mbutton = findViewById(R.id.notification_act_button);
        newWordText = findViewById(R.id.newWord_notificationAct);
    }
}