package com.cs407.lab05_milestone1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.SharedPreferences;  // Import for SharedPreferences
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.database.Cursor;
import java.util.ArrayList;
import android.view.View;

import android.widget.TextView;


public class NotesActivity extends AppCompatActivity {
private ArrayList<String> displayNotes = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = findViewById(R.id.notesToolbar);
        setSupportActionBar(toolbar);

        //1. Display welcome message. Get the username from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("com.cs407.lab05_milestone1", Context.MODE_PRIVATE); // Replace "YourSharedPrefName" with your actual shared preference name
        String username = sharedPref.getString("username", "");
        if (username != null && !username.isEmpty()) {
            TextView welcomeTextView = findViewById(R.id.welcomeTextView);  // Assuming you have a TextView to display the welcome message
            welcomeTextView.setText("Welcome, " + username + " to notes app!");
        }
        Context context = getApplicationContext();
        SQLiteDatabase sqlLiteDatabase = context.openOrCreateDatabase("YourDatabaseName", MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqlLiteDatabase);
        //3. Read the notes
        ArrayList<Notes> notes1 = dbHelper.readNotes(username);


        //3. Initialize the notes class variable using readNotes method implemented in the DBHelper class
        // Use the class username you got from SharedPreferences as a parameter to readNotes method.
        //ArrayList<Notes> notes1 = Notes.getContent();

        //4. Create an ArrayList<String> object for iterating over notes.
       // ArrayList<String> displayNotes = new ArrayList<>();

        for (Notes notes : notes1) {
            displayNotes.add(String.format("Title:%s\nDate:%s\n", notes.getTitle(), notes.getDate()));
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView notesListView = findViewById(R.id.notesListView); //
        notesListView.setAdapter(adapter);

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), noteWriting.class);
                intent.putExtra("username", username);

                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.addNote) {
            Intent intent = new Intent(this, noteWriting.class);
            startActivity(intent);
            finish();
            return true;
        } else if (itemId == R.id.logout) {
            // Handle the Logout action
            // Retrieve the username stored in SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab05_milestone1", MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "");
            sharedPreferences.edit().clear().apply();


            // Redirect to the login screen
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
