package com.amap.bundle.drive.result.motorresult.result.tip;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.drive.result.tip.util.RouteCarResultTipUtil;
import com.amap.bundle.drive.result.tip.util.RouteCarResultTipUtil.TipType;
import com.amap.bundle.drive.result.tip.view.TipsView;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.jni.ae.route.model.RouteIncident;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.BalloonText;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;

public class AjxMotorTipsManager implements com.amap.bundle.drive.result.tip.view.TipsView.a {
    public static final String a = "AjxMotorTipsManager";
    public ArrayList<px> b;
    public TipPriorityComparator c;
    public ViewGroup d;
    public TipsView e;
    public View f;
    public ViewGroup g;
    public BalloonText h;
    public boolean i = false;
    public Context j;
    public rb k;
    public a l;
    public ph m;
    public pg n;
    public List<pk> o;
    public RouteIncident p;
    public RouteIncident q;
    public boolean r = false;
    public px<sn> s;
    public int t;
    public boolean u = false;
    private ValueAnimator v;
    private final int w = 2;

    public static class TipPriorityComparator implements Serializable, Comparator {
        private TipPriorityComparator() {
        }

        public /* synthetic */ TipPriorityComparator(byte b) {
            this();
        }

        public int compare(Object obj, Object obj2) {
            return ((px) obj).d - ((px) obj2).d <= 0.0f ? 1 : -1;
        }
    }

    public interface a extends com.amap.bundle.drive.result.tip.view.TipsView.a {
        void a(int i);
    }

    public AjxMotorTipsManager(Context context, IRouteUI iRouteUI, boolean z) {
        this.j = context;
        this.b = new ArrayList<>();
        View inflate = LayoutInflater.from(this.j).inflate(R.layout.view_driveresult_tips, null);
        this.d = (ViewGroup) inflate.findViewById(R.id.route_car_result_tips_holder);
        this.g = (ViewGroup) inflate.findViewById(R.id.tips_bg_mask);
        LayoutParams layoutParams = (LayoutParams) this.d.getLayoutParams();
        layoutParams.topMargin = z ? agn.a(context, 54.0f) : iRouteUI.m();
        this.d.setLayoutParams(layoutParams);
        iRouteUI.a(inflate);
        View inflate2 = LayoutInflater.from(this.j).inflate(R.layout.route_car_result_foot_tips, null);
        LayoutParams layoutParams2 = new LayoutParams(-2, -2);
        layoutParams2.addRule(14);
        layoutParams2.topMargin = z ? agn.a(context, 54.0f) : iRouteUI.m() + 10;
        inflate2.setLayoutParams(layoutParams2);
        iRouteUI.a(inflate2);
        this.h = (BalloonText) inflate2.findViewById(R.id.car_too_long_warning);
    }

    public final void a() {
        if (this.h != null) {
            this.h.hide();
        }
    }

    public final void b() {
        if (this.h != null) {
            this.h.hide();
        }
        if (this.m != null && this.m.h != null && this.m.g != null && ((this.m.i == null || this.m.i.size() <= 0) && ((double) cfe.a(this.m.h.getPoint(), this.m.g.getPoint())) * 1.72d < 200.0d)) {
            String type = this.m.h.getType();
            LinkedList<String> linkedList = new LinkedList<>();
            if (!TextUtils.isEmpty(type)) {
                if (type.contains(MergeUtil.SEPARATOR_KV)) {
                    String[] split = type.split("\\|");
                    if (split != null && split.length > 0) {
                        for (String add : split) {
                            linkedList.add(add);
                        }
                    }
                } else {
                    linkedList.add(type);
                }
            }
            for (String str : linkedList) {
                if (!str.startsWith("0101") && !str.startsWith("0102") && !str.startsWith("0103") && !str.startsWith("0104") && !str.startsWith("0105") && !str.startsWith("03") && !str.startsWith("1509")) {
                    if (str.startsWith("18")) {
                        return;
                    }
                } else {
                    return;
                }
            }
            if (this.h != null) {
                this.h.show(5000);
            }
            if (this.h != null) {
                this.h.hide();
            }
        }
    }

    public final void a(int i2) {
        this.d.setTranslationY((float) i2);
    }

    public final boolean c() {
        return this.d.getChildCount() > 0 && this.d.isShown();
    }

    public final int d() {
        if (this.b != null) {
            return this.b.size();
        }
        return 0;
    }

