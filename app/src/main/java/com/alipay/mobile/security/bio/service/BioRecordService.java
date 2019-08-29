package com.alipay.mobile.security.bio.service;

import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.Map;

public abstract class BioRecordService extends BioService {
    public abstract int getSequenceID();

    public abstract String getUniqueID();

    public abstract void setExtProperty(Map<String, String> map);

    public abstract void setUniqueID(String str);

    public abstract void write(MetaRecord metaRecord);
}
