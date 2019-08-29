package com.autonavi.minimap.ajx3.upgrade;

import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import org.json.JSONException;
import org.json.JSONObject;

@Path(builder = AosURLBuilder.class, host = "h5_log_url", sign = {"id", "timestamp"}, url = "/ws/h5_log?")
public class H5LogParam implements ParamEntity {
    public String bundle_name;
    public int bundle_update_type;
    public String bundle_version;
    public String click;
    public String id = "";
    public String other;
    public String page;
    public String status;
    public String timestamp;
    public String type;
    public String url;

    public H5LogParam() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        this.timestamp = sb.toString();
        this.page = "";
        this.click = "";
        this.status = "";
        this.type = "";
        this.other = "";
        this.url = "";
        this.bundle_name = "";
        this.bundle_version = "";
        this.bundle_update_type = -1;
    }

    public String toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("timestamp", this.timestamp);
            jSONObject.put("page", this.page);
            jSONObject.put("click", this.click);
            jSONObject.put("status", this.status);
            jSONObject.put("type", this.type);
            jSONObject.put(H5ResourceHandlerUtil.OTHER, this.other);
            jSONObject.put("url", this.url);
            jSONObject.put("bundle_name", this.bundle_name);
            jSONObject.put("bundle_version", this.bundle_version);
            jSONObject.put("bundle_update_type", this.bundle_update_type);
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("id=");
        sb2.append(this.id);
        sb2.append("\n");
        sb.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder("timestamp=");
        sb3.append(this.timestamp);
        sb3.append("\n");
        sb.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder("page=");
        sb4.append(this.page);
        sb4.append("\n");
        sb.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder("click=");
        sb5.append(this.click);
        sb5.append("\n");
        sb.append(sb5.toString());
        StringBuilder sb6 = new StringBuilder("status=");
        sb6.append(this.status);
        sb6.append("\n");
        sb.append(sb6.toString());
        StringBuilder sb7 = new StringBuilder("type=");
        sb7.append(this.type);
        sb7.append("\n");
        sb.append(sb7.toString());
        StringBuilder sb8 = new StringBuilder("other=");
        sb8.append(this.other);
        sb8.append("\n");
        sb.append(sb8.toString());
        StringBuilder sb9 = new StringBuilder("url=");
        sb9.append(this.url);
        sb9.append("\n");
        sb.append(sb9.toString());
        StringBuilder sb10 = new StringBuilder("bundle_name=");
        sb10.append(this.bundle_name);
        sb10.append("\n");
        sb.append(sb10.toString());
        StringBuilder sb11 = new StringBuilder("bundle_version=");
        sb11.append(this.bundle_version);
        sb11.append("\n");
        sb.append(sb11.toString());
        StringBuilder sb12 = new StringBuilder("bundle_update_type=");
        sb12.append(this.bundle_update_type);
        sb12.append("\n");
        sb.append(sb12.toString());
        return sb.toString();
    }
}
