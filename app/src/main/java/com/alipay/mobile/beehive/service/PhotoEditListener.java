package com.alipay.mobile.beehive.service;

import android.graphics.Bitmap;
import android.os.Bundle;

public interface PhotoEditListener {
    void onEditCanceled(PhotoInfo photoInfo);

    void onPhotoEdited(PhotoInfo photoInfo, Bundle bundle, Bitmap bitmap);
}
