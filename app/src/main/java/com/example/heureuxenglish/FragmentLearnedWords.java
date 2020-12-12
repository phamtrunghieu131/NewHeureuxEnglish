package com.example.heureuxenglish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentLearnedWords extends Fragment {

    TextView notificationText;
    GridView listWord;
    ArrayList<Word> learnedWords = new ArrayList<Word>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    WordAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learnedwords,container,false);

        listWord = view.findViewById(R.id.list_learnedWords);
        notificationText = view.findViewById(R.id.thongbao_learnedWord);

        mDatabase.child("learnedWords").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            notificationText.setVisibility(View.INVISIBLE);
                            learnedWords.clear();
                            for(DataSnapshot dss : snapshot.getChildren()){
                                Word word = dss.getValue(Word.class);
                                learnedWords.add(word);
                            }
                        }
                        adapter = new WordAdapter(getActivity(),R.layout.word_element,learnedWords);
                        listWord.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return view;
    }
}
