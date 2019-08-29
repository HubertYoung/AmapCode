package com.jiuyan.inimage;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.f.f;
import com.jiuyan.inimage.util.q;

/* compiled from: InPhotoEditActivity */
class p implements f {
    final /* synthetic */ InPhotoEditActivity a;

    p(InPhotoEditActivity inPhotoEditActivity) {
        this.a = inPhotoEditActivity;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(String str) {
        q.a("onShowPasterRelation id:" + str);
        this.a.l.setVisibility(0);
        this.a.l.a(str);
    }
}
