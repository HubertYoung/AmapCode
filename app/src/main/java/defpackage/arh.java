package defpackage;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout.LayoutParams;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.amaphome.widget.OldMapStatusBar;
import com.autonavi.bundle.amaphome.widget.StatusBar;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.LogVersionType;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.statusbar.StatusBarManager;
import com.autonavi.minimap.statusbar.StatusBarManager.FeatureType;
import com.autonavi.minimap.statusbar.StatusBarManager.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: arh reason: default package */
/* compiled from: StatusBarManagerImpl */
public class arh extends StatusBarManager {
    private static arh b;
    protected List<a> a = new ArrayList();
    private View c;
    private LayoutParams d;
    private LayoutParams e;
    private boolean f = true;

    public static arh a() {
        if (b == null) {
            synchronized (arh.class) {
                try {
                    if (b == null) {
                        b = new arh();
                    }
                }
            }
        }
        return b;
    }

    public final void a(@NonNull FeatureType featureType) {
        a f2 = f(featureType);
        if (f2 != null) {
            if (!f2.e) {
                f2.a(null);
                f2.f = null;
                this.a.add(f2);
            }
            c();
        }
    }

    public final boolean b(@NonNull FeatureType featureType) {
        a g = g(featureType);
        return (g == null || g.j == null) ? false : true;
    }

    public final void a(bid bid) {
        c(bid);
    }

    public final void b(bid bid) {
        c(bid);
    }

    private void c(bid bid) {
        a aVar;
        Iterator<a> it = this.a.iterator();
        while (true) {
            if (!it.hasNext()) {
                aVar = null;
                break;
            }
            aVar = it.next();
            if (aVar != null && aVar.e && aVar.j == this.c) {
                break;
            }
        }
        if (aVar != null && this.c != null) {
            if (!(bid instanceof emc) || !((emc) bid).u()) {
                this.c.setVisibility(8);
                aVar.i = a.b;
                return;
            }
            this.c.setVisibility(0);
            if (aVar.i == a.b) {
                ObjectAnimator.ofFloat(this.c, "alpha", new float[]{0.0f, 1.0f}).setDuration(500).start();
            }
            aVar.i = a.a;
        }
    }

    public final void a(boolean z) {
        a aVar;
        Iterator<a> it = this.a.iterator();
        while (true) {
            if (!it.hasNext()) {
                aVar = null;
                break;
            }
            aVar = it.next();
            if (aVar != null && aVar.e && aVar.j == this.c) {
                break;
            }
        }
        if (aVar != null && this.c != null) {
            if (!z || aVar.i != a.b) {
                if (!z && aVar.i == a.a) {
                    this.c.setVisibility(8);
                    aVar.i = a.b;
                    this.f = false;
                }
                return;
            }
            this.c.setVisibility(0);
            aVar.i = a.a;
            this.f = z;
        }
    }

    public final void c(@NonNull FeatureType featureType) {
        a(featureType, true);
    }

    public final void d(@NonNull FeatureType featureType) {
        a(featureType, false);
    }

    private void a(FeatureType featureType, boolean z) {
        a g = g(featureType);
        if (g == null) {
            a aVar = new a(featureType);
            aVar.e = z;
            this.a.add(aVar);
            return;
        }
        if (g.e != z) {
            g.e = z;
            c();
        }
    }

