package com.example.cs411project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRegistration extends AppCompatActivity implements View.OnClickListener {

    // add EditTexts
    private EditText editTextUserID;
    private EditText editTextDisplayID;
    private EditText editTextAddress;
    private TextView textViewRESULT;
    private Button add_user_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        // EditTexts
        editTextUserID = (EditText) findViewById(R.id.User_ID);
        editTextDisplayID = (EditText) findViewById(R.id.Display_ID);
        editTextAddress = (EditText) findViewById(R.id.Address);
        textViewRESULT = (TextView) findViewById(R.id.SuccessMessageBox);
        add_user_button = (Button) findViewById(R.id.search_restaurants_button);
        add_user_button.setOnClickListener(this);
    }

    private void addUser() {
        final String userid = editTextUserID.getText().toString().trim();
        final String displayid = editTextDisplayID.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_ADD_USER, new Response.Listener<String>() {
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
                params.put("User_ID", userid);
                params.put("Display_ID", displayid);
                params.put("Address", address);
                params.put("Total_Outings", "7");
                return params;
            }
        };
        RequestQueueHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

//    private void addUser() throws JSONException {
//        final String userid = editTextUserID.getText().toString().trim();
//        final String displayid = editTextDisplayID.getText().toString().trim();
//        final String address = editTextAddress.getText().toString().trim();
//        JSONObject json = new JSONObject();
//        json.put("User_ID", "hello");
//        Map<String,String> params = new HashMap<>();
//        params.put("User_ID", userid);
//        params.put("Display_ID", displayid);
//        params.put("Address", address);
//        params.put("Total_Outings", "5");
//        JSONObject parameters = new JSONObject(params);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
//                Constants.URL_ADD_USER, json, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    VolleyLog.wtf(response.toString(), "utf-8");
//                    JSONObject jsonObject = new JSONObject((Map) response);
//                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//
//                } catch (JSONException e) {
//                    System.out.println("here2");
//                    Log.d("here2", e.toString());
//                    e.printStackTrace();
//                }
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("here5");
//
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        RequestQueueHandler.getInstance(this).addToRequestQueue(jsonObjectRequest);
//    }

//    private void addUser() {
//        final String userid = editTextUserID.getText().toString().trim();
//        final String displayid = editTextDisplayID.getText().toString().trim();
//        final String address = editTextAddress.getText().toString().trim();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                Constants.URL_ADD_USER, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //JSONArray arr = new JSONArray(response);
//                System.out.println(response);
//                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println(error.getMessage());
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String,String>();
//                //JSONObject params = new JSONObject();
//                params.put("User_ID", userid);
//                params.put("Display_ID", displayid);
//                params.put("Address", address);
//                params.put("Total_Outings", "5");
//                return params;
//            }
//            @Override
//            public String getHeaders() {
//                //return "application/json";
//                return "x-www-form-urlencoded";
//            }
//        };
//        RequestQueueHandler.getInstance(this).addToRequestQueue(stringRequest);
//    }



    public void onClick(View btn) {
        if (btn == add_user_button) {
            addUser();
        }
    }
}

