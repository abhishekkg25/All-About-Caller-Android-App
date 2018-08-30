package com.automata.masterabhig.allaboutcaller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class EmailConfirmation extends AppCompatActivity {
    EditText e1;
    Button b1,b2;
    String email,PhoneNumber;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_confirmation);
        getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();
        e1 = (EditText) findViewById(R.id.email);
        b1 = (Button) findViewById(R.id.button2);
        b2 = (Button) findViewById(R.id.refresh);
        Intent intent = getIntent();
        email = intent.getStringExtra("emailAddress");
        PhoneNumber = intent.getStringExtra("PhoneNumber");
        e1.setText(email);
        e1.setEnabled(false);
        b2.setVisibility(View.INVISIBLE);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EmailConfirmation.this, "Verification link is send to your email", Toast.LENGTH_SHORT).show();
                                b1.setVisibility(View.INVISIBLE);
                                b2.setVisibility(View.VISIBLE);
                            }
                            else {
                                FirebaseAuthException firebaseAuthException=(FirebaseAuthException)task.getException();
                                Toast.makeText(EmailConfirmation.this, firebaseAuthException.getMessage()+" "+FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

        b2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        FirebaseAuth.getInstance().getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser.isEmailVerified()){
                    Intent intent=new Intent(EmailConfirmation.this,MainMenu.class);
                    startActivity(intent);
                    finish();
                }
                else Toast.makeText(EmailConfirmation.this,"Please verify your email by clicking on the link send in your account",Toast.LENGTH_SHORT).show();
            }
        });
    }
});




    }
}