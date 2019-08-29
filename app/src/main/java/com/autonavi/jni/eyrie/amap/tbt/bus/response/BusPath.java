package com.autonavi.jni.eyrie.amap.tbt.bus.response;

import java.util.ArrayList;

public class BusPath {
    public String allLength;
    public String allfootlength;
    public ArrayList<BusTag> busTag;
    public String busindex;
    public String carbon;
    public Emergency emergency;
    public String endfootlength;
    public String endfoottime;
    public Walk endwalk;
    public String endwalkcoord;
    public BusPoi epoi;
    public String expense;
    public String expensetime;
    public String min_tag;
    public String night;
    public String risk_time_des;
    public ArrayList<BusSegment> segmentlist;
    public BusPoi spoi;
    public String time_tag;
    public String time_tag_des;

    public static class AlterBus {
        public String bus_des;
        public String bus_key_name;
        public String bus_time_tag;
        public String busid;
        public String busname;
        public String bustype;
        public String cityCode;
        public String color;
        public String directionName;
        public String drivercoord;
        public String driverlength;
        public String drivertime;
        public Emergency emergency;
        public String endid;
        public String endname;
        public String endtime;
        public String estation_poiid;
        public Eta eta;
        public String footlength;
        public String foottime;
        public String interval_desc;
        public String loop;
        public String passdepotcoord;
        public String passdepotcount;
        public String passdepotid;
        public String passdepotname;
        public String price;
        public String pricecount;
        public String realtime;
        public String sstation_poiid;
        public String startid;
        public String startname;
        public String starttime;
        public String stationEndTime;
        public String stationStartTime;
        public String transfertip;
        public String transfertype;
    }

    public static class BusPoi {
        public String id;
        public String name;
        public String type;
        public String x;
        public String y;
    }

    public static class BusSegment {
        public ArrayList<AlterBus> alterlist;
        public String busDriveTime;
        public String busWaitTime;
        public String bus_des;
        public String bus_key_name;
        public String bus_time_tag;
        public String busid;
        public String busname;
        public String bustype;
        public String cityCode;
        public String color;
        public String depotdirection;
        public String directionName;
        public String drivercoord;
        public String driverlength;
        public String drivertime;
        public Emergency emergency;
        public String enddirection;
        public String endid;
        public String endname;
        public String endstangle;
        public String endtime;
        public String escrowdstat;
        public String estation_poiid;
        public Eta eta;
        public String footlength;
        public String foottime;
        public SubwayPort inport;
        public String interval_desc;
        public IrregularTime irregulartime;
        public String loop;
        public NewIrregularTime new_irregular_time;
        public SubwayPort outport;
        public String passdepotcoord;
        public String passdepotcount;
        public String passdepotid;
        public String passdepotlinkstat;
        public String passdepotname;
        public String passdepotstat;
        public String price;
        public String pricecount;
        public String realtime;
        public String sscrowdstat;
        public String sslinkstat;
        public String sstation_poiid;
        public String startdirection;
        public String startid;
        public String startname;
        public String starttime;
        public String stationEndTime;
        public String stationStartTime;
        public String time_tag;
        public String tm_limit;
        public String tmct_2early;
        public String transfertip;
        public String transfertype;
        public Walk walk;
        public String walkcoord;
    }

    public static class BusTag {
        public String color;
        public String desc;
    }

    public static class Emergency {
        public String busid;
        public String busname;
        public String edescription;
        public String estatus;
        public String eventTag;
        public String ldescription;
        public String linetype;
        public String sdescription;
        public String sstatus;
    }

    public static class Eta {
        public String etaCoords;
        public ArrayList<EtaLnk> lnk;
    }

    public static class EtaLnk {
        public String eidx;
        public String sidx;
        public String v;
    }

    public static class Irregular {
        public String description;
        public String mark;
        public ArrayList<String> timedesc;
    }

    public static class IrregularTime {
        public String holiday;
        public String normalday;
        public String workday;
    }

    public static class NewIrregularTime {
        public ArrayList<Irregular> irregular;
        public String title;
        public String type;
    }

    public static class SubwayPort {
        public String buscolor;
        public String buskeyname;
        public String coord;
        public String description;
        public String name;
    }

    public static class Walk {
        public String action;
        public String dir;
        public ArrayList<WalkInfo> infolist;
    }

    public static class WalkInfo {
        public String assist;
        public String coord;
        public String distance;
        public String en_locate;
        public String indoor;
        public String main;
        public String road;
        public String walktype_orgn;
    }
}
