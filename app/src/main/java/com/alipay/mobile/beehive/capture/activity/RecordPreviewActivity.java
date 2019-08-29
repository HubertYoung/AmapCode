package com.alipay.mobile.beehive.capture.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.modle.MediaInfo;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.capture.service.impl.CaptureServiceImpl;
import com.alipay.mobile.beehive.capture.utils.Logger;
import com.alipay.mobile.beehive.capture.utils.OtherUtils;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseActivity;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.common.lbs.LBSLocationManagerProxy;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import java.io.File;

public class RecordPreviewActivity extends BeehiveBaseActivity {
    public static final String EXTRA_KEY_MEDIA_INFO = "EXTRA_KEY_MEDIA_INFO";
    private static final String TAG = "RecordPreviewActivity";
    private View bottomBar;
    /* access modifiers changed from: private */
    public String mBusinessId;
    private Bundle mExtras;
    /* access modifiers changed from: private */
    public MediaInfo photoInfo;
    private ImageView previewImg;
    private TextView takeAgain;
    private TextView usePhoto;

    static class a implements Runnable {
        private String a;

        public a(String path) {
            this.a = OtherUtils.getAbsPath(path);
        }

        public final void run() {
            File img = new File(this.a);
            if (img.exists()) {
                if (img.delete()) {
                    Logger.debug(RecordPreviewActivity.TAG, "Delete image success.");
                } else {
                    Logger.debug(RecordPreviewActivity.TAG, "Delete image failed.");
                }
                OtherUtils.scanMediaFile(this.a);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_preview);
        if (checkParams()) {
            this.previewImg = (ImageView) findViewById(R.id.previewIv);
            this.takeAgain = (TextView) findViewById(R.id.takeAgain);
            this.bottomBar = findViewById(R.id.bottomBar);
            this.takeAgain.setOnClickListener(new OnClickListener() {
                public final void onClick(View v) {
                    RecordPreviewActivity.this.takePictureAgain();
                }
            });
            this.usePhoto = (TextView) findViewById(R.id.usePhoto);
            this.usePhoto.setOnClickListener(new OnClickListener() {
                public final void onClick(View v) {
                    CaptureServiceImpl.notifyAction(false, RecordPreviewActivity.this.photoInfo, true);
                    OtherUtils.scanMediaFile(OtherUtils.getAbsPath(RecordPreviewActivity.this.photoInfo.path));
                    RecordPreviewActivity.this.finish();
                }
            });
            renderViews(this.photoInfo);
        }
    }

    /* access modifiers changed from: private */
    public void takePictureAgain() {
        Intent recordIntent = new Intent(LauncherApplicationAgent.getInstance().getApplicationContext(), CaptureActivity.class);
        this.mExtras.remove("EXTRA_KEY_MEDIA_INFO");
        recordIntent.putExtras(this.mExtras);
        recordIntent.putExtra(CaptureParam.INIT_TYPE, 1);
        MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        microApplicationContext.startActivity(microApplicationContext.findTopRunningApp(), recordIntent);
        deleteImage(this.photoInfo.path);
        finish();
    }

    private void deleteImage(String path) {
        if (TextUtils.isEmpty(path)) {
            Logger.debug(TAG, "deleteImage called when path is Empty.");
        } else {
            BackgroundExecutor.execute((Runnable) new a(path));
        }
    }

    private boolean checkParams() {
        this.mExtras = getIntent().getExtras();
        if (this.mExtras == null) {
            finish();
            Logger.debug(TAG, "Extras is Null.");
            return false;
        }
        this.photoInfo = (MediaInfo) this.mExtras.getSerializable("EXTRA_KEY_MEDIA_INFO");
        if (this.photoInfo == null) {
            finish();
            Logger.debug(TAG, "PhotoInfo is Null.");
            return false;
        }
        this.photoInfo.location = LBSLocationManagerProxy.getInstance().getLastKnownLocation(getApplicationContext());
        if (this.photoInfo.location == null) {
            Logger.debug(TAG, "Get lbs location is null.");
        }
        this.mBusinessId = this.mExtras.getString("businessId");
        return true;
    }

    private void renderViews(MediaInfo info) {
        if (this.photoInfo.heightPx < this.photoInfo.widthPx) {
            this.previewImg.setScaleType(ScaleType.FIT_CENTER);
            this.bottomBar.setBackgroundColor(getResources().getColor(R.color.colorLandscapeBarBg));
        } else {
            this.previewImg.setScaleType(ScaleType.CENTER_CROP);
            this.bottomBar.setBackgroundColor(getResources().getColor(R.color.colorPortraitBarBg));
        }
        getImageService().loadOriginalImage(info.path, this.previewImg, null, null, this.mBusinessId);
    }

    /* access modifiers changed from: private */
    public MultimediaImageService getImageService() {
        MultimediaImageService ret = (MultimediaImageService) MicroServiceUtil.getMicroService(MultimediaImageService.class);
        if (ret == null) {
            Logger.warn(TAG, "MultimediaImageService is Null.");
        }
        return ret;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mayPreloadThumbnail();
        }
    }

    private void mayPreloadThumbnail() {
        final int thumbnailSize = this.mExtras.getInt(CaptureParam.PRE_LOAD_THUMBNAIL_SIZE, -1);
        if (thumbnailSize > 0) {
            new Handler().post(new Runnable() {
                public final void run() {
                    Logger.debug(RecordPreviewActivity.TAG, "Perform preload thumbnail,path = " + RecordPreviewActivity.this.photoInfo.path + "size = " + thumbnailSize);
                    RecordPreviewActivity.this.getImageService().loadImage(RecordPreviewActivity.this.photoInfo.path, null, null, null, thumbnailSize, thumbnailSize, null, new Size(RecordPreviewActivity.this.photoInfo.widthPx, RecordPreviewActivity.this.photoInfo.heightPx), RecordPreviewActivity.this.mBusinessId);
                }
            });
        }
    }

    public void onBackPressed() {
        takePictureAgain();
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b3485";
    }
}
