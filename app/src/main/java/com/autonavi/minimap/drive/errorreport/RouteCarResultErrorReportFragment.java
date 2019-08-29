package com.autonavi.minimap.drive.errorreport;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.model.NavigationResult;
import com.amap.bundle.drivecommon.mvp.view.DriveBaseMapPage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLRouteOverlay;
import com.autonavi.jni.ae.route.route.Route;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportPointOverlay;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportRouteOverlay;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportRoutePointItem;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportRoutePointItem.RoutePointType;
import com.autonavi.minimap.drive.errorreport.overlay.ErrorReportRoutePointOverlay;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.TitleBar;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.json.JSONObject;

@PageAction("amap.basemap.action.route_car_error_report")
public class RouteCarResultErrorReportFragment extends DriveBaseMapPage<dfd> implements Callback, OnClickListener, btz {
    private static final int[] ag = {6, 6, 6, 6};
    public View A;
    public View B;
    public View C;
    public TextView D;
    public TextView E;
    public TextView F;
    public TextView G;
    public TextView H;
    public TextView I;
    public TextView J;
    public View K;
    public TextView L;
    public AlertView M = null;
    public dex N;
    public Handler O = new Handler(this);
    public int P;
    public int Q;
    public int R;
    public String S = "4";
    public boolean T = true;
    public ForegroundColorSpan U;
    public long V = 0;
    public float W = 0.0f;
    public float X = 16.0f;
    public float Y = 0.0f;
    public GeoPoint Z = LocationInstrument.getInstance().getLatestPosition();
    public int a = 1;
    /* access modifiers changed from: private */
    public djm aa;
    private int ab = -1;
    private int ac = -1;
    private int ad = -1;
    private String ae = "";
    /* access modifiers changed from: private */
    public String af = "";
    public ICarRouteResult b;
    public Route c;
    public ErrorReportRouteOverlay d;
    public ErrorReportPointOverlay e;
    public ErrorReportRoutePointOverlay f;
    public dfg g;
    public GeoPoint h;
    public GeoPoint i;
    public ArrayList<GeoPoint> j;
    public TranslateAnimation k;
    public View l;
    public TitleBar m;
    public View n;
    public View o;
    public View p;
    public View q;
    public View r;
    public View s;
    public View t;
    public View u;
    public ImageView v;
    public View w;
    public View x;
    public TextView y;
    public TextView z;

    public static class a implements Runnable {
        WeakReference<RouteCarResultErrorReportFragment> a;

        public a(RouteCarResultErrorReportFragment routeCarResultErrorReportFragment) {
            this.a = new WeakReference<>(routeCarResultErrorReportFragment);
        }

