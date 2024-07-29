package com.example.androidproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Setting extends AppCompatActivity {
    public String tag = "Settings";
    private UserDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LinearLayout accountPrivacy = findViewById(R.id.accountPrivacyLayout);
        LinearLayout notification = findViewById(R.id.notificationLayout);
        LinearLayout language = findViewById(R.id.languageLayout);
        LinearLayout theme = findViewById(R.id.themeLayout);
        LinearLayout delete = findViewById(R.id.deleteLayout);
        RelativeLayout backButton = findViewById(R.id.backButton);
        dbHelper = new UserDatabaseHelper(this);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "back button is pressed");
                Intent intent  = new Intent(Setting.this, ProfileSection.class);
                intent.putExtra("fullName", getIntent().getStringExtra("fullName"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
                finish();
            }
        });

        accountPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Clicked on account privacy");
                Intent intent = new Intent(Setting.this, accountPrivacy.class);
                intent.putExtra("fullName", getIntent().getStringExtra("fullName"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
                finish();
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Clicked on notification");
                Intent intent = new Intent(Setting.this, notifications.class);
                intent.putExtra("fullName", getIntent().getStringExtra("fullName"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
                finish();
            }
        });
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Clicked on language");
            }
        });
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Clicked on theme");
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Clicked on delete");
                showDeleteAccountDialog();
            }
        });
    }
        private void showDeleteAccountDialog() {
            new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAccount();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
        }
        private void deleteAccount() {
            String email = getIntent().getStringExtra("email");
            dbHelper.deleteUser(email);
            clearLoginState();
            Intent intent = new Intent(Setting.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        private void clearLoginState() {
            SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
    }
