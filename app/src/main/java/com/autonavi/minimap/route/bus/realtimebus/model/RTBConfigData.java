package com.autonavi.minimap.route.bus.realtimebus.model;

import android.text.TextUtils;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.Serializable;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class RTBConfigData implements Serializable {
    private String adr_imgresource;
    private String company_line_font;
    private String company_linecolor;
    private String company_status_font;
    private String company_statuscolor;
    private String home_line_font;
    private String home_linecolor;
    private String home_status_font;
    private String home_statuscolor;
    private String imgAbsolutePath;
    private String imgParentPath;
    private boolean isDownLoadAll = false;
    private String night_line_font;
    private String night_linecolor;
    private String night_status_font;
    private String night_statuscolor;
    private RTBZipFileData rtbZipFileData;
    private String version;

    public void createPath() {
        if (!TextUtils.isEmpty(this.adr_imgresource)) {
            this.imgAbsolutePath = dyv.a(dyw.a(this.adr_imgresource), FilePathHelper.SUFFIX_DOT_ZIP);
            this.imgParentPath = dyv.b();
            this.rtbZipFileData = new RTBZipFileData();
        }
    }

    public RTBZipFileData getRtbZipFileData() {
        return this.rtbZipFileData;
    }

    public void setRtbZipFileData(RTBZipFileData rTBZipFileData) {
        this.rtbZipFileData = rTBZipFileData;
    }

    public String getImgParentPath() {
        return this.imgParentPath;
    }

    public void setImgParentPath(String str) {
        this.imgParentPath = str;
    }

    public String getImgAbsolutePath() {
        return this.imgAbsolutePath;
    }

    public void setImgAbsolutePath(String str) {
        this.imgAbsolutePath = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public boolean isDownLoadAll() {
        return this.isDownLoadAll;
    }

    public void setDownLoadAll(boolean z) {
        this.isDownLoadAll = z;
    }

    public String getHome_status_font() {
        return this.home_status_font;
    }

    public int getHomeStatusFont() {
        if (TextUtils.isEmpty(this.home_status_font)) {
            return -1;
        }
        return Integer.parseInt(this.home_status_font);
    }

    public void setHome_status_font(String str) {
        this.home_status_font = str;
    }

    public String getHome_line_font() {
        return this.home_line_font;
    }

    public int getHomeLineFont() {
        if (TextUtils.isEmpty(this.home_line_font)) {
            return -1;
        }
        return Integer.parseInt(this.home_line_font);
    }

    public void setHome_line_font(String str) {
        this.home_line_font = str;
    }

    public String getHome_linecolor() {
        return this.home_linecolor;
    }

    public void setHome_linecolor(String str) {
        this.home_linecolor = str;
    }

    public String getHome_statuscolor() {
        return this.home_statuscolor;
    }

    public void setHome_statuscolor(String str) {
        this.home_statuscolor = str;
    }

    public String getCompany_status_font() {
        return this.company_status_font;
    }

    public int getCompanyStatusFont() {
        if (TextUtils.isEmpty(this.company_status_font)) {
            return -1;
        }
        return Integer.parseInt(this.company_status_font);
    }

    public void setCompany_status_font(String str) {
        this.company_status_font = str;
    }

    public String getCompany_line_font() {
        return this.company_line_font;
    }

    public int getCompanyLineFont() {
        if (TextUtils.isEmpty(this.company_line_font)) {
            return -1;
        }
        return Integer.parseInt(this.company_line_font);
    }

    public void setCompany_line_font(String str) {
        this.company_line_font = str;
    }

    public String getCompany_linecolor() {
        return this.company_linecolor;
    }

    public void setCompany_linecolor(String str) {
        this.company_linecolor = str;
    }

    public String getCompany_statuscolor() {
        return this.company_statuscolor;
    }

    public void setCompany_statuscolor(String str) {
        this.company_statuscolor = str;
    }

    public String getNight_status_font() {
        return this.night_status_font;
    }

    public int getNightStatusFont() {
        if (TextUtils.isEmpty(this.night_status_font)) {
            return -1;
        }
        return Integer.parseInt(this.night_status_font);
    }

    public void setNight_status_font(String str) {
        this.night_status_font = str;
    }

    public String getNight_line_font() {
        return this.night_line_font;
    }

    public int getNightLineFont() {
        if (TextUtils.isEmpty(this.night_line_font)) {
            return -1;
        }
        return Integer.parseInt(this.night_line_font);
    }

    public void setNight_line_font(String str) {
        this.night_line_font = str;
    }

    public String getNight_linecolor() {
        return this.night_linecolor;
    }

    public void setNight_linecolor(String str) {
        this.night_linecolor = str;
    }

    public String getNight_statuscolor() {
        return this.night_statuscolor;
    }

    public void setNight_statuscolor(String str) {
        this.night_statuscolor = str;
    }

    public String getAdr_imgresource() {
        return this.adr_imgresource;
    }

    public void setAdr_imgresource(String str) {
        this.adr_imgresource = str;
    }
}
