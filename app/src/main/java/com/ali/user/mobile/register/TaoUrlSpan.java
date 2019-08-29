package com.ali.user.mobile.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.view.View;
import com.ali.user.mobile.h5.AliuserWebViewActivity;
import com.ali.user.mobile.security.ui.R;

public class TaoUrlSpan extends URLSpan {
    Context context;
    private String mSeed = null;

    public TaoUrlSpan(String str) {
        super(str);
    }

    public TaoUrlSpan(String str, String str2) {
        super(str);
        this.mSeed = str2;
    }

    public TaoUrlSpan setContext(Context context2) {
        this.context = context2;
        return this;
    }

    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setUnderlineText(false);
    }

    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("url", getURL());
        bundle.putString("dt", view.getResources().getString(R.string.bQ));
        if (!TextUtils.isEmpty(this.mSeed)) {
            LogUtils.a("UC-ZC-161225-02", this.mSeed);
        }
        Intent intent = new Intent(this.context, AliuserWebViewActivity.class);
        intent.putExtras(bundle);
        this.context.startActivity(intent);
    }
}
