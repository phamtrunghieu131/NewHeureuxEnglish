package com.example.heureuxenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class HostRoomActivity extends AppCompatActivity {

    TextView roomNameText,hostName,hostWord,hostDay,hostPoint,waitingText,guessName,guessWord,guessDay,guessPoint;
    Button startButton;
    ImageView closeButton;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String roomName;
    int status;
    Random rd = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_room);
        creat();
        final Intent intent = getIntent();
        roomName = intent.getStringExtra("roomName");
        roomNameText.setText(roomName);

        mDatabase.child("room").child(roomName).child("host").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                hostName.setText(user.getName());
                hostWord.setText("Từ đã học: "+user.getCountLearnedWord());
                hostDay.setText("Số ngày học: "+user.getHardDay()+"");
                hostPoint.setText("Điểm: "+user.getPoint()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("room").child(roomName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                status = snapshot.child("status").getValue(Integer.class);
                if(status == 1 || status == 2 || status == 3){
                    waitingText.setVisibility(View.INVISIBLE);
                    User user = snapshot.child("guest").getValue(User.class);
                    guessName.setText(user.getName());
                    guessWord.setText("Từ đã học: "+user.getCountLearnedWord());
                    guessDay.setText("Số ngày học: "+user.getHardDay()+"");
                    guessPoint.setText("Điểm: "+user.getPoint()+"");
                } else {
                    waitingText.setVisibility(View.VISIBLE);
                    guessName.setText("");
                    guessWord.setText("");
                    guessDay.setText("");
                    guessPoint.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("room").child(roomName).child("status").setValue(-1);
                Intent intent1 = new Intent(HostRoomActivity.this,ListRoomActivity.class);
                startActivity(intent1);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status == 0){
                    Toast.makeText(HostRoomActivity.this,"Hãy đợi người chơi khác...",Toast.LENGTH_SHORT).show();
                }
                if (status == 1){
                    Toast.makeText(HostRoomActivity.this,"Đối thủ chưa sẵn sàng...",Toast.LENGTH_SHORT).show();
                }
                if(status == 2){
                    FirebaseDatabase.getInstance().getReference("room").child(roomName).child("hostCount").setValue(-1);
                    FirebaseDatabase.getInstance().getReference("room").child(roomName).child("guestCount").setValue(-1);
                    FirebaseDatabase.getInstance().getReference("room").child(roomName).child("randomQuestion").setValue(rd.nextInt(10));
                    FirebaseDatabase.getInstance().getReference("room").child(roomName).child("status").setValue(3);
                    Intent intent1 = new Intent(HostRoomActivity.this,InGameActivity.class);
                    intent1.putExtra("roomName",roomName);
                    intent1.putExtra("type","hostCount");
                    startActivity(intent1);
                }
            }
        });
    }

    private void creat() {
        roomNameText = findViewById(R.id.hostRoom_name);
        hostName = findViewById(R.id.hostRoom_hostName);
        hostWord = findViewById(R.id.hostRoom_hostLearnedWord);
        hostDay = findViewById(R.id.hostRoom_hostHardDay);
        hostPoint = findViewById(R.id.hostRoom_hostPoint);
        closeButton = findViewById(R.id.closeButton);
        waitingText = findViewById(R.id.waiting_text);
        guessName = findViewById(R.id.hostRoom_guessName);
        guessWord = findViewById(R.id.hostRoom_guessLearnedWord);
        guessDay = findViewById(R.id.hostRoom_guessHardDay);
        guessPoint = findViewById(R.id.hostRoom_guessPoint);
        startButton = findViewById(R.id.hostRoom_startButton);
    }
}