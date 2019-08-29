package com.autonavi.minimap.basemap.traffic.presenter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.CloseFrame;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.basemap.traffic.page.AbstractTrafficSubmitPage;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractTrafficSubmitPresenter<Page extends AbstractTrafficSubmitPage> extends AbstractBasePresenter<Page> implements OnClickListener {
    private static final Object t = new Object();
    /* access modifiers changed from: 0000 */
    public ArrayList<String> a = new ArrayList<>();
    protected boolean b = false;
    protected int c = 2;
    protected String d = "";
    protected String e = "";
    protected String f = "";
    public int g = 1;
    protected String h = "";
    protected String i = "";
    public GeoPoint j = null;
    public POI k = null;
    protected int l = -1;
    protected boolean m = false;
    boolean n = false;
    /* access modifiers changed from: 0000 */
    public ProgressDlg o = null;
    private dtd p;
    /* access modifiers changed from: private */
    public WeakReference<Dialog> q;
    /* access modifiers changed from: private */
    public a r;
    /* access modifiers changed from: private */
    public final OnClickListener s = new OnClickListener() {
        public final void onClick(View view) {
            String str = (String) view.getTag();
            int i = 1;
            for (int i2 = 0; i2 < AbstractTrafficSubmitPresenter.this.a.size(); i2++) {
                if (str.equals(AbstractTrafficSubmitPresenter.this.a.get(i2))) {
                    i = i2 + 1;
                }
            }
            cst cst = new cst(((AbstractTrafficSubmitPage) AbstractTrafficSubmitPresenter.this.mPage).getActivity(), AbstractTrafficSubmitPresenter.this.a, new defpackage.cst.b() {
                public final void a(String str) {
                    AbstractTrafficSubmitPresenter.this.e();
                    AbstractTrafficSubmitPage abstractTrafficSubmitPage = (AbstractTrafficSubmitPage) AbstractTrafficSubmitPresenter.this.mPage;
                    abstractTrafficSubmitPage.a(abstractTrafficSubmitPage.a.findViewWithTag(str));
                    AbstractTrafficSubmitPresenter.a(AbstractTrafficSubmitPresenter.this, str);
                }

                public final void a() {
                    AbstractTrafficSubmitPresenter.this.j();
                }
            }, i);
            AbstractTrafficSubmitPresenter.this.q = new WeakReference(cst);
            cst.show();
        }
    };
    private com.autonavi.common.Callback.a u;

    static class ReverseGeocodeListener implements Callback<ReverseGeocodeResponser> {
        private WeakReference<AbstractTrafficSubmitPresenter> weakReference;

        public ReverseGeocodeListener(AbstractTrafficSubmitPresenter abstractTrafficSubmitPresenter) {
            this.weakReference = new WeakReference<>(abstractTrafficSubmitPresenter);
        }

        public void error(Throwable th, boolean z) {
            if (this.weakReference != null && this.weakReference.get() != null) {
                ((AbstractTrafficSubmitPresenter) this.weakReference.get()).r.sendMessage(((AbstractTrafficSubmitPresenter) this.weakReference.get()).r.obtainMessage(1006));
            }
        }

        public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
            if (reverseGeocodeResponser.errorCode == -1 || reverseGeocodeResponser.errorCode == 500 || reverseGeocodeResponser.errorCode == 7) {
                if (!(this.weakReference == null || this.weakReference.get() == null)) {
                    ((AbstractTrafficSubmitPresenter) this.weakReference.get()).r.sendMessage(((AbstractTrafficSubmitPresenter) this.weakReference.get()).r.obtainMessage(1006));
                }
            } else if (!(this.weakReference == null || this.weakReference.get() == null)) {
                AbstractTrafficSubmitPresenter.a((AbstractTrafficSubmitPresenter) this.weakReference.get(), reverseGeocodeResponser);
            }
        }
    }

    static class a extends Handler {
        private WeakReference<AbstractTrafficSubmitPage> a;
        private WeakReference<AbstractTrafficSubmitPresenter> b;

        a(WeakReference<AbstractTrafficSubmitPage> weakReference, WeakReference<AbstractTrafficSubmitPresenter> weakReference2) {
            this.a = weakReference;
            this.b = weakReference2;
        }

        public final void handleMessage(Message message) {
            if (this.a != null && this.b != null) {
                AbstractTrafficSubmitPage abstractTrafficSubmitPage = (AbstractTrafficSubmitPage) this.a.get();
                AbstractTrafficSubmitPresenter abstractTrafficSubmitPresenter = (AbstractTrafficSubmitPresenter) this.b.get();
                if (abstractTrafficSubmitPage != null && abstractTrafficSubmitPage.isAlive() && abstractTrafficSubmitPresenter != null && message.what == 1015) {
                    abstractTrafficSubmitPage.a(AbstractTrafficSubmitPresenter.b((ReverseGeocodeResponser) message.obj));
                }
            }
        }
    }

    static class b implements defpackage.dtd.b {
        View a = LayoutInflater.from(AMapPageUtil.getAppContext()).inflate(R.layout.traffic_report_pic_item, null);
        ImageView b = ((ImageView) this.a.findViewById(R.id.image_thumbnail));
        ProgressBar c = ((ProgressBar) this.a.findViewById(R.id.progress_error_load));
        WeakReference<AbstractTrafficSubmitPresenter> d;

        public b(AbstractTrafficSubmitPresenter abstractTrafficSubmitPresenter) {
            this.d = new WeakReference<>(abstractTrafficSubmitPresenter);
        }

        public final void a(final Bitmap bitmap, final String str) {
            if (this.d != null && this.d.get() != null && AbstractTrafficSubmitPresenter.f((AbstractTrafficSubmitPresenter) this.d.get()).isAlive()) {
                ((AbstractTrafficSubmitPresenter) this.d.get()).r.post(new Runnable() {
                    public final void run() {
                        if (b.this.d != null && b.this.d.get() != null && AbstractTrafficSubmitPresenter.f((AbstractTrafficSubmitPresenter) b.this.d.get()).isAlive()) {
                            AbstractTrafficSubmitPresenter abstractTrafficSubmitPresenter = (AbstractTrafficSubmitPresenter) b.this.d.get();
                            abstractTrafficSubmitPresenter.a.add(str);
                            b.this.a.setTag(str);
                            b.this.b.setTag(str);
                            b.this.c.setVisibility(4);
                            if (bitmap != null && !bitmap.isRecycled()) {
                                b.this.b.setImageBitmap(bitmap);
                            }
                            b.this.b.setOnClickListener(abstractTrafficSubmitPresenter.s);
                        }
                    }
                });
            }
        }

        public final void a() {
            if (this.d != null && this.d.get() != null && AbstractTrafficSubmitPresenter.f((AbstractTrafficSubmitPresenter) this.d.get()).isAlive()) {
                ((AbstractTrafficSubmitPresenter) this.d.get()).r.post(new Runnable() {
                    public final void run() {
                        if (b.this.d != null && b.this.d.get() != null && AbstractTrafficSubmitPresenter.f((AbstractTrafficSubmitPresenter) b.this.d.get()).isAlive()) {
                            b.this.c.setVisibility(0);
                            AbstractTrafficSubmitPage f = AbstractTrafficSubmitPresenter.f((AbstractTrafficSubmitPresenter) b.this.d.get());
                            f.a.addView(b.this.a);
                            if (f.a.getChildCount() > 0) {
                                f.a.setVisibility(0);
                            }
                            f.a();
                        }
                    }
                });
            }
        }

        public final void b() {
            if (this.d != null && this.d.get() != null && AbstractTrafficSubmitPresenter.f((AbstractTrafficSubmitPresenter) this.d.get()).isAlive()) {
                ((AbstractTrafficSubmitPresenter) this.d.get()).r.post(new Runnable() {
                    public final void run() {
                        if (b.this.d != null && b.this.d.get() != null && AbstractTrafficSubmitPresenter.f((AbstractTrafficSubmitPresenter) b.this.d.get()).isAlive()) {
                            AbstractTrafficSubmitPresenter abstractTrafficSubmitPresenter = (AbstractTrafficSubmitPresenter) b.this.d.get();
                            ToastHelper.showLongToast(AbstractTrafficSubmitPresenter.f(abstractTrafficSubmitPresenter).getString(R.string.gallay_error));
                            AbstractTrafficSubmitPresenter.f(abstractTrafficSubmitPresenter).a(b.this.a);
                            if (abstractTrafficSubmitPresenter.a.size() == 0) {
                                AbstractTrafficSubmitPresenter.f(abstractTrafficSubmitPresenter).a((View) b.this.b);
                            }
                            b.this.b.setImageResource(R.drawable.v3_icon);
                        }
                    }
                });
            }
        }
    }

    public void c() {
    }

    public void d() {
    }

    public void e() {
    }

    public void f() {
    }

    public AbstractTrafficSubmitPresenter(Page page) {
        super(page);
        this.r = new a(new WeakReference(page), new WeakReference(this));
    }

    public void onPageCreated() {
        super.onPageCreated();
        AbstractTrafficSubmitPage abstractTrafficSubmitPage = (AbstractTrafficSubmitPage) this.mPage;
        PageBundle arguments = ((AbstractTrafficSubmitPage) this.mPage).getArguments();
        boolean z = false;
        if (arguments != null && arguments.getBoolean("intent_report_page_simple_version", false)) {
            z = true;
        }
        abstractTrafficSubmitPage.a(z);
        a();
    }

    public void onClick(View view) {
        POI poi;
        int id = view.getId();
        if (id == R.id.title_btn_left) {
            k();
        } else if (id == R.id.image_plus) {
            j();
        } else {
            if (id == R.id.btn_pick_poi) {
                f();
                if (this.k == null) {
                    poi = POIFactory.createPOI(((AbstractTrafficSubmitPage) this.mPage).getString(R.string.my_location), LocationInstrument.getInstance().getLatestPosition());
                    if (poi == null) {
                        poi = POIFactory.createPOI("", DoNotUseTool.getMapManager().getMapView().o());
                    }
                } else {
                    poi = this.k;
                }
                ((InputMethodManager) ((AbstractTrafficSubmitPage) this.mPage).getActivity().getSystemService("input_method")).hideSoftInputFromWindow(((AbstractTrafficSubmitPage) this.mPage).getContentView().getApplicationWindowToken(), 0);
                SelectPoiFromMapBean selectPoiFromMapBean = new SelectPoiFromMapBean();
                selectPoiFromMapBean.setOldPOI(poi);
                selectPoiFromMapBean.setHideOldPoi(true);
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("SelectPoiFromMapBean", selectPoiFromMapBean);
                ((AbstractTrafficSubmitPage) this.mPage).startPageForResult((String) "amap.basemap.action.base_select_fix_poi_from_map_page", pageBundle, 196613);
            }
        }
    }

    /* access modifiers changed from: private */
    public void j() {
        d();
        Activity activity = ((AbstractTrafficSubmitPage) this.mPage).getActivity();
        if (this.p == null && activity != null) {
            this.p = new dtd((bid) this.mPage, activity);
            this.p.g = new b(this);
        }
        if (this.p != null) {
            if (!this.n) {
                this.p.a();
                return;
            }
            this.p.show();
        }
    }

    /* access modifiers changed from: protected */
    public final void b() {
        try {
            if (this.o != null && this.o.isShowing()) {
                this.o.dismiss();
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003f, code lost:
        if (android.text.TextUtils.isEmpty(r3) == false) goto L_0x0043;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onResult(int r2, com.autonavi.common.Page.ResultType r3, com.autonavi.common.PageBundle r4) {
        /*
            r1 = this;
            super.onResult(r2, r3, r4)
            r0 = 196613(0x30005, float:2.75513E-40)
            if (r2 != r0) goto L_0x0053
            com.autonavi.common.Page$ResultType r2 = com.autonavi.common.Page.ResultType.OK
            if (r3 != r2) goto L_0x0053
            if (r4 == 0) goto L_0x0053
            java.lang.String r2 = "SelectFixPoiFromMapFragment.MapClickResult"
            boolean r2 = r4.containsKey(r2)
            if (r2 == 0) goto L_0x0053
            java.lang.String r2 = "SelectFixPoiFromMapFragment.MapClickResult"
            java.lang.Object r2 = r4.getObject(r2)
            com.autonavi.common.model.POI r2 = (com.autonavi.common.model.POI) r2
            java.lang.String r3 = "SelectFixPoiFromMapFragment.MapClickResultObj"
            java.lang.Object r3 = r4.getObject(r3)
            if (r2 == 0) goto L_0x0053
            r4 = 1
            r1.b = r4
            r1.k = r2
            java.lang.String r4 = r2.getName()
            if (r3 == 0) goto L_0x0042
            boolean r0 = r3 instanceof com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser
            if (r0 == 0) goto L_0x0042
            com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser r3 = (com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser) r3
            java.lang.String r3 = b(r3)
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 != 0) goto L_0x0042
            goto L_0x0043
        L_0x0042:
            r3 = r4
        L_0x0043:
            com.autonavi.map.fragmentcontainer.page.IPage r4 = r1.mPage
            com.autonavi.minimap.basemap.traffic.page.AbstractTrafficSubmitPage r4 = (com.autonavi.minimap.basemap.traffic.page.AbstractTrafficSubmitPage) r4
            r4.a(r3)
            com.autonavi.common.model.GeoPoint r2 = r2.getPoint()
            r1.j = r2
            r2 = 3
            r1.c = r2
        L_0x0053:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.basemap.traffic.presenter.AbstractTrafficSubmitPresenter.onResult(int, com.autonavi.common.Page$ResultType, com.autonavi.common.PageBundle):void");
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if ((i2 == 12288 || i2 == 12289) && this.p != null) {
            if (i3 == -1) {
                try {
                    if (!(this.q == null || this.q.get() == null || !((Dialog) this.q.get()).isShowing())) {
                        ((Dialog) this.q.get()).dismiss();
                    }
                    a(true);
                } catch (Exception unused) {
                    ToastHelper.showLongToast(((AbstractTrafficSubmitPage) this.mPage).getString(R.string.gallay_error));
                }
            }
            dtd dtd = this.p;
            if (i3 == -1) {
                if (i2 == dtd.h) {
                    if (dtd.j) {
                        Map<String, Object> a2 = adu.a(intent);
                        dtd.f = ((Integer) a2.get("shooted_orientation")).intValue();
                        defpackage.dtd.a aVar = new defpackage.dtd.a((String) a2.get("camera_pic_path"));
                        aVar.a = dtd.g;
                        aVar.start();
                        return;
                    }
                    defpackage.dtd.a aVar2 = new defpackage.dtd.a(dtd.e.getAbsolutePath());
                    aVar2.a = dtd.g;
                    aVar2.start();
                } else if (i2 == dtd.i) {
                    Uri data = intent.getData();
                    if (data != null) {
                        defpackage.dtd.a aVar3 = new defpackage.dtd.a(data);
                        aVar3.a = dtd.g;
                        aVar3.start();
                    }
                } else if (i2 == dtd.l) {
                    Uri data2 = intent.getData();
                    if (data2 != null) {
                        defpackage.dtd.a aVar4 = new defpackage.dtd.a(data2);
                        aVar4.a = dtd.g;
                        aVar4.start();
                    }
                }
            }
        }
    }

    public ON_BACK_TYPE onBackPressed() {
        if (((AbstractTrafficSubmitPage) this.mPage).hasViewLayer()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        k();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public void onDestroy() {
        super.onDestroy();
        if (!this.m) {
            a(false);
        }
    }

    private void k() {
        c();
        ((AbstractTrafficSubmitPage) this.mPage).setResult(ResultType.CANCEL, (PageBundle) null);
        ((AbstractTrafficSubmitPage) this.mPage).finish();
    }

    /* access modifiers changed from: protected */
    public final File g() {
        if (this.a != null && this.a.size() > 0) {
            File file = new File(this.a.get(0));
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final void i() {
        a(null, 0, true);
    }

    /* access modifiers changed from: protected */
    public final void a(cso cso, long j2, boolean z) {
        if (((AbstractTrafficSubmitPage) this.mPage).getContentView() != null) {
            PageBundle pageBundle = new PageBundle();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("Traffic_Event_Report_Success", "true");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            pageBundle.putObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, jSONObject.toString());
            ((AbstractTrafficSubmitPage) this.mPage).setResult(ResultType.OK, pageBundle);
            ((AbstractTrafficSubmitPage) this.mPage).finish();
            if (z && cso != null && this.j != null && j2 != 0) {
                MapManager mapManager = DoNotUseTool.getMapManager();
                if (mapManager != null) {
                    mapManager.getMapView().a(this.j.x, this.j.y);
                    csn.a().a(this.j, this.l > 0 ? this.l : (int) j2, cso.e);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void a(boolean z) {
        synchronized (t) {
            if (z) {
                try {
                    ((AbstractTrafficSubmitPage) this.mPage).b();
                } catch (Exception e2) {
                    e2.printStackTrace();
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (this.a != null && this.a.size() > 0) {
                for (int i2 = 0; i2 < this.a.size(); i2++) {
                    if (!TextUtils.isEmpty(this.a.get(i2))) {
                        File file = new File(this.a.get(i2));
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
                this.a.clear();
            }
        }
    }

    /* access modifiers changed from: private */
    public static String b(ReverseGeocodeResponser reverseGeocodeResponser) {
        if (reverseGeocodeResponser == null) {
            return "";
        }
        String district = reverseGeocodeResponser.getDistrict();
        if (TextUtils.isEmpty(district)) {
            district = reverseGeocodeResponser.getCity();
        }
        if (TextUtils.isEmpty(district)) {
            district = reverseGeocodeResponser.getProvince();
        }
        String desc = reverseGeocodeResponser.getDesc();
        if (!TextUtils.isEmpty(desc) && !TextUtils.isEmpty(district)) {
            int indexOf = desc.indexOf(district);
            if (indexOf >= 0) {
                desc = desc.substring(indexOf + district.length());
            }
        }
        return desc;
    }

    /* access modifiers changed from: protected */
    public void a() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition == null) {
            latestPosition = DoNotUseTool.getMapManager().getMapView().o();
        }
        if (latestPosition != null) {
            if (this.u != null) {
                this.u.cancel();
                this.u = null;
            }
            this.u = ReverseGeocodeManager.getReverseGeocodeResult(latestPosition, new ReverseGeocodeListener(this));
        }
    }

    /* access modifiers changed from: protected */
    public final void h() {
        if (LocationInstrument.getInstance().getLatestPosition(5) == null) {
            ToastHelper.showLongToast(((AbstractTrafficSubmitPage) this.mPage).getString(R.string.oper_location_err));
        } else if (LocationInstrument.getInstance().getLatestLocation().getProvider().equals(WidgetType.GPS)) {
            dfm dfm = (dfm) ank.a(dfm.class);
            if (dfm != null) {
                dfl[] f2 = dfm.f();
                if (f2 == null || f2.length <= 0 || f2[f2.length - 1] == null) {
                    this.j = LocationInstrument.getInstance().getLatestPosition(5);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                StringBuilder sb3 = new StringBuilder();
                StringBuilder sb4 = new StringBuilder();
                StringBuilder sb5 = new StringBuilder();
                if (!(f2 == null || f2.length == 0)) {
                    if (f2[f2.length - 1] != null) {
                        this.j = cob.a(f2[f2.length - 1].b, f2[f2.length - 1].a);
                    }
                    int i2 = 0;
                    while (i2 < f2.length) {
                        if (f2[i2] != null) {
                            dfl dfl = f2[i2];
                            Calendar instance = Calendar.getInstance();
                            instance.set(dfl.e, dfl.f, dfl.g, dfl.h, dfl.i, dfl.j);
                            sb3.append(instance.getTimeInMillis() / 1000);
                            sb.append(f2[i2].a);
                            sb2.append(f2[i2].b);
                            sb4.append(dfl.c);
                            sb5.append(dfl.d);
                            if (i2 + 1 < f2.length) {
                                sb.append(",");
                                sb2.append(",");
                                sb3.append(",");
                                sb4.append(",");
                                sb5.append(",");
                            }
                        }
                        i2++;
                    }
                }
                this.i = sb5.toString();
                this.h = sb4.toString();
                this.f = sb3.toString();
                this.d = sb.toString();
                this.e = sb2.toString();
                this.c = 1;
            }
        } else {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition == null) {
                ToastHelper.showLongToast(((AbstractTrafficSubmitPage) this.mPage).getString(R.string.oper_location_err));
            } else {
                this.j = latestPosition;
            }
        }
    }

    static /* synthetic */ void a(AbstractTrafficSubmitPresenter abstractTrafficSubmitPresenter, String str) {
        if (!TextUtils.isEmpty(str)) {
            File file = new File(str);
            try {
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (abstractTrafficSubmitPresenter.a != null && abstractTrafficSubmitPresenter.a.size() > 0) {
                Iterator<String> it = abstractTrafficSubmitPresenter.a.iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    if (str.equals(next)) {
                        abstractTrafficSubmitPresenter.a.remove(next);
                        return;
                    }
                }
            }
        }
    }

    static /* synthetic */ AbstractTrafficSubmitPage f(AbstractTrafficSubmitPresenter abstractTrafficSubmitPresenter) {
        return (AbstractTrafficSubmitPage) abstractTrafficSubmitPresenter.mPage;
    }

    static /* synthetic */ void a(AbstractTrafficSubmitPresenter abstractTrafficSubmitPresenter, ReverseGeocodeResponser reverseGeocodeResponser) {
        Message obtainMessage = abstractTrafficSubmitPresenter.r.obtainMessage(CloseFrame.TLS_ERROR);
        obtainMessage.obj = reverseGeocodeResponser;
        abstractTrafficSubmitPresenter.r.sendMessage(obtainMessage);
    }
}
