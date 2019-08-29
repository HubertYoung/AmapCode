package com.alipay.mobile.beehive.video.views;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
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
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.photo.view.CircleProgressBar;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.service.PhotoInfo;
import java.io.File;
import java.lang.ref.WeakReference;

public class OriVideoPreviewCon extends BaseVideoPreviewCon {
    private static final int CHECK_INTERVAL = 500;
    private static final String TAG = "OriVideoPreviewCon";
    public static final String ZERO_DURATION = "00:00";
    private Runnable autoPlayR = new Runnable() {
        public final void run() {
            OriVideoPreviewCon.this.clickAction();
        }
    };
    private b downListener = new b(this);
    private boolean isDurationParsed = false;
    private boolean isStartFromPause = false;
    private Drawable mThumbDrawable;
    /* access modifiers changed from: private */
    public Handler mTimerHandler = new Handler();
    private int retryTimes = 0;
    private Runnable timeCheckR = new Runnable() {
        public final void run() {
            long timeRemain = OriVideoPreviewCon.this.mVideoInfo.getVideoDuration() - ((long) OriVideoPreviewCon.this.vh.c.getCurrentPosition());
            String duration = null;
            if (!OriVideoPreviewCon.this.mDestroyed.get()) {
                duration = PhotoUtil.formatDuration(timeRemain);
                if (TextUtils.isEmpty(duration)) {
                    duration = OriVideoPreviewCon.ZERO_DURATION;
                }
                OriVideoPreviewCon.this.vh.e.setText(duration);
            }
            if (OriVideoPreviewCon.this.mFocusing.get() && !TextUtils.isEmpty(duration) && !TextUtils.equals(duration, OriVideoPreviewCon.ZERO_DURATION)) {
                OriVideoPreviewCon.this.mTimerHandler.postDelayed(this, 500);
            }
        }
    };
    /* access modifiers changed from: private */
    public a vh;

    static class a {
        public ImageView a;
        public CircleProgressBar b;
        public VideoPlayView c;
        public ImageView d;
        public TextView e;

        public a(View p) {
            this.a = (ImageView) p.findViewById(R.id.oPVideoThumb);
            this.b = (CircleProgressBar) p.findViewById(R.id.oPProgressBar);
            this.c = (VideoPlayView) p.findViewById(R.id.videoPlayView);
            this.d = (ImageView) p.getRootView().findViewById(R.id.oPStart);
            this.e = (TextView) p.getRootView().findViewById(R.id.oPVideoTime);
        }
    }

    private static class b implements APVideoDownloadCallback {
        private WeakReference<OriVideoPreviewCon> a;

        public b(OriVideoPreviewCon con) {
            this.a = new WeakReference<>(con);
        }

        private OriVideoPreviewCon a() {
            OriVideoPreviewCon con = (OriVideoPreviewCon) this.a.get();
            if (con == null || con.mDestroyed.get()) {
                return null;
            }
            return con;
        }

        public final void onDownloadStart(APMultimediaTaskModel apMultimediaTaskModel) {
            OriVideoPreviewCon con = a();
            if (con != null) {
                con.safeChaneViewState(1);
            }
        }

        public final void onDownloadFinished(APVideoDownloadRsp apVideoDownloadRsp) {
            OriVideoPreviewCon con = a();
            if (con != null) {
                con.safeChaneViewState(2);
                con.controlPlay(5);
            }
        }

        public final void onDownloadError(APVideoDownloadRsp rsp) {
            OriVideoPreviewCon con = a();
            if (con != null) {
                con.safeChaneViewState(3);
                con.safeShowDownLoadError(rsp);
                PhotoLogger.error((String) OriVideoPreviewCon.TAG, "video download error. path = " + con.mVideoInfo.getPhotoPath());
            }
        }

        public final void onDownloadProgress(APMultimediaTaskModel apMultimediaTaskModel, int progress) {
            OriVideoPreviewCon con = a();
            if (con != null) {
                con.safeChaneViewState(4);
                con.vh.b.safeSetProgress(progress);
            }
        }

        public final void onThumbDownloadFinished(APVideoDownloadRsp apVideoDownloadRsp) {
        }
    }

    public OriVideoPreviewCon(View composeView, PhotoInfo videoInfo) {
        super(composeView, videoInfo, false);
        renderViews(composeView);
    }

