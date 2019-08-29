package com.jiuyan.inimage;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;

/* compiled from: InPhotoEditActivity */
class u implements Runnable {
    final /* synthetic */ InPhotoEditActivity a;

    u(InPhotoEditActivity inPhotoEditActivity) {
        this.a = inPhotoEditActivity;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        this.a.toast(this.a.getString(R.string.in_sdk_limited_photo_size), 0);
        this.a.onClick(this.a.findViewById(R.id.ll_crop));
        this.a.b(false);
    }
}
