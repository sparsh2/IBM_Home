package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class WaterActivity extends AppCompatActivity {

    TextView reportView;
    int flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        flag=0;

        reportView = findViewById(R.id.detailedReport);

        Spinner graphSpinner = (Spinner) findViewById(R.id.select);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(WaterActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.range_selector));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        graphSpinner.setAdapter(adapter);

    }

    public void detailedReport(View v){
        String str = reportView.getText().toString();
        if(flag==0) {
            str += "\n Water temperature   =   -\nTurbidity   =   -\nTDS   =   -";
            reportView.setText(str);
            flag=1;
        } else{
            str = "Detailed Report";
            reportView.setText(str);
            flag=0;
        }
    }
}
