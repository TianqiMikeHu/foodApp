package com.example.cs411project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button search_restaurants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        search_restaurants = (Button) findViewById(R.id.search_restaurants);
        search_restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_restaurants();
            }
        });
    }

    public void search_restaurants(){
        Intent intent = new Intent(this, SearchRestaurants.class);
        startActivity(intent);
    }
}