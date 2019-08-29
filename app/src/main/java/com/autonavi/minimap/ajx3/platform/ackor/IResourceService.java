package com.autonavi.minimap.ajx3.platform.ackor;

import android.content.res.AssetManager;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface IResourceService {
    AjxFileInfo getAjxFileInfo();

    IAjxFileReadListener getAjxFileReadListener();

    AssetManager getAssets();

    String onPreOpenFileStream(String str);
}
