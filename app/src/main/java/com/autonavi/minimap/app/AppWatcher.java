package com.autonavi.minimap.app;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

public final class AppWatcher {

    @Path(builder = AosURLBuilder.class, host = "h5_log_url", sign = {"id", "timestamp"}, url = "/ws/h5_log?")
    public static class UninstallLogParam implements ParamEntity {
        public String id = "";
        public String source;
        public String timestamp;

        public UninstallLogParam() {
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            this.timestamp = sb.toString();
            this.source = "uninstall";
        }
    }
}
