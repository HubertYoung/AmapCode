package defpackage;

import android.content.res.Configuration;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.search.fragment.SearchCallbackResultFragment;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;

/* renamed from: dim reason: default package */
/* compiled from: SearchCallbackResultPresenter */
public final class dim extends sw<SearchCallbackResultFragment, dil> {
    public dim(SearchCallbackResultFragment searchCallbackResultFragment) {
        super(searchCallbackResultFragment);
    }

    public final void onPageCreated() {
        ListView listView;
        SearchCallbackResultFragment searchCallbackResultFragment = (SearchCallbackResultFragment) this.mPage;
        searchCallbackResultFragment.getContentView().findViewById(R.id.wrapper_layout).setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0) {
                    return false;
                }
                SearchCallbackResultFragment.this.finish();
                return true;
            }
        });
        if (searchCallbackResultFragment.getContentView() == null) {
            listView = null;
        } else {
            searchCallbackResultFragment.b = (PullToRefreshListView) searchCallbackResultFragment.getContentView().findViewById(R.id.vouchers_pull_refresh_list);
            searchCallbackResultFragment.b.setMode(Mode.BOTH);
            searchCallbackResultFragment.b.setFootershowflag(false);
            searchCallbackResultFragment.b.setVisibility(0);
            searchCallbackResultFragment.a = (ListView) searchCallbackResultFragment.b.getRefreshableView();
            searchCallbackResultFragment.f = searchCallbackResultFragment.b.changeFooter();
            searchCallbackResultFragment.b.mLvFooterLoadingFrame.removeView(searchCallbackResultFragment.b.mFooterLoadingView);
            searchCallbackResultFragment.f.setVisibility(0);
            searchCallbackResultFragment.a.addFooterView(searchCallbackResultFragment.f, null, false);
            searchCallbackResultFragment.b.setOnRefreshListener((d<T>) new d<ListView>() {
                public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                    SearchCallbackResultFragment.this.r.onPullDownToRefresh(pullToRefreshBase);
                    SearchCallbackResultFragment.this.b.mHeaderLoadingView.setVisibility(8);
                }

                public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                    SearchCallbackResultFragment.this.r.onPullUpToRefresh(pullToRefreshBase);
                    SearchCallbackResultFragment.this.b.mFooterLoadingView.setVisibility(8);
                }
            });
            searchCallbackResultFragment.a(1, 1);
            listView = searchCallbackResultFragment.a;
        }
        searchCallbackResultFragment.a = listView;
        if (searchCallbackResultFragment.getContentView() != null) {
            searchCallbackResultFragment.g = (TextView) searchCallbackResultFragment.getContentView().findViewById(R.id.title);
            searchCallbackResultFragment.h = (Button) searchCallbackResultFragment.getContentView().findViewById(R.id.btn_confirm);
            Button button = (Button) searchCallbackResultFragment.getContentView().findViewById(R.id.clean_history);
            button.setText(R.string.cancel);
            button.setVisibility(0);
            button.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    SearchCallbackResultFragment.this.finish();
                }
            });
            searchCallbackResultFragment.j = (TextView) searchCallbackResultFragment.getContentView().findViewById(R.id.btn_netsearch);
        }
        searchCallbackResultFragment.c = searchCallbackResultFragment.getActivity().getLayoutInflater().inflate(R.layout.search_listview_error_and_city_suggest_header, null);
        searchCallbackResultFragment.e = (LinearLayout) searchCallbackResultFragment.c.findViewById(R.id.error_info_layout);
        searchCallbackResultFragment.d = (LinearLayout) searchCallbackResultFragment.c.findViewById(R.id.error_info_container);
        if (searchCallbackResultFragment.a != null) {
            searchCallbackResultFragment.a.setVisibility(0);
            searchCallbackResultFragment.a.addHeaderView(searchCallbackResultFragment.c, null, false);
            searchCallbackResultFragment.a.setOnItemClickListener(new OnItemClickListener() {
                /* JADX WARNING: type inference failed for: r1v0, types: [android.widget.AdapterView<?>, android.widget.AdapterView] */
                /* JADX WARNING: Unknown variable types count: 1 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void onItemClick(android.widget.AdapterView<?> r1, android.view.View r2, int r3, long r4) {
                    /*
                        r0 = this;
                        android.widget.Adapter r1 = r1.getAdapter()
                        java.lang.Object r1 = r1.getItem(r3)
                        com.autonavi.common.model.POI r1 = (com.autonavi.common.model.POI) r1
                        com.autonavi.minimap.drive.search.fragment.SearchCallbackResultFragment r2 = com.autonavi.minimap.drive.search.fragment.SearchCallbackResultFragment.this
                        r2.a(r1)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.search.fragment.SearchCallbackResultFragment.AnonymousClass5.onItemClick(android.widget.AdapterView, android.view.View, int, long):void");
                }
            });
        }
        PageBundle arguments = searchCallbackResultFragment.getArguments();
        if (arguments != null) {
            if (arguments.containsKey("search_url")) {
                searchCallbackResultFragment.k = (aen) arguments.getObject("search_url");
            }
            if (arguments.containsKey("dialog_title")) {
                searchCallbackResultFragment.g.setText(arguments.getString("dialog_title"));
            }
            if (arguments.containsKey("OFFLINE_FIRST")) {
                searchCallbackResultFragment.m = arguments.getBoolean("OFFLINE_FIRST", false);
            }
            if (arguments.containsKey("search_parser")) {
                searchCallbackResultFragment.i = (aud) arguments.getObject("search_parser");
                if (searchCallbackResultFragment.m && searchCallbackResultFragment.j != null) {
                    searchCallbackResultFragment.j.setVisibility(0);
                    searchCallbackResultFragment.j.setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            if (SearchCallbackResultFragment.this.k != null) {
                                SearchCallbackResultFragment.this.m = true;
                                PageBundle pageBundle = new PageBundle();
                                pageBundle.putBoolean("search_net", true);
                                SearchCallbackResultFragment.this.setResult(ResultType.OK, pageBundle);
                                SearchCallbackResultFragment.this.finish();
                            }
                        }
                    });
                } else if (searchCallbackResultFragment.j != null) {
                    searchCallbackResultFragment.j.setVisibility(8);
                }
                searchCallbackResultFragment.b();
            }
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        SearchCallbackResultFragment searchCallbackResultFragment = (SearchCallbackResultFragment) this.mPage;
        searchCallbackResultFragment.a.postDelayed(new Runnable() {
            public final void run() {
                if (SearchCallbackResultFragment.this.o != null) {
                    SearchCallbackResultFragment.this.a.setAdapter(SearchCallbackResultFragment.this.o);
                    SearchCallbackResultFragment.this.a.postInvalidate();
                    SearchCallbackResultFragment.this.o.notifyDataSetChanged();
                }
                SearchCallbackResultFragment.this.b.postInvalidate();
            }
        }, 100);
    }

    public final void onDestroy() {
        SearchCallbackResultFragment searchCallbackResultFragment = (SearchCallbackResultFragment) this.mPage;
        if (searchCallbackResultFragment.l != null && !searchCallbackResultFragment.l.isCancelled()) {
            searchCallbackResultFragment.l.cancel();
            searchCallbackResultFragment.l = null;
        }
    }

    public final /* synthetic */ su a() {
        return new dil(this);
    }
}
