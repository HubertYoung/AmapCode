package com.jiuyan.inimage.a;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.bean.BeanDataPaster;

/* compiled from: PasterGalleryRecommendAdapter */
class e implements OnClickListener {
    final /* synthetic */ BeanDataPaster a;
    final /* synthetic */ h b;
    final /* synthetic */ int c;
    final /* synthetic */ c d;

    e(c cVar, BeanDataPaster beanDataPaster, h hVar, int i) {
        this.d = cVar;
        this.a = beanDataPaster;
        this.b = hVar;
        this.c = i;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onClick(View view) {
        if (!this.d.a(this.a.url)) {
            this.d.a(this.a, this.b.b, this.c);
        } else if (this.d.d != null) {
            this.d.d.a(this.c, this.a);
        }
    }
}
