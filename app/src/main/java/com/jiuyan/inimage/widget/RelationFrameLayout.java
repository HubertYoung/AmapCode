package com.jiuyan.inimage.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;
import com.jiuyan.inimage.a.c;
import com.jiuyan.inimage.a.i;
import com.jiuyan.inimage.a.j;
import com.jiuyan.inimage.a.m;
import com.jiuyan.inimage.bean.BeanDataPaster;
import com.jiuyan.inimage.bean.BeanRecRelation;
import com.jiuyan.inimage.http.HttpLauncher;
import com.jiuyan.inimage.util.q;
import java.util.ArrayList;
import java.util.List;

public class RelationFrameLayout extends FrameLayout {
    private final String a = RelationFrameLayout.class.getSimpleName();
    /* access modifiers changed from: private */
    public RecyclerView b;
    private c c;
    /* access modifiers changed from: private */
    public RecyclerView d;
    /* access modifiers changed from: private */
    public j e;
    /* access modifiers changed from: private */
    public View f;
    /* access modifiers changed from: private */
    public String g = "";
    /* access modifiers changed from: private */
    public String h = "";
    private String i;
    /* access modifiers changed from: private */
    public y j;

    public void setOnPasterSelectedListener(y yVar) {
        this.j = yVar;
    }

    public RelationFrameLayout(Context context) {
        super(context);
        a();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public RelationFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public RelationFrameLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a();
    }

    /* access modifiers changed from: protected */
    public void a() {
        inflate(getContext(), R.layout.in_alipay_layout_relation_view, this);
        this.f = findViewById(R.id.layout_relate);
        this.f.setVisibility(8);
        this.d = (RecyclerView) findViewById(R.id.recycler_recommend_title);
        this.d.setVisibility(4);
        this.b = (RecyclerView) findViewById(R.id.recycler_recommend);
        this.b.setVisibility(4);
        c();
    }

    public void b() {
        setVisibility(8);
        this.c.b();
    }

    /* access modifiers changed from: protected */
    public void c() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(0);
        this.b.setLayoutManager(linearLayoutManager);
        this.c = new c(getContext());
        this.b.setAdapter(this.c);
        this.c.a((i) new v(this));
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(0);
        this.d.setLayoutManager(linearLayoutManager2);
        this.e = new j(getContext());
        this.d.setAdapter(this.e);
        this.e.a((m) new w(this));
    }

    public void a(String str) {
        q.a("Paster", "mPasterId " + this.i + ", newId = " + str);
        q.a("Paster", "mPasterId " + this.i + ", newId = " + str);
        if (!str.equals(this.i)) {
            this.i = str;
            HttpLauncher httpLauncher = new HttpLauncher(getContext(), 0, "https://www.in66.com/", "extapi/alipay/relativepaster");
            httpLauncher.putParam("ptid", str);
            httpLauncher.execute(BeanRecRelation.class);
            httpLauncher.setOnCompleteListener(new x(this));
        }
    }

    /* access modifiers changed from: private */
    public void a(List<BeanDataPaster> list) {
        if (list != null && list.size() != 0) {
            ArrayList arrayList = new ArrayList();
            for (BeanDataPaster add : list) {
                arrayList.add(add);
            }
            this.c.b((List<BeanDataPaster>) arrayList);
        }
    }
}
