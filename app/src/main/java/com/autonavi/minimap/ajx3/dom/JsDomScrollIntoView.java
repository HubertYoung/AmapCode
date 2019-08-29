package com.autonavi.minimap.ajx3.dom;

public class JsDomScrollIntoView {
    private final int mHeight;
    private final long mId;
    private final int mLeft;
    private final String mOptions;
    private final int mTop;
    private final int mWidth;

    private native int nativeGetHeight(long j);

    private native int nativeGetLeft(long j);

    private native long nativeGetNodeId(long j);

    private native Object nativeGetOption(long j);

    private native int nativeGetTop(long j);

    private native int nativeGetWidth(long j);

    private native void nativeRelease(long j);

    JsDomScrollIntoView(long j) {
        this.mId = nativeGetNodeId(j);
        this.mLeft = nativeGetLeft(j);
        this.mTop = nativeGetTop(j);
        this.mWidth = nativeGetWidth(j);
        this.mHeight = nativeGetHeight(j);
        Object nativeGetOption = nativeGetOption(j);
        if (nativeGetOption == null) {
            this.mOptions = null;
        } else if (nativeGetOption instanceof String) {
            this.mOptions = (String) nativeGetOption;
        } else {
            this.mOptions = nativeGetOption.toString();
        }
        nativeRelease(j);
    }

    public long getNodeId() {
        return this.mId;
    }

    public int getLeft() {
        return this.mLeft;
    }

    public int getTop() {
        return this.mTop;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public String getOption() {
        return this.mOptions;
    }
}
