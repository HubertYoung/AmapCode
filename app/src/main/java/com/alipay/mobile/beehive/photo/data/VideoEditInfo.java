package com.alipay.mobile.beehive.photo.data;

import com.alipay.mobile.beehive.service.PhotoInfo;
import java.io.Serializable;

public class VideoEditInfo implements Serializable {
    public long fileSize;
    public double latitude;
    public double longitude;
    public int mediaType = 2;
    public long modifiedTime;
    public String path;
    public long videoDuration;
    public int videoHeight;
    public int videoWidth;

    public VideoEditInfo(PhotoInfo pi) {
        this.latitude = pi.getLatitude();
        this.longitude = pi.getLongitude();
        this.modifiedTime = pi.getModifiedTime();
        this.mediaType = pi.getMediaType();
        this.videoWidth = pi.videoWidth;
        this.videoHeight = pi.videoHeight;
        this.videoDuration = pi.getVideoDuration();
        this.fileSize = pi.getPhotoSize();
        this.path = pi.getPhotoPath();
    }

    public VideoEditInfo() {
    }

    public static PhotoItem rollback(VideoEditInfo vi) {
        PhotoItem pi = new PhotoItem();
        pi.setPhotoPath(vi.path);
        pi.setVideoDuration(vi.videoDuration);
        pi.setVideoHeight(vi.videoHeight);
        pi.setVideoWidth(vi.videoWidth);
        pi.setPhotoSize(vi.fileSize);
        pi.setMediaType(vi.mediaType);
        pi.setLatitude(vi.latitude);
        pi.setLongitude(vi.longitude);
        pi.setModifiedTime(vi.modifiedTime);
        return pi;
    }
}
