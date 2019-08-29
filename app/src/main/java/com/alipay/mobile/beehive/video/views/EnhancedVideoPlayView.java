package com.alipay.mobile.beehive.video.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.antui.basic.AUToast;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.multimedia.adjuster.api.APMSandboxProcessor;
import java.io.File;

public class EnhancedVideoPlayView extends FrameLayout implements IVideoViewControl {
    protected static final String FILE_SCHEME = "file://";
    private static final String TAG = "VideoPlayView";
    private static int sVideoShowType = 0;
    private IVideoViewControl mController;
    private ImageView mGStartBtn;
    private TextView mGVideoTime;
    private View mRootView;

    public EnhancedVideoPlayView(Context context) {
        this(context, null, 0);
    }

    public EnhancedVideoPlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EnhancedVideoPlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (1 == sVideoShowType) {
            LayoutInflater.from(context).inflate(R.layout.view_ori_video_select_preview, this, true);
        } else {
            LayoutInflater.from(context).inflate(R.layout.view_enhanced_video_play_view, this, true);
        }
    }

    public void loadVideoInfo(PhotoInfo videoInfo) {
        if (this.mController != null) {
            this.mController.destroy();
            this.mController = null;
        }
        if (videoInfo != null && videoInfo.isVideo()) {
            if (1 == sVideoShowType) {
                this.mController = new VideoSelectCon(this, videoInfo);
            } else {
                this.mController = BaseVideoPreviewCon.genController(this, videoInfo);
            }
        }
    }

    public static void globalConfig(int oVideoExtraInfoType, boolean showPlayFinishHint, boolean disableAutoPlayInPoorNetwork, int videoShowType, boolean autoDownloadInMobileNetwork, boolean enableSmallVideoStreamPlay, boolean enableOriginalVideoStreamPlay) {
        BaseVideoPreviewCon.globalConfig(showPlayFinishHint, disableAutoPlayInPoorNetwork, autoDownloadInMobileNetwork, enableSmallVideoStreamPlay, enableOriginalVideoStreamPlay);
        sVideoShowType = videoShowType;
    }

    public void onFocus(boolean isAutPlayOri) {
        if (this.mController != null) {
            this.mController.onFocus(isAutPlayOri);
            return;
        }
        removeVideoPlayZone();
        PhotoLogger.debug(TAG, "controler == null ,@ onFocus");
    }

    public void onLoseFocus() {
        if (this.mController != null) {
            this.mController.onLoseFocus();
        } else {
            PhotoLogger.debug(TAG, "controler == null ,@ onLoseFocus");
        }
    }

    public void destroy() {
        if (this.mController != null) {
            this.mController.destroy();
            this.mController = null;
        }
    }

    private void removeVideoPlayZone() {
        if (this.mRootView == null) {
            this.mRootView = getRootView();
            this.mGVideoTime = (TextView) this.mRootView.findViewById(R.id.oPVideoTime);
            this.mGStartBtn = (ImageView) this.mRootView.findViewById(R.id.oPStart);
        }
        if (this.mGStartBtn != null) {
            this.mGStartBtn.setVisibility(8);
        }
        if (this.mGVideoTime != null) {
            this.mGVideoTime.setVisibility(8);
        }
    }

    public static void callSystemPlay(String path, Context context) {
        PhotoLogger.debug(TAG, "playLocalOriginalVideo() called.");
        String pathOrUri = path;
        PhotoLogger.debug(TAG, "playOriginalVideo() filePath = " + pathOrUri);
        if (!TextUtils.isEmpty(pathOrUri)) {
            String localUri = pathOrUri;
            if (pathOrUri.startsWith(File.separator)) {
                localUri = "file://" + pathOrUri;
            }
            Uri uri = Uri.parse(localUri);
            String errorStr = null;
            if (!APMSandboxProcessor.checkFileExist(uri.toString())) {
                errorStr = "video file not exist!";
            }
            if (errorStr != null) {
                PhotoLogger.debug(TAG, "play video File error,path = " + uri.getPath() + "error = " + errorStr);
                return;
            }
            PhotoLogger.debug(TAG, "play local original video : url = " + uri.toString());
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(uri, "video/*");
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                AUToast.makeToast(context, 0, (CharSequence) context.getString(R.string.no_suitable_video_player_hint), 1).show();
            }
        }
    }
}
