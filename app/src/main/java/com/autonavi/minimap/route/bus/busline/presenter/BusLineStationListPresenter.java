package com.autonavi.minimap.route.bus.busline.presenter;

import android.os.Handler;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.b;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.poi.PoiRequestHolder;
import com.autonavi.minimap.poi.param.BusBaseRequest;
import com.autonavi.minimap.poi.param.BusLiteRequest;
import com.autonavi.minimap.poi.param.BusRequest;
import com.autonavi.minimap.poi.param.NewBusRequest;
import com.autonavi.minimap.route.bus.busline.page.BusLineStationListPage;
import com.autonavi.minimap.route.bus.busline.page.BusLineStationMapPage;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class BusLineStationListPresenter extends eaf<BusLineStationListPage> implements Callback<IBusLineSearchResult>, b {
    public static final String BUNDLE_KEY_RESULT_OBJ = "bundle_key_result";
    /* access modifiers changed from: private */
    public CompatDialog dialog;
    /* access modifiers changed from: private */
    public IBusLineSearchResult mBusLineResult;
    private Handler mHandler = new Handler();

    public class BusLineTurnPageCallback extends FalconAosPrepareResponseCallback<duk> {
        final /* synthetic */ BusLineStationListPresenter a;
        private BusBaseRequest b;
        private Callback<IBusLineSearchResult> c;
        private String d = "";
        private String e;
        private boolean f;

        public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
            return b(aosByteResponse);
        }

        public final /* synthetic */ void a(Object obj) {
            duk duk = (duk) obj;
            if (this.a.dialog != null && this.a.dialog.isShowing()) {
                this.a.dialog.dismiss();
            }
            if (duk.a != null) {
                duk.a.setBusRequest(this.b);
                duk.a.setSearchKeyword(this.b.c);
            }
            if (!this.f && this.c != null) {
                this.c.callback(duk.a);
            }
        }

        /* JADX WARNING: type inference failed for: r1v0, types: [java.lang.String, com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter] */
        /* JADX WARNING: type inference failed for: r2v0, types: [java.lang.String, com.autonavi.minimap.poi.param.BusBaseRequest] */
        /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.Object, com.autonavi.common.Callback<com.autonavi.minimap.route.bus.inter.IBusLineSearchResult>, com.autonavi.minimap.poi.param.BusBaseRequest] */
        /* JADX WARNING: type inference failed for: r4v0, types: [boolean, com.autonavi.common.Callback<com.autonavi.minimap.route.bus.inter.IBusLineSearchResult>] */
        /* JADX WARNING: Unknown variable types count: 4 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public BusLineTurnPageCallback(java.lang.String r1, com.autonavi.minimap.poi.param.BusBaseRequest r2, com.autonavi.common.Callback<com.autonavi.minimap.route.bus.inter.IBusLineSearchResult> r3, boolean r4) {
            /*
                r0 = this;
                r0.a = r1
                r0.<init>()
                java.lang.String r1 = ""
                r0.d = r1
                r0.d = r2
                r0.b = r3
                r0.c = r4
                java.lang.String r1 = r3.toString()
                java.lang.String r1 = defpackage.agy.a(r1)
                r0.e = r1
                r1 = 0
                r0.f = r1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter.BusLineTurnPageCallback.<init>(com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter, java.lang.String, com.autonavi.minimap.poi.param.BusBaseRequest, com.autonavi.common.Callback):void");
        }

        private static duk b(AosByteResponse aosByteResponse) {
            duk duk = new duk();
            try {
                duk.parser((byte[]) aosByteResponse.getResult());
            } catch (UnsupportedEncodingException e2) {
                kf.a((Throwable) e2);
            } catch (JSONException e3) {
                kf.a((Throwable) e3);
            }
            return duk;
        }

        public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
            if (this.a.dialog != null && this.a.dialog.isShowing()) {
                this.a.dialog.dismiss();
            }
            if (this.c != null) {
                this.c.error(aosResponseException, false);
            }
        }
    }

    public class a implements Runnable {
        private boolean b;

        /* synthetic */ a(BusLineStationListPresenter busLineStationListPresenter, boolean z, byte b2) {
            this(z);
        }

        private a(boolean z) {
            this.b = false;
            this.b = z;
        }

        public final void run() {
            int i = BusLineStationListPresenter.this.mBusLineResult.getBusReqest().a;
            if (this.b) {
                if (i < BusLineStationListPresenter.this.mBusLineResult.getTotalPoiPage()) {
                    BusLineStationListPresenter.this.reloadPage(this.b);
                } else {
                    ((BusLineStationListPage) BusLineStationListPresenter.this.mPage).e();
                }
            } else if (i > 1) {
                BusLineStationListPresenter.this.reloadPage(false);
            } else {
                ((BusLineStationListPage) BusLineStationListPresenter.this.mPage).e();
            }
        }
    }

    public void onDestroy() {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public BusLineStationListPresenter(BusLineStationListPage busLineStationListPage) {
        super(busLineStationListPage);
    }

    public void onPageCreated() {
        initData();
    }

    private void initData() {
        PageBundle arguments = ((BusLineStationListPage) this.mPage).getArguments();
        if (arguments != null) {
            IBusLineSearchResult iBusLineSearchResult = (IBusLineSearchResult) arguments.get(BUNDLE_KEY_RESULT_OBJ);
            ((BusLineStationListPage) this.mPage).c.setKeyword(iBusLineSearchResult.getSearchKeyword());
            loadData(iBusLineSearchResult);
        }
    }

    private void loadData(IBusLineSearchResult iBusLineSearchResult) {
        if (iBusLineSearchResult != null) {
            boolean z = this.mBusLineResult == null || iBusLineSearchResult.getBusReqest().b;
            this.mBusLineResult = iBusLineSearchResult;
            BusLineStationListPage busLineStationListPage = (BusLineStationListPage) this.mPage;
            busLineStationListPage.f.setData(iBusLineSearchResult);
            int i = iBusLineSearchResult.getBusReqest().a;
            int totalPoiPage = iBusLineSearchResult.getTotalPoiPage();
            busLineStationListPage.d.onRefreshComplete();
            if (busLineStationListPage.g != null) {
                busLineStationListPage.g.setVisibility(0);
            }
            busLineStationListPage.d.setMode(Mode.BOTH);
            busLineStationListPage.d.mHeaderLoadingView.setRefreshingLabel(busLineStationListPage.getString(R.string.busline_loading));
            if (i == 1) {
                busLineStationListPage.d.hideImageHead();
                busLineStationListPage.d.setNeedRefreshing(false);
                PullToRefreshListView pullToRefreshListView = busLineStationListPage.d;
                StringBuilder sb = new StringBuilder();
                sb.append(busLineStationListPage.getString(R.string.busline_now_is));
                sb.append(i);
                sb.append(busLineStationListPage.getString(R.string.busline_no_prev_page));
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(busLineStationListPage.getString(R.string.busline_now_is));
                sb3.append(i);
                sb3.append(busLineStationListPage.getString(R.string.busline_no_prev_page));
                pullToRefreshListView.setHeaderText(sb2, sb3.toString(), busLineStationListPage.getString(R.string.busline_loading));
                PullToRefreshListView pullToRefreshListView2 = busLineStationListPage.d;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(busLineStationListPage.getString(R.string.busline_now_is));
                sb4.append(i);
                sb4.append(busLineStationListPage.getString(R.string.busline_page));
                sb4.append("，");
                sb4.append(busLineStationListPage.getString(R.string.busline_pull_up_to_load_next));
                String sb5 = sb4.toString();
                StringBuilder sb6 = new StringBuilder();
                sb6.append(busLineStationListPage.getString(R.string.busline_release_to_load));
                sb6.append(i + 1);
                sb6.append(busLineStationListPage.getString(R.string.busline_page));
                pullToRefreshListView2.setFooterText(sb5, sb6.toString(), busLineStationListPage.getString(R.string.busline_loading));
            } else {
                busLineStationListPage.d.showImageHead();
                busLineStationListPage.d.setNeedRefreshing(true);
                PullToRefreshListView pullToRefreshListView3 = busLineStationListPage.d;
                StringBuilder sb7 = new StringBuilder();
                sb7.append(busLineStationListPage.getString(R.string.busline_release_to_load));
                int i2 = i - 1;
                sb7.append(i2);
                sb7.append(busLineStationListPage.getString(R.string.busline_page));
                String sb8 = sb7.toString();
                StringBuilder sb9 = new StringBuilder();
                sb9.append(busLineStationListPage.getString(R.string.busline_release_to_load));
                sb9.append(i2);
                sb9.append(busLineStationListPage.getString(R.string.busline_page));
                pullToRefreshListView3.setHeaderText(sb8, sb9.toString(), busLineStationListPage.getString(R.string.busline_loading));
                PullToRefreshListView pullToRefreshListView4 = busLineStationListPage.d;
                StringBuilder sb10 = new StringBuilder();
                sb10.append(busLineStationListPage.getString(R.string.busline_now_is));
                sb10.append(i);
                sb10.append(busLineStationListPage.getString(R.string.busline_page));
                sb10.append("，");
                sb10.append(busLineStationListPage.getString(R.string.busline_pull_up_to_load_next));
                String sb11 = sb10.toString();
                StringBuilder sb12 = new StringBuilder();
                sb12.append(busLineStationListPage.getString(R.string.busline_release_to_load));
                sb12.append(i + 1);
                sb12.append(busLineStationListPage.getString(R.string.busline_page));
                pullToRefreshListView4.setFooterText(sb11, sb12.toString(), busLineStationListPage.getString(R.string.busline_loading));
            }
            if (i < totalPoiPage) {
                busLineStationListPage.d.showImageFoot();
            }
            if (i >= totalPoiPage) {
                PullToRefreshListView pullToRefreshListView5 = busLineStationListPage.d;
                StringBuilder sb13 = new StringBuilder();
                sb13.append(busLineStationListPage.getString(R.string.busline_now_is));
                sb13.append(i);
                sb13.append(busLineStationListPage.getString(R.string.busline_no_more_page));
                String sb14 = sb13.toString();
                StringBuilder sb15 = new StringBuilder();
                sb15.append(busLineStationListPage.getString(R.string.busline_now_is));
                sb15.append(i);
                sb15.append(busLineStationListPage.getString(R.string.busline_no_more_page));
                pullToRefreshListView5.setFooterText(sb14, sb15.toString(), busLineStationListPage.getString(R.string.busline_loading));
                busLineStationListPage.d.setMode(Mode.PULL_FROM_START);
                busLineStationListPage.d.hideImageFoot();
            }
            if (busLineStationListPage.g != null) {
                busLineStationListPage.e.removeFooterView(busLineStationListPage.g);
            }
            if (busLineStationListPage.g == null) {
                busLineStationListPage.g = busLineStationListPage.d.changeFooter();
                busLineStationListPage.g.setVisibility(0);
            }
            busLineStationListPage.e.addFooterView(busLineStationListPage.g, null, false);
            busLineStationListPage.e.startAnimation(z ? busLineStationListPage.b : busLineStationListPage.a);
            busLineStationListPage.e.setSelection(0);
        }
    }

    /* access modifiers changed from: private */
    public void reloadPage(boolean z) {
        BusBaseRequest busReqest = this.mBusLineResult.getBusReqest();
        busReqest.a = z ? busReqest.a + 1 : busReqest.a - 1;
        busReqest.b = z;
        this.dialog = aav.a(busReqest, ((BusLineStationListPage) this.mPage).getContext().getResources().getString(R.string.loadingMessage));
        this.dialog.show();
        BusLineTurnPageCallback busLineTurnPageCallback = new BusLineTurnPageCallback(this, ((BusLineStationListPage) this.mPage).getContext().getResources().getString(R.string.loadingMessage), busReqest, this);
        if (busReqest instanceof BusRequest) {
            PoiRequestHolder.getInstance().sendBus((BusRequest) busReqest, busLineTurnPageCallback);
        } else if (busReqest instanceof BusLiteRequest) {
            PoiRequestHolder.getInstance().sendBusLite((BusLiteRequest) busReqest, busLineTurnPageCallback);
        } else {
            if (busReqest instanceof NewBusRequest) {
                PoiRequestHolder.getInstance().sendNewBus((NewBusRequest) busReqest, busLineTurnPageCallback);
            }
        }
    }

    public void callback(IBusLineSearchResult iBusLineSearchResult) {
        if (iBusLineSearchResult != null) {
            loadData(iBusLineSearchResult);
        }
    }

    public void error(Throwable th, boolean z) {
        ((BusLineStationListPage) this.mPage).e();
    }

    public void onCancelled() {
        ((BusLineStationListPage) this.mPage).e();
    }

    public void onSwitchClick() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("bundle_key_result_obj", this.mBusLineResult);
        ((BusLineStationListPage) this.mPage).startPage(BusLineStationMapPage.class, pageBundle);
    }

    public void onPullDownToRefresh() {
        this.mHandler.postDelayed(new a(this, false, 0), 10);
    }

    public void onPullUpToRefresh() {
        this.mHandler.postDelayed(new a(this, true, 0), 10);
    }
}
