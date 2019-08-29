package org.altbeacon.beacon.service.a;

import android.annotation.TargetApi;
import android.content.Context;
import org.altbeacon.bluetooth.BluetoothCrashResolver;

@TargetApi(26)
/* compiled from: CycledLeScannerForAndroidO */
class e extends k {
    private static final String k = e.class.getSimpleName();

    e(Context context, boolean backgroundFlag, a cycledLeScanCallback, BluetoothCrashResolver crashResolver) {
        super(context, backgroundFlag, cycledLeScanCallback, crashResolver);
    }
}
