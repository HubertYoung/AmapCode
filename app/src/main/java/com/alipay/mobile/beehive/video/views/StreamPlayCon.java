package com.alipay.mobile.beehive.video.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.VideoPlayParams;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnCompletionListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnInfoListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnPlayErrorListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnPreparedListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnProgressUpdateListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnSeekCompleteListener;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.plugin.H5PhotoPlugin;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.Map;

public class StreamPlayCon extends BaseVideoPreviewCon implements OnClickListener, OnCompletionListener, OnInfoListener, OnPlayErrorListener, OnPreparedListener, OnProgressUpdateListener, OnSeekCompleteListener {
    public static int ALWAYS_INVISIBLE = 1;
    public static int AUTO_SWITCH_VISIBILITY = 0;
    public static int CLICK_SWITCH_VISIBILITY = 2;
    private static final int DELAY_TO_AUTO_HIDE = 3000;
    private static final String TAG = "StreamPlayCon";
    private static final int VIDEO_STATE_BUFFERING = 0;
    private static final int VIDEO_STATE_ERROR = 4;
    private static final int VIDEO_STATE_INIT = 5;
    private static final int VIDEO_STATE_PAUSING = 3;
    private static final int VIDEO_STATE_PLAYING = 2;
    private static final int VIDEO_STATE_STOP = 1;
    private static Point sScreenWH;
    private boolean isLoopVideo = true;
    /* access modifiers changed from: private */
    public boolean isUserSeeking;
    /* access modifiers changed from: private */
    public boolean isVideoAvailable;
    /* access modifiers changed from: private */
    public boolean isVideoPrepared;
    private boolean mAutoDismiss = false;
    private Handler mAutoHideControlZoneHandler = new Handler(Looper.getMainLooper());
    private Handler mAutoHideMobileNetworkHintHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public int mCurrentState;
    /* access modifiers changed from: private */
    public long mDuration;
    private boolean mEnableFullScreen = false;
    /* access modifiers changed from: private */
    public Drawable mPausingDrawable;
    /* access modifiers changed from: private */
    public Drawable mPlayDrawable;
    /* access modifiers changed from: private */
    public String mPlayVideo;
    private OnSeekBarChangeListener mSeekListener = new OnSeekBarChangeListener() {
        public final void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            StreamPlayCon.this.isUserSeeking = fromUser;
        }

        public final void onStartTrackingTouch(SeekBar seekBar) {
            StreamPlayCon.this.pauseVideo(true);
        }

