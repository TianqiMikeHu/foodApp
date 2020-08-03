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

public class SearchReviews extends AppCompatActivity implements View.OnClickListener {

    private EditText restaurant_name;
    private TextView textViewRESULT;
    private Button searchReviewsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_reviews);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);


        restaurant_name = (EditText) findViewById(R.id.Eatery_Name);
        textViewRESULT = (TextView) findViewById(R.id.Result);
        searchReviewsButton = (Button) findViewById(R.id.search_restaurants_button);
        searchReviewsButton.setOnClickListener(this);
    }

    private void searchReviews() {
        final String restaurantname = restaurant_name.getText().toString().trim();
        final ArrayList<String> resultList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_SEARCH_REVIEW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    response = response.replace("[","");
                    response = response.replace("]","");
                    textViewRESULT.setText(response);
//                    JSONArray arr = new JSONArray(response);
//                    for (int i = 0; i < arr.length(); i++) {
//                        System.out.println(arr.getJSONObject(i));
//                        JSONObject review = arr.getJSONObject(i);
//                        resultList.add(review.toString());
//                    }
//                    for (int i = 0; i < resultList.size(); i++) {
//                        textViewRESULT.append(resultList.get(i));
//                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textViewRESULT.setText("error");
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Eatery_Name", restaurantname);
                return params;
            }
        };
        RequestQueueHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void onClick(View btn) {
        if (btn == searchReviewsButton) {
            searchReviews();
        }
    }
}

