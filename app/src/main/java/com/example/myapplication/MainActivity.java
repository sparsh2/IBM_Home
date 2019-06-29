package com.example.myapplication;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void energy (View view) {
        Intent intent = new Intent(MainActivity.this, EnergyActivity.class);
        MainActivity.this.startActivity(intent);
    }

    public void air (View view) {
        //Change this later
//        Intent intent = new Intent(MainActivity.this, AirActivity.class);
//        MainActivity.this.startActivity(intent);

        Intent intent = new Intent(MainActivity.this, Demo.class);
        MainActivity.this.startActivity(intent);

    }

    public void water (View view) {
        Intent intent = new Intent(MainActivity.this, WaterActivity.class);
        MainActivity.this.startActivity(intent);
    }
}
