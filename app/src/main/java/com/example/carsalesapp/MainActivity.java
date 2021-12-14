package com.example.carsalesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsalesapp.adapter.RecyclerViewAdapter;
import com.example.carsalesapp.model.UserInformation;
import com.example.carsalesapp.viewmodel.CarViewModel;

import java.util.List;

import util.CarApi;

public class MainActivity extends AppCompatActivity {

    public static final String CAR_ID = "car_id";
    public static final String USER_EMAIL = "user_email";
    public  String CarFK;
    private LiveData<List<UserInformation>> userList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private CarViewModel carViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
/*
         userInformationViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
         .getApplication()).create(UserInformationViewModel.class);

        userInformationViewModel.getAllUsers().observe(this, userInformation -> {
        // Set the adapter
        recyclerViewAdapter = new RecyclerViewAdapter(userInformation,MainActivity.this);
        recyclerView.setAdapter(recyclerViewAdapter);
        });

 */

        carViewModel = new ViewModelProvider
                .AndroidViewModelFactory(MainActivity.this.getApplication())
                .create(CarViewModel.class);

        carViewModel.getCars().observe(this, carEntities -> {
            // Set the adapter
            recyclerViewAdapter = new RecyclerViewAdapter(carEntities,MainActivity.this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });

        CarApi carApi = CarApi.getInstance();
        Bundle userData = getIntent().getExtras();
        if(userData!=null){
            carApi.setUserName(userData.getString(LoginActivity.USER_EMAIL));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.sortByPriceHL:
                recyclerViewAdapter.sortByPriceHL();
                carViewModel.getCars().observe(this, carEntities -> {
                            recyclerViewAdapter = new RecyclerViewAdapter(carEntities,MainActivity.this);
                            recyclerView.setAdapter(recyclerViewAdapter);
                        });
                return true;
            case R.id.sortByPriceLH:
                recyclerViewAdapter.sortByPriceLH();
                carViewModel.getCars().observe(this, carEntities -> {
                    recyclerViewAdapter = new RecyclerViewAdapter(carEntities,MainActivity.this);
                    recyclerView.setAdapter(recyclerViewAdapter);
                });
                return true;
            case R.id.action_add:
                Intent addCar = new Intent(this, AddCarActivity.class);
                startActivity(addCar);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            case R.id.action_signout:
                Intent logout = new Intent(this, LoginActivity.class);
                startActivity(logout);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            case R.id.myPostsId:
                Intent currentUserActivity = new Intent(this, CurrentUserActivity.class);
                startActivity(currentUserActivity);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

//    @Override
//    public void ocCardClick(int position) {
//        CarEntity carEntity = Objects.requireNonNull(carViewModel.allCars.getValue().get(position));
//        Log.d("Tag","onCardClick" + carEntity.getId());
//        Intent intent = new Intent(MainActivity.this,UpdateCarInfoActivity.class);
//        intent.putExtra(CAR_ID,carEntity.getId());
//        intent.putExtra(USER_EMAIL,CarFK);
//        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }
// ======================== Code used for testing and debugging ==============================

/*            for (UserInformation userInformation1:userInformation){
                Log.d("Tag", "onCreate: " + userInformation1.getEmail());
                userArrayList.add(userInformation1.getEmail());
            }
            // Create array adapter
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,userArrayList);

            // Add to the list view
            listView.setAdapter(arrayAdapter);*/
//        listView = findViewById(R.id.listViewId);
//        userArrayList = new ArrayList<>();
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("List", "onItemClick: " + userArrayList.get(i)  );
            }
        });*/