package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.ali.user.mobile.ui.widget.keyboard.APSafeEditText;

public abstract class APBasePwdInputBox extends APLinearLayout {
    protected boolean isWithKeyboard;

    public interface PWDInputListener2 {
    }

    public abstract EditText getEditText();

    public abstract String getInputedPwd(int i);

    public abstract void setPwdInputListener(PWDInputListener2 pWDInputListener2);

    public APBasePwdInputBox(Context context) {
        super(context);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0020  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public APBasePwdInputBox(android.content.Context r3, android.util.AttributeSet r4) {
        /*
            r2 = this;
            r2.<init>(r3, r4)
            r0 = 0
            int[] r1 = com.ali.user.mobile.security.ui.R.styleable.R     // Catch:{ Exception -> 0x0024, all -> 0x001c }
            android.content.res.TypedArray r3 = r3.obtainStyledAttributes(r4, r1)     // Catch:{ Exception -> 0x0024, all -> 0x001c }
            int r4 = com.ali.user.mobile.security.ui.R.styleable.T     // Catch:{ Exception -> 0x0025, all -> 0x001a }
            r0 = 1
            boolean r4 = r3.getBoolean(r4, r0)     // Catch:{ Exception -> 0x0025, all -> 0x001a }
            r2.isWithKeyboard = r4     // Catch:{ Exception -> 0x0025, all -> 0x001a }
            if (r3 == 0) goto L_0x0019
            r3.recycle()
            return
        L_0x0019:
            return
        L_0x001a:
            r4 = move-exception
            goto L_0x001e
        L_0x001c:
            r4 = move-exception
            r3 = r0
        L_0x001e:
            if (r3 == 0) goto L_0x0023
            r3.recycle()
        L_0x0023:
            throw r4
        L_0x0024:
            r3 = r0
        L_0x0025:
            if (r3 == 0) goto L_0x002a
            r3.recycle()
        L_0x002a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.ui.widget.APBasePwdInputBox.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    public void callInputMethod() {
        APEditText aPEditText = (APEditText) getEditText();
        if (aPEditText != null) {
            aPEditText.requestFocus();
            showInputPannel(aPEditText);
        }
    }

    public void hideInputMethod() {
        EditText editText = getEditText();
        if (editText instanceof APSafeEditText) {
            ((APSafeEditText) editText).closeSafeKeyboard();
        }
    }

    /* access modifiers changed from: protected */
    public void showInputPannel(final View view) {
        if (view instanceof APSafeEditText) {
            APSafeEditText aPSafeEditText = (APSafeEditText) view;
            if (aPSafeEditText.isPasswordType()) {
                aPSafeEditText.showSafeKeyboard();
                return;
            }
        }
        view.postDelayed(new Runnable() {
            public void run() {
                ((InputMethodManager) view.getContext().getSystemService("input_method")).showSoftInput(view, 0);
            }
        }, 500);
    }
}
