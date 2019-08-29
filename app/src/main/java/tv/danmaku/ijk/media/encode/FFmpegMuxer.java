package tv.danmaku.ijk.media.encode;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.text.TextUtils;
import android.view.Surface;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.multimedia.img.base.SoLibLoader;
import com.alipay.streammedia.encode.NativeRecordMuxer;
import com.alipay.streammedia.encode.RecordVideoResult;
import com.alipay.streammedia.encode.RecorderInternalCounter;
import com.alipay.streammedia.mmengine.MMNativeException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import tv.danmaku.ijk.media.widget.CameraView;

public final class FFmpegMuxer extends BaseMuxer {
    private static final List<String> CRF_OPTS = new ArrayList<String>() {
        {
            add("18");
            add("19");
            add("20");
            add("21");
            add("22");
            add("23");
            add("24");
            add("25");
            add("26");
            add(FFmpegSessionConfig.CRF_27);
            add("28");
        }
    };
    public static final int ERR_ALLOC_MEM_FAIL = 1;
    public static final int ERR_BROKEN_PIPE = -32;
    public static final int ERR_HTTP_CONN_RESET = -104;
    public static final int ERR_HTTP_CONN_TIMEOUT = -110;
    public static final int ERR_QUEUE_FULL = 2;
    public static final int FRAME_TYPE_CONFIG = 2;
    public static final int FRAME_TYPE_DEFAULT = 0;
    public static final int FRAME_TYPE_KEY = 1;
    public static final int MUXING_INIT_DEFAULT_FAIL = -1;
    public static final int MUXING_INIT_ENCODER_FAIL = 1;
    public static final int MUXING_INIT_NETWORK_FAIL = 2;
    public static final int MUXING_INIT_NOT_READY = 3;
    public static final int MUXING_INIT_SUCCESS = 0;
    private static final List<String> PRESET_OPTS = new ArrayList<String>() {
        {
            add(FFmpegSessionConfig.PRESET_ULTRAFAST);
            add(FFmpegSessionConfig.PRESET_SUPERFAST);
            add(FFmpegSessionConfig.PRESET_VERYFAST);
            add(FFmpegSessionConfig.PRESET_FASTER);
            add(FFmpegSessionConfig.PRESET_FAST);
            add(FFmpegSessionConfig.PRESET_MEDIUM);
            add("slow");
            add("slower");
            add("veryslow");
            add("placebo");
        }
    };
    private static final String TAG = "FFmpegMuxer";
    private long audioInitTimeStamp = 0;
    private boolean bFirst = true;
    private Object mLock = new Object();
    private NativeRecordMuxer mMuxer;
    private AtomicInteger mPutVideoRet = new AtomicInteger(0);
    private VideoRecordListener recordListener = null;
    private int testCount = 0;
    private long videoInitTimeStamp = 0;

    public FFmpegMuxer() {
        try {
            this.mMuxer = new NativeRecordMuxer(new SoLibLoader());
        } catch (MMNativeException e) {
            Logger.E((String) TAG, (Throwable) e, "NativeRecordMuxer load exp code=" + e.getCode(), new Object[0]);
        }
    }

    public final int init(FFmpegSessionConfig cfg) {
        if (AppUtils.isDebug(AppUtils.getApplicationContext()) || AppUtils.isRC()) {
            cfg.recordLog = 1;
        } else {
            cfg.recordLog = 0;
        }
        int result = -1;
        if (cfg.crf != null) {
            if (cfg.crf.length() > 0) {
                if (!CRF_OPTS.contains(cfg.crf)) {
                    Logger.E(TAG, "FFmpegMuxer unCorrect crf: " + cfg.crf + ";use default crf 26", new Object[0]);
                }
            }
            cfg.crf = "26";
        }
        if (cfg.preset != null) {
            if (cfg.preset.length() > 0) {
                if (!PRESET_OPTS.contains(cfg.preset)) {
                    Logger.E(TAG, "FFmpegMuxer unCorrect preset: " + cfg.preset + ";use default preset veryfast", new Object[0]);
                }
            }
            cfg.preset = FFmpegSessionConfig.PRESET_VERYFAST;
        }
        if (!TextUtils.isEmpty(cfg.crf) && !TextUtils.isEmpty(cfg.preset)) {
            cfg.useAbr = 0;
        }
        synchronized (this.mLock) {
            Logger.D(TAG, "Muxing init enter synchronized block", new Object[0]);
            try {
                if (this.mMuxer != null) {
                    result = this.mMuxer.init(cfg);
                }
            } catch (MMNativeException e) {
                Logger.E((String) TAG, (Throwable) e, "Muxing init exp code=" + e.getCode(), new Object[0]);
            }
        }
        if (cfg.recordLog == 1) {
            Logger.D(TAG, "set Muxing to softencoder result=" + result + ";cfg=" + cfg.toString(), new Object[0]);
        } else {
            Logger.D(TAG, "set Muxing to softencoder result=" + result, new Object[0]);
        }
        if (result != 0) {
        }
        return result;
    }

