package com.alipay.mobile.common.transport.download;

import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.Header;

public class DownloadRequest extends HttpUrlRequest {
    public static final String OPERATION_TYPE = "download_request";
    private String a;
    private boolean b = false;
    private boolean c = false;

    public DownloadRequest(String url) {
        super(url);
        addTags(TransportConstants.KEY_OPERATION_TYPE, OPERATION_TYPE);
    }

    public DownloadRequest(String url, byte[] reqData, ArrayList<Header> headers) {
        super(url, reqData, headers, (HashMap<String, String>) null);
        addTags(TransportConstants.KEY_OPERATION_TYPE, OPERATION_TYPE);
    }

    public DownloadRequest(String url, String path, byte[] reqData, ArrayList<Header> headers) {
        super(url, reqData, headers, (HashMap<String, String>) null);
        addTags(TransportConstants.KEY_OPERATION_TYPE, OPERATION_TYPE);
        setPath(path);
    }

    public String getPath() {
        return this.a;
    }

    public void setPath(String path) {
        if (path == null) {
            throw new IllegalArgumentException("Not set valid path.");
        }
        this.a = path;
    }

    public boolean isRedownload() {
        return this.b;
    }

    public void setRedownload(boolean redownload) {
        this.b = redownload;
    }

    public boolean isUrgentResource() {
        return this.c;
    }

    public void setUrgentResource(boolean urgentResource) {
        this.c = urgentResource;
    }
}
