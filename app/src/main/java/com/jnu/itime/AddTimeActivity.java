package com.jnu.itime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jnu.itime.data.model.MyTime;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class AddTimeActivity extends AppCompatActivity {
    public static final int RC_CHOOSE_PHOTO = 901;
    private String parent;
    private MyTime myTime = new MyTime(0,0,0,0,0,"null","","");
    private int nowYear, nowMonth, nowDay, nowHour, nowMinute;
    private EditText addTitleText,addDescriptionText;
    private String uri = "null";
    private String dateText,timeText;
    private TextView chooseTimeTextView, choosePicTextView;
    private ImageView selectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);

        Toolbar mToolbar = findViewById(R.id.mToolbar);

        addTitleText = (EditText)findViewById(R.id.add_title_text);
        addDescriptionText = (EditText)findViewById(R.id.add_description_text);
        chooseTimeTextView = (TextView)findViewById(R.id.add_time_text);
        choosePicTextView = (TextView)findViewById(R.id.add_pic_text);
        selectImage = (ImageView)findViewById(R.id.add_image_view);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true);

        parent = getIntent().getStringExtra("parent");
        init();

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_time_toolbar_comfirm:
                        if(addTitleText.getText().toString().isEmpty()){
                            Toast.makeText(AddTimeActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        Intent intent = new Intent();
                        myTime.setImageUri(uri);
                        myTime.setTitle(addTitleText.getText().toString());
                        myTime.setDescription(addDescriptionText.getText().toString());
                        intent.putExtra("addTime",myTime);
                        setResult(RESULT_OK,intent);
                        AddTimeActivity.this.finish();
                        break;
                }
                return false;
            }
        });

        chooseTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTimeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateText = String.format("%d年%d月%d日",year,monthOfYear+1,dayOfMonth);
                        myTime.setYear(year);
                        myTime.setMonth(monthOfYear+1);
                        myTime.setDay(dayOfMonth);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTimeActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        timeText = String.format(" %d:%d",hourOfDay,minute);
                                        chooseTimeTextView.setText(dateText + " " + timeText);
                                        myTime.setHour(hourOfDay);
                                        myTime.setMinute(minute);
                                    }
                                }, nowHour, nowMinute, true);
                        timePickerDialog.show();
                    }
                }, nowYear, nowMonth, nowDay);
                datePickerDialog.show();
            }
        });

        choosePicTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动态申请获取访问 读写磁盘的权限
                if (ContextCompat.checkSelfPermission(AddTimeActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddTimeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                }

                //打开相册
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //Intent.ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT"
                intent.setType("image/*");
                startActivityForResult(intent, RC_CHOOSE_PHOTO); // 打开相册
            }
        });
    }

    private void init(){
        Calendar ca = Calendar.getInstance();
        nowYear = ca.get(Calendar.YEAR);
        nowMonth = ca.get(Calendar.MONTH);
        nowDay = ca.get(Calendar.DAY_OF_MONTH);
        nowHour = ca.get(Calendar.HOUR_OF_DAY);
        nowMinute = ca.get(Calendar.MINUTE);

        if(parent == "TimeListView"){
            myTime.setYear(nowYear);
            myTime.setMonth(nowMonth+1);
            myTime.setDay(nowDay);
            myTime.setMonth(0);
            myTime.setMinute(0);
        }
        else{
            myTime = (MyTime) getIntent().getSerializableExtra("selectTime");
            addTitleText.setText(myTime.getTitle());
            addDescriptionText.setText(myTime.getDescription());
            Bitmap bitmap = null;
            try {
                uri = myTime.getImageUri();
                Uri imageUri = Uri.parse(uri);
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(imageStream);
                selectImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            dateText = String.format("%d年%d月%d日",myTime.getYear(),myTime.getMonth(),myTime.getDay());
            if(myTime.getHour() != 0 || myTime.getMinute() != 0){
                timeText = String.format(" %d:%d",myTime.getHour(),myTime.getMinute());
                dateText = dateText + " " + timeText;
            }
            chooseTimeTextView.setText(dateText);
        }
    }

    //设置toolbar的menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_time_toolbar_menu, menu);
        return true;
    }

    //设置返回点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if (resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                uri = imageUri.toString();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(imageStream);
                selectImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
