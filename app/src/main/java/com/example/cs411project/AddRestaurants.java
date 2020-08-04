package com.example.cs411project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class AddRestaurants extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextOPENTIME;
    private EditText editTextCLOSINGTIME;
    private EditText editTextOPENDAYS;
    private EditText editTextAddress;
    private EditText editTextPRICING;
    private EditText editTextPHONE;
    private EditText editTextREGIONALTYPE;
    private EditText editTextEATERYTYPE;

    private Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_restaurants);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        editTextName = (EditText) findViewById(R.id.Eatery_Name);
        editTextEmail = (EditText) findViewById(R.id.Email);
        editTextOPENTIME = (EditText) findViewById(R.id.Start_Hour);
        editTextCLOSINGTIME = (EditText) findViewById(R.id.End_Hour);
        editTextOPENDAYS = (EditText) findViewById(R.id.Open_Days);
        editTextAddress = (EditText) findViewById(R.id.Address);
        editTextPRICING = (EditText) findViewById(R.id.Pricing);
        editTextPHONE = (EditText) findViewById(R.id.Phone_Num);
        editTextREGIONALTYPE = (EditText) findViewById(R.id.Regional_Type);
        editTextEATERYTYPE = (EditText) findViewById(R.id.Eatery_Type);

        add_button = (Button) findViewById(R.id.add_restaurants_button);
        add_button.setOnClickListener(this);
    }

    private void addRestaurant() {
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String open = editTextOPENTIME.getText().toString().trim();
        final String close = editTextCLOSINGTIME.getText().toString().trim();
        final String days = editTextOPENDAYS.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        final String pricing = editTextPRICING.getText().toString().trim();
        final String phone = editTextPHONE.getText().toString().trim();
        final String reg_type = editTextREGIONALTYPE.getText().toString().trim();
        final String eatery_type = editTextEATERYTYPE.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_ADD_RESTAURANT, new Response.Listener<String>() {
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
                params.put("Name", name);
                params.put("Website", email);
                params.put("Start_Hour", open);
                params.put("End_Hour", close);
                params.put("Open_Days", days);
                params.put("Address", address);
                params.put("Price_Level", pricing);
                params.put("Phone", phone);
                params.put("Regional_Type", reg_type);
                params.put("Eatery_Type", eatery_type);
                return params;
            }
        };
        RequestQueueHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void onClick(View btn) {
        if (btn == add_button) {
            addRestaurant();
        }
    }
}
