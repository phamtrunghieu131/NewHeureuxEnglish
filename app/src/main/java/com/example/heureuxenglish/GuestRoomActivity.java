package com.example.heureuxenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GuestRoomActivity extends AppCompatActivity {

    TextView roomNameText,hostName,hostWord,hostDay,hostPoint,guessName,guessWord,guessDay,guessPoint;
    Button readyButton;
    ImageView closeButton;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_room);
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

        mDatabase.child("room").child(roomName).child("guest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                guessName.setText(user.getName());
                guessWord.setText("Từ đã học: "+user.getCountLearnedWord());
                guessDay.setText("Số ngày học: "+user.getHardDay()+"");
                guessPoint.setText("Điểm: "+user.getPoint()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("room").child(roomName).child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int status = snapshot.getValue(Integer.class);
                if(status == -1){
                    Intent intent1 = new Intent(GuestRoomActivity.this,ListRoomActivity.class);
                    startActivity(intent1);
                }
                if(status == 3){
                    Intent intent1 = new Intent(GuestRoomActivity.this,InGameActivity.class);
                    intent1.putExtra("roomName",roomName);
                    intent1.putExtra("type","guestCount");
                    startActivity(intent1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GuestRoomActivity.this,ListRoomActivity.class);
                FirebaseDatabase.getInstance().getReference("room").child(roomName).child("status").setValue(0);
                startActivity(intent1);
            }
        });

        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("room").child(roomName).child("status").setValue(2);
                readyButton.setText("Đã sẵn sàng");
                readyButton.setClickable(false);
            }
        });

    }

    private void creat() {
        roomNameText = findViewById(R.id.guestRoom_name);
        hostName = findViewById(R.id.guestRoom_hostName);
        hostWord = findViewById(R.id.guestRoom_hostLearnedWord);
        hostDay = findViewById(R.id.guestRoom_hostHardDay);
        hostPoint = findViewById(R.id.guestRoom_hostPoint);
        guessName = findViewById(R.id.guestRoom_guessName);
        guessWord = findViewById(R.id.guestRoom_guessLearnedWord);
        guessDay = findViewById(R.id.guestRoom_guessHardDay);
        guessPoint = findViewById(R.id.guestRoom_guessPoint);
        closeButton = findViewById(R.id.guestRoom_closeButton);
        readyButton = findViewById(R.id.guestRoom_readyButton);
    }
}