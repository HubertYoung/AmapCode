package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.minimap.photograph.LaunchCameraAndGalleryPage;
import com.autonavi.minimap.photograph.LaunchOnlyCameraPage;
import com.autonavi.minimap.photograph.LaunchOnlyGalleryPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.basemap.action.photo_select_gallery", "amap.basemap.action.photo_select_camera", "amap.basemap.action.photo_select_camera_gallery"}, module = "photoselect", pages = {"com.autonavi.minimap.photograph.LaunchOnlyGalleryPage", "com.autonavi.minimap.photograph.LaunchOnlyCameraPage", "com.autonavi.minimap.photograph.LaunchCameraAndGalleryPage"})
@KeepName
public final class PHOTOSELECT_PageAction_DATA extends HashMap<String, Class<?>> {
    public PHOTOSELECT_PageAction_DATA() {
        put("amap.basemap.action.photo_select_gallery", LaunchOnlyGalleryPage.class);
        put("amap.basemap.action.photo_select_camera", LaunchOnlyCameraPage.class);
        put("amap.basemap.action.photo_select_camera_gallery", LaunchCameraAndGalleryPage.class);
    }
}
