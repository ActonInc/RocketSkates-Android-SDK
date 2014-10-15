package com.acton.r.sdk.samples.remotecontrol;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.acton.r.sdk.Skate;
import com.acton.r.sdk.SkateControl;
import com.acton.r.sdk.SkateScanListener;
import com.acton.r.sdk.SkateScanner;
import com.acton.r.sdk.SkateStateListener;
import com.zerokol.views.JoystickView;

public class MainActivity extends Activity {
    private static final String TAG = "RemoteControl";
    
    private JoystickView joystick;

    private Skate connectedSkate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        joystick = (JoystickView) findViewById(R.id.joystick);
        joystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(int angle, int power) {
                if (connectedSkate == null || !connectedSkate.isConnected()) {
                    return;
                }
                
                Log.i(TAG, String.format("Remote input: angle=%d, power=%d", angle, power));
                connectedSkate.setAngle(angle);
                connectedSkate.setSpeed(power);
            }
        });
        
        SkateScanner.get().setScanListener(new SkateScanListener() {
            @Override
            public void onScanStarted() {
                Log.i(TAG, "Scan started");
            }

            @Override
            public void onSkateDiscovered(Skate skate) {
                Log.i(TAG, String.format("Skate discovered: %s (%s)",
                    skate.getName(), skate.getAddress()));
            }

            @Override
            public void onScanEnded(final List<Skate> skateList) {
                Log.i(TAG, String.format(
                    "Scan finished, %d pairs of skates discovered",
                    skateList.size()));
                
                if (skateList.size() == 1) {
                    connectToSkate(skateList.get(0));
                }

                String[] skateNames = new String[skateList.size()];
                for (int i = 0; i < skateList.size(); i++) {
                    skateNames[i] = skateList.get(i).getName();
                }
                
                if (skateList.size() > 0) {
                    new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Select a skate to connect")
                    .setItems(skateNames,
                        new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            connectToSkate(skateList.get(which));
                        }
                    })
                    .create()
                    .show();
                }
            }

            @Override
            public void onError() {
                Log.i(TAG, "An error occurred while scanning for skates");
            }
        });
        
        SkateScanner.get().startScan();
    }

    private void connectToSkate(final Skate skate) {
        Log.i(TAG,
            String.format("Connecting to skate '%s'...", skate.getName()));
        skate.setSkateStateListener(new SkateStateListener() {
            @Override
            public void onSkateConnected() {
                connectedSkate = skate;
                joystick.setVisibility(View.VISIBLE);
                skate.setControl(SkateControl.REMOTE);
                Log.i(TAG, "Skate connected");
            }

            @Override
            public void onSkateDisconnected() {
                joystick.setVisibility(View.INVISIBLE);
                Log.i(TAG, "Skate disconnected");
            }

            @Override
            public void onSkateDataSent() {
            }

            @Override
            public void onSkateDataReceived() {
            }
        });
        skate.connect(getApplicationContext());
    }
}
