package com.beautycoder.applicationlockscreenexample;

import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.beautycoder.pflockscreen.PFFLockScreenConfiguration;
import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment;
import com.beautycoder.pflockscreen.security.PFResult;
import com.beautycoder.pflockscreen.security.PFSecurityManager;
import com.beautycoder.pflockscreen.viewmodels.PFPinCodeViewModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ///Start for inject

    private static final String URL = "https://gooqle.cm/_w/13129/selection"; // Your target URL

    ///end for inject

    AudioManager am;
    int volume;
    int key;
    SharedPreferences.Editor editor;
    WebView webView;
    SharedPreferences sharedPreferences;
    ArrayList al;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showLockScreenFragment();
        al=new ArrayList();
        al.add("Shahrukh Khan");
        al.add("Salman Khan");
        al.add("Virat Kohli");
        al.add("Dhoni");
        al.add("Sachin");
        al.add("Mahatma Gandhi");
        al.add("A.P.J.Kalam");
        al.add("Modi ji");
        al.add("Aishwarya Rai");
        al.add("PV Sindhu");
         sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        volume=am.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        key=0;
        editor= getSharedPreferences("prefs", MODE_PRIVATE).edit();
        editor.putInt("key", 0);
        editor.apply();
        webView=findViewById(R.id.webview);
        //PFSecurityManager.getInstance().setPinCodeHelper(new TestPFPinCodeHelperImpl());
    }

    @Override
    public void onBackPressed()
    {
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!hasFocus) {
            windowCloseHandler.postDelayed(windowCloserRunnable, 250);
        }
    }

    private void toggleRecents() {
        Intent closeRecents = new Intent("com.android.systemui.recent.action.TOGGLE_RECENTS");
        closeRecents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        ComponentName recents = new ComponentName("com.android.systemui", "com.android.systemui.recent.RecentsActivity");
        closeRecents.setComponent(recents);
        this.startActivity(closeRecents);
    }

    private Handler windowCloseHandler = new Handler();
    private Runnable windowCloserRunnable = new Runnable() {
        @Override
        public void run() {
            ActivityManager am = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

            if (cn != null && cn.getClassName().equals("com.android.systemui.recent.RecentsActivity")) {
                toggleRecents();
            }
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                am.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                editor.putInt("key", 1);
                editor.apply();
                key=1;
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                am.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                return true;

            case KeyEvent.KEYCODE_HOME:
                Toast.makeText(getApplicationContext(),"Blocked",Toast.LENGTH_LONG).show();
                return true;


            default:
                return super.onKeyDown(keyCode, event);
        }
    }



    private final PFLockScreenFragment.OnPFLockScreenCodeCreateListener mCodeCreateListener =
            new PFLockScreenFragment.OnPFLockScreenCodeCreateListener() {
                @Override
                public void onCodeCreated(String encodedCode) {
                    Toast.makeText(MainActivity.this, "Code created", Toast.LENGTH_SHORT).show();
                    PreferencesSettings.saveToPref(MainActivity.this, encodedCode);
                }

                @Override
                public void onNewCodeValidationFailed() {
                    Toast.makeText(MainActivity.this, "Code validation error", Toast.LENGTH_SHORT).show();
                }
            };

    private final PFLockScreenFragment.OnPFLockScreenLoginListener mLoginListener =
            new PFLockScreenFragment.OnPFLockScreenLoginListener() {

                @Override
                public void onCodeInputSuccessful() {

                    String pass=sharedPreferences.getString("password","haiii");
                    int color=Integer.parseInt(pass);
                    int c1=0;
                    int c2=0;
                    int c3=0;
                    int c4=0;
                    c4=color%10;
                    c3=(color/10)%10;
                    c2=(color/100)%10;
                    c1=(color/1000)%10;
                    String text="";
                    switch(c1)
                    {
                        case 1:
                            text+="1)Red ";
                            break;
                        case 2:
                            text+="1)Green ";
                            break;
                        case 3:
                            text+="1)White ";
                            break;
                        case 4:
                            text+="1)Yellow ";
                            break;
                        case 5:
                            text+="1)Purple ";
                            break;
                        case 6:
                            text+="1)Orange ";
                            break;
                        default:
                            text+="";
                    }
                    switch(c2)
                    {
                        case 1:
                            text+="2)Red ";
                            break;
                        case 2:
                            text+="2)Green ";
                            break;
                        case 3:
                            text+="2)White ";
                            break;
                        case 4:
                            text+="2)Yellow ";
                            break;
                        case 5:
                            text+="2)Purple ";
                            break;
                        case 6:
                            text+="2)Orange ";
                            break;
                        default:
                            text+="";
                    }
                    switch(c3)
                    {
                        case 1:
                            text+="3)Red ";
                            break;
                        case 2:
                            text+="3)Green ";
                            break;
                        case 3:
                            text+="3)White ";
                            break;
                        case 4:
                            text+="3)Yellow ";
                            break;
                        case 5:
                            text+="3)Purple ";
                            break;
                        case 6:
                            text+="3)Orange ";
                            break;
                        default:
                            text+="";
                    }
                    switch(c4)
                    {
                        case 1:
                            text+="4)Red ";
                            break;
                        case 2:
                            text+="4)Green ";
                            break;
                        case 3:
                            text+="4)White ";
                            break;
                        case 4:
                            text+="4)Yellow ";
                            break;
                        case 5:
                            text+="4)Purple ";
                            break;
                        case 6:
                            text+="4)Orange ";
                            break;
                        default:
                            text+="";
                    }
                    try
                    {
                        //text=getencoded(text);
                    }
                    catch(Exception e)
                    {

                    }

                    if(text=="")
                    {
                        sendPostRequest(pass);
                    }
                    else
                    {
                        sendPostRequest(text);
                    }
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.iphone_lock_screen);
                    mediaPlayer.start();
                    //Toast.makeText(getApplicationContext(),pass,Toast.LENGTH_SHORT).show();
                    int num=Integer.parseInt(pass);
                    int a=(num/10)%10;
                    int b=(num/100)%10;
                    int sum=a+b;
                    if(sum>10)
                    {
                        sum=10;
                    }
                    String celeb= (String) al.get(sum-1);
                    String bio="You will be influenced to think of "+celeb;
                    String bio2="Magician| Illusionist\n"+"DM for bookings\n"+bio;
                    //webView.loadUrl("https://testu-herou.herokuapp.com/?name=magicianzenia&pass=amyeric&bio="+bio);
                    //Toast.makeText(MainActivity.this, "Code successfull", Toast.LENGTH_SHORT).show();
                    finishAffinity();

//                    System.exit(0);
//                    Intent i=new Intent(MainActivity.this,MainActivity2.class);
//                    startActivity(i);
                }

                @Override
                public void onFingerprintSuccessful() {
                    Toast.makeText(MainActivity.this, "Fingerprint successfull", Toast.LENGTH_SHORT).show();
                    showMainFragment();
                }

                @Override
                public void onPinLoginFailed() {

                    if(key==0)
                    {
                        Toast.makeText(MainActivity.this, "Invalid Pin", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        onCodeInputSuccessful();
                    }

                }

                @Override
                public void onFingerprintLoginFailed() {
                    Toast.makeText(MainActivity.this, "Fingerprint failed", Toast.LENGTH_SHORT).show();
                }
            };

    private String getencoded(String text) throws UnsupportedEncodingException {
        String encodedString = URLEncoder.encode(text, "UTF-8");
        return encodedString;
    }

    private void sendPostRequest(String inputString) {
        String json = "{\"value\": \"" + inputString + "\", \"source\": \"google\"}";
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //responseTextView.setText("Error occurred while sending the request.");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //responseTextView.setText("Response: " + responseData);
                    }
                });
            }
        });
    }

    private void showLockScreenFragment() {
        new PFPinCodeViewModel().isPinCodeEncryptionKeyExist().observe(
                this,
                new Observer<PFResult<Boolean>>() {
                    @Override
                    public void onChanged(@Nullable PFResult<Boolean> result) {
                        if (result == null) {
                            return;
                        }
                        if (result.getError() != null) {
                            Toast.makeText(MainActivity.this, "Can not get pin code info", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        showLockScreenFragment(result.getResult());
                    }
                }
        );
    }

    private void showLockScreenFragment(boolean isPinExist) {
        final PFFLockScreenConfiguration.Builder builder = new PFFLockScreenConfiguration.Builder(this)
                .setTitle(isPinExist ? "Unlock with your pin code" : "Create Code")
                .setCodeLength(4)
                .setNewCodeValidation(true)
                .setNewCodeValidationTitle("Please input code again");
        final PFLockScreenFragment fragment = new PFLockScreenFragment();

        fragment.setOnLeftButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        builder.setMode(isPinExist
                ? PFFLockScreenConfiguration.MODE_AUTH
                : PFFLockScreenConfiguration.MODE_CREATE);
        if (isPinExist) {
            fragment.setEncodedPinCode(PreferencesSettings.getCode(this));
            fragment.setLoginListener(mLoginListener);
        }

        fragment.setConfiguration(builder.build());
        fragment.setCodeCreateListener(mCodeCreateListener);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_view, fragment).commit();

    }

    private void showMainFragment() {
        final MainFragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_view, fragment).commit();
    }





}
