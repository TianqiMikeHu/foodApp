package com.example.cs411project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SearchRestaurants extends AppCompatActivity implements View.OnClickListener {

    // @TODO: add all EditTexts here and within onCreate
    private EditText editTextName;
    private Button search_restaurants_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_restaurants);

        // not sure if this is necessary
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        editTextName = (EditText) findViewById(R.id.Name);
        search_restaurants_button = (Button) findViewById(R.id.search_restaurants_button);
        search_restaurants_button.setOnClickListener(this);
    }

    private void insertRestaurant() {
        // @TODO: get all values entered by user - not just name
        final String name = editTextName.getText().toString().trim();
        //

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_INSERT_RESTAURANT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //@TODO: add all vals to params
                params.put("Name", name);
                params.put("Address", "tempAddress123");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onClick(View btn) {
        if (btn == search_restaurants_button) {
            insertRestaurant();
        }
    }
}
