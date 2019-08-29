package com.alipay.mobile.common.transport.sys.telephone;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;

public class DefaultNetTelephonyManager extends NetTelephonyManagerAdapter {
    private TelephonyManager a;

    public DefaultNetTelephonyManager() {
        this.a = (TelephonyManager) TransportEnvUtil.getContext().getSystemService("phone");
    }

    public DefaultNetTelephonyManager(Context context) {
        this.a = (TelephonyManager) context.getSystemService("phone");
    }

    public CellLocation getCellLocation() {
        return this.a.getCellLocation();
    }
}
