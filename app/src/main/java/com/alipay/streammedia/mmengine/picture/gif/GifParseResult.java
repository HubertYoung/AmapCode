package com.alipay.streammedia.mmengine.picture.gif;

public class GifParseResult {
    private int code;
    private long delay;
    private int frameIndex;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public int getFrameIndex() {
        return this.frameIndex;
    }

    public void setFrameIndex(int frameIndex2) {
        this.frameIndex = frameIndex2;
    }

    public long getDelay() {
        return this.delay;
    }

    public void setDelay(long delay2) {
        this.delay = delay2;
    }
}
