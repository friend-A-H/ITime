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
import com.jnu.itime.TimeItem;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class TimeAdapter extends ArrayAdapter<TimeItem> {
    private int resourceId;

    public TimeAdapter(Context context, int resource, List<TimeItem> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TimeItem timeItem = getItem(position);//获取当前项的实例
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
        String dateText;
        dateText = String.valueOf(timeItem.getYear()) + "年" + String.valueOf(timeItem.getMonth()) + "月" + String.valueOf(timeItem.getDay()) + "日";
        ((TextView) view.findViewById(R.id.time_title)).setText(timeItem.getTitle());
        ((TextView) view.findViewById(R.id.time_date)).setText(dateText);
        ((TextView) view.findViewById(R.id.time_description)).setText(timeItem.getDescription());
        ((TextView) view.findViewById(R.id.time_countdown)).setText(timeItem.getCountdown());
        return view;
    }
}
