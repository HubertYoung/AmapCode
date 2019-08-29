package org.altbeacon.beacon.service;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collection;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;

/* compiled from: RangingData */
public final class h {
    private final Collection<Beacon> a;
    private final Region b;

    public h(Collection<Beacon> beacons, Region region) {
        synchronized (beacons) {
            this.a = beacons;
        }
        this.b = region;
    }

    public final Collection<Beacon> a() {
        return this.a;
    }

    public final Region b() {
        return this.b;
    }

    public final Bundle c() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("region", this.b);
        ArrayList serializableBeacons = new ArrayList();
        for (Beacon beacon : this.a) {
            serializableBeacons.add(beacon);
        }
        bundle.putSerializable("beacons", serializableBeacons);
        return bundle;
    }

    public static h a(Bundle bundle) {
        bundle.setClassLoader(Region.class.getClassLoader());
        Region region = null;
        Collection beacons = null;
        if (bundle.get("beacons") != null) {
            beacons = (Collection) bundle.getSerializable("beacons");
        }
        if (bundle.get("region") != null) {
            region = (Region) bundle.getSerializable("region");
        }
        return new h(beacons, region);
    }
}
