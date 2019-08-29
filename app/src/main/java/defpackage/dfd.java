package defpackage;

import android.graphics.Rect;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.model.NavigationPath;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.ae.AEUtil;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.jni.ae.route.route.Route;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.RouteItem;
import com.autonavi.minimap.drive.errorreport.RouteCarResultErrorReportFragment;
import com.autonavi.minimap.drive.errorreport.RouteCarResultErrorReportFragment.a;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportPointOverlay;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportRouteOverlay;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportRoutePointOverlay;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import com.autonavi.widget.ui.TitleBar;
import org.json.JSONObject;

/* renamed from: dfd reason: default package */
/* compiled from: RouteCarResultErrorReportPresenter */
public final class dfd extends sv<RouteCarResultErrorReportFragment, dfc> {
    public dfd(RouteCarResultErrorReportFragment routeCarResultErrorReportFragment) {
        super(routeCarResultErrorReportFragment);
    }

    public final void onPageCreated() {
        int i;
        Route route;
        super.onPageCreated();
        RouteCarResultErrorReportFragment routeCarResultErrorReportFragment = (RouteCarResultErrorReportFragment) this.mPage;
        routeCarResultErrorReportFragment.l = routeCarResultErrorReportFragment.getContentView();
        if (!(routeCarResultErrorReportFragment.getMapManager() == null || routeCarResultErrorReportFragment.getMapManager().getMapView() == null)) {
            routeCarResultErrorReportFragment.d = new ErrorReportRouteOverlay(routeCarResultErrorReportFragment.getMapManager().getMapView());
            routeCarResultErrorReportFragment.addOverlay(routeCarResultErrorReportFragment.d);
            routeCarResultErrorReportFragment.f = new ErrorReportRoutePointOverlay(routeCarResultErrorReportFragment.getMapManager().getMapView());
            routeCarResultErrorReportFragment.addOverlay(routeCarResultErrorReportFragment.f);
            routeCarResultErrorReportFragment.e = new ErrorReportPointOverlay(routeCarResultErrorReportFragment.getMapManager().getMapView());
            routeCarResultErrorReportFragment.addOverlay(routeCarResultErrorReportFragment.e);
            routeCarResultErrorReportFragment.e.setMoveToFocus(false);
            routeCarResultErrorReportFragment.f.setMoveToFocus(false);
        }
        PageBundle arguments = routeCarResultErrorReportFragment.getArguments();
        routeCarResultErrorReportFragment.b = (ICarRouteResult) arguments.get("RouteCarResultErrorReportFragment.bundle_key_result");
        tn.a();
        if (!tn.a(routeCarResultErrorReportFragment.b)) {
            routeCarResultErrorReportFragment.finish();
        } else {
            routeCarResultErrorReportFragment.h = routeCarResultErrorReportFragment.b.getShareStartPos();
            routeCarResultErrorReportFragment.i = routeCarResultErrorReportFragment.b.getShareEndPos();
            routeCarResultErrorReportFragment.j = routeCarResultErrorReportFragment.b.getShareMidPoses();
            routeCarResultErrorReportFragment.V = arguments.getLong("PATH_RESULT_PTR", 0);
            int focusRouteIndex = routeCarResultErrorReportFragment.b.getFocusRouteIndex();
            CalcRouteResult calcRouteResult = routeCarResultErrorReportFragment.b.getCalcRouteResult();
            if (calcRouteResult == null) {
                route = null;
            } else {
                route = calcRouteResult.getRoute(focusRouteIndex);
            }
            routeCarResultErrorReportFragment.c = route;
            NavigationPath focusNavigationPath = routeCarResultErrorReportFragment.b.getFocusNavigationPath();
            if (focusNavigationPath != null) {
                routeCarResultErrorReportFragment.g = dfg.a(focusNavigationPath.mEngineLineItem);
            }
            if (arguments.containsKey("RouteCarResultErrorReportFragment.from_page_code")) {
                routeCarResultErrorReportFragment.S = arguments.getString("RouteCarResultErrorReportFragment.from_page_code");
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            routeCarResultErrorReportFragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            routeCarResultErrorReportFragment.P = displayMetrics.widthPixels;
            routeCarResultErrorReportFragment.Q = displayMetrics.heightPixels;
            routeCarResultErrorReportFragment.R = ags.d(routeCarResultErrorReportFragment.getContext());
            tn.a().a(CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN, calcRouteResult);
        }
        if (routeCarResultErrorReportFragment.g()) {
            if (!"47".equals(routeCarResultErrorReportFragment.S)) {
                if ("46".equals(routeCarResultErrorReportFragment.S)) {
                    i = 1;
                } else if ("51".equals(routeCarResultErrorReportFragment.S)) {
                    i = 2;
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("from", i);
                LogManager.actionLogV2("P00437", "B001", jSONObject);
            }
            i = 0;
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("from", i);
            } catch (Exception unused) {
            }
            LogManager.actionLogV2("P00437", "B001", jSONObject2);
        }
        View view = routeCarResultErrorReportFragment.l;
        routeCarResultErrorReportFragment.m = (TitleBar) view.findViewById(R.id.title);
        routeCarResultErrorReportFragment.m.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RouteCarResultErrorReportFragment.this.c();
            }
        });
        routeCarResultErrorReportFragment.m.setActionTextVisibility(4);
        routeCarResultErrorReportFragment.A = view.findViewById(R.id.mapBottomInteractiveView);
        if (routeCarResultErrorReportFragment.b()) {
            routeCarResultErrorReportFragment.B = view.findViewById(R.id.truck_error_category_bottom);
            routeCarResultErrorReportFragment.B.setVisibility(0);
            routeCarResultErrorReportFragment.r = view.findViewById(R.id.truck_cannot_pass_category);
            routeCarResultErrorReportFragment.r.setOnClickListener(routeCarResultErrorReportFragment);
            routeCarResultErrorReportFragment.s = view.findViewById(R.id.truck_around_way_category);
            routeCarResultErrorReportFragment.s.setOnClickListener(routeCarResultErrorReportFragment);
            routeCarResultErrorReportFragment.t = view.findViewById(R.id.truck_msg_error_category);
            routeCarResultErrorReportFragment.t.setOnClickListener(routeCarResultErrorReportFragment);
            routeCarResultErrorReportFragment.u = view.findViewById(R.id.truck_other_question_category);
            routeCarResultErrorReportFragment.u.setOnClickListener(routeCarResultErrorReportFragment);
        } else {
            routeCarResultErrorReportFragment.B = view.findViewById(R.id.car_error_category_bottom);
            routeCarResultErrorReportFragment.B.setVisibility(0);
            routeCarResultErrorReportFragment.n = view.findViewById(R.id.cannot_pass_category);
            routeCarResultErrorReportFragment.n.setOnClickListener(routeCarResultErrorReportFragment);
            routeCarResultErrorReportFragment.o = view.findViewById(R.id.around_way_category);
            routeCarResultErrorReportFragment.o.setOnClickListener(routeCarResultErrorReportFragment);
            routeCarResultErrorReportFragment.p = view.findViewById(R.id.other_question_category);
            routeCarResultErrorReportFragment.p.setOnClickListener(routeCarResultErrorReportFragment);
        }
        routeCarResultErrorReportFragment.C = view.findViewById(R.id.error_edit_detail_bottom);
        routeCarResultErrorReportFragment.D = (TextView) view.findViewById(R.id.edit_detail_radio_1);
        routeCarResultErrorReportFragment.D.setOnClickListener(routeCarResultErrorReportFragment);
        routeCarResultErrorReportFragment.E = (TextView) view.findViewById(R.id.edit_detail_radio_2);
        routeCarResultErrorReportFragment.E.setOnClickListener(routeCarResultErrorReportFragment);
        routeCarResultErrorReportFragment.F = (TextView) view.findViewById(R.id.edit_detail_radio_3);
        routeCarResultErrorReportFragment.F.setOnClickListener(routeCarResultErrorReportFragment);
        routeCarResultErrorReportFragment.G = (TextView) view.findViewById(R.id.edit_detail_radio_4);
        routeCarResultErrorReportFragment.G.setOnClickListener(routeCarResultErrorReportFragment);
        routeCarResultErrorReportFragment.q = view.findViewById(R.id.bubble_confirm_layout);
        LayoutParams layoutParams = routeCarResultErrorReportFragment.q.getLayoutParams();
        layoutParams.height = (routeCarResultErrorReportFragment.Q * 2) / 5;
        routeCarResultErrorReportFragment.q.setLayoutParams(layoutParams);
        routeCarResultErrorReportFragment.v = (ImageView) view.findViewById(R.id.iv_center);
        routeCarResultErrorReportFragment.w = view.findViewById(R.id.iv_bubble_shadow);
        routeCarResultErrorReportFragment.x = view.findViewById(R.id.poi_confirm_choose);
        routeCarResultErrorReportFragment.x.setOnClickListener(routeCarResultErrorReportFragment);
        routeCarResultErrorReportFragment.y = (TextView) view.findViewById(R.id.move_map_tip_tv);
        routeCarResultErrorReportFragment.K = view.findViewById(R.id.error_radio_layout);
        routeCarResultErrorReportFragment.L = (TextView) view.findViewById(R.id.car_error_problem_text);
        routeCarResultErrorReportFragment.z = (TextView) view.findViewById(R.id.edit_detail_title);
        routeCarResultErrorReportFragment.H = (TextView) view.findViewById(R.id.car_error_page_edit_problem_input);
        routeCarResultErrorReportFragment.H.setMovementMethod(dew.a());
        routeCarResultErrorReportFragment.H.setOnClickListener(routeCarResultErrorReportFragment);
        routeCarResultErrorReportFragment.I = (TextView) view.findViewById(R.id.car_error_page_edit_contact_input);
        String a = RouteCarResultErrorReportFragment.a();
        if (!TextUtils.isEmpty(a) && TextUtils.isEmpty(routeCarResultErrorReportFragment.I.getText())) {
            routeCarResultErrorReportFragment.I.setText(a);
        }
        routeCarResultErrorReportFragment.I.setOnClickListener(routeCarResultErrorReportFragment);
        view.findViewById(R.id.car_error_page_edit_problem_input_layout).setOnClickListener(routeCarResultErrorReportFragment);
        view.findViewById(R.id.car_error_page_edit_contact_input_layout).setOnClickListener(routeCarResultErrorReportFragment);
        routeCarResultErrorReportFragment.J = (TextView) view.findViewById(R.id.car_error_submit);
        NoDBClickUtil.a((View) routeCarResultErrorReportFragment.J, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RouteCarResultErrorReportFragment.e(RouteCarResultErrorReportFragment.this);
            }
        });
        routeCarResultErrorReportFragment.f();
        if (!(routeCarResultErrorReportFragment.getMapManager() == null || routeCarResultErrorReportFragment.getMapManager().getMapView() == null)) {
            routeCarResultErrorReportFragment.getMapManager().getMapView().g(0.0f);
        }
        routeCarResultErrorReportFragment.getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(new Rect());
        if (routeCarResultErrorReportFragment.k == null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, 0.0f, 0, -30.0f);
            routeCarResultErrorReportFragment.k = translateAnimation;
            routeCarResultErrorReportFragment.k.setDuration(250);
            routeCarResultErrorReportFragment.k.setInterpolator(new AccelerateInterpolator());
            routeCarResultErrorReportFragment.k.setAnimationListener(new AnimationListener() {
                public final void onAnimationEnd(Animation animation) {
                }

                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                }
            });
        }
        routeCarResultErrorReportFragment.a(1);
        routeCarResultErrorReportFragment.U = new ForegroundColorSpan(routeCarResultErrorReportFragment.getResources().getColor(R.color.c_10));
    }

    public final void onStart() {
        super.onStart();
        ((RouteCarResultErrorReportFragment) this.mPage).setSoftInputMode(18);
        RouteCarResultErrorReportFragment routeCarResultErrorReportFragment = (RouteCarResultErrorReportFragment) this.mPage;
        if (routeCarResultErrorReportFragment.d != null && routeCarResultErrorReportFragment.e != null && routeCarResultErrorReportFragment.g != null && routeCarResultErrorReportFragment.getMapManager() != null) {
            if (routeCarResultErrorReportFragment.getMapManager().getMapView() != null) {
                routeCarResultErrorReportFragment.getMapManager().getMapView().a(routeCarResultErrorReportFragment.getMapManager().getMapView().p(false), routeCarResultErrorReportFragment.getMapManager().getMapView().ae(), 1);
                if (AEUtil.IS_DEBUG) {
                    ku a = ku.a();
                    StringBuilder sb = new StringBuilder("[RouteCarResultErrorReportFragment]onResume#setMapModeAndStyle#getMapManager().getMapView().getMapMode(false):");
                    sb.append(routeCarResultErrorReportFragment.getMapManager().getMapView().p(false));
                    a.c("NaviMonitor", sb.toString());
                }
            }
            if (5 == routeCarResultErrorReportFragment.a) {
                routeCarResultErrorReportFragment.e();
            }
            if (!(routeCarResultErrorReportFragment.d == null || routeCarResultErrorReportFragment.g == null)) {
                tn.a();
                if (tn.a(routeCarResultErrorReportFragment.b)) {
                    routeCarResultErrorReportFragment.d.removeAll();
                    routeCarResultErrorReportFragment.d.removeRouteName();
                    routeCarResultErrorReportFragment.d.addItem((RouteItem) routeCarResultErrorReportFragment.g);
                    routeCarResultErrorReportFragment.d.addRouteName(routeCarResultErrorReportFragment.g.isRefreshMap());
                }
            }
            routeCarResultErrorReportFragment.d();
            if (routeCarResultErrorReportFragment.T) {
                routeCarResultErrorReportFragment.h();
                routeCarResultErrorReportFragment.T = false;
            }
            if (routeCarResultErrorReportFragment.getMapManager().getMapView() != null) {
                routeCarResultErrorReportFragment.getMapManager().getMapView().b(false);
            }
            routeCarResultErrorReportFragment.requestScreenOrientation(1);
            if (routeCarResultErrorReportFragment.b != null) {
                tn.a();
                if (!tn.a(routeCarResultErrorReportFragment.b)) {
                    routeCarResultErrorReportFragment.finish();
                }
            }
        }
    }

    public final void onStop() {
        super.onStop();
        RouteCarResultErrorReportFragment routeCarResultErrorReportFragment = (RouteCarResultErrorReportFragment) this.mPage;
        routeCarResultErrorReportFragment.d.removeRouteName();
        routeCarResultErrorReportFragment.d.clear();
    }

    public final void onDestroy() {
        super.onDestroy();
        RouteCarResultErrorReportFragment routeCarResultErrorReportFragment = (RouteCarResultErrorReportFragment) this.mPage;
        routeCarResultErrorReportFragment.O.removeCallbacksAndMessages(null);
        if (routeCarResultErrorReportFragment.V != 0) {
            NaviManager.releasePathResult(routeCarResultErrorReportFragment.V);
        }
        if (routeCarResultErrorReportFragment.getMapManager() != null && routeCarResultErrorReportFragment.getMapManager().getMapView() != null) {
            if (!(routeCarResultErrorReportFragment.getMapManager() == null || routeCarResultErrorReportFragment.getMapManager().getMapView() == null)) {
                bty mapView = routeCarResultErrorReportFragment.getMapManager().getMapView();
                mapView.N();
                mapView.a(routeCarResultErrorReportFragment.Z.x, routeCarResultErrorReportFragment.Z.y);
                mapView.e(routeCarResultErrorReportFragment.W);
                mapView.d(routeCarResultErrorReportFragment.X);
                mapView.g(routeCarResultErrorReportFragment.Y);
                bqx bqx = (bqx) ank.a(bqx.class);
                if (bqx != null) {
                    bqx.a(tf.a("SharedPreferences", "traffic", false), false, routeCarResultErrorReportFragment.getMapManager(), routeCarResultErrorReportFragment.getContext());
                }
            }
            routeCarResultErrorReportFragment.getMapManager().getMapView().b(DriveUtil.getTrafficMode(routeCarResultErrorReportFragment.getContext()));
            tn.a().a(CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN);
        }
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    public final void onMapSurfaceCreated() {
        super.onMapSurfaceCreated();
    }

    public final void onMapSurfaceChanged(int i, int i2) {
        super.onMapSurfaceChanged(i, i2);
    }

    public final boolean onMapLevelChange(boolean z) {
        super.onMapLevelChange(z);
        return false;
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        RouteCarResultErrorReportFragment routeCarResultErrorReportFragment = (RouteCarResultErrorReportFragment) this.mPage;
        routeCarResultErrorReportFragment.requestScreenOrientation(1);
        if (i == 256 && pageBundle.getBoolean("upload_result")) {
            routeCarResultErrorReportFragment.finish();
        }
    }

    public final boolean onMapTouchEvent(MotionEvent motionEvent) {
        RouteCarResultErrorReportFragment routeCarResultErrorReportFragment = (RouteCarResultErrorReportFragment) this.mPage;
        if (motionEvent.getAction() == 2 && ((routeCarResultErrorReportFragment.a == 2 || routeCarResultErrorReportFragment.a == 4) && routeCarResultErrorReportFragment.a != 3)) {
            routeCarResultErrorReportFragment.a(3);
        }
        return super.onMapTouchEvent(motionEvent);
    }

    public final boolean onMapMotionStop() {
        RouteCarResultErrorReportFragment routeCarResultErrorReportFragment = (RouteCarResultErrorReportFragment) this.mPage;
        if (routeCarResultErrorReportFragment.a == 3) {
            routeCarResultErrorReportFragment.O.removeCallbacksAndMessages(null);
            routeCarResultErrorReportFragment.O.postDelayed(new a(routeCarResultErrorReportFragment), 600);
        }
        return super.onMapMotionStop();
    }

    public final ON_BACK_TYPE onBackPressed() {
        RouteCarResultErrorReportFragment routeCarResultErrorReportFragment = (RouteCarResultErrorReportFragment) this.mPage;
        if (routeCarResultErrorReportFragment.M != null) {
            routeCarResultErrorReportFragment.dismissViewLayer(routeCarResultErrorReportFragment.M);
            routeCarResultErrorReportFragment.M = null;
            return ON_BACK_TYPE.TYPE_IGNORE;
        } else if (routeCarResultErrorReportFragment.N != null) {
            routeCarResultErrorReportFragment.N.a();
            return ON_BACK_TYPE.TYPE_IGNORE;
        } else {
            routeCarResultErrorReportFragment.c();
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
    }

    public final /* synthetic */ st a() {
        return new dfc(this);
    }
}
