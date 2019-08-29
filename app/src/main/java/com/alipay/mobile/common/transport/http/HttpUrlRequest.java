package com.alipay.mobile.common.transport.http;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.RequestMethodUtils;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.transport.utils.ZURLEncodedUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;

public class HttpUrlRequest extends Request {
    public static final byte TASK_STATE_END = 2;
    public static final byte TASK_STATE_INIT = 0;
    public static final byte TASK_STATE_RUNNING = 1;
    private String a;
    public boolean allowNonNet;
    public boolean allowRetry;
    private byte[] b;
    private String c;
    public boolean capture;
    private ArrayList<Header> d;
    private Map<String, String> e;
    private boolean f;
    private boolean g;
    private boolean h;
    private boolean i;
    private String j;
    private HttpForm k;
    private InputStream l;
    private boolean m;
    public long mTimeout;
    private long n;
    protected Thread networkThread;
    private HttpEntity o;
    private HttpUriRequest p;
    private HttpResponse q;
    private boolean r;
    private boolean s;
    private boolean t;
    protected int taskState;
    private String u;
    private boolean v;

    public HttpUrlRequest(HttpUriRequest httpUriRequest) {
        this(httpUriRequest.getURI().toString());
        this.p = httpUriRequest;
        Header[] allHeaders = httpUriRequest.getAllHeaders();
        if (allHeaders != null && allHeaders.length > 0) {
            for (Header header : allHeaders) {
                addHeader(header);
            }
        }
        if (this.p instanceof HttpEntityEnclosingRequestBase) {
            HttpEntityEnclosingRequestBase requestBase = this.p;
            HttpEntity entity = requestBase.getEntity();
            if (entity != null) {
                requestBase.setEntity(new ZNetworkHttpEntityWrapper(entity));
            }
        }
        setRequestMethod(RequestMethodUtils.getMethodByHttpUriRequest(httpUriRequest));
        a(httpUriRequest);
    }

    public HttpUrlRequest(String url) {
        this.h = true;
        this.i = true;
        this.j = "GET";
        this.m = false;
        this.n = 0;
        this.allowRetry = false;
        this.mTimeout = -1;
        this.allowNonNet = false;
        this.r = false;
        this.s = false;
        this.capture = false;
        this.t = false;
        this.v = false;
        this.taskState = 0;
        this.a = ZURLEncodedUtil.urlEncode(url);
        this.d = new ArrayList<>();
        this.e = new HashMap();
        this.c = "application/x-www-form-urlencoded";
    }

    public HttpUrlRequest(String url, byte[] reqData, ArrayList<Header> headers, HashMap<String, String> tags) {
        this.h = true;
        this.i = true;
        this.j = "GET";
        this.m = false;
        this.n = 0;
        this.allowRetry = false;
        this.mTimeout = -1;
        this.allowNonNet = false;
        this.r = false;
        this.s = false;
        this.capture = false;
        this.t = false;
        this.v = false;
        this.taskState = 0;
        this.a = ZURLEncodedUtil.urlEncode(url);
        this.b = reqData;
        if (reqData != null) {
            this.n = (long) reqData.length;
        }
        if (headers == null) {
            this.d = new ArrayList<>();
        } else {
            this.d = headers;
        }
        if (tags == null) {
            this.e = new HashMap(4);
        } else {
            this.e = tags;
        }
        this.c = "application/x-www-form-urlencoded";
    }

    public HttpUrlRequest(String url, HttpForm httpForm, ArrayList<Header> headers, HashMap<String, String> tags) {
        this(url, (byte[]) null, headers, tags);
        this.k = httpForm;
        this.n = httpForm.getContentLength();
    }

    public HttpUrlRequest(String url, HttpEntity httpEntity, ArrayList<Header> headers, HashMap<String, String> tags) {
        this(url, (byte[]) null, headers, tags);
        this.o = httpEntity;
        this.n = httpEntity.getContentLength();
    }

