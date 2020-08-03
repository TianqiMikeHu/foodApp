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

public class Review extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUserID;
    private EditText editTextEateryName;
    private EditText editTextDate;
    private EditText editTextFeedback;
    private TextView textViewRESULT;
    private Button submit_review_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        editTextUserID = (EditText) findViewById(R.id.User_ID);
        editTextEateryName = (EditText) findViewById(R.id.Eatery_Name);
        editTextDate = (EditText) findViewById(R.id.Visit_Date);
        editTextFeedback = (EditText) findViewById(R.id.editTextTextMultiLine);
        textViewRESULT = (TextView) findViewById(R.id.SuccessMessageBox);
        submit_review_button = (Button) findViewById(R.id.search_restaurants_button);
        submit_review_button.setOnClickListener(this);
    }

    private void submitReview() {
        final String userID = editTextUserID.getText().toString().trim();
        final String eateryname = editTextEateryName.getText().toString().trim();
        final String date = editTextDate.getText().toString().trim();
        final String feedback = editTextFeedback.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_ADD_REVIEW, new Response.Listener<String>() {
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
                params.put("User_ID", userID);
                params.put("Eatery_Name", eateryname);
                params.put("Visit_Date", date);
                params.put("Feedback", feedback);
                return params;
            }
        };
        RequestQueueHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void onClick(View btn) {
        if (btn == submit_review_button) {
            submitReview();
        }
    }
}
