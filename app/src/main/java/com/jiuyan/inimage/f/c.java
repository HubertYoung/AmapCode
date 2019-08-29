package com.jiuyan.inimage.f;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.InPhotoEditActivity;
import com.jiuyan.inimage.paster.ViewOperation;
import com.jiuyan.inimage.paster.d;
import com.jiuyan.inimage.paster.f;

/* compiled from: CoreLayerDecor */
class c implements f {
    final /* synthetic */ b b;

    c(b bVar) {
        this.b = bVar;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a() {
    }

    public void a(d dVar) {
        ((ViewOperation) this.b.a).a(dVar);
        ((InPhotoEditActivity) this.b.b).a();
    }
}
