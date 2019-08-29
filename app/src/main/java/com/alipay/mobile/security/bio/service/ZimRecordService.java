package com.alipay.mobile.security.bio.service;

import com.alipay.mobile.security.bio.api.BioDetector;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ZimRecordService extends BioService {
    public static final HashSet<String> EXCLUDE_EXT_PARAMS = new HashSet<>(Arrays.asList(new String[]{"zimID", "logModelVersion", "logPlanId", BioDetector.EXT_KEY_SCENE_ID_BUNDLE, "uiVersion", "logType"}));

    public abstract void addExtProperties(Map<String, String> map);

    public abstract void addExtProperty(String str, String str2);

    public abstract Map<String, String> getExtProperties();

    public abstract int getRetryID();

    public abstract int getSequenceID();

    public abstract String getUniqueID();

    public abstract void init(String str, int i, int i2, Map<String, String> map);

    public abstract void retry();

    public abstract void setLogClassifier(Set<String> set);

    public abstract boolean write(MetaRecord metaRecord);

    public abstract boolean write(MetaRecord metaRecord, Map<String, String> map);
}
