package com.alipay.zoloz.toyger.workspace;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.ScreenUtil;
import com.alipay.zoloz.toyger.R;
import com.alipay.zoloz.toyger.extservice.record.TimeRecord;
import com.alipay.zoloz.toyger.extservice.record.ToygerRecordService;
import com.alipay.zoloz.toyger.util.ObjectUtil;
import com.alipay.zoloz.toyger.widget.ToygerCirclePattern;
import java.util.HashMap;

public class ToygerCaptureFragment extends ToygerFragment {
    private static final int REQUEST_CAMERA = 1;
    private View mContentView;
    boolean mIsFirstResume;
    /* access modifiers changed from: private */
    public int mLight = 255;
    private ToygerCirclePattern mToygerCirclePattern;
    private ToygerWorkspace mToygerWorkspace;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        BioLog.i("Fragment onCreate");
        if (this.mToygerCallback != null && this.mToygerCallback.getRemoteConfig() != null && this.mToygerCallback.getRemoteConfig().getColl() != null) {
            this.mLight = this.mToygerCallback.getRemoteConfig().getColl().getLight();
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        BioLog.i("Fragment onCreateView");
        try {
            if (this.mContentView != null) {
                ViewGroup viewGroup2 = (ViewGroup) this.mContentView.getParent();
                if (viewGroup2 != null) {
                    viewGroup2.removeView(this.mContentView);
                }
            } else {
                initView(layoutInflater, viewGroup);
            }
            recordExtDetectionStart(this.mToygerCallback.getRemoteConfig().getAlgorithm());
        } catch (Throwable th) {
            BioLog.e(Log.getStackTraceString(th));
            this.mToygerCallback.finishActivity(false);
            this.mToygerCallback.sendResponse(205);
        }
        return this.mContentView;
    }

    private void initView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        this.mContentView = layoutInflater.inflate(R.layout.toyger_circle_pattern_component, viewGroup, false);
        this.mToygerCirclePattern = (ToygerCirclePattern) this.mContentView.findViewById(R.id.toyger_circle_pattern_component);
        this.mToygerCirclePattern.init(this.mToygerCallback.getRemoteConfig().getDeviceSettings());
        BioLog.i("Test onCreateView");
        this.mToygerWorkspace = new ToygerWorkspace(this.mBioServiceManager, this.mToygerCallback, this.mToygerCirclePattern);
        if (!isSupportPermissionDialog()) {
            this.mToygerWorkspace.setCameraVisible(true);
        } else if (ActivityCompat.checkSelfPermission(getActivity(), "android.permission.CAMERA") != 0) {
            this.mToygerCallback.setCameraPermission(false);
            this.mToygerWorkspace.setCameraVisible(false);
            requestCameraPermission();
        } else {
            this.mToygerWorkspace.setCameraVisible(true);
        }
        this.mToygerWorkspace.init();
    }

    public void onDestroy() {
        BioLog.i("Fragment onDestroy");
        if (this.mToygerWorkspace != null) {
            this.mToygerWorkspace.destroy();
        }
        super.onDestroy();
    }

    public void onPause() {
        BioLog.i("Fragment onPause");
        if (this.mToygerWorkspace != null) {
            this.mToygerWorkspace.pause();
        }
        super.onPause();
    }

    public void onResume() {
        BioLog.i("Fragment onResume");
        if (!this.mIsFirstResume) {
            this.mIsFirstResume = true;
            recordExtDetectionEnd();
        }
        if (this.mToygerWorkspace != null) {
            this.mToygerWorkspace.resume();
        }
        new Handler().post(new b(this));
        super.onResume();
    }

    public boolean ontActivityEvent(int i, KeyEvent keyEvent) {
        if (this.mToygerWorkspace != null) {
            return this.mToygerWorkspace.ontActivityEvent(i, keyEvent);
        }
        return super.ontActivityEvent(i, keyEvent);
    }

    private boolean isSupportPermissionDialog() {
        if (VERSION.SDK == null || Integer.parseInt(VERSION.SDK) < 23) {
            return false;
        }
        return true;
    }

    private void requestCameraPermission() {
        requestPermissions(new String[]{"android.permission.CAMERA"}, 1);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i != 1) {
            return;
        }
        if (iArr.length == 1 && iArr[0] == 0) {
            this.mToygerWorkspace.setCameraVisible(true);
            this.mToygerCallback.setCameraPermission(true);
            this.mToygerWorkspace.startTimerTask();
            return;
        }
        this.mToygerWorkspace.setCameraVisible(false);
        this.mToygerCallback.setCameraPermission(false);
        this.mToygerWorkspace.alertCameraPermission();
        this.mToygerWorkspace.stopTimerTask();
    }

    private void recordExtDetectionStart(JSONObject jSONObject) {
        ToygerRecordService toygerRecordService = (ToygerRecordService) this.mBioServiceManager.getBioService(ToygerRecordService.class);
        if (toygerRecordService != null) {
            TimeRecord.getInstance().setEnterDetectionTime(System.currentTimeMillis());
            toygerRecordService.write(ToygerRecordService.ENTER_DETECTION_START, ObjectUtil.objectToStringMap(jSONObject));
        }
    }

    private void recordExtDetectionEnd() {
        ToygerRecordService toygerRecordService = (ToygerRecordService) this.mBioServiceManager.getBioService(ToygerRecordService.class);
        if (toygerRecordService != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("timecost", (System.currentTimeMillis() - TimeRecord.getInstance().getEnterDetectionTime()));
            hashMap.put("brightness", ScreenUtil.getScreenBrightness(getContext()));
            toygerRecordService.write(ToygerRecordService.ENTER_DETECTION_END, hashMap);
        }
    }
}
