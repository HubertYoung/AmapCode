package com.autonavi.map.core;

import android.app.Activity;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.impl.Locator.LocationPreference;
import com.autonavi.common.impl.Locator.Provider;
import com.autonavi.map.fragmentcontainer.page.IPage;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.sdk.location.LocationInstrument;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class LocationMode {
    public static final boolean DEFAULT_LOCATE = true;

    public interface LocationGpsAndNetwork {
    }

    public interface LocationGpsOnly {
    }

    public interface LocationIgnore {
    }

    public interface LocationNavi {
    }

    public interface LocationNetworkOnly {
    }

    public interface LocationNone {
    }

    static class StopLocation implements LocationNone {
        private StopLocation() {
        }
    }

    public static void stopLocation() {
        design(new StopLocation());
    }

    public static void design(Object obj) {
        Class cls;
        if (obj != null) {
            if (LocationIgnore.class.isInstance(obj)) {
                epp.a().a(0);
            } else if (IPage.class.isInstance(obj) || !Transparent.class.isInstance(obj)) {
                if (LocationNone.class.isInstance(obj)) {
                    LocationInstrument.getInstance().setProvider(new Provider[0]);
                    cls = LocationNone.class;
                    epp.a().a(0);
                } else if (LocationGpsOnly.class.isInstance(obj)) {
                    LocationInstrument.getInstance().setProvider(Provider.PROVIDER_GPS);
                    cls = LocationGpsOnly.class;
                    epp.a().a(3);
                } else if (LocationNetworkOnly.class.isInstance(obj)) {
                    LocationInstrument.getInstance().setProvider(Provider.PROVIDER_NETWORK);
                    cls = LocationNetworkOnly.class;
                    epp.a().a(1);
                } else if (LocationGpsAndNetwork.class.isInstance(obj)) {
                    LocationInstrument.getInstance().setProvider(Provider.PROVIDER_GPS, Provider.PROVIDER_NETWORK);
                    cls = LocationGpsAndNetwork.class;
                    epp.a().a(1);
                } else if (LocationNavi.class.isInstance(obj)) {
                    if (ept.b) {
                        LocationInstrument.getInstance().setProvider(Provider.PROVIDER_GPS, Provider.PROVIDER_NETWORK_COARSE);
                    } else {
                        LocationInstrument.getInstance().setProvider(Provider.PROVIDER_GPS);
                    }
                    cls = LocationNavi.class;
                    epp.a().a(3);
                } else {
                    LocationInstrument.getInstance().setProvider(Provider.PROVIDER_GPS, Provider.PROVIDER_NETWORK);
                    cls = LocationGpsAndNetwork.class;
                    epp.a().a(3);
                }
                StringBuilder sb = new StringBuilder("page: ");
                sb.append(obj.getClass().getSimpleName());
                sb.append(", locationMode: ");
                sb.append(cls.getSimpleName());
                AMapLog.d("LocationMode", sb.toString());
                Activity activity = null;
                if (obj instanceof IPage) {
                    activity = (Activity) ((IPage) obj).getContext();
                }
                if (activity != null && (activity instanceof brr) && ((brr) activity).i()) {
                    LocationPreference locationPreference = (LocationPreference) obj.getClass().getAnnotation(LocationPreference.class);
                    if (locationPreference == null || !locationPreference.availableOnBackground()) {
                        LocationInstrument.getInstance().doStopLocate();
                    }
                }
            }
        }
    }
}
