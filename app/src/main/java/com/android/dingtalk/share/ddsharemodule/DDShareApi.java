package com.android.dingtalk.share.ddsharemodule;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import com.android.dingtalk.share.ddsharemodule.message.BaseReq;
import com.android.dingtalk.share.ddsharemodule.message.DDMessage;
import com.android.dingtalk.share.ddsharemodule.message.DDMessageAct;
import com.android.dingtalk.share.ddsharemodule.message.SendMessageToDD.Req;
import com.android.dingtalk.share.ddsharemodule.message.SendMessageToDD.Resp;
import com.android.dingtalk.share.ddsharemodule.plugin.DDVersionCheck;
import com.android.dingtalk.share.ddsharemodule.plugin.SignatureCheck;

public class DDShareApi implements IDDShareApi {
    private static final String TAG = "DDShareApi";
    private String mAppId;
    private Context mContext;
    private boolean mNeedSignatureCheck;

    public DDShareApi(Context context, String str) {
        this(context, str, false);
    }

    public DDShareApi(Context context, String str, boolean z) {
        this.mContext = context;
        this.mAppId = str;
        this.mNeedSignatureCheck = z;
    }

    public boolean registerApp(String str) {
        if (!checkSumConsistent(ShareConstant.DD_APP_PACKAGE)) {
            return false;
        }
        if (str != null) {
            this.mAppId = str;
        }
        new StringBuilder("register app ").append(this.mContext.getPackageName());
        Context context = this.mContext;
        String str2 = ShareConstant.PERMISSION_ACTION_HANDLE_APP_REGISTER;
        StringBuilder sb = new StringBuilder();
        sb.append(ShareConstant.ACTION_APP_REGISTER);
        sb.append(str);
        DDMessage.send(context, ShareConstant.DD_APP_PACKAGE, str2, sb.toString());
        return true;
    }

    public void unregisterApp() {
        if (checkSumConsistent(ShareConstant.DD_APP_PACKAGE) && this.mAppId != null && this.mAppId.length() != 0) {
            new StringBuilder("unregister app ").append(this.mContext.getPackageName());
            Context context = this.mContext;
            String str = ShareConstant.PERMISSION_ACTION_HANDLE_APP_UNREGISTER;
            StringBuilder sb = new StringBuilder();
            sb.append(ShareConstant.ACTION_APP_UNREGISTER);
            sb.append(this.mAppId);
            DDMessage.send(context, ShareConstant.DD_APP_PACKAGE, str, sb.toString());
        }
    }

    public boolean isDDAppInstalled() {
        boolean z = false;
        try {
            if (this.mContext.getPackageManager().getPackageInfo(ShareConstant.DD_APP_PACKAGE, 64) != null && checkSumConsistent(ShareConstant.DD_APP_PACKAGE)) {
                z = true;
            }
            return z;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    public boolean handleIntent(Intent intent, IDDAPIEventHandler iDDAPIEventHandler) {
        String stringExtra = intent.getStringExtra(ShareConstant.EXTRA_MESSAGE_APP_PACKAGE_NAME);
        if (stringExtra == null || stringExtra.length() == 0 || iDDAPIEventHandler == null) {
            return false;
        }
        switch (intent.getIntExtra(ShareConstant.EXTRA_COMMAND_TYPE, 0)) {
            case 1:
                iDDAPIEventHandler.onResp(new Resp(intent.getExtras()));
                return true;
            case 2:
                iDDAPIEventHandler.onResp(new Resp(intent.getExtras()));
                return true;
            case 3:
                iDDAPIEventHandler.onReq(new Req(intent.getExtras()));
                return true;
            case 4:
                iDDAPIEventHandler.onReq(new Req(intent.getExtras()));
                return true;
            default:
                return false;
        }
    }

    public boolean isDDSupportAPI() {
        return getDDSupportAPI() > 20151125;
    }

    public boolean isDDSupportDingAPI() {
        return getDDSupportAPI() > 20160214;
    }

    public int getDDSupportAPI() {
        if (!isDDAppInstalled()) {
            return 0;
        }
        return DDVersionCheck.getSdkVersionFromMetaData(this.mContext, 0);
    }

    public boolean openDDApp() {
        if (!isDDAppInstalled()) {
            return false;
        }
        try {
            this.mContext.startActivity(this.mContext.getPackageManager().getLaunchIntentForPackage(ShareConstant.DD_APP_PACKAGE));
            return true;
        } catch (Exception e) {
            new StringBuilder("start dd Main Activity fail, exception = ").append(e.getMessage());
            return false;
        }
    }

    public boolean sendReq(BaseReq baseReq) {
        if (!checkSumConsistent(ShareConstant.DD_APP_PACKAGE) || !baseReq.checkArgs()) {
            return false;
        }
        Bundle bundle = new Bundle();
        baseReq.toBundle(bundle);
        return DDMessageAct.sendDDFriend(this.mContext, this.mAppId, bundle);
    }

    public boolean sendReqToDing(BaseReq baseReq) {
        if (!checkSumConsistent(ShareConstant.DD_APP_PACKAGE) || !baseReq.checkArgs()) {
            return false;
        }
        Bundle bundle = new Bundle();
        baseReq.toBundle(bundle);
        return DDMessageAct.sendDing(this.mContext, this.mAppId, bundle);
    }

    private boolean checkSumConsistent(String str) {
        if (!this.mNeedSignatureCheck) {
            return true;
        }
        return TextUtils.equals(SignatureCheck.getMD5Signature(this.mContext, str), ShareConstant.DD_APP_SIGNATURE);
    }

    private static boolean checkSumConsistent(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr.length == 0 || bArr2 == null || bArr2.length == 0 || bArr.length != bArr2.length) {
            return false;
        }
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }
}
