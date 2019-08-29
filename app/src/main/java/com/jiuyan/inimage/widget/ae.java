package com.jiuyan.inimage.widget;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.util.q;

/* compiled from: TextWaterMarkView */
class ae implements e {
    final /* synthetic */ TextWaterMarkView b;

    ae(TextWaterMarkView textWaterMarkView) {
        this.b = textWaterMarkView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean a() {
        q.a(this.b.c, "on input method back");
        this.b.k();
        return true;
    }
}
