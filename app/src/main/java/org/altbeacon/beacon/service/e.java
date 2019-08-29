package org.altbeacon.beacon.service;

import android.os.Bundle;
import org.altbeacon.beacon.Region;

/* compiled from: MonitoringData */
public final class e {
    private final boolean a;
    private final Region b;

    public e(boolean inside, Region region) {
        this.a = inside;
        this.b = region;
    }

    public final boolean a() {
        return this.a;
    }

    public final Region b() {
        return this.b;
    }

    public final Bundle c() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("region", this.b);
        bundle.putBoolean("inside", this.a);
        return bundle;
    }

    public static e a(Bundle bundle) {
        bundle.setClassLoader(Region.class.getClassLoader());
        Region region = null;
        if (bundle.get("region") != null) {
            region = (Region) bundle.getSerializable("region");
        }
        return new e(Boolean.valueOf(bundle.getBoolean("inside")).booleanValue(), region);
    }
}
