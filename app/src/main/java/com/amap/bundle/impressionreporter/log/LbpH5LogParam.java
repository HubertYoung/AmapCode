package com.amap.bundle.impressionreporter.log;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "h5_log_url", sign = {"id", "timestamp"}, url = "/ws/h5_log?")
public class LbpH5LogParam implements ParamEntity {
    public String id = "lbp_scheme";
    public String lbpvia;
    public String timestamp;

    public LbpH5LogParam() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        this.timestamp = sb.toString();
        this.lbpvia = "";
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("id=");
        stringBuffer.append(this.id);
        stringBuffer.append(", lbpvia=");
        stringBuffer.append(this.lbpvia);
        stringBuffer.append(",timestamp=");
        stringBuffer.append(this.timestamp);
        return stringBuffer.toString();
    }
}
