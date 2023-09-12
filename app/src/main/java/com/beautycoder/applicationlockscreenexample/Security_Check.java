package com.beautycoder.applicationlockscreenexample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Security_Check extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor myedit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security__check);
        sp=getSharedPreferences("mypref",MODE_PRIVATE);
        myedit=sp.edit();
        if(sp.getString("flag","no").equals("yes"))
        {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);

            //Toast.makeText(getApplicationContext(),"Welcome!!!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String imei=getIMEIDeviceId(getApplicationContext());
            System.out.println("kitty"+imei);
            if(true) //imei.equals("99040ad5f61dc610")
            {
                myedit.putString("flag","yes");
                myedit.commit();
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                //Toast.makeText(getApplicationContext(),"Welcome!!",Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
            else
            {
                //Toast.makeText(getApplicationContext(),"Illegal Download"+imei,Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

    public static String getIMEIDeviceId(Context context) {

        String deviceId;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return "";
                }
            }
            assert mTelephony != null;
            if (mTelephony.getDeviceId() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    deviceId = mTelephony.getImei();
                } else {
                    deviceId = mTelephony.getDeviceId();
                }
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        Log.d("deviceId", deviceId);
        return deviceId;
    }
}