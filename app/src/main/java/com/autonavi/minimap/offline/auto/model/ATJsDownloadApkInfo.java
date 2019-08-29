package com.autonavi.minimap.offline.auto.model;

import android.support.annotation.Keep;
import com.autonavi.minimap.offline.auto.model.ATJsApkInfo.ATJsApkItem;
import java.io.Serializable;

@Keep
public class ATJsDownloadApkInfo implements Serializable {
    private static final long serialVersionUID = -8259714498885743006L;
    public int code;
    public ATJsApkItem item;
    public int ret_type;
}
