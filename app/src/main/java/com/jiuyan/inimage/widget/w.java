package com.jiuyan.inimage.widget;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.a.m;
import com.jiuyan.inimage.bean.BeanRecRelation.DataEntity;

/* compiled from: RelationFrameLayout */
class w implements m {
    final /* synthetic */ RelationFrameLayout b;

    w(RelationFrameLayout relationFrameLayout) {
        this.b = relationFrameLayout;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(int i, DataEntity dataEntity) {
        if (!this.b.g.equals(dataEntity.cate_id)) {
            this.b.g = dataEntity.cate_id;
            this.b.a(this.b.e.b().get(i).list);
            this.b.e.a(i);
        }
    }
}
