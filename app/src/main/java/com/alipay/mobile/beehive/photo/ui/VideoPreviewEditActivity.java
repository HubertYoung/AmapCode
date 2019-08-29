package com.alipay.mobile.beehive.photo.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoCutCallback;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoEditor;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoThumbnailListener;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoCutReq;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoCutRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoThumbnailReq;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoThumbnailRsp;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnProgressUpdateListener;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseFragmentActivity;
import com.alipay.mobile.beehive.photo.data.VideoEditInfo;
import com.alipay.mobile.beehive.photo.util.CommonUtils;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.VideoUtils;
import com.alipay.mobile.beehive.photo.view.video.VideoCompressDialog;
import com.alipay.mobile.beehive.photo.view.video.VideoCutWrapView;
import com.alipay.mobile.beehive.photo.view.video.VideoCutWrapView.OnVideoEditInfoUpdateListener;
import com.alipay.mobile.beehive.photo.view.video.VideoCutWrapView.VideoThumbAdapter;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.util.MicroServiceUtil;

public class VideoPreviewEditActivity extends BeehiveBaseFragmentActivity implements OnClickListener, APVideoCutCallback {
    private static final String BIZ_BEEHIVE_VIDEO_EDIT = "BIZ_BEEHIVE_VIDEO_EDIT";
    private static final String TAG = "VideoPreviewEditActivity";
    private Button btnDone;
    /* access modifiers changed from: private */
    public int cutBeginTime;
    /* access modifiers changed from: private */
    public int cutEndTime;
    /* access modifiers changed from: private */
    public long cutStartAt;
    private ImageButton ibBack;
    /* access modifiers changed from: private */
    public boolean isOnEditing;
    /* access modifiers changed from: private */
    public boolean isResumed;
    private boolean isShowTimeIndicator;
    private OnVideoEditInfoUpdateListener mEditUpdateListener = new OnVideoEditInfoUpdateListener() {
        public final void onVideoEditFinish(int begin, int end) {
            VideoPreviewEditActivity.this.isOnEditing = false;
            VideoPreviewEditActivity.this.cutBeginTime = begin;
            VideoPreviewEditActivity.this.cutEndTime = end;
            PhotoLogger.d(VideoPreviewEditActivity.TAG, "-> Start play at " + begin + " to " + end);
            if (VideoPreviewEditActivity.this.isResumed) {
                VideoPreviewEditActivity.this.videoPlayView.reset();
                VideoPreviewEditActivity.this.doPlay(VideoPreviewEditActivity.this.cutBeginTime);
            }
            VideoPreviewEditActivity.this.pendingUpdateTimeIndicator(begin, end);
        }

        public final void onEditStart(boolean isWindowResize) {
            VideoPreviewEditActivity.this.isOnEditing = true;
            VideoPreviewEditActivity.this.videoCutView.setPlayingProgress(0);
            if (isWindowResize) {
                VideoPreviewEditActivity.this.videoPlayView.pause();
            }
        }
    };
    /* access modifiers changed from: private */
    public APVideoEditor mEditor;
    private OnProgressUpdateListener mPlayProgressListener = new OnProgressUpdateListener() {
        public final void onProgressUpdate(final long l) {
            VideoPreviewEditActivity.this.runOnUiThread(new Runnable() {
                public final void run() {
                    if (!VideoPreviewEditActivity.this.isOnEditing) {
                        VideoPreviewEditActivity.this.videoCutView.setPlayingProgress((int) ((((float) (l - ((long) VideoPreviewEditActivity.this.cutBeginTime))) / ((float) (VideoPreviewEditActivity.this.cutEndTime - VideoPreviewEditActivity.this.cutBeginTime))) * 100.0f));
                        if (l >= ((long) VideoPreviewEditActivity.this.cutEndTime) && VideoPreviewEditActivity.this.isResumed) {
                            VideoPreviewEditActivity.this.videoPlayView.reset();
                            VideoPreviewEditActivity.this.doPlay(VideoPreviewEditActivity.this.cutBeginTime);
                        }
                    }
                }
            });
        }
    };
    /* access modifiers changed from: private */
    public VideoCompressDialog mProgressDialog;
    private APVideoThumbnailListener mThumbLoadListener = new APVideoThumbnailListener() {
        public final void onGetThumbnail(final APVideoThumbnailReq req, final APVideoThumbnailRsp rsp) {
            if (!VideoPreviewEditActivity.this.isFinishing()) {
                VideoPreviewEditActivity.this.runOnUiThread(new Runnable() {
                    public final void run() {
                        if (req instanceof a) {
                            ((a) req).a(rsp.bitmap);
                        }
                    }
                });
            }
        }
    };
    /* access modifiers changed from: private */
    public VideoEditInfo mVideoInfo;
    private String mVideoPath;
    private MultimediaVideoService mVideoService;
    /* access modifiers changed from: private */
    public int mVideoTimeLimit;
    private TextView tvTimeIndicator;
    private TextView tvTitle;
    /* access modifiers changed from: private */
    public VideoCutWrapView videoCutView;
    /* access modifiers changed from: private */
    public SightVideoPlayView videoPlayView;

