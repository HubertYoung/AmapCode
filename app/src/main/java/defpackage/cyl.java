package defpackage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ListAdapter;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.frequentlocation.entity.FrequentLocationData;
import com.autonavi.minimap.bundle.frequentlocation.util.FrequentLocationInfoEx;
import com.autonavi.minimap.bundle.frequentlocation.view.FrequentLocationAdapter2;
import com.autonavi.minimap.bundle.frequentlocation.view.FrequentLocationAdapter2.d;
import com.autonavi.minimap.bundle.frequentlocation.view.FrequentLocationAdapter2.f;
import com.autonavi.minimap.bundle.frequentlocation.view.FrequentLocationView$2;
import com.autonavi.minimap.widget.draglistview.DragSortController;
import com.autonavi.minimap.widget.draglistview.DragSortListView;
import com.autonavi.minimap.widget.draglistview.DragSortListView.DropListener;
import com.autonavi.minimap.widget.draglistview.DragSortListView.RemoveListener;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cyl reason: default package */
/* compiled from: FrequentLocationView */
public final class cyl implements OnClickListener, cyn {
    private static final int C = (FrequentLocationAdapter2.mIsTesting ? 10 : 30);
    private static int D;
    private static int E;
    /* access modifiers changed from: private */
    public boolean A;
    private int B = -1;
    private DropListener F = new DropListener() {
        public final void drop(int i, int i2) {
            cyl.a(i + 2, true);
            if (i != i2) {
                FrequentLocationData frequentLocationData = (FrequentLocationData) cyl.this.f.getItem(i);
                List data = cyl.this.f.getData();
                ArrayList arrayList = new ArrayList();
                if (data != null) {
                    data.remove(frequentLocationData);
                    data.add(i2, frequentLocationData);
                }
                for (int i3 = 0; i3 <= i2; i3++) {
                    FrequentLocationData frequentLocationData2 = (FrequentLocationData) cyl.this.f.getItem(i3);
                    if (!frequentLocationData2.b) {
                        frequentLocationData2.b = true;
                        arrayList.add(frequentLocationData2);
                    }
                }
                cyl.this.f.notifyDataSetChanged();
                cyl.this.g.refreshFooter();
                cyl.d();
                cyl.b((List<FrequentLocationData>) arrayList);
            }
        }
    };
    private RemoveListener G = new RemoveListener() {
        public final void remove(int i) {
        }
    };
    public View a;
    cyj b = new cyj();
    public com.autonavi.minimap.bundle.frequentlocation.view.FrequentLocationAdapter2.a c = new com.autonavi.minimap.bundle.frequentlocation.view.FrequentLocationAdapter2.a() {
        public final void a(FrequentLocationData frequentLocationData) {
            cyl.this.a(false);
            cyl.this.g.planRoute(frequentLocationData.b());
            int i = frequentLocationData.a == 1001 ? 0 : frequentLocationData.a == 1002 ? 1 : frequentLocationData.a == 1000 ? cyl.this.f.getPosition(frequentLocationData) + 2 : -1;
            cyl.a(i, false);
        }

        public final void a() {
            cyl.this.A = true;
            cyl.this.g.editLocation(1001);
        }

        public final void b() {
            cyl.this.A = true;
            cyl.this.g.editLocation(1002);
        }

        public final void c() {
            cyl.this.a(false);
            cyl.this.g.search();
        }

        public final void b(FrequentLocationData frequentLocationData) {
            cyl.this.f.getData().remove(frequentLocationData);
            cyl.this.f.notifyDataSetChanged();
            cyl.this.g.refreshFooter();
            if (cyl.this.f.getCount() == 0) {
                cyl.this.f.setIsEditMode(false);
            }
            if (frequentLocationData.b) {
                cyl.d();
            } else {
                cyl.b(frequentLocationData);
            }
        }

        public final void d() {
            cyl.this.A = true;
            cyl.this.g.onAddPoi();
        }

        public final void a(boolean z) {
            cyl.this.g.onEditModeChanged(z);
        }
    };
    /* access modifiers changed from: private */
    public bid d;
    /* access modifiers changed from: private */
    public cym e;
    /* access modifiers changed from: private */
    public FrequentLocationAdapter2 f;
    /* access modifiers changed from: private */
    public cyg g;
    /* access modifiers changed from: private */
    public cyk h;
    /* access modifiers changed from: private */
    public View i;
    private View j;
    /* access modifiers changed from: private */
    public int k;
    /* access modifiers changed from: private */
    public int l;
    private b m;
    private View n;
    /* access modifiers changed from: private */
    public DragSortListView o;
    private DragSortController p;
    private View q;
    private d r;
    private View s;
    private d t;
    private View u;
    private f v;
    private View w;
    private com.autonavi.minimap.bundle.frequentlocation.view.FrequentLocationAdapter2.b x;
    private ViewGroup y;
    private View z;

