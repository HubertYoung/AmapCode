package com.autonavi.minimap.offline.auto.model;

import android.support.annotation.Keep;
import com.autonavi.common.annotation.Name;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Keep
public class ATJsApkInfo implements Serializable {
    private static final long serialVersionUID = 5321086222173994643L;
    public String build;
    public String display_ver;
    public String div;
    @Name("is_connected")
    public boolean isConnected = false;
    public List<ATJsApkItem> items = new ArrayList();
    public String version;

    @Keep
    public static final class ATJsApkItem implements Serializable {
        private static final long serialVersionUID = 8721485175535384730L;
        public String build;
        public String display_ver;
        public String div;
        @Name("download_bytes")
        public long downloadBytes;
        public String errorCause;
        public int id;
        public String local_file;
        @Name("send_params")
        public String sendParams;
        public int status;
        @Name("total_bytes")
        public long totalBytes;
        public String url;
        public String version;
    }
}
