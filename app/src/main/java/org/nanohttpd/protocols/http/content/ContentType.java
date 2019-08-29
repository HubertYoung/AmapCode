package org.nanohttpd.protocols.http.content;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentType {
    private static final Pattern a = Pattern.compile("[ |\t]*([^/^ ^;^,]+/[^ ^;^,]+)", 2);
    private static final Pattern b = Pattern.compile("[ |\t]*(charset)[ |\t]*=[ |\t]*['|\"]?([^\"^'^;^,]*)['|\"]?", 2);
    private static final Pattern c = Pattern.compile("[ |\t]*(boundary)[ |\t]*=[ |\t]*['|\"]?([^\"^'^;^,]*)['|\"]?", 2);
    private final String d;
    private final String e;
    private final String f;
    private final String g;

    public ContentType(String contentTypeHeader) {
        this.d = contentTypeHeader;
        if (contentTypeHeader != null) {
            this.e = a(contentTypeHeader, a, "", 1);
            this.f = a(contentTypeHeader, b, null, 2);
        } else {
            this.e = "";
            this.f = "UTF-8";
        }
        if ("multipart/form-data".equalsIgnoreCase(this.e)) {
            this.g = a(contentTypeHeader, c, null, 2);
        } else {
            this.g = null;
        }
    }

    private static String a(String contentTypeHeader, Pattern pattern, String defaultValue, int group) {
        Matcher matcher = pattern.matcher(contentTypeHeader);
        return matcher.find() ? matcher.group(group) : defaultValue;
    }

    public String getContentTypeHeader() {
        return this.d;
    }

    public String getContentType() {
        return this.e;
    }

    public String getEncoding() {
        return this.f == null ? "US-ASCII" : this.f;
    }

    public String getBoundary() {
        return this.g;
    }

    public boolean isMultipart() {
        return "multipart/form-data".equalsIgnoreCase(this.e);
    }

    public ContentType tryUTF8() {
        if (this.f == null) {
            return new ContentType(this.d + "; charset=UTF-8");
        }
        return this;
    }
}
