package com.jiuyan.inimage;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: InPhotoEditActivity */
class s implements OnClickListener {
    final /* synthetic */ Runnable a;
    final /* synthetic */ InPhotoEditActivity b;

    s(InPhotoEditActivity inPhotoEditActivity, Runnable runnable) {
        this.b = inPhotoEditActivity;
        this.a = runnable;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (this.a != null) {
            this.a.run();
        }
    }
}
