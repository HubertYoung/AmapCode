package com.autonavi.minimap.route.bus.realtimebus.page;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.c;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNetworkOnly;
import com.autonavi.map.core.view.MvpImageView;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.MapInteractiveRelativeLayout;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.inter.impl.BusLineSearch;
import com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager.DataManagerRequest;
import com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager.DataManagerStatus;
import com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager.a;
import com.autonavi.minimap.route.bus.realtimebus.adapter.RTBusAttentionAdapter;
import com.autonavi.minimap.route.bus.realtimebus.callback.RealTimeBusSearchCallback;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;
import com.autonavi.minimap.route.bus.realtimebus.page.RTBusPositionPage;
import com.autonavi.minimap.route.bus.realtimebus.presenter.RTBusPositionPresenter;
import com.autonavi.minimap.route.bus.realtimebus.presenter.RTBusPositionPresenter.RealTimeCollectCallBack;
import com.autonavi.minimap.route.bus.realtimebus.view.RealBusPositionArroundPanelView;
import com.autonavi.minimap.route.bus.realtimebus.view.RealBusPositionArroundPanelView.ListStatus;
import com.autonavi.minimap.route.bus.realtimebus.view.RealBusPositionAttentionView;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.ui.TitleBar.b;
import java.util.ArrayList;
import java.util.List;

public class RTBusPositionPage extends AbstractBaseMapPage<RTBusPositionPresenter> implements OnTouchListener, c, LocationNetworkOnly, launchModeSingleTask, a, RealBusPositionAttentionView.a {
    public TitleBar a;
    public TitleBar b;
    public ArrayList<b> c;
    public RealBusPositionAttentionView d;
    public MapInteractiveRelativeLayout e;
    public dwb f;
    public RealBusPositionArroundPanelView g;
    public dvp h = new dvp() {
        public final void a(int i) {
            if (RTBusPositionPage.this.e != null) {
                RTBusPositionPage.this.e.setPadding(0, 0, 0, i);
            }
        }

        public final void a() {
            if (RTBusPositionPage.this.getSuspendManager() != null) {
                RTBusPositionPage.this.getSuspendManager().d().f();
            }
            if (((RTBusPositionPresenter) RTBusPositionPage.this.mPresenter).a != null) {
                ((RTBusPositionPresenter) RTBusPositionPage.this.mPresenter).a.a(true);
            }
        }

        public final void c(RealTimeBusAndStationMatchup realTimeBusAndStationMatchup) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("RealTimeBusSettingFragmnet.IBusLineResult", realTimeBusAndStationMatchup);
            pageBundle.putInt("bundle_key_pageId", 2);
            RTBusPositionPage.this.startPage(RTBusFollowSettingPage.class, pageBundle);
        }

        public final void a(RealTimeBusAndStationMatchup realTimeBusAndStationMatchup) {
            RTBusPositionPresenter rTBusPositionPresenter = (RTBusPositionPresenter) RTBusPositionPage.this.mPresenter;
            String stationId = realTimeBusAndStationMatchup.stationId();
            String busId = realTimeBusAndStationMatchup.busId();
            ((RTBusPositionPage) rTBusPositionPresenter.mPage).getActivity();
            btd b = bso.a().b(busId, stationId);
            if (b != null) {
                boolean z = !TextUtils.isEmpty(b.is_push);
                rTBusPositionPresenter.c = stationId;
                if (!z) {
                    rTBusPositionPresenter.a(realTimeBusAndStationMatchup);
                    return;
                }
                ((RTBusPositionPage) rTBusPositionPresenter.mPage).a(((RTBusPositionPage) rTBusPositionPresenter.mPage).getString(R.string.busline_loading), dyj.a(stationId, busId, "false", b.alert_time, b.alert_day, new RealTimeCollectCallBack(realTimeBusAndStationMatchup)));
            }
        }

        public final void b(RealTimeBusAndStationMatchup realTimeBusAndStationMatchup) {
            RTBusPositionPresenter rTBusPositionPresenter = (RTBusPositionPresenter) RTBusPositionPage.this.mPresenter;
            if (rTBusPositionPresenter.q.b() >= 50) {
                ToastHelper.showToast(((RTBusPositionPage) rTBusPositionPresenter.mPage).getString(R.string.real_time_bus_attention_upperlimit));
                return;
            }
            rTBusPositionPresenter.f = realTimeBusAndStationMatchup.mBean;
            realTimeBusAndStationMatchup.setFollow(true);
            if (rTBusPositionPresenter.p.getBooleanValue("realbus_position_push_clean", true)) {
                rTBusPositionPresenter.l = true;
                rTBusPositionPresenter.f();
                return;
            }
            rTBusPositionPresenter.g();
        }

