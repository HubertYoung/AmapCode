package com.alipay.android.phone.mobilecommon.logger;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.log.BehaviourIdEnum;
import com.alipay.mobile.security.bio.log.VerifyBehavior;
import com.alipay.mobile.security.bio.service.local.monitorlog.MonitorLogService;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.ReflectUtils;

public class AlipayMonitorLogService extends MonitorLogService {
    public void logBehavior(BehaviourIdEnum behaviourIdEnum, VerifyBehavior verifyBehavior) {
        if (verifyBehavior == null) {
            BioLog.w((String) "verifyBehavior is null");
            return;
        }
        Behavor behavor = new Behavor();
        behavor.setBehaviourPro(verifyBehavior.getBizType());
        try {
            ReflectUtils.invokeMethod(behavor, "setLoggerLevel", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(verifyBehavior.getLoggerLevel())});
        } catch (Throwable th) {
        }
        behavor.setUserCaseID(verifyBehavior.getUserCaseID());
        if (!TextUtils.isEmpty(verifyBehavior.getAppID())) {
            behavor.setAppID(verifyBehavior.getAppID());
        }
        behavor.setSeedID(verifyBehavior.getSeedID());
        behavor.setParam1(verifyBehavior.getParam1());
        behavor.setParam2(verifyBehavior.getParam2());
        behavor.setParam3(verifyBehavior.getParam3());
        if (verifyBehavior.getExtParams() != null) {
            behavor.getExtParams().putAll(verifyBehavior.getExtParams());
        }
        behavor.getExtParams().put("integration", "alipaycloud_china_bundle");
        behavor.addExtParam("verison", "3.0.0");
        behavor.addExtParam("mpaas", "1");
        LoggerFactory.getBehavorLogger().event(behaviourIdEnum == null ? "" : behaviourIdEnum.getDes(), behavor);
    }

    public void trigUpload() {
        try {
            super.trigUpload();
            BioLog.i("trigUpload 主动触发日志上传");
            LoggerFactory.getLogContext().flush(MetaRecord.BIZ_TYPE, false);
            LoggerFactory.getLogContext().uploadAfterSync(MetaRecord.BIZ_TYPE);
        } catch (Throwable th) {
            BioLog.w((String) "trigUpload got Exception", th);
        }
    }
}
