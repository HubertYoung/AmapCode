package com.alipay.mobile.security.zim.a;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.log.BehaviourIdEnum;
import com.alipay.mobile.security.bio.log.VerifyBehavior;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.ZimRecordService;
import com.alipay.mobile.security.bio.service.local.apsecurity.ApSecurityService;
import com.alipay.mobile.security.bio.service.local.monitorlog.MonitorLogService;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.SignHelper;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: RecordProcessor */
public final class a {
    static a a;
    static String e = "zimStart";
    static String f = "verifyRequest";
    static String g = "initProdRequest";
    static String h = "initProdResponse";
    static String i = "authRequest";
    static String j = "authResponse";
    static String k = "validateRequest";
    static String l = "validateResponse";
    static String m = "verifyResponse";
    static String n = "zimExit";
    int b = 0;
    int c = 0;
    ZimRecordService d;
    HashMap<String, MetaRecord> o = new HashMap<>();
    protected Map<String, String> p = new HashMap();
    protected Context q;
    private final String r;
    private MonitorLogService s;

    public static synchronized a a(Context context) {
        a aVar;
        synchronized (a.class) {
            aVar = new a(context);
            a = aVar;
        }
        return aVar;
    }

    public static a a() {
        return a;
    }

    private a(Context context) {
        this.q = context;
        this.r = SignHelper.SHA1(System.currentTimeMillis() + Math.round(10000.0f));
        this.p.put("logModelVersion", "V1.0");
        this.p.put("logPlanId", "ZOLOZ_LOGPLAN_ALIPAYCLOUD_V1");
        this.p.put("logType", "BI_C_V1");
        this.o.put(e, new MetaRecord((String) "UC-RZHY-170807-01", (String) "event", (String) "20001117", e, 1));
        this.o.put(f, new MetaRecord((String) "UC-RZHY-170807-01", (String) "event", (String) "20001117", f, 1));
        this.o.put(g, new MetaRecord((String) "UC-RZHY-170807-02", (String) "event", (String) "20001117", g, 1));
        this.o.put(h, new MetaRecord((String) "UC-RZHY-170807-03", (String) "event", (String) "20001117", h, 1));
        this.o.put(i, new MetaRecord((String) "UC-RZHY-170807-04", (String) "event", (String) "20001117", i, 1));
        this.o.put(j, new MetaRecord((String) "UC-RZHY-170807-05", (String) "event", (String) "20001117", j, 1));
        this.o.put(k, new MetaRecord((String) "UC-RZHY-170807-06", (String) "event", (String) "20001117", k, 1));
        this.o.put(l, new MetaRecord((String) "UC-RZHY-170807-07", (String) "event", (String) "20001117", l, 1));
        this.o.put(m, new MetaRecord((String) "UC-RZHY-170807-08", (String) "event", (String) "20001117", m, 1));
        this.o.put(n, new MetaRecord((String) "UC-RZHY-170807-09", (String) "event", (String) "20001117", n, 1));
    }

    public final void a(String str) {
        String str2;
        BioServiceManager currentInstance = BioServiceManager.getCurrentInstance();
        if (this.d == null) {
            this.d = (ZimRecordService) currentInstance.getBioService(ZimRecordService.class.getName());
            this.p.put("zimID", str);
            ApSecurityService apSecurityService = (ApSecurityService) currentInstance.getBioService(ApSecurityService.class);
            if (apSecurityService != null) {
                str2 = apSecurityService.getApDidToken();
            } else {
                str2 = "";
            }
            if (TextUtils.isEmpty(str2)) {
                str2 = ApSecurityService.getStaticApDidToken();
            }
            this.p.put(DictionaryKeys.V2_APDID, str2);
            this.d.init(this.r, this.b, this.c, this.p);
            if (this.s != null) {
                this.s.destroy();
                this.s = null;
            }
        }
    }

    public final boolean b(String str) {
        return a(str, null);
    }

    public final boolean a(String str, Map<String, String> map) {
        JSONObject jSONObject;
        if (this.o.containsKey(str)) {
            MetaRecord metaRecord = this.o.get(str);
            if (this.d != null) {
                return this.d.write(metaRecord, map);
            }
            if (this.s == null) {
                this.s = (MonitorLogService) BioServiceManager.getLocalService(this.q, MonitorLogService.class);
                if (this.s == null) {
                    BioLog.w((Throwable) new IllegalStateException("mZimRecordService == null && mMonitorLogService == null"));
                    return false;
                }
                this.s.create(null);
            }
            VerifyBehavior verifyBehavior = new VerifyBehavior();
            verifyBehavior.setUserCaseID(metaRecord.getCaseID());
            String actionID = metaRecord.getActionID();
            verifyBehavior.setAppID(metaRecord.getAppID());
            verifyBehavior.setSeedID(metaRecord.getSeedID());
            verifyBehavior.setParam1(this.r);
            StringBuilder sb = new StringBuilder();
            int i2 = this.b + 1;
            this.b = i2;
            verifyBehavior.setParam2(sb.append(i2).toString());
            verifyBehavior.setParam3(this.c);
            verifyBehavior.setBizType(metaRecord.getBizType());
            verifyBehavior.setLoggerLevel(metaRecord.getPriority());
            verifyBehavior.addExtParam("base64", "true");
            HashMap hashMap = new HashMap();
            for (String next : this.p.keySet()) {
                String str2 = this.p.get(next);
                if (ZimRecordService.EXCLUDE_EXT_PARAMS.contains(next)) {
                    verifyBehavior.addExtParam(next, str2);
                } else {
                    hashMap.put(next, str2);
                }
            }
            verifyBehavior.addExtParam("publicParam", Base64.encodeToString(new JSONObject(hashMap).toString().getBytes(), 2));
            if (map != null) {
                jSONObject = new JSONObject(map);
            } else {
                jSONObject = new JSONObject();
            }
            verifyBehavior.addExtParam("extParam", Base64.encodeToString(jSONObject.toString().getBytes(), 2));
            this.s.logBehavior(BehaviourIdEnum.convert(actionID), verifyBehavior);
            return true;
        }
        BioLog.w("Not support record: key=" + str);
        return false;
    }
}
