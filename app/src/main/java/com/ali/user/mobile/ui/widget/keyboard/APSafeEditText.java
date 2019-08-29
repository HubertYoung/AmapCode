package com.ali.user.mobile.ui.widget.keyboard;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.Editable.Factory;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.ali.user.mobile.ui.widget.APEditText;
import java.lang.ref.WeakReference;

public final class APSafeEditText extends APEditText {
    final String TAG = "APSafeEditText";
    /* access modifiers changed from: private */
    public WeakReference<OnTouchListener> a;
    private OnTouchListener b;
    /* access modifiers changed from: private */
    public OnFocusChangeListener c;
    private OnFocusChangeListener d;
    /* access modifiers changed from: private */
    public boolean e = false;
    private boolean f = false;
    private boolean g = true;
    private Dialog h;
    private boolean i = true;
    protected boolean isAutoShowSafeKeyboard = true;
    private boolean j = false;
    private boolean k = false;
    private final String l = "";
    private long m;
    private long n;
    private int o = 0;
    private boolean p = false;
    private boolean q = false;

    public APSafeEditText(Context context) {
        super(context);
        a();
    }

    public APSafeEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public APSafeEditText(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a();
    }

    private void a() {
        if (this.p) {
            setInputType(getInputType());
            return;
        }
        if (this.isAutoShowSafeKeyboard && this.isAutoShowSafeKeyboard) {
            super.setOnFocusChangeListener(new OnFocusChangeListener() {
                public void onFocusChange(View view, boolean z) {
                    if (APSafeEditText.this.isAutoShowSafeKeyboard) {
                        if (z) {
                            APSafeEditText.this.post(new Runnable() {
                                public void run() {
                                    APSafeEditText.showNormalKeyboard(APSafeEditText.this);
                                }
                            });
                        } else {
                            APSafeEditText.hideNormalKeyboard(APSafeEditText.this);
                        }
                    }
                    if (APSafeEditText.this.c != null) {
                        APSafeEditText.this.c.onFocusChange(view, z);
                    }
                }
            });
        }
    }

    public final void setGreatWall(boolean z) {
        this.k = z;
    }

    public final void setAccessibilityProtect(boolean z) {
        "setAccessibilityProtect: ".concat(String.valueOf(z));
        this.q = z;
    }

    public final void sendAccessibilityEvent(int i2) {
        if (!this.q || VERSION.SDK_INT < 16) {
            super.sendAccessibilityEvent(i2);
        }
    }

