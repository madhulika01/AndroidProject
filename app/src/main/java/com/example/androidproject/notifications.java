package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class notifications extends AppCompatActivity {
    private final String tag="Notifications";
    private SwitchCompat remindersToggle ;
    private SwitchCompat trendingPlacesToggle;
    private SwitchCompat feedbackToggle;
    private SwitchCompat supportToggle;
    private Button saveNotificationPref;
    private SharedPreferences notificationsPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notifications);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        remindersToggle = findViewById(R.id.reminders_toggle);
        trendingPlacesToggle = findViewById(R.id.trending_places_toggle);
        feedbackToggle = findViewById(R.id.feedback_toggle);
        supportToggle = findViewById(R.id.support_toggle);
        saveNotificationPref = findViewById(R.id.saveNotifiationPref);
        notificationsPrefs = getSharedPreferences("NotificationPrefs",MODE_PRIVATE);
        loadNotificationsSettings();
        RelativeLayout backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Back button is pressed");
                Intent intent  = new Intent(notifications.this, Setting.class);
                intent.putExtra("fullName", getIntent().getStringExtra("fullName"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
                finish();
            }
        });
        saveNotificationPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotificationsSettings();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadNotificationsSettings();
    }
    private void loadNotificationsSettings() {
        boolean reminders = notificationsPrefs.getBoolean("reminders", true);
        boolean trendingPlaces = notificationsPrefs.getBoolean("trendingPlaces", true);
        boolean feedback = notificationsPrefs.getBoolean("feedback", true);
        boolean support = notificationsPrefs.getBoolean("support", true);

        remindersToggle.setChecked(reminders);
        trendingPlacesToggle.setChecked(trendingPlaces);
        feedbackToggle.setChecked(feedback);
        supportToggle.setChecked(support);
    }

    private void saveNotificationsSettings() {
       boolean reminders = remindersToggle.isChecked();
       boolean trendingPlaces = trendingPlacesToggle.isChecked();
       boolean feedback = feedbackToggle.isChecked();
       boolean support = supportToggle.isChecked();
       new saveNotificationsPreferencesTask(this,notificationsPrefs,reminders,trendingPlaces,feedback,support).execute();
    }
    private static class saveNotificationsPreferencesTask extends AsyncTask<Void, Void, Void>{
        private Context context;
        private SharedPreferences sharedPreferences;
        private boolean reminders;
        private boolean trendingPlaces;
        private boolean feedback;
        private boolean support;
        public saveNotificationsPreferencesTask(Context context,SharedPreferences sharedPreferences,boolean reminders,boolean trendingPlaces,boolean feedback,boolean support){
            this.context = context;
            this.sharedPreferences = sharedPreferences;
            this.reminders = reminders;
            this.trendingPlaces = trendingPlaces;
            this.feedback = feedback;
            this.support = support;
        }
        @Override
        protected Void doInBackground(Void... voids){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("reminders",reminders);
            editor.putBoolean("trendingPlaces",trendingPlaces);
            editor.putBoolean("feedback",feedback);
            editor.putBoolean("support",support);
            editor.apply();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            Toast.makeText(context,"Notification settings saved",Toast.LENGTH_SHORT).show();
        }
    }
}