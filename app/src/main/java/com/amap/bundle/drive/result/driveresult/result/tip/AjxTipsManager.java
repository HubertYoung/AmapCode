package com.amap.bundle.drive.result.driveresult.result.tip;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
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
import com.alipay.apmobilesecuritysdk.rpc.gen.DeviceData;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.drive.result.tip.util.RouteCarResultTipUtil;
import com.amap.bundle.drive.result.tip.util.RouteCarResultTipUtil.TipType;
import com.amap.bundle.drive.result.tip.view.TipsView;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.statistics.util.LogUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.jni.ae.route.model.RouteIncident;
import com.autonavi.map.db.model.Car;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.BalloonText;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;

public class AjxTipsManager implements com.amap.bundle.drive.result.tip.view.TipsView.a {
    private static final String n = "AjxTipsManager";
    private View A = null;
    private IRouteUI B = null;
    private boolean C = false;
    ArrayList<px> a;
    TipPriorityComparator b;
    public ViewGroup c;
    public ViewGroup d;
    public boolean e = false;
    public a f;
    public ph g;
    public List<pp> h;
    public RouteIncident i;
    public RouteIncident j;
    public px<sn> k;
    int l;
    public int m = 0;
    private View o;
    private ViewGroup p;
    /* access modifiers changed from: private */
    public TipsView q;
    private BalloonText r;
    private Context s;
    private rb t;
    private ValueAnimator u;
    private pg v;
    private List<pk> w;
    /* access modifiers changed from: private */
    public boolean x = false;
    private final int y;
    private boolean z = false;

    static class TipPriorityComparator implements Serializable, Comparator {
        private TipPriorityComparator() {
        }

        /* synthetic */ TipPriorityComparator(byte b) {
            this();
        }

        public int compare(Object obj, Object obj2) {
            return ((px) obj).d - ((px) obj2).d <= 0.0f ? 1 : -1;
        }
    }

    public interface a extends com.amap.bundle.drive.result.tip.view.TipsView.a {
        void a(int i);
    }

    public AjxTipsManager(Context context, int i2, IRouteUI iRouteUI, boolean z2) {
        this.y = i2;
        this.s = context;
        this.B = iRouteUI;
        this.C = z2;
        this.a = new ArrayList<>();
        this.o = LayoutInflater.from(this.s).inflate(R.layout.view_driveresult_tips, null);
        this.p = (ViewGroup) this.o.findViewById(R.id.route_car_result_tips_holder);
        this.d = (ViewGroup) this.o.findViewById(R.id.tips_bg_mask);
        LayoutParams layoutParams = (LayoutParams) this.p.getLayoutParams();
        layoutParams.topMargin = z2 ? agn.a(context, 54.0f) : iRouteUI.m();
        this.p.setLayoutParams(layoutParams);
        iRouteUI.a(this.o);
        this.A = LayoutInflater.from(this.s).inflate(R.layout.route_car_result_foot_tips, null);
        LayoutParams layoutParams2 = new LayoutParams(-2, -2);
        layoutParams2.addRule(14);
        layoutParams2.topMargin = z2 ? agn.a(context, 54.0f) : iRouteUI.m() + 10;
        this.A.setLayoutParams(layoutParams2);
        iRouteUI.a(this.A);
        this.r = (BalloonText) this.A.findViewById(R.id.car_too_long_warning);
    }

    public final void a() {
        if (this.p != null) {
            LayoutParams layoutParams = (LayoutParams) this.p.getLayoutParams();
            layoutParams.topMargin = this.C ? agn.a(this.s, 54.0f) : this.B.m();
            this.p.setLayoutParams(layoutParams);
        }
        if (this.A != null) {
            LayoutParams layoutParams2 = (LayoutParams) this.A.getLayoutParams();
            layoutParams2.topMargin = this.C ? agn.a(this.s, 54.0f) : this.B.m() + 10;
            this.A.setLayoutParams(layoutParams2);
        }
    }

    public final void b() {
        if (this.r != null) {
            this.r.setVisibility(8);
        }
    }

    public final void c() {
        if (this.r != null) {
            this.r.hide();
        }
    }

