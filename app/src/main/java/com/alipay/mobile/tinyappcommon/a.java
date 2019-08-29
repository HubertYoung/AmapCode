package com.alipay.mobile.tinyappcommon;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.ShareService;
import com.alipay.mobile.liteprocess.LiteProcessApi;
import com.alipay.mobile.liteprocess.ipc.IpcMsgClient;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.a.e;
import com.alipay.mobile.tinyappcommon.a.f;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.c.b;
import com.alipay.mobile.tinyappcommon.remotedebug.TinyAppRemoteDebugInterceptorImpl;
import com.alipay.mobile.tinyappcommon.storage.IPCSharedPreferenceStorage;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import com.alipay.mobile.worker.remotedebug.TinyAppRemoteDebugInterceptorManager;

/* compiled from: TinyAppInitService */
public final class a {
    /* access modifiers changed from: private */
    public volatile boolean a;

    /* renamed from: com.alipay.mobile.tinyappcommon.a$a reason: collision with other inner class name */
    /* compiled from: TinyAppInitService */
    private static class C0099a {
        public static a a = new a(0);
    }

    /* synthetic */ a(byte b) {
        this();
    }

    private a() {
        this.a = false;
    }

    public static a a() {
        return C0099a.a;
    }

    public final void b() {
        try {
            TinyAppRemoteDebugInterceptorManager.get().injectRemoteDebugInterceptor(TinyAppRemoteDebugInterceptorImpl.getInstance());
        } catch (Throwable e) {
            H5Log.e((String) "TinyAppInitService", "init..e:" + e);
        }
        WalletTinyappUtils.runOnMainThread(new Runnable() {
            public final void run() {
                if (!a.this.a) {
                    if (LiteProcessApi.isLiteProcess()) {
                        IpcMsgClient.registerRspBizHandler("TINY_APP_BIZ", e.a());
                    }
                    a.this.a = true;
                }
                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                    public final void run() {
                        TinyAppService.get().setMixActionService(c.a());
                        TinyAppService.get().setTinyAppStartupInterceptor(b.a);
                        TinyAppService.get().setLiteProcessService(f.a);
                        com.alipay.mobile.tinyappcommon.a.b.a();
                        a.d();
                        IPCSharedPreferenceStorage.getInstance().init();
                    }
                });
            }
        });
        ShareService shareService = (ShareService) H5Utils.findServiceByInterface(ShareService.class.getName());
        if (shareService != null) {
            synchronized (shareService) {
                if (!(shareService.getQRCodeShareInterceptor() instanceof com.alipay.mobile.tinyappcommon.b.a)) {
                    shareService.setQRCodeShareInterceptor(new com.alipay.mobile.tinyappcommon.b.a(shareService.getQRCodeShareInterceptor()));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void d() {
        String userId = LoggerFactory.getLogContext().getUserId();
        if (TextUtils.isEmpty(userId)) {
            H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
            if (h5LoginProvider != null) {
                userId = h5LoginProvider.getUserId();
            }
            if (!TextUtils.isEmpty(userId)) {
                LoggerFactory.getLogContext().setUserId(userId);
            }
        }
    }
}
