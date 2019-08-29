package com.jiuyan.inimage;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.callback.IPhotoSaveDelegate.IPhotoSaveCallback;
import com.jiuyan.inimage.util.q;

/* compiled from: InPhotoEditActivity */
class e implements IPhotoSaveCallback {
    final /* synthetic */ d a;

    e(d dVar) {
        this.a = dVar;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPhotoSaveResult(boolean z, String str) {
        q.a("mingtian ", "save photo begin " + z + ", path " + str);
        q.a("mingtian ", "save photo begin " + z + ", path " + str);
        this.a.b.a(z, this.a.a, str);
        this.a.b.J = false;
    }
}
