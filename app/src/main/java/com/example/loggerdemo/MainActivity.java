package com.example.loggerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TDGModel tdgMod;
    private ToggleButton tbLogMode;
    //File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File f = new File(getExternalFilesDir(null), "tdgdemo.txt");
        try {
            tdgMod = new TDGModel((SensorManager) getSystemService(SENSOR_SERVICE), f);
        } catch (IOException e) {
            e.printStackTrace();
        }


        tbLogMode = (ToggleButton)findViewById(R.id.tbLogMode);
        tbLogMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 //Intent i =

                 try {
                     tdgMod.setLogging(isChecked);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 /*
                 if(i!=null){
                     startActivity(i);
                 }
                 */
             }
         }

        );
    }

    @Override
    protected void onDestroy() {
        tdgMod.close();
        super.onDestroy();
    }
}