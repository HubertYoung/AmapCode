package org.android.agoo.control;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.autonavi.common.SuperId;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.android.agoo.common.Config;
import org.android.agoo.common.MsgDO;
import org.android.agoo.common.ThreadUtil;
import org.android.agoo.message.MessageService;
import org.android.agoo.service.SendMessage;
import org.android.agoo.service.SendMessage.Stub;
import org.json.JSONArray;
import org.json.JSONObject;

public class AgooFactory {
    /* access modifiers changed from: private */
    public static Context d;
    protected NotifManager a = null;
    public ScheduledThreadPoolExecutor b;
    /* access modifiers changed from: private */
    public MessageService c = null;

    /* renamed from: org.android.agoo.control.AgooFactory$5 reason: invalid class name */
    class AnonymousClass5 implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ AgooFactory c;

        public void run() {
            this.c.a(this.a, this.b);
        }
    }

    class MessageConnection implements ServiceConnection {
        /* access modifiers changed from: private */
        public Intent b;
        private String c;
        /* access modifiers changed from: private */
        public SendMessage d;
        /* access modifiers changed from: private */
        public ServiceConnection e = this;

        public MessageConnection(String str, Intent intent) {
            this.c = str;
            this.b = intent;
        }

        public void onServiceDisconnected(ComponentName componentName) {
            ALog.d("AgooFactory", "MessageConnection disConnected", new Object[0]);
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ALog.d("AgooFactory", "MessageConnection conneted:".concat(String.valueOf(componentName)), new Object[0]);
            this.d = Stub.asInterface(iBinder);
            StringBuilder sb = new StringBuilder("onConnected current tid:");
            sb.append(Thread.currentThread().getId());
            ALog.d("AgooFactory", sb.toString(), new Object[0]);
            StringBuilder sb2 = new StringBuilder("MessageConnection sent:");
            sb2.append(this.b);
            ALog.d("AgooFactory", sb2.toString(), new Object[0]);
            if (this.d != null) {
                AgooFactory.this.b.execute(new Runnable() {
                    public void run() {
                        try {
                            StringBuilder sb = new StringBuilder("onConnected running tid:");
                            sb.append(Thread.currentThread().getId());
                            ALog.d("AgooFactory", sb.toString(), new Object[0]);
                            MessageConnection.this.d.doSend(MessageConnection.this.b);
                        } catch (RemoteException e) {
                            ALog.e("AgooFactory", "send error", e, new Object[0]);
                        } catch (Throwable th) {
                            ALog.d("AgooFactory", "send finish. close this connection", new Object[0]);
                            MessageConnection.this.d = null;
                            AgooFactory.d.unbindService(MessageConnection.this.e);
                            throw th;
                        }
                        ALog.d("AgooFactory", "send finish. close this connection", new Object[0]);
                        MessageConnection.this.d = null;
                        AgooFactory.d.unbindService(MessageConnection.this.e);
                    }
                });
            }
        }
    }

    class SendMessageRunnable implements Runnable {
        private String b;
        private Intent c;

        public SendMessageRunnable(String str, Intent intent) {
            this.b = str;
            this.c = intent;
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(9:0|1|2|3|4|5|6|(1:8)|9) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0060 */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x00c0 A[Catch:{ Throwable -> 0x00ca }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r6 = this;
                r0 = 0
                java.lang.String r1 = "AgooFactory"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r3 = "running tid:"
                r2.<init>(r3)     // Catch:{ Throwable -> 0x00ca }
                java.lang.Thread r3 = java.lang.Thread.currentThread()     // Catch:{ Throwable -> 0x00ca }
                long r3 = r3.getId()     // Catch:{ Throwable -> 0x00ca }
                r2.append(r3)     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r3 = ",pack="
                r2.append(r3)     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r3 = r6.b     // Catch:{ Throwable -> 0x00ca }
                r2.append(r3)     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00ca }
                java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x00ca }
                com.taobao.accs.utl.ALog.d(r1, r2, r3)     // Catch:{ Throwable -> 0x00ca }
                android.content.Context r1 = org.android.agoo.control.AgooFactory.d     // Catch:{ Throwable -> 0x00ca }
                android.content.Intent r2 = r6.c     // Catch:{ Throwable -> 0x00ca }
                r1.sendBroadcast(r2)     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r1 = "AgooFactory"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r3 = "SendMessageRunnable for accs,pack="
                r2.<init>(r3)     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r3 = r6.b     // Catch:{ Throwable -> 0x00ca }
                r2.append(r3)     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00ca }
                java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x00ca }
                com.taobao.accs.utl.ALog.d(r1, r2, r3)     // Catch:{ Throwable -> 0x00ca }
                android.content.Intent r1 = r6.c     // Catch:{ Throwable -> 0x0060 }
                java.lang.String r2 = r6.b     // Catch:{ Throwable -> 0x0060 }
                r1.setPackage(r2)     // Catch:{ Throwable -> 0x0060 }
                android.content.Intent r1 = r6.c     // Catch:{ Throwable -> 0x0060 }
                java.lang.String r2 = "org.agoo.android.intent.action.RECEIVE"
                r1.setAction(r2)     // Catch:{ Throwable -> 0x0060 }
                android.content.Context r1 = org.android.agoo.control.AgooFactory.d     // Catch:{ Throwable -> 0x0060 }
                android.content.Intent r2 = r6.c     // Catch:{ Throwable -> 0x0060 }
                r3 = 1
                com.taobao.accs.dispatch.IntentDispatch.dispatchIntent(r1, r2, r3)     // Catch:{ Throwable -> 0x0060 }
            L_0x0060:
                android.content.Intent r1 = new android.content.Intent     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r2 = "org.android.agoo.client.MessageReceiverService"
                r1.<init>(r2)     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r2 = r6.b     // Catch:{ Throwable -> 0x00ca }
                r1.setPackage(r2)     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r2 = "AgooFactory"
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r4 = "this message pack:"
                r3.<init>(r4)     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r4 = r6.b     // Catch:{ Throwable -> 0x00ca }
                r3.append(r4)     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x00ca }
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x00ca }
                com.taobao.accs.utl.ALog.d(r2, r3, r4)     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r2 = "AgooFactory"
                java.lang.String r3 = "start to service..."
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x00ca }
                com.taobao.accs.utl.ALog.d(r2, r3, r4)     // Catch:{ Throwable -> 0x00ca }
                org.android.agoo.control.AgooFactory$MessageConnection r2 = new org.android.agoo.control.AgooFactory$MessageConnection     // Catch:{ Throwable -> 0x00ca }
                org.android.agoo.control.AgooFactory r3 = org.android.agoo.control.AgooFactory.this     // Catch:{ Throwable -> 0x00ca }
                android.content.Intent r4 = r6.c     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r5 = "id"
                java.lang.String r4 = r4.getStringExtra(r5)     // Catch:{ Throwable -> 0x00ca }
                android.content.Intent r5 = r6.c     // Catch:{ Throwable -> 0x00ca }
                r2.<init>(r4, r5)     // Catch:{ Throwable -> 0x00ca }
                android.content.Context r3 = org.android.agoo.control.AgooFactory.d     // Catch:{ Throwable -> 0x00ca }
                org.android.agoo.control.AgooFactory.d     // Catch:{ Throwable -> 0x00ca }
                org.android.agoo.control.AgooFactory.d     // Catch:{ Throwable -> 0x00ca }
                r4 = 17
                boolean r1 = r3.bindService(r1, r2, r4)     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r2 = "AgooFactory"
                java.lang.String r3 = "start service ret:"
                java.lang.String r4 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x00ca }
                java.lang.String r3 = r3.concat(r4)     // Catch:{ Throwable -> 0x00ca }
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x00ca }
                com.taobao.accs.utl.ALog.d(r2, r3, r4)     // Catch:{ Throwable -> 0x00ca }
                if (r1 != 0) goto L_0x00c9
                java.lang.String r1 = "AgooFactory"
                java.lang.String r2 = "SendMessageRunnable is error"
                java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x00ca }
                com.taobao.accs.utl.ALog.d(r1, r2, r3)     // Catch:{ Throwable -> 0x00ca }
            L_0x00c9:
                return
            L_0x00ca:
                r1 = move-exception
                java.lang.String r2 = "AgooFactory"
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                java.lang.String r4 = "SendMessageRunnable is error,e="
                r3.<init>(r4)
                java.lang.String r1 = r1.toString()
                r3.append(r1)
                java.lang.String r1 = r3.toString()
                java.lang.Object[] r0 = new java.lang.Object[r0]
                com.taobao.accs.utl.ALog.e(r2, r1, r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.control.AgooFactory.SendMessageRunnable.run():void");
        }
    }

    public final void a(Context context, NotifManager notifManager, MessageService messageService) {
        d = context.getApplicationContext();
        this.b = ThreadUtil.a();
        this.a = notifManager;
        if (this.a == null) {
            this.a = new NotifManager();
        }
        this.a.a(d);
        this.c = messageService;
        if (this.c == null) {
            this.c = new MessageService();
        }
        this.c.a(d);
    }

    public final void a(final byte[] bArr, final String str) {
        if (bArr != null && bArr.length > 0) {
            this.b.execute(new Runnable() {
                public void run() {
                    try {
                        String str = new String(bArr, "utf-8");
                        JSONArray jSONArray = new JSONArray(str);
                        int length = jSONArray.length();
                        if (length == 1) {
                            String str2 = null;
                            String str3 = null;
                            for (int i = 0; i < length; i++) {
                                JSONObject jSONObject = jSONArray.getJSONObject(i);
                                if (jSONObject != null) {
                                    str2 = jSONObject.getString("i");
                                    str3 = jSONObject.getString(SuperId.BIT_1_MAIN_VOICE_ASSISTANT);
                                }
                            }
                            if (ALog.isPrintLog(Level.I)) {
                                StringBuilder sb = new StringBuilder("saveMsg msgId:");
                                sb.append(str2);
                                sb.append(",message=");
                                sb.append(str);
                                sb.append(",currentPack=");
                                sb.append(str3);
                                sb.append(",reportTimes=");
                                sb.append(Config.f(AgooFactory.d));
                                ALog.i("AgooFactory", sb.toString(), new Object[0]);
                            }
                            if (!TextUtils.isEmpty(str3) && TextUtils.equals(str3, AgooFactory.d.getPackageName())) {
                                if (TextUtils.isEmpty(str)) {
                                    AgooFactory.this.c.a(str2, str, "0");
                                    return;
                                }
                                AgooFactory.this.c.a(str2, str, str);
                            }
                        }
                    } catch (Throwable th) {
                        StringBuilder sb2 = new StringBuilder("saveMsg fail:");
                        sb2.append(th.toString());
                        ALog.e("AgooFactory", sb2.toString(), new Object[0]);
                    }
                }
            });
        }
    }

    public final void a(final byte[] bArr, final String str, final ExtraInfo extraInfo) {
        try {
            if (ALog.isPrintLog(Level.I)) {
                ALog.i("AgooFactory", "into--[AgooFactory,msgRecevie]:messageSource=".concat(String.valueOf(str)), new Object[0]);
            }
            this.b.execute(new Runnable() {
                public void run() {
                    AgooFactory.this.a(bArr, str, extraInfo, true);
                }
            });
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("serviceImpl init task fail:");
            sb.append(th.toString());
            ALog.e("AgooFactory", sb.toString(), new Object[0]);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01ee, code lost:
        if (com.taobao.accs.utl.ALog.isPrintLog(com.taobao.accs.utl.ALog.Level.I) != false) goto L_0x01f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:?, code lost:
        com.taobao.accs.utl.ALog.i("AgooFactory", "agoo msg has no time", new java.lang.Object[0]);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:82:0x01e8 */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x03ad  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.os.Bundle a(byte[] r33, java.lang.String r34, com.taobao.accs.base.TaoBaseService.ExtraInfo r35, boolean r36) {
        /*
            r32 = this;
            r1 = r32
            r2 = r33
            r3 = r34
            r4 = r35
            r5 = 66002(0x101d2, float:9.2489E-41)
            r6 = 0
            r7 = 0
            if (r2 == 0) goto L_0x0376
            int r8 = r2.length     // Catch:{ Throwable -> 0x0373 }
            if (r8 > 0) goto L_0x0014
            goto L_0x0376
        L_0x0014:
            java.lang.String r8 = new java.lang.String     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r9 = "utf-8"
            r8.<init>(r2, r9)     // Catch:{ Throwable -> 0x0373 }
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0373 }
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)     // Catch:{ Throwable -> 0x0373 }
            if (r2 == 0) goto L_0x0046
            java.lang.String r2 = "AgooFactory"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r10 = "msgRecevie,message--->["
            r9.<init>(r10)     // Catch:{ Throwable -> 0x0373 }
            r9.append(r8)     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r10 = "],utdid="
            r9.append(r10)     // Catch:{ Throwable -> 0x0373 }
            android.content.Context r10 = d     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r10 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r10)     // Catch:{ Throwable -> 0x0373 }
            r9.append(r10)     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x0373 }
            java.lang.Object[] r10 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x0373 }
            com.taobao.accs.utl.ALog.i(r2, r9, r10)     // Catch:{ Throwable -> 0x0373 }
        L_0x0046:
            boolean r2 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0373 }
            if (r2 == 0) goto L_0x0079
            com.taobao.accs.utl.UTMini r2 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r3 = "accs.msgRecevie"
            android.content.Context r4 = d     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r4 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r4)     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r8 = "message==null"
            r2.commitEvent(r5, r3, r4, r8)     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r2 = "AgooFactory"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r4 = "handleMessage message==null,utdid="
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0373 }
            android.content.Context r4 = d     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r4 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r4)     // Catch:{ Throwable -> 0x0373 }
            r3.append(r4)     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0373 }
            java.lang.Object[] r4 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x0373 }
            com.taobao.accs.utl.ALog.i(r2, r3, r4)     // Catch:{ Throwable -> 0x0373 }
            return r6
        L_0x0079:
            org.json.JSONArray r2 = new org.json.JSONArray     // Catch:{ Throwable -> 0x0373 }
            r2.<init>(r8)     // Catch:{ Throwable -> 0x0373 }
            int r5 = r2.length()     // Catch:{ Throwable -> 0x0373 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0373 }
            r9.<init>()     // Catch:{ Throwable -> 0x0373 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0373 }
            r10.<init>()     // Catch:{ Throwable -> 0x0373 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0373 }
            r11.<init>()     // Catch:{ Throwable -> 0x0373 }
            r13 = r6
            r14 = r13
            r12 = 0
        L_0x0094:
            if (r12 >= r5) goto L_0x034d
            android.os.Bundle r13 = new android.os.Bundle     // Catch:{ Throwable -> 0x0373 }
            r13.<init>()     // Catch:{ Throwable -> 0x0373 }
            org.json.JSONObject r15 = r2.getJSONObject(r12)     // Catch:{ Throwable -> 0x0373 }
            if (r15 == 0) goto L_0x0331
            org.android.agoo.common.MsgDO r6 = new org.android.agoo.common.MsgDO     // Catch:{ Throwable -> 0x0373 }
            r6.<init>()     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r7 = "p"
            java.lang.String r7 = r15.getString(r7)     // Catch:{ Throwable -> 0x0373 }
            r22 = r2
            java.lang.String r2 = "i"
            java.lang.String r2 = r15.getString(r2)     // Catch:{ Throwable -> 0x0373 }
            r23 = r14
            java.lang.String r14 = "b"
            java.lang.String r14 = r15.getString(r14)     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r1 = "f"
            r24 = r10
            r25 = r11
            long r10 = r15.getLong(r1)     // Catch:{ Throwable -> 0x032d }
            java.lang.String r1 = "ext"
            boolean r1 = r15.isNull(r1)     // Catch:{ Throwable -> 0x032d }
            if (r1 != 0) goto L_0x00db
            java.lang.String r1 = "ext"
            java.lang.String r1 = r15.getString(r1)     // Catch:{ Throwable -> 0x00d5 }
            goto L_0x00dd
        L_0x00d5:
            r0 = move-exception
            r2 = r0
            r1 = r32
            goto L_0x03a5
        L_0x00db:
            r1 = r23
        L_0x00dd:
            r9.append(r2)     // Catch:{ Throwable -> 0x032d }
            r26 = r8
            int r8 = r5 + -1
            if (r12 >= r8) goto L_0x00ee
            r27 = r5
            java.lang.String r5 = ","
            r9.append(r5)     // Catch:{ Throwable -> 0x00d5 }
            goto L_0x00f0
        L_0x00ee:
            r27 = r5
        L_0x00f0:
            r6.a = r2     // Catch:{ Throwable -> 0x032d }
            r6.b = r1     // Catch:{ Throwable -> 0x032d }
            r6.c = r7     // Catch:{ Throwable -> 0x032d }
            r6.e = r3     // Catch:{ Throwable -> 0x032d }
            boolean r5 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Throwable -> 0x032d }
            if (r5 == 0) goto L_0x0113
            java.lang.String r2 = "11"
            r6.d = r2     // Catch:{ Throwable -> 0x00d5 }
            org.android.agoo.control.NotifManager.a(r6, r4)     // Catch:{ Throwable -> 0x00d5 }
        L_0x0105:
            r30 = r1
            r28 = r9
            r29 = r24
            r5 = r25
        L_0x010d:
            r9 = r26
            r1 = r32
            goto L_0x032a
        L_0x0113:
            boolean r5 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Throwable -> 0x032d }
            if (r5 == 0) goto L_0x0121
            java.lang.String r2 = "12"
            r6.d = r2     // Catch:{ Throwable -> 0x00d5 }
            org.android.agoo.control.NotifManager.a(r6, r4)     // Catch:{ Throwable -> 0x00d5 }
            goto L_0x0105
        L_0x0121:
            r16 = -1
            int r5 = (r10 > r16 ? 1 : (r10 == r16 ? 0 : -1))
            if (r5 != 0) goto L_0x012f
            java.lang.String r2 = "13"
            r6.d = r2     // Catch:{ Throwable -> 0x00d5 }
            org.android.agoo.control.NotifManager.a(r6, r4)     // Catch:{ Throwable -> 0x00d5 }
            goto L_0x0105
        L_0x012f:
            android.content.Context r5 = d     // Catch:{ Throwable -> 0x032d }
            boolean r5 = a(r5, r7)     // Catch:{ Throwable -> 0x032d }
            if (r5 != 0) goto L_0x017c
            java.lang.String r5 = "AgooFactory"
            java.lang.String r6 = "msgRecevie checkpackage is del,pack="
            java.lang.String r10 = java.lang.String.valueOf(r7)     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r6 = r6.concat(r10)     // Catch:{ Throwable -> 0x00d5 }
            r10 = 0
            java.lang.Object[] r11 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x00d5 }
            com.taobao.accs.utl.ALog.d(r5, r6, r11)     // Catch:{ Throwable -> 0x00d5 }
            com.taobao.accs.utl.UTMini r16 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x00d5 }
            r17 = 66002(0x101d2, float:9.2489E-41)
            java.lang.String r18 = "accs.msgRecevie"
            android.content.Context r5 = d     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r19 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r5)     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r20 = "deletePack"
            r21 = r7
            r16.commitEvent(r17, r18, r19, r20, r21)     // Catch:{ Throwable -> 0x00d5 }
            r5 = r25
            r5.append(r7)     // Catch:{ Throwable -> 0x00d5 }
            r6 = r24
            r6.append(r2)     // Catch:{ Throwable -> 0x00d5 }
            if (r12 >= r8) goto L_0x0175
            java.lang.String r2 = ","
            r5.append(r2)     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r2 = ","
            r6.append(r2)     // Catch:{ Throwable -> 0x00d5 }
        L_0x0175:
            r30 = r1
            r29 = r6
            r28 = r9
            goto L_0x010d
        L_0x017c:
            r8 = r24
            r5 = r25
            android.os.Bundle r10 = a(r10, r6)     // Catch:{ Throwable -> 0x032d }
            java.lang.String r11 = "encrypted"
            java.lang.String r11 = r10.getString(r11)     // Catch:{ Throwable -> 0x032d }
            r28 = r9
            android.content.Context r9 = d     // Catch:{ Throwable -> 0x032d }
            java.lang.String r9 = r9.getPackageName()     // Catch:{ Throwable -> 0x032d }
            boolean r9 = r9.equals(r7)     // Catch:{ Throwable -> 0x032d }
            r29 = r8
            if (r9 == 0) goto L_0x01d2
            r9 = 4
            java.lang.String r9 = java.lang.Integer.toString(r9)     // Catch:{ Throwable -> 0x00d5 }
            boolean r9 = android.text.TextUtils.equals(r11, r9)     // Catch:{ Throwable -> 0x00d5 }
            if (r9 != 0) goto L_0x01d0
            java.lang.String r2 = "AgooFactory"
            java.lang.String r7 = "msgRecevie msg encrypted flag not exist, cannot prase!!!"
            r8 = 0
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x00d5 }
            com.taobao.accs.utl.ALog.e(r2, r7, r9)     // Catch:{ Throwable -> 0x00d5 }
            com.taobao.accs.utl.UTMini r16 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x00d5 }
            r17 = 66002(0x101d2, float:9.2489E-41)
            java.lang.String r18 = "accs.msgRecevie"
            android.content.Context r2 = d     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r19 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r2)     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r20 = "encrypted!=4"
            java.lang.String r21 = "15"
            r16.commitEvent(r17, r18, r19, r20, r21)     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r2 = "24"
            r6.d = r2     // Catch:{ Throwable -> 0x00d5 }
            org.android.agoo.control.NotifManager.a(r6, r4)     // Catch:{ Throwable -> 0x00d5 }
            r30 = r1
            goto L_0x010d
        L_0x01d0:
            r6 = 0
            goto L_0x01d3
        L_0x01d2:
            r6 = 1
        L_0x01d3:
            r13.putAll(r10)     // Catch:{ Throwable -> 0x032d }
            java.lang.String r9 = "t"
            java.lang.String r9 = r15.getString(r9)     // Catch:{ Throwable -> 0x01e8 }
            boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x01e8 }
            if (r10 != 0) goto L_0x01fa
            java.lang.String r10 = "time"
            r13.putString(r10, r9)     // Catch:{ Throwable -> 0x01e8 }
            goto L_0x01fa
        L_0x01e8:
            com.taobao.accs.utl.ALog$Level r9 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x032d }
            boolean r9 = com.taobao.accs.utl.ALog.isPrintLog(r9)     // Catch:{ Throwable -> 0x032d }
            if (r9 == 0) goto L_0x01fa
            java.lang.String r9 = "AgooFactory"
            java.lang.String r10 = "agoo msg has no time"
            r11 = 0
            java.lang.Object[] r15 = new java.lang.Object[r11]     // Catch:{ Throwable -> 0x00d5 }
            com.taobao.accs.utl.ALog.i(r9, r10, r15)     // Catch:{ Throwable -> 0x00d5 }
        L_0x01fa:
            java.lang.String r9 = "trace"
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x032d }
            r13.putLong(r9, r10)     // Catch:{ Throwable -> 0x032d }
            java.lang.String r9 = "id"
            r13.putString(r9, r2)     // Catch:{ Throwable -> 0x032d }
            java.lang.String r2 = "body"
            r13.putString(r2, r14)     // Catch:{ Throwable -> 0x032d }
            java.lang.String r2 = "source"
            r13.putString(r2, r7)     // Catch:{ Throwable -> 0x032d }
            java.lang.String r2 = "fromAppkey"
            android.content.Context r9 = d     // Catch:{ Throwable -> 0x032d }
            java.lang.String r9 = org.android.agoo.common.Config.a(r9)     // Catch:{ Throwable -> 0x032d }
            r13.putString(r2, r9)     // Catch:{ Throwable -> 0x032d }
            java.lang.String r2 = "extData"
            r13.putString(r2, r1)     // Catch:{ Throwable -> 0x032d }
            java.lang.String r2 = "oriData"
            r9 = r26
            r13.putString(r2, r9)     // Catch:{ Throwable -> 0x032d }
            if (r36 == 0) goto L_0x031a
            android.content.Context r10 = d     // Catch:{ Throwable -> 0x032d }
            android.content.Intent r11 = new android.content.Intent     // Catch:{ Throwable -> 0x032d }
            r11.<init>()     // Catch:{ Throwable -> 0x032d }
            java.lang.String r14 = "org.agoo.android.intent.action.RECEIVE"
            r11.setAction(r14)     // Catch:{ Throwable -> 0x032d }
            r11.setPackage(r7)     // Catch:{ Throwable -> 0x032d }
            r11.putExtras(r13)     // Catch:{ Throwable -> 0x032d }
            java.lang.String r14 = "type"
            java.lang.String r15 = "common-push"
            r11.putExtra(r14, r15)     // Catch:{ Throwable -> 0x032d }
            java.lang.String r14 = "message_source"
            r11.putExtra(r14, r3)     // Catch:{ Throwable -> 0x032d }
            r14 = 32
            r11.addFlags(r14)     // Catch:{ Throwable -> 0x032d }
            android.os.Bundle r14 = new android.os.Bundle     // Catch:{ Throwable -> 0x0260 }
            r14.<init>()     // Catch:{ Throwable -> 0x0260 }
            java.lang.String r15 = "accs_extra"
            r14.putSerializable(r15, r4)     // Catch:{ Throwable -> 0x0260 }
            java.lang.String r15 = "msg_agoo_bundle"
            r11.putExtra(r15, r14)     // Catch:{ Throwable -> 0x0260 }
            r30 = r1
            goto L_0x026e
        L_0x0260:
            r0 = move-exception
            r14 = r0
            java.lang.String r15 = "AgooFactory"
            java.lang.String r8 = "sendMsgToBussiness"
            r30 = r1
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x032d }
            com.taobao.accs.utl.ALog.e(r15, r8, r14, r2)     // Catch:{ Throwable -> 0x032d }
        L_0x026e:
            com.taobao.accs.utl.ALog$Level r1 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x032d }
            boolean r1 = com.taobao.accs.utl.ALog.isPrintLog(r1)     // Catch:{ Throwable -> 0x032d }
            if (r1 == 0) goto L_0x02ac
            java.lang.String r1 = "AgooFactory"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r8 = "sendMsgToBussiness intent:"
            r2.<init>(r8)     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r8 = r13.toString()     // Catch:{ Throwable -> 0x00d5 }
            r2.append(r8)     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r8 = ",utdid="
            r2.append(r8)     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r8 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r10)     // Catch:{ Throwable -> 0x00d5 }
            r2.append(r8)     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r8 = ",pack="
            r2.append(r8)     // Catch:{ Throwable -> 0x00d5 }
            r2.append(r7)     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r8 = ",agooFlag="
            r2.append(r8)     // Catch:{ Throwable -> 0x00d5 }
            r2.append(r6)     // Catch:{ Throwable -> 0x00d5 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00d5 }
            r8 = 0
            java.lang.Object[] r14 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x00d5 }
            com.taobao.accs.utl.ALog.i(r1, r2, r14)     // Catch:{ Throwable -> 0x00d5 }
        L_0x02ac:
            if (r6 == 0) goto L_0x030c
            com.taobao.accs.utl.UTMini r16 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x032d }
            r17 = 66002(0x101d2, float:9.2489E-41)
            java.lang.String r18 = "accs.msgRecevie"
            java.lang.String r19 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r10)     // Catch:{ Throwable -> 0x032d }
            java.lang.String r20 = "agooMsg"
            java.lang.String r21 = "15"
            r16.commitEvent(r17, r18, r19, r20, r21)     // Catch:{ Throwable -> 0x032d }
            com.taobao.accs.utl.ALog$Level r1 = com.taobao.accs.utl.ALog.Level.D     // Catch:{ Throwable -> 0x02fd }
            boolean r1 = com.taobao.accs.utl.ALog.isPrintLog(r1)     // Catch:{ Throwable -> 0x02fd }
            if (r1 == 0) goto L_0x02ee
            java.lang.String r1 = "AgooFactory"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r6 = "onHandleMessage current tid:"
            r2.<init>(r6)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.Thread r6 = java.lang.Thread.currentThread()     // Catch:{ Throwable -> 0x02e9 }
            long r14 = r6.getId()     // Catch:{ Throwable -> 0x02e9 }
            r2.append(r14)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x02e9 }
            r6 = 0
            java.lang.Object[] r8 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x02e9 }
            com.taobao.accs.utl.ALog.d(r1, r2, r8)     // Catch:{ Throwable -> 0x02e9 }
            goto L_0x02ee
        L_0x02e9:
            r0 = move-exception
            r2 = r0
            r1 = r32
            goto L_0x0301
        L_0x02ee:
            r1 = r32
            java.util.concurrent.ScheduledThreadPoolExecutor r2 = r1.b     // Catch:{ Throwable -> 0x02fb }
            org.android.agoo.control.AgooFactory$SendMessageRunnable r6 = new org.android.agoo.control.AgooFactory$SendMessageRunnable     // Catch:{ Throwable -> 0x02fb }
            r6.<init>(r7, r11)     // Catch:{ Throwable -> 0x02fb }
            r2.execute(r6)     // Catch:{ Throwable -> 0x02fb }
            goto L_0x032a
        L_0x02fb:
            r0 = move-exception
            goto L_0x0300
        L_0x02fd:
            r0 = move-exception
            r1 = r32
        L_0x0300:
            r2 = r0
        L_0x0301:
            java.lang.String r6 = "AgooFactory"
            java.lang.String r7 = "sendMsgByBindService error >>"
            r8 = 0
            java.lang.Object[] r10 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x0373 }
            com.taobao.accs.utl.ALog.e(r6, r7, r2, r10)     // Catch:{ Throwable -> 0x0373 }
            goto L_0x032a
        L_0x030c:
            r1 = r32
            java.lang.String r2 = com.taobao.accs.client.AdapterGlobalClientInfo.getAgooCustomServiceName(r7)     // Catch:{ Throwable -> 0x0373 }
            r11.setClassName(r7, r2)     // Catch:{ Throwable -> 0x0373 }
            r2 = 1
            com.taobao.accs.dispatch.IntentDispatch.dispatchIntent(r10, r11, r2)     // Catch:{ Throwable -> 0x0373 }
            goto L_0x032a
        L_0x031a:
            r30 = r1
            r1 = r32
            java.lang.String r2 = "type"
            java.lang.String r6 = "common-push"
            r13.putString(r2, r6)     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r2 = "message_source"
            r13.putString(r2, r3)     // Catch:{ Throwable -> 0x0373 }
        L_0x032a:
            r14 = r30
            goto L_0x033d
        L_0x032d:
            r0 = move-exception
            r1 = r32
            goto L_0x0374
        L_0x0331:
            r22 = r2
            r27 = r5
            r28 = r9
            r29 = r10
            r5 = r11
            r23 = r14
            r9 = r8
        L_0x033d:
            int r12 = r12 + 1
            r11 = r5
            r8 = r9
            r2 = r22
            r5 = r27
            r9 = r28
            r10 = r29
            r6 = 0
            r7 = 0
            goto L_0x0094
        L_0x034d:
            r29 = r10
            r5 = r11
            int r2 = r5.length()     // Catch:{ Throwable -> 0x0373 }
            if (r2 <= 0) goto L_0x0372
            org.android.agoo.common.MsgDO r2 = new org.android.agoo.common.MsgDO     // Catch:{ Throwable -> 0x0373 }
            r2.<init>()     // Catch:{ Throwable -> 0x0373 }
            r6 = r29
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x0373 }
            r2.a = r6     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0373 }
            r2.c = r5     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r5 = "10"
            r2.d = r5     // Catch:{ Throwable -> 0x0373 }
            r2.e = r3     // Catch:{ Throwable -> 0x0373 }
            org.android.agoo.control.NotifManager.a(r2, r4)     // Catch:{ Throwable -> 0x0373 }
        L_0x0372:
            return r13
        L_0x0373:
            r0 = move-exception
        L_0x0374:
            r2 = r0
            goto L_0x03a5
        L_0x0376:
            com.taobao.accs.utl.UTMini r2 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r3 = "accs.msgRecevie"
            android.content.Context r4 = d     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r4 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r4)     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r6 = "data==null"
            r2.commitEvent(r5, r3, r4, r6)     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r2 = "AgooFactory"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r4 = "handleMessage data==null,utdid="
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0373 }
            android.content.Context r4 = d     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r4 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r4)     // Catch:{ Throwable -> 0x0373 }
            r3.append(r4)     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0373 }
            r4 = 0
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0373 }
            com.taobao.accs.utl.ALog.i(r2, r3, r5)     // Catch:{ Throwable -> 0x0373 }
            r2 = 0
            return r2
        L_0x03a5:
            com.taobao.accs.utl.ALog$Level r3 = com.taobao.accs.utl.ALog.Level.E
            boolean r3 = com.taobao.accs.utl.ALog.isPrintLog(r3)
            if (r3 == 0) goto L_0x03bf
            java.lang.String r3 = "AgooFactory"
            java.lang.String r4 = "msgRecevie is error,e="
            java.lang.String r2 = java.lang.String.valueOf(r2)
            java.lang.String r2 = r4.concat(r2)
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]
            com.taobao.accs.utl.ALog.e(r3, r2, r4)
        L_0x03bf:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.control.AgooFactory.a(byte[], java.lang.String, com.taobao.accs.base.TaoBaseService$ExtraInfo, boolean):android.os.Bundle");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00b2 A[Catch:{ Throwable -> 0x00e7 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r9) {
        /*
            r0 = 0
            r1 = 0
            android.content.Context r2 = d     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r2 = org.android.agoo.common.Config.a(r2)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r3 = "ACCS_SDK"
            android.content.Context r4 = d     // Catch:{ Throwable -> 0x00e7 }
            boolean r3 = com.taobao.accs.utl.UtilityImpl.utdidChanged(r3, r4)     // Catch:{ Throwable -> 0x00e7 }
            if (r3 == 0) goto L_0x001b
            java.lang.String r3 = "ACCS_SDK"
            android.content.Context r4 = d     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r3 = com.taobao.accs.utl.UtilityImpl.getUtdid(r3, r4)     // Catch:{ Throwable -> 0x00e7 }
            goto L_0x0021
        L_0x001b:
            android.content.Context r3 = d     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r3 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r3)     // Catch:{ Throwable -> 0x00e7 }
        L_0x0021:
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x00e7 }
            if (r4 == 0) goto L_0x002d
            android.content.Context r3 = d     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r3 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r3)     // Catch:{ Throwable -> 0x00e7 }
        L_0x002d:
            int r4 = com.taobao.accs.client.AdapterGlobalClientInfo.mSecurityType     // Catch:{ Throwable -> 0x00e7 }
            r5 = 2
            if (r4 != r5) goto L_0x0066
            java.lang.String r4 = com.taobao.accs.utl.AdapterUtilityImpl.mAgooAppSecret     // Catch:{ Throwable -> 0x00e7 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x00e7 }
            if (r4 != 0) goto L_0x005c
            java.lang.String r4 = com.taobao.accs.utl.AdapterUtilityImpl.mAgooAppSecret     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r5 = "utf-8"
            byte[] r4 = r4.getBytes(r5)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e7 }
            r5.<init>()     // Catch:{ Throwable -> 0x00e7 }
            r5.append(r2)     // Catch:{ Throwable -> 0x00e7 }
            r5.append(r3)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r3 = r5.toString()     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r5 = "utf-8"
            byte[] r3 = r3.getBytes(r5)     // Catch:{ Throwable -> 0x00e7 }
            byte[] r3 = org.android.agoo.common.EncryptUtil.a(r4, r3)     // Catch:{ Throwable -> 0x00e7 }
            goto L_0x00b0
        L_0x005c:
            java.lang.String r3 = "AgooFactory"
            java.lang.String r4 = "getAppsign secret null"
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00e7 }
            com.taobao.accs.utl.ALog.e(r3, r4, r5)     // Catch:{ Throwable -> 0x00e7 }
            goto L_0x00af
        L_0x0066:
            android.content.Context r4 = d     // Catch:{ Throwable -> 0x00e7 }
            com.alibaba.wireless.security.open.SecurityGuardManager r4 = com.alibaba.wireless.security.open.SecurityGuardManager.getInstance(r4)     // Catch:{ Throwable -> 0x00e7 }
            if (r4 == 0) goto L_0x00a6
            java.lang.String r5 = "AgooFactory"
            java.lang.String r6 = "SecurityGuardManager not null!"
            java.lang.Object[] r7 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00e7 }
            com.taobao.accs.utl.ALog.d(r5, r6, r7)     // Catch:{ Throwable -> 0x00e7 }
            com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent r4 = r4.getSecureSignatureComp()     // Catch:{ Throwable -> 0x00e7 }
            com.alibaba.wireless.security.open.SecurityGuardParamContext r5 = new com.alibaba.wireless.security.open.SecurityGuardParamContext     // Catch:{ Throwable -> 0x00e7 }
            r5.<init>()     // Catch:{ Throwable -> 0x00e7 }
            r5.appKey = r2     // Catch:{ Throwable -> 0x00e7 }
            java.util.Map<java.lang.String, java.lang.String> r6 = r5.paramMap     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r7 = "INPUT"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e7 }
            r8.<init>()     // Catch:{ Throwable -> 0x00e7 }
            r8.append(r2)     // Catch:{ Throwable -> 0x00e7 }
            r8.append(r3)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r3 = r8.toString()     // Catch:{ Throwable -> 0x00e7 }
            r6.put(r7, r3)     // Catch:{ Throwable -> 0x00e7 }
            r3 = 3
            r5.requestType = r3     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r3 = com.taobao.accs.client.AdapterGlobalClientInfo.mAuthCode     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r3 = r4.signRequest(r5, r3)     // Catch:{ Throwable -> 0x00e7 }
            byte[] r3 = org.android.agoo.common.EncryptUtil.a(r3)     // Catch:{ Throwable -> 0x00e7 }
            goto L_0x00b0
        L_0x00a6:
            java.lang.String r3 = "AgooFactory"
            java.lang.String r4 = "SecurityGuardManager is null"
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00e7 }
            com.taobao.accs.utl.ALog.e(r3, r4, r5)     // Catch:{ Throwable -> 0x00e7 }
        L_0x00af:
            r3 = r0
        L_0x00b0:
            if (r3 == 0) goto L_0x00dd
            int r4 = r3.length     // Catch:{ Throwable -> 0x00e7 }
            if (r4 <= 0) goto L_0x00dd
            r4 = 8
            byte[] r9 = android.util.Base64.decode(r9, r4)     // Catch:{ Throwable -> 0x00e7 }
            javax.crypto.spec.SecretKeySpec r4 = new javax.crypto.spec.SecretKeySpec     // Catch:{ Throwable -> 0x00e7 }
            byte[] r3 = org.android.agoo.common.EncryptUtil.a(r3)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r5 = "AES"
            r4.<init>(r3, r5)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r3 = "utf-8"
            byte[] r2 = r2.getBytes(r3)     // Catch:{ Throwable -> 0x00e7 }
            byte[] r2 = org.android.agoo.common.EncryptUtil.a(r2)     // Catch:{ Throwable -> 0x00e7 }
            byte[] r9 = org.android.agoo.common.EncryptUtil.a(r9, r4, r2)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r2 = new java.lang.String     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r3 = "utf-8"
            r2.<init>(r9, r3)     // Catch:{ Throwable -> 0x00e7 }
            r0 = r2
            goto L_0x00f1
        L_0x00dd:
            java.lang.String r9 = "AgooFactory"
            java.lang.String r2 = "aesDecrypt key is null!"
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00e7 }
            com.taobao.accs.utl.ALog.e(r9, r2, r3)     // Catch:{ Throwable -> 0x00e7 }
            goto L_0x00f1
        L_0x00e7:
            r9 = move-exception
            java.lang.String r2 = "AgooFactory"
            java.lang.String r3 = "parseEncryptedMsg failure: "
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.taobao.accs.utl.ALog.e(r2, r3, r9, r1)
        L_0x00f1:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.control.AgooFactory.a(java.lang.String):java.lang.String");
    }

    public final void a(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str)) {
                if (!TextUtils.isEmpty(str2)) {
                    if (ALog.isPrintLog(Level.I)) {
                        StringBuilder sb = new StringBuilder("updateNotifyMsg begin,messageId=");
                        sb.append(str);
                        sb.append(",status=");
                        sb.append(str2);
                        sb.append(",reportTimes=");
                        sb.append(Config.f(d));
                        ALog.i("AgooFactory", sb.toString(), new Object[0]);
                    }
                    if (TextUtils.equals(str2, "8")) {
                        this.c.a(str, (String) "2");
                        return;
                    }
                    if (TextUtils.equals(str2, "9")) {
                        this.c.a(str, (String) "3");
                    }
                }
            }
        } catch (Throwable th) {
            StringBuilder sb2 = new StringBuilder("updateNotifyMsg e=");
            sb2.append(th.toString());
            ALog.e("AgooFactory", sb2.toString(), new Object[0]);
        }
    }

    private static final boolean a(Context context, String str) {
        try {
            if (context.getPackageManager().getApplicationInfo(str, 0) != null) {
                return true;
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    private static Bundle a(long j, MsgDO msgDO) {
        Bundle bundle = new Bundle();
        try {
            char[] charArray = Long.toBinaryString(j).toCharArray();
            if (charArray != null && 8 <= charArray.length) {
                if (8 <= charArray.length) {
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(charArray[1]);
                    sb2.append(charArray[2]);
                    sb2.append(charArray[3]);
                    sb2.append(charArray[4]);
                    sb.append(Integer.parseInt(sb2.toString(), 2));
                    bundle.putString("encrypted", sb.toString());
                    if (charArray[6] == '1') {
                        bundle.putString("report", "1");
                        msgDO.j = "1";
                    }
                    if (charArray[7] == '1') {
                        bundle.putString("notify", "1");
                    }
                }
                if (9 <= charArray.length && charArray[8] == '1') {
                    bundle.putString("has_test", "1");
                }
                if (10 <= charArray.length && charArray[9] == '1') {
                    bundle.putString("duplicate", "1");
                }
                if (11 <= charArray.length && charArray[10] == '1') {
                    bundle.putInt("popup", 1);
                }
            }
        } catch (Throwable unused) {
        }
        return bundle;
    }
}
