package com.alipay.mobile.h5container.api;

import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5PageImageManager {
    private static final String TAG = "H5PageImageManager";
    private static H5PageImageManager instance = null;
    /* access modifiers changed from: private */
    public Map<String, List<H5PageImage>> providerMap = new ConcurrentHashMap();

    public static synchronized H5PageImageManager getInstance() {
        H5PageImageManager h5PageImageManager;
        synchronized (H5PageImageManager.class) {
            synchronized (H5PageImageManager.class) {
                if (instance == null) {
                    instance = new H5PageImageManager();
                }
            }
            h5PageImageManager = instance;
        }
        return h5PageImageManager;
    }

    private H5PageImageManager() {
    }

    public synchronized void put(String url, H5PageImage h5PageImage) {
        try {
            if (this.providerMap != null) {
                if (this.providerMap.containsKey(url)) {
                    List list = this.providerMap.get(url);
                    if (list != null) {
                        list.add(h5PageImage);
                    }
                } else {
                    List newList = new ArrayList();
                    newList.add(h5PageImage);
                    this.providerMap.put(url, newList);
                }
            }
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
        }
        return;
    }

    public synchronized void uploadLog(final String url) {
        if (this.providerMap != null && this.providerMap.containsKey(url)) {
            H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                public void run() {
                    List list = (List) H5PageImageManager.this.providerMap.get(url);
                    if (list != null && !list.isEmpty()) {
                        H5PageImage pageImage = (H5PageImage) list.get(0);
                        String param3 = H5PageImageManager.this.getParam(list);
                        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_PAGE_ONLINE_IMAGE").param3().add(param3, null).param4().add("url=" + url + "^appId=" + pageImage.appId + "^version=" + pageImage.version, null));
                        H5PageImageManager.this.providerMap.remove(url);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public String getParam(List<H5PageImage> list) {
        String param = "";
        int size = list.size();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (!param.equals("")) {
                    param = param + MergeUtil.SEPARATOR_KV;
                }
                param = param + "url=" + list.get(i).url + "^statusCode=" + list.get(i).statusCode + "^size=" + list.get(i).size + "^costTime=" + list.get(i).costTime + "^etag=" + list.get(i).etag;
            }
        }
        return param;
    }
}
