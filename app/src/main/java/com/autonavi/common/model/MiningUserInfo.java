package com.autonavi.common.model;

import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@Deprecated
@KeepName
public class MiningUserInfo implements Cloneable {
    public String adcode = "";
    public String address = "";
    public String latitude = "";
    public String longitude = "";
    public String name = "";
    public String poiid = "";
}
