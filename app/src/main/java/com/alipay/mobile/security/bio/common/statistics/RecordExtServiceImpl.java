package com.alipay.mobile.security.bio.common.statistics;

import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.service.BioRecordService;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.utils.BioLog;
import java.util.Hashtable;
import java.util.Map;

public class RecordExtServiceImpl extends RecordExtService {
    protected Hashtable<RecordExtAction, MetaRecord> a = new a(this);
    private BioRecordService b;
    private int c = 1;
    private String d;

    public void write(RecordExtAction recordExtAction) {
        a(recordExtAction, null);
    }

    public void write(RecordExtAction recordExtAction, Map<String, String> map) {
        a(recordExtAction, map);
    }

    private void a(RecordExtAction recordExtAction, Map<String, String> map) {
        MetaRecord metaRecord = this.a.get(recordExtAction);
        metaRecord.setParam2(Integer.toString(this.c));
        metaRecord.setParam3(this.d);
        metaRecord.setParam4(map);
        this.c++;
        BioLog.i("RecordExtService:" + metaRecord.toString());
        this.b.write(metaRecord);
    }

    public String getRetryID() {
        return this.d;
    }

    public void setRetryID(String str) {
        this.d = str;
    }

    public String getUniqueID() {
        return this.b.getUniqueID();
    }

    public void setExtProperty(Map<String, String> map) {
        this.b.setExtProperty(map);
    }

    public void setUniqueID(String str) {
        this.b.setUniqueID(str);
    }

    public void onCreate(BioServiceManager bioServiceManager) {
        this.b = (BioRecordService) bioServiceManager.getBioService(BioRecordService.class);
    }

    public void onDestroy() {
    }
}
