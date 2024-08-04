/*
package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class destinationAdapter extends RecyclerView.Adapter<destinationAdapter.ViewHolder> {

    private Context context;
    private List<destination> destinationList;

    public destinationAdapter(Context context, List<destination> destinationList) {
        this.context = context;
        this.destinationList = destinationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.location_tile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        destination destination = destinationList.get(position);
        holder.destinationTitle.setText(destination.getTitle());
        holder.destinationLocation.setText(destination.getLocation());
        holder.destinationImage.setImageResource(destination.getImageResId());

        holder.itemView.setOnClickListener(v -> {

            Log.e("destinationAdapter", "adapter called, click detected");
            Intent intent = new Intent(context, LocationPage.class);
            intent.putExtra("destinationName", destination.getTitle());
            intent.putExtra("destinationLocation", destination.getLocation());
            intent.putExtra("destinationDescription", destination.getDescription());
            intent.putExtra("destinationRating", destination.getRating());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView destinationImage;
        public TextView destinationTitle;
        public TextView destinationLocation;

        public ViewHolder(View itemView) {
            super(itemView);
            destinationImage = itemView.findViewById(R.id.destinationImage);
            destinationTitle = itemView.findViewById(R.id.destinationTitle);
            destinationLocation = itemView.findViewById(R.id.destinationLocation);
        }
    }
}
*/

package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class destinationAdapter extends RecyclerView.Adapter<destinationAdapter.ViewHolder> {

    private Context context;
    private List<Map<String, Object>> destinationList;

    public destinationAdapter(Context context, List<Map<String, Object>> destinationList) {
        this.context = context;
        this.destinationList = destinationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.location_tile, parent, false);
        return new ViewHolder(view);
    }

    public void updateList(List<Map<String, Object>> newList) {
        destinationList = newList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, Object> item = destinationList.get(position);
        holder.destinationTitle.setText((String) item.get("City"));
        holder.destinationLocation.setText((String) item.get("Country"));
        String imageUrl = (String) item.get("ImageURL");
        Picasso.get().load(imageUrl).into(holder.destinationImage);


        //----------------------------------------------------
        /*destination destination = destinationList.get(position);
        holder.destinationTitle.setText(destination.getTitle());
        holder.destinationLocation.setText(destination.getLocation());
        holder.destinationImage.setImageResource(destination.getImageResId());
*/
        holder.itemView.setOnClickListener(v -> {
            Log.e("destinationAdapter", "adapter called, click detected");
            Intent intent = new Intent(context, LocationPage.class);
            intent.putExtra("City", (String) item.get("City"));
            String cityTag = intent.getStringExtra("City");
            Log.e("destAdapter", "cityTag from destAdapter" + cityTag);
            /*intent.putExtra("destinationName", destination.getTitle());
            intent.putExtra("destinationLocation", destination.getLocation());
            intent.putExtra("destinationDescription", destination.getDescription());
            intent.putExtra("destinationRating", destination.getRating());*/
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public ImageView destinationImage;
        public ShapeableImageView destinationImage;
        public TextView destinationTitle;
        public TextView destinationLocation;

        public ViewHolder(View itemView) {
            super(itemView);
            destinationImage = itemView.findViewById(R.id.destinationImage);
            destinationTitle = itemView.findViewById(R.id.destinationTitle);
            destinationLocation = itemView.findViewById(R.id.destinationLocation);

        }
    }
}
