package com.alipay.zoloz.toyger.workspace;

import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.alipay.mobile.security.bio.config.bean.FaceTips;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.local.apsecurity.ApSecurityService;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.NetworkUtil;
import com.alipay.mobile.security.bio.workspace.BioFragment;
import com.alipay.mobile.security.bio.workspace.BioFragmentContainer;
import com.alipay.mobile.security.bio.workspace.BioFragmentResponse;
import com.alipay.mobile.security.faceauth.UserVerifyInfo;
import com.alipay.mobile.security.zim.plugin.ZIMH5Plugin;
import com.alipay.zoloz.toyger.BuildConfig;
import com.alipay.zoloz.toyger.R;
import com.alipay.zoloz.toyger.extservice.record.TimeRecord;
import com.alipay.zoloz.toyger.extservice.record.ToygerRecordService;
import com.alipay.zoloz.toyger.interfaces.ToygerCallback;
import com.alipay.zoloz.toyger.interfaces.ToygerEvent;
import com.alipay.zoloz.toyger.util.DialogTypeIndex;
import com.alipay.zoloz.toyger.widget.GenenalDialog;
import com.alipay.zoloz.toyger.widget.GenenalDialog.Builder;
import java.util.HashMap;

public class ToygerActivity extends BioFragmentContainer implements ToygerCallback {
    public boolean mCameraPermission = true;
    public DialogTypeIndex mDialogTypeIndex;
    public FaceRemoteConfig mFaceRemoteConfig = null;
    public int mRetryTime;
    protected ToygerRecordService mToygerRecordService;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        BioFragment toygerCaptureFragment;
        setTheme(R.style.FaceNoAnimationTheme);
        super.onCreate(bundle);
        BioServiceManager currentInstance = BioServiceManager.getCurrentInstance();
        if (currentInstance == null) {
            BioLog.e((Throwable) new IllegalStateException("null == BioServiceManager.getCurrentInstance()"));
            finish();
            return;
        }
        this.mToygerRecordService = (ToygerRecordService) currentInstance.getBioService(ToygerRecordService.class);
        setVolumeControlStream(3);
        getWindow().addFlags(128);
        getWindow().addFlags(1024);
        if (BuildConfig.UI_ORANGE.equalsIgnoreCase("Cherry")) {
            hideBottomUIMenu();
        }
        if (this.mBioAppDescription != null) {
            try {
                BioLog.i("zolozTime", "parseObject");
                this.mFaceRemoteConfig = (FaceRemoteConfig) JSON.parseObject(this.mBioAppDescription.getCfg(), FaceRemoteConfig.class);
                BioLog.i("zolozTime", "parseObject end:" + this.mFaceRemoteConfig.toString());
            } catch (Exception e) {
                BioLog.w((Throwable) e);
                protectRemoteConfig();
            }
        }
        recordExtEntrySDK();
        if (this.mFaceRemoteConfig == null || this.mFaceRemoteConfig.getNavi() == null || !this.mFaceRemoteConfig.getNavi().isEnable()) {
            toygerCaptureFragment = new ToygerCaptureFragment();
        } else {
            toygerCaptureFragment = new ToygerNavigationFragment();
        }
        forward(null, toygerCaptureFragment);
        preLoadToygerAsync();
    }

    /* access modifiers changed from: protected */
    public void hideBottomUIMenu() {
        if (VERSION.SDK_INT > 11 && VERSION.SDK_INT < 19) {
            getWindow().getDecorView().setSystemUiVisibility(8);
        } else if (VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(4102);
        }
    }

    private void preLoadToygerAsync() {
        new Thread(new a(this), "preload_toyger").start();
    }

    public void sendResponse(String str, int i, String str2) {
        BioFragmentResponse bioFragmentResponse = new BioFragmentResponse();
        bioFragmentResponse.errorCode = i;
        recordExtCallVerify(bioFragmentResponse);
        super.sendResponse(str, i, str2);
    }

    public void sendResponse(BioFragmentResponse bioFragmentResponse) {
        recordExtCallVerify(bioFragmentResponse);
        super.sendResponse(bioFragmentResponse);
    }

    public FaceRemoteConfig getRemoteConfig() {
        return this.mFaceRemoteConfig;
    }

    public void alert(DialogTypeIndex dialogTypeIndex, String str, String str2, String str3, String str4, OnClickListener onClickListener, String str5, OnClickListener onClickListener2, boolean z, boolean z2) {
        if (!isFinishing()) {
            Builder builder = new Builder(this);
            if (str2 != null) {
                builder.setTitle(str2);
            }
            if (str3 != null) {
                builder.setMessage2(str3);
            }
            if (str4 != null) {
                builder.setPositiveButton(str4, onClickListener);
            }
            if (str5 != null) {
                builder.setNegativeButton(str5, onClickListener2);
            }
            builder.showCloseButton(false);
            builder.showProtocol(false);
            this.mDialogTypeIndex = dialogTypeIndex;
            GenenalDialog show = builder.show();
            show.setCanceledOnTouchOutside(z);
            show.setCancelable(false);
            show.setTag(dialogTypeIndex);
            show.getWindow().setGravity(17);
        }
    }

    public DialogTypeIndex getAlertTag() {
        return this.mDialogTypeIndex;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z = false;
        if (this.mBioFragment != null) {
            z = ((ToygerEvent) this.mBioFragment).ontActivityEvent(i, keyEvent);
        }
        if (z) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public void gotoSettings() {
        Intent intent = new Intent("android.settings.SETTINGS");
        intent.setFlags(268435456);
        startActivity(intent);
    }

    public int getRetryTime() {
        return this.mRetryTime;
    }

    public void setRetryTime(int i) {
        if (this.mRetryTime != i) {
            this.mRetryTime = i;
        }
    }

    public boolean haveCameraPermission() {
        return this.mCameraPermission;
    }

    public void setCameraPermission(boolean z) {
        this.mCameraPermission = z;
    }

    public UserVerifyInfo getUserVerifyInfo() {
        UserVerifyInfo userVerifyInfo = new UserVerifyInfo();
        userVerifyInfo.actid = "circle";
        userVerifyInfo.apdid = this.mBioAppDescription.getExtProperty().get("APDID") != null ? this.mBioAppDescription.getExtProperty().get("APDID") : "";
        userVerifyInfo.appid = this.mBioAppDescription.getExtProperty().get("appid") != null ? this.mBioAppDescription.getExtProperty().get("appid") : "";
        userVerifyInfo.behid = this.mBioAppDescription.getTag();
        userVerifyInfo.sceid = this.mBioAppDescription.getExtProperty().get("SCENE_ID") != null ? this.mBioAppDescription.getExtProperty().get("SCENE_ID") : "";
        userVerifyInfo.bistoken = this.mBioAppDescription.getBistoken();
        userVerifyInfo.userid = this.mBioAppDescription.getExtProperty().get("userid") != null ? this.mBioAppDescription.getExtProperty().get("userid") : "";
        userVerifyInfo.vtoken = this.mBioAppDescription.getExtProperty().get("TOKEN_ID") != null ? this.mBioAppDescription.getExtProperty().get("TOKEN_ID") : "";
        userVerifyInfo.verifyid = this.mBioAppDescription.getExtProperty().get(BioDetector.EXT_KEY_VERIFYID) != null ? this.mBioAppDescription.getExtProperty().get(BioDetector.EXT_KEY_VERIFYID) : "";
        return userVerifyInfo;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        BioLog.i("ToygerActivity destroy!");
        recordExtExitSdk();
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    public void onWindowFocusChanged(boolean z) {
        if (this.mBioFragment != null) {
            ((ToygerEvent) this.mBioFragment).onWindowFocusChanged(z);
        }
        super.onWindowFocusChanged(z);
    }

    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    /* access modifiers changed from: protected */
    public void commandFinished() {
        this.mToygerRecordService.write(ToygerRecordService.NOTICE_EXIT_SDK);
        super.commandFinished();
    }

    private void recordExtEntrySDK() {
        String str;
        if (this.mBioAppDescription != null) {
            HashMap hashMap = new HashMap();
            hashMap.put(BioDetector.EXT_KEY_SCENE_ID_BUNDLE, this.mFaceRemoteConfig.getSceneEnv().getSceneCode());
            hashMap.put("uiVersion", this.mFaceRemoteConfig.getUi());
            hashMap.put(ZIMH5Plugin.ZIM_IDENTIFY_VERIFYID, this.mBioAppDescription.getExtProperty().get(BioDetector.EXT_KEY_VERIFYID));
            String staticApDidToken = ApSecurityService.getStaticApDidToken();
            if (TextUtils.isEmpty(staticApDidToken)) {
                ApSecurityService apSecurityService = (ApSecurityService) BioServiceManager.getCurrentInstance().getBioService(ApSecurityService.class);
                if (apSecurityService != null) {
                    str = apSecurityService.getApDidToken();
                    hashMap.put("APDIDTOKEN", str);
                    hashMap.put("TOKEN_ID", this.mBioAppDescription.getExtProperty().get("TOKEN_ID"));
                    hashMap.put("product", "toyger");
                    hashMap.put("bioType", "facedetect");
                    hashMap.put("networkType", NetworkUtil.getNetworkType(this));
                    this.mToygerRecordService.addExtProperties(hashMap);
                    this.mToygerRecordService.write(ToygerRecordService.ENTRY_SDK, new HashMap());
                    TimeRecord.getInstance().setEntrySdkTime(System.currentTimeMillis());
                }
            }
            str = staticApDidToken;
            hashMap.put("APDIDTOKEN", str);
            hashMap.put("TOKEN_ID", this.mBioAppDescription.getExtProperty().get("TOKEN_ID"));
            hashMap.put("product", "toyger");
            hashMap.put("bioType", "facedetect");
            hashMap.put("networkType", NetworkUtil.getNetworkType(this));
            this.mToygerRecordService.addExtProperties(hashMap);
            this.mToygerRecordService.write(ToygerRecordService.ENTRY_SDK, new HashMap());
            TimeRecord.getInstance().setEntrySdkTime(System.currentTimeMillis());
        }
    }

    private void recordExtCallVerify(BioFragmentResponse bioFragmentResponse) {
        HashMap hashMap = new HashMap();
        hashMap.put("returncode", (bioFragmentResponse != null ? Integer.valueOf(bioFragmentResponse.errorCode) : ""));
        this.mToygerRecordService.write(ToygerRecordService.CALLBACK_VERIFY_SYSTEM, hashMap);
    }

    private void recordExtExitSdk() {
        HashMap hashMap = new HashMap();
        hashMap.put("timecost", (System.currentTimeMillis() - TimeRecord.getInstance().getEntrySdkTime()));
        if (this.mToygerRecordService != null) {
            this.mToygerRecordService.write(ToygerRecordService.EXIT_SDK, hashMap);
        }
    }

    private void protectRemoteConfig() {
        if (this.mFaceRemoteConfig == null) {
            this.mFaceRemoteConfig = new FaceRemoteConfig();
            this.mFaceRemoteConfig.setFaceTips(new FaceTips());
        } else if (this.mFaceRemoteConfig.getFaceTips() == null) {
            this.mFaceRemoteConfig.setFaceTips(new FaceTips());
        }
    }
}
