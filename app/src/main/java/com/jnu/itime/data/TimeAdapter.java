package com.jnu.itime.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jnu.itime.R;
import com.jnu.itime.data.model.MyTime;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeAdapter extends ArrayAdapter<MyTime> {
    private int resourceId;

    public TimeAdapter(Context context, int resource, List<MyTime> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        MyTime timeItem = getItem(position);//获取当前项的实例
        if(timeItem.getImageUri().equals("null")){
            ((ImageView) view.findViewById(R.id.time_cover)).setImageResource(R.drawable.time);
        }
        else{
            Bitmap bitmap = null;
            try {
                Uri imageUri = Uri.parse(timeItem.getImageUri());
                InputStream imageStream = view.getContext().getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(imageStream);
                ((ImageView) view.findViewById(R.id.time_cover)).setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        String dateText,formatDate;
        dateText = String.valueOf(timeItem.getYear()) + "年" + String.valueOf(timeItem.getMonth()) + "月" + String.valueOf(timeItem.getDay()) + "日";
        ((TextView) view.findViewById(R.id.time_title)).setText(timeItem.getTitle());
        ((TextView) view.findViewById(R.id.time_date)).setText(dateText);
        ((TextView) view.findViewById(R.id.time_description)).setText(timeItem.getDescription());

        //获取当前日期
        Calendar calendar=Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
        Date nowDate= calendar.getTime();
        Date setDate= new Date();
        //计算相差天数
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        if(timeItem.getMonth() < 10 && timeItem.getDay() < 10) {
            formatDate = String.valueOf(timeItem.getYear()) + "-" + "0" + String.valueOf(timeItem.getMonth()) + "-" + "0" + String.valueOf(timeItem.getDay());
        }
        else if(timeItem.getMonth() >= 10 && timeItem.getDay() < 10) {
            formatDate = String.valueOf(timeItem.getYear()) + "-" + String.valueOf(timeItem.getMonth()) + "-" + "0" + String.valueOf(timeItem.getDay());
        }
        else if(timeItem.getMonth() < 10 && timeItem.getDay() >= 10){
            formatDate = String.valueOf(timeItem.getYear()) + "-" + "0" + String.valueOf(timeItem.getMonth()) + "-" + String.valueOf(timeItem.getDay());
        }
        else{
            formatDate = String.valueOf(timeItem.getYear()) + "-" + String.valueOf(timeItem.getMonth()) + "-" + String.valueOf(timeItem.getDay());
        }

        try {
            setDate = sdt.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long disMs = setDate.getTime() - nowDate.getTime();
        if(disMs >=0){
            disMs += 1000;
        }
        int difDay = (int)(disMs / (24 * 3600 * 1000));
        String countdownText;
        if(difDay == 0) {
            countdownText = "今天";
        }
        else if(difDay < 0){
            countdownText = String.valueOf("已经" + difDay*(-1) + "天");
        }
        else{
            countdownText = String.valueOf("还有" + difDay + "天");
        }

        ((TextView) view.findViewById(R.id.time_countdown)).setText(countdownText);
        return view;
    }
}
