package com.example.androidproject;

import android.content.Context;
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

public class RecommendedPlacesAdapter extends RecyclerView.Adapter<RecommendedPlacesAdapter.ViewHolder> {

    private List<RecommendedPlace> places;
    private Context context;

    public RecommendedPlacesAdapter(Context context, List<RecommendedPlace> places) {
        this.context = context;
        this.places = places;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommended_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecommendedPlace place = places.get(position);
        holder.cityText.setText(place.getCity());
        holder.countryText.setText(place.getCountry());
        String imageUrl = place.getImageUrl();
        Picasso.get().load(imageUrl).into(holder.imageView);  // Load the image using Picasso
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cityText;
        public TextView countryText;
        public ShapeableImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            cityText = itemView.findViewById(R.id.destination_title);
            countryText = itemView.findViewById(R.id.destination_location);
            imageView = itemView.findViewById(R.id.destination_image);
        }
    }
}
