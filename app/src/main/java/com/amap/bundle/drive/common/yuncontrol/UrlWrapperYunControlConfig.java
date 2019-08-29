package com.amap.bundle.drive.common.yuncontrol;

import android.text.TextUtils;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"diu", "div"}, url = "ws/mapapi/conf/tbt/file_update")
public class UrlWrapperYunControlConfig implements ParamEntity {
    public String tbt_version = "";
    public long version = 0;

    public void setTBTVersion(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.tbt_version = str;
        }
    }

    public void setVersion(long j) {
        this.version = j;
    }
}
