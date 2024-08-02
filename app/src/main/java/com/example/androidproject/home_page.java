package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class home_page extends AppCompatActivity {

    private RecyclerView recyclerView;
    private destinationAdapter destinationAdapter;
    private List<destination> destinationList;
    private BottomNavigationView navBar;
    String tag = "home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.recycler_view);
        navBar = findViewById(R.id.navBar);

        recyclerView.setVerticalScrollBarEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        destinationList = new ArrayList<>();
        destinationList.add(new destination("Nusa Penida", "Experience the breathtaking beauty of Nusa Penida, Bali, with its dramatic cliffs, pristine beaches, and vibrant marine life.", "Nusapenida, Bali", 4.6, R.drawable.ic_launcher_foreground));
        destinationList.add(new destination("Carlton Hill", "Enjoy panoramic views of Edinburgh from the historic and scenic Carlton Hill, home to iconic monuments and lush green spaces.", "Edinburg, Scotland", 4.0, R.drawable.ic_launcher_foreground));
        destinationList.add(new destination("Westminster Bridge", "Marvel at the iconic London skyline from Westminster Bridge, offering stunning views of Big Ben.", "London, England", 4.0, R.drawable.ic_launcher_foreground));
        destinationList.add(new destination("Santorini", "Enchanting beauty of Santorini, Greece, where whitewashed buildings, vibrant blue domes, and breathtaking sunsets create a picture-perfect paradise.", "Greece", 4.0, R.drawable.ic_launcher_foreground));


        String item = destinationList.get(1).getLocation();
        Log.i(tag, "added to destinationList " + item);

        destinationAdapter = new destinationAdapter(this, destinationList);
        recyclerView.setAdapter(destinationAdapter);




        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /*switch(item.getItemId()) {
                    case (R.id.nav_home):
                        startActivity(new Intent(Wishlist.this, home_page.class));
                        return true;
                    case (R.id.nav_favorites):
                        return true;
                }*/
                if(item.getItemId() == R.id.nav_favorites) {
                    startActivity(new Intent(home_page.this, Wishlist.class));
                    return true;
                }
                return false;
            }
        });
    }
}