    public final void b() {
        e();
        for (int i = 0; i < this.a.size(); i++) {
            a aVar = this.a.get(i);
            if (!(aVar == null || aVar.j == null)) {
                ViewParent parent = aVar.j.getParent();
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(aVar.j);
                }
            }
        }
    }

    private a f(FeatureType featureType) {
        a g = g(featureType);
        if (g != null) {
            this.a.remove(g);
        }
        return g;
    }

    private a g(FeatureType featureType) {
        if (this.a.isEmpty()) {
            return null;
        }
        for (int i = 0; i < this.a.size(); i++) {
            a aVar = this.a.get(i);
            if (aVar != null && aVar.g == featureType) {
                return aVar;
            }
        }
        return null;
    }

    public final void c() {
        a aVar;
        View view;
        if (this.a.isEmpty()) {
            e();
            return;
        }
        Iterator<a> it = this.a.iterator();
        while (true) {
            aVar = null;
            if (!it.hasNext()) {
                view = null;
                break;
            }
            aVar = it.next();
            if (aVar != null && aVar.e && aVar.j != null) {
                view = aVar.j;
                break;
            }
        }
        if (view == null) {
            e();
        } else {
            a(aVar, view);
        }
    }

    private void a(a aVar, View view) {
        if (view != this.c || this.c.getParent() == null) {
            if (this.c != null && (this.c.getParent() instanceof ViewGroup)) {
                ((ViewGroup) this.c.getParent()).removeView(this.c);
            }
            this.c = view;
            bid pageContext = AMapPageUtil.getPageContext();
            if (!(pageContext instanceof emc) || !((emc) pageContext).u()) {
                this.c.setVisibility(8);
                aVar.i = a.b;
            } else {
                this.c.setVisibility(0);
                aVar.i = a.a;
            }
            a(aVar, this.c, a(this.c.getContext()));
            return;
        }
        if (!a.a.a) {
            euk.a(AMapAppGlobal.getTopActivity(), 0);
        }
        a(aVar);
    }

    private static void a(a aVar) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("from", aVar.g == FeatureType.TYPE_TAXI ? "共享" : "单车");
            jSONObject.put("status", a.a.a ? LogVersionType.REDESIGN : LogVersionType.UN_REDESIGN);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00001", "B281", jSONObject);
    }

    private void e() {
        this.c = null;
        f();
    }

    private static void f() {
        Stub.getMapWidgetManager().removeMsgStateBarView();
        Stub.getMapWidgetManager().setFullScreen(false);
        if (!a.a.a) {
            euk.b(AMapAppGlobal.getTopActivity());
        }
    }

    public final void a(@NonNull final FeatureType featureType, @NonNull final View view) {
        boolean z;
        Long valueOf = Long.valueOf(11259375);
        if (featureType != FeatureType.TYPE_GROUP && featureType != FeatureType.TYPE_FREERIDE) {
            a f2 = f(featureType);
            if (f2 == null) {
                f2 = new a(featureType);
            }
            f2.f = null;
            f2.a(view);
            if (a.a.a) {
                StatusBar statusBar = new StatusBar(AMapAppGlobal.getTopActivity(), f2.g == FeatureType.TYPE_TAXI ? R.drawable.statusbar_type_taxi : R.drawable.statusbar_type_ride, f2.d);
                statusBar.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        arh.e(featureType);
                        view.performClick();
                    }
                });
                f2.j = statusBar;
            } else {
                OldMapStatusBar oldMapStatusBar = new OldMapStatusBar(AMapAppGlobal.getTopActivity(), f2.d);
                oldMapStatusBar.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        arh.e(featureType);
                        view.performClick();
                    }
                });
                f2.j = oldMapStatusBar;
            }
            if (valueOf.longValue() != 11259375) {
                if (this.a.size() != 0) {
                    Long l = this.a.get(0).h;
                    if (l.longValue() != -1 && valueOf.longValue() <= l.longValue()) {
                        z = false;
                        if (!z && this.a.size() > 0) {
                            this.a.add(1, f2);
                        }
                    }
                }
                z = true;
                this.a.add(1, f2);
            }
            this.a.add(0, f2);
            c();
        }
    }

    public final void a(final FeatureType featureType, CharSequence charSequence, final OnClickListener onClickListener) {
        if (featureType != FeatureType.TYPE_GROUP && featureType != FeatureType.TYPE_FREERIDE) {
            bid pageContext = AMapPageUtil.getPageContext();
            Context activity = pageContext != null ? pageContext.getActivity() : null;
            if (activity != null) {
                a f2 = f(featureType);
                if (f2 == null) {
                    f2 = new a(featureType);
                }
                f2.f = null;
                f2.d = charSequence;
                if (a.a.a) {
                    StatusBar statusBar = new StatusBar(activity, f2.g == FeatureType.TYPE_TAXI ? R.drawable.statusbar_type_taxi : R.drawable.statusbar_type_ride, charSequence);
                    statusBar.setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            arh.e(featureType);
                            if (onClickListener != null) {
                                view.setTag(featureType);
                                onClickListener.onClick(view);
                            }
                        }
                    });
                    f2.j = statusBar;
                } else {
                    OldMapStatusBar oldMapStatusBar = new OldMapStatusBar(activity, charSequence);
                    oldMapStatusBar.setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            arh.e(featureType);
                            if (onClickListener != null) {
                                view.setTag(featureType);
                                onClickListener.onClick(view);
                            }
                        }
                    });
                    f2.j = oldMapStatusBar;
                }
                this.a.add(0, f2);
                c();
            }
        }
    }

    private static void a(a aVar, View view, LayoutParams layoutParams) {
        if (aVar.i != a.b) {
            a(aVar);
            Stub.getMapWidgetManager().setMsgStateBarView(view, layoutParams);
            Stub.getMapWidgetManager().setFullScreen(true);
            if (!a.a.a) {
                euk.a(AMapAppGlobal.getTopActivity(), 0);
            }
            if (view.getVisibility() == 0) {
                ObjectAnimator.ofFloat(view, "translationY", new float[]{-200.0f, 0.0f}).setDuration(500).start();
                ObjectAnimator.ofFloat(view, "alpha", new float[]{0.0f, 1.0f}).setDuration(500).start();
            }
        }
    }

    private LayoutParams a(Context context) {
        if (a.a.a) {
            if (this.d == null) {
                this.d = new LayoutParams(-1, -2);
                this.d.leftMargin = AMapAppGlobal.getApplication().getResources().getDimensionPixelSize(R.dimen.statusbar_margin_left);
                this.d.topMargin = AMapAppGlobal.getApplication().getResources().getDimensionPixelSize(R.dimen.statusbar_margin_top) + ags.d(context);
                this.d.rightMargin = AMapAppGlobal.getApplication().getResources().getDimensionPixelSize(R.dimen.statusbar_margin_right);
            }
            return this.d;
        }
        if (this.e == null) {
            this.e = new LayoutParams(-1, -2);
        }
        return this.e;
    }

    static /* synthetic */ void e(FeatureType featureType) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("from", featureType == FeatureType.TYPE_TAXI ? "共享" : "单车");
            jSONObject.put("status", a.a.a ? LogVersionType.REDESIGN : LogVersionType.UN_REDESIGN);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00001", "B282", jSONObject);
    }
}
