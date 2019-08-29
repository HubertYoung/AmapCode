package com.autonavi.bundle.entity.infolite.internal.template;

import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;

public class PoiTaobaoTemplate extends PoiLayoutTemplate {
    private String deliveryCom;
    private String deliveryScore;
    private String descCom;
    private String descScore;
    private String mCatlist;
    private String name;
    private String sellScore;
    private String serviceCom;
    private String serviceScore;
    private String type;

    public String getValue() {
        return null;
    }

    public int isShown() {
        return 0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getSellScore() {
        return this.sellScore;
    }

    public void setSellScore(String str) {
        this.sellScore = str;
    }

    public String getmCatlist() {
        return this.mCatlist;
    }

    public void setmCatlist(String str) {
        this.mCatlist = str;
    }

    public String getDescCom() {
        return this.descCom;
    }

    public void setDescCom(String str) {
        this.descCom = str;
    }

    public String getDescScore() {
        return this.descScore;
    }

    public void setDescScore(String str) {
        this.descScore = str;
    }

    public String getServiceCom() {
        return this.serviceCom;
    }

    public void setServiceCom(String str) {
        this.serviceCom = str;
    }

    public String getServiceScore() {
        return this.serviceScore;
    }

    public void setServiceScore(String str) {
        this.serviceScore = str;
    }

    public String getDeliveryCom() {
        return this.deliveryCom;
    }

    public void setDeliveryCom(String str) {
        this.deliveryCom = str;
    }

    public String getDeliveryScore() {
        return this.deliveryScore;
    }

    public void setDeliveryScore(String str) {
        this.deliveryScore = str;
    }
}
