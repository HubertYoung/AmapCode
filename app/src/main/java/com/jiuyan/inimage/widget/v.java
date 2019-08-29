package com.jiuyan.inimage.widget;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.a.i;
import com.jiuyan.inimage.bean.BeanDataPaster;
import com.jiuyan.inimage.d.b;
import com.jiuyan.inimage.util.k;

/* compiled from: RelationFrameLayout */
class v implements i {
    final /* synthetic */ RelationFrameLayout b;

    v(RelationFrameLayout relationFrameLayout) {
        this.b = relationFrameLayout;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(int i, BeanDataPaster beanDataPaster) {
        b bVar = new b();
        bVar.a = k.a(beanDataPaster);
        bVar.b = this.b.h;
        this.b.j.a(bVar);
    }

    public void a() {
    }
}
