package com.automata.masterabhig.allaboutcaller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by MASTER ABHIG on 22-05-2018.
 */

public class MenuFragmant extends Fragment {
    Button b1,b2,b3,b4,b5;
    String phonenumber,email,name;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.menu_fregmant,container,false);
        b1=(Button)view.findViewById(R.id.button6);
        b2=(Button)view.findViewById(R.id.button18);
        b3=(Button)view.findViewById(R.id.button9);
        b4=(Button)view.findViewById(R.id.button13);
        b5=(Button)view.findViewById(R.id.button14);
        Bundle bundle=this.getArguments();
       /* if(bundle!=null) {
            phonenumber = bundle.getString("phonenumber");
            name = bundle.getString("name");
            email = bundle.getString("email");
        } */
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Myprofile.class);
              // intent.putExtra("phoneNumber",phonenumber);
               // intent.putExtra("Name",name);
                //intent.putExtra("Email",email);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),InfoAboutApp.class);
                startActivity(intent);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Settings.class);
                startActivity(intent);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Favorite.class);
                startActivity(intent);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"remove the app from home button",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
