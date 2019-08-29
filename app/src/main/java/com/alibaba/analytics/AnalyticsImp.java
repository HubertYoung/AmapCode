package com.alibaba.analytics;

import android.app.Application;
import android.os.RemoteException;
import com.alibaba.analytics.IAnalytics.Stub;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.config.DebugPluginSwitch;
import com.alibaba.analytics.core.config.SystemConfigMgr;
import com.alibaba.analytics.core.logbuilder.SessionTimeAndIndexMgr;
import com.alibaba.analytics.core.selfmonitor.SelfChecker;
import com.alibaba.analytics.core.sync.UploadMgr;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.appmonitor.delegate.AppMonitorDelegate;
import com.alibaba.appmonitor.delegate.AppMonitorDelegate.Alarm;
import com.alibaba.appmonitor.delegate.AppMonitorDelegate.Counter;
import com.alibaba.appmonitor.delegate.AppMonitorDelegate.OffLineCounter;
import com.alibaba.appmonitor.delegate.AppMonitorDelegate.Stat;
import com.alibaba.appmonitor.delegate.TransactionDelegate;
import com.alibaba.appmonitor.event.EventType;
import com.alibaba.mtl.appmonitor.Transaction;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.ut.mini.UTAnalyticsDelegate;
import java.util.Map;

public class AnalyticsImp extends Stub {
    private static Application mApplication;

    public AnalyticsImp(Application application) {
        mApplication = application;
    }

    public void initUT() throws RemoteException {
        Logger.d((String) "start..", new Object[0]);
        Variables.getInstance().init(mApplication);
        Logger.d((String) "end..", new Object[0]);
    }

    public static Application getApplication() {
        return mApplication;
    }

