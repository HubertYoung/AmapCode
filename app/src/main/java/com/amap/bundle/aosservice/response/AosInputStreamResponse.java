package com.amap.bundle.aosservice.response;

import java.io.InputStream;

public class AosInputStreamResponse extends AosResponse<InputStream> {
    public /* synthetic */ Object parseResult() {
        return getBodyInputStream();
    }
}