    /* renamed from: cyl$a */
    /* compiled from: FrequentLocationView */
    class a extends DragSortController {
        DragSortListView a;
        private int c;
        private int d = -1;
        private FrequentLocationAdapter2 e;

        public final void onDragFloatView(View view, Point point, Point point2) {
        }

        public a(DragSortListView dragSortListView, FrequentLocationAdapter2 frequentLocationAdapter2) {
            super(dragSortListView);
            this.a = dragSortListView;
            this.e = frequentLocationAdapter2;
            this.d = frequentLocationAdapter2.getDivPosition();
        }

        public final int startDragPosition(MotionEvent motionEvent) {
            return super.startDragPosition(motionEvent);
        }

        public final View onCreateFloatView(int i) {
            this.c = i;
            if (!this.e.getIsEditMode() || this.e.getCount() <= 1) {
                return null;
            }
            View view = this.e.getView(i, null, this.a);
            View inflate = LayoutInflater.from(cyl.this.d.getContext()).inflate(R.layout.item_frequent_location_normal_drag_layout, this.a, false);
            ((ViewGroup) inflate.findViewById(R.id.drag_view_container)).addView(view);
            return inflate;
        }

        public final int getFloatViewHeightOffset() {
            return cyl.this.d.getContext().getResources().getDimensionPixelSize(R.dimen.frequent_drag_item_shadow_height) * -1 * 2;
        }
    }

    /* renamed from: cyl$b */
    /* compiled from: FrequentLocationView */
    class b implements Runnable {
        private b() {
        }

        /* synthetic */ b(cyl cyl, byte b) {
            this();
        }

