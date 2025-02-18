package com.commerce.discountfinder.Admin;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.commerce.discountfinder.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class ViewStatsActivity extends AppCompatActivity {

    private TextView usersCountTextView;
    private TextView shopsCountTextView;
    private DatabaseReference usersRef;
    private DatabaseReference shopsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);

        usersCountTextView = findViewById(R.id.usersCount);
        shopsCountTextView = findViewById(R.id.shopsCount);

        // الوصول إلى المراجع الخاصة بالمستخدمين والمحلات
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        shopsRef = FirebaseDatabase.getInstance().getReference("shops");

        // استرجاع عدد المستخدمين
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long usersCount = dataSnapshot.getChildrenCount();
                usersCountTextView.setText("عدد المستخدمين: " + usersCount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewStatsActivity.this, "فشل تحميل عدد المستخدمين", Toast.LENGTH_SHORT).show();
            }
        });

        // استرجاع عدد المحلات
        shopsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long shopsCount = dataSnapshot.getChildrenCount();
                shopsCountTextView.setText("عدد المحلات: " + shopsCount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewStatsActivity.this, "فشل تحميل عدد المحلات", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
