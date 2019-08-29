package defpackage;

import android.text.TextUtils;
import com.alipay.sdk.util.h;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/* renamed from: bpi reason: default package */
/* compiled from: MultipartRequest */
public final class bpi extends bph {
    protected final List<a> a;

    /* renamed from: bpi$a */
    /* compiled from: MultipartRequest */
    public static class a {
        public String a;
        public File b;
        public String c;

        public a(File file, String str) {
            this.b = file;
            this.a = str;
        }

        public a(String str, String str2) {
            this.c = str;
            this.a = str2;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("key:");
            sb.append(this.a != null ? this.a : "");
            sb.append(",value:");
            String str = this.b != null ? this.b.getAbsolutePath() : this.c != null ? this.c : "";
            sb.append(str);
            sb.append(h.d);
            return sb.toString();
        }
    }

    public bpi() {
        this.a = new LinkedList();
        this.mChannel = 1;
        this.mMethod = 1;
        this.requestStatistics.a = bpu.a(this.mMethod);
    }

    public final void a(String str, File file) {
        this.a.add(new a(file, str));
    }

    public final void a(String str, String str2) {
        this.a.add(new a(str2, str));
    }

    public final List<a> a() {
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
        if (this.a.size() > 0) {
            sb.append(",multipart:");
            sb.append("[");
            int size = this.a.size();
            for (int i = 0; i < size; i++) {
                a aVar = this.a.get(i);
                if (aVar != null) {
                    if (i != 0) {
                        sb.append(",");
                    }
                    sb.append(aVar.toString());
                }
            }
            sb.append("]");
        }
        sb.append(h.d);
        return sb.toString();
    }
}
