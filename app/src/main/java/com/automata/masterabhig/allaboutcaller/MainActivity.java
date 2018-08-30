package com.automata.masterabhig.allaboutcaller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ImageView i1,i2,i3;
    TextView t1;
    Button b1;
    EditText e1,e2;
    ProgressBar p1,p2;
    LinearLayout l1,l2;
    Spinner s1;
    FirebaseAuth firebaseAuth;
    String phoneNumber,verificationCode;
    PhoneAuthProvider.ForceResendingToken token;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    int flag=0;
    Firebase url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Firebase.setAndroidContext(this);
        url = new Firebase("https://master-abhig1.firebaseio.com/");
        firebaseAuth = FirebaseAuth.getInstance();
        t1 = (TextView) findViewById(R.id.textView1);//information text
        i1 = (ImageView) findViewById(R.id.imageView);//call image
        i2 = (ImageView) findViewById(R.id.imageView2);//lock image
        i3 = (ImageView) findViewById(R.id.imageView3);//my app image
        b1 = (Button) findViewById(R.id.button);//code verification button
        e1 = (EditText) findViewById(R.id.editText);//phone number
        e2 = (EditText) findViewById(R.id.editText2);//code
        p1 = (ProgressBar) findViewById(R.id.progressBar1);//phone number progress
        p2 = (ProgressBar) findViewById(R.id.progressBar2);//verification code progress
        //s1=(Spinner)findViewById(R.id.spinner);//spinner of country code
        l1 = (LinearLayout) findViewById(R.id.linearLayout);//phone number linear layout
        l2 = (LinearLayout) findViewById(R.id.linearLayout2);//code linear layout
        p1.setVisibility(View.INVISIBLE);
        p2.setVisibility(View.INVISIBLE);
        e2.setEnabled(false);
        //ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,PhoneCodes.countryNcodes);
        //s1.setAdapter(arrayAdapter);
        if (isNetworkAvailable() == true) {
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (e1.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    }
                   else{
                    if (flag == 0) {
                        p1.setVisibility(View.VISIBLE);
                        phoneNumber = e1.getText().toString();
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                phoneNumber, //phone number
                                120,  //time of expiration of code
                                TimeUnit.SECONDS, //time unit
                                MainActivity.this, //which activity
                                mcallbacks
                        );
                    } else {

                        String code = e2.getText().toString();
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationCode, code);
                        signInWithPhoneAuthCredential(phoneAuthCredential);

                    }
                }
                }
            });
            mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(MainActivity.this, "There is an error in the code!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    flag = 1;
                    verificationCode = s;
                    token = forceResendingToken;
                    e1.setEnabled(false);
                    p1.setVisibility(View.INVISIBLE);
                    e2.setEnabled(true);
                    p2.setVisibility(View.VISIBLE);
                    b1.setText("Proceed");
                    t1.setText("Enter Verification code");
                }

            };
        }
        else{
            Toast.makeText(MainActivity.this,"Please Check your internet connection",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnected();
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = task.getResult().getUser();
                            Intent intent=new Intent(MainActivity.this,InfoBlock.class);
                            intent.putExtra("PhoneNumber",phoneNumber);
                            startActivity(intent);
                            finish(); //end of this page or not to go back again with back button
                         //  Firebase firebase=url.child(phoneNumber);
                          // firebase.setValue("hello");
                                                      // ...
                        } else {
                            Toast.makeText(MainActivity.this,"Error in the code",Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}
