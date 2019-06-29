package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Demo extends AppCompatActivity {

    TextView textView;

    private void establishMQTTconnection2() {

        String clientId = MqttClient.generateClientId();

        MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), Constants.MQTT_BROKER_URL,
                        Constants.CLIENT_ID);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(Constants.API_KEY);
        options.setPassword(Constants.AUTHORIZATION_TOKEN.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("MQTT", "onSuccess of mqqt establish 2.");
                    Toast.makeText(Demo.this, "Connected", Toast.LENGTH_LONG).show();
                    String str = "Successfully Connected";
                    textView.setText(str);

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems

                    Log.d("MQTT", "onFailure of mqqt establish 2.");
                    Toast.makeText(Demo.this, "Not Connected", Toast.LENGTH_LONG).show();

                    textView.setText("Failed to Connect!!");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        textView = findViewById(R.id.textview);

//        PahoMqttClient newConnection = new PahoMqttClient();
//        MqttAndroidClient androidClient = newConnection.getMqttClient(this.getApplicationContext(),
//                Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);

        establishMQTTconnection2();


    }
}
