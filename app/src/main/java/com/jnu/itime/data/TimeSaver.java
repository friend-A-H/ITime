package com.jnu.itime.data;

import android.content.Context;

import com.jnu.itime.data.model.MyTime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class TimeSaver {
    public TimeSaver(Context context) {
        this.context = context;
    }

    Context context;

    public ArrayList<MyTime> getTimes() {
        return times;
    }

    ArrayList<MyTime> times = new ArrayList<MyTime>();

    public void save(){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput("TimeSerializable.txt", Context.MODE_PRIVATE));
            outputStream.writeObject(times);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MyTime> load(){
        try{
            ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput("TimeSerializable.txt"));
            times = (ArrayList<MyTime>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
}
