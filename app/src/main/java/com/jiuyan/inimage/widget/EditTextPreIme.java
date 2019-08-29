package com.jiuyan.inimage.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import com.alipay.android.hackbyte.ClassVerifier;

public class EditTextPreIme extends EditText {
    private e a;

    public EditTextPreIme(Context context) {
        super(context);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public EditTextPreIme(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EditTextPreIme(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setOnBackPressListener(e eVar) {
        this.a = eVar;
    }

    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 4 || this.a == null || !this.a.a()) {
            return super.dispatchKeyEventPreIme(keyEvent);
        }
        return true;
    }
}
