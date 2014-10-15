package com.acton.r.sdk.samples.hellorskates;

import java.util.List;

import com.acton.r.sdk.Skate;
import com.acton.r.sdk.SkateScanListener;
import com.acton.r.sdk.SkateScanner;
import com.acton.r.sdk.SkateStateListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String TAG = "HelloRSkate";
    
    private TextView statusText;
    private Button scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        statusText = (TextView) findViewById(R.id.status_text);
        scanButton = (Button) findViewById(R.id.scan_button);
        
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkateScanner.get().startScan();
            }
        });
        
        SkateScanner.get().setScanListener(new SkateScanListener() {
            @Override
            public void onSkateDiscovered(Skate skate) {
                log(String.format("Skate discovered: %s (%s)", skate.getName(), skate.getAddress()));
            }
            
            @Override
            public void onScanStarted() {
                log("Scan started");
            }
            
            @Override
            public void onScanEnded(List<Skate> skateList) {
                log(String.format("Scan finished, %d pairs of skates discovered", skateList.size()));
                
                if (skateList.size() > 0) {
                    connectToSkate(skateList.get(0));
                }
            }
            
            @Override
            public void onError() {
                log("An error occurred while scanning for skates");
            }
        });
    }
    
    private void connectToSkate(Skate skate) {
        log(String.format("Connecting to skate '%s'...", skate.getName()));
        skate.setSkateStateListener(new SkateStateListener() {
            @Override
            public void onSkateConnected() {
                log("Skate connected");
            }
            
            @Override
            public void onSkateDisconnected() {
                log("Skate disconnected");
            }
            
            @Override
            public void onSkateDataSent() {
            }
            
            @Override
            public void onSkateDataReceived() {
            }
        });
    }
    
    private void log(String s) {
        Log.i(TAG, s);
        statusText.append(s + "\n");
    }
}
