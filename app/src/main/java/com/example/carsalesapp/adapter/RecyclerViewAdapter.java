package com.example.carsalesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsalesapp.R;
import com.example.carsalesapp.model.UserInformation;

import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder>{
    private List<UserInformation> userList;
    private Context context;

    public RecyclerViewAdapter(List<UserInformation> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserInformation userInformation = Objects.requireNonNull(userList.get(position));
        holder.email.setText(userInformation.getEmail());
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(userList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.recyclerEmailId);
        }
    }
}
