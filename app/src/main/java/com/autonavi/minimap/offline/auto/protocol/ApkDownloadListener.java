package com.autonavi.minimap.offline.auto.protocol;

import com.alipay.sdk.util.h;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.minimap.offline.auto.model.ATJsApkInfo.ATJsApkItem;
import com.autonavi.minimap.offline.auto.model.ATJsDownloadApkInfo;
import com.autonavi.minimap.offline.koala.Koala;
import com.autonavi.minimap.offline.koala.exception.KoalaDownloadInvalidException;
import com.autonavi.minimap.offline.koala.exception.KoalaDownloadOutOfSpaceException;
import com.autonavi.minimap.offline.koala.exception.KoalaDownloadRootDirException;
import com.autonavi.minimap.offline.koala.exception.KoalaDownloadSizeException;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadListener;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadProfile;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadProfile.Detail;
import com.autonavi.minimap.offline.map.inter.impl.OfflineNetStatusTracer;
import com.autonavi.minimap.offline.map.inter.impl.OfflineNetStatusTracer.Listener;
import com.autonavi.minimap.offline.service.IAutoOfflineJsCallback;
import java.net.HttpRetryException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.List;

public class ApkDownloadListener implements IKoalaDownloadListener {
    private IAutoOfflineJsCallback mCallback;
    /* access modifiers changed from: private */
    public Koala mKoala;
    private Listener mNetListener;

    static final class a {
        /* access modifiers changed from: private */
        public static ApkDownloadListener a = new ApkDownloadListener();
    }

    public void onAfterAction(int i, Object obj) {
    }

    public void onBeforeAction(int i) {
    }

    public void onBind(int i) {
    }

    public void onBlockComplete(int i, String str) {
    }

    private ApkDownloadListener() {
        this.mNetListener = new Listener() {
            public final void onNetChanged(int i) {
                if (i != 2 && ApkDownloadListener.this.mKoala != null) {
                    ApkDownloadListener.this.mKoala.pauseAll();
                }
            }
        };
    }

    public static ApkDownloadListener get() {
        return a.a;
    }

    public void setKoala(Koala koala) {
        this.mKoala = koala;
    }

    public void setCallback(IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        this.mCallback = iAutoOfflineJsCallback;
    }

    private void executeCallback(String str) {
        if (this.mCallback != null) {
            this.mCallback.call(str);
        }
    }

    private String createErrorJson(int i) {
        StringBuilder sb = new StringBuilder("{\"code\":");
        sb.append(i);
        sb.append(h.d);
        return sb.toString();
    }

    private String toRetJson(int i, int i2) {
        if (this.mKoala == null) {
            return "";
        }
        KoalaDownloadProfile profile = this.mKoala.getProfile(i);
        ATJsDownloadApkInfo aTJsDownloadApkInfo = new ATJsDownloadApkInfo();
        aTJsDownloadApkInfo.ret_type = i2;
        aTJsDownloadApkInfo.item = new ATJsApkItem();
        aTJsDownloadApkInfo.item.id = i;
        aTJsDownloadApkInfo.code = 1;
        if (!profile.isEmpty()) {
            Detail[] details = profile.getDetails();
            ATJsApkItem findApkItem = ApkDownloadPersistence.get().findApkItem(i);
            if (details != null && details.length > 0) {
                Detail detail = details[0];
                aTJsDownloadApkInfo.item.downloadBytes = detail.getDownloadBytes();
                aTJsDownloadApkInfo.item.totalBytes = findApkItem != null ? findApkItem.totalBytes : detail.getTotalBytes();
                aTJsDownloadApkInfo.item.url = detail.getUrl();
                aTJsDownloadApkInfo.item.local_file = detail.getLocalPath();
                aTJsDownloadApkInfo.item.status = profile.getStatus().getValue();
            }
        }
        try {
            return JsonUtil.toString(aTJsDownloadApkInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void onPending(int i) {
        executeCallback(toRetJson(i, 0));
    }

    public void onConnected(int i, String str) {
        OfflineNetStatusTracer.get().addListener(this.mNetListener);
        executeCallback(toRetJson(i, 1));
    }

    public void onProgress(int i, String str, long j, long j2, long j3, long j4) {
        executeCallback(toRetJson(i, 2));
    }

    public void onComplete(int i) {
        OfflineNetStatusTracer.get().removeListener(this.mNetListener);
        executeCallback(toRetJson(i, 3));
        List<ATJsApkItem> list = ApkDownloadPersistence.get().getApkInfo().items;
        if (list != null && list.size() > 0 && this.mKoala != null) {
            for (ATJsApkItem next : list) {
                if (next.id != i) {
                    this.mKoala.remove(next.id);
                }
            }
        }
    }

    private boolean isNetworkException(Throwable th) {
        return (th instanceof SocketException) || (th instanceof SocketTimeoutException) || (th instanceof UnknownHostException) || (th instanceof UnknownServiceException) || (th instanceof HttpRetryException) || (th instanceof URISyntaxException);
    }

    public void onError(int i, Throwable th) {
        OfflineNetStatusTracer.get().removeListener(this.mNetListener);
        if (isNetworkException(th)) {
            executeCallback(createErrorJson(100));
            if (this.mKoala != null) {
                this.mKoala.pause(i);
            }
        } else if (th instanceof KoalaDownloadOutOfSpaceException) {
            executeCallback(createErrorJson(101));
        } else if (th instanceof KoalaDownloadSizeException) {
            executeCallback(createErrorJson(103));
        } else if (th instanceof KoalaDownloadInvalidException) {
            executeCallback(createErrorJson(102));
        } else if (th instanceof KoalaDownloadRootDirException) {
            executeCallback(createErrorJson(104));
        } else {
            executeCallback(createErrorJson(0));
        }
    }

    public void onResume(int i) {
        executeCallback(toRetJson(i, 4));
    }

    public void onPause(int i) {
        OfflineNetStatusTracer.get().removeListener(this.mNetListener);
        executeCallback(toRetJson(i, 5));
    }

    public void onRemove(int i) {
        executeCallback(toRetJson(i, 6));
    }
}
