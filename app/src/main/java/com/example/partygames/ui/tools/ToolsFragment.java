package com.example.partygames.ui.tools;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.partygames.Question;
import com.example.partygames.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ToolsFragment extends Fragment {

    Button button;

    private ToolsViewModel toolsViewModel;

    FirebaseFirestore dbf;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        dbf = FirebaseFirestore.getInstance();

        toolsViewModel = ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        button = root.findViewById(R.id.button);

        final TextView textView = root.findViewById(R.id.text_tools);
        final TextView textViewGameVersion = root.findViewById(R.id.textViewGameVersion);
        final TextView textViewQuestionCount = root.findViewById(R.id.textViewQuestionCount);

        toolsViewModel.getGameVersion().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewGameVersion.setText(s);
            }
        });
        toolsViewModel.getQuestionCont().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewQuestionCount.setText(s);
            }
        });
        toolsViewModel.LoadDB().observe(this, new Observer<ArrayList<Question>>() {
            @Override
            public void onChanged(ArrayList<Question> questions) {
                if (questions!=null)
                textView.setText(String.valueOf(questions.size()));
                else textView.setText("Нет данных");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //long date = System.currentTimeMillis();
                //SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
                //textViewQuestionCount.setText(sdf.format(date));
                toolsViewModel.loadToDataBaseFromFirebase();
            }
        });

        return root;
    }
}