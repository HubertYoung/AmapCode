package com.alipay.mobile.beehive.video.views;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.widget.VideoPlayView;
import com.alipay.mobile.antui.basic.AUToast;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.ui.PhotoBrowseView;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.VideoUtils;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseVideoPreviewCon implements IVideoViewControl {
    private static final int CODE_ERR_FILE_ID_NOT_EXIST = 11;
    private static final int CODE_ERR_NETWORK_ERR = 10;
    private static final int CODE_ERR_NO_NETWORK = 9;
    static final int CODE_ERR_PATH_EMPTY = 7;
    private static final int CODE_ERR_TASK_CANCELED = 5;
    private static final int CODE_SUCCESS = 0;
    static final int STATE_DOWNLOAD_ERROR = 3;
    static final int STATE_DOWNLOAD_FINISH = 2;
    static final int STATE_INIT = 0;
    static final int STATE_START_DOWNLOAD = 1;
    static final int STATE_UPDATEPROGRESS = 4;
    static final int STA_PAUSE = 6;
    static final int STA_PLAY = 5;
    static final int STA_STOP = 7;
    private static final String TAG = "BaseVideoPreviewCon";
    static boolean sAutoDownloadInMobileNetwork = false;
    static boolean sDisableAutoPlayInPoorNetwork = false;
    static boolean sEnableOriginalVideoStreamPlay = false;
    static boolean sEnableSmallVideoStreamPlay = false;
    static boolean sShowPlayFinishHint = false;
    Context mContext;
    AtomicBoolean mDestroyed = new AtomicBoolean(false);
    AtomicBoolean mFocusing = new AtomicBoolean(false);
    Handler mHandler = new Handler(Looper.getMainLooper());
    PhotoInfo mVideoInfo;
    private MultimediaVideoService mVideoService;
    private a vh;

    private static class a {
        ViewStub a;
        ViewStub b;
        ViewGroup c;
        ViewGroup d;
        VideoPlayView e;
        FrameLayout f;
        FrameLayout g;
        ImageView h;
        TextView i;
        private View j;

        a(View p) {
            this.j = p;
            this.b = (ViewStub) p.findViewById(R.id.vs_video_download_play_zone);
            this.a = (ViewStub) p.findViewById(R.id.vs_video_stream_play_zone);
            this.h = (ImageView) p.getRootView().findViewById(R.id.oPStart);
            this.i = (TextView) p.getRootView().findViewById(R.id.oPVideoTime);
        }

        public final void a() {
            if (this.d == null) {
                this.d = (ViewGroup) this.b.inflate();
                this.e = (VideoPlayView) this.j.findViewById(R.id.videoPlayView);
                this.f = (FrameLayout) this.j.findViewById(R.id.smallVideoZone);
                this.g = (FrameLayout) this.j.findViewById(R.id.oriVideoPreviewZone);
                this.j.setTag(R.id.id_video_preview_view_holder, this);
            }
        }

        public final void b() {
            if (this.c == null) {
                this.c = (ViewGroup) this.a.inflate();
                this.j.setTag(R.id.id_video_preview_view_holder, this);
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void onChangeViewStateCalled(int i);

    static void globalConfig(boolean showPlayFinishHint, boolean disableAutoPlayInPoorNetwork, boolean autoDownloadInMobileNetwork, boolean enableSmallVideoStreamPlay, boolean enableOriginalVideoStreamPlay) {
        sShowPlayFinishHint = showPlayFinishHint;
        sDisableAutoPlayInPoorNetwork = disableAutoPlayInPoorNetwork;
        sAutoDownloadInMobileNetwork = autoDownloadInMobileNetwork;
        sEnableSmallVideoStreamPlay = enableSmallVideoStreamPlay;
        sEnableOriginalVideoStreamPlay = enableOriginalVideoStreamPlay;
        PhotoLogger.debug(TAG, "Auto download in mobile network = " + autoDownloadInMobileNetwork);
    }

    static IVideoViewControl genController(View composeView, PhotoInfo videoInfo) {
        if (PhotoBrowseView.sDisableVideoStreamPlay) {
            PhotoLogger.debug(TAG, "Global disable video stream play.");
            if (videoInfo.getMediaType() == 1) {
                return new SmallVideoPreviewCon(composeView, videoInfo);
            }
            if (videoInfo.getMediaType() == 2) {
                return new OriVideoPreviewCon(composeView, videoInfo);
            }
        } else if (videoInfo.getMediaType() == 1) {
            if (sEnableSmallVideoStreamPlay) {
                return new StreamPlayCon(composeView, videoInfo);
            }
            return new SmallVideoPreviewCon(composeView, videoInfo);
        } else if (videoInfo.getMediaType() == 2) {
            boolean isLocalFile = VideoUtils.isLocalFile(videoInfo.getPhotoPath());
            if (!sEnableOriginalVideoStreamPlay || isLocalFile) {
                return new OriVideoPreviewCon(composeView, videoInfo);
            }
            return new StreamPlayCon(composeView, videoInfo);
        }
        return null;
    }

    public BaseVideoPreviewCon(View composeView, PhotoInfo videoInfo, boolean isStreamPlay) {
        this.mVideoInfo = videoInfo;
        this.mContext = composeView.getContext();
        initViews(composeView, isStreamPlay);
    }

    private void getViewHolder(View p) {
        Object tag = p.getTag(R.id.id_video_preview_view_holder);
        if (tag instanceof a) {
            this.vh = (a) tag;
            return;
        }
        this.vh = new a(p);
        p.setTag(R.id.id_video_preview_view_holder, this.vh);
    }

    private void initViews(View p, boolean isStreamPlay) {
        getViewHolder(p);
        if (!isStreamPlay) {
            downloadPlay();
        } else {
            streamPlay();
        }
    }

    private void streamPlay() {
        this.vh.b();
        this.vh.c.setVisibility(0);
        goneView(this.vh.d);
        initVisiableModule(false, false);
    }

    private void downloadPlay() {
        this.vh.a();
        this.vh.d.setVisibility(0);
        goneView(this.vh.c);
        if (this.mVideoInfo.getMediaType() == 1) {
            initVisiableModule(true, false);
        } else if (this.mVideoInfo.getMediaType() == 2) {
            initVisiableModule(false, true);
        }
        this.vh.e.setVideoId(this.mVideoInfo.getPhotoPath());
    }

    private void goneView(View v) {
        if (v != null) {
            v.setVisibility(8);
        }
    }

    private void initVisiableModule(boolean isSmallVisiable, boolean isOriPVisiable) {
        int sv;
        int ov = 0;
        if (isSmallVisiable) {
            sv = 0;
        } else {
            sv = 8;
        }
        if (!isOriPVisiable) {
            ov = 8;
        }
        if (this.vh.f != null) {
            this.vh.f.setVisibility(sv);
        }
        if (this.vh.g != null) {
            this.vh.g.setVisibility(ov);
        }
    }

    /* access modifiers changed from: protected */
    public MultimediaVideoService getVideoService() {
        if (this.mVideoService == null) {
            this.mVideoService = (MultimediaVideoService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaVideoService.class.getName());
        }
        return this.mVideoService;
    }

    /* access modifiers changed from: protected */
    public void safeChaneViewState(final int state) {
        if (!this.mDestroyed.get()) {
            if (!isMainThread()) {
                this.mHandler.post(new Runnable() {
                    public final void run() {
                        BaseVideoPreviewCon.this.onChangeViewStateCalled(state);
                    }
                });
            } else {
                onChangeViewStateCalled(state);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void runOnUIThread(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            this.mHandler.post(r);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isWifiConnected() {
        NetworkInfo wifiNetwork = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getNetworkInfo(1);
        if (wifiNetwork == null || !wifiNetwork.isConnected()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void safeShowDownLoadError(APVideoDownloadRsp rsp) {
        String toastStr;
        if (!this.mDestroyed.get()) {
            int rtCode = rsp.getRetCode();
            if (rtCode != 0 && rtCode != 5) {
                if (rtCode == 11) {
                    toastStr = this.mContext.getString(R.string.file_not_exist);
                } else if (rtCode == 9 || rtCode == 10) {
                    toastStr = this.mContext.getString(R.string.network_error);
                } else if (rtCode == 7) {
                    toastStr = this.mContext.getString(R.string.video_file_expired_or_deleted);
                } else {
                    toastStr = this.mContext.getString(R.string.download_failed_try_again_later);
                }
                final String finalToastStr = toastStr;
                this.mHandler.post(new Runnable() {
                    public final void run() {
                        AUToast.makeToast(BaseVideoPreviewCon.this.mContext, 0, (CharSequence) finalToastStr, 1).show();
                    }
                });
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean safeInsidePlay(int state) {
        if (!this.mDestroyed.get()) {
            if (state == 5) {
                if (this.mFocusing.get()) {
                    threadSafeUpdateViews(state);
                    this.vh.e.setScreenOnWhilePlaying(true);
                    this.vh.e.start(this.mVideoInfo.getPhotoPath(), 0);
                    return true;
                }
            } else if (state == 7) {
                this.vh.e.stop();
                threadSafeUpdateViews(state);
                return true;
            } else if (state == 6) {
                this.vh.e.pause();
                threadSafeUpdateViews(state);
                return true;
            }
        }
        return false;
    }

    private void threadSafeUpdateViews(final int state) {
        if (isMainThread()) {
            onChangeViewStateCalled(state);
        } else {
            this.mHandler.post(new Runnable() {
                public final void run() {
                    BaseVideoPreviewCon.this.onChangeViewStateCalled(state);
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public void safeCallSystemPlay() {
        PhotoLogger.debug(TAG, "playLocalOriginalVideo() called.");
        if (this.mDestroyed.get()) {
            PhotoLogger.debug(TAG, "related video info has changed,just return");
        } else if (!this.mFocusing.get()) {
            PhotoLogger.debug(TAG, "current video view is not in focus,just return");
        } else {
            EnhancedVideoPlayView.callSystemPlay(this.mVideoInfo.getPhotoPath(), this.mContext);
        }
    }

    private boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public void destroy() {
        this.mDestroyed.set(true);
    }

    public void onFocus(boolean isAutoPlayOri) {
        this.mFocusing.set(true);
        if (this.vh.g != null) {
            int visibility = this.vh.g.getVisibility();
            this.vh.h.setVisibility(visibility);
            this.vh.i.setVisibility(visibility);
            return;
        }
        this.vh.h.setVisibility(8);
        this.vh.i.setVisibility(8);
    }

    public void onLoseFocus() {
        this.mFocusing.set(false);
    }
}