        public final void onStopTrackingTouch(SeekBar seekBar) {
            if (StreamPlayCon.this.isUserSeeking) {
                long seekTarget = (long) (((float) (StreamPlayCon.this.mDuration * ((long) seekBar.getProgress()))) / 100.0f);
                PhotoLogger.d(StreamPlayCon.TAG, "Call seek to position = " + seekTarget);
                StreamPlayCon.this.vh.h.seekTo(seekTarget);
                StreamPlayCon.this.setSeekBarEnable(false, "onStopTrackingTouch");
                StreamPlayCon.this.resumeVideo();
            }
        }
    };
    /* access modifiers changed from: private */
    public String mStopPlayVideo;
    private int mToolBarVisibility = AUTO_SWITCH_VISIBILITY;
    private int mTopBarVisibility = AUTO_SWITCH_VISIBILITY;
    private int mVideoHeight = -1;
    private int mVideoWidth = -1;
    /* access modifiers changed from: private */
    public a vh;

    private static class a {
        View a;
        TextView b;
        TextView c;
        TextView d;
        ImageView e;
        ImageView f;
        SeekBar g;
        SightVideoPlayView h;
        ViewGroup i;
        ViewGroup j;
        ProgressBar k;
        View l;
        View m;
        ViewGroup n;
        AUIconView o;
        TextView p;

        a(View parent) {
            this.a = parent;
            this.c = (TextView) parent.findViewById(R.id.tv_video_duration);
            this.d = (TextView) parent.findViewById(R.id.tv_video_played_time);
            this.e = (ImageView) parent.findViewById(R.id.iv_bottom_play_btn);
            this.f = (ImageView) parent.findViewById(R.id.iv_center_play_btn);
            this.g = (SeekBar) parent.findViewById(R.id.sb_progress_control);
            this.h = (SightVideoPlayView) parent.findViewById(R.id.v_sight_play);
            this.i = (ViewGroup) parent.findViewById(R.id.ll_error_hint);
            this.b = (TextView) parent.findViewById(R.id.tv_error_hint);
            this.k = (ProgressBar) parent.findViewById(R.id.pb_buffering);
            this.l = parent.findViewById(R.id.v_place_holder);
            this.m = parent.getRootView().findViewById(R.id.iv_download_entry);
            this.o = (AUIconView) parent.findViewById(R.id.iv_top_finish_play);
            this.p = (TextView) parent.findViewById(R.id.tv_mobile_network_hint);
            this.n = (ViewGroup) parent.findViewById(R.id.fl_top_control_zone);
            this.j = (ViewGroup) parent.findViewById(R.id.ll_video_control_zone);
        }
    }

    StreamPlayCon(View composeView, PhotoInfo videoInfo) {
        super(composeView, videoInfo, true);
        parseScreenSize(composeView);
        this.mPlayVideo = this.mContext.getString(R.string.str_start_play_video);
        this.mStopPlayVideo = this.mContext.getString(R.string.str_stop_play_video);
        parseVideoConfig(videoInfo);
        renderViews(composeView);
    }

    private void parseScreenSize(View composeView) {
        if (sScreenWH == null || sScreenWH.x <= 0 || sScreenWH.y <= 0) {
            sScreenWH = new Point();
            ((WindowManager) composeView.getContext().getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getSize(sScreenWH);
        }
    }

    private void parseVideoConfig(PhotoInfo videoInfo) {
        if (videoInfo.bizExtraParams != null) {
            this.mEnableFullScreen = videoInfo.bizExtraParams.getBoolean(PhotoParam.KEY_VIDEO_FULL_SCREEN, false);
            this.mToolBarVisibility = videoInfo.bizExtraParams.getInt(PhotoParam.KEY_VIDEO_TOOL_BAR_VISIBILITY_STYLE, AUTO_SWITCH_VISIBILITY);
            this.mTopBarVisibility = videoInfo.bizExtraParams.getInt(PhotoParam.KEY_VIDEO_TOP_BAR_VISIBILITY_STYLE, AUTO_SWITCH_VISIBILITY);
            this.mAutoDismiss = videoInfo.bizExtraParams.getBoolean(PhotoParam.SINGLE_VIDEO_AUTO_DISMISS_WHEN_PLAY_FINISHED, false);
            this.mVideoHeight = videoInfo.bizExtraParams.getInt(H5PhotoPlugin.KEY_VIDEO_HEIGHT, -1);
            this.mVideoWidth = videoInfo.bizExtraParams.getInt(H5PhotoPlugin.KEY_VIDEO_WIDTH, -1);
        }
    }

    private void renderViews(View p) {
        initViewHolder(p);
        this.mPlayDrawable = this.mContext.getResources().getDrawable(R.drawable.ic_video_play);
        this.mPausingDrawable = this.mContext.getResources().getDrawable(R.drawable.ic_video_pause);
        this.vh.e.setOnClickListener(this);
        this.vh.f.setOnClickListener(this);
        this.vh.i.setOnClickListener(this);
        this.vh.o.setOnClickListener(this);
        this.vh.a.setOnClickListener(this);
        this.vh.g.setOnSeekBarChangeListener(this.mSeekListener);
        setSeekBarEnable(false, "renderViews");
        adjustRightMargin();
        this.isVideoAvailable = getVideoService().isVideoAvailable(this.mVideoInfo.getPhotoPath());
        setViewState(5);
        setVideoParams();
    }

    private void adjustRightMargin() {
        this.vh.l.setVisibility(this.vh.m.getVisibility() == 0 ? 0 : 8);
    }

    private void initViewHolder(View p) {
        Object tag = p.getTag(R.id.id_stream_play_view_holder);
        if (tag instanceof a) {
            this.vh = (a) tag;
            return;
        }
        this.vh = new a(p);
        p.setTag(R.id.id_stream_play_view_holder, this.vh);
    }

    private void setVideoParams() {
        this.mDuration = this.mVideoInfo.getVideoDuration();
        if (!this.mEnableFullScreen || this.mVideoHeight <= 0 || this.mVideoWidth <= 0 || sScreenWH == null || sScreenWH.x <= 0 || sScreenWH.y <= 0) {
            this.vh.h.setAutoFitCenter(true);
        } else {
            this.vh.h.setCenterCropped(this.mVideoWidth, this.mVideoHeight, sScreenWH.x, sScreenWH.y);
        }
        this.vh.h.setKeepScreenOn(true);
        this.isLoopVideo = isLoopVideo();
        this.vh.h.setLooping(this.isLoopVideo);
        loadVideoThumb();
        setVideoParamsDepends(this.mVideoInfo.getPhotoPath());
        this.vh.h.setOnPreparedListener(this);
        this.vh.h.setOnCompletionListener(this);
        this.vh.h.setOnErrorListener(this);
        this.vh.h.setOnProgressUpateListener(this);
        this.vh.h.setOnInfoListener(this);
        this.vh.h.setOnSeekCompleteListener(this);
    }

    private boolean isLoopVideo() {
        boolean ret = true;
        if (this.mVideoInfo.bizExtraParams != null) {
            ret = this.mVideoInfo.bizExtraParams.getBoolean("isLoop", true);
        }
        if (!ret || this.mAutoDismiss) {
            return false;
        }
        return true;
    }

    private void cancelAutoHide() {
        this.mAutoHideControlZoneHandler.removeCallbacksAndMessages(null);
    }

    private void startAutoHide() {
        cancelAutoHide();
        this.mAutoHideControlZoneHandler.postDelayed(new Runnable() {
            public final void run() {
                StreamPlayCon.this.setControlZoneVisibility(4);
            }
        }, BalloonLayout.DEFAULT_DISPLAY_DURATION);
    }

    /* access modifiers changed from: private */
    public void setSeekBarEnable(final boolean isEnable, final String from) {
        if (!this.mDestroyed.get()) {
            runOnUIThread(new Runnable() {
                public final void run() {
                    PhotoLogger.d(StreamPlayCon.TAG, from + ":### Set seekBarEnable = " + isEnable);
                    StreamPlayCon.this.vh.g.setEnabled(isEnable);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void setViewState(final int state) {
        runOnUIThread(new Runnable() {
            public final void run() {
                if (!StreamPlayCon.this.mDestroyed.get()) {
                    PhotoLogger.d(StreamPlayCon.TAG, "state = " + state);
                    switch (state) {
                        case 0:
                            PhotoLogger.d(StreamPlayCon.TAG, "BUFFERING");
                            StreamPlayCon.this.vh.i.setVisibility(8);
                            StreamPlayCon.this.vh.f.setVisibility(8);
                            StreamPlayCon.this.vh.k.setVisibility(0);
                            StreamPlayCon.this.vh.e.setImageDrawable(StreamPlayCon.this.mPausingDrawable);
                            StreamPlayCon.this.vh.e.setContentDescription(StreamPlayCon.this.mStopPlayVideo);
                            break;
                        case 1:
                            PhotoLogger.d(StreamPlayCon.TAG, "STOP");
                            StreamPlayCon.this.vh.k.setVisibility(8);
                            StreamPlayCon.this.vh.k.setProgress(0);
                            StreamPlayCon.this.vh.f.setVisibility(8);
                            StreamPlayCon.this.vh.e.setImageDrawable(StreamPlayCon.this.mPlayDrawable);
                            StreamPlayCon.this.vh.e.setContentDescription(StreamPlayCon.this.mPlayVideo);
                            StreamPlayCon.this.vh.i.setVisibility(8);
                            break;
                        case 2:
                            PhotoLogger.d(StreamPlayCon.TAG, "PLAYING");
                            StreamPlayCon.this.vh.k.setVisibility(8);
                            StreamPlayCon.this.vh.f.setVisibility(8);
                            StreamPlayCon.this.vh.e.setImageDrawable(StreamPlayCon.this.mPausingDrawable);
                            StreamPlayCon.this.vh.e.setContentDescription(StreamPlayCon.this.mStopPlayVideo);
                            StreamPlayCon.this.vh.i.setVisibility(8);
                            break;
                        case 3:
                            PhotoLogger.d(StreamPlayCon.TAG, "PAUSING");
                            StreamPlayCon.this.vh.k.setVisibility(8);
                            StreamPlayCon.this.vh.f.setVisibility(0);
                            StreamPlayCon.this.vh.e.setImageDrawable(StreamPlayCon.this.mPlayDrawable);
                            StreamPlayCon.this.vh.e.setContentDescription(StreamPlayCon.this.mPlayVideo);
                            StreamPlayCon.this.vh.i.setVisibility(8);
                            break;
                        case 4:
                            PhotoLogger.d(StreamPlayCon.TAG, "ERROR");
                            StreamPlayCon.this.vh.j.setVisibility(8);
                            StreamPlayCon.this.vh.k.setVisibility(8);
                            StreamPlayCon.this.vh.k.setProgress(0);
                            StreamPlayCon.this.vh.i.setVisibility(0);
                            StreamPlayCon.this.vh.f.setVisibility(8);
                            StreamPlayCon.this.vh.e.setImageDrawable(StreamPlayCon.this.mPlayDrawable);
                            StreamPlayCon.this.vh.e.setContentDescription(StreamPlayCon.this.mPlayVideo);
                            break;
                        case 5:
                            PhotoLogger.d(StreamPlayCon.TAG, "INIT");
                            StreamPlayCon.this.vh.i.setVisibility(8);
                            StreamPlayCon.this.vh.f.setVisibility(StreamPlayCon.this.isVideoAvailable ? 8 : 0);
                            StreamPlayCon.this.vh.k.setVisibility(8);
                            StreamPlayCon.this.vh.e.setImageDrawable(StreamPlayCon.this.mPlayDrawable);
                            StreamPlayCon.this.vh.e.setContentDescription(StreamPlayCon.this.mPlayVideo);
                            StreamPlayCon.this.vh.g.setProgress(0);
                            StreamPlayCon.this.vh.d.setText(OriVideoPreviewCon.ZERO_DURATION);
                            break;
                    }
                    StreamPlayCon.this.mCurrentState = state;
                }
            }
        });
    }

    private void loadVideoThumb() {
        String path = this.mVideoInfo.getPhotoPath();
        if (this.mVideoInfo.bizExtraParams != null) {
            String p = this.mVideoInfo.bizExtraParams.getString("videoThumb");
            if (!TextUtils.isEmpty(p)) {
                path = p;
            }
        }
        getVideoService().loadVideoThumb(path, this.vh.h, null, null, ImageHelper.getBusinessId());
    }

    private void setVideoParamsDepends(String videoId) {
        if (TextUtils.isEmpty(videoId)) {
            return;
        }
        if (videoId.contains(MergeUtil.SEPARATOR_KV)) {
            VideoPlayParams params = new VideoPlayParams();
            params.mBizId = ImageHelper.getBusinessId();
            params.mEnableAudio = true;
            params.mVideoId = videoId.substring(0, videoId.indexOf(MergeUtil.SEPARATOR_KV));
            this.vh.h.setVideoParams(params);
        } else if (isHttpFile(videoId)) {
            PhotoLogger.d(TAG, "Http videoId = " + videoId);
            VideoPlayParams params2 = new VideoPlayParams();
            params2.mBizId = ImageHelper.getBusinessId();
            params2.mEnableAudio = true;
            params2.mVideoId = videoId;
            this.vh.h.setVideoParams(params2);
        } else {
            this.vh.h.setVideoId(videoId);
        }
    }

    private boolean isHttpFile(String videoId) {
        if (videoId.length() >= 4) {
            return "http".equalsIgnoreCase(TextUtils.substring(videoId, 0, 4));
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onChangeViewStateCalled(int state) {
    }

    public void onFocus(boolean isAutoPlayOri) {
        super.onFocus(isAutoPlayOri);
        playDepends(isAutoPlayOri);
    }

    public void onLoseFocus() {
        super.onLoseFocus();
        stopVideoPlay();
    }

    private void stopVideoPlay() {
        this.vh.h.stop();
        setViewState(1);
        broadCastAction("videoStopPlay");
        cancelAutoHide();
    }

    public void destroy() {
        super.destroy();
        PhotoLogger.d(TAG, "destroy:###");
        this.vh.h.setOnPreparedListener(null);
        this.vh.h.setOnErrorListener(null);
        this.vh.h.setOnProgressUpateListener(null);
        this.vh.h.setOnInfoListener(null);
        this.vh.h.setOnSeekCompleteListener(null);
        this.mAutoHideMobileNetworkHintHandler.removeCallbacksAndMessages(null);
        cancelAutoHide();
    }

    private void playDepends(boolean autoPlay) {
        boolean needDownloadByNetwork = false;
        if (isClickSwitch()) {
            setControlZoneVisibility(8);
        } else {
            setControlZoneVisibility(0);
        }
        if (!this.isVideoAvailable && !isWifiConnected()) {
            needDownloadByNetwork = true;
        }
        updateMobileNetworkHint(needDownloadByNetwork);
        if (autoPlay) {
            startPlayVideo();
            if (isClickSwitch()) {
                cancelAutoHide();
            }
        } else if (!needDownloadByNetwork) {
            startPlayVideo();
            if (isClickSwitch()) {
                cancelAutoHide();
            }
        } else {
            setViewState(5);
        }
    }

    private boolean isClickSwitch() {
        return this.mTopBarVisibility == CLICK_SWITCH_VISIBILITY || this.mToolBarVisibility == CLICK_SWITCH_VISIBILITY;
    }

    private void updateMobileNetworkHint(boolean isShow) {
        this.vh.p.setVisibility(isShow ? 0 : 8);
        this.mAutoHideMobileNetworkHintHandler.postDelayed(new Runnable() {
            public final void run() {
                StreamPlayCon.this.vh.p.setVisibility(8);
            }
        }, BalloonLayout.DEFAULT_DISPLAY_DURATION);
    }

    public void onError(final int i, final String s) {
        runOnUIThread(new Runnable() {
            public final void run() {
                PhotoLogger.d(StreamPlayCon.TAG, "onError:### i=" + i + ",desc = " + s);
                StreamPlayCon.this.vh.b.setText(StreamPlayCon.this.mContext.getResources().getString(i == -10000 ? R.string.str_failed_network_error : R.string.str_failed_other_reason));
                StreamPlayCon.this.setViewState(4);
            }
        });
    }

    public void onClick(View v) {
        if (v == this.vh.f) {
            startPlayVideo();
        } else if (v == this.vh.e) {
            if (this.vh.e.getDrawable() == this.mPlayDrawable) {
                startPlayVideo();
            } else if (!this.isVideoPrepared) {
                PhotoLogger.d(TAG, "Video is not prepared,ignore pause action.");
            } else {
                pauseVideo(false);
            }
        } else if (v == this.vh.i) {
            startPlayVideo();
        } else if (v == this.vh.o) {
            broadCastAction("videoCancelPlay");
            actionBack();
        } else if (v != this.vh.a) {
        } else {
            if (!hasControlZone()) {
                actionBack();
            } else {
                toggleControlZone();
            }
        }
    }

    private void actionBack() {
        if (this.mContext instanceof Activity) {
            ((Activity) this.mContext).onBackPressed();
        }
    }

    private boolean hasControlZone() {
        return (this.mToolBarVisibility == ALWAYS_INVISIBLE && this.mTopBarVisibility == ALWAYS_INVISIBLE) ? false : true;
    }

    private void toggleControlZone() {
        cancelAutoHide();
        int targetVisibility = this.vh.n.getVisibility() == 0 ? 4 : 0;
        setControlZoneVisibility(targetVisibility);
        if (this.vh.h.isPlaying() && targetVisibility == 0) {
            startAutoHide();
        }
    }

    /* access modifiers changed from: private */
    public void setControlZoneVisibility(int visibility) {
        int topBarVisibility;
        int bottomBarVisibility;
        if (this.mTopBarVisibility == ALWAYS_INVISIBLE) {
            topBarVisibility = 8;
        } else {
            topBarVisibility = visibility;
        }
        this.vh.n.setVisibility(topBarVisibility);
        if (this.mToolBarVisibility == ALWAYS_INVISIBLE) {
            bottomBarVisibility = 8;
        } else {
            bottomBarVisibility = visibility;
        }
        this.vh.j.setVisibility(bottomBarVisibility);
    }

    public void onProgressUpdate(final long l) {
        PhotoLogger.d(TAG, "progress = " + l);
        runOnUIThread(new Runnable() {
            public final void run() {
                if (StreamPlayCon.this.mFocusing.get()) {
                    String t = PhotoUtil.formatDurationWithZero(l);
                    if (!TextUtils.equals(t, StreamPlayCon.this.vh.d.getText())) {
                        StreamPlayCon.this.vh.g.setProgress((int) ((((float) l) / ((float) StreamPlayCon.this.mDuration)) * 100.0f));
                    }
                    StreamPlayCon.this.vh.d.setText(t);
                }
            }
        });
    }

    public void onPrepared(final Bundle bundle) {
        runOnUIThread(new Runnable() {
            public final void run() {
                PhotoLogger.d(StreamPlayCon.TAG, "onPrepared:###");
                StreamPlayCon.this.isVideoPrepared = true;
                StreamPlayCon.this.setSeekBarEnable(true, "onPrepared");
                if (bundle != null) {
                    long duration = bundle.getLong("duration");
                    if (duration > 0) {
                        StreamPlayCon.this.mDuration = duration;
                        StreamPlayCon.this.vh.c.setText(PhotoUtil.formatDurationWithZero(StreamPlayCon.this.mDuration));
                    }
                }
            }
        });
    }

    public boolean onInfo(int i, Bundle bundle) {
        PhotoLogger.d(TAG, "onInfo:### i=" + i);
        if (i == 3 || i == 702) {
            setViewState(2);
        } else if (i == 701) {
            setViewState(0);
        }
        return true;
    }

    public void onSeekComplete(Bundle bundle) {
        setSeekBarEnable(true, "onSeekComplete");
    }

    public void onCompletion(Bundle bundle) {
        runOnUIThread(new Runnable() {
            public final void run() {
                if (StreamPlayCon.this.mDestroyed.get()) {
                    PhotoLogger.d(StreamPlayCon.TAG, "onCompletion,but con destroyed!");
                } else {
                    StreamPlayCon.this.onVideoPlayComplete();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void onVideoPlayComplete() {
        PhotoLogger.d(TAG, "Video play completed.");
        broadCastAction("videoFinishPlay");
        if (this.mAutoDismiss) {
            actionBack();
        } else if (!this.isLoopVideo) {
            cancelAutoHide();
            setViewState(5);
            loadVideoThumb();
            if (this.vh.n.getVisibility() != 0) {
                toggleControlZone();
            }
        }
    }

    /* access modifiers changed from: private */
    public void pauseVideo(boolean isSeekingPause) {
        this.vh.h.pause();
        broadCastAction("videoPausePlay");
        if (!isSeekingPause) {
            setViewState(3);
        }
        cancelAutoHide();
    }

    private void startPlayVideo() {
        if (this.mCurrentState == 3) {
            resumeVideo();
            return;
        }
        this.vh.h.start();
        broadCastAction("videoStartPlay");
        startAutoHide();
    }

    /* access modifiers changed from: private */
    public void resumeVideo() {
        this.vh.h.resume();
        broadCastAction("videoStartPlay");
        startAutoHide();
    }

    private void broadCastAction(String action) {
        if (this.mVideoInfo != null && this.mVideoInfo.bizExtraParams != null) {
            broadCastMediaEvent(action, this.mVideoInfo.bizExtraParams.getInt(H5PhotoPlugin.KEY_MEDIA_FILE_INDEX), null);
        }
    }

    public static void broadCastMediaEvent(String event, int index, Map<String, String> extra) {
        Context context = LauncherApplicationAgent.getInstance().getApplicationContext();
        Intent intent = new Intent(H5PhotoPlugin.ACTION_MEDIA_BROWSE_EVENT);
        JSONObject msg = new JSONObject();
        msg.put((String) "event", (Object) event);
        msg.put((String) "index", (Object) Integer.valueOf(index));
        if (extra != null) {
            msg.putAll(extra);
        }
        intent.putExtra(H5PhotoPlugin.KEY_EVENT_TYPE, msg);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
