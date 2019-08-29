package com.alipay.inside.android.phone.mrpc.core;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
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
    private static final int HASH_ACCEPT_RANGES = 1397189435;
    private static final int HASH_CACHE_CONTROL = -208775662;
    private static final int HASH_CONN_DIRECTIVE = -775651618;
    private static final int HASH_CONTENT_DISPOSITION = -1267267485;
    private static final int HASH_CONTENT_ENCODING = 2095084583;
    private static final int HASH_CONTENT_LEN = -1132779846;
    private static final int HASH_CONTENT_TYPE = 785670158;
    private static final int HASH_ETAG = 3123477;
    private static final int HASH_EXPIRES = -1309235404;
    private static final int HASH_LAST_MODIFIED = 150043680;
    private static final int HASH_LOCATION = 1901043637;
    private static final int HASH_PRAGMA = -980228804;
    private static final int HASH_PROXY_AUTHENTICATE = -301767724;
    private static final int HASH_PROXY_CONNECTION = 285929373;
    private static final int HASH_REFRESH = 1085444827;
    private static final int HASH_SET_COOKIE = 1237214767;
    private static final int HASH_TRANSFER_ENCODING = 1274458357;
    private static final int HASH_WWW_AUTHENTICATE = -243037365;
    private static final int HASH_X_PERMITTED_CROSS_DOMAIN_POLICIES = -1345594014;
    private static final int HEADER_COUNT = 19;
    private static final int IDX_ACCEPT_RANGES = 10;
    private static final int IDX_CACHE_CONTROL = 12;
    private static final int IDX_CONN_DIRECTIVE = 4;
    private static final int IDX_CONTENT_DISPOSITION = 9;
    private static final int IDX_CONTENT_ENCODING = 3;
    private static final int IDX_CONTENT_LEN = 1;
    private static final int IDX_CONTENT_TYPE = 2;
    private static final int IDX_ETAG = 14;
    private static final int IDX_EXPIRES = 11;
    private static final int IDX_LAST_MODIFIED = 13;
    private static final int IDX_LOCATION = 5;
    private static final int IDX_PRAGMA = 16;
    private static final int IDX_PROXY_AUTHENTICATE = 8;
    private static final int IDX_PROXY_CONNECTION = 6;
    private static final int IDX_REFRESH = 17;
    private static final int IDX_SET_COOKIE = 15;
    private static final int IDX_TRANSFER_ENCODING = 0;
    private static final int IDX_WWW_AUTHENTICATE = 7;
    private static final int IDX_X_PERMITTED_CROSS_DOMAIN_POLICIES = 18;
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
    private static final String[] sHeaderNames = {"transfer-encoding", "content-length", "content-type", "content-encoding", "connection", "location", "proxy-connection", "www-authenticate", "proxy-authenticate", "content-disposition", "accept-ranges", "expires", "cache-control", "last-modified", "etag", "set-cookie", "pragma", "refresh", "x-permitted-cross-domain-policies"};
    private int connectionType = 0;
    private long contentLength = -1;
    private ArrayList<String> cookies = new ArrayList<>(2);
    private ArrayList<String> mExtraHeaderNames = new ArrayList<>(4);
    private ArrayList<String> mExtraHeaderValues = new ArrayList<>(4);
    private String[] mHeaders = new String[19];
    private long transferEncoding = 0;

    public interface HeaderCallback {
        void header(String str, String str2);
    }

    public final void parseHeader(CharArrayBuffer charArrayBuffer) {
        int lowercaseIndexOf = CharArrayBuffers.setLowercaseIndexOf(charArrayBuffer, 58);
        if (lowercaseIndexOf != -1) {
            String substringTrimmed = charArrayBuffer.substringTrimmed(0, lowercaseIndexOf);
            if (substringTrimmed.length() != 0) {
                int i = lowercaseIndexOf + 1;
                String substringTrimmed2 = charArrayBuffer.substringTrimmed(i, charArrayBuffer.length());
                switch (substringTrimmed.hashCode()) {
                    case HASH_X_PERMITTED_CROSS_DOMAIN_POLICIES /*-1345594014*/:
                        if (substringTrimmed.equals("x-permitted-cross-domain-policies")) {
                            this.mHeaders[18] = substringTrimmed2;
                            return;
                        }
                        break;
                    case HASH_EXPIRES /*-1309235404*/:
                        if (substringTrimmed.equals("expires")) {
                            this.mHeaders[11] = substringTrimmed2;
                            return;
                        }
                        break;
                    case HASH_CONTENT_DISPOSITION /*-1267267485*/:
                        if (substringTrimmed.equals("content-disposition")) {
                            this.mHeaders[9] = substringTrimmed2;
                            return;
                        }
                        break;
                    case HASH_CONTENT_LEN /*-1132779846*/:
                        if (substringTrimmed.equals("content-length")) {
                            this.mHeaders[1] = substringTrimmed2;
                            try {
                                this.contentLength = Long.parseLong(substringTrimmed2);
                                return;
                            } catch (NumberFormatException e) {
                                LoggerFactory.f().b((String) "rpc", (Throwable) e);
                                return;
                            }
                        }
                        break;
                    case HASH_PRAGMA /*-980228804*/:
                        if (substringTrimmed.equals("pragma")) {
                            this.mHeaders[16] = substringTrimmed2;
                            return;
                        }
                        break;
                    case HASH_CONN_DIRECTIVE /*-775651618*/:
                        if (substringTrimmed.equals("connection")) {
                            this.mHeaders[4] = substringTrimmed2;
                            setConnectionType(charArrayBuffer, i);
                            return;
                        }
                        break;
                    case HASH_PROXY_AUTHENTICATE /*-301767724*/:
                        if (substringTrimmed.equals("proxy-authenticate")) {
                            this.mHeaders[8] = substringTrimmed2;
                            return;
                        }
                        break;
                    case HASH_WWW_AUTHENTICATE /*-243037365*/:
                        if (substringTrimmed.equals("www-authenticate")) {
                            this.mHeaders[7] = substringTrimmed2;
                            return;
                        }
                        break;
                    case HASH_CACHE_CONTROL /*-208775662*/:
                        if (substringTrimmed.equals("cache-control")) {
                            if (this.mHeaders[12] == null || this.mHeaders[12].length() <= 0) {
                                this.mHeaders[12] = substringTrimmed2;
                                return;
                            }
                            StringBuilder sb = new StringBuilder();
                            String[] strArr = this.mHeaders;
                            sb.append(strArr[12]);
                            sb.append(',');
                            sb.append(substringTrimmed2);
                            strArr[12] = sb.toString();
                            return;
                        }
                        break;
                    case HASH_ETAG /*3123477*/:
                        if (substringTrimmed.equals("etag")) {
                            this.mHeaders[14] = substringTrimmed2;
                            return;
                        }
                        break;
                    case HASH_LAST_MODIFIED /*150043680*/:
                        if (substringTrimmed.equals("last-modified")) {
                            this.mHeaders[13] = substringTrimmed2;
                            return;
                        }
                        break;
                    case HASH_PROXY_CONNECTION /*285929373*/:
                        if (substringTrimmed.equals("proxy-connection")) {
                            this.mHeaders[6] = substringTrimmed2;
                            setConnectionType(charArrayBuffer, i);
                            return;
                        }
                        break;
                    case HASH_CONTENT_TYPE /*785670158*/:
                        if (substringTrimmed.equals("content-type")) {
                            this.mHeaders[2] = substringTrimmed2;
                            return;
                        }
                        break;
                    case HASH_REFRESH /*1085444827*/:
                        if (substringTrimmed.equals("refresh")) {
                            this.mHeaders[17] = substringTrimmed2;
                            return;
                        }
                        break;
                    case HASH_SET_COOKIE /*1237214767*/:
                        if (substringTrimmed.equals("set-cookie")) {
                            this.mHeaders[15] = substringTrimmed2;
                            this.cookies.add(substringTrimmed2);
                            return;
                        }
                        break;
                    case HASH_TRANSFER_ENCODING /*1274458357*/:
                        if (substringTrimmed.equals("transfer-encoding")) {
                            this.mHeaders[0] = substringTrimmed2;
                            HeaderElement[] parseElements = BasicHeaderValueParser.DEFAULT.parseElements(charArrayBuffer, new ParserCursor(i, charArrayBuffer.length()));
                            int length = parseElements.length;
                            if ("identity".equalsIgnoreCase(substringTrimmed2) || length <= 0 || !"chunked".equalsIgnoreCase(parseElements[length - 1].getName())) {
                                this.transferEncoding = -1;
                                return;
                            } else {
                                this.transferEncoding = -2;
                                return;
                            }
                        }
                        break;
                    case HASH_ACCEPT_RANGES /*1397189435*/:
                        if (substringTrimmed.equals("accept-ranges")) {
                            this.mHeaders[10] = substringTrimmed2;
                            return;
                        }
                        break;
                    case HASH_LOCATION /*1901043637*/:
                        if (substringTrimmed.equals("location")) {
                            this.mHeaders[5] = substringTrimmed2;
                            return;
                        }
                        break;
                    case HASH_CONTENT_ENCODING /*2095084583*/:
                        if (substringTrimmed.equals("content-encoding")) {
                            this.mHeaders[3] = substringTrimmed2;
                            return;
                        }
                        break;
                    default:
                        this.mExtraHeaderNames.add(substringTrimmed);
                        this.mExtraHeaderValues.add(substringTrimmed2);
                        break;
                }
            }
        }
    }

    public final long getTransferEncoding() {
        return this.transferEncoding;
    }

    public final long getContentLength() {
        return this.contentLength;
    }

    public final int getConnectionType() {
        return this.connectionType;
    }

    public final String getContentType() {
        return this.mHeaders[2];
    }

    public final String getContentEncoding() {
        return this.mHeaders[3];
    }

    public final String getLocation() {
        return this.mHeaders[5];
    }

    public final String getWwwAuthenticate() {
        return this.mHeaders[7];
    }

    public final String getProxyAuthenticate() {
        return this.mHeaders[8];
    }

    public final String getContentDisposition() {
        return this.mHeaders[9];
    }

    public final String getAcceptRanges() {
        return this.mHeaders[10];
    }

    public final String getExpires() {
        return this.mHeaders[11];
    }

    public final String getCacheControl() {
        return this.mHeaders[12];
    }

    public final String getLastModified() {
        return this.mHeaders[13];
    }

    public final String getEtag() {
        return this.mHeaders[14];
    }

    public final ArrayList<String> getSetCookie() {
        return this.cookies;
    }

    public final String getPragma() {
        return this.mHeaders[16];
    }

    public final String getRefresh() {
        return this.mHeaders[17];
    }

    public final String getXPermittedCrossDomainPolicies() {
        return this.mHeaders[18];
    }

    public final void setContentLength(long j) {
        this.contentLength = j;
    }

    public final void setContentType(String str) {
        this.mHeaders[2] = str;
    }

    public final void setContentEncoding(String str) {
        this.mHeaders[3] = str;
    }

    public final void setLocation(String str) {
        this.mHeaders[5] = str;
    }

    public final void setWwwAuthenticate(String str) {
        this.mHeaders[7] = str;
    }

    public final void setProxyAuthenticate(String str) {
        this.mHeaders[8] = str;
    }

    public final void setContentDisposition(String str) {
        this.mHeaders[9] = str;
    }

    public final void setAcceptRanges(String str) {
        this.mHeaders[10] = str;
    }

    public final void setExpires(String str) {
        this.mHeaders[11] = str;
    }

    public final void setCacheControl(String str) {
        this.mHeaders[12] = str;
    }

    public final void setLastModified(String str) {
        this.mHeaders[13] = str;
    }

    public final void setEtag(String str) {
        this.mHeaders[14] = str;
    }

    public final void setXPermittedCrossDomainPolicies(String str) {
        this.mHeaders[18] = str;
    }

    public final void getHeaders(HeaderCallback headerCallback) {
        for (int i = 0; i < 19; i++) {
            String str = this.mHeaders[i];
            if (str != null) {
                headerCallback.header(sHeaderNames[i], str);
            }
        }
        int size = this.mExtraHeaderNames.size();
        for (int i2 = 0; i2 < size; i2++) {
            headerCallback.header(this.mExtraHeaderNames.get(i2), this.mExtraHeaderValues.get(i2));
        }
    }

    private void setConnectionType(CharArrayBuffer charArrayBuffer, int i) {
        if (CharArrayBuffers.containsIgnoreCaseTrimmed(charArrayBuffer, i, "Close")) {
            this.connectionType = 1;
            return;
        }
        if (CharArrayBuffers.containsIgnoreCaseTrimmed(charArrayBuffer, i, "Keep-Alive")) {
            this.connectionType = 2;
        }
    }
}
