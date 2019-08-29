package org.altbeacon.beacon.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.MainThread;
import java.lang.ref.WeakReference;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.b.d;

/* compiled from: BeaconService */
final class a extends Handler {
    private final WeakReference<BeaconService> a;

    a(BeaconService service) {
        super(Looper.getMainLooper());
        this.a = new WeakReference<>(service);
    }

    @MainThread
    public final void handleMessage(Message msg) {
        BeaconService service = (BeaconService) this.a.get();
        if (service != null) {
            StartRMData startRMData = StartRMData.a(msg.getData());
            if (startRMData != null) {
                switch (msg.what) {
                    case 2:
                        d.b("BeaconService", "start ranging received", new Object[0]);
                        Region c = startRMData.c();
                        startRMData.d();
                        service.a(c, new b());
                        service.a(startRMData.a(), startRMData.b(), startRMData.e());
                        return;
                    case 3:
                        d.b("BeaconService", "stop ranging received", new Object[0]);
                        service.a(startRMData.c());
                        service.a(startRMData.a(), startRMData.b(), startRMData.e());
                        return;
                    case 4:
                        d.b("BeaconService", "start monitoring received", new Object[0]);
                        Region c2 = startRMData.c();
                        startRMData.d();
                        service.b(c2, new b());
                        service.a(startRMData.a(), startRMData.b(), startRMData.e());
                        return;
                    case 5:
                        d.b("BeaconService", "stop monitoring received", new Object[0]);
                        service.b(startRMData.c());
                        service.a(startRMData.a(), startRMData.b(), startRMData.e());
                        return;
                    case 6:
                        d.b("BeaconService", "set scan intervals received", new Object[0]);
                        service.a(startRMData.a(), startRMData.b(), startRMData.e());
                        return;
                    default:
                        super.handleMessage(msg);
                        return;
                }
            } else if (msg.what == 7) {
                d.b("BeaconService", "Received settings update from other process", new Object[0]);
                s settingsData = s.a(msg.getData());
                if (settingsData != null) {
                    settingsData.a(service);
                } else {
                    d.c("BeaconService", "Settings data missing", new Object[0]);
                }
            } else {
                d.b("BeaconService", "Received unknown message from other process : " + msg.what, new Object[0]);
            }
        }
    }
}
