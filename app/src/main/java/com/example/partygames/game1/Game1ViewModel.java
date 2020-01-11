package com.example.partygames.game1;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.partygames.DB;
import com.example.partygames.Question;

import java.util.ArrayList;
import java.util.Random;

public class Game1ViewModel extends AndroidViewModel {

    private DB db;
    ArrayList<Question> arr;
    final Random random = new Random();
    private MutableLiveData<String> question;

    public Game1ViewModel(@NonNull Application application) {
        super(application);
        db = new DB(getApplication());
        question = new MutableLiveData<>();
        arr = new ArrayList<Question>(db.getAllQuestion());
        setNewQuestion();
    }

    public LiveData<String> getQuestion() {
        //Log.d("Tag", "getQuestion:" + question.toString());
        return question;
    }

    public void setNewQuestion() {
        if (arr.size()!=0) {
            Integer randomGame = random.nextInt(arr.size());
            question.setValue(arr.get(randomGame).getText());
            arr.remove(arr.get(randomGame));
        } else
            question.setValue("Вы сыграли все вопросы :(");
    }
}
