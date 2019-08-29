package com.autonavi.bundle.uitemplate.mapwidget.widget.activity;

import android.widget.ImageView;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IEventDelegate;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import java.io.File;

public interface IActivityEventDelegate extends IEventDelegate {

    public interface LoadGifImgCallback {
        void onFail();

        void onSuccess(File file);
    }

    void executeMsgAction(AmapMessage amapMessage);

    void loadGifImg(ImageView imageView, String str, LoadGifImgCallback loadGifImgCallback);

    void reportActivityShowLog(AmapMessage amapMessage);
}
