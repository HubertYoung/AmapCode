package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.bundle.busnavi.ajx.ModuleBus;
import com.autonavi.bundle.busnavi.ajx.ModuleBus.a;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPath.WalkPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.BusPaths;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.bundle.routecommon.entity.OnFootNaviSection;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.basemap.errorback.inter.IBusErrorReportRemind;
import com.autonavi.minimap.route.ajx.module.bus.IBusNaviDetailModuleBridge;
import com.autonavi.minimap.route.ajx.module.bus.IBusNaviDetailModuleBridge.Callback;
import com.autonavi.minimap.route.bus.navidetail.model.pojo.BusAlternativeItem;
import com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailDialogFactory.OnDialogClickListener;
import com.autonavi.minimap.route.common.util.RouteSharingUtil;
import com.autonavi.sdk.location.LocationInstrument;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dxd reason: default package */
/* compiled from: BusNaviDetailPresenter */
public final class dxd extends Ajx3PagePresenter implements bgo, IBusNaviDetailModuleBridge, b {
    private ModuleBus a;
    @NonNull
    private dwv b = new dwv();
    private dxe c;
    private dxg<dxa> d;
    private dxg<dxb> e;

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public dxd(Ajx3Page ajx3Page) {
        super(ajx3Page);
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        a(pageBundle);
        if (this.b.a()) {
            ((a) this.mPage).c();
        }
    }

    public final void onPageCreated() {
        super.onPageCreated();
        if (this.b.a()) {
            ((a) this.mPage).c();
        }
    }

    public final void onStart() {
        super.onStart();
        if (this.a != null) {
            this.a.updatePOIInfo();
        }
        if (this.c != null) {
            dxe.a(((a) this.mPage).e(), false);
        }
        this.b.a(true, ((a) this.mPage).f());
    }

    public final void onStop() {
        super.onStop();
        if (this.c != null) {
            IBusErrorReportRemind iBusErrorReportRemind = (IBusErrorReportRemind) ank.a(IBusErrorReportRemind.class);
            if (iBusErrorReportRemind != null) {
                iBusErrorReportRemind.dismissDialog();
            }
        }
        this.b.a(false, null);
    }

    public final void onDestroy() {
        CharSequence charSequence;
        super.onDestroy();
        dwv dwv = this.b;
        if (dwv.f == null || dwv.f.size() <= 0) {
            charSequence = null;
        } else {
            ArrayList arrayList = new ArrayList();
            for (Entry<String, Map<String, BusAlternativeItem>> value : dwv.f.entrySet()) {
                for (Entry value2 : ((Map) value.getValue()).entrySet()) {
                    arrayList.add((BusAlternativeItem) value2.getValue());
                }
            }
            charSequence = JSON.toJSONString(arrayList);
        }
        if (!TextUtils.isEmpty(charSequence)) {
            PageBundle g = ((a) this.mPage).g();
            if (g == null) {
                g = new PageBundle();
            }
            g.putObject("refresh_json", charSequence);
            g.putObject("bus_route_data", this.a.getOriginalBusRouteData());
            ((Ajx3Page) this.mPage).setResult(ResultType.OK, g);
        }
        if (this.a != null) {
            this.a.onBusRemindPageDestroy();
        }
        PlaySoundUtils.getInstance().setHandleInterruptEventObj(null);
        dwv dwv2 = this.b;
        if (dwv2.d != null) {
            dww dww = dwv2.d;
            if (dww.a != null) {
                dww.a.cancel();
                dww.a = null;
            }
        }
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (i == 1 && this.e != null) {
            this.e.a(resultType);
        }
    }

    public final void a(PageBundle pageBundle) {
        this.b.a(dwx.a(pageBundle));
        if (!this.b.a()) {
            ((a) this.mPage).b();
            return;
        }
        this.c = new dxe();
        dxe.a(((a) this.mPage).e(), true);
    }

    public final String c() {
        if (!this.b.a()) {
            return "";
        }
        return this.b.a.b();
    }

    public final boolean d() {
        return this.b.a() && this.b.a.a();
    }

    public final boolean e() {
        return this.b.a() && this.b.a.a;
    }

    public final void a(ModuleBus moduleBus) {
        this.a = moduleBus;
        this.a.setBusNaviDetailModuleBridge(this);
    }

