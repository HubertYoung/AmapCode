package com.autonavi.minimap.search.templete.type;

import android.content.Context;
import java.io.Serializable;

public abstract class PoiLayoutTemplate implements Serializable {
    public static final String ARRAY = "array";
    public static final String ATM_BG = "poi_3atm";
    public static final String BACKGROUNDCOLOR = "backgroundcolor";
    public static final String BANK_BG = "poi_3bank";
    public static final String BJBANK_BG = "poi_1beijinghang";
    public static final String BUSDADZHAN_BG = "poi_4busdadzhan";
    public static final String BUTTON = "button";
    public static final String CHONGDIANZHAN_BG = "poi_charge";
    public static final String DYN_BUTTON = "dyn_button";
    public static final String GDBANK_BG = "poi_1guangdahang";
    public static final String GFBANK_BG = "poi_1guangfahang";
    public static final String GSBANK_BG = "poi_1gonghang";
    public static final String HTML = "html";
    public static final String HXBANK_BG = "poi_1huaxiahang";
    public static final String IMAGE = "img";
    public static final String JSBANK_BG = "poi_1jianhang";
    public static final String JTBANK_BG = "poi_1jiaohang";
    public static final String LINK = "link";
    public static final String MSBANK_BG = "poi_1minshenghang";
    public static final String NYBANK_BG = "poi_1nonghang";
    public static final String PABANK_BG = "poi_1pinganhang";
    public static final String PARKING_BG = "poi_5parking";
    public static final String PARKING_DEFAULT_BG = "poi_parking_default";
    public static final String PARKING_ENOUGH_BG = "poi_parking_enough";
    public static final String PARKING_EXHAUSTED_BG = "poi_parking_exhausted";
    public static final String PARKING_SHORTAGE_BG = "poi_parking_shortage";
    public static final String QPPETROL_BG = "poi_2qiaopaizhan";
    public static final String SHOPPING_SPLITER = "$$$";
    public static final String SHOPPING_SPLITER_REG = "\\${3}";
    public static final String SPLITER = "+++";
    public static final String SPLITER_REG = "\\+{3}";
    public static final String TEXT = "text";
    public static final String WEBIMG = "webimg";
    public static final String XYBANK_BG = "poi_1xingyehang";
    public static final String YZBANK_BG = "poi_1youzhenghang";
    public static final String ZGBANK_BG = "poi_1zhonghang";
    public static final String ZSBANK_BG = "poi_1zhaohang";
    public static final String ZSHPETROL_BG = "poi_2zhongshihuazhan";
    public static final String ZSYPETROL_BG = "poi_2zhongshiyouzhan";
    public static final String ZXBANK_BG = "poi_1zhongxinhang";
    private static final long serialVersionUID = 2866077874637725163L;
    private int id = 0;
    private String name = "";
    private String type = "";

    public abstract String getValue();

    public boolean isEnable() {
        return true;
    }

    public abstract int isShown();

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    /* access modifiers changed from: protected */
    public int getResourceId(Context context, String str, String str2) {
        if (context == null) {
            return -1;
        }
        return context.getResources().getIdentifier(str, str2, "com.autonavi.minimap");
    }
}
