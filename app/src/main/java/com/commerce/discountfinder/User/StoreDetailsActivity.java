package com.commerce.discountfinder.User;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.commerce.discountfinder.Class.Offer;
import com.commerce.discountfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class StoreDetailsActivity extends AppCompatActivity {

    private TextView storeNameTextView, storeDescriptionTextView, discountDetailsTextView, discountTimeLeftTextView;
    private ImageView storeImageView;
    private Button saveToFavoritesButton;

    private String currentUserId;
    private String offerId;
    private boolean isOfferSaved = false;

    private String storeName, storeDescription, storeImageUrl, discountDetails, discountTimeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);

        storeNameTextView = findViewById(R.id.store_name_detail);
        storeDescriptionTextView = findViewById(R.id.store_description_detail);
        storeImageView = findViewById(R.id.store_image_detail);
        discountDetailsTextView = findViewById(R.id.discount_details);
        discountTimeLeftTextView = findViewById(R.id.discount_time_left);
        saveToFavoritesButton = findViewById(R.id.save_to_favorites_button);

        storeName = getIntent().getStringExtra("store_name");
        storeDescription = getIntent().getStringExtra("store_description");
        storeImageUrl = getIntent().getStringExtra("store_image_url");
        discountDetails = getIntent().getStringExtra("discount_details");
        discountTimeLeft = getIntent().getStringExtra("discount_time_left");

        storeNameTextView.setText(storeName);
        storeDescriptionTextView.setText(storeDescription);
        discountDetailsTextView.setText(discountDetails);
        discountTimeLeftTextView.setText(discountTimeLeft);
        Glide.with(this).load(storeImageUrl).into(storeImageView);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        offerId = getIntent().getStringExtra("offerId");

        checkIfOfferSaved();

        Button saveToFavoritesButton = findViewById(R.id.save_to_favorites_button);
        saveToFavoritesButton.setOnClickListener(v -> {
            if (isOfferSaved) {
                deleteOfferFromFirebase();
            } else {
                saveOfferToFirebase();
            }
        });
    }

    private void checkIfOfferSaved() {
        String offerId = getIntent().getStringExtra("offerId");

        if (offerId != null) {
            DatabaseReference savedOffersRef = FirebaseDatabase.getInstance().getReference("SavedOffers")
                    .child(currentUserId)
                    .child(offerId);

            savedOffersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        isOfferSaved = true;
                        updateSaveButton();
                    } else {
                        isOfferSaved = false;
                        updateSaveButton();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle error
                }
            });
        } else {
            Log.e("StoreDetailsActivity", "Offer ID is null.");
        }
    }

    private void updateSaveButton() {
        Button saveButton = findViewById(R.id.save_to_favorites_button);
        if (isOfferSaved) {
            saveButton.setText("حذف العرض");
        } else {
            saveButton.setText("حفظ العرض");
        }
    }

    private void saveOfferToFirebase() {
        // Create an Offer object with the data from the Intent
        Offer offer = new Offer(
                storeName,
                storeDescription,
                storeImageUrl,
                getIntent().getDoubleExtra("store_latitude", 0.0),
                getIntent().getDoubleExtra("store_longitude", 0.0),
                offerId,
                discountDetails,
                discountTimeLeft,
                getIntent().getStringExtra("store_type")
        );

        // Get reference to "SavedOffers" in Firebase for the current user
        DatabaseReference savedOffersRef = FirebaseDatabase.getInstance().getReference("SavedOffers")
                .child(currentUserId)
                .child(offerId);

        // Save the offer object in Firebase
        savedOffersRef.setValue(offer)
                .addOnSuccessListener(aVoid -> {
                    isOfferSaved = true;
                    updateSaveButton();
                    Toast.makeText(StoreDetailsActivity.this, "تم حفظ العرض بنجاح", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(StoreDetailsActivity.this, "فشل في حفظ العرض", Toast.LENGTH_SHORT).show();
                });
    }

    // دالة لحذف العرض من المحفوظات
    private void deleteOfferFromFirebase() {
        // Get reference to the saved offer and remove it
        DatabaseReference savedOffersRef = FirebaseDatabase.getInstance().getReference("SavedOffers")
                .child(currentUserId)  // Under the current user's ID
                .child(offerId);       // Using offerId as the key

        savedOffersRef.removeValue()
                .addOnSuccessListener(aVoid -> {
                    isOfferSaved = false;
                    updateSaveButton();
                    Toast.makeText(StoreDetailsActivity.this, "تم حذف العرض بنجاح", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(StoreDetailsActivity.this, "فشل في حذف العرض", Toast.LENGTH_SHORT).show();
                });
    }
}