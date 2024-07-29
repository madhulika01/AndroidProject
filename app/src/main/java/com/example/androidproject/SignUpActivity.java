package com.example.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {
    private EditText fullNameEdit;
    private EditText usernameEdit;
    private EditText emailIDEdit;
    private EditText passwordEdit;
    private EditText confirmPasswordEdit;
    private Button signUpButton;
    private TextView alreadyUser;
    private UserDatabaseHelper dbHelper;
    private String tag="SignUp Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fullNameEdit = findViewById(R.id.fullNameEdit);
        usernameEdit = findViewById(R.id.usernameEdit);
        emailIDEdit = findViewById(R.id.emailIDEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        confirmPasswordEdit = findViewById(R.id.confirmPasswordEdit);
        signUpButton = findViewById(R.id.signUpButton);
        alreadyUser = findViewById(R.id.alreadyUser);

        dbHelper = new UserDatabaseHelper(this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEdit.getText().toString();
                String username = usernameEdit.getText().toString();
                String email = emailIDEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String confirmPassword = confirmPasswordEdit.getText().toString();
                if(isInputValid(fullName,username,email,password,confirmPassword)) {
                    if (addUserToDatabase(fullName, username, email, password)) {
                        Toast.makeText(SignUpActivity.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                        saveLoginState(username,fullName,email);
                        navigateNext();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignUpActivity.this, "Please check your inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean isInputValid(String fullName,String username,String email,String password,String confirmPassword){
        return !fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() ||confirmPassword.isEmpty() && !password.equals(confirmPassword);
    }

    private boolean addUserToDatabase(String fullName,String username,String email, String password){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(UserDatabaseHelper.FULL_NAME,fullName);
        cValues.put(UserDatabaseHelper.USERNAME,username);
        cValues.put(UserDatabaseHelper.EMAIL,email);
        cValues.put(UserDatabaseHelper.PASSWORD,password);
        Log.i(tag,"Entry added to the Database Users with Username" + username +" and password "+password);
        long newRowId = db.insert(UserDatabaseHelper.USERS, null,cValues);
        return newRowId!=-1;
    }
    private void saveLoginState(String username, String fullName, String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("username", username);
        editor.putString("fullName", fullName);
        editor.putString("email", email);
        editor.apply();
    }

    private void navigateNext() {
        Intent intent = new Intent(SignUpActivity.this, ProfileSection.class);
        startActivity(intent);
        finish();
    }
}