package com.mpaas.nebula.provider;

import com.alipay.android.phone.mobilecommon.multimedia.api.APMToolService;
import com.alipay.mobile.nebula.provider.H5APMTool;
import com.alipay.mobile.nebula.util.H5Log;
import com.mpaas.nebula.NebulaBiz;

public class H5APMToolImpl implements H5APMTool {
    public String encodeToLocalId(String path) {
        APMToolService apmToolService = (APMToolService) NebulaBiz.findServiceByInterface(APMToolService.class.getName());
        if (apmToolService != null) {
            String localId = apmToolService.encodeToLocalId(path);
            H5Log.d("H5APMToolImpl", "localId :" + localId + " path:" + path);
            return localId;
        }
        H5Log.e((String) "H5APMToolImpl", (String) "apmToolService ==null ");
        return null;
    }

    public String decodeToPath(String localId) {
        APMToolService apmToolService = (APMToolService) NebulaBiz.findServiceByInterface(APMToolService.class.getName());
        if (apmToolService != null) {
            String path = apmToolService.decodeToPath(localId);
            H5Log.d("H5APMToolImpl", "localId :" + localId + " path:" + path);
            return path;
        }
        H5Log.e((String) "H5APMToolImpl", (String) "apmToolService ==null ");
        return null;
    }
}
