package com.alipay.android.phone.inside.offlinecode.model;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class BehaviorRecord {
    public long checkMgTime = System.currentTimeMillis();
    public long checkOgTime = System.currentTimeMillis();
    public int generateMgTimes;
    public int generateOgTimes;
    public long modifyTime;

    public String serializeJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("generateMgTimes", this.generateMgTimes);
            jSONObject.put("generateOgTimes", this.generateOgTimes);
            jSONObject.put("checkMgTime", this.checkMgTime);
            jSONObject.put("checkOgTime", this.checkOgTime);
            jSONObject.put("modifyTime", this.modifyTime);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return jSONObject.toString();
    }

    public static BehaviorRecord parse(String str) {
        BehaviorRecord behaviorRecord;
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("generateMgTimes", 0);
            int optInt2 = jSONObject.optInt("generateOgTimes", 0);
            long optLong = jSONObject.optLong("checkMgTime", System.currentTimeMillis());
            long optLong2 = jSONObject.optLong("checkOgTime", System.currentTimeMillis());
            long optLong3 = jSONObject.optLong("modifyTime", 0);
            behaviorRecord = new BehaviorRecord();
            try {
                behaviorRecord.generateMgTimes = optInt;
                behaviorRecord.generateOgTimes = optInt2;
                behaviorRecord.checkMgTime = optLong;
                behaviorRecord.checkOgTime = optLong2;
                behaviorRecord.modifyTime = optLong3;
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            behaviorRecord = null;
            LoggerFactory.f().c((String) "inside", th);
            return behaviorRecord;
        }
        return behaviorRecord;
    }
}
