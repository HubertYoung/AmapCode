package com.alipay.android.phone.mobilecommon.multimedia.video.data;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.BaseInfo;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoDownloadCallback;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;

public class APVideoReq extends BaseInfo implements Cloneable {
    private boolean bSync;
    private Drawable defDrawable;
    private boolean forceVideo;
    private Integer height;
    private APImageDownLoadCallback imageDownloadCallback;
    private String imageMd5;
    private String path;
    private String thumbId;
    private APVideoDownloadCallback videoDownloadCallback;
    private String videoId;
    private String videoMd5;
    private Integer width;

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width2) {
        this.width = width2;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path2) {
        this.path = path2;
        if (TextUtils.isEmpty(path2) || !path2.contains(MergeUtil.SEPARATOR_KV)) {
            this.videoId = path2;
            this.thumbId = null;
            return;
        }
        String[] parts = path2.split("\\|");
        this.videoId = parts[0];
        this.thumbId = parts[1];
    }

    public boolean isForceVideo() {
        return this.forceVideo;
    }

    public void setForceVideo(boolean forceVideo2) {
        this.forceVideo = forceVideo2;
    }

    public Drawable getDefDrawable() {
        return this.defDrawable;
    }

    public void setDefDrawable(Drawable defDrawable2) {
        this.defDrawable = defDrawable2;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height2) {
        this.height = height2;
    }

    public boolean isSync() {
        return this.bSync;
    }

    public void setSync(boolean bSync2) {
        this.bSync = bSync2;
    }

    public APImageDownLoadCallback getImageDownloadCallback() {
        return this.imageDownloadCallback;
    }

    public void setImageDownloadCallback(APImageDownLoadCallback cb) {
        this.imageDownloadCallback = cb;
    }

    public APVideoDownloadCallback getVideoDownloadCallback() {
        return this.videoDownloadCallback;
    }

    public void setVideoDownloadCallback(APVideoDownloadCallback videoDownloadCallback2) {
        this.videoDownloadCallback = videoDownloadCallback2;
    }

    public String getImageMd5() {
        return this.imageMd5;
    }

    public String getVideoMd5() {
        return this.videoMd5;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public String getThumbId() {
        return this.thumbId;
    }

    public APVideoReq clone() {
        try {
            return (APVideoReq) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public void setMd5(String md5) {
        if (TextUtils.isEmpty(md5) || !md5.contains(MergeUtil.SEPARATOR_KV)) {
            this.imageMd5 = null;
            this.videoMd5 = md5;
            return;
        }
        String[] parts = md5.split("\\|");
        this.imageMd5 = parts[1];
        this.videoMd5 = parts[0];
    }
}
