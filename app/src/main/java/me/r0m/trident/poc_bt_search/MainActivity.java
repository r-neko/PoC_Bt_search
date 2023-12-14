package me.r0m.trident.poc_bt_search;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import me.r0m.trident.poc_bt_search.BtBroadcastReceiver;

public class MainActivity extends AppCompatActivity {

    public static Context getContextOfApplication() {
        return getContextOfApplication();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Init Broadcast Receiver

        BtBroadcastReceiver btBroadcastReceiver = new BtBroadcastReceiver();


        // Intent Filter

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(btBroadcastReceiver, intentFilter);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            Log.d("BT", "No permission to scan for bluetooth devices");
            return;
        }


        if (BluetoothAdapter.getDefaultAdapter().isDiscovering()) {
            // Discovery is already in progress
        } else {
            Log.d("BT", "Bluetooth is not discovering. May not be enabled.");
        }
    }
}