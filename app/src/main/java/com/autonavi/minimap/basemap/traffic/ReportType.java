package com.autonavi.minimap.basemap.traffic;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"ME_MUTABLE_ENUM_FIELD"})
public enum ReportType {
    INVALID("", "", ""),
    CONGESTION("1055", "11021", "004"),
    TROUBLE("1050", "11010", ""),
    ACCIDENT("1050", "11011", "101"),
    PONDING("1100", "11100", "501"),
    POLICE("1095", "11033", "803"),
    PROCESS("1065", "11040", "201"),
    STOP("1070", "11050", ""),
    PIC("1105", "11071", ""),
    NO_EVENT("", "", "909");
    
    public final String layerId;
    public final String layerTag;
    public final String mEventType;

    private ReportType(String str, String str2, String str3) {
        this.layerId = str;
        this.layerTag = str2;
        this.mEventType = str3;
    }

    public final String getReportName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.layerId);
        sb.append("-");
        sb.append(this.layerTag);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(CONGESTION.layerId);
        sb3.append("-");
        sb3.append(CONGESTION.layerTag);
        if (sb2.equals(sb3.toString())) {
            return "拥堵";
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(TROUBLE.layerId);
        sb4.append("-");
        sb4.append(TROUBLE.layerTag);
        if (sb2.equals(sb4.toString())) {
            return "故障";
        }
        StringBuilder sb5 = new StringBuilder();
        sb5.append(ACCIDENT.layerId);
        sb5.append("-");
        sb5.append(ACCIDENT.layerTag);
        if (sb2.equals(sb5.toString())) {
            return "车祸";
        }
        StringBuilder sb6 = new StringBuilder();
        sb6.append(PONDING.layerId);
        sb6.append("-");
        sb6.append(PONDING.layerTag);
        if (sb2.equals(sb6.toString())) {
            return "积水";
        }
        StringBuilder sb7 = new StringBuilder();
        sb7.append(POLICE.layerId);
        sb7.append("-");
        sb7.append(POLICE.layerTag);
        if (sb2.equals(sb7.toString())) {
            return "警察";
        }
        StringBuilder sb8 = new StringBuilder();
        sb8.append(PROCESS.layerId);
        sb8.append("-");
        sb8.append(PROCESS.layerTag);
        if (sb2.equals(sb8.toString())) {
            return "施工";
        }
        StringBuilder sb9 = new StringBuilder();
        sb9.append(STOP.layerId);
        sb9.append("-");
        sb9.append(STOP.layerTag);
        if (sb2.equals(sb9.toString())) {
            return "封路";
        }
        StringBuilder sb10 = new StringBuilder();
        sb10.append(PIC.layerId);
        sb10.append("-");
        sb10.append(PIC.layerTag);
        return sb2.equals(sb10.toString()) ? "实景" : "";
    }
}
