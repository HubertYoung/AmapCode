package com.jiuyan.inimage;

import android.graphics.Bitmap;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: InPhotoEditActivity */
class d implements Runnable {
    final /* synthetic */ Bitmap a;
    final /* synthetic */ InPhotoEditActivity b;

    d(InPhotoEditActivity inPhotoEditActivity, Bitmap bitmap) {
        this.b = inPhotoEditActivity;
        this.a = bitmap;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        InSDKEntrance.sPhotoSaveDelegate.savePhoto(this.a, new e(this));
    }
}
