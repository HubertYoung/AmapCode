package com.autonavi.minimap.offline.auto.protocol;

import com.autonavi.minimap.offline.auto.model.ATJsApkInfo.ATJsApkItem;
import com.autonavi.minimap.offline.koala.impl.KoalaDownloader;

public class ApkDownloader extends KoalaDownloader {
    public void setDownloadData(int i, String str, String str2) {
        super.setDownloadData(i, str, str2);
        ATJsApkItem findApkItem = ApkDownloadPersistence.get().findApkItem(i);
        if (findApkItem != null) {
            this.mTotalBytes = findApkItem.totalBytes;
        }
    }
}
