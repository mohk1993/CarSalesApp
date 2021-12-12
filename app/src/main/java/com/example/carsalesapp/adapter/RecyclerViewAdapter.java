package com.example.carsalesapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsalesapp.R;
import com.example.carsalesapp.converters.Converters;
import com.example.carsalesapp.model.CarEntity;
import com.example.carsalesapp.model.CarInformation;
import com.example.carsalesapp.model.UserInformation;

import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder>{
    private List<CarEntity> carList;
    private Context context;

    public RecyclerViewAdapter(List<CarEntity> carList, Context context) {
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
        CarEntity carEntity = Objects.requireNonNull(carList.get(position));
        holder.Model.setText(carEntity.getModel());
        holder.Manufacturer.setText(carEntity.getManufacturer());
        holder.Price.setText(carEntity.getPrice().toString());
        holder.Owner.setText(carEntity.getFK());
        holder.carImage.setImageBitmap(Converters.ByteToBitMap(carEntity.getImage()));
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(carList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Model;
        public TextView Manufacturer;
        public TextView Price;
        public TextView Owner;
        public ImageView carImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Model = itemView.findViewById(R.id.model);
            Manufacturer = itemView.findViewById(R.id.manufacturer);
            Price = itemView.findViewById(R.id.price);
            Owner = itemView.findViewById(R.id.userName);
            carImage = itemView.findViewById(R.id.recyclerCarImage);
        }


    }

}
