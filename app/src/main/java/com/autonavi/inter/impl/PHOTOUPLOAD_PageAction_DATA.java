package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.map.search.album.page.AlbumFolderPage;
import com.autonavi.map.search.album.page.AlbumMainPage;
import com.autonavi.map.search.album.page.AlbumPreviewPage;
import com.autonavi.map.search.album.page.AlbumSelectPhotoPage;
import com.autonavi.map.search.photo.page.PhotoPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.album.action.AlbumFolderPage", "amap.search.action.photo", "amap.album.action.AlbumSelectPhotoPage", "amap.album.action.AlbumMainPage", "amap.album.action.AlbumPreviewPage"}, module = "photoupload", pages = {"com.autonavi.map.search.album.page.AlbumFolderPage", "com.autonavi.map.search.photo.page.PhotoPage", "com.autonavi.map.search.album.page.AlbumSelectPhotoPage", "com.autonavi.map.search.album.page.AlbumMainPage", "com.autonavi.map.search.album.page.AlbumPreviewPage"})
@KeepName
public final class PHOTOUPLOAD_PageAction_DATA extends HashMap<String, Class<?>> {
    public PHOTOUPLOAD_PageAction_DATA() {
        put("amap.album.action.AlbumFolderPage", AlbumFolderPage.class);
        put("amap.search.action.photo", PhotoPage.class);
        put("amap.album.action.AlbumSelectPhotoPage", AlbumSelectPhotoPage.class);
        put("amap.album.action.AlbumMainPage", AlbumMainPage.class);
        put("amap.album.action.AlbumPreviewPage", AlbumPreviewPage.class);
    }
}
