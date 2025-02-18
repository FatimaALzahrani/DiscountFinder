package com.commerce.discountfinder.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.commerce.discountfinder.Class.Store;
import com.commerce.discountfinder.R;
import com.commerce.discountfinder.User.StoreDetailsActivity;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private List<Store> storeList;
    private Context context;
    private double userLatitude, userLongitude;

    public StoreAdapter(Context context, List<Store> storeList, double userLatitude, double userLongitude) {
        this.context = context;
        this.storeList = storeList;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.store_item, parent, false);
        return new StoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, int position) {
        Store store = storeList.get(position);
        holder.nameTextView.setText(store.getName());
        holder.discountPercentage.setText(String.valueOf(store.getDiscountPercentage())+"%");
        holder.time.setText(store.getTime());

        // Load image using Glide
        Glide.with(context).load(store.getImageUrl()).into(holder.storeImageView);

        // Calculate distance from user's location to store
        double distance = calculateDistance(userLatitude, userLongitude, store.getLatitude(), store.getLongitude());
        holder.distanceTextView.setText(String.format("المسافة: %.2f كم", distance));

        // Set click listener to navigate to store details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StoreDetailsActivity.class);
            intent.putExtra("store_name", store.getName());
            intent.putExtra("store_description", store.getDescription());
            intent.putExtra("store_image_url", store.getImageUrl());
            intent.putExtra("store_latitude", store.getLatitude());
            intent.putExtra("store_longitude", store.getLongitude());
            intent.putExtra("offerId",store.getOfferId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, discountPercentage, distanceTextView,time;
        ImageView storeImageView;

        public StoreViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.store_name);
            discountPercentage = itemView.findViewById(R.id.discount_percentage);
            time = itemView.findViewById(R.id.time_left);
            distanceTextView = itemView.findViewById(R.id.store_distance);
            storeImageView = itemView.findViewById(R.id.store_image);
        }
    }

    // Method to calculate the distance between two geographical points
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of Earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }

    public void updateUserLocation(double latitude, double longitude) {
        this.userLatitude = latitude;
        this.userLongitude = longitude;
        notifyDataSetChanged();
    }

    public void updateStoreList(List<Store> newStoreList) {
        this.storeList = newStoreList;
        notifyDataSetChanged();
    }

}