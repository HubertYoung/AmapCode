package com.alipay.mobile.beehive.service;

import android.os.Bundle;
import java.util.List;

public interface PhotoSelectListener {
    void onPhotoSelected(List<PhotoInfo> list, Bundle bundle);

    void onSelectCanceled();
}
