package com.jiuyan.inimage;

import android.text.TextUtils;
import android.util.Log;
import android.view.ViewTreeObserver.OnPreDrawListener;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.util.DisplayUtil;
import com.jiuyan.inimage.util.b;

/* compiled from: InPhotoEditActivity */
class v implements OnPreDrawListener {
    final /* synthetic */ InPhotoEditActivity a;

    v(InPhotoEditActivity inPhotoEditActivity) {
        this.a = inPhotoEditActivity;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean onPreDraw() {
        if (!this.a.R) {
            this.a.t = this.a.s - DisplayUtil.getStatusBarHeight(this.a);
            try {
                if (this.a.S) {
                    this.a.a(b.a);
                    this.a.p();
                } else if (TextUtils.isEmpty("/storage/emulated/0/DCIM/Camera/1464870376730.jpg")) {
                    this.a.finish();
                } else {
                    this.a.a((String) "/storage/emulated/0/DCIM/Camera/1464870376730.jpg");
                }
                if (this.a.L) {
                    this.a.o();
                }
                this.a.R = true;
            } catch (Exception e) {
                e.printStackTrace();
                this.a.finish();
            }
        }
        return true;
    }
}
