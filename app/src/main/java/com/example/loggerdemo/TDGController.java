package com.example.loggerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;

public class TDGController extends AppCompatActivity {
    private TDGModel tdgMod;
    //private ToggleButton tbLogMode;
    //File f;
    private Button btnInput1;
    private Button btnInput2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File senf = new File(getExternalFilesDir(null), "tdgsen.txt");
        File inpf = new File(getExternalFilesDir(null), "tdginp.txt");
        try {
            tdgMod = new TDGModel((SensorManager) getSystemService(SENSOR_SERVICE), senf, inpf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.tdg_view);
        btnInput1 = (Button)findViewById(R.id.input1);
        btnInput2 = (Button)findViewById(R.id.input2);

        btnInput1.setOnClickListener(new View.OnClickListener(){

             @Override
             public void onClick(View v) {
                 tdgMod.input('1', SystemClock.elapsedRealtimeNanos());
             }
         }

        );

        btnInput2.setOnClickListener(new View.OnClickListener(){

                                         @Override
                                         public void onClick(View v) {
                                             tdgMod.input('2', SystemClock.elapsedRealtimeNanos());
                                         }
                                     }

        );

        try {
            tdgMod.setLogging(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
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

                 if(i!=null){
                     startActivity(i);
                 }

             }
         }

        );*/
    }

    @Override
    protected void onDestroy() {
        try {
            tdgMod.setLogging(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tdgMod.close();
        super.onDestroy();
    }


}