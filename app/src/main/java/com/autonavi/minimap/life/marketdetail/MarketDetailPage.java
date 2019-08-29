package com.autonavi.minimap.life.marketdetail;

import android.graphics.Color;
import android.os.Handler;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.template.AbstractViewHolderAdapter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.common.page.BaseListDataNodePage;
import com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView;
import com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.a;
import com.autonavi.minimap.life.marketdetail.view.TitleViewWithBackText;
import com.autonavi.minimap.shoping.param.PoiListRequest;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import java.util.ArrayList;
import java.util.List;

public class MarketDetailPage extends BaseListDataNodePage<dpf, dph, dpg> implements OnClickListener, a {
    private TitleViewWithBackText a;
    private LinearLayout b;
    private View c;
    private View d;
    private TextView e;
    private TextView f;
    private TextView g;
    private LinearLayout h;
    private LinearLayout i;
    private LinearLayout j;
    private MarketDetailFilterListView k;
    /* access modifiers changed from: private */
    public List<dph> l;
    private String m;
    private String n;
    private boolean o;
    private int p;
    private int q;
    private Handler r = new Handler();

    public final /* synthetic */ void a(Object obj) {
        dph dph = (dph) obj;
        if (dph != null) {
            POI createPOI = POIFactory.createPOI(dph.b, null);
            createPOI.setId(dph.a);
            String str = dph.c;
            String str2 = dph.d;
            try {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    createPOI.setPoint(new GeoPoint(Double.parseDouble(str), Double.parseDouble(str2)));
                }
                createPOI.getPoiExtra().put("showIndoorMap", Boolean.valueOf(this.o));
                String str3 = dph.g;
                PageBundle pageBundle = new PageBundle();
                pageBundle.putSerializable("POI", createPOI);
                pageBundle.putInt("from_id", 5);
                pageBundle.putString("key_floor", str3);
                startPage((String) "amap.search.action.poidetail", pageBundle);
            } catch (NumberFormatException unused) {
            }
        }
    }

    public final /* synthetic */ void a(Object obj, AbstractViewHolderAdapter.a aVar, int i2) {
        String str;
        dph dph = (dph) obj;
        dpg dpg = (dpg) aVar;
        if (dph != null) {
            if (!TextUtils.isEmpty(dph.i)) {
                ko.a(dpg.a, dph.i, R.drawable.groupbuy_icon_null);
            } else {
                dpg.a.setImageResource(R.drawable.groupbuy_icon_null);
            }
            String str2 = dph.b;
            TextView textView = dpg.b;
            if (TextUtils.isEmpty(str2)) {
                str = "";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(i2 + 1);
                sb.append(".");
                sb.append(str2);
                str = sb.toString();
            }
            textView.setText(str);
            if (dph.h == 0) {
                dpg.c.setVisibility(8);
            } else {
                dpg.c.setVisibility(0);
            }
            String str3 = dph.f;
            if (TextUtils.isEmpty(str3) || str3.equals("null") || str3.equals("0") || str3.equals("0.0")) {
                dpg.d.setVisibility(8);
            } else {
                dpg.d.setVisibility(0);
                try {
                    dpg.d.setRating(Float.parseFloat(str3));
                } catch (NumberFormatException unused) {
                    dpg.d.setVisibility(8);
                }
            }
            String str4 = dph.e;
            if (TextUtils.isEmpty(str4) || str4.equals("0") || str4.equals("0.0") || str4.equals("null")) {
                dpg.e.setText("");
            } else if (dph.j.equals(getResources().getString(R.string.market_detail_food))) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(getResources().getString(R.string.market_detail_price));
                sb2.append(str4);
                String sb3 = sb2.toString();
                SpannableString spannableString = new SpannableString(sb3);
                spannableString.setSpan(new ForegroundColorSpan(-9079435), 0, 2, 33);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 3, sb3.length(), 33);
                dpg.e.setText(spannableString);
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(getResources().getString(R.string.market_detail_symbol_rmb));
                sb4.append(str4);
                sb4.append(getResources().getString(R.string.market_detail_qi));
                String sb5 = sb4.toString();
                SpannableString spannableString2 = new SpannableString(sb5);
                spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 0, 0, 33);
                spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 1, sb5.length() - 1, 33);
                spannableString2.setSpan(new ForegroundColorSpan(-9079435), sb5.length() - 1, sb5.length(), 33);
                dpg.e.setText(spannableString2);
            }
            String str5 = dph.g;
            if (TextUtils.isEmpty(str5)) {
                dpg.f.setVisibility(8);
            } else {
                dpg.f.setVisibility(0);
                dpg.f.setText(str5);
            }
            String str6 = dph.k;
            if (TextUtils.isEmpty(str6)) {
                str6 = dph.j;
            }
            TextView textView2 = dpg.g;
            if (TextUtils.isEmpty(str6)) {
                str6 = "";
            }
            textView2.setText(str6);
        }
    }

    public final int g() {
        return R.layout.indoor_market_detail_layout;
    }

    public final int h() {
        return R.id.market_detail_list;
    }

    public final int d() {
        return R.layout.indoor_market_detail_list_item;
    }

    public final void a(View view) {
        this.a = (TitleViewWithBackText) view.findViewById(R.id.market_detail_title);
        this.a.setOnBackImageButtonClickListener(new OnClickListener() {
            public final void onClick(View view) {
                MarketDetailPage.this.finish();
            }
        });
        this.e = (TextView) view.findViewById(R.id.filter_floor);
        this.h = (LinearLayout) view.findViewById(R.id.linearLayoutFloor);
        this.f = (TextView) view.findViewById(R.id.filter_category);
        this.i = (LinearLayout) view.findViewById(R.id.linearLayoutCategory);
        this.g = (TextView) view.findViewById(R.id.filter_category_preferential);
        this.j = (LinearLayout) view.findViewById(R.id.linearLayoutCategoryPreferential);
        this.h.setOnClickListener(this);
        this.i.setOnClickListener(this);
        this.j.setOnClickListener(this);
        this.k = (MarketDetailFilterListView) view.findViewById(R.id.filterListView);
        this.k.setMarketDetailFilterListViewListener(this);
        this.k.setVisibility(8);
        this.c = view.findViewById(R.id.floor_view_line);
        this.d = view.findViewById(R.id.category_view_line);
        this.b = (LinearLayout) view.findViewById(R.id.market_detail_filter);
        this.b.setVisibility(8);
    }

    public final void c() {
        this.l = new ArrayList();
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.n = (String) arguments.getObject("bundleMarketName");
            this.o = arguments.getBoolean("showIndoorMap");
            this.m = (String) arguments.getObject("bundleParentPoiid");
            dpi dpi = (dpi) arguments.getObject("bundleResponseModel");
            this.a.setTitleText(this.n == null ? "" : this.n);
            a(dpi);
        }
    }

    public final void a(PullToRefreshBase<ListView> pullToRefreshBase) {
        super.a(pullToRefreshBase);
        if (this.q < this.p) {
            PoiListRequest poiListRequest = new PoiListRequest(this.m, this.k.getSelectedFilterFloor(), this.k.getSelectedFilterCategory(), this.k.getSelectedFilterPrefertial(), this.q + 1);
            new StringBuilder("mMarketDetailFilterListView.getSelectedFilterPrefertial() == ").append(this.k.getSelectedFilterPrefertial());
            a(poiListRequest);
            return;
        }
        e();
        ToastHelper.showToast(getResources().getString(R.string.market_detail_no_next_page));
    }

    private void a(PoiListRequest poiListRequest) {
        dpe.a(poiListRequest, new Callback<dpi>() {
            public void callback(dpi dpi) {
                MarketDetailPage.this.e();
                MarketDetailPage.this.a(dpi);
            }

            public void error(Throwable th, boolean z) {
                ToastHelper.showToast(MarketDetailPage.this.getResources().getString(R.string.market_detail_net_error));
                MarketDetailPage.this.e();
                if (MarketDetailPage.this.l == null || MarketDetailPage.this.l.size() <= 0) {
                    MarketDetailPage.this.a(Mode.DISABLED);
                }
            }
        });
    }

    public void onClick(View view) {
        if (view == this.h) {
            if (this.h.isSelected()) {
                this.h.setSelected(false);
                this.i.setSelected(false);
                this.j.setSelected(false);
                this.k.setVisibility(8);
                return;
            }
            this.h.setSelected(true);
            this.i.setSelected(false);
            this.j.setSelected(false);
            this.k.showListView(0);
        } else if (view != this.i) {
            if (view == this.j) {
                if (this.j.isSelected()) {
                    this.h.setSelected(false);
                    this.i.setSelected(false);
                    this.j.setSelected(false);
                    this.k.setVisibility(8);
                    return;
                }
                this.h.setSelected(false);
                this.i.setSelected(false);
                this.j.setSelected(true);
                this.k.showListView(2);
            }
        } else if (this.i.isSelected()) {
            this.h.setSelected(false);
            this.i.setSelected(false);
            this.j.setSelected(false);
            this.k.setVisibility(8);
        } else {
            this.h.setSelected(false);
            this.i.setSelected(true);
            this.j.setSelected(false);
            this.k.showListView(1);
        }
    }

    public final void a(String str, String str2, String str3, boolean z) {
        this.h.setSelected(false);
        this.i.setSelected(false);
        this.j.setSelected(false);
        if (!z) {
            a(0);
            return;
        }
        PoiListRequest poiListRequest = new PoiListRequest(this.m, str, str2, str3, 1);
        a(poiListRequest);
    }

    public final void j() {
        this.h.setSelected(false);
        this.i.setSelected(false);
        this.j.setSelected(false);
    }

    /* access modifiers changed from: private */
    public void a(dpi dpi) {
        if (dpi != null) {
            List<dph> list = dpi.j;
            if (list != null && list.size() > 0) {
                this.q = dpi.a;
                this.p = dpi.b;
                boolean z = true;
                if (this.q == 1) {
                    this.l.clear();
                    this.l.addAll(dpi.j);
                    a(this.l);
                    a(0);
                } else {
                    final int f2 = f();
                    this.l.addAll(dpi.j);
                    a(this.l);
                    this.r.post(new Runnable() {
                        public final void run() {
                            MarketDetailPage.this.a(f2 + 1);
                        }
                    });
                }
                if (this.q >= this.p) {
                    a(Mode.DISABLED);
                } else {
                    a(Mode.PULL_FROM_END);
                }
                this.k.setData(dpi);
                String selectedFilterFloor = this.k.getSelectedFilterFloor();
                String selectedFilterCategory = this.k.getSelectedFilterCategory();
                String selectedFilterPrefertial = this.k.getSelectedFilterPrefertial();
                TextView textView = this.e;
                if (TextUtils.isEmpty(selectedFilterFloor)) {
                    selectedFilterFloor = getResources().getString(R.string.market_detail_all_floor);
                }
                textView.setText(selectedFilterFloor);
                TextView textView2 = this.f;
                if (TextUtils.isEmpty(selectedFilterCategory)) {
                    selectedFilterCategory = getResources().getString(R.string.market_detail_all_classify);
                }
                textView2.setText(selectedFilterCategory);
                TextView textView3 = this.g;
                if (TextUtils.isEmpty(selectedFilterPrefertial)) {
                    selectedFilterPrefertial = getResources().getString(R.string.market_detail_all_preferencial);
                }
                textView3.setText(selectedFilterPrefertial);
                boolean z2 = dpi.h != null && dpi.h.size() > 0;
                boolean z3 = dpi.g != null && dpi.g.size() > 0;
                if (dpi.i == null || dpi.i.size() <= 0) {
                    z = false;
                }
                if (z2 && z3 && z) {
                    this.b.setVisibility(0);
                    this.e.setVisibility(0);
                    this.f.setVisibility(0);
                    this.g.setVisibility(0);
                }
                if (!z2 && z3 && z) {
                    this.b.setVisibility(0);
                    this.e.setVisibility(8);
                    this.c.setVisibility(8);
                    this.f.setVisibility(0);
                    this.g.setVisibility(0);
                }
                if (z2 && !z3 && z) {
                    this.b.setVisibility(0);
                    this.e.setVisibility(0);
                    this.c.setVisibility(8);
                    this.f.setVisibility(8);
                    this.g.setVisibility(0);
                }
                if (z2 && z3 && !z) {
                    this.b.setVisibility(0);
                    this.e.setVisibility(0);
                    this.d.setVisibility(8);
                    this.f.setVisibility(0);
                    this.g.setVisibility(8);
                }
                if (!z2 && !z3 && !z) {
                    this.b.setVisibility(8);
                }
            }
        }
    }

    public final /* synthetic */ AbstractViewHolderAdapter.a b(View view) {
        return new dpg(view);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dpf(this);
    }
}
