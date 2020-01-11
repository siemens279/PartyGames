package com.example.partygames.ui.tools;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.partygames.DB;
import com.example.partygames.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ToolsViewModel extends AndroidViewModel {

    private FirebaseFirestore dbf;
    private MutableLiveData<String> mTextGameVersion;
    private MutableLiveData<String> mTextQuestionCount;
    private MutableLiveData<ArrayList<Question>> mTextQuestionDB;
    private DB db;

    //SharedPreferences sharedPreferences;

    public ToolsViewModel(@NonNull Application application) {
        super(application);
        mTextGameVersion = new MutableLiveData<>();
        mTextQuestionCount = new MutableLiveData<>();
        mTextQuestionDB = new MutableLiveData<>();
        dbf = FirebaseFirestore.getInstance();
        db = new DB(getApplication());
       // sharedPreferences = getApplication().getSharedPreferences("Version", MODE_PRIVATE);
       // String text = sharedPreferences.getString(KEY, "");

    }

    public LiveData<String> getGameVersion() {
        getFromFirebaseVersion();
        return mTextGameVersion;
    }

    public LiveData<String> getQuestionCont() {
        mTextQuestionCount.setValue(String.valueOf(db.getCount()));
        return mTextQuestionCount;
    }

    public LiveData<ArrayList<Question>> LoadDB() {
        //mTextQuestionDB.setValue(loadToDataBaseFromFirebase());
        return mTextQuestionDB;
    }

    public void getFromFirebaseVersion() {
        dbf.collection("version")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                mTextGameVersion.setValue(document.getString("version"));
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void loadToDataBaseFromFirebase() {
        ArrayList<Question> questions = new ArrayList<>();
        db.deleteOll();
        dbf.collection("question")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                //mTextGameVersion.setValue(document.getString("version"));
                                Question question = new Question();
                                question.setIdFirebase(document.getId());

                                if(document.getString("colUser").equals("")) question.setColUser(1);
                                    else question.setColUser(Integer.parseInt(document.getString("colUser")));
                                if (document.getString("hard").equals("")) question.setHard(1);
                                    else question.setHard(Integer.parseInt(document.getString("hard")));

                                question.setSubject(document.getString("subject"));
                                question.setText(document.getString("text"));
                                //Log.d("TAG", "--------------------=> " + question.getText());
                                db.addQuestion(question);
                            }
                            mTextQuestionDB.postValue(db.getAllQuestion());
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                        //Log.d("TAG", "--------------------=> " + db.getCount());
                    }
                });
        //return questions;
//        mTextQuestionDB.setValue(db.getAllQuestion());
    }

}