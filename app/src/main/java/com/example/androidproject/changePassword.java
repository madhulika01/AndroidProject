package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class changePassword extends AppCompatActivity {
    public String tag="changePassword";
    private UserDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = UserDatabaseHelper.getInstance(this);
        EditText enterPassword = findViewById(R.id.enterPasswordEditText);
        EditText confirmPassword = findViewById(R.id.confirmPasswordEditText);
        Button resetPassword = findViewById(R.id.resetPassword);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag,"Clicked on reset password");
                String newPassword = enterPassword.getText().toString();
                String confirmNewPassword = confirmPassword.getText().toString();
                if(newPassword.isEmpty() || confirmNewPassword.isEmpty()){
                    Toast.makeText(changePassword.this,"Please fill in all fields",Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(confirmNewPassword)) {
                    Toast.makeText(changePassword.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                }else{
                    changeUserPassword(newPassword);
                    navigateToProfileSection();
                }
            }
        });
    }
    private void changeUserPassword(String newPassword){
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        if(dbHelper.updatePassword(username,newPassword)){
            Toast.makeText(changePassword.this,"Password changed successfully", Toast.LENGTH_SHORT).show();
            Log.i(tag,"Password changed successfully for user: "+username);
        }else{
            Toast.makeText(changePassword.this,"Failed to change password", Toast.LENGTH_SHORT).show();
            Log.i(tag,"Failed to change password for user: "+username);
        }
    }
    private void navigateToProfileSection(){
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        String fullName = sharedPreferences.getString("fullName", "");
        String email = sharedPreferences.getString("email", "");

        Intent intent = new Intent(changePassword.this, ProfileSection.class);
        intent.putExtra("fullName", fullName);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }
}