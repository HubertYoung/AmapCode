package com.autonavi.minimap.onekeycheck.module;

import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class CloudConfigInfos {
    public ConfigInfos urls;

    @KeepPublicClassMembers
    @KeepName
    public static class ConfigInfos {
        public String md5;
        public String url;
    }
}
