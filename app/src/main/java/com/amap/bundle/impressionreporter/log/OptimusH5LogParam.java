package com.amap.bundle.impressionreporter.log;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "h5_log_url", sign = {"id", "timestamp"}, url = "/ws/h5_log?")
public class OptimusH5LogParam implements ParamEntity {
    public String id = "ad_display";
    public String msg_id;
    public int operateType;
    public int tag;
    public String timestamp;

    public OptimusH5LogParam() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        this.timestamp = sb.toString();
        this.msg_id = "";
        this.tag = 2;
        this.operateType = 1;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("id=");
        stringBuffer.append(this.id);
        stringBuffer.append(", msg_id=");
        stringBuffer.append(this.msg_id);
        stringBuffer.append(", tag=");
        stringBuffer.append(this.tag);
        stringBuffer.append(", operateType=");
        stringBuffer.append(this.operateType);
        stringBuffer.append(",timestamp=");
        stringBuffer.append(this.timestamp);
        return stringBuffer.toString();
    }
}
