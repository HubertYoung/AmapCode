package com.alipay.mobile.common.transport.http;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.util.ArrayList;
import org.apache.http.HeaderElement;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

public final class Headers {
    public static final String ACCEPT_RANGES = "accept-ranges";
    public static final String CACHE_CONTROL = "cache-control";
    public static final int CONN_CLOSE = 1;
    public static final String CONN_DIRECTIVE = "connection";
    public static final int CONN_KEEP_ALIVE = 2;
    public static final String CONTENT_DISPOSITION = "content-disposition";
    public static final String CONTENT_ENCODING = "content-encoding";
    public static final String CONTENT_LEN = "content-length";
    public static final String CONTENT_TYPE = "content-type";
    public static final String ETAG = "etag";
    public static final String EXPIRES = "expires";
    public static final String LAST_MODIFIED = "last-modified";
    public static final String LOCATION = "location";
    public static final int NO_CONN_TYPE = 0;
    public static final long NO_CONTENT_LENGTH = -1;
    public static final long NO_TRANSFER_ENCODING = 0;
    public static final String PRAGMA = "pragma";
    public static final String PROXY_AUTHENTICATE = "proxy-authenticate";
    public static final String PROXY_CONNECTION = "proxy-connection";
    public static final String REFRESH = "refresh";
    public static final String SET_COOKIE = "set-cookie";
    public static final String TRANSFER_ENCODING = "transfer-encoding";
    public static final String WWW_AUTHENTICATE = "www-authenticate";
    public static final String X_PERMITTED_CROSS_DOMAIN_POLICIES = "x-permitted-cross-domain-policies";
    private static final String[] f = {"transfer-encoding", "content-length", "content-type", "content-encoding", "connection", "location", "proxy-connection", "www-authenticate", "proxy-authenticate", "content-disposition", "accept-ranges", "expires", "cache-control", "last-modified", "etag", "set-cookie", "pragma", "refresh", "x-permitted-cross-domain-policies"};
    private long a = 0;
    private long b = -1;
    private int c = 0;
    private ArrayList<String> d = new ArrayList<>(2);
    private String[] e = new String[19];
    private ArrayList<String> g = new ArrayList<>(4);
    private ArrayList<String> h = new ArrayList<>(4);

    public interface HeaderCallback {
        void header(String str, String str2);
    }

