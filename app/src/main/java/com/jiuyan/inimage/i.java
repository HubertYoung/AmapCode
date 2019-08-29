package com.jiuyan.inimage;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;

/* compiled from: InPhotoEditActivity */
class i implements Runnable {
    final /* synthetic */ InPhotoEditActivity a;

    i(InPhotoEditActivity inPhotoEditActivity) {
        this.a = inPhotoEditActivity;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        if (this.a.P) {
            this.a.onClick(this.a.findViewById(R.id.tv_edit_cancel));
        } else if (this.a.A != null) {
            this.a.A.d();
        }
    }
}
