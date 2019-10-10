package com.example.datacollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView t1, t2, t3, t4, t5;
    Switch s1 ;
    Boolean var;


    private static final String filename = "fname.txt";

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DataCollection";

    public String accel, light, prox;
    public int x = 0;
    public int z=0;


    FileWriter fstream;
    File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    File myFile = new File(folder, "user_details.txt");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView) findViewById(R.id.displaytime);
        t2 = (TextView) findViewById(R.id.displaylight);
        t3 = (TextView) findViewById(R.id.prox);
        t4 = (TextView) findViewById(R.id.acceldata);
        t5 = (TextView) findViewById(R.id.extdata);
        final Button b1 = (Button) findViewById(R.id.button1);
        final Button b2 = (Button) findViewById(R.id.button2);


        s1=(Switch)findViewById(R.id.switch1);
        s1.setText("Start Data collection ");
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    b1.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.VISIBLE);
                    x=0;

                }
                else
                {
                    x = 0;
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                }

            }
        });




        /*try {
            fstream = new FileWriter(myFile, true);
            fstream.append("start" + "\n");
            fstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/




        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");


        String time = simpleDateFormat.format(calander.getTime());
        t1.setText(time);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(x!=1) {
                    x = 1;
                    Example a = new Example();
                    a.start();
                }
                z = 0;
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.VISIBLE);

            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(x!=1)
               {
                   Example a = new Example();
                   a.start();
                   x=1;
               }
               z=1;
                b2.setVisibility(View.INVISIBLE);
                b1.setVisibility(View.VISIBLE);
            }
        });


    }




    class Example extends Thread implements SensorEventListener {
        SensorManager sensorManager;
        Sensor sensor, sensor2, sensor3;
        SensorEvent event;


        public void run() {

            sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);


            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

            sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

            sensor3 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, sensor2, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, sensor3, SensorManager.SENSOR_DELAY_NORMAL);

            while (x == 1) {
                try {

                    light = t2.getText().toString();
                    prox = t3.getText().toString();
                    accel = t4.getText().toString();

                    Calendar calander = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");


                    fstream = new FileWriter(myFile, true);
                    fstream.append((simpleDateFormat.format(calander.getTime())).toString() + "      ");

                    fstream.append(light + "  ");

                    fstream.append(prox + "  ");

                    fstream.append(z+"  ");

                    fstream.append(accel + "\n");
                    fstream.close();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

          /*  try {
                fstream = new FileWriter(myFile, true);
                fstream.append("last");
                fstream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }

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

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                t2.setText("" + event.values[0]);
            } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                t3.setText("" + event.values[0]);
                prox = t3.getText().toString();
            } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                t4.setText("" + event.values[0]);
                accel = t4.getText().toString();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }


    }

}