    class a extends APVideoThumbnailReq {
        private ImageView b;

        public a(ImageView target) {
            this.b = target;
            this.b.setImageBitmap(null);
            this.b.setTag(this);
        }

        private boolean a() {
            return this.b.getTag() == this;
        }

        public final void a(Bitmap bmp) {
            if (a()) {
                this.b.setImageBitmap(bmp);
            } else {
                PhotoLogger.d(VideoPreviewEditActivity.TAG, "Target changed.");
            }
        }
    }

    /* access modifiers changed from: private */
    public void pendingUpdateTimeIndicator(int begin, int end) {
        if (this.tvTimeIndicator.getVisibility() != 0) {
            return;
        }
        if (this.mVideoInfo == null || this.mVideoInfo.videoDuration <= 0) {
            this.tvTimeIndicator.setText("");
            return;
        }
        float duration = ((float) Math.round(((float) this.mVideoInfo.videoDuration) / 100.0f)) / 10.0f;
        this.tvTimeIndicator.setText((((float) Math.round(((float) (end - begin)) / 100.0f)) / 10.0f) + "秒/" + duration + "秒");
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!parseParams()) {
            PhotoLogger.w(TAG, "Invalid params,finish activity!");
            finish();
            return;
        }
        setContentView(R.layout.activity_video_preview_edit);
        this.ibBack = (ImageButton) findViewById(R.id.bt_back);
        CommonUtils.setTitleBarBackDrawable(this.ibBack);
        this.ibBack.setOnClickListener(this);
        this.btnDone = (Button) findViewById(R.id.bt_finish);
        this.btnDone.setOnClickListener(this);
        this.tvTitle = (TextView) findViewById(R.id.tv_index);
        this.tvTitle.setText(getString(R.string.str_video_preview));
        this.tvTimeIndicator = (TextView) findViewById(R.id.tv_time_indicator);
        this.tvTimeIndicator.setVisibility(this.isShowTimeIndicator ? 0 : 4);
        this.mVideoService = (MultimediaVideoService) MicroServiceUtil.getMicroService(MultimediaVideoService.class);
        this.videoCutView = (VideoCutWrapView) findViewById(R.id.video_cut_view);
        this.videoPlayView = (SightVideoPlayView) findViewById(R.id.video_play_view);
        this.videoPlayView.setOnProgressUpateListener(this.mPlayProgressListener);
        this.videoCutView.setOnVideoEditInfoUpdateListener(this.mEditUpdateListener);
        this.mEditor = this.mVideoService.getVideoEditor(this.mVideoPath, BIZ_BEEHIVE_VIDEO_EDIT);
        int realDuration = this.mEditor.getVideoInfo().duration;
        PhotoLogger.d(TAG, "Before = " + this.mVideoInfo.videoDuration + "->After = " + realDuration);
        this.mVideoInfo.videoDuration = (long) realDuration;
        this.mEditor.setVideoThumbnalListener(this.mThumbLoadListener);
        VideoCutWrapView videoCutWrapView = this.videoCutView;
        VideoCutWrapView videoCutWrapView2 = this.videoCutView;
        videoCutWrapView2.getClass();
        videoCutWrapView.setVideoThumbAdapter(new VideoThumbAdapter(videoCutWrapView2) {
            {
                x0.getClass();
            }

            public final int getVideoDuration() {
                return (int) VideoPreviewEditActivity.this.mVideoInfo.videoDuration;
            }

            public final int getMaxCutDuration() {
                return VideoPreviewEditActivity.this.mVideoTimeLimit;
            }

            public final void onBindImage(ImageView view, float thumbAtTime, int thumbWidth, int thumbHeight) {
                PhotoLogger.d(VideoPreviewEditActivity.TAG, "Load thumb at time = " + thumbAtTime);
                a req = new a(view);
                req.position = (long) thumbAtTime;
                req.targetWidth = thumbWidth;
                req.targetHeight = thumbHeight;
                VideoPreviewEditActivity.this.mEditor.getVideoThumbnail(req);
            }
        });
        this.videoPlayView.setAutoFitCenter(true);
        this.videoPlayView.setLooping(true);
        this.videoPlayView.setFastPlay(1);
    }

    private boolean parseParams() {
        this.mVideoInfo = (VideoEditInfo) getIntent().getSerializableExtra(VideoPreviewActivity.KEY_EXTRA_VIDEO_INFO);
        this.mVideoTimeLimit = getIntent().getIntExtra(VideoPreviewActivity.KEY_EXTRA_TIME_LIMIT, 10000);
        PhotoLogger.d(TAG, "Video time limit = " + this.mVideoTimeLimit);
        if (this.mVideoInfo == null) {
            PhotoLogger.w(TAG, "Params invalid.");
            return false;
        }
        this.mVideoPath = VideoUtils.getVideoAbsPath(this.mVideoInfo);
        if (TextUtils.isEmpty(this.mVideoPath)) {
            PhotoLogger.w(TAG, "Video path invalid,which = " + this.mVideoPath);
            return false;
        }
        this.isShowTimeIndicator = getIntent().getBooleanExtra(PhotoParam.KEY_SHOW_VIDEO_TIME_INDICATOR, false);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.mEditor != null) {
            PhotoLogger.d(TAG, "Release video editor.");
            this.mEditor.release();
        }
    }

    public void onClick(View v) {
        if (v == this.btnDone) {
            doCutVideo();
        } else if (v == this.ibBack) {
            finish();
        }
    }

    private void doCutVideo() {
        APVideoCutReq cutReq = new APVideoCutReq();
        cutReq.startPositon = (long) this.cutBeginTime;
        cutReq.endPosition = (long) this.cutEndTime;
        if (this.videoPlayView.isPlaying()) {
            this.videoPlayView.pause();
        }
        this.mProgressDialog = VideoCompressDialog.buildAndShow(this);
        this.cutStartAt = System.currentTimeMillis();
        this.mEditor.cutVideo(cutReq, this);
    }

    public void onVideoCutFinished(final APVideoCutRsp rsp) {
        runOnUiThread(new Runnable() {
            public final void run() {
                VideoPreviewEditActivity.this.mProgressDialog.dismiss();
                if (rsp.errCode < 0) {
                    PhotoLogger.d(VideoPreviewEditActivity.TAG, "Cut video error,code = " + rsp.errCode);
                    VideoPreviewActivity.alertCytFailedByErrorCode(rsp.errCode, VideoPreviewEditActivity.this);
                    return;
                }
                PhotoLogger.d(VideoPreviewEditActivity.TAG, "Video cut cost: " + (System.currentTimeMillis() - VideoPreviewEditActivity.this.cutStartAt));
                PhotoInfo afterCut = VideoEditInfo.rollback(VideoPreviewEditActivity.this.mVideoInfo);
                afterCut.setPhotoPath(rsp.id);
                afterCut.setVideoDuration(rsp.end - rsp.start);
                int[] wh = VideoUtils.getWidthAndHeightConsiderRotation(rsp.targetWidht, rsp.targetHeight, rsp.rotation);
                afterCut.setVideoWidth(wh[0]);
                afterCut.setVideoHeight(wh[1]);
                VideoPreviewActivity.notifyResult(VideoPreviewEditActivity.this.getIntent(), afterCut);
                VideoPreviewEditActivity.this.setResult(-1);
                VideoPreviewEditActivity.this.finish();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.isResumed = false;
        if (this.videoPlayView.isPlaying()) {
            PhotoLogger.d(TAG, "onPause, stop play.");
            this.videoPlayView.pause();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.isResumed = true;
        if (this.cutEndTime <= 0) {
            PhotoLogger.d(TAG, "OnResume,but cutEndTime invalid,do nothing");
        } else if (!this.videoPlayView.isPlaying()) {
            PhotoLogger.d(TAG, "onResume, start play.");
            doPlay(this.cutBeginTime);
        }
    }

    /* access modifiers changed from: private */
    public void doPlay(int beginTime) {
        PhotoLogger.d(TAG, "doPlay called.");
        this.videoPlayView.start(this.mVideoPath, (long) beginTime);
    }

    public void onProgress(APVideoCutRsp rsp) {
        PhotoLogger.d(TAG, "Progress = " + rsp.progress);
        if (this.mProgressDialog != null) {
            this.mProgressDialog.updateProgress(rsp.progress);
        }
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b3492";
    }
}
