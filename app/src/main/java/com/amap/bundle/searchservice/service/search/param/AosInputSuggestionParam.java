package com.amap.bundle.searchservice.service.search.param;

import android.graphics.Rect;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SearchURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.Serializable;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
@Path(builder = SearchURLBuilder.class, host = "search_aos_url", sign = {"city", "words"}, url = "ws/mapapi/poi/tipslite?")
public class AosInputSuggestionParam implements ParamEntity, Serializable {
    public static final String SUGGUEST_TYPE_ALL = "poi|bus|busline";
    public static final String SUGGUEST_TYPE_BUS_BUSLINE = "bus|busline";
    public static final String SUGGUEST_TYPE_POI = "poi";
    public static final String SUGGUEST_TYPE_POI_BUS = "poi|bus";
    public static final String SUGGUEST_TYPE_REAL_TIME_BUSLINE = "busline";
    private static final long serialVersionUID = 88473830998601L;
    public boolean adcode;
    public String category;
    public String city = null;
    public String datatype;
    public int filter_result_type;
    public String geoobj;
    public double latitude;
    public double longitude;
    public boolean need_virtualtip;
    public String search_sceneid;
    public String superid;
    public String user_city;
    public String user_loc = null;
    public String words = null;
    public String x;
    public String y;

    public AosInputSuggestionParam(String str, String str2, String str3, String str4, String str5, String str6, boolean z, Rect rect, int i, int i2) {
        String str7 = null;
        StringBuilder sb = new StringBuilder();
        sb.append(LocationInstrument.getInstance().getLatestPosition().getAdCode());
        this.user_city = sb.toString();
        this.geoobj = null;
        this.x = null;
        this.y = null;
        this.category = null;
        this.datatype = null;
        this.search_sceneid = null;
        this.superid = "";
        this.filter_result_type = 0;
        this.longitude = 0.0d;
        this.latitude = 0.0d;
        this.adcode = true;
        this.need_virtualtip = true;
        convertAdcode2Citycode(str2);
        this.words = str;
        this.user_loc = str3;
        this.user_city = str4;
        this.category = str5;
        this.datatype = str6;
        if (str6.equals("busline")) {
            this.superid = SuperId.BIT_8_TIPS;
        }
        DPoint a = cfg.a((long) i, (long) i2);
        this.longitude = a.x;
        this.latitude = a.y;
        if (rect == null) {
            this.geoobj = "";
        } else {
            this.geoobj = getGeoobj(rect);
        }
        this.x = z ? String.valueOf(this.longitude) : null;
        this.y = z ? String.valueOf(this.latitude) : str7;
    }

    private void convertAdcode2Citycode(String str) {
        if (str != null && str.length() > 4) {
            try {
                this.city = li.a().a(str).i;
                return;
            } catch (Exception unused) {
            }
        }
        this.city = str;
    }

    private String getGeoobj(Rect rect) {
        StringBuffer stringBuffer = new StringBuffer();
        if (rect != null) {
            DPoint a = cfg.a((long) rect.left, (long) rect.top);
            DPoint a2 = cfg.a((long) rect.right, (long) rect.bottom);
            stringBuffer.append(a.x);
            stringBuffer.append(MergeUtil.SEPARATOR_KV);
            stringBuffer.append(a.y);
            stringBuffer.append(MergeUtil.SEPARATOR_KV);
            stringBuffer.append(a2.x);
            stringBuffer.append(MergeUtil.SEPARATOR_KV);
            stringBuffer.append(a2.y);
        }
        return stringBuffer.toString();
    }
}
