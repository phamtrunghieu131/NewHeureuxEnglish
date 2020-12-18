package com.example.heureuxenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameNotificationActivity extends AppCompatActivity {

    TextView hostCount,guestCount,hostName,guestName;
    Button confirmButton;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String roomName;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_notification);
        creat();

        Intent intent = getIntent();
        roomName = intent.getStringExtra("roomName");
        type = intent.getStringExtra("type");

        mDatabase.child("room").child(roomName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User host = snapshot.child("host").getValue(User.class);
                User guest = snapshot.child("guest").getValue(User.class);

                hostName.setText(host.getName()+" trả lời được:");
                guestName.setText(guest.getName()+" trả lời được:");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mDatabase.child("room").child(roomName).child("hostCount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = snapshot.getValue(Integer.class);
                if(count == -1){
                    hostCount.setText("Đang chờ người chơi...");
                }
                else{
                    hostCount.setText(count+" câu");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("room").child(roomName).child("guestCount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = snapshot.getValue(Integer.class);
                if(count == -1){
                    guestCount.setText("Đang chờ người chơi...");
                }
                else{
                    guestCount.setText(count+" câu");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("hostCount")){
                    Intent intent1 = new Intent(GameNotificationActivity.this,HostRoomActivity.class);
                    intent1.putExtra("roomName",roomName);
                    startActivity(intent1);
                }
                else{
                    mDatabase.child("room").child(roomName).child("status").setValue(1);
                    Intent intent1 = new Intent(GameNotificationActivity.this,GuestRoomActivity.class);
                    intent1.putExtra("roomName",roomName);
                    startActivity(intent1);
                }
            }
        });
    }

    private void creat() {
        hostCount = findViewById(R.id.host_count);
        guestCount = findViewById(R.id.guest_count);
        guestName = findViewById(R.id.guest_countQuestion);
        hostName = findViewById(R.id.host_countQuestion);
        confirmButton = findViewById(R.id.game_confirmButton);
    }
}