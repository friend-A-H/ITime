package com.jnu.itime.data;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ColorSaver {
    public ColorSaver(Context context) {
        this.context = context;
    }

    Context context;

    public int getColor() {
        return color;
    }

    int color = -16711681;

    public void save(int setColor){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput("ColorSerializable.txt", Context.MODE_PRIVATE));
            outputStream.writeObject(setColor);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int load(){
        try{
            ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput("ColorSerializable.txt"));
            color = (int) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }
}
