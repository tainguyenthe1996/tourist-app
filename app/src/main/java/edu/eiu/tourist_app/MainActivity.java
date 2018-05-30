package edu.eiu.tourist_app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recycleAdapter;
    private PlacesViewModel placesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup RecyclerView Setup
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Handle Places ViewModel
        placesViewModel = ViewModelProviders.of(this).get(PlacesViewModel.class);
        LiveData<List<WikipediaPage>> placesData = placesViewModel.getTouristSites();
        placesData.observe(this, new Observer<List<WikipediaPage>>() {
            @Override
            public void onChanged(@Nullable List<WikipediaPage> touristSites) {
                recycleAdapter = new TouristRecyclerAdapter(touristSites);
                recyclerView.setAdapter(recycleAdapter);
            }
        });


    }
}
