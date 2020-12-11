package com.example.heureuxenglish;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.app.Fragment;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class FragmentHomePage extends Fragment {

    TextView roofMaxTargetText,bottomMaxTargetText,currentLearnedWordText;
    ImageView learnWordImg, practiceImg,targetImg,notificationImg;
    ProgressBar progressBar;
    int count;
    int target;
    int hardDay;
    Random random = new Random();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage,container,false);

        learnWordImg = view.findViewById(R.id.hoc_tu_moi);
        practiceImg = view.findViewById(R.id.on_tap);
        targetImg = view.findViewById(R.id.muc_tieu);
        roofMaxTargetText = view.findViewById(R.id.maxTarget_Text);
        bottomMaxTargetText = view.findViewById(R.id.muc_tieu_hien_tai);
        progressBar = view.findViewById(R.id.progess_bar);
        currentLearnedWordText = view.findViewById(R.id.currentLearnedWord);
        notificationImg = view.findViewById(R.id.thong_bao);

        mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lastTimeLearning")
                .addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LatestDay latestDay = snapshot.getValue(LatestDay.class);
                        Date date = new Date();
                        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LatestDay today = new LatestDay(localDate.getDayOfMonth(),localDate.getMonthValue(),localDate.getYear());

                        if(!today.equals(latestDay)){
                            FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("wordToday").setValue(0);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("countLearnedWord")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        count = snapshot.getValue(Integer.class);
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
                        roofMaxTargetText.setText("/ "+target);
                        bottomMaxTargetText.setText(target+"");
                        progressBar.setMax(target);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wordToday")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int wordToday = snapshot.getValue(Integer.class);
                        progressBar.setProgress(wordToday);
                        currentLearnedWordText.setText(wordToday+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        learnWordImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NewWordActivity.class);
                startActivity(intent);
            }
        });

        practiceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count != 0) {
                    Intent intent = new Intent(getActivity(), QuestionActivity.class);
                    intent.putExtra("type", 0);
                    intent.putExtra("count", random.nextInt(count));
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"Chưa có từ nào để ôn tập\n       Hãy học từ mới!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        targetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_target);
                TextView target1Text = dialog.findViewById(R.id.textView15);
                TextView target2Text = dialog.findViewById(R.id.textView14);
                TextView target3Text = dialog.findViewById(R.id.textView13);

                target1Text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("target")
                                .setValue(5);
                        dialog.dismiss();
                    }
                });
                target2Text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("target")
                                .setValue(10);
                        dialog.dismiss();
                    }
                });
                target3Text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("target")
                                .setValue(15);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        notificationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SettingActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
