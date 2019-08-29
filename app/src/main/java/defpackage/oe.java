package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.drive.ajx.inter.IRealNaviEventCallback;
import com.amap.bundle.drive.common.speaker.SpeakerPlayManager;
import com.amap.bundle.drive.navi.tools.RealNaviEventManager$1;
import com.amap.bundle.drivecommon.navi.navidata.NavigationDataResult;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.jni.ae.route.route.Route;
import com.autonavi.jni.ae.route.route.RouteSegment;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: oe reason: default package */
/* compiled from: RealNaviEventManager */
public final class oe implements IRealNaviEventCallback {
    private String a = "RealNaviEventManager";
    private boolean b = true;
    private POI c;
    private POI d;
    private ArrayList<POI> e = new ArrayList<>();
    /* access modifiers changed from: private */
    public NavigationDataResult f;
    private sp g;
    private SpeakerPlayManager h;
    private MapManager i;
    private LinkedHashSet<String> j;

    public final void onMainPathIdUpdate(long j2) {
    }

    public oe(NavigationDataResult navigationDataResult, sp spVar, SpeakerPlayManager speakerPlayManager, MapManager mapManager, LinkedHashSet<String> linkedHashSet) {
        this.f = navigationDataResult;
        this.g = spVar;
        this.h = speakerPlayManager;
        this.i = mapManager;
        this.j = linkedHashSet;
    }

    public final void onCalRoute(String str) {
        AMapLog.d(this.a, "onCal---json=".concat(String.valueOf(str)));
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("focusIndex");
            JSONArray optJSONArray = jSONObject.optJSONArray("routeSet");
            int length = optJSONArray.length();
            int[] iArr = new int[length];
            for (int i2 = 0; i2 < length; i2++) {
                iArr[i2] = optJSONArray.getInt(i2);
            }
            long createPathResult = NaviManager.createPathResult(iArr);
            CalcRouteResult calcRouteResult = new CalcRouteResult();
            calcRouteResult.setPtr(createPathResult);
            calcRouteResult.mResultInfo.put("valid", Boolean.TRUE);
            Route route = calcRouteResult.getRoute(optInt);
            String naviID = route.getNaviID();
            if (!TextUtils.isEmpty(naviID)) {
                this.j.add(naviID);
            }
            this.f.setRouteNaviId(route.getNaviID());
            if (this.b) {
                this.b = false;
                ahn.b().execute(new Runnable(calcRouteResult.getRoute(optInt), new RealNaviEventManager$1(this, createPathResult)) {
                    final /* synthetic */ Route a;
                    final /* synthetic */ Callback b;

                    {
                        this.a = r1;
                        this.b = r2;
                    }

                    public final void run() {
                        List<GeoPoint[]> list;
                        Route route = this.a;
                        if (route != null && route.getSegmentCount() > 0) {
                            int segmentCount = route.getSegmentCount();
                            list = new ArrayList<>(route.getSegmentCount());
                            int i = 0;
                            while (true) {
                                if (i >= segmentCount) {
                                    break;
                                }
                                RouteSegment segment = route.getSegment(i);
                                if (segment != null) {
                                    GeoPoint[] a2 = ro.a(segment.getSegCoor());
                                    if (a2 == null || a2.length <= 1) {
                                        break;
                                    }
                                    list.add(a2);
                                    i++;
                                } else {
                                    break;
                                }
                            }
                        }
                        list = null;
                        if (list == null || list.size() <= 0) {
                            this.b.callback(null);
                            return;
                        }
                        ArrayList arrayList = new ArrayList();
                        for (GeoPoint[] geoPointArr : list) {
                            for (GeoPoint add : (GeoPoint[]) r2.next()) {
                                arrayList.add(add);
                            }
                        }
                        this.b.callback(arrayList);
                    }
                });
                return;
            }
            NaviManager.releasePathResult(createPathResult);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void onTravelPointsUpdate(String str) {
        AMapLog.d(this.a, "updateTravelPoints--JSON=".concat(String.valueOf(str)));
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject = jSONObject.optJSONObject(H5PageData.KEY_UC_START);
            JSONObject optJSONObject2 = jSONObject.optJSONObject("end");
            JSONArray optJSONArray = jSONObject.optJSONArray("via");
            this.c = bnx.a(optJSONObject.toString());
            this.d = bnx.a(optJSONObject2.toString());
            ArrayList arrayList = new ArrayList();
            this.e.clear();
            int length = optJSONArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                POI a2 = bnx.a(((JSONObject) optJSONArray.get(i2)).toString());
                this.e.add(a2);
                arrayList.add(a2.getPoint());
            }
            this.f.setFromPOI(this.c);
            this.f.setToPOI(this.d);
            this.f.setMidPOIs(this.e);
            this.f.setShareStartPos(this.c.getPoint());
            this.f.setShareEndPos(this.d.getPoint());
            this.f.setShareMidPos(arrayList);
            this.g.f = this.c;
            this.g.i = this.d;
            this.g.g = this.e;
            a();
            this.f.setOriginMidPOIs(this.g.h);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void onSpeakerChanged(int i2) {
        if (this.h != null) {
            SpeakerPlayManager speakerPlayManager = this.h;
            boolean z = true;
            if (i2 != 1) {
                z = false;
            }
            speakerPlayManager.a(z);
        }
    }

    private void a() {
        if (this.g.h == null) {
            this.g.h = new ArrayList();
        } else {
            this.g.h.clear();
        }
        for (int i2 = 0; i2 < this.g.g.size(); i2++) {
            this.g.h.add(new so(this.g.g.get(i2), i2));
        }
    }

    public final boolean isRealDayNightMode() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        if (latestPosition == null && this.c != null) {
            latestPosition = this.c.getPoint();
        }
        if ((latestPosition == null || latestPosition.x == 0 || latestPosition.y == 0) && this.d != null) {
            latestPosition = this.d.getPoint();
        }
        if (!((latestPosition != null && latestPosition.x != 0 && latestPosition.y != 0) || this.i == null || this.i.getMapView() == null)) {
            latestPosition = this.i.getMapView().o();
        }
        if (latestPosition == null || latestPosition.x == 0 || latestPosition.y == 0) {
            return false;
        }
        DPoint a2 = cfg.a((long) latestPosition.x, (long) latestPosition.y);
        return qz.a(a2.x, a2.y, LocationInstrument.getInstance().getLatestLocation());
    }
}
