package com.autonavi.minimap.route.bus.localbus.uitl;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.common.Callback;
import com.autonavi.minimap.route.bus.model.Bus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public final class AlterBusesManager {
    private BusPath a;

    public class AlterListResponser implements Callback<dvy> {
        private int mItemIndex;
        private a mListener;
        private int mSectionIndex;
        private ArrayList<BusPathSection> mSections;

        public AlterListResponser(ArrayList<BusPathSection> arrayList, int i, int i2, a aVar) {
            this.mListener = aVar;
            this.mSections = arrayList;
            this.mSectionIndex = i;
            this.mItemIndex = i2;
        }

        public void error(Throwable th, boolean z) {
            if (this.mListener != null) {
                this.mListener.a();
            }
        }

        public void callback(dvy dvy) {
            if (this.mListener != null) {
                this.mListener.b();
            }
            if (dvy.errorCode == 1) {
                Bus bus = dvy.a;
                if (bus != null) {
                    int i = this.mItemIndex;
                    ArrayList<BusPathSection> arrayList = this.mSections;
                    if (i >= 0 && i < arrayList.size()) {
                        BusPathSection busPathSection = arrayList.get(i);
                        if (busPathSection != null) {
                            busPathSection.mXs = bus.coordX;
                            busPathSection.mYs = bus.coordY;
                            for (int i2 = 0; i2 < busPathSection.mStations.length; i2++) {
                                busPathSection.mStations[i2].mX = bus.stationX[i2];
                                busPathSection.mStations[i2].mY = bus.stationY[i2];
                                busPathSection.mStations[i2].mName = bus.stations[i2];
                                busPathSection.mStations[i2].mStationdirection = bus.stationdirection[i2];
                            }
                            busPathSection.mEta = bus.eta;
                            busPathSection.intervalDesc = bus.interval;
                            busPathSection.mRouteColor = bus.color;
                            busPathSection.irregulartime = bus.irregulartime;
                            busPathSection.isNeedRequest = false;
                            boolean a2 = AlterBusesManager.this.a(busPathSection, this.mSectionIndex);
                            if (dvy.b != null) {
                                busPathSection.mBusData = Arrays.copyOf(dvy.b, dvy.b.length);
                            }
                            if (this.mListener != null) {
                                new HashMap().put(Integer.valueOf(this.mSectionIndex), busPathSection.bus_id);
                                this.mListener.a(a2);
                            }
                        }
                    }
                    dvy.b = null;
                }
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(dvy.errorMessage);
            ToastHelper.showLongToast(sb.toString());
        }
    }

    public interface a {
        void a();

        void a(boolean z);

        void b();
    }

    public AlterBusesManager(BusPath busPath) {
        this.a = busPath;
    }

    public final boolean a(BusPathSection busPathSection, int i) {
        BusPathSection busPathSection2;
        if (busPathSection == null || this.a == null || this.a.mPathSections == null || this.a.mPathSections.length <= 0) {
            return false;
        }
        BusPathSection busPathSection3 = this.a.mPathSections[i];
        if (busPathSection.bus_id.equals(busPathSection3.bus_id) || busPathSection3.alter_list == null) {
            return false;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= busPathSection3.alter_list.length) {
                busPathSection2 = null;
                i2 = -1;
                break;
            } else if (busPathSection.bus_id.equals(busPathSection3.alter_list[i2].bus_id)) {
                busPathSection2 = busPathSection;
                break;
            } else {
                i2++;
            }
        }
        if (i2 == -1) {
            return false;
        }
        busPathSection2.mStations = busPathSection.mStations;
        busPathSection2.alter_list = busPathSection3.alter_list;
        busPathSection2.walk_path = busPathSection3.walk_path;
        busPathSection2.tripList = busPathSection3.tripList;
        busPathSection2.isRidePath = busPathSection3.isRidePath;
        this.a.mPathSections[i] = busPathSection2;
        busPathSection3.alter_list = null;
        busPathSection3.walk_path = null;
        busPathSection3.tripList = null;
        busPathSection2.alter_list[i2] = busPathSection3;
        return true;
    }
}
