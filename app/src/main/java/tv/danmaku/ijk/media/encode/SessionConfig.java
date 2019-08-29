package tv.danmaku.ijk.media.encode;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.OrientationDetector;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoDeviceWrapper;
import com.alipay.zoloz.toyger.bean.Config;
import java.io.File;
import java.util.UUID;

public class SessionConfig {
    public static final int VIDEO_BITRATE_DEFAULT = 1100800;
    public static int VIDEO_HARDENCODE_H = 640;
    public static int VIDEO_HARDENCODE_PRE_H = 848;
    public static int VIDEO_HARDENCODE_PRE_W = Config.HQ_IMAGE_WIDTH;
    public static int VIDEO_HARDENCODE_W = 368;
    public long audioInitTimeStamp = 0;
    private int mAudioBitrate = 16000;
    private int mAudioSamplerate = 44100;
    private FFmpegSessionConfig mFFmpegSessionConfig;
    private int mHeight = VIDEO_HARDENCODE_H;
    private boolean mLandscape = false;
    private AndroidMuxer mMuxer;
    private int mNumAudioChannels = 1;
    private int mOrientation = 0;
    private File mOutputFile;
    public int mType = 0;
    private UUID mUUID = UUID.randomUUID();
    private int mVideoBitrate = VIDEO_BITRATE_DEFAULT;
    private String mVideoId = String.valueOf(System.currentTimeMillis());
    private int mWidth = VIDEO_HARDENCODE_W;
    private String vPublishUrl;
    public long videoInitTimeStamp = 0;

    public SessionConfig(int type) {
        File outputFile = new File(CacheContext.get().getDiskCache().genPathByKey(this.mVideoId));
        this.mType = type;
        if (isLiveConfig()) {
            this.mVideoBitrate = VideoUtils.BITRATE_320;
            this.mFFmpegSessionConfig = FFmpegSessionConfig.create(this.mType);
            if (VideoDeviceWrapper.getLiveConfig().rate > 0) {
                this.mVideoBitrate = VideoDeviceWrapper.getLiveConfig().rate;
            }
            this.mFFmpegSessionConfig.vencHardware = 1;
        } else if (isOMXVideo()) {
            this.mFFmpegSessionConfig = FFmpegSessionConfig.create(this.mType);
        } else {
            this.mMuxer = AndroidMuxer.create(outputFile.getAbsolutePath());
        }
        this.mOutputFile = outputFile;
        this.vPublishUrl = this.mOutputFile.getAbsolutePath();
    }

    public void setOmxMask(int omxMask) {
        if (isOMXVideo() && this.mFFmpegSessionConfig != null) {
            this.mFFmpegSessionConfig.omxMask = omxMask;
        }
    }

    public boolean isOMXVideo() {
        return this.mType == 3;
    }

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

    public FFmpegMuxer getmFFmpegMuxer() {
        if (this.mFFmpegSessionConfig == null) {
            return null;
        }
        return this.mFFmpegSessionConfig.getMuxer();
    }

    public void updateLiveInitTimeStamp() {
        if (isLiveConfig() && getmFFmpegMuxer() != null) {
            if (this.mFFmpegSessionConfig != null) {
                this.mFFmpegSessionConfig.audioInitTimeStamp = this.audioInitTimeStamp;
                this.mFFmpegSessionConfig.videoInitTimeStamp = this.videoInitTimeStamp;
            }
            getmFFmpegMuxer().setAudioInitTimeStamp(Long.valueOf(this.audioInitTimeStamp));
            getmFFmpegMuxer().setVideoInitTimeStamp(Long.valueOf(this.videoInitTimeStamp));
        }
    }

    public int getTotalBitrate() {
        return this.mVideoBitrate + this.mAudioBitrate;
    }

    public int getVideoWidth() {
        return this.mWidth;
    }

    public int getVideoHeight() {
        return this.mHeight;
    }

