package defpackage;

import android.app.Activity;
import android.content.Intent;
import com.autonavi.common.Callback;
import com.autonavi.gdtaojin.camera.CameraInterface;
import com.autonavi.gdtaojin.camera.CameraInterface.onCaptureButtonClickListener;
import java.util.HashMap;
import java.util.Map;

/* renamed from: adu reason: default package */
/* compiled from: GxdCamera */
public final class adu {
    public static void a(String str, Activity activity, int i, final Callback callback) {
        CameraInterface.setCameraFloder(str);
        CameraInterface.setOnCaptureButtonClickListener(new onCaptureButtonClickListener() {
            public final void onCaptureEnd() {
            }

            public final void onCapture() {
                callback.callback(null);
            }
        });
        CameraInterface.showCameraActivityForResult(activity, i);
    }

    public static Map<String, Object> a(Intent intent) {
        HashMap hashMap = new HashMap();
        hashMap.put("camera_pic_path", CameraInterface.getPicturePathByURI(intent.getData()));
        hashMap.put("shooted_orientation", Integer.valueOf((int) CameraInterface.getShootedOrientation(intent)));
        return hashMap;
    }
}
