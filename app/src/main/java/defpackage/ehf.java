package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.alipay.AlibcAlipay;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.archivement.ArchivementRequestHolder;
import com.autonavi.minimap.archivement.param.ReportRequest;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import com.autonavi.minimap.order.param.BikeOrderDetailRequest;
import com.autonavi.minimap.route.logs.operation.OperationCollectionParam;
import com.autonavi.minimap.route.logs.operation.OperationCollectionRequestCallback;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil.OperationType;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.sharebike.model.BaseNetResult;
import com.autonavi.minimap.route.sharebike.model.BikeTrack;
import com.autonavi.minimap.route.sharebike.model.OrderDetail;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingMapPage;
import com.autonavi.minimap.route.sharebike.presenter.ShareRidingFinishPresenter$1;
import com.autonavi.minimap.route.sharebike.view.ShareRidingFinshShareView;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.io.File;

/* renamed from: ehf reason: default package */
/* compiled from: ShareRidingFinishPresenter */
public final class ehf extends eae<ShareRidingFinishPage> {
    /* access modifiers changed from: private */
    public static boolean p = true;
    public eej a;
    public RideTraceHistory b = null;
    public RideState c = null;
    public OrderDetail d = null;
    public String e;
    public String f;
    public int g = 1;
    public boolean h;
    public boolean i;
    public boolean j = true;
    public boolean k;
    /* access modifiers changed from: private */
    public a l;
    /* access modifiers changed from: private */
    public efj m;
    private boolean n = true;
    private BikeTrack o;
    private int q = 0;
    /* access modifiers changed from: private */
    public ctl r;
    private boolean s = false;
    /* access modifiers changed from: private */
    public boolean t = false;
    /* access modifiers changed from: private */
    public boolean u = false;
    private boolean v;
    private boolean w;
    /* access modifiers changed from: private */
    public int x;

