package com.alipay.mobile.antui.iconfont.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessagePopItem {
    public String content;
    public Map<String, IconInfo> extInfo;
    public HashMap<String, Object> externParam;
    public IconInfo icon;
    public ArrayList<String> optionBtn;
    public String title;

    public MessagePopItem() {
    }

    public MessagePopItem(String title2) {
        this.title = title2;
    }

    public MessagePopItem(IconInfo drawable, String title2) {
        this.icon = drawable;
        this.title = title2;
    }

    public MessagePopItem(IconInfo drawable, String title2, String content2) {
        this.icon = drawable;
        this.title = title2;
        this.content = content2;
    }

    public MessagePopItem(IconInfo drawable, String title2, String content2, ArrayList<String> optionBtn2) {
        this.icon = drawable;
        this.title = title2;
        this.content = content2;
        this.optionBtn = optionBtn2;
    }
}
