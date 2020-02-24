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
    String username = "ibm";
    String password = "ibmiot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        registerButton = (Button) findViewById(R.id.RegisterButton);
        loginButton = (Button)findViewById(R.id.LoginButton);
        textInputUsername = findViewById(R.id.text_input_username);
        textInputPassword = findViewById(R.id.text_input_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){

//                if(!validatePassword() | !validateUsername() )
//                {
//                    return;
//                }

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

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
        if(textInputUsername.getText().toString()!=username){
            textInputPassword.setError("Wrong username or password!");
            return false;
        }
        else
        {
            textInputUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String passwordInput = textInputPassword.getText().toString();

        if(passwordInput.isEmpty())
        {
            textInputPassword.setError("Field can't be empty");
            return false;
        }
        else
        {
            if(passwordInput!=password){
                textInputPassword.setError("Wrong username or password!");
                return false;
            }
            textInputPassword.setError(null);
            return true;
        }
    }

    public void confirmInput(View view) {
        if(!validatePassword() | !validateUsername() )
        {
            return;
        }

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(intent);
    }
}
