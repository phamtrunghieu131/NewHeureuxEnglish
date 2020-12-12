package com.example.heureuxenglish;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;

import android.app.Fragment;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentProfile extends Fragment {
    
    TextView learnedWordText,pointText,levelText,hardDayText,nameText,pointToUplvText,nextLvText;
    ImageView settingImg;
    DatabaseReference mDatabase;
    int currentPoint;
    ArrayList<User> listUser = new ArrayList<User>();
    UserAdapter adapter;
    ListView listView;
    ContentLoadingProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        learnedWordText = view.findViewById(R.id.learnedWord);
        pointText = view.findViewById(R.id.point);
        levelText = view.findViewById(R.id.level);
        hardDayText = view.findViewById(R.id.hardDay);
        nameText = view.findViewById(R.id.name);
        pointToUplvText = view.findViewById(R.id.pointToUpLv);
        nextLvText = view.findViewById(R.id.nextLv);
        progressBar = view.findViewById(R.id.expProgressBar);
        settingImg = view.findViewById(R.id.setting_button);
        listView = view.findViewById(R.id.list_user);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);
                currentPoint = user.getPoint();
                int level = currentPoint/100;
                int pointToUpLv = level*100+100-currentPoint;
                learnedWordText.setText(user.getCountLearnedWord()+"");
                nameText.setText(user.getName());
                pointText.setText(currentPoint+"");
                levelText.setText(level + "");
                hardDayText.setText(user.getHardDay()+"");
                pointToUplvText.setText(pointToUpLv+"");
                nextLvText.setText(level+1+"");
                progressBar.setProgress(100-pointToUpLv);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    listUser.clear();
                    for(DataSnapshot dss : snapshot.getChildren()){
                        User user = dss.getValue(User.class);
                        listUser.add(user);
                    }
                    for(int i=0; i<listUser.size() ; i++){
                        for (int j = i+1; j<listUser.size(); j++){
                            if(listUser.get(i).getPoint() < listUser.get(j).getPoint()){
                                User user = listUser.get(i);
                                listUser.set(i,listUser.get(j));
                                listUser.set(j,user);
                            }
                        }
                    }
                }
                adapter = new UserAdapter(getActivity(),R.layout.user_element,listUser);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        settingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SettingActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
