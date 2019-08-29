package com.amap.bundle.aosservice.response;

public class AosByteResponse extends AosResponse<byte[]> {
    /* access modifiers changed from: protected */
    /* renamed from: a */
    public byte[] parseResult() {
        return getResponseBodyData();
    }
}
