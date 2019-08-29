package com.alipay.mobile.beehive.video.views;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoDownloadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.widget.VideoPlayView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.VideoPlayView.OnPlayCompletionListener;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.view.CircleProgressBar;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.service.PhotoInfo;
import java.lang.ref.WeakReference;

public class SmallVideoPreviewCon extends BaseVideoPreviewCon {
    private static final String TAG = "SmallVideoPreviewCon";
    private static final int TIME_DELAY_MOVE_HINT = 5000;
    /* access modifiers changed from: private */
    public Handler delayMoveHintHandler;
    private a downListener = new a(this);
    /* access modifiers changed from: private */
    public boolean isHintShowed = false;
    /* access modifiers changed from: private */
    public b vh;

    private static class a implements APVideoDownloadCallback {
        private WeakReference<SmallVideoPreviewCon> a;

        public a(SmallVideoPreviewCon con) {
            this.a = new WeakReference<>(con);
        }

        private SmallVideoPreviewCon a() {
            SmallVideoPreviewCon con = (SmallVideoPreviewCon) this.a.get();
            if (con == null || con.mDestroyed.get()) {
                return null;
            }
            return con;
        }

        public final void onDownloadStart(APMultimediaTaskModel apMultimediaTaskModel) {
            SmallVideoPreviewCon con = a();
            if (con != null) {
                con.safeChaneViewState(1);
            }
        }

        public final void onDownloadFinished(APVideoDownloadRsp apVideoDownloadRsp) {
            SmallVideoPreviewCon con = a();
            if (con != null) {
                con.safeChaneViewState(2);
                con.controlPlay(true);
            }
        }

        public final void onDownloadError(APVideoDownloadRsp rsp) {
            SmallVideoPreviewCon con = a();
            if (con != null) {
                con.safeChaneViewState(3);
                con.safeShowDownLoadError(rsp);
                PhotoLogger.error((String) SmallVideoPreviewCon.TAG, "video download error. path = " + con.mVideoInfo.getPhotoPath());
            }
        }

        public final void onDownloadProgress(APMultimediaTaskModel apMultimediaTaskModel, int progress) {
            SmallVideoPreviewCon con = a();
            if (con != null) {
                con.safeChaneViewState(4);
                con.vh.a.safeSetProgress(progress);
            }
        }

        public final void onThumbDownloadFinished(APVideoDownloadRsp apVideoDownloadRsp) {
        }
    }

    static class b {
        public CircleProgressBar a;
        public ImageView b;
        public TextView c;
        public VideoPlayView d;

        public b(View p) {
            this.a = (CircleProgressBar) p.findViewById(R.id.sProgressBar);
            this.b = (ImageView) p.findViewById(R.id.sStart);
            this.c = (TextView) p.findViewById(R.id.finishHint);
            this.d = (VideoPlayView) p.findViewById(R.id.videoPlayView);
        }
    }

    public SmallVideoPreviewCon(View composeView, PhotoInfo videoInfo) {
        super(composeView, videoInfo, false);
        renderViews(composeView);
    }

    private void renderViews(View p) {
        getViewHolder(p);
        safeChaneViewState(0);
        if (sShowPlayFinishHint) {
            if (this.delayMoveHintHandler == null) {
                this.delayMoveHintHandler = new Handler();
            }
            this.vh.d.setOnCompletionListener(new OnPlayCompletionListener() {
                public final void onCompletion() {
                    SmallVideoPreviewCon.this.showPlayFinishHint();
                }
            });
        }
        this.vh.d.setLooping(true);
        this.vh.d.setAutoFitCenter(true);
        drawThumb();
    }

    private void getViewHolder(View p) {
        Object tag = p.getTag(R.id.id_download_play_small_video_view_holder);
        if (tag instanceof b) {
            this.vh = (b) tag;
            return;
        }
        this.vh = new b(p);
        p.setTag(R.id.id_download_play_small_video_view_holder, this.vh);
    }

    private void drawThumb() {
        getVideoService().loadVideoThumb(this.mVideoInfo.getPhotoPath(), this.vh.d, null, new APImageDownLoadCallback() {
            public final void onSucc(APImageDownloadRsp arg0) {
            }

            public final void onProcess(String arg0, int arg1) {
            }

            public final void onError(APImageDownloadRsp arg0, Exception arg1) {
                PhotoLogger.error((String) SmallVideoPreviewCon.TAG, "download video thumbnail error. video path = " + SmallVideoPreviewCon.this.mVideoInfo.getPhotoPath());
            }
        }, ImageHelper.getBusinessId());
    }

