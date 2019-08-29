package com.jiuyan.inimage.a;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.bean.BeanRecRelation.DataEntity;

/* compiled from: PasterGalleryRecommendTitleAdapter */
class k implements OnClickListener {
    final /* synthetic */ int a;
    final /* synthetic */ DataEntity b;
    final /* synthetic */ j c;

    k(j jVar, int i, DataEntity dataEntity) {
        this.c = jVar;
        this.a = i;
        this.b = dataEntity;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onClick(View view) {
        if (this.c.d != null) {
            this.c.d.a(this.a, this.b);
        }
    }
}
