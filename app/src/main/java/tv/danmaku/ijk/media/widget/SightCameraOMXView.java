package tv.danmaku.ijk.media.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.alipay.streammedia.encode.utils.OMXConfig;
import tv.danmaku.ijk.media.encode.SessionConfig;

public class SightCameraOMXView extends SightCameraGLESView {
    private int omxMask = 0;
    private int videoBritate = SessionConfig.VIDEO_BITRATE_DEFAULT;

    public SightCameraOMXView(Context context) {
        super(context);
    }

    public SightCameraOMXView(Context context, OMXConfig config) {
        super(context);
        if (config.width > 0 && config.height > 0) {
            SessionConfig.VIDEO_HARDENCODE_W = config.width;
            SessionConfig.VIDEO_HARDENCODE_H = config.height;
        }
        this.videoBritate = config.bitrate;
        this.omxMask = config.flags;
    }

    public SightCameraOMXView(Context context, AttributeSet set) {
        super(context, set);
    }

    public SightCameraOMXView(Context context, AttributeSet set, int defaultStyle) {
        super(context, set, defaultStyle);
    }

    /* access modifiers changed from: protected */
    public int getRecordType() {
        return 3;
    }

    /* access modifiers changed from: protected */
    public SessionConfig getSessionConfig() {
        SessionConfig config = super.getSessionConfig();
        config.setmVideoBitrate(this.videoBritate);
        config.setOmxMask(this.omxMask);
        return config;
    }

    public boolean isOMX() {
        return true;
    }
}
