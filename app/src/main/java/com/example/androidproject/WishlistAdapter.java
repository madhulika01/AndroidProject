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

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(destination destination);
    }

    private Context context;
    private List<Map<String, Object>> destinationList;
    private List<String> userWishlist;
    private OnItemClickListener listener;

    private int position;

    public WishlistAdapter(Context context, List<Map<String, Object>> destinationList, List<String> userWishlist, OnItemClickListener listener) {
        this.context = context;
        this.destinationList = destinationList;
        this.userWishlist = userWishlist;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //destination destination = destinationList.get(position);
        String cityTag = userWishlist.get(position);
        for(int i = 0; i<userWishlist.size(); ++i) {
            if(Objects.equals(destinationList.get(i).get("City"), cityTag)) {
                Log.d("WishlistAdapter", "setting pos with i = " + i);
                position = i;
            }
        }
        Map<String, Object> item = destinationList.get(position);

        holder.destinationTitle.setText((String) item.get("City"));
        holder.destinationDescription.setText((String) item.get("Description"));
        holder.destinationLocation.setText((String) item.get("Country"));

        String imageUrl = (String) item.get("ImageURL");
        Picasso.get().load(imageUrl).into(holder.destinationImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //listener.onItemClick(destination);
                Log.e("destinationAdapter", "adapter called, click detected");
                Intent intent = new Intent(context, LocationPage.class);
                intent.putExtra("City", (String) item.get("City"));
                String cityTag = intent.getStringExtra("City");
                Log.e("destAdapter", "cityTag from destAdapter" + cityTag);
                */
/*intent.putExtra("destinationName", destination.getTitle());
                intent.putExtra("destinationLocation", destination.getLocation());
                intent.putExtra("destinationDescription", destination.getDescription());
                intent.putExtra("destinationRating", destination.getRating());*//*

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userWishlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView destinationTitle;
        public TextView destinationDescription;
        public TextView destinationLocation;
        public TextView destinationRating;
        public ImageView destinationImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            destinationTitle = itemView.findViewById(R.id.destination_title);
            destinationDescription = itemView.findViewById(R.id.destination_description);
            destinationLocation = itemView.findViewById(R.id.destination_location);
            destinationRating = itemView.findViewById(R.id.destination_rating);
            destinationImage = itemView.findViewById(R.id.destination_image);
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
import java.util.Objects;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(destination destination);
        void onDeleteClick(int position);
    }

    private Context context;
    private List<Map<String, Object>> destinationList;
    private List<String> userWishlist;
    private OnItemClickListener listener;

    public WishlistAdapter(Context context, List<Map<String, Object>> destinationList, List<String> userWishlist, OnItemClickListener listener) {
        this.context = context;
        this.destinationList = destinationList;
        this.userWishlist = userWishlist;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String cityTag = userWishlist.get(position);
        for (int i = 0; i < userWishlist.size(); ++i) {
            if (Objects.equals(destinationList.get(i).get("City"), cityTag)) {
                Log.d("WishlistAdapter", "setting pos with i = " + i);
                position = i;
            }
        }
        Map<String, Object> item = destinationList.get(position);

        holder.destinationTitle.setText((String) item.get("City"));
        holder.destinationDescription.setText((String) item.get("Description"));
        holder.destinationLocation.setText((String) item.get("Country"));
        Log.e("WishlistAdapter", (String) item.get("Description"));


        String imageUrl = (String) item.get("ImageURL");
        Picasso.get().load(imageUrl).into(holder.destinationImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("destinationAdapter", "adapter called, click detected");
                Intent intent = new Intent(context, LocationPage.class);
                intent.putExtra("City", (String) item.get("City"));
                String cityTag = intent.getStringExtra("City");
                Log.e("destAdapter", "cityTag from destAdapter" + cityTag);
                context.startActivity(intent);
            }
        });

        int finalPosition = position;
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClick(finalPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userWishlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView destinationTitle;
        public TextView destinationDescription;
        public TextView destinationLocation;
        public TextView destinationRating;
        //public ImageView destinationImage;
        public ShapeableImageView destinationImage;
        public ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            destinationTitle = itemView.findViewById(R.id.destination_title);
            destinationDescription = itemView.findViewById(R.id.destination_description);
            destinationLocation = itemView.findViewById(R.id.destination_location);
            //destinationRating = itemView.findViewById(R.id.destination_rating);
            destinationImage = itemView.findViewById(R.id.destination_image);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
