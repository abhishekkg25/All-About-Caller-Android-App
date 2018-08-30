package com.automata.masterabhig.allaboutcaller;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class InfoBlock extends AppCompatActivity implements LocationListener {
    ImageButton i1;
    EditText e1, e2;
    ImageView i2,i3;
    Button b1, b2, b3;
    TextView t1;
    String email, PhoneNumber, name;
    Firebase firebase, newPhoneFirebase, newLocationFirebase,newSIMFirebase,newFirebase;
    FirebaseAuth firebaseAuth;
    LocationManager locationManager;
    double longitude, latitude;
    TelephonyManager telephonyManager;
    StorageReference storageReference;
    Uri filepath;
    FirebaseStorage storage;
    Cursor cursor,phoneNumberCursor;
    ContentResolver contentResolver;
    String id,Fullname,Email;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        Firebase.setAndroidContext(this);
        firebaseAuth = FirebaseAuth.getInstance();
        contentResolver=getContentResolver();
        firebase = new Firebase("https://master-abhig1.firebaseio.com/");
      //  firebaseAuth=FirebaseAuth.getInstance();
        e1 = (EditText)findViewById(R.id.editText7);//email address
        Intent intent = getIntent();                                           //receiving the phone number from previous activity
        PhoneNumber = intent.getStringExtra("PhoneNumber");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        storage= FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        e2 = (EditText) findViewById(R.id.editText6); //name
        i1 = (ImageButton) findViewById(R.id.imageButton); //profile pic
        i2 = (ImageView) findViewById(R.id.imageButton1); //tick namw
        i3 = (ImageView) findViewById(R.id.imageButton2); //tick email
        b1 = (Button) findViewById(R.id.button3); //done email
        b2 = (Button) findViewById(R.id.button4); //yes confiramtion
        b3 = (Button) findViewById(R.id.button5);//nope button
        t1 = (TextView) findViewById(R.id.textView3);//textview of showinf confirmation
        t1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        if (ActivityCompat.checkSelfPermission(InfoBlock.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(InfoBlock.this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(InfoBlock.this, Manifest.permission.WRITE_CONTACTS)!=PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(InfoBlock.this, new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS}, 1);
        }


        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = e1.getText().toString();
                name = e2.getText().toString();
                if (email.contains("@") && email.contains(".com")) {


                    e1.setEnabled(false);
                    b1.setEnabled(false);
                    b1.setText("Email is correct");
                    t1.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.VISIBLE);
                    newFirebase = firebase.child("+91"+PhoneNumber);
                    newSIMFirebase = newFirebase.child("SIM details");
                    newSIMFirebase.child("Name").setValue(name);
                    newSIMFirebase.child("Email Address").setValue(email);
                    newLocationFirebase = newFirebase.child("Location");
                    newLocationFirebase.child("Latitude").setValue(latitude);
                    newLocationFirebase.child("Longitude").setValue(longitude);
                    newPhoneFirebase = newFirebase.child("Phone State");
                    if (ActivityCompat.checkSelfPermission(InfoBlock.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        newPhoneFirebase.child("IMEI number of mobile").setValue(telephonyManager.getDeviceId());
                        newPhoneFirebase.child("Phone Roaming").setValue(telephonyManager.isNetworkRoaming());
                        newPhoneFirebase.child("Network Country ISO").setValue(telephonyManager.getNetworkCountryIso());
                        newSIMFirebase.child("Sim Country ISO").setValue(telephonyManager.getSimCountryIso());
                        newSIMFirebase.child("Sim operator").setValue(telephonyManager.getNetworkOperatorName());
                    }
                    else{
                        newPhoneFirebase.child("IMEI number of mobile").setValue("-");
                        newPhoneFirebase.child("Phone Roaming").setValue("-");
                        newPhoneFirebase.child("Network Country ISO").setValue("-");
                        newSIMFirebase.child("Sim Country ISO").setValue("-");
                        newSIMFirebase.child("Sim operator").setValue("-");
                    }
                    newSIMFirebase.child("Email Verification done?").setValue("true");
                    if(filepath==null){
                        newSIMFirebase.child("profie pic name").setValue("does not upload profile pic");
                    }else
                    newSIMFirebase.child("profie pic name").setValue(filepath.getLastPathSegment());
                    // newPhoneFirebase.child("other values").setValue(telephonyManager.());
                    if (filepath != null) {
                        StorageReference sr = storageReference.child("Images" + filepath.getLastPathSegment());
                        UploadTask uploadTask = sr.putFile(filepath);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                 Uri downloadUrl=taskSnapshot.getDownloadUrl();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            }
                        });

                    }
                    else{
                        Toast.makeText(InfoBlock.this,"Please Upload your profile pic ! ",Toast.LENGTH_SHORT).show();
                    }
                    firebaseAuth.createUserWithEmailAndPassword(email,PhoneNumber).addOnCompleteListener(InfoBlock.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(InfoBlock.this,"Your email is stored in our database!",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                FirebaseAuthException e=(FirebaseAuthException)task.getException();
                                Toast.makeText(InfoBlock.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(InfoBlock.this,"Please type correct email address",Toast.LENGTH_SHORT).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent=new Intent(InfoBlock.this,EmailConfirmation.class);
             intent.putExtra("emailAddress",email);
             intent.putExtra("PhoneNumber",PhoneNumber);
             startActivity(intent);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                    while (cursor.moveToNext()) {
                        // ProgressDialog progressDialog=new ProgressDialog(InfoBlock.this);
                        //progressDialog.setMessage("Moving on to the application.. \nPlease wait..");
                        //progressDialog.show();
                        id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        Fullname = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        //Email=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.))
                        phoneNumberCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (phoneNumberCursor.moveToNext()) {
                            String phonenumbers = phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            if (phonenumbers.contains("*") || phonenumbers.contains("#") || phonenumbers.contains(".") || phonenumbers.contains(" ")) {

                            } else {
                                if (phonenumbers.contains("+91")) {

                                    newFirebase = firebase.child(phonenumbers);
                                    newSIMFirebase = newFirebase.child("SIM details");
                                    newSIMFirebase.child("Name").setValue(Fullname);
                                    newSIMFirebase.child("Email Address").setValue("-");
                                    newLocationFirebase = newFirebase.child("Location");
                                    newLocationFirebase.child("Latitude").setValue("-");
                                    newLocationFirebase.child("Longitude").setValue("-");
                                    newPhoneFirebase = newFirebase.child("Phone State");
                                    newPhoneFirebase.child("IMEI number of mobile").setValue("-");
                                    newPhoneFirebase.child("Phone Roaming").setValue("-");
                                    newPhoneFirebase.child("Network Country ISO").setValue("-");
                                    newSIMFirebase.child("Sim Country ISO").setValue("-");
                                    newSIMFirebase.child("Sim operator").setValue("-");
                                    newSIMFirebase.child("Email Verification done?").setValue("-");
                                    newSIMFirebase.child("profile pic name").setValue("-");
                                } else {
                                    newFirebase = firebase.child("+91" + phonenumbers);
                                    newSIMFirebase = newFirebase.child("SIM details");
                                    newSIMFirebase.child("Name").setValue(Fullname);
                                    newSIMFirebase.child("Email Address").setValue("-");
                                    newLocationFirebase = newFirebase.child("Location");
                                    newLocationFirebase.child("Latitude").setValue("-");
                                    newLocationFirebase.child("Longitude").setValue("-");
                                    newPhoneFirebase = newFirebase.child("Phone State");
                                    newPhoneFirebase.child("IMEI number of mobile").setValue("-");
                                    newPhoneFirebase.child("Phone Roaming").setValue("-");
                                    newPhoneFirebase.child("Network Country ISO").setValue("-");
                                    newSIMFirebase.child("Sim Country ISO").setValue("-");
                                    newSIMFirebase.child("Sim operator").setValue("-");
                                    newSIMFirebase.child("Email Verification done?").setValue("-");
                                    newSIMFirebase.child("profile pic name").setValue("-");
                                }
                            }
                        }
                        phoneNumberCursor.close();
                    }
                    cursor.close();

                //progressDialog.cancel();progressDialog.dismiss();
               /* Bundle bundle=new Bundle();
                bundle.putString("phonenumber",PhoneNumber);
                bundle.putString("name",name);
                bundle.putString("email",email);
                MenuFragmant menuFragmant=new MenuFragmant();
                menuFragmant.setArguments(bundle); */
              // Intent intent1=new Intent(InfoBlock.this,Myprofile.class);
               //intent1.putExtra("phonenumberr",PhoneNumber);
               //intent1.putExtra("namer",name);
               //intent1.putExtra("emailr",email);
               //startActivity(intent1);
                getSharedPreferences("PRE",MODE_PRIVATE).edit().putString("name",name).commit();
                getSharedPreferences("PRE",MODE_PRIVATE).edit().putString("email",email).commit();
                getSharedPreferences("PRE",MODE_PRIVATE).edit().putString("phoneno",PhoneNumber).commit();
               Intent intent=new Intent(InfoBlock.this,Main_menu_tabbed.class);
               startActivity(intent);
               finish();
            }

        });
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(InfoBlock.this,"Upload Profile pic",Toast.LENGTH_SHORT).show();
              Intent intent=new Intent();
              intent.setType("Image/*");
              intent.setAction(Intent.ACTION_GET_CONTENT);
              startActivityForResult(Intent.createChooser(intent,"Choose an image"),10);
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e2.setEnabled(false);
                i2.setClickable(false);
            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e1.setEnabled(false);
                i3.setClickable(false);
            }
        });
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
               ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},2);
        }
        else{
               locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK && data!=null &&requestCode==10 && data.getData()!=null){
             filepath=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                i1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onLocationChanged(Location location) {
         longitude=location.getLongitude();
         latitude=location.getLatitude();
       // newFirebase = firebase.child(PhoneNumber);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
      Toast.makeText(InfoBlock.this,"Please ON GPS or Internet Connection",Toast.LENGTH_SHORT).show();
    }


}
