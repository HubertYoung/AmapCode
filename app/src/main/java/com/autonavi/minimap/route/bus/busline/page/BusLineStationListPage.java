package com.autonavi.minimap.route.bus.busline.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView;
import com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView.a;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleInstance;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.route.bus.busline.adapter.BusLineStationResultAdapter;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.minimap.route.bus.inter.IBusLineResult;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import java.util.ArrayList;

@PageAction("amap.extra.route.busline_station_list")
public class BusLineStationListPage extends AbstractBasePage<BusLineStationListPresenter> implements a, LocationNone, launchModeSingleInstance, d<ListView>, dvl {
    public Animation a;
    public Animation b;
    public SearchKeywordResultTitleView c;
    public PullToRefreshListView d;
    public ListView e;
    public BusLineStationResultAdapter f;
    public View g;

    public final void c() {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.busline_station_list_fragment);
        requestScreenOrientation(1);
        this.a = AnimationUtils.loadAnimation(getContext(), R.anim.autonavi_top_in);
        this.b = AnimationUtils.loadAnimation(getContext(), R.anim.autonavi_bottom_in);
        View contentView = getContentView();
        this.c = (SearchKeywordResultTitleView) contentView.findViewById(R.id.busline_result_list_title_view);
        this.c.setOnSearchKeywordResultTitleViewListener(this);
        if (euk.a()) {
            int a2 = euk.a(getContext());
            View findViewById = findViewById(R.id.immersive_status_bar_placeholder);
            LayoutParams layoutParams = findViewById.getLayoutParams();
            layoutParams.height = a2;
            findViewById.setLayoutParams(layoutParams);
        }
        this.d = (PullToRefreshListView) contentView.findViewById(R.id.busline_search_result_pull_refresh_list);
        this.d.mLvFooterLoadingFrame.removeView(this.d.mFooterLoadingView);
        this.e = this.d.getListView();
        this.d.setFootershowflag(false);
        this.d.setMode(Mode.BOTH);
        this.d.setOnRefreshListener((d<T>) this);
        this.e.setChoiceMode(1);
        this.e.setDescendantFocusability(393216);
        this.f = new BusLineStationResultAdapter(getContext());
        this.f.setListItemInteraction(this);
        this.e.setAdapter(this.f);
    }

    public final void a() {
        finish();
    }

    public final void b() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString(TrafficUtil.KEYWORD, this.c.getKeyword());
        startPage(BusLineSearchPage.class, pageBundle);
    }

    public final void d() {
        ((BusLineStationListPresenter) this.mPresenter).onSwitchClick();
    }

    public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        ((BusLineStationListPresenter) this.mPresenter).onPullDownToRefresh();
    }

    public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        ((BusLineStationListPresenter) this.mPresenter).onPullUpToRefresh();
    }

    public final void e() {
        if (this.d != null) {
            this.d.onRefreshComplete();
        }
    }

    public final void a(int i, POI poi, IBusLineResult iBusLineResult) {
        if (iBusLineResult != null) {
            iBusLineResult.setFocusedPoiIndex(i);
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("POI", poi.clone());
        pageBundle.putString("fromSource", "buslinemap");
        pageBundle.putObject("buslinedata", iBusLineResult);
        startPage((String) "amap.search.action.poidetail", pageBundle);
    }

    public final void b(int i, POI poi, IBusLineResult iBusLineResult) {
        if (iBusLineResult != null) {
            iBusLineResult.setFocusedPoiIndex(i);
        }
        bax bax = (bax) a.a.a(bax.class);
        if (bax != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundle_key_poi_end", poi);
            pageBundle.putBoolean("bundle_key_auto_route", true);
            bax.a(pageBundle);
        }
    }

    public final void a(POI poi) {
        String phone = poi.getPhone();
        if (!TextUtils.isEmpty(phone)) {
            String type = poi.getType();
            if (type.length() >= 4) {
                type = type.substring(0, 4);
            }
            if ("1001".equals(type) || "1002".equals(type)) {
                ArrayList arrayList = new ArrayList();
                if (phone.indexOf(59) >= 0) {
                    String[] split = phone.split(";");
                    for (int i = 0; i < split.length; i++) {
                        if (split[i].substring(0, 3).equals("400")) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(AMapAppGlobal.getApplication().getString(R.string.busline_phone_booking));
                            sb.append(AMapAppGlobal.getApplication().getString(R.string.busline_left_bracket));
                            sb.append(split[i]);
                            sb.append(AMapAppGlobal.getApplication().getString(R.string.busline_right_bracket));
                            sb.append("$");
                            sb.append(split[i]);
                            arrayList.add(sb.toString());
                        } else {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(AMapAppGlobal.getApplication().getString(R.string.busline_reception_phone));
                            sb2.append(AMapAppGlobal.getApplication().getString(R.string.busline_left_bracket));
                            sb2.append(split[i]);
                            sb2.append(AMapAppGlobal.getApplication().getString(R.string.busline_right_bracket));
                            sb2.append("$");
                            sb2.append(split[i]);
                            arrayList.add(sb2.toString());
                        }
                    }
                } else if (phone.substring(0, 3).equals("400")) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(AMapAppGlobal.getApplication().getString(R.string.busline_phone_booking));
                    sb3.append(AMapAppGlobal.getApplication().getString(R.string.busline_left_bracket));
                    sb3.append(phone);
                    sb3.append(AMapAppGlobal.getApplication().getString(R.string.busline_right_bracket));
                    sb3.append("$");
                    sb3.append(phone);
                    arrayList.add(sb3.toString());
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(AMapAppGlobal.getApplication().getString(R.string.busline_reception_phone));
                    sb4.append(AMapAppGlobal.getApplication().getString(R.string.busline_left_bracket));
                    sb4.append(phone);
                    sb4.append(AMapAppGlobal.getApplication().getString(R.string.busline_right_bracket));
                    sb4.append("$");
                    sb4.append(phone);
                    arrayList.add(sb4.toString());
                }
                if (arrayList.size() > 0) {
                    bnz.a(arrayList, getActivity());
                }
            } else if (phone.indexOf(";") > 0) {
                ArrayList arrayList2 = new ArrayList();
                String[] split2 = phone.split(";");
                for (int i2 = 0; i2 < split2.length; i2++) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(split2[i2]);
                    sb5.append("$");
                    sb5.append(split2[i2]);
                    arrayList2.add(sb5.toString());
                }
                if (arrayList2.size() > 0) {
                    bnz.a(arrayList2, getActivity());
                }
            } else {
                getActivity();
                bnz.a(phone);
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new BusLineStationListPresenter(this);
    }
}
