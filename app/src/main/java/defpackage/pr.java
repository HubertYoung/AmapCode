package defpackage;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.drive.result.motorresult.event.AjxRouteMotorResultEventDetailPage;
import com.autonavi.common.PageBundle;
import com.autonavi.jni.ae.route.model.ForbiddenLineInfo;
import com.autonavi.map.fragmentcontainer.page.MapBasePresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.favorites.data.RouteItem;

/* renamed from: pr reason: default package */
/* compiled from: AjxRouteMotorResultEventDetailPresenter */
public final class pr extends MapBasePresenter<AjxRouteMotorResultEventDetailPage> {
    public pr(AjxRouteMotorResultEventDetailPage ajxRouteMotorResultEventDetailPage) {
        super(ajxRouteMotorResultEventDetailPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        AjxRouteMotorResultEventDetailPage ajxRouteMotorResultEventDetailPage = (AjxRouteMotorResultEventDetailPage) this.mPage;
        ajxRouteMotorResultEventDetailPage.n = ajxRouteMotorResultEventDetailPage.getMapView().j(false);
        ajxRouteMotorResultEventDetailPage.o = ajxRouteMotorResultEventDetailPage.getMapView().k(false);
        ajxRouteMotorResultEventDetailPage.q = ajxRouteMotorResultEventDetailPage.getMapView().s();
        ajxRouteMotorResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_detail_close).setOnClickListener(ajxRouteMotorResultEventDetailPage);
        ajxRouteMotorResultEventDetailPage.m = (ViewGroup) ajxRouteMotorResultEventDetailPage.getContentView().findViewById(R.id.mapBottomInteractiveView);
        ajxRouteMotorResultEventDetailPage.m.setVisibility(8);
        ajxRouteMotorResultEventDetailPage.a = (ViewGroup) ajxRouteMotorResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_forbid_layout);
        ajxRouteMotorResultEventDetailPage.a.setVisibility(8);
        ajxRouteMotorResultEventDetailPage.c = (TextView) ajxRouteMotorResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_forbid_time);
        ajxRouteMotorResultEventDetailPage.d = (TextView) ajxRouteMotorResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_forbid_truck_type);
        ajxRouteMotorResultEventDetailPage.b = (TextView) ajxRouteMotorResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_detail_title);
        ajxRouteMotorResultEventDetailPage.e = (TextView) ajxRouteMotorResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_detail_info);
        ajxRouteMotorResultEventDetailPage.f = (ImageView) ajxRouteMotorResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_detail_icon);
        ajxRouteMotorResultEventDetailPage.m.addOnLayoutChangeListener(ajxRouteMotorResultEventDetailPage.G);
        PageBundle arguments = ajxRouteMotorResultEventDetailPage.getArguments();
        if (arguments != null) {
            ajxRouteMotorResultEventDetailPage.g = arguments.getInt("data_type");
            ajxRouteMotorResultEventDetailPage.i = arguments.getInt("event_id");
            ajxRouteMotorResultEventDetailPage.j = arguments.getInt("focusIndex");
            ajxRouteMotorResultEventDetailPage.k = arguments.getLongArray("result_id");
            ajxRouteMotorResultEventDetailPage.h = (ph) arguments.getObject(RouteItem.ROUTE_DATA);
            ajxRouteMotorResultEventDetailPage.t = new ForbiddenLineInfo();
            ajxRouteMotorResultEventDetailPage.u = arguments.getInt("forbiddenId");
            ajxRouteMotorResultEventDetailPage.v = arguments.getInt("forbiddenType");
            ajxRouteMotorResultEventDetailPage.w = arguments.getInt("vehicleType");
            ajxRouteMotorResultEventDetailPage.x = arguments.getString("timeDescription");
            ajxRouteMotorResultEventDetailPage.y = arguments.getString("roadNameString");
            ajxRouteMotorResultEventDetailPage.z = arguments.getString("nextRoadNameString");
            ajxRouteMotorResultEventDetailPage.E = arguments.getString("detail_weather");
            ajxRouteMotorResultEventDetailPage.A = arguments.getDouble("opanlayer_lon");
            ajxRouteMotorResultEventDetailPage.B = arguments.getDouble("opanlayer_lat");
            ajxRouteMotorResultEventDetailPage.C = arguments.getInt("opanlayer_z");
            ajxRouteMotorResultEventDetailPage.D = arguments.getString("opanlayer_poiId");
            ajxRouteMotorResultEventDetailPage.t.pathId = (long) ajxRouteMotorResultEventDetailPage.u;
            ajxRouteMotorResultEventDetailPage.t.forbiddenType = (byte) ajxRouteMotorResultEventDetailPage.v;
            ajxRouteMotorResultEventDetailPage.t.forbiddenTime = ajxRouteMotorResultEventDetailPage.x;
            ajxRouteMotorResultEventDetailPage.t.carType = (byte) ajxRouteMotorResultEventDetailPage.w;
            ajxRouteMotorResultEventDetailPage.t.roadName = ajxRouteMotorResultEventDetailPage.y;
            ajxRouteMotorResultEventDetailPage.t.nextRoadName = ajxRouteMotorResultEventDetailPage.z;
        }
    }

    public final void onStart() {
        super.onStart();
        AjxRouteMotorResultEventDetailPage ajxRouteMotorResultEventDetailPage = (AjxRouteMotorResultEventDetailPage) this.mPage;
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            int i = ajxRouteMotorResultEventDetailPage.g;
            if (!(i == 8 || i == 10)) {
                awo.b(9001);
            } else {
                awo.c(9001);
            }
        }
        ajxRouteMotorResultEventDetailPage.getMapView().a(0, ajxRouteMotorResultEventDetailPage.getMapView().l(false), 1);
        ajxRouteMotorResultEventDetailPage.getMapView().b(false);
    }

    public final void onResume() {
        super.onResume();
        ((AjxRouteMotorResultEventDetailPage) this.mPage).requestScreenOrientation(1);
        ((AjxRouteMotorResultEventDetailPage) this.mPage).a();
    }

    public final void onPause() {
        super.onPause();
        AjxRouteMotorResultEventDetailPage ajxRouteMotorResultEventDetailPage = (AjxRouteMotorResultEventDetailPage) this.mPage;
        if (ajxRouteMotorResultEventDetailPage.r != null) {
            alv alv = new alv();
            alv.a = 9001;
            alv.b = ajxRouteMotorResultEventDetailPage.r.a;
            alv.c = ajxRouteMotorResultEventDetailPage.r.b;
            alv.d = 1;
            ajxRouteMotorResultEventDetailPage.getGLMapView().a(alv);
        }
    }

    public final void onStop() {
        super.onStop();
        AjxRouteMotorResultEventDetailPage ajxRouteMotorResultEventDetailPage = (AjxRouteMotorResultEventDetailPage) this.mPage;
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.c(9001);
        }
        bty mapView = ajxRouteMotorResultEventDetailPage.getMapView();
        if (mapView != null) {
            mapView.a(ajxRouteMotorResultEventDetailPage.n, ajxRouteMotorResultEventDetailPage.getMapView().l(false), ajxRouteMotorResultEventDetailPage.o);
            mapView.b(ajxRouteMotorResultEventDetailPage.q);
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        AjxRouteMotorResultEventDetailPage ajxRouteMotorResultEventDetailPage = (AjxRouteMotorResultEventDetailPage) this.mPage;
        if (ajxRouteMotorResultEventDetailPage.l != null) {
            ajxRouteMotorResultEventDetailPage.s.removeRouteEventInterface(ajxRouteMotorResultEventDetailPage.H);
            ajxRouteMotorResultEventDetailPage.s.setIEventDetailDialog(null);
        }
        if (ajxRouteMotorResultEventDetailPage.getMapView() != null) {
            ajxRouteMotorResultEventDetailPage.getMapView().b(tt.a((Context) ajxRouteMotorResultEventDetailPage.getActivity()) / 2, (tt.b((Context) ajxRouteMotorResultEventDetailPage.getActivity()) - ags.d(ajxRouteMotorResultEventDetailPage.getActivity())) / 2);
        }
    }

    public final void onMapAnimationFinished(aln aln) {
        super.onMapAnimationFinished(aln);
        ((AjxRouteMotorResultEventDetailPage) this.mPage).onEventPageMapAnimationFinished(aln);
    }

    public final boolean onEngineActionGesture(alg alg) {
        ((AjxRouteMotorResultEventDetailPage) this.mPage).onEventMapEngineActionGesture(alg);
        return super.onEngineActionGesture(alg);
    }
}
