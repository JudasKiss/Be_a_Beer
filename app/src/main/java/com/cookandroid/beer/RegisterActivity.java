package com.cookandroid.beer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner =(Spinner)findViewById(R.id.yearSpinner);
        adapter=ArrayAdapter.createFromResource(this,R.array.year, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner =(Spinner)findViewById(R.id.monthSpinner);
        adapter=ArrayAdapter.createFromResource(this,R.array.month, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner =(Spinner)findViewById(R.id.daySpinner);
        adapter=ArrayAdapter.createFromResource(this,R.array.day, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}