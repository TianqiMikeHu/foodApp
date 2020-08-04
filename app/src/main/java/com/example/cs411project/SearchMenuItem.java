package com.example.cs411project;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchMenuItem extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextItemName;
    private EditText editTextItemType;
    private EditText editTextItemPrice;
    private EditText editTextStartHour;
    private EditText editTextEndHour;
    private TextView textViewRESULT;
    private Button search_menu_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_menu_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        editTextItemName = (EditText) findViewById(R.id.Item_Name);
        editTextItemType = (EditText) findViewById(R.id.Item_Type);
        editTextItemPrice = (EditText) findViewById(R.id.Item_Price);
        editTextStartHour = (EditText) findViewById(R.id.Start_Hour);
        editTextEndHour = (EditText) findViewById(R.id.End_Hour);
        textViewRESULT = (TextView) findViewById(R.id.Result);
        search_menu_button = (Button) findViewById(R.id.search_menu_item_button);
        search_menu_button.setOnClickListener(this);
    }

    private void searchMenu() {
        final String itemName = editTextItemName.getText().toString().trim();
        final String itemType = editTextItemType.getText().toString().trim();
        final String itemPrice = editTextItemPrice.getText().toString().trim();
        final String startHour = editTextStartHour.getText().toString().trim();
        final String endHour = editTextEndHour.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_GET_MENU_ITEM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("[","");
                response = response.replace("]","");
                response = response.replace("{","");
                response = response.replace("}","");
                response = response.replace("\\","");
                response = response.replace("\"","");
                response = response.replace("products","Restaurants");
                response = response.replace("success: 1","");

                textViewRESULT.setText(response);
                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textViewRESULT.setText("error");
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                String userid = Integer.toString(globalVars.getUser());
                params.put("User_ID", userid);
                params.put("Name", itemName);
                params.put("Type", itemType);
                params.put("MaxPrice", itemPrice);
                params.put("AvailableAfter", startHour);
                params.put("AvailableUntil", endHour);

                return params;
            }
        };

        RequestQueueHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void onClick(View btn) {
        if (btn == search_menu_button) {
            searchMenu();
        }
    }
}