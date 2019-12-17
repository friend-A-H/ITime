package com.jnu.itime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.jnu.itime.data.AboutFragment;
import com.jnu.itime.data.ColorPickerDialog;
import com.jnu.itime.data.ColorSaver;
import com.jnu.itime.data.TimeFragment;

public class TimeListViewActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_NEW_TIME = 902;
    public static final int REQUEST_CODE_DETAIL_TIME = 903;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TimeFragment timeFragment;
    private AboutFragment aboutFragment;
    private FragmentTransaction transaction;
    private ColorPickerDialog dialog;
    private ColorSaver colorSaver;
    int setColor = -16776961;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_list_view);

        toolbar=findViewById(R.id.general_toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.nav_view);

        timeFragment = new TimeFragment();
        aboutFragment = new AboutFragment();

        colorSaver = new ColorSaver(TimeListViewActivity.this);
        setColor = colorSaver.load();
        toolbar.setBackgroundColor(setColor);

        setSupportActionBar(toolbar);  //让toolbar去取代actionbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_frame_layout,timeFragment);
        transaction.hide(timeFragment);
        transaction.add(R.id.main_frame_layout,aboutFragment);
        transaction.hide(aboutFragment);
        transaction.show(timeFragment);
        transaction.commit();

        navigationView.setCheckedItem(R.id.nav_time);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_time:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.hide(timeFragment);
                        transaction.hide(aboutFragment);
                        transaction.show(timeFragment);
                        transaction.commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_about:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.hide(timeFragment);
                        transaction.hide(aboutFragment);
                        transaction.show(aboutFragment);
                        transaction.commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_theme_color:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        dialog = new ColorPickerDialog(TimeListViewActivity.this, " ",
                                new ColorPickerDialog.OnColorChangedListener() {
                                    @Override
                                    public void colorChanged(int color) {
                                        toolbar.setBackgroundColor(color);
                                        timeFragment.setColor(color);
                                        setColor = color;
                                        colorSaver.save(setColor);
                                    }
                                });
                        dialog.show();
                        break;
                    default:
                        Toast.makeText(TimeListViewActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){//菜单图标点击监听
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
