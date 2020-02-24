package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;


public class AirActivity extends AppCompatActivity {

    EditText newedit;
    Button pubbutton;
    TextView textView;

    private HashMap subscriptions = new HashMap();


    MqttAndroidClient deviceclient;

    private void addToHistory(String mainText){
        System.out.println("LOG: " + mainText);
    }

    public void subscribeToDeviceEvents(String deviceType, String deviceId, String event, String format) {
        try {
            String newTopic = "iot-2/type/"+deviceType+"/id/"+deviceId+"/evt/"+event+"/fmt/" + format;
            subscriptions.put(newTopic, new Integer(0));
            subscribeToTopic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publishMessage(@NonNull MqttAndroidClient client, @NonNull String msg, int qos, @NonNull String topic)
            throws MqttException, UnsupportedEncodingException {
        byte[] encodedPayload = new byte[0];
        encodedPayload = msg.getBytes("UTF-8");
        MqttMessage message = new MqttMessage(encodedPayload);
        message.setId(320);
        message.setRetained(true);
        message.setQos(qos);
        client.publish(topic, message);
    }

    public void subscribeToTopic(){
        try {
            deviceclient.subscribe(Constants.DEVICE_SUBSCRIBE_TOPIC, 0, null, new IMqttActionListener() {
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

    private void establishMQTTconnection2() {

//        String clientId = MqttClient.generateClientId();

        deviceclient = new MqttAndroidClient(this.getApplicationContext(), Constants.MQTT_BROKER_URL,
                Constants.DEVICE_CLIENT_ID);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);



        options.setUserName(Constants.USERNAME);
        options.setPassword(Constants.DEVICE_AUTHORIZATION_TOKEN.toCharArray());


        deviceclient.setCallback(new MqttCallbackExtended() {
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
            IMqttToken token = deviceclient.connect(options);
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


        try{
            publishMessage(deviceclient,newedit.getText().toString(), 0, Constants.DEVICE_PUBLISH_TOPIC);
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public void onclick1(View v){
        try{
            publishMessage(deviceclient,newedit.getText().toString(), 0, Constants.DEVICE_PUBLISH_TOPIC);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air2);

        newedit = findViewById(R.id.newedit);
        pubbutton = findViewById(R.id.publishbutton);
        textView = findViewById(R.id.textview);

        establishMQTTconnection2();


//        GraphView  graph = (GraphView) findViewById(R.id.myGraph);
//        LineGraphSeries <DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0,1),
//                new DataPoint(1,5),
//                new DataPoint(2,3),
//                new DataPoint(3,2)
//        });
//        graph.addSeries(series);

    }
}
