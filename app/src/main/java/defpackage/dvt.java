package defpackage;

import android.content.Context;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.ExTrainPath;
import com.autonavi.bundle.routecommon.entity.ExtBusPath;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.model.ExTaxiPath;
import java.util.ArrayList;
import java.util.Arrays;

/* renamed from: dvt reason: default package */
/* compiled from: BusStationDesItemFactory */
public final class dvt {
    private static dvs a(POI poi) {
        if (poi == null) {
            return null;
        }
        String name = poi.getName();
        dvs dvs = new dvs();
        dvs.a = name;
        dvs.b = name;
        dvs.d = name;
        dvs.A = 2;
        dvs.m = new int[]{poi.getPoint().x};
        dvs.n = new int[]{poi.getPoint().y};
        return dvs;
    }

    private static dvs b(POI poi) {
        if (poi == null) {
            return null;
        }
        String name = poi.getName();
        dvs dvs = new dvs();
        dvs.a = name;
        dvs.d = name;
        dvs.b = name;
        dvs.A = 3;
        dvs.m = new int[]{poi.getPoint().x};
        dvs.n = new int[]{poi.getPoint().y};
        return dvs;
    }

    private static dvs a(ExTrainPath exTrainPath) {
        if (exTrainPath == null) {
            return null;
        }
        dvs dvs = new dvs();
        dvs.A = 5;
        dvs.b = exTrainPath.sst;
        dvs.d = exTrainPath.tst;
        dvs.B = exTrainPath;
        dvs.m = exTrainPath.mXs;
        dvs.n = exTrainPath.mYs;
        return dvs;
    }

    private static dvs a(int i) {
        dvs dvs = new dvs();
        dvs.A = 17;
        dvs.H = i;
        return dvs;
    }

    private static dvs a(ExTaxiPath exTaxiPath, int i) {
        dvs dvs = new dvs();
        dvs.A = 17;
        dvs.H = i;
        dvs.x = true;
        dvs.v = exTaxiPath.length;
        dvs.w = exTaxiPath.time;
        dvs.a = "";
        dvs.b = exTaxiPath.mStartName;
        dvs.d = exTaxiPath.mEndName;
        dvs.m = new int[]{exTaxiPath.startX, exTaxiPath.endX};
        dvs.n = new int[]{exTaxiPath.startY, exTaxiPath.endY};
        dvs.r = exTaxiPath.startX;
        dvs.s = exTaxiPath.startY;
        dvs.t = exTaxiPath.endX;
        dvs.u = exTaxiPath.endY;
        dvs.q = exTaxiPath.cost;
        return dvs;
    }

    public static ArrayList<dvs> a(Context context, IBusRouteResult iBusRouteResult, ExtBusPath extBusPath) {
        dvs dvs;
        if (context == null || iBusRouteResult == null || extBusPath == null || iBusRouteResult.getFromPOI() == null || iBusRouteResult.getToPOI() == null) {
            return null;
        }
        dvw dvw = new dvw();
        ArrayList<dvs> arrayList = new ArrayList<>();
        dvs a = a(iBusRouteResult.getFromPOI());
        int i = dvw.b;
        dvw.b = i + 1;
        a.o = i;
        arrayList.add(a);
        ArrayList<axb> busPathList = extBusPath.getBusPathList();
        int size = busPathList.size();
        ExTaxiPath exTaxiPath = null;
        int i2 = 0;
        while (true) {
            int i3 = 1;
            if (i2 >= size) {
                break;
            }
            axb axb = busPathList.get(i2);
            if (axb instanceof BusPath) {
                a(context, iBusRouteResult, (BusPath) axb, arrayList, i2, dvw);
            } else if (axb instanceof ExTrainPath) {
                if (!arrayList.isEmpty()) {
                    dvs dvs2 = arrayList.get(arrayList.size() - 1);
                    if (!(dvs2.A == 0 || dvs2.A == 12)) {
                        if (dvs2.A != 5) {
                            i3 = 2;
                        }
                        if (exTaxiPath != null) {
                            dvs = a(exTaxiPath, i3);
                        } else {
                            dvs = a(i3);
                        }
                        int i4 = dvw.b;
                        dvw.b = i4 + 1;
                        dvs.o = i4;
                        arrayList.add(dvs);
                    }
                }
                dvs a2 = a((ExTrainPath) axb);
                int i5 = dvw.b;
                dvw.b = i5 + 1;
                a2.o = i5;
                a2.j = i2;
                arrayList.add(a2);
            } else if (axb instanceof ExTaxiPath) {
                exTaxiPath = (ExTaxiPath) axb;
            }
            i2++;
        }
        if (arrayList.get(arrayList.size() - 1).A == 5) {
            if (exTaxiPath != null) {
                dvs a3 = a(exTaxiPath, 3);
                int i6 = dvw.b;
                dvw.b = i6 + 1;
                a3.o = i6;
                arrayList.add(a3);
            } else {
                dvs a4 = a(3);
                int i7 = dvw.b;
                dvw.b = i7 + 1;
                a4.o = i7;
                arrayList.add(a4);
            }
        }
        dvs b = b(iBusRouteResult.getToPOI());
        int i8 = dvw.b;
        dvw.b = i8 + 1;
        b.o = i8;
        arrayList.add(b);
        return arrayList;
    }

