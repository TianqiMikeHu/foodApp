package com.example.cs411project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;

public class MainActivity extends AppCompatActivity {
    private Button search_restaurants;
    private Button search_menu_item;
    private Button user_registration;
    private Button review;
    private Button search_reviews;
    private Button add_update_delete_restaurants;
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
        search_menu_item = (Button) findViewById(R.id.search_menu_item);
        search_menu_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_menu_item();
            }
        });
        user_registration = (Button) findViewById(R.id.user_registration);
        user_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_registration();
            }
        });
        review = (Button) findViewById(R.id.write_a_review);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review();
            }
        });
        search_reviews = (Button) findViewById(R.id.search_reviews);
        search_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_reviews();
            }
        });
        add_update_delete_restaurants = (Button) findViewById(R.id.add_update_delete_restaurants);
        add_update_delete_restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_update_delete_restaurants();
            }
        });
    }

    public void search_restaurants(){
        Intent intent = new Intent(this, SearchRestaurants.class);
        startActivity(intent);
    }

    public void search_menu_item(){
        Intent intent = new Intent(this, SearchMenuItem.class);
        startActivity(intent);
    }

    public void user_registration(){
        Intent intent = new Intent(this, UserRegistration.class);
        startActivity(intent);
    }

    public void review(){
        Intent intent = new Intent(this, Review.class);
        startActivity(intent);
    }

    public void search_reviews(){
        Intent intent = new Intent(this, SearchReviews.class);
        startActivity(intent);
    }

    public void add_update_delete_restaurants(){
        Intent intent = new Intent(this, AddUpdateDeleteRestaurants.class);
        startActivity(intent);
    }
}