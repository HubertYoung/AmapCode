package com.alipay.zoloz.toyger.workspace.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisBehavTask;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.task.ActionFrame;
import com.alipay.mobile.security.bio.task.ActionType;
import com.alipay.mobile.security.bio.task.SubTask;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.FastBlur;
import com.alipay.mobile.security.faceauth.FaceService;
import com.alipay.zoloz.toyger.bean.ToygerFrame;
import com.alipay.zoloz.toyger.extservice.record.ToygerRecordService;
import com.alipay.zoloz.toyger.interfaces.ToygerCallback;
import com.alipay.zoloz.toyger.upload.UploadManager;
import com.alipay.zoloz.toyger.widget.RoundProgressBar;
import com.alipay.zoloz.toyger.widget.ToygerCirclePattern;
import com.alipay.zoloz.toyger.widget.WaveHelper;
import com.alipay.zoloz.toyger.workspace.FaceRemoteConfig;

public class ToygerBaseTask implements SubTask {
    protected String extInfoFormat = "{\"actcnt\":0,\"vidcnt\":%1$d,\"EyeLeftOcclussion\":%2$d,\"EyeRightOcclussion\":%3$d}";
    protected boolean hasBeHaviorInfo = true;
    protected ToygerFrame mBestToygerFrame;
    protected BioServiceManager mBioServiceManager;
    protected BisBehavTask mBisBehavTask = new BisBehavTask();
    protected int mBorderColor = Color.parseColor("#44FFFFFF");
    protected int mBorderWidth = 10;
    protected ImageView mBottomImage;
    protected TextView mBottomText;
    protected Context mContext;
    protected int mEyeLeftOcclussion;
    protected int mEyeRightOcclussion;
    protected FaceRemoteConfig mFaceRemoteConfig;
    protected FaceService mFaceService;
    protected RoundProgressBar mInnerRoundProgressBar;
    protected Handler mMainHandler = null;
    protected RoundProgressBar mOuterRoundBakProgressBar;
    protected RoundProgressBar mOuterRoundProgressBar;
    protected long mTaskEndTime;
    protected String mTaskName;
    protected long mTaskStartTime;
    protected TextView mTipView;
    protected ToygerCirclePattern mToygerCirclePattern;
    protected ToygerRecordService mToygerRecordService;
    protected UploadManager mUploadManager;
    protected int mVidcnt = 0;
    protected WaveHelper mWaveHelper;
    protected Handler mWorkspaceHandler;

    public BisBehavTask getBisBehavTask() {
        return this.mBisBehavTask;
    }

    public boolean isHasBeHaviorInfo() {
        return this.hasBeHaviorInfo;
    }

    public int getVidcnt() {
        return this.mVidcnt;
    }

    public ToygerBaseTask(BioServiceManager bioServiceManager, ToygerCirclePattern toygerCirclePattern, Handler handler, ToygerCallback toygerCallback) {
        this.mBioServiceManager = bioServiceManager;
        this.mToygerCirclePattern = toygerCirclePattern;
        this.mWorkspaceHandler = handler;
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mFaceService = (FaceService) bioServiceManager.getBioService(FaceService.class);
        this.mWaveHelper = new WaveHelper(toygerCirclePattern.getWaveView());
        this.mInnerRoundProgressBar = this.mToygerCirclePattern.getRoundProgressBarInner();
        this.mOuterRoundProgressBar = this.mToygerCirclePattern.getRoundProgressBar();
        this.mOuterRoundBakProgressBar = this.mToygerCirclePattern.getOuterBakRoundProgressBar();
        this.mBottomImage = this.mToygerCirclePattern.getBottomImage();
        this.mBottomText = this.mToygerCirclePattern.getBottomTextView();
        this.mTipView = this.mToygerCirclePattern.getTopTip();
        this.mContext = this.mBioServiceManager.getBioApplicationContext();
        this.mFaceRemoteConfig = toygerCallback.getRemoteConfig();
        this.mToygerRecordService = (ToygerRecordService) bioServiceManager.getBioService(ToygerRecordService.class);
    }

    public int init() {
        this.mTaskStartTime = System.currentTimeMillis();
        return 0;
    }

    public ActionType action(ActionFrame actionFrame) {
        return ActionType.RUN;
    }

    public int done() {
        this.mToygerCirclePattern.getTitleBar().setVisibility(8);
        if (this.mBestToygerFrame != null) {
            this.mUploadManager.uploadFaceInfo(this.mBestToygerFrame);
        }
        return 0;
    }

    public void stop() {
    }

    public void destroy() {
    }

    /* access modifiers changed from: protected */
    public void showBestFrameBlur(Bitmap bitmap) {
        if (bitmap != null) {
            Bitmap blur = blur(bitmap, 3.0f);
            bitmap.recycle();
            if (this.mMainHandler != null) {
                this.mMainHandler.post(new d(this, blur));
            }
        }
    }

    public static Bitmap blur(Bitmap bitmap, float f) {
        Throwable th;
        Bitmap bitmap2;
        Bitmap bitmap3 = null;
        try {
            Bitmap createBitmap = Bitmap.createBitmap((int) (((float) bitmap.getWidth()) / 6.0f), (int) (((float) bitmap.getHeight()) / 6.0f), Config.ARGB_4444);
            try {
                Canvas canvas = new Canvas(createBitmap);
                canvas.scale(0.16666667f, 0.16666667f);
                Paint paint = new Paint();
                paint.setFlags(2);
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                return FastBlur.doBlur(createBitmap, (int) f, true);
            } catch (OutOfMemoryError e) {
                Throwable th2 = e;
                bitmap2 = createBitmap;
                th = th2;
            }
        } catch (OutOfMemoryError e2) {
            Throwable th3 = e2;
            bitmap2 = bitmap3;
            th = th3;
        }
        BioLog.e(th);
        return bitmap2;
    }

    public void setUploadManager(UploadManager uploadManager) {
        this.mUploadManager = uploadManager;
    }
}
