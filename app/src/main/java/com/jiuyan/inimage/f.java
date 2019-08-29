package com.jiuyan.inimage;

import android.graphics.Bitmap;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.util.q;

/* compiled from: InPhotoEditActivity */
class f implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ Bitmap b;
    final /* synthetic */ String c;
    final /* synthetic */ InPhotoEditActivity d;

    f(InPhotoEditActivity inPhotoEditActivity, boolean z, Bitmap bitmap, String str) {
        this.d = inPhotoEditActivity;
        this.a = z;
        this.b = bitmap;
        this.c = str;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        if (InSDKEntrance.sEditCallback != null) {
            InSDKEntrance.sEditCallback.onEditDone(this.a, this.b, this.c);
            q.a("sEditCallback getresult succ");
            q.a((String) "sEditCallback getresult succ");
        } else {
            q.a("sEditCallback is null");
            q.a((String) "sEditCallback is null");
        }
        this.d.dismissProgressDialog();
        this.d.finish();
    }
}
