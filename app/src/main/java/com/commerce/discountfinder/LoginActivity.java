package com.commerce.discountfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.commerce.discountfinder.Admin.AdminActivity;
import com.commerce.discountfinder.Investor.InvestorActivity;
import com.commerce.discountfinder.User.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // ربط العناصر
        emailEditText = findViewById(R.id.emailField);
        passwordEditText = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);

        // تنفيذ عملية تسجيل الدخول عند الضغط على زر "تسجيل الدخول"
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "الرجاء ملء جميع الحقول", Toast.LENGTH_SHORT).show();
                } else {
                    // تسجيل الدخول باستخدام البريد الإلكتروني وكلمة المرور
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // التحقق من فئة المستخدم (مستثمر، رئيس، أو مستخدم عادي)
                                    checkUserRole(user);
                                } else {
                                    Toast.makeText(LoginActivity.this, "فشل في تسجيل الدخول: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    // التحقق من فئة المستخدم وتوجيهه إلى الصفحة المناسبة
    private void checkUserRole(FirebaseUser user) {
        if (user != null) {
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
            usersRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        String role = snapshot.child("role").getValue(String.class);

                        if (role != null) {
                            switch (role) {
                                case "مستثمر":
                                    startActivity(new Intent(LoginActivity.this, InvestorActivity.class));
                                    break;
                                case "رئيس":
                                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                                    break;
                                default:
                                    startActivity(new Intent(LoginActivity.this, UserActivity.class));
                                    break;
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "لم يتم تحديد دور المستخدم", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "لم يتم العثور على البيانات", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "فشل في تحميل البيانات: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void Signup(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void toforget(View view) {
        Intent intent = new Intent(this,ForgetActivity.class);
        startActivity(intent);
    }
}
