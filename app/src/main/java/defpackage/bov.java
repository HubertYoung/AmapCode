package defpackage;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* renamed from: bov reason: default package */
/* compiled from: HurlResponse */
public final class bov {
    public InputStream a;
    public Map<String, List<String>> b;
    public int c = 0;

    public bov() {
    }

    public bov(InputStream inputStream, int i, Map<String, List<String>> map) {
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream may not be null");
        }
        this.a = inputStream;
        this.c = i;
        this.b = map;
    }
}
