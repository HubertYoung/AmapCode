package defpackage;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.media.AudioManager;
import java.util.Set;

/* renamed from: tp reason: default package */
/* compiled from: DriveBlutoothUtils */
public final class tp {
    public static boolean a(Activity activity) {
        if (kj.a(activity, new String[]{"android.permission.BLUETOOTH"})) {
            AudioManager audioManager = (AudioManager) activity.getApplicationContext().getSystemService("audio");
            boolean isBluetoothA2dpOn = audioManager.isBluetoothA2dpOn();
            boolean isBluetoothScoOn = audioManager.isBluetoothScoOn();
            if (isBluetoothA2dpOn || isBluetoothScoOn) {
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                if (defaultAdapter != null) {
                    Set<BluetoothDevice> bondedDevices = defaultAdapter.getBondedDevices();
                    if (bondedDevices.size() > 0) {
                        for (BluetoothDevice next : bondedDevices) {
                            if (next != null) {
                                BluetoothClass bluetoothClass = next.getBluetoothClass();
                                if (bluetoothClass != null && bluetoothClass.hasService(2097152)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
