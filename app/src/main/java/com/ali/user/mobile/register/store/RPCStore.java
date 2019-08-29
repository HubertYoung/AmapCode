package com.ali.user.mobile.register.store;

public class RPCStore implements IStore {
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0328, code lost:
        r14.e = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0334, code lost:
        com.ali.user.mobile.log.AliUserLog.c("Reg_RPCStore", "finally");
        r15.dismissProgress();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x033e, code lost:
        throw r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x02bc, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x02bf, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
        com.ali.user.mobile.log.AliUserLog.b((java.lang.String) "Reg_RPCStore", r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x02d0, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:?, code lost:
        com.ali.user.mobile.log.AliUserLog.a((java.lang.String) "Reg_RPCStore", (java.lang.Throwable) r0);
        com.ali.user.mobile.log.LogAgent.a((java.lang.String) "UC-REG-20161230-01", (java.lang.String) "regRpc", r13.b, java.lang.String.valueOf(r0.getCode()), com.ali.user.mobile.register.model.State.a, (java.util.Map<java.lang.String, java.lang.String>) null);
        r13 = com.ali.user.mobile.register.router.RouterPages.a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x02fc, code lost:
        if ((r13.getActivity() instanceof com.ali.user.mobile.register.ui.RegPurePhoneActivity) == false) goto L_0x02fe;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0303, code lost:
        if (r0.isClientError() != false) goto L_0x0305;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0305, code lost:
        com.ali.user.mobile.log.AliUserLog.c(getClass().getSimpleName(), "rpcException.isClientError, show network guide");
        new android.os.Handler(android.os.Looper.getMainLooper()).post(new com.ali.user.mobile.register.store.RPCStore.AnonymousClass2(r12));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0325, code lost:
        r14.e = r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x02d0 A[ExcHandler: RpcException (r0v4 'e' com.alipay.inside.android.phone.mrpc.core.RpcException A[CUSTOM_DECLARE]), Splitter:B:11:0x0040] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.ali.user.mobile.register.model.State a(com.ali.user.mobile.register.model.SimpleRequest r13, com.ali.user.mobile.register.model.State r14, com.ali.user.mobile.base.BaseActivity r15) {
        /*
            r12 = this;
            r0 = 0
            if (r14 == 0) goto L_0x033f
            com.ali.user.mobile.register.Account r1 = r14.a()
            if (r1 == 0) goto L_0x033f
            if (r13 != 0) goto L_0x000d
            goto L_0x033f
        L_0x000d:
            r14.c = r0
            r14.e = r0
            java.lang.String r0 = "Reg_RPCStore"
            java.lang.String r1 = "do rpc "
            java.lang.String r2 = java.lang.String.valueOf(r13)
            java.lang.String r1 = r1.concat(r2)
            com.ali.user.mobile.log.AliUserLog.c(r0, r1)
            boolean r0 = r13.g
            if (r0 == 0) goto L_0x0029
            java.lang.String r0 = ""
            r15.showProgress(r0)
        L_0x0029:
            com.ali.user.mobile.register.router.IRouterHandler r0 = com.ali.user.mobile.register.router.RouterPages.a()
            if (r0 == 0) goto L_0x0040
            android.os.Handler r1 = new android.os.Handler
            android.os.Looper r2 = android.os.Looper.getMainLooper()
            r1.<init>(r2)
            com.ali.user.mobile.register.store.RPCStore$1 r2 = new com.ali.user.mobile.register.store.RPCStore$1
            r2.<init>(r0)
            r1.post(r2)
        L_0x0040:
            com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.UnifyRegisterAllReqPb r0 = new com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.UnifyRegisterAllReqPb     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.<init>()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = "ALIPAY"
            r0.appId = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = "android"
            r0.clientType = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = "2.0.0.0"
            r0.sdkVersion = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = "android"
            r0.systemType = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.ali.user.mobile.info.AppInfo r1 = com.ali.user.mobile.info.AppInfo.getInstance()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            android.content.Context r2 = com.ali.user.mobile.AliUserInit.b()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = r1.getAppKey(r2)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.appKey = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = r1.getProductId()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.productId = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = r1.getProductVersion()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.productVersion = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = r1.getChannel()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.channel = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = r1.getDeviceKeySet()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.devKeySet = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.alipay.apmobilesecuritysdk.face.APSecuritySdk$TokenResult r2 = r1.getTokenResult()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            if (r2 == 0) goto L_0x0089
            java.lang.String r3 = r2.umidToken     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.umidToken = r3     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = r2.apdid     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.apdId = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
        L_0x0089:
            com.ali.user.mobile.info.TidInfo r1 = r1.getTidInfo()     // Catch:{ Exception -> 0x0096 }
            if (r1 == 0) goto L_0x009c
            java.lang.String r1 = r1.a()     // Catch:{ Exception -> 0x0096 }
            r0.tid = r1     // Catch:{ Exception -> 0x0096 }
            goto L_0x009c
        L_0x0096:
            r1 = move-exception
            java.lang.String r2 = "Reg_RPCStore"
            com.ali.user.mobile.log.AliUserLog.a(r2, r1)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
        L_0x009c:
            com.alipay.android.phone.inside.common.info.DeviceInfo r1 = com.alipay.android.phone.inside.common.info.DeviceInfo.a()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.ali.user.mobile.info.DeviceInfo.b()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = com.alipay.android.phone.inside.common.info.DeviceInfo.o()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.userAgent = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = com.ali.user.mobile.info.DeviceInfo.g()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.utdid = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            int r2 = com.ali.user.mobile.info.DeviceInfo.e()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.screenWidth = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            int r2 = com.ali.user.mobile.info.DeviceInfo.f()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.screenHigh = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = r1.i()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.mobileBrand = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = r1.j()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.mobileModel = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = r1.k()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.systemVersion = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            boolean r2 = com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig.i()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.isPrisonBreak = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = r1.p()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.IMEI = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = r1.q()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.IMSI = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.util.List<com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.ExternKVParam> r1 = r0.externParams     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            if (r1 != 0) goto L_0x00f6
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r1.<init>()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.externParams = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
        L_0x00f6:
            com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.ExternKVParam r1 = new com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.ExternKVParam     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r1.<init>()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = "isTrojan"
            r1.key = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            boolean r2 = com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig.j()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r1.value = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.ExternKVParam r2 = new com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.ExternKVParam     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r2.<init>()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r3 = "currentOperateMobile"
            r2.key = r3     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r3 = com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig.h()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r2.value = r3     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.util.List<com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.ExternKVParam> r3 = r0.externParams     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r3.add(r1)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.util.List<com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.ExternKVParam> r1 = r0.externParams     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r1.add(r2)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            android.content.Context r1 = com.ali.user.mobile.AliUserInit.b()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.ali.user.mobile.info.NetWorkInfo r1 = com.ali.user.mobile.info.NetWorkInfo.a(r1)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = r1.b()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.accessPoint = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r2 = r1.a()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.wifiMac = r2     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = r1.b()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.wifiNodeName = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.alipay.android.phone.inside.commonbiz.ids.model.LocationInfo r1 = com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig.c()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0147
            java.lang.String r1 = ""
            goto L_0x0163
        L_0x0147:
            java.lang.String r4 = "%s;%s;%s"
            r5 = 3
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r6 = r1.d()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r5[r3] = r6     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r6 = r1.e()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r5[r2] = r6     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r6 = 2
            java.lang.String r1 = r1.a()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r5[r6] = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = java.lang.String.format(r4, r5)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
        L_0x0163:
            r0.clientPostion = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.alipay.android.phone.inside.commonbiz.ids.model.TelephoneInfo r1 = com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig.e()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            if (r1 != 0) goto L_0x0174
            java.lang.String r1 = "Reg_RPCStore"
            java.lang.String r4 = "telephone is null"
            com.ali.user.mobile.log.AliUserLog.c(r1, r4)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            goto L_0x01bc
        L_0x0174:
            java.util.List r4 = r1.a()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.alipay.android.phone.inside.commonbiz.ids.model.CdmaModel r1 = r1.b()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            if (r4 == 0) goto L_0x01ae
            boolean r5 = r4.isEmpty()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            if (r5 != 0) goto L_0x01ae
            java.lang.Object r5 = r4.get(r3)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            if (r5 != 0) goto L_0x018d
            java.lang.String r5 = ""
            goto L_0x0197
        L_0x018d:
            java.lang.Object r5 = r4.get(r3)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.alipay.android.phone.inside.commonbiz.ids.model.GsmModel r5 = (com.alipay.android.phone.inside.commonbiz.ids.model.GsmModel) r5     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r5 = r5.d()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
        L_0x0197:
            r0.lacId = r5     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.Object r5 = r4.get(r3)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            if (r5 != 0) goto L_0x01a2
            java.lang.String r4 = ""
            goto L_0x01ac
        L_0x01a2:
            java.lang.Object r4 = r4.get(r3)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.alipay.android.phone.inside.commonbiz.ids.model.GsmModel r4 = (com.alipay.android.phone.inside.commonbiz.ids.model.GsmModel) r4     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r4 = r4.c()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
        L_0x01ac:
            r0.cellId = r4     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
        L_0x01ae:
            if (r1 == 0) goto L_0x01bc
            java.lang.String r4 = r1.a()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.lacId = r4     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = r1.a()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.cellId = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
        L_0x01bc:
            java.lang.String r1 = r13.b     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r14.d = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.ali.user.mobile.register.Account r1 = r14.a()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = r1.getAccountForRPC()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.longonId = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.ali.user.mobile.register.Account r1 = r14.a()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = r1.getAreaCodeForRPC()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.countryCode = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = r14.f     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.token = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = r14.d     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.registSceneCode = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = r13.f     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.securityId = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = "Reg_RPCStore"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r5 = "securityId "
            r4.<init>(r5)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r5 = r13.f     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r4.append(r5)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r4 = r4.toString()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.ali.user.mobile.log.AliUserLog.c(r1, r4)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = "registerPreVerify"
            java.lang.String r4 = r13.b     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            boolean r1 = r1.equals(r4)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            if (r1 == 0) goto L_0x022c
            java.lang.String r1 = r13.d     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.rdsInfo = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.util.List<com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.ExternKVParam> r1 = r0.externParams     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            if (r1 != 0) goto L_0x020f
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r1.<init>()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.externParams = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
        L_0x020f:
            com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.ExternKVParam r1 = new com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.ExternKVParam     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r1.<init>()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r4 = "edgeData"
            r1.key = r4     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r4 = "register"
            java.lang.String r5 = ""
            java.lang.String r6 = r0.longonId     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r7 = ""
            java.lang.String r4 = com.ali.user.mobile.util.EdgeUtil.a(r4, r5, r6, r7)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r1.value = r4     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.util.List<com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.ExternKVParam> r4 = r0.externParams     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r4.add(r1)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            goto L_0x025a
        L_0x022c:
            java.lang.String r1 = "verifySms"
            java.lang.String r4 = r13.b     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            boolean r1 = r1.equals(r4)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            if (r1 == 0) goto L_0x023c
            java.lang.String r1 = r13.c     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.smsCode = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            goto L_0x025a
        L_0x023c:
            java.lang.String r1 = "sendSms"
            java.lang.String r4 = r13.b     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            boolean r1 = r1.equals(r4)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            if (r1 != 0) goto L_0x025a
            java.lang.String r1 = "setLoginPassword"
            java.lang.String r4 = r13.b     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            boolean r1 = r1.equals(r4)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            if (r1 == 0) goto L_0x025a
            java.lang.String r1 = r13.e     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = com.ali.user.mobile.util.RsaUtils.a(r1)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r0.queryPassword = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
        L_0x025a:
            com.alipay.android.phone.inside.commonservice.CommonServiceFactory r1 = com.alipay.android.phone.inside.commonservice.CommonServiceFactory.getInstance()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.alipay.android.phone.inside.commonservice.RpcService r1 = r1.getRpcService()     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.Class<com.alipay.mobileapp.biz.rpc.unifyregister.UserUnifyRegisterAllFacade> r4 = com.alipay.mobileapp.biz.rpc.unifyregister.UserUnifyRegisterAllFacade.class
            java.lang.Object r1 = r1.getRpcProxy(r4)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.alipay.mobileapp.biz.rpc.unifyregister.UserUnifyRegisterAllFacade r1 = (com.alipay.mobileapp.biz.rpc.unifyregister.UserUnifyRegisterAllFacade) r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.UnifyRegisterAllResPb r1 = r1.mobileUnifyRegister(r0)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r14.c = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.UnifyRegisterAllResPb r1 = r14.c     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r4 = "register"
            java.lang.String r5 = r0.longonId     // Catch:{ Throwable -> 0x028e, RpcException -> 0x02d0 }
            java.lang.String r6 = ""
            java.lang.String r7 = ""
            if (r1 == 0) goto L_0x0289
            r8 = 200(0xc8, double:9.9E-322)
            java.lang.Long r1 = r1.resultStatus     // Catch:{ Throwable -> 0x028e, RpcException -> 0x02d0 }
            long r10 = r1.longValue()     // Catch:{ Throwable -> 0x028e, RpcException -> 0x02d0 }
            int r1 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r1 != 0) goto L_0x0289
            goto L_0x028a
        L_0x0289:
            r2 = 0
        L_0x028a:
            com.ali.user.mobile.util.EdgeUtil.a(r4, r5, r6, r7, r2)     // Catch:{ Throwable -> 0x028e, RpcException -> 0x02d0 }
            goto L_0x0296
        L_0x028e:
            r1 = move-exception
            java.lang.String r2 = "Reg_RPCStore"
            java.lang.String r3 = "processEdgeAfterRegister"
            com.ali.user.mobile.log.AliUserLog.b(r2, r3, r1)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
        L_0x0296:
            java.lang.String r1 = ""
            com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.UnifyRegisterAllResPb r2 = r14.c     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            if (r2 == 0) goto L_0x02ae
            java.lang.String r1 = r0.registSceneCode     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r14.d = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.UnifyRegisterAllResPb r1 = r14.c     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = r1.token     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r14.f = r1     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.UnifyRegisterAllResPb r1 = r14.c     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.Long r1 = r1.resultStatus     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
        L_0x02ae:
            r4 = r1
            java.lang.String r1 = "UC-REG-20161230-01"
            java.lang.String r2 = "regRpc"
            java.lang.String r3 = r0.registSceneCode     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            java.lang.String r5 = com.ali.user.mobile.register.model.State.a     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            r6 = 0
            com.ali.user.mobile.log.LogAgent.a(r1, r2, r3, r4, r5, r6)     // Catch:{ RpcException -> 0x02d0, Throwable -> 0x02bf }
            goto L_0x02c5
        L_0x02bc:
            r13 = move-exception
            goto L_0x0334
        L_0x02bf:
            r13 = move-exception
            java.lang.String r0 = "Reg_RPCStore"
            com.ali.user.mobile.log.AliUserLog.b(r0, r13)     // Catch:{ all -> 0x02bc }
        L_0x02c5:
            java.lang.String r13 = "Reg_RPCStore"
            java.lang.String r0 = "finally"
            com.ali.user.mobile.log.AliUserLog.c(r13, r0)
            r15.dismissProgress()
            goto L_0x032b
        L_0x02d0:
            r0 = move-exception
            java.lang.String r1 = "Reg_RPCStore"
            com.ali.user.mobile.log.AliUserLog.a(r1, r0)     // Catch:{ all -> 0x02bc }
            java.lang.String r2 = "UC-REG-20161230-01"
            java.lang.String r3 = "regRpc"
            java.lang.String r4 = r13.b     // Catch:{ all -> 0x02bc }
            int r13 = r0.getCode()     // Catch:{ all -> 0x02bc }
            java.lang.String r5 = java.lang.String.valueOf(r13)     // Catch:{ all -> 0x02bc }
            java.lang.String r6 = com.ali.user.mobile.register.model.State.a     // Catch:{ all -> 0x02bc }
            r7 = 0
            com.ali.user.mobile.log.LogAgent.a(r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x02bc }
            com.ali.user.mobile.register.router.IRouterHandler r13 = com.ali.user.mobile.register.router.RouterPages.a()     // Catch:{ all -> 0x02bc }
            if (r13 == 0) goto L_0x0328
            com.ali.user.mobile.base.BaseActivity r1 = r13.getActivity()     // Catch:{ all -> 0x02bc }
            if (r1 == 0) goto L_0x0328
            com.ali.user.mobile.base.BaseActivity r1 = r13.getActivity()     // Catch:{ all -> 0x02bc }
            boolean r1 = r1 instanceof com.ali.user.mobile.register.ui.RegPurePhoneActivity     // Catch:{ all -> 0x02bc }
            if (r1 != 0) goto L_0x02ff
            goto L_0x0328
        L_0x02ff:
            boolean r1 = r0.isClientError()     // Catch:{ all -> 0x02bc }
            if (r1 == 0) goto L_0x0325
            java.lang.Class r0 = r12.getClass()     // Catch:{ all -> 0x02bc }
            java.lang.String r0 = r0.getSimpleName()     // Catch:{ all -> 0x02bc }
            java.lang.String r1 = "rpcException.isClientError, show network guide"
            com.ali.user.mobile.log.AliUserLog.c(r0, r1)     // Catch:{ all -> 0x02bc }
            android.os.Handler r0 = new android.os.Handler     // Catch:{ all -> 0x02bc }
            android.os.Looper r1 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x02bc }
            r0.<init>(r1)     // Catch:{ all -> 0x02bc }
            com.ali.user.mobile.register.store.RPCStore$2 r1 = new com.ali.user.mobile.register.store.RPCStore$2     // Catch:{ all -> 0x02bc }
            r1.<init>(r13)     // Catch:{ all -> 0x02bc }
            r0.post(r1)     // Catch:{ all -> 0x02bc }
            goto L_0x02c5
        L_0x0325:
            r14.e = r0     // Catch:{ all -> 0x02bc }
            goto L_0x02c5
        L_0x0328:
            r14.e = r0     // Catch:{ all -> 0x02bc }
            goto L_0x02c5
        L_0x032b:
            java.lang.String r13 = "Reg_RPCStore"
            java.lang.String r15 = "return"
            com.ali.user.mobile.log.AliUserLog.c(r13, r15)
            return r14
        L_0x0334:
            java.lang.String r14 = "Reg_RPCStore"
            java.lang.String r0 = "finally"
            com.ali.user.mobile.log.AliUserLog.c(r14, r0)
            r15.dismissProgress()
            throw r13
        L_0x033f:
            java.lang.String r13 = "Reg_RPCStore"
            java.lang.String r14 = "null state/account/simpleRequest"
            com.ali.user.mobile.log.AliUserLog.d(r13, r14)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.register.store.RPCStore.a(com.ali.user.mobile.register.model.SimpleRequest, com.ali.user.mobile.register.model.State, com.ali.user.mobile.base.BaseActivity):com.ali.user.mobile.register.model.State");
    }
}
