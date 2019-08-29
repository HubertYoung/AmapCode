package com.alipay.streammedia.mmengine.picture.scale;

public enum ScaleFilter {
    UPSCALE_1_5X(0),
    UPSCALE_2X(1),
    UPSCALE_4X(2);
    
    private int config;

    private ScaleFilter(int i) {
        this.config = i;
    }

    /* access modifiers changed from: 0000 */
    public final int config() {
        return this.config;
    }
}
