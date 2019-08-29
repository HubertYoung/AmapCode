package defpackage;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.entity.Trip;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.IBusErrorReportRemind;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.d;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage;
import com.autonavi.minimap.route.bus.busline.page.BusLineToMapPage;
import com.autonavi.minimap.route.bus.inter.IBusLineResult;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.common.util.RouteSharingUtil.AnonymousClass5;
import com.autonavi.minimap.route.net.module.RTBusLocation;
import java.lang.ref.WeakReference;
import org.json.JSONObject;

/* renamed from: dut reason: default package */
/* compiled from: BusLineDetailPresenter */
public final class dut extends eaf<BusLineDetailPage> implements com.autonavi.minimap.route.common.view.RouteFrameFooterView.a, defpackage.dun.a, defpackage.duo.a, defpackage.dup.a {
    public final dup a = new dup("4");
    public final dun b = new dun();
    public Bus c;
    public int d = -2;
    public boolean e;
    private final dur f = new dur();
    private final duo g = new duo();
    private final Handler h = new a(this);
    private boolean i = false;
    private String j;
    private boolean k;

    /* renamed from: dut$a */
    /* compiled from: BusLineDetailPresenter */
    static class a extends Handler {
        private final WeakReference<dut> a;

        public a(dut dut) {
            this.a = new WeakReference<>(dut);
        }

