package com.amap.bundle.lotuspool.internal.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.lotuspool.internal.model.bean.Command;
import com.amap.bundle.lotuspool.internal.model.bean.CommandResult;
import com.amap.bundle.lotuspool.internal.remote.IRemoteCommandExecuterProxy.Stub;
import org.jetbrains.annotations.Nullable;

public class LotusPoolProxyService extends Service {
    /* access modifiers changed from: private */
    public final String a = LotusPoolProxyService.class.getSimpleName();
    private Stub b = new Stub() {
        public CommandResult execute(String str, int i, Command command) throws RemoteException {
            wv a = wu.a(LotusPoolProxyService.this.getApplicationContext(), command.h);
            if (bno.a) {
                String a2 = LotusPoolProxyService.this.a;
                StringBuilder sb = new StringBuilder("run remote cmd=");
                sb.append(command.h);
                AMapLog.d(a2, sb.toString());
            }
            if (a == null) {
                return null;
            }
            return a.a(str, i, command);
        }
    };

    public int onStartCommand(Intent intent, int i, int i2) {
        return 2;
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return this.b;
    }
}
