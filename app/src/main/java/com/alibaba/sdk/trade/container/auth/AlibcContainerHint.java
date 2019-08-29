package com.alibaba.sdk.trade.container.auth;

import com.alibaba.baichuan.android.auth.AlibcAuthHint;

public class AlibcContainerHint {
    public static final int ADD_CART = 1;

    public static String getHintId(int i) {
        return i != 1 ? "" : AlibcAuthHint.WANT_ADD_CART.getHintID();
    }
}
