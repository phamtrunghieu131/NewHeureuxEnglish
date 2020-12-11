package com.example.heureuxenglish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class FragmentGame extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game,container,false);

        final AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.autoComplete_dictionary);
        Button mButton =view.findViewById(R.id.button_translate);
        final TextView mTextView = view.findViewById(R.id.textView_translate);

        return view;
    }
}