package com.alipay.mobile.beehive.imageedit.service;

import android.graphics.Bitmap;
import java.util.Map;

public interface InImageEditListener {
    void onResult(boolean z, String str, Bitmap bitmap, Map<String, Object> map);
}
