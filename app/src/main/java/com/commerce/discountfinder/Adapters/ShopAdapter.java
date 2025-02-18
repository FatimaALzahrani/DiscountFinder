package com.commerce.discountfinder.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.commerce.discountfinder.Class.Shop;
import com.commerce.discountfinder.R;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private List<Shop> shopList;

    public ShopAdapter(List<Shop> shopList) {
        this.shopList = shopList;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        Shop shop = shopList.get(position);
        holder.shopName.setText(shop.getName());
        holder.shopLocation.setText(shop.getLocation());
        holder.shopDiscount.setText("خصم: " + shop.getDiscount());
        holder.shopDuration.setText("مدة العرض: " + shop.getDuration());
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {

        public TextView shopName, shopLocation, shopDiscount, shopDuration;

        public ShopViewHolder(View itemView) {
            super(itemView);
            shopName = itemView.findViewById(R.id.shopName);
            shopLocation = itemView.findViewById(R.id.shopLocation);
            shopDiscount = itemView.findViewById(R.id.shopDiscount);
            shopDuration = itemView.findViewById(R.id.shopDuration);
        }
    }
}
