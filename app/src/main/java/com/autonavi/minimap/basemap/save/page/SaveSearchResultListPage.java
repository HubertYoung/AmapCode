package com.autonavi.minimap.basemap.save.page;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleInstance;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.pulltorefresh.internal.LoadingLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SaveSearchResultListPage extends AbstractBasePage<cro> implements launchModeSingleInstance {
    /* access modifiers changed from: private */
    public static Handler n = new Handler();
    public SuperId a;
    /* access modifiers changed from: private */
    public boolean b = false;
    private Animation c;
    private Animation d;
    private SaveSearchListAdapter e;
    /* access modifiers changed from: private */
    public aud f = null;
    /* access modifiers changed from: private */
    public PullToRefreshListView g = null;
    private ListView h = null;
    private LoadingLayout i = null;
    private SearchKeywordResultTitleView j = null;
    /* access modifiers changed from: private */
    public int k = 1;
    /* access modifiers changed from: private */
    public int l = 0;
    /* access modifiers changed from: private */
    public int m = 0;
    private int o = 0;
    /* access modifiers changed from: private */
    public int p = 1;
    private com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView.a q = new com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView.a() {
        public final void c() {
        }

        public final void a() {
            SaveSearchResultListPage.this.finish();
        }

        public final void b() {
            SaveSearchResultListPage.this.finish();
        }

        public final void d() {
            PageBundle arguments = SaveSearchResultListPage.this.getArguments();
            if (arguments != null) {
                if (arguments.get("poi_search_result") != null) {
                    arguments.remove("poi_search_result");
                }
                if (arguments.get("cur_page") != null) {
                    arguments.remove("cur_page");
                }
                if (arguments.get("total_page") != null) {
                    arguments.remove("total_page");
                }
                arguments.putObject("cur_page", Integer.valueOf(SaveSearchResultListPage.this.k));
                arguments.putObject("total_page", Integer.valueOf(SaveSearchResultListPage.this.l));
                arguments.putObject("poi_search_result", SaveSearchResultListPage.this.f);
                SaveSearchResultListPage.this.startPageForResult(SaveSearchResultMapPage.class, arguments, 2);
            }
        }
    };

    static class SaveSearchListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<a> mList = new ArrayList();

        public long getItemId(int i) {
            return (long) i;
        }

        public void clear() {
            if (this.mList != null && this.mList.size() > 0) {
                this.mList.clear();
            }
        }

        SaveSearchListAdapter(Context context) {
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        public void addItems(List<a> list) {
            this.mList.addAll(list);
        }

        public void removeItem(int i) {
            this.mList.remove(i);
        }

        public int getCount() {
            return this.mList.size();
        }

        public Object getItem(int i) {
            return this.mList.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2;
            b bVar;
            if (view == null) {
                bVar = new b();
                view2 = this.mInflater.inflate(R.layout.save_search_list_item, null);
                bVar.a = (TextView) view2.findViewById(R.id.tvAddrPrev);
                bVar.c = (TextView) view2.findViewById(R.id.tvAddrLast);
                bVar.b = (TextView) view2.findViewById(R.id.tvOrder);
                view2.setTag(bVar);
            } else {
                view2 = view;
                bVar = (b) view.getTag();
            }
            a aVar = this.mList.get(i);
            bVar.c.setText(aVar.a.getName());
            bVar.a.setText(aVar.a.getAddr());
            bVar.b.setText(String.valueOf(aVar.b));
            return view2;
        }
    }

    public static class a {
        public POI a;
        public int b;
    }

    static class b {
        TextView a;
        TextView b;
        TextView c;

        b() {
        }
    }

    private void b() {
        PageBundle arguments = getArguments();
        int i2 = arguments.getInt("tipType");
        String string = arguments.getString(TrafficUtil.KEYWORD);
        if (arguments.get("cur_page") != null) {
            this.k = arguments.getInt("cur_page");
        }
        if (arguments.get("total_page") != null) {
            this.l = arguments.getInt("total_page");
        }
        if (DoNotUseTool.getMapManager().getMapView() != null) {
            aen aen = new aen(string, DoNotUseTool.getMapView().H());
            aen.e = this.p;
            if (i2 == 0) {
                aen.c = "532000";
            } else if (i2 == 1) {
                aen.c = "531000";
            }
            aen.d = SuperId.getInstance().getScenceId();
            cqz cqz = new cqz(getContext(), aen, new defpackage.cqz.a() {
                public final void a(String str) {
                    SuperId.getInstance().setBit3("08");
                    SaveSearchResultListPage.this.a(SaveSearchResultListPage.this.f, SaveSearchResultListPage.this.b);
                }

                public final void a(PageBundle pageBundle) {
                    aud aud = (aud) pageBundle.getObject("poi_search_result");
                    if (!(aud == null || aud.b == null || aud.b.d == null || aud.b.d.size() <= 0)) {
                        SaveSearchResultListPage.this.f = aud;
                        SaveSearchResultListPage.this.k = SaveSearchResultListPage.this.p;
                    }
                    SaveSearchResultListPage.this.a(SaveSearchResultListPage.this.f, SaveSearchResultListPage.this.b);
                }

                public final void a() {
                    SaveSearchResultListPage.this.a(SaveSearchResultListPage.this.f, SaveSearchResultListPage.this.b);
                }
            });
            cqz.c = string;
            cqz.a();
        }
    }

    public void onCreate(Context context) {
        int i2;
        super.onCreate(context);
        this.c = AnimationUtils.loadAnimation(getContext(), R.anim.autonavi_top_in);
        this.d = AnimationUtils.loadAnimation(getContext(), R.anim.autonavi_bottom_in);
        PageBundle arguments = getArguments();
        this.f = (aud) arguments.getObject("poi_search_result");
        aud aud = this.f;
        if (aud == null) {
            i2 = 0;
        } else {
            i2 = ((aud.b.e + 10) - 1) / 10;
        }
        this.l = i2;
        this.m = aud.b.e;
        if (arguments.getObject("cur_page") != null) {
            this.k = arguments.getInt("cur_page");
        }
        setContentView(R.layout.save_search_result_list_fragment);
        View contentView = getContentView();
        this.j = (SearchKeywordResultTitleView) contentView.findViewById(R.id.view_normal_title);
        this.j.setOnSearchKeywordResultTitleViewListener(this.q);
        this.j.showListModel(true);
        this.g = (PullToRefreshListView) contentView.findViewById(R.id.vouchers_pull_refresh_list);
        this.g.setMode(Mode.BOTH);
        this.g.setFootershowflag(true);
        this.h = (ListView) this.g.getRefreshableView();
        this.h.setChoiceMode(1);
        this.h.setDescendantFocusability(393216);
        this.h.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                PageBundle arguments = SaveSearchResultListPage.this.getArguments();
                if (arguments.get("poi_search_result") != null) {
                    arguments.remove("poi_search_result");
                }
                if (arguments.get("SELECTED_ITEM_INDEX_DATA_KEY") != null) {
                    arguments.remove("SELECTED_ITEM_INDEX_DATA_KEY");
                }
                if (arguments.get("cur_page") != null) {
                    arguments.remove("cur_page");
                }
                if (arguments.get("total_page") != null) {
                    arguments.remove("total_page");
                }
                int i2 = i - 1;
                if (i2 < 0) {
                    i2 = 0;
                }
                arguments.putObject("SELECTED_ITEM_INDEX_DATA_KEY", Integer.valueOf(i2));
                arguments.putObject("cur_page", Integer.valueOf(SaveSearchResultListPage.this.k));
                arguments.putObject("total_page", Integer.valueOf(SaveSearchResultListPage.this.l));
                arguments.putObject("poi_search_result", SaveSearchResultListPage.this.f);
                SaveSearchResultListPage.this.startPageForResult(SaveSearchResultMapPage.class, arguments, 2);
            }
        });
        PageBundle arguments2 = getArguments();
        String str = "";
        if (arguments2.containsKey(TrafficUtil.KEYWORD)) {
            str = arguments2.getString(TrafficUtil.KEYWORD);
        }
        this.j.setKeyword(str);
        this.g.setOnRefreshListener((d<T>) new d<ListView>() {
            public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                SaveSearchResultListPage.n.postDelayed(new Runnable() {
                    public final void run() {
                        SaveSearchResultListPage.f(SaveSearchResultListPage.this);
                    }
                }, 10);
            }

            public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                if (SaveSearchResultListPage.this.k < SaveSearchResultListPage.this.l || SaveSearchResultListPage.this.m >= 10) {
                    SaveSearchResultListPage.n.postDelayed(new Runnable() {
                        public final void run() {
                            SaveSearchResultListPage.h(SaveSearchResultListPage.this);
                        }
                    }, 10);
                    return;
                }
                if (SaveSearchResultListPage.this.g != null) {
                    SaveSearchResultListPage.this.g.onRefreshComplete();
                }
            }
        });
        this.e = new SaveSearchListAdapter(getActivity());
        this.h.setAdapter(this.e);
        a(this.f, this.b);
    }

    /* access modifiers changed from: private */
    public void a(aud aud, boolean z) {
        this.f = aud;
        a(this.k, this.l);
        c();
        this.h.startAnimation(z ? this.c : this.d);
    }

    private void c() {
        if (this.f != null) {
            ArrayList arrayList = new ArrayList();
            this.o = (this.k - 1) * 10;
            Iterator<POI> it = this.f.b.d.iterator();
            while (it.hasNext()) {
                a aVar = new a();
                aVar.a = it.next();
                int i2 = this.o + 1;
                this.o = i2;
                aVar.b = i2;
                arrayList.add(aVar);
            }
            this.e.clear();
            this.e.addItems(arrayList);
            this.e.notifyDataSetChanged();
            this.h.setSelection(0);
        }
    }

    private void a(int i2, int i3) {
        this.g.onRefreshComplete();
        if (this.i != null) {
            this.i.setVisibility(0);
        }
        this.g.setMode(Mode.BOTH);
        this.g.mHeaderLoadingView.setRefreshingLabel(getString(R.string.isloading));
        if (i2 == 1) {
            this.g.hideImageHead();
            this.g.setNeedRefreshing(false);
            this.g.setHeaderText(getString(R.string.first_page_no_last_apage), getString(R.string.first_page_no_last_apage), getString(R.string.isloading));
            this.g.setFooterText(getString(R.string.first_page_pull_to_second_page), getString(R.string.release_to_second_page), getString(R.string.isloading));
        } else {
            this.g.showImageHead();
            this.g.setNeedRefreshing(true);
            int i4 = i2 - 1;
            this.g.setHeaderText(String.format(getString(R.string.pull_down_to_loading_next_page), new Object[]{Integer.valueOf(i4)}), String.format(getString(R.string.release_to_next_page), new Object[]{Integer.valueOf(i4)}), getString(R.string.isloading));
            int i5 = i2 + 1;
            this.g.setFooterText(String.format(getString(R.string.pull_up_to_next_page), new Object[]{Integer.valueOf(i5)}), String.format(getString(R.string.release_to_next_page), new Object[]{Integer.valueOf(i5)}), getString(R.string.isloading));
        }
        if (i2 < i3) {
            this.g.showImageFoot();
        }
        if (i2 >= i3 || this.f.b.d.size() < 10) {
            this.g.setFooterText(String.format(getString(R.string.current_page_no_next_page), new Object[]{Integer.valueOf(i2)}), String.format(getString(R.string.current_page_no_next_page), new Object[]{Integer.valueOf(i2)}), getString(R.string.isloading));
            this.g.setMode(Mode.PULL_FROM_START);
            this.g.hideImageFoot();
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cro(this);
    }

    static /* synthetic */ void f(SaveSearchResultListPage saveSearchResultListPage) {
        int i2 = saveSearchResultListPage.k;
        if (i2 <= 1) {
            saveSearchResultListPage.g.onRefreshComplete();
            return;
        }
        saveSearchResultListPage.b = true;
        saveSearchResultListPage.p = i2 - 1;
        saveSearchResultListPage.b();
    }

    static /* synthetic */ void h(SaveSearchResultListPage saveSearchResultListPage) {
        saveSearchResultListPage.b = false;
        saveSearchResultListPage.p = saveSearchResultListPage.k + 1;
        saveSearchResultListPage.b();
    }
}
