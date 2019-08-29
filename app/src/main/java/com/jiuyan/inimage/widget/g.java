package com.jiuyan.inimage.widget;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.callback.IFaceDelegate.IDetectCallback;
import com.jiuyan.inimage.e.a;
import com.jiuyan.inimage.util.q;

/* compiled from: MagicWandView */
class g implements IDetectCallback {
    final /* synthetic */ MagicWandView a;

    g(MagicWandView magicWandView) {
        this.a = magicWandView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onDetectResult(int[][] iArr) {
        if (iArr == null) {
            this.a.a(0, (String) "get", (String) "");
            q.a((String) "face info is null");
            return;
        }
        q.a("face info succ,length " + iArr.length);
        this.a.d = new a(iArr);
        if (this.a.d.c() > 0) {
            q.a("face count is " + this.a.d.c());
            this.a.a(this.a.d.c(), (String) "face", String.valueOf(this.a.a(this.a.d, this.a.b)));
            return;
        }
        q.a((String) "face count is 0");
        this.a.a(0, (String) "get", (String) "");
    }
}
