package com.example.carsalesapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carsalesapp.adapter.RecyclerViewAdapter;
import com.example.carsalesapp.model.CarInformation;
import com.example.carsalesapp.viewmodel.CarInformationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private CarInformationViewModel carInformationViewModel;
    private RequestQueue requestQueue;

    private static final int InternetRequestCode = 1;

    Button loadMoreVehiclesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        carInformationViewModel = new ViewModelProvider
                .AndroidViewModelFactory(MainActivity.this.getApplication())
                .create(CarInformationViewModel.class);

        carInformationViewModel
                .getCars()
                .observe(this, carInformation -> {
            // Set the adapter
            recyclerViewAdapter = new RecyclerViewAdapter(carInformation,MainActivity.this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });

        loadMoreVehiclesButton = findViewById(R.id.loadMoreVehiclesButton);
        loadMoreVehiclesButton.setOnClickListener(v -> {
            loadMoreVehiclesButtonPressed();
        });
    }

    private void loadMoreVehiclesButtonPressed()
    {
        // not sure if works
        if (ContextCompat.checkSelfPermission( this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)
            QueueRequestToOutsources();
        else
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, InternetRequestCode);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case InternetRequestCode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    QueueRequestToOutsources();
                else
                    RemoveMoreVehiclesButton();

                return;
        }
    }

    private void RemoveMoreVehiclesButton()
    {
        ViewGroup parentView = (ViewGroup) loadMoreVehiclesButton.getParent();
        parentView.removeView(loadMoreVehiclesButton);
    }

    private void QueueRequestToOutsources()
    {
        RemoveMoreVehiclesButton();
        String request = "https://en.autoplius.lt/ads/used-cars?order_by=3&order_direction=DESC"; // for further requests add &page_nr=2

        StringRequest stringRequest = new StringRequest(Request.Method.GET, request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ParseVehicles(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    private void ParseVehicles(String response)
    {
        Scanner scanner = new Scanner(response);
        boolean started = false;
        List list = new ArrayList();
        while(scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            if (started)
            {
                if(line.contains("</a>"))
                {
                    AddCarFromStrings(list);
                    list.clear();
                }
                else if(!line.trim().isEmpty())
                    list.add(line.trim());
            }
            else if (line.contains("<div class=\"list-items\">"))
                started = true;
        }
    }

    private void AddCarFromStrings(List<String> list)
    {
        String description = "";
        Double price = 0.0;
        String city = "";
        String link = "";
        String imageLink = "";

        for(int i = 0; i<list.size(); i++)
        {
            String line = list.get(i);
            if (line.contains("<div class=\"line1\">"))
            {
                String descriptionLine = list.get(i+1).trim();
                if (descriptionLine.contains("</div>"))
                    descriptionLine = descriptionLine.replace("</div>", "");
                description = descriptionLine.trim();
            }
            else if (line.contains("<div class=\"pricing-container\">"))
            {
                String priceString = list.get(i+2).trim();
                price = Double.parseDouble(priceString.substring(0, priceString.indexOf("&")-1).replace(" ", ""));
            }
            else if (line.contains("<div class=\"item-parameters\">"))
            {
                String gasLine = list.get(i+2);
                String gasType = gasLine.substring(0, gasLine.length() - 7).trim();

                String volumeAndPowerLine = list.get(i+4);
                String volumeAndPower = volumeAndPowerLine.substring(0, volumeAndPowerLine.length() - 7).trim();

                String gearboxLine = list.get(i+5);
                String gearbox = gearboxLine.substring(6, gearboxLine.length() - 7);

                String kilometrageLine = list.get(i+6);
                String kilometrage = kilometrageLine.substring(6, kilometrageLine.length() - 7);

                String bodyTypeLine = list.get(i+7);
                String bodyType = bodyTypeLine.substring(6, bodyTypeLine.length() - 7);

                String cityLine = list.get(i+8).trim();
                city = cityLine.substring(6,cityLine.length()-7);
            }
            else if(line.contains("href"))
            {
                String trimmed = line.trim();
                link = trimmed.substring(trimmed.indexOf("=")+2, trimmed.length() - 1);
            }
            else if(line.contains("img class"))
            {
                String identifier = "src=\"";
                imageLink = line.substring(line.indexOf(identifier)+identifier.length()-1, line.length() -  8);
            }
        }
        if (price != 0.0 && !description.isEmpty())
        {
            CarInformation carInfo = new CarInformation(description, city, price, link, imageLink);

            recyclerViewAdapter.addItem(carInfo);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(requestQueue != null)
            requestQueue.cancelAll(InternetRequestCode);
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