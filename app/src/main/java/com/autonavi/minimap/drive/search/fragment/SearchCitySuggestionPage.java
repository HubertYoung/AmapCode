package com.autonavi.minimap.drive.search.fragment;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.save.page.SaveSearchErrorSuggestionPage;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.search.adapter.SearchSuggestionAdapter;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.pulltorefresh.internal.LoadingLayout;
import java.util.ArrayList;

public class SearchCitySuggestionPage extends AbstractBasePage implements OnClickListener {
    aud a;
    /* access modifiers changed from: 0000 */
    public ListView b;
    /* access modifiers changed from: 0000 */
    public PullToRefreshListView c;
    boolean d;
    /* access modifiers changed from: 0000 */
    public SearchSuggestionAdapter e;
    int f;
    int g;
    OnItemClickListener h = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            CitySuggestion citySuggestion = (CitySuggestion) SearchCitySuggestionPage.this.e.getItem(i - SearchCitySuggestionPage.this.b.getHeaderViewsCount());
            if (citySuggestion != null) {
                if (SearchCitySuggestionPage.this.n != null) {
                    SearchCitySuggestionPage.this.n.a(citySuggestion.adcode);
                }
                PageBundle arguments = SearchCitySuggestionPage.this.getArguments();
                if (arguments != null) {
                    if (arguments.getBoolean("voice_process", false) || arguments.getBoolean("is_from_search_call_back", false)) {
                        SearchCitySuggestionPage.this.finish();
                        return;
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putString("city_name", citySuggestion.name);
                    pageBundle.putString("city_code", citySuggestion.citycode);
                    pageBundle.putString(TrafficUtil.KEYWORD, SearchCitySuggestionPage.this.p);
                    pageBundle.putBoolean("clear_search_edit_focus", true);
                    SearchCitySuggestionPage.this.setResult(ResultType.OK, pageBundle);
                    SearchCitySuggestionPage.this.startPage((String) "amap.search.action.searchfragment", pageBundle);
                }
            }
        }
    };
    private View i;
    private LoadingLayout j;
    private TextView k;
    private View l;
    private View m;
    /* access modifiers changed from: private */
    public a n;
    /* access modifiers changed from: private */
    public Handler o = new Handler();
    /* access modifiers changed from: private */
    public String p;

    public interface a {
        void a(String str);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.full_screen_list_dialog_layout);
        requestScreenOrientation(1);
        View contentView = getContentView();
        this.k = (TextView) contentView.findViewById(R.id.title_text_name);
        this.l = contentView.findViewById(R.id.title_btn_left);
        this.l.setOnClickListener(this);
        this.m = contentView.findViewById(R.id.title_btn_right);
        this.m.setOnClickListener(this);
        this.b = (ListView) contentView.findViewById(R.id.list);
        this.c = (PullToRefreshListView) contentView.findViewById(R.id.vouchers_pull_refresh_list);
        this.c.setVisibility(0);
        this.c.setMode(Mode.BOTH);
        this.c.setFootershowflag(false);
        this.b = (ListView) this.c.getRefreshableView();
        this.b.setVisibility(0);
        this.b.setOverScrollMode(2);
        this.c.setOnRefreshListener((d<T>) new d<ListView>() {
            public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                SearchCitySuggestionPage.this.o.postDelayed(new Runnable() {
                    public final void run() {
                        SearchCitySuggestionPage searchCitySuggestionPage = SearchCitySuggestionPage.this;
                        searchCitySuggestionPage.d = true;
                        if (searchCitySuggestionPage.f <= 1) {
                            searchCitySuggestionPage.f = 1;
                            searchCitySuggestionPage.c.onRefreshComplete();
                            return;
                        }
                        searchCitySuggestionPage.f--;
                        searchCitySuggestionPage.e.setCityData(SearchCitySuggestionPage.a(searchCitySuggestionPage.f, searchCitySuggestionPage.a.b.c));
                        searchCitySuggestionPage.a();
                        searchCitySuggestionPage.b();
                    }
                }, 10);
            }

            public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                SearchCitySuggestionPage.this.o.postDelayed(new Runnable() {
                    public final void run() {
                        SearchCitySuggestionPage searchCitySuggestionPage = SearchCitySuggestionPage.this;
                        searchCitySuggestionPage.d = false;
                        if (searchCitySuggestionPage.f < searchCitySuggestionPage.g) {
                            searchCitySuggestionPage.f++;
                        } else {
                            searchCitySuggestionPage.f = searchCitySuggestionPage.g;
                        }
                        searchCitySuggestionPage.e.setCityData(SearchCitySuggestionPage.a(searchCitySuggestionPage.f, searchCitySuggestionPage.a.b.c));
                        searchCitySuggestionPage.b.setAdapter(searchCitySuggestionPage.e);
                        searchCitySuggestionPage.a();
                        searchCitySuggestionPage.b();
                        SearchCitySuggestionPage.this.c.mFooterLoadingView.setVisibility(8);
                    }
                }, 10);
            }
        });
        NoDBClickUtil.a((AdapterView<?>) this.b, this.h);
        this.i = LayoutInflater.from(getContext()).inflate(R.layout.drive_search_listview_city_suggest_header, null);
        this.b.addHeaderView(this.i, null, false);
        this.j = this.c.changeFooter();
        this.j.setVisibility(0);
        this.b.addFooterView(this.j, null, false);
        PageBundle arguments = getArguments();
        this.a = (aud) arguments.get(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ);
        this.n = (a) arguments.getObject(SaveSearchErrorSuggestionPage.BUNDLE_KEY_LISTENER);
        this.f = 1;
        ArrayList<CitySuggestion> a2 = a(this.f, this.a.b.c);
        this.e = new SearchSuggestionAdapter(getContext(), a2);
        this.e.setCityData(a2);
        this.g = a(this.a.b.c);
        this.p = arguments.getString(TrafficUtil.KEYWORD);
        this.k.setText(this.p);
        if (this.a.b.b != null && this.a.b.b.size() > 0) {
            ((TextView) this.i.findViewById(R.id.error_info)).setText(this.a.b.b.get(0));
        }
        this.b.setAdapter(this.e);
        a();
        b();
    }

    public final void a() {
        if (this.j != null) {
            this.j.setVisibility(0);
        }
        this.c.onRefreshComplete();
        this.c.setMode(Mode.BOTH);
        this.c.mHeaderLoadingView.setRefreshingLabel(getString(R.string.isloading));
        if (this.f == 1) {
            this.c.hideImageHead();
            this.c.setNeedRefreshing(false);
            this.c.setHeaderText(getString(R.string.first_page_no_last_apage), getString(R.string.first_page_no_last_apage), getString(R.string.isloading));
            this.c.setFooterText(getString(R.string.first_page_pull_to_second_page), getString(R.string.drive_release_to_next), getString(R.string.isloading));
        } else {
            this.c.showImageHead();
            this.c.setNeedRefreshing(true);
            this.c.setHeaderText(String.format(getString(R.string.drive_cur_page_pull_down_to_loading_next), new Object[]{Integer.valueOf(this.f)}), getString(R.string.drive_release_to_last), getString(R.string.isloading));
            this.c.setFooterText(String.format(getString(R.string.drive_cur_page_pull_up_to_loading_next), new Object[]{Integer.valueOf(this.f)}), getString(R.string.drive_release_to_next), getString(R.string.isloading));
        }
        if (this.d) {
            this.b.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.autonavi_top_in));
        } else {
            this.b.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.autonavi_bottom_in));
        }
    }

    public void onClick(View view) {
        if (view == this.l) {
            setResult(ResultType.CANCEL, (PageBundle) null);
            finish();
        }
    }

    public final void b() {
        if (this.f >= this.g) {
            this.c.hideImageFoot();
            this.c.setFooterText(String.format(getString(R.string.current_page_no_next_page), new Object[]{Integer.valueOf(this.f)}), String.format(getString(R.string.current_page_no_next_page), new Object[]{Integer.valueOf(this.f)}), getString(R.string.isloading));
            this.c.setMode(Mode.PULL_FROM_START);
            return;
        }
        this.c.showImageFoot();
    }

    public static ArrayList<CitySuggestion> a(int i2, ArrayList<CitySuggestion> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        int size = arrayList.size();
        if (i2 <= 0 || i2 > a(arrayList)) {
            return null;
        }
        int i3 = (i2 - 1) * 10;
        if (i3 >= size) {
            return null;
        }
        int i4 = (i3 + 10) - 1;
        int i5 = size - 1;
        if (i4 > i5) {
            i4 = i5;
        }
        int i6 = (i4 - i3) + 1;
        ArrayList<CitySuggestion> arrayList2 = new ArrayList<>();
        for (int i7 = 0; i7 < i6; i7++) {
            arrayList2.add(arrayList.get(i3 + i7));
        }
        return arrayList2;
    }

    private static int a(ArrayList<CitySuggestion> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            return 0;
        }
        int size = arrayList.size() / 10;
        if (arrayList.size() % 10 != 0) {
            size++;
        }
        return size;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new din(this);
    }
}
