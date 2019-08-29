package com.alipay.mobile.nebulauc.impl.setup;

import android.view.View;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.alipay.mobile.nebulauc.webar.FaceDetector;
import com.alipay.mobile.nebulauc.webar.FakeDetector;
import com.alipay.mobile.nebulauc.webar.MarkerDetector;
import com.alipay.mobile.nebulauc.webar.XNNDetector;
import com.uc.webview.export.WebView;
import com.uc.webview.export.extension.ARManager;
import java.util.HashMap;

public class UcArSetup {
    private static final String TAG = "H5UcService::UcArSetup";

    private static class DetectorInfo {
        public Class<?> clazz;
        HashMap<String, String> metadata;
        String name;
        public VaildCallback vaildCallback;

        public interface VaildCallback {
            boolean vaild(String str, View view);
        }

        DetectorInfo(String detectorName, Class<?> clazz2, HashMap<String, String> extraInfo) {
            this.name = detectorName;
            this.clazz = clazz2;
            this.metadata = extraInfo;
        }
    }

    /* access modifiers changed from: private */
    public static H5Page getTopH5Page() {
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null) {
            H5Page h5Page = h5Service.getTopH5Page();
            if (h5Page != null) {
                return h5Page;
            }
        }
        return null;
    }

    public static boolean disableAR() {
        return "yes".equalsIgnoreCase(H5ConfigUtil.getConfig("h5_disableUcAR"));
    }

    /* access modifiers changed from: private */
    public static boolean checkIsTinyApp() {
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service == null) {
            return false;
        }
        H5Page h5Page = h5Service.getTopH5Page();
        if (h5Page == null) {
            return false;
        }
        String parentAppId = h5Page.getParams().getString("parentAppId");
        if (parentAppId == null || parentAppId.isEmpty()) {
            return false;
        }
        return true;
    }

    public static long init() {
        UcSetupTracing.beginTrace("registerARDetector");
        long ts = System.currentTimeMillis();
        ARManager aRManager = ARManager.getInstance();
        if (aRManager != null) {
            HashMap markerDetectorMap = new HashMap();
            markerDetectorMap.put("version", "4");
            HashMap fakeDetectorMap = new HashMap();
            fakeDetectorMap.put("version", "beta");
            fakeDetectorMap.put("foo", "foo");
            HashMap faceDetectorMap = new HashMap();
            faceDetectorMap.put("version", "1");
            registerDetector(aRManager, new DetectorInfo[]{new DetectorInfo("MarkerDetector", MarkerDetector.class, markerDetectorMap), new DetectorInfo("FakeDetector", FakeDetector.class, fakeDetectorMap), new DetectorInfo("FaceDetector", FaceDetector.class, faceDetectorMap), new DetectorInfo(XNNDetector.TAG, XNNDetector.class, null)});
        } else {
            H5Log.e((String) TAG, (String) "get aRManager fail, register MarkerDetector fail");
        }
        UcSetupTracing.endTrace("registerARDetector");
        return System.currentTimeMillis() - ts;
    }

    private static void registerDetector(ARManager aRManager, DetectorInfo[] infos) {
        for (DetectorInfo info : infos) {
            final String name = info.name;
            info.vaildCallback = new VaildCallback() {
                public boolean vaild(String url, View coreView) {
                    if (!UcArSetup.checkIsTinyApp()) {
                        return true;
                    }
                    H5Page h5Page = UcArSetup.getTopH5Page();
                    if (h5Page == null) {
                        return false;
                    }
                    try {
                        if (((WebView) h5Page.getContentView()).getCoreView() == coreView) {
                            return ((H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName())).hasWebARPermission("WA_" + name, url, h5Page);
                        }
                        return false;
                    } catch (Exception e) {
                        return false;
                    }
                }
            };
            aRManager.registerARDetector(info);
        }
    }
}
