package com.alipay.zoloz.toyger.workspace.task;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.task.ActionFrame;
import com.alipay.mobile.security.bio.task.ActionType;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.StringUtil;
import com.alipay.mobile.security.faceauth.FaceDetectType;
import com.alipay.zoloz.toyger.R;
import com.alipay.zoloz.toyger.ToygerService;
import com.alipay.zoloz.toyger.bean.FrameType;
import com.alipay.zoloz.toyger.bean.ToygerFrame;
import com.alipay.zoloz.toyger.extservice.record.ToygerRecordService;
import com.alipay.zoloz.toyger.interfaces.ToygerCallback;
import com.alipay.zoloz.toyger.widget.ToygerCirclePattern;
import java.util.Map;

public class CherryCaptureTask extends ToygerBaseTask {
    private String bottomText;
    private int imageIndex;
    /* access modifiers changed from: private */
    public boolean isDarkScreen = false;
    /* access modifiers changed from: private */
    public boolean isShowNoFace = false;
    private boolean isShowProcessBar;
    private boolean mIsAuthInBackground = false;
    private float mQuality = 0.0f;
    private ToygerRecordService mToygerRecordService;
    private String topText;

    public CherryCaptureTask(BioServiceManager bioServiceManager, ToygerCirclePattern toygerCirclePattern, Handler handler, ToygerCallback toygerCallback) {
        super(bioServiceManager, toygerCirclePattern, handler, toygerCallback);
        this.mToygerRecordService = (ToygerRecordService) bioServiceManager.getBioService(ToygerRecordService.class);
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.isShowProcessBar = this.mFaceRemoteConfig.getColl().isProgressbar();
        this.bottomText = this.mFaceRemoteConfig.getColl().getBottomText();
        this.topText = this.mFaceRemoteConfig.getColl().getTopText();
        this.imageIndex = this.mFaceRemoteConfig.getColl().getImageIndex();
        Map<String, String> extProperty = toygerCallback.getAppDescription().getExtProperty();
        if (extProperty == null || extProperty.isEmpty() || !extProperty.containsKey(BioDetector.EXT_KEY_AUTH_IN_BACKGROUND)) {
            this.mIsAuthInBackground = false;
        } else {
            this.mIsAuthInBackground = Boolean.parseBoolean(extProperty.get(BioDetector.EXT_KEY_AUTH_IN_BACKGROUND));
        }
    }

    public int init() {
        this.mTaskName = "cherryDetectTask";
        if (this.mFaceService != null) {
            this.mFaceService.setDetectType(FaceDetectType.BLINK);
        }
        this.mToygerCirclePattern.showProcessBar(0.0f);
        this.mToygerCirclePattern.getTitleBar().setVisibility(0);
        if ("Cherry".equalsIgnoreCase("Cherry")) {
            this.mToygerCirclePattern.getTitleBar().setSoundButton(8);
            this.mOuterRoundProgressBar.setVisibility(0);
            this.mOuterRoundBakProgressBar.setVisibility(8);
        } else {
            this.mToygerCirclePattern.getTitleBar().setTimeOut("120S");
        }
        this.mToygerCirclePattern.getOuterBakRoundProgressBar().setRoundColor(Color.parseColor("#E5BA84"));
        this.mWaveHelper.setBorder(this.mBorderWidth, this.mBorderColor);
        this.mWaveHelper.cancel();
        this.mToygerCirclePattern.getGuassianBackground().setVisibility(8);
        this.mTipView.setVisibility(4);
        this.mToygerRecordService.write(ToygerRecordService.DETECT_COND_START);
        if (!StringUtil.isNullorEmpty(this.bottomText)) {
            this.mBottomText.setText(this.bottomText);
        }
        if (!StringUtil.isNullorEmpty(this.topText)) {
            this.mTipView.setText(this.topText);
        }
        if (this.imageIndex == 1) {
            this.mBottomImage.setBackgroundDrawable(new BitmapDrawable(this.mContext.getResources(), BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.face_circle_people2)));
        }
        return super.init();
    }

    public ActionType action(ActionFrame actionFrame) {
        ToygerFrame toygerFrame = (ToygerFrame) actionFrame.getObject();
        BioLog.w((String) ToygerService.TAG, "CherryCaptureTask.action(): frameType = " + toygerFrame.frameType);
        if (toygerFrame.frameType == FrameType.COMPLETED) {
            if (this.mWorkspaceHandler != null) {
                this.mWorkspaceHandler.sendEmptyMessage(1);
            }
            this.mBestToygerFrame = toygerFrame;
            if (this.mBestToygerFrame != null) {
                this.mQuality = this.mBestToygerFrame.tgFaceAttr.quality;
            }
            BioLog.e((String) ToygerService.TAG, "FrameType.FRAME : mBestToygerFrame = toygerFrame : " + this.mBestToygerFrame);
            if (this.mIsAuthInBackground) {
                return ActionType.DONE;
            }
            if (this.mToygerCirclePattern != null) {
                this.mToygerCirclePattern.getTitleBar().setVisibility(8);
                this.mToygerCirclePattern.showProcessBar(1.0f, 50, true);
                this.mToygerCirclePattern.getTitleBar().setSoundButton(8);
            }
            this.mMainHandler.postDelayed(new a(this), 300);
            return ActionType.DONE;
        } else if (toygerFrame.frameType == FrameType.FRAME) {
            return ActionType.RUN;
        } else {
            if (toygerFrame.frameType == FrameType.DARK) {
                this.isDarkScreen = true;
                if (this.mToygerCirclePattern != null) {
                    this.mToygerCirclePattern.getTitleBar().setVisibility(8);
                    this.mToygerCirclePattern.showProcessBar(1.0f, 50, true);
                    this.mMainHandler.postDelayed(new b(this), 300);
                }
                if (this.mWorkspaceHandler != null) {
                    this.mWorkspaceHandler.sendEmptyMessage(2);
                }
                return ActionType.RUN;
            }
            if (toygerFrame.frameType == FrameType.STATE) {
                if (toygerFrame.tgFaceState.hasFace) {
                    this.mToygerCirclePattern.showProcessBar(toygerFrame.tgFaceState.progress);
                } else if (this.isShowNoFace) {
                    return ActionType.RUN;
                } else {
                    this.isShowNoFace = true;
                    this.mMainHandler.postDelayed(new c(this), 500);
                    this.mToygerCirclePattern.showProcessBar(0.0f, 50, true);
                }
            }
            return super.action(actionFrame);
        }
    }

    public int done() {
        this.mTaskEndTime = System.currentTimeMillis();
        generateBisBehavTask();
        return super.done();
    }

    public void stop() {
        super.stop();
    }

    private void generateBisBehavTask() {
        this.mBisBehavTask.extInfo = String.format(this.extInfoFormat, new Object[]{Integer.valueOf(this.mVidcnt), Integer.valueOf(this.mEyeLeftOcclussion), Integer.valueOf(this.mEyeRightOcclussion)});
        BioLog.i("BisBehavTask:" + this.mBisBehavTask.extInfo);
        this.mBisBehavTask.quality = (int) this.mQuality;
        this.mBisBehavTask.name = this.mTaskName;
        this.mBisBehavTask.idx = "0";
        this.mBisBehavTask.dur = (int) (this.mTaskEndTime - this.mTaskStartTime);
    }
}
