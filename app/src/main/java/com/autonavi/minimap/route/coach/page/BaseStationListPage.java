package com.autonavi.minimap.route.coach.page;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.cloudsync.widget.IphoneTreeView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.common.view.RouteLoadingView;
import com.autonavi.minimap.route.train.stationlist.ListIndexView;
import com.autonavi.minimap.route.train.stationlist.Station;
import com.autonavi.minimap.route.train.stationlist.StationListAdapter;
import com.autonavi.minimap.route.train.stationlist.StationManager;
import com.autonavi.minimap.route.train.stationlist.StationManager.JSONParseType;
import com.autonavi.widget.ui.TitleBar;
import com.tencent.connect.common.Constants;
import java.lang.ref.WeakReference;
import java.util.List;
import org.json.JSONArray;

public abstract class BaseStationListPage extends AbstractBasePage<dzq> {
    public TitleBar a;
    public IphoneTreeView b;
    public IphoneTreeView c;
    public StationManager d;
    public RouteLoadingView e;
    public a f;
    private ListIndexView g;
    private StationListAdapter h;
    private StationListAdapter i;

    class OnListItemClickListener implements OnChildClickListener {
        private StationListAdapter mAdapter;

        OnListItemClickListener(StationListAdapter stationListAdapter) {
            this.mAdapter = stationListAdapter;
        }

