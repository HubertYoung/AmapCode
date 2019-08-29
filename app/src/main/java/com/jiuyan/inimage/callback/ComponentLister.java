package com.jiuyan.inimage.callback;

import com.alipay.android.hackbyte.ClassVerifier;

public interface ComponentLister extends IComponentListener<Integer, Integer, Object, Void> {
    public static final int ACTION_ANIMATION_END = 101;
    public static final int ACTION_ANIMATION_START = 100;
    public static final int TYPE_CROPPE = 1;
    public static final int TYPE_ROTATE = 2;
    public static final int TYPE_WATERMARK = 3;
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);
}
