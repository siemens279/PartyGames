package com.example.partygames.game1;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.partygames.R;
import com.example.partygames.ui.home.HomeViewModel;

public class Game1Fragment extends Fragment {

    private Game1ViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(Game1ViewModel.class);
        View root = inflater.inflate(R.layout.game1_fragment, container, false);
        //LinearLayout ll = root.findViewById(R.id.game1);
        final TextView textViewQuestion = root.findViewById(R.id.textViewQuestionH);
        final Button buttonNext = root.findViewById(R.id.buttonNextG1);
        mViewModel.getQuestion().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewQuestion.setText(s);
            }
        });



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setNewQuestion();
                //textViewQuestion.setText("hgfjhfhjhj");
            }
        });

        return root;
    }
}
