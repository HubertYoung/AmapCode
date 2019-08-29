package com.ant.multimedia.encode;

import android.text.TextUtils;
import com.alipay.zoloz.toyger.bean.Config;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class SessionConfig {
    protected static final String TAG = "SessionConfig";
    public static final int VIDEO_BITRATE_DEFAULT = 1024000;
    public static int VIDEO_HARDENCODE_H = 640;
    public static int VIDEO_HARDENCODE_PRE_H = 848;
    public static int VIDEO_HARDENCODE_PRE_W = Config.HQ_IMAGE_WIDTH;
    public static int VIDEO_HARDENCODE_W = 368;
    private int a = VIDEO_BITRATE_DEFAULT;
    public long audioInitTimeStamp = 0;
    private int b = 44100;
    private int c = 16000;
    private int d = 1;
    private int e = 0;
    private boolean f = false;
    private List<int[]> g = null;
    public int mHeight = VIDEO_HARDENCODE_H;
    protected AndroidMuxer mMuxer;
    protected File mOutputFile;
    protected UUID mUUID;
    protected String mVideoId;
    public int mWidth = VIDEO_HARDENCODE_W;
    public long videoInitTimeStamp = 0;

    public UUID getUUID() {
        return this.mUUID;
    }

    public File getOutputFile() {
        return this.mOutputFile;
    }

    public String getVideoId() {
        return this.mVideoId;
    }

    public AndroidMuxer getMuxer() {
        return this.mMuxer;
    }

    public int getTotalBitrate() {
        return this.a + this.c;
    }

    public int getVideoWidth() {
        return this.mWidth;
    }

    public int getVideoHeight() {
        return this.mHeight;
    }

    public int getVideoBitrate() {
        return this.a;
    }

    public int getNumAudioChannels() {
        return this.d;
    }

    public int getAudioBitrate() {
        return this.c;
    }

    public int getAudioSamplerate() {
        return this.b;
    }

    public void setOrientaion(int orientaion) {
        this.e = orientaion;
    }

    public void setTrackNum(int num) {
        if (this.mMuxer != null) {
            this.mMuxer.setTrackNum(num);
        }
    }

    public int getOrientation() {
        return this.e;
    }

    public boolean isLandscape() {
        return this.f;
    }

    public void setLandscape(boolean landscape) {
        this.f = landscape;
        if (landscape) {
            this.mWidth = VIDEO_HARDENCODE_H;
            this.mHeight = VIDEO_HARDENCODE_W;
            return;
        }
        this.mWidth = VIDEO_HARDENCODE_W;
        this.mHeight = VIDEO_HARDENCODE_H;
    }

    public boolean checkPublishUrl(String publishUrl) {
        return !TextUtils.isEmpty(publishUrl) && publishUrl.startsWith("rtmp");
    }

    public void setmVideoBitrate(int videoBitrate) {
        this.a = videoBitrate;
    }

    public List<int[]> getSupportedEncodeSize() {
        if (this.g == null) {
            this.g = new ArrayList();
            this.g.add(new int[]{540, 960});
            this.g.add(new int[]{480, 848});
            this.g.add(new int[]{368, 640});
        }
        return this.g;
    }

    public boolean isLiveConfig() {
        return false;
    }
}