        public final void handleMessage(Message message) {
            try {
                dut dut = (dut) this.a.get();
                if (dut != null) {
                    if (((BusLineDetailPage) dut.mPage).isAlive()) {
                        int i = message.what;
                        if (i != 1000) {
                            if (i == 2000) {
                                dut.b(dut);
                            }
                            return;
                        }
                        dut.a(dut, (String) message.obj, message.arg1);
                    }
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }

    public dut(BusLineDetailPage busLineDetailPage) {
        super(busLineDetailPage);
    }

    public final void onStart() {
        zt.a().a("realtime_bus_detail", true);
        IBusErrorReportRemind iBusErrorReportRemind = (IBusErrorReportRemind) ank.a(IBusErrorReportRemind.class);
        if (iBusErrorReportRemind != null) {
            iBusErrorReportRemind.handleResume(((BusLineDetailPage) this.mPage).getContext());
        }
        bim.aa().a((biv) this.f);
    }

    public final void onPause() {
        super.onPause();
        zt.a().a("realtime_bus_detail", false);
    }

    public final void onStop() {
        IBusErrorReportRemind iBusErrorReportRemind = (IBusErrorReportRemind) ank.a(IBusErrorReportRemind.class);
        if (iBusErrorReportRemind != null) {
            iBusErrorReportRemind.dismissDialog();
        }
        this.f.a();
        bim.aa().a((biv) null);
        this.a.a();
    }

    public final void onDestroy() {
        this.h.removeMessages(1000);
        this.h.removeMessages(2000);
        this.b.e = null;
        this.a.b = null;
        this.a.a();
        this.g.c = null;
        this.g.b = false;
        ((BusLineDetailPage) this.mPage).setResult(ResultType.OK, (PageBundle) null);
    }

    public final void onResult(int i2, ResultType resultType, PageBundle pageBundle) {
        if (resultType == ResultType.OK && i2 == 4105 && pageBundle != null && pageBundle.getBoolean("result_need_exchange", false)) {
            this.b.b();
        }
    }

    public final void a(int i2, String str) {
        this.h.sendMessage(this.h.obtainMessage(1000, i2, -1, str));
    }

    public final void b() {
        if (this.c.isRealTime) {
            RTBusLocation rTBusLocation = null;
            int i2 = -1;
            if (ebj.a(this.a.c)) {
                dup dup = this.a;
                rTBusLocation = dup.a.get(this.c.id);
                if (rTBusLocation != null) {
                    i2 = rTBusLocation.getStatus();
                }
            }
            if (rTBusLocation != null) {
                Trip trip = rTBusLocation.getTrip(0);
                if (trip == null || !trip.isArriving()) {
                    if (this.k) {
                        this.a.a(30000);
                        this.k = false;
                        eao.b("---BusLineDetail--update polling time 30s");
                    }
                } else if (!this.k) {
                    this.a.a(10000);
                    this.k = true;
                    eao.b("---BusLineDetail--update polling time 10s");
                }
            }
            BusLineDetailPage busLineDetailPage = (BusLineDetailPage) this.mPage;
            busLineDetailPage.c.setLoadingRealTime(false);
            busLineDetailPage.c.setRealTimeBusline(rTBusLocation);
            busLineDetailPage.c.setRealTimeStatus(i2);
            busLineDetailPage.b();
        }
    }

    public final void a() {
        this.c = this.b.b;
        ((BusLineDetailPage) this.mPage).a(this.c);
        ((BusLineDetailPage) this.mPage).a(f());
        this.g.a(this.c);
    }

    public final void c() {
        Context context = ((BusLineDetailPage) this.mPage).getContext();
        Bus bus = this.c;
        if (context != null && bus != null) {
            StringBuilder sb = new StringBuilder();
            String str = "";
            String string = context.getString(R.string.busline_total_length_about);
            if (!TextUtils.isEmpty(bus.name)) {
                String str2 = bus.name;
                int lastIndexOf = str2.lastIndexOf("(");
                sb.append(str2.substring(0, lastIndexOf));
                sb.append("\n");
                String substring = str2.substring(lastIndexOf + 1, str2.length() - 1);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(substring);
                sb2.append("\n");
                sb.append(sb2.toString());
            }
            if (bus.startTime >= 0 && bus.endTime >= 0) {
                String string2 = context.getString(R.string.busline_first_last_car_time);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(string2);
                sb3.append(Token.SEPARATOR);
                sb3.append(axt.b(bus.startTime));
                sb3.append(" - ");
                sb3.append(axt.b(bus.endTime));
                str = sb3.toString();
            }
            if (bus.length > 0) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(string);
                sb4.append(bus.length);
                sb4.append(context.getString(R.string.km));
                string = sb4.toString();
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str);
            sb5.append(",");
            sb5.append(string);
            sb.append(sb5.toString());
            if (bus.stations != null) {
                sb.append(context.getString(R.string.busline_via_station));
                int length = bus.stations.length;
                for (int i2 = 0; i2 < length; i2++) {
                    sb.append(bus.stations[i2]);
                    if (i2 == length - 1) {
                        sb.append("。");
                    } else {
                        sb.append("、");
                    }
                }
            }
            String str3 = bus.id;
            String str4 = bus.name;
            String sb6 = sb.toString();
            StringBuilder sb7 = new StringBuilder();
            sb7.append(context.getString(R.string.busline_share_title));
            sb7.append(bus.name);
            sb7.append(",");
            sb7.append(str);
            sb7.append("  ");
            String sb8 = sb7.toString();
            dct dct = new dct(0);
            dct.h = true;
            dct.g = true;
            dct.f = true;
            dct.d = true;
            dct.e = true;
            dct.a = true;
            StringBuilder sb9 = new StringBuilder();
            sb9.append(ConfigerHelper.getInstance().getShareMsgUrl());
            sb9.append("?b=");
            sb9.append(str3);
            String sb10 = sb9.toString();
            dcb dcb = (dcb) defpackage.esb.a.a.a(dcb.class);
            if (dcb != null) {
                AnonymousClass5 r1 = new dcd(str4, sb6, context, sb10, sb8) {
                    final /* synthetic */ String a;
                    final /* synthetic */ String b;
                    final /* synthetic */ Context c;
                    final /* synthetic */ String d;
                    final /* synthetic */ String e;

                    {
                        this.a = r1;
                        this.b = r2;
                        this.c = r3;
                        this.d = r4;
                        this.e = r5;
                    }

                    public final ShareParam getShareDataByType(int i) {
                        switch (i) {
                            case 0:
                                d dVar = new d();
                                dVar.a = this.b;
                                dVar.b = this.d;
                                return dVar;
                            case 1:
                                com.autonavi.minimap.bundle.share.entity.ShareParam.a aVar = new com.autonavi.minimap.bundle.share.entity.ShareParam.a();
                                aVar.a = this.b;
                                aVar.b = this.d;
                                return aVar;
                            case 3:
                                e eVar = new e(0);
                                eVar.f = this.a;
                                eVar.a = this.b;
                                eVar.g = ahc.a(this.c, R.drawable.weixin_bus);
                                eVar.b = this.d;
                                eVar.e = 0;
                                return eVar;
                            case 4:
                                e eVar2 = new e(1);
                                eVar2.f = this.a;
                                eVar2.a = this.b;
                                eVar2.g = ahc.a(this.c, R.drawable.weixin_bus);
                                eVar2.b = this.d;
                                eVar2.e = 0;
                                return eVar2;
                            case 5:
                                f fVar = new f();
                                fVar.a = this.e;
                                fVar.b = this.d;
                                return fVar;
                            default:
                                return null;
                        }
                    }
                };
                dcb.a(dct, (dcd) r1);
            }
        }
    }

    public final void d() {
        LogManager.actionLogV2("P00265", "B002");
        dva.a(this.c, this.d, false);
    }

    public final void e() {
        if (f()) {
            this.f.a();
        }
        Context context = ((BusLineDetailPage) this.mPage).getContext();
        boolean z = false;
        if (context != null) {
            String g2 = g();
            if (TextUtils.isEmpty(g2)) {
                Bus bus = this.c;
                String a2 = ebk.a();
                cos b2 = dux.b(bus);
                com com2 = (com) ank.a(com.class);
                if (com2 != null) {
                    coq a3 = com2.a(a2);
                    if (a3 != null) {
                        a3.a(b2);
                    }
                }
                ToastHelper.showToast(((BusLineDetailPage) this.mPage).getString(R.string.route_save_success));
                z = true;
            } else {
                ebk.a(g2);
                dwl.a(AMapAppGlobal.getApplication(), g2, null);
                ToastHelper.showToast(context.getString(R.string.route_save_cancel));
            }
        }
        ((BusLineDetailPage) this.mPage).a(z);
    }

    private boolean f() {
        return !TextUtils.isEmpty(g());
    }

    private String g() {
        if (!this.i || this.f.a || this.b.d) {
            return dux.a(this.c);
        }
        return this.j;
    }

    public final void a(int i2, int i3) {
        IBusLineResult iBusLineResult = this.b.a;
        if (iBusLineResult != null) {
            iBusLineResult.setFocusStationIndex(i2);
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("BusLineToMapFragment.IBusLineResult", iBusLineResult);
            pageBundle.putObject("BusLineToMapFragment.RealTimeBuslines", this.a.a);
            pageBundle.putString("BusLineToMapFragment.NEAR1KMSTATIONNAME", this.g.a);
            pageBundle.putBoolean("BusLineToMapFragment.ISEXCHANGE", this.b.d);
            pageBundle.putObject("BusLineToMapFragment.CURBUS", this.b.b);
            pageBundle.putInt("BusLineToMapFragment.WATCH_MODE", i3);
            ((BusLineDetailPage) this.mPage).startPageForResult(BusLineToMapPage.class, pageBundle, 4105);
        }
    }

    public final void a(int i2, boolean z) {
        this.a.a();
        if (this.c != null && i2 >= 0 && this.c.stationIds != null && i2 < this.c.stationIds.length && this.c.isRealTime) {
            ((BusLineDetailPage) this.mPage).a();
            this.a.a(this.c, this.c.stationIds[i2], z);
        }
    }

    public final void onPageCreated() {
        IBusLineResult iBusLineResult;
        PageBundle arguments = ((BusLineDetailPage) this.mPage).getArguments();
        if (arguments != null) {
            this.i = arguments.getBoolean("key_from_favorites");
            this.j = arguments.getString("item_key_from_favorites");
            Bus bus = null;
            if (arguments.containsKey("BusLineDetailFragment.IBusLineResult")) {
                iBusLineResult = (IBusLineResult) arguments.getObject("BusLineDetailFragment.IBusLineResult");
                if (iBusLineResult != null) {
                    if (arguments.containsKey("BusLineDetailFragment.FOCUS_BUSLINE_INDEX")) {
                        iBusLineResult.setFocusBusLineIndex(arguments.getInt("BusLineDetailFragment.FOCUS_BUSLINE_INDEX"));
                    }
                    if (arguments.containsKey("BusLineDetailFragment.CUR_POI_PAGE")) {
                        iBusLineResult.setCurPoiPage(arguments.getInt("BusLineDetailFragment.CUR_POI_PAGE"));
                    }
                    Bus[] busLineArray = iBusLineResult.getBusLineArray(iBusLineResult.getCurPoiPage());
                    int focusBusLineIndex = iBusLineResult.getFocusBusLineIndex();
                    if (!(busLineArray == null || busLineArray[focusBusLineIndex] == null)) {
                        bus = busLineArray[focusBusLineIndex].copyObject();
                        this.b.a(iBusLineResult, bus);
                    }
                }
            } else {
                iBusLineResult = null;
            }
            if (bus != null && arguments.containsKey("bundle_key_pageId")) {
                int i2 = arguments.getInt("bundle_key_pageId");
                boolean z = bus.isRealTime;
                long adCode = iBusLineResult.getAdCode();
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(AutoJsonUtils.JSON_ADCODE, adCode);
                    jSONObject.put("itemId", i2);
                    jSONObject.put("type", z ? 1 : 0);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                LogManager.actionLogV2("P00116", "B009", jSONObject);
            }
            boolean z2 = this.i;
            if (bus != null) {
                if (z2) {
                    bus.isRealTime = false;
                }
                if (TextUtils.isEmpty(bus.key_name)) {
                    bus.key_name = bus.name.replace(bus.startName, "").replace(bus.endName, "").replace("--", "").replace("(", "").replace(")", "");
                }
            }
        }
        this.c = this.b.b;
        ((BusLineDetailPage) this.mPage).a(this.c);
        ((BusLineDetailPage) this.mPage).a(f());
        BusLineDetailPage busLineDetailPage = (BusLineDetailPage) this.mPage;
        if (this.b.a()) {
            busLineDetailPage.a.setVisibility(0);
        } else {
            busLineDetailPage.a.setVisibility(8);
        }
        this.f.b = ((BusLineDetailPage) this.mPage).getContentView();
        this.g.a(this.c);
        IBusErrorReportRemind iBusErrorReportRemind = (IBusErrorReportRemind) ank.a(IBusErrorReportRemind.class);
        if (iBusErrorReportRemind != null) {
            iBusErrorReportRemind.handlePageOnResume((Activity) ((BusLineDetailPage) this.mPage).getContext(), 4);
        }
    }

    static /* synthetic */ void a(dut dut, String str, int i2) {
        if (dut.d == -2 && !TextUtils.isEmpty(str)) {
            String[] strArr = dut.c.stations;
            if (strArr != null) {
                int i3 = 0;
                while (true) {
                    if (i3 >= strArr.length) {
                        break;
                    } else if (str.equals(strArr[i3])) {
                        dut.d = i3;
                        ((BusLineDetailPage) dut.mPage).a(dut.d);
                        break;
                    } else {
                        i3++;
                    }
                }
            }
        }
        if (i2 != -1) {
            dut.h.sendEmptyMessageDelayed(2000, 200);
            dut.a(i2, false);
        }
        BusLineDetailPage busLineDetailPage = (BusLineDetailPage) dut.mPage;
        if (busLineDetailPage.c != null) {
            busLineDetailPage.c.setNear1KmStationName(str);
            busLineDetailPage.b();
            if (busLineDetailPage.getActivity() != null) {
                busLineDetailPage.b.setSelectionFromTop(i2, (busLineDetailPage.getActivity().getWindow().getDecorView().getMeasuredHeight() / 4) - 48);
                return;
            }
            busLineDetailPage.b.setSelection(i2);
        }
    }

    static /* synthetic */ void b(dut dut) {
        BusLineDetailPage busLineDetailPage = (BusLineDetailPage) dut.mPage;
        busLineDetailPage.d.setVisibility(0);
        busLineDetailPage.d.setTranslationY(0.0f);
    }
}
