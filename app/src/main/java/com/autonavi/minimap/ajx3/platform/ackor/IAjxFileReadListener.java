package com.autonavi.minimap.ajx3.platform.ackor;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface IAjxFileReadListener {
    void onOpenFailed(int i);

    void onOpenFailed(int i, String str);

    void onReadFailed(String str, String str2);
}
