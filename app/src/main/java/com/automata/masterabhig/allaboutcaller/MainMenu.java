package com.automata.masterabhig.allaboutcaller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Thread thread=new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }
                catch (InterruptedException e){
                    Toast.makeText(MainMenu.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                finally {
                    Boolean isFirstRun=getSharedPreferences("PREFERENCE",MODE_PRIVATE).getBoolean("isFirstRun",true);
                    if(isFirstRun) {
                        Intent intent = new Intent(MainMenu.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(MainMenu.this, Main_menu_tabbed.class);
                        startActivity(intent);
                        finish();
                    }
                    getSharedPreferences("PREFERENCE",MODE_PRIVATE).edit().putBoolean("isFirstRun",false).commit();
                }
            }
        };
        thread.start();
    }
}
