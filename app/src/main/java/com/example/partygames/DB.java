package com.example.partygames;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {

    private static final String DB_TABLE = "question";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IDFB = "idFarebase";
    public static final String COLUMN_COLUSER = "colUser";
    public static final String COLUMN_HARD = "hard";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_TEXT = "text";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_IDFB + " text, " +
                    COLUMN_COLUSER + " integer, " +
                    COLUMN_HARD + " integer, " +
                    COLUMN_SUBJECT + " text, " +
                    COLUMN_TEXT + " text" +
                    ");";

    private static final String TAG = "TAG";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "question";

    public DB(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void addQuestion(Question question) {
        //Log.i(TAG, "MyDatabaseHelper.addNote ... " + question.getText());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IDFB, question.getIdFirebase());
        values.put(COLUMN_COLUSER, question.getColUser());
        values.put(COLUMN_HARD, question.getHard());
        values.put(COLUMN_SUBJECT, question.getSubject());
        values.put(COLUMN_TEXT, question.getText());
        db.insert(DB_TABLE, null, values);
        db.close();
    }


    public Question getQuestion(int id) {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[] { COLUMN_ID, COLUMN_IDFB, COLUMN_COLUSER, COLUMN_HARD, COLUMN_SUBJECT, COLUMN_TEXT }, COLUMN_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
        cursor.moveToFirst();
        Question question = new Question();
        question.setId(Integer.parseInt(cursor.getString(0)));
        question.setIdFirebase(cursor.getString(1));
        question.setColUser(Integer.parseInt(cursor.getString(2)));
        question.setHard(Integer.parseInt(cursor.getString(3)));
        question.setSubject(cursor.getString(4));
        question.setText(cursor.getString(5));
        return question;
    }


    public ArrayList<Question> getAllQuestion() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );
        ArrayList<Question> noteList = new ArrayList<Question>();
        String selectQuery = "SELECT  * FROM " + DB_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(Integer.parseInt(cursor.getString(0)));
                question.setIdFirebase(cursor.getString(1));
                question.setColUser(Integer.parseInt(cursor.getString(2)));
                question.setHard(Integer.parseInt(cursor.getString(3)));
                question.setSubject(cursor.getString(4));
                question.setText(cursor.getString(5));
                noteList.add(question);
            } while (cursor.moveToNext());
        }
        return noteList;
    }

    public int getCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );
        String countQuery = "SELECT  * FROM " + DB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //public int updateNote(Question note) {
       // Log.i(TAG, "MyDatabaseHelper.updateNote ... "  + note.getNoteTitle());
      //  SQLiteDatabase db = this.getWritableDatabase();
      //  ContentValues values = new ContentValues();
      //  values.put(COLUMN_NOTE_TITLE, note.getNoteTitle());
      //  values.put(COLUMN_NOTE_CONTENT, note.getNoteContent());
      //  return db.update(TABLE_NOTE, values, COLUMN_NOTE_ID + " = ?", new String[]{String.valueOf(note.getNoteId())});
    //}

    public void deleteNote(Question question) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + question.getText() );
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, COLUMN_ID + " = ?", new String[] { String.valueOf(question.getId()) });
        db.close();
    }

    public void deleteOll() {
        Log.i(TAG, "MyDatabaseHelper.deleteoll ... ");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
        db.close();
    }

}
