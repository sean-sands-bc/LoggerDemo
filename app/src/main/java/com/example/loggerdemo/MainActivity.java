package com.example.loggerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private LoggerModel logMod;
    private ToggleButton tbLogMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logMod = new LoggerModel((SensorManager) getSystemService(SENSOR_SERVICE));

        tbLogMode = (ToggleButton)findViewById(R.id.tbLogMode);
        tbLogMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 logMod.setLogging(isChecked);
             }
         }

        );
    }
}