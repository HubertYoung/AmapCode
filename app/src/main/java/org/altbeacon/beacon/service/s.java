package org.altbeacon.beacon.service;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.g;
import org.altbeacon.beacon.j;

/* compiled from: SettingsData */
public class s implements Serializable {
    private static final String g = s.class.getSimpleName();
    ArrayList<j> a;
    Boolean b;
    Boolean c;
    Long d;
    Boolean e;
    Boolean f;

    public final Bundle a() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SettingsData", this);
        return bundle;
    }

    public static s a(@NonNull Bundle bundle) {
        bundle.setClassLoader(Region.class.getClassLoader());
        if (bundle.get("SettingsData") != null) {
            return (s) bundle.getSerializable("SettingsData");
        }
        return null;
    }

    public final void a(@NonNull BeaconService scanService) {
        d.a(g, "Applying settings changes to scanner in other process", new Object[0]);
        g beaconManager = g.a(scanService.a());
        List beaconParsers = beaconManager.d();
        boolean beaconParsersChanged = false;
        if (beaconParsers.size() == this.a.size()) {
            int i = 0;
            while (true) {
                if (i >= beaconParsers.size()) {
                    break;
                } else if (!beaconParsers.get(i).equals(this.a.get(i))) {
                    d.a(g, "Beacon parsers have changed to: " + this.a.get(i).g(), new Object[0]);
                    beaconParsersChanged = true;
                    break;
                } else {
                    i++;
                }
            }
        } else {
            beaconParsersChanged = true;
            d.a(g, "Beacon parsers have been added or removed.", new Object[0]);
        }
        if (beaconParsersChanged) {
            d.a(g, "Updating beacon parsers", new Object[0]);
            beaconManager.d().clear();
            beaconManager.d().addAll(this.a);
            scanService.d();
        } else {
            d.a(g, "Beacon parsers unchanged.", new Object[0]);
        }
        MonitoringStatus monitoringStatus = MonitoringStatus.a(scanService.a());
        if (monitoringStatus.g() && !this.b.booleanValue()) {
            monitoringStatus.e();
        } else if (!monitoringStatus.g() && this.b.booleanValue()) {
            monitoringStatus.f();
        }
        g.b(this.c.booleanValue());
        g.a(this.d.longValue());
        f.a(this.e.booleanValue());
        Beacon.a(this.f.booleanValue());
    }

    public final s a(@NonNull Context context) {
        g beaconManager = g.a(context);
        this.a = new ArrayList<>(beaconManager.d());
        this.b = Boolean.valueOf(beaconManager.n());
        this.c = Boolean.valueOf(g.y());
        this.d = Long.valueOf(g.a());
        this.e = Boolean.valueOf(f.b());
        this.f = Boolean.valueOf(Beacon.a());
        return this;
    }
}
