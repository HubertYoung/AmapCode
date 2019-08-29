package com.alibaba.appmonitor.sample;

import com.alibaba.analytics.core.db.annotation.Column;
import com.alibaba.analytics.core.db.annotation.TableName;
import com.alibaba.analytics.utils.Logger;
import java.util.ArrayList;
import java.util.Map;

@TableName("ap_alarm")
public class AlarmConfig extends AMConifg {
    @Column("fcp")
    protected int failSampling = 0;
    @Column("scp")
    protected int successSampling = 0;

    public boolean isSampled(int i, String str, String str2, Boolean bool, Map<String, String> map) {
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(str);
        arrayList.add(str2);
        return sampling(i, arrayList, bool.booleanValue());
    }

    @Deprecated
    public boolean isSampled(int i, String str, String str2, Boolean bool) {
        return isSampled(i, str, str2, bool, null);
    }

    private boolean sampling(int i, ArrayList<String> arrayList, boolean z) {
        AlarmConfig alarmConfig = this;
        while (arrayList != null && arrayList.size() != 0) {
            String remove = arrayList.remove(0);
            if (!alarmConfig.isContains(remove)) {
                return alarmConfig.checkSelfSampling(i, z);
            }
            alarmConfig = (AlarmConfig) alarmConfig.getNext(remove);
        }
        return alarmConfig.checkSelfSampling(i, z);
    }

    private boolean checkSelfSampling(int i, boolean z) {
        if (z) {
            Logger.d((String) "", "samplingSeed", Integer.valueOf(i), "sampling", Integer.valueOf(this.successSampling));
            return i < this.successSampling;
        }
        Logger.d((String) "", "samplingSeed", Integer.valueOf(i), "sampling", Integer.valueOf(this.failSampling));
        return i < this.failSampling;
    }

    public void setSampling(int i) {
        this.successSampling = i;
        this.failSampling = i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AlarmConfig{");
        sb.append("module=");
        sb.append(this.module);
        sb.append(", monitorPoint=");
        sb.append(this.monitorPoint);
        sb.append(", offline=");
        sb.append(this.offline);
        sb.append(", failSampling=");
        sb.append(this.failSampling);
        sb.append(", successSampling=");
        sb.append(this.successSampling);
        sb.append('}');
        return sb.toString();
    }
}
