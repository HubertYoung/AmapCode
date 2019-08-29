package com.jiuyan.inimage;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;

/* compiled from: InPhotoEditActivity */
class c implements Runnable {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        this.a.a.toast(this.a.a.getString(R.string.in_sdk_limited_photo_size), 0);
        this.a.a.onClick(this.a.a.findViewById(R.id.ll_crop));
        this.a.a.b(false);
    }
}
