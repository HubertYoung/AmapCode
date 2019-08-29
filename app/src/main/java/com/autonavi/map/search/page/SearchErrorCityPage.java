package com.autonavi.map.search.page;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.search.adapter.SearchSuggestionAdapter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.pulltorefresh.internal.LoadingLayout;
import java.util.ArrayList;
import org.json.JSONObject;

public class SearchErrorCityPage extends AbstractSearchBasePage<cbb> {
    /* access modifiers changed from: 0000 */
    public InfoliteResult a;
    /* access modifiers changed from: 0000 */
    public SearchSuggestionAdapter b;
    /* access modifiers changed from: 0000 */
    public ListView c;
    /* access modifiers changed from: 0000 */
    public PullToRefreshListView d;
    int e;
    int f;
    Handler g = new Handler();
    private TextView h;
    private ArrayList<String> i;
    private ArrayList<CitySuggestion> j;
    private LinearLayout k;
    /* access modifiers changed from: private */
    public a l;
    private LoadingLayout m;

    public interface a {
        void a(String str);

        void b(String str);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.full_screen_list_dialog_layout);
        requestScreenOrientation(1);
        PageBundle arguments = getArguments();
        InfoliteResult infoliteResult = (InfoliteResult) arguments.get(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ);
        this.i = infoliteResult.searchInfo.f;
        this.j = infoliteResult.searchInfo.g;
        this.a = infoliteResult;
        this.l = (a) arguments.getObject(SearchErrorSuggestionPage.BUNDLE_KEY_ONITEMLISTENER);
        this.e = 1;
        this.b = new SearchSuggestionAdapter(getContext(), cbz.a(this.e, this.a.searchInfo.g));
        this.b.setCityData(cbz.a(this.e, this.a.searchInfo.g));
        this.f = cbz.a(this.a.searchInfo.g);
        View contentView = getContentView();
        View findViewById = contentView.findViewById(R.id.title_layout);
        findViewById.findViewById(R.id.title_btn_right).setVisibility(4);
        this.h = (TextView) contentView.findViewById(R.id.title_text_name);
        findViewById.findViewById(R.id.title_btn_left).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                SearchErrorCityPage.this.finish();
            }
        });
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.search_listview_error_and_city_suggest_header_view, null);
        this.k = (LinearLayout) inflate.findViewById(R.id.error_info_container);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.city_layout);
        this.c = (ListView) contentView.findViewById(R.id.list);
        if (this.b.getCount() > 0) {
            linearLayout.setVisibility(0);
        } else {
            linearLayout.setVisibility(8);
        }
        this.d = (PullToRefreshListView) contentView.findViewById(R.id.vouchers_pull_refresh_list);
        this.d.setVisibility(0);
        this.d.setMode(Mode.BOTH);
        this.d.setFootershowflag(false);
        this.c = (ListView) this.d.getRefreshableView();
        this.c.setDividerHeight(0);
        this.c.setVisibility(0);
        this.d.setOnRefreshListener((d<T>) new d<ListView>() {
            public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                SearchErrorCityPage.this.g.postDelayed(new Runnable() {
                    public final void run() {
                        SearchErrorCityPage searchErrorCityPage = SearchErrorCityPage.this;
                        if (searchErrorCityPage.e <= 1) {
                            searchErrorCityPage.e = 1;
                            searchErrorCityPage.d.onRefreshComplete();
                            return;
                        }
                        searchErrorCityPage.e--;
                        searchErrorCityPage.b.setCityData(cbz.a(searchErrorCityPage.e, searchErrorCityPage.a.searchInfo.g));
                        searchErrorCityPage.a();
                        searchErrorCityPage.b();
                    }
                }, 10);
            }

            public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                SearchErrorCityPage.this.g.postDelayed(new Runnable() {
                    public final void run() {
                        SearchErrorCityPage searchErrorCityPage = SearchErrorCityPage.this;
                        if (searchErrorCityPage.e < searchErrorCityPage.f) {
                            searchErrorCityPage.e++;
                        } else {
                            searchErrorCityPage.e = searchErrorCityPage.f;
                        }
                        searchErrorCityPage.b.setCityData(cbz.a(searchErrorCityPage.e, searchErrorCityPage.a.searchInfo.g));
                        searchErrorCityPage.c.setAdapter(searchErrorCityPage.b);
                        searchErrorCityPage.a();
                        searchErrorCityPage.b();
                        SearchErrorCityPage.this.d.mFooterLoadingView.setVisibility(8);
                    }
                }, 10);
            }
        });
        a();
        this.c.addHeaderView(inflate, null, false);
        this.c.setDividerHeight(0);
        this.m = this.d.changeFooter();
        this.m.setVisibility(0);
        this.c.addFooterView(this.m, null, false);
        this.c.setAdapter(this.b);
        b();
        if (!(this.a == null || this.a.mWrapper == null)) {
            this.h.setText(this.a.mKeyword);
        }
        for (int i2 = 0; i2 < this.i.size(); i2++) {
            View inflate2 = View.inflate(getContext(), R.layout.search_keyword_error_item, null);
            ((TextView) inflate2.findViewById(R.id.error_info)).setText(this.i.get(i2));
            inflate2.setTag(this.i.get(i2));
            final String str = this.i.get(i2);
            inflate2.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (SearchErrorCityPage.this.l != null) {
                        SearchErrorCityPage.this.l.b(str);
                    }
                    PageBundle arguments = SearchErrorCityPage.this.getArguments();
                    int i = 11102;
                    if (arguments != null) {
                        i = arguments.getInt("from_page");
                    }
                    if (i == 12400) {
                        SearchErrorCityPage.this.finish();
                        return;
                    }
                    bcb bcb = (bcb) defpackage.esb.a.a.a(bcb.class);
                    if (bcb != null) {
                        bcb.a().c(str).a().a(SearchErrorCityPage.this);
                    }
                }
            });
            inflate2.findViewById(R.id.top_line).setVisibility(0);
            this.k.addView(inflate2);
            if (i2 != 0) {
                inflate2.findViewById(R.id.sep).setVisibility(0);
            }
        }
        this.b = new SearchSuggestionAdapter(getContext(), this.j);
        this.b.setCityData(this.j);
        this.c.setAdapter(this.b);
        NoDBClickUtil.a((AdapterView<?>) this.c, (OnItemClickListener) new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                int headerViewsCount = i - SearchErrorCityPage.this.c.getHeaderViewsCount();
                CitySuggestion citySuggestion = (CitySuggestion) SearchErrorCityPage.this.b.getItem(headerViewsCount);
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("ItemId", headerViewsCount);
                    if (citySuggestion != null) {
                        jSONObject.put("ItemName", citySuggestion.name);
                    }
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
                PageBundle arguments = SearchErrorCityPage.this.getArguments();
                int i2 = 11102;
                if (arguments != null) {
                    i2 = arguments.getInt("from_page");
                }
                if (!(SearchErrorCityPage.this.l == null || citySuggestion == null)) {
                    SearchErrorCityPage.this.l.a(citySuggestion.citycode);
                }
                if (i2 == 12400) {
                    SearchErrorCityPage.this.finish();
                    return;
                }
                bcb bcb = (bcb) defpackage.esb.a.a.a(bcb.class);
                if (bcb != null) {
                    bcb.a().a(citySuggestion != null ? citySuggestion.name : "").b(citySuggestion != null ? citySuggestion.citycode : "").c(SearchErrorCityPage.this.a.mKeyword).a().a(SearchErrorCityPage.this);
                }
            }
        });
    }

    public final void a() {
        if (this.m != null) {
            this.m.setVisibility(0);
        }
        this.d.onRefreshComplete();
        this.d.setMode(Mode.BOTH);
        this.d.mHeaderLoadingView.setRefreshingLabel(getString(R.string.isloading));
        if (this.e == 1) {
            this.d.hideImageHead();
            this.d.setNeedRefreshing(false);
            this.d.setHeaderText(getString(R.string.first_page_no_last_apage), getString(R.string.first_page_no_last_apage), getString(R.string.isloading));
            this.d.setFooterText(getString(R.string.first_page_pull_to_second_page), getString(R.string.release_to_next), getString(R.string.isloading));
            return;
        }
        this.d.showImageHead();
        this.d.setNeedRefreshing(true);
        this.d.setHeaderText(String.format(getString(R.string.cur_page_pull_down_to_loading_next), new Object[]{Integer.valueOf(this.e)}), getString(R.string.release_to_last), getString(R.string.loading));
        this.d.setFooterText(String.format(getString(R.string.cur_page_pull_up_to_loading_next), new Object[]{Integer.valueOf(this.e)}), getString(R.string.release_to_next), getString(R.string.isloading));
    }

    public final void b() {
        if (this.e >= this.f) {
            this.d.hideImageFoot();
            this.d.setFooterText(String.format(getString(R.string.current_page_no_next_page), new Object[]{Integer.valueOf(this.e)}), String.format(getString(R.string.current_page_no_next_page), new Object[]{Integer.valueOf(this.e)}), getString(R.string.isloading));
            this.d.setMode(Mode.PULL_FROM_START);
            return;
        }
        this.d.showImageFoot();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cbb(this);
    }
}
