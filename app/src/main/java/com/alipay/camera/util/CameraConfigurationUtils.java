package com.alipay.camera.util;

import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera.Area;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build.VERSION;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.bqcscanservice.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class CameraConfigurationUtils {
    private static int a = -1;
    private static int b = -1;

    private CameraConfigurationUtils() {
    }

    public static void setFocus(Parameters parameters, boolean autoFocus) {
        String focusMode;
        List supportedFocusModes = parameters.getSupportedFocusModes();
        if (autoFocus) {
            focusMode = a("focus mode", supportedFocusModes, "auto");
        } else {
            focusMode = a("focus mode", supportedFocusModes, "continuous-picture", "continuous-video", "auto");
        }
        if (focusMode == null) {
            focusMode = a("focus mode", supportedFocusModes, "macro", "edof");
        }
        if (focusMode == null) {
            Logger.i("CameraConfiguration", "Cannot set Focus mode: autoFocus is " + autoFocus);
        } else if (!focusMode.equals(parameters.getFocusMode())) {
            parameters.setFocusMode(focusMode);
        } else {
            Logger.i("CameraConfiguration", "Focus mode already set to " + focusMode);
        }
    }

    public static void setTorch(Parameters parameters, boolean on) {
        String flashMode;
        List supportedFlashModes = parameters.getSupportedFlashModes();
        if (on) {
            flashMode = a("flash mode", supportedFlashModes, "torch", CameraParams.FLASH_MODE_ON);
        } else {
            flashMode = a("flash mode", supportedFlashModes, CameraParams.FLASH_MODE_OFF);
        }
        if (flashMode == null) {
            return;
        }
        if (!flashMode.equals(parameters.getFlashMode())) {
            Logger.d("CameraConfiguration", "Setting flash mode to " + flashMode);
            parameters.setFlashMode(flashMode);
            return;
        }
        Logger.d("CameraConfiguration", "Flash mode already set to " + flashMode);
    }

    public static void setBestExposure(Parameters parameters, boolean lightOn) {
        float f = 0.0f;
        int minExposure = parameters.getMinExposureCompensation();
        int maxExposure = parameters.getMaxExposureCompensation();
        float step = parameters.getExposureCompensationStep();
        if (!(minExposure == 0 && maxExposure == 0) && step > 0.0f) {
            if (!lightOn) {
                f = 1.5f;
            }
            int compensationSteps = Math.round(f / step);
            float actualCompensation = step * ((float) compensationSteps);
            int compensationSteps2 = Math.max(Math.min(compensationSteps, maxExposure), minExposure);
            if (parameters.getExposureCompensation() == compensationSteps2) {
                Logger.i("CameraConfiguration", "Exposure compensation already set to " + compensationSteps2 + " / " + actualCompensation);
                return;
            }
            Logger.i("CameraConfiguration", "Setting exposure compensation to " + compensationSteps2 + " / " + actualCompensation);
            parameters.setExposureCompensation(compensationSteps2);
            return;
        }
        Logger.i("CameraConfiguration", "Camera does not support exposure compensation");
    }

    public static void setBestPreviewFPS(Parameters parameters, int minFPS, int maxFPS) {
        List supportedPreviewFpsRanges = parameters.getSupportedPreviewFpsRange();
        Logger.i("CameraConfiguration", "Supported FPS ranges: " + a((Collection<int[]>) supportedPreviewFpsRanges));
        if (supportedPreviewFpsRanges != null && !supportedPreviewFpsRanges.isEmpty()) {
            int[] suitableFPSRange = null;
            int maxRange = 0;
            for (int[] fpsRange : supportedPreviewFpsRanges) {
                int thisMin = fpsRange[0];
                int thisMax = fpsRange[1];
                if (thisMin >= minFPS * 1000 && thisMax <= maxFPS * 1000) {
                    int suitRange = (thisMin / 1000) * (thisMax / 1000);
                    if (suitRange > maxRange) {
                        suitableFPSRange = fpsRange;
                        maxRange = suitRange;
                    }
                }
            }
            if (suitableFPSRange == null) {
                Logger.i("CameraConfiguration", "No suitable FPS range?");
                return;
            }
            int[] currentFpsRange = new int[2];
            parameters.getPreviewFpsRange(currentFpsRange);
            if (Arrays.equals(currentFpsRange, suitableFPSRange)) {
                Logger.i("CameraConfiguration", "FPS range already set to " + Arrays.toString(suitableFPSRange));
                return;
            }
            Logger.i("CameraConfiguration", "Setting FPS range to " + Arrays.toString(suitableFPSRange));
            parameters.setPreviewFpsRange(suitableFPSRange[0], suitableFPSRange[1]);
        }
    }

    public static void setFocusArea(Parameters parameters) {
        if (parameters.getMaxNumFocusAreas() > 0) {
            Logger.d("CameraConfiguration", "Old focus areas: " + a((Iterable<Area>) parameters.getFocusAreas()));
            List middleArea = a();
            Logger.d("CameraConfiguration", "Setting focus area to : " + a((Iterable<Area>) middleArea));
            parameters.setFocusAreas(middleArea);
            return;
        }
        Logger.d("CameraConfiguration", "Device does not support focus areas");
    }

    public static void setMetering(Parameters parameters) {
        if (parameters.getMaxNumMeteringAreas() > 0) {
            Logger.i("CameraConfiguration", "Old metering areas: " + parameters.getMeteringAreas());
            List middleArea = a();
            Logger.i("CameraConfiguration", "Setting metering area to : " + a((Iterable<Area>) middleArea));
            parameters.setMeteringAreas(middleArea);
            return;
        }
        Logger.i("CameraConfiguration", "Device does not support metering areas");
    }

    private static List<Area> a() {
        return Collections.singletonList(new Area(new Rect(-400, -400, 400, 400), 1));
    }

    public static void setVideoStabilization(Parameters parameters) {
        if (VERSION.SDK_INT < 15 || !parameters.isVideoStabilizationSupported()) {
            Logger.d("CameraConfiguration", "This device does not support video stabilization");
        } else if (parameters.getVideoStabilization()) {
            Logger.d("CameraConfiguration", "Video stabilization already enabled");
        } else {
            Logger.d("CameraConfiguration", "Enabling video stabilization...");
            parameters.setVideoStabilization(true);
        }
    }

    public static void setBarcodeSceneMode(Parameters parameters) {
        if ("barcode".equals(parameters.getSceneMode())) {
            Logger.i("CameraConfiguration", "Barcode scene mode already set");
            return;
        }
        String sceneMode = a("scene mode", parameters.getSupportedSceneModes(), "barcode");
        if (sceneMode != null) {
            parameters.setSceneMode(sceneMode);
        }
    }

    private static int b() {
        if (b > 0) {
            return b;
        }
        return 2073600;
    }

    public static Point findBestPictureSizeValue(Parameters parameters, int cameraDisplayOrientation) {
        int realWidth;
        int realHeight;
        List<Size> sortedSupportedSizes = new ArrayList<>(parameters.getSupportedPictureSizes());
        Collections.sort(sortedSupportedSizes, new Comparator<Size>() {
            public final int compare(Size a, Size b) {
                int aPixels = a.height * a.width;
                int bPixels = b.height * b.width;
                if (bPixels < aPixels) {
                    return -1;
                }
                if (bPixels > aPixels) {
                    return 1;
                }
                return 0;
            }
        });
        for (Size supportedPreviewSize : sortedSupportedSizes) {
            int realWidth2 = supportedPreviewSize.width;
            int realHeight2 = supportedPreviewSize.height;
            if (realWidth2 * realHeight2 <= b()) {
                return new Point(realWidth2, realHeight2);
            }
        }
        Size supportedPreviewSize2 = (Size) sortedSupportedSizes.get(0);
        if (cameraDisplayOrientation % 180 == 0) {
            realWidth = supportedPreviewSize2.width;
            realHeight = supportedPreviewSize2.height;
        } else {
            realWidth = supportedPreviewSize2.height;
            realHeight = supportedPreviewSize2.width;
        }
        return new Point(realWidth, realHeight);
    }

    public static void setZoom(Parameters parameters, double targetZoomRatio) {
        if (parameters.isZoomSupported()) {
            Integer zoom = a(parameters, targetZoomRatio);
            if (zoom != null) {
                if (parameters.getZoom() == zoom.intValue()) {
                    Logger.i("CameraConfiguration", "Zoom is already set to " + zoom);
                    return;
                }
                Logger.i("CameraConfiguration", "Setting zoom to " + zoom);
                parameters.setZoom(zoom.intValue());
                return;
            }
            return;
        }
        Logger.i("CameraConfiguration", "Zoom is not supported");
    }

    private static Integer a(Parameters parameters, double targetZoomRatio) {
        List ratios = parameters.getZoomRatios();
        Logger.i("CameraConfiguration", "Zoom ratios: " + ratios);
        int maxZoom = parameters.getMaxZoom();
        if (ratios == null || ratios.isEmpty() || ratios.size() != maxZoom + 1) {
            Logger.w("CameraConfiguration", "Invalid zoom ratios!");
            return null;
        }
        double target100 = 100.0d * targetZoomRatio;
        double smallestDiff = Double.POSITIVE_INFINITY;
        int closestIndex = 0;
        for (int i = 0; i < ratios.size(); i++) {
            double diff = Math.abs(((double) ratios.get(i).intValue()) - target100);
            if (diff < smallestDiff) {
                smallestDiff = diff;
                closestIndex = i;
            }
        }
        Logger.i("CameraConfiguration", "Chose zoom ratio of " + (((double) ratios.get(closestIndex).intValue()) / 100.0d));
        return Integer.valueOf(closestIndex);
    }

    public static void setInvertColor(Parameters parameters) {
        if ("negative".equals(parameters.getColorEffect())) {
            Logger.i("CameraConfiguration", "Negative effect already set");
            return;
        }
        String colorMode = a("color effect", parameters.getSupportedColorEffects(), "negative");
        if (colorMode != null) {
            parameters.setColorEffect(colorMode);
        }
    }

    public static Point findBestPreviewSizeValue(Parameters parameters, Point screenResolution) {
        int maybeFlippedWidth;
        int maybeFlippedHeight;
        List<Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        if (supportedPreviewSizes == null) {
            Size defaultSize = parameters.getPreviewSize();
            if (defaultSize == null) {
                throw new IllegalStateException("Parameters contained no preview size!");
            }
            Logger.w("CameraConfiguration", "No supported preview sizes; using default;");
            return new Point(defaultSize.width, defaultSize.height);
        }
        ArrayList<Size> arrayList = new ArrayList<>(supportedPreviewSizes);
        Collections.sort(arrayList, new Comparator<Size>() {
            public final int compare(Size a, Size b) {
                int aPixels = a.height * a.width;
                int bPixels = b.height * b.width;
                if (bPixels < aPixels) {
                    return -1;
                }
                if (bPixels > aPixels) {
                    return 1;
                }
                return 0;
            }
        });
        StringBuilder previewSizesString = new StringBuilder();
        for (Size supportedPreviewSize : arrayList) {
            previewSizesString.append(supportedPreviewSize.width).append('x').append(supportedPreviewSize.height).append(' ');
        }
        Logger.i("CameraConfiguration", "Supported preview sizes: " + previewSizesString);
        double screenAspectRatio = ((double) screenResolution.x) / ((double) screenResolution.y);
        if (!(screenAspectRatio < 1.0d)) {
            screenAspectRatio = 1.0d / screenAspectRatio;
        }
        Logger.d("CameraConfiguration", String.format("width:%d, height:%d, ratio:%.3f", new Object[]{Integer.valueOf(screenResolution.x), Integer.valueOf(screenResolution.y), Double.valueOf(screenAspectRatio)}));
        Point bestSize = null;
        double diff = Double.POSITIVE_INFINITY;
        for (Size supportedPreviewSize2 : arrayList) {
            int realWidth = supportedPreviewSize2.width;
            int realHeight = supportedPreviewSize2.height;
            int pixels = realWidth * realHeight;
            if (pixels >= 921600 && pixels <= 2073600) {
                boolean isCandidatePortrait = realWidth > realHeight;
                if (isCandidatePortrait) {
                    maybeFlippedWidth = realHeight;
                } else {
                    maybeFlippedWidth = realWidth;
                }
                if (isCandidatePortrait) {
                    maybeFlippedHeight = realWidth;
                } else {
                    maybeFlippedHeight = realHeight;
                }
                if (maybeFlippedWidth == screenResolution.x && maybeFlippedHeight == screenResolution.y) {
                    Point exactPoint = new Point(realWidth, realHeight);
                    Logger.i("CameraConfiguration", "Found preview size exactly matching screen size: " + exactPoint);
                    return exactPoint;
                }
                double aspectRatio = ((double) maybeFlippedWidth) / ((double) maybeFlippedHeight);
                double distortion = Math.abs(aspectRatio - screenAspectRatio);
                if (distortion < diff) {
                    bestSize = new Point(realWidth, realHeight);
                    diff = distortion;
                }
                Logger.d("CameraConfiguration", String.format("preview.x: %d, preview.y: %d, ratio: %.3f, diff: %.3f", new Object[]{Integer.valueOf(maybeFlippedWidth), Integer.valueOf(maybeFlippedHeight), Double.valueOf(aspectRatio), Double.valueOf(distortion)}));
            }
        }
        if (bestSize == null) {
            Size defaultPreview = parameters.getPreviewSize();
            if (defaultPreview == null) {
                throw new IllegalStateException("Parameters contained no preview size!");
            }
            Point defaultSize2 = new Point(defaultPreview.width, defaultPreview.height);
            Logger.i("CameraConfiguration", "No suitable preview sizes, using default: " + defaultSize2);
            Logger.i("CameraConfiguration", "default previewSize: " + parameters.getPreviewSize().width + "," + parameters.getPreviewSize().height);
            return defaultSize2;
        }
        Logger.i("CameraConfiguration", "bestSize is not null: " + bestSize);
        return bestSize;
    }

    private static String a(String name, Collection<String> supportedValues, String... desiredValues) {
        Logger.d("CameraConfiguration", "Requesting " + name + " value from among: " + Arrays.toString(desiredValues));
        Logger.d("CameraConfiguration", "Supported " + name + " values: " + supportedValues);
        if (supportedValues != null) {
            for (String desiredValue : desiredValues) {
                if (supportedValues.contains(desiredValue)) {
                    Logger.i("CameraConfiguration", "Can set " + name + " to: " + desiredValue);
                    return desiredValue;
                }
            }
        }
        Logger.i("CameraConfiguration", "No supported values match");
        return null;
    }

    private static String a(Collection<int[]> arrays) {
        if (arrays == null || arrays.isEmpty()) {
            return "[]";
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        Iterator it = arrays.iterator();
        while (it.hasNext()) {
            buffer.append(Arrays.toString(it.next()));
            if (it.hasNext()) {
                buffer.append(", ");
            }
        }
        buffer.append(']');
        return buffer.toString();
    }

    private static String a(Iterable<Area> areas) {
        if (areas == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (Area area : areas) {
            result.append(area.rect).append(':').append(area.weight).append(' ');
        }
        return result.toString();
    }
}
