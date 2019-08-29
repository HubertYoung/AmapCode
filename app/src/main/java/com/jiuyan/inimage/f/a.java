package com.jiuyan.inimage.f;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.View;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.util.c;

/* compiled from: BaseCoreLayer */
public abstract class a<RootView extends View, Attachment> implements g<RootView, Attachment> {
    protected RootView a;

    public a() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public Bitmap a(Config config) {
        return c.a(this.a, config);
    }
}
