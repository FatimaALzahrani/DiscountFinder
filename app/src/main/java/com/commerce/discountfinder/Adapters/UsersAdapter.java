package com.commerce.discountfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.commerce.discountfinder.Admin.ManageUsersActivity;
import com.commerce.discountfinder.Class.User;
import com.commerce.discountfinder.R;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private ArrayList<User> usersList;
    private Context context;
    private ManageUsersActivity activity;

    public UsersAdapter(ArrayList<User> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
        this.activity = (ManageUsersActivity) context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = usersList.get(position);
        holder.nameTextView.setText(user.getName());
        holder.emailTextView.setText(user.getEmail());

        // زر الحذف
        holder.deleteButton.setOnClickListener(v -> activity.deleteUser(user.getUid()));
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, emailTextView;
        Button deleteButton;

        public UserViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.userName);
            emailTextView = itemView.findViewById(R.id.userEmail);
            deleteButton = itemView.findViewById(R.id.deleteUserButton);
        }
    }
}
