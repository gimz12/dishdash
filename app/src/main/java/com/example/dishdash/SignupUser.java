package com.example.dishdash;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupUser extends AppCompatActivity {

    private DBHelper DBHelper;
    EditText usernameEditText,passwordEditText,emailEditText;
    Button signup;
    TextView login_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText =(EditText) findViewById(R.id.username);
        passwordEditText =(EditText) findViewById(R.id.password);
        emailEditText =(EditText) findViewById(R.id.email);
        signup =(Button) findViewById(R.id.signup_button);
        login_link =(TextView) findViewById(R.id.login_link);

        DBHelper = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

    }

    public void signup(){
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password); // Note: In a real app, you should hash passwords before storing
        values.put("email", email);
        values.put("registration_date", System.currentTimeMillis());
        values.put("is_admin", 0);

        long result = db.insert("Users", null, values);

        if (result == -1) {
            Toast.makeText(this, "Signup failed. Please try again.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show();
            // Redirect to LoginActivity or another screen
            Intent intent = new Intent(this, LoginUser.class);
            startActivity(intent);
        }
    }
}