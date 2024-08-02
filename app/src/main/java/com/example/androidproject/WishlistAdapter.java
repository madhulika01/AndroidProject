package com.example.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(destination destination);
    }

    private Context context;
    private List<destination> destinationList;
    private OnItemClickListener listener;

    public WishlistAdapter(Context context, List<destination> destinationList, OnItemClickListener listener) {
        this.context = context;
        this.destinationList = destinationList;
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
        destination destination = destinationList.get(position);
        holder.destinationTitle.setText(destination.getTitle());
        holder.destinationDescription.setText(destination.getDescription());
        holder.destinationLocation.setText(destination.getLocation());
        holder.destinationRating.setText(String.valueOf(destination.getRating()));
        holder.destinationImage.setImageResource(destination.getImageResId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(destination);
            }
        });
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
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
