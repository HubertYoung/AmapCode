package com.alipay.mobile.common.transport.utils;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.monitor.DataContainer;

public class DataItemsUtil {
    public static void putDataItem2DataContainer(DataContainer dataContainer, String key, String value) {
        if (dataContainer != null && TextUtils.isEmpty(dataContainer.getDataItem(key))) {
            dataContainer.putDataItem(key, value);
        }
    }

    public static void putDataItem2ContainerAnyway(DataContainer dataContainer, String key, String value) {
        if (dataContainer != null) {
            dataContainer.putDataItem(key, value);
        }
    }

    public static String getDataItem2DataContainer(DataContainer dataContainer, String key) {
        if (dataContainer == null) {
            return "";
        }
        return dataContainer.getDataItem(key);
    }

    public static void removeFromDataContainer(DataContainer dataContainer, String key) {
        if (dataContainer != null) {
            dataContainer.removeDataItem(key);
        }
    }
}
