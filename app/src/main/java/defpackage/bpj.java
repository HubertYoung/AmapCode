package defpackage;

import android.text.TextUtils;
import com.alipay.sdk.util.h;

/* renamed from: bpj reason: default package */
/* compiled from: PostRequest */
public class bpj extends bph {
    protected byte[] mBody;
    protected String mContentType;

    public bpj() {
        this.mMethod = 1;
        this.requestStatistics.a = bpu.a(this.mMethod);
    }

    public byte[] getBody() {
        return this.mBody;
    }

    public void setBody(byte[] bArr) {
        this.mBody = bArr;
    }

    public String getContentType() {
        return this.mContentType;
    }

    public void setContentType(String str) {
        this.mContentType = str;
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
        if (getBody() != null && getBody().length > 0 && "application/x-www-form-urlencoded".equalsIgnoreCase(this.mContentType)) {
            try {
                sb.append(",body:");
                sb.append(new String(getBody()));
            } catch (Throwable unused) {
            }
        }
        sb.append(h.d);
        return sb.toString();
    }
}