    public final void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (!this.q || VERSION.SDK_INT < 16) {
            super.sendAccessibilityEventUnchecked(accessibilityEvent);
        }
    }

    public final Editable getText() {
        if (!this.k) {
            return super.getText();
        }
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        Editable editable = null;
        if (stackTrace != null && stackTrace.length > 2) {
            try {
                if (Class.forName(stackTrace[1].getClassName()).isAssignableFrom(getClass())) {
                    editable = super.getText();
                }
            } catch (ClassNotFoundException unused) {
            }
        }
        if (editable != null) {
            return editable;
        }
        String str = "";
        for (int i2 = 0; i2 < super.getText().length(); i2++) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("*");
            str = sb.toString();
        }
        return Factory.getInstance().newEditable(str);
    }

    public final Editable getSafeText() {
        return super.getText();
    }

    @TargetApi(11)
    public final void setInputType(int i2) {
        boolean z;
        int i3 = i2 & 4095;
        boolean z2 = false;
        boolean z3 = i3 == 129;
        if (VERSION.SDK_INT >= 11) {
            boolean z4 = i3 == 225;
            if (i3 == 18) {
                z2 = true;
            }
            z = z2;
            z2 = z4;
        } else {
            z = false;
        }
        if (z3 || z2 || z) {
            setSafeKeyboard();
        }
        super.setInputType(i2);
    }

    public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        super.onTextChanged(charSequence, i2, i3, i4);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.p) {
            return a(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    private boolean a(MotionEvent motionEvent) {
        boolean z = true;
        if (this.e) {
            this.f = true;
            try {
                z = super.onTouchEvent(motionEvent);
            } catch (Exception unused) {
            }
            this.f = false;
            return z;
        }
        try {
            return super.onTouchEvent(motionEvent);
        } catch (Exception unused2) {
            return true;
        }
    }

    public final boolean onCheckIsTextEditor() {
        if (!this.p) {
            return super.onCheckIsTextEditor();
        }
        if (VERSION.SDK_INT >= 11) {
            return super.onCheckIsTextEditor();
        }
        if (this.f) {
            return false;
        }
        return super.onCheckIsTextEditor();
    }

    public final boolean isTextSelectable() {
        if (!this.p) {
            return super.isTextSelectable();
        }
        if (this.f) {
            return true;
        }
        return super.isTextSelectable();
    }

    /* access modifiers changed from: protected */
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.p) {
            this.n = System.currentTimeMillis();
            StringBuilder sb = new StringBuilder("ondetached focusLoseTime:");
            sb.append(this.n);
            l(sb.toString());
            long j2 = this.n - this.m;
            StringBuilder sb2 = new StringBuilder("focusLoseTime:");
            sb2.append(this.n);
            l(sb2.toString());
            StringBuilder sb3 = new StringBuilder("focusGetTime :");
            sb3.append(this.m);
            l(sb3.toString());
            l("timeUse:".concat(String.valueOf(j2)));
            if (j2 > 0 && j2 <= 120000) {
                l("isUseSafeKeyboard:".concat(String.valueOf(this.e)));
                Editable safeText = getSafeText();
                if (safeText == null) {
                    this.o = 0;
                } else {
                    String obj = safeText.toString();
                    if (TextUtils.isEmpty(obj)) {
                        this.o = 0;
                    } else {
                        StringBuilder sb4 = new StringBuilder("focusGetTxtLen:");
                        sb4.append(this.o);
                        l(sb4.toString());
                        int length = obj.length() - this.o;
                        this.o = 0;
                        StringBuilder sb5 = new StringBuilder("pstrLen:");
                        sb5.append(length);
                        sb5.append("pstr:");
                        sb5.append(obj);
                        l(sb5.toString());
                        if (length > 0) {
                            l("aovct:".concat(String.valueOf(((float) j2) / ((float) length))));
                        }
                    }
                }
            }
            closeSafeKeyboard();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:?, code lost:
        r3 = getClass().getMethod("setShowSoftInputOnFocus", new java.lang.Class[]{java.lang.Boolean.TYPE});
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002f, code lost:
        r3 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setSafeKeyboard() {
        /*
            r7 = this;
            r0 = 1
            r7.e = r0
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 11
            if (r1 < r2) goto L_0x003e
            r1 = 0
            r2 = 0
            java.lang.Class r3 = r7.getClass()     // Catch:{ NoSuchMethodException -> 0x001d }
            java.lang.String r4 = "setSoftInputShownOnFocus"
            java.lang.Class[] r5 = new java.lang.Class[r0]     // Catch:{ NoSuchMethodException -> 0x001d }
            java.lang.Class r6 = java.lang.Boolean.TYPE     // Catch:{ NoSuchMethodException -> 0x001d }
            r5[r2] = r6     // Catch:{ NoSuchMethodException -> 0x001d }
            java.lang.reflect.Method r3 = r3.getMethod(r4, r5)     // Catch:{ NoSuchMethodException -> 0x001d }
            goto L_0x0030
        L_0x001d:
            java.lang.Class r3 = r7.getClass()     // Catch:{ NoSuchMethodException -> 0x002f }
            java.lang.String r4 = "setShowSoftInputOnFocus"
            java.lang.Class[] r5 = new java.lang.Class[r0]     // Catch:{ NoSuchMethodException -> 0x002f }
            java.lang.Class r6 = java.lang.Boolean.TYPE     // Catch:{ NoSuchMethodException -> 0x002f }
            r5[r2] = r6     // Catch:{ NoSuchMethodException -> 0x002f }
            java.lang.reflect.Method r3 = r3.getMethod(r4, r5)     // Catch:{ NoSuchMethodException -> 0x002f }
            goto L_0x0030
        L_0x002f:
            r3 = r1
        L_0x0030:
            if (r3 == 0) goto L_0x003e
            r3.setAccessible(r0)     // Catch:{ Exception -> 0x003e }
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x003e }
            java.lang.Boolean r1 = java.lang.Boolean.FALSE     // Catch:{ Exception -> 0x003e }
            r0[r2] = r1     // Catch:{ Exception -> 0x003e }
            r3.invoke(r7, r0)     // Catch:{ Exception -> 0x003e }
        L_0x003e:
            com.ali.user.mobile.ui.widget.keyboard.APSafeEditText$1 r0 = new com.ali.user.mobile.ui.widget.keyboard.APSafeEditText$1
            r0.<init>()
            r7.b = r0
            android.view.View$OnTouchListener r0 = r7.b
            super.setOnTouchListener(r0)
            com.ali.user.mobile.ui.widget.keyboard.APSafeEditText$2 r0 = new com.ali.user.mobile.ui.widget.keyboard.APSafeEditText$2
            r0.<init>()
            r7.d = r0
            android.view.View$OnFocusChangeListener r0 = r7.d
            super.setOnFocusChangeListener(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.ui.widget.keyboard.APSafeEditText.setSafeKeyboard():void");
    }

    public static void showNormalKeyboard(EditText editText) {
        if (editText != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getSystemService("input_method");
            if (!inputMethodManager.showSoftInput(editText, 1)) {
                l("first show fail");
                if (!inputMethodManager.showSoftInput(editText, 2)) {
                    l("second show fail");
                    if (!inputMethodManager.showSoftInput(editText, 0)) {
                        l("thrid show fail");
                    }
                }
            }
        }
    }

    public static void hideNormalKeyboard(EditText editText) {
        if (editText != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getSystemService("input_method");
            if (inputMethodManager != null && !inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 1)) {
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 2);
            }
        }
    }

    public final void setOnTouchListener(OnTouchListener onTouchListener) {
        if (onTouchListener != null) {
            if (this.p) {
                if (this.e && !onTouchListener.equals(this.b)) {
                    this.a = new WeakReference<>(onTouchListener);
                } else {
                    super.setOnTouchListener(onTouchListener);
                }
            } else {
                super.setOnTouchListener(onTouchListener);
            }
        }
    }

    public final void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        if (onFocusChangeListener != null) {
            if (this.p) {
                if (!this.e) {
                    super.setOnFocusChangeListener(onFocusChangeListener);
                } else if (!onFocusChangeListener.equals(this.d)) {
                    this.c = onFocusChangeListener;
                    return;
                }
                return;
            }
            super.setOnFocusChangeListener(onFocusChangeListener);
        }
    }

    public final void showSafeKeyboard() {
        if (!this.p) {
            l("call show normal");
            post(new Runnable() {
                public void run() {
                    APSafeEditText.showNormalKeyboard(APSafeEditText.this);
                }
            });
        } else if (this.e) {
            this.j = true;
            postDelayed(new Runnable() {
                public void run() {
                    if (APSafeEditText.this.hasFocus()) {
                    }
                }
            }, 10);
        }
    }

    static void l(String str) {
        "APKeyboard ".concat(String.valueOf(str));
    }

    public final void closeSafeKeyboard() {
        if (this.e) {
            this.j = false;
        }
    }

    public final boolean isPasswordType() {
        return this.e;
    }

    public final Dialog getDialog() {
        return this.h;
    }

    public final void setDialog(Dialog dialog) {
        this.h = dialog;
    }

    public final boolean isUseSafePassKeyboard() {
        return this.g;
    }

    public final void setUseSafePassKeyboard(boolean z, int i2) {
        if (this.p) {
            this.g = z;
            if (z) {
                setSafeKeyboard();
                return;
            }
            this.e = false;
            super.setOnFocusChangeListener(null);
            this.d = null;
            super.setOnTouchListener(null);
            this.d = null;
        }
    }

    public final void setOKText(String str) {
        if (!this.p) {
            setImeActionLabel(str, getImeActionId());
        }
    }

    public final void setOkEnabled(boolean z) {
        StringBuilder sb = new StringBuilder("isUseSafeKeyboard:");
        sb.append(this.p);
        l(sb.toString());
        if (this.p) {
            if (!this.e) {
            }
        } else if (z) {
            setImeOptions(255);
        } else {
            setImeOptions(getImeActionId());
        }
    }

    public final boolean isShowing() {
        return this.j;
    }

    public final void setShowing(boolean z) {
        this.j = z;
    }

    public final boolean isOnShowEnableOk() {
        return this.i;
    }

    public final void setOnShowEnableOk(boolean z) {
        this.i = z;
    }

    public final boolean isAutoShowSafeKeyboard() {
        return this.isAutoShowSafeKeyboard;
    }

    public final void setAutoShowSafeKeyboard(boolean z) {
        this.isAutoShowSafeKeyboard = z;
    }

    public final OnFocusChangeListener getOnFocusChangeListener() {
        if (this.c != null) {
            return this.c;
        }
        return super.getOnFocusChangeListener();
    }

    static /* synthetic */ void access$200(APSafeEditText aPSafeEditText) {
        aPSafeEditText.m = System.currentTimeMillis();
        Editable safeText = aPSafeEditText.getSafeText();
        if (safeText == null) {
            aPSafeEditText.o = 0;
            return;
        }
        String obj = safeText.toString();
        if (!TextUtils.isEmpty(obj)) {
            aPSafeEditText.o = obj.length();
        }
    }
}
