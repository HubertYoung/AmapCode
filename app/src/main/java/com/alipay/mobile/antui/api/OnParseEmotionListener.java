package com.alipay.mobile.antui.api;

import android.content.Context;
import android.text.SpannableStringBuilder;

public interface OnParseEmotionListener {
    boolean parser(Context context, SpannableStringBuilder spannableStringBuilder, CharSequence charSequence, int i);
}
