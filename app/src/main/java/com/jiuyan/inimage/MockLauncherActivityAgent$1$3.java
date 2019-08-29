package com.jiuyan.inimage;

import android.graphics.Bitmap;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.callback.IPhotoSaveDelegate;
import com.jiuyan.inimage.callback.IPhotoSaveDelegate.IPhotoSaveCallback;
import com.jiuyan.inimage.util.q;

class MockLauncherActivityAgent$1$3 implements IPhotoSaveDelegate {
    final /* synthetic */ w this$1;

    MockLauncherActivityAgent$1$3(w wVar) {
        this.this$1 = wVar;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void savePhoto(Bitmap bitmap, IPhotoSaveCallback iPhotoSaveCallback) {
        q.a("mingtian ", "save photo");
        iPhotoSaveCallback.onPhotoSaveResult(true, "xxx");
    }
}
