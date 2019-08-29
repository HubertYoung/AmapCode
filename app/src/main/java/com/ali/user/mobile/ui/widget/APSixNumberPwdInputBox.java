package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APBasePwdInputBox.PWDInputListener2;
import com.ali.user.mobile.ui.widget.keyboard.APSafeEditText;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APSixNumberPwdInputBox extends APBasePwdInputBox {
    public static final int DARK = 17;
    public static final int NORMAL = 16;
    /* access modifiers changed from: private */
    public String a;
    private List<APImageView> b;
    /* access modifiers changed from: private */
    public APEditText c;
    private final Map<Integer, String> d;
    /* access modifiers changed from: private */
    public int e;
    /* access modifiers changed from: private */
    public PWDInputListener2 f;
    /* access modifiers changed from: private */
    public PWDInputListener g;
    private int h;
    private List<View> i;
    /* access modifiers changed from: private */
    public String j;
    /* access modifiers changed from: private */
    public String k;

    public interface PWDInputListener {
        void pwdInputed(int i, Editable editable);
    }

    public APSixNumberPwdInputBox(Context context) {
        super(context);
        this.d = new HashMap();
        this.e = 0;
        this.h = 16;
        this.j = "";
        this.k = "";
        this.a = "";
        a(context);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public APSixNumberPwdInputBox(android.content.Context r4, android.util.AttributeSet r5) {
        /*
            r3 = this;
            r3.<init>(r4, r5)
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r3.d = r0
            r0 = 0
            r3.e = r0
            r0 = 16
            r3.h = r0
            java.lang.String r1 = ""
            r3.j = r1
            java.lang.String r1 = ""
            r3.k = r1
            r3.a(r4)
            r1 = 0
            int[] r2 = com.ali.user.mobile.security.ui.R.styleable.R     // Catch:{ Exception -> 0x003b, all -> 0x0033 }
            android.content.res.TypedArray r4 = r4.obtainStyledAttributes(r5, r2)     // Catch:{ Exception -> 0x003b, all -> 0x0033 }
            int r5 = com.ali.user.mobile.security.ui.R.styleable.S     // Catch:{ Exception -> 0x003c, all -> 0x0031 }
            int r5 = r4.getInt(r5, r0)     // Catch:{ Exception -> 0x003c, all -> 0x0031 }
            r3.h = r5     // Catch:{ Exception -> 0x003c, all -> 0x0031 }
            if (r4 == 0) goto L_0x0042
            r4.recycle()
            return
        L_0x0031:
            r5 = move-exception
            goto L_0x0035
        L_0x0033:
            r5 = move-exception
            r4 = r1
        L_0x0035:
            if (r4 == 0) goto L_0x003a
            r4.recycle()
        L_0x003a:
            throw r5
        L_0x003b:
            r4 = r1
        L_0x003c:
            if (r4 == 0) goto L_0x0042
            r4.recycle()
            return
        L_0x0042:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.ui.widget.APSixNumberPwdInputBox.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    public void setBgtype(int i2) {
        int i3 = 0;
        if (this.h != 17) {
            while (i3 < this.i.size()) {
                if (i3 == 0) {
                    this.i.get(i3).setBackgroundResource(R.drawable.R);
                } else if (i3 == this.i.size() - 1) {
                    this.i.get(i3).setBackgroundResource(R.drawable.V);
                } else {
                    this.i.get(i3).setBackgroundResource(R.drawable.T);
                }
                i3++;
            }
            return;
        }
        while (i3 < this.i.size()) {
            if (i3 == 0) {
                this.i.get(i3).setBackgroundResource(R.drawable.S);
            } else if (i3 == this.i.size() - 1) {
                this.i.get(i3).setBackgroundResource(R.drawable.W);
            } else {
                this.i.get(i3).setBackgroundResource(R.drawable.U);
            }
            i3++;
        }
    }

    private void a(Context context) {
        LayoutInflater.from(context).inflate(R.layout.p, this, true);
        setData(this);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        if (16 != this.h) {
            setBgtype(this.h);
        }
    }

    /* access modifiers changed from: protected */
    public void setData(APLinearLayout aPLinearLayout) {
        APLinearLayout aPLinearLayout2 = (APLinearLayout) aPLinearLayout.findViewById(R.id.aD);
        this.i = new ArrayList();
        this.i.add(aPLinearLayout.findViewById(R.id.aL));
        this.i.add(aPLinearLayout.findViewById(R.id.aM));
        this.i.add(aPLinearLayout.findViewById(R.id.aN));
        this.i.add(aPLinearLayout.findViewById(R.id.aO));
        this.i.add(aPLinearLayout.findViewById(R.id.aP));
        this.i.add(aPLinearLayout.findViewById(R.id.aQ));
        this.b = new ArrayList();
        this.b.add((APImageView) aPLinearLayout.findViewById(R.id.aF));
        this.b.add((APImageView) aPLinearLayout.findViewById(R.id.aG));
        this.b.add((APImageView) aPLinearLayout.findViewById(R.id.aH));
        this.b.add((APImageView) aPLinearLayout.findViewById(R.id.aI));
        this.b.add((APImageView) aPLinearLayout.findViewById(R.id.aJ));
        this.b.add((APImageView) aPLinearLayout.findViewById(R.id.aK));
        this.c = (APEditText) aPLinearLayout.findViewById(R.id.aE);
        if (VERSION.SDK_INT < 11) {
            ((APSafeEditText) this.c).setUseSafePassKeyboard(true, 2);
        }
        aPLinearLayout2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                APSafeEditText aPSafeEditText = (APSafeEditText) APSixNumberPwdInputBox.this.c;
                aPSafeEditText.setOnShowEnableOk(aPSafeEditText.getSafeText().toString().length() == 6);
                View focusedChild = APSixNumberPwdInputBox.this.getFocusedChild();
                if (focusedChild == null) {
                    APSixNumberPwdInputBox.this.c.requestFocus();
                    if (!aPSafeEditText.isAutoShowSafeKeyboard()) {
                        aPSafeEditText.showSafeKeyboard();
                    }
                } else if (!focusedChild.equals(aPSafeEditText)) {
                    aPSafeEditText.requestFocus();
                    if (aPSafeEditText.isFocused()) {
                        aPSafeEditText.showSafeKeyboard();
                    }
                } else {
                    aPSafeEditText.showSafeKeyboard();
                }
            }
        });
        this.c.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                String obj = ((APSafeEditText) APSixNumberPwdInputBox.this.c).getSafeText().toString();
                APSixNumberPwdInputBox.this.j = obj;
                APSixNumberPwdInputBox.this.a = obj;
                APSixNumberPwdInputBox.access$300(APSixNumberPwdInputBox.this, obj.length());
                if (APSixNumberPwdInputBox.this.a.length() == 6) {
                    APSixNumberPwdInputBox.access$400(APSixNumberPwdInputBox.this);
                }
                if (APSixNumberPwdInputBox.this.f != null) {
                    APSixNumberPwdInputBox.this.c.postDelayed(new Runnable() {
                        public void run() {
                            APSixNumberPwdInputBox.this.f;
                            APSixNumberPwdInputBox.this.e;
                            APSixNumberPwdInputBox.this.c.getEditableText();
                        }
                    }, 100);
                }
                if (APSixNumberPwdInputBox.this.g != null) {
                    APSixNumberPwdInputBox.this.c.postDelayed(new Runnable() {
                        public void run() {
                            if (!APSixNumberPwdInputBox.this.j.equals(APSixNumberPwdInputBox.this.k)) {
                                APSixNumberPwdInputBox.this.k = APSixNumberPwdInputBox.this.j;
                                APSixNumberPwdInputBox.this.g.pwdInputed(APSixNumberPwdInputBox.this.e, APSixNumberPwdInputBox.this.c.getEditableText());
                            }
                        }
                    }, 100);
                }
            }
        });
    }

    public APEditText getInputView() {
        return this.c;
    }

    public String getInputValue() {
        return this.a;
    }

    public void addSpwdInputWatcher(TextWatcher textWatcher) {
        this.c.addTextChangedListener(textWatcher);
    }

    public String getInputedPwd(int i2) {
        Map<Integer, String> map = this.d;
        if (i2 == -1) {
            i2 = this.e;
        }
        return map.get(Integer.valueOf(i2));
    }

    public void clearPwd() {
        this.d.clear();
        this.e = 0;
    }

    public void clearPwdByIndex(int i2) {
        this.d.remove(Integer.valueOf(i2));
        this.e = i2 - 1;
    }

    public void clearInput() {
        this.c.setText("");
        this.c.requestFocus();
        showInputPannel(this.c);
    }

    public APEditText getEditText() {
        return this.c;
    }

    public APSafeEditText getSafeEditText() {
        return (APSafeEditText) this.c;
    }

    public void setPwdText(String str) {
        this.c.setText(str);
    }

    public void setPwdInputListener(PWDInputListener2 pWDInputListener2) {
        this.f = pWDInputListener2;
    }

    public void setPwdInputListener(PWDInputListener pWDInputListener) {
        this.g = pWDInputListener;
    }

    static /* synthetic */ void access$300(APSixNumberPwdInputBox aPSixNumberPwdInputBox, int i2) {
        for (int i3 = 0; i3 < aPSixNumberPwdInputBox.b.size(); i3++) {
            if (i3 < i2) {
                aPSixNumberPwdInputBox.b.get(i3).setVisibility(0);
            } else {
                aPSixNumberPwdInputBox.b.get(i3).setVisibility(8);
            }
        }
    }

    static /* synthetic */ void access$400(APSixNumberPwdInputBox aPSixNumberPwdInputBox) {
        Map<Integer, String> map = aPSixNumberPwdInputBox.d;
        int i2 = aPSixNumberPwdInputBox.e + 1;
        aPSixNumberPwdInputBox.e = i2;
        map.put(Integer.valueOf(i2), ((APSafeEditText) aPSixNumberPwdInputBox.c).getSafeText().toString());
    }
}
