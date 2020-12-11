package com.example.heureuxenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView questionText,answer_1,answer_2,answer_3,answer_4;
    ArrayList<Answer> answers = new ArrayList<Answer>();
    boolean pass = true;
    int currentPoint;
    int count;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        creat();

        Intent intent = getIntent();
        count = intent.getIntExtra("count",10);
        type = intent.getIntExtra("type",1);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("word").child(count+"").child("mcQuestion").child("listAnswer")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    answers.clear();

                    for(DataSnapshot postSnapshot : snapshot.getChildren()){
                        Answer answer = postSnapshot.getValue(Answer.class);
                        answers.add(answer);
                    }
                    answer_1.setText(answers.get(0).getContent());
                    answer_2.setText(answers.get(1).getContent());
                    answer_3.setText(answers.get(2).getContent());
                    answer_4.setText(answers.get(3).getContent());
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mDatabase.child("word").child(count+"").child("mcQuestion").child("question")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String question = snapshot.getValue(String.class);
                questionText.setText(question+"...");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("point")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currentPoint = snapshot.getValue(Integer.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        answer_1.setOnClickListener(this);
        answer_2.setOnClickListener(this);
        answer_3.setOnClickListener(this);
        answer_4.setOnClickListener(this);
    }

    private void creat() {
        questionText = findViewById(R.id.textView7);
        answer_1 = findViewById(R.id.textView8);
        answer_2 = findViewById(R.id.textView9);
        answer_3 = findViewById(R.id.answer_3);
        answer_4 = findViewById(R.id.answer_4);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textView8:
                checkAnswer(answer_1,answers.get(0));
                break;
            case R.id.textView9:
                checkAnswer(answer_2,answers.get(1));
                break;
            case R.id.answer_3:
                checkAnswer(answer_3,answers.get(2));
                break;
            case R.id.answer_4:
                checkAnswer(answer_4,answers.get(3));
                break;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(QuestionActivity.this,QuestionTwoActivity.class);
                intent.putExtra("pass",pass);
                intent.putExtra("count",count);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        },800);
    }

    private void checkAnswer(TextView textAnswer, Answer answer) {
        if(answer.isCorrect()){
            textAnswer.setBackgroundResource(R.drawable.correct_answer_corner);
            FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("point").setValue(currentPoint+10);
        }
        else {
            textAnswer.setBackgroundResource(R.drawable.wrong_answer_corner);
            if(answers.get(0).isCorrect()){
                answer_1.setBackgroundResource(R.drawable.correct_answer_corner);
            }
            else if(answers.get(1).isCorrect()){
                answer_2.setBackgroundResource(R.drawable.correct_answer_corner);
            }
            else if(answers.get(2).isCorrect()){
                answer_3.setBackgroundResource(R.drawable.correct_answer_corner);
            }
            else if(answers.get(3).isCorrect()){
                answer_4.setBackgroundResource(R.drawable.correct_answer_corner);
            }
            pass = false;
        }
    }
}