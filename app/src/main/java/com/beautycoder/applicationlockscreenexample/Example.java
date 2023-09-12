package com.beautycoder.applicationlockscreenexample;

import android.accessibilityservice.AccessibilityService;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

class example extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        if (action == KeyEvent.ACTION_UP) {
            if (keyCode == 4 && getSharedPreferences("example",0).getBoolean("Back",false)) {
                return true;
            }
            else if (keyCode == 3 && getSharedPreferences("example",0).getBoolean("Home",false)) {
                return true;
            }
            else if (keyCode==KeyEvent.KEYCODE_APP_SWITCH && getSharedPreferences("example",0).getBoolean("Recent",false)){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return super.onKeyEvent(event);
        }
    }
}
