package com.autonavi.minimap.bundle.msgbox.entity;

import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class AmapMessageModel {
    public static final String DEFAULT_CATEGORY = "1";
    public String actionUri;
    public AmapMessageBtnAction[] amapMessageBtnActions;
    public boolean barDisplay;
    public boolean boxDisplay;
    public String category = "1";
    public String countdownEndtime;
    public long createdTime;
    public String descMessage;
    public String goldImage1 = "";
    public String goldImage2 = "";
    public int goldNum = -1;
    public boolean hasShown = false;
    public String id;
    public String impression;
    public boolean isCountDown;
    public boolean isUnRead = true;
    public String label;
    public String labelColor;
    public String msgImgUri;
    public String msgImgUriV2;
    public int msgType;
    public String nickName;
    public int showType;
    public int tag;
    public String title;
    public int totalGoldNum = -1;
    public long updateTime;
    public String wordStatus;
}
