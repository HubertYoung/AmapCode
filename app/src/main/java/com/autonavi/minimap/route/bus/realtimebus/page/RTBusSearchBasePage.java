package com.autonavi.minimap.route.bus.realtimebus.page;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ListView;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.SuperId;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.search.view.SearchHistoryList;
import com.autonavi.minimap.search.view.SearchSuggestList;
import com.autonavi.minimap.widget.SearchEdit;
import defpackage.dyq;

public abstract class RTBusSearchBasePage<Presenter extends dyq> extends AbstractBasePage<Presenter> implements launchModeSingleTask {
    public dwp a;
    protected dwo b = new dwo() {
        public final SearchHistoryList a(ListView listView) {
            return ((dyq) RTBusSearchBasePage.this.mPresenter).a(listView);
        }

        public final SearchSuggestList a(SearchEdit searchEdit, ListView listView) {
            return ((dyq) RTBusSearchBasePage.this.mPresenter).a(searchEdit, listView);
        }

        public final void a(SearchEdit searchEdit) {
            ((dyq) RTBusSearchBasePage.this.mPresenter).a(searchEdit);
        }

        public final void a(TipItem tipItem, String str) {
            ((dyq) RTBusSearchBasePage.this.mPresenter).a = tipItem;
            if (TextUtils.isEmpty(tipItem.adcode) && !TextUtils.isEmpty(((dyq) RTBusSearchBasePage.this.mPresenter).b)) {
                tipItem.adcode = ((dyq) RTBusSearchBasePage.this.mPresenter).b;
            }
            ((dyq) RTBusSearchBasePage.this.mPresenter).a(str, ((dyq) RTBusSearchBasePage.this.mPresenter).b);
        }

        public final void a() {
            if (RTBusSearchBasePage.this.a != null) {
                String a2 = RTBusSearchBasePage.this.a.a();
                SuperId.getInstance().reset();
                SuperId.getInstance().setBit1("i");
                SuperId.getInstance().setBit2("03");
                ((dyq) RTBusSearchBasePage.this.mPresenter).a(a2);
            }
        }
    };

    /* access modifiers changed from: protected */
    public abstract void a();

    public void onCreate(Context context) {
        super.onCreate(context);
        a();
    }

    public final String b() {
        return this.a != null ? this.a.a() : "";
    }

    public final void a(String str) {
        if (this.a != null) {
            this.a.c = str;
        }
    }

    public final String c() {
        return this.a != null ? this.a.c : "";
    }

    public final SearchHistoryList d() {
        if (this.a != null) {
            return this.a.f();
        }
        return null;
    }

    public final SearchSuggestList e() {
        if (this.a != null) {
            return this.a.g();
        }
        return null;
    }

    public static void b(String str) {
        ToastHelper.showToast(str);
    }
}
