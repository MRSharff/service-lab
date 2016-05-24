package com.msharff.servicelab;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REBOOT = 3;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},
                    MY_PERMISSIONS_REBOOT);
        }

        mSharedPreferences =
                getSharedPreferences(getString(R.string.SHARED_PREFS), Context.MODE_PRIVATE);
//        final SharedPreferences.Editor editor = mSharedPreferences.edit();

//        Button startButton = (Button) findViewById(R.id.start_button);
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RSSService.setServiceAlarm(v.getContext(), true);
//                editor.putBoolean(getString(R.string.ON), true);
//                editor.commit();
//
//            }
//        });
//
//        Button stopButton = (Button) findViewById(R.id.stop_button);
//        stopButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RSSService.setServiceAlarm(v.getContext(), false);
//                editor.putBoolean(getString(R.string.ON), false);
//                editor.commit();
//
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void startService() {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        RSSService.setServiceAlarm(this, true);
        editor.putBoolean(getString(R.string.ON), true);
        editor.commit();
    }

    private void stopService() {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        RSSService.setServiceAlarm(this, false);
        editor.putBoolean(getString(R.string.ON), false);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String result = getIntent().getStringExtra(RSSService.FEED);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadData(result, "text/html", null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_start_service:
                startService();
                return true;
            case R.id.action_stop_service:
                stopService();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
