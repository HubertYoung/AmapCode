package com.mpaas.nebula.plugin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.scancode.export.ScanCallback;
import com.alipay.android.phone.scancode.export.ScanRequest;
import com.alipay.android.phone.scancode.export.ScanRequest.ScanType;
import com.alipay.android.phone.scancode.export.ScanService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.SchemeService;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.scansdk.constant.Constants;

public class H5ScanPlugin extends H5SimplePlugin {
    public static final String SCAN = "scan";
    public static final String TAG = "H5ScanPlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("scan");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        if ("scan".equals(event.getAction())) {
            a(event, bridgeContext);
        }
        return true;
    }

    private void a(H5Event event, final H5BridgeContext bridgeContext) {
        ScanType scanType;
        JSONObject parseObject = event.getParam();
        final String type = parseObject.getString("type");
        if (Constants.SCAN_BAR.equals(type)) {
            scanType = ScanType.BARCODE;
        } else if (Constants.SCAN_QR.equals(type)) {
            scanType = ScanType.QRCODE;
        } else {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        final String actionType = H5Utils.getString(parseObject, (String) "actionType", (String) "scan");
        ScanService scanService = (ScanService) H5Utils.findServiceByInterface(ScanService.class.getName());
        String sourceId = "";
        if (event.getTarget() instanceof H5Page) {
            sourceId = H5Utils.getString(((H5Page) event.getTarget()).getParams(), (String) "appId", (String) "");
        }
        scanService.scan(event.getActivity(), new ScanRequest().setScanType(scanType).setSourceId(sourceId).setNotSupportAlbum(H5Utils.getBoolean(parseObject, (String) "hideAlbum", false)), new ScanCallback() {
            public void onScanResult(boolean isSuccess, Intent result) {
                if (!isSuccess) {
                    bridgeContext.sendBridgeResult("error", "10");
                } else if (result == null) {
                    bridgeContext.sendBridgeResult("error", "11");
                } else {
                    Uri uri = result.getData();
                    if (uri != null) {
                        String barcode = uri.toString();
                        Bundle bundle = result.getExtras();
                        if (bundle == null || bundle.isEmpty()) {
                            H5Log.d(H5ScanPlugin.TAG, "return result old way");
                            H5ScanPlugin.a(actionType, barcode);
                            if (type.equals(Constants.SCAN_BAR)) {
                                bridgeContext.sendBridgeResult(Constants.NORMAL_MA_TYPE_BAR, barcode);
                            } else if (type.equals(Constants.SCAN_QR)) {
                                bridgeContext.sendBridgeResult(Constants.NORMAL_MA_TYPE_QR, barcode);
                            }
                        } else {
                            H5Log.d(H5ScanPlugin.TAG, "return result new way");
                            String scanType = bundle.getString(Constants.ETAO_RESULT_TYPE);
                            H5ScanPlugin.a(actionType, barcode);
                            if ("QR".equalsIgnoreCase(scanType)) {
                                bridgeContext.sendBridgeResult(Constants.NORMAL_MA_TYPE_QR, barcode);
                            } else {
                                bridgeContext.sendBridgeResult(Constants.NORMAL_MA_TYPE_BAR, barcode);
                            }
                        }
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static void a(String actionType, String barcode) {
        if ("scanAndRoute".equals(actionType)) {
            SchemeService service = (SchemeService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(SchemeService.class.getName());
            if (service != null) {
                try {
                    service.process(Uri.parse(barcode));
                } catch (Exception e) {
                    H5Log.e((String) TAG, (Throwable) e);
                }
            }
        }
    }
}
