package com.autonavi.minimap.basemap.save.page;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
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
import java.util.ArrayList;
import org.json.JSONObject;

public class SaveSearchErrorCityPage extends AbstractBasePage<crl> {
    ArrayList<CitySuggestion> a;
    /* access modifiers changed from: 0000 */
    public SaveSearchCitySuggestionAdapter b;
    /* access modifiers changed from: 0000 */
    public ListView c;
    /* access modifiers changed from: 0000 */
    public PullToRefreshListView d;
    int e;
    int f;
    Handler g = new Handler();
    OnItemClickListener h = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            int headerViewsCount = i - SaveSearchErrorCityPage.this.c.getHeaderViewsCount();
            CitySuggestion citySuggestion = (CitySuggestion) SaveSearchErrorCityPage.this.b.getItem(headerViewsCount);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("ItemId", headerViewsCount);
                if (citySuggestion != null) {
                    jSONObject.put("ItemName", citySuggestion.name);
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
            SaveSearchErrorCityPage.this.finish();
            if (SaveSearchErrorCityPage.this.q != null && citySuggestion != null) {
                SaveSearchErrorCityPage.this.q.a(citySuggestion.citycode);
            }
        }
    };
    private View i;
    private LinearLayout j;
    private TextView k;
    private View l;
    private ArrayList<String> m;
    private LinearLayout n;
    private TextView o;
    private aud p;
    /* access modifiers changed from: private */
    public a q;
    private LoadingLayout r;

    public interface a {
        void a(String str);

        void b(String str);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.full_screen_list_dialog_layout);
        View contentView = getContentView();
        PageBundle arguments = getArguments();
        aud aud = (aud) arguments.get(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ);
        Object obj = arguments.get("bundle_key_callback");
        if (obj != null && (obj instanceof a)) {
            this.q = (a) obj;
        }
        this.m = aud.b.b;
        this.a = aud.b.c;
        this.p = aud;
        this.e = 1;
        this.b = new SaveSearchCitySuggestionAdapter(getContext(), crs.a(this.e, this.a, 10));
        this.f = crs.a(this.a, 10);
        this.i = contentView.findViewById(R.id.title_layout);
        this.i.findViewById(R.id.title_btn_right).setVisibility(4);
        this.k = (TextView) contentView.findViewById(R.id.title_text_name);
        this.i.findViewById(R.id.title_btn_left).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                SaveSearchErrorCityPage.this.finish();
            }
        });
        this.l = getActivity().getLayoutInflater().inflate(R.layout.save_search_listview_error_and_city_suggest_header_view, null);
        this.n = (LinearLayout) this.l.findViewById(R.id.error_info_container);
        this.j = (LinearLayout) this.l.findViewById(R.id.city_layout);
        this.c = (ListView) contentView.findViewById(R.id.list);
        if (this.b.getCount() > 0) {
            this.j.setVisibility(0);
        } else {
            this.j.setVisibility(8);
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
                SaveSearchErrorCityPage.this.g.postDelayed(new Runnable() {
                    public final void run() {
                        SaveSearchErrorCityPage saveSearchErrorCityPage = SaveSearchErrorCityPage.this;
                        if (saveSearchErrorCityPage.e <= 1) {
                            saveSearchErrorCityPage.e = 1;
                            saveSearchErrorCityPage.d.onRefreshComplete();
                            return;
                        }
                        saveSearchErrorCityPage.e--;
                        saveSearchErrorCityPage.b.setData(crs.a(saveSearchErrorCityPage.e, saveSearchErrorCityPage.a, 10));
                        saveSearchErrorCityPage.a();
                        saveSearchErrorCityPage.b();
                    }
                }, 10);
            }

            public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                SaveSearchErrorCityPage.this.g.postDelayed(new Runnable() {
                    public final void run() {
                        SaveSearchErrorCityPage saveSearchErrorCityPage = SaveSearchErrorCityPage.this;
                        if (saveSearchErrorCityPage.e < saveSearchErrorCityPage.f) {
                            saveSearchErrorCityPage.e++;
                        } else {
                            saveSearchErrorCityPage.e = saveSearchErrorCityPage.f;
                        }
                        saveSearchErrorCityPage.b.setData(crs.a(saveSearchErrorCityPage.e, saveSearchErrorCityPage.a, 10));
                        saveSearchErrorCityPage.c.setAdapter(saveSearchErrorCityPage.b);
                        saveSearchErrorCityPage.a();
                        saveSearchErrorCityPage.b();
                        SaveSearchErrorCityPage.this.d.mFooterLoadingView.setVisibility(8);
                    }
                }, 10);
            }
        });
        a();
        NoDBClickUtil.a((AdapterView<?>) this.c, this.h);
        this.c.addHeaderView(this.l, null, false);
        this.c.setDividerHeight(0);
        this.r = this.d.changeFooter();
        this.r.setVisibility(0);
        this.c.addFooterView(this.r, null, false);
        this.c.setAdapter(this.b);
        b();
        if (getArguments() != null) {
            String string = getArguments().getString("bundle_key_keyword");
            if (!TextUtils.isEmpty(string)) {
                this.k.setText(string);
            }
        }
        for (int i2 = 0; i2 < this.m.size(); i2++) {
            View inflate = View.inflate(getContext(), R.layout.save_search_keyword_error_item_xml, null);
            this.o = (TextView) inflate.findViewById(R.id.error_info);
            this.o.setText(this.m.get(i2));
            inflate.setTag(this.m.get(i2));
            final String str = this.m.get(i2);
            inflate.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    SaveSearchErrorCityPage.this.finish();
                    if (SaveSearchErrorCityPage.this.q != null) {
                        SaveSearchErrorCityPage.this.q.b(str);
                    }
                }
            });
            this.n.addView(inflate);
            if (i2 != 0) {
                inflate.findViewById(R.id.sep).setVisibility(0);
            }
        }
        this.b = new SaveSearchCitySuggestionAdapter(getContext(), this.a);
        this.c.setAdapter(this.b);
        this.c.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                CitySuggestion citySuggestion = (CitySuggestion) SaveSearchErrorCityPage.this.b.getItem(i - SaveSearchErrorCityPage.this.c.getHeaderViewsCount());
                SaveSearchErrorCityPage.this.finish();
                if (SaveSearchErrorCityPage.this.q != null && citySuggestion != null) {
                    SaveSearchErrorCityPage.this.q.a(citySuggestion.citycode);
                }
            }
        });
    }

    public final void a() {
        if (this.r != null) {
            this.r.setVisibility(0);
        }
        this.d.onRefreshComplete();
        this.d.setMode(Mode.BOTH);
        this.d.mHeaderLoadingView.setRefreshingLabel(getString(R.string.isloading));
        if (this.e == 1) {
            this.d.hideImageHead();
            this.d.setNeedRefreshing(false);
            this.d.setHeaderText(getString(R.string.first_page_no_last_apage), getString(R.string.first_page_no_last_apage), getString(R.string.isloading));
            this.d.setFooterText(getString(R.string.first_page_pull_to_second_page), getString(R.string.release_to_second_page), getString(R.string.isloading));
            return;
        }
        this.d.showImageHead();
        this.d.setNeedRefreshing(true);
        this.d.setHeaderText(String.format(getString(R.string.pull_down_to_loading_next_page), new Object[]{Integer.valueOf(this.e - 1)}), String.format(getString(R.string.release_to_next_page), new Object[]{Integer.valueOf(this.e - 1)}), getString(R.string.loading));
        this.d.setFooterText(String.format(getString(R.string.pull_up_to_next_page), new Object[]{Integer.valueOf(this.e + 1)}), String.format(getString(R.string.release_to_next_page), new Object[]{Integer.valueOf(this.e + 1)}), getString(R.string.isloading));
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
        return new crl(this);
    }
}
