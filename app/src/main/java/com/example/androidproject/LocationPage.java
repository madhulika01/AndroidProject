
package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class LocationPage extends AppCompatActivity {
    private Button openMaps;
    private ImageView placeImage;
    private TextView placeNameCity;
    private TextView placeNameCountry;
    private TextView placeDescription;
    private ImageView one;
    private ImageView two;
    private ImageView three;
    private ImageView four;
    private ImageButton backButton;
    private double lattitude, longitude;
    private static final String TAG = "DataExtractionActivity";

    private String cityTag;
    private int position;

    private List<Map<String, Object>> dataList;

    //Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_location_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();

        openMaps = findViewById(R.id.open_maps);
        backButton = findViewById(R.id.back_button);
        dataList = new ArrayList<>();
        placeImage = findViewById(R.id.place_image);
        placeNameCity = findViewById(R.id.place_name);
        placeNameCountry = findViewById(R.id.place_location);
        placeDescription = findViewById(R.id.place_description);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);

        cityTag = intent.getStringExtra("City");


        try {
            dataList = loadData("database.csv");
            if (!dataList.isEmpty()) {
                setLocationData(dataList, cityTag);
            } else {
                Log.e(TAG, "Data list is empty");
                Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error loading data: " + e.getMessage());
            Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
        }

        openMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationPage.this, MapLocation.class);
                intent.putExtra("lattitude", lattitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setLocationData(List<Map<String, Object>> dataList, String cityTag) {
        Log.e("LocationPage", "SetLocationCalled");
        if (dataList.isEmpty()) {
            Log.e("LocationPage", "Data list is empty");
            return;
        }
        Log.e("LocationPage", "cityTag passed" + cityTag);
        for(int i = 0; i<dataList.size(); ++i) {
            if(i==1)
                Log.d("LocationPage", "loop = " + i);
            if(Objects.equals(dataList.get(i).get("City"), cityTag)) {
                Log.d("LocationPage", "setting pos with i = " + i);
                position = i;
            }
        }
        Log.d("LocationPage", "position = " + position);
        Log.d("LocationPage", "country = " + dataList.get(position).get("Country"));

        Map<String, Object> item = dataList.get(position);
        Log.e("LocationPage", "itemList set");
        String imageUrl = (String) item.get("ImageURL");
        Picasso.get().load(imageUrl).into(placeImage);

        placeNameCity.setText((String) item.get("City"));
        placeNameCountry.setText((String) item.get("Country"));
        placeDescription.setText((String) item.get("Description"));

        String oneUrl = (String) item.get("One");
        Picasso.get().load(oneUrl).into(one);

        String twoUrl = (String) item.get("Two");
        Picasso.get().load(twoUrl).into(two);

        String threeUrl = (String) item.get("Three");
        Picasso.get().load(threeUrl).into(three);

        String fourUrl = (String) item.get("Four");
        Picasso.get().load(fourUrl).into(four);

       String tempLat = (String) item.get("Lat");
        String tempLong= (String) item.get("Long");
        lattitude = Double.parseDouble(tempLat);//(double) item.get("Lat");
        longitude = Double.parseDouble(tempLong);//(double) item.get("Long");

        /*Log.e("LocationPage", tempLat);
        Log.e("LocationPage", tempLong);*/
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
                        map.put("One", parts[2].trim());
                        map.put("Two", parts[3].trim());
                        map.put("Three", parts[4].trim());
                        map.put("Four", parts[5].trim());
                        map.put("ImageURL", parts[6].trim());
                        map.put("Description", parts[7].trim());
                        map.put("Lat", parts[8].trim());
                        map.put("Long", parts[9].trim());
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

//main^


/*
package com.example.androidproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataExtractionActivity extends AppCompatActivity {

    private static final String TAG = "DataExtractionActivity";

    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private List<Map<String, Object>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_extraction);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        dataAdapter = new DataAdapter(this, dataList);
        recyclerView.setAdapter(dataAdapter);

        try {
            dataList = loadData("processed_data.csv");
            dataAdapter.setDataList(dataList);
            dataAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            Log.e(TAG, "Error loading data: " + e.getMessage());
            Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Map<String, Object>> loadData(String filename) throws IOException {
        List<Map<String, Object>> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(filename)))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, "Reading line: " + line); // Log each line read
                String[] parts = line.split(",");
                if (parts.length == 9) { // Adjusted to match the number of columns
                    try {
                        Map<String, Object> map = new HashMap<>();
                        map.put("Country", parts[0].trim());
                        map.put("City", parts[1].trim());
                        map.put("Type of Destination", parts[2].trim());
                        map.put("Activities", parts[3].trim());
                        map.put("TravelWith", parts[4].trim());
                        map.put("Climate", parts[5].trim());
                        map.put("Features", parts[6].trim()); // Assuming combined features are in the 7th column
                        float clusterValue = Float.parseFloat(parts[7].trim());
                        map.put("Cluster", clusterValue);  // Ensure clusters are floats
                        map.put("ImageURL", parts[8].trim());  // Add Image URL
                        Log.d(TAG, "Parsed cluster value: " + clusterValue);
                        data.add(map);
                        Log.d(TAG, "Item added: " + map);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Error parsing cluster value in line: " + line + " - " + e.getMessage());
                    }
                } else {
                    Log.e(TAG, "Unexpected number of columns in line: " + line);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading file: " + e.getMessage());
        }
        Log.d(TAG, "Data loaded, total items: " + data.size());
        return data;
    }
}
*/
/*

package com.example.androidproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationPage extends AppCompatActivity {

    private static final String TAG = "DataExtractionActivity";

    private List<Map<String, Object>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_page);


        dataList = new ArrayList<>();
        try {
            dataList = loadData("processed_data.csv");
            //dataAdapter.setDataList(dataList);
            //dataAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            Log.e(TAG, "Error loading data: " + e.getMessage());
            Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Map<String, Object>> loadData(String filename) throws IOException {
        List<Map<String, Object>> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(filename)))) {
            String line;
            reader.readLine(); // Skip header
            if ((line = reader.readLine()) != null) {
                Log.d(TAG, "Reading line: " + line); // Log the line read
                String[] parts = line.split(",");
                if (parts.length == 8) { // Adjusted to match the number of columns
                    try {
                        Map<String, Object> map = new HashMap<>();
                        map.put("Country", parts[0].trim());
                        map.put("City", parts[1].trim());
                        map.put("One", parts[2].trim());
                        map.put("Two", parts[3].trim());
                        map.put("Three", parts[4].trim());
                        map.put("Four", parts[5].trim());
                        map.put("ImageURL", parts[6].trim());
                        map.put("Description", parts[7].trim());

                        Log.d(TAG, "Item added: " + map);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Error parsing cluster value in line: " + line + " - " + e.getMessage());
                    }
                } else {
                    Log.e(TAG, "Unexpected number of columns in line: " + line);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading file: " + e.getMessage());
        }
        Log.d(TAG, "Data loaded, total items: " + data.size());
        return data;
    }
}
*/

