package com.autonavi.minimap.offline.koala.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.autonavi.minimap.offline.koala.KoalaConfig;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadDashboard;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class KoalaDownloadClient implements ServiceConnection {
    private List<a> mActionList;
    private boolean mBindService;
    private KoalaDownloadBinder mBinder;

    public interface Action {
        void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard);
    }

    static class a {
        public String a;
        public KoalaConfig b;
        public WeakReference<Action> c;

        public a(String str, KoalaConfig koalaConfig, WeakReference<Action> weakReference) {
            this.a = str;
            this.b = koalaConfig;
            this.c = weakReference;
        }
    }

    static final class b {
        /* access modifiers changed from: private */
        public static final KoalaDownloadClient a = new KoalaDownloadClient();
    }

    public static KoalaDownloadClient getInstance() {
        return b.a;
    }

    private KoalaDownloadClient() {
        this.mBindService = false;
        this.mActionList = new ArrayList();
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            this.mBinder = (KoalaDownloadBinder) iBinder;
            for (int size = this.mActionList.size() - 1; size >= 0; size--) {
                a remove = this.mActionList.remove(size);
                Action action = (Action) remove.c.get();
                if (action != null) {
                    action.execute(getDashboard(remove.a, remove.b));
                }
            }
        } catch (Throwable unused) {
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.mBinder = null;
    }

    public IKoalaDownloadDashboard getDashboard(String str, KoalaConfig koalaConfig) {
        if (this.mBinder != null) {
            return this.mBinder.getDashboard(str, koalaConfig);
        }
        throw new IllegalArgumentException("binder is null!");
    }

    public void releaseDashboard(String str) {
        if (this.mBinder != null) {
            this.mBinder.releaseDashboard(str);
        }
    }

    public boolean isConnected() {
        return this.mBinder != null;
    }

    public void bindService(Context context) {
        try {
            Intent intent = new Intent(context, KoalaDownloadService.class);
            this.mBindService = context.bindService(intent, this, 1);
            context.startService(intent);
        } catch (Throwable unused) {
        }
    }

    public void unbindService(Context context) {
        try {
            if (this.mBindService) {
                context.unbindService(this);
                this.mBindService = false;
            }
            context.stopService(new Intent(context, KoalaDownloadService.class));
        } catch (Throwable unused) {
        }
    }

    public void run(String str, KoalaConfig koalaConfig, WeakReference<Action> weakReference) {
        if (!isConnected()) {
            bindService(koalaConfig.getContext());
            this.mActionList.add(new a(str, koalaConfig, weakReference));
        } else if (weakReference.get() != null) {
            ((Action) weakReference.get()).execute(getDashboard(str, koalaConfig));
        }
    }
}
