package com.commerce.discountfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.commerce.discountfinder.Class.Offer;
import com.commerce.discountfinder.R;
import java.util.ArrayList;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {

    private ArrayList<Offer> offers;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Offer offer);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OfferAdapter(ArrayList<Offer> offers, Context context) {
        this.offers = offers;
        this.context = context;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_offer, parent, false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        Offer offer = offers.get(position);
        holder.storeNameTextView.setText(offer.getStoreName());
        holder.discountDetailsTextView.setText(offer.getStoreDescription());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(offer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {
        TextView storeNameTextView, discountDetailsTextView;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            storeNameTextView = itemView.findViewById(R.id.item_store_name);
            discountDetailsTextView = itemView.findViewById(R.id.item_discount_details);
        }
    }
}
