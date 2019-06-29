package com.example.myapplication;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class SecondActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 4 characters
                    "$");

    private Button registerButton;

    private EditText email;
    private EditText username;
    private EditText password;
    private EditText rePassword;
    private EditText modelNo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        registerButton = findViewById(R.id.RegisterButton);
        email = findViewById(R.id.text_input_email);
        username = findViewById(R.id.text_input_username);
        password = findViewById(R.id.text_input_password);
        rePassword = findViewById(R.id.text_input_repassword);
        modelNo = findViewById(R.id.text_input_modelno);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
            }
        });


    }

    private void Validate() {
        boolean a = valEmail();
        boolean b = valUser();
        boolean c = matchPass();
        boolean d = valModel();
        if(a && b && c && d)
        {

        }
    }

    private boolean valEmail(){
        String mailId = email.getText().toString();
        if(Patterns.EMAIL_ADDRESS.matcher(mailId).matches())
        {
            return true;
        }
        else
        {
            email.setError("Enter a valid Mail-id");
            return false;
        }
    }

    private boolean valUser(){
        String userid = username.getText().toString();
        if(userid.length()>15)
        {
            username.setError("Username too long");
            return false;
        }
        else if(userid.length() == 0)
        {
            username.setError("Field can't be empty");
            return false;
        }
        return true;
    }

    private boolean valModel(){
        String model = modelNo.getText().toString();
        if(model.length()==0)
        {
            modelNo.setError("Field can't be empty");
            return false;
        }
        for(int i=0; i<model.length(); i++)
        {
            if(model.charAt(i) == ' ') {
                modelNo.setError("Enter a valid Model-Number");
                return false;
            }
        }
        return true;
    }

    private boolean matchPass(){
        String psw = password.getText().toString();
        String reEnteredPsw = rePassword.getText().toString();

        if(psw.length()==0)
        {
            password.setError("Field can't be empty");
            if(reEnteredPsw.length()==0)
            {
                rePassword.setError("Field can't be empty");
            }
            return false;
        }

        if(!PASSWORD_PATTERN.matcher(psw).matches())
        {
            password.setError("Password too weak!");
            return false;
        }
        else if(!psw.equals(reEnteredPsw))
        {
            rePassword.setError("Password doesn't match");
            return false;
        }
        else
        {
            return true;
        }
    }
}