    public void updateUserAccount(String str, String str2, String str3) throws RemoteException {
        try {
            UTAnalyticsDelegate.getInstance().updateUserAccount(str, str2, str3);
        } catch (VerifyError e) {
            Logger.e(null, e, new Object[0]);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void setAppVersion(String str) throws RemoteException {
        try {
            UTAnalyticsDelegate.getInstance().setAppVersion(str);
        } catch (VerifyError e) {
            Logger.e(null, e, new Object[0]);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void setChannel(String str) throws RemoteException {
        try {
            UTAnalyticsDelegate.getInstance().setChannel(str);
        } catch (VerifyError e) {
            Logger.e(null, e, new Object[0]);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void updateSessionProperties(Map map) throws RemoteException {
        try {
            UTAnalyticsDelegate.getInstance().updateSessionProperties(map);
        } catch (VerifyError e) {
            Logger.e(null, e, new Object[0]);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void setSessionProperties(Map map) throws RemoteException {
        try {
            UTAnalyticsDelegate.getInstance().setSessionProperties(map);
        } catch (VerifyError e) {
            Logger.e(null, e, new Object[0]);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void turnOnDebug() throws RemoteException {
        try {
            UTAnalyticsDelegate.getInstance().turnOnDebug();
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void transferLog(Map map) throws RemoteException {
        Logger.d();
        try {
            if (!Variables.getInstance().isInit()) {
                Variables.getInstance().init(mApplication);
            }
            UTAnalyticsDelegate.getInstance().transferLog(map);
        } catch (VerifyError e) {
            Logger.e(null, e, new Object[0]);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void dispatchLocalHits() throws RemoteException {
        try {
            UploadMgr.getInstance().dispatchHits();
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void saveCacheDataToLocal() throws RemoteException {
        try {
            UTAnalyticsDelegate.getInstance().saveCacheDataToLocal();
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void init() throws RemoteException {
        try {
            initUT();
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void destroy() throws RemoteException {
        try {
            AppMonitorDelegate.destroy();
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void triggerUpload() throws RemoteException {
        try {
            AppMonitorDelegate.triggerUpload();
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void setSampling(int i) throws RemoteException {
        try {
            AppMonitorDelegate.setSampling(i);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void enableLog(boolean z) throws RemoteException {
        try {
            AppMonitorDelegate.enableLog(z);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public String selfCheck(String str) throws RemoteException {
        try {
            return SelfChecker.getInstance().check("selfcheck", str);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
            return null;
        }
    }

    public void setStatisticsInterval2(int i, int i2) throws RemoteException {
        try {
            AppMonitorDelegate.setStatisticsInterval(getEventType(i), i2);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void setRequestAuthInfo(boolean z, boolean z2, String str, String str2) throws RemoteException {
        try {
            AppMonitorDelegate.setRequestAuthInfo(z, z2, str, str2);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void turnOnRealTimeDebug(Map map) throws RemoteException {
        try {
            Variables.getInstance().turnOnRealTimeDebug(map);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void turnOffRealTimeDebug() throws RemoteException {
        try {
            Variables.getInstance().turnOffRealTimeDebug();
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void counter_setStatisticsInterval(int i) throws RemoteException {
        try {
            Counter.setStatisticsInterval(i);
        } catch (VerifyError e) {
            Logger.e(null, e, new Object[0]);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void counter_setSampling(int i) throws RemoteException {
        try {
            Counter.setSampling(i);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public boolean counter_checkSampled(String str, String str2) throws RemoteException {
        try {
            return Counter.checkSampled(str, str2);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
            return false;
        }
    }

    public void counter_commit1(String str, String str2, double d) throws RemoteException {
        try {
            Counter.commit(str, str2, d);
        } catch (VerifyError e) {
            Logger.e(null, e, new Object[0]);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void counter_commit2(String str, String str2, String str3, double d) throws RemoteException {
        try {
            Counter.commit(str, str2, str3, d);
        } catch (VerifyError e) {
            Logger.e(null, e, new Object[0]);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void alarm_setStatisticsInterval(int i) throws RemoteException {
        try {
            Alarm.setStatisticsInterval(i);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void alarm_setSampling(int i) throws RemoteException {
        try {
            Alarm.setSampling(i);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public boolean alarm_checkSampled(String str, String str2) throws RemoteException {
        try {
            return Alarm.checkSampled(str, str2);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
            return false;
        }
    }

    public void alarm_commitSuccess1(String str, String str2) throws RemoteException {
        try {
            Alarm.commitSuccess(str, str2);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void alarm_commitSuccess2(String str, String str2, String str3) throws RemoteException {
        try {
            Alarm.commitSuccess(str, str2, str3);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void alarm_commitFail1(String str, String str2, String str3, String str4) throws RemoteException {
        try {
            Alarm.commitFail(str, str2, str3, str4);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void alarm_commitFail2(String str, String str2, String str3, String str4, String str5) throws RemoteException {
        try {
            Alarm.commitFail(str, str2, str3, str4, str5);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void offlinecounter_setStatisticsInterval(int i) throws RemoteException {
        try {
            OffLineCounter.setStatisticsInterval(i);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void offlinecounter_setSampling(int i) throws RemoteException {
        OffLineCounter.setSampling(i);
    }

    public boolean offlinecounter_checkSampled(String str, String str2) throws RemoteException {
        return OffLineCounter.checkSampled(str, str2);
    }

    public void offlinecounter_commit(String str, String str2, double d) throws RemoteException {
        OffLineCounter.commit(str, str2, d);
    }

    public void setStatisticsInterval1(int i) throws RemoteException {
        try {
            AppMonitorDelegate.setStatisticsInterval(i);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void register1(String str, String str2, MeasureSet measureSet) throws RemoteException {
        try {
            AppMonitorDelegate.register(str, str2, measureSet);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void register2(String str, String str2, MeasureSet measureSet, boolean z) throws RemoteException {
        try {
            AppMonitorDelegate.register(str, str2, measureSet, z);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void register3(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet) throws RemoteException {
        try {
            AppMonitorDelegate.register(str, str2, measureSet, dimensionSet);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void register4(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) throws RemoteException {
        try {
            AppMonitorDelegate.register(str, str2, measureSet, dimensionSet, z);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void stat_begin(String str, String str2, String str3) throws RemoteException {
        try {
            Stat.begin(str, str2, str3);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void stat_end(String str, String str2, String str3) throws RemoteException {
        try {
            Stat.end(str, str2, str3);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void stat_setStatisticsInterval(int i) throws RemoteException {
        try {
            Stat.setStatisticsInterval(i);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void stat_setSampling(int i) throws RemoteException {
        try {
            Stat.setSampling(i);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public boolean stat_checkSampled(String str, String str2) throws RemoteException {
        return Stat.checkSampled(str, str2);
    }

    public void stat_commit1(String str, String str2, double d) throws RemoteException {
        try {
            Stat.commit(str, str2, d);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void stat_commit2(String str, String str2, DimensionValueSet dimensionValueSet, double d) throws RemoteException {
        try {
            Stat.commit(str, str2, dimensionValueSet, d);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void stat_commit3(String str, String str2, DimensionValueSet dimensionValueSet, MeasureValueSet measureValueSet) throws RemoteException {
        try {
            Stat.commit(str, str2, dimensionValueSet, measureValueSet);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    private EventType getEventType(int i) {
        return EventType.getEventType(i);
    }

    public void transaction_begin(Transaction transaction, String str) throws RemoteException {
        try {
            TransactionDelegate.begin(transaction, str);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void transaction_end(Transaction transaction, String str) throws RemoteException {
        try {
            TransactionDelegate.end(transaction, str);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void updateMeasure(String str, String str2, String str3, double d, double d2, double d3) throws RemoteException {
        AppMonitorDelegate.updateMeasure(str, str2, str3, d, d2, d3);
    }

    public String getValue(String str) throws RemoteException {
        try {
            if (DebugPluginSwitch.KEY.equals(str)) {
                return SystemConfigMgr.getInstance().get(str);
            }
            if ("tpk_md5".equals(str)) {
                return Variables.getInstance().getTpkMD5();
            }
            if ("tpk_string".equals(str)) {
                return Variables.getInstance().getTPKString();
            }
            if ("session_timestamp".equals(str)) {
                StringBuilder sb = new StringBuilder();
                sb.append(SessionTimeAndIndexMgr.getInstance().getSessionTimestamp());
                return sb.toString();
            }
            if ("autoExposure".equalsIgnoreCase(str)) {
                return SystemConfigMgr.getInstance().get(str);
            }
            return null;
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void setGlobalProperty(String str, String str2) throws RemoteException {
        try {
            AppMonitorDelegate.setGlobalProperty(str, str2);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public void removeGlobalProperty(String str) throws RemoteException {
        try {
            AppMonitorDelegate.removeGlobalProperty(str);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }
}