    public static RouteIncident a(List<RouteIncident> list, List<RouteIncident> list2) {
        int i2;
        int i3;
        if (list == null || list.size() <= 0) {
            i3 = -1;
            i2 = -1;
        } else {
            i3 = 0;
            int i4 = -1;
            i2 = -1;
            while (true) {
                if (i3 >= list.size()) {
                    i3 = i4;
                    break;
                }
                RouteIncident routeIncident = list.get(i3);
                if (routeIncident != null && !TextUtils.isEmpty(routeIncident.title)) {
                    int b2 = b(RouteCarResultTipUtil.b(routeIncident.tipsType));
                    if (b2 == 4) {
                        i2 = b2;
                        break;
                    } else if (b2 > i2) {
                        i4 = i3;
                        i2 = b2;
                    }
                }
                i3++;
            }
            if (i3 != -1) {
                return list.get(i3);
            }
        }
        if (list2 != null && list2.size() > 0) {
            for (int i5 = 0; i5 < list2.size(); i5++) {
                RouteIncident routeIncident2 = list2.get(i5);
                if (routeIncident2 != null && !TextUtils.isEmpty(routeIncident2.title)) {
                    int b3 = b(RouteCarResultTipUtil.b(routeIncident2.tipsType));
                    if (b3 > i2) {
                        i2 = b3;
                        i3 = i5;
                    }
                }
            }
            if (i3 != -1) {
                return list2.get(i3);
            }
        }
        return null;
    }

