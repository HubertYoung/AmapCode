package defpackage;

/* renamed from: aim reason: default package */
/* compiled from: ProtocolProcessManager */
public final class aim {
    private static aim b;
    public boolean a = false;

    private aim() {
    }

    public static synchronized aim a() {
        aim aim;
        synchronized (aim.class) {
            try {
                if (b == null) {
                    b = new aim();
                }
                aim = b;
            }
        }
        return aim;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0123, code lost:
        r0 = (com.amap.bundle.voiceservice.scene.SceneBean) r1.getValue();
        r6 = r0.mBlockBool;
        r10 = (java.lang.Class) r0.mPair.first;
        r11 = (java.lang.String) r0.mPair.second;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(int r16, com.autonavi.data.service.IResultCallBack r17, java.lang.String r18, java.lang.String r19, java.lang.String r20) {
        /*
            r7 = r16
            boolean r0 = defpackage.ait.a()
            r8 = 0
            if (r0 != 0) goto L_0x0021
            r0 = 9001(0x2329, float:1.2613E-41)
            java.lang.String r5 = defpackage.aid.a(r0, r8)
            r0 = r7
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            defpackage.aiu.a(r0, r1, r2, r3, r4, r5)
            java.lang.String r0 = "主图未进入就绪状态"
            defpackage.aiq.b(r7, r0)
            return
        L_0x0021:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            boolean r0 = defpackage.ahp.a(r0)
            if (r0 != 0) goto L_0x0043
            r0 = 9003(0x232b, float:1.2616E-41)
            java.lang.String r5 = defpackage.aid.a(r0, r8)
            r0 = r7
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            defpackage.aiu.a(r0, r1, r2, r3, r4, r5)
            java.lang.String r0 = "高德地图不在前台"
            defpackage.aiq.b(r7, r0)
            return
        L_0x0043:
            aip r0 = defpackage.aip.a()
            java.util.Queue<com.amap.bundle.voiceservice.task.VoiceTaskBean> r0 = r0.a
            java.util.Iterator r0 = r0.iterator()
        L_0x004d:
            boolean r1 = r0.hasNext()
            r2 = 0
            if (r1 == 0) goto L_0x0062
            java.lang.Object r1 = r0.next()
            com.amap.bundle.voiceservice.task.VoiceTaskBean r1 = (com.amap.bundle.voiceservice.task.VoiceTaskBean) r1
            boolean r1 = r1.isBlock()
            if (r1 == 0) goto L_0x004d
            r0 = 1
            goto L_0x0063
        L_0x0062:
            r0 = 0
        L_0x0063:
            if (r0 == 0) goto L_0x007d
            r0 = 9002(0x232a, float:1.2614E-41)
            java.lang.String r5 = defpackage.aid.a(r0, r8)
            r0 = r7
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            defpackage.aiu.a(r0, r1, r2, r3, r4, r5)
            java.lang.String r0 = "任务阻塞"
            defpackage.aiq.b(r7, r0)
            return
        L_0x007d:
            java.util.Map r0 = com.amap.bundle.voiceservice.scene.VoiceMethodTable.get(r19)
            r9 = 9004(0x232c, float:1.2617E-41)
            if (r0 == 0) goto L_0x01a3
            int r1 = r0.size()
            if (r1 != 0) goto L_0x008d
            goto L_0x01a3
        L_0x008d:
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
            bid r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r3 = 4611686018427387904(0x4000000000000000, double:2.0)
            r5 = 1152921504606846976(0x1000000000000000, double:1.2882297539194267E-231)
            r10 = 0
            if (r1 == 0) goto L_0x00ef
            boolean r12 = r1 instanceof com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder
            if (r12 == 0) goto L_0x00ef
            com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder r1 = (com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder) r1
            long r12 = r1.getScene()
            r14 = 536870912(0x20000000, double:2.652494739E-315)
            long r14 = r14 & r12
            int r1 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r1 > 0) goto L_0x00ed
            r14 = 1073741824(0x40000000, double:5.304989477E-315)
            long r14 = r14 & r12
            int r1 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r1 > 0) goto L_0x00ed
            r14 = 2147483648(0x80000000, double:1.0609978955E-314)
            long r14 = r14 & r12
            int r1 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r1 > 0) goto L_0x00ed
            r14 = 4294967296(0x100000000, double:2.121995791E-314)
            long r14 = r14 & r12
            int r1 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r1 > 0) goto L_0x00ed
            r14 = 8589934592(0x200000000, double:4.243991582E-314)
            long r14 = r14 & r12
            int r1 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r1 > 0) goto L_0x00ed
            r14 = 17179869184(0x400000000, double:8.4879831639E-314)
            long r14 = r14 & r12
            int r1 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r1 > 0) goto L_0x00ed
            r14 = 4503599627370496(0x10000000000000, double:2.2250738585072014E-308)
            long r14 = r14 & r12
            int r1 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r1 <= 0) goto L_0x00eb
            goto L_0x00ed
        L_0x00eb:
            long r12 = r12 | r3
            goto L_0x00f0
        L_0x00ed:
            long r12 = r12 | r5
            goto L_0x00f0
        L_0x00ef:
            r12 = r10
        L_0x00f0:
            long r14 = r12 & r5
            int r1 = (r14 > r5 ? 1 : (r14 == r5 ? 0 : -1))
            if (r1 == 0) goto L_0x00f7
            long r12 = r12 | r3
        L_0x00f7:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x013b
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r3 = r1.getKey()
            java.lang.Long r3 = (java.lang.Long) r3
            long r3 = r3.longValue()
            r5 = -9223372036854775808
            long r3 = r3 & r5
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x0123
            java.lang.Object r3 = r1.getKey()
            java.lang.Long r3 = (java.lang.Long) r3
            long r3 = r3.longValue()
            long r3 = r3 & r12
            int r3 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r3 <= 0) goto L_0x00f7
        L_0x0123:
            java.lang.Object r0 = r1.getValue()
            com.amap.bundle.voiceservice.scene.SceneBean r0 = (com.amap.bundle.voiceservice.scene.SceneBean) r0
            android.util.Pair<java.lang.Class, java.lang.String> r1 = r0.mPair
            java.lang.Object r1 = r1.first
            java.lang.Class r1 = (java.lang.Class) r1
            android.util.Pair<java.lang.Class, java.lang.String> r2 = r0.mPair
            java.lang.Object r2 = r2.second
            java.lang.String r2 = (java.lang.String) r2
            boolean r0 = r0.mBlockBool
            r6 = r0
            r10 = r1
            r11 = r2
            goto L_0x013e
        L_0x013b:
            r10 = r8
            r11 = r10
            r6 = 0
        L_0x013e:
            if (r10 == 0) goto L_0x018d
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 != 0) goto L_0x018d
            com.amap.bundle.voiceservice.task.VoiceTaskBean r12 = new com.amap.bundle.voiceservice.task.VoiceTaskBean
            r0 = r12
            r1 = r18
            r2 = r7
            r3 = r19
            r4 = r20
            r5 = r17
            r0.<init>(r1, r2, r3, r4, r5, r6)
            aip r0 = defpackage.aip.a()
            int r1 = r12.getToken()
            java.lang.String r2 = "token="
            java.lang.String r1 = java.lang.String.valueOf(r1)
            java.lang.String r1 = r2.concat(r1)
            java.lang.String r2 = "AMapService"
            com.amap.bundle.logs.AMapLog.d(r2, r1)
            java.lang.String r2 = "logAddTask"
            defpackage.aiq.a(r2, r1)
            java.util.Queue<com.amap.bundle.voiceservice.task.VoiceTaskBean> r0 = r0.a
            r0.add(r12)
            boolean r0 = defpackage.ain.a(r7, r10, r11, r4)
            if (r0 != 0) goto L_0x018c
            aip r0 = defpackage.aip.a()
            java.lang.String r1 = defpackage.aid.a(r9, r8)
            r0.a(r7, r1)
            java.lang.String r0 = "当前客户端版本，该指令不支持---分发失败"
            defpackage.aiq.c(r7, r0)
        L_0x018c:
            return
        L_0x018d:
            r4 = r20
            java.lang.String r5 = defpackage.aid.a(r9, r8)
            r0 = r7
            r1 = r17
            r2 = r18
            r3 = r19
            defpackage.aiu.a(r0, r1, r2, r3, r4, r5)
            java.lang.String r0 = "当前界面不支持该指令"
            defpackage.aiq.c(r7, r0)
            return
        L_0x01a3:
            r4 = r20
            java.lang.String r5 = defpackage.aid.a(r9, r8)
            r0 = r7
            r1 = r17
            r2 = r18
            r3 = r19
            defpackage.aiu.a(r0, r1, r2, r3, r4, r5)
            java.lang.String r0 = "接口不存在"
            defpackage.aiq.c(r7, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aim.a(int, com.autonavi.data.service.IResultCallBack, java.lang.String, java.lang.String, java.lang.String):void");
    }
}
