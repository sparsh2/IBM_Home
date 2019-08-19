package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class WaterActivity extends AppCompatActivity {

    TextView reportView,textView;
    int flag;




    // MQTT protocol

    MqttAndroidClient Appclient;

    private void addToHistory(String mainText){
        System.out.println("LOG: " + mainText);
    }

    private void establishMQTTconnection2() {

//        String clientId = MqttClient.generateClientId();

        Appclient = new MqttAndroidClient(this.getApplicationContext(), Constants.MQTT_BROKER_URL,
                Constants.APP_CLIENT_ID);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);



        options.setUserName(Constants.USERNAME);
        options.setPassword(Constants.AUTHORIZATION_TOKEN.toCharArray());


        Appclient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                if(b){
                    addToHistory("Reconnected to : " + Constants.MQTT_BROKER_URL);
//                    Toast.makeText(HomeActivity.this, "Connected", Toast.LENGTH_LONG).show();
                    textView.setText("Live");
                    // Because Clean Session is true, we need to re-subscribe
                    subscribeToTopic();
                }
                else {
                    addToHistory("Connected to: " + Constants.MQTT_BROKER_URL);
                    textView.setText("Live");
                }
            }



            @Override
            public void connectionLost(Throwable throwable) {
                addToHistory("The Connection was lost.");
//                Toast.makeText(HomeActivity.this, "Connection Lost", Toast.LENGTH_LONG).show();
                textView.setText("Offline");
                throwable.printStackTrace();
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                addToHistory("Incoming message: " + new String(mqttMessage.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });



        try {
            IMqttToken token = Applient.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("MQTT", "onSuccess of mqqt establish.");
//                    Toast.makeText(HomeActivity.this, "Connected", Toast.LENGTH_LONG).show();
                    String str = "Live";
                    textView.setText(str);

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems

                    Log.d("MQTT", "onFailure of mqqt establish 2.");
//                    Toast.makeText(HomeActivity.this, "Not Connected", Toast.LENGTH_LONG).show();

                    textView.setText("Offline");
                    exception.printStackTrace();

                }
            });
//            subscribeToTopic();
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }





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
