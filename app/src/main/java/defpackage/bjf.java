package defpackage;

import java.util.List;
import java.util.Map;

/* renamed from: bjf reason: default package */
/* compiled from: DownloadCallback */
public interface bjf {
    void onError(int i, int i2);

    void onFinish(bpk bpk);

    void onProgressUpdate(long j, long j2);

    void onStart(long j, Map<String, List<String>> map, int i);
}
