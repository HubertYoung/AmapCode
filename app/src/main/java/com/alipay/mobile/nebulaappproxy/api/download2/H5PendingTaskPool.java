package com.alipay.mobile.nebulaappproxy.api.download2;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class H5PendingTaskPool {
    private final Queue<Pair<H5DownloadRequest, H5DownloadCallback>> a = new ConcurrentLinkedQueue();
    private final Map<String, Pair<H5DownloadRequest, H5DownloadCallback>> b = new HashMap();

    /* access modifiers changed from: 0000 */
    public final void a(H5DownloadRequest request, H5DownloadCallback callback) {
        synchronized (this.a) {
            if (this.b.containsKey(request.getDownloadUrl())) {
                H5Log.d("H5AppDownloadManagerV2", "[H5PendingTaskPool] addToPendingTask already have task " + request.getDownloadUrl());
                return;
            }
            H5Log.d("H5AppDownloadManagerV2", "[H5PendingTaskPool] addToPendingTask current size: " + this.a.size() + Token.SEPARATOR + request.getDownloadUrl());
            Pair pair = new Pair(request, callback);
            this.b.put(request.getDownloadUrl(), pair);
            this.a.add(pair);
        }
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public final Pair<H5DownloadRequest, H5DownloadCallback> a() {
        Pair pair;
        synchronized (this.a) {
            try {
                H5Log.d("H5AppDownloadManagerV2", "[H5PendingTaskPool] popPendingTasks: " + this.a.size());
                pair = this.a.poll();
                if (pair != null) {
                    this.b.remove(((H5DownloadRequest) pair.first).getDownloadUrl());
                    H5Log.d("H5AppDownloadManagerV2", "[H5PendingTaskPool] popPendingTasks: " + pair.first);
                }
            }
        }
        return pair;
    }

    /* access modifiers changed from: 0000 */
    public final void a(String url) {
        synchronized (this.a) {
            Pair pair = this.b.remove(url);
            if (pair != null) {
                H5Log.d("H5AppDownloadManagerV2", "[H5PendingTaskPool] remove task in pool: " + url);
                this.a.remove(pair);
            }
        }
    }
}
