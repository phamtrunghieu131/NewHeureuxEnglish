package com.example.heureuxenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heureuxenglish.QuestionActivity;
import com.example.heureuxenglish.R;
import com.example.heureuxenglish.Word;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class NewWordActivity extends AppCompatActivity {

    TextView newWordText,translateText,englishExText,vietnameseExText;
    ProgressBar progressBar;
    Button mButton;
    Word word;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        creat();

        progressBar.setVisibility(View.VISIBLE);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

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

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                word = snapshot.child("word").child(count+"").getValue(Word.class);
                newWordText.setText(word.getWordInEnglish());
                translateText.setText(word.getWordInVietnamese());
                englishExText.setText(word.getExampleInEnglish());
                vietnameseExText.setText(word.getExampleInVietnamese());
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(NewWordActivity.this, QuestionActivity.class);
               intent.putExtra("count",count);
               intent.putExtra("type",1);
               startActivity(intent);

            }
        });
    }

    private void creat() {
        newWordText = findViewById(R.id.textView);
        translateText = findViewById(R.id.textView4);
        englishExText = findViewById(R.id.textView6);
        vietnameseExText = findViewById(R.id.vietnameseExample);
        mButton = findViewById(R.id.continue_button);
        progressBar = findViewById(R.id.progressBar2);
    }
}