package com.example.courseapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;

    // Constructor
    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText("Name: " + user.getName());
        holder.ageTextView.setText("Age: " + user.getAge());
        holder.premiumTextView.setText("Premium: " + (user.isPremium() ? "Yes" : "No"));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // Update data for the adapter
    public void updateData(List<User> updatedUserList) {
        this.userList = updatedUserList;
        notifyDataSetChanged();
    }

    // ViewHolder class
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, ageTextView, premiumTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.user_name);
            ageTextView = itemView.findViewById(R.id.user_age);
            premiumTextView = itemView.findViewById(R.id.user_premium);
        }
    }
}

