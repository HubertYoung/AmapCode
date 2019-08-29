package com.alipay.mobile.common.nbnet.biz.util;

import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetHeaderConflictException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetNoResponseException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetProtocolException;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.transport.http.HeaderMap;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.autonavi.link.protocol.http.MultipartUtility;
import java.io.CharArrayWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public class ProtocolUtils {
    public static final byte[] a(Map<String, String> headers, String requestLine) {
        StringBuilder result = new StringBuilder(256);
        result.append(requestLine.trim()).append(MultipartUtility.LINE_FEED);
        result.append(b(headers));
        return result.toString().getBytes("UTF-8");
    }

    public static final byte[] a(Map<String, String> headers) {
        return b(headers).toString().getBytes("UTF-8");
    }

    private static String b(Map<String, String> headers) {
        StringBuilder result = new StringBuilder();
        for (Entry entry : headers.entrySet()) {
            String key = (String) entry.getKey();
            if (!TextUtils.isEmpty(key)) {
                result.append(key).append(": ").append((String) entry.getValue()).append(MultipartUtility.LINE_FEED);
            }
        }
        result.append(MultipartUtility.LINE_FEED);
        return result.toString();
    }

    public static final Map<String, String> a(InputStream inputStream) {
        Map header = new HeaderMap();
        a(inputStream, header);
        while (true) {
            String line = b(inputStream);
            if (line.length() == 0) {
                return header;
            }
            a(line, header);
        }
    }

    private static void a(InputStream inputStream, Map<String, String> header) {
        String statusLine = b(inputStream);
        if (TextUtils.isEmpty(statusLine)) {
            throw new ProtocolException("statusLine may not be null");
        } else if (statusLine.length() < 14) {
            throw new ProtocolException("Unexpected status line: " + statusLine);
        } else if (statusLine.charAt(8) == ' ' && statusLine.charAt(12) == ' ') {
            try {
                String responseCode = statusLine.substring(9, 12);
                String responseMsg = statusLine.substring(13);
                header.put("statusLine", statusLine);
                header.put("responseCode", responseCode);
                header.put("responseMessage", responseMsg);
            } catch (Throwable e) {
                ProtocolException protocolException = new ProtocolException("Unexpected status line: " + statusLine);
                protocolException.initCause(e);
                throw protocolException;
            }
        } else {
            throw new ProtocolException("Unexpected status line: " + statusLine);
        }
    }

    private static String b(InputStream in) {
        StringBuilder lineBuilder = new StringBuilder(80);
        boolean finish = false;
        int i = 0;
        while (true) {
            if (i >= 2048) {
                break;
            }
            int read = in.read();
            if (read == -1) {
                throw new NBNetNoResponseException();
            } else if (((char) read) == 10) {
                finish = true;
                break;
            } else {
                lineBuilder.append((char) read);
                i++;
            }
        }
        if (!finish) {
            throw new NBNetProtocolException("Illegal header, line length > 2048");
        }
        int len = lineBuilder.length();
        if (len > 0 && lineBuilder.charAt(len - 1) == 13) {
            lineBuilder.setLength(len - 1);
        }
        return lineBuilder.toString();
    }

    private static void a(String line, Map<String, String> headers) {
        int index = line.indexOf(":", 1);
        if (index != -1) {
            headers.put(line.substring(0, index).trim(), line.substring(index + 1).trim());
        } else {
            NBNetLogCat.a((String) "ProtocolUtils", "add2Header. Illegal header： " + line);
        }
    }

    public static final String a(Map<String, String> headers, InputStream inputStream) {
        String contentCharSet = c(headers);
        if (TextUtils.isEmpty(contentCharSet)) {
            contentCharSet = "UTF-8";
        }
        int contentLength = NBNetCommonUtil.b(headers.get("Content-Length"));
        if (contentLength < 0) {
            contentLength = 4096;
        }
        Reader reader = new InputStreamReader(inputStream, contentCharSet);
        CharArrayWriter charArrayWriter = new CharArrayWriter(contentLength);
        char[] buff = new char[1024];
        while (true) {
            int len = reader.read(buff);
            if (len == -1) {
                return charArrayWriter.toString();
            }
            charArrayWriter.write(buff, 0, len);
        }
    }

    private static String c(Map<String, String> headers) {
        String[] split;
        if (headers == null) {
            throw new IllegalArgumentException("headers may not be null");
        }
        String contentType = headers.get("content-type");
        if (TextUtils.isEmpty(contentType)) {
            return "";
        }
        if (!contentType.contains("charset")) {
            return "";
        }
        for (String element : contentType.split(";|,")) {
            if (element.indexOf("charset") != -1) {
                int indexOfEqual = element.indexOf("=");
                if (indexOfEqual != -1) {
                    return element.substring(indexOfEqual + 1);
                }
            }
        }
        return "";
    }

    public static final String a(URL url) {
        String host = url.getHost();
        int port = url.getPort();
        if (port <= 0 || port == NBNetCommonUtil.a(url.getProtocol())) {
            return host;
        }
        return host + ":" + port;
    }

    public static final void a(HeaderMap<String, String> srcHeaderMap, HeaderMap<String, String> destHeaderMap) {
        if (srcHeaderMap != null && !srcHeaderMap.isEmpty()) {
            for (Entry srcEntry : srcHeaderMap.entrySet()) {
                if (!destHeaderMap.containsKey(srcEntry.getKey())) {
                    destHeaderMap.put((String) srcEntry.getKey(), srcEntry.getValue());
                } else {
                    String logContent = "Copy header fail. because key conflict, key is " + ((String) srcEntry.getKey());
                    if (MiscUtils.isDebugger(NBNetEnvUtils.a())) {
                        throw new NBNetHeaderConflictException(logContent);
                    }
                    LogCatUtil.warn((String) "ProtocolUtils", logContent);
                }
            }
        }
    }
}
