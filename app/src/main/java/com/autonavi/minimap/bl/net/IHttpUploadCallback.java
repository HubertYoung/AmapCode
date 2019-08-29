package com.autonavi.minimap.bl.net;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface IHttpUploadCallback extends IHttpResponseCallback {
    void onProgress(int i, String str, long j, long j2);
}
