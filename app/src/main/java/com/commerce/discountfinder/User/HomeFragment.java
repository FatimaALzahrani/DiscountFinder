package com.commerce.discountfinder.User;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.commerce.discountfinder.Adapters.StoreAdapter;
import com.commerce.discountfinder.Class.Store;
import com.commerce.discountfinder.LoginActivity;
import com.commerce.discountfinder.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class HomeFragment extends Fragment {

    private View view;
    private RecyclerView storeRecyclerView;
    private StoreAdapter storeAdapter;
    private List<Store> storeList = new ArrayList<>();
    private double userLatitude, userLongitude;

    private Spinner filterSpinner;
    private TextInputEditText searchEditText;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference storesRef;

    FusedLocationProviderClient fusedLocationClient;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        storesRef = firebaseDatabase.getReference("stores");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());

        getLocation();

        storeRecyclerView = view.findViewById(R.id.store_recycler_view);
        storeRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        storeAdapter = new StoreAdapter(view.getContext(), storeList, userLatitude, userLongitude);
        storeRecyclerView.setAdapter(storeAdapter);

        filterSpinner = view.findViewById(R.id.filter_spinner);
        searchEditText = view.findViewById(R.id.search_edit_text);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.store_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                filterStores(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        // البحث
        searchEditText.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                searchStores(charSequence.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {
            }
        });

        loadStoreData();
        return view;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                userLatitude = location.getLatitude();
                                userLongitude = location.getLongitude();
                                storeAdapter.updateUserLocation(userLatitude, userLongitude);
                            } else {
                                Log.e("UserActivity", "Location is null");
                            }
                        }
                    });
        }
    }

    private void loadStoreData() {
        storesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storeList.clear();

                for (DataSnapshot storeSnapshot : dataSnapshot.getChildren()) {
                    Store store = storeSnapshot.getValue(Store.class);
                    if (store != null) {
                        storeList.add(store);
                    }
                }

                storeAdapter.notifyDataSetChanged();

                Log.d("UserActivity", "Data loaded: " + storeList.size() + " stores found");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(view.getContext(), "خطأ في تحميل البيانات: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterStores(int position) {
        List<Store> filteredList = new ArrayList<>();
        for (Store store : storeList) {
            if (position == 0 || store.getType().equalsIgnoreCase(getResources().getStringArray(R.array.store_types)[position])) {
                filteredList.add(store);
            }
        }
        storeAdapter.updateStoreList(filteredList);
    }

    private void searchStores(String query) {
        List<Store> filteredList = new ArrayList<>();
        for (Store store : storeList) {
            if (store.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(store);
            }
        }
        storeAdapter.updateStoreList(filteredList);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}