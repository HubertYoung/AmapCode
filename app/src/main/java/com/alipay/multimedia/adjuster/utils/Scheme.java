package com.alipay.multimedia.adjuster.utils;

import com.alipay.mobile.common.share.widget.ResUtils;
import java.util.Locale;

public enum Scheme {
    HTTP("http"),
    HTTPS("https"),
    FILE("file"),
    CONTENT("content"),
    ASSETS("assets"),
    DRAWABLE(ResUtils.DRAWABLE),
    UNKNOWN("");
    
    private String scheme;
    private String uriPrefix;

    private Scheme(String scheme2) {
        this.scheme = scheme2;
        this.uriPrefix = scheme2 + "://";
    }

    public static Scheme ofUri(String uri) {
        Scheme[] values;
        if (uri != null) {
            for (Scheme s : values()) {
                if (s.belongsTo(uri)) {
                    return s;
                }
            }
        }
        return UNKNOWN;
    }

    private boolean belongsTo(String uri) {
        return uri.toLowerCase(Locale.US).startsWith(this.uriPrefix);
    }

    public final String wrap(String path) {
        return this.uriPrefix + path;
    }

    public final String crop(String uri) {
        if (belongsTo(uri)) {
            return uri.substring(this.uriPrefix.length());
        }
        throw new IllegalArgumentException(String.format("URI [%1$s] doesn't have expected scheme [%2$s]", new Object[]{uri, this.scheme}));
    }
}
