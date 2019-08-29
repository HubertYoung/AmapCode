package com.autonavi.minimap.search.model;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SearchURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.common.model.GeoPoint;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@Path(builder = SearchURLBuilder.class, sign = {"id", "longitude", "latitude", "keywords", "category", "geoobj"}, url = "ws/mapapi/poi/infolite/?")
@KeepImplementations
@KeepName
public class SearchCallbackUrlWrapper implements ParamEntity {
    public String addr_poi_merge = "true";
    public String brand_id;
    public String busorcar = "car";
    public String category = "";
    public String center;
    public String city = "";
    public String citysuggestion = "true";
    public String classify_data = "";
    public String cmspoi = "1";
    public String data_type = "POI";
    public boolean direct_jump = true;
    public String geoobj;
    public String id = "";
    public String is_classify = "true";
    public String keywords;
    public String latitude = "";
    public boolean loc_strict = false;
    public String longitude = "";
    public String need_codepoint = "true";
    public boolean need_magicbox = false;
    public String need_parkinfo = "true";
    public String onlypoi = "poi";
    public String output = "json";
    public int pagenum;
    public int pagesize = 10;
    public String qii = "true";
    public String query_acs = "false";
    public String query_scene = "car";
    public String query_type = "TQUERY";
    public String range = "";
    public int scenario = 2;
    public String scene_id;
    public String scenefilter;
    public String search_operate = "0";
    public String search_sceneid = null;
    public String sort_rule = "0";
    public String source;
    public String specialpoi = "0";
    public String src_type = "";
    public String sug;
    public String sugadcode;
    public String sugpoiname;
    public String superid = "";
    public String user_city;
    public String user_loc;
    public String utd_sceneid = "101000";
    public String version = "2.13";

    public SearchCallbackUrlWrapper(String str, GeoPoint geoPoint, String str2, int i) {
        this.geoobj = str;
        this.pagenum = i;
        this.keywords = str2;
        if (geoPoint != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(geoPoint.getLongitude());
            sb.append(",");
            sb.append(geoPoint.getLatitude());
            this.user_loc = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(geoPoint.inMainland());
            this.user_city = sb2.toString();
        }
    }

    public SearchCallbackUrlWrapper(String str, GeoPoint geoPoint, String str2, int i, String str3) {
        this.geoobj = str;
        this.pagenum = i;
        this.keywords = str2;
        this.city = str3;
        if (geoPoint != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(geoPoint.getLongitude());
            sb.append(",");
            sb.append(geoPoint.getLatitude());
            this.user_loc = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(geoPoint.inMainland());
            this.user_city = sb2.toString();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("InfoliteParam [id=");
        sb.append(this.id);
        sb.append(", longitude=");
        sb.append(this.longitude);
        sb.append(", latitude=");
        sb.append(this.latitude);
        sb.append(", keywords=");
        sb.append(this.keywords);
        sb.append(", category=");
        sb.append(this.category);
        sb.append(", geoobj=");
        sb.append(this.geoobj);
        sb.append(", version=");
        sb.append(this.version);
        sb.append(", output=");
        sb.append(this.output);
        sb.append(", pagenum=");
        sb.append(this.pagenum);
        sb.append(", pagesize=");
        sb.append(this.pagesize);
        sb.append(", data_type=");
        sb.append(this.data_type);
        sb.append(", search_operate=");
        sb.append(this.search_operate);
        sb.append(", cmspoi=");
        sb.append(this.cmspoi);
        sb.append(", query_type=");
        sb.append(this.query_type);
        sb.append(", need_magicbox=");
        sb.append(this.need_magicbox);
        sb.append(", onlypoi=");
        sb.append(this.onlypoi);
        sb.append(", busorcar=");
        sb.append(this.busorcar);
        sb.append(", qii=");
        sb.append(this.qii);
        sb.append(", user_loc=");
        sb.append(this.user_loc);
        sb.append(", user_city=");
        sb.append(this.user_city);
        sb.append(", src_type=");
        sb.append(this.src_type);
        sb.append(", is_classify=");
        sb.append(this.is_classify);
        sb.append(", need_parkinfo=");
        sb.append(this.need_parkinfo);
        sb.append(", need_codepoint=");
        sb.append(this.need_codepoint);
        sb.append(", query_acs=");
        sb.append(this.query_acs);
        sb.append(", addr_poi_merge=");
        sb.append(this.addr_poi_merge);
        sb.append(", range=");
        sb.append(this.range);
        sb.append(", specialpoi=");
        sb.append(this.specialpoi);
        sb.append(", city=");
        sb.append(this.city);
        sb.append(", sort_rule=");
        sb.append(this.sort_rule);
        sb.append(", citysuggestion=");
        sb.append(this.citysuggestion);
        sb.append(", classify_data=");
        sb.append(this.classify_data);
        sb.append(", source=");
        sb.append(this.source);
        sb.append(", center=");
        sb.append(this.center);
        sb.append(", sugpoiname=");
        sb.append(this.sugpoiname);
        sb.append(", sugadcode=");
        sb.append(this.sugadcode);
        sb.append(", brand_id=");
        sb.append(this.brand_id);
        sb.append(", scene_id=");
        sb.append(this.scene_id);
        sb.append(", sug=");
        sb.append(this.sug);
        sb.append(", scenefilter=");
        sb.append(this.scenefilter);
        sb.append(", loc_strict=");
        sb.append(this.loc_strict);
        sb.append(", direct_jump=");
        sb.append(this.direct_jump);
        sb.append(", utd_sceneid=");
        sb.append(this.utd_sceneid);
        sb.append(", search_sceneid=");
        sb.append(this.search_sceneid);
        sb.append("]");
        return sb.toString();
    }
}