    private void getViewHolder(View p) {
        Object tag = p.getTag(R.id.id_download_play_original_video_view_holder);
        if (tag instanceof a) {
            this.vh = (a) tag;
            return;
        }
        this.vh = new a(p);
        p.setTag(R.id.id_download_play_original_video_view_holder, this.vh);
    }

    private void renderViews(View p) {
        getViewHolder(p);
        this.vh.c.setLooping(false);
        this.vh.c.setAutoFitCenter(true);
        this.vh.c.setOnCompletionListener(new OnPlayCompletionListener() {
            public final void onCompletion() {
                OriVideoPreviewCon.this.controlPlay(7);
            }
        });
        safeChaneViewState(0);
        getVideoService().loadAlbumVideo(this.mVideoInfo.getPhotoPath(), this.vh.a, Integer.valueOf(640), Integer.valueOf(640), null, new APImageDownLoadCallback() {
            public final void onSucc(APImageDownloadRsp arg0) {
                PhotoLogger.debug(OriVideoPreviewCon.TAG, "load thumbnail done,path = " + OriVideoPreviewCon.this.mVideoInfo.getPhotoPath());
            }

            public final void onProcess(String arg0, int arg1) {
            }

            public final void onError(APImageDownloadRsp arg0, Exception arg1) {
                PhotoLogger.error((String) OriVideoPreviewCon.TAG, "download video thumbnail error. video path = " + OriVideoPreviewCon.this.mVideoInfo.getPhotoPath());
            }
        }, ImageHelper.getBusinessId());
    }

    /* access modifiers changed from: protected */
    public void onChangeViewStateCalled(int state) {
        switch (state) {
            case 0:
                this.isStartFromPause = false;
                this.vh.c.setVisibility(8);
                this.vh.a.setVisibility(0);
                this.vh.b.setVisibility(8);
                return;
            case 1:
                this.vh.b.setVisibility(0);
                if (this.mFocusing.get()) {
                    this.vh.d.setVisibility(8);
                    this.vh.e.setVisibility(8);
                    return;
                }
                return;
            case 2:
                this.vh.b.setVisibility(8);
                if (this.mFocusing.get()) {
                    this.vh.d.setVisibility(0);
                    this.vh.e.setVisibility(0);
                    return;
                }
                return;
            case 3:
                this.vh.b.setVisibility(8);
                if (this.mFocusing.get()) {
                    this.vh.d.setImageResource(R.drawable.play);
                    this.vh.d.setVisibility(0);
                    this.vh.e.setVisibility(0);
                    return;
                }
                return;
            case 4:
                if (this.vh.b.getVisibility() != 0) {
                    this.vh.b.setVisibility(0);
                }
                if (this.mFocusing.get()) {
                    this.vh.d.setVisibility(8);
                    this.vh.e.setVisibility(8);
                    return;
                }
                return;
            case 5:
                if (this.mFocusing.get()) {
                    this.vh.d.setImageResource(R.drawable.pause);
                }
                if (this.vh.b.getVisibility() == 0) {
                    this.vh.b.setVisibility(8);
                }
                this.vh.a.setVisibility(8);
                this.vh.c.setVisibility(0);
                if (!this.isStartFromPause) {
                    this.isStartFromPause = false;
                    drawThumbFirst();
                    return;
                }
                return;
            case 6:
                this.isStartFromPause = true;
                if (this.mFocusing.get()) {
                    this.vh.d.setImageResource(R.drawable.play);
                }
                if (this.vh.c.getVisibility() != 0) {
                    this.vh.c.setVisibility(0);
                }
                if (this.vh.a.getVisibility() != 8) {
                    this.vh.a.setVisibility(8);
                    return;
                }
                return;
            case 7:
                this.isStartFromPause = false;
                if (this.mFocusing.get()) {
                    this.vh.d.setImageResource(R.drawable.play);
                    this.vh.e.setText(PhotoUtil.formatDuration(this.mVideoInfo.getVideoDuration()));
                }
                this.vh.a.setVisibility(0);
                this.vh.c.setVisibility(8);
                return;
            default:
                return;
        }
    }

    private void drawThumbFirst() {
        if (this.mThumbDrawable == null) {
            this.mThumbDrawable = this.vh.a.getDrawable();
        }
        if (this.mThumbDrawable instanceof BitmapDrawable) {
            this.vh.c.drawBitmap(((BitmapDrawable) this.mThumbDrawable).getBitmap());
        }
    }

    private void loadAndPlay() {
        safeChaneViewState(1);
        getVideoService().downloadVideo(this.mVideoInfo.getPhotoPath(), this.downListener, ImageHelper.getBusinessId());
    }

