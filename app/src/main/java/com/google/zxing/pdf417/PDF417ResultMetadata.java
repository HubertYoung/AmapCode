package com.google.zxing.pdf417;

public final class PDF417ResultMetadata {
    private String fileId;
    private boolean lastSegment;
    private int[] optionalData;
    private int segmentIndex;

    public final int getSegmentIndex() {
        return this.segmentIndex;
    }

    public final void setSegmentIndex(int i) {
        this.segmentIndex = i;
    }

    public final String getFileId() {
        return this.fileId;
    }

    public final void setFileId(String str) {
        this.fileId = str;
    }

    public final int[] getOptionalData() {
        return this.optionalData;
    }

    public final void setOptionalData(int[] iArr) {
        this.optionalData = iArr;
    }

    public final boolean isLastSegment() {
        return this.lastSegment;
    }

    public final void setLastSegment(boolean z) {
        this.lastSegment = z;
    }
}
