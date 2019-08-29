package com.alipay.mobile.liteprocess;

import android.content.Context;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.advice.ActivityBackAdvice;
import com.alipay.mobile.liteprocess.ipc.IpcMsgServer;
import com.alipay.mobile.quinox.utils.ProcessInfo;
import java.util.List;

public class LiteProcessApi {

    public interface LiteClient {
        void onClientDestory();
    }

    public static void registerLiteProcessActivityClass(Class clazz, int lpid, boolean needHookBackKey) {
        if (isMainProcess()) {
            LiteProcessServerManager.g().a(clazz, lpid);
        } else if (isLiteProcess()) {
            LiteProcessClientManager.a(clazz, lpid);
            if (!needHookBackKey) {
                LiteProcessClientManager.a(clazz);
            }
        }
    }

    public static void registerLiteClient(LiteClient liteClient) {
        if (isLiteProcess()) {
            LiteProcessClientManager.a(liteClient);
        }
    }

    public static boolean isTaskRoot(Object activity) {
        return ActivityBackAdvice.isTaskRoot(activity);
    }

    public static void moveTaskToBack(Object activity) {
        ActivityBackAdvice.moveTaskToBack(activity);
    }

    public static void setContext(Context context) {
        Util.setContext(context);
    }

    public static Context getContext() {
        return Util.getContext();
    }

    public static int getLpid() {
        return Util.getLpid();
    }

    public static ProcessInfo getProcessInfo() {
        return Util.a();
    }

    public static String getCurrentProcessName() {
        return Util.getCurrentProcessName();
    }

    public static boolean isMainProcess() {
        return Util.isMainProcess();
    }

    public static boolean isLiteProcess() {
        return Util.isLiteProcess();
    }

    public static void stopLiteProcessByAppIdInServer(String appId) {
        LoggerFactory.getTraceLogger().warn((String) Const.TAG, new Throwable());
        LiteProcessServerManager.g().a(appId);
    }

    public static void stopLiteProcessByLpidInServer(int lpid) {
        LoggerFactory.getTraceLogger().warn((String) Const.TAG, new Throwable());
        LiteProcessServerManager.g().a(lpid);
    }

    public static void stopAllLiteProcessInServer() {
        LoggerFactory.getTraceLogger().warn((String) Const.TAG, new Throwable());
        LiteProcessServerManager.g().a();
    }

    public static void stopSelfInClient() {
        LoggerFactory.getTraceLogger().warn((String) Const.TAG, new Throwable());
        LiteProcessClientManager.b();
    }

    public static boolean isAllLiteProcessHide() {
        return LiteProcessServerManager.g().isAllLiteProcessHide();
    }

    @Nullable
    public static LiteProcess findProcessByAppId(String appId) {
        return LiteProcessServerManager.g().b(appId);
    }

    @Nullable
    public static LiteProcess findProcessByLpid(int lpid) {
        return LiteProcessServerManager.g().findProcessByLpid(lpid);
    }

    public static final List<LiteProcess> getAllAliveProcess() {
        return LiteProcessServerManager.g().getAllAliveProcess();
    }

    @Nullable
    public static LiteProcess findProcessByPid(int pid) {
        return LiteProcessServerManager.g().findProcessByPid(pid);
    }

    public static boolean hasPreloadProcess() {
        return LiteProcessServerManager.g().hasPreloadProcess();
    }

    public static void notifyUcInitSuccess() {
        LiteProcess readyProcess = LiteProcessServerManager.g().getReadyLiteProcess();
        if (readyProcess != null && readyProcess.d == 2 && readyProcess.h != null) {
            Message msg = Message.obtain();
            msg.what = 19;
            IpcMsgServer.reply(readyProcess.h, Const.TAG, msg);
            LoggerFactory.getTraceLogger().debug(Const.TAG, "notifyUcInitSuccess");
        }
    }

    public static void onSchemeStart(String schemeUri) {
        if (Config.t && !TextUtils.isEmpty(schemeUri) && !hasPreloadProcess() && !schemeUri.contains("2018030502317859") && !schemeUri.contains("2018032302435038")) {
            LiteProcessServerManager.g().startLiteProcessAsync(-6);
        }
    }
}
