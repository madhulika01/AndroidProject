package com.example.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class accountPrivacy extends AppCompatActivity {
    private final String tag ="AccountPrivacy";
    private SwitchCompat third_party;
    private SwitchCompat receive_emails;
    private Button savePreferences;
    private SharedPreferences accountPrivacyPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_privacy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        third_party = findViewById(R.id.third_party_toggle);
        receive_emails = findViewById(R.id.receive_mails_toggle);
        savePreferences = findViewById(R.id.savePreferences);
        accountPrivacyPreferences = getSharedPreferences("AccountPrivacyPrefs",MODE_PRIVATE);
        loadAccountPrivacySettings();
        RelativeLayout backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "back button is pressed");
                Intent intent  = new Intent(accountPrivacy.this, Setting.class);
                intent.putExtra("fullName", getIntent().getStringExtra("fullName"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
                finish();
            }
        });
        savePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAccountPrivacySettings();
            }
        });
    }
    private void loadAccountPrivacySettings(){
        boolean thirdParty = accountPrivacyPreferences.getBoolean("thirdParty",true);
        boolean receiveEmails = accountPrivacyPreferences.getBoolean("receiveEmails",true);
        third_party.setChecked(thirdParty);
        receive_emails.setChecked(receiveEmails);
    }
    private void saveAccountPrivacySettings(){
        SharedPreferences.Editor editor = accountPrivacyPreferences.edit();
        editor.putBoolean("thirdParty",third_party.isChecked());
        editor.putBoolean("receiveEmails",receive_emails.isChecked());
        editor.apply();
        Log.i(tag,"Privacy settings saved");
        Toast.makeText(accountPrivacy.this,"Privacy settings saved",Toast.LENGTH_SHORT).show();
    }
}