    /* access modifiers changed from: private */
    public void controlPlay(int state) {
        switch (state) {
            case 5:
                if (!this.mDestroyed.get()) {
                    this.mTimerHandler.removeCallbacksAndMessages(null);
                    String path = this.mVideoInfo.getPhotoPath();
                    if (getVideoService().isVideoAvailable(path)) {
                        this.retryTimes = 0;
                        if (this.mVideoInfo.getVideoDuration() <= 0) {
                            tryParseDurationFromFile(path);
                        }
                        this.mTimerHandler.post(this.timeCheckR);
                        safeInsidePlay(5);
                        return;
                    }
                    int i = this.retryTimes;
                    this.retryTimes = i + 1;
                    if (i < 3) {
                        loadAndPlay();
                        return;
                    }
                    APVideoDownloadRsp response = new APVideoDownloadRsp();
                    response.setRetCode(7);
                    safeShowDownLoadError(response);
                    return;
                }
                return;
            case 6:
                this.mTimerHandler.removeCallbacksAndMessages(null);
                safeInsidePlay(6);
                return;
            case 7:
                this.mTimerHandler.removeCallbacksAndMessages(null);
                safeInsidePlay(7);
                return;
            default:
                return;
        }
    }

    private void tryParseDurationFromFile(String idOrPath) {
        if (!this.isDurationParsed) {
            PhotoLogger.w(TAG, "tryParseDurationFromFile:###");
            PhotoLogger.w(TAG, "idOrPath = " + idOrPath);
            this.isDurationParsed = true;
            try {
                String path = getVideoService().getVideoPathById(idOrPath);
                PhotoLogger.w(TAG, "Local file path =" + path);
                int durationParsedFromFile = getVideoService().parseVideoInfo(path).duration;
                PhotoLogger.w(TAG, "Parsed duration = " + durationParsedFromFile);
                if (durationParsedFromFile > 0) {
                    PhotoLogger.w(TAG, "Update duration from " + this.mVideoInfo.getVideoDuration() + " ,to " + durationParsedFromFile);
                    this.mVideoInfo.setVideoDuration((long) durationParsedFromFile);
                }
            } catch (Throwable e) {
                PhotoLogger.w(TAG, "Parse video duration exception," + e.getMessage());
            }
        }
    }

    public void onFocus(boolean isAutoPlayOri) {
        PhotoLogger.debug(TAG, "onFocus");
        super.onFocus(isAutoPlayOri);
        this.vh.e.setText(PhotoUtil.formatDuration(this.mVideoInfo.getVideoDuration()));
        this.vh.d.setImageResource(R.drawable.play);
        this.vh.d.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                OriVideoPreviewCon.this.clickAction();
            }
        });
        if (isAutoPlayOri) {
            if (isWifiConnected() || getVideoService().isVideoAvailable(this.mVideoInfo.getPhotoPath())) {
                this.mHandler.postDelayed(this.autoPlayR, 100);
            } else {
                PhotoLogger.info(TAG, "video is not downloaded,no wifi either.wait user click to start download!");
            }
        }
        APMultimediaTaskModel task = getVideoService().getVideoDownloadStatus(this.mVideoInfo.getPhotoPath());
        if (task == null) {
            return;
        }
        if (task.getStatus() == 1 || task.getStatus() == 0) {
            PhotoLogger.debug(TAG, "ori video is loading or wait for loading.");
            loadAndPlay();
        }
    }

    /* access modifiers changed from: private */
    public void clickAction() {
        if (this.mVideoInfo.getPhotoPath().contains(File.separator)) {
            safeCallSystemPlay();
            PhotoLogger.info(TAG, "video = " + this.mVideoInfo.getPhotoPath() + "video resist in local storage,call system player to play.");
            return;
        }
        controlPlay(this.vh.c.isPlaying() ? 6 : 5);
    }

    public void onLoseFocus() {
        PhotoLogger.debug(TAG, "onLoseFocus");
        super.onLoseFocus();
        controlPlay(7);
        this.mHandler.removeCallbacks(this.autoPlayR);
    }

    public void destroy() {
        ImageHelper.safeRemoveDrawable(this.vh.a);
        super.destroy();
        this.mTimerHandler.removeCallbacksAndMessages(null);
        this.vh.c.setOnCompletionListener(null);
        this.mThumbDrawable = null;
        this.vh.b.safeSetProgress(0);
    }
}
