package com.autonavi.minimap.basemap.save.page;

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
import com.autonavi.minimap.basemap.save.widget.SaveSearchCitySuggestionAdapter;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.pulltorefresh.internal.LoadingLayout;

public class SaveSearchCitySuggestionPage extends AbstractBasePage<crk> implements OnClickListener {
    aud a;
    /* access modifiers changed from: 0000 */
    public ListView b;
    /* access modifiers changed from: 0000 */
    public PullToRefreshListView c;
    boolean d;
    /* access modifiers changed from: 0000 */
    public SaveSearchCitySuggestionAdapter e;
    int f;
    int g;
    OnItemClickListener h = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            CitySuggestion citySuggestion = (CitySuggestion) SaveSearchCitySuggestionPage.this.e.getItem(i - SaveSearchCitySuggestionPage.this.b.getHeaderViewsCount());
            if (citySuggestion != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("city_name", citySuggestion.name);
                pageBundle.putString("city_code", citySuggestion.citycode);
                SaveSearchCitySuggestionPage.this.setResult(ResultType.OK, pageBundle);
                if (SaveSearchCitySuggestionPage.this.n != null) {
                    SaveSearchCitySuggestionPage.this.n.a(citySuggestion.citycode);
                }
                SaveSearchCitySuggestionPage.this.setResult(ResultType.OK, (PageBundle) null);
                SaveSearchCitySuggestionPage.this.finish();
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

    public interface a {
        void a(String str);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.full_screen_list_dialog_layout);
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
        this.c.setOnRefreshListener((d<T>) new d<ListView>() {
            public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                SaveSearchCitySuggestionPage.this.o.postDelayed(new Runnable() {
                    public final void run() {
                        SaveSearchCitySuggestionPage saveSearchCitySuggestionPage = SaveSearchCitySuggestionPage.this;
                        saveSearchCitySuggestionPage.d = true;
                        if (saveSearchCitySuggestionPage.f <= 1) {
                            saveSearchCitySuggestionPage.f = 1;
                            saveSearchCitySuggestionPage.c.onRefreshComplete();
                            return;
                        }
                        saveSearchCitySuggestionPage.f--;
                        saveSearchCitySuggestionPage.e.setData(crs.a(saveSearchCitySuggestionPage.f, saveSearchCitySuggestionPage.a.b.c, 9));
                        saveSearchCitySuggestionPage.a();
                        saveSearchCitySuggestionPage.b();
                    }
                }, 10);
            }

            public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                SaveSearchCitySuggestionPage.this.o.postDelayed(new Runnable() {
                    public final void run() {
                        SaveSearchCitySuggestionPage saveSearchCitySuggestionPage = SaveSearchCitySuggestionPage.this;
                        saveSearchCitySuggestionPage.d = false;
                        if (saveSearchCitySuggestionPage.f < saveSearchCitySuggestionPage.g) {
                            saveSearchCitySuggestionPage.f++;
                        } else {
                            saveSearchCitySuggestionPage.f = saveSearchCitySuggestionPage.g;
                        }
                        saveSearchCitySuggestionPage.e.setData(crs.a(saveSearchCitySuggestionPage.f, saveSearchCitySuggestionPage.a.b.c, 9));
                        saveSearchCitySuggestionPage.b.setAdapter(saveSearchCitySuggestionPage.e);
                        saveSearchCitySuggestionPage.a();
                        saveSearchCitySuggestionPage.b();
                        SaveSearchCitySuggestionPage.this.c.mFooterLoadingView.setVisibility(8);
                    }
                }, 10);
            }
        });
        NoDBClickUtil.a((AdapterView<?>) this.b, this.h);
        this.i = LayoutInflater.from(getContext()).inflate(R.layout.save_search_listview_city_suggest_header, null);
        this.b.addHeaderView(this.i, null, false);
        this.j = this.c.changeFooter();
        this.j.setVisibility(0);
        this.b.addFooterView(this.j, null, false);
        PageBundle arguments = getArguments();
        aud aud = (aud) arguments.get(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ);
        Object obj = arguments.get(SaveSearchErrorSuggestionPage.BUNDLE_KEY_LISTENER);
        if (obj != null && (obj instanceof a)) {
            this.n = (a) obj;
        }
        this.a = aud;
        this.f = 1;
        this.e = new SaveSearchCitySuggestionAdapter(getContext(), crs.a(this.f, this.a.b.c, 9));
        this.g = crs.a(this.a.b.c, 9);
        if (this.a.d != null) {
            this.k.setText(this.a.d);
        }
        if (this.a.b.b.size() > 0) {
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
            this.c.setFooterText(getString(R.string.first_page_pull_to_second_page), getString(R.string.release_to_second_page), getString(R.string.isloading));
        } else {
            this.c.showImageHead();
            this.c.setNeedRefreshing(true);
            this.c.setHeaderText(String.format(getString(R.string.pull_down_to_loading_next_page), new Object[]{Integer.valueOf(this.f - 1)}), String.format(getString(R.string.release_to_next_page), new Object[]{Integer.valueOf(this.f - 1)}), getString(R.string.isloading));
            this.c.setFooterText(String.format(getString(R.string.pull_up_to_next_page), new Object[]{Integer.valueOf(this.f + 1)}), String.format(getString(R.string.release_to_next_page), new Object[]{Integer.valueOf(this.f + 1)}), getString(R.string.isloading));
        }
        if (this.d) {
            this.b.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.autonavi_top_in));
        } else {
            this.b.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.autonavi_bottom_in));
        }
    }

    public void onClick(View view) {
        if (view == this.l) {
            c();
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

    public final void c() {
        setResult(ResultType.CANCEL, (PageBundle) null);
        finish();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new crk(this);
    }
}
