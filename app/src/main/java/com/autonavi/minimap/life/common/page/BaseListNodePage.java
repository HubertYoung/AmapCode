package com.autonavi.minimap.life.common.page;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.c;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import defpackage.doi;

public abstract class BaseListNodePage<Presenter extends doi> extends AbstractBasePage<Presenter> implements c<ListView> {
    private PullToRefreshListView a;

    public void a(PullToRefreshBase<ListView> pullToRefreshBase) {
    }

    /* access modifiers changed from: protected */
    public abstract ListAdapter b();

    /* access modifiers changed from: protected */
    public abstract int g();

    /* access modifiers changed from: protected */
    public abstract int h();

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(g());
    }

    public void a() {
        this.a = (PullToRefreshListView) getContentView().findViewById(h());
        this.a.changeFooter().setVisibility(0);
        this.a.setMode(Mode.PULL_FROM_START);
        this.a.mFooterLoadingView.setRefreshingLabel(getResources().getString(R.string.pull_to_refresh_refreshing_label));
        this.a.setVerticalScrollBarEnabled(true);
        this.a.setOnRefreshListener((c<T>) this);
        this.a.setAdapter(b());
        this.a.getListView().setChoiceMode(1);
    }

    public final void e() {
        if (this.a != null) {
            this.a.onRefreshComplete();
        }
    }

    public final void a(Mode mode) {
        if (this.a != null) {
            this.a.setMode(mode);
        }
    }

    public final void a(int i) {
        this.a.getListView().setSelection(i);
    }

    public final int f() {
        return this.a.getListView().getFirstVisiblePosition();
    }

    public final void i() {
        if (this.a != null) {
            this.a.unRegistAllListener();
        }
    }
}
