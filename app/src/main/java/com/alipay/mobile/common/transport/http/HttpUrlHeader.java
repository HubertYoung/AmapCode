package com.alipay.mobile.common.transport.http;

import android.text.TextUtils;
import com.alipay.sdk.util.h;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.HeaderGroup;

public class HttpUrlHeader implements Serializable {
    protected HeaderGroup headergroup = new HeaderGroup();

    public Header[] getAllHeaders() {
        if (this.headergroup == null) {
            return new Header[0];
        }
        return this.headergroup.getAllHeaders();
    }

    @Deprecated
    public Map<String, String> getHeaders() {
        Header[] allHeaders = this.headergroup.getAllHeaders();
        HeaderMap headMap = new HeaderMap(allHeaders.length);
        for (Header header : allHeaders) {
            if (TextUtils.isEmpty((String) headMap.get(header.getName()))) {
                headMap.put(header.getName(), header.getValue());
            }
        }
        return Collections.unmodifiableMap(headMap);
    }

    public String getHead(String key) {
        Header firstHeader = this.headergroup.getFirstHeader(key);
        if (firstHeader == null) {
            return "";
        }
        return firstHeader.getValue();
    }

    public Header getLastHeader(String name) {
        return this.headergroup.getLastHeader(name);
    }

    public void setHead(String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("Header key may not be null");
        }
        this.headergroup.updateHeader(new BasicHeader(key, value));
    }

    public void addHead(String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("Header key may not be null");
        }
        this.headergroup.addHeader(new BasicHeader(key, value));
    }

    public void removeHeaders(String name) {
        if (name != null) {
            Iterator i = this.headergroup.iterator();
            while (i.hasNext()) {
                if (name.equalsIgnoreCase(((Header) i.next()).getName())) {
                    i.remove();
                }
            }
        }
    }

    public Map<String, List<String>> toMultimap() {
        HeaderMap mapHeaders = new HeaderMap();
        HeaderIterator iterator = this.headergroup.iterator();
        while (iterator.hasNext()) {
            Header header = iterator.nextHeader();
            String headerName = header.getName();
            if (TextUtils.isEmpty(headerName)) {
                headerName = "";
            }
            List headList = (List) mapHeaders.get(headerName);
            if (headList == null) {
                headList = new ArrayList(2);
                mapHeaders.put(headerName, headList);
            }
            headList.add(header.getValue());
        }
        return Collections.unmodifiableMap(mapHeaders);
    }

    public String toString() {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("HttpUrlHeader{headers=");
        HeaderIterator iterator = this.headergroup.iterator();
        while (iterator.hasNext()) {
            Header header = iterator.nextHeader();
            headerBuilder.append(header.getName()).append(":").append(header.getValue()).append(",");
        }
        headerBuilder.append(h.d);
        return headerBuilder.toString();
    }
}
