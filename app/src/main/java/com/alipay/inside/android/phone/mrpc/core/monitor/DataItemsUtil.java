package com.alipay.inside.android.phone.mrpc.core.monitor;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;

public class DataItemsUtil {
    private static final String TAG = "DataItemsUtil";

    public static void putDataItem2Container(DataContainer dataContainer, String str, String str2) {
        if (dataContainer != null) {
            try {
                if (!TextUtils.isEmpty(str2)) {
                    dataContainer.putDataItem(str, str2);
                }
            } catch (Throwable th) {
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("putDataItem2Container ex:");
                sb.append(th.toString());
                f.d(TAG, sb.toString());
            }
        }
    }

    public static String getDataItem2Container(DataContainer dataContainer, String str) {
        if (dataContainer == null) {
            return "";
        }
        try {
            return dataContainer.getDataItem(str);
        } catch (Throwable th) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("getDataItem2Container ex:");
            sb.append(th.toString());
            f.d(TAG, sb.toString());
            return "";
        }
    }

    public static void removeFromContainer(DataContainer dataContainer, String str) {
        if (dataContainer != null) {
            try {
                dataContainer.removeDataItem(str);
            } catch (Throwable th) {
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("removeFromContainer ex:");
                sb.append(th.toString());
                f.d(TAG, sb.toString());
            }
        }
    }
}