    public static String a(RouteIncident routeIncident) {
        if (TextUtils.isEmpty(routeIncident.title)) {
            return null;
        }
        if (4 != routeIncident.tipsType) {
            return routeIncident.title;
        }
        String str = routeIncident.title;
        if (str.endsWith(".") || str.endsWith("。") || str.endsWith(",")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str.substring(0, str.length() - 1));
            sb.append(AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_already_avoid_incident_with_comma));
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_already_avoid_incident_with_comma));
        return sb2.toString();
    }

    private static int b(TipType tipType) {
        if (tipType == TipType.INCIDENT_UNAVOIDABLE_INCIDENT_AT_END) {
            return 4;
        }
        if (tipType == TipType.INCIDENT_UNAVOIDABLE_INCIDENT_AT_MID) {
            return 3;
        }
        if (tipType == TipType.INCIDENT_UNAVOIDABLE_INCIDENT_AT_START) {
            return 2;
        }
        if (tipType == TipType.INCIDENT_UNAVOIDABLE_INCIDENT_AT_OTHER) {
            return 1;
        }
        return tipType == TipType.INCIDENT_AVOIDABLE_INCIDENT ? 0 : -1;
    }

    public static String a(String str, String str2) {
        return !TextUtils.isEmpty(str) ? str : str2;
    }

    public static boolean a(TipType tipType) {
        if (tipType != TipType.RESTRICT_OTHER_PLACE_SET_PLATE && tipType != TipType.RESTRICT_OTHER_PLACE_OPEN_RESTRICT_SWITCH) {
            return true;
        }
        if (tipType == TipType.RESTRICT_OTHER_PLACE_SET_PLATE && DriveUtil.needShowMotorPlateSetting()) {
            return true;
        }
        if (tipType != TipType.RESTRICT_OTHER_PLACE_OPEN_RESTRICT_SWITCH || !DriveUtil.needShowMotorPlateOpenAvoidLimitedNotice()) {
            return false;
        }
        return true;
    }

    public final boolean e() {
        sq sqVar = this.n.o;
        if (sqVar == null) {
            return false;
        }
        int i2 = sqVar.e;
        boolean z = true;
        if (!(i2 == -1 || i2 == 0 || i2 == 1)) {
            TipType a2 = RouteCarResultTipUtil.a(i2);
            if ((TipType.RESTRICT_ALREADY_AVOID_RESTRICT_AREA == a2 || TipType.RESTRICT_SOON_EFFECT_AVOID_RESTRICT_AREA == a2 || TipType.RESTRICT_SOON_END_ACROSS_RESTRICT_AREA == a2) && (this.n.q == null || this.n.q.c() > 2)) {
                z = false;
            }
        }
        return z;
    }

    public final boolean f() {
        if (this.n == null || this.n.p == null || this.n.p.type != 1) {
            return false;
        }
        return true;
    }

    public static void a(TipsView tipsView) {
        LayoutParams layoutParams = (LayoutParams) tipsView.getLayoutParams();
        layoutParams.topMargin = tipsView.getResources().getDimensionPixelOffset(R.dimen.tips_vertical_margin);
        tipsView.setLayoutParams(layoutParams);
    }

    public final int g() {
        if (this.d == null || this.d.getVisibility() != 0 || this.e == null || this.i || this.e.getVisibility() != 0) {
            return 0;
        }
        if (this.e.getMeasuredHeight() <= 0) {
            this.e.measure(0, 0);
        }
        return this.e.getMeasuredHeight();
    }

    private void l() {
        this.d.setVisibility(8);
        if (this.f != null) {
            this.f.setVisibility(0);
        }
    }

    private boolean m() {
        return this.t == RouteType.MOTOR.getValue();
    }

    public final void h() {
        if (this.d != null) {
            this.b.clear();
            if (this.v != null && this.v.isRunning()) {
                this.v.cancel();
            }
            if (this.e != null) {
                this.e.setVisibility(8);
            }
            if (this.f != null) {
                this.f.setVisibility(4);
            }
            this.d.removeAllViews();
            this.m = null;
            this.n = null;
            this.o = null;
            this.p = null;
            this.q = null;
            if (this.k != null) {
                this.k.a();
                this.r = false;
            }
        }
    }

    public final void a(Float f2) {
        this.d.setAlpha(f2.floatValue());
        this.h.setAlpha(f2.floatValue());
    }

    /* access modifiers changed from: private */
    public void n() {
        if (this.b.size() > 1) {
            TextView textView = (TextView) this.f.findViewById(R.id.tips_unread_count);
            int size = this.b.size() - 1;
            if (size >= 3) {
                size = 2;
            }
            textView.setText(String.valueOf(size));
        }
    }

    /* access modifiers changed from: private */
    public void o() {
        this.v = this.e.getZoomOutAnimation();
        this.v.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Rect rect = (Rect) valueAnimator.getAnimatedValue();
                if (AjxMotorTipsManager.this.e != null) {
                    AjxMotorTipsManager.this.e.layout(rect.left, rect.top, rect.right, rect.bottom);
                    AjxMotorTipsManager.this.e.invalidate();
                }
            }
        });
        this.v.addListener(new AnimatorListener() {
            public final void onAnimationCancel(Animator animator) {
            }

            public final void onAnimationRepeat(Animator animator) {
            }

            public final void onAnimationStart(Animator animator) {
            }

            public final void onAnimationEnd(Animator animator) {
                AjxMotorTipsManager.this.j();
                AjxMotorTipsManager.e(AjxMotorTipsManager.this);
            }
        });
        this.v.start();
    }

    public final void i() {
        Animation loadAnimation = AnimationUtils.loadAnimation(this.j, R.anim.tips_list_hiding);
        this.d.setAnimation(loadAnimation);
        this.d.startAnimation(loadAnimation);
    }

    public final void j() {
        if (this.k != null) {
            this.k.a();
            this.r = false;
        }
        this.d.removeAllViews();
        if (this.b != null && this.b.size() > 0) {
            l();
        }
        if (this.l != null && this.b != null && this.b.size() > 0) {
            this.l.a(this.b.get(0).a);
        }
    }

    public final void k() {
        this.d.setVisibility(8);
        this.g.setVisibility(8);
        l();
    }

    public final void c(int i2) {
        if (this.k != null) {
            this.k.a();
            this.r = false;
        }
        if (!this.i) {
            n();
            o();
            a((String) DataflowMonitorModel.METHOD_NAME_CLOSE, i2, (String) "auto");
        } else {
            i();
            a((String) DataflowMonitorModel.METHOD_NAME_CLOSE, i2, (String) "manu");
        }
        if (this.l != null) {
            this.l.c(i2);
        }
        if (this.f != null) {
            this.f.setVisibility(0);
        }
        e(i2);
    }

    public final void b(int i2) {
        if (this.k != null) {
            this.k.a();
            this.r = false;
        }
        if (!this.i) {
            n();
            o();
            a((String) "more", i2, (String) "auto");
        } else {
            i();
            a((String) "more", i2, (String) "manu");
            if (i2 == 20 || i2 == 21) {
                a((String) "B010", (JSONObject) null);
            }
        }
        if (this.l != null) {
            this.l.b(i2);
        }
        if (this.f != null) {
            this.f.setVisibility(0);
        }
        e(i2);
    }

    public static void a(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            LogManager.actionLogV2("P00435", str);
        } else {
            LogManager.actionLogV2("P00435", str, jSONObject);
        }
    }

    public final void a(int i2, String str) {
        if (this.w != 2) {
            a((String) "B087", b(str, ri.a(this.j, i2)));
        }
    }

    private void a(String str, int i2, String str2) {
        if (this.w != 2) {
            a((String) "B086", a(str, str2, ri.a(this.j, i2)));
        }
    }

    public static void d(int i2) {
        if (i2 == 8 || i2 == 9) {
            a((String) "B107", f(i2 == 9 ? 1 : 2));
        }
    }

    private static void e(int i2) {
        if (i2 == 8 || i2 == 9) {
            a((String) "B106", f(i2 == 9 ? 1 : 2));
        }
    }

    private JSONObject b(String str, String str2) {
        String str3 = this.w == 0 ? "car" : DriveUtil.NAVI_TYPE_TRUCK;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", str);
            jSONObject.put("text", str2);
            jSONObject.put("from", str3);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    private static JSONObject f(int i2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("from", i2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    private JSONObject a(String str, String str2, String str3) {
        String str4 = this.w == 0 ? "car" : DriveUtil.NAVI_TYPE_TRUCK;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", str);
            jSONObject.put("status", str2);
            jSONObject.put("text", str3);
            jSONObject.put("from", str4);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    public static RouteIncident a(List<RouteIncident> list) {
        if (list.size() > 0) {
            int i2 = 0;
            int i3 = -1;
            char c2 = 65535;
            while (true) {
                if (i2 >= list.size()) {
                    i2 = i3;
                    break;
                }
                RouteIncident routeIncident = list.get(i2);
                if (routeIncident != null && !TextUtils.isEmpty(routeIncident.title)) {
                    TipType b2 = RouteCarResultTipUtil.b(routeIncident.tipsType);
                    char c3 = b2 == TipType.SCHEDULE_ROUTE ? 1 : b2 == TipType.SCHEDULE_END ? (char) 0 : 65535;
                    if (c3 == 2) {
                        break;
                    } else if (c3 > c2) {
                        i3 = i2;
                        c2 = c3;
                    }
                }
                i2++;
            }
            if (i2 != -1) {
                return list.get(i2);
            }
        }
        return null;
    }

    public final TipsView a(px pxVar) {
        int i2 = pxVar.a;
        if (i2 == 0) {
            if (m()) {
                Context context = this.j;
                boolean z = this.u;
                e();
                return py.a(context, z, pxVar);
            }
            Context context2 = this.j;
            e();
            return py.a(context2, pxVar);
        } else if (i2 == 1) {
            if (!m()) {
                return py.a(this.j, pxVar, e());
            }
            Context context3 = this.j;
            boolean z2 = this.u;
            e();
            return py.b(context3, z2, pxVar);
        } else if (i2 == 2) {
            return py.d(this.j, pxVar);
        } else {
            if (i2 == 3) {
                return py.e(this.j, pxVar);
            }
            if (i2 == 4) {
                if (m()) {
                    return py.c(this.j, this.u, pxVar);
                }
                return py.f(this.j, pxVar);
            } else if (i2 == 5) {
                return py.g(this.j, pxVar);
            } else {
                if (i2 == 6) {
                    return py.i(this.j, pxVar);
                }
                if (i2 == 7) {
                    return py.c(this.j, pxVar, f());
                }
                if (i2 == 8) {
                    if (!m()) {
                        return py.a(this.j, pxVar, e());
                    }
                    Context context4 = this.j;
                    boolean z3 = this.u;
                    e();
                    return py.b(context4, z3, pxVar);
                } else if (i2 == 9) {
                    if (!m()) {
                        return py.a(this.j, pxVar, e());
                    }
                    Context context5 = this.j;
                    boolean z4 = this.u;
                    e();
                    return py.b(context5, z4, pxVar);
                } else if (i2 == 14) {
                    return py.j(this.j, pxVar);
                } else {
                    if (i2 == 15 || i2 == 16 || i2 == 17 || i2 == 18) {
                        return py.c(this.j, pxVar);
                    }
                    if (i2 == 19) {
                        return py.h(this.j, pxVar);
                    }
                    if (i2 == 20) {
                        return py.k(this.j, pxVar);
                    }
                    if (i2 == 21) {
                        return py.l(this.j, pxVar);
                    }
                    if (i2 == 22) {
                        Context context6 = this.j;
                        e();
                        return py.b(context6, pxVar);
                    } else if (i2 == 23) {
                        return py.b(this.j, pxVar, e());
                    } else {
                        if (m()) {
                            Context context7 = this.j;
                            boolean z5 = this.u;
                            e();
                            return py.a(context7, z5, pxVar);
                        }
                        Context context8 = this.j;
                        e();
                        return py.a(context8, pxVar);
                    }
                }
            }
        }
    }

    static /* synthetic */ void e(AjxMotorTipsManager ajxMotorTipsManager) {
        if (ajxMotorTipsManager.b.size() > 1) {
            ajxMotorTipsManager.f.findViewById(R.id.tips_unread_count).setVisibility(0);
            PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{0.0f, 1.0f});
            PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{0.0f, 1.0f});
            ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(ajxMotorTipsManager.f.findViewById(R.id.tips_unread_count), new PropertyValuesHolder[]{ofFloat, ofFloat2});
            ofPropertyValuesHolder.setInterpolator(new AccelerateInterpolator());
            ofPropertyValuesHolder.setDuration(200);
            ofPropertyValuesHolder.start();
            return;
        }
        ajxMotorTipsManager.f.findViewById(R.id.tips_unread_count).setVisibility(8);
    }
}
