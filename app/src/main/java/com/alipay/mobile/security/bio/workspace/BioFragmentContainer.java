package com.alipay.mobile.security.bio.workspace;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.biometric.a.a.c;
import com.alipay.android.phone.mobilecommon.biometric.a.a.d;
import com.alipay.mobile.security.bio.api.BioResponse;
import com.alipay.mobile.security.bio.config.Constant;
import com.alipay.mobile.security.bio.constants.CodeConstants;
import com.alipay.mobile.security.bio.service.BioAppDescription;
import com.alipay.mobile.security.bio.service.BioAppManager;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.local.rpc.BioRPCService;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.SignHelper;
import java.util.UUID;

public class BioFragmentContainer extends FragmentActivity implements BioFragmentCallBack {
    public BioAppDescription mBioAppDescription;
    public BioFragment mBioFragment;
    protected BioServiceManager mBioServiceManager;
    private final BroadcastReceiver mBiologyBroadcastReceiver = new b(this);
    protected FragmentManager mFragmentManager;
    protected LocalBroadcastManager mLocalBroadcastManager;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(d.bio_framework_main);
        this.mBioServiceManager = BioServiceManager.getCurrentInstance();
        this.mLocalBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        IntentFilter intentFilter = new IntentFilter(Constant.BIOLOGY_FLAG_AUTOCLOSE);
        IntentFilter intentFilter2 = new IntentFilter(Constant.BIOLOGY_FLAG_SERVER_SUCCESS);
        IntentFilter intentFilter3 = new IntentFilter(Constant.BIOLOGY_FLAG_SERVER_FAIL);
        IntentFilter intentFilter4 = new IntentFilter(Constant.BIOLOGY_FLAG_SERVER_RETRY);
        this.mLocalBroadcastManager.registerReceiver(this.mBiologyBroadcastReceiver, intentFilter);
        this.mLocalBroadcastManager.registerReceiver(this.mBiologyBroadcastReceiver, intentFilter2);
        this.mLocalBroadcastManager.registerReceiver(this.mBiologyBroadcastReceiver, intentFilter3);
        this.mLocalBroadcastManager.registerReceiver(this.mBiologyBroadcastReceiver, intentFilter4);
        this.mFragmentManager = getSupportFragmentManager();
        try {
            this.mBioAppDescription = ((BioAppManager) this.mBioServiceManager.getBioService(BioAppManager.class)).getBioAppDescription(getIntent().getStringExtra(Constant.BIOLOGY_INTENT_ACTION_INFO));
            String remoteURL = this.mBioAppDescription.getRemoteURL();
            if (!TextUtils.isEmpty(remoteURL)) {
                ((BioRPCService) this.mBioServiceManager.getBioService(BioRPCService.class)).setRemoteUrl(remoteURL);
            }
        } catch (Exception e) {
            BioLog.e(e.toString());
        }
    }

    public void backward(Bundle bundle) {
        if (this.mFragmentManager.getBackStackEntryCount() > 1) {
            this.mFragmentManager.popBackStack();
            this.mBioFragment = (BioFragment) this.mFragmentManager.getFragments().get(this.mFragmentManager.getBackStackEntryCount() - 1);
        }
    }

    public void forward(Bundle bundle, BioFragment bioFragment) {
        FragmentTransaction beginTransaction = this.mFragmentManager.beginTransaction();
        beginTransaction.replace(c.bio_framework_container, bioFragment);
        this.mBioFragment = bioFragment;
        boolean z = false;
        if (bundle != null) {
            z = bundle.getBoolean("BACK_STACK", true);
        }
        if (z) {
            beginTransaction.addToBackStack(null);
        }
        if (!isFinishing()) {
            try {
                beginTransaction.commitAllowingStateLoss();
            } catch (Exception e) {
            }
        }
    }

    public void finish(Bundle bundle) {
        finish();
    }

    /* access modifiers changed from: protected */
    public String getUniqueID() {
        return SignHelper.MD5(System.currentTimeMillis() + "_" + (Math.random() * 10000.0d) + UUID.randomUUID().toString());
    }

    /* access modifiers changed from: protected */
    public void onReceiveAction(Intent intent) {
    }

    public void onDestroy() {
        this.mLocalBroadcastManager.unregisterReceiver(this.mBiologyBroadcastReceiver);
        super.onDestroy();
    }

    public void commandFinished() {
        BioLog.i("commandFinished");
        finishActivity(false);
    }

    public BioAppDescription getAppDescription() {
        return this.mBioAppDescription;
    }

    /* access modifiers changed from: protected */
    public void verifyCallBackEvent() {
    }

    public void finishActivity(boolean z) {
        BioLog.i("finishActivity:param: " + z + " remote:" + this.mBioAppDescription.isAutoClose());
        if (!z) {
            BioLog.i("finishActivity end2");
            finish();
        } else if (this.mBioAppDescription.isAutoClose()) {
            BioLog.i("finishActivity end1");
            finish();
        }
    }

    public void sendResponse(String str, int i, String str2) {
        Intent intent = new Intent(Constant.BIOLOGY_CALLBACK_ACTION);
        BioResponse bioResponse = new BioResponse();
        bioResponse.setSuccess(false);
        bioResponse.setResult(i);
        if (i == 100) {
            bioResponse.subCode = CodeConstants.ERROR_CAMERA;
            bioResponse.subMsg = CodeConstants.getMessage(bioResponse.subCode);
        } else if (i == 205) {
            bioResponse.subCode = CodeConstants.SERVER_PARAM_ERROR;
            bioResponse.subMsg = CodeConstants.getMessage(bioResponse.subCode);
        } else if (i == 209) {
            bioResponse.subCode = CodeConstants.OUT_TIME;
            bioResponse.subMsg = CodeConstants.getMessage(bioResponse.subCode);
        } else if (i == 102) {
            bioResponse.subCode = CodeConstants.USER_UNSURPPORT_CPU;
            bioResponse.subMsg = CodeConstants.getMessage(bioResponse.subCode);
        } else if (i == 105) {
            bioResponse.subCode = CodeConstants.ANDROID_VERSION_LOW;
            bioResponse.subMsg = CodeConstants.getMessage(bioResponse.subCode);
        } else if (i == 201) {
            bioResponse.subCode = CodeConstants.INVALID_ARGUMENT;
            bioResponse.subMsg = CodeConstants.getMessage(bioResponse.subCode);
        } else if (i == 202) {
            bioResponse.subCode = CodeConstants.USER_QUITE;
            bioResponse.subMsg = CodeConstants.getMessage(bioResponse.subCode);
        } else if (i == 203) {
            bioResponse.subCode = CodeConstants.OVER_TIME;
            bioResponse.subMsg = CodeConstants.getMessage(bioResponse.subCode);
        } else if (i == 207) {
            bioResponse.subCode = CodeConstants.NETWORK_TIMEOUT;
            bioResponse.subMsg = CodeConstants.getMessage(bioResponse.subCode);
        } else if (i == 208) {
            bioResponse.subCode = CodeConstants.SERVER_FAIL;
            bioResponse.subMsg = CodeConstants.getMessage(bioResponse.subCode);
        } else if (i == 301) {
            bioResponse.subCode = CodeConstants.USER_BACK;
            bioResponse.subMsg = CodeConstants.getMessage(bioResponse.subCode);
        }
        bioResponse.setResultMessage(str2);
        bioResponse.setTag(str);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BIOLOGY_INTENT_ACTION_REV, JSON.toJSONString(bioResponse));
        intent.putExtras(bundle);
        BioLog.w((String) "sendAvatarResponse(String uniqueID, int error, String other)");
        this.mLocalBroadcastManager.sendBroadcast(intent);
    }

    public void sendResponse(int i) {
        if (this.mBioAppDescription != null) {
            sendResponse(this.mBioAppDescription.getTag(), i, "");
        }
    }

    public void sendResponse(BioFragmentResponse bioFragmentResponse) {
        Intent intent = new Intent(Constant.BIOLOGY_CALLBACK_ACTION);
        BioResponse bioResponse = new BioResponse();
        bioResponse.setSuccess(bioFragmentResponse.isSucess);
        bioResponse.setToken(bioFragmentResponse.token);
        bioResponse.subCode = bioFragmentResponse.subCode;
        bioResponse.subMsg = bioFragmentResponse.subMsg;
        bioResponse.setResultMessage(bioFragmentResponse.resultMessage);
        bioResponse.setResult(bioFragmentResponse.errorCode);
        bioResponse.setTag(this.mBioAppDescription.getTag());
        if (bioFragmentResponse.ext != null) {
            bioResponse.setExt(bioFragmentResponse.ext);
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BIOLOGY_INTENT_ACTION_REV, JSON.toJSONString(bioResponse));
        intent.putExtras(bundle);
        BioLog.w((String) "sendResponse(BioFragmentResponse)");
        this.mLocalBroadcastManager.sendBroadcast(intent);
    }

    public void sendProgressResponse(BioFragmentResponse bioFragmentResponse) {
        Intent intent = new Intent(Constant.BIOLOGY_CALLBACK_PROGRESS_ACTION);
        BioResponse bioResponse = new BioResponse();
        bioResponse.setSuccess(bioFragmentResponse.isSucess);
        bioResponse.setToken(bioFragmentResponse.token);
        bioResponse.subCode = bioFragmentResponse.subCode;
        bioResponse.subMsg = bioFragmentResponse.subMsg;
        bioResponse.setResultMessage(bioFragmentResponse.resultMessage);
        bioResponse.setResult(bioFragmentResponse.errorCode);
        bioResponse.setTag(this.mBioAppDescription.getTag());
        if (bioFragmentResponse.ext != null) {
            bioResponse.setExt(bioFragmentResponse.ext);
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BIOLOGY_INTENT_ACTION_REV, JSON.toJSONString(bioResponse));
        intent.putExtras(bundle);
        BioLog.w((String) "sendProgressResponse(BioFragmentResponse)");
        this.mLocalBroadcastManager.sendBroadcast(intent);
    }

    public void sendAvatarResponse(BioFragmentResponse bioFragmentResponse) {
        Intent intent = new Intent(Constant.BIOLOGY_SEND_AVATAR_ACTION);
        BioResponse bioResponse = new BioResponse();
        bioResponse.setSuccess(bioFragmentResponse.isSucess);
        bioResponse.setToken(bioFragmentResponse.token);
        bioResponse.setResultMessage(bioFragmentResponse.resultMessage);
        bioResponse.setResult(bioFragmentResponse.errorCode);
        bioResponse.setTag(this.mBioAppDescription.getTag());
        if (bioFragmentResponse.ext != null) {
            bioResponse.setExt(bioFragmentResponse.ext);
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BIOLOGY_INTENT_ACTION_REV, JSON.toJSONString(bioResponse));
        intent.putExtras(bundle);
        BioLog.w((String) "sendAvatarResponse(BioFragmentResponse)");
        sendStickyBroadcast(intent);
    }
}
