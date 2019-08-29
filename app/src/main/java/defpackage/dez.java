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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.drivecommon.navi.navidata.NavigationDataResult;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.ae.AEUtil;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.errorreport.NavigationErrorReportFragment;
import com.autonavi.minimap.drive.errorreport.NavigationErrorReportFragment.a;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportLineOverlay;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportPointOverlay;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportRoutePointOverlay;
import com.autonavi.widget.ui.TitleBar;
import java.util.ArrayList;

/* renamed from: dez reason: default package */
/* compiled from: NavigationErrorReportPresenter */
public final class dez extends sv<NavigationErrorReportFragment, dey> {
    public dez(NavigationErrorReportFragment navigationErrorReportFragment) {
        super(navigationErrorReportFragment);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        NavigationErrorReportFragment navigationErrorReportFragment = (NavigationErrorReportFragment) this.mPage;
        navigationErrorReportFragment.o = navigationErrorReportFragment.getContentView();
        MapManager mapManager = navigationErrorReportFragment.getMapManager();
        if (mapManager != null) {
            navigationErrorReportFragment.i = new ErrorReportLineOverlay(mapManager.getMapView());
            navigationErrorReportFragment.addOverlay(navigationErrorReportFragment.i);
            navigationErrorReportFragment.k = new ErrorReportRoutePointOverlay(mapManager.getMapView());
            navigationErrorReportFragment.addOverlay(navigationErrorReportFragment.k);
            navigationErrorReportFragment.j = new ErrorReportPointOverlay(mapManager.getMapView());
            navigationErrorReportFragment.addOverlay(navigationErrorReportFragment.j);
            navigationErrorReportFragment.j.setMoveToFocus(false);
            navigationErrorReportFragment.k.setMoveToFocus(false);
        }
        PageBundle arguments = navigationErrorReportFragment.getArguments();
        navigationErrorReportFragment.b.clear();
        if (arguments != null) {
            navigationErrorReportFragment.V = arguments.getString("navi_type");
            navigationErrorReportFragment.a = (NavigationDataResult) arguments.get("RouteCarResultErrorReportFragment.bundle_key_result");
            if (navigationErrorReportFragment.a != null) {
                navigationErrorReportFragment.ad = arguments.getBoolean("isFromEyrie", false);
                navigationErrorReportFragment.e = navigationErrorReportFragment.a.getShareStartPos();
                navigationErrorReportFragment.f = navigationErrorReportFragment.a.getShareEndPos();
                navigationErrorReportFragment.g = navigationErrorReportFragment.a.getOriginMidPOIs();
                ArrayList<ArrayList<GeoPoint>> passedPoints = navigationErrorReportFragment.a.getPassedPoints();
                if (passedPoints == null || passedPoints.size() == 0) {
                    passedPoints = new ArrayList<>();
                    if (navigationErrorReportFragment.e != null) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(navigationErrorReportFragment.e);
                        passedPoints.add(arrayList);
                    }
                }
                for (int i = 0; i < passedPoints.size(); i++) {
                    navigationErrorReportFragment.b.addAll(passedPoints.get(i));
                }
                ahn.b().execute(new Runnable(navigationErrorReportFragment.b, new Callback<ArrayList<GeoPoint>>() {
                    public void callback(ArrayList<GeoPoint> arrayList) {
                        NavigationErrorReportFragment.this.af = arrayList;
                    }

                    public void error(Throwable th, boolean z) {
                        th.printStackTrace();
                    }
                }) {
                    final /* synthetic */ ArrayList a;
                    final /* synthetic */ Callback b;

                    {
                        this.a = r1;
                        this.b = r2;
                    }

                    public final void run() {
                        int i;
                        ArrayList arrayList = new ArrayList();
                        int size = this.a.size();
                        int i2 = 0;
                        while (true) {
                            i = size - 1;
                            if (i2 >= i) {
                                break;
                            }
                            int i3 = i2 + 1;
                            arrayList.add(this.a.get(i2));
                            djs.a((GeoPoint) this.a.get(i2), (GeoPoint) this.a.get(i3), arrayList);
                            i2 = i3;
                        }
                        if (size > 0) {
                            arrayList.add(this.a.get(i));
                        }
                        this.b.callback(arrayList);
                    }
                });
                if (navigationErrorReportFragment.b.size() > 0) {
                    navigationErrorReportFragment.c = new ArrayList();
                    StringBuilder sb = new StringBuilder("setData---isFromEyrie=");
                    sb.append(navigationErrorReportFragment.ad);
                    AMapLog.d("NavigationErrorReportFragment", sb.toString());
                    if (!navigationErrorReportFragment.ad) {
                        navigationErrorReportFragment.c.add(dfe.a((GeoPoint[]) navigationErrorReportFragment.b.toArray(new GeoPoint[navigationErrorReportFragment.b.size()])));
                    } else {
                        int i2 = 0;
                        while (true) {
                            int i3 = i2 + 2000;
                            if (i3 > navigationErrorReportFragment.b.size()) {
                                break;
                            }
                            navigationErrorReportFragment.c.add(dfe.a((GeoPoint[]) navigationErrorReportFragment.b.subList(i2, i3).toArray(new GeoPoint[2000])));
                            i2 = i3;
                        }
                        if (i2 < navigationErrorReportFragment.b.size()) {
                            ArrayList<GeoPoint> arrayList2 = navigationErrorReportFragment.b;
                            navigationErrorReportFragment.c.add(dfe.a((GeoPoint[]) arrayList2.subList(i2, arrayList2.size()).toArray(new GeoPoint[(navigationErrorReportFragment.b.size() - i2)])));
                        }
                    }
                }
                ArrayList<GeoPoint> deviationPoints = navigationErrorReportFragment.a.getDeviationPoints();
                if (deviationPoints != null && deviationPoints.size() > 0) {
                    navigationErrorReportFragment.h.addAll(deviationPoints);
                    navigationErrorReportFragment.Q = true;
                }
                navigationErrorReportFragment.R = true;
                ArrayList<GeoPoint> orgLinePoints = navigationErrorReportFragment.a.getOrgLinePoints();
                navigationErrorReportFragment.d = dfe.b((GeoPoint[]) orgLinePoints.toArray(new GeoPoint[orgLinePoints.size()]));
                DisplayMetrics displayMetrics = new DisplayMetrics();
                navigationErrorReportFragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                navigationErrorReportFragment.Z = displayMetrics.heightPixels;
            }
        }
        View view = navigationErrorReportFragment.o;
        navigationErrorReportFragment.o.addOnLayoutChangeListener(navigationErrorReportFragment.ae);
        navigationErrorReportFragment.p = (TitleBar) view.findViewById(R.id.title);
        navigationErrorReportFragment.p.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                NavigationErrorReportFragment.this.a();
            }
        });
        navigationErrorReportFragment.p.setActionTextVisibility(4);
        navigationErrorReportFragment.C = view.findViewById(R.id.mapBottomInteractiveView);
        navigationErrorReportFragment.l = (LinearLayout) view.findViewById(R.id.ckb_container);
        navigationErrorReportFragment.m = (CheckBox) view.findViewById(R.id.ckb_off_route_point);
        navigationErrorReportFragment.m.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                NavigationErrorReportFragment.this.Q = z;
                NavigationErrorReportFragment.this.h();
            }
        });
        navigationErrorReportFragment.n = (CheckBox) view.findViewById(R.id.ckb_org_route_line);
        navigationErrorReportFragment.n.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                NavigationErrorReportFragment.this.R = z;
                NavigationErrorReportFragment.this.g();
            }
        });
        if (navigationErrorReportFragment.c()) {
            navigationErrorReportFragment.D = view.findViewById(R.id.truck_error_category_bottom);
            view.findViewById(R.id.truck_cannot_pass_category).setOnClickListener(navigationErrorReportFragment);
            view.findViewById(R.id.truck_around_way_category).setOnClickListener(navigationErrorReportFragment);
            view.findViewById(R.id.truck_msg_error_category).setOnClickListener(navigationErrorReportFragment);
            view.findViewById(R.id.truck_camera_error_category).setOnClickListener(navigationErrorReportFragment);
            view.findViewById(R.id.truck_destination_error_category).setOnClickListener(navigationErrorReportFragment);
        } else {
            navigationErrorReportFragment.D = view.findViewById(R.id.car_error_category_bottom);
            view.findViewById(R.id.cannot_pass_category).setOnClickListener(navigationErrorReportFragment);
            view.findViewById(R.id.around_way_category).setOnClickListener(navigationErrorReportFragment);
            view.findViewById(R.id.camera_error_category).setOnClickListener(navigationErrorReportFragment);
            view.findViewById(R.id.speed_error_category).setOnClickListener(navigationErrorReportFragment);
            view.findViewById(R.id.destination_error_category).setOnClickListener(navigationErrorReportFragment);
            view.findViewById(R.id.wrong_drive_category).setOnClickListener(navigationErrorReportFragment);
        }
        navigationErrorReportFragment.q = view.findViewById(R.id.bubble_confirm_layout);
        LayoutParams layoutParams = navigationErrorReportFragment.q.getLayoutParams();
        layoutParams.height = (navigationErrorReportFragment.Z * 2) / 5;
        navigationErrorReportFragment.q.setLayoutParams(layoutParams);
        navigationErrorReportFragment.r = (ImageView) view.findViewById(R.id.iv_center);
        navigationErrorReportFragment.s = view.findViewById(R.id.iv_bubble_shadow);
        navigationErrorReportFragment.t = view.findViewById(R.id.poi_confirm_choose);
        navigationErrorReportFragment.t.setOnClickListener(navigationErrorReportFragment);
        navigationErrorReportFragment.u = (TextView) view.findViewById(R.id.move_map_tip_tv);
        navigationErrorReportFragment.E = view.findViewById(R.id.error_edit_detail_bottom);
        navigationErrorReportFragment.F = (TextView) view.findViewById(R.id.edit_detail_radio_1);
        navigationErrorReportFragment.F.setOnClickListener(navigationErrorReportFragment);
        navigationErrorReportFragment.G = (TextView) view.findViewById(R.id.edit_detail_radio_2);
        navigationErrorReportFragment.G.setOnClickListener(navigationErrorReportFragment);
        navigationErrorReportFragment.H = (TextView) view.findViewById(R.id.edit_detail_radio_3);
        navigationErrorReportFragment.H.setOnClickListener(navigationErrorReportFragment);
        navigationErrorReportFragment.I = (TextView) view.findViewById(R.id.edit_detail_radio_4);
        navigationErrorReportFragment.I.setOnClickListener(navigationErrorReportFragment);
        navigationErrorReportFragment.J = view.findViewById(R.id.edit_detail_radio_1_icon);
        navigationErrorReportFragment.K = view.findViewById(R.id.edit_detail_radio_2_icon);
        navigationErrorReportFragment.L = view.findViewById(R.id.edit_detail_radio_3_icon);
        navigationErrorReportFragment.M = view.findViewById(R.id.edit_detail_radio_4_icon);
        navigationErrorReportFragment.v = (TextView) view.findViewById(R.id.edit_detail_title);
        navigationErrorReportFragment.N = (TextView) view.findViewById(R.id.car_error_page_edit_problem_input);
        navigationErrorReportFragment.N.setMovementMethod(dew.a());
        navigationErrorReportFragment.N.setOnClickListener(navigationErrorReportFragment);
        navigationErrorReportFragment.O = (TextView) view.findViewById(R.id.car_error_page_edit_contact_input);
        String b = NavigationErrorReportFragment.b();
        if (!TextUtils.isEmpty(b) && TextUtils.isEmpty(navigationErrorReportFragment.O.getText())) {
            navigationErrorReportFragment.O.setText(b);
        }
        navigationErrorReportFragment.O.setOnClickListener(navigationErrorReportFragment);
        view.findViewById(R.id.car_error_page_edit_problem_input_layout).setOnClickListener(navigationErrorReportFragment);
        view.findViewById(R.id.car_error_page_edit_contact_input_layout).setOnClickListener(navigationErrorReportFragment);
        navigationErrorReportFragment.P = (TextView) view.findViewById(R.id.car_error_submit);
        navigationErrorReportFragment.P.setOnClickListener(navigationErrorReportFragment);
        navigationErrorReportFragment.e();
        navigationErrorReportFragment.ac = new ForegroundColorSpan(navigationErrorReportFragment.getResources().getColor(R.color.c_10));
        navigationErrorReportFragment.z = view.findViewById(R.id.error_radio_layout);
        navigationErrorReportFragment.w = (TextView) view.findViewById(R.id.edit_detail_select_type);
        navigationErrorReportFragment.a(navigationErrorReportFragment.w, R.string.car_error_edit_detail_choose_type);
        navigationErrorReportFragment.x = (TextView) view.findViewById(R.id.car_error_problem_text);
        navigationErrorReportFragment.y = (TextView) view.findViewById(R.id.car_error_telephone_text);
        navigationErrorReportFragment.A = (TextView) view.findViewById(R.id.select_destination_text);
        navigationErrorReportFragment.a(navigationErrorReportFragment.A, R.string.car_error_edit_detail_choose_destination);
        navigationErrorReportFragment.B = (TextView) view.findViewById(R.id.select_destination_position);
        navigationErrorReportFragment.B.setOnClickListener(navigationErrorReportFragment);
        if (!(navigationErrorReportFragment.getSuspendManager() == null || navigationErrorReportFragment.getSuspendManager().d() == null)) {
            navigationErrorReportFragment.getSuspendManager().d().f();
        }
        navigationErrorReportFragment.getSuspendManager().b().disableView(209);
        navigationErrorReportFragment.getMapView().g(0.0f);
        navigationErrorReportFragment.getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(new Rect());
        navigationErrorReportFragment.l.setVisibility(0);
        navigationErrorReportFragment.n.setChecked(navigationErrorReportFragment.R);
        ArrayList<GeoPoint> deviationPoints2 = navigationErrorReportFragment.a.getDeviationPoints();
        if (deviationPoints2 == null || deviationPoints2.size() <= 0) {
            navigationErrorReportFragment.m.setVisibility(8);
        } else {
            navigationErrorReportFragment.m.setVisibility(0);
            navigationErrorReportFragment.m.setChecked(navigationErrorReportFragment.Q);
        }
        if (navigationErrorReportFragment.ab == null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, 0.0f, 0, -30.0f);
            navigationErrorReportFragment.ab = translateAnimation;
            navigationErrorReportFragment.ab.setDuration(250);
            navigationErrorReportFragment.ab.setInterpolator(new AccelerateInterpolator());
            navigationErrorReportFragment.ab.setAnimationListener(new AnimationListener() {
                public final void onAnimationEnd(Animation animation) {
                }

                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                }
            });
        }
        navigationErrorReportFragment.a(1);
    }

    public final void onResume() {
        super.onResume();
        ((NavigationErrorReportFragment) this.mPage).setSoftInputMode(18);
        NavigationErrorReportFragment navigationErrorReportFragment = (NavigationErrorReportFragment) this.mPage;
        if (navigationErrorReportFragment.i != null && navigationErrorReportFragment.j != null && navigationErrorReportFragment.c != null) {
            if (!(5 != navigationErrorReportFragment.T || navigationErrorReportFragment.aa == null || navigationErrorReportFragment.U == null)) {
                navigationErrorReportFragment.U.d = navigationErrorReportFragment.aa;
                navigationErrorReportFragment.d();
            }
            navigationErrorReportFragment.g();
            navigationErrorReportFragment.h();
            if (navigationErrorReportFragment.S) {
                navigationErrorReportFragment.f();
                navigationErrorReportFragment.S = false;
            }
            if (!(navigationErrorReportFragment.getMapView() == null || navigationErrorReportFragment.getMapManager() == null)) {
                bty mapView = navigationErrorReportFragment.getMapManager().getMapView();
                if (mapView != null) {
                    navigationErrorReportFragment.getMapView().a(mapView.p(false), mapView.ae(), 1);
                    navigationErrorReportFragment.getMapView().b(false);
                    if (AEUtil.IS_DEBUG) {
                        ku a = ku.a();
                        StringBuilder sb = new StringBuilder("[NavigationErrorReportFragment]onResume#setMapModeAndStyle#getMapView().getMapMode(false):");
                        sb.append(mapView.p(false));
                        a.c("NaviMonitor", sb.toString());
                    }
                }
            }
            navigationErrorReportFragment.requestScreenOrientation(1);
        }
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onDestroy() {
        super.onDestroy();
        NavigationErrorReportFragment navigationErrorReportFragment = (NavigationErrorReportFragment) this.mPage;
        navigationErrorReportFragment.Y.removeCallbacksAndMessages(null);
        if (navigationErrorReportFragment.getMapView() != null) {
            navigationErrorReportFragment.o.removeOnLayoutChangeListener(navigationErrorReportFragment.ae);
            navigationErrorReportFragment.getMapView().b(DriveUtil.getTrafficMode(navigationErrorReportFragment.getContext()));
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
        NavigationErrorReportFragment navigationErrorReportFragment = (NavigationErrorReportFragment) this.mPage;
        navigationErrorReportFragment.requestScreenOrientation(1);
        if (i == 256 && pageBundle.getBoolean("upload_result")) {
            navigationErrorReportFragment.finish();
        }
    }

    public final boolean onMapTouchEvent(MotionEvent motionEvent) {
        NavigationErrorReportFragment navigationErrorReportFragment = (NavigationErrorReportFragment) this.mPage;
        if (motionEvent.getAction() == 2 && ((navigationErrorReportFragment.T == 2 || navigationErrorReportFragment.T == 4) && navigationErrorReportFragment.T != 3)) {
            navigationErrorReportFragment.a(3);
        }
        return super.onMapTouchEvent(motionEvent);
    }

    public final boolean onMapMotionStop() {
        NavigationErrorReportFragment navigationErrorReportFragment = (NavigationErrorReportFragment) this.mPage;
        if (navigationErrorReportFragment.T == 3) {
            navigationErrorReportFragment.Y.removeCallbacksAndMessages(null);
            navigationErrorReportFragment.Y.postDelayed(new a(navigationErrorReportFragment), 600);
        }
        return super.onMapMotionStop();
    }

    public final ON_BACK_TYPE onBackPressed() {
        NavigationErrorReportFragment navigationErrorReportFragment = (NavigationErrorReportFragment) this.mPage;
        if (navigationErrorReportFragment.W != null) {
            navigationErrorReportFragment.dismissViewLayer(navigationErrorReportFragment.W);
            navigationErrorReportFragment.W = null;
            return ON_BACK_TYPE.TYPE_IGNORE;
        } else if (navigationErrorReportFragment.X != null) {
            navigationErrorReportFragment.X.a();
            return ON_BACK_TYPE.TYPE_IGNORE;
        } else {
            navigationErrorReportFragment.a();
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
    }

    public final /* synthetic */ st a() {
        return new dey(this);
    }
}
