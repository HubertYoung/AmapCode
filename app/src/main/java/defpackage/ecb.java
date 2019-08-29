package defpackage;

import android.location.GnssStatus;
import android.location.GnssStatus.Callback;
import android.location.GpsStatus.Listener;
import android.os.Build.VERSION;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: ecb reason: default package */
/* compiled from: BaseLocationTool */
public abstract class ecb implements Listener {
    private Callback gnssStatusCallback;
    /* access modifiers changed from: private */
    public int nSatCount = -1;

    /* access modifiers changed from: protected */
    public abstract void onNewSatCount(int i);

    public ecb() {
        if (VERSION.SDK_INT >= 24) {
            this.gnssStatusCallback = new Callback() {
                public final void onSatelliteStatusChanged(GnssStatus gnssStatus) {
                    ecb.this.nSatCount = ecb.this.getSatelliteCountByGNSSStatus(gnssStatus);
                    ecb.this.onNewSatCount(ecb.this.nSatCount);
                }
            };
        }
    }

    public int getSatCount() {
        return this.nSatCount;
    }

    public void setSatCount(int i) {
        this.nSatCount = i;
    }

    /* access modifiers changed from: protected */
    public void onStartLocation() {
        if (VERSION.SDK_INT < 24) {
            LocationInstrument.getInstance().addGpsStatusListener(this);
        } else {
            LocationInstrument.getInstance().registerGnssStatusCallback(this.gnssStatusCallback);
        }
    }

    /* access modifiers changed from: protected */
    public void onStopLocation() {
        if (VERSION.SDK_INT < 24) {
            LocationInstrument.getInstance().removeGpsStatusListener(this);
        } else {
            LocationInstrument.getInstance().unregisterGnssStatusCallback(this.gnssStatusCallback);
        }
    }

    /* access modifiers changed from: private */
    public int getSatelliteCountByGNSSStatus(GnssStatus gnssStatus) {
        if (gnssStatus == null) {
            return 0;
        }
        int[] iArr = new int[7];
        if (VERSION.SDK_INT >= 24) {
            int satelliteCount = gnssStatus.getSatelliteCount();
            for (int i = 0; i < satelliteCount; i++) {
                if (gnssStatus.usedInFix(i)) {
                    switch (gnssStatus.getConstellationType(i)) {
                        case 0:
                            iArr[0] = iArr[0] + 1;
                            break;
                        case 1:
                            iArr[1] = iArr[1] + 1;
                            break;
                        case 2:
                            iArr[2] = iArr[2] + 1;
                            break;
                        case 3:
                            iArr[3] = iArr[3] + 1;
                            break;
                        case 4:
                            iArr[4] = iArr[4] + 1;
                            break;
                        case 5:
                            iArr[5] = iArr[5] + 1;
                            break;
                        case 6:
                            iArr[6] = iArr[6] + 1;
                            break;
                    }
                }
            }
        }
        int i2 = 0;
        for (int i3 = 0; i3 < 7; i3++) {
            if (iArr[i3] > i2) {
                i2 = iArr[i3];
            }
        }
        return i2;
    }
}
