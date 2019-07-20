package com.example.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SplashScreen extends AppCompatActivity {

    private static final int permission_request_code = 100;
    String[] perms = {"android.permission.READ_CONTACTS", "android.permission.INTERNET"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash_screen);

//        for(long i=0; i<1000000000; i++){
//
//        }
//
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }




    }

    public void next(View v){
        Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
        SplashScreen.this.startActivity(intent);
    }

    private void myRequestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,
                Manifest.permission.READ_CONTACTS) ||
                ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,
                        Manifest.permission.INTERNET)){

            new AlertDialog.Builder(SplashScreen.this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to continue")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SplashScreen.this, perms, permission_request_code);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(SplashScreen.this, perms, permission_request_code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == permission_request_code){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            }
        }
        else{
            myRequestPermission();
        }
    }
}
