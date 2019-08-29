package com.autonavi.minimap.basemap.traffic.net;

import android.os.Environment;
import com.amap.bundle.blutils.FileUtil;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.CachePolicyCallback;
import com.autonavi.common.Callback.CachePolicyCallback.CachePolicy;
import com.autonavi.common.Callback.d;
import java.io.File;

public abstract class AudioDownloadCallback implements Callback<File>, CachePolicyCallback, d {
    private String url;

    public String getCacheKey() {
        return null;
    }

    public void onCancelled() {
    }

    public void onLoading(long j, long j2) {
    }

    public void onStart() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public CachePolicy getCachePolicy() {
        return CachePolicy.NetworkOnly;
    }

    public final String getSavePath() {
        String substring = this.url.substring(this.url.lastIndexOf("/") + 1);
        StringBuilder sb = new StringBuilder();
        sb.append(agy.a(substring));
        sb.append(".spx");
        String sb2 = sb.toString();
        if (Environment.getExternalStorageState().equals("mounted")) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(Environment.getExternalStorageDirectory());
            sb3.append("/autonavi/audio/");
            return new File(sb3.toString(), sb2).getPath();
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(FileUtil.getFilesDir());
        sb4.append("/audio/");
        sb4.append(sb2);
        return sb4.toString();
    }
}
