package com.autonavi.minimap.ajx3.htmcompat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class AjxURLSpan extends ClickableSpan {
    private final String mURL;

    AjxURLSpan(String str) {
        this.mURL = str;
    }

    /* access modifiers changed from: 0000 */
    public String getURL() {
        return this.mURL;
    }

    public void onClick(View view) {
        Uri parse = Uri.parse(getURL());
        Context context = view.getContext();
        Intent intent = new Intent("android.intent.action.VIEW", parse);
        intent.putExtra("com.android.browser.application_id", context.getPackageName());
        try {
            context.startActivity(intent);
        } catch (Exception unused) {
        }
    }

    public void updateDrawState(TextPaint textPaint) {
        textPaint.setColor(-16776961);
        textPaint.setUnderlineText(true);
    }
}