    public int getVideoBitrate() {
        return this.mVideoBitrate;
    }

    public int getNumAudioChannels() {
        return this.mNumAudioChannels;
    }

    public int getAudioBitrate() {
        return this.mAudioBitrate;
    }

    public int getAudioSamplerate() {
        return this.mAudioSamplerate;
    }

    public void setOrientaion(int orientaion) {
        this.mOrientation = orientaion;
        if (!isOMXVideo()) {
            this.mMuxer.setOrientation(orientaion);
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public boolean isLandscape() {
        return this.mLandscape;
    }

    public void setLandscape(boolean landscape) {
        this.mLandscape = landscape;
        if (landscape) {
            this.mWidth = VIDEO_HARDENCODE_H;
            this.mHeight = VIDEO_HARDENCODE_W;
            return;
        }
        this.mWidth = VIDEO_HARDENCODE_W;
        this.mHeight = VIDEO_HARDENCODE_H;
    }

    public boolean isLiveConfig() {
        return this.mType == 1;
    }

    public String getLiveUrl() {
        return this.vPublishUrl;
    }

    public void setLiveUrl(String publishUrl) {
        if (isLiveConfig()) {
            this.vPublishUrl = publishUrl;
        }
    }

    public boolean checkPublishUrl(String publishUrl) {
        return !TextUtils.isEmpty(publishUrl) && publishUrl.startsWith("rtmp");
    }

    public int initFFmpegMuxer() {
        if (isLiveConfig() && checkPublishUrl(this.vPublishUrl)) {
            if (this.mFFmpegSessionConfig == null) {
                this.mFFmpegSessionConfig = FFmpegSessionConfig.create(this.mType);
            }
            this.mFFmpegSessionConfig.cpu_level = 4;
            this.mFFmpegSessionConfig.vPublishUrl = this.vPublishUrl;
            this.mFFmpegSessionConfig.vPreviewWidth = this.mWidth;
            this.mFFmpegSessionConfig.vPreviewHeight = this.mHeight;
            this.mFFmpegSessionConfig.vEncode = 0;
            int ret = VideoUtils.convertMuxInitToRspCode(this.mFFmpegSessionConfig.getMuxer().init(this.mFFmpegSessionConfig));
            Logger.D("SessionConfig", "initFFmpegMuxer ret=" + ret, new Object[0]);
            return ret;
        } else if (!isOMXVideo() || TextUtils.isEmpty(this.vPublishUrl)) {
            return 0;
        } else {
            if (this.mFFmpegSessionConfig == null) {
                this.mFFmpegSessionConfig = FFmpegSessionConfig.create(this.mType);
            }
            this.mFFmpegSessionConfig.videoBitrate = this.mVideoBitrate;
            this.mFFmpegSessionConfig.vPublishUrl = this.vPublishUrl;
            this.mFFmpegSessionConfig.vPreviewWidth = this.mWidth;
            this.mFFmpegSessionConfig.vPreviewHeight = this.mHeight;
            this.mFFmpegSessionConfig.rotate = OrientationDetector.getInstance(AppUtils.getApplicationContext()).getDevOrientation();
            return this.mFFmpegSessionConfig.getMuxer().init(this.mFFmpegSessionConfig);
        }
    }

    public void uninitFFmpegMuxer() {
        if ((isLiveConfig() || isOMXVideo()) && this.mFFmpegSessionConfig.getMuxer() != null) {
            this.mFFmpegSessionConfig.getMuxer().uninit();
            Logger.D("SessionConfig", "uninitFFmpegMuxer", new Object[0]);
        }
    }

    public FFmpegSessionConfig getFFmpegSessionConfig() {
        return this.mFFmpegSessionConfig;
    }

    public boolean isUseFalconLooks() {
        return 2 == this.mType;
    }

    public void setmVideoBitrate(int videoBitrate) {
        this.mVideoBitrate = videoBitrate;
    }
}
