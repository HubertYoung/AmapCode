package com.alipay.mobile.tinyappcustom.biz.auth;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.accountopenauth.IAccountOAuthCallback;
import com.alipay.android.phone.inside.api.accountopenauth.IAccountOAuthService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcustom.api.TinyAppAuthCallback;
import com.alipay.mobile.tinyappcustom.api.TinyAppAuthManager;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TinyAppAuthBridge implements IAccountOAuthService {
    private static final String TAG = TinyAppAuthBridge.class.getName();
    private Map<String, Bundle> authRequests;

    private static class a {
        /* access modifiers changed from: private */
        public static final TinyAppAuthBridge a = new TinyAppAuthBridge();
    }

    private TinyAppAuthBridge() {
        this.authRequests = new ConcurrentHashMap();
    }

    public static TinyAppAuthBridge get() {
        return a.a;
    }

    public void executeSkipIdentifyAuth(String uuid, Bundle requestParams) {
        if (!TextUtils.isEmpty(uuid)) {
            this.authRequests.put(uuid, requestParams);
        }
    }

    public void getMCAuthLoginInfo(String needRefreshToken, String needAuth, String uuid, IAccountOAuthCallback iAccountOAuthCallback) {
        try {
            TinyAppAuthCallback callback = TinyAppAuthManager.get().getAuthCallback();
            if (callback == null) {
                H5Log.e(TAG, (String) "third party don't inject auth callback");
            } else if ("yes".equalsIgnoreCase(needAuth)) {
                H5Log.d(TAG, "needAuth is yes");
                if (TextUtils.isEmpty(uuid)) {
                    H5Log.e(TAG, (String) "account inside doesn't pass uuid");
                    return;
                }
                Bundle params = this.authRequests.get(uuid);
                if (params != null) {
                    callback.getMCAuthLoginInfo(needRefreshToken, params, iAccountOAuthCallback);
                } else {
                    H5Log.e(TAG, "uuid:" + uuid + ",can't find auth request params");
                }
            } else {
                H5Log.d(TAG, "needAuth is no");
                callback.getMCAuthLoginInfo(needRefreshToken, iAccountOAuthCallback);
            }
        } catch (Throwable e) {
            H5Log.e(TAG, (String) "auth callback has some problems");
            H5Log.e(TAG, e);
        }
    }

    public void openH5Page(Bundle bundle) {
        if (bundle == null) {
            try {
                H5Log.d(TAG, "openH5Page bundle is null ...");
            } catch (Throwable e) {
                H5Log.e(TAG, (String) "open auth h5 web failed");
                H5Log.e(TAG, e);
            }
        } else {
            WeakReference ref = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity();
            Activity topActivity = null;
            if (ref != null) {
                topActivity = (Activity) ref.get();
            }
            if (topActivity != null) {
                H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
                H5Bundle h5Bundle = new H5Bundle();
                h5Bundle.setParams(bundle);
                if (h5Service != null) {
                    H5Log.d(TAG, "openH5Page..." + bundle.toString());
                    h5Service.startPageFromActivity(topActivity, h5Bundle);
                    return;
                }
                return;
            }
            bundle.putBoolean(Const.START_APP_IN_CURRENT_PROCESS, true);
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp("", "20000067", bundle);
        }
    }
}
