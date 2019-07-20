package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class EnergyActivity extends AppCompatActivity {

    TextView reportView;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy);

        flag=0;

        reportView = findViewById(R.id.detailedReport);

        Spinner graphSpinner = (Spinner) findViewById(R.id.select);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(EnergyActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.range_selector));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        graphSpinner.setAdapter(adapter);
    }

    public void detailedReport(View v){
        String str = reportView.getText().toString();
        if(flag==0) {
            str += "\n O2% = \nCO2% = ";
            reportView.setText(str);
            flag=1;
        } else{
            str = "Detailed Report";
            reportView.setText(str);
            flag=0;
        }
    }
}
