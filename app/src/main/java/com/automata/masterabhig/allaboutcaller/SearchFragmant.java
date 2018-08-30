package com.automata.masterabhig.allaboutcaller;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Locale;

/**
 * Created by MASTER ABHIG on 22-05-2018.
 */

public class SearchFragmant extends Fragment {
    EditText e1,e2,e3,e4,e6,e7,e8,e9;
    ImageView i1,i2;
    ImageView favorite,call,block,share;
    ImageView search;
    Firebase url,second;
    LinearLayout l1;
    String phoneNumber;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    Button e5;
    String latitude,longitude;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.search_fregmant,container,false);
        Firebase.setAndroidContext(getActivity());
        storageReference= FirebaseStorage.getInstance().getReference();
        e1=(EditText)view.findViewById(R.id.getPhoneno); //mobileno
        e2=(EditText)view.findViewById(R.id.Name); //name
        e3=(EditText)view.findViewById(R.id.Email_addr); //email
        e4=(EditText)view.findViewById(R.id.Operator); //operator
        e5=(Button) view.findViewById(R.id.Current_location); //location
        e6=(EditText)view.findViewById(R.id.CSC); //csc
        e7=(EditText)view.findViewById(R.id.ev); //email verification
        e8=(EditText)view.findViewById(R.id.roaming); //roaming
        e9=(EditText)view.findViewById(R.id.imei); //imei

        i1=(ImageView)view.findViewById(R.id.imageView9); //profilepic
        i2=(ImageView)view.findViewById(R.id.imageView8); //Just pic
        call=(ImageView) view.findViewById(R.id.imagebutton6); //call
        share=(ImageView) view.findViewById(R.id.button10);
        block=(ImageView) view.findViewById(R.id.imageButton2);
        favorite=(ImageView) view.findViewById(R.id.button8);
        search=(ImageView)view.findViewById(R.id.search);

        l1=(LinearLayout)view.findViewById(R.id.mainll);

        l1.setEnabled(false);
        i1.setEnabled(false);

        e2.setClickable(false); e2.setEnabled(false);
        e3.setClickable(false); e3.setEnabled(false);
        e4.setClickable(false); e4.setEnabled(false);

        e6.setClickable(false); e6.setEnabled(false);
        e7.setClickable(false); e7.setEnabled(false);
        e8.setClickable(false); e8.setEnabled(false);
        e9.setClickable(false); e9.setEnabled(false);
        l1.setVisibility(View.INVISIBLE);
        i1.setVisibility(View.INVISIBLE);
        share.setVisibility(View.INVISIBLE);
        call.setVisibility(View.INVISIBLE);
        block.setVisibility(View.INVISIBLE);
        favorite.setVisibility(View.INVISIBLE);

        i2.setVisibility(View.VISIBLE);
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Contact added to block list",Toast.LENGTH_SHORT).show();

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Feature is not available yet",Toast.LENGTH_SHORT).show();

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(getActivity());
                progressDialog.setMessage("Connecting to DataBase \nRetrieving Information.. \nPlease Wait..\n\nOnly with first search it takes time");
                progressDialog.setTitle("All About Caller Says");
                progressDialog.show();
                phoneNumber=e1.getText().toString();
                url=new Firebase("https://master-abhig1.firebaseio.com/");
               //second=url.child("+91"+phoneNumber).child("SIM details").child("Name");

                    url.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child("+91" + phoneNumber).exists()) {
                                progressDialog.dismiss();
                                progressDialog.cancel();

                               // Uri filepath =storageReference.child("Images"+dataSnapshot.child("+91"+phoneNumber).child("SIM details").child("profile pic name").getValue());
                                // Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity(),sr.getFile())
                                l1.setVisibility(View.VISIBLE);
                                e2.setText(dataSnapshot.child("+91" + phoneNumber).child("SIM details").child("Name").getValue(String.class));
                                e3.setText(dataSnapshot.child("+91" + phoneNumber).child("SIM details").child("Email Address").getValue(String.class));
                                e4.setText(dataSnapshot.child("+91" + phoneNumber).child("SIM details").child("Sim operator").getValue(String.class));
                                latitude=dataSnapshot.child("+91" + phoneNumber).child("Location").child("Latitude").getValue(String.class);
                                longitude=dataSnapshot.child("+91" + phoneNumber).child("Location").child("Longitude").getValue(String.class);
                              //  e5.setText("Latitude:" + e5.getText() + " Longitude:" + dataSnapshot.child("+91" + phoneNumber).child("Location").child("Latitude").getValue(String.class));
                                if(dataSnapshot.child("+91" + phoneNumber).child("SIM details").child("Sim Country ISO").getValue(String.class).equalsIgnoreCase("In")){
                                    e6.setText("INDIA");
                                }
                                else e6.setText(dataSnapshot.child("+91" + phoneNumber).child("SIM details").child("Sim Country ISO").getValue(String.class));
                                if(dataSnapshot.child("+91" + phoneNumber).child("SIM details").child("Email Verification done?").getValue(String.class).equalsIgnoreCase("true")){
                                    e7.setText("Verified");
                                }
                                else
                                e7.setText("No");
                                if(dataSnapshot.child("+91" + phoneNumber).child("Phone State").child("Phone Roaming").getValue(String.class).equalsIgnoreCase("true")){
                                    e8.setText("Yes");
                                }
                                else
                                e8.setText("No");
                                e9.setText(dataSnapshot.child("+91" + phoneNumber).child("Phone State").child("IMEI number of mobile").getValue(String.class));

                                i1.setVisibility(View.VISIBLE);
                                share.setVisibility(View.VISIBLE);
                                call.setVisibility(View.VISIBLE);
                                block.setVisibility(View.VISIBLE);
                                favorite.setVisibility(View.VISIBLE);
                                i2.setVisibility(View.INVISIBLE);

                            }
                            else{
                                progressDialog.dismiss();
                                progressDialog.cancel();
                                i2.setVisibility(View.VISIBLE);
                                i1.setVisibility(View.INVISIBLE);
                                share.setVisibility(View.INVISIBLE);
                                call.setVisibility(View.INVISIBLE);
                                block.setVisibility(View.INVISIBLE);
                                favorite.setVisibility(View.INVISIBLE);
                                l1.setVisibility(View.INVISIBLE);
                                Toast.makeText(getActivity(),"Current phone number's Information is not available",Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                              Toast.makeText(getActivity(),firebaseError.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
        e5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Opening Google Map",Toast.LENGTH_SHORT).show();
               // String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                String geoUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude ;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });

call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},1);
        }
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + e1.getText().toString().trim()));
            startActivity(intent);
        }
        catch (ActivityNotFoundException e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
});
favorite.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(getActivity(),"Number is added to favorite List",Toast.LENGTH_SHORT).show();
        getActivity().getSharedPreferences("favorite", Context.MODE_PRIVATE).getString("Favorite_contact",String.valueOf(e1.getText()+" "+e2.getText()));
    }
});
        return view;


    }

  /*  @Override
    public void onDestroy() {
        Intent intent=new Intent(getActivity(),MenuFragmant.class);
        startActivity(intent);
        super.onDestroy();
    }
    */
}
