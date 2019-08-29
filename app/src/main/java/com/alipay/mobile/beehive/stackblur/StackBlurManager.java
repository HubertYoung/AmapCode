package com.alipay.mobile.beehive.stackblur;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import java.io.FileOutputStream;
import java.util.concurrent.ThreadPoolExecutor;

public class StackBlurManager {
    static final int EXECUTOR_THREADS = 2;
    private final a _blurProcess = new b();
    private final Bitmap _image;
    private Bitmap _result;

    static ThreadPoolExecutor getExecutor() {
        TaskScheduleService ts = (TaskScheduleService) MicroServiceUtil.getMicroService(TaskScheduleService.class);
        if (ts != null) {
            return ts.acquireExecutor(ScheduleType.NORMAL);
        }
        return null;
    }

    public StackBlurManager(Bitmap image) {
        this._image = image;
    }

    public Bitmap process(int radius) {
        this._result = this._blurProcess.a(this._image, (float) radius);
        return this._result;
    }

    public Bitmap returnBlurredImage() {
        return this._result;
    }

    public void saveIntoFile(String path) {
        try {
            this._result.compress(CompressFormat.PNG, 90, new FileOutputStream(path));
        } catch (Exception globalException) {
            PhotoLogger.warn((String) "StackBlurManager", (Throwable) globalException);
        }
    }

    public Bitmap getImage() {
        return this._image;
    }

    public Bitmap processNatively(int radius) {
        this._result = new NativeBlurProcess().a(this._image, (float) radius);
        return this._result;
    }
}
