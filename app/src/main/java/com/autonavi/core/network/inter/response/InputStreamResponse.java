package com.autonavi.core.network.inter.response;

import java.io.InputStream;

public class InputStreamResponse extends bpk<InputStream> {
    public /* synthetic */ Object parseResult() {
        return getBodyInputStream();
    }
}
