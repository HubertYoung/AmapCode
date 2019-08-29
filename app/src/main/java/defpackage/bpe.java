package defpackage;

import android.text.TextUtils;
import com.alipay.sdk.util.h;
import java.io.File;

/* renamed from: bpe reason: default package */
/* compiled from: FileUploadRequest */
public final class bpe extends bph {
    protected File a;
    protected String b;

    public bpe() {
        this.mChannel = 1;
        this.mMethod = 1;
        this.requestStatistics.a = bpu.a(this.mMethod);
    }

    public final void a(String str) {
        this.b = str;
    }

    public final String a() {
        return this.b;
    }

    public final void a(File file) {
        this.a = file;
    }

    public final File b() {
        return this.a;
    }

    public final String toString() {
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
        sb.append(",file:");
        sb.append(this.a != null ? this.a.getAbsolutePath() : "");
        sb.append(h.d);
        return sb.toString();
    }
}
