package com.alipay.mobile.common.transportext.biz.mmtp;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.mobile.common.amnet.api.AmnetListenerAdpter;
import com.alipay.mobile.common.info.AppInfo;
import com.alipay.mobile.common.lbs.LBSLocation;
import com.alipay.mobile.common.lbs.LBSLocationManagerProxy;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transportext.biz.appevent.AmnetUserInfo;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;
import com.alipay.mobile.common.transportext.biz.util.LogUtilAmnet;
import com.alipay.mobile.common.transportext.biz.util.LoginHelper;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.scansdk.constant.Constants;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.HashMap;
import java.util.Map;

public class MMTPGeneralListenerImpl extends AmnetListenerAdpter {
    private static final String CLASS_SCHEME_SERVICE = "com.alipay.mobile.framework.service.common.SchemeService";
    private static final String LOGTAG = "amnet_MmtpGeneralListenerImpl";
    private static volatile MMTPGeneralListenerImpl instance;

    private MMTPGeneralListenerImpl() {
    }

    public static synchronized MMTPGeneralListenerImpl getInstance() {
        MMTPGeneralListenerImpl mMTPGeneralListenerImpl;
        synchronized (MMTPGeneralListenerImpl.class) {
            if (instance == null) {
                instance = new MMTPGeneralListenerImpl();
            }
            mMTPGeneralListenerImpl = instance;
        }
        return mMTPGeneralListenerImpl;
    }

    public Map<Byte, Map<String, String>> collect(Map<Byte, Map<String, String>> param) {
        LogUtilAmnet.d(LOGTAG, "collect MmtpGeneralListenerImpl ");
        Map commonInit = new HashMap();
        commonInit.put("language", MiscUtils.getAlipayLocaleDes());
        commonInit.put("awid", AppInfo.createInstance(ExtTransportEnv.getAppContext()).getmAwid());
        LBSLocation location = LBSLocationManagerProxy.getInstance().getLastKnownLocation(ExtTransportEnv.getAppContext());
        if (location != null) {
            commonInit.put("latitude", String.valueOf(location.getLatitude()));
            commonInit.put("longitude", String.valueOf(location.getLongitude()));
            commonInit.put("locTime", String.valueOf(location.getLocationtime()));
        }
        commonInit.put(DictionaryKeys.DEV_APDIDTOKEN, APSecuritySdk.getInstance(ExtTransportEnv.getAppContext()).getTokenResult().apdid);
        String sourceId = getSourceId();
        if (!TextUtils.isEmpty(sourceId)) {
            commonInit.put(Constants.SERVICE_SOURCE_ID, sourceId);
        }
        Map oldInit = param.get(Byte.valueOf(0));
        if (oldInit == null) {
            param.put(Byte.valueOf(0), commonInit);
        } else {
            oldInit.putAll(commonInit);
        }
        if (MiscUtils.isDebugger(ExtTransportEnv.getAppContext())) {
            try {
                LogUtilAmnet.d(LOGTAG, "collect [ MmtpGeneralListenerImpl ] " + JSON.toJSONString(param));
            } catch (Throwable e) {
                LogCatUtil.warn((String) LOGTAG, "collect print exception. " + e.toString());
            }
        }
        return param;
    }

    public void notifyInitOk() {
        LinkStateListener.getInstance().notifyInitOk();
    }

    public void change(int state) {
        LinkStateListener.getInstance().change(state);
    }

    private String getSourceId() {
        try {
            Object schemeServiceObj = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(CLASS_SCHEME_SERVICE);
            if (schemeServiceObj == null) {
                return "";
            }
            Object result = schemeServiceObj.getClass().getDeclaredMethod("getLastTagId", new Class[0]).invoke(schemeServiceObj, new Object[0]);
            if (result != null) {
                return String.valueOf(result);
            }
            return "";
        } catch (Throwable e) {
            LogCatUtil.warn((String) LOGTAG, "getSourceId has occurr " + e.toString());
        }
    }

    public void listenSessionInvalid() {
        LoginHelper.tryLogin();
    }

    public void notifyResendSessionid() {
        AmnetUserInfo.resendSessionid();
    }
}
