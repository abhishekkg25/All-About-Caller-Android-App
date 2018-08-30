package com.automata.masterabhig.allaboutcaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by MASTER ABHIG on 22-05-2018.
 */

public class RecievedCall extends BroadcastReceiver {
    Firebase url;
    String PhoneNumber,name;
    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            Firebase.setAndroidContext(context);
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)) ;
            {
                PhoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                url = new Firebase("https://master-abhig1.firebaseio.com/");
                url.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        name = dataSnapshot.child(PhoneNumber).child("SIM details").child("Name").getValue(String.class);
                        Toast.makeText(context, "Phone of " + name, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            /*    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

                WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
                        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSPARENT);

                params.height = WindowManager.LayoutParams.MATCH_PARENT;
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.format = PixelFormat.TRANSLUCENT;

                params.gravity = Gravity.TOP;

                LinearLayout ly = new LinearLayout(context);
                ly.setBackgroundColor(Color.RED);
                ly.setOrientation(LinearLayout.VERTICAL);

                wm.addView(ly, params); */
            }
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){
              PhoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                url = new Firebase("https://master-abhig1.firebaseio.com/");
                url.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        name = dataSnapshot.child(PhoneNumber).child("SIM details").child("Name").getValue(String.class);
                        Toast.makeText(context, "Phone of " + name, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
             /*   WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

                WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
                        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSPARENT);

                params.height = WindowManager.LayoutParams.MATCH_PARENT;
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.format = PixelFormat.TRANSLUCENT;

                params.gravity = Gravity.TOP;

                LinearLayout ly = new LinearLayout(context);
                ly.setBackgroundColor(Color.RED);
                ly.setOrientation(LinearLayout.VERTICAL);

                wm.addView(ly, params); */
            }

        }
        catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
