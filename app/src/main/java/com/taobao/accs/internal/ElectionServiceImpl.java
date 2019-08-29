package com.taobao.accs.internal;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.base.IBaseService;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.net.BaseConnection;
import com.taobao.accs.net.SpdyConnection;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.Utils;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ElectionServiceImpl implements IBaseService {
    private static final String TAG = "ElectionServiceImpl";
    protected static ConcurrentHashMap<String, BaseConnection> mConnections = new ConcurrentHashMap<>(2);
    private Service mBaseService = null;
    private Context mContext;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public abstract int onHostStartCommand(Intent intent, int i, int i2);

    public boolean onUnbind(Intent intent) {
        return false;
    }

    public abstract void onVotedHost();

    public ElectionServiceImpl(Service service) {
        this.mBaseService = service;
        this.mContext = service.getApplicationContext();
    }

    public void onCreate() {
        ALog.i(TAG, "onCreate,", Constants.KEY_SDK_VERSION, Integer.valueOf(Constants.SDK_VERSION_CODE));
    }

    public int onStartCommand(final Intent intent, int i, int i2) {
        if (intent == null) {
            return 2;
        }
        String action = intent.getAction();
        ALog.i(TAG, "onStartCommand begin", "action", action);
        if (TextUtils.equals(action, Constants.ACTION_START_SERVICE)) {
            ThreadPoolExecutorFactory.execute(new Runnable() {
                public void run() {
                    ElectionServiceImpl.this.handleStartCommand(intent);
                }
            });
        }
        return onHostStartCommand(intent, i, i2);
    }

    public void onDestroy() {
        ALog.e(TAG, "Service onDestroy", new Object[0]);
        this.mContext = null;
        this.mBaseService = null;
    }

    /* access modifiers changed from: private */
    public void handleStartCommand(Intent intent) {
        try {
            String stringExtra = intent.getStringExtra("packageName");
            String stringExtra2 = intent.getStringExtra("appKey");
            String stringExtra3 = intent.getStringExtra("ttid");
            String stringExtra4 = intent.getStringExtra("app_sercet");
            String stringExtra5 = intent.getStringExtra(Constants.KEY_CONFIG_TAG);
            int intExtra = intent.getIntExtra(Constants.KEY_MODE, 0);
            ALog.i(TAG, "handleStartCommand", Constants.KEY_CONFIG_TAG, stringExtra5, "appkey", stringExtra2, "appSecret", stringExtra4, "ttid", stringExtra3, "pkg", stringExtra);
            if (!TextUtils.isEmpty(stringExtra) && !TextUtils.isEmpty(stringExtra2) && stringExtra.equals(this.mContext.getPackageName())) {
                Utils.setMode(this.mContext, intExtra);
                BaseConnection connection = getConnection(this.mContext, stringExtra5, false, -1);
                if (connection != null) {
                    connection.mTtid = stringExtra3;
                } else {
                    ALog.e(TAG, "handleStartCommand start action, no connection", Constants.KEY_CONFIG_TAG, stringExtra5);
                }
                UtilityImpl.saveAppKey(this.mContext, stringExtra2, stringExtra4);
            }
        } catch (Throwable th) {
            ALog.e(TAG, "handleStartCommand", th, new Object[0]);
        }
    }

    protected static BaseConnection getConnection(Context context, String str, boolean z, int i) {
        BaseConnection baseConnection = null;
        try {
            if (TextUtils.isEmpty(str)) {
                ALog.w(TAG, "getConnection configTag null or env invalid", "conns.size", Integer.valueOf(mConnections.size()));
                return mConnections.size() > 0 ? mConnections.elements().nextElement() : null;
            }
            ALog.i(TAG, "getConnection", Constants.KEY_CONFIG_TAG, str, H5PageData.KEY_UC_START, Boolean.valueOf(z));
            AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
            if (configByTag == null || !configByTag.getDisableChannel()) {
                int mode = Utils.getMode(context);
                StringBuilder sb = new StringBuilder(str);
                sb.append(MergeUtil.SEPARATOR_KV);
                sb.append(mode);
                String sb2 = sb.toString();
                BaseConnection baseConnection2 = mConnections.get(sb2);
                if (baseConnection2 == null) {
                    try {
                        AccsClientConfig.mEnv = mode;
                        baseConnection = new SpdyConnection(context, 0, str);
                        if (z) {
                            baseConnection.start();
                        }
                        if (mConnections.size() < 10) {
                            mConnections.put(sb2, baseConnection);
                        } else {
                            ALog.e(TAG, "getConnection fail as exist too many conns!!!", new Object[0]);
                        }
                    } catch (Throwable th) {
                        th = th;
                        baseConnection = baseConnection2;
                        ALog.e(TAG, "getConnection", th, new Object[0]);
                        return baseConnection;
                    }
                } else {
                    baseConnection = baseConnection2;
                }
                return baseConnection;
            }
            ALog.e(TAG, "getConnection channel disabled!", Constants.KEY_CONFIG_TAG, str);
            return null;
        } catch (Throwable th2) {
            th = th2;
            ALog.e(TAG, "getConnection", th, new Object[0]);
            return baseConnection;
        }
    }
}
