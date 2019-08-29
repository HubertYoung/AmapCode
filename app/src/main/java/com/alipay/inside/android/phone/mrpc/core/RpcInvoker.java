package com.alipay.inside.android.phone.mrpc.core;

import android.annotation.TargetApi;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.Serializer;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.json.JsonSerializerV2;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf.PBSerializer;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.util.RPCProtoDesc;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.util.SerializerFactory;
import com.alipay.inside.android.phone.mrpc.core.utils.GtsUtils;
import com.alipay.inside.android.phone.mrpc.core.utils.MiscUtils;
import com.alipay.inside.android.phone.mrpc.core.utils.RpcSignUtil;
import com.alipay.inside.android.phone.mrpc.core.utils.RpcSignUtil.SignData;
import com.alipay.inside.jsoncodec.JSONCodec;
import com.alipay.inside.mobile.framework.service.annotation.CheckLogin;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public class RpcInvoker {
    /* access modifiers changed from: private */
    public static final ThreadLocal<Map<String, Object>> EXT_PARAM = new ThreadLocal<>();
    private static final byte MODE_DEFAULT = 0;
    /* access modifiers changed from: private */
    public static final ThreadLocal<Object> RETURN_VALUE = new ThreadLocal<>();
    private static final String TAG = "RpcInvoker";
    private byte mMode = 0;
    private RpcFactory mRpcFactory;
    private AtomicInteger rpcSequence;
    private SerializerFactory serializerFactory;

    interface Handle {
        boolean handle(RpcInterceptor rpcInterceptor, Annotation annotation) throws RpcException;
    }

    public RpcInvoker(RpcFactory rpcFactory) {
        this.mRpcFactory = rpcFactory;
        this.rpcSequence = new AtomicInteger();
        this.serializerFactory = new SerializerFactory(this.mRpcFactory);
    }

    public RpcFactory getRpcFactory() {
        return this.mRpcFactory;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00f4 A[Catch:{ all -> 0x00dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00fa A[Catch:{ all -> 0x00dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x014c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0152  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x016c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object invoke(java.lang.Object r17, java.lang.Class<?> r18, java.lang.reflect.Method r19, java.lang.Object[] r20, com.alipay.inside.android.phone.mrpc.core.InnerRpcInvokeContext r21) throws com.alipay.inside.android.phone.mrpc.core.RpcException {
        /*
            r16 = this;
            r10 = r16
            r11 = r19
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r2 = "RpcInvoker"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "ThreadId=["
            r3.<init>(r4)
            java.lang.Thread r4 = java.lang.Thread.currentThread()
            long r4 = r4.getId()
            r3.append(r4)
            java.lang.String r4 = "]  invoke. object=["
            r3.append(r4)
            java.lang.Class r4 = r19.getDeclaringClass()
            java.lang.String r4 = r4.getName()
            r3.append(r4)
            java.lang.String r4 = "]  methodName=["
            r3.append(r4)
            java.lang.String r4 = r19.getName()
            r3.append(r4)
            java.lang.String r4 = "]"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.a(r2, r3)
            boolean r1 = com.alipay.inside.android.phone.mrpc.core.utils.ThreadUtil.checkMainThread()
            if (r1 == 0) goto L_0x0052
            java.lang.IllegalThreadStateException r1 = new java.lang.IllegalThreadStateException
            java.lang.String r2 = "can't call rpc in main thread."
            r1.<init>(r2)
            throw r1
        L_0x0052:
            long r12 = java.lang.System.currentTimeMillis()
            java.lang.ThreadLocal<java.lang.Object> r1 = RETURN_VALUE
            r14 = 0
            r1.set(r14)
            java.lang.ThreadLocal<java.util.Map<java.lang.String, java.lang.Object>> r1 = EXT_PARAM
            r1.set(r14)
            java.util.concurrent.atomic.AtomicInteger r1 = r10.rpcSequence
            int r8 = r1.incrementAndGet()
            java.lang.annotation.Annotation[] r6 = r19.getAnnotations()     // Catch:{ RpcException -> 0x00e1 }
            r1 = r10
            r2 = r17
            r3 = r18
            r4 = r11
            r5 = r20
            r7 = r21
            r1.preHandle(r2, r3, r4, r5, r6, r7)     // Catch:{ RpcException -> 0x00e1 }
            byte r1 = r10.mMode     // Catch:{ RpcException -> 0x00e1 }
            if (r1 != 0) goto L_0x009a
            com.alipay.inside.android.phone.mrpc.core.gwprotocol.util.RPCProtoDesc r9 = new com.alipay.inside.android.phone.mrpc.core.gwprotocol.util.RPCProtoDesc     // Catch:{ RpcException -> 0x00e1 }
            r9.<init>()     // Catch:{ RpcException -> 0x00e1 }
            java.lang.String r4 = com.alipay.inside.android.phone.mrpc.core.utils.RpcInvokerUtil.getOperationTypeValue(r19, r20)     // Catch:{ RpcException -> 0x00e1 }
            r1 = r10
            r2 = r11
            r3 = r20
            r5 = r8
            r6 = r21
            r7 = r9
            com.alipay.inside.android.phone.mrpc.core.Response r1 = r1.singleCall(r2, r3, r4, r5, r6, r7)     // Catch:{ RpcException -> 0x00e1 }
            java.lang.Object r2 = r10.processResponse(r11, r1, r9)     // Catch:{ RpcException -> 0x0096 }
            goto L_0x009c
        L_0x0096:
            r0 = move-exception
            r8 = r0
            r15 = r1
            goto L_0x00e4
        L_0x009a:
            r1 = r14
            r2 = r1
        L_0x009c:
            long r3 = java.lang.System.currentTimeMillis()
            long r3 = r3 - r12
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r5 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r6 = "RpcInvoker"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "ThreadId=["
            r7.<init>(r8)
            java.lang.Thread r8 = java.lang.Thread.currentThread()
            long r8 = r8.getId()
            r7.append(r8)
            java.lang.String r8 = "] methodName=["
            r7.append(r8)
            java.lang.String r8 = r19.getName()
            r7.append(r8)
            java.lang.String r8 = "] invokeTiming=["
            r7.append(r8)
            r7.append(r3)
            java.lang.String r3 = "]"
            r7.append(r3)
            java.lang.String r3 = r7.toString()
            r5.a(r6, r3)
            r15 = r1
            r9 = r2
            goto L_0x014a
        L_0x00dd:
            r0 = move-exception
            r1 = r0
            goto L_0x0176
        L_0x00e1:
            r0 = move-exception
            r8 = r0
            r15 = r14
        L_0x00e4:
            java.lang.String r1 = com.alipay.inside.android.phone.mrpc.core.utils.RpcInvokerUtil.getOperationTypeValue(r19, r20)     // Catch:{ all -> 0x00dd }
            perfLog(r8, r1)     // Catch:{ all -> 0x00dd }
            java.lang.String r1 = com.alipay.inside.android.phone.mrpc.core.utils.RpcInvokerUtil.getOperationTypeValue(r19, r20)     // Catch:{ all -> 0x00dd }
            r8.setOperationType(r1)     // Catch:{ all -> 0x00dd }
            if (r15 == 0) goto L_0x00fa
            byte[] r1 = r15.getResData()     // Catch:{ all -> 0x00dd }
            r3 = r1
            goto L_0x00fb
        L_0x00fa:
            r3 = r14
        L_0x00fb:
            java.lang.annotation.Annotation[] r7 = r19.getAnnotations()     // Catch:{ all -> 0x00dd }
            r1 = r10
            r2 = r17
            r4 = r18
            r5 = r11
            r6 = r20
            r9 = r21
            r1.exceptionHandle(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x00dd }
            long r1 = java.lang.System.currentTimeMillis()
            long r1 = r1 - r12
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r3 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r4 = "RpcInvoker"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "ThreadId=["
            r5.<init>(r6)
            java.lang.Thread r6 = java.lang.Thread.currentThread()
            long r6 = r6.getId()
            r5.append(r6)
            java.lang.String r6 = "] methodName=["
            r5.append(r6)
            java.lang.String r6 = r19.getName()
            r5.append(r6)
            java.lang.String r6 = "] invokeTiming=["
            r5.append(r6)
            r5.append(r1)
            java.lang.String r1 = "]"
            r5.append(r1)
            java.lang.String r1 = r5.toString()
            r3.a(r4, r1)
            r9 = r14
        L_0x014a:
            if (r15 == 0) goto L_0x0152
            byte[] r1 = r15.getResData()
            r3 = r1
            goto L_0x0153
        L_0x0152:
            r3 = r14
        L_0x0153:
            java.lang.annotation.Annotation[] r7 = r19.getAnnotations()
            r1 = r10
            r2 = r17
            r4 = r18
            r5 = r11
            r6 = r20
            r8 = r21
            r1.postHandle(r2, r3, r4, r5, r6, r7, r8)
            java.lang.ThreadLocal<java.lang.Object> r1 = RETURN_VALUE
            java.lang.Object r1 = r1.get()
            if (r1 == 0) goto L_0x0172
            java.lang.ThreadLocal<java.lang.Object> r1 = RETURN_VALUE
            java.lang.Object r9 = r1.get()
        L_0x0172:
            r10.printReturnObjLog(r11, r9)
            return r9
        L_0x0176:
            long r2 = java.lang.System.currentTimeMillis()
            long r2 = r2 - r12
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r4 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r5 = "RpcInvoker"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "ThreadId=["
            r6.<init>(r7)
            java.lang.Thread r7 = java.lang.Thread.currentThread()
            long r7 = r7.getId()
            r6.append(r7)
            java.lang.String r7 = "] methodName=["
            r6.append(r7)
            java.lang.String r7 = r19.getName()
            r6.append(r7)
            java.lang.String r7 = "] invokeTiming=["
            r6.append(r7)
            r6.append(r2)
            java.lang.String r2 = "]"
            r6.append(r2)
            java.lang.String r2 = r6.toString()
            r4.a(r5, r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.inside.android.phone.mrpc.core.RpcInvoker.invoke(java.lang.Object, java.lang.Class, java.lang.reflect.Method, java.lang.Object[], com.alipay.inside.android.phone.mrpc.core.InnerRpcInvokeContext):java.lang.Object");
    }

    private Object processResponse(Method method, Response response, RPCProtoDesc rPCProtoDesc) {
        Type genericReturnType = method.getGenericReturnType();
        Object parser = this.serializerFactory.getDeserializer(genericReturnType, response, rPCProtoDesc).parser();
        if (genericReturnType != Void.TYPE) {
            RETURN_VALUE.set(parser);
        }
        return parser;
    }

    private void printReturnObjLog(Method method, Object obj) {
        if (obj != null) {
            try {
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("ThreadId=[");
                sb.append(Thread.currentThread().getId());
                sb.append("] methodName=[");
                sb.append(method.getName());
                sb.append("] returnObj=[");
                sb.append(JSONCodec.toJSONString(obj));
                sb.append("]");
                f.a((String) TAG, sb.toString());
            } catch (Throwable th) {
                LoggerFactory.f().b((String) "rpc", th);
            }
        } else {
            TraceLogger f2 = LoggerFactory.f();
            StringBuilder sb2 = new StringBuilder("ThreadId=[");
            sb2.append(Thread.currentThread().getId());
            sb2.append("] methodName=[");
            sb2.append(method.getName());
            sb2.append("] returnObj=[ is null ]");
            f2.a((String) TAG, sb2.toString());
        }
    }

    private boolean handleAnnotations(Annotation[] annotationArr, Handle handle) throws RpcException {
        try {
            boolean z = true;
            for (Annotation annotation : annotationArr) {
                RpcInterceptor findRpcInterceptor = this.mRpcFactory.findRpcInterceptor(annotation.annotationType());
                if (findRpcInterceptor != null) {
                    z = handle.handle(findRpcInterceptor, annotation);
                    if (!z) {
                        break;
                    }
                }
            }
            return z;
        } catch (Throwable th) {
            if (th instanceof RpcException) {
                throw ((RpcException) th);
            }
            throw new RpcException(Integer.valueOf(9), th);
        }
    }

    private void postHandle(Object obj, byte[] bArr, Class<?> cls, Method method, Object[] objArr, Annotation[] annotationArr, InnerRpcInvokeContext innerRpcInvokeContext) throws RpcException {
        LoggerFactory.f().b((String) "rpc", "RpcInvoker::postHandle > invokeContext:".concat(String.valueOf(innerRpcInvokeContext)));
        final Object obj2 = obj;
        final byte[] bArr2 = bArr;
        final Class<?> cls2 = cls;
        final Method method2 = method;
        final Object[] objArr2 = objArr;
        AnonymousClass1 r3 = new Handle() {
            public boolean handle(RpcInterceptor rpcInterceptor, Annotation annotation) throws RpcException {
                if (rpcInterceptor.postHandle(obj2, RpcInvoker.RETURN_VALUE, bArr2, cls2, method2, objArr2, annotation)) {
                    return true;
                }
                Integer valueOf = Integer.valueOf(9);
                StringBuilder sb = new StringBuilder();
                sb.append(rpcInterceptor);
                sb.append("postHandle stop this call.");
                throw new RpcException(valueOf, sb.toString());
            }
        };
        handleAnnotations(annotationArr, r3);
    }

    private void exceptionHandle(Object obj, byte[] bArr, Class<?> cls, Method method, Object[] objArr, Annotation[] annotationArr, RpcException rpcException, InnerRpcInvokeContext innerRpcInvokeContext) throws RpcException {
        LoggerFactory.f().b((String) "rpc", "RpcInvoker::exceptionHandle > invokeContext:".concat(String.valueOf(innerRpcInvokeContext)));
        final Object obj2 = obj;
        final byte[] bArr2 = bArr;
        final Class<?> cls2 = cls;
        final Method method2 = method;
        final Object[] objArr2 = objArr;
        final RpcException rpcException2 = rpcException;
        AnonymousClass2 r3 = new Handle() {
            public boolean handle(RpcInterceptor rpcInterceptor, Annotation annotation) throws RpcException {
                if (rpcInterceptor.exceptionHandle(obj2, RpcInvoker.RETURN_VALUE, bArr2, cls2, method2, objArr2, rpcException2, annotation)) {
                    TraceLogger f = LoggerFactory.f();
                    StringBuilder sb = new StringBuilder();
                    sb.append(rpcException2);
                    sb.append(" need process");
                    f.d(RpcInvoker.TAG, sb.toString());
                    return true;
                }
                TraceLogger f2 = LoggerFactory.f();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(rpcException2);
                sb2.append(" need not process,interceptor already process");
                f2.d(RpcInvoker.TAG, sb2.toString());
                return false;
            }
        };
        if (handleAnnotations(annotationArr, r3)) {
            throw rpcException;
        }
    }

    private void preHandle(Object obj, Class<?> cls, Method method, Object[] objArr, Annotation[] annotationArr, InnerRpcInvokeContext innerRpcInvokeContext) throws RpcException {
        LoggerFactory.f().b((String) "rpc", "RpcInvoker::preHandle > invokeContext:".concat(String.valueOf(innerRpcInvokeContext)));
        final Object obj2 = obj;
        final Class<?> cls2 = cls;
        final Method method2 = method;
        final Object[] objArr2 = objArr;
        AnonymousClass3 r2 = new Handle() {
            public boolean handle(RpcInterceptor rpcInterceptor, Annotation annotation) throws RpcException {
                if (rpcInterceptor.preHandle(obj2, RpcInvoker.RETURN_VALUE, new byte[0], cls2, method2, objArr2, annotation, RpcInvoker.EXT_PARAM)) {
                    return true;
                }
                Integer valueOf = Integer.valueOf(9);
                StringBuilder sb = new StringBuilder();
                sb.append(rpcInterceptor);
                sb.append("preHandle stop this call.");
                throw new RpcException(valueOf, sb.toString());
            }
        };
        handleAnnotations(annotationArr, r2);
    }

    private Response singleCall(Method method, Object[] objArr, String str, int i, InnerRpcInvokeContext innerRpcInvokeContext, RPCProtoDesc rPCProtoDesc) throws RpcException {
        Method method2 = method;
        InnerRpcInvokeContext innerRpcInvokeContext2 = innerRpcInvokeContext;
        checkLogin(method2, innerRpcInvokeContext2);
        Serializer serializer = getSerializer(method, objArr, str, i, innerRpcInvokeContext, rPCProtoDesc);
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("operationType=");
        String str2 = str;
        sb.append(str2);
        sb.append(",serializerClass=");
        sb.append(serializer.getClass().getSimpleName());
        f.a((String) TAG, sb.toString());
        if (EXT_PARAM.get() != null) {
            serializer.setExtParam(EXT_PARAM.get());
        }
        byte[] packet = serializer.packet();
        HttpCaller httpCaller = new HttpCaller(this.mRpcFactory.getConfig(), method2, i, str2, packet, this.serializerFactory.getContentType(rPCProtoDesc), this.mRpcFactory.getContext(), innerRpcInvokeContext2);
        addInfo2Caller(method2, serializer, httpCaller, str2, packet, innerRpcInvokeContext2);
        Response response = (Response) httpCaller.call();
        EXT_PARAM.set(null);
        return response;
    }

    public static void addProtocolArgs(String str, Object obj) {
        if (EXT_PARAM.get() == null) {
            EXT_PARAM.set(new HashMap());
        }
        EXT_PARAM.get().put(str, obj);
    }

    private void addInfo2Caller(Method method, Serializer serializer, RpcCaller rpcCaller, String str, byte[] bArr, InnerRpcInvokeContext innerRpcInvokeContext) {
        if (rpcCaller instanceof HttpCaller) {
            HttpCaller httpCaller = (HttpCaller) rpcCaller;
            if (serializer instanceof JsonSerializerV2) {
                httpCaller.setReqDataDigest(((JsonSerializerV2) serializer).getRequestDataDigest());
                httpCaller.setContentType("application/json");
            } else if (serializer instanceof PBSerializer) {
                httpCaller.setContentType("application/protobuf");
                httpCaller.setReqDataDigest(((PBSerializer) serializer).getRequestDataDigest());
            }
            String str2 = GtsUtils.get64HexCurrentTimeMillis();
            httpCaller.setTimeStamp(str2);
            if (isNeedSign(method)) {
                httpCaller.setSignData(getSignData(str, bArr, str2, innerRpcInvokeContext));
            }
        }
    }

    @TargetApi(8)
    private SignData getSignData(String str, byte[] bArr, String str2, InnerRpcInvokeContext innerRpcInvokeContext) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Operation-Type=");
        stringBuffer.append(str);
        stringBuffer.append("&");
        stringBuffer.append("Request-Data=");
        stringBuffer.append(Base64.encodeToString(bArr, 2));
        stringBuffer.append("&");
        stringBuffer.append("Ts=");
        stringBuffer.append(str2);
        String stringBuffer2 = stringBuffer.toString();
        if (MiscUtils.isDebugger(this.mRpcFactory.getContext())) {
            LoggerFactory.f().a((String) TAG, "sign content: ".concat(String.valueOf(stringBuffer2)));
        }
        return RpcSignUtil.signature(this.mRpcFactory.getContext(), innerRpcInvokeContext.appKey, true, stringBuffer2);
    }

    private void checkLogin(Method method, InnerRpcInvokeContext innerRpcInvokeContext) {
        try {
            OperationType operationType = (OperationType) method.getAnnotation(OperationType.class);
            if (operationType != null && ((CheckLogin) method.getAnnotation(CheckLogin.class)) != null) {
                String cookie = CookieAccessHelper.getCookie(innerRpcInvokeContext.gwUrl, getRpcFactory().getContext());
                if (TextUtils.isEmpty(cookie)) {
                    TraceLogger f = LoggerFactory.f();
                    StringBuilder sb = new StringBuilder("CheckLogin_prejudge: cookie is empty  API=[");
                    sb.append(operationType.value());
                    sb.append("]");
                    f.c((String) TAG, sb.toString());
                    throw new RpcException(Integer.valueOf(2000), (String) "登录超时，请重新登录:登录超时，请重新登录");
                } else if (!cookie.contains("ALIPAYJSESSIONID")) {
                    TraceLogger f2 = LoggerFactory.f();
                    StringBuilder sb2 = new StringBuilder("CheckLogin_prejudge: cookie not contains ALIPAYJSESSIONID!  API=[");
                    sb2.append(operationType.value());
                    sb2.append("]");
                    f2.c((String) TAG, sb2.toString());
                    throw new RpcException(Integer.valueOf(2000), (String) "登录超时，请重新登录:登录超时，请重新登录");
                }
            }
        } catch (Throwable th) {
            TraceLogger f3 = LoggerFactory.f();
            StringBuilder sb3 = new StringBuilder("checkLogin ex:");
            sb3.append(th.toString());
            f3.d(TAG, sb3.toString());
        }
    }

    private Serializer getSerializer(Method method, Object[] objArr, String str, int i, InnerRpcInvokeContext innerRpcInvokeContext, RPCProtoDesc rPCProtoDesc) {
        return this.serializerFactory.getSerializer(i, str, method, objArr, innerRpcInvokeContext, rPCProtoDesc);
    }

    public static boolean isNeedSign(Method method) {
        SignCheck signCheck = (SignCheck) method.getAnnotation(SignCheck.class);
        if (signCheck != null && TextUtils.equals(signCheck.value(), BQCCameraParam.VALUE_NO)) {
            return false;
        }
        return true;
    }

    private static void perfLog(RpcException rpcException, String str) {
        try {
            if (isNeedPerfLog(rpcException)) {
                HashMap hashMap = new HashMap();
                hashMap.put("API", str);
                hashMap.put("ERR_CODE", String.valueOf(rpcException.getCode()));
                hashMap.put("ERR_MSG", MiscUtils.getRootCause(rpcException).toString());
                StringBuffer stringBuffer = new StringBuffer();
                for (Entry entry : hashMap.entrySet()) {
                    stringBuffer.append((String) entry.getKey());
                    stringBuffer.append("=");
                    stringBuffer.append((String) entry.getValue());
                    stringBuffer.append(",");
                }
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("errorStr:");
                sb.append(stringBuffer.toString());
                f.a((String) TAG, sb.toString());
                LoggerFactory.e().a("RPC_ERR", stringBuffer.toString());
            }
        } catch (Throwable th) {
            TraceLogger f2 = LoggerFactory.f();
            StringBuilder sb2 = new StringBuilder("perfLog ex:");
            sb2.append(th.toString());
            f2.d(TAG, sb2.toString());
        }
    }

    private static boolean isNeedPerfLog(RpcException rpcException) {
        if (rpcException.getCode() == 10 || rpcException.getCode() == 9 || rpcException.getCode() == 13 || rpcException.getCode() == 2 || rpcException.getCode() == 1 || rpcException.isServerError()) {
            return true;
        }
        return false;
    }
}
