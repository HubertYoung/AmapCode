package com.ali.auth.third.mtop.rpc;

import com.ali.auth.third.core.model.LoginReturnData;
import com.ali.auth.third.core.model.RpcRequest;
import com.ali.auth.third.core.model.RpcResponse;

public class a {
    private static a a;

    /* JADX WARNING: type inference failed for: r5v0, types: [java.lang.Class<V>, java.lang.Class] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <V> com.ali.auth.third.core.model.RpcResponse<V> a(mtopsdk.mtop.domain.MtopResponse r4, java.lang.Class<V> r5) {
        /*
            r3 = this;
            r0 = 0
            if (r4 == 0) goto L_0x0066
            boolean r1 = r4.isApiSuccess()
            if (r1 != 0) goto L_0x000a
            goto L_0x0066
        L_0x000a:
            byte[] r4 = r4.getBytedata()     // Catch:{ Exception -> 0x0061 }
            if (r4 == 0) goto L_0x0060
            java.lang.String r1 = new java.lang.String     // Catch:{ Exception -> 0x0061 }
            r1.<init>(r4)     // Catch:{ Exception -> 0x0061 }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x0061 }
            r4.<init>(r1)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r1 = "data"
            org.json.JSONObject r4 = r4.optJSONObject(r1)     // Catch:{ Exception -> 0x0061 }
            com.ali.auth.third.core.model.RpcResponse r1 = new com.ali.auth.third.core.model.RpcResponse     // Catch:{ Exception -> 0x0061 }
            r1.<init>()     // Catch:{ Exception -> 0x0061 }
            java.lang.String r2 = "code"
            int r2 = r4.optInt(r2)     // Catch:{ Exception -> 0x0061 }
            r1.code = r2     // Catch:{ Exception -> 0x0061 }
            java.lang.String r2 = "codeGroup"
            java.lang.String r2 = r4.optString(r2)     // Catch:{ Exception -> 0x0061 }
            r1.codeGroup = r2     // Catch:{ Exception -> 0x0061 }
            java.lang.String r2 = "message"
            java.lang.String r2 = r4.optString(r2)     // Catch:{ Exception -> 0x0061 }
            r1.message = r2     // Catch:{ Exception -> 0x0061 }
            java.lang.String r2 = "actionType"
            java.lang.String r2 = r4.optString(r2)     // Catch:{ Exception -> 0x0061 }
            r1.actionType = r2     // Catch:{ Exception -> 0x0061 }
            java.lang.String r2 = "returnValue"
            java.lang.String r2 = r4.optString(r2)     // Catch:{ Exception -> 0x0061 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0061 }
            if (r2 != 0) goto L_0x005f
            java.lang.String r2 = "returnValue"
            java.lang.String r4 = r4.optString(r2)     // Catch:{ Exception -> 0x0061 }
            java.lang.Object r4 = com.ali.auth.third.core.util.JSONUtils.parseStringValue(r4, r5)     // Catch:{ Exception -> 0x0061 }
            r1.returnValue = r4     // Catch:{ Exception -> 0x0061 }
        L_0x005f:
            return r1
        L_0x0060:
            return r0
        L_0x0061:
            r4 = move-exception
            r4.printStackTrace()
            return r0
        L_0x0066:
            if (r4 == 0) goto L_0x0179
            boolean r1 = r4.isNetworkError()
            if (r1 == 0) goto L_0x0081
            com.ali.auth.third.core.rpc.protocol.RpcException r4 = new com.ali.auth.third.core.rpc.protocol.RpcException
            r5 = 7
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            android.content.Context r0 = com.ali.auth.third.core.context.KernelContext.context
            java.lang.String r1 = "aliusersdk_network_error"
            java.lang.String r0 = com.ali.auth.third.core.util.ResourceUtils.getString(r0, r1)
            r4.<init>(r5, r0)
            throw r4
        L_0x0081:
            boolean r1 = r4.isApiLockedResult()
            if (r1 == 0) goto L_0x009b
            com.ali.auth.third.core.rpc.protocol.RpcException r4 = new com.ali.auth.third.core.rpc.protocol.RpcException
            r5 = 400(0x190, float:5.6E-43)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            android.content.Context r0 = com.ali.auth.third.core.context.KernelContext.context
            java.lang.String r1 = "aliusersdk_network_error"
            java.lang.String r0 = com.ali.auth.third.core.util.ResourceUtils.getString(r0, r1)
            r4.<init>(r5, r0)
            throw r4
        L_0x009b:
            boolean r1 = r4.is41XResult()
            if (r1 == 0) goto L_0x00b5
            com.ali.auth.third.core.rpc.protocol.RpcException r4 = new com.ali.auth.third.core.rpc.protocol.RpcException
            r5 = 401(0x191, float:5.62E-43)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            android.content.Context r0 = com.ali.auth.third.core.context.KernelContext.context
            java.lang.String r1 = "aliusersdk_network_error"
            java.lang.String r0 = com.ali.auth.third.core.util.ResourceUtils.getString(r0, r1)
            r4.<init>(r5, r0)
            throw r4
        L_0x00b5:
            boolean r1 = r4.isExpiredRequest()
            if (r1 == 0) goto L_0x00cf
            com.ali.auth.third.core.rpc.protocol.RpcException r4 = new com.ali.auth.third.core.rpc.protocol.RpcException
            r5 = 402(0x192, float:5.63E-43)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            android.content.Context r0 = com.ali.auth.third.core.context.KernelContext.context
            java.lang.String r1 = "aliusersdk_network_error"
            java.lang.String r0 = com.ali.auth.third.core.util.ResourceUtils.getString(r0, r1)
            r4.<init>(r5, r0)
            throw r4
        L_0x00cf:
            boolean r1 = r4.isIllegelSign()
            if (r1 == 0) goto L_0x00e9
            com.ali.auth.third.core.rpc.protocol.RpcException r4 = new com.ali.auth.third.core.rpc.protocol.RpcException
            r5 = 403(0x193, float:5.65E-43)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            android.content.Context r0 = com.ali.auth.third.core.context.KernelContext.context
            java.lang.String r1 = "aliusersdk_network_error"
            java.lang.String r0 = com.ali.auth.third.core.util.ResourceUtils.getString(r0, r1)
            r4.<init>(r5, r0)
            throw r4
        L_0x00e9:
            boolean r1 = r4.isSystemError()
            if (r1 == 0) goto L_0x0103
            com.ali.auth.third.core.rpc.protocol.RpcException r4 = new com.ali.auth.third.core.rpc.protocol.RpcException
            r5 = 406(0x196, float:5.69E-43)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            android.content.Context r0 = com.ali.auth.third.core.context.KernelContext.context
            java.lang.String r1 = "aliusersdk_network_error"
            java.lang.String r0 = com.ali.auth.third.core.util.ResourceUtils.getString(r0, r1)
            r4.<init>(r5, r0)
            throw r4
        L_0x0103:
            boolean r1 = r4.isSessionInvalid()
            if (r1 == 0) goto L_0x011d
            com.ali.auth.third.core.rpc.protocol.RpcException r4 = new com.ali.auth.third.core.rpc.protocol.RpcException
            r5 = 407(0x197, float:5.7E-43)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            android.content.Context r0 = com.ali.auth.third.core.context.KernelContext.context
            java.lang.String r1 = "aliusersdk_session_error"
            java.lang.String r0 = com.ali.auth.third.core.util.ResourceUtils.getString(r0, r1)
            r4.<init>(r5, r0)
            throw r4
        L_0x011d:
            com.ali.auth.third.core.model.RpcResponse r1 = new com.ali.auth.third.core.model.RpcResponse     // Catch:{ Exception -> 0x0175 }
            r1.<init>()     // Catch:{ Exception -> 0x0175 }
            byte[] r4 = r4.getBytedata()     // Catch:{ Exception -> 0x0175 }
            if (r4 == 0) goto L_0x0174
            java.lang.String r2 = new java.lang.String     // Catch:{ Exception -> 0x0175 }
            r2.<init>(r4)     // Catch:{ Exception -> 0x0175 }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x0175 }
            r4.<init>(r2)     // Catch:{ Exception -> 0x0175 }
            java.lang.String r2 = "data"
            org.json.JSONObject r4 = r4.optJSONObject(r2)     // Catch:{ Exception -> 0x0175 }
            if (r4 == 0) goto L_0x0174
            java.lang.String r2 = "message"
            java.lang.String r2 = r4.optString(r2)     // Catch:{ Exception -> 0x0175 }
            r1.message = r2     // Catch:{ Exception -> 0x0175 }
            java.lang.String r2 = "actionType"
            java.lang.String r2 = r4.optString(r2)     // Catch:{ Exception -> 0x0175 }
            r1.actionType = r2     // Catch:{ Exception -> 0x0175 }
            java.lang.String r2 = "code"
            int r2 = r4.optInt(r2)     // Catch:{ Exception -> 0x0175 }
            r1.code = r2     // Catch:{ Exception -> 0x0175 }
            java.lang.String r2 = "codeGroup"
            java.lang.String r2 = r4.optString(r2)     // Catch:{ Exception -> 0x0175 }
            r1.codeGroup = r2     // Catch:{ Exception -> 0x0175 }
            java.lang.String r2 = "returnValue"
            java.lang.String r2 = r4.optString(r2)     // Catch:{ Exception -> 0x0175 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0175 }
            if (r2 != 0) goto L_0x0174
            java.lang.String r2 = "returnValue"
            java.lang.String r4 = r4.optString(r2)     // Catch:{ Exception -> 0x0175 }
            java.lang.Object r4 = com.alibaba.fastjson.JSON.parseObject(r4, r5)     // Catch:{ Exception -> 0x0175 }
            r1.returnValue = r4     // Catch:{ Exception -> 0x0175 }
        L_0x0174:
            return r1
        L_0x0175:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0179:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.mtop.rpc.a.a(mtopsdk.mtop.domain.MtopResponse, java.lang.Class):com.ali.auth.third.core.model.RpcResponse");
    }

    public static synchronized a a() {
        a aVar;
        synchronized (a.class) {
            if (a == null) {
                a = new a();
            }
            aVar = a;
        }
        return aVar;
    }

    public <V> RpcResponse<V> a(RpcRequest rpcRequest, Class<V> cls) {
        return a(rpcRequest, cls, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x009d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <V> com.ali.auth.third.core.model.RpcResponse<V> a(com.ali.auth.third.core.model.RpcRequest r8, java.lang.Class<V> r9, java.lang.String r10) {
        /*
            r7 = this;
            r0 = 0
            mtopsdk.mtop.domain.MtopRequest r1 = new mtopsdk.mtop.domain.MtopRequest     // Catch:{ Exception -> 0x0089 }
            r1.<init>()     // Catch:{ Exception -> 0x0089 }
            java.lang.String r2 = r8.target     // Catch:{ Exception -> 0x0089 }
            r1.setApiName(r2)     // Catch:{ Exception -> 0x0089 }
            java.lang.String r2 = r8.version     // Catch:{ Exception -> 0x0089 }
            r1.setVersion(r2)     // Catch:{ Exception -> 0x0089 }
            r2 = 0
            r1.setNeedEcode(r2)     // Catch:{ Exception -> 0x0089 }
            r1.setNeedSession(r2)     // Catch:{ Exception -> 0x0089 }
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x0089 }
            r3.<init>()     // Catch:{ Exception -> 0x0089 }
        L_0x001c:
            java.util.ArrayList<java.lang.String> r4 = r8.paramNames     // Catch:{ Exception -> 0x0089 }
            int r4 = r4.size()     // Catch:{ Exception -> 0x0089 }
            if (r2 >= r4) goto L_0x003c
            java.util.ArrayList<java.lang.String> r4 = r8.paramNames     // Catch:{ Exception -> 0x0089 }
            java.lang.Object r4 = r4.get(r2)     // Catch:{ Exception -> 0x0089 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x0089 }
            java.util.ArrayList<java.lang.Object> r5 = r8.paramValues     // Catch:{ Exception -> 0x0089 }
            java.lang.Object r5 = r5.get(r2)     // Catch:{ Exception -> 0x0089 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0089 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0089 }
            int r2 = r2 + 1
            goto L_0x001c
        L_0x003c:
            java.lang.String r8 = r3.toString()     // Catch:{ Exception -> 0x0089 }
            r1.setData(r8)     // Catch:{ Exception -> 0x0089 }
            java.lang.String r8 = "OPEN"
            android.content.Context r2 = com.ali.auth.third.core.context.KernelContext.context     // Catch:{ Exception -> 0x0089 }
            mtopsdk.mtop.intf.Mtop r8 = mtopsdk.mtop.intf.Mtop.a(r8, r2)     // Catch:{ Exception -> 0x0089 }
            java.lang.String r2 = com.ali.auth.third.core.MemberSDK.ttid     // Catch:{ Exception -> 0x0089 }
            ffg r8 = r8.a(r1, r2)     // Catch:{ Exception -> 0x0089 }
            mtopsdk.mtop.domain.MethodEnum r1 = mtopsdk.mtop.domain.MethodEnum.POST     // Catch:{ Exception -> 0x0089 }
            ffg r8 = r8.reqMethod(r1)     // Catch:{ Exception -> 0x0089 }
            r1 = 94
            ffg r8 = r8.setBizId(r1)     // Catch:{ Exception -> 0x0089 }
            r1 = 10000(0x2710, float:1.4013E-41)
            ffg r8 = r8.setConnectionTimeoutMilliSecond(r1)     // Catch:{ Exception -> 0x0089 }
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x0089 }
            if (r1 != 0) goto L_0x006c
            r8.setReqUserId(r10)     // Catch:{ Exception -> 0x0089 }
        L_0x006c:
            r10 = 1
            r8.retryTime(r10)     // Catch:{ Exception -> 0x0089 }
            mtopsdk.mtop.domain.MtopResponse r8 = r8.syncRequest()     // Catch:{ Exception -> 0x0089 }
            java.lang.String r10 = "login.MTOPWrapperImpl"
            java.lang.String r1 = "receive MtopResponse"
            java.lang.String r2 = java.lang.String.valueOf(r8)     // Catch:{ Exception -> 0x0084 }
            java.lang.String r1 = r1.concat(r2)     // Catch:{ Exception -> 0x0084 }
            com.ali.auth.third.core.trace.SDKLogger.d(r10, r1)     // Catch:{ Exception -> 0x0084 }
            goto L_0x0096
        L_0x0084:
            r10 = move-exception
            r6 = r10
            r10 = r8
            r8 = r6
            goto L_0x008b
        L_0x0089:
            r8 = move-exception
            r10 = r0
        L_0x008b:
            java.lang.String r1 = "login.MTOPWrapperImpl"
            java.lang.String r2 = "MtopResponse error"
            com.ali.auth.third.core.trace.SDKLogger.e(r1, r2, r8)
            r8.printStackTrace()
            r8 = r10
        L_0x0096:
            if (r8 == 0) goto L_0x009d
            com.ali.auth.third.core.model.RpcResponse r8 = r7.a(r8, r9)
            return r8
        L_0x009d:
            java.lang.String r8 = "login.MTOPWrapperImpl"
            java.lang.String r9 = "MtopResponse response=null"
            com.ali.auth.third.core.trace.SDKLogger.e(r8, r9)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.mtop.rpc.a.a(com.ali.auth.third.core.model.RpcRequest, java.lang.Class, java.lang.String):com.ali.auth.third.core.model.RpcResponse");
    }

    public String a(RpcRequest rpcRequest) {
        return a(rpcRequest, LoginReturnData.class).toString();
    }
}
