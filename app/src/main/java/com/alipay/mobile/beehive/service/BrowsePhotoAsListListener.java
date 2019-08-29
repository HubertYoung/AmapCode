package com.alipay.mobile.beehive.service;

import android.app.Activity;
import android.view.View;
import java.util.List;

public interface BrowsePhotoAsListListener {
    void onBrowseFinish(List<PhotoInfo> list, List<PhotoInfo> list2);

    boolean onPhotoClick(Activity activity, View view, List<PhotoInfo> list, int i);

    void onPhotoDelete(Activity activity, List<PhotoInfo> list, PhotoInfo photoInfo);

    boolean onPhotoLongClick(Activity activity, View view, List<PhotoInfo> list, int i);
}
