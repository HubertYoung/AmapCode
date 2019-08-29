package com.alipay.mobile.beehive.api;

import android.content.Context;

public interface EmotionParserExecutor {
    CharSequence parser(Context context, CharSequence charSequence);

    CharSequence parser(Context context, CharSequence charSequence, int i);
}
