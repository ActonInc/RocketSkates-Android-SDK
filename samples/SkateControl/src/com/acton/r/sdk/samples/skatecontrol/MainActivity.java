package com.acton.r.sdk.samples.skatecontrol;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acton.r.sdk.Skate;
import com.acton.r.sdk.SkateControl;
import com.acton.r.sdk.SkateID;
import com.acton.r.sdk.SkateMode;
import com.acton.r.sdk.SkateScanListener;
import com.acton.r.sdk.SkateScanner;
import com.acton.r.sdk.SkateStateListener;
import com.acton.r.sdk.SpeedUnit;

public class MainActivity extends Activity {
    private static final String TAG = "SkateControl";

    private Button scanConnectButton;

    private RelativeLayout skateInfoPanel;
    private TextView skateNameText;
    private TextView skateStatusText;
    private Button skateControlButton;
    private Button skateModeButton;

    private Skate connectedSkate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanConnectButton = (Button) findViewById(R.id.scan_connect_button);
        scanConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkateScanner.get().startScan();
            }
        });

        skateInfoPanel = (RelativeLayout) findViewById(R.id.skate_info_panel);
        skateNameText = (TextView) findViewById(R.id.skate_name_text);
        skateStatusText = (TextView) findViewById(R.id.skate_status_text);

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
            public void onScanEnded(List<Skate> skateList) {
                Log.i(TAG, String.format(
                    "Scan finished, %d pairs of skates discovered",
                    skateList.size()));

                if (skateList.size() > 0) {
                    connectToSkate(skateList.get(0));
                }
            }

            @Override
            public void onError() {
                Log.i(TAG, "An error occurred while scanning for skates");
            }
        });

        skateControlButton = (Button) findViewById(R.id.skate_control_button);
        skateModeButton = (Button) findViewById(R.id.skate_mode_button);

        skateControlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Select skate control")
                    .setItems(new String[] { "UNLOCK", "LOCK" },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                int which) {
                                switch (which) {
                                    case 0:
                                        connectedSkate
                                            .setControl(SkateControl.UNLOCK);
                                        break;
                                    case 1:
                                        connectedSkate
                                            .setControl(SkateControl.LOCK);
                                        break;
                                }
                            }
                        })
                    .create()
                    .show();
            }
        });

        skateModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Select skate mode")
                    .setItems(
                        new String[] { "BEGINNER", "NORMAL", "PERFORMANCE" },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                int which) {
                                switch (which) {
                                    case 0:
                                        connectedSkate
                                            .setMode(SkateMode.BEGINNER);
                                        break;
                                    case 1:
                                        connectedSkate
                                            .setMode(SkateMode.NORMAL);
                                        break;
                                    case 2:
                                        connectedSkate
                                            .setMode(SkateMode.PERFORMANCE);
                                        break;
                                }
                            }
                        })
                    .create()
                    .show();
            }
        });
    }

    private void connectToSkate(final Skate skate) {
        Log.i(TAG,
            String.format("Connecting to skate '%s'...", skate.getName()));
        skate.setSkateStateListener(new SkateStateListener() {
            @Override
            public void onSkateConnected() {
                connectedSkate = skate;
                skateInfoPanel.setVisibility(View.VISIBLE);
                Log.i(TAG, "Skate connected");
            }

            @Override
            public void onSkateDisconnected() {
                skateInfoPanel.setVisibility(View.INVISIBLE);
                Log.i(TAG, "Skate disconnected");
            }

            @Override
            public void onSkateDataSent() {
            }

            @Override
            public void onSkateDataReceived() {
                updateSkateStatus();
            }
        });
    }

    private void updateSkateStatus() {
        skateNameText.setText("Skate connected: " + connectedSkate.getName());

        String skateStatus = "";
        skateStatus += "Voltage: \t\t"
            + connectedSkate.getVoltage(SkateID.MASTER) + "V / "
            + connectedSkate.getVoltage(SkateID.SLAVE) + "V\n";
        skateStatus += "Drain: \t\t" + connectedSkate.getDrain(SkateID.MASTER)
            + "A / " + connectedSkate.getDrain(SkateID.SLAVE) + "A\n";
        skateStatus += "Battery: \t\t"
            + connectedSkate.getBatteryCapacity(SkateID.MASTER) + "% / "
            + connectedSkate.getBatteryCapacity(SkateID.SLAVE) + "%\n";
        skateStatus += "Battery Temp: \t"
            + connectedSkate.getBatteryTemp(SkateID.MASTER) + "°C / "
            + connectedSkate.getBatteryTemp(SkateID.SLAVE) + "°C\n";
        skateStatus += "RPM: \t\t"
            + connectedSkate.getSpeed(SkateID.MASTER, SpeedUnit.RPM) + " / "
            + connectedSkate.getSpeed(SkateID.SLAVE, SpeedUnit.RPM) + "\n";
        skateStatus += "Weight: \t\t" + connectedSkate.getWeight();

        skateStatusText.setText(skateStatus);
    }
}
