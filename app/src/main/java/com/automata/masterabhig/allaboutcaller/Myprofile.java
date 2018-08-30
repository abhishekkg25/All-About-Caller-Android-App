package com.automata.masterabhig.allaboutcaller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Myprofile extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5;
    ImageView i1,i2,i3;
    Button b1,b2,b3;
    String phonumber,name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        getSupportActionBar().hide();
        Intent intent=getIntent();
        phonumber=intent.getStringExtra("phonenumberr");
        name=intent.getStringExtra("namer");
        email=intent.getStringExtra("emailr");
        e1=(EditText)findViewById(R.id.editText25); //phonenumber
        e2=(EditText)findViewById(R.id.editText26); //name
        e3=(EditText)findViewById(R.id.editText27);  //email
        e4=(EditText)findViewById(R.id.editText28);  //phoneconfirm
        e5=(EditText)findViewById(R.id.editText29);  //emailconfirm
        i1=(ImageView)findViewById(R.id.imageView25); //editname
        i2=(ImageView)findViewById(R.id.imageView26); //editemail
        i3=(ImageView)findViewById(R.id.imageView10); //profilepic
        b1=(Button)findViewById(R.id.button15); //back
        b2=(Button)findViewById(R.id.button16); //confirphone?
        b3=(Button)findViewById(R.id.button17); //confirmemail?
        e1.setEnabled(false);
        e2.setEnabled(false);
        e3.setEnabled(false);
        e4.setEnabled(false);
        e5.setEnabled(false);
        b2.setEnabled(false);
        e1.setText(phonumber);
        e2.setText(name);
        e3.setText(email);
        e1.setText(getSharedPreferences("PRE",MODE_PRIVATE).getString("phoneno","**NOT REGISTERED**"));
        e2.setText(getSharedPreferences("PRE",MODE_PRIVATE).getString("name","**NOT REGISTERED**"));
        e3.setText(getSharedPreferences("PRE",MODE_PRIVATE).getString("email","**NOT REGISTERED**"));
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e2.setEnabled(true);
                b1.setText("Save");
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e3.setEnabled(true);
                b1.setText("Save");
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