    public HttpUrlRequest(String url, InputStream inputStream, long dataLength, ArrayList<Header> headers, HashMap<String, String> tags) {
        this(url, (byte[]) null, headers, tags);
        this.l = inputStream;
        this.n = dataLength;
    }

    public String getUrl() {
        return this.a;
    }

    public String setUrl(String url) {
        String urlEncode = ZURLEncodedUtil.urlEncode(url);
        this.a = urlEncode;
        return urlEncode;
    }

    public byte[] getReqData() {
        return this.b;
    }

    public void setReqData(byte[] reqData) {
        if (this.l != null) {
            throw new IllegalArgumentException("You have been set inputStream  ， not allowed to set reqData");
        } else if (this.k != null) {
            throw new IllegalArgumentException("You have been set httpForm ， not allowed to set reqData");
        } else {
            this.b = reqData;
            if (reqData != null) {
                this.n = (long) reqData.length;
            }
        }
    }

    public String getContentType() {
        if (this.d == null || this.d.isEmpty()) {
            return this.c;
        }
        Iterator<Header> it = this.d.iterator();
        while (it.hasNext()) {
            Header header = it.next();
            if ("Content-Type".equalsIgnoreCase(header.getName()) && !TextUtils.isEmpty(header.getValue())) {
                this.c = header.getValue();
                return this.c;
            }
        }
        return this.c;
    }

    public void setContentType(String contentType) {
        this.c = contentType;
    }

    public void setHeaders(ArrayList<Header> headers) {
        if (headers != null) {
            this.d = headers;
        }
    }

    public void addHeader(Header header) {
        this.d.add(header);
    }

    public void setHeader(Header header) {
        if (header != null) {
            int i2 = 0;
            while (i2 < this.d.size()) {
                Header current = this.d.get(i2);
                if (current == null || current.getName() == null || !current.getName().equalsIgnoreCase(header.getName())) {
                    i2++;
                } else {
                    LogCatUtil.warn((String) "HttpUrlRequest", "setHeadert. Conflict header , key=[" + header.getName() + "], old_value=[" + current.getValue() + "] , new_value=[" + header.getValue() + "] ");
                    this.d.set(i2, header);
                    return;
                }
            }
            this.d.add(header);
        }
    }

    public void addHeader(String name, String value) {
        this.d.add(new BasicHeader(name, value));
    }

    public ArrayList<Header> getHeaders() {
        return this.d;
    }

    public void setTags(Map<String, String> tags) {
        this.e = tags;
    }

    public void addTags(String key, String value) {
        if (this.e == null) {
            this.e = new HashMap();
        }
        this.e.put(key, value);
    }

    public String getTag(String key) {
        if (this.e == null) {
            return null;
        }
        return this.e.get(key);
    }

    public boolean isResetCookie() {
        return this.g;
    }

    public void setResetCookie(boolean resetCookie) {
        this.g = resetCookie;
    }

    public String getKey() {
        return getUrl() + Integer.toHexString(getReqData().hashCode());
    }

    public String toString() {
        Object[] objArr = new Object[4];
        objArr[0] = getUrl();
        objArr[1] = getHeaders();
        objArr[2] = getTags().toString();
        objArr[3] = this.b == null ? "" : new String(this.b);
        return String.format("Url : %s,HttpHeader: %s, Tags: %s, Body:%s", objArr);
    }