    private static void a(Context context, IBusRouteResult iBusRouteResult, BusPath busPath, ArrayList<dvs> arrayList, int i, dvw dvw) {
        dvs dvs;
        dvs dvs2;
        if (context != null && iBusRouteResult != null && busPath != null) {
            int i2 = busPath.mSectionNum;
            if (i2 > 0) {
                String name = iBusRouteResult.getFromPOI().getName();
                int i3 = 0;
                while (true) {
                    dvs = null;
                    if (i3 >= i2) {
                        break;
                    }
                    BusPathSection busPathSection = busPath.mPathSections[i3];
                    if (busPathSection.mFootLength > 0) {
                        if (busPathSection == null) {
                            dvs2 = null;
                        } else {
                            dvs2 = new dvs();
                            dvs2.d = busPathSection.mStartName;
                            dvs2.k = busPathSection;
                            dvs2.h = busPathSection.mFootLength;
                            dvs2.i = busPathSection.foot_time;
                            dvs2.A = 0;
                            dvs2.y = false;
                            if (!(busPathSection.walk_path == null || busPathSection.walk_path.infolist == null || busPathSection.walk_path.infolist.size() <= 0)) {
                                dvs2.m = busPathSection.walk_path.infolist.get(0).mXs;
                                dvs2.n = busPathSection.walk_path.infolist.get(0).mYs;
                            }
                        }
                        dvs2.b = name;
                        int i4 = dvw.b;
                        dvw.b = i4 + 1;
                        dvs2.o = i4;
                        dvs2.j = i;
                        arrayList.add(dvs2);
                    }
                    if (!(context == null || busPathSection == null)) {
                        dvs = new dvs();
                        dvs.k = busPathSection;
                        dvs.b = busPathSection.mStartName;
                        dvs.d = busPathSection.mEndName;
                        dvs.a = busPathSection.mSectionName;
                        dvs.m = busPathSection.mXs;
                        dvs.n = busPathSection.mYs;
                        dvs.A = 6;
                        if (busPathSection.mStations != null) {
                            dvs.g = busPathSection.mStations.length;
                            if (busPathSection.mStations.length - 2 > 0) {
                                dvs.l = new ArrayList<>();
                                dvs.l.addAll(Arrays.asList(busPathSection.mStations).subList(1, busPathSection.mStations.length - 1));
                            }
                        }
                        if (busPathSection.mBusType > 0) {
                            dvs.z = busPathSection.mBusType;
                        } else if (dvs.a.contains(context.getString(R.string.route_subway))) {
                            dvs.z = 2;
                        } else {
                            dvs.z = 1;
                        }
                        if (BusPath.isSubway(dvs.z)) {
                            if (busPathSection.subway_inport != null) {
                                dvs.c = BusPathSection.dealSubWayPortName(busPathSection.subway_inport, false);
                            }
                            if (busPathSection.subway_outport != null) {
                                dvs.e = BusPathSection.dealSubWayPortName(busPathSection.subway_outport, true);
                                dvs.f = busPathSection.subway_outport.subwayName;
                            }
                        }
                    }
                    int i5 = dvw.b;
                    dvw.b = i5 + 1;
                    dvs.o = i5;
                    dvs.j = i;
                    dvs.F = i;
                    arrayList.add(dvs);
                    name = busPathSection.mEndName;
                    i3++;
                }
                if (busPath.mEndWalkLength > 0) {
                    POI toPOI = iBusRouteResult.getToPOI();
                    if (!(toPOI == null || busPath == null || busPath.mEndWalkLength <= 0)) {
                        String name2 = toPOI.getName();
                        dvs = new dvs();
                        dvs.h = busPath.mEndWalkLength;
                        dvs.i = busPath.endfoottime;
                        dvs.A = 0;
                        dvs.a = name2;
                        dvs.d = name2;
                        dvs.b = name2;
                        dvs.y = true;
                        if (busPath.endwalk == null || busPath.endwalk.infolist == null || busPath.endwalk.infolist.size() <= 0) {
                            dvs.m = new int[]{toPOI.getPoint().x};
                            dvs.n = new int[]{toPOI.getPoint().y};
                        } else {
                            dvs.m = busPath.endwalk.infolist.get(0).mXs;
                            dvs.n = busPath.endwalk.infolist.get(0).mYs;
                        }
                    }
                    dvs.j = i;
                    int i6 = dvw.b;
                    dvw.b = i6 + 1;
                    dvs.o = i6;
                    dvs.C = true;
                    arrayList.add(dvs);
                }
            }
        }
    }
}
