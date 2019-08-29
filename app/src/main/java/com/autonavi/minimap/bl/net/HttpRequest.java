package com.autonavi.minimap.bl.net;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import java.util.HashMap;
import java.util.Map;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class HttpRequest {
    public static final int BODY_RECVTYPE_EACH = 1;
    public static final int BODY_RECVTYPE_ONCE = 0;
    public static final int CACHE_POLICY_CACHE_FIRST = 3;
    public static final int CACHE_POLICY_CACHE_ONLY = 0;
    public static final int CACHE_POLICY_NETWORK_ONLY = 1;
    private static final String FEATURE_REQUEST_CONTENT_COMPRESSION = "flag_request_content_compression";
    public static final int FILE_FORMAT_BODY_STREAM = 0;
    public static final int FILE_FORMAT_MULTIPART_FORM = 1;
    public static final int METHOD_GET = 1;
    public static final int METHOD_HEAD = 2;
    public static final int METHOD_POST = 0;
    public static final int PARAM_FORMAT_MULTIPART_FORM = 3;
    public static final int PARAM_FORMAT_URLENCODED_DEFAULT = 0;
    public static final int PARAM_FORMAT_URLENCODED_FORM = 2;
    public static final int PARAM_FORMAT_URLENCODED_URL = 1;
    public static final int VERSION_1_0 = 0;
    public static final int VERSION_1_1 = 1;
    public static final int VERSION_2_0 = 2;
    private byte[] mBody;
    private int mBodyRecvType = 1;
    private String mCacheKey;
    private int mCachePolicy = 1;
    private Map<String, String> mHeaders = new HashMap();
    private int mHttpVersion = 1;
    private boolean mIsContentCompression = false;
    private int mMethod = 1;
    private int mPriority = 0;
    private int mReqParamFormat = 0;
    private Map<String, String> mReqParams = new HashMap();
    private int mRetryTimes = 3;
    private int mTimeoutMillis = HttpConstants.CONNECTION_TIME_OUT;
    private int mUploadFileFormat = 0;
    private Map<String, String> mUploadFiles = new HashMap();
    private String mUrl;
    private Object mUserData;

    public void setHttpBodyRecvType(int i) {
        this.mBodyRecvType = i;
    }

    public void setUrl(String str) {
        this.mUrl = str;
    }

    public void setMethod(int i) {
        this.mMethod = i;
    }

    public void setTimeout(int i) {
        this.mTimeoutMillis = i;
    }

    public void setRetryTimes(int i) {
        this.mRetryTimes = i;
    }

    public void setHttpVersion(int i) {
        this.mHttpVersion = i;
    }

    public void setBody(byte[] bArr) {
        this.mBody = bArr;
    }

    public void addUploadFile(String str) {
        this.mUploadFiles.put(str, "");
    }

    public void addUploadFile(String str, String str2) {
        this.mUploadFiles.put(str, str2);
    }

    public void setUserData(Object obj) {
        this.mUserData = obj;
    }

    public void setCachePolicy(int i) {
        setCachePolicy(i, "");
    }

    public void setCachePolicy(int i, String str) {
        this.mCachePolicy = i;
        this.mCacheKey = str;
    }

    public void setPriority(int i) {
        this.mPriority = i;
    }

    public void addHeader(String str, String str2) {
        this.mHeaders.put(str, str2);
    }

    public void removeHeader(String str) {
        this.mHeaders.remove(str);
    }

    public void addReqParam(String str, String str2) {
        this.mReqParams.put(str, str2);
    }

    public void setReqParamFormat(int i) {
        this.mReqParamFormat = i;
    }

    public void setUploadFileFormat(int i) {
        this.mUploadFileFormat = i;
    }

    public void setContentCompression(boolean z) {
        this.mIsContentCompression = z;
    }

    public Map<String, String> getReqParams() {
        return this.mReqParams;
    }

    public int getHttpBodyRecvType() {
        return this.mBodyRecvType;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public int getMethod() {
        return this.mMethod;
    }

    public int getTimeout() {
        return this.mTimeoutMillis;
    }

    public int getRetryTimes() {
        return this.mRetryTimes;
    }

    public int getHttpVersion() {
        return this.mHttpVersion;
    }

    public byte[] getBody() {
        return this.mBody;
    }

    public Map<String, String> getUploadFiles() {
        return this.mUploadFiles;
    }

    public Object getUserData() {
        return this.mUserData;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public int getCachePolicy() {
        return this.mCachePolicy;
    }

    public String getCacheKey() {
        return this.mCacheKey;
    }

    public Map<String, String> getHeaders() {
        return this.mHeaders;
    }

    public int getReqParamFormat() {
        return this.mReqParamFormat;
    }

    public int getUploadFileFormat() {
        return this.mUploadFileFormat;
    }

    public boolean isContentCompression() {
        return this.mIsContentCompression;
    }

    /* access modifiers changed from: protected */
    public void setCommand(String str, String str2) {
        if (FEATURE_REQUEST_CONTENT_COMPRESSION.equals(str)) {
            setContentCompression("2".equals(str2));
        }
    }
}