    /* access modifiers changed from: private */
    public void showPlayFinishHint() {
        this.mHandler.post(new Runnable() {
            public final void run() {
                if (!SmallVideoPreviewCon.this.isHintShowed) {
                    SmallVideoPreviewCon.this.vh.c.setVisibility(0);
                    SmallVideoPreviewCon.this.isHintShowed = true;
                    SmallVideoPreviewCon.this.delayMoveHintHandler.postDelayed(new Runnable() {
                        public final void run() {
                            SmallVideoPreviewCon.this.vh.c.setVisibility(8);
                        }
                    }, 5000);
                }
            }
        });
    }

    public void onFocus(boolean isAutoPlayOri) {
        PhotoLogger.debug(TAG, "onFocus");
        super.onFocus(isAutoPlayOri);
        this.isHintShowed = false;
        drawThumb();
        handleOfficalVideo();
    }

    public void onLoseFocus() {
        PhotoLogger.debug(TAG, "onLoseFocue");
        super.onLoseFocus();
        this.vh.d.stop();
        this.vh.c.setVisibility(8);
        this.isHintShowed = false;
        if (this.delayMoveHintHandler != null) {
            this.delayMoveHintHandler.removeCallbacksAndMessages(null);
        }
    }

    public void destroy() {
        super.destroy();
        this.vh.a.safeSetProgress(0);
    }

    private void handleOfficalVideo() {
        if (getVideoService().isVideoAvailable(this.mVideoInfo.getPhotoPath())) {
            if (isWifiConnected()) {
                controlPlay(true);
            } else if (sDisableAutoPlayInPoorNetwork) {
                this.vh.b.setVisibility(0);
                this.vh.b.setOnClickListener(new OnClickListener() {
                    public final void onClick(View v) {
                        SmallVideoPreviewCon.this.controlPlay(true);
                    }
                });
            } else {
                controlPlay(true);
            }
        } else if (isWifiConnected() || sAutoDownloadInMobileNetwork) {
            downloadAndPlayInside();
        } else {
            loadAndPlayDepends();
        }
    }

    /* access modifiers changed from: private */
    public void downloadAndPlayInside() {
        safeChaneViewState(1);
        getVideoService().downloadVideo(this.mVideoInfo.getPhotoPath(), this.downListener, ImageHelper.getBusinessId());
    }

    private void loadAndPlayDepends() {
        this.vh.b.setVisibility(0);
        this.vh.b.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                SmallVideoPreviewCon.this.downloadAndPlayInside();
            }
        });
        APMultimediaTaskModel task = getVideoService().getVideoDownloadStatus(this.mVideoInfo.getPhotoPath());
        if (task == null) {
            return;
        }
        if (task.getStatus() == 1 || task.getStatus() == 0) {
            PhotoLogger.debug(TAG, "small video is loading or wait for loading.");
            downloadAndPlayInside();
        }
    }

    /* access modifiers changed from: private */
    public void controlPlay(boolean isPlay) {
        if (isPlay) {
            safeInsidePlay(5);
        } else {
            safeInsidePlay(7);
        }
    }

    /* access modifiers changed from: protected */
    public final void onChangeViewStateCalled(int state) {
        switch (state) {
            case 0:
                this.vh.d.setVisibility(0);
                this.vh.a.setVisibility(8);
                this.vh.b.setVisibility(8);
                this.vh.c.setVisibility(8);
                return;
            case 1:
                this.vh.a.setVisibility(0);
                this.vh.b.setVisibility(8);
                return;
            case 2:
                this.vh.a.setVisibility(8);
                this.vh.b.setVisibility(8);
                return;
            case 3:
                this.vh.a.setVisibility(8);
                break;
            case 4:
                if (this.vh.a.getVisibility() != 0) {
                    this.vh.a.setVisibility(0);
                }
                if (this.vh.b.getVisibility() != 8) {
                    this.vh.b.setVisibility(8);
                    return;
                }
                return;
            case 5:
                this.vh.b.setVisibility(8);
                if (this.vh.a.getVisibility() == 0) {
                    this.vh.a.setVisibility(8);
                    return;
                }
                return;
            case 7:
                break;
            default:
                return;
        }
        this.vh.b.setVisibility(0);
    }
}