        public final void run() {
            RouteCarResultErrorReportFragment routeCarResultErrorReportFragment = (RouteCarResultErrorReportFragment) this.a.get();
            if (routeCarResultErrorReportFragment != null) {
                RouteCarResultErrorReportFragment.a(routeCarResultErrorReportFragment);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public dfd createPresenter() {
        return new dfd(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.car_error_report_page);
        bty mapView = getMapView();
        if (mapView != null) {
            this.X = mapView.v();
            this.Y = mapView.J();
            this.W = mapView.I();
            this.Z = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
        }
        requestScreenOrientation(1);
    }

    public View getMapSuspendView() {
        ccy suspendWidgetHelper = getSuspendWidgetHelper();
        if (suspendWidgetHelper == null) {
            return null;
        }
        suspendWidgetHelper.a(suspendWidgetHelper.d());
        return new dev(this).getSuspendView();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cannot_pass_category) {
            c(3);
            a(2);
            if (!DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.af)) {
                a(j(), this.S, djs.a(getContext(), this.ab), "B028");
            } else {
                d(0);
            }
        } else if (id == R.id.around_way_category) {
            c(2);
            a(2);
            if (!DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.af)) {
                a(j(), this.S, djs.a(getContext(), this.ab), "B028");
            } else {
                d(1);
            }
        } else if (id == R.id.other_question_category) {
            c(4);
            a(2);
            if (!DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.af)) {
                a(j(), this.S, djs.a(getContext(), this.ab), "B028");
            } else {
                d(2);
            }
        } else if (id == R.id.truck_cannot_pass_category) {
            c(8);
            a(2);
            a(j(), this.S, djs.a(getContext(), this.ab), "B028");
        } else if (id == R.id.truck_around_way_category) {
            c(9);
            a(2);
            a(j(), this.S, djs.a(getContext(), this.ab), "B028");
        } else if (id == R.id.truck_msg_error_category) {
            c(10);
            a(2);
            a(j(), this.S, djs.a(getContext(), this.ab), "B028");
        } else if (id == R.id.truck_other_question_category) {
            c(11);
            a(2);
            a(j(), this.S, djs.a(getContext(), this.ab), "B028");
        }
        if (id == R.id.poi_confirm_choose) {
            if (!b()) {
                this.D.setText(getString(R.string.car_error_radio_do_work));
                this.E.setText(getString(R.string.car_error_radio_single_direction));
                this.F.setText(getString(R.string.car_error_radio_no_turn));
                this.G.setText(getString(R.string.car_error_radio_no_way));
            } else if (this.ab == 8) {
                this.D.setText(getString(R.string.car_error_radio_no_way));
                this.E.setText(getString(R.string.car_error_radio_do_work));
                this.F.setText(getString(R.string.car_error_radio_other));
                this.G.setVisibility(8);
            } else if (this.ab == 9) {
                this.D.setText(getString(R.string.car_error_radio_road_bad));
                this.E.setText(getString(R.string.car_error_radio_road_around));
                this.F.setText(getString(R.string.car_error_radio_other));
                this.G.setVisibility(8);
            } else if (this.ab == 10) {
                this.D.setText(getString(R.string.car_error_radio_limit_pass));
                this.E.setText(getString(R.string.car_error_radio_limit_height));
                this.F.setText(getString(R.string.car_error_radio_limit_width));
                this.G.setText(getString(R.string.car_error_radio_limit_weight));
                this.G.setVisibility(0);
            } else if (this.ab == 11) {
                this.K.setVisibility(8);
            }
            a(5);
            e();
            a(j(), this.S, getString(R.string.car_error_tip_confirm_tip_log_report), "B028");
        } else if (id == R.id.edit_detail_radio_1) {
            b(1);
        } else if (id == R.id.edit_detail_radio_2) {
            b(2);
        } else if (id == R.id.edit_detail_radio_3) {
            b(3);
        } else if (id == R.id.edit_detail_radio_4) {
            b(4);
        } else {
            if (id == R.id.car_error_page_edit_problem_input_layout || id == R.id.car_error_page_edit_problem_input) {
                if (this.N == null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putBoolean("bundle_contact", false);
                    pageBundle.putString("ErrorReportDescriptionInputFragment.hintstring", djs.a(getContext(), this.ab, true));
                    pageBundle.putString("ErrorReportDescriptionInputFragment.inputstring", this.ae);
                    if (this.ab == 3 && this.ad != -1) {
                        pageBundle.putBoolean("bundle_limit_input_count", false);
                    }
                    final dex dex = new dex(getContext(), R.layout.route_line_error_report_description_input_layout, pageBundle);
                    dex.c = new defpackage.dex.a() {
                        public final void a(String str) {
                            RouteCarResultErrorReportFragment.this.dismissViewLayer(dex);
                            RouteCarResultErrorReportFragment.b(RouteCarResultErrorReportFragment.this, str);
                        }
                    };
                    dex.d = pageBundle;
                    showViewLayer(dex);
                    setSoftInputMode(18);
                    this.C.setVisibility(8);
                    this.N = dex;
                }
            } else if ((id == R.id.car_error_page_edit_contact_input_layout || id == R.id.car_error_page_edit_contact_input) && this.N == null) {
                PageBundle pageBundle2 = new PageBundle();
                pageBundle2.putBoolean("bundle_contact", true);
                pageBundle2.putString("ErrorReportDescriptionInputFragment.hintstring", getResources().getString(R.string.car_error_edit_detail_input_contact));
                pageBundle2.putString("ErrorReportDescriptionInputFragment.inputstring", this.I.getText().toString());
                final dex dex2 = new dex(getContext(), R.layout.route_line_error_report_description_input_layout, pageBundle2);
                dex2.c = new defpackage.dex.a() {
                    public final void a(String str) {
                        RouteCarResultErrorReportFragment.this.dismissViewLayer(dex2);
                        RouteCarResultErrorReportFragment.a(RouteCarResultErrorReportFragment.this, str);
                    }
                };
                dex2.d = pageBundle2;
                showViewLayer(dex2);
                setSoftInputMode(18);
                this.C.setVisibility(8);
                this.N = dex2;
            }
        }
    }

    private void b(int i2) {
        while (this.ad == i2 && -1 != i2) {
            i2 = -1;
        }
        Drawable drawable = getResources().getDrawable(R.drawable.car_error_detail_layout_bg);
        int color = getResources().getColor(R.color.f_c_2);
        if (this.ad == 1 || i2 == -1) {
            this.l.findViewById(R.id.edit_detail_radio_1_icon).setVisibility(8);
            this.D.setBackgroundDrawable(drawable);
            this.D.setTextColor(color);
        }
        if (this.ad == 2 || i2 == -1) {
            this.l.findViewById(R.id.edit_detail_radio_2_icon).setVisibility(8);
            this.E.setBackgroundDrawable(drawable);
            this.E.setTextColor(color);
        }
        if (this.ad == 3 || i2 == -1) {
            this.l.findViewById(R.id.edit_detail_radio_3_icon).setVisibility(8);
            this.F.setBackgroundDrawable(drawable);
            this.F.setTextColor(color);
        }
        if (this.ad == 4 || i2 == -1) {
            this.l.findViewById(R.id.edit_detail_radio_4_icon).setVisibility(8);
            this.G.setBackgroundDrawable(drawable);
            this.G.setTextColor(color);
        }
        Drawable drawable2 = getResources().getDrawable(R.drawable.car_error_radio_item_bg);
        int color2 = getResources().getColor(R.color.f_c_1);
        String str = "";
        if (i2 == 1) {
            this.l.findViewById(R.id.edit_detail_radio_1_icon).setVisibility(0);
            this.D.setBackgroundDrawable(drawable2);
            this.D.setTextColor(color2);
            str = this.D.getText().toString();
        } else if (i2 == 2) {
            this.l.findViewById(R.id.edit_detail_radio_2_icon).setVisibility(0);
            this.E.setBackgroundDrawable(drawable2);
            this.E.setTextColor(color2);
            str = this.E.getText().toString();
        } else if (i2 == 3) {
            this.l.findViewById(R.id.edit_detail_radio_3_icon).setVisibility(0);
            this.F.setBackgroundDrawable(drawable2);
            this.F.setTextColor(color2);
            str = this.F.getText().toString();
        } else if (i2 == 4) {
            this.l.findViewById(R.id.edit_detail_radio_4_icon).setVisibility(0);
            this.G.setBackgroundDrawable(drawable2);
            this.G.setTextColor(color2);
            str = this.G.getText().toString();
        }
        if (!b() || !str.equals(getString(R.string.car_error_radio_other))) {
            this.L.setText(R.string.car_error_edit_detail_description);
        } else {
            a(this.L, R.string.car_error_edit_detail_description);
        }
        this.ad = i2;
        f();
    }

    public final boolean b() {
        if ("42".equals(this.S) || SuperId.BIT_2_INDOOR_TAG_HOT.equals(this.S)) {
            this.af = DriveUtil.NAVI_TYPE_TRUCK;
            return true;
        } else if ("47".equals(this.S) || "46".equals(this.S) || "51".equals(this.S)) {
            this.af = DriveUtil.NAVI_TYPE_MOTORBIKE;
            return false;
        } else {
            this.af = "car";
            return false;
        }
    }

    private String j() {
        return b() ? "2" : "1";
    }

    public boolean handleMessage(Message message) {
        int i2 = message.what;
        return false;
    }

    public final void c() {
        switch (this.a) {
            case 1:
                finish();
                return;
            case 2:
                a(1);
                return;
            case 3:
            case 4:
                a(1);
                return;
            case 5:
                com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(getContext());
                aVar.a((CharSequence) getResources().getString(R.string.car_error_edit_detail_give_up)).b((CharSequence) getResources().getString(R.string.car_error_edit_detail_cancel), (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        RouteCarResultErrorReportFragment.this.dismissViewLayer(alertView);
                        RouteCarResultErrorReportFragment.this.M = null;
                    }
                }).a((CharSequence) getResources().getString(R.string.car_error_edit_detail_yes), (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        RouteCarResultErrorReportFragment.this.dismissViewLayer(alertView);
                        RouteCarResultErrorReportFragment.this.M = null;
                        RouteCarResultErrorReportFragment.this.a(2);
                    }
                });
                this.M = aVar.a();
                showViewLayer(this.M);
                this.M.startAnimation();
                break;
        }
    }

    private void c(int i2) {
        this.ac = this.ab;
        this.ab = i2;
        if (this.aa == null || this.aa.b != this.ab) {
            this.aa = new djm();
            this.aa.g = "";
            this.aa.d.x = Integer.MIN_VALUE;
            this.aa.d.y = Integer.MIN_VALUE;
            this.aa.a(getContext(), this.ab);
            if (g()) {
                this.aa.c = djs.b(this.ab);
            }
        }
    }

    public final void d() {
        if (this.e != null && this.f != null) {
            this.f.removeAll();
            this.f.addItem(ErrorReportRoutePointItem.a(RoutePointType.START, this.h));
            this.f.addItem(ErrorReportRoutePointItem.a(RoutePointType.END, this.i));
            if (!(this.af == DriveUtil.NAVI_TYPE_MOTORBIKE || this.j == null || this.j.size() <= 0)) {
                int size = this.j.size();
                if (size == 1) {
                    this.f.addItem(ErrorReportRoutePointItem.a(RoutePointType.MID, this.j.get(0)));
                    return;
                }
                for (int i2 = 0; i2 < size; i2++) {
                    this.f.addItem(ErrorReportRoutePointItem.a(this.j.get(i2), i2));
                }
            }
        }
    }

    public final void e() {
        if (this.aa != null && this.aa.d != null && this.e != null) {
            if (this.e.getSize() >= 0) {
                this.e.removeAll();
            }
            this.e.addItem(dff.a(this.aa));
            if (!(getMapManager() == null || getMapManager().getMapView() == null)) {
                getMapManager().getMapView().R();
            }
        }
    }

    public final void a(int i2) {
        if (1 == i2 || this.aa != null) {
            this.a = i2;
            switch (i2) {
                case 1:
                    this.B.setVisibility(0);
                    this.C.setVisibility(8);
                    this.v.setVisibility(8);
                    this.w.setVisibility(8);
                    this.x.setVisibility(8);
                    this.y.setVisibility(8);
                    this.e.removeAll();
                    this.m.setTitle(getResources().getString(R.string.car_error_title_bar_page_category));
                    this.aa = null;
                    this.ab = -1;
                    this.ac = -1;
                    b(-1);
                    this.ae = "";
                    String a2 = a();
                    if (!TextUtils.isEmpty(a2)) {
                        this.I.setText(a2);
                    } else {
                        this.I.setText("");
                    }
                    if (getSuspendManager() != null) {
                        cdz d2 = getSuspendManager().d();
                        if (d2 != null) {
                            d2.f();
                        }
                    }
                    h();
                    return;
                case 2:
                    this.B.setVisibility(8);
                    this.C.setVisibility(8);
                    this.v.setVisibility(0);
                    this.w.setVisibility(0);
                    this.x.setVisibility(8);
                    ((TextView) this.x.findViewById(R.id.car_error_tip_confirm_text)).setText(djs.b(getContext(), this.ab));
                    this.y.setVisibility(0);
                    this.e.removeAll();
                    f();
                    l();
                    this.m.setTitle(djs.a(getContext(), this.ab));
                    return;
                case 3:
                    this.B.setVisibility(8);
                    this.C.setVisibility(8);
                    this.x.setVisibility(8);
                    this.y.setVisibility(8);
                    return;
                case 4:
                    this.B.setVisibility(8);
                    this.C.setVisibility(8);
                    this.x.setVisibility(0);
                    this.y.setVisibility(8);
                    return;
                case 5:
                    this.B.setVisibility(8);
                    this.C.setVisibility(0);
                    if (this.ab == 8 || this.ab == 9) {
                        this.G.setVisibility(8);
                    }
                    this.v.setVisibility(8);
                    this.w.setVisibility(8);
                    this.x.setVisibility(8);
                    k();
                    switch (this.ab) {
                        case 2:
                            this.K.setVisibility(8);
                            this.L.setText(R.string.car_error_edit_detail_description);
                            break;
                        case 3:
                            this.K.setVisibility(0);
                            this.L.setText(R.string.car_error_edit_detail_description);
                            break;
                        case 4:
                            this.K.setVisibility(8);
                            a(this.L, R.string.car_error_edit_detail_description);
                            break;
                        case 8:
                            this.K.setVisibility(0);
                            this.L.setText(R.string.car_error_edit_detail_description);
                            break;
                        case 9:
                            this.K.setVisibility(0);
                            this.L.setText(R.string.car_error_edit_detail_description);
                            break;
                        case 10:
                            this.K.setVisibility(0);
                            this.L.setText(R.string.car_error_edit_detail_description);
                            break;
                        case 11:
                            this.K.setVisibility(8);
                            a(this.L, R.string.car_error_edit_detail_description);
                            break;
                        default:
                            this.K.setVisibility(8);
                            break;
                    }
                    this.l.findViewById(R.id.select_destination_position_layout).setVisibility(8);
                    this.m.setTitle(getResources().getString(R.string.car_error_title_bar_page_edit_detail));
                    this.z.setText(djs.a(getContext(), this.ab));
                    this.H.setHint(djs.a(getContext(), this.ab, true));
                    this.H.setText(this.ae);
                    f();
                    cde suspendManager = getSuspendManager();
                    if (suspendManager != null) {
                        cdz d3 = suspendManager.d();
                        if (d3 != null) {
                            d3.f();
                        }
                    }
                    h();
                    break;
            }
        }
    }

    private void k() {
        if (this.ac != this.ab) {
            this.D.setSelected(false);
            this.E.setSelected(false);
            this.F.setSelected(false);
            this.G.setSelected(false);
            this.H.setText("");
        }
    }

    private void l() {
        if (this.y != null) {
            this.y.setText(g() ? djs.d(getContext(), this.ab) : djs.c(getContext(), this.ab));
        }
    }

    public final void f() {
        if (this.J != null) {
            if (!n()) {
                this.J.setBackgroundDrawable(getResources().getDrawable(R.drawable.car_error_submit_invalid_bg));
                this.J.setTextColor(getResources().getColor(R.color.f_c_1_a));
                return;
            }
            this.J.setBackgroundDrawable(getResources().getDrawable(R.drawable.car_error_submit_selector));
            this.J.setTextColor(getResources().getColor(R.color.f_c_1));
        }
    }

    private boolean m() {
        if (this.I == null) {
            return true;
        }
        CharSequence text = this.I.getText();
        if (TextUtils.isEmpty(text)) {
            return true;
        }
        if (text.length() != 11 || '1' != text.charAt(0)) {
            return false;
        }
        for (int i2 = 0; i2 < text.length(); i2++) {
            if (!Character.isDigit(text.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:84:0x013d A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean n() {
        /*
            r7 = this;
            djm r0 = r7.aa
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            int r0 = r7.ab
            r2 = -1
            r3 = 5
            r4 = 1
            switch(r0) {
                case 2: goto L_0x0119;
                case 3: goto L_0x00e9;
                case 4: goto L_0x00c5;
                case 5: goto L_0x000e;
                case 6: goto L_0x000e;
                case 7: goto L_0x000e;
                case 8: goto L_0x0063;
                case 9: goto L_0x0063;
                case 10: goto L_0x002d;
                case 11: goto L_0x000f;
                default: goto L_0x000e;
            }
        L_0x000e:
            return r1
        L_0x000f:
            android.widget.TextView r0 = r7.H
            java.lang.CharSequence r0 = r0.getText()
            if (r0 == 0) goto L_0x002c
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x002c
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x002c
            return r4
        L_0x002c:
            return r1
        L_0x002d:
            android.widget.TextView r0 = r7.H
            java.lang.CharSequence r0 = r0.getText()
            int r5 = r7.ad
            if (r5 == r2) goto L_0x004e
            if (r0 == 0) goto L_0x004d
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x004d
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x013d
        L_0x004d:
            return r4
        L_0x004e:
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x013d
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x013d
            return r4
        L_0x0063:
            android.widget.TextView r0 = r7.H
            java.lang.CharSequence r0 = r0.getText()
            android.widget.TextView r5 = r7.L
            if (r5 == 0) goto L_0x0095
            android.widget.TextView r5 = r7.L
            java.lang.CharSequence r5 = r5.getText()
            java.lang.String r5 = r5.toString()
            java.lang.String r6 = "＊"
            boolean r5 = r5.contains(r6)
            if (r5 == 0) goto L_0x0095
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x013d
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x013d
            return r4
        L_0x0095:
            int r5 = r7.ad
            if (r5 == r2) goto L_0x00b0
            if (r0 == 0) goto L_0x00af
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x00af
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x013d
        L_0x00af:
            return r4
        L_0x00b0:
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x013d
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x013d
            return r4
        L_0x00c5:
            boolean r0 = r7.m()
            if (r0 != 0) goto L_0x00cc
            return r1
        L_0x00cc:
            android.widget.TextView r0 = r7.H
            java.lang.CharSequence r0 = r0.getText()
            if (r0 == 0) goto L_0x013d
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x013d
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x013d
            return r4
        L_0x00e9:
            int r0 = r7.ad
            if (r0 == r2) goto L_0x00f5
            boolean r0 = r7.m()
            if (r0 != 0) goto L_0x00f4
            return r1
        L_0x00f4:
            return r4
        L_0x00f5:
            boolean r0 = r7.m()
            if (r0 != 0) goto L_0x00fc
            return r1
        L_0x00fc:
            android.widget.TextView r0 = r7.H
            java.lang.CharSequence r0 = r0.getText()
            if (r0 == 0) goto L_0x013d
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x013d
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x013d
            return r4
        L_0x0119:
            boolean r0 = r7.m()
            if (r0 != 0) goto L_0x0120
            return r1
        L_0x0120:
            android.widget.TextView r0 = r7.H
            java.lang.CharSequence r0 = r0.getText()
            if (r0 == 0) goto L_0x013e
            java.lang.String r2 = r0.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x013e
            java.lang.String r0 = r0.toString()
            int r0 = r0.length()
            if (r0 < r3) goto L_0x013d
            goto L_0x013e
        L_0x013d:
            return r1
        L_0x013e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.errorreport.RouteCarResultErrorReportFragment.n():boolean");
    }

    private static void d(int i2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", i2);
        } catch (Exception unused) {
        }
        LogManager.actionLogV2("P00437", "B002", jSONObject);
    }

    public final boolean g() {
        if (!TextUtils.isEmpty(this.af)) {
            return DriveUtil.NAVI_TYPE_MOTORBIKE.equals(this.af);
        }
        return "47".equals(this.S) || "46".equals(this.S) || "51".equals(this.S);
    }

    public final void h() {
        int i2;
        int i3;
        int i4;
        int i5;
        if (this.d != null && getMapManager() != null) {
            NavigationResult navigationResult = null;
            if (this.b != null) {
                tn.a();
                if (tn.a(this.b)) {
                    navigationResult = this.b.getNaviResultData();
                }
            }
            if (navigationResult != null) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int i6 = displayMetrics.widthPixels;
                int d2 = displayMetrics.heightPixels - ags.d(getContext());
                int a2 = agn.a(getContext(), 51.0f);
                int a3 = agn.a(getContext(), 51.0f);
                if (this.m == null || this.m.getVisibility() != 0) {
                    i3 = 0;
                    i2 = 0;
                } else {
                    if (this.m.getMeasuredHeight() <= 0) {
                        this.m.measure(0, 0);
                    }
                    i3 = this.m.getMeasuredHeight() + 0;
                    i2 = this.m.getMeasuredHeight() + 0;
                }
                int intrinsicHeight = i3 + getResources().getDrawable(R.drawable.bubble_start).getIntrinsicHeight() + agn.a(getContext(), 8.0f);
                if (this.A == null || this.A.getVisibility() != 0) {
                    i5 = 0;
                    i4 = 0;
                } else {
                    if (this.A.getMeasuredHeight() <= 0) {
                        this.A.measure(0, 0);
                    }
                    i5 = this.A.getMeasuredHeight() + 0;
                    i4 = this.A.getMeasuredHeight() + 0;
                }
                defpackage.dif.a a4 = new defpackage.dif.a().a(navigationResult.maxBound, a2 + agn.a(getContext(), (float) ag[0]), intrinsicHeight + agn.a(getContext(), (float) ag[1]), a3 + agn.a(getContext(), (float) ag[2]), i5 + agn.a(getContext(), 6.0f) + agn.a(getContext(), (float) ag[3]));
                int i7 = (((d2 - i2) - i4) / 2) + i2;
                defpackage.dif.a a5 = a4.a(getMapManager().getMapView(), i6, d2, i6 / 2, i7);
                a5.f = 0;
                a5.a().b();
                float d3 = a4.a().a.d();
                GLOverlay gLOverlay = this.d.getGLOverlay();
                if (gLOverlay instanceof GLRouteOverlay) {
                    ti.a((int) d3, (GLRouteOverlay) gLOverlay);
                }
            }
        }
    }

    private static void a(String str, String str2, String str3, String str4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", str);
            jSONObject.put("from", str2);
            jSONObject.put(TrafficUtil.KEYWORD, str3);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00072", str4, jSONObject);
    }

    private void a(TextView textView, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(i2));
        sb.append("＊");
        String sb2 = sb.toString();
        int length = sb2.length();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(sb2);
        spannableStringBuilder.setSpan(this.U, length - 1, length, 33);
        textView.setText(spannableStringBuilder);
    }

    public static String a() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e2 = iAccountService.e();
        if (e2 == null) {
            return "";
        }
        return e2.h;
    }

    static /* synthetic */ void a(RouteCarResultErrorReportFragment routeCarResultErrorReportFragment) {
        if ((3 == routeCarResultErrorReportFragment.a || routeCarResultErrorReportFragment.a == 4) && routeCarResultErrorReportFragment.aa != null && routeCarResultErrorReportFragment.getMapManager() != null && routeCarResultErrorReportFragment.getMapManager().getMapView() != null) {
            routeCarResultErrorReportFragment.O.post(new Runnable() {
                public final void run() {
                    if (RouteCarResultErrorReportFragment.this.v != null && RouteCarResultErrorReportFragment.this.k != null) {
                        RouteCarResultErrorReportFragment.this.a(4);
                        RouteCarResultErrorReportFragment.this.v.clearAnimation();
                        RouteCarResultErrorReportFragment.this.v.startAnimation(RouteCarResultErrorReportFragment.this.k);
                        RouteCarResultErrorReportFragment.this.k.startNow();
                    }
                }
            });
            final int left = (routeCarResultErrorReportFragment.w.getLeft() + routeCarResultErrorReportFragment.w.getRight()) / 2;
            final int top = (routeCarResultErrorReportFragment.w.getTop() + routeCarResultErrorReportFragment.w.getBottom()) / 2;
            GeoPoint geoPoint = new GeoPoint(routeCarResultErrorReportFragment.getMapManager().getMapView().c(left, top));
            if (routeCarResultErrorReportFragment.aa.a()) {
                djp djp = new djp();
                AnonymousClass1 r5 = new defpackage.djp.a() {
                    public final void a(final GeoPoint geoPoint) {
                        if (geoPoint != null && geoPoint.x != 0 && geoPoint.y != 0 && RouteCarResultErrorReportFragment.this.aa != null) {
                            RouteCarResultErrorReportFragment.this.O.post(new Runnable() {
                                public final void run() {
                                    bty mapView = RouteCarResultErrorReportFragment.this.getMapManager() == null ? null : RouteCarResultErrorReportFragment.this.getMapManager().getMapView();
                                    if (mapView != null) {
                                        mapView.b(geoPoint.x, geoPoint.y, left, top);
                                    }
                                }
                            });
                            RouteCarResultErrorReportFragment.this.aa.d = geoPoint;
                        }
                    }
                };
                if (djp.a != null) {
                    djp.a.add(r5);
                }
                djp.execute(new Object[]{routeCarResultErrorReportFragment.c, geoPoint});
                return;
            }
            routeCarResultErrorReportFragment.aa.d = geoPoint;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:108:0x01b6, code lost:
        if (r4.equals("51") != false) goto L_0x01ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x01f1, code lost:
        if (r4.equals("51") != false) goto L_0x01f5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x0228, code lost:
        if (r4.equals("51") != false) goto L_0x022c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00fe, code lost:
        if (r4.equals("51") != false) goto L_0x0102;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0140, code lost:
        if (r4.equals("51") != false) goto L_0x0144;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x017b, code lost:
        if (r4.equals("51") != false) goto L_0x017f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void e(com.autonavi.minimap.drive.errorreport.RouteCarResultErrorReportFragment r9) {
        /*
            boolean r0 = r9.n()
            if (r0 == 0) goto L_0x0294
            java.lang.String r0 = ""
            boolean r1 = r9.b()
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L_0x008c
            int r1 = r9.ab
            r4 = 3
            switch(r1) {
                case 8: goto L_0x006b;
                case 9: goto L_0x0049;
                case 10: goto L_0x0018;
                default: goto L_0x0016;
            }
        L_0x0016:
            goto L_0x00bd
        L_0x0018:
            int r1 = r9.ad
            if (r1 != r3) goto L_0x0024
            int r0 = com.autonavi.minimap.R.string.car_error_radio_limit_pass
            java.lang.String r0 = r9.getString(r0)
            goto L_0x00bd
        L_0x0024:
            int r1 = r9.ad
            if (r1 != r2) goto L_0x0030
            int r0 = com.autonavi.minimap.R.string.car_error_radio_limit_height
            java.lang.String r0 = r9.getString(r0)
            goto L_0x00bd
        L_0x0030:
            int r1 = r9.ad
            if (r1 != r4) goto L_0x003c
            int r0 = com.autonavi.minimap.R.string.car_error_radio_limit_width
            java.lang.String r0 = r9.getString(r0)
            goto L_0x00bd
        L_0x003c:
            int r1 = r9.ad
            r4 = 4
            if (r1 != r4) goto L_0x00bd
            int r0 = com.autonavi.minimap.R.string.car_error_radio_limit_weight
            java.lang.String r0 = r9.getString(r0)
            goto L_0x00bd
        L_0x0049:
            int r1 = r9.ad
            if (r1 != r3) goto L_0x0055
            int r0 = com.autonavi.minimap.R.string.car_error_radio_road_bad
            java.lang.String r0 = r9.getString(r0)
            goto L_0x00bd
        L_0x0055:
            int r1 = r9.ad
            if (r1 != r2) goto L_0x0060
            int r0 = com.autonavi.minimap.R.string.car_error_radio_road_around
            java.lang.String r0 = r9.getString(r0)
            goto L_0x00bd
        L_0x0060:
            int r1 = r9.ad
            if (r1 != r4) goto L_0x00bd
            int r0 = com.autonavi.minimap.R.string.car_error_radio_other
            java.lang.String r0 = r9.getString(r0)
            goto L_0x00bd
        L_0x006b:
            int r1 = r9.ad
            if (r1 != r3) goto L_0x0076
            int r0 = com.autonavi.minimap.R.string.car_error_radio_no_way
            java.lang.String r0 = r9.getString(r0)
            goto L_0x00bd
        L_0x0076:
            int r1 = r9.ad
            if (r1 != r2) goto L_0x0081
            int r0 = com.autonavi.minimap.R.string.car_error_radio_do_work
            java.lang.String r0 = r9.getString(r0)
            goto L_0x00bd
        L_0x0081:
            int r1 = r9.ad
            if (r1 != r4) goto L_0x00bd
            int r0 = com.autonavi.minimap.R.string.car_error_radio_other
            java.lang.String r0 = r9.getString(r0)
            goto L_0x00bd
        L_0x008c:
            int r1 = r9.ad
            switch(r1) {
                case 1: goto L_0x00b3;
                case 2: goto L_0x00a8;
                case 3: goto L_0x009d;
                case 4: goto L_0x0092;
                default: goto L_0x0091;
            }
        L_0x0091:
            goto L_0x00bd
        L_0x0092:
            android.content.res.Resources r0 = r9.getResources()
            int r1 = com.autonavi.minimap.R.string.car_error_radio_no_way
            java.lang.String r0 = r0.getString(r1)
            goto L_0x00bd
        L_0x009d:
            android.content.res.Resources r0 = r9.getResources()
            int r1 = com.autonavi.minimap.R.string.car_error_radio_no_turn
            java.lang.String r0 = r0.getString(r1)
            goto L_0x00bd
        L_0x00a8:
            android.content.res.Resources r0 = r9.getResources()
            int r1 = com.autonavi.minimap.R.string.car_error_radio_single_direction
            java.lang.String r0 = r0.getString(r1)
            goto L_0x00bd
        L_0x00b3:
            android.content.res.Resources r0 = r9.getResources()
            int r1 = com.autonavi.minimap.R.string.car_error_radio_do_work
            java.lang.String r0 = r0.getString(r1)
        L_0x00bd:
            boolean r1 = r9.g()
            if (r1 == 0) goto L_0x023a
            djm r1 = r9.aa
            boolean r4 = r9.g()
            r5 = 0
            if (r4 != 0) goto L_0x00ce
            goto L_0x0238
        L_0x00ce:
            int r4 = r9.ab
            r6 = 1692(0x69c, float:2.371E-42)
            r7 = -1
            switch(r4) {
                case 2: goto L_0x0202;
                case 3: goto L_0x0113;
                case 4: goto L_0x00d8;
                default: goto L_0x00d6;
            }
        L_0x00d6:
            goto L_0x0238
        L_0x00d8:
            java.lang.String r4 = r9.S
            int r8 = r4.hashCode()
            if (r8 == r6) goto L_0x00f8
            switch(r8) {
                case 1666: goto L_0x00ee;
                case 1667: goto L_0x00e4;
                default: goto L_0x00e3;
            }
        L_0x00e3:
            goto L_0x0101
        L_0x00e4:
            java.lang.String r2 = "47"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0101
            r2 = 0
            goto L_0x0102
        L_0x00ee:
            java.lang.String r2 = "46"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0101
            r2 = 1
            goto L_0x0102
        L_0x00f8:
            java.lang.String r3 = "51"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x0101
            goto L_0x0102
        L_0x0101:
            r2 = -1
        L_0x0102:
            switch(r2) {
                case 0: goto L_0x010f;
                case 1: goto L_0x010b;
                case 2: goto L_0x0107;
                default: goto L_0x0105;
            }
        L_0x0105:
            goto L_0x0238
        L_0x0107:
            r5 = 5106(0x13f2, float:7.155E-42)
            goto L_0x0238
        L_0x010b:
            r5 = 4606(0x11fe, float:6.454E-42)
            goto L_0x0238
        L_0x010f:
            r5 = 4706(0x1262, float:6.595E-42)
            goto L_0x0238
        L_0x0113:
            int r4 = r9.ad
            switch(r4) {
                case 1: goto L_0x01cb;
                case 2: goto L_0x0190;
                case 3: goto L_0x0155;
                case 4: goto L_0x011a;
                default: goto L_0x0118;
            }
        L_0x0118:
            goto L_0x0238
        L_0x011a:
            java.lang.String r4 = r9.S
            int r8 = r4.hashCode()
            if (r8 == r6) goto L_0x013a
            switch(r8) {
                case 1666: goto L_0x0130;
                case 1667: goto L_0x0126;
                default: goto L_0x0125;
            }
        L_0x0125:
            goto L_0x0143
        L_0x0126:
            java.lang.String r2 = "47"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0143
            r2 = 0
            goto L_0x0144
        L_0x0130:
            java.lang.String r2 = "46"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0143
            r2 = 1
            goto L_0x0144
        L_0x013a:
            java.lang.String r3 = "51"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x0143
            goto L_0x0144
        L_0x0143:
            r2 = -1
        L_0x0144:
            switch(r2) {
                case 0: goto L_0x0151;
                case 1: goto L_0x014d;
                case 2: goto L_0x0149;
                default: goto L_0x0147;
            }
        L_0x0147:
            goto L_0x0238
        L_0x0149:
            r5 = 5105(0x13f1, float:7.154E-42)
            goto L_0x0238
        L_0x014d:
            r5 = 4605(0x11fd, float:6.453E-42)
            goto L_0x0238
        L_0x0151:
            r5 = 4705(0x1261, float:6.593E-42)
            goto L_0x0238
        L_0x0155:
            java.lang.String r4 = r9.S
            int r8 = r4.hashCode()
            if (r8 == r6) goto L_0x0175
            switch(r8) {
                case 1666: goto L_0x016b;
                case 1667: goto L_0x0161;
                default: goto L_0x0160;
            }
        L_0x0160:
            goto L_0x017e
        L_0x0161:
            java.lang.String r2 = "47"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x017e
            r2 = 0
            goto L_0x017f
        L_0x016b:
            java.lang.String r2 = "46"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x017e
            r2 = 1
            goto L_0x017f
        L_0x0175:
            java.lang.String r3 = "51"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x017e
            goto L_0x017f
        L_0x017e:
            r2 = -1
        L_0x017f:
            switch(r2) {
                case 0: goto L_0x018c;
                case 1: goto L_0x0188;
                case 2: goto L_0x0184;
                default: goto L_0x0182;
            }
        L_0x0182:
            goto L_0x0238
        L_0x0184:
            r5 = 5104(0x13f0, float:7.152E-42)
            goto L_0x0238
        L_0x0188:
            r5 = 4604(0x11fc, float:6.452E-42)
            goto L_0x0238
        L_0x018c:
            r5 = 4704(0x1260, float:6.592E-42)
            goto L_0x0238
        L_0x0190:
            java.lang.String r4 = r9.S
            int r8 = r4.hashCode()
            if (r8 == r6) goto L_0x01b0
            switch(r8) {
                case 1666: goto L_0x01a6;
                case 1667: goto L_0x019c;
                default: goto L_0x019b;
            }
        L_0x019b:
            goto L_0x01b9
        L_0x019c:
            java.lang.String r2 = "47"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x01b9
            r2 = 0
            goto L_0x01ba
        L_0x01a6:
            java.lang.String r2 = "46"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x01b9
            r2 = 1
            goto L_0x01ba
        L_0x01b0:
            java.lang.String r3 = "51"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x01b9
            goto L_0x01ba
        L_0x01b9:
            r2 = -1
        L_0x01ba:
            switch(r2) {
                case 0: goto L_0x01c7;
                case 1: goto L_0x01c3;
                case 2: goto L_0x01bf;
                default: goto L_0x01bd;
            }
        L_0x01bd:
            goto L_0x0238
        L_0x01bf:
            r5 = 5103(0x13ef, float:7.151E-42)
            goto L_0x0238
        L_0x01c3:
            r5 = 4603(0x11fb, float:6.45E-42)
            goto L_0x0238
        L_0x01c7:
            r5 = 4703(0x125f, float:6.59E-42)
            goto L_0x0238
        L_0x01cb:
            java.lang.String r4 = r9.S
            int r8 = r4.hashCode()
            if (r8 == r6) goto L_0x01eb
            switch(r8) {
                case 1666: goto L_0x01e1;
                case 1667: goto L_0x01d7;
                default: goto L_0x01d6;
            }
        L_0x01d6:
            goto L_0x01f4
        L_0x01d7:
            java.lang.String r2 = "47"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x01f4
            r2 = 0
            goto L_0x01f5
        L_0x01e1:
            java.lang.String r2 = "46"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x01f4
            r2 = 1
            goto L_0x01f5
        L_0x01eb:
            java.lang.String r3 = "51"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x01f4
            goto L_0x01f5
        L_0x01f4:
            r2 = -1
        L_0x01f5:
            switch(r2) {
                case 0: goto L_0x01ff;
                case 1: goto L_0x01fc;
                case 2: goto L_0x01f9;
                default: goto L_0x01f8;
            }
        L_0x01f8:
            goto L_0x0238
        L_0x01f9:
            r5 = 5102(0x13ee, float:7.15E-42)
            goto L_0x0238
        L_0x01fc:
            r5 = 4602(0x11fa, float:6.449E-42)
            goto L_0x0238
        L_0x01ff:
            r5 = 4702(0x125e, float:6.589E-42)
            goto L_0x0238
        L_0x0202:
            java.lang.String r4 = r9.S
            int r8 = r4.hashCode()
            if (r8 == r6) goto L_0x0222
            switch(r8) {
                case 1666: goto L_0x0218;
                case 1667: goto L_0x020e;
                default: goto L_0x020d;
            }
        L_0x020d:
            goto L_0x022b
        L_0x020e:
            java.lang.String r2 = "47"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x022b
            r2 = 0
            goto L_0x022c
        L_0x0218:
            java.lang.String r2 = "46"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x022b
            r2 = 1
            goto L_0x022c
        L_0x0222:
            java.lang.String r3 = "51"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x022b
            goto L_0x022c
        L_0x022b:
            r2 = -1
        L_0x022c:
            switch(r2) {
                case 0: goto L_0x0236;
                case 1: goto L_0x0233;
                case 2: goto L_0x0230;
                default: goto L_0x022f;
            }
        L_0x022f:
            goto L_0x0238
        L_0x0230:
            r5 = 5101(0x13ed, float:7.148E-42)
            goto L_0x0238
        L_0x0233:
            r5 = 4601(0x11f9, float:6.447E-42)
            goto L_0x0238
        L_0x0236:
            r5 = 4701(0x125d, float:6.588E-42)
        L_0x0238:
            r1.a = r5
        L_0x023a:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x0263
            java.lang.String r1 = r9.ae
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0263
            djm r1 = r9.aa
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            java.lang.String r0 = "#"
            r2.append(r0)
            java.lang.String r0 = r9.ae
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.g = r0
            goto L_0x0278
        L_0x0263:
            djm r1 = r9.aa
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            java.lang.String r0 = r9.ae
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.g = r0
        L_0x0278:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            djm r1 = r9.aa
            r0.add(r1)
            com.autonavi.map.core.MapManager r1 = r9.getMapManager()
            if (r1 == 0) goto L_0x0294
            cfc r2 = defpackage.cfc.a()
            com.autonavi.minimap.drive.errorreport.RouteCarResultErrorReportFragment$2 r3 = new com.autonavi.minimap.drive.errorreport.RouteCarResultErrorReportFragment$2
            r3.<init>(r0)
            r2.a(r1, r3)
        L_0x0294:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.errorreport.RouteCarResultErrorReportFragment.e(com.autonavi.minimap.drive.errorreport.RouteCarResultErrorReportFragment):void");
    }

    static /* synthetic */ void a(RouteCarResultErrorReportFragment routeCarResultErrorReportFragment, String str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        routeCarResultErrorReportFragment.I.setText(str);
        routeCarResultErrorReportFragment.f();
        routeCarResultErrorReportFragment.C.clearAnimation();
        routeCarResultErrorReportFragment.C.setVisibility(0);
        routeCarResultErrorReportFragment.N = null;
    }

    static /* synthetic */ void b(RouteCarResultErrorReportFragment routeCarResultErrorReportFragment, String str) {
        routeCarResultErrorReportFragment.ae = str;
        if (TextUtils.isEmpty(str)) {
            routeCarResultErrorReportFragment.H.setText("");
        } else {
            routeCarResultErrorReportFragment.H.setText(str);
        }
        routeCarResultErrorReportFragment.f();
        routeCarResultErrorReportFragment.C.clearAnimation();
        routeCarResultErrorReportFragment.C.setVisibility(0);
        routeCarResultErrorReportFragment.N = null;
    }
}
