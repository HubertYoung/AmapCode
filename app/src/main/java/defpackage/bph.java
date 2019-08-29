package defpackage;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.alipay.sdk.util.h;
import java.util.HashMap;
import java.util.Map;

/* renamed from: bph reason: default package */
/* compiled from: HttpRequest */
public abstract class bph {
    protected int mChannel = 0;
    protected Map<String, String> mHeaders = new HashMap();
    protected volatile boolean mIsCancelled;
    protected int mMethod = 0;
    protected Map<String, String> mParams = new HashMap();
    protected int mPriority = Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
    protected int mRetryTimes = 3;
    protected int mTimeout = HttpConstants.CONNECTION_TIME_OUT;
    protected String mUrl;
    public bpp requestStatistics = new bpp();

    public void setUrl(String str) {
        this.mUrl = str;
        this.requestStatistics.f = this.mUrl;
    }

    public void addHeader(String str, String str2) {
        this.mHeaders.put(str, str2);
    }

    public void addParam(String str, String str2) {
        this.mParams.put(str, str2);
    }

    public void addParams(Map<String, String> map) {
        this.mParams.putAll(map);
    }

    public String getUrl() {
        return this.mUrl;
    }

    public int getMethod() {
        return this.mMethod;
    }

    public Map<String, String> getHeaders() {
        return this.mHeaders;
    }

    public Map<String, String> getParams() {
        return this.mParams;
    }

    public int getRetryTimes() {
        return this.mRetryTimes;
    }

    public void setRetryTimes(int i) {
        this.mRetryTimes = i;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public void setTimeout(int i) {
        this.mTimeout = i;
    }

    public void cancel() {
        this.mIsCancelled = true;
    }

    public boolean isCancelled() {
        return this.mIsCancelled;
    }

    public void setChannel(int i) {
        this.mChannel = i;
    }

    public int getChannel() {
        return this.mChannel;
    }

    public void setPriority(int i) {
        this.mPriority = i;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(this.mUrl);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("method:");
        sb.append(bpu.a(this.mMethod));
        sb.append(",url:");
        sb.append(!TextUtils.isEmpty(getUrl()) ? getUrl() : "");
        sb.append(",channel:");
        sb.append(this.mChannel);
        sb.append(",retryTimes:");
        sb.append(this.mRetryTimes);
        sb.append(",timeout:");
        sb.append(this.mTimeout);
        sb.append(",priority:");
        sb.append(this.mPriority);
        sb.append(",header:");
        sb.append(getHeaders() != null ? getHeaders().toString() : "");
        sb.append(h.d);
        return sb.toString();
    }
}
