package com.alipay.mobile.beehive.photo.ui;

import com.alipay.mobile.beehive.photo.view.PhotoPreview;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoMenu;

public interface PhotoPagerListener {

    public interface V2 extends PhotoPagerListener {
        boolean onMenuClicked(PhotoMenu photoMenu);
    }

    public interface V3 extends V2 {
        void onPageSelected(int i, PhotoInfo photoInfo);
    }

    public interface V4 extends V3 {
        boolean onMenuClicked(PhotoMenu photoMenu, PhotoInfo photoInfo, PhotoPreview photoPreview);
    }

    void onPageClicked();

    boolean onPageLongClicked(String str, int i);

    void onPageScrolledAcross(int i, int i2, String str, String str2);

    void onPageSelected(int i, int i2, String str);
}
