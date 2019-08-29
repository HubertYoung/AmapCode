package com.alipay.mobile.common.transport.iprank.dao.models;

public class IpLbsModel {
    public int id;
    public String latlng;

    public IpLbsModel() {
    }

    public IpLbsModel(int _id, String _latlng) {
        this.id = _id;
        this.latlng = _latlng;
    }

    public String toString() {
        return "IpLbsModel{id=" + this.id + ", latlng='" + this.latlng + '\'' + '}';
    }
}
