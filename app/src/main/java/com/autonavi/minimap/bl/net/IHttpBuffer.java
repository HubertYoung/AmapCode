package com.autonavi.minimap.bl.net;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface IHttpBuffer {
    byte[] getBytes();

    int getLength();

    Object getPtr();

    void recycle();
}
