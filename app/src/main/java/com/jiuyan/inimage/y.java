package com.jiuyan.inimage;

import android.graphics.Bitmap;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.callback.IEditCallback;
import com.jiuyan.inimage.util.q;

/* compiled from: MockLauncherActivityAgent */
class y implements IEditCallback {
    final /* synthetic */ w a;

    y(w wVar) {
        this.a = wVar;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onEditCancel() {
        q.a("mingtian ", "edit cancel");
    }

    public void onEditNothing(Bitmap bitmap) {
        q.a("mingtian ", "edit nothing");
    }

    public void onEditDone(boolean z, Bitmap bitmap, String str) {
        q.a("mingtian ", "succ " + z);
        q.a("mingtian ", "path " + str);
        if (bitmap != null) {
            q.a("mingtian ", "bitmap " + bitmap.getWidth() + ", " + bitmap.getHeight());
        }
        InSDKEntrance.release();
    }
}
