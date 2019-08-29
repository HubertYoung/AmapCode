package com.taobao.accs.client;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.taobao.accs.IAgooAppReceiver;
import com.taobao.accs.IAppReceiver;
import com.taobao.accs.ILoginInfo;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalClientInfo {
    public static final String AGOO_SERVICE_ID = "agooSend";
    private static Map<String, String> SERVICES = null;
    public static IAgooAppReceiver mAgooAppReceiver = null;
    public static Context mContext = null;
    public static String mCookieSec = null;
    private static volatile GlobalClientInfo mInstance = null;
    public static boolean mSupprotElection = false;
    private Map<String, AccsAbstractDataListener> LISTENERS = new ConcurrentHashMap();
    private ActivityManager mActivityManager;
    private ConcurrentHashMap<String, IAppReceiver> mAppReceiver;
    private ConnectivityManager mConnectivityManager;
    private ConcurrentHashMap<String, ILoginInfo> mILoginInfoImpl;
    private PackageInfo mPackageInfo;

    static {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        SERVICES = concurrentHashMap;
        concurrentHashMap.put(AGOO_SERVICE_ID, "org.android.agoo.accs.AgooService");
        SERVICES.put("agooAck", "org.android.agoo.accs.AgooService");
        SERVICES.put("agooTokenReport", "org.android.agoo.accs.AgooService");
    }

    public static GlobalClientInfo getInstance(Context context) {
        if (mInstance == null) {
            synchronized (GlobalClientInfo.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new GlobalClientInfo(context);
                    }
                }
            }
        }
        return mInstance;
    }

    public static Context getContext() {
        return mContext;
    }

    private GlobalClientInfo(Context context) {
        if (context == null) {
            throw new RuntimeException("Context is null!!");
        }
        if (mContext == null) {
            mContext = context.getApplicationContext();
        }
        ThreadPoolExecutorFactory.execute(new Runnable() {
            public void run() {
                GlobalClientInfo.mCookieSec = UtilityImpl.restoreCookie(GlobalClientInfo.mContext);
            }
        });
    }

    public ActivityManager getActivityManager() {
        if (this.mActivityManager == null) {
            this.mActivityManager = (ActivityManager) mContext.getSystemService(WidgetType.ACTIVITY);
        }
        return this.mActivityManager;
    }

    public ConnectivityManager getConnectivityManager() {
        if (this.mConnectivityManager == null) {
            this.mConnectivityManager = (ConnectivityManager) mContext.getSystemService("connectivity");
        }
        return this.mConnectivityManager;
    }

    public void setLoginInfoImpl(String str, ILoginInfo iLoginInfo) {
        if (this.mILoginInfoImpl == null) {
            this.mILoginInfoImpl = new ConcurrentHashMap<>(1);
        }
        if (iLoginInfo != null) {
            this.mILoginInfoImpl.put(str, iLoginInfo);
        }
    }

    public void clearLoginInfoImpl() {
        this.mILoginInfoImpl = null;
    }

    public String getSid(String str) {
        if (this.mILoginInfoImpl == null) {
            return null;
        }
        ILoginInfo iLoginInfo = this.mILoginInfoImpl.get(str);
        if (iLoginInfo == null) {
            return null;
        }
        return iLoginInfo.getSid();
    }

    public String getUserId(String str) {
        if (this.mILoginInfoImpl == null) {
            return null;
        }
        ILoginInfo iLoginInfo = this.mILoginInfoImpl.get(str);
        if (iLoginInfo == null) {
            return null;
        }
        return iLoginInfo.getUserId();
    }

    public String getNick(String str) {
        if (this.mILoginInfoImpl == null) {
            return null;
        }
        ILoginInfo iLoginInfo = this.mILoginInfoImpl.get(str);
        if (iLoginInfo == null) {
            return null;
        }
        return iLoginInfo.getNick();
    }

    public void setAppReceiver(String str, IAppReceiver iAppReceiver) {
        if (iAppReceiver != null) {
            if (iAppReceiver instanceof IAgooAppReceiver) {
                mAgooAppReceiver = (IAgooAppReceiver) iAppReceiver;
                return;
            }
            if (this.mAppReceiver == null) {
                this.mAppReceiver = new ConcurrentHashMap<>(2);
            }
            this.mAppReceiver.put(str, iAppReceiver);
        }
    }

    public Map<String, IAppReceiver> getAppReceiver() {
        return this.mAppReceiver;
    }

    public void registerService(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            SERVICES.put(str, str2);
        }
    }

    public void unRegisterService(String str) {
        if (!TextUtils.isEmpty(str)) {
            SERVICES.remove(str);
        }
    }

    public String getService(String str) {
        return SERVICES.get(str);
    }

    public void registerListener(String str, AccsAbstractDataListener accsAbstractDataListener) {
        if (!TextUtils.isEmpty(str) && accsAbstractDataListener != null) {
            this.LISTENERS.put(str, accsAbstractDataListener);
        }
    }

    public void unregisterListener(String str) {
        this.LISTENERS.remove(str);
    }

    public AccsAbstractDataListener getListener(String str) {
        return this.LISTENERS.get(str);
    }

    public PackageInfo getPackageInfo() {
        try {
            if (this.mPackageInfo == null) {
                this.mPackageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            }
        } catch (Throwable th) {
            ALog.e("GlobalClientInfo", "getPackageInfo", th, new Object[0]);
        }
        return this.mPackageInfo;
    }
}
