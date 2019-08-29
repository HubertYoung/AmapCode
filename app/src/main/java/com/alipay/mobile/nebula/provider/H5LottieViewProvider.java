package com.alipay.mobile.nebula.provider;

import android.app.Activity;
import android.view.View;
import java.util.Map;

public interface H5LottieViewProvider {
    View getLottieView(Activity activity);

    void playAnimation(View view);

    void setBackgroundColor(View view, int i);

    void setImgs(View view, Map<String, byte[]> map);

    void setLocationXY(View view, int i, int i2);

    void setMainJson(View view, byte[] bArr);

    void setWidthAndHeight(View view, int i, int i2);

    void stopAnimation(View view);
}
