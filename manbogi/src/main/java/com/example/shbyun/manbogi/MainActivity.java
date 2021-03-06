package com.example.shbyun.manbogi;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.lang.String;


public class MainActivity extends AppCompatActivity implements SensorEventListener{



    private SensorManager mSensorManager;
    private Sensor sensor_Gravity;
    private Sensor sensor_accelerometer;
    private Sensor sensor_linear_acceleration;
    private Sensor sensor_Gyroscope;
    double acceleration;
    int steps;
    int dir_UP;
    int dir_DOWN;
    double gravity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor_Gravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensor_accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensor_linear_acceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensor_Gyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }

    protected void onResume(){
        super.onResume();

        mSensorManager.registerListener(this, sensor_Gravity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, sensor_accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, sensor_linear_acceleration, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, sensor_Gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause(){
        super.onPause();

        mSensorManager.unregisterListener(this);
    }



    public final void onAccuracyChanged(Sensor sensor, int accuracy){

    }
    public final void onSensorChanged(SensorEvent event){


        TextView stepsview;

        stepsview = (TextView)findViewById(R.id.stepsView);

        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            acceleration = Math.sqrt(x*x + y*y + z*z);


        }
        if(event.sensor.getType()==Sensor.TYPE_GRAVITY){
            float gx = event.values[0];
            float gy = event.values[1];
            float gz = event.values[2];
            gravity = Math.sqrt(gx*gx+gy*gy+gz*gz);

        }

        if(acceleration - gravity > 5){
            dir_UP = 1;
        }
        if(gravity - acceleration > 5 && dir_UP == 1){
            dir_DOWN = 1;
        }

        if(dir_DOWN == 1){
            steps++;
            stepsview.setText("걸음 수 :  " + steps);
            dir_DOWN = 0;
            dir_UP = 0;

        }
    }



}
