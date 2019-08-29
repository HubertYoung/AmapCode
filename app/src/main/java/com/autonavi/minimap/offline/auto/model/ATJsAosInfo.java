package com.autonavi.minimap.offline.auto.model;

import android.support.annotation.Keep;
import com.autonavi.common.annotation.Name;
import java.io.Serializable;

@Keep
public class ATJsAosInfo implements Serializable {
    private static final long serialVersionUID = -5522141310122593422L;
    public String build;
    public String display_ver;
    public String div;
    @Name("send_params")
    public String sendParams;
    public long size;
    public String url;
    public String version;
}
