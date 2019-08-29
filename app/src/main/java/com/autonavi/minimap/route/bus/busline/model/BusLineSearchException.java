package com.autonavi.minimap.route.bus.busline.model;

import android.content.res.Resources;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

public class BusLineSearchException extends RuntimeException {
    private int code;

    public BusLineSearchException(int i, String str) {
        super(str);
        this.code = i;
    }

    public String getLocalizedMessage() {
        Resources resources = AMapAppGlobal.getApplication().getApplicationContext().getResources();
        int i = this.code;
        if (i == 7) {
            return resources.getString(R.string.error_no_record_found);
        }
        if (i == 12) {
            return resources.getString(R.string.error_permission_denial);
        }
        switch (i) {
            case 0:
                return resources.getString(R.string.error_server_busy);
            case 1:
                return "";
            case 2:
                return resources.getString(R.string.error_request_failure);
            case 3:
                return resources.getString(R.string.error_incorrect_parameter);
            case 4:
                return resources.getString(R.string.error_incorrect_signature);
            case 5:
                return resources.getString(R.string.error_outdated_license);
            default:
                return resources.getString(R.string.error_default_message);
        }
    }
}
