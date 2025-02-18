package com.commerce.discountfinder.User;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commerce.discountfinder.Adapters.OfferAdapter;
import com.commerce.discountfinder.Class.Offer;
import com.commerce.discountfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class SavedFragment extends Fragment {

    private RecyclerView recyclerView;
    private OfferAdapter offerAdapter;
    private ArrayList<Offer> savedOffersList;
    private DatabaseReference savedOffersRef;

    public SavedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        recyclerView = view.findViewById(R.id.recycler_saved_offers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        savedOffersList = new ArrayList<>();
        offerAdapter = new OfferAdapter(savedOffersList, getContext());
        recyclerView.setAdapter(offerAdapter);

        // تعيين مستمع للنقر على عنصر العرض
        offerAdapter.setOnItemClickListener(new OfferAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Offer offer) {
                // الانتقال إلى صفحة التفاصيل مع تمرير بيانات العرض
                Intent intent = new Intent(getContext(), StoreDetailsActivity.class);
                intent.putExtra("store_name", offer.getStoreName());
                intent.putExtra("store_description", offer.getStoreDescription());
                intent.putExtra("store_image_url", offer.getStoreImageUrl());
                intent.putExtra("discount_details", offer.getRating());
                intent.putExtra("discount_time_left", offer.getTime());
                intent.putExtra("offerId",offer.getOfferId());
                startActivity(intent);
            }
        });

        loadSavedOffers();

        return view;
    }

    private void loadSavedOffers() {
        // الحصول على معرف المستخدم الحالي
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        savedOffersRef = FirebaseDatabase.getInstance().getReference("SavedOffers").child(userId);

        savedOffersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                savedOffersList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Offer offer = ds.getValue(Offer.class);
                    if (offer != null) {
                        savedOffersList.add(offer);
                    }
                }
                offerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // معالجة الأخطاء إن وجدت
            }
        });
    }
}
