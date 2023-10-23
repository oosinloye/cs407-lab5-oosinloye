package com.cs407.lab05_milestone1;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String username = "username";
        //setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences =
                getSharedPreferences("com.cs407.lab05_milestone1", MODE_PRIVATE);


        if (sharedPreferences.getString("username", "")!= "") {
            // Redirect to the notes page
            Intent intent = new Intent(this, NotesActivity.class);
            startActivity(intent);
            finish();
            return; // Stop further execution since we're finishing this activity
        } else {
            setContentView(R.layout.activity_main);
        }


        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. Get the username from the EditText component and assign it a variable
                EditText usernameEditText = findViewById(R.id.usernameEditText);
                String enteredUsername = usernameEditText.getText().toString().trim();


                // 2. Adding the entered username to SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab05_milestone1", MODE_PRIVATE);
                sharedPreferences.edit().putString("username", enteredUsername).apply();


                // 3. Start the NotesActivity (i.e., the second activity)
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                intent.putExtra("username", enteredUsername);
                startActivity(intent);
            }
        });
    }
}
