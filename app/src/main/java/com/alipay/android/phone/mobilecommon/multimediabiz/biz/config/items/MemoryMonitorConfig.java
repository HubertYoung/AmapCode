package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;
import java.util.List;

public class MemoryMonitorConfig {
    @JSONField(name = "kem")
    public int enableKnockOutExpiredMem = 1;
    @JSONField(name = "es")
    public List<MemoryExpiredStrategy> expiredStrategies;
    @JSONField(name = "kop")
    public long knockOutPeriod = 20000;
    @JSONField(name = "l1ts")
    public List<MemoryTrimStrategy> level1TrimStrategies;
    @JSONField(name = "l2ts")
    public List<MemoryTrimStrategy> level2TrimStrategies;
    @JSONField(name = "l3ts")
    public List<MemoryTrimStrategy> level3TrimStrategies;
    @JSONField(name = "l1")
    public int supportLevel1 = 1;
    @JSONField(name = "l2")
    public int supportLevel2 = 1;
    @JSONField(name = "l3")
    public int supportLevel3 = 1;

    public List<MemoryTrimStrategy> getMemoryTrimStrategyForLevel1() {
        if (this.level1TrimStrategies == null && this.supportLevel1 == 1) {
            this.level1TrimStrategies = new ArrayList();
            this.level1TrimStrategies.add(new MemoryTrimStrategy(1, 25165824));
        }
        return this.level1TrimStrategies;
    }

    public List<MemoryTrimStrategy> getMemoryTrimStrategyForLevel2() {
        if (this.level2TrimStrategies == null && this.supportLevel2 == 1) {
            this.level2TrimStrategies = new ArrayList();
            this.level2TrimStrategies.add(new MemoryTrimStrategy(1, 2097152));
            this.level2TrimStrategies.add(new MemoryTrimStrategy(3, 0));
        }
        return this.level2TrimStrategies;
    }

    public List<MemoryTrimStrategy> getMemoryTrimStrategyForLevel3() {
        if (this.level3TrimStrategies == null && this.supportLevel3 == 1) {
            this.level3TrimStrategies = new ArrayList();
            this.level3TrimStrategies.add(new MemoryTrimStrategy(1, 1048576));
            this.level3TrimStrategies.add(new MemoryTrimStrategy(2, 0));
        }
        return this.level3TrimStrategies;
    }

    public List<MemoryExpiredStrategy> getExpiredStrategies() {
        if (this.expiredStrategies == null && this.enableKnockOutExpiredMem == 1) {
            this.expiredStrategies = new ArrayList(3);
            this.expiredStrategies.add(new MemoryExpiredStrategy(3, 300000));
            this.expiredStrategies.add(new MemoryExpiredStrategy(2, 300000));
            this.expiredStrategies.add(new MemoryExpiredStrategy(1, 900000));
            this.expiredStrategies.add(new MemoryExpiredStrategy(4, 300000));
        }
        return this.expiredStrategies;
    }

    public String toString() {
        return "MemoryMonitorConfig{supportLevel1=" + this.supportLevel1 + ", supportLevel2=" + this.supportLevel2 + ", supportLevel3=" + this.supportLevel3 + ", level1TrimStrategies=" + getMemoryTrimStrategyForLevel1() + ", level2TrimStrategies=" + getMemoryTrimStrategyForLevel2() + ", level3TrimStrategies=" + getMemoryTrimStrategyForLevel3() + '}';
    }
}
