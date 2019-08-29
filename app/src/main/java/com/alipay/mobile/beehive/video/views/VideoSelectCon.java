package com.alipay.mobile.beehive.video.views;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.io.File;

public class VideoSelectCon implements IVideoViewControl {
    private static final String TAG = "OriVideoPreviewCon2";
    /* access modifiers changed from: private */
    public Context mContext;
    private TextView mExtraInfo;
    private ImageView mStartBtn = ((ImageView) this.mTargetView.findViewById(R.id.oSStart));
    private View mTargetView;
    private ImageView mThumbImgView = ((ImageView) this.mTargetView.findViewById(R.id.oSVideoThumb));
    /* access modifiers changed from: private */
    public PhotoInfo mVideoInfo;
    private MultimediaVideoService mVideoService;

    public VideoSelectCon(View targetView, PhotoInfo videoInfo) {
        this.mVideoInfo = videoInfo;
        this.mTargetView = targetView;
        this.mContext = targetView.getContext();
        this.mStartBtn.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                if (VideoSelectCon.this.mVideoInfo.getPhotoPath().contains(File.separator)) {
                    EnhancedVideoPlayView.callSystemPlay(VideoSelectCon.this.mVideoInfo.getPhotoPath(), VideoSelectCon.this.mContext);
                } else {
                    EnhancedVideoPlayView.callSystemPlay(VideoSelectCon.this.getVideoService().getVideoPathById(VideoSelectCon.this.mVideoInfo.getPhotoPath()), VideoSelectCon.this.mContext);
                }
            }
        });
        this.mExtraInfo = (TextView) this.mTargetView.findViewById(R.id.oSExtraInfo);
        if (this.mVideoInfo.getPhotoSize() > 0) {
            this.mExtraInfo.setText(this.mContext.getString(R.string.video_size) + this.mVideoInfo.getVideoSizeStr());
            this.mExtraInfo.setVisibility(0);
        } else {
            this.mExtraInfo.setVisibility(8);
        }
        getVideoService().loadAlbumVideo(this.mVideoInfo.getPhotoPath(), this.mThumbImgView, Integer.valueOf(640), Integer.valueOf(640), null, new APImageDownLoadCallback() {
            public final void onSucc(APImageDownloadRsp arg0) {
                PhotoLogger.debug(VideoSelectCon.TAG, "load thumbnail done,path = " + VideoSelectCon.this.mVideoInfo.getPhotoPath());
            }

            public final void onProcess(String arg0, int arg1) {
            }

            public final void onError(APImageDownloadRsp arg0, Exception arg1) {
                PhotoLogger.error((String) VideoSelectCon.TAG, "download video thumbnail error. video path = " + VideoSelectCon.this.mVideoInfo.getPhotoPath());
            }
        }, ImageHelper.getBusinessId());
    }

    /* access modifiers changed from: private */
    public MultimediaVideoService getVideoService() {
        if (this.mVideoService == null) {
            this.mVideoService = (MultimediaVideoService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaVideoService.class.getName());
        }
        return this.mVideoService;
    }

    public void onFocus(boolean isAutoPlayOri) {
    }

    public void onLoseFocus() {
    }

    public void destroy() {
        ImageHelper.safeRemoveDrawable(this.mThumbImgView);
    }
}
