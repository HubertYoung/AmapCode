package com.alipay.zoloz.toyger.upload;

public class UploadContent {
    public final byte[] content;
    public final byte[] contentSig;
    public final boolean isUTF8;

    public UploadContent(byte[] bArr, byte[] bArr2, boolean z) {
        this.content = bArr;
        this.contentSig = bArr2;
        this.isUTF8 = z;
    }

    public String toString() {
        return "UploadContent{content=" + (this.content == null ? "null" : "***") + ", contentSig=" + (this.contentSig == null ? "null" : "***") + ", isUTF8=" + this.isUTF8 + '}';
    }
}
