package com.autonavi.minimap.life.common.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import com.autonavi.map.template.AbstractViewHolderAdapter.a;
import com.autonavi.map.template.AbstractViewHolderBaseAdapter;
import defpackage.doh;
import java.util.List;

public abstract class BaseListDataNodePage<Presenter extends doh, T, VH extends a> extends BaseListNodePage<Presenter> {
    private AbstractViewHolderBaseAdapter<T, VH> a;

    /* access modifiers changed from: protected */
    public abstract void a(View view);

    /* access modifiers changed from: protected */
    public abstract void a(T t);

    /* access modifiers changed from: protected */
    public abstract void a(T t, VH vh, int i);

    /* access modifiers changed from: protected */
    public abstract VH b(View view);

    /* access modifiers changed from: protected */
    public abstract void c();

    /* access modifiers changed from: protected */
    public abstract int d();

    public void onCreate(Context context) {
        super.onCreate(context);
    }

    public final void a() {
        super.a();
        a(getContentView());
        c();
    }

    /* access modifiers changed from: protected */
    public final ListAdapter b() {
        this.a = new AbstractViewHolderBaseAdapter<T, VH>() {
            public View onCreateView(ViewGroup viewGroup, int i) {
                return LayoutInflater.from(BaseListDataNodePage.this.getContext()).inflate(BaseListDataNodePage.this.d(), null);
            }

            public VH onCreateViewHolder(View view, ViewGroup viewGroup, int i) {
                return BaseListDataNodePage.this.b(view);
            }

            public void onBindViewHolderWithData(VH vh, T t, int i, int i2) {
                BaseListDataNodePage.a(BaseListDataNodePage.this, t, vh, i);
            }
        };
        return this.a;
    }

    /* access modifiers changed from: protected */
    public final void a(List<T> list) {
        if (this.a != null) {
            this.a.setDataAndChangeDataSet(list);
        }
    }

    static /* synthetic */ void a(BaseListDataNodePage baseListDataNodePage, final Object obj, a aVar, int i) {
        aVar.itemView.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                BaseListDataNodePage.this.a(obj);
            }
        });
        baseListDataNodePage.a(obj, aVar, i);
    }
}
