package com.automata.masterabhig.allaboutcaller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class InfoAboutApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_about_app);
        TextView textView =(TextView)findViewById(R.id.textView6);
        String data="This Application gives you all the information of the unknown number caller or you can search manually " +
                "in search section.The application will give \n1.Name of the caller\n2.Email address of the caller(you can contact through mail also " +
                "if you are not willing to call him/her)\n" +
                "3.Current Location of the caller(in latitude & longitude)\n4.IMEI number of the user's phone\n" +
                "5.Operator details\n\n\n\nGive us feedback and suggestions at: \nabhigupta.master@gmail.com";
        textView.setText(data);
    }
}
