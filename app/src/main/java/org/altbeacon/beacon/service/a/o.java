package org.altbeacon.beacon.service.a;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.ParcelUuid;
import android.support.annotation.MainThread;
import java.util.List;
import org.altbeacon.beacon.b.d;

/* compiled from: CycledLeScannerForLollipop */
final class o extends ScanCallback {
    final /* synthetic */ k a;

    o(k this$0) {
        this.a = this$0;
    }

    @MainThread
    public final void onScanResult(int callbackType, ScanResult scanResult) {
        if (d.a()) {
            d.a("CycledLeScannerForLollipop", "got record", new Object[0]);
            List<ParcelUuid> uuids = scanResult.getScanRecord().getServiceUuids();
            if (uuids != null) {
                for (ParcelUuid uuid : uuids) {
                    d.a("CycledLeScannerForLollipop", "with service uuid: " + uuid, new Object[0]);
                }
            }
        }
        this.a.h.a(scanResult.getDevice(), scanResult.getRssi(), scanResult.getScanRecord().getBytes());
        if (this.a.m > 0) {
            d.a("CycledLeScannerForLollipop", "got a filtered scan result in the background.", new Object[0]);
        }
    }

    @MainThread
    public final void onBatchScanResults(List<ScanResult> results) {
        d.a("CycledLeScannerForLollipop", "got batch records", new Object[0]);
        for (ScanResult scanResult : results) {
            this.a.h.a(scanResult.getDevice(), scanResult.getRssi(), scanResult.getScanRecord().getBytes());
        }
        if (this.a.m > 0) {
            d.a("CycledLeScannerForLollipop", "got a filtered batch scan result in the background.", new Object[0]);
        }
    }

    @MainThread
    public final void onScanFailed(int errorCode) {
        switch (errorCode) {
            case 1:
                d.d("CycledLeScannerForLollipop", "Scan failed: a BLE scan with the same settings is already started by the app", new Object[0]);
                return;
            case 2:
                d.d("CycledLeScannerForLollipop", "Scan failed: app cannot be registered", new Object[0]);
                return;
            case 3:
                d.d("CycledLeScannerForLollipop", "Scan failed: internal error", new Object[0]);
                return;
            case 4:
                d.d("CycledLeScannerForLollipop", "Scan failed: power optimized scan feature is not supported", new Object[0]);
                return;
            default:
                d.d("CycledLeScannerForLollipop", "Scan failed with unknown error (errorCode=" + errorCode + ")", new Object[0]);
                return;
        }
    }
}
