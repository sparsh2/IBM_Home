package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Button registerButton;
    private Button loginButton;
    private EditText textInputUsername;
    private EditText textInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        registerButton = (Button) findViewById(R.id.RegisterButton);
        loginButton = (Button)findViewById(R.id.LoginButton);
        textInputUsername = findViewById(R.id.text_input_username);
        textInputPassword = findViewById(R.id.text_input_password);

        registerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this, SecondActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });



    }

    private boolean validateUsername(){
        String usernameInput = textInputUsername.getText().toString().trim();

        if(usernameInput.isEmpty())
        {
            textInputUsername.setError("Field can't be empty");
            return false;
        }
        else if(usernameInput.length() > 15)
        {
            textInputUsername.setError("Username too long");
            return false;
        }
        else
        {
            textInputUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String passwordInput = textInputPassword.getText().toString().trim();

        if(passwordInput.isEmpty())
        {
            textInputPassword.setError("Field can't be empty");
            return false;
        }
        else
        {
            textInputPassword.setError(null);
            return true;
        }
    }

    public void confirmInput(View view) {
        if(!validatePassword() | !validateUsername())
        {
            return;
        }

        Intent intent = new Intent(LoginActivity.this, Air.class);
        LoginActivity.this.startActivity(intent);
    }
}