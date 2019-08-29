package com.alipay.mobile.security.bio.common.statistics;

import com.alipay.mobile.security.bio.service.BioService;
import java.util.Map;

public abstract class RecordExtService extends BioService {
    public abstract String getRetryID();

    public abstract String getUniqueID();

    public abstract void setExtProperty(Map<String, String> map);

    public abstract void setRetryID(String str);

    public abstract void setUniqueID(String str);

    public abstract void write(RecordExtAction recordExtAction);

    public abstract void write(RecordExtAction recordExtAction, Map<String, String> map);
}