    public final void d() {
        if (this.r != null) {
            this.r.hide();
        }
        if (this.g != null && this.g.h != null && this.g.g != null && ((this.g.i == null || this.g.i.size() <= 0) && ((double) cfe.a(this.g.h.getPoint(), this.g.g.getPoint())) * 1.72d < 200.0d)) {
            String type = this.g.h.getType();
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
            if (bno.a) {
                String str2 = n;
                StringBuilder sb = new StringBuilder("showOnFootTips startPOI=");
                sb.append(pz.a(this.g.g));
                sb.append(",endPOI:");
                sb.append(pz.a(this.g.h));
                tq.b("NaviMonitor", str2, sb.toString());
            }
            if (this.r != null) {
                this.r.show(5000);
            }
            if (this.r != null) {
                this.r.hide();
            }
        }
    }

    public final void a(int i2) {
        LayoutParams layoutParams = (LayoutParams) this.p.getLayoutParams();
        layoutParams.topMargin = i2;
        this.p.setLayoutParams(layoutParams);
    }

    public final void d(int i2) {
        this.p.setTranslationY((float) i2);
    }

    public final boolean e() {
        return this.p.getChildCount() > 0 && this.p.isShown();
    }

    public final int f() {
        if (this.a != null) {
            return this.a.size();
        }
        return 0;
    }

    public final void a(ph phVar) {
        if (phVar != null && phVar.f.get(0) != null) {
            this.g = phVar;
            this.v = phVar.a(0);
            if (this.v != null) {
                this.h = this.v.n;
            }
            o();
        }
    }

    private void o() {
        if (this.g != null) {
            this.v = this.g.a(this.g.e);
            t();
            r();
            q();
            a(!this.v.d);
            v();
            s();
            p();
            F();
            z();
            A();
        }
    }

