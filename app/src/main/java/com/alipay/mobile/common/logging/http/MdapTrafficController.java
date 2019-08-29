package com.alipay.mobile.common.logging.http;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.strategy.LogStrategyManager;
import com.alipay.mobile.common.logging.util.LoggingSPCache;
import com.alipay.mobile.common.logging.util.NetUtil;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.concurrent.TimeUnit;

public class MdapTrafficController {

    public class MdapTrafficException extends IllegalStateException {
        public MdapTrafficException(String message) {
            super(message);
        }
    }

    public static void a(Context context, String logCategory, int byteDataSize) {
        String uploadDayKey = new StringBuilder(LoggingSPCache.KEY_CUR_UPLOAD_DAY).append(logCategory).toString();
        String uploadTraficKey = new StringBuilder(LoggingSPCache.KEY_CUR_UPLOAD_TRAFIC).append(logCategory).toString();
        long curDay = System.currentTimeMillis() / TimeUnit.DAYS.toMillis(1);
        long lastCurday = LoggingSPCache.getInstance().getLong(uploadDayKey, 0);
        String networkType = NetUtil.getNetworkType(context);
        boolean isConnected = !TextUtils.isEmpty(networkType);
        boolean isPositive = LogStrategyManager.getInstance().isPositiveDiagnose();
        boolean isIgnoreTrafic = isPositive || "WIFI".equals(networkType);
        int contentSize = byteDataSize;
        StringBuilder message = new StringBuilder();
        message.append(logCategory).append(" upload");
        if (curDay != lastCurday) {
            message.append(" on the new day");
            LoggingSPCache.getInstance().putLongCommit(uploadDayKey, curDay);
            LoggingSPCache.getInstance().putIntCommit(uploadTraficKey, 0);
            a(isConnected, isIgnoreTrafic, uploadTraficKey, byteDataSize);
        } else {
            int todayTrafic = LoggingSPCache.getInstance().getInt(uploadTraficKey, 0);
            int totalTrafic = todayTrafic + byteDataSize;
            message.append(", todayByte: ").append(totalTrafic);
            if (todayTrafic <= 2097152) {
                a(isConnected, isIgnoreTrafic, uploadTraficKey, totalTrafic);
            } else if (isIgnoreTrafic) {
                LoggerFactory.getTraceLogger().info("MdapTraffic", "checkAndUpdateConsume, do not check by positive.");
            } else {
                throw new MdapTrafficException(logCategory + " upload trafic limited ! todayByte: " + todayTrafic);
            }
        }
        message.append(", contentPeek: #").append("").append(MetaRecord.LOG_SEPARATOR);
        message.append(", contentSize: ").append(contentSize);
        message.append(", traficByte: ").append(byteDataSize);
        message.append(", network: ").append(networkType);
        message.append(", connected: ").append(isConnected);
        message.append(", positive: ").append(isPositive);
        LoggerFactory.getTraceLogger().info("MdapTraffic", message.toString());
    }

    private static void a(boolean isConnected, boolean isIgnored, String cacheKey, int byteSize) {
        if (!isConnected) {
            LoggerFactory.getTraceLogger().info("MdapTraffic", "doUpdateConsume, do not update by disconnected.");
        } else if (isIgnored) {
            LoggerFactory.getTraceLogger().info("MdapTraffic", "doUpdateConsume, do not update by positive.");
        } else {
            LoggingSPCache.getInstance().putIntCommit(cacheKey, byteSize);
        }
    }
}
