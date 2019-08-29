package com.jiuyan.inimage.callback;

import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.d.a;

public interface OnMagicWandClickListener {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    void onMagicWandClick(a aVar);
}
