package tv.danmaku.ijk.media.player.misc;

import android.annotation.TargetApi;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import tv.danmaku.ijk.media.player.IjkMediaMeta;
import tv.danmaku.ijk.media.player.IjkMediaMeta.IjkStreamMeta;

public class IjkMediaFormat implements IMediaFormat {
    public static final String CODEC_NAME_H264 = "h264";
    public static final String KEY_IJK_BIT_RATE_UI = "ijk-bit-rate-ui";
    public static final String KEY_IJK_CHANNEL_UI = "ijk-channel-ui";
    public static final String KEY_IJK_CODEC_LONG_NAME_UI = "ijk-codec-long-name-ui";
    public static final String KEY_IJK_CODEC_NAME_UI = "ijk-codec-name-ui";
    public static final String KEY_IJK_CODEC_PIXEL_FORMAT_UI = "ijk-pixel-format-ui";
    public static final String KEY_IJK_CODEC_PROFILE_LEVEL_UI = "ijk-profile-level-ui";
    public static final String KEY_IJK_FRAME_RATE_UI = "ijk-frame-rate-ui";
    public static final String KEY_IJK_RESOLUTION_UI = "ijk-resolution-ui";
    public static final String KEY_IJK_SAMPLE_RATE_UI = "ijk-sample-rate-ui";
    private static final Map<String, Formatter> sFormatterMap = new HashMap();
    public final IjkStreamMeta mMediaFormat;

    abstract class Formatter {
        /* access modifiers changed from: protected */
        public abstract String doFormat(IjkMediaFormat ijkMediaFormat);

        private Formatter() {
        }

        public String format(IjkMediaFormat mediaFormat) {
            String value = doFormat(mediaFormat);
            if (TextUtils.isEmpty(value)) {
                return getDefaultString();
            }
            return value;
        }

        /* access modifiers changed from: protected */
        public String getDefaultString() {
            return "N/A";
        }
    }

