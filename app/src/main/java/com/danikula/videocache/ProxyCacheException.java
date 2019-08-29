package com.danikula.videocache;

public class ProxyCacheException extends Exception {
    private static final String LIBRARY_VERSION = ". Version: 2.7.0";

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public ProxyCacheException(String str) {
        // StringBuilder sb = new StringBuilder();
        // sb.append(str);
        // sb.append(LIBRARY_VERSION);
        super(sb.toString());
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public ProxyCacheException(String str, Throwable th) {
        // StringBuilder sb = new StringBuilder();
        // sb.append(str);
        // sb.append(LIBRARY_VERSION);
        super(sb.toString(), th);
    }

    public ProxyCacheException(Throwable th) {
        super("No explanation error. Version: 2.7.0", th);
    }
}