    public final void a(boolean z) {
        if (this.c != null && this.b.a()) {
            dwz dwz = new dwz();
            dwz.a = this.b.a.b;
            dwz.b = this.b.a.c;
            dwz.c = z;
            this.c.a((a) this.mPage, dwz);
        }
    }

    public final void a() {
        if (this.a != null) {
            this.a.requestRealTimeBusData();
        }
    }

    public final void b() {
        if (this.b.a() && this.b.a.b != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("time", ahw.a(new Date(), (String) "yyyy-MM-dd HH:mm:ss"));
                if (this.b.a() && this.b.a.b != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.b.a.b.getBsid());
                    sb.append("_");
                    sb.append(this.b.a.b.getFocusBusPathIndex());
                    jSONObject.put("itemId", sb.toString());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            dwj.a("B045", jSONObject);
        }
    }

    public final void onListStatusChange(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("height")) {
                ((a) this.mPage).a((float) jSONObject.getInt("height"));
                return;
            }
            if (jSONObject.has("status")) {
                ((a) this.mPage).a(jSONObject.getString("status"));
            }
        } catch (JSONException e2) {
            if (bno.a) {
                throw new RuntimeException(e2);
            }
            e2.printStackTrace();
        }
    }

    public final void onExchangeAlterRoute(dwy dwy) {
        BusAlternativeItem busAlternativeItem;
        dwv dwv = this.b;
        if (dwy != null && dwt.a(dwv.a.b, dwy)) {
            IBusRouteResult iBusRouteResult = dwv.a.b;
            int i = dwy.a;
            int i2 = dwy.b;
            int i3 = dwy.c;
            BusPathSection busPathSection = null;
            if (iBusRouteResult == null) {
                busAlternativeItem = null;
            } else {
                BusPath busPathWithIndex = iBusRouteResult.getBusPathWithIndex(i);
                if (!(busPathWithIndex == null || busPathWithIndex.mPathSections == null || busPathWithIndex.mPathSections.length <= 0 || i2 >= busPathWithIndex.mPathSections.length || i3 == 0)) {
                    busPathSection = busPathWithIndex.mPathSections[i2];
                }
                String valueOf = String.valueOf(iBusRouteResult.getFocusBusPathIndex());
                String valueOf2 = String.valueOf(i2);
                busAlternativeItem = new BusAlternativeItem();
                busAlternativeItem.setPlan_number(valueOf);
                busAlternativeItem.setSegment_number(valueOf2);
                if (busPathSection != null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(busPathSection.bus_id);
                    BusPathSection[] busPathSectionArr = busPathSection.alter_list;
                    int length = busPathSectionArr != null ? busPathSectionArr.length : 0;
                    for (int i4 = 0; i4 < length; i4++) {
                        arrayList.add(busPathSectionArr[i4].bus_id);
                    }
                    busAlternativeItem.setAlter_list(arrayList);
                }
            }
            if (busAlternativeItem != null) {
                if (dwv.f == null) {
                    dwv.f = new HashMap();
                }
                String plan_number = busAlternativeItem.getPlan_number();
                String segment_number = busAlternativeItem.getSegment_number();
                if (dwv.f.containsKey(plan_number)) {
                    dwv.f.get(plan_number).put(segment_number, busAlternativeItem);
                    return;
                }
                HashMap hashMap = new HashMap();
                hashMap.put(segment_number, busAlternativeItem);
                dwv.f.put(plan_number, hashMap);
            }
        }
    }

    public final void triggerShare() {
        if (this.mPage != null && ((a) this.mPage).d() && this.b.a() && this.b.a.b != null) {
            Context context = ((Ajx3Page) this.mPage).getContext();
            IBusRouteResult iBusRouteResult = this.b.a.b;
            dct dct = new dct(0);
            dct.h = true;
            dct.g = true;
            RouteSharingUtil.a(context, dct, iBusRouteResult);
        }
    }

    public final void triggerFavor(a aVar) {
        dwv dwv = this.b;
        a aVar2 = (a) this.mPage;
        dwj.a("B008", null);
        if (dwv.b || dwv.a == null || dwv.a.b == null) {
            int b2 = dwv.b();
            int c2 = dwv.c();
            if (b2 != -1 && b2 < c2 && !TextUtils.isEmpty(dwv.c[b2])) {
                ebk.a(dwv.c[b2]);
                dwv.c[b2] = null;
            }
            dwv.b = false;
            dwv.e.b = true;
            if (aVar != null) {
                aVar.a(false);
            }
            return;
        }
        POI shareFromPOI = dwv.a.b.getShareFromPOI();
        POI shareToPOI = dwv.a.b.getShareToPOI();
        if (dwv.d == null) {
            dwv.d = new dww();
        }
        if (shareFromPOI != null && "我的位置".equals(shareFromPOI.getName())) {
            aVar2.a(true, (String) "dialog_type_progress", (OnDialogClickListener) new OnDialogClickListener(aVar) {
                final /* synthetic */ a a;

                {
                    this.a = r2;
                }

                public final void a(int i) {
                    if (i == 1) {
                        boolean d = dwv.this.d();
                        if (this.a != null) {
                            this.a.a(d);
                        }
                    }
                }
            });
            dwv.d.a(shareFromPOI.getPoint(), new a(aVar2, shareFromPOI, aVar) {
                final /* synthetic */ a a;
                final /* synthetic */ POI b;
                final /* synthetic */ a c;

                {
                    this.a = r2;
                    this.b = r3;
                    this.c = r4;
                }

                public final void a(int i, String str) {
                    this.a.a(false, (String) "dialog_type_progress", (OnDialogClickListener) null);
                    if (i == 1 && !TextUtils.isEmpty(str)) {
                        this.b.setName(str);
                    }
                    boolean d2 = dwv.this.d();
                    if (this.c != null) {
                        this.c.a(d2);
                    }
                }
            });
        } else if (shareToPOI == null || dwv.d == null || !"我的位置".equals(shareToPOI.getName())) {
            boolean d2 = dwv.d();
            if (aVar != null) {
                aVar.a(d2);
            }
        } else {
            aVar2.a(true, (String) "dialog_type_progress", (OnDialogClickListener) new OnDialogClickListener(aVar) {
                final /* synthetic */ a a;

                {
                    this.a = r2;
                }

                public final void a(int i) {
                    if (i == 1) {
                        boolean d = dwv.this.d();
                        if (this.a != null) {
                            this.a.a(d);
                        }
                    }
                }
            });
            dwv.d.a(shareToPOI.getPoint(), new a(aVar2, shareToPOI, aVar) {
                final /* synthetic */ a a;
                final /* synthetic */ POI b;
                final /* synthetic */ a c;

                {
                    this.a = r2;
                    this.b = r3;
                    this.c = r4;
                }

                public final void a(int i, String str) {
                    this.a.a(false, (String) "dialog_type_progress", (OnDialogClickListener) null);
                    if (i == 1 && !TextUtils.isEmpty(str)) {
                        this.b.setName(str);
                    }
                    boolean d2 = dwv.this.d();
                    if (this.c != null) {
                        this.c.a(d2);
                    }
                }
            });
        }
    }

    public final void accessUgc() {
        dxb dxb;
        if (this.b.a()) {
            if (this.e == null) {
                this.e = new dxh();
            }
            IBusRouteResult iBusRouteResult = this.b.a.b;
            if (iBusRouteResult == null) {
                dxb = null;
            } else {
                dxb dxb2 = new dxb();
                dxb2.a = iBusRouteResult.getFromPOI().getName();
                dxb2.b = iBusRouteResult.getToPOI().getName();
                dxb2.c = iBusRouteResult.getBsid();
                dxb2.d = iBusRouteResult.getFocusBusPathIndex();
                BusPaths busPathsResult = iBusRouteResult.getBusPathsResult();
                int focusBusPathIndex = iBusRouteResult.getFocusBusPathIndex();
                if (busPathsResult != null && busPathsResult.mBusPaths != null && focusBusPathIndex >= 0 && focusBusPathIndex < busPathsResult.mBusPaths.length) {
                    dvv a2 = dwk.a(busPathsResult.mBusPaths[focusBusPathIndex]);
                    if (a2 != null) {
                        dxb2.e = a2.c;
                    }
                }
                dxb = dxb2;
            }
            this.e.a((a) this.mPage, dxb);
        }
    }

    public final void accessReroute() {
        if (this.b.a() && this.b.a.b != null) {
            bax bax = (bax) a.a.a(bax.class);
            if (bax != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putBoolean("bundle_key_auto_route", true);
                POI createPOI = POIFactory.createPOI("我的位置", LocationInstrument.getInstance().getLatestPosition());
                POI toPOI = this.b.a.b.getToPOI();
                pageBundle.putInt("key_type", RouteType.BUS.getValue());
                pageBundle.putObject("bundle_key_poi_start", createPOI);
                pageBundle.putObject("bundle_key_poi_end", toPOI);
                bax.a(pageBundle);
            }
        }
    }

    public final boolean isFavorite() {
        return this.b.b;
    }

    public final void calcDistanceToCurrentPath(@NonNull Callback<Integer> callback) {
        dwv dwv = this.b;
        int i = -1;
        if (!(dwv.a == null || dwv.a.b == null)) {
            BusPath focusBusPath = dwv.a.b.getFocusBusPath();
            if (focusBusPath != null) {
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < focusBusPath.mSectionNum; i2++) {
                    BusPathSection busPathSection = focusBusPath.mPathSections[i2];
                    int[] iArr = busPathSection.mXs;
                    int[] iArr2 = busPathSection.mYs;
                    if (!(iArr == null || iArr2 == null)) {
                        for (int i3 = 0; i3 < iArr.length; i3++) {
                            arrayList.add(new GeoPoint(iArr[i3], iArr2[i3]));
                        }
                    }
                    WalkPath walkPath = busPathSection.walk_path;
                    if (!(walkPath == null || walkPath.infolist == null || walkPath.infolist.size() <= 0)) {
                        Iterator<OnFootNaviSection> it = walkPath.infolist.iterator();
                        while (it.hasNext()) {
                            OnFootNaviSection next = it.next();
                            if (next != null) {
                                int[] iArr3 = next.mXs;
                                int[] iArr4 = next.mYs;
                                if (!(iArr3 == null || iArr4 == null)) {
                                    for (int i4 = 0; i4 < iArr3.length; i4++) {
                                        arrayList.add(new GeoPoint(iArr3[i4], iArr4[i4]));
                                    }
                                }
                            }
                        }
                    }
                }
                GeoPoint[] geoPointArr = new GeoPoint[arrayList.size()];
                arrayList.toArray(geoPointArr);
                i = cfe.a(LocationInstrument.getInstance().getLatestPosition(), geoPointArr);
            }
        }
        callback.callback(Integer.valueOf(i));
    }

    public final void updateBusRemindStatus(boolean z) {
        if (z) {
            eac a2 = eac.a();
            ead a3 = ead.a(1);
            a3.c = eay.a(R.string.notification_title_bus_navi);
            a3.d = eay.a(R.string.notification_content_bus_navi);
            a2.a(a3);
        } else {
            eac.a().a(1);
        }
        if (this.mPage != null) {
            ((a) this.mPage).b(z);
        }
    }

    public final void selectBusRoute(int i) {
        dwv dwv = this.b;
        if (dwv.a != null && dwv.a.b != null && dwv.a.b.getFocusBusPathIndex() != i) {
            dwv.a.b.setFocusBusPathIndex(i);
            dwv.a(dwv.e.b);
        }
    }

    public final void showBusNaviDisclaimer(@NonNull final Callback callback) {
        if (this.mPage != null) {
            if (!dwu.a()) {
                ((a) this.mPage).a(true, (String) "dialog_type_declare_bus", (OnDialogClickListener) new OnDialogClickListener() {
                    public final void a(int i) {
                        if (i == 0) {
                            dwu.b();
                            if (ebm.a(dxd.a(dxd.this).e())) {
                                callback.callback(new Object[0]);
                            }
                        }
                    }
                });
                return;
            }
            if (ebm.a(((a) this.mPage).e())) {
                callback.callback(new Object[0]);
            }
        }
    }

    public final void switchRealtimeBusEnable(boolean z) {
        if (this.mPage != null) {
            ((a) this.mPage).a(z);
        }
    }

    public final void triggerFeedback() {
        if (this.mPage != null && ((a) this.mPage).d()) {
            a(true);
        }
    }

    public final void accessFootOrRideNavi(dxa dxa) {
        if (ebm.a(((a) this.mPage).e())) {
            if (this.d == null) {
                this.d = new dxf();
            }
            this.d.a((a) this.mPage, dxa);
        }
    }

    static /* synthetic */ a a(dxd dxd) {
        return (a) dxd.mPage;
    }
}
