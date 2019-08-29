package com.alipay.inside.android.phone.mrpc.core.monitor;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RPCDataParser {
    private static final String TAG = "RPC_PERF";

    public static void buildAndWriteLog(RPCDataContainer rPCDataContainer) {
        if (rPCDataContainer != null) {
            try {
                HashMap hashMap = new HashMap();
                String dataItem = rPCDataContainer.getDataItem("ERROR");
                if (TextUtils.isEmpty(dataItem)) {
                    hashMap.put("RESULT", "T");
                } else {
                    hashMap.put("RESULT", "F");
                    hashMap.put("ERROR", dataItem);
                }
                putItem2Map(hashMap, "API", rPCDataContainer);
                putItem2Map(hashMap, "NETTYPE", rPCDataContainer);
                putItem2Map(hashMap, "HRC", rPCDataContainer);
                putItem2Map(hashMap, "REQ_SIZE", rPCDataContainer);
                putItem2Map(hashMap, "REQ_RAW_SIZE", rPCDataContainer);
                putItem2Map(hashMap, "RES_SIZE", rPCDataContainer);
                putItem2Map(hashMap, RPCDataItems.RES_RAW_SIZE, rPCDataContainer);
                putItem2Map(hashMap, RPCDataItems.ENCODE_TIME, rPCDataContainer);
                putItem2Map(hashMap, RPCDataItems.DECODE_TIME, rPCDataContainer);
                putItem2Map(hashMap, "UUID", rPCDataContainer);
                putItem2Map(hashMap, "RETRY", rPCDataContainer);
                putItem2Map(hashMap, "RPC_ALL_TIME", rPCDataContainer);
                StringBuffer stringBuffer = new StringBuffer();
                for (Entry entry : hashMap.entrySet()) {
                    stringBuffer.append((String) entry.getKey());
                    stringBuffer.append("=");
                    stringBuffer.append((String) entry.getValue());
                    stringBuffer.append(",");
                }
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("logStr:");
                sb.append(stringBuffer.toString());
                f.a((String) TAG, sb.toString());
                LoggerFactory.c().a("RPC", stringBuffer.toString(), Long.valueOf(0));
            } catch (Throwable th) {
                TraceLogger f2 = LoggerFactory.f();
                StringBuilder sb2 = new StringBuilder("buildAndWriteLog ex:");
                sb2.append(th.toString());
                f2.d(TAG, sb2.toString());
            }
        }
    }

    private static void putItem2Map(Map<String, String> map, String str, RPCDataContainer rPCDataContainer) {
        if (map != null) {
            try {
                String dataItem = rPCDataContainer.getDataItem(str);
                if (!TextUtils.isEmpty(dataItem)) {
                    map.put(str, dataItem);
                }
            } catch (Throwable th) {
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("putItem2Map ex:");
                sb.append(th.toString());
                f.d(TAG, sb.toString());
            }
        }
    }
}
