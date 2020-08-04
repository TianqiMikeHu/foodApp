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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateRestaurants extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEateryID;
    private EditText editTextNewPrice;
    private EditText editTextItemID;
    private EditText editTextMenuID;
    private Button update_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_restaurants);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        editTextEateryID = (EditText) findViewById(R.id.Eatery_ID);
        editTextNewPrice = (EditText) findViewById(R.id.Eatery_Name_new);
        editTextItemID = (EditText) findViewById(R.id.Email_new);
        editTextMenuID = (EditText) findViewById(R.id.Start_Hour_new);
        update_button = (Button) findViewById(R.id.update_restaurants_button);
        update_button.setOnClickListener(this);
    }

    private void update() {
        final String eateryID = editTextEateryID.getText().toString().trim();
        final String newPrice = editTextNewPrice.getText().toString().trim();
        final String itemID = editTextItemID.getText().toString().trim();
        final String menuID = editTextMenuID.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UPDATE_PRICING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
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
                params.put("new_value", newPrice);
                params.put("item_id", itemID);
                params.put("menu_id", menuID);
                params.put("eatery_id", eateryID);
                return params;
            }
        };
        RequestQueueHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void onClick(View btn) {
        if (btn == update_button) {
            update();
        }
    }
}
