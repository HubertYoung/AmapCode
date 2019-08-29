package defpackage;

import android.os.Message;
import android.view.View;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import com.autonavi.minimap.drive.route.VoiceTrafficABFragment;
import com.autonavi.minimap.drive.route.VoiceTrafficABFragment.a;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/* renamed from: die reason: default package */
/* compiled from: VoiceTrafficABPresenter */
public final class die extends sv<VoiceTrafficABFragment, did> {
    public final void onNewIntent(PageBundle pageBundle) {
    }

    public die(VoiceTrafficABFragment voiceTrafficABFragment) {
        super(voiceTrafficABFragment);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        VoiceTrafficABFragment voiceTrafficABFragment = (VoiceTrafficABFragment) this.mPage;
        View contentView = voiceTrafficABFragment.getContentView();
        bty mapView = voiceTrafficABFragment.getMapManager() == null ? null : voiceTrafficABFragment.getMapManager().getMapView();
        if (mapView != null) {
            voiceTrafficABFragment.n = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            voiceTrafficABFragment.m = mapView.w();
            voiceTrafficABFragment.l = mapView.J();
        }
        voiceTrafficABFragment.g = contentView.findViewById(R.id.lLayout_traffic_left);
        voiceTrafficABFragment.h = contentView.findViewById(R.id.lLayout_traffic_mid);
        voiceTrafficABFragment.i = contentView.findViewById(R.id.lLayout_traffic_right);
        contentView.setVisibility(8);
        voiceTrafficABFragment.getMapCustomizeManager().disableView(2241);
        PageBundle arguments = voiceTrafficABFragment.getArguments();
        if (arguments.containsKey("traffic_nodes")) {
            Object object = arguments.getObject("traffic_nodes");
            if (object != null) {
                voiceTrafficABFragment.f = (ArrayList) object;
            }
        }
        voiceTrafficABFragment.j = new a(voiceTrafficABFragment);
        voiceTrafficABFragment.d = arguments.getString("traffic_navi_data");
        voiceTrafficABFragment.b = (POI) arguments.getObject("start_poi");
        voiceTrafficABFragment.c = (POI) arguments.getObject("end_poi");
        voiceTrafficABFragment.q = contentView;
        voiceTrafficABFragment.e = DriveUtil.parseBase64NaviData(voiceTrafficABFragment.d, voiceTrafficABFragment.b, voiceTrafficABFragment.c);
        Message obtain = Message.obtain();
        obtain.what = 0;
        obtain.obj = voiceTrafficABFragment.q;
        voiceTrafficABFragment.j.sendMessage(obtain);
        voiceTrafficABFragment.k = bim.aa().k((String) "103");
    }

    public final void onStart() {
        super.onStart();
        VoiceTrafficABFragment voiceTrafficABFragment = (VoiceTrafficABFragment) this.mPage;
        VoiceTrafficABFragment.a();
        voiceTrafficABFragment.b();
        try {
            if (voiceTrafficABFragment.getMapManager() != null) {
                awo awo = (awo) a.a.a(awo.class);
                if (awo != null) {
                    awo.b(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (voiceTrafficABFragment.o != null) {
            voiceTrafficABFragment.o.b();
            voiceTrafficABFragment.o.a(voiceTrafficABFragment.p);
        }
    }

    public final void onStop() {
        super.onStop();
        VoiceTrafficABFragment voiceTrafficABFragment = (VoiceTrafficABFragment) this.mPage;
        try {
            if (voiceTrafficABFragment.getMapManager() != null) {
                awo awo = (awo) a.a.a(awo.class);
                if (awo != null) {
                    awo.b(voiceTrafficABFragment.k);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (voiceTrafficABFragment.o != null) {
            voiceTrafficABFragment.p = voiceTrafficABFragment.o.c();
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        VoiceTrafficABFragment voiceTrafficABFragment = (VoiceTrafficABFragment) this.mPage;
        bty mapView = voiceTrafficABFragment.getMapManager() == null ? null : voiceTrafficABFragment.getMapManager().getMapView();
        if (mapView != null) {
            mapView.a(voiceTrafficABFragment.n.x, voiceTrafficABFragment.n.y);
            mapView.e(voiceTrafficABFragment.m);
            mapView.g(voiceTrafficABFragment.l);
        }
        if (voiceTrafficABFragment.e != null) {
            tn a = tn.a();
            CalcRouteResult calcRouteResult = voiceTrafficABFragment.e.getCalcRouteResult();
            if (calcRouteResult != null) {
                for (Entry next : a.a.entrySet()) {
                    if (((CalcRouteScene) next.getKey()).isMultiRouteCachePlan()) {
                        List list = (List) next.getValue();
                        if (list != null && !list.isEmpty() && list.contains(calcRouteResult)) {
                            a.b(calcRouteResult);
                            list.remove(calcRouteResult);
                            tn.a(calcRouteResult);
                        }
                    }
                }
            }
        }
    }

    public final boolean onLineOverlayClick(long j) {
        super.onLineOverlayClick(j);
        VoiceTrafficABFragment voiceTrafficABFragment = (VoiceTrafficABFragment) this.mPage;
        voiceTrafficABFragment.e.getFocusRouteIndex();
        int a = voiceTrafficABFragment.a.a(j);
        if (a < 0) {
            return false;
        }
        voiceTrafficABFragment.e.setFocusRouteIndex(a);
        voiceTrafficABFragment.a.a();
        voiceTrafficABFragment.a.a(new dhj());
        switch (a) {
            case 0:
                voiceTrafficABFragment.c();
                break;
            case 1:
                voiceTrafficABFragment.d();
                break;
            case 2:
                voiceTrafficABFragment.e();
                break;
        }
        return true;
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
    }

    public final void onMapSurfaceCreated() {
        super.onMapSurfaceCreated();
        VoiceTrafficABFragment.a();
    }

    public final /* synthetic */ st a() {
        return new did(this);
    }
}
