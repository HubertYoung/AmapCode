package com.autonavi.minimap.route.bus.busline.page;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.inter.impl.BusLineSearch;
import com.autonavi.minimap.search.view.SearchHistoryList;
import com.autonavi.minimap.search.view.SearchSuggestList;
import com.autonavi.minimap.widget.SearchEdit;
import com.autonavi.minimap.widget.SearchEdit.ISearchEditEventListener;
import com.autonavi.widget.ui.TitleBar;
import org.json.JSONObject;

public class BusLineSearchPage extends AbstractBasePage<duu> implements launchModeSingleTask {
    public SearchEdit a;
    public EditText b = null;
    public SearchHistoryList c;
    public SearchSuggestList d;
    OnClickListener e = new OnClickListener() {
        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.title_btn_right) {
                ((duu) BusLineSearchPage.this.mPresenter).a();
            } else if (id == R.id.btn_search) {
                duu duu = (duu) BusLineSearchPage.this.mPresenter;
                duu.a = BusLineSearchPage.this.b.getText().toString();
                if (!TextUtils.isEmpty(duu.a)) {
                    duu.a = duu.a.trim();
                }
                SuperId.getInstance().reset();
                SuperId.getInstance().setBit1("i");
                SuperId.getInstance().setBit2("03");
                duu.b();
                dys.a((String) "P00114", (String) "B003", (JSONObject) null);
            } else {
                if (id == R.id.title_btn_left) {
                    BusLineSearchPage.this.finish();
                }
            }
        }
    };
    ISearchEditEventListener f = new ISearchEditEventListener() {
        String a;

        public final void onItemLongClicked(TipItem tipItem) {
        }

        public final void onRoute(TipItem tipItem) {
        }

        public final void onItemClicked(TipItem tipItem) {
            BusLineSearchPage.this.d.cancelSuggestNetWork();
            String obj = BusLineSearchPage.this.b.getText().toString();
            duu duu = (duu) BusLineSearchPage.this.mPresenter;
            if (!TextUtils.isEmpty(obj)) {
                obj = obj.trim();
            }
            if (!TextUtils.isEmpty(obj)) {
                duu.a = obj;
                if (!TextUtils.isEmpty(duu.a)) {
                    duu.c = tipItem;
                    BusLineSearch.a(duu.a, 1, (duu.c == null || duu.c.adcode == null || duu.c.adcode.trim().equals("")) ? duu.b : tipItem.adcode, duu.d);
                    return;
                }
                ToastHelper.showLongToast(((BusLineSearchPage) duu.mPage).getString(R.string.act_search_error_empty));
            }
        }

        public final boolean afterTextChanged(Editable editable) {
            this.a = editable.toString();
            if (this.a.length() > 0) {
                BusLineSearchPage.this.g.setVisibility(0);
                BusLineSearchPage.this.g.setEnabled(true);
            } else if (this.a.length() == 0 && BusLineSearchPage.this.g.getVisibility() == 0) {
                BusLineSearchPage.this.g.setVisibility(8);
                BusLineSearchPage.this.g.setEnabled(false);
                BusLineSearchPage.this.d.cancelSuggestNetWork();
            }
            return false;
        }

        public final void onClearEdit() {
            onHideSugg();
            BusLineSearchPage.this.h.setVisibility(0);
        }

        public final void onShowHistory(int i) {
            if (BusLineSearchPage.this.h.getVisibility() != 0) {
                BusLineSearchPage.this.h.setVisibility(0);
                BusLineSearchPage.this.c.showHistory();
                BusLineSearchPage.this.d.cancelSuggestNetWork();
            }
        }

        public final void onHideHistory() {
            BusLineSearchPage.this.c.cancelTask();
            if (BusLineSearchPage.this.h.getVisibility() != 8) {
                BusLineSearchPage.this.h.setVisibility(8);
            }
        }

        public final void onShowSugg(int i) {
            if (!TextUtils.isEmpty(this.a)) {
                BusLineSearchPage.this.d.clearSuggData();
                BusLineSearchPage.this.i.setVisibility(0);
                BusLineSearchPage.this.d.showSuggest(this.a);
            }
        }

        public final void onHideSugg() {
            BusLineSearchPage.this.i.setVisibility(8);
            BusLineSearchPage.this.d.clearSuggData();
            BusLineSearchPage.this.d.cancelSuggestNetWork();
        }
    };
    /* access modifiers changed from: private */
    public Button g = null;
    /* access modifiers changed from: private */
    public ListView h;
    /* access modifiers changed from: private */
    public ListView i;
    private TitleBar j;

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.busline_search_layout);
        View contentView = getContentView();
        this.j = (TitleBar) contentView.findViewById(R.id.title_bar);
        this.j.setBackImgContentDescription(getString(R.string.autonavi_back));
        this.j.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                BusLineSearchPage.this.finish();
            }
        });
        this.j.setOnActionClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ((duu) BusLineSearchPage.this.mPresenter).a();
            }
        });
        contentView.findViewById(R.id.btn_search_back).setVisibility(8);
        this.g = (Button) contentView.findViewById(R.id.btn_search);
        this.g.setVisibility(8);
        NoDBClickUtil.a((View) this.g, this.e);
        this.g.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (BusLineSearchPage.this.b != null) {
                    BusLineSearchPage.this.a.hideInputMethod();
                }
                return false;
            }
        });
        this.a = (SearchEdit) contentView.findViewById(R.id.search_search_layout);
        this.h = (ListView) contentView.findViewById(R.id.history_list_view);
        this.c = new SearchHistoryList(getContext(), this.h, 10114, 1);
        this.i = (ListView) contentView.findViewById(R.id.search_sug_container);
        SearchSuggestList searchSuggestList = new SearchSuggestList(getContext(), this.a, this.i, 10114, "", false);
        this.d = searchSuggestList;
        this.d.setPageContext(this);
        this.b = this.a.getEditText();
        this.b.setHint(getString(R.string.busline_route_and_station));
        this.b.setImeOptions(3);
        this.c.setOnItemEventListener(this.a.onItemEventListener);
        this.d.setOnItemEventListener(this.a.onItemEventListener);
        this.a.setSearchEditEventListener(this.f);
        aho.a(new Runnable() {
            public final void run() {
                StringBuilder sb = new StringBuilder("BusLine onPageCreate searchEdit focus: ");
                sb.append(BusLineSearchPage.this.a.hasFocus());
                eao.a((String) "Amap#", sb.toString());
                BusLineSearchPage.this.a.clearFocus();
            }
        });
        dys.a((String) "P00114", (String) "B004", (JSONObject) null);
    }

    public final void a(GeoPoint geoPoint, long j2, GeoPoint geoPoint2, String str, String str2) {
        if (geoPoint != null) {
            this.d.initPosSearch(geoPoint, j2, 1, "bus|busline", 10114);
        } else {
            this.d.initPosSearch(geoPoint2, j2, 1, "bus|busline", 10114);
        }
        this.j.setTitle(TextUtils.isEmpty(str) ? getString(R.string.busline_beijing) : str);
        this.a.setSelfCall(true);
        this.b.setText(str2);
        this.b.requestFocus();
        this.a.showInputMethod();
        this.c.showHistory();
        this.i.setVisibility(8);
        if (!TextUtils.isEmpty(str2)) {
            this.b.setSelection(str2.length());
        }
    }

    public final void a(String str) {
        this.b.setText(str);
        if (!TextUtils.isEmpty(str)) {
            this.b.requestFocus();
            this.b.setSelection(str.length());
        }
    }

    public final void b(String str) {
        this.j.setTitle(str);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new duu(this);
    }
}
