package com.commerce.discountfinder.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.commerce.discountfinder.R;

public class AdminActivity extends AppCompatActivity {

    private Button manageShopsButton, manageUsersButton, viewStatsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // ربط الأزرار
        manageShopsButton = findViewById(R.id.manageShopsButton);
        manageUsersButton = findViewById(R.id.manageUsersButton);
        viewStatsButton = findViewById(R.id.viewStatsButton);

        // زر إدارة المحلات
        manageShopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // الذهاب إلى صفحة إدارة المحلات
                startActivity(new Intent(AdminActivity.this, ManageShopsActivity.class));
            }
        });

        // زر إدارة المستخدمين
        manageUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // الذهاب إلى صفحة إدارة المستخدمين
                startActivity(new Intent(AdminActivity.this, ManageUsersActivity.class));
            }
        });

        // زر عرض الإحصائيات (اختياري)
        viewStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // الذهاب إلى صفحة عرض الإحصائيات
                startActivity(new Intent(AdminActivity.this, ViewStatsActivity.class));
            }
        });
    }
}
