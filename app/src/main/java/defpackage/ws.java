package defpackage;

import android.app.Service;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Looper;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.lotuspool.internal.model.CommandDao.Properties;
import com.amap.bundle.lotuspool.internal.model.bean.Command;
import com.amap.bundle.lotuspool.internal.model.bean.CommandResult;
import com.amap.bundle.lotuspool.internal.remote.IRemoteCommandExecuterProxy;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: ws reason: default package */
/* compiled from: CommandHandler */
public class ws extends Handler {
    /* access modifiers changed from: 0000 */
    public static final String a = "ws";
    xf b;
    Service c;
    ServiceConnection d;
    /* access modifiers changed from: private */
    public volatile IRemoteCommandExecuterProxy e;

    ws(Looper looper, Service service) {
        super(looper);
        this.c = service;
    }

    public final void a(int i) {
        obtainMessage(1, i, 0).sendToTarget();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0095, code lost:
        r2 = r1.b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0099, code lost:
        if (r2.c < 0) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a3, code lost:
        if (r2.c < r2.b.size()) goto L_0x00a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a6, code lost:
        r3 = r2.b;
        r4 = r2.c;
        r2.c = r4 + 1;
        r2 = r3.get(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b5, code lost:
        r2 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b6, code lost:
        if (r2 == null) goto L_0x011a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ba, code lost:
        if (r2.e == null) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c2, code lost:
        if (r2.e.isEmpty() != false) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00c6, code lost:
        if (defpackage.bno.a == false) goto L_0x0115;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00c8, code lost:
        r3 = a;
        r4 = new java.lang.StringBuilder("run commands =");
        r4.append(r2.a);
        r4.append(":");
        r4.append(r2.e.size());
        r4.append(":");
        r4.append(r2.b);
        r4.append(":time=");
        r4.append(r2.c);
        r4.append(":type=");
        r4.append(r2.d);
        r4.append(" hasremotecmds=");
        r4.append(r1.b.f);
        com.amap.bundle.logs.AMapLog.d(r3, r4.toString(), true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0115, code lost:
        a(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x011a, code lost:
        r1.b.a();
        sendEmptyMessage(5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0123, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleMessage(android.os.Message r21) {
        /*
            r20 = this;
            r1 = r20
            r2 = r21
            int r3 = r2.what
            r4 = 4
            r5 = 0
            r6 = 1
            switch(r3) {
                case 1: goto L_0x01f5;
                case 2: goto L_0x0176;
                case 3: goto L_0x0124;
                case 4: goto L_0x0095;
                case 5: goto L_0x000e;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x02d5
        L_0x000e:
            xf r2 = r1.b
            java.lang.String r2 = r2.c()
            boolean r3 = defpackage.bno.a
            if (r3 == 0) goto L_0x0027
            java.lang.String r3 = a
            java.lang.String r4 = "uploading results of execution="
            java.lang.String r7 = java.lang.String.valueOf(r2)
            java.lang.String r4 = r4.concat(r7)
            com.amap.bundle.logs.AMapLog.d(r3, r4, r6)
        L_0x0027:
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x02d5
            com.amap.bundle.lotuspool.internal.model.http.FeedbackResultsEntity r3 = new com.amap.bundle.lotuspool.internal.model.http.FeedbackResultsEntity
            r3.<init>()
            r3.feedbacks = r2
            com.amap.bundle.aosservice.request.AosPostRequest r2 = defpackage.aax.b(r3)     // Catch:{ Exception -> 0x0044 }
            defpackage.yq.a()     // Catch:{ Exception -> 0x0044 }
            java.lang.Class<com.amap.bundle.lotuspool.internal.model.http.FeedbackResultsResponse> r3 = com.amap.bundle.lotuspool.internal.model.http.FeedbackResultsResponse.class
            com.amap.bundle.aosservice.response.AosResponse r2 = defpackage.yq.a(r2, r3)     // Catch:{ Exception -> 0x0044 }
            com.amap.bundle.lotuspool.internal.model.http.FeedbackResultsResponse r2 = (com.amap.bundle.lotuspool.internal.model.http.FeedbackResultsResponse) r2     // Catch:{ Exception -> 0x0044 }
            goto L_0x0062
        L_0x0044:
            r0 = move-exception
            r2 = r0
            boolean r3 = defpackage.bno.a
            if (r3 == 0) goto L_0x0061
            java.lang.String r3 = a
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r7 = "upload results err="
            r4.<init>(r7)
            java.lang.String r2 = android.util.Log.getStackTraceString(r2)
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            com.amap.bundle.logs.AMapLog.w(r3, r2, r6)
        L_0x0061:
            r2 = r5
        L_0x0062:
            if (r2 == 0) goto L_0x02d5
            java.lang.Object r2 = r2.getResult()
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 != r6) goto L_0x02d5
            boolean r2 = defpackage.bno.a
            if (r2 == 0) goto L_0x007b
            java.lang.String r2 = a
            java.lang.String r3 = "upload feedback suc"
            com.amap.bundle.logs.AMapLog.d(r2, r3)
        L_0x007b:
            xf r2 = r1.b
            java.util.Map<java.lang.String, xg> r3 = r2.d
            boolean r3 = r3.isEmpty()
            if (r3 != 0) goto L_0x02d5
            java.util.Map<java.lang.String, xg> r3 = r2.d
            r3.clear()
            xe r2 = r2.e
            xd r2 = r2.a
            java.lang.Class<com.amap.bundle.lotuspool.internal.model.bean.CommandResult> r3 = com.amap.bundle.lotuspool.internal.model.bean.CommandResult.class
            r2.deleteAll(r3)
            goto L_0x02d5
        L_0x0095:
            xf r2 = r1.b
            int r3 = r2.c
            if (r3 < 0) goto L_0x00b5
            int r3 = r2.c
            java.util.List<xh> r4 = r2.b
            int r4 = r4.size()
            if (r3 < r4) goto L_0x00a6
            goto L_0x00b5
        L_0x00a6:
            java.util.List<xh> r3 = r2.b
            int r4 = r2.c
            int r7 = r4 + 1
            r2.c = r7
            java.lang.Object r2 = r3.get(r4)
            xh r2 = (defpackage.xh) r2
            goto L_0x00b6
        L_0x00b5:
            r2 = r5
        L_0x00b6:
            if (r2 == 0) goto L_0x011a
            java.util.List<com.amap.bundle.lotuspool.internal.model.bean.Command> r3 = r2.e
            if (r3 == 0) goto L_0x0095
            java.util.List<com.amap.bundle.lotuspool.internal.model.bean.Command> r3 = r2.e
            boolean r3 = r3.isEmpty()
            if (r3 != 0) goto L_0x0095
            boolean r3 = defpackage.bno.a
            if (r3 == 0) goto L_0x0115
            java.lang.String r3 = a
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r7 = "run commands ="
            r4.<init>(r7)
            java.lang.String r7 = r2.a
            r4.append(r7)
            java.lang.String r7 = ":"
            r4.append(r7)
            java.util.List<com.amap.bundle.lotuspool.internal.model.bean.Command> r7 = r2.e
            int r7 = r7.size()
            r4.append(r7)
            java.lang.String r7 = ":"
            r4.append(r7)
            long r7 = r2.b
            r4.append(r7)
            java.lang.String r7 = ":time="
            r4.append(r7)
            long r7 = r2.c
            r4.append(r7)
            java.lang.String r7 = ":type="
            r4.append(r7)
            int r7 = r2.d
            r4.append(r7)
            java.lang.String r7 = " hasremotecmds="
            r4.append(r7)
            xf r7 = r1.b
            boolean r7 = r7.f
            r4.append(r7)
            java.lang.String r4 = r4.toString()
            com.amap.bundle.logs.AMapLog.d(r3, r4, r6)
        L_0x0115:
            r1.a(r2)
            goto L_0x0095
        L_0x011a:
            xf r2 = r1.b
            r2.a()
            r2 = 5
            r1.sendEmptyMessage(r2)
            return
        L_0x0124:
            android.content.ServiceConnection r2 = r1.d
            if (r2 != 0) goto L_0x0172
            xf r2 = r1.b
            boolean r2 = r2.f
            if (r2 == 0) goto L_0x0172
            android.content.Intent r2 = new android.content.Intent
            android.app.Service r3 = r1.c
            java.lang.Class<com.amap.bundle.lotuspool.internal.remote.LotusPoolProxyService> r5 = com.amap.bundle.lotuspool.internal.remote.LotusPoolProxyService.class
            r2.<init>(r3, r5)
            ws$1 r3 = new ws$1
            r3.<init>()
            r1.d = r3
            boolean r3 = defpackage.bno.a
            if (r3 == 0) goto L_0x0149
            java.lang.String r3 = a
            java.lang.String r5 = "bind main service"
            com.amap.bundle.logs.AMapLog.d(r3, r5, r6)
        L_0x0149:
            android.app.Service r3 = r1.c     // Catch:{ Exception -> 0x0151 }
            android.content.ServiceConnection r5 = r1.d     // Catch:{ Exception -> 0x0151 }
            r3.bindService(r2, r5, r6)     // Catch:{ Exception -> 0x0151 }
            return
        L_0x0151:
            r0 = move-exception
            r2 = r0
            boolean r3 = defpackage.bno.a
            if (r3 == 0) goto L_0x016e
            java.lang.String r3 = a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r7 = "bind remote service err! "
            r5.<init>(r7)
            java.lang.String r2 = android.util.Log.getStackTraceString(r2)
            r5.append(r2)
            java.lang.String r2 = r5.toString()
            com.amap.bundle.logs.AMapLog.e(r3, r2, r6)
        L_0x016e:
            r1.sendEmptyMessage(r4)
            return
        L_0x0172:
            r1.sendEmptyMessage(r4)
            return
        L_0x0176:
            com.amap.bundle.lotuspool.internal.model.http.CommandsListEntity r3 = new com.amap.bundle.lotuspool.internal.model.http.CommandsListEntity
            r3.<init>()
            xf r7 = r1.b
            java.lang.String r7 = r7.b()
            r3.last_dispatch_sequence = r7
            boolean r7 = defpackage.bno.a
            if (r7 == 0) goto L_0x019c
            java.lang.String r7 = a
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "request commands params="
            r8.<init>(r9)
            java.lang.String r9 = r3.last_dispatch_sequence
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            com.amap.bundle.logs.AMapLog.d(r7, r8)
        L_0x019c:
            com.amap.bundle.aosservice.request.AosPostRequest r3 = defpackage.aax.b(r3)     // Catch:{ Exception -> 0x01ac }
            defpackage.yq.a()     // Catch:{ Exception -> 0x01ac }
            java.lang.Class<com.amap.bundle.lotuspool.internal.model.http.RemoteTaskResponse> r7 = com.amap.bundle.lotuspool.internal.model.http.RemoteTaskResponse.class
            com.amap.bundle.aosservice.response.AosResponse r3 = defpackage.yq.a(r3, r7)     // Catch:{ Exception -> 0x01ac }
            com.amap.bundle.lotuspool.internal.model.http.RemoteTaskResponse r3 = (com.amap.bundle.lotuspool.internal.model.http.RemoteTaskResponse) r3     // Catch:{ Exception -> 0x01ac }
            goto L_0x01bc
        L_0x01ac:
            r0 = move-exception
            r3 = r0
            java.lang.String r7 = "T1"
            java.lang.String r8 = a
            java.lang.String r9 = "Net-Exception"
            java.lang.String r3 = android.util.Log.getStackTraceString(r3)
            com.amap.bundle.logs.AMapLog.logErrorNative(r7, r8, r9, r3)
            r3 = r5
        L_0x01bc:
            if (r3 == 0) goto L_0x01e9
            int r5 = r3.a
            if (r5 != r6) goto L_0x01e9
            java.lang.Object r5 = r3.getResult()
            if (r5 == 0) goto L_0x01e9
            xf r5 = r1.b
            java.lang.Object r3 = r3.getResult()
            java.util.List r3 = (java.util.List) r3
            r5.a(r3)
            int r2 = r2.arg1
            if (r6 != r2) goto L_0x01df
            long r2 = java.lang.System.currentTimeMillis()
            defpackage.xf.a(r2)
            return
        L_0x01df:
            if (r4 != r2) goto L_0x01e8
            long r2 = java.lang.System.currentTimeMillis()
            defpackage.xf.b(r2)
        L_0x01e8:
            return
        L_0x01e9:
            boolean r2 = defpackage.bno.a
            if (r2 == 0) goto L_0x01f4
            java.lang.String r2 = a
            java.lang.String r3 = "response err!"
            com.amap.bundle.logs.AMapLog.e(r2, r3, r6)
        L_0x01f4:
            return
        L_0x01f5:
            xf r3 = r1.b
            r4 = 2
            r5 = 0
            if (r3 != 0) goto L_0x02c1
            xf r3 = new xf
            r3.<init>()
            r1.b = r3
            xf r3 = r1.b
            boolean r7 = defpackage.bno.a
            if (r7 == 0) goto L_0x020f
            java.lang.String r7 = defpackage.xf.a
            java.lang.String r8 = "LotusPoolStorage init"
            com.amap.bundle.logs.AMapLog.d(r7, r8)
        L_0x020f:
            xe r7 = new xe
            android.app.Application r8 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            r7.<init>(r8)
            r3.e = r7
            xe r7 = r3.e
            java.util.List<xh> r8 = r3.b
            xd r7 = r7.a
            java.lang.Class<com.amap.bundle.lotuspool.internal.model.bean.Command> r9 = com.amap.bundle.lotuspool.internal.model.bean.Command.class
            de.greenrobot.dao.query.QueryBuilder r7 = r7.queryBuilder(r9)
            de.greenrobot.dao.Property[] r9 = new de.greenrobot.dao.Property[r4]
            de.greenrobot.dao.Property r10 = com.amap.bundle.lotuspool.internal.model.CommandDao.Properties.b
            r9[r5] = r10
            de.greenrobot.dao.Property r10 = com.amap.bundle.lotuspool.internal.model.CommandDao.Properties.f
            r9[r6] = r10
            de.greenrobot.dao.query.QueryBuilder r7 = r7.orderAsc(r9)
            de.greenrobot.dao.query.Query r7 = r7.build()
            java.util.List r7 = r7.list()
            if (r7 == 0) goto L_0x028c
            boolean r9 = r7.isEmpty()
            if (r9 == 0) goto L_0x0245
            goto L_0x028c
        L_0x0245:
            java.util.Iterator r7 = r7.iterator()
            r9 = 0
        L_0x024a:
            boolean r10 = r7.hasNext()
            if (r10 == 0) goto L_0x028d
            java.lang.Object r10 = r7.next()
            com.amap.bundle.lotuspool.internal.model.bean.Command r10 = (com.amap.bundle.lotuspool.internal.model.bean.Command) r10
            boolean r11 = r10.a()
            if (r11 == 0) goto L_0x024a
            xh r11 = new xh
            long r13 = r10.b
            java.lang.String r15 = r10.a
            r19 = r7
            long r6 = r10.d
            int r12 = r10.e
            r18 = r12
            r12 = r11
            r16 = r6
            r12.<init>(r13, r15, r16, r18)
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r11.e = r6
            java.util.List<com.amap.bundle.lotuspool.internal.model.bean.Command> r6 = r11.e
            r6.add(r10)
            r8.add(r11)
            if (r9 != 0) goto L_0x0288
            boolean r6 = defpackage.xi.a(r10)
            if (r6 == 0) goto L_0x0288
            r9 = 1
        L_0x0288:
            r7 = r19
            r6 = 1
            goto L_0x024a
        L_0x028c:
            r9 = 0
        L_0x028d:
            r3.f = r9
            xe r6 = r3.e
            java.util.Map r6 = r6.a()
            r3.d = r6
            java.util.Map<java.lang.String, xg> r6 = r3.d
            if (r6 != 0) goto L_0x02a2
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            r3.d = r6
        L_0x02a2:
            ik r3 = defpackage.ik.a()
            android.app.Application r6 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r7 = r3.d
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 == 0) goto L_0x02bf
            android.content.Context r7 = r3.b
            if (r7 != 0) goto L_0x02b8
            r3.b = r6
        L_0x02b8:
            android.content.Context r6 = r3.b
            if (r6 == 0) goto L_0x02bf
            r3.b()
        L_0x02bf:
            java.lang.String r3 = r3.d
        L_0x02c1:
            int r2 = r2.arg1
            boolean r3 = defpackage.xi.a(r2)
            if (r3 == 0) goto L_0x02d0
            android.os.Message r2 = r1.obtainMessage(r4, r2, r5)
            r2.sendToTarget()
        L_0x02d0:
            r2 = 3
            r1.sendEmptyMessage(r2)
            return
        L_0x02d5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ws.handleMessage(android.os.Message):void");
    }

    private void a(xh xhVar) {
        ws wsVar;
        ws wsVar2 = this;
        xh xhVar2 = xhVar;
        ArrayList arrayList = new ArrayList(xhVar2.e.size());
        Iterator<Command> it = xhVar2.e.iterator();
        while (it.hasNext()) {
            Command next = it.next();
            if (bno.a) {
                String str = a;
                StringBuilder sb = new StringBuilder("run command=");
                sb.append(xhVar2.a);
                sb.append(":");
                sb.append(next.f);
                sb.append(":");
                sb.append(next.h);
                AMapLog.e(str, sb.toString());
            }
            if (xf.a(xhVar2.a, String.valueOf(next.f))) {
                arrayList.add(next);
            } else {
                wv a2 = wu.a(AMapAppGlobal.getApplication(), next.h);
                if (a2 == null) {
                    xf xfVar = wsVar2.b;
                    Iterator<Command> it2 = it;
                    CommandResult commandResult = r7;
                    CommandResult commandResult2 = new CommandResult(xhVar2.a, xhVar2.b, xhVar2.c, xhVar2.d, next.i, next.f, 103, "");
                    xfVar.a(commandResult);
                    arrayList.add(next);
                    it = it2;
                    wsVar2 = this;
                    xhVar2 = xhVar;
                } else {
                    Iterator<Command> it3 = it;
                    if (next.k <= 0 || next.k >= System.currentTimeMillis()) {
                        xhVar2 = xhVar;
                        if (!a2.a(next)) {
                            xf xfVar2 = this.b;
                            CommandResult commandResult3 = r6;
                            CommandResult commandResult4 = new CommandResult(xhVar2.a, xhVar2.b, xhVar2.c, xhVar2.d, next.i, next.f, 101, "");
                            xfVar2.a(commandResult3);
                            arrayList.add(next);
                        } else if (xi.a(xhVar2.c, next)) {
                            xf.b(xhVar2.a, String.valueOf(next.f));
                            CommandResult commandResult5 = null;
                            if (xi.a(next)) {
                                wsVar = this;
                                if (wsVar.e != null) {
                                    try {
                                        commandResult5 = wsVar.e.execute(xhVar2.a, next.f, next);
                                    } catch (Exception e2) {
                                        Exception exc = e2;
                                        if (bno.a) {
                                            String str2 = a;
                                            StringBuilder sb2 = new StringBuilder("main proxy run exception ");
                                            sb2.append(DebugLog.getPrintStackTraceString(exc));
                                            AMapLog.e(str2, sb2.toString());
                                        }
                                    }
                                }
                            } else {
                                wsVar = this;
                                commandResult5 = a2.a(xhVar2.a, next.f, next);
                            }
                            if (commandResult5 != null) {
                                if (xhVar2.d == 0) {
                                    xf xfVar3 = wsVar.b;
                                    String str3 = xhVar2.a;
                                    int i = next.f;
                                    xfVar3.e.a.queryBuilder(Command.class).where(Properties.a.eq(str3), Properties.f.le(Integer.valueOf(i))).buildDelete().executeDeleteWithoutDetachingEntities();
                                }
                                arrayList.add(next);
                                if (1 == commandResult5.i) {
                                    wsVar.b.a(commandResult5);
                                }
                            } else if (bno.a) {
                                String str4 = a;
                                StringBuilder sb3 = new StringBuilder("break cmd2=");
                                sb3.append(next.h);
                                sb3.append(":");
                                sb3.append(xhVar2.a);
                                sb3.append(":");
                                sb3.append(next.f);
                                AMapLog.d(str4, sb3.toString());
                            }
                            xf.c(xhVar2.a, String.valueOf(next.f));
                            wsVar2 = wsVar;
                            it = it3;
                        } else if (bno.a) {
                            String str5 = a;
                            StringBuilder sb4 = new StringBuilder("break cmd1=");
                            sb4.append(next.h);
                            sb4.append(":");
                            sb4.append(xhVar2.a);
                            sb4.append(":");
                            sb4.append(next.f);
                            AMapLog.d(str5, sb4.toString());
                        }
                    } else {
                        xf xfVar4 = this.b;
                        xh xhVar3 = xhVar;
                        String str6 = xhVar3.a;
                        long j = xhVar3.b;
                        long j2 = xhVar3.c;
                        int i2 = xhVar3.d;
                        long j3 = next.i;
                        int i3 = next.f;
                        StringBuilder sb5 = new StringBuilder();
                        xf xfVar5 = xfVar4;
                        sb5.append(next.k);
                        sb5.append(":");
                        sb5.append(System.currentTimeMillis());
                        xhVar2 = xhVar3;
                        CommandResult commandResult6 = new CommandResult(str6, j, j2, i2, j3, i3, 105, sb5.toString());
                        xfVar5.a(commandResult6);
                        arrayList.add(next);
                    }
                    it = it3;
                    wsVar2 = this;
                }
            }
        }
        ws wsVar3 = wsVar2;
        if (!arrayList.isEmpty()) {
            xhVar2.e.removeAll(arrayList);
            arrayList.clear();
        }
    }
}
