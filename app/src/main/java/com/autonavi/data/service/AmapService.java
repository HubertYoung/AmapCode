package com.autonavi.data.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.data.service.IDataProtocol.Stub;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class AmapService extends Service {
    public static final String a = "AmapService";
    /* access modifiers changed from: 0000 */
    public Handler b = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public boolean c = false;
    /* access modifiers changed from: private */
    public Map<Integer, Pair<aib, Boolean>> d = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public Map<Integer, IMessageCallback> e = new ConcurrentHashMap();
    private Stub f = new Stub() {
        public void sendCommandMessage(String str, String str2, int i, String str3, IResultCallBack iResultCallBack) throws RemoteException {
            int callingUid = getCallingUid();
            StringBuilder sb = new StringBuilder("module=");
            sb.append(str);
            sb.append(" methodID=");
            sb.append(str2);
            sb.append(" token=");
            sb.append(i);
            sb.append(" jsonParam=");
            sb.append(str3);
            sb.append(" callUid=");
            sb.append(callingUid);
            String sb2 = sb.toString();
            AMapLog.d("AMapService", sb2);
            aiq.a((String) "logRequest", sb2);
            final Pair a = AmapService.a(AmapService.this, callingUid);
            if (((Boolean) a.second).booleanValue()) {
                AmapService.this.c = aiv.a();
                if (TextUtils.equals(str2, "getAmapStatus")) {
                    AmapService amapService = AmapService.this;
                    String str4 = ((aib) a.first).a;
                    Handler handler = amapService.b;
                    AnonymousClass3 r0 = new Runnable(i, iResultCallBack, str4, str2, str3) {
                        final /* synthetic */ int a;
                        final /* synthetic */ IResultCallBack b;
                        final /* synthetic */ String c;
                        final /* synthetic */ String d;
                        final /* synthetic */ String e;

                        {
                            this.a = r2;
                            this.b = r3;
                            this.c = r4;
                            this.d = r5;
                            this.e = r6;
                        }

                        public final void run() {
                            aiu.a(this.a, this.b, this.c, this.d, this.e, aid.a((!ait.a() || !aim.a().a) ? 9001 : 10000, null));
                        }
                    };
                    handler.post(r0);
                } else if (!AmapService.this.c) {
                    aiu.a(i, iResultCallBack, ((aib) a.first).a, str2, str3, aid.a(9006, null));
                } else if (TextUtils.equals(str, "voice")) {
                    Handler b = AmapService.this.b;
                    final int i2 = i;
                    final IResultCallBack iResultCallBack2 = iResultCallBack;
                    final String str5 = str2;
                    final String str6 = str3;
                    AnonymousClass1 r02 = new Runnable() {
                        public final void run() {
                            aim.a();
                            aim.a(i2, iResultCallBack2, ((aib) a.first).a, str5, str6);
                        }
                    };
                    b.post(r02);
                } else {
                    aiu.a(i, iResultCallBack, ((aib) a.first).a, str2, str3, aid.a(9004, null));
                }
            } else {
                aiu.a(i, iResultCallBack, ((aib) a.first).a, str2, str3, aid.a(9005, null));
            }
        }

        public void setProtocolMessageCallback(IMessageCallback iMessageCallback) throws RemoteException {
            int callingUid = getCallingUid();
            AMapLog.d("AMapService", "setProtocolMessageCallback() uid=".concat(String.valueOf(callingUid)));
            AmapService.this.e.put(Integer.valueOf(callingUid), iMessageCallback);
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            int callingUid = getCallingUid();
            AMapLog.d("AMapService", "onTransact() uid".concat(String.valueOf(callingUid)));
            if (!((Boolean) AmapService.a(AmapService.this, callingUid).second).booleanValue()) {
                Pair<aib, Boolean> a = aiv.a((Context) AmapService.this, callingUid);
                AmapService.this.d.put(Integer.valueOf(callingUid), new Pair(a.first, Boolean.valueOf(((Boolean) a.second).booleanValue())));
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }
    };
    private a g = new a() {
        public final void a(String str) {
            aiq.a(str);
            AmapService.this.c = aiv.a();
            if (!AmapService.this.c) {
                StringBuilder sb = new StringBuilder("云控没开  mVoiceModuleCloudOpen=");
                sb.append(AmapService.this.c);
                aiq.a(sb.toString());
            }
            for (Entry entry : AmapService.this.e.entrySet()) {
                if (((Boolean) AmapService.a(AmapService.this, ((Integer) entry.getKey()).intValue()).second).booleanValue() && entry.getValue() != null) {
                    try {
                        ((IMessageCallback) entry.getValue()).onMessageCallback(str);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        StringBuilder sb2 = new StringBuilder("exception:");
                        sb2.append(e.getMessage());
                        aiq.a(sb2.toString());
                    }
                }
            }
        }
    };

    public void onCreate() {
        super.onCreate();
        this.c = aiv.a();
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        boolean z = this.c;
        StringBuilder sb = new StringBuilder("logBindedMessage:isAmapServiceOpen=");
        sb.append(z);
        sb.append(" isDebug=");
        sb.append(eqo.a);
        String sb2 = sb.toString();
        AMapLog.d("AMapService", sb2);
        aiq.a((String) "logBindedMessage", sb2);
        aik.a().a = this.g;
        return this.f;
    }

    public boolean onUnbind(Intent intent) {
        String concat = "logUnBindedMessage:isAmapServiceOpen=".concat(String.valueOf(this.c));
        AMapLog.d("AMapService", concat);
        aiq.a((String) "logUnBindedMessage", concat);
        this.f = null;
        this.d.clear();
        this.e.clear();
        aik.a().a = null;
        return super.onUnbind(intent);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    static /* synthetic */ Pair a(AmapService amapService, int i) {
        Pair pair = amapService.d.get(Integer.valueOf(i));
        return pair == null ? new Pair(new aib("", ""), Boolean.FALSE) : pair;
    }
}
