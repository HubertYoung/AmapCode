package com.autonavi.minimap.drive.search.fragment;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.search.adapter.PoiSelectAdapter;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.pulltorefresh.internal.LoadingLayout;
import java.util.ArrayList;

public class SearchCallbackResultFragment extends DriveBasePage<dim> implements Transparent {
    private static final boolean q = (Integer.parseInt(VERSION.SDK) >= 11);
    public ListView a;
    public PullToRefreshListView b;
    public View c;
    public LinearLayout d;
    public LinearLayout e;
    public LoadingLayout f;
    public TextView g;
    public Button h;
    public aud i;
    public TextView j = null;
    public aen k;
    public com.autonavi.common.Callback.a l;
    public boolean m = false;
    /* access modifiers changed from: 0000 */
    public int n = 1;
    /* access modifiers changed from: private */
    public BaseAdapter o;
    private boolean p = true;
    /* access modifiers changed from: private */
    public d<ListView> r = new d<ListView>() {
        public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            pullToRefreshBase.postDelayed(new Runnable() {
                public final void run() {
                    SearchCallbackResultFragment.a(SearchCallbackResultFragment.this);
                }
            }, 10);
        }

        public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            pullToRefreshBase.postDelayed(new Runnable() {
                public final void run() {
                    SearchCallbackResultFragment.b(SearchCallbackResultFragment.this);
                }
            }, 10);
        }
    };

    class a implements aeb<aud> {
        /* synthetic */ a(SearchCallbackResultFragment searchCallbackResultFragment, byte b) {
            this();
        }

        private a() {
        }

        /* renamed from: a */
        public final void callback(final aud aud) {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                aho.a(new Runnable() {
                    public final void run() {
                        a.this.callback(aud);
                    }
                });
                return;
            }
            dii.a();
            SearchCallbackResultFragment.this.a();
            if (aud.b.d == null || aud.b.d.size() <= 0) {
                ToastHelper.showLongToast(SearchCallbackResultFragment.this.getString(R.string.drive_no_result_please_retry_later));
            } else {
                SearchCallbackResultFragment.this.i.b.d.addAll(aud.b.d);
                int a2 = SearchCallbackResultFragment.a(SearchCallbackResultFragment.this.i) + 1;
                int j = SearchCallbackResultFragment.this.n + 1;
                if (j <= a2) {
                    a2 = j;
                }
                if (a2 < 2) {
                    a2 = 2;
                }
                SearchCallbackResultFragment.this.n = a2;
            }
            if (SearchCallbackResultFragment.this.n > 1) {
                SearchCallbackResultFragment.this.b();
            }
        }

        public final void error(int i) {
            if (-1 == i) {
                ToastHelper.showLongToast(SearchCallbackResultFragment.this.getString(R.string.network_error_message));
            } else {
                ToastHelper.showLongToast(SearchCallbackResultFragment.this.getString(R.string.drive_no_result_please_retry_later));
            }
            SearchCallbackResultFragment.this.a();
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.drive_fragment_searchcallback_result_list);
    }

    private void a(BaseAdapter baseAdapter) {
        this.o = baseAdapter;
        this.a.setAdapter(this.o);
    }

    public final void a(int i2, int i3) {
        if (i2 == 0) {
            this.b.onRefreshComplete();
            return;
        }
        if (this.f != null) {
            this.f.setVisibility(0);
        }
        this.b.mHeaderLoadingView.setRefreshingLabel(getString(R.string.isloading));
        this.b.onRefreshComplete();
        this.b.setMode(Mode.BOTH);
        this.b.showImageFoot();
        if (i2 == 1) {
            this.b.hideImageHead();
            this.b.setNeedRefreshing(false);
            this.b.setHeaderText(getString(R.string.first_page_no_last_apage), getString(R.string.first_page_no_last_apage), getString(R.string.isloading));
            this.b.setFooterText(getString(R.string.first_page_pull_to_second_page), getString(R.string.drive_release_to_next), getString(R.string.isloading));
        } else {
            this.b.showImageHead();
            this.b.setNeedRefreshing(true);
            this.b.setHeaderText(String.format(getString(R.string.drive_cur_page_pull_down_to_loading_next), new Object[]{Integer.valueOf(i2)}), getString(R.string.drive_release_to_last), getString(R.string.isloading));
            this.b.setFooterText(String.format(getString(R.string.drive_cur_page_pull_up_to_loading_next), new Object[]{Integer.valueOf(i2)}), getString(R.string.drive_release_to_next), getString(R.string.isloading));
        }
        if (i2 >= i3) {
            this.b.setFooterText(String.format(getString(R.string.current_page_no_next_page), new Object[]{Integer.valueOf(i2)}), String.format(getString(R.string.current_page_no_next_page), new Object[]{Integer.valueOf(i2)}), getString(R.string.isloading));
            this.b.setMode(Mode.PULL_FROM_START);
            if (this.o != null && this.o.getCount() > 0 && this.o.getCount() <= 10) {
                this.b.hideImageFoot();
            }
        }
        if (this.o != null && this.o.getCount() > 0) {
            this.a.setSelection(0);
        }
    }

    public final void a() {
        if (this.b != null) {
            this.b.onRefreshComplete();
        }
    }

    private void a(ArrayList<POI> arrayList) {
        ArrayList<String> arrayList2 = this.i.b.b;
        this.d.removeAllViews();
        if (arrayList2 == null || arrayList2.size() <= 0) {
            this.e.setVisibility(8);
        } else {
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                View inflate = getActivity().getLayoutInflater().inflate(R.layout.drive_search_keyword_error_item_xml, null);
                String str = arrayList2.get(i2);
                ((TextView) inflate.findViewById(R.id.error_info)).setText(str);
                inflate.setTag(str);
                inflate.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        String obj = view.getTag().toString();
                        PageBundle pageBundle = new PageBundle();
                        pageBundle.putString(TrafficUtil.KEYWORD, obj);
                        SearchCallbackResultFragment.this.setResult(ResultType.OK, pageBundle);
                        SearchCallbackResultFragment.this.finish();
                    }
                });
                this.d.addView(inflate);
                if (i2 != 0) {
                    inflate.findViewById(R.id.sep).setVisibility(0);
                }
            }
            this.e.setVisibility(0);
        }
        a((BaseAdapter) new PoiSelectAdapter(getContext(), arrayList));
        a(this.n, c(this.i));
    }

    /* access modifiers changed from: private */
    public void a(POI poi) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("result_poi", poi);
        setResult(ResultType.OK, pageBundle);
        finish();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x009a, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x009f, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.util.ArrayList<com.autonavi.common.model.POI> a(int r6, defpackage.aud r7) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            if (r7 == 0) goto L_0x009e
            auc r1 = r7.b     // Catch:{ all -> 0x009b }
            if (r1 == 0) goto L_0x009e
            auc r1 = r7.b     // Catch:{ all -> 0x009b }
            java.util.ArrayList<com.autonavi.common.model.POI> r1 = r1.d     // Catch:{ all -> 0x009b }
            if (r1 == 0) goto L_0x009e
            auc r1 = r7.b     // Catch:{ all -> 0x009b }
            java.util.ArrayList<com.autonavi.common.model.POI> r1 = r1.d     // Catch:{ all -> 0x009b }
            int r1 = r1.size()     // Catch:{ all -> 0x009b }
            if (r1 != 0) goto L_0x001a
            goto L_0x009e
        L_0x001a:
            r1 = 0
            if (r7 == 0) goto L_0x005b
            auc r2 = r7.b     // Catch:{ all -> 0x009b }
            if (r2 == 0) goto L_0x005b
            auc r2 = r7.b     // Catch:{ all -> 0x009b }
            java.util.ArrayList<com.autonavi.common.model.POI> r2 = r2.d     // Catch:{ all -> 0x009b }
            if (r2 != 0) goto L_0x0028
            goto L_0x005b
        L_0x0028:
            r2 = 0
        L_0x0029:
            auc r3 = r7.b     // Catch:{ all -> 0x009b }
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d     // Catch:{ all -> 0x009b }
            int r3 = r3.size()     // Catch:{ all -> 0x009b }
            if (r2 >= r3) goto L_0x005b
            auc r3 = r7.b     // Catch:{ all -> 0x009b }
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d     // Catch:{ all -> 0x009b }
            java.lang.Object r3 = r3.get(r2)     // Catch:{ all -> 0x009b }
            com.autonavi.common.model.POI r3 = (com.autonavi.common.model.POI) r3     // Catch:{ all -> 0x009b }
            if (r3 == 0) goto L_0x0058
            java.lang.String r3 = r3.getId()     // Catch:{ all -> 0x009b }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x009b }
            if (r3 == 0) goto L_0x0058
            auc r3 = r7.b     // Catch:{ all -> 0x009b }
            auc r4 = r7.b     // Catch:{ all -> 0x009b }
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.d     // Catch:{ all -> 0x009b }
            java.lang.Object r2 = r4.remove(r2)     // Catch:{ all -> 0x009b }
            com.autonavi.common.model.POI r2 = (com.autonavi.common.model.POI) r2     // Catch:{ all -> 0x009b }
            r3.f = r2     // Catch:{ all -> 0x009b }
            goto L_0x005b
        L_0x0058:
            int r2 = r2 + 1
            goto L_0x0029
        L_0x005b:
            auc r2 = r7.b     // Catch:{ all -> 0x009b }
            java.util.ArrayList<com.autonavi.common.model.POI> r2 = r2.d     // Catch:{ all -> 0x009b }
            int r2 = r2.size()     // Catch:{ all -> 0x009b }
            if (r6 <= 0) goto L_0x0099
            int r3 = b(r7)     // Catch:{ all -> 0x009b }
            if (r6 <= r3) goto L_0x006c
            goto L_0x0099
        L_0x006c:
            int r6 = r6 + -1
            int r6 = r6 * 10
            if (r6 < r2) goto L_0x0074
            monitor-exit(r5)
            return r0
        L_0x0074:
            int r0 = r6 + 10
            int r0 = r0 + -1
            int r2 = r2 + -1
            if (r0 <= r2) goto L_0x007d
            r0 = r2
        L_0x007d:
            int r0 = r0 - r6
            int r0 = r0 + 1
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x009b }
            r2.<init>()     // Catch:{ all -> 0x009b }
        L_0x0085:
            if (r1 >= r0) goto L_0x0097
            auc r3 = r7.b     // Catch:{ all -> 0x009b }
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d     // Catch:{ all -> 0x009b }
            int r4 = r6 + r1
            java.lang.Object r3 = r3.get(r4)     // Catch:{ all -> 0x009b }
            r2.add(r3)     // Catch:{ all -> 0x009b }
            int r1 = r1 + 1
            goto L_0x0085
        L_0x0097:
            monitor-exit(r5)
            return r2
        L_0x0099:
            monitor-exit(r5)
            return r0
        L_0x009b:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        L_0x009e:
            monitor-exit(r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.search.fragment.SearchCallbackResultFragment.a(int, aud):java.util.ArrayList");
    }

    private static int b(aud aud) {
        if (aud == null || aud.b == null || aud.b.d == null || aud.b.d.size() <= 0) {
            return 1;
        }
        return ((aud.b.d.size() + 10) - 1) / 10;
    }

    private static int c(aud aud) {
        if (aud == null) {
            return 0;
        }
        return ((aud.b.e + 10) - 1) / 10;
    }

    public static int a(aud aud) {
        if (aud == null || aud.b == null || aud.b.d == null || aud.b.d.size() <= 0) {
            return 1;
        }
        return ((aud.b.d.size() + 10) - 1) / 10;
    }

    public final void b() {
        ArrayList<POI> a2 = a(this.n, this.i);
        if (a2 == null) {
            ArrayList<POI> arrayList = null;
            if (this.i.a != null) {
                arrayList = this.i.a.POIList;
            }
            if (arrayList != null) {
                a(arrayList);
            }
        } else if (this.i.b.a.e == 1) {
            if (a2.size() > 0) {
                a(a2.get(0));
            }
        } else {
            a(a2);
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dim(this);
    }

    static /* synthetic */ void a(SearchCallbackResultFragment searchCallbackResultFragment) {
        searchCallbackResultFragment.n--;
        int i2 = searchCallbackResultFragment.n;
        if (i2 == 1) {
            ListAdapter adapter = searchCallbackResultFragment.a.getAdapter();
            if (q) {
                if (adapter != null) {
                    searchCallbackResultFragment.a.addHeaderView(searchCallbackResultFragment.c, null, false);
                }
            } else if (adapter != null && searchCallbackResultFragment.a.getHeaderViewsCount() == 0) {
                searchCallbackResultFragment.a.addHeaderView(searchCallbackResultFragment.c, null, false);
            }
        } else if (i2 != 0) {
            searchCallbackResultFragment.a.removeHeaderView(searchCallbackResultFragment.c);
        }
        if (i2 <= 0) {
            searchCallbackResultFragment.n = 1;
            searchCallbackResultFragment.a(0, b(searchCallbackResultFragment.i));
            return;
        }
        searchCallbackResultFragment.b();
    }

    static /* synthetic */ void b(SearchCallbackResultFragment searchCallbackResultFragment) {
        searchCallbackResultFragment.p = false;
        int a2 = a(searchCallbackResultFragment.i) + 1;
        int i2 = searchCallbackResultFragment.n + 1;
        if (i2 == 1) {
            ListAdapter adapter = searchCallbackResultFragment.a.getAdapter();
            if (q) {
                if (adapter != null) {
                    searchCallbackResultFragment.a.addHeaderView(searchCallbackResultFragment.c, null, false);
                }
            } else if (adapter != null && searchCallbackResultFragment.a.getHeaderViewsCount() == 0) {
                searchCallbackResultFragment.a.addHeaderView(searchCallbackResultFragment.c, null, false);
            }
        } else {
            searchCallbackResultFragment.a.removeHeaderView(searchCallbackResultFragment.c);
        }
        if (i2 <= a2) {
            a2 = i2;
        }
        if (a2 < 2) {
            a2 = 2;
        }
        if (a2 <= searchCallbackResultFragment.i.b.e) {
            ArrayList<POI> a3 = searchCallbackResultFragment.a(a2, searchCallbackResultFragment.i);
            if (a3 == null || a3.size() <= 0) {
                searchCallbackResultFragment.k.e = a2;
                a aVar = new a(searchCallbackResultFragment, 0);
                ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
                if (iSearchService != null) {
                    dii.a(searchCallbackResultFragment.k.a, iSearchService.b(aew.a((aem) searchCallbackResultFragment.k), searchCallbackResultFragment.m ? 1 : 0, aVar), searchCallbackResultFragment.getContext());
                }
            } else {
                searchCallbackResultFragment.n = a2;
                searchCallbackResultFragment.b();
            }
        }
    }
}
