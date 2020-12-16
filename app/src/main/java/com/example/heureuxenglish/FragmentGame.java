package com.example.heureuxenglish;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentGame extends Fragment {

    Button createRoomButton,chooseRoomButton;
    DatabaseReference mDatabase;
    ArrayList<Room> rooms = new ArrayList<Room>();
    User host;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game,container,false);

        createRoomButton = view.findViewById(R.id.createRoom_button);
        chooseRoomButton = view.findViewById(R.id.chooseRoom_button);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        host = snapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        mDatabase.child("room").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    rooms.clear();
                    for (DataSnapshot dss : snapshot.getChildren()){
                        Room room = dss.getValue(Room.class);
                        rooms.add(room);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        createRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_create_id_room);

                Button createButton = dialog.findViewById(R.id.create_button);
                Button cancelButton = dialog.findViewById(R.id.cancel_button);
                final EditText nameRoom = dialog.findViewById(R.id.nameRoom_text);

                createButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = nameRoom.getText().toString();
                        if(name.isEmpty()){
                            nameRoom.setError("Không được để trống");
                            nameRoom.requestFocus();
                            return;
                        }
                        if(name.length() > 6){
                            nameRoom.setError("Không được quá 6 kí tự");
                            nameRoom.requestFocus();
                            return;
                        }

                        for(Room currentRoom : rooms){
                            if (currentRoom.getName().equals(name)){
                                nameRoom.setError("Tên phòng đã tồn tại");
                                return;
                            }
                        }

                        Room room = new Room(name,host);
                        FirebaseDatabase.getInstance().getReference("room").child(name).setValue(room);

                        Intent intent = new Intent(getActivity(),HostRoomActivity.class);
                        intent.putExtra("roomName",name);
                        startActivity(intent);
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        chooseRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ListRoomActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}