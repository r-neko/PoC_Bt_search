package me.r0m.trident.poc_bt_search;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

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


        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Log.d("BT", "Device does not support Bluetooth");
            return;
        } else {
            Log.d("BT", "Device supports Bluetooth");

            // Start discovering devices

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                Log.d("BT", "No permission to scan bluetooth devices");
                return;
            }
            else {
                Log.d("BT", "Permission to scan bluetooth devices granted");
            }
            if (bluetoothAdapter.isDiscovering()) {
                Log.d("BT", "Already discovering devices");
                bluetoothAdapter.cancelDiscovery();
            } else {
                Log.d("BT", "Start discovering devices");
                bluetoothAdapter.startDiscovery();
            }
        }

    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d("BT", "Received broadcast");
            String action = intent.getAction();

            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                Log.d("BT", "No permission to connect to bluetooth device");
                return;
            }
            String deviceName = device.getName();
            String deviceHardwareAddress = device.getAddress(); // MAC address

            if (deviceName != null) {
                Log.d("BT", "Found device: " + deviceName + " " + deviceHardwareAddress);
            }
            else {
                Log.d("BT", "Found device: " + deviceHardwareAddress);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver); //レシーバー破棄

    }

}

