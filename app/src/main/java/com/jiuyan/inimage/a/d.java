package com.jiuyan.inimage.a;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: PasterGalleryRecommendAdapter */
class d implements OnClickListener {
    final /* synthetic */ c a;

    d(c cVar) {
        this.a = cVar;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onClick(View view) {
        if (this.a.d != null) {
            this.a.d.a();
        }
    }
}
