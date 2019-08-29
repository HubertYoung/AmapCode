package defpackage;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.minimap.route.bus.model.Bus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/* renamed from: dwt reason: default package */
/* compiled from: AlterRouteDataProcessor */
public final class dwt {
    public static boolean a(IBusRouteResult iBusRouteResult, dwy dwy) {
        if (iBusRouteResult.getFocusBusPath() == null || iBusRouteResult.getFromPOI() == null || iBusRouteResult.getFromPOI().getPoint() == null || dwy == null) {
            return false;
        }
        BusPath busPathWithIndex = iBusRouteResult.getBusPathWithIndex(dwy.a);
        if (busPathWithIndex == null || busPathWithIndex.mPathSections == null || busPathWithIndex.mPathSections.length <= 0 || dwy.b >= busPathWithIndex.mPathSections.length) {
            return false;
        }
        BusPathSection busPathSection = busPathWithIndex.mPathSections[dwy.b];
        ArrayList arrayList = new ArrayList();
        arrayList.add(busPathSection);
        if (busPathSection.alter_list != null) {
            Collections.addAll(arrayList, busPathSection.alter_list);
        }
        if (arrayList.size() == 0 || dwy.c >= arrayList.size()) {
            return false;
        }
        BusPathSection busPathSection2 = (BusPathSection) arrayList.get(dwy.c);
        if (!TextUtils.isEmpty(dwy.d)) {
            dvy dvy = new dvy();
            try {
                dvy.parser(dwy.d.getBytes());
                a(dvy, busPathSection2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        BusPathSection a = a(busPathSection, busPathSection2);
        if (a == null) {
            return false;
        }
        busPathWithIndex.mPathSections[dwy.b] = a;
        return true;
    }

    private static void a(dvy dvy, BusPathSection busPathSection) {
        if (dvy.errorCode != 1 || dvy.a == null || busPathSection == null) {
            dvy.b = null;
            return;
        }
        Bus bus = dvy.a;
        busPathSection.mXs = bus.coordX;
        busPathSection.mYs = bus.coordY;
        for (int i = 0; i < busPathSection.mStations.length; i++) {
            busPathSection.mStations[i].mX = bus.stationX[i];
            busPathSection.mStations[i].mY = bus.stationY[i];
            busPathSection.mStations[i].mName = bus.stations[i];
            busPathSection.mStations[i].mStationdirection = bus.stationdirection[i];
        }
        busPathSection.mEta = bus.eta;
        busPathSection.intervalDesc = bus.interval;
        busPathSection.mRouteColor = bus.color;
        busPathSection.irregulartime = bus.irregulartime;
        busPathSection.isNeedRequest = false;
        if (dvy.b != null) {
            busPathSection.mBusData = Arrays.copyOf(dvy.b, dvy.b.length);
        }
        dvy.b = null;
    }

    @Nullable
    private static BusPathSection a(BusPathSection busPathSection, BusPathSection busPathSection2) {
        if (busPathSection2 == null || busPathSection == null || TextUtils.equals(busPathSection2.bus_id, busPathSection.bus_id) || busPathSection.alter_list == null) {
            return null;
        }
        int i = 0;
        while (true) {
            if (i >= busPathSection.alter_list.length) {
                busPathSection2 = null;
                i = -1;
                break;
            } else if (TextUtils.equals(busPathSection2.bus_id, busPathSection.alter_list[i].bus_id)) {
                break;
            } else {
                i++;
            }
        }
        if (i == -1) {
            return null;
        }
        busPathSection2.alter_list = busPathSection.alter_list;
        busPathSection2.walk_path = busPathSection.walk_path;
        busPathSection2.tripList = busPathSection.tripList;
        busPathSection2.isRidePath = busPathSection.isRidePath;
        busPathSection.alter_list = null;
        busPathSection.walk_path = null;
        busPathSection.tripList = null;
        busPathSection2.alter_list[i] = busPathSection;
        return busPathSection2;
    }
}
