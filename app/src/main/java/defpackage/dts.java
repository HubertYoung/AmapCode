package defpackage;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

@TargetApi(15)
/* renamed from: dts reason: default package */
/* compiled from: CameraConfigurationUtils */
public final class dts {
    private static final Pattern a = Pattern.compile(";");

    public static void a(Parameters parameters, boolean z) {
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        String a2 = a((Collection<String>) supportedFocusModes, "auto");
        if (!z && a2 == null) {
            a2 = a((Collection<String>) supportedFocusModes, "macro", "edof");
        }
        if (a2 != null && !a2.equals(parameters.getFocusMode())) {
            parameters.setFocusMode(a2);
        }
    }

    public static void b(Parameters parameters, boolean z) {
        String str;
        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        if (z) {
            str = a((Collection<String>) supportedFlashModes, "torch", CameraParams.FLASH_MODE_ON);
        } else {
            str = a((Collection<String>) supportedFlashModes, CameraParams.FLASH_MODE_OFF);
        }
        if (str != null && !str.equals(parameters.getFlashMode())) {
            parameters.setFlashMode(str);
        }
    }

    public static Point a(Parameters parameters, Point point) {
        List<Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        if (supportedPreviewSizes == null) {
            Size previewSize = parameters.getPreviewSize();
            if (previewSize != null) {
                return new Point(previewSize.width, previewSize.height);
            }
            throw new IllegalStateException("Parameters contained no preview size!");
        }
        ArrayList<Size> arrayList = new ArrayList<>(supportedPreviewSizes);
        Collections.sort(arrayList, new Comparator<Size>() {
            public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
                Size size = (Size) obj;
                Size size2 = (Size) obj2;
                int i = size.height * size.width;
                int i2 = size2.height * size2.width;
                if (i2 < i) {
                    return -1;
                }
                return i2 > i ? 1 : 0;
            }
        });
        if (Log.isLoggable("qrcode", 4)) {
            StringBuilder sb = new StringBuilder();
            for (Size size : arrayList) {
                sb.append(size.width);
                sb.append('x');
                sb.append(size.height);
                sb.append(' ');
            }
        }
        double d = ((double) point.x) / ((double) point.y);
        Size size2 = null;
        Iterator it = arrayList.iterator();
        while (true) {
            boolean z = false;
            if (it.hasNext()) {
                Size size3 = (Size) it.next();
                int i = size3.width;
                int i2 = size3.height;
                if (i * i2 < 153600) {
                    it.remove();
                } else {
                    if (d < 1.0d) {
                        z = true;
                    }
                    int i3 = z ? i2 : i;
                    int i4 = z ? i : i2;
                    if (Math.abs((((double) i3) / ((double) i4)) - d) > 0.15d) {
                        it.remove();
                    } else if (i3 == point.x && i4 == point.y) {
                        return new Point(i, i2);
                    } else {
                        if (((double) Math.abs(i4 - point.y)) < Double.MAX_VALUE) {
                            size2 = size3;
                        }
                    }
                }
            } else if (size2 != null) {
                return new Point(size2.width, size2.height);
            } else {
                if (!arrayList.isEmpty()) {
                    Size size4 = (Size) arrayList.get(0);
                    return new Point(size4.width, size4.height);
                }
                Size previewSize2 = parameters.getPreviewSize();
                if (previewSize2 != null) {
                    return new Point(previewSize2.width, previewSize2.height);
                }
                throw new IllegalStateException("Parameters contained no preview size!");
            }
        }
    }

    private static String a(Collection<String> collection, String... strArr) {
        if (collection != null) {
            for (String str : strArr) {
                if (collection.contains(str)) {
                    return str;
                }
            }
        }
        return null;
    }
}
