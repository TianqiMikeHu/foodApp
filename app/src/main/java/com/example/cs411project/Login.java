package com.example.cs411project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextUSERID;
    private Button log_in_button;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        editTextUSERID = (EditText) findViewById(R.id.user_id);
        log_in_button = (Button) findViewById(R.id.log_in_button);
        log_in_button.setOnClickListener(this);
        title = (TextView) findViewById(R.id.Title);
    }

    public void onClick(View btn){
        if(btn == log_in_button){
            String temp = editTextUSERID.getText().toString().trim();
            title.setText("You have logged in, "+temp+"!");
        }
    }
}
