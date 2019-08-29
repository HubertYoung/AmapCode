package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.search.adapter.SearchSuggestionAdapter;
import com.autonavi.minimap.drive.search.fragment.SearchErrorCityFragment;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import java.util.ArrayList;
import org.json.JSONObject;

/* renamed from: dis reason: default package */
/* compiled from: SearchErrorCityPresenter */
public final class dis extends sw<SearchErrorCityFragment, dip> {
    public dis(SearchErrorCityFragment searchErrorCityFragment) {
        super(searchErrorCityFragment);
        this.b = new dip(this);
    }

    public final void onPageCreated() {
        SearchErrorCityFragment searchErrorCityFragment = (SearchErrorCityFragment) this.mPage;
        searchErrorCityFragment.requestScreenOrientation(1);
        aud aud = (aud) searchErrorCityFragment.getArguments().get(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ);
        searchErrorCityFragment.b = aud.b.b;
        searchErrorCityFragment.c = aud.b.c;
        searchErrorCityFragment.e = aud;
        searchErrorCityFragment.k = 1;
        ArrayList<CitySuggestion> a = SearchErrorCityFragment.a(searchErrorCityFragment.k, searchErrorCityFragment.e.b.c);
        searchErrorCityFragment.g = new SearchSuggestionAdapter(searchErrorCityFragment.getContext(), a);
        searchErrorCityFragment.g.setCityData(a);
        searchErrorCityFragment.l = SearchErrorCityFragment.a(searchErrorCityFragment.e.b.c);
        View contentView = searchErrorCityFragment.getContentView();
        View findViewById = contentView.findViewById(R.id.title_layout);
        findViewById.findViewById(R.id.title_btn_right).setVisibility(4);
        searchErrorCityFragment.a = (TextView) contentView.findViewById(R.id.title_text_name);
        findViewById.findViewById(R.id.title_btn_left).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                SearchErrorCityFragment.this.finish();
            }
        });
        View inflate = searchErrorCityFragment.getActivity().getLayoutInflater().inflate(R.layout.drive_search_listview_error_and_city_suggest_header_view, null);
        searchErrorCityFragment.d = (LinearLayout) inflate.findViewById(R.id.error_info_container);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.city_layout);
        searchErrorCityFragment.h = (ListView) contentView.findViewById(R.id.list);
        if (searchErrorCityFragment.g.getCount() > 0) {
            linearLayout.setVisibility(0);
        } else {
            linearLayout.setVisibility(8);
        }
        searchErrorCityFragment.i = (PullToRefreshListView) contentView.findViewById(R.id.vouchers_pull_refresh_list);
        searchErrorCityFragment.i.setVisibility(0);
        searchErrorCityFragment.i.setMode(Mode.BOTH);
        searchErrorCityFragment.i.setFootershowflag(false);
        searchErrorCityFragment.h = (ListView) searchErrorCityFragment.i.getRefreshableView();
        searchErrorCityFragment.h.setDividerHeight(0);
        searchErrorCityFragment.h.setVisibility(0);
        searchErrorCityFragment.i.setOnRefreshListener((d<T>) new d<ListView>() {
            public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                SearchErrorCityFragment.this.m.postDelayed(new Runnable() {
                    public final void run() {
                        SearchErrorCityFragment searchErrorCityFragment = SearchErrorCityFragment.this;
                        if (searchErrorCityFragment.k <= 1) {
                            searchErrorCityFragment.k = 1;
                            searchErrorCityFragment.i.onRefreshComplete();
                            return;
                        }
                        searchErrorCityFragment.k--;
                        searchErrorCityFragment.g.setCityData(SearchErrorCityFragment.a(searchErrorCityFragment.k, searchErrorCityFragment.e.b.c));
                        searchErrorCityFragment.a();
                        searchErrorCityFragment.b();
                    }
                }, 10);
            }

            public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                SearchErrorCityFragment.this.m.postDelayed(new Runnable() {
                    public final void run() {
                        SearchErrorCityFragment searchErrorCityFragment = SearchErrorCityFragment.this;
                        if (searchErrorCityFragment.k < searchErrorCityFragment.l) {
                            searchErrorCityFragment.k++;
                        } else {
                            searchErrorCityFragment.k = searchErrorCityFragment.l;
                        }
                        searchErrorCityFragment.g.setCityData(SearchErrorCityFragment.a(searchErrorCityFragment.k, searchErrorCityFragment.e.b.c));
                        searchErrorCityFragment.h.setAdapter(searchErrorCityFragment.g);
                        searchErrorCityFragment.a();
                        searchErrorCityFragment.b();
                        SearchErrorCityFragment.this.i.mFooterLoadingView.setVisibility(8);
                    }
                }, 10);
            }
        });
        searchErrorCityFragment.a();
        NoDBClickUtil.a((AdapterView<?>) searchErrorCityFragment.h, searchErrorCityFragment.n);
        searchErrorCityFragment.h.addHeaderView(inflate, null, false);
        searchErrorCityFragment.h.setDividerHeight(0);
        searchErrorCityFragment.j = searchErrorCityFragment.i.changeFooter();
        searchErrorCityFragment.j.setVisibility(0);
        searchErrorCityFragment.h.addFooterView(searchErrorCityFragment.j, null, false);
        searchErrorCityFragment.h.setAdapter(searchErrorCityFragment.g);
        searchErrorCityFragment.b();
        if (searchErrorCityFragment.e != null) {
            searchErrorCityFragment.a.setText(searchErrorCityFragment.e.d);
        }
        for (int i = 0; i < searchErrorCityFragment.b.size(); i++) {
            View inflate2 = View.inflate(searchErrorCityFragment.getContext(), R.layout.drive_search_keyword_error_item_xml, null);
            ((TextView) inflate2.findViewById(R.id.error_info)).setText(searchErrorCityFragment.b.get(i));
            inflate2.setTag(searchErrorCityFragment.b.get(i));
            inflate2.setOnClickListener(new OnClickListener(searchErrorCityFragment.b.get(i)) {
                final /* synthetic */ String a;

                {
                    this.a = r2;
                }

                public final void onClick(View view) {
                    if (SearchErrorCityFragment.this.f != null) {
                        SearchErrorCityFragment.this.f.b(this.a);
                    }
                    SearchErrorCityFragment.this.finish();
                }
            });
            inflate2.findViewById(R.id.top_line).setVisibility(0);
            searchErrorCityFragment.d.addView(inflate2);
            if (i != 0) {
                inflate2.findViewById(R.id.sep).setVisibility(0);
            }
        }
        searchErrorCityFragment.g = new SearchSuggestionAdapter(searchErrorCityFragment.getContext(), searchErrorCityFragment.c);
        searchErrorCityFragment.g.setCityData(searchErrorCityFragment.c);
        searchErrorCityFragment.h.setAdapter(searchErrorCityFragment.g);
        NoDBClickUtil.a((AdapterView<?>) searchErrorCityFragment.h, (OnItemClickListener) new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                int headerViewsCount = i - SearchErrorCityFragment.this.h.getHeaderViewsCount();
                CitySuggestion citySuggestion = (CitySuggestion) SearchErrorCityFragment.this.g.getItem(headerViewsCount);
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("ItemId", headerViewsCount);
                    if (citySuggestion != null) {
                        jSONObject.put("ItemName", citySuggestion.name);
                    }
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
                SearchErrorCityFragment.this.finish();
                if (SearchErrorCityFragment.this.f != null && citySuggestion != null) {
                    SearchErrorCityFragment.this.f.a(citySuggestion.citycode);
                }
            }
        });
    }

    public final /* synthetic */ su a() {
        return new dip(this);
    }
}