    public final RecordVideoResult uninit() {
        RecordVideoResult result;
        Logger.I(TAG, "Muxing uninit", new Object[0]);
        RecordVideoResult result2 = null;
        synchronized (this.mLock) {
            Logger.I(TAG, "Muxing uninit enter synchronized block", new Object[0]);
            try {
                if (this.mMuxer != null) {
                    result2 = this.mMuxer.uninit();
                }
            } catch (MMNativeException e) {
                Logger.E((String) TAG, (Throwable) e, "Muxing uninit exp code=" + e.getCode(), new Object[0]);
                result = new RecordVideoResult();
                result.setCode(e.getCode());
                result2 = result;
            } catch (Throwable th) {
                th = th;
                RecordVideoResult recordVideoResult = result;
                throw th;
            }
            Logger.I(TAG, "Muxing uninit end", new Object[0]);
            return result2;
        }
    }

    public final void setVideoRecordListener(VideoRecordListener listener) {
        this.recordListener = listener;
    }

    public final int putAudio(byte[] data, int size, boolean mute, long pts) {
        int mute_flag;
        if (mute) {
            mute_flag = 1;
        } else {
            mute_flag = 0;
        }
        try {
            if (this.mMuxer != null) {
                return this.mMuxer.putAudioData(data, size, mute_flag, pts);
            }
        } catch (Throwable e) {
            Logger.E((String) TAG, e, (String) "putAudioData exp", new Object[0]);
        }
        return -1;
    }

    public final int putVideoHardware(byte[] data, int size, long pts, int frameType) {
        try {
            if (this.mMuxer != null) {
                return this.mMuxer.putVideoDataHardware(data, size, pts, frameType);
            }
        } catch (Throwable e) {
            Logger.E((String) TAG, e, (String) "putAudioData exp", new Object[0]);
        }
        return -1;
    }

    public final int putVideo(byte[] data, int size, long pts, int rotation, boolean mirror) {
        try {
            if (this.mMuxer != null) {
                return this.mMuxer.putVideoData(data, size, pts, rotation, mirror ? 1 : 0);
            }
        } catch (Throwable e) {
            Logger.E((String) TAG, e, (String) "putAudioData exp", new Object[0]);
        }
        return -1;
    }

    public final void setAudioInitTimeStamp(Long audioInitTimeStamp2) {
        this.audioInitTimeStamp = audioInitTimeStamp2.longValue();
    }

    public final void setVideoInitTimeStamp(Long videoInitTimeStamp2) {
        this.videoInitTimeStamp = videoInitTimeStamp2.longValue();
    }

    public final Surface getInputSurface(FFmpegSessionConfig config) {
        Surface surface = null;
        if (this.mMuxer == null || config == null) {
            return surface;
        }
        try {
            return this.mMuxer.getInputSurface(config);
        } catch (Throwable e) {
            Logger.E((String) TAG, e, (String) "getInputSurface exp", new Object[0]);
            return surface;
        }
    }

    public final void drainEncoder() {
        try {
            if (this.mMuxer != null) {
                this.mMuxer.drainEncoder();
            }
        } catch (Throwable e) {
            Logger.E((String) TAG, e, (String) "drainEncoder exp", new Object[0]);
        }
    }

