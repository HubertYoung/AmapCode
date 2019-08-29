package com.autonavi.bundle.openlayer.entity;

import android.text.TextUtils;

public class LayerItem {
    private String action_url;
    private int control_status = 0;
    private String data;
    private long end_time;
    private String icon;
    private int icon_id;
    private boolean is_new = true;
    private int item_id;
    private int layer_id;
    private int layer_type;
    private int level = 0;
    private String name;
    private int order;
    private long start_time;
    private int switch_status = 0;
    private String toast;

    public int getItem_id() {
        return this.item_id;
    }

    public void setItem_id(int i) {
        this.item_id = i;
    }

    public int getLayer_id() {
        return this.layer_id;
    }

    public void setLayer_id(int i) {
        this.layer_id = i;
    }

    public int getLayer_type() {
        return this.layer_type;
    }

    public void setLayer_type(int i) {
        this.layer_type = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String str) {
        this.icon = str;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public String getAction_url() {
        return this.action_url;
    }

    public void setAction_url(String str) {
        this.action_url = str;
    }

    public long getStart_time() {
        return this.start_time;
    }

    public void setStart_time(long j) {
        this.start_time = j;
    }

    public long getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(long j) {
        this.end_time = j;
    }

    public int getSwitch_status() {
        return this.switch_status;
    }

    public void setSwitch_Status(int i) {
        this.switch_status = i;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public String getToast() {
        return this.toast;
    }

    public void setToast(String str) {
        this.toast = str;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int i) {
        this.order = i;
    }

    public int getIcon_id() {
        return this.icon_id;
    }

    public void setIcon_id(int i) {
        this.icon_id = i;
    }

    public boolean isIs_new() {
        return this.is_new;
    }

    public void setIs_new(boolean z) {
        this.is_new = z;
    }

    public boolean isDynamic() {
        return !TextUtils.isEmpty(this.action_url) && !TextUtils.isEmpty(this.icon);
    }

    public int getControl_status() {
        return this.control_status;
    }

    public void setControl_status(int i) {
        this.control_status = i;
    }
}
