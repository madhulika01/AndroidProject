/*
package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wishlist extends AppCompatActivity implements WishlistAdapter.OnItemClickListener {
    private RecyclerView wishlistRecycler;
    private WishlistAdapter wishlistAdapter;
    //private List<destination> destinationList;
    private List<Map<String, Object>> destinationList;
    private List<String> userWishlist;
    private BottomNavigationView navBar;

    private String tag = "Wishlist";
    private String TAG = "Wishlist";

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

        destinationList = new ArrayList<Map<String, Object>>();
        try {
            destinationList = loadData("database.csv");
        } catch (IOException e) {
            Log.e(tag, "Error loading data: " + e.getMessage());
        }
        userWishlist = new ArrayList<String>();
        userWishlist.add("Tokyo");
        userWishlist.add("Paris");


        wishlistAdapter = new WishlistAdapter(this, destinationList, userWishlist, this::onItemClick);
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
    private List<Map<String, Object>> loadData(String filename) throws IOException {
        List<Map<String, Object>> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(filename)))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, "Reading line: " + line); // Log the line read
                String[] parts = line.split(",");
                if (parts.length == 10) { // Adjusted to match the number of columns
                    try {
                        Map<String, Object> map = new HashMap<>();
                        map.put("Country", parts[0].trim());
                        map.put("City", parts[1].trim());*/
/*
                        map.put("One", parts[2].trim());
                        map.put("Two", parts[3].trim());
                        map.put("Three", parts[4].trim());
                        map.put("Four", parts[5].trim());*//*

                        map.put("ImageURL", parts[6].trim());
                        */
/*map.put("Description", parts[7].trim());
                        map.put("Lat", parts[8].trim());
                        map.put("Long", parts[9].trim());*//*

                        data.add(map); // Add the map to the data list
                        Log.d(TAG, "Item added: " + map);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Error parsing cluster value in line: " + line + " - " + e.getMessage());
                    }
                } else {
                    Log.e(TAG, "Unexpected number of columns in line: " + line);
                    Log.e(TAG, "Read: " + parts.length);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading file: " + e.getMessage());
        }
        Log.d(TAG, "Data loaded, total items: " + data.size());
        return data;
    }
}*/

package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wishlist extends AppCompatActivity implements WishlistAdapter.OnItemClickListener {
    private RecyclerView wishlistRecycler;
    private WishlistAdapter wishlistAdapter;
    private List<Map<String, Object>> destinationList;
    private List<String> userWishlist;
    private BottomNavigationView navBar;


    private String tag = "Wishlist";
    private String TAG = "Wishlist";

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
        try {
            destinationList = loadData("database.csv");
        } catch (IOException e) {
            Log.e(tag, "Error loading data: " + e.getMessage());
        }
        userWishlist = new ArrayList<>();
        userWishlist.add("Tokyo");
        userWishlist.add("Paris");

        wishlistAdapter = new WishlistAdapter(this, destinationList, userWishlist, this);
        wishlistRecycler.setAdapter(wishlistAdapter);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home) {
                    startActivity(new Intent(Wishlist.this, home_page.class));
                    return true;
                }else if(item.getItemId() == R.id.editProfile) {
                    startActivity(new Intent(Wishlist.this, ProfileSection.class));
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

    @Override
    public void onDeleteClick(int position) {
        userWishlist.remove(position);
        wishlistAdapter.notifyItemRemoved(position);
    }

    private List<Map<String, Object>> loadData(String filename) throws IOException {
        List<Map<String, Object>> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(filename)))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, "Reading line: " + line); // Log the line read
                String[] parts = line.split(",");
                if (parts.length == 10) { // Adjusted to match the number of columns
                    try {
                        Map<String, Object> map = new HashMap<>();
                        map.put("Country", parts[0].trim());
                        map.put("City", parts[1].trim());
                        map.put("ImageURL", parts[6].trim());
                        map.put("Description", parts[7].trim());
                        data.add(map); // Add the map to the data list
                        Log.d(TAG, "Item added: " + map);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Error parsing cluster value in line: " + line + " - " + e.getMessage());
                    }
                } else {
                    Log.e(TAG, "Unexpected number of columns in line: " + line);
                    Log.e(TAG, "Read: " + parts.length);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading file: " + e.getMessage());
        }
        Log.d(TAG, "Data loaded, total items: " + data.size());
        return data;
    }
}
