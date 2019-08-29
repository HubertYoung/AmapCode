package com.alipay.mobile.security.bio.common.record.impl;

import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.log.BehaviourIdEnum;
import com.alipay.mobile.security.bio.log.VerifyBehavior;
import com.alipay.mobile.security.bio.service.BioRecordService;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.local.monitorlog.MonitorLogService;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.SignHelper;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

public class BioRecordServiceImpl extends BioRecordService {
    protected int a;
    protected String b;
    protected Map<String, String> c;
    protected Object d;
    private MonitorLogService e;

    public BioRecordServiceImpl() {
        this.a = 0;
        this.b = "";
        this.c = new Hashtable();
        this.d = new Object();
        this.b = SignHelper.SHA1(System.currentTimeMillis() + Math.round(10000.0f));
        synchronized (this.d) {
            if (this.c != null) {
                this.c.clear();
            }
        }
    }

    public void onCreate(BioServiceManager bioServiceManager) {
        super.onCreate(bioServiceManager);
        this.e = (MonitorLogService) bioServiceManager.getBioService(MonitorLogService.class);
        BioLog.w(getClass().getName() + " call mBioServiceManager.getBioService(MonitorLogService.class): " + this.e);
    }

    public void write(MetaRecord metaRecord) {
        superWrite(metaRecord);
        BioLog.i(getClass().getSimpleName() + "(sequenceId=" + this.a + "):" + metaRecord);
        if (metaRecord != null) {
            VerifyBehavior verifyBehavior = new VerifyBehavior();
            verifyBehavior.setUserCaseID(metaRecord.getCaseID());
            String actionID = metaRecord.getActionID();
            verifyBehavior.setAppID(metaRecord.getAppID());
            verifyBehavior.setSeedID(metaRecord.getSeedID());
            verifyBehavior.setParam1(this.b);
            verifyBehavior.setParam2(metaRecord.getParam2());
            verifyBehavior.setParam3(metaRecord.getParam3());
            verifyBehavior.setBizType(metaRecord.getBizType());
            verifyBehavior.setLoggerLevel(metaRecord.getPriority());
            a(verifyBehavior, this.c);
            a(verifyBehavior, metaRecord.getParam4());
            BehaviourIdEnum convert = BehaviourIdEnum.convert(actionID);
            if (this.e == null) {
                BioLog.e((Throwable) new RuntimeException(getClass().getName() + ".write(" + metaRecord + ") failed. MonitorLogService==null"));
            } else {
                this.e.logBehavior(convert, verifyBehavior);
            }
        }
    }

    private static void a(VerifyBehavior verifyBehavior, Map<String, String> map) {
        if (map != null) {
            for (Entry next : map.entrySet()) {
                Object key = next.getKey();
                Object value = next.getValue();
                if (!(key == null || value == null)) {
                    verifyBehavior.addExtParam(key.toString(), value.toString());
                }
            }
        }
    }

    public void superWrite(MetaRecord metaRecord) {
        synchronized (this.d) {
            if (metaRecord != null) {
                metaRecord.setParam1(this.b);
                if (metaRecord.isEnableSequence()) {
                    this.a++;
                    metaRecord.setSequenceId(this.a);
                    this.c.put("sequence_id", this.a);
                } else {
                    this.c.remove("sequence_id");
                }
            }
        }
    }

    public String getUniqueID() {
        return this.b;
    }

    public void setUniqueID(String str) {
        this.b = str;
    }

    public void setExtProperty(Map<String, String> map) {
        synchronized (this.d) {
            Map<String, String> map2 = this.c;
            if (map != null) {
                for (Entry next : map.entrySet()) {
                    Object key = next.getKey();
                    Object value = next.getValue();
                    if (!(key == null || value == null)) {
                        map2.put(key.toString(), value.toString());
                    }
                }
            }
        }
    }

    public int getSequenceID() {
        return this.a;
    }

    public void onDestroy() {
    }
}
