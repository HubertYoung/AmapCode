package com.alibaba.appmonitor.delegate;

import com.alibaba.analytics.core.selfmonitor.exception.ExceptionEventBuilder;
import com.alibaba.analytics.core.selfmonitor.exception.ExceptionEventBuilder.ExceptionType;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.appmonitor.event.EventRepo;
import com.alibaba.appmonitor.event.EventType;
import com.alibaba.appmonitor.sample.AMSamplingMgr;
import com.alibaba.mtl.appmonitor.Transaction;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;

public class TransactionDelegate {
    private static final String TAG = "TransactionDelegate";

    public static void begin(Transaction transaction, String str) {
        try {
            if (AppMonitorDelegate.sdkInit && transaction != null) {
                Logger.d((String) TAG, "statEvent begin. module: ", transaction.module, " monitorPoint: ", transaction.monitorPoint, " measureName: ", str);
                if (EventType.STAT.isOpen() && (AppMonitorDelegate.IS_DEBUG || AMSamplingMgr.getInstance().checkSampled(EventType.STAT, transaction.module, transaction.monitorPoint))) {
                    EventRepo.getRepo().beginStatEvent(transaction.transactionId, transaction.eventId, transaction.module, transaction.monitorPoint, str);
                    addElapseEventDimensionValue(transaction);
                }
            }
        } catch (Throwable th) {
            ExceptionEventBuilder.log(ExceptionType.AP, th);
        }
    }

    private static void addElapseEventDimensionValue(Transaction transaction) {
        if (!(transaction == null || transaction.dimensionValues == null)) {
            EventRepo.getRepo().commitElapseEventDimensionValue(transaction.transactionId, transaction.eventId, transaction.module, transaction.monitorPoint, DimensionValueSet.create().addValues(transaction.dimensionValues));
        }
    }

    public static void end(Transaction transaction, String str) {
        try {
            if (AppMonitorDelegate.sdkInit && transaction != null) {
                Logger.d((String) TAG, "statEvent end. module: ", transaction.module, " monitorPoint: ", transaction.monitorPoint, " measureName: ", str);
                if (EventType.STAT.isOpen() && (AppMonitorDelegate.IS_DEBUG || AMSamplingMgr.getInstance().checkSampled(EventType.STAT, transaction.module, transaction.monitorPoint))) {
                    addElapseEventDimensionValue(transaction);
                    EventRepo.getRepo().endStatEvent(transaction.transactionId, str, false);
                }
            }
        } catch (Throwable th) {
            ExceptionEventBuilder.log(ExceptionType.AP, th);
        }
    }
}
