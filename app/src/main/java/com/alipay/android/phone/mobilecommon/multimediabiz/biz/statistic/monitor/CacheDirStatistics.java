package com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.monitor;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.BaseStatistics;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP1;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP2;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SPExt;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.ST;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ST(caseId = "UC-MM-C40", seedId = "CacheDirStatistics")
public class CacheDirStatistics extends BaseStatistics {
    public Set<String> dirSet = new HashSet();
    @SPExt(name = "dir")
    public String dirs;
    @SP1
    public int retCode = 0;
    @SP2
    public int times = 0;

    public String getCaseId() {
        return "UC-MM-C40";
    }

    public String getSeedId() {
        return "CacheDirStatistics";
    }

    public String getParam1() {
        return String.valueOf(this.retCode);
    }

    public String getParam2() {
        return String.valueOf(this.times);
    }

    public String getParam3() {
        return "";
    }

    /* access modifiers changed from: protected */
    public void fillExtParams(Map<String, String> ext) {
        ext.put("dir", this.dirs);
    }

    public void submitRemoteAsync() {
        this.times = this.dirSet.size();
        if (this.times > 0) {
            this.dirs = Arrays.toString(this.dirSet.toArray());
            super.submitRemoteAsync();
        }
    }
}
