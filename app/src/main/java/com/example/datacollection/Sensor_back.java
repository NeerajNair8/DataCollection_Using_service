package com.example.datacollection;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.NavigableMap;

import static android.hardware.Sensor.TYPE_PROXIMITY;

public class Sensor_back extends Service implements SensorEventListener {

    SensorManager sensorManager ;
    Sensor sensor,sensor3,sensor2;
    SensorEvent event ;
    private Context context;

    public String accel, light, prox;
    FileWriter fstream;


    private HandlerThread mSensorThread;
    private Handler mSensorHandler;
    Thread thread;
    private Intent intend1;

    String oldvalue,newvalue;

    public Sensor_back() {
            context=Global_context.getAppcontext();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null ;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        oldvalue ="0";
        newvalue="";


        System.out.println(" Service Created ");

        Toast.makeText(context, "SERVICE CREATED", Toast.LENGTH_SHORT).show();

        sensorManager = (SensorManager) context.getSystemService(Service.SENSOR_SERVICE);

        assert sensorManager != null;
        sensor = sensorManager.getDefaultSensor(TYPE_PROXIMITY);
        sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensor3 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorThread=new HandlerThread("Sensor Thread", Thread.MAX_PRIORITY);
        mSensorThread.start();
        mSensorHandler= new Handler(mSensorThread.getLooper());
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL,mSensorHandler);
        sensorManager.registerListener(this, sensor2, SensorManager.SENSOR_DELAY_NORMAL,mSensorHandler);
        sensorManager.registerListener(this, sensor3, SensorManager.SENSOR_DELAY_NORMAL,mSensorHandler);

        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println(" in function Thread ");
                Looper.prepare();
                while (mSensorThread.isAlive()){
                    //newvalue=light;
                    //if(!(oldvalue.equals(newvalue)))
                    func();
                   // else if (oldvalue.equals(newvalue))
                   // {
                    //    System.out.println(" Sensor stopped working ");
                  //  }
                  //  oldvalue=newvalue;
                    if(!(mSensorThread.isAlive()))
                        System.out.println(" Sensor Thread Stopped");


                }
            }
        }).start();
        mSensorHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context," new thread created",Toast.LENGTH_SHORT).show();
                //while (true)
                //func();

            }
        });



        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //System.out.println(" IN ONSENSORCHANGED ");

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            light= String.valueOf(event.values[0]);
        } else if (event.sensor.getType() == TYPE_PROXIMITY) {
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

    @Override
    public void onDestroy() {
        //Intent intent1=new Intent(context,Sensor_back.class);
        startService(intend1);
        //sendBroadcast(intent1);
        //sensorManager.unregisterListener(this);
        System.out.println(" Service Stopped");
        Toast.makeText(context," Exiting From Service ",Toast.LENGTH_SHORT).show();
        mSensorThread.quitSafely();
        super.onDestroy();
    }


    public void func ()
    {
        Toast.makeText(context," Creating file ",Toast.LENGTH_SHORT).show();
        try {

            /*light = t2.getText().toString();
            prox = t3.getText().toString();
            accel = t4.getText().toString();*/

            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File myFile = new File(folder, "user_details2.txt");

            Calendar calander = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");


            fstream = new FileWriter(myFile, true);
            fstream.append((simpleDateFormat.format(calander.getTime())).toString() + "      ");

            fstream.append(light + "  ");

            fstream.append(prox + "  ");

            fstream.append(0+"  ");

            fstream.append(accel + "\n");
            //System.out.println(" Completed Writing on File ");
            fstream.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
