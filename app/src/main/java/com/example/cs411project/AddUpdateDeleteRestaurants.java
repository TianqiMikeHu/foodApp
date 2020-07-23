package com.example.cs411project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddUpdateDeleteRestaurants extends AppCompatActivity {
    private Button add_link;
    private Button delete_link;
    private Button update_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_update_delete_restaurants);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        add_link = (Button) findViewById(R.id.add_link);
        add_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_link();
            }
        });

        delete_link = (Button) findViewById(R.id.delete_link);
        delete_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_link();
            }
        });

        update_link = (Button) findViewById(R.id.update_link);
        update_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_link();
            }
        });
    }

    public void add_link(){
        Intent intent = new Intent(this, AddRestaurants.class);
        startActivity(intent);
    }

    public void delete_link(){
        Intent intent = new Intent(this, DeleteRestaurants.class);
        startActivity(intent);
    }
    
    public void update_link(){
        Intent intent = new Intent(this, UpdateRestaurants.class);
        startActivity(intent);
    }
}
