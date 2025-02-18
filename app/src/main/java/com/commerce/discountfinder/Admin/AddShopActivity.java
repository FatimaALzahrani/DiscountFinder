package com.commerce.discountfinder.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.commerce.discountfinder.Class.Shop;
import com.commerce.discountfinder.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.UUID;

public class AddShopActivity extends AppCompatActivity {

    private EditText shopNameInput, shopLocationInput, shopDiscountInput, shopDurationInput;
    private Button saveShopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        // ربط العناصر
        shopNameInput = findViewById(R.id.shopNameInput);
        shopLocationInput = findViewById(R.id.shopLocationInput);
        shopDiscountInput = findViewById(R.id.shopDiscountInput);
        shopDurationInput = findViewById(R.id.shopDurationInput);
        saveShopButton = findViewById(R.id.saveShopButton);

        // زر حفظ المحل
        saveShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveShopToFirebase();
            }
        });
    }

    // حفظ المحل إلى Firebase
    private void saveShopToFirebase() {
        String shopName = shopNameInput.getText().toString().trim();
        String shopLocation = shopLocationInput.getText().toString().trim();
        String shopDiscount = shopDiscountInput.getText().toString().trim();
        String shopDuration = shopDurationInput.getText().toString().trim();

        // التحقق من وجود قيم صحيحة
        if (shopName.isEmpty() || shopLocation.isEmpty() || shopDiscount.isEmpty() || shopDuration.isEmpty()) {
            Toast.makeText(AddShopActivity.this, "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        // إنشاء معرف فريد للمحل
        String shopId = UUID.randomUUID().toString();

        // إنشاء كائن Shop
        Shop newShop = new Shop(shopId, shopName, shopLocation, shopDiscount, shopDuration);

        // الوصول إلى Firebase
        DatabaseReference shopsRef = FirebaseDatabase.getInstance().getReference("shops");

        // إضافة المحل إلى قاعدة البيانات
        shopsRef.child(shopId).setValue(newShop).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AddShopActivity.this, "تم إضافة المحل بنجاح", Toast.LENGTH_SHORT).show();
                finish(); // العودة إلى الصفحة السابقة
            } else {
                Toast.makeText(AddShopActivity.this, "حدث خطأ أثناء إضافة المحل", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
