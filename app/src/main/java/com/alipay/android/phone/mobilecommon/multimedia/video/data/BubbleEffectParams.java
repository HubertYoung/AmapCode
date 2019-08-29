package com.alipay.android.phone.mobilecommon.multimedia.video.data;

import android.graphics.Bitmap;

public class BubbleEffectParams {
    public static final int BUBBLE_TYPE_LEFT = 1;
    public static final int BUBBLE_TYPE_NONE = 0;
    public static final int BUBBLE_TYPE_RIGHT = 2;
    public Bitmap mBubbleShape;
    public int mBubbleType = 0;
    public int mRoundRadius = 0;
    public int mTriangleOffset = 0;

    public BubbleEffectParams(int radius, int offset, int type) {
        this.mRoundRadius = radius;
        this.mTriangleOffset = offset;
        this.mBubbleType = type;
    }

    public String toString() {
        return "BubbleEffectParams{mRoundRadius=" + this.mRoundRadius + ", mTriangleOffset=" + this.mTriangleOffset + ", mBubbleType=" + this.mBubbleType + ", mBubbleShape=" + this.mBubbleShape + '}';
    }
}
