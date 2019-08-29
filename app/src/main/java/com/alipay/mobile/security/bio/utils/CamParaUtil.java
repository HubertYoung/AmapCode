package com.alipay.mobile.security.bio.utils;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CamParaUtil {
    private static CamParaUtil b = null;
    private CameraSizeComparator a = new CameraSizeComparator();

    public class CameraSizeComparator implements Comparator<Size> {
        public CameraSizeComparator() {
        }

        public int compare(Size size, Size size2) {
            if (size.width == size2.width) {
                return 0;
            }
            if (size.width > size2.width) {
                return 1;
            }
            return -1;
        }
    }

    private CamParaUtil() {
    }

    public static synchronized CamParaUtil getInstance() {
        CamParaUtil camParaUtil;
        synchronized (CamParaUtil.class) {
            if (b == null) {
                camParaUtil = new CamParaUtil();
                b = camParaUtil;
            } else {
                camParaUtil = b;
            }
        }
        return camParaUtil;
    }

    public Size getPropPreviewSize(List<Size> list, float f, int i) {
        int i2;
        if (list == null) {
            return null;
        }
        Collections.sort(list, this.a);
        int i3 = 0;
        Iterator<Size> it = list.iterator();
        while (true) {
            i2 = i3;
            if (!it.hasNext()) {
                break;
            }
            Size next = it.next();
            if (next.width >= i && equalRate(next, f)) {
                BioLog.i("PreviewSize:w = " + next.width + "h = " + next.height);
                break;
            }
            i3 = i2 + 1;
        }
        if (i2 == list.size()) {
            i2 = list.size() - 1;
        }
        return list.get(i2);
    }

    public Size getPropPreviewSize(List<Size> list, int i, int i2) {
        Size size;
        if (list == null) {
            return null;
        }
        Collections.sort(list, this.a);
        Iterator<Size> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                size = null;
                break;
            }
            size = it.next();
            if (size.width >= i && size.height >= i2) {
                BioLog.i("PreviewSize:w = " + size.width + "h = " + size.height);
                break;
            }
        }
        return size;
    }

    public Size getPropPictureSize(List<Size> list, float f, int i) {
        int i2 = 0;
        if (list == null) {
            return null;
        }
        Collections.sort(list, this.a);
        Iterator<Size> it = list.iterator();
        float f2 = 0.0f;
        int i3 = 0;
        while (true) {
            int i4 = i2;
            if (!it.hasNext()) {
                return list.get(i4);
            }
            Size next = it.next();
            if (next.width >= i) {
                if (i4 == 0) {
                    f2 = a(next, f);
                    i4 = i3;
                }
                if (f2 > a(next, f)) {
                    f2 = a(next, f);
                    i2 = i3;
                    i3++;
                    f2 = f2;
                }
            }
            i2 = i4;
            i3++;
            f2 = f2;
        }
    }

    private static float a(Size size, float f) {
        return Math.abs((((float) size.width) / ((float) size.height)) - f);
    }

    public boolean equalRate(Size size, float f) {
        if (((double) Math.abs((((float) size.width) / ((float) size.height)) - f)) <= 0.03d) {
            return true;
        }
        return false;
    }

    public void printSupportPreviewSize(Parameters parameters) {
        List<Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        if (supportedPreviewSizes != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < supportedPreviewSizes.size()) {
                    Size size = supportedPreviewSizes.get(i2);
                    BioLog.i("previewSizes:width = " + size.width + " height = " + size.height);
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }

    public void printSupportPictureSize(Parameters parameters) {
        List<Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
        if (supportedPictureSizes != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < supportedPictureSizes.size()) {
                    Size size = supportedPictureSizes.get(i2);
                    BioLog.i("pictureSizes:width = " + size.width + " height = " + size.height);
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }

    public void printSupportFocusMode(Parameters parameters) {
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        if (supportedFocusModes != null) {
            for (String str : supportedFocusModes) {
                BioLog.i("focusModes--" + str);
            }
        }
    }

    public static Map<String, String> getCameraResolution() {
        HashMap hashMap = new HashMap();
        new ArrayList();
        try {
            int numberOfCameras = Camera.getNumberOfCameras();
            for (int i = 0; i < numberOfCameras; i++) {
                Camera open = Camera.open(i);
                CameraInfo cameraInfo = new CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing == 1) {
                    Size size = open.getParameters().getSupportedPictureSizes().get(0);
                    hashMap.put("frontCamera", size.width + "*" + size.height);
                } else if (cameraInfo.facing == 0) {
                    Size size2 = open.getParameters().getSupportedPictureSizes().get(0);
                    hashMap.put("backCamera", size2.width + "*" + size2.height);
                }
                if (open != null) {
                    open.release();
                }
            }
        } catch (Exception e) {
        }
        return hashMap;
    }
}