    public Map<String, String> getTags() {
        if (this.e == null || this.e.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        return this.e;
    }

    public int hashCode() {
        int result = 1;
        if (this.e != null && this.e.containsKey(TransportConstants.KEY_REQ_DATA_DIGEST)) {
            result = this.e.get(TransportConstants.KEY_REQ_DATA_DIGEST).hashCode() + 31;
        }
        return (TextUtils.isEmpty(this.a) ? 0 : this.a.hashCode()) + (result * 31);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        HttpUrlRequest other = (HttpUrlRequest) obj;
        if (this.b == null) {
            if (other.b != null) {
                return false;
            }
        } else if (!Arrays.equals(this.b, other.b)) {
            return false;
        }
        if (this.a == null) {
            if (other.a != null) {
                return false;
            }
            return true;
        } else if (!TextUtils.equals(this.a, other.a)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isBgRpc() {
        return this.f;
    }

    public void setBgRpc(boolean mBgRpc) {
        this.f = mBgRpc;
    }

    public boolean isUseEtag() {
        return this.h;
    }

    public void setUseEtag(boolean useEtag) {
        this.h = useEtag;
    }

    public boolean isCompress() {
        return this.i;
    }

    public void setCompress(boolean compress) {
        this.i = compress;
    }

    public boolean isContainerHeader(String headerName) {
        Iterator<Header> it = this.d.iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(headerName, it.next().getName())) {
                return true;
            }
        }
        return false;
    }

    public void setRequestMethod(String requestMethod) {
        this.j = requestMethod;
    }

    public String getRequestMethod() {
        return this.j;
    }

    public HttpForm getHttpForm() {
        return this.k;
    }

    public void setHttpForm(HttpForm httpForm) {
        if (this.l != null) {
            throw new IllegalArgumentException("You have been set inputStream  ， not allowed to set httpForm");
        } else if (this.b != null) {
            throw new IllegalArgumentException("You have been set reqData ， not allowed to set httpForm");
        } else {
            this.k = httpForm;
            if (httpForm != null) {
                this.n = httpForm.getContentLength();
            }
        }
    }

    public void setInputStream(InputStream inputStream) {
        if (this.k != null) {
            throw new IllegalArgumentException("You have been set httpForm ， not allowed to set inputStream");
        } else if (this.b != null) {
            throw new IllegalArgumentException("You have been set mReqData ， not allowed to set inputStream");
        } else {
            this.l = inputStream;
            if (inputStream != null) {
                innerSetDataLength(inputStream);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void innerSetDataLength(InputStream inputStream) {
        try {
            if (this.n <= 0) {
                int available = inputStream.available();
                if (available > 0) {
                    this.n = (long) available;
                }
            }
        } catch (IOException e2) {
            LogCatUtil.error(HttpWorker.TAG, "HttpUrlRequest#setInputStream. available error!", e2);
        }
    }

    public InputStream getInputStream() {
        return this.l;
    }

    public void setUrgentFlag(boolean isUrgent) {
        this.m = isUrgent;
    }

    public boolean getUrgentFlag() {
        return this.m;
    }

    public long getDataLength() {
        return this.n;
    }

    public void setDataLength(long dataLength) {
        this.n = dataLength;
    }

    public HttpEntity getHttpEntity() {
        return this.o;
    }

    public void setHttpEntity(HttpEntity httpEntity) {
        this.o = httpEntity;
        if (httpEntity != null) {
            this.n = httpEntity.getContentLength();
        }
    }

    public HttpUriRequest getHttpUriRequest() {
        return this.p;
    }

    public void setHttpUriRequest(HttpUriRequest httpUriRequest) {
        this.p = httpUriRequest;
    }

    public String getCancelMsg() {
        return this.u;
    }

    public void cancel() {
        super.cancel();
        if (this.p != null) {
            try {
                closeRequestEntity();
                closeResponseStream();
                if (!this.p.isAborted()) {
                    this.p.abort();
                    if (isTaskStateRunning()) {
                        this.networkThread.interrupt();
                        LogCatUtil.info("HttpUrlRequest", "invoke cancel, interrupt thread");
                    }
                    LogCatUtil.info("HttpUrlRequest", "invoke cancel, abort request");
                }
            } catch (Throwable e2) {
                LogCatUtil.warn((String) "HttpUrlRequest", "abort request exception. errMsg=" + e2.toString());
            }
        }
    }

    public void cancel(String cancelMsg) {
        this.u = cancelMsg;
        cancel();
    }

    /* access modifiers changed from: protected */
    public void closeRequestEntity() {
        if (this.p != null && (this.p instanceof HttpEntityEnclosingRequest)) {
            try {
                HttpEntity entity = this.p.getEntity();
                if (entity != null) {
                    InputStream content = entity.getContent();
                    if (content != null) {
                        content.close();
                    }
                }
            } catch (Throwable e2) {
                LogCatUtil.warn((String) "HttpUrlRequest", "closeRequestEntity exception: " + e2.toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void closeResponseStream() {
        if (this.q != null) {
            try {
                HttpEntity entity = this.q.getEntity();
                if (entity != null) {
                    InputStream content = entity.getContent();
                    if (content != null) {
                        content.close();
                    }
                }
            } catch (Throwable e2) {
                LogCatUtil.warn((String) "HttpUrlRequest", "closeResponseStream exception: " + e2.toString());
            }
        }
    }

    public void setHttpResponse(HttpResponse httpResponse) {
        this.q = httpResponse;
    }

    public void setTimeout(long timeout) {
        this.mTimeout = timeout;
    }

    public long getTimeout() {
        return this.mTimeout;
    }

    public boolean isAllowNonNet() {
        return this.allowNonNet;
    }

    public void setAllowNonNet(boolean allowNonNet2) {
        this.allowNonNet = allowNonNet2;
    }

    public boolean isDisableEncrypt() {
        return this.r;
    }

    public void setDisableEncrypt(boolean disableEncrypt) {
        this.r = disableEncrypt;
    }

    public boolean isEnableEncrypt() {
        return this.s;
    }

    public void setEnableEncrypt(boolean enableEncrypt) {
        this.s = enableEncrypt;
    }

    public boolean isCapture() {
        return this.capture;
    }

    public void setCapture(boolean capture2) {
        this.capture = capture2;
    }

    public boolean isUseHttpStdRetryModel() {
        return this.t;
    }

    public void setUseHttpStdRetryModel(boolean useHttpStdRetryModel) {
        this.t = useHttpStdRetryModel;
    }

    public boolean isSwitchLoginRpc() {
        return this.v;
    }

    public void setSwitchLoginRpc(boolean switchLoginRpc) {
        this.v = switchLoginRpc;
    }

    public void setNetworkThread(Thread networkThread2) {
        this.networkThread = networkThread2;
    }

    public int getTaskState() {
        return this.taskState;
    }

    public void setTaskState(int taskState2) {
        this.taskState = taskState2;
    }

    public boolean isTaskStateInit() {
        return this.taskState == 0;
    }

    public boolean isTaskStateRunning() {
        return this.taskState == 1;
    }

    public boolean isTaskStateEnd() {
        return this.taskState == 2;
    }

    private void a(HttpUriRequest httpUriRequest) {
        HttpParams params = httpUriRequest.getParams();
        if (params != null) {
            String bizId = "";
            try {
                bizId = (String) params.getParameter("bizId");
                params.removeParameter("bizId");
            } catch (Throwable e2) {
                LogCatUtil.warn((String) "HttpUrlRequest", "Get bizId from parameter fail. msg: " + e2.toString());
            }
            if (!TextUtils.isEmpty(bizId)) {
                addTags("bizId", bizId);
            }
            try {
                Object targetSpiObj = params.getParameter(TransportConstants.KEY_TARGET_SPI);
                if (targetSpiObj != null) {
                    params.removeParameter(TransportConstants.KEY_TARGET_SPI);
                    if (targetSpiObj instanceof String) {
                        String targetSpi = (String) targetSpiObj;
                        if (!TextUtils.isEmpty(targetSpi)) {
                            addTags(TransportConstants.KEY_TARGET_SPI, targetSpi);
                            return;
                        }
                        return;
                    }
                    LogCatUtil.warn((String) "HttpUrlRequest", "[paramsCopyToTags] Illegal target spi data type: " + targetSpiObj.getClass().getName());
                }
            } catch (Throwable e3) {
                LogCatUtil.error((String) "HttpUrlRequest", "[paramsCopyToTags] Not find target spi param. msg : " + e3.toString());
            }
        }
    }
}