        public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j) {
            if (this.mAdapter.getChildType(i, i2) == 2) {
                List<ejq> categoryList = this.mAdapter.getCategoryList();
                if (categoryList != null && i < categoryList.size()) {
                    ejq ejq = categoryList.get(i);
                    if (ejq != null) {
                        ejq.d = false;
                        if (ejq.b != null && ejq.b.size() > 0) {
                            ejq.b.remove(ejq.b.size() - 1);
                            ejq.b.addAll(ejq.c);
                        }
                        this.mAdapter.notifyDataSetChanged();
                    }
                }
                return false;
            }
            Station child = this.mAdapter.getChild(i, i2);
            if (child == null || child.name.equals("暂无定位")) {
                return false;
            }
            BaseStationListPage.this.a(BaseStationListPage.this.getContext());
            StationManager e = BaseStationListPage.this.d;
            if (child != null) {
                String a = StationManager.a(child, e.b());
                if (a != null && !a.isEmpty()) {
                    Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
                    StringBuilder sb = new StringBuilder();
                    sb.append(e.c());
                    sb.append("history_station");
                    edit.putString(sb.toString(), a);
                    edit.apply();
                }
            }
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString(Constants.KEY_ACTION, "action_switch_city");
            pageBundle.putObject("key_city", child);
            BaseStationListPage.this.setResult(ResultType.OK, pageBundle);
            BaseStationListPage.this.finish();
            return false;
        }
    }

    public static class a extends defpackage.ahl.a<Boolean> {
        private WeakReference<BaseStationListPage> a;

        public final /* synthetic */ void onFinished(Object obj) {
            Boolean bool = (Boolean) obj;
            super.onFinished(bool);
            BaseStationListPage baseStationListPage = (BaseStationListPage) this.a.get();
            if (baseStationListPage != null && baseStationListPage.isAlive()) {
                if (bool.booleanValue()) {
                    baseStationListPage.a(baseStationListPage.getContentView());
                    baseStationListPage.e.setVisibility(8);
                    return;
                }
                baseStationListPage.finish();
            }
        }

        public a(BaseStationListPage baseStationListPage) {
            this.a = new WeakReference<>(baseStationListPage);
        }

        public final void onError(Throwable th) {
            super.onError(th);
            BaseStationListPage baseStationListPage = (BaseStationListPage) this.a.get();
            if (baseStationListPage != null && baseStationListPage.isAlive()) {
                baseStationListPage.finish();
            }
        }

        public final /* synthetic */ Object doBackground() throws Exception {
            BaseStationListPage baseStationListPage = (BaseStationListPage) this.a.get();
            boolean z = false;
            if (baseStationListPage != null) {
                StationManager e = baseStationListPage.d;
                JSONArray a2 = e.a();
                if (a2 != null ? e.a(a2, JSONParseType.PARSE_FROM_LOCAL_FILE) : false) {
                    z = true;
                }
            }
            return Boolean.valueOf(z);
        }
    }

    public static CharSequence a(int i2) {
        switch (i2) {
            case 1008:
            case 1010:
                return "出发城市(中文/拼音/首字母)";
            case 1009:
            case 1011:
                return "到达城市(中文/拼音/首字母)";
            default:
                return "中文/拼音/首字母";
        }
    }

    public abstract int a();

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.station_list_layout);
    }

    public final void a(Context context) {
        ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(this.a.getEditText().getWindowToken(), 0);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0135, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.lang.String r14) {
        /*
            r13 = this;
            monitor-enter(r13)
            com.autonavi.common.cloudsync.widget.IphoneTreeView r0 = r13.b     // Catch:{ all -> 0x0136 }
            if (r0 == 0) goto L_0x0134
            com.autonavi.common.cloudsync.widget.IphoneTreeView r0 = r13.c     // Catch:{ all -> 0x0136 }
            if (r0 != 0) goto L_0x000b
            goto L_0x0134
        L_0x000b:
            com.autonavi.minimap.route.train.stationlist.StationManager r0 = r13.d     // Catch:{ all -> 0x0136 }
            java.lang.String r14 = r14.toLowerCase()     // Catch:{ all -> 0x0136 }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0136 }
            r1.<init>()     // Catch:{ all -> 0x0136 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x0136 }
            r2.<init>()     // Catch:{ all -> 0x0136 }
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ all -> 0x0136 }
            r3.<init>()     // Catch:{ all -> 0x0136 }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ all -> 0x0136 }
            r4.<init>()     // Catch:{ all -> 0x0136 }
            r5 = 0
            r6 = 0
        L_0x0027:
            java.util.List<ejq> r7 = r0.a     // Catch:{ all -> 0x0136 }
            int r7 = r7.size()     // Catch:{ all -> 0x0136 }
            if (r6 >= r7) goto L_0x00ff
            java.util.List<ejq> r7 = r0.a     // Catch:{ all -> 0x0136 }
            java.lang.Object r7 = r7.get(r6)     // Catch:{ all -> 0x0136 }
            ejq r7 = (defpackage.ejq) r7     // Catch:{ all -> 0x0136 }
            if (r7 == 0) goto L_0x00fb
            java.util.List<com.autonavi.minimap.route.train.stationlist.Station> r8 = r7.b     // Catch:{ all -> 0x0136 }
            r9 = 0
        L_0x003c:
            int r10 = r8.size()     // Catch:{ all -> 0x0136 }
            if (r9 >= r10) goto L_0x0094
            java.lang.Object r10 = r8.get(r9)     // Catch:{ all -> 0x0136 }
            com.autonavi.minimap.route.train.stationlist.Station r10 = (com.autonavi.minimap.route.train.stationlist.Station) r10     // Catch:{ all -> 0x0136 }
            java.lang.String r11 = r0.f     // Catch:{ all -> 0x0136 }
            java.lang.String r12 = r10.name     // Catch:{ all -> 0x0136 }
            boolean r11 = r11.equals(r12)     // Catch:{ all -> 0x0136 }
            if (r11 != 0) goto L_0x0091
            java.lang.String r11 = r10.initial     // Catch:{ all -> 0x0136 }
            if (r11 == 0) goto L_0x0062
            java.lang.String r11 = r10.initial     // Catch:{ all -> 0x0136 }
            boolean r11 = r11.equals(r14)     // Catch:{ all -> 0x0136 }
            if (r11 == 0) goto L_0x0062
            r2.add(r10)     // Catch:{ all -> 0x0136 }
            goto L_0x0091
        L_0x0062:
            java.lang.String r11 = r10.initial     // Catch:{ all -> 0x0136 }
            if (r11 == 0) goto L_0x0072
            java.lang.String r11 = r10.initial     // Catch:{ all -> 0x0136 }
            int r11 = r11.indexOf(r14)     // Catch:{ all -> 0x0136 }
            if (r11 != 0) goto L_0x0072
            r2.add(r10)     // Catch:{ all -> 0x0136 }
            goto L_0x0091
        L_0x0072:
            java.lang.String r11 = r10.name     // Catch:{ all -> 0x0136 }
            if (r11 == 0) goto L_0x0082
            java.lang.String r11 = r10.name     // Catch:{ all -> 0x0136 }
            boolean r11 = r11.contains(r14)     // Catch:{ all -> 0x0136 }
            if (r11 == 0) goto L_0x0082
            r4.add(r10)     // Catch:{ all -> 0x0136 }
            goto L_0x0091
        L_0x0082:
            java.lang.String r11 = r10.pinyin     // Catch:{ all -> 0x0136 }
            if (r11 == 0) goto L_0x0091
            java.lang.String r11 = r10.pinyin     // Catch:{ all -> 0x0136 }
            int r11 = r11.indexOf(r14)     // Catch:{ all -> 0x0136 }
            if (r11 != 0) goto L_0x0091
            r3.add(r10)     // Catch:{ all -> 0x0136 }
        L_0x0091:
            int r9 = r9 + 1
            goto L_0x003c
        L_0x0094:
            java.util.List<com.autonavi.minimap.route.train.stationlist.Station> r8 = r7.c     // Catch:{ all -> 0x0136 }
            boolean r7 = r7.d     // Catch:{ all -> 0x0136 }
            if (r7 == 0) goto L_0x00fb
            if (r8 == 0) goto L_0x00fb
            boolean r7 = r8.isEmpty()     // Catch:{ all -> 0x0136 }
            if (r7 != 0) goto L_0x00fb
            r7 = 0
        L_0x00a3:
            int r9 = r8.size()     // Catch:{ all -> 0x0136 }
            if (r7 >= r9) goto L_0x00fb
            java.lang.Object r9 = r8.get(r7)     // Catch:{ all -> 0x0136 }
            com.autonavi.minimap.route.train.stationlist.Station r9 = (com.autonavi.minimap.route.train.stationlist.Station) r9     // Catch:{ all -> 0x0136 }
            java.lang.String r10 = r0.f     // Catch:{ all -> 0x0136 }
            java.lang.String r11 = r9.name     // Catch:{ all -> 0x0136 }
            boolean r10 = r10.equals(r11)     // Catch:{ all -> 0x0136 }
            if (r10 != 0) goto L_0x00f8
            java.lang.String r10 = r9.initial     // Catch:{ all -> 0x0136 }
            if (r10 == 0) goto L_0x00c9
            java.lang.String r10 = r9.initial     // Catch:{ all -> 0x0136 }
            boolean r10 = r10.equals(r14)     // Catch:{ all -> 0x0136 }
            if (r10 == 0) goto L_0x00c9
            r2.add(r9)     // Catch:{ all -> 0x0136 }
            goto L_0x00f8
        L_0x00c9:
            java.lang.String r10 = r9.initial     // Catch:{ all -> 0x0136 }
            if (r10 == 0) goto L_0x00d9
            java.lang.String r10 = r9.initial     // Catch:{ all -> 0x0136 }
            int r10 = r10.indexOf(r14)     // Catch:{ all -> 0x0136 }
            if (r10 != 0) goto L_0x00d9
            r2.add(r9)     // Catch:{ all -> 0x0136 }
            goto L_0x00f8
        L_0x00d9:
            java.lang.String r10 = r9.name     // Catch:{ all -> 0x0136 }
            if (r10 == 0) goto L_0x00e9
            java.lang.String r10 = r9.name     // Catch:{ all -> 0x0136 }
            boolean r10 = r10.contains(r14)     // Catch:{ all -> 0x0136 }
            if (r10 == 0) goto L_0x00e9
            r4.add(r9)     // Catch:{ all -> 0x0136 }
            goto L_0x00f8
        L_0x00e9:
            java.lang.String r10 = r9.pinyin     // Catch:{ all -> 0x0136 }
            if (r10 == 0) goto L_0x00f8
            java.lang.String r10 = r9.pinyin     // Catch:{ all -> 0x0136 }
            int r10 = r10.indexOf(r14)     // Catch:{ all -> 0x0136 }
            if (r10 != 0) goto L_0x00f8
            r3.add(r9)     // Catch:{ all -> 0x0136 }
        L_0x00f8:
            int r7 = r7 + 1
            goto L_0x00a3
        L_0x00fb:
            int r6 = r6 + 1
            goto L_0x0027
        L_0x00ff:
            r1.addAll(r3)     // Catch:{ all -> 0x0136 }
            r1.addAll(r2)     // Catch:{ all -> 0x0136 }
            r1.addAll(r4)     // Catch:{ all -> 0x0136 }
            java.util.List r14 = com.autonavi.minimap.route.train.stationlist.StationManager.a(r1)     // Catch:{ all -> 0x0136 }
            com.autonavi.minimap.route.train.stationlist.StationListAdapter r0 = r13.i     // Catch:{ all -> 0x0136 }
            r1 = 1
            r0.setLocalList(r14, r1)     // Catch:{ all -> 0x0136 }
            com.autonavi.minimap.route.train.stationlist.StationListAdapter r0 = r13.i     // Catch:{ all -> 0x0136 }
            r0.notifyDataSetChanged()     // Catch:{ all -> 0x0136 }
            r0 = 0
        L_0x0118:
            int r1 = r14.size()     // Catch:{ all -> 0x0136 }
            if (r0 >= r1) goto L_0x0126
            com.autonavi.common.cloudsync.widget.IphoneTreeView r1 = r13.c     // Catch:{ all -> 0x0136 }
            r1.expandGroup(r0)     // Catch:{ all -> 0x0136 }
            int r0 = r0 + 1
            goto L_0x0118
        L_0x0126:
            com.autonavi.common.cloudsync.widget.IphoneTreeView r14 = r13.b     // Catch:{ all -> 0x0136 }
            r0 = 8
            r14.setVisibility(r0)     // Catch:{ all -> 0x0136 }
            com.autonavi.common.cloudsync.widget.IphoneTreeView r14 = r13.c     // Catch:{ all -> 0x0136 }
            r14.setVisibility(r5)     // Catch:{ all -> 0x0136 }
            monitor-exit(r13)
            return
        L_0x0134:
            monitor-exit(r13)
            return
        L_0x0136:
            r14 = move-exception
            monitor-exit(r13)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.coach.page.BaseStationListPage.a(java.lang.String):void");
    }

    /* access modifiers changed from: private */
    public void a(View view) {
        this.a = (TitleBar) view.findViewById(R.id.title_bar);
        this.a.getEditText().setHint(a(getRequestCode()));
        this.a.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                BaseStationListPage.this.a(BaseStationListPage.this.getContext());
                BaseStationListPage.this.finish();
            }
        });
        this.a.addTextChangedListener(new TextWatcher() {
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void afterTextChanged(Editable editable) {
                String obj = editable.toString();
                if (obj.length() > 0 && (obj.toCharArray()[obj.length() - 1] == 10 || obj.toCharArray()[obj.length() - 1] == 13)) {
                    BaseStationListPage.this.a.getEditText().setText(obj.substring(0, obj.length() - 1));
                }
                String obj2 = BaseStationListPage.this.a.getEditText().getText().toString();
                if (TextUtils.isEmpty(obj2)) {
                    BaseStationListPage.d(BaseStationListPage.this);
                } else {
                    BaseStationListPage.this.a(obj2);
                }
                BaseStationListPage.b(BaseStationListPage.this, obj2);
            }
        });
        this.h = new StationListAdapter(this, this.d);
        this.h.setLocalList(this.d.b, false);
        this.b = (IphoneTreeView) view.findViewById(R.id.ex_station_list);
        this.b.setAdapter(this.h);
        this.b.setHeaderView(getActivity().getLayoutInflater().inflate(R.layout.station_list_head_item, this.b, false));
        this.b.setGroupIndicator(null);
        if (this.d.b != null) {
            for (int i2 = 0; i2 < this.d.b.size(); i2++) {
                this.b.expandGroup(i2);
            }
        }
        this.b.setOnGroupClickListener(new OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                return true;
            }
        });
        this.h.setOnChildClickListener(new OnListItemClickListener(this.h));
        this.i = new StationListAdapter(this, this.d);
        this.c = (IphoneTreeView) view.findViewById(R.id.ex_search_list);
        this.c.setAdapter(this.i);
        this.c.setHeaderView(getActivity().getLayoutInflater().inflate(R.layout.station_list_head_item, this.c, false));
        this.c.setGroupIndicator(null);
        this.c.setOnGroupClickListener(new OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                return true;
            }
        });
        this.i.setOnChildClickListener(new OnListItemClickListener(this.i));
        this.g = (ListIndexView) view.findViewById(R.id.index_view);
        this.g.init(getActivity(), this.d.b, this.b);
        ListIndexView listIndexView = this.g;
        StationManager stationManager = this.d;
        listIndexView.setAlphabet((String[]) stationManager.d.toArray(new String[stationManager.d.size()]));
        this.g.setIndexViewVisible(this.d.d(), this.d.e());
        this.g.requestLayout();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dzq(this);
    }

    static /* synthetic */ void a(BaseStationListPage baseStationListPage, JSONArray jSONArray) {
        StationManager stationManager = baseStationListPage.d;
        if (jSONArray != null) {
            stationManager.a(jSONArray, JSONParseType.PARSE_FROM_SERVER_RESPONSE);
        }
        baseStationListPage.a(baseStationListPage.getContentView());
    }

    static /* synthetic */ void d(BaseStationListPage baseStationListPage) {
        if (baseStationListPage.b != null && baseStationListPage.c != null) {
            List<ejq> list = baseStationListPage.d.b;
            baseStationListPage.h.setLocalList(list, false);
            baseStationListPage.h.notifyDataSetChanged();
            for (int i2 = 0; i2 < list.size(); i2++) {
                baseStationListPage.b.expandGroup(i2);
            }
            baseStationListPage.c.setVisibility(8);
            baseStationListPage.b.setVisibility(0);
        }
    }

    static /* synthetic */ void b(BaseStationListPage baseStationListPage, String str) {
        if (TextUtils.isEmpty(str)) {
            baseStationListPage.g.setVisibility(0);
            baseStationListPage.g.init(baseStationListPage.getActivity(), baseStationListPage.d.b, baseStationListPage.b);
            return;
        }
        baseStationListPage.g.setVisibility(8);
    }
}
