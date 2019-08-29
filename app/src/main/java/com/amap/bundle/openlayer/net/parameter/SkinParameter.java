package com.amap.bundle.openlayer.net.parameter;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SnsURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = SnsURLBuilder.class, host = "aos_sns_url", sign = {"div", "dic"}, url = "ws/oss/maplayer/list?")
public class SkinParameter implements ParamEntity {
    public String adcode;
    public String dic;
    public String div;
    public int flag;
    public String skin_md5;

    public SkinParameter(String str, String str2, String str3, int i, String str4) {
        this.adcode = str;
        this.div = str2;
        this.dic = str3;
        this.flag = i;
        this.skin_md5 = str4;
    }
}
