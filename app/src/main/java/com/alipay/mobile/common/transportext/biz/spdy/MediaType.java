package com.alipay.mobile.common.transportext.biz.spdy;

import android.text.TextUtils;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MediaType {
    private static final Pattern PARAMETER = Pattern.compile(";\\s*([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\")");
    private static final String QUOTED = "\"([^\"]*)\"";
    private static final String TOKEN = "([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)";
    private static final Pattern TYPE_SUBTYPE = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
    private final String charset;
    private final String mediaType;
    private final String subtype;
    private final String type;

    private MediaType(String mediaType2, String type2, String subtype2, String charset2) {
        this.mediaType = mediaType2;
        this.type = type2;
        this.subtype = subtype2;
        this.charset = charset2;
    }

    public static MediaType parse(String string) {
        Matcher typeSubtype = TYPE_SUBTYPE.matcher(string);
        if (!typeSubtype.lookingAt()) {
            return null;
        }
        String type2 = typeSubtype.group(1).toLowerCase(Locale.US);
        String subtype2 = typeSubtype.group(2).toLowerCase(Locale.US);
        String charset2 = null;
        Matcher parameter = PARAMETER.matcher(string);
        for (int s = typeSubtype.end(); s < string.length(); s = parameter.end()) {
            parameter.region(s, string.length());
            if (!parameter.lookingAt()) {
                return null;
            }
            String name = parameter.group(1);
            if (name != null && name.equalsIgnoreCase("charset")) {
                if (charset2 != null) {
                    throw new IllegalArgumentException("Multiple charsets: " + string);
                } else if (parameter.group(2) != null) {
                    charset2 = parameter.group(2);
                } else {
                    charset2 = parameter.group(3);
                }
            }
        }
        return new MediaType(string, type2, subtype2, charset2);
    }

    public final String type() {
        return this.type;
    }

    public final String subtype() {
        return this.subtype;
    }

    public final Charset charset() {
        if (this.charset != null) {
            return Charset.forName(this.charset);
        }
        return null;
    }

    public final Charset charset(Charset defaultValue) {
        return this.charset != null ? Charset.forName(this.charset) : defaultValue;
    }

    public final String toString() {
        return this.mediaType;
    }

    public final boolean equals(Object o) {
        return (o instanceof MediaType) && TextUtils.equals(((MediaType) o).mediaType, this.mediaType);
    }

    public final int hashCode() {
        return this.mediaType.hashCode();
    }
}
