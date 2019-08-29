package com.ali.user.mobile.accountbiz.extservice.impl;

import android.content.Context;
import com.ali.user.mobile.accountbiz.extservice.RSAService;
import com.ali.user.mobile.accountbiz.extservice.base.BaseExtService;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.rpc.vo.mobilegw.GetRSAPKeyApi;
import com.ali.user.mobile.rpc.vo.mobilegw.RSAPKeyResult;
import com.alipay.android.phone.inside.common.sec.RSAHelper;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

public class RSAServiceImpl extends BaseExtService implements RSAService {
    private static final String TAG = "RSAServiceImpl";
    private static RSAService mRsaService;
    private static String rsaPkData;
    private static String rsaTSData;
    private long mSafeRSAServerTS;

    private RSAServiceImpl(Context context) {
        super(context);
    }

    public static RSAService getInstance(Context context) {
        if (mRsaService == null) {
            synchronized (RSAServiceImpl.class) {
                try {
                    if (mRsaService == null) {
                        mRsaService = new RSAServiceImpl(context);
                    }
                }
            }
        }
        return mRsaService;
    }

    public String RSAEncrypt(String str, boolean z) {
        try {
            getRsaKey();
            StringBuilder sb = new StringBuilder("rsa公钥：");
            sb.append(rsaPkData);
            sb.append(";rsa公钥时间戳：");
            sb.append(rsaTSData);
            AliUserLog.c(TAG, sb.toString());
            if (rsaPkData != null) {
                if (z) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(rsaTSData);
                    str = sb2.toString();
                }
                AliUserLog.c(TAG, "获取rsa公钥成功，进行密码加密");
                String a = RSAHelper.a(str, rsaPkData);
                AliUserLog.c(TAG, "获取rsa公钥成功，进行密码加密 encryptCurrentPwd=".concat(String.valueOf(a)));
                return a;
            }
            AliUserLog.c(TAG, "获取rsa公钥失败");
            return null;
        } catch (RpcException e) {
            StringBuilder sb3 = new StringBuilder("{[info=RSAEncrypt],[msg=");
            sb3.append(e.getCode());
            sb3.append(Token.SEPARATOR);
            sb3.append(e.getMessage());
            sb3.append("]}");
            AliUserLog.c(TAG, sb3.toString());
            throw e;
        }
    }

    public String getRsaKey() {
        AliUserLog.c(TAG, "获取rsa公钥");
        AliUserLog.c(TAG, "本地无缓存公钥信息存在，请求服务器获取公钥");
        try {
            RSAPKeyResult rsaKey = ((GetRSAPKeyApi) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(GetRSAPKeyApi.class)).getRsaKey();
            if (rsaKey == null) {
                return null;
            }
            rsaPkData = rsaKey.rsaPK;
            rsaTSData = rsaKey.rsaTS;
            long currentTimeMillis = System.currentTimeMillis();
            if (rsaTSData != null && rsaTSData.length() > 0 && String.valueOf(currentTimeMillis).length() > 3) {
                this.mSafeRSAServerTS = Long.parseLong(rsaTSData) - Long.parseLong(String.valueOf(currentTimeMillis).substring(3));
            }
            AliUserLog.c(TAG, "从服务器获取rsa成功返回");
            return rsaPkData;
        } catch (RpcException e) {
            StringBuilder sb = new StringBuilder("请求 rsa 服务器失败 ");
            sb.append(e.getCode());
            sb.append(Token.SEPARATOR);
            sb.append(e.getMessage());
            AliUserLog.c(TAG, sb.toString());
            throw e;
        }
    }

    public String getRsaTS() {
        if (rsaTSData != null) {
            return rsaTSData;
        }
        getRsaKey();
        return rsaTSData;
    }

    public long getSafeRSATS() {
        if (this.mSafeRSAServerTS != 0) {
            return this.mSafeRSAServerTS;
        }
        getRsaKey();
        return this.mSafeRSAServerTS;
    }
}
