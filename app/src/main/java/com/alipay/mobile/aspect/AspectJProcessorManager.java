package com.alipay.mobile.aspect;

import com.alipay.mobile.aspect.processor.IAspectJProcessor;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.sina.weibo.sdk.constant.WBConstants;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AspectJProcessorManager {
    public static final int RELATIONSHIP_FATHER = 1;
    public static final int RELATIONSHIP_SELF = 4;
    public static final int RELATIONSHIP_SON = 0;
    public static final int RELATIONSHIP_STRANGER = 2;
    public static final String TAG = "AspectJProcessorManager";
    private static AspectJProcessorManager a;
    private Map<String, List<IAspectJProcessor>> b;

    private AspectJProcessorManager() {
        String[] supportMethods = a();
        Map map = new HashMap(53);
        for (int i = 0; i < 53; i++) {
            map.put(supportMethods[i], new LinkedList());
        }
        this.b = Collections.unmodifiableMap(map);
    }

    public static AspectJProcessorManager get() {
        if (a != null) {
            return a;
        }
        synchronized (AspectJProcessorManager.class) {
            try {
                if (a == null) {
                    a = new AspectJProcessorManager();
                }
            }
        }
        return a;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean registerProcessor(com.alipay.mobile.aspect.processor.IAspectJProcessor r14) {
        /*
            r13 = this;
            r7 = 1
            r6 = 0
            if (r14 != 0) goto L_0x0010
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "AspectJProcessorManager"
            java.lang.String r9 = "Register processor error, processor is null."
            r7.error(r8, r9)
        L_0x000f:
            return r6
        L_0x0010:
            java.lang.String r1 = r14.getMethodName()
            if (r1 == 0) goto L_0x001e
            java.util.Map<java.lang.String, java.util.List<com.alipay.mobile.aspect.processor.IAspectJProcessor>> r8 = r13.b
            boolean r8 = r8.containsKey(r1)
            if (r8 != 0) goto L_0x0037
        L_0x001e:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "AspectJProcessorManager"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "Register processor error, methodName is invalid, value: "
            r9.<init>(r10)
            java.lang.StringBuilder r9 = r9.append(r1)
            java.lang.String r9 = r9.toString()
            r7.error(r8, r9)
            goto L_0x000f
        L_0x0037:
            java.lang.Class r5 = r14.getTargetClass()
            if (r5 != 0) goto L_0x0049
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "AspectJProcessorManager"
            java.lang.String r9 = "Register processor error, targetClass is null."
            r7.error(r8, r9)
            goto L_0x000f
        L_0x0049:
            java.util.Map<java.lang.String, java.util.List<com.alipay.mobile.aspect.processor.IAspectJProcessor>> r8 = r13.b
            java.lang.Object r2 = r8.get(r1)
            java.util.List r2 = (java.util.List) r2
            monitor-enter(r2)
            boolean r8 = r2.isEmpty()     // Catch:{ all -> 0x007e }
            if (r8 == 0) goto L_0x005e
            r2.add(r14)     // Catch:{ all -> 0x007e }
            monitor-exit(r2)     // Catch:{ all -> 0x007e }
            r6 = r7
            goto L_0x000f
        L_0x005e:
            r4 = 0
            java.util.Iterator r8 = r2.iterator()     // Catch:{ all -> 0x007e }
        L_0x0063:
            boolean r9 = r8.hasNext()     // Catch:{ all -> 0x007e }
            if (r9 == 0) goto L_0x00df
            java.lang.Object r0 = r8.next()     // Catch:{ all -> 0x007e }
            com.alipay.mobile.aspect.processor.IAspectJProcessor r0 = (com.alipay.mobile.aspect.processor.IAspectJProcessor) r0     // Catch:{ all -> 0x007e }
            if (r14 != r0) goto L_0x0081
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x007e }
            java.lang.String r8 = "AspectJProcessorManager"
            java.lang.String r9 = "Register processor error, processor is already in list."
            r7.error(r8, r9)     // Catch:{ all -> 0x007e }
            monitor-exit(r2)     // Catch:{ all -> 0x007e }
            goto L_0x000f
        L_0x007e:
            r6 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x007e }
            throw r6
        L_0x0081:
            java.lang.Class r9 = r0.getTargetClass()     // Catch:{ all -> 0x007e }
            int r3 = a(r9, r5)     // Catch:{ all -> 0x007e }
            r9 = 4
            if (r3 != r9) goto L_0x00b5
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x007e }
            java.lang.String r8 = "AspectJProcessorManager"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x007e }
            java.lang.String r10 = "Register processor error, conflict class, c1: "
            r9.<init>(r10)     // Catch:{ all -> 0x007e }
            java.lang.Class r10 = r0.getTargetClass()     // Catch:{ all -> 0x007e }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x007e }
            java.lang.String r10 = ", c2: "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x007e }
            java.lang.StringBuilder r9 = r9.append(r5)     // Catch:{ all -> 0x007e }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x007e }
            r7.error(r8, r9)     // Catch:{ all -> 0x007e }
            monitor-exit(r2)     // Catch:{ all -> 0x007e }
            goto L_0x000f
        L_0x00b5:
            if (r3 != r7) goto L_0x0063
            r4 = 1
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x007e }
            java.lang.String r10 = "AspectJProcessorManager"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x007e }
            java.lang.String r12 = "Register processor, found it's parent class, seize first position. c1: "
            r11.<init>(r12)     // Catch:{ all -> 0x007e }
            java.lang.Class r12 = r0.getTargetClass()     // Catch:{ all -> 0x007e }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x007e }
            java.lang.String r12 = ", c2"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x007e }
            java.lang.StringBuilder r11 = r11.append(r5)     // Catch:{ all -> 0x007e }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x007e }
            r9.info(r10, r11)     // Catch:{ all -> 0x007e }
            goto L_0x0063
        L_0x00df:
            if (r4 == 0) goto L_0x00e9
            r6 = 0
            r2.add(r6, r14)     // Catch:{ all -> 0x007e }
        L_0x00e5:
            monitor-exit(r2)     // Catch:{ all -> 0x007e }
            r6 = r7
            goto L_0x000f
        L_0x00e9:
            r2.add(r14)     // Catch:{ all -> 0x007e }
            goto L_0x00e5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.aspect.AspectJProcessorManager.registerProcessor(com.alipay.mobile.aspect.processor.IAspectJProcessor):boolean");
    }

    private static int a(Class<?> c1, Class<?> c2) {
        boolean relativeFirst = c1.isAssignableFrom(c2);
        boolean relativeSecond = c2.isAssignableFrom(c1);
        if (relativeFirst && relativeSecond) {
            return 4;
        }
        if (relativeFirst) {
            return 1;
        }
        if (relativeSecond) {
            return 0;
        }
        return 2;
    }

    public void unregisterProcessor(IAspectJProcessor processor) {
        if (processor != null && processor.getMethodName() != null && this.b.containsKey(processor.getMethodName())) {
            List processorList = this.b.get(processor.getMethodName());
            synchronized (processorList) {
                processorList.remove(processor);
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.mobile.aspect.processor.IAspectJProcessor obtainProcessor(org.aspectj.lang.JoinPoint r12) {
        /*
            r11 = this;
            r6 = 0
            if (r12 == 0) goto L_0x0009
            org.aspectj.lang.Signature r7 = r12.getSignature()     // Catch:{ Throwable -> 0x00a3 }
            if (r7 != 0) goto L_0x0016
        L_0x0009:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x00a3 }
            java.lang.String r8 = "AspectJProcessorManager"
            java.lang.String r9 = "Obtain processor error, joinPoint or signature is null."
            r7.error(r8, r9)     // Catch:{ Throwable -> 0x00a3 }
            r2 = r6
        L_0x0015:
            return r2
        L_0x0016:
            org.aspectj.lang.Signature r7 = r12.getSignature()     // Catch:{ Throwable -> 0x00a3 }
            boolean r7 = r7 instanceof org.aspectj.lang.reflect.MethodSignature     // Catch:{ Throwable -> 0x00a3 }
            if (r7 != 0) goto L_0x0040
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x00a3 }
            java.lang.String r8 = "AspectJProcessorManager"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a3 }
            java.lang.String r10 = "Obtain processor error, signature is invalid type, type: "
            r9.<init>(r10)     // Catch:{ Throwable -> 0x00a3 }
            org.aspectj.lang.Signature r10 = r12.getSignature()     // Catch:{ Throwable -> 0x00a3 }
            java.lang.Class r10 = r10.getClass()     // Catch:{ Throwable -> 0x00a3 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x00a3 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x00a3 }
            r7.error(r8, r9)     // Catch:{ Throwable -> 0x00a3 }
            r2 = r6
            goto L_0x0015
        L_0x0040:
            org.aspectj.lang.Signature r4 = r12.getSignature()     // Catch:{ Throwable -> 0x00a3 }
            org.aspectj.lang.reflect.MethodSignature r4 = (org.aspectj.lang.reflect.MethodSignature) r4     // Catch:{ Throwable -> 0x00a3 }
            java.lang.String r1 = r4.getName()     // Catch:{ Throwable -> 0x00a3 }
            if (r1 == 0) goto L_0x0054
            java.util.Map<java.lang.String, java.util.List<com.alipay.mobile.aspect.processor.IAspectJProcessor>> r7 = r11.b     // Catch:{ Throwable -> 0x00a3 }
            boolean r7 = r7.containsKey(r1)     // Catch:{ Throwable -> 0x00a3 }
            if (r7 != 0) goto L_0x0061
        L_0x0054:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x00a3 }
            java.lang.String r8 = "AspectJProcessorManager"
            java.lang.String r9 = "Obtain processor error, methodName is null or can't find it in processor pool."
            r7.error(r8, r9)     // Catch:{ Throwable -> 0x00a3 }
            r2 = r6
            goto L_0x0015
        L_0x0061:
            java.util.Map<java.lang.String, java.util.List<com.alipay.mobile.aspect.processor.IAspectJProcessor>> r7 = r11.b     // Catch:{ Throwable -> 0x00a3 }
            java.lang.Object r3 = r7.get(r1)     // Catch:{ Throwable -> 0x00a3 }
            java.util.List r3 = (java.util.List) r3     // Catch:{ Throwable -> 0x00a3 }
            boolean r7 = r3.isEmpty()     // Catch:{ Throwable -> 0x00a3 }
            if (r7 == 0) goto L_0x0071
            r2 = r6
            goto L_0x0015
        L_0x0071:
            monitor-enter(r3)     // Catch:{ Throwable -> 0x00a3 }
            r0 = 0
        L_0x0073:
            int r7 = r3.size()     // Catch:{ all -> 0x00a0 }
            if (r0 >= r7) goto L_0x00c4
            java.lang.Object r2 = r3.get(r0)     // Catch:{ all -> 0x00a0 }
            com.alipay.mobile.aspect.processor.IAspectJProcessor r2 = (com.alipay.mobile.aspect.processor.IAspectJProcessor) r2     // Catch:{ all -> 0x00a0 }
            java.lang.Class r7 = r2.getTargetClass()     // Catch:{ all -> 0x00a0 }
            java.lang.Class r8 = r4.getDeclaringType()     // Catch:{ all -> 0x00a0 }
            boolean r7 = r7.isAssignableFrom(r8)     // Catch:{ all -> 0x00a0 }
            if (r7 == 0) goto L_0x00c1
            java.lang.String r7 = r2.getMethodName()     // Catch:{ all -> 0x00a0 }
            boolean r7 = r1.equals(r7)     // Catch:{ all -> 0x00a0 }
            if (r7 == 0) goto L_0x00c1
            java.util.List r7 = r2.getParameters()     // Catch:{ all -> 0x00a0 }
            if (r7 != 0) goto L_0x00b0
            monitor-exit(r3)     // Catch:{ all -> 0x00a0 }
            goto L_0x0015
        L_0x00a0:
            r7 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00a0 }
            throw r7     // Catch:{ Throwable -> 0x00a3 }
        L_0x00a3:
            r5 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "AspectJProcessorManager"
            r7.error(r8, r5)
        L_0x00ad:
            r2 = r6
            goto L_0x0015
        L_0x00b0:
            java.lang.Class[] r7 = r4.getParameterTypes()     // Catch:{ all -> 0x00a0 }
            java.util.List r8 = r2.getParameters()     // Catch:{ all -> 0x00a0 }
            boolean r7 = a(r7, r8)     // Catch:{ all -> 0x00a0 }
            if (r7 == 0) goto L_0x00c1
            monitor-exit(r3)     // Catch:{ all -> 0x00a0 }
            goto L_0x0015
        L_0x00c1:
            int r0 = r0 + 1
            goto L_0x0073
        L_0x00c4:
            monitor-exit(r3)     // Catch:{ all -> 0x00a0 }
            goto L_0x00ad
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.aspect.AspectJProcessorManager.obtainProcessor(org.aspectj.lang.JoinPoint):com.alipay.mobile.aspect.processor.IAspectJProcessor");
    }

    private static boolean a(Class[] pointcutParams, List<Class[]> processorParamsList) {
        for (int i = 0; i < processorParamsList.size(); i++) {
            Class[] temp = processorParamsList.get(i);
            if (pointcutParams.length == temp.length) {
                if (pointcutParams.length == 0) {
                    return true;
                }
                boolean find = true;
                int j = 0;
                while (true) {
                    if (j >= pointcutParams.length) {
                        break;
                    } else if (!pointcutParams[j].equals(temp[j])) {
                        find = false;
                        break;
                    } else {
                        j++;
                    }
                }
                if (find) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String[] a() {
        return new String[]{"query", "insert", "update", "delete", "applyBatch", "registerContentObserver", "sendTextMessage", "startService", WBConstants.SHARE_START_ACTIVITY, "onReceive", "openCamera", "startRecording", "getCellLocation", "getNeighboringCellInfo", "getDeviceId", "getImei", "getSimSerialNumber", "getSubscriberId", "getLastKnownLocation", "requestLocationUpdates", "requestPermissions", "open", "bindService", "registerListener", "unregisterListener", "deleteOnExit", "getBSSID", "getAddress", "getHardwareAddress", "getMacAddress", "getSSID", "getCid", "getBaseStationId", "getInstalledPackages", "getIpAddress", "getHostAddress", "getLine1Number", "getSimCountryIso", "getSimOperator", "getSimOperatorName", "getNetworkOperator", "getNetworkOperatorName", CommonEvents.GET_NETWORK_TYPE, "getNetworkId", "getScanResults", "startScan", "set", "setExact", "setRepeating", "setInexactRepeating", "cancel", "acquire", "release"};
    }
}
