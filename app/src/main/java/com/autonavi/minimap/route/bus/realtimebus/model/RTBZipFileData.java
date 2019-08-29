package com.autonavi.minimap.route.bus.realtimebus.model;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class RTBZipFileData implements Serializable {
    private List<String> paths;
    private String rtbBusIcon;
    private String rtbBusLike;
    private String rtbBusNormal;
    private String rtbBuslikeSelected;
    private String rtbClose2;
    private String rtbCompanyBg;
    private String rtbCompanyClose2;
    private String rtbCompanyIcon1;
    private String rtbCompanyIcon2;
    private String rtbCompanysignalIcon;
    private String rtbHomeBg;
    private String rtbIcon1;
    private String rtbNightBg;
    private String rtbNightClose2;
    private String rtbNightIcon1;
    private String rtbNightIcon2;
    private String rtbNightSignalIcon;
    private String rtbSelected;
    private String rtbSignalIcon;
    private String rtbhomeIcon2;

    public RTBZipFileData() {
        String b = dyv.b();
        if (!TextUtils.isEmpty(b)) {
            if (this.paths == null) {
                this.paths = new ArrayList();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            sb.append("busstop/rtb_bus_normal.png");
            this.rtbBusNormal = sb.toString();
            this.paths.add(this.rtbBusNormal);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(b);
            sb2.append("busstop/rtb_bus_like.png");
            this.rtbBusLike = sb2.toString();
            this.paths.add(this.rtbBusLike);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(b);
            sb3.append("busstop/rtb_busstop_selected.png");
            this.rtbSelected = sb3.toString();
            this.paths.add(this.rtbSelected);
            StringBuilder sb4 = new StringBuilder();
            sb4.append(b);
            sb4.append("/busstop/rtb_busstoplike_selected.png");
            this.rtbBuslikeSelected = sb4.toString();
            this.paths.add(this.rtbBuslikeSelected);
            StringBuilder sb5 = new StringBuilder();
            sb5.append(b);
            sb5.append("busstop/rtb_bus_icon.png");
            this.rtbBusIcon = sb5.toString();
            this.paths.add(this.rtbBusIcon);
            StringBuilder sb6 = new StringBuilder();
            sb6.append(b);
            sb6.append("home/rtb_homeicon2_pop.png");
            this.rtbhomeIcon2 = sb6.toString();
            this.paths.add(this.rtbhomeIcon2);
            StringBuilder sb7 = new StringBuilder();
            sb7.append(b);
            sb7.append("home/rtb_realtimebus_homepop.9.png");
            this.rtbHomeBg = sb7.toString();
            this.paths.add(this.rtbHomeBg);
            StringBuilder sb8 = new StringBuilder();
            sb8.append(b);
            sb8.append("home/rtb_realtimebus_close2.png");
            this.rtbClose2 = sb8.toString();
            this.paths.add(this.rtbClose2);
            StringBuilder sb9 = new StringBuilder();
            sb9.append(b);
            sb9.append("home/rtb_homeicon1_pop.png");
            this.rtbIcon1 = sb9.toString();
            this.paths.add(this.rtbIcon1);
            StringBuilder sb10 = new StringBuilder();
            sb10.append(b);
            sb10.append("home/rtb_realtimebus_signalicon.png");
            this.rtbSignalIcon = sb10.toString();
            this.paths.add(this.rtbSignalIcon);
            StringBuilder sb11 = new StringBuilder();
            sb11.append(b);
            sb11.append("work/rtb_workicon2_pop.png");
            this.rtbCompanyIcon2 = sb11.toString();
            this.paths.add(this.rtbCompanyIcon2);
            StringBuilder sb12 = new StringBuilder();
            sb12.append(b);
            sb12.append("work/rtb_realtimebus_officepop.9.png");
            this.rtbCompanyBg = sb12.toString();
            this.paths.add(this.rtbCompanyBg);
            StringBuilder sb13 = new StringBuilder();
            sb13.append(b);
            sb13.append("work/rtb_realtimebus_close2.png");
            this.rtbCompanyClose2 = sb13.toString();
            this.paths.add(this.rtbCompanyClose2);
            StringBuilder sb14 = new StringBuilder();
            sb14.append(b);
            sb14.append("work/rtb_workicon1_pop.png");
            this.rtbCompanyIcon1 = sb14.toString();
            this.paths.add(this.rtbCompanyIcon1);
            StringBuilder sb15 = new StringBuilder();
            sb15.append(b);
            sb15.append("work/rtb_realtimebus_signalicon.png");
            this.rtbCompanysignalIcon = sb15.toString();
            this.paths.add(this.rtbCompanysignalIcon);
            StringBuilder sb16 = new StringBuilder();
            sb16.append(b);
            sb16.append("night/rtb_nighticon2_pop.png");
            this.rtbNightIcon2 = sb16.toString();
            this.paths.add(this.rtbNightIcon2);
            StringBuilder sb17 = new StringBuilder();
            sb17.append(b);
            sb17.append("night/rtb_realtimebus_nightpop.9.png");
            this.rtbNightBg = sb17.toString();
            this.paths.add(this.rtbNightBg);
            StringBuilder sb18 = new StringBuilder();
            sb18.append(b);
            sb18.append("night/rtb_realtimebus_close2.png");
            this.rtbNightClose2 = sb18.toString();
            this.paths.add(this.rtbNightClose2);
            StringBuilder sb19 = new StringBuilder();
            sb19.append(b);
            sb19.append("night/rtb_nighticon1_pop.png");
            this.rtbNightIcon1 = sb19.toString();
            this.paths.add(this.rtbNightIcon1);
            StringBuilder sb20 = new StringBuilder();
            sb20.append(b);
            sb20.append("night/rtb_realtimebus_signalicon.png");
            this.rtbNightSignalIcon = sb20.toString();
            this.paths.add(this.rtbNightSignalIcon);
        }
    }

    public List<String> getPaths() {
        return this.paths;
    }

    public String getRtbBusNormal() {
        return this.rtbBusNormal;
    }

    public String getRtbBusLike() {
        return this.rtbBusLike;
    }

    public String getRtbSelected() {
        return this.rtbSelected;
    }

    public String getRtbBuslikeSelected() {
        return this.rtbBuslikeSelected;
    }

    public String getRtbBusIcon() {
        return this.rtbBusIcon;
    }

    public String getRtbhomeIcon2() {
        return this.rtbhomeIcon2;
    }

    public String getRtbHomeBg() {
        return this.rtbHomeBg;
    }

    public String getRtbClose2() {
        return this.rtbClose2;
    }

    public String getRtbIcon1() {
        return this.rtbIcon1;
    }

    public String getRtbSignalIcon() {
        return this.rtbSignalIcon;
    }

    public String getRtbCompanyIcon2() {
        return this.rtbCompanyIcon2;
    }

    public String getRtbCompanyBg() {
        return this.rtbCompanyBg;
    }

    public String getRtbCompanyClose2() {
        return this.rtbCompanyClose2;
    }

    public String getRtbCompanyIcon1() {
        return this.rtbCompanyIcon1;
    }

    public String getRtbCompanysignalIcon() {
        return this.rtbCompanysignalIcon;
    }

    public String getRtbNightIcon2() {
        return this.rtbNightIcon2;
    }

    public String getRtbNightBg() {
        return this.rtbNightBg;
    }

    public String getRtbNightClose2() {
        return this.rtbNightClose2;
    }

    public String getRtbNightIcon1() {
        return this.rtbNightIcon1;
    }

    public String getRtbNightSignalIcon() {
        return this.rtbNightSignalIcon;
    }
}
