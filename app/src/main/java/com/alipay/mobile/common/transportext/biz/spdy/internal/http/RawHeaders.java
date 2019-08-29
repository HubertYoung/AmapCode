package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import android.text.TextUtils;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.mwallet.ErrorMsgHelper;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.link.protocol.http.MultipartUtility;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public final class RawHeaders {
    private static final Comparator<String> FIELD_NAME_COMPARATOR = new Comparator<String>() {
        public final int compare(String a, String b) {
            if (TextUtils.equals(a, b)) {
                return 0;
            }
            if (a == null) {
                return -1;
            }
            if (b == null) {
                return 1;
            }
            return String.CASE_INSENSITIVE_ORDER.compare(a, b);
        }
    };
    private int httpMinorVersion = 1;
    private final List<String> namesAndValues = new ArrayList(20);
    private String requestLine;
    private int responseCode = -1;
    private String responseMessage;
    private String statusLine;

    public RawHeaders() {
    }

    public RawHeaders(RawHeaders copyFrom) {
        this.namesAndValues.addAll(copyFrom.namesAndValues);
        this.requestLine = copyFrom.requestLine;
        this.statusLine = copyFrom.statusLine;
        this.httpMinorVersion = copyFrom.httpMinorVersion;
        this.responseCode = copyFrom.responseCode;
        this.responseMessage = copyFrom.responseMessage;
    }

    public final void setRequestLine(String requestLine2) {
        this.requestLine = requestLine2.trim();
    }

    public final void setStatusLine(String statusLine2) {
        if (this.responseMessage != null) {
            throw new IllegalStateException("statusLine is already set");
        }
        boolean hasMessage = statusLine2.length() > 13;
        if (!statusLine2.startsWith("HTTP/1.") || statusLine2.length() < 12 || statusLine2.charAt(8) != ' ' || (hasMessage && statusLine2.charAt(12) != ' ')) {
            throw new ProtocolException(getUnexpStatLineExcepMsg(statusLine2));
        }
        int httpMinorVersion2 = statusLine2.charAt(7) - '0';
        if (httpMinorVersion2 < 0 || httpMinorVersion2 > 9) {
            throw new ProtocolException(getUnexpStatLineExcepMsg(statusLine2));
        }
        try {
            int responseCode2 = Integer.parseInt(statusLine2.substring(9, 12));
            this.responseMessage = hasMessage ? statusLine2.substring(13) : "";
            this.responseCode = responseCode2;
            this.statusLine = statusLine2;
            this.httpMinorVersion = httpMinorVersion2;
        } catch (NumberFormatException e) {
            throw new ProtocolException(getUnexpStatLineExcepMsg(statusLine2));
        }
    }

    public final void addSpdyRequestHeaders(String method, String path, String version, String host, String scheme) {
        add(":method", method);
        add(":scheme", scheme);
        add(":path", path);
        add(":version", version);
        add(":host", host);
    }

    public final String getStatusLine() {
        return this.statusLine;
    }

    public final int getHttpMinorVersion() {
        if (this.httpMinorVersion != -1) {
            return this.httpMinorVersion;
        }
        return 1;
    }

    public final int getResponseCode() {
        return this.responseCode;
    }

    public final String getResponseMessage() {
        return this.responseMessage;
    }

    public final void addLine(String line) {
        int index = line.indexOf(":", 1);
        if (index != -1) {
            addLenient(line.substring(0, index), line.substring(index + 1));
        } else if (line.startsWith(":")) {
            addLenient("", line.substring(1));
        } else {
            addLenient("", line);
        }
    }

    public final void add(String fieldName, String value) {
        if (fieldName == null) {
            throw new IllegalArgumentException("fieldname == null");
        } else if (value == null) {
            throw new IllegalArgumentException("value == null");
        } else if (fieldName.length() != 0 && fieldName.indexOf(0) == -1 && value.indexOf(0) == -1) {
            addLenient(fieldName, value);
        } else {
            throw new IllegalArgumentException("Unexpected header: " + fieldName + ": " + value);
        }
    }

    private void addLenient(String fieldName, String value) {
        this.namesAndValues.add(fieldName);
        this.namesAndValues.add(value.trim());
    }

    public final void removeAll(String fieldName) {
        for (int i = 0; i < this.namesAndValues.size(); i += 2) {
            if (fieldName.equalsIgnoreCase(this.namesAndValues.get(i))) {
                this.namesAndValues.remove(i);
                this.namesAndValues.remove(i);
            }
        }
    }

    public final void addAll(String fieldName, List<String> headerFields) {
        for (String value : headerFields) {
            add(fieldName, value);
        }
    }

    public final void set(String fieldName, String value) {
        removeAll(fieldName);
        add(fieldName, value);
    }

    public final int length() {
        return this.namesAndValues.size() / 2;
    }

    public final String getFieldName(int index) {
        int fieldNameIndex = index * 2;
        if (fieldNameIndex < 0 || fieldNameIndex >= this.namesAndValues.size()) {
            return null;
        }
        return this.namesAndValues.get(fieldNameIndex);
    }

    public final Set<String> names() {
        TreeSet result = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < length(); i++) {
            result.add(getFieldName(i));
        }
        return Collections.unmodifiableSet(result);
    }

    public final String getValue(int index) {
        int valueIndex = (index * 2) + 1;
        if (valueIndex < 0 || valueIndex >= this.namesAndValues.size()) {
            return null;
        }
        return this.namesAndValues.get(valueIndex);
    }

    public final String get(String fieldName) {
        for (int i = this.namesAndValues.size() - 2; i >= 0; i -= 2) {
            if (fieldName.equalsIgnoreCase(this.namesAndValues.get(i))) {
                return this.namesAndValues.get(i + 1);
            }
        }
        return null;
    }

    public final List<String> values(String name) {
        List result = null;
        for (int i = 0; i < length(); i++) {
            if (name.equalsIgnoreCase(getFieldName(i))) {
                if (result == null) {
                    result = new ArrayList(2);
                }
                result.add(getValue(i));
            }
        }
        if (result != null) {
            return Collections.unmodifiableList(result);
        }
        return Collections.emptyList();
    }

    public final RawHeaders getAll(Set<String> fieldNames) {
        RawHeaders result = new RawHeaders();
        for (int i = 0; i < this.namesAndValues.size(); i += 2) {
            String fieldName = this.namesAndValues.get(i);
            if (fieldNames.contains(fieldName)) {
                result.add(fieldName, this.namesAndValues.get(i + 1));
            }
        }
        return result;
    }

    public final byte[] toBytes() {
        StringBuilder result = new StringBuilder(256);
        result.append(this.requestLine).append(MultipartUtility.LINE_FEED);
        for (int i = 0; i < this.namesAndValues.size(); i += 2) {
            result.append(this.namesAndValues.get(i)).append(": ").append(this.namesAndValues.get(i + 1)).append(MultipartUtility.LINE_FEED);
        }
        result.append(MultipartUtility.LINE_FEED);
        return result.toString().getBytes("ISO-8859-1");
    }

    public static RawHeaders fromBytes(InputStream in) {
        RawHeaders headers;
        do {
            headers = new RawHeaders();
            headers.setStatusLine(Util.readAsciiLine(in));
            readHeaders(in, headers);
        } while (headers.getResponseCode() == 100);
        return headers;
    }

    public static void readHeaders(InputStream in, RawHeaders out) {
        while (true) {
            String line = Util.readAsciiLine(in);
            if (line.length() != 0) {
                out.addLine(line);
            } else {
                return;
            }
        }
    }

    public final Map<String, List<String>> toMultimap(boolean response) {
        Map result = new TreeMap(FIELD_NAME_COMPARATOR);
        for (int i = 0; i < this.namesAndValues.size(); i += 2) {
            String fieldName = this.namesAndValues.get(i);
            String value = this.namesAndValues.get(i + 1);
            List allValues = new ArrayList();
            List otherValues = (List) result.get(fieldName);
            if (otherValues != null) {
                allValues.addAll(otherValues);
            }
            allValues.add(value);
            result.put(fieldName, Collections.unmodifiableList(allValues));
        }
        if (response && this.statusLine != null) {
            result.put(null, Collections.unmodifiableList(Collections.singletonList(this.statusLine)));
        } else if (this.requestLine != null) {
            result.put(null, Collections.unmodifiableList(Collections.singletonList(this.requestLine)));
        }
        return Collections.unmodifiableMap(result);
    }

    public static RawHeaders fromMultimap(Map<String, List<String>> map, boolean response) {
        if (!response) {
            throw new UnsupportedOperationException();
        }
        RawHeaders result = new RawHeaders();
        for (Entry entry : map.entrySet()) {
            String fieldName = (String) entry.getKey();
            List<String> values = (List) entry.getValue();
            if (fieldName != null) {
                for (String value : values) {
                    result.addLenient(fieldName, value);
                }
            } else if (!values.isEmpty()) {
                result.setStatusLine((String) values.get(values.size() - 1));
            }
        }
        return result;
    }

    public final List<String> toNameValueBlock() {
        Set names = new HashSet();
        List result = new ArrayList();
        for (int i = 0; i < this.namesAndValues.size(); i += 2) {
            String name = this.namesAndValues.get(i).toLowerCase(Locale.US);
            String value = this.namesAndValues.get(i + 1);
            if (!TextUtils.equals(name, "connection") && !TextUtils.equals(name, "host") && !TextUtils.equals(name, "keep-alive") && !TextUtils.equals(name, "proxy-connection") && !TextUtils.equals(name, "transfer-encoding")) {
                if (names.add(name)) {
                    result.add(name);
                    result.add(value);
                } else {
                    int j = 0;
                    while (true) {
                        if (j >= result.size()) {
                            break;
                        } else if (TextUtils.equals(name, (CharSequence) result.get(j))) {
                            result.set(j + 1, ((String) result.get(j + 1)) + "\u0000" + value);
                            break;
                        } else {
                            j += 2;
                        }
                    }
                }
            }
        }
        return result;
    }

    public static RawHeaders fromNameValueBlock(List<String> nameValueBlock) {
        if (nameValueBlock.size() % 2 != 0) {
            throw new IllegalArgumentException("Unexpected name value block: " + nameValueBlock);
        }
        String status = null;
        String version = null;
        RawHeaders result = new RawHeaders();
        for (int i = 0; i < nameValueBlock.size(); i += 2) {
            String name = nameValueBlock.get(i);
            String values = nameValueBlock.get(i + 1);
            int start = 0;
            while (start < values.length()) {
                int end = values.indexOf(0, start);
                if (end == -1) {
                    end = values.length();
                }
                String value = values.substring(start, end);
                if (TextUtils.equals(":status", name)) {
                    status = value;
                } else if (TextUtils.equals(":version", name)) {
                    version = value;
                } else {
                    result.namesAndValues.add(name);
                    result.namesAndValues.add(value);
                }
                start = end + 1;
            }
        }
        if (status == null) {
            throw new ProtocolException(ErrorMsgHelper.getMessage(ErrorMsgHelper.STATUS_HEADER_NOT_PRESENT));
        } else if (version == null) {
            throw new ProtocolException(ErrorMsgHelper.getMessage(ErrorMsgHelper.VERSION_HEADER_NOT_PRESENT));
        } else {
            result.setStatusLine(version + Token.SEPARATOR + status);
            return result;
        }
    }

    private String getUnexpStatLineExcepMsg(String statusLine2) {
        return String.format(ErrorMsgHelper.getMessage(ErrorMsgHelper.UNEXPECTED_STATUS_LINE), new Object[]{statusLine2});
    }
}
