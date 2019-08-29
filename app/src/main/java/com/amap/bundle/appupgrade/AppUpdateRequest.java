package com.amap.bundle.appupgrade;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"dic", "div"}, url = "ws/app/conf/app_update?")
public class AppUpdateRequest implements ParamEntity {
    public String amap_ae8_params;
    public String appver;
    public String build;
    public String dic;
    public String dip;
    public String diu;
    public String div;
    public String gray_res;
    public long last_update_time = 0;
    public String md5;
    public String type;

    @SuppressFBWarnings({"UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
    public String toString() {
        StringBuilder sb = new StringBuilder("AppUpdateRequest{div='");
        sb.append(this.div);
        sb.append('\'');
        sb.append(", dic='");
        sb.append(this.dic);
        sb.append('\'');
        sb.append(", dip='");
        sb.append(this.dip);
        sb.append('\'');
        sb.append(", diu='");
        sb.append(this.diu);
        sb.append('\'');
        sb.append(", type='");
        sb.append(this.type);
        sb.append('\'');
        sb.append(", build='");
        sb.append(this.build);
        sb.append('\'');
        sb.append(", appver='");
        sb.append(this.appver);
        sb.append('\'');
        sb.append(", amap_ae8_params='");
        sb.append(this.amap_ae8_params);
        sb.append('\'');
        sb.append(", gray_res='");
        sb.append(this.gray_res);
        sb.append('\'');
        sb.append(", md5='");
        sb.append(this.md5);
        sb.append('\'');
        sb.append(", last_update_time=");
        sb.append(this.last_update_time);
        sb.append('}');
        return sb.toString();
    }
}
