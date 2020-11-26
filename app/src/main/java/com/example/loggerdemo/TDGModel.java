package com.example.loggerdemo;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.MemoryFile;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;

public class TDGModel {
    private class PressEvent{
        private char press;
        private long timestamp;

        public PressEvent(char p, long t){
            press = p;
            timestamp = t;
        }

        public char getPress(){
            return press;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    private SensorManager mSensorManager;
    private Sensor mGyro;
    private MemoryFile mem;
    private SensorDirectChannel mSDC;
    private File file;
    private Queue<PressEvent> peQ;

    public TDGModel(SensorManager sm, File f) throws IOException {
        mSensorManager = sm;
        file = f;
        mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mem = new MemoryFile(null, 1048575);

        mSDC = sm.createDirectChannel(mem);
    }

    public void input(char pressed, long timestamp){
        peQ.add(new PressEvent(pressed,timestamp));
    }

    public void setLogging(boolean b) throws IOException {
        if(b)
        {
            mSDC.configure(mGyro,SensorDirectChannel.RATE_NORMAL);
            System.out.println("on");
            //return null;
        }
        else
        {
            mSDC.configure(mGyro,SensorDirectChannel.RATE_STOP);
            InputStream is = mem.getInputStream();
            //Path path;
            FileOutputStream fos = new FileOutputStream(file);

            byte[] buf = new byte[8192];
            int length;
            while ((length = is.read(buf)) > 0) {
                fos.write(buf, 0, length);
            }

            DataOutputStream dos = new DataOutputStream(fos);
            while(!peQ.isEmpty()){
                PressEvent pe = peQ.remove();
                dos.writeChar(pe.getPress());
                dos.writeLong(pe.getTimestamp());

            }




            //Files.copy(is,path);

            //File exportFile = new File("/storage/emulated/0/Download/keyspy/loggerdemo.logger");
            //Intent i = new Intent(Intent.ACTION_CREATE_DOCUMENT);

            /*
            try {
                //Path path = FileSystems.getDefault().getPath("keyspy", "logger", "foo.bar");

                //Files.createDirectories(path.getParent());
                //Files.createFile(path);
                //new File(path.toString());

                //Files.copy(is, path);
            } catch (IOException e) {
                e.printStackTrace();
            }
             */
            System.out.println("off");
            //return i;
        }
    }

    public void close(){
        mSDC.close();
    }
}
