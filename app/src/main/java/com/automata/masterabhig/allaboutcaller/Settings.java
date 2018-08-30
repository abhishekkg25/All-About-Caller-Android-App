package com.automata.masterabhig.allaboutcaller;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        t1=(TextView)findViewById(R.id.textView9); //location
        t2=(TextView)findViewById(R.id.textView10); //storage
        t3=(TextView)findViewById(R.id.textView12); //contact
        t4=(TextView)findViewById(R.id.textView14); //telephone
        t5=(TextView)findViewById(R.id.textView15); //call log
        t6=(TextView)findViewById(R.id.textView16);

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            t1.setTextColor(Color.parseColor("#ff0000"));
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ) {
            t4.setTextColor(Color.parseColor("#ff0000"));
        }
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
            t3.setTextColor(Color.parseColor("#ff0000"));

        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            t5.setTextColor(Color.parseColor("#ff0000"));
        }
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t1.getCurrentTextColor()==Color.parseColor("#ff0000")){
                    ActivityCompat.requestPermissions(Settings.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    finish();
                }
                else{
                    t1.setTextColor(Color.parseColor("#ff0000"));
                    Toast.makeText(Settings.this,"Permission is off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t2.getCurrentTextColor()==Color.parseColor("#ff0000")){
                    ActivityCompat.requestPermissions(Settings.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    finish();
                }
                else{
                    t2.setTextColor(Color.parseColor("#ff0000"));
                    Toast.makeText(Settings.this,"Permission is off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t2.getCurrentTextColor()==Color.parseColor("#ff0000")){
                    ActivityCompat.requestPermissions(Settings.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
                    finish();
                }
                else{
                    t2.setTextColor(Color.parseColor("#ff0000"));
                    Toast.makeText(Settings.this,"Permission is off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t2.getCurrentTextColor()==Color.parseColor("#ff0000")){
                    ActivityCompat.requestPermissions(Settings.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                    finish();
                }
                else{
                    t2.setTextColor(Color.parseColor("#ff0000"));
                    Toast.makeText(Settings.this,"Permission is off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t5.setTextColor(Color.parseColor("#ff0000"));
            }
        });
    }
}
