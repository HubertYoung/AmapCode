package com.alipay.mobile.beehive.cityselect.model;

public class PageInfo {
    private int index;
    private int listIndex;
    private int listTop;
    private String name;
    private int type;

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index2) {
        this.index = index2;
    }

    public int getListIndex() {
        return this.listIndex;
    }

    public void setListIndex(int listIndex2) {
        this.listIndex = listIndex2;
    }

    public int getListTop() {
        return this.listTop;
    }

    public void setListTop(int listTop2) {
        this.listTop = listTop2;
    }
}
