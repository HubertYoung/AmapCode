package com.alipay.mobile.beehive.capture.modle;

import android.graphics.Rect;
import com.alipay.mobile.common.lbs.LBSLocation;
import java.io.Serializable;

public class MediaInfo implements Serializable {
    public static final int MEDIA_TYPE_IMAGE = 0;
    public static final int MEDIA_TYPE_VIDEO = 1;
    public Bounds cropRect;
    public long durationMs;
    public int heightPx;
    public Bounds innerWindowBounds;
    public boolean isTakenByCMD;
    public boolean isTakenByFrontCamera;
    public LBSLocation location;
    public Bounds maskBounds;
    public long mediaFileSize;
    public int mediaType;
    public String path;
    public int rotation;
    public int widthPx;

    public static class Bounds implements Serializable {
        public int bottom;
        public int left;
        public int right;
        public int top;

        public Bounds(Rect rect) {
            this.left = rect.left;
            this.top = rect.top;
            this.right = rect.right;
            this.bottom = rect.bottom;
        }

        public Bounds() {
        }
    }

    public MediaInfo(int mediaType2, String path2, int widthPx2, int heightPx2, int rotation2, long durationMs2, LBSLocation location2) {
        this(mediaType2, path2, widthPx2, heightPx2, rotation2, durationMs2, location2, null, null, null);
    }

    public MediaInfo(int mediaType2, String path2, int widthPx2, int heightPx2, int rotation2, long durationMs2, LBSLocation location2, Bounds maskRect, Bounds windowRect, Bounds cutRect) {
        this.mediaType = mediaType2;
        this.path = path2;
        this.heightPx = heightPx2;
        this.widthPx = widthPx2;
        this.rotation = rotation2;
        this.durationMs = durationMs2;
        this.location = location2;
        this.maskBounds = maskRect;
        this.innerWindowBounds = windowRect;
        this.cropRect = cutRect;
    }

    public MediaInfo() {
    }
}
