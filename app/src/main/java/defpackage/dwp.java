package defpackage;

import android.app.Activity;
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
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.view.SearchHistoryList;
import com.autonavi.minimap.search.view.SearchSuggestList;
import com.autonavi.minimap.widget.SearchEdit;
import com.autonavi.minimap.widget.SearchEdit.ISearchEditEventListener;

/* renamed from: dwp reason: default package */
/* compiled from: SearchPanelController */
public final class dwp {
    dwo a;
    String b = "";
    public String c = "";
    protected SearchEdit d;
    Button e = null;
    protected EditText f = null;
    ListView g;
    protected SearchHistoryList h;
    protected ListView i;
    protected SearchSuggestList j;
    private bid k;
    private Activity l;
    private ISearchEditEventListener m = new ISearchEditEventListener() {
        private String b;

        public final void onItemLongClicked(TipItem tipItem) {
        }

        public final void onRoute(TipItem tipItem) {
        }

        public final void onItemClicked(TipItem tipItem) {
            dwp.this.j.cancelSuggestNetWork();
            String obj = dwp.this.f.getText().toString();
            if (!TextUtils.isEmpty(obj)) {
                obj = obj.trim();
            }
            if (!TextUtils.isEmpty(obj)) {
                dwp.this.b = obj;
                if (TextUtils.isEmpty(dwp.this.b)) {
                    ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.act_search_error_empty));
                } else if (dwp.this.a != null) {
                    dwp.this.a.a(tipItem, dwp.this.b);
                }
            }
        }

        public final boolean afterTextChanged(Editable editable) {
            this.b = editable.toString();
            if (this.b.length() > 0) {
                dwp.this.e.setVisibility(0);
                dwp.this.e.setEnabled(true);
            } else if (this.b.length() == 0 && dwp.this.e.getVisibility() == 0) {
                dwp.this.e.setVisibility(8);
                dwp.this.e.setEnabled(false);
                dwp.this.j.cancelSuggestNetWork();
            }
            return false;
        }

        public final void onClearEdit() {
            onHideSugg();
            dwp.this.g.setVisibility(0);
        }

        public final void onShowHistory(int i) {
            if (dwp.this.g.getVisibility() != 0) {
                dwp.this.g.setVisibility(0);
                dwp.this.h.showHistory();
                dwp.this.j.cancelSuggestNetWork();
            }
        }

        public final void onHideHistory() {
            dwp.this.h.cancelTask();
            if (dwp.this.g.getVisibility() != 8) {
                dwp.this.g.setVisibility(8);
            }
        }

        public final void onShowSugg(int i) {
            if (!TextUtils.isEmpty(this.b)) {
                dwp.this.j.clearSuggData();
                dwp.this.i.setVisibility(0);
                dwp.this.j.showSuggest(this.b);
            }
        }

        public final void onHideSugg() {
            dwp.this.i.setVisibility(8);
            dwp.this.j.clearSuggData();
            dwp.this.j.cancelSuggestNetWork();
        }
    };

    public dwp(dwo dwo) {
        this.a = dwo;
    }

    public final String a() {
        String obj = this.f.getText().toString();
        if (!TextUtils.isEmpty(obj)) {
            this.b = obj.trim();
        }
        return this.b;
    }

    public final void a(String str) {
        if (str != null) {
            this.b = str.trim();
        }
        this.f.setText(this.b);
        if (!TextUtils.isEmpty(this.b)) {
            this.f.requestFocus();
            this.f.setSelection(this.b.length());
        }
    }

    public final void a(bid bid, View view) {
        this.k = bid;
        this.l = bid.getActivity();
        this.d = (SearchEdit) view.findViewById(R.id.search_search_layout);
        this.e = (Button) view.findViewById(R.id.btn_search);
        this.e.setVisibility(8);
        NoDBClickUtil.a((View) this.e, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (dwp.this.a != null) {
                    dwp.this.a.a();
                }
            }
        });
        this.e.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (dwp.this.f != null) {
                    dwp.this.d.hideInputMethod();
                }
                return false;
            }
        });
        this.g = (ListView) view.findViewById(R.id.history_list_view);
        this.h = this.a.a(this.g);
        this.i = (ListView) view.findViewById(R.id.search_sug_container);
        this.j = this.a.a(this.d, this.i);
        this.j.setPageContext(this.k);
        this.f = this.d.getEditText();
        this.f.setImeOptions(3);
        this.a.a(this.d);
        this.h.setOnItemEventListener(this.d.onItemEventListener);
        this.j.setOnItemEventListener(this.d.onItemEventListener);
        this.d.setSearchEditEventListener(this.m);
        this.f.setText(this.b);
        this.f.requestFocus();
        this.d.showInputMethod();
        this.h.showHistory();
        this.i.setVisibility(8);
        if (!TextUtils.isEmpty(this.b)) {
            this.f.setSelection(this.b.length());
        }
    }

    public final void b() {
        SuperId.getInstance().reset();
        SuperId.getInstance().setBit1("i");
        this.d.setSuperIdBit1("i");
    }

    public final void c() {
        if (this.h != null) {
            this.h.cancelTask();
        }
        if (this.j != null) {
            this.j.cancelTask();
        }
    }

    public final void d() {
        if (this.d != null) {
            this.d.onDestory();
        }
        if (this.j != null) {
            this.j.onDestroy();
        }
    }

    public final SearchEdit e() {
        return this.d;
    }

    public final void b(String str) {
        try {
            this.d.setAdcode(Long.parseLong(str));
            this.j.setAdcode(Long.parseLong(str));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final SearchHistoryList f() {
        return this.h;
    }

    public final SearchSuggestList g() {
        return this.j;
    }

    public final void a(String str, long j2) {
        this.h.setCityCode(str);
        this.h.setAdcode(j2);
        SearchHistoryList searchHistoryList = this.h;
        this.d.getClass();
        searchHistoryList.setMode(1);
    }
}
