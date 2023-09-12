package com.beautycoder.applicationlockscreenexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ScreenReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("contom"+intent.getAction());
        Toast.makeText(context,"haiii",Toast.LENGTH_LONG).show();
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}