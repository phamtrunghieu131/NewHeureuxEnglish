package com.example.heureuxenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListRoomActivity extends AppCompatActivity {

    GridView listRoom;
    ArrayList<Room> list = new ArrayList<Room>();
    RoomAdapter adapter;
    User currentUser;
    ImageView closeButton;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room);
        creat();

        mDatabase.child("room").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    list.clear();
                    for(DataSnapshot dss : snapshot.getChildren()){
                        Room room = dss.getValue(Room.class);
                        list.add(room);
                    }
                }
                adapter = new RoomAdapter(ListRoomActivity.this,R.layout.room_element,list);
                listRoom.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Room room = list.get(position);
                if(room.getStatus()==0){
                    FirebaseDatabase.getInstance().getReference("room").child(room.getName()).child("guest").setValue(currentUser);
                    FirebaseDatabase.getInstance().getReference("room").child(room.getName()).child("status").setValue(1);
                    Intent intent = new Intent(ListRoomActivity.this,GuestRoomActivity.class);
                    intent.putExtra("roomName",room.getName());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ListRoomActivity.this,"Phòng đã đầy",Toast.LENGTH_SHORT).show();
                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListRoomActivity.this,MainActivity.class);
                intent.putExtra("number",3);
                startActivity(intent);
            }
        });
    }

    private void creat() {
        listRoom = findViewById(R.id.list_room);
        closeButton = findViewById(R.id.closeButton_listRoom);
    }
}