    @TargetApi(16)
    public final void writeSampleData(MediaCodec encoder, int trackIndex, int bufferIndex, ByteBuffer encodedData, BufferInfo bufferInfo) {
        try {
            if (bufferInfo.size == 0) {
                Logger.D(TAG, "ignoring zero size buffer", new Object[0]);
                encoder.releaseOutputBuffer(bufferIndex, false);
                return;
            }
            if ((bufferInfo.flags & 2) != 0) {
                if (trackIndex == 1) {
                    Logger.D(TAG, "ignoring BUFFER_FLAG_CODEC_CONFIG", new Object[0]);
                    encoder.releaseOutputBuffer(bufferIndex, false);
                    return;
                }
                this.videoConfig = new byte[bufferInfo.size];
                encodedData.get(this.videoConfig);
                encodedData.position(bufferInfo.offset);
                encodedData.limit(bufferInfo.offset + bufferInfo.size);
            }
            bufferInfo.presentationTimeUs = getNextRelativePts(bufferInfo.presentationTimeUs, trackIndex);
            int result = -1;
            if (trackIndex == 0) {
                bufferInfo.presentationTimeUs += this.videoInitTimeStamp;
                boolean bIframe = (bufferInfo.flags & 1) > 0;
                if (bIframe) {
                    long timestamp = bufferInfo.presentationTimeUs - 1159;
                    if (timestamp < 0) {
                        timestamp = bufferInfo.presentationTimeUs;
                    }
                    putVideoHardware(this.videoConfig, this.videoConfig.length, timestamp, 2);
                    result = putVideoHardware(convertToByteArray(encodedData), bufferInfo.size, bufferInfo.presentationTimeUs, 1);
                    this.mPutVideoRet.set(result);
                } else if (this.mPutVideoRet.get() != 2) {
                    result = putVideoHardware(convertToByteArray(encodedData), bufferInfo.size, bufferInfo.presentationTimeUs, 0);
                    this.mPutVideoRet.set(result);
                }
                if ((result == 0 || this.mPutVideoRet.get() == 2) && this.recordListener != null) {
                    this.recordListener.onVideoTimeUpdate(bufferInfo.presentationTimeUs);
                    this.recordListener.onGetCount(getPublishCounter(), bufferInfo.presentationTimeUs, this.mPutVideoRet.get(), bIframe);
                    if (this.bFirst) {
                        this.bFirst = false;
                        Logger.D(CameraView.TAG, "putVideoHardware fist time presentationTimeUs=" + bufferInfo.presentationTimeUs, new Object[0]);
                    }
                } else if (result == -104 && this.recordListener != null) {
                    this.recordListener.onPutError(result);
                }
            } else {
                bufferInfo.presentationTimeUs += this.audioInitTimeStamp;
                result = putAudio(convertToByteArray(encodedData), bufferInfo.size, false, bufferInfo.presentationTimeUs);
                if ((result == 0 || result == 2) && this.recordListener != null) {
                    this.recordListener.onAudioTimeUpdate(bufferInfo.presentationTimeUs);
                }
            }
            if (isNeedLog()) {
                Logger.D(TAG, "writeSampleData trackIndex=" + trackIndex + ";encodedData.len=" + encodedData.capacity() + ";bufferInfo.size=" + bufferInfo.size + ";bufferInfo.pts=" + bufferInfo.presentationTimeUs + ";result=" + result + ", flag=" + bufferInfo.flags + ";mPutVideoRet=" + this.mPutVideoRet.get(), new Object[0]);
            }
            encoder.releaseOutputBuffer(bufferIndex, false);
        } catch (Exception e) {
            Logger.D(TAG, "writeSampleData exp=" + e.toString(), new Object[0]);
            encoder.releaseOutputBuffer(bufferIndex, false);
        }
    }

    public final int addTrack(MediaFormat trackFormat) {
        return 0;
    }

    @TargetApi(9)
    private byte[] convertToByteArray(ByteBuffer buffer) {
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes, 0, bytes.length);
        return bytes;
    }

    public final LiveCounter getPublishCounter() {
        if (this.mMuxer == null) {
            return null;
        }
        RecorderInternalCounter counter = this.mMuxer.getPublishCounter();
        LiveCounter data = new LiveCounter();
        data.setRecordValues(counter);
        return data;
    }

    private boolean isNeedLog() {
        if (this.testCount % 30 != 0) {
            this.testCount++;
            return false;
        }
        this.testCount = 0;
        this.testCount++;
        return true;
    }

    public final void releaseInputSurface(Surface surface) {
        if (this.mMuxer != null) {
            this.mMuxer.releaseInputSurface(surface);
        }
    }
}
