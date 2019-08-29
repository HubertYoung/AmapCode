package com.jiuyan.inimage;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;

/* compiled from: InPhotoEditActivity */
class m implements Runnable {
    final /* synthetic */ InPhotoEditActivity a;

    m(InPhotoEditActivity inPhotoEditActivity) {
        this.a = inPhotoEditActivity;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        if (!this.a.P) {
            if (this.a.B != null) {
                this.a.B.e();
            }
            this.a.l();
            return;
        }
        this.a.onClick(this.a.findViewById(R.id.tv_edit_cancel));
    }
}
