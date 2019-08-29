package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;

/* compiled from: AUCardMenu */
final class k extends AULinearLayout {
    final /* synthetic */ AUCardMenu a;
    private o b;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public k(AUCardMenu aUCardMenu, Context context) {
        // this.a = aUCardMenu;
        super(context);
        a(context);
    }

    private void a(Context context) {
        View layout = LayoutInflater.from(context).inflate(R.layout.view_btn_combined, this, true);
        this.b = new o(this.a, 0);
        this.b.a = (AUTextView) layout.findViewById(R.id.left_btn);
        this.b.b = (AUTextView) layout.findViewById(R.id.right_btn);
    }

    public final void a(String leftText, String rightText) {
        if (!TextUtils.isEmpty(leftText)) {
            this.b.a.setText(leftText);
            this.b.a.setVisibility(0);
        } else {
            this.b.a.setVisibility(4);
        }
        if (!TextUtils.isEmpty(rightText)) {
            this.b.b.setText(rightText);
            this.b.b.setVisibility(0);
            return;
        }
        this.b.b.setVisibility(4);
    }

    public final o a() {
        return this.b;
    }
}
