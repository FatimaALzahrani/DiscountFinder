package com.commerce.discountfinder.Admin;

import com.commerce.discountfinder.Adapters.ShopAdapter;
import com.commerce.discountfinder.Class.Shop;
import com.commerce.discountfinder.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class ManageShopsActivity extends AppCompatActivity {

    private RecyclerView shopsRecyclerView;
    private ShopAdapter shopAdapter;
    private ArrayList<Shop> shopList;
    private Button addShopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shops);

        // ربط العناصر
        shopsRecyclerView = findViewById(R.id.shopsRecyclerView);
        addShopButton = findViewById(R.id.addShopButton);

        // إعداد RecyclerView
        shopsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shopList = new ArrayList<>();
        shopAdapter = new ShopAdapter(shopList);
        shopsRecyclerView.setAdapter(shopAdapter);

        // تحميل المحلات من Firebase
        loadShopsFromFirebase();

        // زر إضافة محل جديد
        addShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // الانتقال إلى صفحة إضافة محل
                startActivity(new Intent(ManageShopsActivity.this, AddShopActivity.class));
            }
        });
    }

    // تحميل المحلات من Firebase
    private void loadShopsFromFirebase() {
        DatabaseReference shopsRef = FirebaseDatabase.getInstance().getReference("shops");

        // الاستماع للتغييرات في قاعدة البيانات
        shopsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shopList.clear();  // مسح القائمة القديمة

                // إضافة المحلات إلى القائمة
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shop shop = snapshot.getValue(Shop.class);
                    shopList.add(shop);
                }

                // تحديث واجهة المستخدم
                shopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ManageShopsActivity.this, "حدث خطأ: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
