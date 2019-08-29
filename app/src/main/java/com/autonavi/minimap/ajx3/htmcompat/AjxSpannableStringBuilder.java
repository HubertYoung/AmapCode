package com.autonavi.minimap.ajx3.htmcompat;

import android.text.SpannableStringBuilder;

public class AjxSpannableStringBuilder extends SpannableStringBuilder {
    private boolean withImgSpan;

    public boolean isWithImgSpan() {
        return this.withImgSpan;
    }

    public void setWithImgSpan(boolean z) {
        this.withImgSpan = z;
    }
}
