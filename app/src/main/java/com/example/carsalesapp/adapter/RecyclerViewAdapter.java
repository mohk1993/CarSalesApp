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
import com.example.carsalesapp.model.CarInformation;
import com.example.carsalesapp.model.UserInformation;

import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder>{
    private List<CarInformation> carList;
    private Context context;

    public RecyclerViewAdapter(List<CarInformation> carList, Context context) {
        this.carList = carList;
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
        CarInformation carInformation = Objects.requireNonNull(carList.get(position));
        holder.Model.setText(carInformation.getModel());
        holder.Manufacturer.setText(carInformation.getManufacturer());
        holder.Price.setText(carInformation.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(carList.size());
    }

    public void addItem(CarInformation item)
    {
        carList.add(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Model;
        public TextView Manufacturer;
        public TextView Price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Model = itemView.findViewById(R.id.model);
            Manufacturer = itemView.findViewById(R.id.manufacturer);
            Price = itemView.findViewById(R.id.price);
        }
    }
}
