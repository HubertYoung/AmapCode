package com.jiuyan.inimage;

import android.util.Log;
import android.view.View;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: InPhotoEditActivity */
class q implements Runnable {
    final /* synthetic */ View a;
    final /* synthetic */ InPhotoEditActivity b;

    q(InPhotoEditActivity inPhotoEditActivity, View view) {
        this.b = inPhotoEditActivity;
        this.a = view;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        this.a.setClickable(true);
    }
}
