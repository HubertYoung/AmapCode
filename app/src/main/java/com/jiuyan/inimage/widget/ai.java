package com.jiuyan.inimage.widget;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: TextWaterMarkView */
class ai implements Runnable {
    final /* synthetic */ TextWaterMarkView a;

    ai(TextWaterMarkView textWaterMarkView) {
        this.a = textWaterMarkView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        this.a.setVisibility(0);
        this.a.l();
    }
}
