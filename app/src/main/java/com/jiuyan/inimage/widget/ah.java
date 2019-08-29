package com.jiuyan.inimage.widget;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.util.p;
import com.jiuyan.inimage.util.q;

/* compiled from: TextWaterMarkView */
class ah implements p {
    final /* synthetic */ TextWaterMarkView b;

    ah(TextWaterMarkView textWaterMarkView) {
        this.b = textWaterMarkView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(int i) {
        q.a(this.b.c, "onSoftKeyboardOpened");
    }

    public void a() {
        q.a(this.b.c, "onSoftKeyboardClosed");
        if (!this.b.s) {
            this.b.k();
        }
    }
}
