package com.alipay.mobile.beehive.rpc;

import android.app.Activity;
import android.text.TextUtils;
import com.alipay.mobile.beehive.rpc.action.DefaultActionProcessor;
import com.alipay.mobile.beehive.util.DebugUtil;
import com.alipay.mobile.beehive.util.MiscUtil;
import com.alipay.mobile.beehive.util.ServiceUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.alipay.mobile.framework.service.common.RpcService;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.squareup.wire.Message;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class RpcUtil {
    public static boolean isNetworkException(Exception ex) {
        if ((ex instanceof RpcException) && ((RpcException) ex).isClientError()) {
            return true;
        }
        return false;
    }

    public static boolean isNetworkSlow(Exception ex) {
        if (ex == null || !(ex instanceof RpcException)) {
            return false;
        }
        RpcException e = (RpcException) ex;
        if (e.getCode() == 5 || e.getCode() == 4) {
            return true;
        }
        return false;
    }

    public static boolean isOverflowException(Exception ex) {
        if (ex == null || !(ex instanceof RpcException) || ((RpcException) ex).getCode() != 1002) {
            return false;
        }
        return true;
    }

    public static boolean isRpcSuccess(Object result) {
        if (result != null) {
            try {
                Field f = getFieldByReflect(result, "success");
                if (f == null || f.getType() == null) {
                    LoggerFactory.getTraceLogger().info(RpcConstant.TAG, getSimpleName(result.getClass()) + " 不存在success字段,是否被混淆?");
                } else {
                    f.setAccessible(true);
                    if (f.getType().isPrimitive()) {
                        return f.getBoolean(result);
                    }
                    if (f.getType().equals(Boolean.class)) {
                        Boolean v = (Boolean) f.get(result);
                        LoggerFactory.getTraceLogger().info(RpcConstant.TAG, getSimpleName(result.getClass()) + " success为大Boolean类型, 反射获取结果=" + v);
                        return v.booleanValue();
                    }
                }
            } catch (Exception ex) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            }
        }
        return false;
    }

    public static Object getObjectByReflectWithBase(Object result, String fieldName) {
        Object obj = null;
        Field f = getFieldByReflect(result, fieldName);
        if (f != null) {
            try {
                return f.get(result);
            } catch (Exception ex) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
                return obj;
            }
        } else {
            Field baseField = getFieldByReflect(result, RpcConstant.BASE);
            if (baseField == null) {
                return obj;
            }
            try {
                Object base = baseField.get(result);
                Field ff = getFieldByReflect(base, fieldName);
                if (ff != null) {
                    return ff.get(base);
                }
                return obj;
            } catch (Exception ex2) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex2);
                return obj;
            }
        }
    }

    public static Field getFieldByReflect(Object result, String fieldName) {
        if (result != null) {
            try {
                Field[] fields = result.getClass().getFields();
                if (fields == null) {
                    return null;
                }
                for (Field f : fields) {
                    if (TextUtils.equals(fieldName, f.getName())) {
                        return f;
                    }
                }
            } catch (Exception ex) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            }
        }
        return null;
    }

    public static String getRpcRunnableLogString(RpcTask task) {
        if (task == null || task.getRunnable() == null) {
            return MiscUtil.NULL_STR;
        }
        return getSimpleName(task.getRunnable().getClass());
    }

    public static String getSimpleName(Object obj) {
        if (obj == null) {
            return MiscUtil.NULL_STR;
        }
        return obj.toString();
    }

    public static String getSimpleName(Class<?> clazz) {
        if (clazz == null) {
            return MiscUtil.NULL_STR;
        }
        String simpleName = clazz.getSimpleName();
        if (TextUtils.isEmpty(simpleName)) {
            return clazz.getName();
        }
        return simpleName;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r6v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.reflect.Method getDeclaredMethodRecursive(java.lang.Class r6, java.lang.String r7, java.lang.Class[] r8) {
        /*
        L_0x0000:
            if (r6 == 0) goto L_0x002b
            java.lang.reflect.Method[] r1 = r6.getDeclaredMethods()
            if (r1 == 0) goto L_0x0026
            int r4 = r1.length
            r3 = 0
        L_0x000a:
            if (r3 >= r4) goto L_0x0026
            r0 = r1[r3]
            java.lang.String r5 = r0.getName()
            boolean r5 = r5.equals(r7)
            if (r5 == 0) goto L_0x0023
            java.lang.Class[] r2 = r0.getParameterTypes()
            boolean r5 = isClassArrayEqual(r8, r2)
            if (r5 == 0) goto L_0x0023
        L_0x0022:
            return r0
        L_0x0023:
            int r3 = r3 + 1
            goto L_0x000a
        L_0x0026:
            java.lang.Class r6 = r6.getSuperclass()
            goto L_0x0000
        L_0x002b:
            r0 = 0
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.beehive.rpc.RpcUtil.getDeclaredMethodRecursive(java.lang.Class, java.lang.String, java.lang.Class[]):java.lang.reflect.Method");
    }

    private static boolean isClassArrayEqual(Class[] a, Class[] b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static <T> T getRpcProxy(Class<T> type) {
        return ((RpcService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(RpcService.class.getName())).getRpcProxy(type);
    }

    public static boolean isFieldPublic(Field f) {
        return (f.getModifiers() & 1) > 0;
    }

    public static String getRpcEventName(RpcTask<?> task) {
        return "rpc_" + task.getTaskId();
    }

    public static boolean isConfigUseCache(RpcRunConfig config) {
        return config != null && (config.cacheMode == CacheMode.CACHE_AND_RPC || config.cacheMode == CacheMode.RPC_OR_CACHE || config.cacheMode == CacheMode.RPC_AND_SAVE_CACHE) && !TextUtils.isEmpty(config.cacheKey);
    }

    public static boolean isConfigCacheAndRpc(RpcRunConfig config) {
        return config != null && config.cacheMode == CacheMode.CACHE_AND_RPC && !TextUtils.isEmpty(config.cacheKey);
    }

    public static String formatTextForDebug(String text) {
        if (!DebugUtil.isDebug() || TextUtils.isEmpty(text) || text.contains("[beehive-rpc组件仅在可切环境中显示,线上不可见]")) {
            return text;
        }
        return text + "\n" + "[beehive-rpc组件仅在可切环境中显示,线上不可见]";
    }

    public static String getString(RpcUiProcessor rr, int id) {
        if (rr.getActivity() == null) {
            return "";
        }
        try {
            return rr.getActivity().getResources().getString(id);
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            return "";
        }
    }

    public static boolean supportFollowAction(Object result) {
        try {
            if (getFieldByReflect(result, RpcConstant.RPC_RESULT_FOLLOW_ACTION) == null) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            return true;
        }
    }

    public static Map<String, String> buildLogExtInfo(RpcRunner r) {
        RpcUiProcessor up = null;
        if (r.getRpcSubscriber() != null) {
            up = r.getRpcSubscriber().getRpcUiProcessor();
        }
        return buildLogExtInfo(up);
    }

    public static Map<String, String> buildLogExtInfo(RpcUiProcessor processor) {
        Map map = new HashMap();
        String appId = "";
        String viewId = "";
        if (!(processor == null || processor.getActivity() == null)) {
            Activity act = processor.getActivity();
            viewId = act.getClass().getName();
            if (act instanceof BaseActivity) {
                appId = ((BaseActivity) act).getAppTrackId();
            } else if (act instanceof BaseFragmentActivity) {
                appId = ((BaseFragmentActivity) act).getAppTrackId();
            }
        }
        if (appId == null) {
            appId = "";
        }
        map.put("appId", appId);
        if (viewId == null) {
            viewId = "";
        }
        map.put("viewId", viewId);
        return map;
    }

    public static boolean handleFollowAction(Activity activity, Object result) {
        if (activity == null) {
            throw new IllegalArgumentException("activity must not be null");
        } else if (result == null) {
            return false;
        } else {
            RpcUiProcessor uiProcessor = new RpcUiProcessor(activity);
            if (handleFollowAction(null, uiProcessor, result, RpcConstant.RPC_RESULT_FOLLOW_ACTION_WITH_BEEHIVE) || handleFollowAction(null, uiProcessor, result, RpcConstant.RPC_RESULT_FOLLOW_ACTION)) {
                return true;
            }
            return false;
        }
    }

    public static boolean handleFollowAction(RpcSubscriber rs, Object result) {
        if (rs == null) {
            throw new IllegalArgumentException("RpcSubscriber must not be null");
        } else if (result == null) {
            return false;
        } else {
            if (handleFollowAction(rs, rs.getRpcUiProcessor(), result, RpcConstant.RPC_RESULT_FOLLOW_ACTION_WITH_BEEHIVE) || handleFollowAction(rs, rs.getRpcUiProcessor(), result, RpcConstant.RPC_RESULT_FOLLOW_ACTION)) {
                return true;
            }
            return false;
        }
    }

    private static boolean handleFollowAction(RpcSubscriber rs, RpcUiProcessor uiProcessor, Object result, String key) {
        if (result == null) {
            return false;
        }
        Object action = getObjectByReflectWithBase(result, key);
        if (action == null || uiProcessor == null || !(action instanceof String) || TextUtils.isEmpty((String) action)) {
            return false;
        }
        return new DefaultActionProcessor(rs).handleFollowAction(uiProcessor, result, (String) action);
    }

    public static void executeInBgThread(Runnable r) {
        executeInBgThread(r, ScheduleType.RPC);
    }

    public static void executeInBgThread(Runnable r, ScheduleType scheduleType) {
        executeInBgThread((TaskScheduleService) ServiceUtil.getServiceByInterface(TaskScheduleService.class), r, scheduleType);
    }

    public static void executeInBgThread(TaskScheduleService ts, Runnable r) {
        executeInBgThread(ts, r, ScheduleType.RPC);
    }

    public static void executeInBgThread(TaskScheduleService ts, Runnable r, ScheduleType scheduleType) {
        if (scheduleType == null) {
            scheduleType = ScheduleType.RPC;
        }
        ThreadPoolExecutor e = ts.acquireExecutor(scheduleType);
        if (e != null) {
            e.execute(r);
        } else {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (String) "执行rpc前获取线程池失败");
        }
    }

    public static boolean checkIsPbFormat(Object request, Object responseType) {
        if (request instanceof Message) {
            return true;
        }
        try {
            if ((responseType instanceof Class) && Message.class.isAssignableFrom((Class) responseType)) {
                return true;
            }
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
        }
        return false;
    }
}
