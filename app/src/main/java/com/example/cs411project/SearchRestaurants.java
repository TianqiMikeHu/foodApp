package com.example.cs411project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SearchRestaurants extends AppCompatActivity implements View.OnClickListener {

    private FusedLocationProviderClient fusedLocationClient;
    private Location user_location;

    private EditText editTextName;
    private EditText editTextOPENTIME;
    private EditText editTextCLOSINGTIME;
    private EditText editTextOPENDAYS;
    private EditText editTextREGIONALTYPE;
    private EditText editTextPRICING;
    private EditText editTextSPECIFICMENUITEM;
    private TextView textViewRESULT;
    private Button search_restaurants_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_restaurants);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        editTextName = (EditText) findViewById(R.id.Name);
        editTextOPENTIME = (EditText) findViewById(R.id.Start_Hour);
        editTextCLOSINGTIME = (EditText) findViewById(R.id.End_Hour);
        editTextOPENDAYS = (EditText) findViewById(R.id.Open_Days);
        editTextREGIONALTYPE = (EditText) findViewById(R.id.Regional_Type);
        editTextPRICING = (EditText) findViewById(R.id.Pricing);
        editTextSPECIFICMENUITEM = (EditText) findViewById(R.id.Menu_Item);

        textViewRESULT = (TextView) findViewById(R.id.Result);

        search_restaurants_button = (Button) findViewById(R.id.search_restaurants_button);
        search_restaurants_button.setOnClickListener(this);
    }


    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            user_location = location;
                            System.out.println(location.getLatitude());
                            System.out.println(location.getLongitude());
                            System.out.println(location.getExtras());
                            String s = location.getLatitude()+", "+location.getLongitude()+", "+location.getExtras();
                            textViewRESULT.setText(s);
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                            @NonNull String[] permissions,
                                            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,
                        "Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(this,
                        "Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void searchRestaurants() {
        final String name = editTextName.getText().toString().trim();
        final String Start_Hour = editTextOPENTIME.getText().toString().trim();
        final String End_Hour = editTextCLOSINGTIME.getText().toString().trim();
        final String Open_Days = editTextOPENDAYS.getText().toString().trim();
        final String Regional_Type = editTextREGIONALTYPE.getText().toString().trim();
        final String Pricing = editTextPRICING.getText().toString().trim();
        final String Menu_Item = editTextSPECIFICMENUITEM.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_GET_RESTAURANTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Gson gson = new Gson();
                //String json = gson.toJson(response);
                response = response.replace("[","");
                response = response.replace("]","");
                response = response.replace("{","");
                response = response.replace("}","");
                response = response.replace("\\","");
                response = response.replace("\"","");
                response = response.replace("\\t","");
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
                params.put("Name", name);
                params.put("OpeningHour", Start_Hour);
                params.put("ClosingHour", End_Hour);
                params.put("OpenDays", Open_Days);
                params.put("Type", Regional_Type);
                params.put("Pricing", Pricing);
                params.put("MenuItem", Menu_Item);

                return params;
            }
        };

        RequestQueueHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void onClick(View btn) {
        if (btn == search_restaurants_button) {
            searchRestaurants();
        }
    }
}
