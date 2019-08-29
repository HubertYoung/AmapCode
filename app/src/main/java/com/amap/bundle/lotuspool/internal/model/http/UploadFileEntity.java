package com.amap.bundle.lotuspool.internal.model.http;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "lotuspool_upload_url", sign = {"diu", "div", "_aosmd5"}, url = "/rd/upfile?")
public class UploadFileEntity implements ParamEntity {
    public long command_id;
    public String dispatch_id;
    public long dispatch_time;
    public String md5;
    public int sequence;
}
