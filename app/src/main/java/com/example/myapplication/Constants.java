package com.example.myapplication;

public class Constants {

    public static final String MQTT_BROKER_URL = "ssl://rr3fy3.messaging.internetofthings.ibmcloud.com";


    //App information
    public static final String APP_PUBLISH_TOPIC = "iot-2/type/device_type/id/device_id/evt/+/fmt/+";
    public static final String APP_SUBSCRIBE_TOPIC = "iot-2/type/device_type/id/device_id/evt/+/fmt/+";
    public static final String APP_AUTHORIZATION_TOKEN = "6b4bd0&fnsRguIhw8D";
    public static final String APP_CLIENT_ID = "d:rr3fy3:Android:64A2F9CCF855";


    //Device information

    public static final String DEVICE_CLIENT_ID = "d:rr3fy3:Android:64A2F9CCF855";
    public static final String DEVICE_PUBLISH_TOPIC = "iot-2/evt/event/fmt/json";
    public static final String DEVICE_SUBSCRIBE_TOPIC = "iot-2/cmd/command1/fmt/json";
    public static final String USERNAME = "use-token-auth";
    public static final String DEVICE_AUTHORIZATION_TOKEN = "6b4bd0&fnsRguIhw8D";

}
