package defpackage;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.drive.result.driveresult.event.AjxRouteCarResultEventDetailPage;
import com.autonavi.common.PageBundle;
import com.autonavi.jni.ae.route.model.ForbiddenLineInfo;
import com.autonavi.map.fragmentcontainer.page.MapBasePresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.favorites.data.RouteItem;

/* renamed from: ot reason: default package */
/* compiled from: AjxRouteCarResultEventDetailPresenter */
public final class ot extends MapBasePresenter<AjxRouteCarResultEventDetailPage> {
    public ot(AjxRouteCarResultEventDetailPage ajxRouteCarResultEventDetailPage) {
        super(ajxRouteCarResultEventDetailPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        AjxRouteCarResultEventDetailPage ajxRouteCarResultEventDetailPage = (AjxRouteCarResultEventDetailPage) this.mPage;
        ajxRouteCarResultEventDetailPage.n = ajxRouteCarResultEventDetailPage.getMapView().j(false);
        ajxRouteCarResultEventDetailPage.o = ajxRouteCarResultEventDetailPage.getMapView().k(false);
        ajxRouteCarResultEventDetailPage.q = ajxRouteCarResultEventDetailPage.getMapView().s();
        ajxRouteCarResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_detail_close).setOnClickListener(ajxRouteCarResultEventDetailPage);
        ajxRouteCarResultEventDetailPage.m = (ViewGroup) ajxRouteCarResultEventDetailPage.getContentView().findViewById(R.id.mapBottomInteractiveView);
        ajxRouteCarResultEventDetailPage.m.setVisibility(8);
        ajxRouteCarResultEventDetailPage.a = (ViewGroup) ajxRouteCarResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_forbid_layout);
        ajxRouteCarResultEventDetailPage.a.setVisibility(8);
        ajxRouteCarResultEventDetailPage.c = (TextView) ajxRouteCarResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_forbid_time);
        ajxRouteCarResultEventDetailPage.d = (TextView) ajxRouteCarResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_forbid_truck_type);
        ajxRouteCarResultEventDetailPage.b = (TextView) ajxRouteCarResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_detail_title);
        ajxRouteCarResultEventDetailPage.e = (TextView) ajxRouteCarResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_detail_info);
        ajxRouteCarResultEventDetailPage.f = (ImageView) ajxRouteCarResultEventDetailPage.getContentView().findViewById(R.id.route_car_result_event_detail_icon);
        ajxRouteCarResultEventDetailPage.m.addOnLayoutChangeListener(ajxRouteCarResultEventDetailPage.H);
        PageBundle arguments = ajxRouteCarResultEventDetailPage.getArguments();
        if (arguments != null) {
            ajxRouteCarResultEventDetailPage.g = arguments.getInt("data_type");
            ajxRouteCarResultEventDetailPage.y = arguments.getInt("route_type");
            ajxRouteCarResultEventDetailPage.i = arguments.getInt("event_id");
            ajxRouteCarResultEventDetailPage.j = arguments.getInt("focusIndex");
            ajxRouteCarResultEventDetailPage.l = arguments.getInt("layer_tag");
            ajxRouteCarResultEventDetailPage.k = arguments.getLongArray("result_id");
            ajxRouteCarResultEventDetailPage.h = (ph) arguments.getObject(RouteItem.ROUTE_DATA);
            ajxRouteCarResultEventDetailPage.u = new ForbiddenLineInfo();
            ajxRouteCarResultEventDetailPage.v = arguments.getInt("forbiddenId");
            ajxRouteCarResultEventDetailPage.w = arguments.getInt("forbiddenType");
            ajxRouteCarResultEventDetailPage.x = arguments.getInt("vehicleType");
            ajxRouteCarResultEventDetailPage.z = arguments.getString("timeDescription");
            ajxRouteCarResultEventDetailPage.A = arguments.getString("roadNameString");
            ajxRouteCarResultEventDetailPage.B = arguments.getString("nextRoadNameString");
            ajxRouteCarResultEventDetailPage.C = arguments.getDouble("opanlayer_lon");
            ajxRouteCarResultEventDetailPage.D = arguments.getDouble("opanlayer_lat");
            ajxRouteCarResultEventDetailPage.E = arguments.getInt("opanlayer_z");
            ajxRouteCarResultEventDetailPage.F = arguments.getString("opanlayer_poiId");
            ajxRouteCarResultEventDetailPage.u.pathId = (long) ajxRouteCarResultEventDetailPage.v;
            ajxRouteCarResultEventDetailPage.u.forbiddenType = (byte) ajxRouteCarResultEventDetailPage.w;
            ajxRouteCarResultEventDetailPage.u.forbiddenTime = ajxRouteCarResultEventDetailPage.z;
            ajxRouteCarResultEventDetailPage.u.carType = (byte) ajxRouteCarResultEventDetailPage.x;
            ajxRouteCarResultEventDetailPage.u.roadName = ajxRouteCarResultEventDetailPage.A;
            ajxRouteCarResultEventDetailPage.u.nextRoadName = ajxRouteCarResultEventDetailPage.B;
        }
    }

    public final void onStart() {
        super.onStart();
        AjxRouteCarResultEventDetailPage ajxRouteCarResultEventDetailPage = (AjxRouteCarResultEventDetailPage) this.mPage;
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.b(9001);
        }
        ajxRouteCarResultEventDetailPage.getMapView().a(0, ajxRouteCarResultEventDetailPage.getMapView().l(false), 1);
        ajxRouteCarResultEventDetailPage.getMapView().b(false);
    }

    public final void onResume() {
        super.onResume();
        ((AjxRouteCarResultEventDetailPage) this.mPage).requestScreenOrientation(1);
        ((AjxRouteCarResultEventDetailPage) this.mPage).a();
    }

    public final void onPause() {
        super.onPause();
        AjxRouteCarResultEventDetailPage ajxRouteCarResultEventDetailPage = (AjxRouteCarResultEventDetailPage) this.mPage;
        if (ajxRouteCarResultEventDetailPage.r != null) {
            alv alv = new alv();
            alv.a = 9001;
            alv.b = ajxRouteCarResultEventDetailPage.r.a;
            alv.c = ajxRouteCarResultEventDetailPage.r.b;
            alv.d = 1;
            bty gLMapView = ajxRouteCarResultEventDetailPage.getGLMapView();
            if (gLMapView != null) {
                gLMapView.a(alv);
            }
        }
    }

    public final void onStop() {
        super.onStop();
        AjxRouteCarResultEventDetailPage ajxRouteCarResultEventDetailPage = (AjxRouteCarResultEventDetailPage) this.mPage;
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.c(9001);
        }
        ajxRouteCarResultEventDetailPage.getMapView().a(ajxRouteCarResultEventDetailPage.n, ajxRouteCarResultEventDetailPage.getMapView().l(false), ajxRouteCarResultEventDetailPage.o);
        ajxRouteCarResultEventDetailPage.getMapView().b(ajxRouteCarResultEventDetailPage.q);
    }

    public final void onDestroy() {
        super.onDestroy();
        AjxRouteCarResultEventDetailPage ajxRouteCarResultEventDetailPage = (AjxRouteCarResultEventDetailPage) this.mPage;
        if (ajxRouteCarResultEventDetailPage.t != null) {
            ajxRouteCarResultEventDetailPage.t.removeRouteEventInterface(ajxRouteCarResultEventDetailPage.I);
        }
        if (ajxRouteCarResultEventDetailPage.getMapView() != null) {
            ajxRouteCarResultEventDetailPage.getMapView().b(tt.a((Context) ajxRouteCarResultEventDetailPage.getActivity()) / 2, (tt.b((Context) ajxRouteCarResultEventDetailPage.getActivity()) - ags.d(ajxRouteCarResultEventDetailPage.getActivity())) / 2);
        }
    }

    public final void onMapAnimationFinished(aln aln) {
        super.onMapAnimationFinished(aln);
        ((AjxRouteCarResultEventDetailPage) this.mPage).onEventPageMapAnimationFinished(aln);
    }

    public final boolean onEngineActionGesture(alg alg) {
        ((AjxRouteCarResultEventDetailPage) this.mPage).onEventMapEngineActionGesture(alg);
        return super.onEngineActionGesture(alg);
    }
}