    public final void parseHeader(CharArrayBuffer buffer) {
        int pos = CharArrayBuffers.setLowercaseIndexOf(buffer, 58);
        if (pos != -1) {
            String name = buffer.substringTrimmed(0, pos);
            if (name.length() != 0) {
                int pos2 = pos + 1;
                String val = buffer.substringTrimmed(pos2, buffer.length());
                switch (name.hashCode()) {
                    case -1345594014:
                        if (TextUtils.equals(name, "x-permitted-cross-domain-policies")) {
                            this.e[18] = val;
                            return;
                        }
                        return;
                    case -1309235404:
                        if (TextUtils.equals(name, "expires")) {
                            this.e[11] = val;
                            return;
                        }
                        return;
                    case -1267267485:
                        if (TextUtils.equals(name, "content-disposition")) {
                            this.e[9] = val;
                            return;
                        }
                        return;
                    case -1132779846:
                        if (TextUtils.equals(name, "content-length")) {
                            this.e[1] = val;
                            try {
                                this.b = Long.parseLong(val);
                                return;
                            } catch (NumberFormatException e2) {
                                LogCatUtil.warn((String) HttpWorker.TAG, "HASH_CONTENT_LEN parseLong(" + val + ") exception : " + e2.toString());
                                return;
                            }
                        } else {
                            return;
                        }
                    case -980228804:
                        if (TextUtils.equals(name, "pragma")) {
                            this.e[16] = val;
                            return;
                        }
                        return;
                    case -775651618:
                        if (TextUtils.equals(name, "connection")) {
                            this.e[4] = val;
                            a(buffer, pos2);
                            return;
                        }
                        return;
                    case -301767724:
                        if (TextUtils.equals(name, "proxy-authenticate")) {
                            this.e[8] = val;
                            return;
                        }
                        return;
                    case -243037365:
                        if (TextUtils.equals(name, "www-authenticate")) {
                            this.e[7] = val;
                            return;
                        }
                        return;
                    case -208775662:
                        if (!TextUtils.equals(name, "cache-control")) {
                            return;
                        }
                        if (this.e[12] == null || this.e[12].length() <= 0) {
                            this.e[12] = val;
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        String[] strArr = this.e;
                        strArr[12] = sb.append(strArr[12]).append(',').append(val).toString();
                        return;
                    case 3123477:
                        if (TextUtils.equals(name, "etag")) {
                            this.e[14] = val;
                            return;
                        }
                        return;
                    case 150043680:
                        if (TextUtils.equals(name, "last-modified")) {
                            this.e[13] = val;
                            return;
                        }
                        return;
                    case 285929373:
                        if (TextUtils.equals(name, "proxy-connection")) {
                            this.e[6] = val;
                            a(buffer, pos2);
                            return;
                        }
                        return;
                    case 785670158:
                        if (TextUtils.equals(name, "content-type")) {
                            this.e[2] = val;
                            return;
                        }
                        return;
                    case 1085444827:
                        if (TextUtils.equals(name, "refresh")) {
                            this.e[17] = val;
                            return;
                        }
                        return;
                    case 1237214767:
                        if (TextUtils.equals(name, "set-cookie")) {
                            this.e[15] = val;
                            this.d.add(val);
                            return;
                        }
                        return;
                    case 1274458357:
                        if (TextUtils.equals(name, "transfer-encoding")) {
                            this.e[0] = val;
                            HeaderElement[] encodings = BasicHeaderValueParser.DEFAULT.parseElements(buffer, new ParserCursor(pos2, buffer.length()));
                            int len = encodings.length;
                            if ("identity".equalsIgnoreCase(val) || len <= 0 || !"chunked".equalsIgnoreCase(encodings[len - 1].getName())) {
                                this.a = -1;
                                return;
                            } else {
                                this.a = -2;
                                return;
                            }
                        } else {
                            return;
                        }
                    case 1397189435:
                        if (TextUtils.equals(name, "accept-ranges")) {
                            this.e[10] = val;
                            return;
                        }
                        return;
                    case 1901043637:
                        if (TextUtils.equals(name, "location")) {
                            this.e[5] = val;
                            return;
                        }
                        return;
                    case 2095084583:
                        if (TextUtils.equals(name, "content-encoding")) {
                            this.e[3] = val;
                            return;
                        }
                        return;
                    default:
                        this.g.add(name);
                        this.h.add(val);
                        return;
                }
            }
        }
    }

    public final long getTransferEncoding() {
        return this.a;
    }

    public final long getContentLength() {
        return this.b;
    }

    public final int getConnectionType() {
        return this.c;
    }

    public final String getContentType() {
        return this.e[2];
    }

    public final String getContentEncoding() {
        return this.e[3];
    }

    public final String getLocation() {
        return this.e[5];
    }

    public final String getWwwAuthenticate() {
        return this.e[7];
    }

    public final String getProxyAuthenticate() {
        return this.e[8];
    }

    public final String getContentDisposition() {
        return this.e[9];
    }

    public final String getAcceptRanges() {
        return this.e[10];
    }

    public final String getExpires() {
        return this.e[11];
    }

    public final String getCacheControl() {
        return this.e[12];
    }

    public final String getLastModified() {
        return this.e[13];
    }

    public final String getEtag() {
        return this.e[14];
    }

    public final ArrayList<String> getSetCookie() {
        return this.d;
    }

    public final String getPragma() {
        return this.e[16];
    }

    public final String getRefresh() {
        return this.e[17];
    }

    public final String getXPermittedCrossDomainPolicies() {
        return this.e[18];
    }

    public final void setContentLength(long value) {
        this.b = value;
    }

    public final void setContentType(String value) {
        this.e[2] = value;
    }

    public final void setContentEncoding(String value) {
        this.e[3] = value;
    }

    public final void setLocation(String value) {
        this.e[5] = value;
    }

    public final void setWwwAuthenticate(String value) {
        this.e[7] = value;
    }

    public final void setProxyAuthenticate(String value) {
        this.e[8] = value;
    }

    public final void setContentDisposition(String value) {
        this.e[9] = value;
    }

    public final void setAcceptRanges(String value) {
        this.e[10] = value;
    }

    public final void setExpires(String value) {
        this.e[11] = value;
    }

    public final void setCacheControl(String value) {
        this.e[12] = value;
    }

    public final void setLastModified(String value) {
        this.e[13] = value;
    }

    public final void setEtag(String value) {
        this.e[14] = value;
    }

    public final void setXPermittedCrossDomainPolicies(String value) {
        this.e[18] = value;
    }

    public final void getHeaders(HeaderCallback hcb) {
        for (int i = 0; i < 19; i++) {
            String h2 = this.e[i];
            if (h2 != null) {
                hcb.header(f[i], h2);
            }
        }
        int extraLen = this.g.size();
        for (int i2 = 0; i2 < extraLen; i2++) {
            hcb.header(this.g.get(i2), this.h.get(i2));
        }
    }

    private void a(CharArrayBuffer buffer, int pos) {
        if (CharArrayBuffers.containsIgnoreCaseTrimmed(buffer, pos, "Close")) {
            this.c = 1;
        } else if (CharArrayBuffers.containsIgnoreCaseTrimmed(buffer, pos, "Keep-Alive")) {
            this.c = 2;
        }
    }
}
