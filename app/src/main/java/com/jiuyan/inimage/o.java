package com.jiuyan.inimage;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: InPhotoEditActivity */
class o implements Runnable {
    final /* synthetic */ InPhotoEditActivity a;

    o(InPhotoEditActivity inPhotoEditActivity) {
        this.a = inPhotoEditActivity;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        if (InSDKEntrance.sEditCallback != null) {
            InSDKEntrance.sEditCallback.onEditCancel();
        }
        this.a.finish();
    }
}