    /* renamed from: ehf$a */
    /* compiled from: ShareRidingFinishPresenter */
    static class a extends ecs<ShareRidingFinishPage> {
        a(ShareRidingFinishPage shareRidingFinishPage) {
            super(shareRidingFinishPage);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            ShareRidingFinishPage shareRidingFinishPage = (ShareRidingFinishPage) a();
            if (ehf.p) {
                String name = ShareRidingMapPage.class.getName();
                StringBuilder sb = new StringBuilder("mapPage == null ");
                sb.append(shareRidingFinishPage == null);
                eao.e(name, sb.toString());
            }
            if (shareRidingFinishPage != null && message.what == 0) {
                dct dct = new dct(0);
                dct.f = true;
                dct.d = true;
                dct.e = true;
                dct.h = true;
                shareRidingFinishPage.c = false;
                if (shareRidingFinishPage.isAlive()) {
                    shareRidingFinishPage.a();
                    shareRidingFinishPage.c = false;
                    dcb dcb = (dcb) defpackage.esb.a.a.a(dcb.class);
                    if (dcb != null) {
                        dcb.a(dct, (dcd) new dcd() {
                            public final ShareParam getShareDataByType(int i) {
                                switch (i) {
                                    case 3:
                                        e eVar = new e(0);
                                        eVar.g = ((ehf) ShareRidingFinishPage.this.mPresenter).b();
                                        eVar.h = ((ehf) ShareRidingFinishPage.this.mPresenter).c();
                                        eVar.c = false;
                                        eVar.e = 3;
                                        return eVar;
                                    case 4:
                                        e eVar2 = new e(1);
                                        eVar2.g = ((ehf) ShareRidingFinishPage.this.mPresenter).b();
                                        eVar2.h = ((ehf) ShareRidingFinishPage.this.mPresenter).c();
                                        eVar2.c = false;
                                        eVar2.e = 3;
                                        return eVar2;
                                    case 5:
                                        f fVar = new f();
                                        fVar.a = AMapAppGlobal.getApplication().getString(R.string.ride_finish_share_weibo_msg);
                                        fVar.j = true;
                                        fVar.h = ((ehf) ShareRidingFinishPage.this.mPresenter).c();
                                        fVar.c = false;
                                        return fVar;
                                    default:
                                        return null;
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    public ehf(ShareRidingFinishPage shareRidingFinishPage) {
        super(shareRidingFinishPage);
    }

    public final void onMapAnimationFinished(aln aln) {
        super.onMapAnimationFinished(aln);
        StringBuilder sb = new StringBuilder("onMapAnimationFinished");
        sb.append(aln == null ? null : Integer.valueOf(aln.a));
        eao.e("RideFinishMapPresenter", sb.toString());
        if (aln != null && aln.a == 201 && this.mPage != null && ((ShareRidingFinishPage) this.mPage).isAlive()) {
            ((ShareRidingFinishPage) this.mPage).getMapView().f(1);
            ShareRidingFinishPage shareRidingFinishPage = (ShareRidingFinishPage) this.mPage;
            if (!shareRidingFinishPage.isAlive() || !((ehf) shareRidingFinishPage.mPresenter).g()) {
                ebr.a(true).postDelayed(new Runnable() {
                    public final void run() {
                        if (ShareRidingFinishPage.this.isAlive()) {
                            eao.e("RideFinishMapPresenter", "initResumeTimer  --------> mPresenter.isNeedSavePic = false ");
                            ((ehf) ShareRidingFinishPage.this.mPresenter).j = false;
                            ShareRidingFinishPage.this.getMapView().f(true);
                        }
                    }
                }, 5000);
            } else {
                shareRidingFinishPage.getMapView().f(true);
            }
        }
    }

    public final void onResume() {
        super.onResume();
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(true, false, false, ((ShareRidingFinishPage) this.mPage).getMapManager(), ((ShareRidingFinishPage) this.mPage).getContext());
        }
        ebo.a((AbstractBaseMapPage) this.mPage);
        if (!this.t) {
            LogManager.actionLogV2("P00301", "B002");
        }
        if (this.n && this.b != null) {
            this.a.a();
            this.a.a(this.b.k);
        }
        bty mapView = ((ShareRidingFinishPage) this.mPage).getMapView();
        if (mapView != null) {
            this.q = mapView.k(false);
            ebf.a(mapView, mapView.j(false), 0, 12);
            mapView.d(false);
            mapView.e(0.0f);
            mapView.g(0.0f);
            mapView.m(true);
            mapView.g(587202559);
        }
        a();
        this.n = false;
        if (this.s && !this.u) {
            this.r = (ctl) defpackage.esb.a.a.a(ctl.class);
            if (this.r != null) {
                this.r.a("18", new ShareRidingFinishPresenter$1(this));
            }
        }
        aho.a(new Runnable() {
            public final void run() {
                ShareRidingFinishPage shareRidingFinishPage = (ShareRidingFinishPage) ehf.this.mPage;
                shareRidingFinishPage.a.getViewTreeObserver().addOnPreDrawListener(shareRidingFinishPage);
            }
        });
        f();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0111, code lost:
        if (android.text.TextUtils.equals(((com.autonavi.minimap.ajx3.Ajx3Page) r3).getAjx3Url(), "path://amap_bundle_tripgroup/src/share_bike/ShareBikeHistory.page.js") != false) goto L_0x013d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0123  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onPageCreated() {
        /*
            r12 = this;
            super.onPageCreated()
            com.autonavi.map.core.LocationMode.stopLocation()
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r0 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r0
            r1 = 1
            r0.requestScreenOrientation(r1)
            ehf$a r0 = new ehf$a
            com.autonavi.map.fragmentcontainer.page.IPage r2 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r2 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r2
            r0.<init>(r2)
            r12.l = r0
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r0 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r0
            android.content.Context r0 = r0.getContext()
            android.graphics.Rect r0 = defpackage.ags.a(r0)
            int r2 = r0.width()
            r3 = 2
            int r2 = r2 / r3
            int r0 = r0.height()
            int r0 = r0 / r3
            com.autonavi.map.fragmentcontainer.page.IPage r4 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r4 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r4
            bty r4 = r4.getMapView()
            r4.b(r2, r0)
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r0 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r0
            com.autonavi.common.PageBundle r0 = r0.getArguments()
            r2 = 0
            if (r0 == 0) goto L_0x00c5
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r0 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r0
            com.autonavi.common.PageBundle r0 = r0.getArguments()
            java.lang.String r4 = "data"
            java.lang.Object r4 = r0.getObject(r4)
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r4 = (com.autonavi.minimap.route.ride.beans.RideTraceHistory) r4
            r12.b = r4
            java.lang.String r4 = "rideState"
            java.lang.Object r4 = r0.getObject(r4)
            com.autonavi.minimap.route.sharebike.model.RideState r4 = (com.autonavi.minimap.route.sharebike.model.RideState) r4
            r12.c = r4
            com.autonavi.minimap.route.sharebike.model.RideState r4 = r12.c
            if (r4 == 0) goto L_0x006c
            com.autonavi.minimap.route.sharebike.model.RideState r4 = r12.c
            java.lang.String r4 = r4.orderId
            r12.f = r4
        L_0x006c:
            java.lang.String r4 = "bundle_cpsource_key"
            java.lang.String r4 = r0.getString(r4)
            r12.e = r4
            java.lang.String r4 = "bundle_orderid_key"
            java.lang.String r4 = r0.getString(r4)
            r12.f = r4
            java.lang.String r4 = r12.e
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 == 0) goto L_0x008c
            java.lang.String r4 = "share_bike_cp_source"
            java.lang.String r4 = defpackage.ehs.b(r4)
            r12.e = r4
        L_0x008c:
            java.lang.String r4 = r12.f
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 == 0) goto L_0x009c
            java.lang.String r4 = "share_bike_order_id"
            java.lang.String r4 = defpackage.ehs.b(r4)
            r12.f = r4
        L_0x009c:
            java.lang.String r4 = "bundle_biketrack_key"
            java.io.Serializable r4 = r0.getSerializable(r4)
            com.autonavi.minimap.route.sharebike.model.BikeTrack r4 = (com.autonavi.minimap.route.sharebike.model.BikeTrack) r4
            r12.o = r4
            java.lang.String r4 = "isfromRidePage"
            boolean r4 = r0.getBoolean(r4)
            r12.s = r4
            java.lang.String r4 = "bundle_key_back_page"
            java.lang.String r4 = r0.getString(r4)
            java.lang.String r5 = "bundle_is_request_coin"
            boolean r5 = r0.containsKey(r5)
            if (r5 == 0) goto L_0x00c6
            java.lang.String r5 = "bundle_is_request_coin"
            boolean r0 = r0.getBoolean(r5)
            r12.v = r0
            goto L_0x00c6
        L_0x00c5:
            r4 = r2
        L_0x00c6:
            java.lang.String r0 = "page_go_back_last_page"
            boolean r0 = android.text.TextUtils.equals(r0, r4)
            r5 = 0
            if (r0 == 0) goto L_0x00d2
            r12.g = r5
            goto L_0x00df
        L_0x00d2:
            java.lang.String r0 = "page_go_to_default_page_to_routepage"
            boolean r0 = android.text.TextUtils.equals(r0, r4)
            if (r0 == 0) goto L_0x00dd
            r12.g = r3
            goto L_0x00df
        L_0x00dd:
            r12.g = r1
        L_0x00df:
            java.util.ArrayList r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPagesStacks()
            if (r0 == 0) goto L_0x013d
            int r4 = r0.size()
            if (r4 <= r1) goto L_0x0114
            int r4 = r0.size()
            int r4 = r4 - r3
            java.lang.Object r3 = r0.get(r4)
            akc r3 = (defpackage.akc) r3
            if (r3 == 0) goto L_0x0114
            boolean r4 = r3 instanceof defpackage.bun
            if (r4 == 0) goto L_0x0114
            bun r3 = (defpackage.bun) r3
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r3 = r3.a
            boolean r4 = r3 instanceof com.autonavi.minimap.ajx3.Ajx3Page
            if (r4 == 0) goto L_0x0115
            r4 = r3
            com.autonavi.minimap.ajx3.Ajx3Page r4 = (com.autonavi.minimap.ajx3.Ajx3Page) r4
            java.lang.String r4 = r4.getAjx3Url()
            java.lang.String r6 = "path://amap_bundle_tripgroup/src/share_bike/ShareBikeHistory.page.js"
            boolean r4 = android.text.TextUtils.equals(r4, r6)
            if (r4 != 0) goto L_0x013d
            goto L_0x0115
        L_0x0114:
            r3 = r2
        L_0x0115:
            esb r4 = defpackage.esb.a.a
            java.lang.Class<atb> r6 = defpackage.atb.class
            esc r4 = r4.a(r6)
            atb r4 = (defpackage.atb) r4
            if (r4 == 0) goto L_0x013d
            java.util.Iterator r0 = r0.iterator()
        L_0x0127:
            boolean r6 = r0.hasNext()
            if (r6 == 0) goto L_0x013d
            r0.next()
            com.autonavi.bundle.busnavi.api.IBusNaviPage r6 = r4.a()
            boolean r6 = r6.a(r3)
            if (r6 == 0) goto L_0x0127
            r0 = 3
            r12.g = r0
        L_0x013d:
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r0 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r0
            r0.a(r2, r2)
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r12.mPage
            if (r0 == 0) goto L_0x0163
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r0 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r0
            boolean r0 = r0.isAlive()
            if (r0 == 0) goto L_0x0163
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r0 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r0
            com.autonavi.map.fragmentcontainer.page.IPage r2 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r2 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r2
            int r3 = com.autonavi.minimap.R.string.share_bike_try_to_load
            java.lang.String r2 = r2.getString(r3)
            r0.a(r2, r1)
        L_0x0163:
            com.autonavi.minimap.order.param.BikeOrderDetailRequest r0 = new com.autonavi.minimap.order.param.BikeOrderDetailRequest
            r0.<init>()
            java.lang.String r2 = r12.e
            r0.b = r2
            java.lang.String r2 = r12.f
            r0.c = r2
            ehf$10 r2 = new ehf$10
            r2.<init>()
            defpackage.egu.a(r0, r2)
            eej r0 = new eej
            com.autonavi.map.fragmentcontainer.page.IPage r2 = r12.mPage
            com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage r2 = (com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage) r2
            r0.<init>(r2)
            r12.a = r0
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r0 = r12.b
            if (r0 == 0) goto L_0x01c8
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r0 = r12.b
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$b r0 = r0.j
            if (r0 != 0) goto L_0x018e
            goto L_0x01c8
        L_0x018e:
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r0 = r12.b
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$b r0 = r0.j
            java.util.ArrayList<com.autonavi.minimap.route.ride.beans.RideTraceHistory$a> r0 = r0.e
            if (r0 == 0) goto L_0x01c8
            int r2 = r0.size()
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$a[] r7 = new com.autonavi.minimap.route.ride.beans.RideTraceHistory.a[r2]
            r2 = 0
        L_0x019d:
            int r3 = r7.length
            if (r2 >= r3) goto L_0x01ab
            java.lang.Object r3 = r0.get(r2)
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$a r3 = (com.autonavi.minimap.route.ride.beans.RideTraceHistory.a) r3
            r7[r2] = r3
            int r2 = r2 + 1
            goto L_0x019d
        L_0x01ab:
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r0 = r12.b
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$b r0 = r0.j
            com.autonavi.common.model.POI r8 = r0.a
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r0 = r12.b
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$b r0 = r0.j
            com.autonavi.common.model.POI r9 = r0.b
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r0 = r12.b
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$b r0 = r0.j
            com.autonavi.common.model.POI r10 = r0.c
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r0 = r12.b
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$b r0 = r0.j
            boolean r11 = r0.d
            eej r6 = r12.a
            r6.a(r7, r8, r9, r10, r11)
        L_0x01c8:
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r0 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r0
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r2 = r12.b
            com.autonavi.minimap.route.sharebike.view.ShareRideFinishBottomView r0 = r0.a
            r0.setTraceDetail(r2)
            com.autonavi.minimap.route.sharebike.model.BikeTrack r0 = r12.o
            if (r0 == 0) goto L_0x01e8
            com.autonavi.minimap.route.sharebike.model.BikeTrack r0 = r12.o
            java.lang.String r0 = r0.startTime
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x01e8
            com.autonavi.map.fragmentcontainer.page.IPage r2 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r2 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r2
            r2.a(r0)
        L_0x01e8:
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r0 = r12.b
            if (r0 == 0) goto L_0x0227
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r0 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r0
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r2 = r12.b
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$RideType r2 = r2.k
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$RideType r3 = com.autonavi.minimap.route.ride.beans.RideTraceHistory.RideType.RIDE_TYPE
            if (r2 == r3) goto L_0x0202
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r2 = r12.b
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$RideType r2 = r2.k
            com.autonavi.minimap.route.ride.beans.RideTraceHistory$RideType r3 = com.autonavi.minimap.route.ride.beans.RideTraceHistory.RideType.SHARE_RIDE_TYPE
            if (r2 != r3) goto L_0x0201
            goto L_0x0202
        L_0x0201:
            r1 = 0
        L_0x0202:
            android.widget.LinearLayout r2 = r0.e
            r3 = 8
            if (r1 == 0) goto L_0x020a
            r4 = 0
            goto L_0x020c
        L_0x020a:
            r4 = 8
        L_0x020c:
            r2.setVisibility(r4)
            android.view.View r0 = r0.g
            if (r1 == 0) goto L_0x0214
            goto L_0x0215
        L_0x0214:
            r3 = 0
        L_0x0215:
            r0.setVisibility(r3)
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r12.mPage
            com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage r0 = (com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage) r0
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r1 = r12.b
            long r1 = r1.h
            java.lang.String r1 = defpackage.efv.c(r1)
            r0.a(r1)
        L_0x0227:
            com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r1 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r0.<init>(r1)
            java.lang.String r1 = "traffic"
            boolean r0 = r0.getBooleanValue(r1, r5)
            r12.k = r0
            boolean r0 = r12.s
            if (r0 == 0) goto L_0x0264
            esb r0 = defpackage.esb.a.a
            java.lang.Class<ctl> r1 = defpackage.ctl.class
            esc r0 = r0.a(r1)
            ctl r0 = (defpackage.ctl) r0
            r12.r = r0
            ctl r0 = r12.r
            if (r0 == 0) goto L_0x0258
            ctl r0 = r12.r
            java.lang.String r1 = "18"
            com.autonavi.minimap.route.sharebike.presenter.ShareRidingFinishPresenter$6 r2 = new com.autonavi.minimap.route.sharebike.presenter.ShareRidingFinishPresenter$6
            r2.<init>(r12)
            r0.a(r1, r2)
        L_0x0258:
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r12.mPage
            bid r0 = (defpackage.bid) r0
            ehf$12 r1 = new ehf$12
            r1.<init>()
            com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.setPageStateListener(r0, r1)
        L_0x0264:
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r12.mPage
            bid r0 = (defpackage.bid) r0
            ehf$9 r1 = new ehf$9
            r1.<init>()
            com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.setActivityStateListener(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ehf.onPageCreated():void");
    }

    /* access modifiers changed from: private */
    public synchronized void e() {
        if (this.mPage != null && ((ShareRidingFinishPage) this.mPage).isAlive()) {
            ((ShareRidingFinishPage) this.mPage).a(((ShareRidingFinishPage) this.mPage).getString(R.string.share_bike_try_to_load), false);
        }
        BikeOrderDetailRequest bikeOrderDetailRequest = new BikeOrderDetailRequest();
        bikeOrderDetailRequest.b = this.e;
        bikeOrderDetailRequest.c = this.f;
        egu.a(bikeOrderDetailRequest, (com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a) new com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a() {
            public final void a(BaseNetResult baseNetResult) {
                if (ehf.this.mPage != null) {
                    ((ShareRidingFinishPage) ehf.this.mPage).a();
                }
                if (ehf.this.mPage != null && ((ShareRidingFinishPage) ehf.this.mPage).isAlive()) {
                    ehf.this.d = (OrderDetail) baseNetResult;
                    ((ShareRidingFinishPage) ehf.this.mPage).a(ehf.this.e, ehf.this.d);
                    if (ehf.this.d != null && !TextUtils.isEmpty(ehf.this.d.isPay)) {
                        if (Integer.parseInt(ehf.this.d.isPay) == 0) {
                            ToastHelper.showToast(((ShareRidingFinishPage) ehf.this.mPage).getString(R.string.share_bike_result_successful_payment));
                            return;
                        }
                        ToastHelper.showToast(((ShareRidingFinishPage) ehf.this.mPage).getString(R.string.share_bike_orderdetail_failed_payment));
                    }
                }
            }
        });
    }

    public final void onStop() {
        super.onStop();
        bty mapView = ((ShareRidingFinishPage) this.mPage).getMapView();
        if (mapView != null) {
            ebf.a(mapView, mapView.j(false), mapView.l(false), this.q);
            mapView.z();
        }
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(true, this.k, false, ((ShareRidingFinishPage) this.mPage).getMapManager(), ((ShareRidingFinishPage) this.mPage).getContext());
        }
    }

    public final void onPause() {
        super.onPause();
        this.n = true;
        this.t = false;
    }

    public final void onDestroy() {
        super.onDestroy();
        PlaySoundUtils.getInstance().clear();
        ((ShareRidingFinishPage) this.mPage).a();
        ((ShareRidingFinishPage) this.mPage).getMapView().f(0);
        this.a.a();
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (this.mPage != null && ((ShareRidingFinishPage) this.mPage).isAlive()) {
            ShareRidingFinishPage shareRidingFinishPage = (ShareRidingFinishPage) this.mPage;
            boolean z = false;
            if (!(shareRidingFinishPage.f == null || shareRidingFinishPage.f.getVisibility() == 8)) {
                z = true;
            }
            if (z) {
                ShareRidingFinishPage shareRidingFinishPage2 = (ShareRidingFinishPage) this.mPage;
                if (shareRidingFinishPage2.f != null) {
                    shareRidingFinishPage2.f.setVisibility(8);
                }
                return ON_BACK_TYPE.TYPE_IGNORE;
            }
            ((ShareRidingFinishPage) this.mPage).b();
        }
        return super.onBackPressed();
    }

    /* access modifiers changed from: private */
    public void f() {
        int height = ((ShareRidingFinishPage) this.mPage).a.getHeight();
        eao.b("sharebike icon : ", "bottomH = ".concat(String.valueOf(height)));
        if (this.x > 0 && height > 0 && !this.w) {
            LogManager.actionLogV2("P00301", "B003");
            new eia(((ShareRidingFinishPage) this.mPage).getContentView()).a(this.x, height);
            this.w = true;
        }
    }

    public final void onMapRenderCompleted() {
        super.onMapRenderCompleted();
        if (this.mPage != null && ((ShareRidingFinishPage) this.mPage).isAlive()) {
            this.i = true;
            ((ShareRidingFinishPage) this.mPage).getMapView().f(0);
            if (this.j && this.b != null && this.b.i != null && this.h && this.i) {
                ((ShareRidingFinishPage) this.mPage).getMapView().f(0);
                if (!g()) {
                    if (this.b == null) {
                        this.j = false;
                        return;
                    }
                    ((ShareRidingFinishPage) this.mPage).getMapView().a(((ShareRidingFinishPage) this.mPage).getMapView().al(), ((ShareRidingFinishPage) this.mPage).getMapView().am(), (defpackage.bty.a) new defpackage.bty.a() {
                        public final void onCallBack(Bitmap bitmap) {
                            if (((ShareRidingFinishPage) ehf.this.mPage).isStarted() && bitmap != null && ehf.this.j) {
                                eet.a(efw.a(bitmap), ehf.this.b.i);
                            }
                            ((ShareRidingFinishPage) ehf.this.mPage).getMapView().f(true);
                            ehf.this.j = false;
                        }
                    });
                }
            }
        }
    }

    private boolean g() {
        if (!new File(eet.a(), this.b.i).exists()) {
            return false;
        }
        ((ShareRidingFinishPage) this.mPage).getMapView().f(true);
        this.j = false;
        return true;
    }

    public final void a() {
        if (this.b != null) {
            this.a.b(this.b.k);
        }
    }

    public final Bitmap b() {
        if (this.m != null) {
            return BitmapFactory.decodeFile(efj.a("runTraceThumbnail.png"));
        }
        return null;
    }

    public final String c() {
        if (this.m != null) {
            return efj.a("runTrace.png");
        }
        return null;
    }

    static /* synthetic */ void j(ehf ehf) {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService != null) {
            if (!ehf.v || !iAccountService.a()) {
                StringBuilder sb = new StringBuilder("mIsRequestCoin = ");
                sb.append(String.valueOf(ehf.v));
                eao.b("sharebike coin : ", sb.toString());
            } else if (ehf.b == null) {
                eao.b("sharebike coin : ", "mTraceHistory is empty");
            } else if (TextUtils.isEmpty(ehf.f) || TextUtils.isEmpty(ehf.e)) {
                eao.b("sharebike coin : ", "mOrderId or cpsource is empty");
            } else {
                int i2 = (int) (ehf.b.g / 1000);
                int i3 = (int) (ehf.b.h / 1000);
                int i4 = ehf.b.c;
                StringBuilder sb2 = new StringBuilder("startTime = ");
                sb2.append(i2);
                sb2.append("--endTime = ");
                sb2.append(i3);
                sb2.append("--distance = ");
                sb2.append(i4);
                sb2.append("--order = ");
                sb2.append(ehf.f);
                sb2.append("--cpsource=");
                sb2.append(ehf.e);
                eao.b("sharebike coin : ", sb2.toString());
                OperationCollectionRequestCallback operationCollectionRequestCallback = new OperationCollectionRequestCallback();
                OperationType operationType = OperationType.TYPE_SHAREBIKE_END;
                ReportRequest buildParam = OperationCollectionParam.buildParam(UpLoadOperationDataUtil.a(operationType), i2, i3, i4, ehf.f, ehf.e);
                ArchivementRequestHolder.getInstance().sendReport(buildParam, operationCollectionRequestCallback);
                StringBuilder sb3 = new StringBuilder("## upload oprationcollection :");
                sb3.append(buildParam.b);
                sb3.append(",");
                sb3.append(buildParam.f);
                sb3.append(",");
                sb3.append(buildParam.c);
                sb3.append(",");
                sb3.append(buildParam.d);
                sb3.append(",");
                sb3.append(buildParam.e);
                sb3.append(",");
                sb3.append(buildParam.g);
                sb3.append(",");
                sb3.append(buildParam.i);
                eao.a(sb3.toString());
                operationCollectionRequestCallback.a = new com.autonavi.minimap.route.logs.operation.OperationCollectionRequestCallback.a() {
                    public final void a(int i) {
                        eao.b("sharebike coin : ", "coin count = ".concat(String.valueOf(i)));
                        ehf.this.x = i;
                        if (i > 0) {
                            ebr.a(true).post(new Runnable() {
                                public final void run() {
                                    ehf.this.f();
                                }
                            });
                        }
                    }
                };
            }
        }
    }

    static /* synthetic */ void a(ehf ehf, OrderDetail orderDetail) {
        int i2;
        bte bte;
        if (orderDetail != null && orderDetail.result) {
            try {
                i2 = Integer.parseInt(orderDetail.ridingTime);
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
                i2 = 0;
            }
            if (!(ehf.b == null || i2 == 0)) {
                int i3 = i2 * 60;
                if (i3 != ehf.b.b) {
                    eab b2 = eaa.a().b(ehf.f);
                    if (b2 != null) {
                        AMapPageUtil.getAppContext();
                        bte = bsp.a().a(b2.c);
                    } else {
                        RideTraceHistory rideTraceHistory = ehf.b;
                        if (rideTraceHistory != null) {
                            bte bte2 = new bte();
                            bte2.a = rideTraceHistory.a;
                            bte2.e = Double.valueOf(rideTraceHistory.e);
                            bte2.d = Integer.valueOf(rideTraceHistory.d);
                            bte2.f = Long.valueOf(rideTraceHistory.g);
                            bte2.g = Long.valueOf(rideTraceHistory.h);
                            bte2.c = Integer.valueOf(rideTraceHistory.c);
                            bte2.i = RideTraceHistory.a(rideTraceHistory.j);
                            bte2.b = Integer.valueOf(rideTraceHistory.b);
                            bte2.h = rideTraceHistory.i;
                            bte2.j = rideTraceHistory.k.getValue();
                            bte2.l = Double.valueOf(rideTraceHistory.f);
                            bte = bte2;
                        } else {
                            bte = null;
                        }
                    }
                    if (bte != null) {
                        bte.b = Integer.valueOf(i3);
                        AMapPageUtil.getAppContext();
                        bsp.a().a(bte);
                    }
                }
            }
        }
    }

    static /* synthetic */ void a(ehf ehf, Bitmap bitmap) {
        boolean z = true;
        if (p) {
            String name = ShareRidingMapPage.class.getName();
            StringBuilder sb = new StringBuilder("createShareBitmap bmp == null");
            sb.append(bitmap == null);
            sb.append("mPage != null ");
            sb.append(ehf.mPage == null);
            eao.e(name, sb.toString());
        }
        if (bitmap == null) {
            if (ehf.mPage != null) {
                ((ShareRidingFinishPage) ehf.mPage).a();
                ((ShareRidingFinishPage) ehf.mPage).c = false;
                ToastHelper.showToast(((ShareRidingFinishPage) ehf.mPage).getString(R.string.share_bike_screenshot_failed));
            }
            return;
        }
        ShareRidingFinshShareView shareRidingFinshShareView = new ShareRidingFinshShareView(((ShareRidingFinishPage) ehf.mPage).getContext());
        shareRidingFinshShareView.setMapBg(bitmap);
        if (ehf.o != null) {
            String str = ehf.o.startTime;
            if (!TextUtils.isEmpty(str)) {
                shareRidingFinshShareView.updateTimeStr(str);
            }
        }
        shareRidingFinshShareView.bindData(ehf.e, ehf.b, ehf.d);
        String a2 = eht.a(ehf.e);
        if (p) {
            String name2 = ShareRidingMapPage.class.getName();
            StringBuilder sb2 = new StringBuilder("FileUtil.isFileExists(cpIcon) && !FileUtil.isFileLocked(cpIcon) ");
            sb2.append(ahd.b(a2) && !ahd.a(a2));
            eao.e(name2, sb2.toString());
            eao.e(ShareRidingMapPage.class.getName(), "ShareBikeUtils.getIconConfPicPath(mCpSource) ".concat(String.valueOf(a2)));
        }
        String str2 = ehf.e;
        if (p) {
            eao.e(ShareRidingMapPage.class.getName(), "dealCpIcon bitmap == null ".concat(String.valueOf(a2)));
        }
        shareRidingFinshShareView.setCpLogoUrl(a2, str2);
        new efj();
        final Bitmap a3 = ehf.mPage == null ? null : efj.a(((ShareRidingFinishPage) ehf.mPage).getContext(), bitmap, shareRidingFinshShareView);
        if (p) {
            String name3 = ShareRidingMapPage.class.getName();
            StringBuilder sb3 = new StringBuilder("dealCpIcon shareBitmap == null ");
            if (a3 != null) {
                z = false;
            }
            sb3.append(z);
            eao.e(name3, sb3.toString());
        }
        if (a3 == null) {
            ToastHelper.showToast(eay.a(R.string.share_bike_screenshot_failed));
            if (ehf.mPage != null) {
                ((ShareRidingFinishPage) ehf.mPage).a();
            }
            ((ShareRidingFinishPage) ehf.mPage).c = false;
            return;
        }
        ehf.m = new efj();
        ehf.l = new a((ShareRidingFinishPage) ehf.mPage);
        ahm.a(new Runnable() {
            public final void run() {
                ehf.this.m;
                Bitmap a2 = ahc.a(a3, a3.getWidth() >> 3, a3.getHeight() >> 3);
                ehf.this.m;
                efj.a(a2, efj.a("runTraceThumbnail.png"));
                ehf.this.m;
                Bitmap bitmap = a3;
                ehf.this.m;
                efj.a(bitmap, efj.a("runTrace.png"));
                ehf.this.l.sendEmptyMessage(0);
                if (ehf.p) {
                    eao.e(ShareRidingMapPage.class.getName(), "mShareHandler.sendEmptyMessage(0) ");
                }
            }
        });
    }

    static /* synthetic */ defpackage.egl.a M(ehf ehf) {
        return new defpackage.egl.a() {
            public final void a(ehq ehq) {
                if (ehf.this.mPage != null && ((ShareRidingFinishPage) ehf.this.mPage).isAlive()) {
                    if (AlibcAlipay.PAY_SUCCESS_CODE.equals(ehq.d)) {
                        ehf.this.e();
                        return;
                    }
                    ToastHelper.showToast(((ShareRidingFinishPage) ehf.this.mPage).getString(R.string.share_bike_alipay_failed));
                }
            }
        };
    }
}
