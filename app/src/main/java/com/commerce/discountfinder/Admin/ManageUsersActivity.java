package com.commerce.discountfinder.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.commerce.discountfinder.Adapters.UsersAdapter;
import com.commerce.discountfinder.Class.User;
import com.commerce.discountfinder.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import java.util.ArrayList;

public class ManageUsersActivity extends AppCompatActivity {

    private RecyclerView usersRecyclerView;
    private UsersAdapter usersAdapter;
    private ArrayList<User> usersList;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        usersRecyclerView = findViewById(R.id.usersRecyclerView);
        usersList = new ArrayList<>();
        usersAdapter = new UsersAdapter(usersList, this);

        // إعداد RecyclerView
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersRecyclerView.setAdapter(usersAdapter);

        // الوصول إلى بيانات المستخدمين في Firebase
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        // استرجاع قائمة المستخدمين من Firebase
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    usersList.add(user);
                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ManageUsersActivity.this, "فشل تحميل المستخدمين", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // حذف مستخدم من Firebase
    public void deleteUser(String userId) {
        usersRef.child(userId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ManageUsersActivity.this, "تم حذف المستخدم بنجاح", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ManageUsersActivity.this, "حدث خطأ أثناء حذف المستخدم", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
