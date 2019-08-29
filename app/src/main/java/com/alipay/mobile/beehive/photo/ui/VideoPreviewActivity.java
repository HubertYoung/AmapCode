package com.alipay.mobile.beehive.photo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.video.CompressLevel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoCutCallback;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoEditor;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoCutReq;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoCutRsp;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseFragmentActivity;
import com.alipay.mobile.beehive.photo.data.PhotoContext;
import com.alipay.mobile.beehive.photo.data.VideoEditInfo;
import com.alipay.mobile.beehive.photo.util.CommonUtils;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.VideoUtils;
import com.alipay.mobile.beehive.photo.view.video.VideoCompressDialog;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.beehive.video.views.EnhancedVideoPlayView;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;

public class VideoPreviewActivity extends BeehiveBaseFragmentActivity implements OnClickListener, APVideoCutCallback {
    public static final String KEY_EXTRA_TIME_LIMIT = "KEY_EXTRA_TIME_LIMIT";
    public static final String KEY_EXTRA_VIDEO_INFO = "KEY_EXTRA_VIDEO_INFO";
    public static final String KEY_HIDE_EDIT = "hideEdit";
    private static final String TAG = "VideoPreviewActivity";
    private Button btnDone;
    /* access modifiers changed from: private */
    public long cutStartAt;
    private ImageButton ibBack;
    private boolean isHideEdit;
    private ImageView ivPlayBtn;
    private ImageView ivVideoCover;
    private CompressLevel mCompressLevel = CompressLevel.V540P;
    /* access modifiers changed from: private */
    public APVideoEditor mEditor;
    /* access modifiers changed from: private */
    public VideoCompressDialog mProgressDialog;
    /* access modifiers changed from: private */
    public VideoEditInfo mVideoInfo;
    private MultimediaVideoService mVideoService;
    private int mVideoTimeLimit;
    private View needCutZone;
    private View noNeedCutZone;
    private TextView tvEditNeed;
    private TextView tvEditNeedHint;
    private TextView tvEditNoNeed;
    private TextView tvTitle;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);
        if (!parseParams()) {
            finish();
            return;
        }
        this.ibBack = (ImageButton) findViewById(R.id.bt_back);
        CommonUtils.setTitleBarBackDrawable(this.ibBack);
        this.btnDone = (Button) findViewById(R.id.bt_finish);
        this.tvTitle = (TextView) findViewById(R.id.tv_index);
        this.tvTitle.setText(getString(R.string.str_video_preview));
        this.ivVideoCover = (ImageView) findViewById(R.id.iv_video_cover);
        this.ivPlayBtn = (ImageView) findViewById(R.id.iv_play_btn);
        this.needCutZone = findViewById(R.id.fl_need_cut_zone);
        this.noNeedCutZone = findViewById(R.id.fl_no_need_cut_zone);
        this.tvEditNeed = (TextView) findViewById(R.id.tv_edit_need);
        this.tvEditNeedHint = (TextView) findViewById(R.id.tv_edit_hint);
        this.tvEditNoNeed = (TextView) findViewById(R.id.tv_edit_no_need);
        this.ibBack.setOnClickListener(this);
        this.btnDone.setOnClickListener(this);
        this.ivPlayBtn.setOnClickListener(this);
        this.tvEditNoNeed.setOnClickListener(this);
        this.tvEditNeed.setOnClickListener(this);
        this.tvEditNeedHint.setText(String.format(getResources().getString(R.string.str_edit_video_hint), new Object[]{String.valueOf(this.mVideoTimeLimit / 1000)}));
        checkTimeLimit();
        loadVideoThumb();
    }

    private void checkTimeLimit() {
        if (getFormatDuration(this.mVideoInfo.videoDuration) > ((long) this.mVideoTimeLimit)) {
            this.needCutZone.setVisibility(0);
            this.noNeedCutZone.setVisibility(8);
            this.btnDone.setEnabled(false);
        } else {
            this.btnDone.setEnabled(true);
            this.needCutZone.setVisibility(8);
            this.noNeedCutZone.setVisibility(0);
        }
        if (this.isHideEdit) {
            this.btnDone.setEnabled(true);
            this.needCutZone.setVisibility(8);
            this.noNeedCutZone.setVisibility(8);
        }
    }

    private long getFormatDuration(long d) {
        return (long) (Math.round(((float) d) / 1000.0f) * 1000);
    }

    private boolean parseParams() {
        this.mVideoInfo = (VideoEditInfo) getIntent().getSerializableExtra(KEY_EXTRA_VIDEO_INFO);
        this.mVideoTimeLimit = getIntent().getIntExtra(KEY_EXTRA_TIME_LIMIT, 10000);
        this.isHideEdit = getIntent().getBooleanExtra(KEY_HIDE_EDIT, false);
        parseCompressLevel();
        if (this.mVideoInfo != null && !TextUtils.isEmpty(this.mVideoInfo.path)) {
            return true;
        }
        PhotoLogger.w(TAG, "Params invalid.");
        return false;
    }

    private void parseCompressLevel() {
        switch (getIntent().getIntExtra(PhotoParam.KEY_VIDEO_COMPRESS_LEVEL, CompressLevel.V540P.getValue())) {
            case 0:
                this.mCompressLevel = CompressLevel.V320P;
                return;
            case 1:
                this.mCompressLevel = CompressLevel.V540P;
                return;
            case 2:
                this.mCompressLevel = CompressLevel.V720P;
                return;
            case 3:
                this.mCompressLevel = CompressLevel.V1080P;
                return;
            default:
                return;
        }
    }

    private void loadVideoThumb() {
        if (this.mVideoService == null) {
            this.mVideoService = (MultimediaVideoService) MicroServiceUtil.getMicroService(MultimediaVideoService.class);
        }
        this.mVideoService.loadAlbumVideo(this.mVideoInfo.path, this.ivVideoCover, Integer.valueOf(640), Integer.valueOf(640), null, new APImageDownLoadCallback() {
            public final void onSucc(APImageDownloadRsp arg0) {
                PhotoLogger.debug(VideoPreviewActivity.TAG, "load thumbnail done,path = " + VideoPreviewActivity.this.mVideoInfo.path);
            }

            public final void onProcess(String arg0, int arg1) {
            }

            public final void onError(APImageDownloadRsp arg0, Exception arg1) {
                PhotoLogger.error((String) VideoPreviewActivity.TAG, "download video thumbnail error. video path = " + VideoPreviewActivity.this.mVideoInfo.path);
            }
        }, ImageHelper.getBusinessId());
    }

    public void onClick(View v) {
        if (v == this.ibBack) {
            finish();
        } else if (v == this.btnDone) {
            doCut();
        } else if (v == this.ivPlayBtn) {
            EnhancedVideoPlayView.callSystemPlay(this.mVideoInfo.path, this);
        } else if (v == this.tvEditNeed || v == this.tvEditNoNeed) {
            Intent intent = new Intent(getApplicationContext(), VideoPreviewEditActivity.class);
            intent.putExtra(KEY_EXTRA_VIDEO_INFO, this.mVideoInfo);
            intent.putExtra(KEY_EXTRA_TIME_LIMIT, this.mVideoTimeLimit);
            intent.putExtra(PhotoParam.CONTEXT_INDEX, getIntent().getStringExtra(PhotoParam.CONTEXT_INDEX));
            intent.putExtra(PhotoParam.KEY_SHOW_VIDEO_TIME_INDICATOR, getIntent().getBooleanExtra(PhotoParam.KEY_SHOW_VIDEO_TIME_INDICATOR, false));
            startActivityForResult(intent, 1001);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001 && resultCode == -1) {
            setResult(-1);
            finish();
        }
    }

    public static void notifyResult(Intent intent, PhotoInfo videoInfo) {
        String photoContextIndex = intent.getStringExtra(PhotoParam.CONTEXT_INDEX);
        if (PhotoContext.contextMap == null || !PhotoContext.contextMap.containsKey(photoContextIndex)) {
            PhotoLogger.w(TAG, "notifyResult Failed");
        } else {
            PhotoContext.contextMap.get(photoContextIndex).notifyVideoSelected(videoInfo);
        }
    }

    private void doCut() {
        this.mEditor = this.mVideoService.getVideoEditor(VideoUtils.getVideoAbsPath(this.mVideoInfo), "");
        pendingFixDuration();
        APVideoCutReq cutReq = new APVideoCutReq();
        cutReq.startPositon = 0;
        cutReq.endPosition = this.mVideoInfo.videoDuration;
        cutReq.quality = this.mCompressLevel;
        this.mProgressDialog = VideoCompressDialog.buildAndShow(this);
        this.cutStartAt = System.currentTimeMillis();
        this.mEditor.cutVideo(cutReq, this);
    }

    private void pendingFixDuration() {
        if (this.mVideoInfo.videoDuration <= 0) {
            int realDuration = this.mEditor.getVideoInfo().duration;
            PhotoLogger.d(TAG, "pendingFixDuration:### Before = " + this.mVideoInfo.videoDuration + "->After = " + realDuration);
            this.mVideoInfo.videoDuration = (long) realDuration;
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        PhotoLogger.d(TAG, "onDestroy:###");
        super.onDestroy();
        if (this.mEditor != null) {
            PhotoLogger.d(TAG, "Release editor.");
            this.mEditor.release();
        }
    }

    public void onVideoCutFinished(final APVideoCutRsp rsp) {
        runOnUiThread(new Runnable() {
            public final void run() {
                if (VideoPreviewActivity.this.mEditor != null) {
                    PhotoLogger.d(VideoPreviewActivity.TAG, "Release editor.");
                    VideoPreviewActivity.this.mEditor.release();
                }
                VideoPreviewActivity.this.mProgressDialog.dismiss();
                if (rsp.errCode < 0) {
                    PhotoLogger.d(VideoPreviewActivity.TAG, "Cut video error,code = " + rsp.errCode);
                    VideoPreviewActivity.alertCytFailedByErrorCode(rsp.errCode, VideoPreviewActivity.this);
                    return;
                }
                PhotoLogger.d(VideoPreviewActivity.TAG, "Video cut cost: " + (System.currentTimeMillis() - VideoPreviewActivity.this.cutStartAt));
                PhotoInfo afterCut = VideoEditInfo.rollback(VideoPreviewActivity.this.mVideoInfo);
                afterCut.setPhotoPath(rsp.id);
                afterCut.setVideoDuration(rsp.end - rsp.start);
                int[] wh = VideoUtils.getWidthAndHeightConsiderRotation(rsp.targetWidht, rsp.targetHeight, rsp.rotation);
                afterCut.setVideoWidth(wh[0]);
                afterCut.setVideoHeight(wh[1]);
                VideoPreviewActivity.notifyResult(VideoPreviewActivity.this.getIntent(), afterCut);
                VideoPreviewActivity.this.setResult(-1);
                VideoPreviewActivity.this.finish();
            }
        });
    }

    static void alertCytFailedByErrorCode(int code, BaseFragmentActivity activity) {
        int toAlertStringId = R.string.str_cut_video_failed_unknow;
        if (code == -10) {
            toAlertStringId = R.string.str_cut_video_failed_size_too_large;
        } else if (code == -103) {
            toAlertStringId = R.string.str_cut_video_failed_invalid_format;
        }
        activity.alert("", activity.getString(toAlertStringId), activity.getString(R.string.confirm), null, "", null);
    }

    public void onProgress(APVideoCutRsp rsp) {
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
        return "a310.b3487";
    }
}
