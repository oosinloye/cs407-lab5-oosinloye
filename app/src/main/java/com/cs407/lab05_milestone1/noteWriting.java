package com.cs407.lab05_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import android.content.Context;
import android.util.Log;
import android.view.View;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class noteWriting extends AppCompatActivity {

    private int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_writing);
        Intent intent = getIntent();

        SharedPreferences sharedPref = getSharedPreferences("com.cs407.lab05_milestone1", Context.MODE_PRIVATE); // Replace "YourSharedPrefName" with your actual shared preference name
        String username = sharedPref.getString("username", "");

        noteId = intent.getIntExtra("noteId", -1);
        Log.i("NoteID 1", Integer.toString(noteId));
        EditText noteEditText = findViewById(R.id.noteEditText);
        //String username = intent.getStringExtra("username");

        Context context = getApplicationContext();
        SQLiteDatabase sqlLiteDatabase = context.openOrCreateDatabase("YourDatabaseName", MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqlLiteDatabase);

        ArrayList<Notes> testing = dbHelper.readNotes(username);
        // If noteId is not -1, then fetch the corresponding note and display its content
        if (noteId != -1) {
            Notes notes = testing.get(noteId);
            String noteContent = notes.getContent();
            noteEditText.setText(noteContent);
        }


        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title;
                DateFormat dataFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String date = dataFormat.format(new Date());

                if (noteId != -1) {
                    title = "NOTES_" + (noteId + 1);
                    Log.i("info", "printing content to be saved" + noteEditText);
                    dbHelper.updateNotes(noteEditText.getText().toString(), title, date, username);
                } else {
                    title = "NOTES_" + (testing.size() + 1);
                    Log.i("Info", "printing content to be saved" + noteEditText.getText().toString());

                    dbHelper.saveNotes(username, date, title, noteEditText.getText().toString());
                }
                Intent intent = new Intent(noteWriting.this, NotesActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "NOTES_" + (noteId + 1);
                Log.i("NOTE_ID", Integer.toString(noteId));
                dbHelper.deleteNotes(noteEditText.getText().toString(), title);
                Intent intent = new Intent(noteWriting.this, NotesActivity.class);
                startActivity(intent);
            }
        });

    }
}
