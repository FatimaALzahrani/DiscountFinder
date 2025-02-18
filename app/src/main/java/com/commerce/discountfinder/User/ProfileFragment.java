package com.commerce.discountfinder.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.commerce.discountfinder.LoginActivity;
import com.commerce.discountfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private EditText emailField, nameField, phoneField;
    private Button saveButton;
    TextView logoutButton;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        // Initialize UI elements
        emailField = view.findViewById(R.id.profileEmail);
        nameField = view.findViewById(R.id.profileName);
        phoneField = view.findViewById(R.id.profilePhone);
        saveButton = view.findViewById(R.id.saveProfileButton);
        logoutButton = view.findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(v -> logoutUser());

        // Initialize Firebase
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());

            // Load user data
            loadUserData();
        }

        // Save button click
        saveButton.setOnClickListener(v -> saveUserData());

        return view;
    }

    private void loadUserData() {
        databaseReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String phone = snapshot.child("phone").getValue(String.class);

                    // Set values to fields
                    nameField.setText(name);
                    emailField.setText(email);
                    phoneField.setText(phone);
                }
            } else {
                Toast.makeText(getActivity(), "Failed to load data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserData() {
        String name = nameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String phone = phoneField.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
            Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update user data in Firebase
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", name);
        updates.put("email", email);
        updates.put("phone", phone);

        databaseReference.updateChildren(updates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Failed to update profile: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logoutUser() {
        mAuth.signOut();
        Toast.makeText(getContext(), "تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
