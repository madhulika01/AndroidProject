package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
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

public class Wishlist extends AppCompatActivity implements WishlistAdapter.OnItemClickListener {
    private RecyclerView wishlistRecycler;
    private WishlistAdapter wishlistAdapter;
    private List<destination> destinationList;
    private BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wishlist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        wishlistRecycler = findViewById(R.id.wishlist_recycler);
        wishlistRecycler.setVerticalScrollBarEnabled(true);
        wishlistRecycler.setLayoutManager(new LinearLayoutManager(this));
        navBar = findViewById(R.id.navBar);

        destinationList = new ArrayList<>();
        destinationList.add(new destination("Nusa Penida", "Experience the breathtaking beauty of Nusa Penida, Bali, with its dramatic cliffs, pristine beaches, and vibrant marine life.", "Nusapenida, Bali", 4.6, R.drawable.ic_launcher_foreground));
        destinationList.add(new destination("Carlton Hill", "Enjoy panoramic views of Edinburgh from the historic and scenic Carlton Hill, home to iconic monuments and lush green spaces.", "Edinburg, Scotland", 4.0, R.drawable.ic_launcher_foreground));
        destinationList.add(new destination("Westminster Bridge", "Marvel at the iconic London skyline from Westminster Bridge, offering stunning views of Big Ben.", "London, England", 4.0, R.drawable.ic_launcher_foreground));
        destinationList.add(new destination("Santorini", "Enchanting beauty of Santorini, Greece, where whitewashed buildings, vibrant blue domes, and breathtaking sunsets create a picture-perfect paradise.", "Greece", 4.0, R.drawable.ic_launcher_foreground));

        wishlistAdapter = new WishlistAdapter(this, destinationList, this);
        wishlistRecycler.setAdapter(wishlistAdapter);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home) {
                    startActivity(new Intent(Wishlist.this, home_page.class));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(destination destination) {
        Intent intent = new Intent(this, LocationPage.class);
        intent.putExtra("location_name", destination.getTitle());
        intent.putExtra("location_details", destination.getDescription());
        startActivity(intent);
    }
}