        public final void run() {
            if (cyl.this.d != null && cyl.this.d.isAlive()) {
                cyl.this.i.setVisibility(0);
                cyk k = cyl.this.h;
                int h = cyl.this.k;
                int min = Math.min(cyl.this.o.getHeight(), cyl.this.l);
                AnonymousClass1 r4 = new AnimationListener() {
                    public final void onAnimationRepeat(@Nullable Animation animation) {
                    }

                    public final void onAnimationStart(@Nullable Animation animation) {
                    }

                    public final void onAnimationEnd(@Nullable Animation animation) {
                        cyl.this.e();
                    }
                };
                float f = (float) h;
                float f2 = (float) min;
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.2f, 1.05f, 0.2f, 1.05f, f, f2);
                scaleAnimation.setDuration(225);
                ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.05f, 0.95f, 1.05f, 0.95f, f, f2);
                scaleAnimation2.setDuration(325);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, k.a.getAlpha());
                alphaAnimation.setDuration(225);
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(scaleAnimation2);
                animationSet.setFillBefore(true);
                animationSet.setFillAfter(true);
                animationSet.setInterpolator(new FastOutSlowInInterpolator());
                animationSet.setAnimationListener(r4);
                k.a.startAnimation(animationSet);
                AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.0f, 0.4f);
                alphaAnimation2.setDuration(225);
                alphaAnimation2.setInterpolator(new DecelerateInterpolator());
                alphaAnimation2.setFillBefore(true);
                alphaAnimation2.setFillAfter(true);
                k.b.startAnimation(alphaAnimation2);
            }
        }
    }

    static /* synthetic */ int d() {
        int i2 = D;
        D = i2 + 1;
        return i2;
    }

    public cyl(bid bid) {
        this.d = bid;
        Context context = this.d.getContext();
        LayoutInflater from = LayoutInflater.from(context);
        this.i = from.inflate(R.layout.view_layer_frequent_location_layout, null);
        this.e = new cym(this.d, this.i);
        this.e.b = new a() {
            public final void a() {
                if (cyl.this.d.isAlive()) {
                    cyl.this.a(true);
                }
            }
        };
        View findViewById = this.i.findViewById(R.id.frequent_location_layer_bg_view);
        findViewById.setOnClickListener(this);
        this.l = context.getResources().getDimensionPixelSize(R.dimen.frequent_view_max_height);
        this.j = this.i.findViewById(R.id.location_recycler_host_view);
        this.f = new FrequentLocationAdapter2(context);
        this.f.setAdapterListener(this.c);
        this.n = this.j.findViewById(R.id.drag_list_view_container);
        this.o = (DragSortListView) this.j.findViewById(R.id.drag_list_view);
        this.o.setDropListener(this.F);
        this.o.setRemoveListener(this.G);
        a aVar = new a(this.o, this.f);
        aVar.setDragHandleId(R.id.drag_edit_state_view);
        aVar.setDragInitMode(2);
        this.p = aVar;
        this.o.setFloatViewManager(this.p);
        this.o.setOnTouchListener(this.p);
        this.o.setOnItemClickListener(new FrequentLocationView$2(this));
        this.q = LayoutInflater.from(context).inflate(R.layout.item_frequent_location_home_company_layout, this.o, false);
        this.r = new d(this.q, this.f.getHomeCompanyViewHolderOwner());
        this.o.addHeaderView(this.q);
        this.s = LayoutInflater.from(context).inflate(R.layout.item_frequent_location_home_company_layout, this.o, false);
        this.t = new d(this.s, this.f.getHomeCompanyViewHolderOwner());
        this.o.addHeaderView(this.s);
        this.u = LayoutInflater.from(context).inflate(R.layout.item_frequent_location_search_layout, this.o, false);
        this.v = new f(this.u, this.f.getSearchViewHolderOwner());
        this.z = LayoutInflater.from(context).inflate(R.layout.item_frequent_location_footer_layout, this.o, false);
        this.o.addFooterView(this.z);
        this.y = (ViewGroup) this.j.findViewById(R.id.drag_list_view_footer_container);
        this.w = LayoutInflater.from(context).inflate(R.layout.item_frequent_location_edit_layout, this.y, false);
        this.x = new com.autonavi.minimap.bundle.frequentlocation.view.FrequentLocationAdapter2.b(this.w, this.f.getEditViewHolderOwner());
        this.y.addView(this.w);
        this.o.setAdapter((ListAdapter) this.f);
        this.k = context.getResources().getDimensionPixelSize(R.dimen.frequent_view_width);
        this.h = new cyk(this.n, findViewById);
        this.a = from.inflate(R.layout.entry_frequent_location_view_layout, null);
        this.a.setOnClickListener(this);
        this.m = new b(this, 0);
    }

    public final void a(cyg cyg) {
        this.g = cyg;
    }

    private void a(List<FrequentLocationData> list, FrequentLocationData frequentLocationData, FrequentLocationData frequentLocationData2, FrequentLocationData frequentLocationData3, FrequentLocationData frequentLocationData4) {
        this.f.setDataAndChangeDataSet(list);
        a(frequentLocationData, frequentLocationData2);
        b(frequentLocationData3, frequentLocationData4);
    }

    public final void a(List<FrequentLocationData> list, FrequentLocationData frequentLocationData, FrequentLocationData frequentLocationData2, FrequentLocationData frequentLocationData3, FrequentLocationData frequentLocationData4, boolean z2) {
        if (this.e.c) {
            a(list, frequentLocationData, frequentLocationData2, frequentLocationData3, frequentLocationData4);
            return;
        }
        View view = this.a;
        View view2 = this.j;
        view.getLocationInWindow(new int[2]);
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        Context context = this.d.getContext();
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.map_container_btn_margin);
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.frequent_view_width);
        view2.setTranslationX((float) (((rect.right - dimensionPixelSize2) - view.getPaddingRight()) - dimensionPixelSize));
        view2.setTranslationY((float) ((rect.bottom - context.getResources().getDimensionPixelSize(R.dimen.frequent_view_max_height)) - view.getPaddingBottom()));
        cym cym = this.e;
        cym.c = true;
        cym.a.showViewLayer(cym);
        a(list, frequentLocationData, frequentLocationData2, frequentLocationData3, frequentLocationData4);
        e();
        if (z2 && !this.h.a()) {
            this.i.setVisibility(4);
            aho.a(this.m);
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        if (this.o != null) {
            this.o.setSelection(0);
        }
    }

    public final void a(FrequentLocationData frequentLocationData, FrequentLocationData frequentLocationData2) {
        this.r.b(frequentLocationData);
        this.t.b(frequentLocationData2);
    }

    public final void b(FrequentLocationData frequentLocationData, FrequentLocationData frequentLocationData2) {
        this.v.b(frequentLocationData);
        this.x.b(frequentLocationData2);
    }

    public final void a(boolean z2) {
        this.g.cancelLoadData();
        aho.b(this.m);
        if (this.A) {
            this.A = false;
        } else if (this.e.c) {
            c();
            if (!z2 || this.h.a()) {
                this.e.a();
                this.f.onDismissLocation();
            } else {
                cyk cyk = this.h;
                AnonymousClass2 r0 = new AnimationListener() {
                    public final void onAnimationRepeat(@Nullable Animation animation) {
                    }

                    public final void onAnimationStart(@Nullable Animation animation) {
                    }

                    public final void onAnimationEnd(@Nullable Animation animation) {
                        if (cyl.this.d.isAlive()) {
                            cyl.this.e.a();
                            cyl.this.f.onDismissLocation();
                        }
                    }
                };
                int width = cyk.a.getWidth();
                int height = cyk.a.getHeight();
                if (width == 0 || height == 0) {
                    r0.onAnimationEnd(null);
                } else {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.2f, 1.0f, 0.2f, (float) width, (float) height);
                    AlphaAnimation alphaAnimation = new AlphaAnimation(cyk.a.getAlpha(), 0.0f);
                    AnimationSet animationSet = new AnimationSet(true);
                    animationSet.addAnimation(alphaAnimation);
                    animationSet.addAnimation(scaleAnimation);
                    animationSet.setDuration(195);
                    animationSet.setFillBefore(true);
                    animationSet.setFillAfter(true);
                    animationSet.setInterpolator(new DecelerateInterpolator());
                    animationSet.setAnimationListener(r0);
                    cyk.a.startAnimation(animationSet);
                    AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.4f, 0.0f);
                    alphaAnimation2.setDuration(195);
                    alphaAnimation2.setFillBefore(true);
                    alphaAnimation2.setFillAfter(true);
                    alphaAnimation2.setInterpolator(new DecelerateInterpolator());
                    cyk.b.startAnimation(alphaAnimation2);
                }
            }
            if (this.B != -1) {
                Activity activity = this.d.getActivity();
                if (activity != null && this.d.isAlive()) {
                    Window window = activity.getWindow();
                    if (window != null) {
                        window.setSoftInputMode(this.B);
                        this.B = -1;
                    }
                }
            }
        }
    }

    public final void a() {
        this.a.setVisibility(8);
        this.i.setVisibility(8);
        this.e.a();
    }

    public final void b() {
        this.a.setVisibility(0);
        this.i.setVisibility(0);
        if (this.e.c) {
            Activity activity = this.d.getActivity();
            if (activity != null && this.d.isAlive()) {
                Window window = activity.getWindow();
                if (window != null) {
                    LayoutParams attributes = window.getAttributes();
                    if (attributes != null) {
                        this.B = attributes.softInputMode;
                    }
                    if (this.B != 48) {
                        window.setSoftInputMode(48);
                    }
                }
            }
        }
    }

    public final void onClick(View view) {
        if (view != this.a || bnp.a()) {
            if (view.getId() == R.id.frequent_location_layer_bg_view) {
                a(true);
            }
        } else if (this.g != null) {
            this.g.loadData();
            LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_FREQUENT_LOCATION_ENTRY_CLICK);
        }
    }

    /* access modifiers changed from: private */
    public static void b(List<FrequentLocationData> list) {
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            for (FrequentLocationData c2 : list) {
                arrayList.add(c2.c());
            }
            if (cyc.e() != null) {
                cyc.e().a((String[]) arrayList.toArray(new String[arrayList.size()]));
            }
        }
    }

    public final void c() {
        if (E != D) {
            E = D;
            bim.aa().n().setFrequentAddress(c(this.f.getCloudSyncData(C)));
        }
    }

    private static String c(List<FrequentLocationData> list) {
        Object obj;
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        if (list != null) {
            for (FrequentLocationData next : list) {
                POI b2 = next.b();
                if (b2 != null) {
                    FrequentLocationInfoEx frequentLocationInfoEx = new FrequentLocationInfoEx();
                    frequentLocationInfoEx.copyFromPoi(b2);
                    frequentLocationInfoEx.frequentRemark = next.c;
                    try {
                        obj = new JSONObject(cyj.a(frequentLocationInfoEx));
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                        obj = null;
                    }
                    if (obj != null) {
                        jSONArray.put(obj);
                    }
                }
            }
        }
        try {
            jSONObject.put("value", jSONArray);
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        return jSONObject.toString();
    }

    public final void a(FrequentLocationData frequentLocationData) {
        FrequentLocationData frequentLocationData2;
        frequentLocationData.b = true;
        List data = this.f.getData();
        if (!TextUtils.isEmpty(frequentLocationData.c())) {
            int size = data.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                frequentLocationData2 = (FrequentLocationData) data.get(size);
                if (frequentLocationData2.b) {
                    break;
                } else if (frequentLocationData.c().equals(frequentLocationData2.c())) {
                    break;
                } else {
                    size--;
                }
            }
        }
        frequentLocationData2 = null;
        if (frequentLocationData2 != null) {
            this.f.getData().remove(frequentLocationData2);
        }
        this.f.getData().add(0, frequentLocationData);
        this.f.notifyDataSetChanged();
        this.g.refreshFooter();
        D++;
        this.f.setIsEditMode(false);
        e();
    }

    static /* synthetic */ void a(int i2, boolean z2) {
        int i3 = i2 + 1;
        JSONObject jSONObject = new JSONObject();
        if (!z2) {
            try {
                jSONObject.put("action", String.valueOf(i3));
            } catch (JSONException unused) {
                return;
            }
        } else {
            jSONObject.put("type", String.valueOf(i3));
        }
        LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_FREQUENT_LOCATION_LIST_ITEM_CLICK, jSONObject);
    }

    static /* synthetic */ void b(FrequentLocationData frequentLocationData) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(frequentLocationData);
        b((List<FrequentLocationData>) arrayList);
    }
}
