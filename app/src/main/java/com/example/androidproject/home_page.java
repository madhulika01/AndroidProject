/*
package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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

    private Button personalRec;
    String tag = "home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.recycler_view);
        navBar = findViewById(R.id.navBar);
        personalRec = findViewById(R.id.tabRecommended);

        personalRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, DestinationSelectionActivity.class);
                startActivity(intent);
            }
        });

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
                */
/*switch(item.getItemId()) {
                    case (R.id.nav_home):
                        startActivity(new Intent(Wishlist.this, home_page.class));
                        return true;
                    case (R.id.nav_favorites):
                        return true;
                }*//*

                if(item.getItemId() == R.id.nav_favorites) {
                    startActivity(new Intent(home_page.this, Wishlist.class));
                    return true;
                }
                return false;
            }
        });
    }
}
*/
package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.search.SearchBar;
import androidx.appcompat.widget.SearchView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class home_page extends AppCompatActivity {

    private RecyclerView recyclerView;
    private destinationAdapter destinationAdapter;
    private List<Map<String, Object>> destinationList;
    private BottomNavigationView navBar;
    private SearchBar searchBar;
    private SearchView searchView;

    private Button featuredButton;

    private Button personalRec;
    String TAG = "home";
    String tag = "home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.recycler_view);
        navBar = findViewById(R.id.navBar);
        personalRec = findViewById(R.id.tabRecommended);
        featuredButton = findViewById(R.id.tabFeatured);
        searchView = findViewById(R.id.search_bar);

        personalRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, DestinationSelectionActivity.class);
                startActivity(intent);
            }
        });

        featuredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, FeaturedActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setVerticalScrollBarEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        destinationList = new ArrayList<Map<String, Object>>();

        try {
            destinationList = loadData("database.csv");
        } catch (IOException e) {
            Log.e(tag, "Error loading data: " + e.getMessage());
        }

        destinationAdapter = new destinationAdapter(this, destinationList);
        recyclerView.setAdapter(destinationAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterDestinations(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterDestinations(newText);
                return false;
            }
        });
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_favorites) {
                    startActivity(new Intent(home_page.this, Wishlist.class));
                    return true;
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    startActivity(new Intent(home_page.this, ProfileSection.class));
                    return true;
                }
                return false;
            }
        });
    }

    private void filterDestinations(String query) {
        List<Map<String, Object>> filteredList = new ArrayList<>();
        for (Map<String, Object> destination : destinationList) {
            String city = (String) destination.get("City");
            String country = (String) destination.get("Country");
            if (city.toLowerCase().contains(query.toLowerCase()) ||
                    country.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(destination);
            }
        }
        destinationAdapter.updateList(filteredList);
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
                        map.put("City", parts[1].trim());/*
                        map.put("One", parts[2].trim());
                        map.put("Two", parts[3].trim());
                        map.put("Three", parts[4].trim());
                        map.put("Four", parts[5].trim());*/
                        map.put("ImageURL", parts[6].trim());
                        /*map.put("Description", parts[7].trim());
                        map.put("Lat", parts[8].trim());
                        map.put("Long", parts[9].trim());*/
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

