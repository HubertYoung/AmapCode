package com.alipay.mobile.beehive.service;

import android.app.Activity;
import android.os.Bundle;
import java.util.List;

public interface PhotoBrowseListener {

    public interface V2 extends PhotoBrowseListener {
        void onPhotoEditWithIn(PhotoInfo photoInfo, PhotoInfo photoInfo2);
    }

    boolean onBottomMenuClick(Activity activity, List<PhotoInfo> list, PhotoMenu photoMenu);

    boolean onLongPressMenuClick(Activity activity, PhotoInfo photoInfo, PhotoMenu photoMenu);

    boolean onPhotoDelete(List<PhotoInfo> list, Bundle bundle);
}
