package com.xiaomi.push.mpcd.job;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build.VERSION;
import com.xiaomi.channel.commonutils.misc.c;
import java.util.Set;

public class d extends f {
    public d(Context context, int i) {
        super(context, i);
    }

    public int a() {
        return 6;
    }

    public String b() {
        StringBuilder sb = new StringBuilder();
        try {
            Set<BluetoothDevice> bondedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
            if (!c.a(bondedDevices)) {
                int i = 0;
                for (BluetoothDevice next : bondedDevices) {
                    if (i > 10) {
                        break;
                    }
                    if (sb.length() > 0) {
                        sb.append(";");
                    }
                    sb.append(next.getName());
                    sb.append(",");
                    sb.append(next.getAddress());
                    sb.append(",");
                    if (VERSION.SDK_INT >= 18) {
                        sb.append(next.getType());
                    }
                    i++;
                }
            }
        } catch (Throwable unused) {
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public boolean c() {
        return this.d.getPackageManager().checkPermission("android.permission.BLUETOOTH", this.d.getPackageName()) == 0;
    }

    public com.xiaomi.xmpush.thrift.d d() {
        return com.xiaomi.xmpush.thrift.d.Bluetooth;
    }
}
