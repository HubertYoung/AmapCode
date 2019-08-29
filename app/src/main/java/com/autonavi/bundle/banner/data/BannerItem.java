package com.autonavi.bundle.banner.data;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class BannerItem {
    public String action;
    public String background;
    public String bannerTitle;
    public String endDateTimestampInSecond;
    public String font;
    public int height;
    public String icon;
    public String id;
    public String imageURL;
    public String impression;
    public boolean mIsHide;
    public String msg_id;
    public String tag;
    public String title;
    public int type;
    public int width;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("id: ");
        sb2.append(this.id);
        sb2.append("\n");
        sb.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder("msg_id: ");
        sb3.append(this.msg_id);
        sb3.append("\n");
        sb.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder("tag: ");
        sb4.append(this.tag);
        sb4.append("\n");
        sb.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder("banner title: ");
        sb5.append(this.bannerTitle);
        sb5.append("\n");
        sb.append(sb5.toString());
        StringBuilder sb6 = new StringBuilder("type: ");
        sb6.append(this.type);
        sb6.append("\n");
        sb.append(sb6.toString());
        StringBuilder sb7 = new StringBuilder("image URL: ");
        sb7.append(this.imageURL);
        sb7.append("\n");
        sb.append(sb7.toString());
        StringBuilder sb8 = new StringBuilder("title: ");
        sb8.append(this.title);
        sb8.append("\n");
        sb.append(sb8.toString());
        StringBuilder sb9 = new StringBuilder("background:");
        sb9.append(this.background);
        sb9.append("\n");
        sb.append(sb9.toString());
        StringBuilder sb10 = new StringBuilder("impression:");
        sb10.append(this.impression);
        sb10.append("\n");
        sb.append(sb10.toString());
        if (this.endDateTimestampInSecond != null) {
            StringBuilder sb11 = new StringBuilder("date: ");
            sb11.append(this.endDateTimestampInSecond.toString());
            sb.append(sb11.toString());
        }
        sb.append("\n");
        return sb.toString();
    }
}
