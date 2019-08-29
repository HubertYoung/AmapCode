package com.autonavi.bundle.amaphome.log;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.sdk.log.util.LogConstant;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Path(builder = AosURLBuilder.class, host = "h5_log_url", sign = {"id", "timestamp"}, url = "/ws/h5_log?")
public class RedesignOnLineLogParam implements ParamEntity {
    public String click = LogConstant.MAIN_MAP_DISPLAY;
    public String page = "P00001";
    public String status;
    public String type;

    @Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TypeIntDef {
        public static final String NEW_MAP = "1";
        public static final String OLD_MAP = "0";
    }

    public RedesignOnLineLogParam(String str, String str2) {
        this.type = str;
        this.status = str2;
    }
}
