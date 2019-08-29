package com.jiuyan.inimage.widget;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.util.q;

/* compiled from: TextWaterMarkView */
class ag implements TextWatcher {
    final /* synthetic */ TextWaterMarkView a;

    ag(TextWaterMarkView textWaterMarkView) {
        this.a = textWaterMarkView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void afterTextChanged(Editable editable) {
        String obj = editable.toString();
        q.a(this.a.c, "afterTextChanged " + obj);
        if (TextUtils.isEmpty(obj)) {
            obj = this.a.t;
            this.a.r.setHintTextColor(this.a.d.getSelectedColor());
        }
        if (this.a.q != null) {
            this.a.q.a(obj);
        }
    }
}
