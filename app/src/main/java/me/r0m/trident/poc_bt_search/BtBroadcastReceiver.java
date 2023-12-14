package me.r0m.trident.poc_bt_search;


import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class BtBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Context applicationContext = MainActivity.getContextOfApplication(); // Get context of application
            if (ActivityCompat.checkSelfPermission(applicationContext, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Log.d("BT", "No permission to connect to bluetooth device");
                return;
            }
            if (device.getName() != null) {
                Log.d("BT", "Found device: " + device.getName() + " " + device.getAddress());
            } else {
                Log.d("BT", "Found device: " + device.getAddress() + "(Unknown name)");
            }
        }
    }
}