        public final void a(String str, String str2) {
            BusLineSearch.a(str, str2, (Callback<IBusLineSearchResult>) new RealTimeBusSearchCallback() {
                public void onResultParseDoneCallback(IBusLineSearchResult iBusLineSearchResult) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("BusLineDetailFragment.IBusLineResult", iBusLineSearchResult);
                    if (RTBusPositionPresenter.this.e()) {
                        pageBundle.putInt("bundle_key_pageId", 3);
                    } else {
                        pageBundle.putInt("bundle_key_pageId", 4);
                    }
                    RTBusPositionPage rTBusPositionPage = (RTBusPositionPage) RTBusPositionPresenter.this.mPage;
                    if (rTBusPositionPage.f != null) {
                        rTBusPositionPage.f.d();
                    }
                    asy asy = (asy) defpackage.esb.a.a.a(asy.class);
                    if (asy != null) {
                        rTBusPositionPage.startPageForResult(asy.b().a(1), pageBundle, 100);
                    }
                }
            }, true);
        }
    };
    private ProgressDlg i;
    private RTBusAttentionAdapter j;
    private ccy k;

    /* access modifiers changed from: private */
    /* renamed from: h */
    public RTBusPositionPresenter createPresenter() {
        return new RTBusPositionPresenter(this);
    }

    public View getMapSuspendView() {
        Context context = getContext();
        this.k = getSuspendWidgetHelper();
        ccv ccv = new ccv(context);
        if (((RTBusPositionPresenter) this.mPresenter).h) {
            MvpImageView mvpImageView = new MvpImageView(getContext());
            mvpImageView.setImageResource(R.drawable.icon_c16_selector);
            mvpImageView.setBackgroundResource(R.drawable.rt_bus_around_refresh_bg_selector);
            mvpImageView.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (RTBusPositionPage.this.g != null) {
                        RTBusPositionPage.this.g.doRefresh();
                    }
                }
            });
            int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
            LayoutParams layoutParams = new LayoutParams(dimensionPixelSize, dimensionPixelSize);
            int a2 = agn.a(getContext(), 4.0f);
            layoutParams.rightMargin = a2;
            layoutParams.bottomMargin = a2;
            ccv.addWidget(mvpImageView, layoutParams, 6);
        }
        ccv.addWidget(this.k.f(), this.k.g(), 7);
        this.k.a(this.k.d());
        this.k.a(ccv.getSuspendView(), this.k.d(), this.k.e(), 3);
        this.k.a((a) new a() {
            public final void a() {
                if (RTBusPositionPage.this.f != null) {
                    RTBusPositionPage.this.f.a(LocationInstrument.getInstance().getLatestPosition());
                }
            }
        });
        return ccv.getSuspendView();
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.route_realtime_real_time_bus_position);
    }

    private void a(boolean z) {
        if (z) {
            ((RTBusPositionPresenter) this.mPresenter).a(true);
            if (!(this.d == null || this.g == null)) {
                this.g.setVisibility(8);
                this.d.setVisibility(0);
                this.e.setVisibility(8);
            }
        } else {
            ((RTBusPositionPresenter) this.mPresenter).a(false);
            if (!(this.d == null || this.g == null)) {
                this.d.setVisibility(8);
                this.g.setVisibility(0);
                this.e.setVisibility(0);
                this.e.setEnabled(true);
            }
        }
        if (this.d != null) {
            this.d.setOnTouchListener(this);
        }
    }

    public final void a(String str, final AosRequest aosRequest) {
        a();
        this.i = new ProgressDlg(getActivity(), str);
        if (aosRequest == null) {
            this.i.setCancelable(false);
        } else {
            this.i.setCancelable(true);
            this.i.setOnCancelListener(new OnCancelListener() {
                public final void onCancel(DialogInterface dialogInterface) {
                    aosRequest.cancel();
                }
            });
        }
        this.i.show();
    }

    public final void a() {
        if (this.i != null) {
            this.i.dismiss();
            this.i = null;
        }
    }

    public String getLoadingMessage() {
        return getString(R.string.isloading);
    }

    public final void b() {
        if (this.f != null) {
            this.f.b();
        }
        bty mapView = getMapManager().getMapView();
        if (mapView != null) {
            ebf.a(mapView, mapView.p(false), mapView.ae(), 2);
            mapView.b(false);
        }
    }

    public final void c() {
        if (((RTBusPositionPresenter) this.mPresenter).d == null || ((RTBusPositionPresenter) this.mPresenter).d.isEmpty()) {
            this.d.toggleAttentionView(false, ((RTBusPositionPresenter) this.mPresenter).h);
        } else {
            this.d.toggleAttentionView(true, ((RTBusPositionPresenter) this.mPresenter).h);
        }
    }

    public final void d() {
        ((RTBusPositionPresenter) this.mPresenter).a();
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.d != null && this.d.getVisibility() == 0;
    }

    public final void e() {
        List<RealTimeBusAndStationMatchup> b2 = ((RTBusPositionPresenter) this.mPresenter).b(true);
        if (b2 == null || b2.isEmpty()) {
            this.d.toggleAttentionView(false, ((RTBusPositionPresenter) this.mPresenter).h);
            return;
        }
        this.d.toggleAttentionView(true, ((RTBusPositionPresenter) this.mPresenter).h);
        if (this.j != null) {
            this.j.setListData(b2);
        }
        this.d.onAttentionRefresh();
    }

    public final void a(List<RealTimeBusAndStationMatchup> list) {
        if (this.j == null) {
            this.j = new RTBusAttentionAdapter(getContext());
            this.j.setListData(list);
            this.j.setRTBusListItemInteraction(this.h);
            this.d.mFollowRealTimeBusListView.setAdapter(this.j);
            this.d.mFollowRealTimeBusListView.setOnRefreshListener((d<T>) new d<ListView>() {
                public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                }

                public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                    ((RTBusPositionPresenter) RTBusPositionPage.this.mPresenter).j = true;
                    RTBusPositionPresenter rTBusPositionPresenter = (RTBusPositionPresenter) RTBusPositionPage.this.mPresenter;
                    rTBusPositionPresenter.c();
                    rTBusPositionPresenter.e.sendMessageDelayed(rTBusPositionPresenter.e.obtainMessage(4), 0);
                    if (!NetworkReachability.b()) {
                        ToastHelper.showLongToast(RTBusPositionPage.this.getString(R.string.toast_msg_conn_error));
                    }
                }
            });
            return;
        }
        this.j.setListData(list);
    }

    public final void f() {
        a(true);
        if (((RTBusPositionPresenter) this.mPresenter).i) {
            ((RTBusPositionPresenter) this.mPresenter).i = false;
            ((RTBusPositionPresenter) this.mPresenter).b();
        } else {
            c();
        }
        ((RTBusPositionPresenter) this.mPresenter).a.d();
    }

    public final void g() {
        a(false);
        this.d.toggleAttentionView(true, ((RTBusPositionPresenter) this.mPresenter).h);
        ((RTBusPositionPresenter) this.mPresenter).d();
        ((RTBusPositionPresenter) this.mPresenter).a.b(false);
    }

    public final void a(DataManagerRequest dataManagerRequest, DataManagerStatus dataManagerStatus) {
        boolean z = true;
        switch (dataManagerStatus) {
            case SUCCESS:
                if (dataManagerRequest == DataManagerRequest.SEARCH || dataManagerRequest == DataManagerRequest.AROUND) {
                    ((RTBusPositionPresenter) this.mPresenter).b = 0;
                    ArrayList<dyi> b2 = ((RTBusPositionPresenter) this.mPresenter).a.b();
                    if (this.g != null) {
                        this.g.setAroundRetryTimes(((RTBusPositionPresenter) this.mPresenter).b);
                        RealBusPositionArroundPanelView realBusPositionArroundPanelView = this.g;
                        if (dataManagerRequest != DataManagerRequest.SEARCH) {
                            z = false;
                        }
                        realBusPositionArroundPanelView.setData(b2, z);
                    }
                    if (!(dataManagerRequest != DataManagerRequest.SEARCH || this.f == null || b2 == null)) {
                        dwb dwb = this.f;
                        dwb.h = false;
                        dwb.g = 0;
                        dwb.b();
                        dwb.f = (ArrayList) b2.clone();
                        dwb.c();
                        dwb.a(dwb.a(dwb.g));
                    }
                    return;
                }
            case DATASET_CHANGED:
                if (dataManagerRequest == DataManagerRequest.AROUND && this.g != null) {
                    this.g.onListItemDataChanged();
                    return;
                }
            case LOADING:
                if (dataManagerRequest == DataManagerRequest.SEARCH) {
                    ((RTBusPositionPresenter) this.mPresenter).b = 0;
                    if (this.g != null) {
                        this.g.setAroundRetryTimes(((RTBusPositionPresenter) this.mPresenter).b);
                        this.g.setListViewStatus(ListStatus.LS_LOADING);
                        return;
                    }
                }
                break;
            case ERROR:
                if (dataManagerRequest == DataManagerRequest.SEARCH) {
                    if (this.g != null) {
                        this.g.setListViewStatus(ListStatus.LS_ERROR);
                    }
                    if (this.f != null) {
                        dwb dwb2 = this.f;
                        if (dwb2.f != null) {
                            dwb2.f.clear();
                        }
                        dwb2.d();
                    }
                    ToastHelper.showToast(getString(R.string.ic_net_error_tipinfo));
                    return;
                } else if (dataManagerRequest == DataManagerRequest.AROUND) {
                    RTBusPositionPresenter rTBusPositionPresenter = (RTBusPositionPresenter) this.mPresenter;
                    rTBusPositionPresenter.b++;
                    if (this.g != null) {
                        this.g.setAroundRetryTimes(((RTBusPositionPresenter) this.mPresenter).b);
                        this.g.onListItemDataChanged();
                        return;
                    }
                }
                break;
            default:
                if (dataManagerRequest == DataManagerRequest.SEARCH || dataManagerRequest == DataManagerRequest.AROUND) {
                    ((RTBusPositionPresenter) this.mPresenter).b = 0;
                    if (this.g != null) {
                        this.g.setAroundRetryTimes(((RTBusPositionPresenter) this.mPresenter).b);
                        this.g.setListViewStatus(ListStatus.LS_EMPTY);
                        break;
                    }
                }
                break;
        }
    }
}
