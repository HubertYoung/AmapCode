package com.alipay.mobile.bqcscanservice.behavior;

import com.alipay.mobile.bqcscanservice.monitor.ScanCodeState;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.nebula.util.H5Utils;

public class BehaviorBury {
    public static void recordCameraPreviewSize(final int x, final int y) {
        new Thread(new Runnable() {
            public final void run() {
                Behavor behavor = new Behavor();
                behavor.setUserCaseID("Android-Preview-Size");
                behavor.setSeedID("CameraPreviewSize");
                behavor.setAppID(H5Utils.SCAN_APP_ID);
                behavor.setParam1(String.valueOf(x));
                behavor.setParam2(String.valueOf(y));
                behavor.setBehaviourPro("Scan");
                LoggerFactory.getBehavorLogger().event("event", behavor);
            }
        }).start();
    }

    public static void recordPreCameraOpenResult(final boolean success) {
        new Thread(new Runnable() {
            public final void run() {
                Behavor behavor = new Behavor();
                behavor.setUserCaseID("Camera-Preopen-Result");
                behavor.setSeedID("scan.cpr");
                behavor.setAppID(H5Utils.SCAN_APP_ID);
                behavor.setBehaviourPro("Scan");
                behavor.setParam1(String.valueOf(success));
                LoggerFactory.getBehavorLogger().event("event", behavor);
            }
        }).start();
    }

    public static void recordPreviewOrientationNewCal(final String devName, final int orientation) {
        new Thread(new Runnable() {
            public final void run() {
                Behavor behavor = new Behavor();
                behavor.setUserCaseID("Android-Camera-Orientation-New");
                behavor.setSeedID("CameraOrientationNew");
                behavor.setAppID(H5Utils.SCAN_APP_ID);
                behavor.setBehaviourPro("Scan");
                behavor.setParam1(String.valueOf(orientation));
                behavor.setParam2(devName);
                LoggerFactory.getBehavorLogger().event("event", behavor);
            }
        }).start();
    }

    public static void recordPreviewOrientationOld(final String devName, final int orientation) {
        new Thread(new Runnable() {
            public final void run() {
                Behavor behavor = new Behavor();
                behavor.setUserCaseID("Android-Camera-Orientation-New-Error");
                behavor.setSeedID("CameraOrientationNewError");
                behavor.setAppID(H5Utils.SCAN_APP_ID);
                behavor.setBehaviourPro("Scan");
                behavor.setParam1(String.valueOf(orientation));
                behavor.setParam2(devName);
                LoggerFactory.getBehavorLogger().event("event", behavor);
            }
        }).start();
    }

    public static void recordSetZoomException(final int zoom) {
        new Thread(new Runnable() {
            public final void run() {
                Behavor behavor = new Behavor();
                behavor.setUserCaseID("Android-Camera-Zoom-Exception");
                behavor.setSeedID("CameraZoomException");
                behavor.setAppID(H5Utils.SCAN_APP_ID);
                behavor.setBehaviourPro("Scan");
                behavor.setParam1(String.valueOf(zoom));
                LoggerFactory.getBehavorLogger().event("event", behavor);
            }
        }).start();
    }

    public static void recordScanDiagnose(ScanCodeState scanCodeState) {
        if (scanCodeState != null && scanCodeState.needUploadBuryInfo()) {
            final String scanCodeStateStr = scanCodeState.dumpBuryInfo();
            new Thread(new Runnable() {
                public final void run() {
                    Behavor behavor = new Behavor();
                    behavor.setUserCaseID("Android-Scan_Diagnose");
                    behavor.setSeedID("scan.asd");
                    behavor.setAppID(H5Utils.SCAN_APP_ID);
                    behavor.setBehaviourPro("Scan");
                    behavor.setParam1(scanCodeStateStr);
                    LoggerFactory.getBehavorLogger().event("event", behavor);
                }
            }).start();
        }
    }
}
