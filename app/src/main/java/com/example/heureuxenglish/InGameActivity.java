package com.example.heureuxenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InGameActivity extends AppCompatActivity implements View.OnClickListener {

    TextView question,answer1,answer2,answer3,answer4;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    ArrayList<Answer> answers = new ArrayList<Answer>();
    int randomQuestion;
    String roomName;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
        creat();

        Intent intent = getIntent();
        roomName = intent.getStringExtra("roomName");
        type = intent.getStringExtra("type");



        mDatabase.child("room").child(roomName).child("randomQuestion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                randomQuestion = snapshot.getValue(Integer.class);

                mDatabase.child("word").child(randomQuestion+"").child("mcQuestion").child("question").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String theQuestion = snapshot.getValue(String.class);
                        question.setText(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                mDatabase.child("word").child(randomQuestion+"").child("mcQuestion").child("listAnswer")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    answers.clear();

                                    for(DataSnapshot postSnapshot : snapshot.getChildren()){
                                        Answer answer = postSnapshot.getValue(Answer.class);
                                        answers.add(answer);
                                    }
                                    answer1.setText(answers.get(0).getContent());
                                    answer2.setText(answers.get(1).getContent());
                                    answer3.setText(answers.get(2).getContent());
                                    answer4.setText(answers.get(3).getContent());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);
    }

    private void creat() {
        question = findViewById(R.id.textView7);
        answer1 = findViewById(R.id.textView8);
        answer2 = findViewById(R.id.textView9);
        answer3 = findViewById(R.id.answer_3);
        answer4 = findViewById(R.id.answer_4);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textView8:
                answer1.setBackgroundResource(R.drawable.answer_choose_corner);
                checkAnswer(answers.get(0));
                break;
            case R.id.textView9:
                answer2.setBackgroundResource(R.drawable.answer_choose_corner);
                checkAnswer(answers.get(1));
                break;
            case R.id.answer_3:
                answer3.setBackgroundResource(R.drawable.answer_choose_corner);
                checkAnswer(answers.get(2));
                break;
            case R.id.answer_4:
                answer4.setBackgroundResource(R.drawable.answer_choose_corner);
                checkAnswer(answers.get(3));
                break;
        }
        Intent intent1 = new Intent(InGameActivity.this,GameNotificationActivity.class);
        intent1.putExtra("roomName",roomName);
        intent1.putExtra("type",type);
        startActivity(intent1);
    }

    private void checkAnswer(Answer answer) {
        if(answer.isCorrect()){
            mDatabase.child("room").child(roomName).child(type).setValue(1);
        }
        else{
            mDatabase.child("room").child(roomName).child(type).setValue(0);
        }
    }

}