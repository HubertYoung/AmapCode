package com.autonavi.minimap.drive.search.fragment;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.search.adapter.SearchSuggestionAdapter;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.pulltorefresh.internal.LoadingLayout;
import java.util.ArrayList;
import org.json.JSONObject;

public class SearchErrorCityFragment extends DriveBasePage<dis> {
    public TextView a;
    public ArrayList<String> b;
    public ArrayList<CitySuggestion> c;
    public LinearLayout d;
    public aud e;
    public a f;
    public SearchSuggestionAdapter g;
    public ListView h;
    public PullToRefreshListView i;
    public LoadingLayout j;
    public int k;
    public int l;
    Handler m = new Handler();
    public OnItemClickListener n = new OnItemClickListener() {
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
            PageBundle arguments = SearchErrorCityFragment.this.getArguments();
            boolean z = false;
            if (arguments != null) {
                z = arguments.getBoolean("voice_process", false);
            }
            if (z) {
                SearchErrorCityFragment.this.finish();
            } else {
                PageBundle pageBundle = new PageBundle();
                if (citySuggestion != null) {
                    pageBundle.putString("city_name", citySuggestion.name);
                    pageBundle.putString("city_code", citySuggestion.citycode);
                }
                pageBundle.putString(TrafficUtil.KEYWORD, SearchErrorCityFragment.this.e.d);
                pageBundle.putBoolean("clear_search_edit_focus", true);
                SearchErrorCityFragment.this.startPage((String) "amap.search.action.searchfragment", pageBundle);
            }
            if (SearchErrorCityFragment.this.f != null && citySuggestion != null) {
                SearchErrorCityFragment.this.f.a(citySuggestion.citycode);
            }
        }
    };

    public interface a {
        void a(String str);

        void b(String str);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.full_screen_list_dialog_layout);
    }

    public final void a() {
        if (this.j != null) {
            this.j.setVisibility(0);
        }
        this.i.onRefreshComplete();
        this.i.setMode(Mode.BOTH);
        this.i.mHeaderLoadingView.setRefreshingLabel(getString(R.string.isloading));
        if (this.k == 1) {
            this.i.hideImageHead();
            this.i.setNeedRefreshing(false);
            this.i.setHeaderText(getString(R.string.first_page_no_last_apage), getString(R.string.first_page_no_last_apage), getString(R.string.isloading));
            this.i.setFooterText(getString(R.string.first_page_pull_to_second_page), getString(R.string.drive_release_to_next), getString(R.string.isloading));
            return;
        }
        this.i.showImageHead();
        this.i.setNeedRefreshing(true);
        this.i.setHeaderText(String.format(getString(R.string.drive_cur_page_pull_down_to_loading_next), new Object[]{Integer.valueOf(this.k)}), getString(R.string.drive_release_to_last), getString(R.string.loading));
        this.i.setFooterText(String.format(getString(R.string.drive_cur_page_pull_up_to_loading_next), new Object[]{Integer.valueOf(this.k)}), getString(R.string.drive_release_to_next), getString(R.string.isloading));
    }

    public final void b() {
        if (this.k >= this.l) {
            this.i.hideImageFoot();
            this.i.setFooterText(String.format(getString(R.string.current_page_no_next_page), new Object[]{Integer.valueOf(this.k)}), String.format(getString(R.string.current_page_no_next_page), new Object[]{Integer.valueOf(this.k)}), getString(R.string.isloading));
            this.i.setMode(Mode.PULL_FROM_START);
            return;
        }
        this.i.showImageFoot();
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

    public static int a(ArrayList<CitySuggestion> arrayList) {
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
        return new dis(this);
    }
}
