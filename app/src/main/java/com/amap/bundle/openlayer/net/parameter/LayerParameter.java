package com.amap.bundle.openlayer.net.parameter;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SnsURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = SnsURLBuilder.class, host = "aos_sns_url", sign = {"div", "dic"}, url = "ws/oss/maplayer/list?")
public class LayerParameter implements ParamEntity {
    public String adcode;
    public String dic;
    public String div;
    public int flag;
    public String md5;

    public LayerParameter(String str, String str2, String str3, int i, String str4) {
        this.adcode = str;
        this.div = str2;
        this.dic = str3;
        this.flag = i;
        this.md5 = str4;
    }
}
