package com.example.carsalesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.carsalesapp.adapter.CurrentUserRecyclerView;
import com.example.carsalesapp.adapter.RecyclerViewAdapter;
import com.example.carsalesapp.model.CarEntity;
import com.example.carsalesapp.model.UserInformation;
import com.example.carsalesapp.viewmodel.CarViewModel;

import java.util.List;
import java.util.Objects;

import util.CarApi;

public class CurrentUserActivity extends AppCompatActivity  implements CurrentUserRecyclerView.OnCardClickListener{

    public static final String CAR_ID = "car_id";
    private LiveData<List<UserInformation>> userList;
    private RecyclerView currentUserRecyclerViewId;
    private CurrentUserRecyclerView currentUserRecyclerView;
    private CarViewModel carViewModel;
    private String currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_user);

        currentUserRecyclerViewId = findViewById(R.id.currentUserRecyclerViewId);
        currentUserRecyclerViewId.setHasFixedSize(true);
        currentUserRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));

        carViewModel = new ViewModelProvider
                .AndroidViewModelFactory(CurrentUserActivity.this.getApplication())
                .create(CarViewModel.class);
        currentUser = CarApi.getInstance().getUserName();
        carViewModel.getAllCurrentUsrCars(currentUser).observe(this, carEntities -> {
            // Set the adapter
            currentUserRecyclerView = new CurrentUserRecyclerView(carEntities,CurrentUserActivity.this,this);
            currentUserRecyclerViewId.setAdapter(currentUserRecyclerView);
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_add:
                Intent addCar = new Intent(this, AddCarActivity.class);
                startActivity(addCar);
                return true;
            case R.id.action_signout:
                Intent logout = new Intent(this, LoginActivity.class);
                startActivity(logout);
                return true;
            case R.id.myPostsId:
                Intent currentUserActivity = new Intent(this, CurrentUserActivity.class);
                startActivity(currentUserActivity);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void ocCardClick(int position) {
        CarEntity carEntity = Objects.requireNonNull(carViewModel.allCurrentUserCars.getValue().get(position));
        Log.d("Tag","onCardClick" + carEntity.getId());
        Intent intent = new Intent(CurrentUserActivity.this,UpdateCarInfoActivity.class);
        intent.putExtra(CAR_ID,carEntity.getId());
        startActivity(intent);
    }
}