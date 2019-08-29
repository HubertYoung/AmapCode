package defpackage;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.WindowManager;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.minimap.qrcode.FrontLightMode;

/* renamed from: dtr reason: default package */
/* compiled from: CameraConfigurationManager */
public final class dtr {
    public Point a;
    Point b;
    private final Context c;

    public dtr(Context context) {
        this.c = context;
    }

    public final void a(Camera camera) {
        Parameters parameters = camera.getParameters();
        Display defaultDisplay = ((WindowManager) this.c.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.a = point;
        this.b = dts.a(parameters, this.a);
    }

    public final void a(Camera camera, boolean z) {
        Parameters parameters = camera.getParameters();
        if (parameters != null) {
            a(parameters, FrontLightMode.readPref(PreferenceManager.getDefaultSharedPreferences(this.c)) == FrontLightMode.ON);
            dts.a(parameters, z);
            parameters.setPreviewSize(this.b.x, this.b.y);
            camera.setParameters(parameters);
            camera.setDisplayOrientation(90);
            Size previewSize = camera.getParameters().getPreviewSize();
            if (!(previewSize == null || (this.b.x == previewSize.width && this.b.y == previewSize.height))) {
                this.b.x = previewSize.width;
                this.b.y = previewSize.height;
            }
        }
    }

    public static boolean b(Camera camera) {
        if (camera != null) {
            try {
                Parameters parameters = camera.getParameters();
                if (parameters != null) {
                    String flashMode = parameters.getFlashMode();
                    if (flashMode == null || (!CameraParams.FLASH_MODE_ON.equals(flashMode) && !"torch".equals(flashMode))) {
                        return false;
                    }
                    return true;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public final void a(Parameters parameters, boolean z) {
        dts.b(parameters, z);
        PreferenceManager.getDefaultSharedPreferences(this.c);
    }
}