    private void p() {
        String str;
        ArrayList<pe> arrayList = new ArrayList<>();
        for (pg next : this.g.f) {
            if (next.i != null) {
                arrayList.addAll(next.i);
            }
        }
        if (!arrayList.isEmpty()) {
            int i2 = 15;
            ArrayList arrayList2 = new ArrayList();
            StringBuilder sb = new StringBuilder();
            boolean z2 = false;
            boolean z3 = false;
            boolean z4 = false;
            boolean z5 = false;
            int i3 = 0;
            for (pe peVar : arrayList) {
                if (peVar.a != null) {
                    arrayList2.addAll(peVar.a);
                }
                if (peVar.b != null) {
                    for (defpackage.pe.a aVar : peVar.b) {
                        switch (aVar.a) {
                            case 0:
                                i3 |= 1;
                                z2 = true;
                                break;
                            case 1:
                                i3 |= 2;
                                z3 = true;
                                break;
                            case 2:
                                i3 |= 4;
                                z4 = true;
                                break;
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                                i3 |= 8;
                                z5 = true;
                                break;
                        }
                    }
                }
            }
            if (z2) {
                if (sb.length() > 0) {
                    sb.append("/");
                }
                sb.append("限高");
            }
            if (z3) {
                if (sb.length() > 0) {
                    sb.append("/");
                }
                sb.append("限宽");
            }
            if (z4) {
                if (sb.length() > 0) {
                    sb.append("/");
                }
                sb.append("限重");
            }
            if (z5) {
                if (sb.length() > 0) {
                    sb.append("/");
                }
                sb.append("禁行");
            }
            if (z2) {
                i2 = 16;
            } else if (z3) {
                i2 = 17;
            } else if (z4) {
                i2 = 18;
            }
            String format = String.format(this.s.getString(R.string.tip_avoid_forbid_title), new Object[]{sb.toString()});
            StringBuilder sb2 = new StringBuilder();
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (!TextUtils.isEmpty(str2)) {
                    if (sb2.length() > 0) {
                        sb2.append("；");
                    }
                    sb2.append(str2);
                }
            }
            if (sb2.length() <= 0) {
                str = "";
            } else {
                str = String.format(this.s.getString(R.string.tip_avoid_forbid_subtitle), new Object[]{sb2.toString()});
            }
            px pxVar = new px(i2);
            pxVar.b = format;
            pxVar.c = str;
            pxVar.d = 1.15f;
            this.a.add(pxVar);
            if (this.y == 1) {
                b((String) "B010", LogUtil.createJSONObj(i3));
                this.m = i3;
            }
        }
    }

    private void q() {
        List<sn> list = this.v.j;
        if (list != null && list.size() != 0) {
            T t2 = (sn) list.get(0);
            if (t2 != null && !TextUtils.isEmpty(t2.o) && !TextUtils.isEmpty(t2.p)) {
                px<sn> pxVar = new px<>(5);
                pxVar.b = t2.o;
                pxVar.c = t2.p;
                pxVar.d = 1.1f;
                pxVar.f = t2;
                this.k = pxVar;
                this.a.add(pxVar);
            }
        }
    }

    private void r() {
        RouteIncident a2 = a(this.v.k, this.v.l);
        if (a2 != null) {
            TipType b2 = RouteCarResultTipUtil.b(a2.tipsType);
            if (TipType.INVALID_TYPE != b2) {
                px pxVar = new px();
                if (b2.mPriority >= TipType.INCIDENT_UNAVOIDABLE_INCIDENT_AT_END.mPriority && b2.mPriority <= TipType.INCIDENT_UNAVOIDABLE_INCIDENT_AT_START.mPriority) {
                    pxVar.a = 2;
                    pxVar.d = 3.0f;
                } else if (b2 == TipType.INCIDENT_AVOIDABLE_INCIDENT) {
                    pxVar.a = 3;
                    pxVar.d = 1.2f;
                }
                pxVar.b = a(a2);
                this.a.add(pxVar);
                this.i = a2;
            }
        }
    }

    private void s() {
        boolean z2;
        boolean z3;
        boolean z4;
        this.w = this.v.m;
        List<pp> list = this.v.n;
        if (this.w != null || list != null) {
            String str = "";
            StringBuilder sb = new StringBuilder();
            boolean z5 = (this.w == null || this.w.size() <= 0 || this.w.get(0) == null) ? false : true;
            int i2 = 10;
            if (list != null) {
                z4 = false;
                z3 = false;
                z2 = false;
                for (pp ppVar : list) {
                    switch (ppVar.a) {
                        case 81:
                            z4 = true;
                            break;
                        case 82:
                            z3 = true;
                            break;
                        case DeviceData.TAG_AC10 /*83*/:
                            z2 = true;
                            break;
                    }
                }
            } else {
                z4 = false;
                z3 = false;
                z2 = false;
            }
            if (z4) {
                if (sb.length() > 0) {
                    sb.append("、");
                }
                sb.append("限高");
            }
            if (z3) {
                if (sb.length() > 0) {
                    sb.append("、");
                }
                sb.append("限宽");
            }
            if (z2) {
                if (sb.length() > 0) {
                    sb.append("、");
                }
                sb.append("限重");
            }
            if (z5) {
                if (sb.length() > 0) {
                    sb.append("、");
                }
                sb.append("禁行");
            }
            String format = String.format(this.s.getString(R.string.tip_unavoid_forbid_title), new Object[]{sb.toString()});
            if (z4 || z3 || z2) {
                i2 = z4 ? 11 : z3 ? 12 : 13;
                StringBuilder sb2 = new StringBuilder();
                Car carTruckInfo = DriveUtil.getCarTruckInfo();
                if (carTruckInfo != null) {
                    sb2.append(DriveUtil.getTruckType(carTruckInfo.truckType));
                    sb2.append("、");
                    StringBuilder sb3 = new StringBuilder("高");
                    sb3.append(carTruckInfo.height);
                    sb3.append("米");
                    sb2.append(sb3.toString());
                    sb2.append("、");
                    StringBuilder sb4 = new StringBuilder("宽");
                    sb4.append(carTruckInfo.width);
                    sb4.append("米");
                    sb2.append(sb4.toString());
                }
                str = String.format(this.s.getString(R.string.tip_unavoid_forbid_subtitle), new Object[]{sb2.toString()});
            }
            px pxVar = new px(i2);
            pxVar.b = format;
            pxVar.c = str;
            pxVar.d = 3.05f;
            this.a.add(pxVar);
        }
    }

    private static RouteIncident a(List<RouteIncident> list, List<RouteIncident> list2) {
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
                    int a2 = a(RouteCarResultTipUtil.b(routeIncident.tipsType));
                    if (a2 == 4) {
                        i2 = a2;
                        break;
                    } else if (a2 > i2) {
                        i4 = i3;
                        i2 = a2;
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
                    int a3 = a(RouteCarResultTipUtil.b(routeIncident2.tipsType));
                    if (a3 > i2) {
                        i2 = a3;
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

    private static String a(RouteIncident routeIncident) {
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

    private static int a(TipType tipType) {
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

    private void t() {
        sq sqVar = this.v.o;
        if (sqVar != null) {
            int i2 = sqVar.e;
            if (i2 != -1) {
                TipType a2 = RouteCarResultTipUtil.a(i2);
                if (TipType.INVALID_TYPE != a2) {
                    px pxVar = new px();
                    if (a2 == TipType.RESTRICT_ALREADY_AVOID_RESTRICT_AREA) {
                        pxVar.a = 1;
                        pxVar.d = 2.0f;
                        if (sqVar.h.length > 0) {
                            pxVar.e = sqVar.h;
                            pxVar.b = this.s.getString(R.string.tip_carnum_avoid_restrict_title);
                        } else {
                            pxVar.b = a(sqVar.a, this.s.getString(R.string.tip_already_avoid_restriction_default_title));
                        }
                    } else if (a2 == TipType.RESTRICT_OTHER_PLACE_OPEN_RESTRICT_SWITCH && DriveUtil.needShowCarPlateOpenAvoidLimitedNotice()) {
                        pxVar.a = 4;
                        pxVar.d = 3.2f;
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.s.getString(R.string.tip_avoid_restrict_title_prefix));
                        sb.append(sqVar.f);
                        sb.append("？");
                        pxVar.b = a(sb.toString(), this.s.getString(R.string.tip_remind_restriction_default_title));
                    } else if (a2 == TipType.RESTRICT_OTHER_PLACE_SET_PLATE && DriveUtil.needShowCarPlateSetting()) {
                        pxVar.a = 6;
                        pxVar.d = 3.2f;
                        pxVar.b = a(sqVar.a, R.string.tip_plate_default_title);
                    } else if (a2.mPriority >= TipType.RESTRICT_START_POI_IN_RESTRICT_AREA.mPriority && a2.mPriority <= TipType.RESTRICT_ACROSS_RESTRICT_AREA.mPriority) {
                        pxVar.a = 0;
                        pxVar.d = 3.1f;
                        if (sqVar.h.length > 0) {
                            pxVar.e = sqVar.h;
                            pxVar.b = this.s.getString(R.string.tip_carnum_unavoid_restrict_title);
                        } else {
                            pxVar.b = this.s.getString(R.string.tip_unavoid_restrict_title);
                        }
                        pxVar.c = sqVar.a;
                    } else if (a2 == TipType.RESTRICT_SOON_EFFECT_AVOID_RESTRICT_AREA) {
                        pxVar.a = 8;
                        pxVar.d = 2.2f;
                        pxVar.b = sqVar.a;
                    } else if (a2 == TipType.RESTRICT_SOON_END_ACROSS_RESTRICT_AREA) {
                        pxVar.a = 9;
                        pxVar.d = 2.1f;
                        pxVar.b = sqVar.a;
                    } else if (a2 == TipType.RESTRICT_ETD_UNABLE_AVOID_RESTRICT) {
                        pxVar.a = 22;
                        pxVar.d = 3.06f;
                        pxVar.b = this.s.getString(R.string.tip_unavoid_restrict_title);
                        pxVar.c = sqVar.a;
                    } else if (a2 == TipType.RESTRICT_ETD_AVOID_RESTRICT) {
                        pxVar.a = 23;
                        pxVar.d = 2.05f;
                        pxVar.b = this.s.getString(R.string.tip_already_avoid_restriction_title);
                        pxVar.c = sqVar.a;
                    }
                    if (b(a2)) {
                        this.a.add(pxVar);
                    }
                }
            }
        }
    }

    private String a(String str, int i2) {
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        return this.s.getString(i2);
    }

    private static String a(String str, String str2) {
        return !TextUtils.isEmpty(str) ? str : str2;
    }

    private static boolean b(TipType tipType) {
        if (tipType != TipType.RESTRICT_OTHER_PLACE_SET_PLATE && tipType != TipType.RESTRICT_OTHER_PLACE_OPEN_RESTRICT_SWITCH) {
            return true;
        }
        if (tipType == TipType.RESTRICT_OTHER_PLACE_SET_PLATE && DriveUtil.needShowCarPlateSetting()) {
            return true;
        }
        if (tipType != TipType.RESTRICT_OTHER_PLACE_OPEN_RESTRICT_SWITCH || !DriveUtil.needShowCarPlateOpenAvoidLimitedNotice()) {
            return false;
        }
        return true;
    }

    private boolean u() {
        sq sqVar = this.v.o;
        if (sqVar == null) {
            return false;
        }
        int i2 = sqVar.e;
        boolean z2 = true;
        if (!(i2 == -1 || i2 == 0 || i2 == 1)) {
            TipType a2 = RouteCarResultTipUtil.a(i2);
            if ((TipType.RESTRICT_ALREADY_AVOID_RESTRICT_AREA == a2 || TipType.RESTRICT_SOON_EFFECT_AVOID_RESTRICT_AREA == a2 || TipType.RESTRICT_SOON_END_ACROSS_RESTRICT_AREA == a2) && (this.v.q == null || this.v.q.c() > 2)) {
                z2 = false;
            }
        }
        return z2;
    }

    private void v() {
        if (this.a.isEmpty() && this.v != null && this.v.p != null && !TextUtils.isEmpty(this.v.p.tipInfo)) {
            px pxVar = new px(7);
            pxVar.d = 1.0f;
            pxVar.b = this.s.getString(R.string.tip_offline_remind);
            this.a.add(pxVar);
        }
    }

    private void a(boolean z2) {
        String str;
        if (z2 && DriveSpUtil.getSearchRouteInNetMode(AMapAppGlobal.getApplication())) {
            px pxVar = new px(14);
            pxVar.d = 0.9f;
            if (DriveUtil.isAvoidLimitedPath()) {
                str = this.s.getString(R.string.tip_type_remind_offline_online_desc);
            } else {
                str = this.s.getString(R.string.tip_type_remind_offline_no_avoid_desc);
            }
            pxVar.b = str;
            this.a.add(pxVar);
        }
    }

    private boolean w() {
        if (this.v == null || this.v.p == null || this.v.p.type != 1) {
            return false;
        }
        return true;
    }

    public final void a(long j2) {
        if (this.a != null && this.a.size() != 0 && this.p != null) {
            this.e = false;
            px pxVar = this.a.get(0);
            float f2 = pxVar.d;
            if ((f2 >= 2.0f && f2 <= 3.2f && f2 != 3.0f) || f2 == 0.9f || f2 == 1.15f || f2 == 1.05f) {
                j2 = 21000;
            }
            long j3 = j2;
            this.q = a(pxVar);
            this.q.setOnTipClickListener(this);
            this.p.addView(this.q);
            TipsView tipsView = this.q;
            LayoutParams layoutParams = (LayoutParams) tipsView.getLayoutParams();
            layoutParams.topMargin = tipsView.getResources().getDimensionPixelOffset(R.dimen.tips_vertical_margin);
            tipsView.setLayoutParams(layoutParams);
            if (j3 > 0) {
                ViewGroup viewGroup = this.p;
                TipsView tipsView2 = this.q;
                this.t = new rb();
                this.t.a(tipsView2.getCountDownView(), viewGroup.getResources().getString(R.string.tip_cancel), "", j3, new b() {
                    public final void a() {
                        AjxTipsManager.this.B();
                        AjxTipsManager.this.D();
                        AjxTipsManager.this.x = false;
                    }
                });
                this.x = true;
            }
            this.p.setVisibility(0);
            a(pxVar.a, (String) "auto");
            f(pxVar.a);
            if (this.f != null) {
                this.f.a(pxVar.a);
            }
        }
    }

    public final int g() {
        if (this.p == null || this.p.getVisibility() != 0 || this.q == null || this.e || this.q.getVisibility() != 0) {
            return 0;
        }
        if (this.q.getMeasuredHeight() <= 0) {
            this.q.measure(0, 0);
        }
        return this.q.getMeasuredHeight();
    }

    public final void h() {
        this.o.bringToFront();
        this.z = true;
        if (this.a != null && this.a.size() != 0 && this.p != null) {
            this.p.setVisibility(0);
            this.e = true;
            int size = this.a.size() > 3 ? 3 : this.a.size();
            if (size > 0) {
                AnimatorSet animatorSet = new AnimatorSet();
                Animator animator = null;
                Animator animator2 = null;
                Animator animator3 = null;
                for (int i2 = 0; i2 < size; i2++) {
                    TipsView e2 = e(i2);
                    e2.setOnTipClickListener(this);
                    if (i2 == 0) {
                        animator = e2.getTopTipAnimation();
                    } else if (i2 == 1) {
                        animator2 = e2.getTranslateAnimation(300, 100);
                    } else if (i2 == 2) {
                        e2.setAlpha(0.0f);
                        animator3 = e2.getTranslateAnimation(300, 200);
                    }
                }
                if (size == 1) {
                    animatorSet.play(animator);
                } else if (size == 2) {
                    animatorSet.play(animator).with(animator2);
                } else if (size == 3) {
                    animatorSet.play(animator).with(animator2).with(animator3);
                }
                animatorSet.start();
            }
            C();
            this.e = true;
        }
    }

    @NonNull
    private TipsView e(int i2) {
        px pxVar = this.a.get(i2);
        TipsView a2 = a(pxVar);
        a2.dismissCancelButton();
        if ((pxVar.a == 7 && !w()) || ((pxVar.a == 1 && !u()) || ((pxVar.a == 9 && !u()) || pxVar.a == 19))) {
            a2.dismissVerticalDivider();
        }
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        if (i2 == 0) {
            layoutParams.topMargin = this.p.getResources().getDimensionPixelOffset(R.dimen.tips_vertical_margin);
        } else {
            layoutParams.topMargin = (a2.targetHeight * (i2 - 1)) + this.p.getResources().getDimensionPixelOffset(R.dimen.tips_vertical_margin);
        }
        this.p.addView(a2, 0, layoutParams);
        a(pxVar.a, (String) "manu");
        f(pxVar.a);
        return a2;
    }

    public final void i() {
        if (this.a != null && this.a.size() != 0 && this.p != null) {
            E();
            this.z = false;
        }
    }

    private void x() {
        this.p.setVisibility(8);
        if (this.c != null) {
            this.c.setVisibility(0);
        }
    }

    private boolean y() {
        return this.l == RouteType.MOTOR.getValue();
    }

    public final pk j() {
        if (this.w == null || this.w.size() <= 0) {
            return null;
        }
        return this.w.get(0);
    }

    public final void k() {
        if (this.p != null) {
            this.a.clear();
            if (this.u != null && this.u.isRunning()) {
                this.u.cancel();
            }
            if (this.q != null) {
                this.q.setVisibility(8);
            }
            if (this.c != null) {
                this.c.setVisibility(4);
            }
            this.p.removeAllViews();
            this.g = null;
            this.v = null;
            this.w = null;
            this.i = null;
            this.j = null;
            if (this.t != null) {
                this.t.a();
                this.x = false;
            }
        }
    }

    public final void l() {
        if (!(this.a == null || this.a.size() == 0 || this.p == null || !this.x)) {
            this.p.setVisibility(0);
        }
    }

    private void z() {
        if (this.b == null) {
            this.b = new TipPriorityComparator(0);
        }
        Collections.sort(this.a, this.b);
    }

    private void A() {
        if (bno.a && this.v != null) {
            ku a2 = ku.a();
            String str = n;
            StringBuilder sb = new StringBuilder("parseHolidayFreeTipInfo  mNavigationPath.mIsHolidayFree:");
            sb.append(this.v.r);
            a2.c(str, sb.toString());
        }
        if (this.v != null && this.v.r) {
            px pxVar = new px(19);
            pxVar.d = 1.05f;
            pxVar.b = this.s.getString(R.string.tip_holiday_free);
            this.a.add(pxVar);
        }
    }

    public final void a(Float f2) {
        this.p.setAlpha(f2.floatValue());
        this.r.setAlpha(f2.floatValue());
    }

    /* access modifiers changed from: private */
    public void B() {
        if (this.a.size() > 1) {
            ((TextView) this.c.findViewById(R.id.tips_unread_count)).setText(String.valueOf(this.a.size() > 3 ? 2 : this.a.size() - 1));
        }
    }

    private void C() {
        ((TextView) this.c.findViewById(R.id.tips_unread_count)).setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void D() {
        this.u = this.q.getZoomOutAnimation();
        this.u.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Rect rect = (Rect) valueAnimator.getAnimatedValue();
                if (AjxTipsManager.this.q != null) {
                    AjxTipsManager.this.q.layout(rect.left, rect.top, rect.right, rect.bottom);
                    AjxTipsManager.this.q.invalidate();
                }
            }
        });
        this.u.addListener(new AnimatorListener() {
            public final void onAnimationCancel(Animator animator) {
            }

            public final void onAnimationRepeat(Animator animator) {
            }

            public final void onAnimationStart(Animator animator) {
            }

            public final void onAnimationEnd(Animator animator) {
                AjxTipsManager.this.m();
                AjxTipsManager.e(AjxTipsManager.this);
            }
        });
        this.u.start();
    }

    private void E() {
        Animation loadAnimation = AnimationUtils.loadAnimation(this.s, R.anim.tips_list_hiding);
        this.p.setAnimation(loadAnimation);
        this.p.startAnimation(loadAnimation);
    }

    public final void m() {
        if (this.t != null) {
            this.t.a();
            this.x = false;
        }
        this.p.removeAllViews();
        if (this.a != null && this.a.size() > 0) {
            x();
        }
        if (this.f != null && this.a != null && this.a.size() > 0) {
            this.f.a(this.a.get(0).a);
        }
    }

    public final void n() {
        this.p.setVisibility(8);
        this.d.setVisibility(8);
        x();
    }

    public final void c(int i2) {
        if (this.t != null) {
            this.t.a();
            this.x = false;
        }
        if (!this.e) {
            B();
            D();
            a((String) DataflowMonitorModel.METHOD_NAME_CLOSE, i2, (String) "auto");
        } else {
            E();
            a((String) DataflowMonitorModel.METHOD_NAME_CLOSE, i2, (String) "manu");
        }
        if (this.f != null) {
            this.f.c(i2);
        }
        if (this.c != null) {
            this.c.setVisibility(0);
        }
        g(i2);
    }

    public final void b(int i2) {
        if (this.t != null) {
            this.t.a();
            this.x = false;
        }
        if (!this.e) {
            B();
            D();
            a((String) "more", i2, (String) "auto");
        } else {
            E();
            a((String) "more", i2, (String) "manu");
        }
        if (this.f != null) {
            this.f.b(i2);
        }
        if (this.c != null) {
            this.c.setVisibility(0);
        }
        g(i2);
    }

    private static void a(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            LogManager.actionLogV2(LogConstant.PAGE_ID_CAR_RESULT_MAP, str);
        } else {
            LogManager.actionLogV2(LogConstant.PAGE_ID_CAR_RESULT_MAP, str, jSONObject);
        }
    }

    private void b(String str, JSONObject jSONObject) {
        String str2 = this.y == 1 ? "P00330" : LogConstant.PAGE_ID_CAR_RESULT_MAP;
        if (jSONObject == null) {
            LogManager.actionLogV2(str2, str);
        } else {
            LogManager.actionLogV2(str2, str, jSONObject);
        }
    }

    private void a(int i2, String str) {
        a((String) "B087", b(str, i(i2)));
    }

    private void a(String str, int i2, String str2) {
        a((String) "B086", a(str, str2, i(i2)));
    }

    private static void f(int i2) {
        if (i2 == 8 || i2 == 9) {
            a((String) "B107", h(i2 == 9 ? 1 : 2));
            return;
        }
        if (i2 == 22 || i2 == 23) {
            a((String) "B127", (JSONObject) null);
        }
    }

    private static void g(int i2) {
        if (i2 == 8 || i2 == 9) {
            a((String) "B106", h(i2 == 9 ? 1 : 2));
            return;
        }
        if ((i2 == 22 || i2 == 23) && rt.a()) {
            a((String) "B128", (JSONObject) null);
        }
    }

    private JSONObject b(String str, String str2) {
        String str3 = this.y == 0 ? "car" : DriveUtil.NAVI_TYPE_TRUCK;
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

    private static JSONObject h(int i2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("from", i2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    private JSONObject a(String str, String str2, String str3) {
        String str4 = this.y == 0 ? "car" : DriveUtil.NAVI_TYPE_TRUCK;
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

    private String i(int i2) {
        switch (i2) {
            case 0:
                return this.s.getString(R.string.tip_type_unavoid_restriction_desc);
            case 1:
                return this.s.getString(R.string.tip_type_avoid_restriction_desc);
            case 2:
                return this.s.getString(R.string.tip_type_unavoid_incident_desc);
            case 3:
                return this.s.getString(R.string.tip_type_avoid_incident_desc);
            case 4:
                return this.s.getString(R.string.tip_type_remind_open_restriction_desc);
            case 5:
                return this.s.getString(R.string.tip_type_avoid_jam_desc);
            case 6:
                return this.s.getString(R.string.tip_type_remind_open_car_plate_desc);
            case 7:
                return this.s.getString(R.string.tip_type_offline_to_online_desc);
            case 8:
                return this.s.getString(R.string.tip_soon_effect_avoid_restrict_title);
            case 9:
                return this.s.getString(R.string.tip_soon_end_cross_restrict_title);
            case 10:
                return this.s.getString(R.string.tip_type_unavoid_desc);
            case 11:
                return this.s.getString(R.string.tip_type_unavoid_desc);
            case 12:
                return this.s.getString(R.string.tip_type_unavoid_desc);
            case 13:
                return this.s.getString(R.string.tip_type_unavoid_desc);
            case 14:
                return this.s.getString(R.string.tip_type_remind_offline_online_desc);
            case 15:
                return this.s.getString(R.string.tip_type_avoid_desc);
            case 16:
                return this.s.getString(R.string.tip_type_avoid_desc);
            case 17:
                return this.s.getString(R.string.tip_type_avoid_desc);
            case 18:
                return this.s.getString(R.string.tip_type_avoid_desc);
            default:
                return "";
        }
    }

    private void F() {
        LinkedList linkedList = new LinkedList();
        if (this.v.k != null) {
            linkedList.addAll(this.v.k);
        }
        if (this.v.l != null) {
            linkedList.addAll(this.v.l);
        }
        RouteIncident a2 = a((List<RouteIncident>) linkedList);
        if (a2 != null) {
            TipType b2 = RouteCarResultTipUtil.b(a2.tipsType);
            if (TipType.INVALID_TYPE != b2) {
                px pxVar = new px();
                if (b2 == TipType.SCHEDULE_ROUTE) {
                    pxVar.a = 20;
                    pxVar.d = 1.9f;
                } else if (b2 == TipType.SCHEDULE_END) {
                    pxVar.a = 21;
                    pxVar.d = 1.8f;
                }
                pxVar.b = a(a2);
                this.a.add(pxVar);
                this.j = a2;
            }
        }
    }

    private static RouteIncident a(List<RouteIncident> list) {
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

    private TipsView a(px pxVar) {
        int i2 = pxVar.a;
        if (i2 == 0) {
            if (y()) {
                Context context = this.s;
                boolean z2 = this.z;
                u();
                return py.a(context, z2, pxVar);
            }
            Context context2 = this.s;
            u();
            return py.a(context2, pxVar);
        } else if (i2 == 1) {
            if (!y()) {
                return py.a(this.s, pxVar, u());
            }
            Context context3 = this.s;
            boolean z3 = this.z;
            u();
            return py.b(context3, z3, pxVar);
        } else if (i2 == 2) {
            return py.d(this.s, pxVar);
        } else {
            if (i2 == 3) {
                return py.e(this.s, pxVar);
            }
            if (i2 == 4) {
                if (y()) {
                    return py.c(this.s, this.z, pxVar);
                }
                return py.f(this.s, pxVar);
            } else if (i2 == 5) {
                return py.g(this.s, pxVar);
            } else {
                if (i2 == 6) {
                    return py.i(this.s, pxVar);
                }
                if (i2 == 7) {
                    return py.c(this.s, pxVar, w());
                }
                if (i2 == 8) {
                    if (!y()) {
                        return py.a(this.s, pxVar, u());
                    }
                    Context context4 = this.s;
                    boolean z4 = this.z;
                    u();
                    return py.b(context4, z4, pxVar);
                } else if (i2 == 9) {
                    if (!y()) {
                        return py.a(this.s, pxVar, u());
                    }
                    Context context5 = this.s;
                    boolean z5 = this.z;
                    u();
                    return py.b(context5, z5, pxVar);
                } else if (i2 == 14) {
                    return py.j(this.s, pxVar);
                } else {
                    if (i2 == 15 || i2 == 16 || i2 == 17 || i2 == 18) {
                        return py.c(this.s, pxVar);
                    }
                    if (i2 == 19) {
                        return py.h(this.s, pxVar);
                    }
                    if (i2 == 20) {
                        return py.k(this.s, pxVar);
                    }
                    if (i2 == 21) {
                        return py.l(this.s, pxVar);
                    }
                    if (i2 == 22) {
                        Context context6 = this.s;
                        u();
                        return py.b(context6, pxVar);
                    } else if (i2 == 23) {
                        return py.b(this.s, pxVar, u());
                    } else {
                        if (y()) {
                            Context context7 = this.s;
                            boolean z6 = this.z;
                            u();
                            return py.a(context7, z6, pxVar);
                        }
                        Context context8 = this.s;
                        u();
                        return py.a(context8, pxVar);
                    }
                }
            }
        }
    }

    static /* synthetic */ void e(AjxTipsManager ajxTipsManager) {
        if (ajxTipsManager.a.size() > 1) {
            ajxTipsManager.c.findViewById(R.id.tips_unread_count).setVisibility(0);
            PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{0.0f, 1.0f});
            PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{0.0f, 1.0f});
            ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(ajxTipsManager.c.findViewById(R.id.tips_unread_count), new PropertyValuesHolder[]{ofFloat, ofFloat2});
            ofPropertyValuesHolder.setInterpolator(new AccelerateInterpolator());
            ofPropertyValuesHolder.setDuration(200);
            ofPropertyValuesHolder.start();
            return;
        }
        ajxTipsManager.c.findViewById(R.id.tips_unread_count).setVisibility(8);
    }
}
