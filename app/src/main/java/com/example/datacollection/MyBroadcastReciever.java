package com.example.datacollection;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;

import com.example.datacollection.MainActivity;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MyBroadcastReciever extends BroadcastReceiver {

    MainActivity obj1 = new MainActivity();
    MainActivity.Example example = obj1.new Example();

    //SensorManager sensorManager;
    //Sensor sensor, sensor2, sensor3;
   // SensorEvent event;
   // public String accel, light, prox;
    //FileWriter fstream;
    Context context;

    public MyBroadcastReciever() {

        context=Global_context.getAppcontext();

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println(" Broadcast Revieved");

        Intent intent1=new Intent(context,Sensor_back.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent1);
        }
        else {
            context.startService(intent1);
        }






       /* sensorManager = (SensorManager) context.getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        sensor3 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensor2, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensor3, SensorManager.SENSOR_DELAY_NORMAL);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                func();;
            }
        }
        ,1000);

        example.start();



           try {
                fstream = new FileWriter(myFile, true);
                fstream.append("last");
                fstream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
}/*

       /* protected void onPause() {
            super.onPause();
            sensorManager.unregisterListener(this);

        }


        protected void onResume() {
            //super.onResume();
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, sensor2, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, sensor3, SensorManager.SENSOR_DELAY_NORMAL);
        }*/




    /*@Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
        light= String.valueOf(event.values[0]);
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
        //t3.setText("" + event.values[0]);
        prox = String.valueOf(event.values[0]);
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        //t4.setText("" + event.values[0]);
        accel =String.valueOf( Math.sqrt( Math.pow(event.values[0],2)+Math.pow(event.values[1],2)+Math.pow(event.values[2],2)));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void func ()
    {
        try {

            /*light = t2.getText().toString();
            prox = t3.getText().toString();
            accel = t4.getText().toString();*/

            /*File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File myFile = new File(folder, "user_details2.txt");

            Calendar calander = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");


           /* fstream = new FileWriter(myFile, true);
            fstream.append((simpleDateFormat.format(calander.getTime())).toString() + "      ");

            fstream.append(light + "  ");

            fstream.append(prox + "  ");

            fstream.append(0+"  ");

            fstream.append(accel + " From Broadcast Reciever\n");
            fstream.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }*/

}}
