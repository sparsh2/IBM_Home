package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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


    EditText editText;
    TextView textView;
    MqttAndroidClient client;

    String publishMessage = "hello World!!";

    private void addToHistory(String mainText){
        System.out.println("LOG: " + mainText);
    }

    public void subscribeToTopic(){
        try {
            client.subscribe(Constants.SUBSCRIBE_TOPIC, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    addToHistory("Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    addToHistory("Failed to subscribe");
                }
            });

        } catch (MqttException ex){
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    public void publishMessage(View v){

        try {
            MqttMessage message = new MqttMessage();
            if(editText.getText().toString()!=""){
                publishMessage = editText.getText().toString();
            } else{
                publishMessage = "Hello world!!";
            }
            message.setPayload(publishMessage.getBytes());
            client.publish(Constants.PUBLISH_TOPIC, message);
            addToHistory("Message Published");
            if(!client.isConnected()){
                addToHistory(client.getBufferedMessageCount() + " messages in buffer.");
            }
        } catch (Exception e) {
            System.err.println("Error Publishing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void establishMQTTconnection2() {

//        String clientId = MqttClient.generateClientId();

        client = new MqttAndroidClient(this.getApplicationContext(), Constants.MQTT_BROKER_URL,
                        Constants.CLIENT_ID);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);



        options.setUserName(Constants.API_KEY);
        options.setPassword(Constants.AUTHORIZATION_TOKEN.toCharArray());


        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                if(b){
                    addToHistory("Reconnected to : " + Constants.MQTT_BROKER_URL);
                    // Because Clean Session is true, we need to re-subscribe
                    subscribeToTopic();
                }
                else {
                    addToHistory("Connected to: " + Constants.MQTT_BROKER_URL);
                }
            }



            @Override
            public void connectionLost(Throwable throwable) {
                addToHistory("The Connection was lost.");
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
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("MQTT", "onSuccess of mqqt establish.");
                    Toast.makeText(Demo.this, "Connected", Toast.LENGTH_LONG).show();
                    String str = "Connected";
                    textView.setText(str);

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems

                    Log.d("MQTT", "onFailure of mqqt establish 2.");
                    Toast.makeText(Demo.this, "Not Connected", Toast.LENGTH_LONG).show();

                    textView.setText("Failed to Connect!!");
                    exception.printStackTrace();

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

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textview);

//        PahoMqttClient newConnection = new PahoMqttClient();
//        MqttAndroidClient androidClient = newConnection.getMqttClient(this.getApplicationContext(),
//                Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);
//
//        try{
//            newConnection.subscribe(androidClient,Constants.PUBLISH_TOPIC,1);
//        } catch(Exception e){
//            e.printStackTrace();
//        }


        establishMQTTconnection2();
    }
}
