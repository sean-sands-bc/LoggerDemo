package com.example.loggerdemo;

import android.hardware.SensorManager;
import android.os.MemoryFile;

import androidx.appcompat.app.AppCompatActivity;

public class LoggerModel {
    private SensorManager mSensorManager;
    private MemoryFile mem;

    public LoggerModel(SensorManager sm) {
        mSensorManager = sm;
        
        sm.createDirectChannel(mem);
    }

    public void setLogging(boolean b){
        if(b)
        {
            System.out.println("on");
        }
        else
        {
            System.out.println("off");
        }
    }
}
