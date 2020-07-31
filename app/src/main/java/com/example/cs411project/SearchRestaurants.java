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
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            user_location = location;
                            System.out.println(location.getLatitude());
                            System.out.println(location.getLongitude());
                            System.out.println(location.getExtras());
                        }
                    }
                });
    }

    private void insertRestaurant() {
        final String name = editTextName.getText().toString().trim();
        final String Start_Hour = editTextOPENTIME.getText().toString().trim();
        final String End_Hour = editTextCLOSINGTIME.getText().toString().trim();
        final String Open_Days = editTextOPENDAYS.getText().toString().trim();
        final String Regional_Type = editTextREGIONALTYPE.getText().toString().trim();
        final String Pricing = editTextPRICING.getText().toString().trim();
        final String Menu_Item = editTextSPECIFICMENUITEM.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_INSERT_RESTAURANT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //JSONObject jsonObject = new JSONObject(response);
                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
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
                //params.put("Name", name);
                //params.put("Address", "test123124");
          params.put("Name:", "TESTNAME");
          params.put("Website:", "atakan.com");
          params.put("Start_Hour:", "0700");
          params.put("End_Hour:", "2300");
          params.put("Open_Days:", "1234560");
          params.put("Address:", "408 E Springfield");
          params.put("Price_Level:", "3");
          params.put("Phone:", "22222222");
          params.put("Regional_Type:", "Turkish");
          params.put("Eatery_Type:", "Ev");

                for (Map.Entry entry : params.entrySet())
                {
                    System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
                }

                return params;
            }
        };

        RequestQueueHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getEatery() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_GET_RESTAURANT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
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
                //JSONObject params = new JSONObject();
                params.put("Name:", "");
                params.put("OpeningHour:", "");
                params.put("ClosingHour:", "");
                params.put("OpenDays:", "%2%4");
                params.put("Type:", "");
                params.put("Pricing:", "");
                params.put("MenuItem:", "Salad");
                return params;
            }
        };
        RequestQueueHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getRestaurants() {
        final ArrayList<String> resultList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_RESTAURANT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject restaurant = arr.getJSONObject(i);
                        resultList.add(restaurant.toString());
                    }
                    for (int i = 0; i < resultList.size(); i++) {
                        textViewRESULT.append(resultList.get(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueueHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void insertEatery() {
        HashMap<String,String> params = new HashMap<>();
        params.put("Name:", "TESTNAME");
        params.put("Website:", "atakan.com");
        params.put("Start_Hour:", "0700");
        params.put("End_Hour:", "2300");
        params.put("Open_Days:", "1234560");
        params.put("Address:", "408 E Springfield");
        params.put("Price_Level:", "3");
        params.put("Phone:", "22222222");
        params.put("Regional_Type:", "Turkish");
        params.put("Eatery_Type:", "Ev");
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Constants.URL_INSERT_RESTAURANT, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Log.println(Log.INFO, "tagconvertstr", "["+response+"]");
                    Log.d("test----", response.toString());
                    JSONObject jsonObject = new JSONObject((Map) response);
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
        });
        RequestQueueHandler.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


    public void onClick(View btn) {
        if (btn == search_restaurants_button) {
            getUserLocation();
            //insertRestaurant();
            //getRestaurants();

            //insertEatery();
            //getEatery();
        }
    }
}
