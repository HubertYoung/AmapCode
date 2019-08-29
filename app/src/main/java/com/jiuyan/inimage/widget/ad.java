package com.jiuyan.inimage.widget;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: TextWaterMarkView */
class ad implements OnEditorActionListener {
    final /* synthetic */ TextWaterMarkView a;

    ad(TextWaterMarkView textWaterMarkView) {
        this.a = textWaterMarkView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 6 && keyEvent.getKeyCode() != 66) {
            return false;
        }
        this.a.k();
        return true;
    }
}