    public IjkMediaFormat(IjkStreamMeta streamMeta) {
        sFormatterMap.put(KEY_IJK_CODEC_LONG_NAME_UI, new Formatter() {
            public String doFormat(IjkMediaFormat mediaFormat) {
                return IjkMediaFormat.this.mMediaFormat.getString(IjkMediaMeta.IJKM_KEY_CODEC_LONG_NAME);
            }
        });
        sFormatterMap.put(KEY_IJK_CODEC_NAME_UI, new Formatter() {
            public String doFormat(IjkMediaFormat mediaFormat) {
                return IjkMediaFormat.this.mMediaFormat.getString(IjkMediaMeta.IJKM_KEY_CODEC_NAME);
            }
        });
        sFormatterMap.put(KEY_IJK_BIT_RATE_UI, new Formatter() {
            /* access modifiers changed from: protected */
            public String doFormat(IjkMediaFormat mediaFormat) {
                int bitRate = mediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_BITRATE);
                if (bitRate <= 0) {
                    return null;
                }
                if (bitRate < 1000) {
                    return String.format(Locale.US, "%d bit/s", new Object[]{Integer.valueOf(bitRate)});
                }
                return String.format(Locale.US, "%d kb/s", new Object[]{Integer.valueOf(bitRate / 1000)});
            }
        });
        sFormatterMap.put(KEY_IJK_CODEC_PROFILE_LEVEL_UI, new Formatter() {
            /* access modifiers changed from: protected */
            public String doFormat(IjkMediaFormat mediaFormat) {
                String profile;
                switch (mediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_CODEC_PROFILE_ID)) {
                    case 44:
                        profile = "CAVLC 4:4:4";
                        break;
                    case 66:
                        profile = "Baseline";
                        break;
                    case 77:
                        profile = "Main";
                        break;
                    case IjkMediaMeta.FF_PROFILE_H264_EXTENDED /*88*/:
                        profile = "Extended";
                        break;
                    case 100:
                        profile = "High";
                        break;
                    case 110:
                        profile = "High 10";
                        break;
                    case 122:
                        profile = "High 4:2:2";
                        break;
                    case 144:
                        profile = "High 4:4:4";
                        break;
                    case 244:
                        profile = "High 4:4:4 Predictive";
                        break;
                    case IjkMediaMeta.FF_PROFILE_H264_CONSTRAINED_BASELINE /*578*/:
                        profile = "Constrained Baseline";
                        break;
                    case IjkMediaMeta.FF_PROFILE_H264_HIGH_10_INTRA /*2158*/:
                        profile = "High 10 Intra";
                        break;
                    case IjkMediaMeta.FF_PROFILE_H264_HIGH_422_INTRA /*2170*/:
                        profile = "High 4:2:2 Intra";
                        break;
                    case IjkMediaMeta.FF_PROFILE_H264_HIGH_444_INTRA /*2292*/:
                        profile = "High 4:4:4 Intra";
                        break;
                    default:
                        return null;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(profile);
                String codecName = mediaFormat.getString(IjkMediaMeta.IJKM_KEY_CODEC_NAME);
                if (!TextUtils.isEmpty(codecName) && codecName.equalsIgnoreCase(IjkMediaFormat.CODEC_NAME_H264)) {
                    int level = mediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_CODEC_LEVEL);
                    if (level < 10) {
                        return sb.toString();
                    }
                    sb.append(" Profile Level ");
                    sb.append((level / 10) % 10);
                    if (level % 10 != 0) {
                        sb.append(".");
                        sb.append(level % 10);
                    }
                }
                return sb.toString();
            }
        });
        sFormatterMap.put(KEY_IJK_CODEC_PIXEL_FORMAT_UI, new Formatter() {
            /* access modifiers changed from: protected */
            public String doFormat(IjkMediaFormat mediaFormat) {
                return mediaFormat.getString(IjkMediaMeta.IJKM_KEY_CODEC_PIXEL_FORMAT);
            }
        });
        sFormatterMap.put(KEY_IJK_RESOLUTION_UI, new Formatter() {
            /* access modifiers changed from: protected */
            public String doFormat(IjkMediaFormat mediaFormat) {
                int width = mediaFormat.getInteger("width");
                int height = mediaFormat.getInteger("height");
                int sarNum = mediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_SAR_NUM);
                int sarDen = mediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_SAR_DEN);
                if (width <= 0 || height <= 0) {
                    return null;
                }
                if (sarNum <= 0 || sarDen <= 0) {
                    return String.format(Locale.US, "%d x %d", new Object[]{Integer.valueOf(width), Integer.valueOf(height)});
                }
                return String.format(Locale.US, "%d x %d [SAR %d:%d]", new Object[]{Integer.valueOf(width), Integer.valueOf(height), Integer.valueOf(sarNum), Integer.valueOf(sarDen)});
            }
        });
        sFormatterMap.put(KEY_IJK_FRAME_RATE_UI, new Formatter() {
            /* access modifiers changed from: protected */
            public String doFormat(IjkMediaFormat mediaFormat) {
                int fpsNum = mediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_FPS_NUM);
                int fpsDen = mediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_FPS_DEN);
                if (fpsNum <= 0 || fpsDen <= 0) {
                    return null;
                }
                return String.valueOf(((float) fpsNum) / ((float) fpsDen));
            }
        });
        sFormatterMap.put(KEY_IJK_SAMPLE_RATE_UI, new Formatter() {
            /* access modifiers changed from: protected */
            public String doFormat(IjkMediaFormat mediaFormat) {
                int sampleRate = mediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_SAMPLE_RATE);
                if (sampleRate <= 0) {
                    return null;
                }
                return String.format(Locale.US, "%d Hz", new Object[]{Integer.valueOf(sampleRate)});
            }
        });
        sFormatterMap.put(KEY_IJK_CHANNEL_UI, new Formatter() {
            /* access modifiers changed from: protected */
            public String doFormat(IjkMediaFormat mediaFormat) {
                int channelLayout = mediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_CHANNEL_LAYOUT);
                if (channelLayout <= 0) {
                    return null;
                }
                if (((long) channelLayout) == 4) {
                    return "mono";
                }
                if (((long) channelLayout) == 3) {
                    return "stereo";
                }
                return String.format(Locale.US, "%x", new Object[]{Integer.valueOf(channelLayout)});
            }
        });
        this.mMediaFormat = streamMeta;
    }

    @TargetApi(16)
    public int getInteger(String name) {
        if (this.mMediaFormat == null) {
            return 0;
        }
        return this.mMediaFormat.getInt(name);
    }

    public String getString(String name) {
        if (this.mMediaFormat == null) {
            return null;
        }
        if (sFormatterMap.containsKey(name)) {
            return sFormatterMap.get(name).format(this);
        }
        return this.mMediaFormat.getString(name);
    }
}
