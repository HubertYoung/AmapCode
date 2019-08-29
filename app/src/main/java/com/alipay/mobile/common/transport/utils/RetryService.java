package com.alipay.mobile.common.transport.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.strategy.StrategyUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class RetryService {
    private static RetryService c = null;
    /* access modifiers changed from: private */
    public Map<String, String> a = new ConcurrentHashMap();
    private ArrayList<String> b = new ArrayList<>(5);

    public static synchronized RetryService getInstance() {
        RetryService retryService;
        synchronized (RetryService.class) {
            if (c != null) {
                retryService = c;
            } else {
                synchronized (RetryService.class) {
                    if (c == null) {
                        c = new RetryService();
                    }
                }
                retryService = c;
            }
        }
        return retryService;
    }

    private RetryService() {
        b();
        if (c() != null) {
            this.a = c();
            a();
        }
    }

    private void a() {
        try {
            if (!MiscUtils.isOtherProcess(TransportEnvUtil.getContext())) {
                NetworkAsyncTaskExecutor.executeIO(new Runnable() {
                    public void run() {
                        int retrySize = 0;
                        int size = RetryService.this.a.size();
                        for (Entry value : RetryService.this.a.entrySet()) {
                            if (TextUtils.equals((CharSequence) value.getValue(), "1")) {
                                retrySize++;
                            }
                        }
                        Performance pf = new TransportPerformance();
                        pf.setSubType("RetryList");
                        pf.getExtPramas().put("totalSize", String.valueOf(size));
                        pf.getExtPramas().put("retrySize", String.valueOf(retrySize));
                        LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_NETWORK, pf);
                        LogCatUtil.debug("RetryService", "retrylist perf:" + pf.toString());
                        if (size > 500) {
                            LoggerFactory.getMonitorLogger().mtBizReport("BIZ_NETWORK", "RETRYLIST", "size:" + size, null);
                        }
                    }
                });
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "RetryService", ex);
        }
    }

    private void b() {
        this.b.add("alipay.client.getRSAKey");
        this.b.add("alipay.mobile.transfer.queryHistoryRecord");
        this.b.add("alipay.mobile.transfer.checkCertify");
        this.b.add("alipay.mobile.transfer.queryHistoryRecord");
    }

    public boolean isSupportResend(String operationType, boolean allowRetry) {
        try {
            if (!TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.RETRY_CAPTAIN), "T")) {
                LogCatUtil.debug("RetryService", "captain don't allow retry");
                return false;
            } else if (StrategyUtil.isSwitchRpc(operationType)) {
                return true;
            } else {
                if (MiscUtils.isLoginRpc(operationType)) {
                    return true;
                }
                if (this.b.contains(operationType)) {
                    return true;
                }
                if (isOperationTypeInRetryList(operationType, allowRetry)) {
                    return true;
                }
                return false;
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "RetryService", ex);
            return false;
        }
    }

    public boolean isOperationTypeInRetryList(String operationType, boolean allowRetry) {
        if (TextUtils.equals(this.a.get(operationType), "1")) {
            return true;
        }
        if (TextUtils.equals(this.a.get(operationType), "0")) {
            return false;
        }
        if (allowRetry) {
            return true;
        }
        return false;
    }

    private static Map<String, String> c() {
        try {
            Context context = TransportEnvUtil.getContext();
            if (context == null) {
                return null;
            }
            return context.getSharedPreferences("net_retry", 0).getAll();
        } catch (Throwable ex) {
            LogCatUtil.error((String) "RetryService", ex);
            return null;
        }
    }

    public void addOperationTypeToRetryList(String operationType) {
        try {
            this.a.put(operationType, "1");
            a(operationType, "1");
        } catch (Throwable ex) {
            LogCatUtil.error((String) "RetryService", ex);
        }
    }

    public void removeOpetationTypeFromRetryList(String operationType) {
        try {
            this.a.put(operationType, "0");
            a(operationType, "0");
        } catch (Throwable ex) {
            LogCatUtil.error((String) "RetryService", ex);
        }
    }

    private void a(final String key, final String val) {
        NetworkAsyncTaskExecutor.executeIO(new Runnable() {
            public void run() {
                Context context = TransportEnvUtil.getContext();
                if (context != null) {
                    Editor edit = context.getSharedPreferences("net_retry", 0).edit();
                    edit.putString(key, val);
                    edit.commit();
                }
            }
        });
    }
}
