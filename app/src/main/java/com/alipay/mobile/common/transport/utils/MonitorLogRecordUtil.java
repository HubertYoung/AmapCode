package com.alipay.mobile.common.transport.utils;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import java.util.HashMap;

public final class MonitorLogRecordUtil {
    public static final void recordMultMainProcessItem(TransportContext transportContext, Context context) {
        if (!TextUtils.isEmpty(DataItemsUtil.getDataItem2DataContainer(transportContext.getCurrentDataContainer(), "ERROR")) && transportContext.bizType == 1 && MiscUtils.isExistMultiMainProcess(context)) {
            DataItemsUtil.putDataItem2DataContainer(transportContext.getCurrentDataContainer(), RPCDataItems.MULTI_MAIN, "T");
            LogCatUtil.warn((String) "MonitorLogUtil", (String) "There are multiple processes.");
        }
    }

    public static final void recordCtrlPrintURLFlagToDataflow(DataflowModel dataflowModel, boolean isPrintUrl) {
        if (dataflowModel.extParams == null) {
            dataflowModel.extParams = new HashMap(1);
        }
        dataflowModel.extParams.put("isPrintReqURL", String.valueOf(isPrintUrl));
    }
}
