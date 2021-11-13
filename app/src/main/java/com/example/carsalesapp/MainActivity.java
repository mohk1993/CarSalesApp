package com.example.carsalesapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsalesapp.adapter.RecyclerViewAdapter;
import com.example.carsalesapp.model.CarInformation;
import com.example.carsalesapp.model.UserInformation;
import com.example.carsalesapp.viewmodel.CarInformationViewModel;
import com.example.carsalesapp.viewmodel.UserInformationViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

      private LiveData<List<UserInformation>> userList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private UserInformationViewModel userInformationViewModel;
    private CarInformationViewModel carInformationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
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

        carInformationViewModel = new ViewModelProvider
                .AndroidViewModelFactory(MainActivity.this.getApplication())
                .create(CarInformationViewModel.class);

        carInformationViewModel.getCars().observe(this, carInformation -> {
            // Set the adapter
            recyclerViewAdapter = new RecyclerViewAdapter(carInformation,MainActivity.this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });
    }
}

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