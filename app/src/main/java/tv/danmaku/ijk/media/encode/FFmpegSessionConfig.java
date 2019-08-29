package tv.danmaku.ijk.media.encode;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.streammedia.encode.NativeSessionConfig;
import java.io.File;

public class FFmpegSessionConfig extends NativeSessionConfig {
    public static final int CPU_ENCODE_LEVEL_BEAUTY = 3;
    public static final int CPU_ENCODE_LEVEL_HIGH = 0;
    public static final int CPU_ENCODE_LEVEL_LIVE = 4;
    public static final int CPU_ENCODE_LEVEL_LOW = 2;
    public static final int CPU_ENCODE_LEVEL_MID = 1;
    public static final String CRF_18 = "18";
    public static final String CRF_19 = "19";
    public static final String CRF_20 = "20";
    public static final String CRF_21 = "21";
    public static final String CRF_22 = "22";
    public static final String CRF_23 = "23";
    public static final String CRF_24 = "24";
    public static final String CRF_25 = "25";
    public static final String CRF_26 = "26";
    public static final String CRF_27 = "27";
    public static final String CRF_28 = "28";
    public static final String PRESET_FAST = "fast";
    public static final String PRESET_FASTER = "faster";
    public static final String PRESET_MEDIUM = "medium";
    public static final String PRESET_SUPERFAST = "superfast";
    public static final String PRESET_ULTRAFAST = "ultrafast";
    public static final String PRESET_VERYFAST = "veryfast";
    private static final String TAG = "FFmpegSessionConfig";
    public static final int VIDEO_SOFTENCODE_H = 960;
    public static final int VIDEO_SOFTENCODE_W = 544;
    public long audioInitTimeStamp;
    private int mHeight;
    public boolean mLandscape;
    private FFmpegMuxer mMuxer;
    public int mType;
    private String mVideoId;
    private int mWidth;
    public long videoInitTimeStamp;

    public FFmpegSessionConfig() {
        this.mWidth = VIDEO_SOFTENCODE_W;
        this.mHeight = 960;
        this.mType = 0;
        this.audioInitTimeStamp = 0;
        this.videoInitTimeStamp = 0;
        this.mLandscape = false;
        this.mMuxer = new FFmpegMuxer();
        this.mVideoId = String.valueOf(System.currentTimeMillis());
        this.vPublishUrl = new File(CacheContext.get().getDiskCache().genPathByKey(this.mVideoId)).getAbsolutePath();
        Logger.D(TAG, "vPublishUrl: " + this.vPublishUrl, new Object[0]);
    }

    public String getVideoId() {
        return this.mVideoId;
    }

    public FFmpegMuxer getMuxer() {
        return this.mMuxer;
    }

    public static FFmpegSessionConfig create(int type) {
        FFmpegSessionConfig session = new FFmpegSessionConfig();
        session.mType = type;
        Logger.D(TAG, "create FFmpegSessionConfig type: " + type, new Object[0]);
        if (type == 1) {
            session.vRecordWidth = SessionConfig.VIDEO_HARDENCODE_W;
            session.vRecordHeight = SessionConfig.VIDEO_HARDENCODE_H;
            session.vEncode = 1;
            session.aSamplerate = 16000;
            session.aEncode = 1;
            session.timeout = ConfigManager.getInstance().getCommonConfigItem().liveConf.handshakeTimeout;
            session.useAbr = 0;
        } else if (type == 3) {
            session.vRecordWidth = SessionConfig.VIDEO_HARDENCODE_W;
            session.vRecordHeight = SessionConfig.VIDEO_HARDENCODE_H;
            session.aSamplerate = 16000;
            session.aEncode = 1;
            session.aEchoCancel = 0;
            session.omxMode = 2;
            session.useAbr = 0;
        } else {
            session.vRecordWidth = VIDEO_SOFTENCODE_W;
            session.vRecordHeight = 960;
            session.vEncode = 1;
            session.aSamplerate = 16000;
            session.aEncode = 1;
            session.useAbr = ConfigManager.getInstance().getUseAbrSwitch();
        }
        return session;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public boolean isLandscape() {
        return this.mLandscape;
    }

    public void setLandscape(boolean landscape) {
        this.mLandscape = landscape;
        if (landscape) {
            this.mWidth = 960;
            this.mHeight = VIDEO_SOFTENCODE_W;
        } else {
            this.mWidth = VIDEO_SOFTENCODE_W;
            this.mHeight = 960;
        }
        this.vRecordWidth = this.mWidth;
        this.vRecordHeight = this.mHeight;
    }
}
