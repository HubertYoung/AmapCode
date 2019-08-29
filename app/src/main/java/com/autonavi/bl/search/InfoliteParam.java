package com.autonavi.bl.search;

public class InfoliteParam implements Cloneable {
    public boolean addr_poi_merge;
    public String ajxVersion;
    public String busorcar;
    public String category;
    public String center;
    public String city;
    public boolean citysuggestion;
    public String classify_data;
    public String cluster_state;
    public String cmspoi;
    public String cur_adcode;
    public String custom;
    public String data_type;
    public boolean direct_jump;
    public String geoobj;
    public String geoobj_adjust;
    public String hotelcheckin;
    public String hotelcheckout;
    public String id;
    public String interior_floor;
    public String interior_poi;
    public String interior_scene;
    public String isBrand;
    public boolean is_classify;
    public String keywords;
    public double latitude;
    public boolean loc_strict;
    public String log_center_id;
    public double longitude;
    public boolean need_codepoint;
    public boolean need_magicbox;
    public boolean need_parkinfo;
    public String need_recommend;
    public boolean need_utd;
    public OfflineParam offline_param;
    public String onlypoi;
    public int pagenum;
    public int pagesize;
    public boolean qii;
    public boolean query_acs;
    public String query_mode;
    public String query_scene;
    public String query_type;
    public String range;
    public String sc_stype;
    public int scenario;
    public String scene_id;
    public String scenefilter;
    public String schema_source;
    public int search_operate;
    public String search_sceneid;
    public String siv;
    public int sort_rule;
    public int specialpoi;
    public String sug;
    public String sugadcode;
    public String sugpoiname;
    public String superid;
    public String tip_rule;
    public String transfer_filter_flag;
    public String transfer_mode;
    public String transfer_nearby_bucket;
    public String transfer_nearby_keyindex;
    public String transfer_nearby_time_opt;
    public String transfer_pdheatmap;
    public String transfer_realtimebus_poi;
    public String transfer_selectfilter;
    public String transparent;
    public String transparent_center_around;
    public String user_city;
    public String user_loc;
    public String utd_sceneid;
    public String version;

    public InfoliteParam clone() {
        try {
            return (InfoliteParam) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
