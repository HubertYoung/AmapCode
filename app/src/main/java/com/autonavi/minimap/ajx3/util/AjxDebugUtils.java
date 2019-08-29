package com.autonavi.minimap.ajx3.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.appmonitor.offline.TempEvent;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.alipay.sdk.app.statistic.c;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3DebugService;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.AjxConstant;
import com.autonavi.minimap.ajx3.analyzer.AjxAnalyzerDelegate;
import com.autonavi.minimap.ajx3.analyzer.AjxAnalyzerInterface;
import com.autonavi.minimap.ajx3.assistant.AjxAssistant;
import com.autonavi.minimap.ajx3.assistant.AjxAssistantApplication;
import com.autonavi.minimap.ajx3.assistant.AjxAssistantMenu;
import com.autonavi.minimap.ajx3.assistant.AjxAssistantMenu.OnMenuChangeListener;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsEngineObserver.InspectorHandler;
import com.autonavi.minimap.ajx3.debug.AjxInspector;
import com.autonavi.minimap.ajx3.debug.AjxSocketHandler;
import com.autonavi.minimap.ajx3.debug.DevToolLog;
import com.autonavi.minimap.ajx3.debug.EagleEyeUtil;
import com.autonavi.minimap.ajx3.inspect.OnRequestOpListener;
import com.autonavi.minimap.ajx3.log.LogBody;
import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.log.SocketStatusListener;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleDB;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleDB.OnDatabaseOpListener;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleLifeCycle;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleLocalStorage;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleLocalStorage.OnOpListener;
import com.autonavi.minimap.ajx3.modules.net.ModuleRequest;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.widget.AjxTransitionState;
import com.autonavi.minimap.ajx3.widget.AjxTransitionState.OnPageLifeCircleListener;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import com.autonavi.minimap.auidebugger.qrcode.DownloadPage;
import com.autonavi.minimap.auidebugger.qrcode.QRCodePage;
import com.autonavi.minimap.auidebugger.qrcode.ScanCodePage;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.widget.switchview.SwitchButton;
import com.autonavi.widget.ui.AlertView.a;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class AjxDebugUtils implements OnLongClickListener, OnMenuChangeListener {
    private static final String DEBUG_SO_FLAG = "100";
    private static final String DEBUG_SO_FLAG_NEW = "debug";
    public static final int MODE_AJX = 0;
    public static final int MODE_JS = 1;
    public static final int MODE_JS_AJX = 2;
    private static int RES_MODE = 0;
    public static final String SP_KEY_ajx3_analyzer = "ajx3_analyzer";
    public static final String SP_KEY_ajx3_debugger = "ajx3_debugger";
    /* access modifiers changed from: private */
    public static boolean deleteing = false;
    public static boolean eyeOpen = false;
    public static boolean isChangeResToastOpen = false;
    private static boolean isInspectorInited;
    private static boolean isInspectorListenSetted;
    private static AjxAnalyzerInterface mAjxAnalyzerInterface;
    static AlertDialog perDialog;
    static AlertDialog resDialog;
    public static AjxDebugUtils sInstance;
    /* access modifiers changed from: private */
    public AjxSocketHandler ajxSocketHandler = new AjxSocketHandler(this);
    /* access modifiers changed from: private */
    public Activity mActivity;
    private MockReceiver mMockReceiver;
    private AmapAjxView mMockView;
    private WindowManager mWindowManager;

    /* renamed from: com.autonavi.minimap.ajx3.util.AjxDebugUtils$30 reason: invalid class name */
    static /* synthetic */ class AnonymousClass30 {
        static final /* synthetic */ int[] $SwitchMap$com$autonavi$minimap$alc$model$ALCLogLevel = new int[ALCLogLevel.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.autonavi.minimap.alc.model.ALCLogLevel[] r0 = com.autonavi.minimap.alc.model.ALCLogLevel.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$autonavi$minimap$alc$model$ALCLogLevel = r0
                int[] r0 = $SwitchMap$com$autonavi$minimap$alc$model$ALCLogLevel     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.autonavi.minimap.alc.model.ALCLogLevel r1 = com.autonavi.minimap.alc.model.ALCLogLevel.P1     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$autonavi$minimap$alc$model$ALCLogLevel     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.minimap.alc.model.ALCLogLevel r1 = com.autonavi.minimap.alc.model.ALCLogLevel.P2     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$autonavi$minimap$alc$model$ALCLogLevel     // Catch:{ NoSuchFieldError -> 0x002a }
                com.autonavi.minimap.alc.model.ALCLogLevel r1 = com.autonavi.minimap.alc.model.ALCLogLevel.P3     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$com$autonavi$minimap$alc$model$ALCLogLevel     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.autonavi.minimap.alc.model.ALCLogLevel r1 = com.autonavi.minimap.alc.model.ALCLogLevel.P4     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = $SwitchMap$com$autonavi$minimap$alc$model$ALCLogLevel     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.autonavi.minimap.alc.model.ALCLogLevel r1 = com.autonavi.minimap.alc.model.ALCLogLevel.P5     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.AjxDebugUtils.AnonymousClass30.<clinit>():void");
        }
    }

    public static class DebugCallback implements Callback {
        private String mClientId;
        private String mDebugIp;

        public DebugCallback(String str, String str2) {
            this.mDebugIp = str;
            this.mClientId = str2;
        }

        public boolean handleMessage(Message message) {
            if (message.what == 200) {
                startJsDebug();
                if (!LogManager.logOpen) {
                    EagleEyeUtil.openEagleEye();
                }
            }
            return true;
        }

        private void startJsDebug() {
            DevToolLog.log("Ajx.getInstance().startDebug invoke");
            Ajx.getInstance().stopDebug();
            Ajx.getInstance().startDebug(this.mDebugIp, this.mClientId);
            DevToolLog.log("Ajx.getInstance().startDebug succced");
        }
    }

    static class DeleteAsyncTask extends AsyncTask<String, Void, Boolean> {
        private AlertDialog alertDialog;
        private Activity mActivity;

        public DeleteAsyncTask(Activity activity) {
            this.mActivity = activity;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            this.alertDialog = new Builder(this.mActivity).create();
            this.alertDialog.setCancelable(false);
            this.alertDialog.setCanceledOnTouchOutside(false);
            this.alertDialog.show();
            this.alertDialog.setContentView(R.layout.deleteing_layout);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            this.alertDialog.dismiss();
            if (bool.booleanValue()) {
                Toast.makeText(this.mActivity, "已删除全部JS资源", 0).show();
            } else {
                Toast.makeText(this.mActivity, "删除失败", 0).show();
            }
            AjxDebugUtils.deleteing = false;
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(String... strArr) {
            boolean z;
            File file = new File(a.a);
            if (file.exists()) {
                z = cmk.a(file);
            } else {
                z = true;
            }
            return Boolean.valueOf(z);
        }
    }

    class MockReceiver extends BroadcastReceiver {
        private MockReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (!(intent == null || intent.getAction() == null || !intent.getAction().equals(Ajx3DebugService.ACTION_CREATE_LIFE_CYCLE_VIEW))) {
                String stringExtra = intent.getStringExtra("url");
                if (stringExtra != null) {
                    AjxDebugUtils.this.createLifeCycleAjxView(stringExtra);
                }
            }
        }
    }

    public static void bindAjx3DebugService() {
    }

    public static void changeResource(boolean z) {
    }

    public static boolean handleAjxDebugScheme(Uri uri) {
        return false;
    }

    public static boolean isRelease() {
        return true;
    }

    public static void sendDebugPageHideAction(String str) {
    }

    public static void unBindAjx3DebugService() {
    }

    public void onMenuCollapse(AjxAssistantMenu ajxAssistantMenu) {
    }

    public void onMenuExpand(AjxAssistantMenu ajxAssistantMenu) {
    }

    public static void updateResMode(int i) {
        RES_MODE = i;
        switch (i) {
            case 0:
                changeResource(true);
                break;
            case 1:
                changeResource(false);
                break;
            default:
                changeResource(AjxFileInfo.isReadFromAjxFile);
                break;
        }
        AMapAppGlobal.getApplication().getSharedPreferences("ajx_debugger", 0).edit().putInt("mLastReadSourceFromMode", RES_MODE).apply();
    }

    public static int getResMode() {
        return RES_MODE;
    }

    private AjxDebugUtils(Activity activity) {
        this.mActivity = activity;
        isChangeResToastOpen = AMapAppGlobal.getApplication().getSharedPreferences("ajx_ajx_debug_performance", 0).getBoolean("ajx_ajx_toast", false);
    }

    public static void showMenu() {
        if (sInstance == null) {
            bindAjx3DebugService();
            return;
        }
        Activity activity = null;
        bui mVPActivityContext = AMapPageUtil.getMVPActivityContext();
        if (mVPActivityContext != null) {
            activity = mVPActivityContext.a();
        }
        if (activity == null) {
            activity = AMapAppGlobal.getTopActivity();
        }
        AjxAssistant.showMenu(AjxAssistantApplication.ITEM, activity, sInstance);
    }

    public static boolean isMenuShown() {
        return AjxAssistant.isMeunShown();
    }

    public static void hideMenu() {
        AjxAssistant.dismissMenu();
    }

    public static int getLastReadSourceFromAjx() {
        return AMapAppGlobal.getApplication().getSharedPreferences("ajx_debugger", 0).getInt("mLastReadSourceFromMode", RES_MODE);
    }

    public static void debug(boolean z) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("amapuri://ajxdebug?startDebugService=".concat(String.valueOf(z))));
        DoNotUseTool.startScheme(intent);
    }

    public static void sendDebugPageShowAction(String str, boolean z) {
        if (RES_MODE == 2 && !"path://amap_bundle_globalvoice/src/card/globalvoice.page.js".equals(str)) {
            changeResource(z);
        }
    }

    public static boolean isAjx3DebugOpen() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(SP_KEY_ajx3_debugger, false);
    }

    public static void setAjx3DebugOpen(boolean z) {
        AjxFileInfo.isDebug = z;
        changeResource(true);
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(SP_KEY_ajx3_debugger, z);
    }

    public static boolean isAjxAnalyzerOpen() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(SP_KEY_ajx3_analyzer, false);
    }

    public static void setAjxAnalyzerOpen(boolean z) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(SP_KEY_ajx3_analyzer, z);
    }

    public static void addNoUrlLog(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        String allAjxFileVersion = AjxFileInfo.getAllAjxFileVersion();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" doesn't exist");
        LogManager.aLog(-1, 2, 4, currentTimeMillis, "生命周期", "", allAjxFileVersion, str, sb.toString(), "", "", "");
    }

    public void openUrl(final String str) {
        this.mActivity.runOnUiThread(new Runnable() {
            public void run() {
                cny.a(AMapPageUtil.getAppContext(), str);
                AMapPageUtil.getPageContext().startPage(DownloadPage.class, (PageBundle) null);
                new StringBuilder("--AjxDebugUtils.openUrl :").append(str);
            }
        });
    }

    public void reloadUrl() {
        handleActionRefresh();
    }

    public static void handleQrCode(final String str) {
        aho.a(new Runnable() {
            public final void run() {
                AjxDebugUtils.debug(true);
                AbstractBasePage abstractBasePage = (AbstractBasePage) AMapPageUtil.getPageContext();
                if (str.endsWith(".js") || str.endsWith(".ajx") || str.endsWith(FilePathHelper.SUFFIX_DOT_ZIP) || str.endsWith(".tar.gz")) {
                    cny.a(abstractBasePage.getContext(), str);
                    abstractBasePage.finish();
                    abstractBasePage.startPage(DownloadPage.class, (PageBundle) null);
                    ToastHelper.showToast(str);
                    return;
                }
                if (str.startsWith("devTools")) {
                    AjxDebugUtils.handleDevToolsScheme(str, abstractBasePage);
                }
            }
        });
    }

    public static void handleDevToolsScheme(String str, AbstractBasePage abstractBasePage) {
        DevToolLog.log("scheme:".concat(String.valueOf(str)));
        if (Ajx.getInstance().isDebuggerSupported() || Ajx.getInstance().isPerformanceLogSupported()) {
            shakeWithDevTool(str, abstractBasePage.getContext());
        } else {
            ToastUtils.showToast("亲，当前引擎不支持调试呦！", 1);
        }
    }

    private static void shakeWithDevTool(String str, Context context) {
        String str2;
        Uri parse = Uri.parse(str);
        String queryParameter = parse.getQueryParameter("ws");
        if (!TextUtils.isEmpty(queryParameter)) {
            AjxInspector.writeStrToFileByAppend(AjxSocketHandler.IP_PATH, queryParameter);
        }
        String queryParameter2 = parse.getQueryParameter("type");
        if (TextUtils.isEmpty(queryParameter2) || !"USB".equalsIgnoreCase(queryParameter2)) {
            str2 = coa.a(context);
        } else {
            str2 = "127.0.0.1";
        }
        String str3 = null;
        String queryParameter3 = parse.getQueryParameter("http");
        if (!TextUtils.isEmpty(queryParameter3)) {
            String queryParameter4 = Uri.parse(queryParameter3).getQueryParameter("clientid");
            if (!TextUtils.isEmpty(queryParameter4)) {
                str3 = queryParameter4;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(queryParameter3);
        sb.append("&device=");
        sb.append(URLEncoder.encode(Build.MODEL));
        sb.append("&diu=");
        sb.append(URLEncoder.encode(NetworkParam.getTaobaoID()));
        sb.append("&host=");
        sb.append(str2);
        sb.append("&platform=android&type=");
        sb.append(queryParameter2);
        String sb2 = sb.toString();
        DevToolLog.log("get_url:".concat(String.valueOf(sb2)));
        coa.a(sb2, new DebugCallback(str2, str3));
    }

    public static void handleEagleEye() {
        if (eyeOpen) {
            EagleEyeUtil.openEagleEye();
        } else {
            EagleEyeUtil.closeEagleEye();
        }
    }

    private void initAjxInspectorHandler() {
        if (!isInspectorInited) {
            isInspectorInited = true;
            Ajx.getInstance().setInpectorHandler(new InspectorHandler() {
                public void handleMessage(long j, String str) {
                    AjxDebugUtils.this.ajxSocketHandler.handleInspector(j, str);
                }
            });
        }
    }

    private void initLogSocketListener() {
        if (LogManager.mInit) {
            DevToolLog.log("alog not init");
        } else {
            LogManager.setSocketStatusListener(new SocketStatusListener() {
                public void onStatusChange(int i) {
                    DevToolLog.log("socketStatus ".concat(String.valueOf(i)));
                    if (i == 3) {
                        ToastUtils.showToast(Ajx3DebugService.CLOSE_DEBUG_SOCKET, 1);
                        AjxDebugUtils.this.registerALCLogListener();
                        return;
                    }
                    ToastUtils.showToast("鹰眼日志关闭", 1);
                    AjxDebugUtils.this.unRegisterALCLogListener();
                }

                public void onMsgRecv(String str) {
                    AjxDebugUtils.this.ajxSocketHandler.handleCmd(str);
                }
            });
        }
    }

    public static void handlePerformance(final Activity activity) {
        if (perDialog == null || !perDialog.isShowing()) {
            View inflate = LayoutInflater.from(activity).inflate(R.layout.ajx_performace_layout, null);
            final EditText editText = (EditText) inflate.findViewById(R.id.ajx_eyeip);
            editText.setHint("请输入IP地址");
            String fileContent = AjxInspector.getFileContent(AjxSocketHandler.IP_PATH);
            if (TextUtils.isEmpty(fileContent)) {
                fileContent = "10.125.12.238";
            }
            editText.setText(fileContent);
            if (activity != null && !activity.isFinishing()) {
                AlertDialog show = new Builder(activity, 5).setTitle("调试设置").setView(inflate).setPositiveButton("确定", new OnClickListener() {
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        AjxDebugUtils.perDialog = null;
                        String obj = editText.getText().toString();
                        if (!TextUtils.isEmpty(obj)) {
                            AjxInspector.writeStrToFileByAppend(AjxSocketHandler.IP_PATH, obj);
                        }
                    }
                }).show();
                perDialog = show;
                if (show.getWindow() != null) {
                    perDialog.getWindow().setSoftInputMode(2);
                }
                perDialog.setCanceledOnTouchOutside(false);
                perDialog.setOnDismissListener(new OnDismissListener() {
                    public final void onDismiss(DialogInterface dialogInterface) {
                        AjxDebugUtils.perDialog = null;
                    }
                });
                SwitchButton switchButton = (SwitchButton) inflate.findViewById(R.id.ajx_engine_eagle);
                SwitchButton switchButton2 = (SwitchButton) inflate.findViewById(R.id.ajx_engine_performance);
                SwitchButton switchButton3 = (SwitchButton) inflate.findViewById(R.id.ajx_engine_js_debug);
                SwitchButton switchButton4 = (SwitchButton) inflate.findViewById(R.id.ajx_eye);
                ((TextView) inflate.findViewById(R.id.msg)).setText(Ajx.getInstance().isPerformanceLogSupported() ? "当前调试配置可用" : "当前调试配置不可用");
                switchButton4.setChecked(eyeOpen);
                switchButton4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (AjxDebugUtils.eyeOpen != z) {
                            AjxDebugUtils.eyeOpen = z;
                            AjxDebugUtils.handleEagleEye();
                        }
                    }
                });
                boolean isPerformanceLogSupported = Ajx.getInstance().isPerformanceLogSupported();
                boolean performanceLogEnabled = Ajx.getInstance().getPerformanceLogEnabled();
                boolean isEagleEyeEnable = Ajx.getInstance().isEagleEyeEnable();
                boolean isDebuggerSupported = Ajx.getInstance().isDebuggerSupported();
                switchButton2.setEnabled(isPerformanceLogSupported);
                switchButton.setEnabled(isPerformanceLogSupported);
                switchButton2.setChecked(performanceLogEnabled);
                switchButton.setChecked(isEagleEyeEnable);
                switchButton3.setChecked(isDebuggerSupported);
                switchButton3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (z) {
                            if (Ajx.getInstance().isDebuggerSupported()) {
                                ToastHelper.showLongToast("当前引擎可以js调试，无需勾选");
                                return;
                            }
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putString(H5AppUtil.down_type, H5Param.SHOW_OPTION_MENU);
                            if (AjxDebugUtils.perDialog != null) {
                                AjxDebugUtils.perDialog.dismiss();
                            }
                            AMapPageUtil.getPageContext().startPage(DownloadPage.class, pageBundle);
                        } else if (Ajx.getInstance().isDebuggerSupported()) {
                            if (AjxConstant.AAR_VERSION.contains(AjxDebugUtils.DEBUG_SO_FLAG) || AjxConstant.AAR_VERSION.contains("debug")) {
                                ToastHelper.showLongToast("当前引擎可以js调试，不能手动关闭");
                            } else {
                                StringBuilder sb = new StringBuilder();
                                sb.append(activity.getFilesDir().getPath());
                                sb.append("/libajx_v3.so");
                                File file = new File(sb.toString());
                                if (file.exists()) {
                                    file.delete();
                                    ToastHelper.showToast("已关闭js调试，重启后生效！", 1);
                                    b.a();
                                }
                            }
                        }
                        AjxDebugConfig.getInstance().setJsDebug(z);
                    }
                });
                if (AjxConstant.AAR_VERSION.contains(DEBUG_SO_FLAG) || AjxConstant.AAR_VERSION.contains("debug")) {
                    switchButton3.setClickable(false);
                    switchButton3.setEnabled(false);
                }
                switchButton2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        Ajx.getInstance().setPerformanceLogEnabled(z);
                        AjxDebugConfig.getInstance().setPerformance(z);
                    }
                });
                switchButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        Ajx.getInstance().setEagleEyeEnable(z);
                        AjxDebugConfig.getInstance().setUiDebug(z);
                    }
                });
                if (!isPerformanceLogSupported) {
                    inflate.findViewById(R.id.ajx_engine_eagle_container).setOnClickListener(new View.OnClickListener() {
                        public final void onClick(View view) {
                            ToastUtils.showToast("调试可配置已关闭，不可以修改哦~", 1);
                        }
                    });
                    inflate.findViewById(R.id.ajx_engine_performance_container).setOnClickListener(new View.OnClickListener() {
                        public final void onClick(View view) {
                            ToastUtils.showToast("调试可配置已关闭，不可以修改哦~", 1);
                        }
                    });
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void registerALCLogListener() {
        try {
            cjy.a = new cjz() {
                public void onRecord(ALCLogLevel aLCLogLevel, long j, String str, String str2, int i, String str3) {
                }

                public void onLog(ALCLogLevel aLCLogLevel, String str, String str2, String str3, String str4, String str5) {
                    int i;
                    switch (AnonymousClass30.$SwitchMap$com$autonavi$minimap$alc$model$ALCLogLevel[aLCLogLevel.ordinal()]) {
                        case 1:
                            i = 4;
                            break;
                        case 2:
                            i = 3;
                            break;
                        case 3:
                            i = 2;
                            break;
                        case 4:
                            i = 1;
                            break;
                        default:
                            i = 0;
                            break;
                    }
                    JSONObject jSONObject = new JSONObject();
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put(H5PermissionManager.level, i);
                        jSONObject2.put(TempEvent.TAG_MODULE, str);
                        jSONObject2.put(c.b, str2);
                        jSONObject2.put("page", str3);
                        jSONObject2.put("event", str4);
                        jSONObject2.put("desc", str5);
                        jSONObject.put("ALCLog", jSONObject2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    LogManager.log(new LogBody.Builder().setLogLevel(i).setTag("ALCLog").setMsg(jSONObject.toString()).setLogType(3).setContextId(-1024).setAjxFileVersion(AjxFileInfo.getAllAjxFileBaseVersion()).build());
                }
            };
        } catch (Throwable unused) {
        }
    }

    private void registerInspectorListener() {
        if (!isInspectorListenSetted) {
            isInspectorListenSetted = true;
            AjxTransitionState.setPageLifeCircleListener(new OnPageLifeCircleListener() {
                public void onPageShow(boolean z, Object obj) {
                }

                public void onPageHide(boolean z) {
                    if (!z) {
                        AjxInspector.clearNetCacheDir();
                    }
                }
            });
            AjxModuleDB.setOnDatabaseOpListener(new OnDatabaseOpListener() {
                public void onDatabaseOpend(String str, String str2) {
                    AjxInspector.databaseAdd(str, str2);
                }
            });
            AjxModuleLocalStorage.setOnOpListener(new OnOpListener() {
                public void onNamespaceAdd(String str) {
                    AjxInspector.namespaceAdded(str);
                }

                public void onDomStorageItemUpdated(String str, String str2, String str3, String str4) {
                    AjxInspector.domStorageItemUpdated(str, str2, str3, str4);
                }

                public void onDomStorageItemAdded(String str, String str2, String str3) {
                    AjxInspector.domStorageItemAdded(str, str2, str3);
                }

                public void onDomStorageItemRemoved(String str, String str2) {
                    AjxInspector.domStorageItemRemoved(str, str2);
                }
            });
            AjxView.setOnRequestOpListener(new OnRequestOpListener() {
                public void onRequestWillBeSend(String str, String str2, String str3, JSONObject jSONObject, String str4, String str5) {
                    AjxInspector.requestWillBeSend(str, str2, str3, jSONObject, str4, str5);
                }

                public void onResponseReceived(String str, String str2, int i, String str3, JSONObject jSONObject, JSONObject jSONObject2, String str4, String str5) {
                    AjxInspector.responseReceived(str, str2, i, str3, jSONObject, jSONObject2, str4);
                    if (str5 != null) {
                        AjxInspector.writeStrToFileByAppend(AjxInspector.generateFileName(str), str5);
                    }
                }

                public void onLoadingFinished(String str, String str2) {
                    AjxInspector.loadingFinished(str, str2);
                }
            });
            ModuleRequest.setOnRequestOpListener(new OnRequestOpListener() {
                public void onRequestWillBeSend(String str, String str2, String str3, JSONObject jSONObject, String str4, String str5) {
                    AjxInspector.requestWillBeSend(str, str2, str3, jSONObject, str4, str5);
                }

                public void onResponseReceived(String str, String str2, int i, String str3, JSONObject jSONObject, JSONObject jSONObject2, String str4, String str5) {
                    AjxInspector.responseReceived(str, str2, i, str3, jSONObject, jSONObject2, str4);
                    if (str5 != null) {
                        AjxInspector.writeStrToFileByAppend(AjxInspector.generateFileName(str), str5);
                    }
                }

                public void onLoadingFinished(String str, String str2) {
                    AjxInspector.loadingFinished(str, str2);
                }
            });
        }
    }

    private void unRegisterInspectorListener() {
        AjxTransitionState.setPageLifeCircleListener(null);
        AjxModuleDB.setOnDatabaseOpListener(null);
        AjxModuleLocalStorage.setOnOpListener(null);
        AjxView.setOnRequestOpListener(null);
        ModuleRequest.setOnRequestOpListener(null);
    }

    private void registerMockReceiver() {
        if (this.mMockReceiver == null) {
            IntentFilter intentFilter = new IntentFilter(Ajx3DebugService.ACTION_CREATE_LIFE_CYCLE_VIEW);
            this.mMockReceiver = new MockReceiver();
            this.mActivity.registerReceiver(this.mMockReceiver, intentFilter);
            this.mWindowManager = (WindowManager) this.mActivity.getApplication().getSystemService(TemplateTinyApp.WINDOW_KEY);
        }
    }

    private void unregisterMockReceiver() {
        if (this.mMockReceiver != null) {
            try {
                this.mActivity.unregisterReceiver(this.mMockReceiver);
            } catch (IllegalArgumentException unused) {
            }
            this.mMockReceiver = null;
        }
        if (this.mMockView != null) {
            this.mWindowManager.removeView(this.mMockView);
            destroyLifeCycleAjxView();
        }
    }

    /* access modifiers changed from: private */
    public void createLifeCycleAjxView(String str) {
        if (this.mMockView != null) {
            destroyLifeCycleAjxView();
        }
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.type = VERSION.SDK_INT >= 26 ? 2038 : 2002;
        layoutParams.format = 1;
        layoutParams.flags = 8;
        layoutParams.gravity = 8388659;
        layoutParams.x = 0;
        cnu.a();
        layoutParams.y = (int) cnu.a(120.0f);
        layoutParams.width = 1;
        layoutParams.height = 1;
        this.mMockView = new AmapAjxView(this.mActivity);
        this.mMockView.setSendLifeCycle(true);
        this.mWindowManager.addView(this.mMockView, layoutParams);
        this.mMockView.load(str, null, "debugger");
    }

    private void destroyLifeCycleAjxView() {
        IAjxContext ajxContext = this.mMockView.getAjxContext();
        if (ajxContext != null) {
            ((AjxModuleLifeCycle) Ajx.getInstance().getModuleIns(ajxContext, "ajx.lifecycle")).unregisterLifeCycle();
        }
        this.mMockView.setSendLifeCycle(false);
        this.mMockView.destroy();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0099, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009a, code lost:
        r3 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x009c, code lost:
        if (r1 == false) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a0, code lost:
        if (resDialog == null) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a8, code lost:
        if (resDialog.isShowing() == false) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00aa, code lost:
        resDialog.dismiss();
        resDialog = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00b1, code lost:
        if (r3 == false) goto L_0x00c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b5, code lost:
        if (perDialog == null) goto L_0x00c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00bd, code lost:
        if (perDialog.isShowing() == false) goto L_0x00c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00bf, code lost:
        perDialog.dismiss();
        perDialog = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00c6, code lost:
        if (r5 == false) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00c8, code lost:
        com.autonavi.minimap.ajx3.analyzer.AjxAnalyzerDelegate.dismiss();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00cb, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMenuItemClick(com.autonavi.minimap.ajx3.assistant.AjxAssistantMenu r5, int r6) {
        /*
            r4 = this;
            boolean r0 = deleteing
            r1 = 1
            if (r0 == 0) goto L_0x000c
            java.lang.String r5 = "正在删除资源"
            com.autonavi.minimap.ajx3.util.ToastUtils.showToast(r5, r1)
            return
        L_0x000c:
            android.app.Activity r0 = r4.mActivity
            if (r0 == 0) goto L_0x00cc
            android.app.Activity r0 = r4.mActivity
            boolean r0 = r0.isFinishing()
            if (r0 == 0) goto L_0x001a
            goto L_0x00cc
        L_0x001a:
            java.lang.String[] r0 = com.autonavi.minimap.ajx3.assistant.AjxAssistantApplication.ITEM
            r6 = r0[r6]
            r0 = -1
            int r2 = r6.hashCode()
            r3 = 0
            switch(r2) {
                case -2139027726: goto L_0x005f;
                case 678489: goto L_0x0054;
                case 811766: goto L_0x0049;
                case 1135395: goto L_0x003e;
                case 1141616: goto L_0x0033;
                case 1163658: goto L_0x0028;
                default: goto L_0x0027;
            }
        L_0x0027:
            goto L_0x0069
        L_0x0028:
            java.lang.String r2 = "返回"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0069
            r6 = 3
            goto L_0x006a
        L_0x0033:
            java.lang.String r2 = "设置"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0069
            r6 = 0
            goto L_0x006a
        L_0x003e:
            java.lang.String r2 = "诊断"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0069
            r6 = 2
            goto L_0x006a
        L_0x0049:
            java.lang.String r2 = "扫码"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0069
            r6 = 4
            goto L_0x006a
        L_0x0054:
            java.lang.String r2 = "刷新"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0069
            r6 = 5
            goto L_0x006a
        L_0x005f:
            java.lang.String r2 = "IDE\n调试"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0069
            r6 = 1
            goto L_0x006a
        L_0x0069:
            r6 = -1
        L_0x006a:
            switch(r6) {
                case 0: goto L_0x0090;
                case 1: goto L_0x0088;
                case 2: goto L_0x0080;
                case 3: goto L_0x007c;
                case 4: goto L_0x0075;
                case 5: goto L_0x006e;
                default: goto L_0x006d;
            }
        L_0x006d:
            goto L_0x0099
        L_0x006e:
            r5.collapse()
            r4.handleActionRefresh()
            goto L_0x0099
        L_0x0075:
            r5.collapse()
            r4.handleActionQRCode()
            goto L_0x0099
        L_0x007c:
            r4.handleActionReturn()
            goto L_0x0099
        L_0x0080:
            r5.collapse()
            r4.handleActionAnalyzer()
            r5 = 0
            goto L_0x009a
        L_0x0088:
            r5.collapse()
            r4.handleActionIDE()
            r5 = 1
            goto L_0x009b
        L_0x0090:
            r5.collapse()
            r4.handleActionResSetting()
            r5 = 1
            r1 = 0
            goto L_0x009a
        L_0x0099:
            r5 = 1
        L_0x009a:
            r3 = 1
        L_0x009b:
            r6 = 0
            if (r1 == 0) goto L_0x00b1
            android.app.AlertDialog r0 = resDialog
            if (r0 == 0) goto L_0x00b1
            android.app.AlertDialog r0 = resDialog
            boolean r0 = r0.isShowing()
            if (r0 == 0) goto L_0x00b1
            android.app.AlertDialog r0 = resDialog
            r0.dismiss()
            resDialog = r6
        L_0x00b1:
            if (r3 == 0) goto L_0x00c6
            android.app.AlertDialog r0 = perDialog
            if (r0 == 0) goto L_0x00c6
            android.app.AlertDialog r0 = perDialog
            boolean r0 = r0.isShowing()
            if (r0 == 0) goto L_0x00c6
            android.app.AlertDialog r0 = perDialog
            r0.dismiss()
            perDialog = r6
        L_0x00c6:
            if (r5 == 0) goto L_0x00cb
            com.autonavi.minimap.ajx3.analyzer.AjxAnalyzerDelegate.dismiss()
        L_0x00cb:
            return
        L_0x00cc:
            java.lang.String r5 = "Activity dead"
            com.autonavi.minimap.ajx3.util.ToastUtils.showToast(r5, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.AjxDebugUtils.onMenuItemClick(com.autonavi.minimap.ajx3.assistant.AjxAssistantMenu, int):void");
    }

    public boolean onLongClick(View view) {
        return handleDumpPageStack();
    }

    private void handleActionResSetting() {
        if (resDialog == null || !resDialog.isShowing()) {
            View inflate = LayoutInflater.from(this.mActivity).inflate(R.layout.ajx_resmode_layout, null);
            final TextView textView = (TextView) inflate.findViewById(R.id.msg);
            SwitchButton switchButton = (SwitchButton) inflate.findViewById(R.id.ajx_resmode_js_ajx);
            SwitchButton switchButton2 = (SwitchButton) inflate.findViewById(R.id.ajx_resmode_js);
            SwitchButton switchButton3 = (SwitchButton) inflate.findViewById(R.id.ajx_resmode_ajx);
            SwitchButton switchButton4 = (SwitchButton) inflate.findViewById(R.id.ajx_resmode_toast);
            ((TextView) inflate.findViewById(R.id.test_web_config)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Ajx3UpgradeManager.getInstance().testWebCloudInterface();
                }
            });
            ((TextView) inflate.findViewById(R.id.test_init)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Ajx3UpgradeManager.getInstance().testInitInterface();
                }
            });
            ((TextView) inflate.findViewById(R.id.test_patch_info)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Ajx3UpgradeManager.getInstance().testPatchInfo();
                }
            });
            switchButton4.setChecked(isChangeResToastOpen);
            switchButton4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    AMapAppGlobal.getApplication().getSharedPreferences("ajx_ajx_debug_performance", 0).edit().putBoolean("ajx_ajx_toast", z).apply();
                    AjxDebugUtils.isChangeResToastOpen = z;
                }
            });
            ((TextView) inflate.findViewById(R.id.ajx_resmode_clear)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (!AjxDebugUtils.deleteing) {
                        if (AMapPageUtil.getTopPageClass() == DownloadPage.class) {
                            ToastHelper.showLongToast("下载中无法删除资源");
                            return;
                        }
                        AjxDebugUtils.deleteing = true;
                        new DeleteAsyncTask(AjxDebugUtils.this.mActivity).execute(new String[0]);
                    }
                }
            });
            ((TextView) inflate.findViewById(R.id.ajx_resmode_clear_ajx)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (!AjxDebugUtils.deleteing) {
                        if (AMapPageUtil.getTopPageClass() == DownloadPage.class) {
                            ToastHelper.showLongToast("下载中无法删除资源");
                            return;
                        }
                        AjxDebugUtils.deleteing = true;
                        ToastHelper.showLongToast("清除AJX中，成功后重启APP");
                        Ajx3UpgradeManager.getInstance().deleteAllDownloadBundle();
                        b.a();
                        AjxDebugUtils.deleteing = false;
                    }
                }
            });
            final boolean z = AjxFileInfo.isReadFromAjxFile;
            final SwitchButton switchButton5 = switchButton;
            final SwitchButton switchButton6 = switchButton2;
            final SwitchButton switchButton7 = switchButton3;
            AnonymousClass26 r2 = new View.OnClickListener() {
                public void onClick(View view) {
                    if (view == switchButton5) {
                        switchButton5.setChecked(true);
                        switchButton6.setChecked(false);
                        switchButton7.setChecked(false);
                        textView.setText("优先读取JS资源 兼容AJX");
                        AjxDebugUtils.updateResMode(2);
                        AjxDebugUtils.changeResource(z);
                    } else if (view == switchButton6) {
                        switchButton5.setChecked(false);
                        switchButton6.setChecked(true);
                        switchButton7.setChecked(false);
                        textView.setText("仅读取JS资源");
                        AjxDebugUtils.updateResMode(1);
                    } else {
                        if (view == switchButton7) {
                            switchButton5.setChecked(false);
                            switchButton6.setChecked(false);
                            switchButton7.setChecked(true);
                            textView.setText("仅读取AJX资源");
                            AjxDebugUtils.updateResMode(0);
                        }
                    }
                }
            };
            switch (RES_MODE) {
                case 0:
                    r2.onClick(switchButton3);
                    break;
                case 1:
                    r2.onClick(switchButton2);
                    break;
                case 2:
                    r2.onClick(switchButton);
                    break;
            }
            switchButton.setOnClickListener(r2);
            switchButton2.setOnClickListener(r2);
            switchButton3.setOnClickListener(r2);
            if (this.mActivity != null && !this.mActivity.isFinishing()) {
                AlertDialog show = new Builder(this.mActivity, 5).setTitle("扫码设置").setView(inflate).setPositiveButton("确定", new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AjxDebugUtils.resDialog = null;
                    }
                }).show();
                resDialog = show;
                show.setCanceledOnTouchOutside(false);
                resDialog.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss(DialogInterface dialogInterface) {
                        AjxDebugUtils.resDialog = null;
                    }
                });
            }
        }
    }

    private void handleActionIDE() {
        initLogSocketListener();
        initAjxInspectorHandler();
        handlePerformance(this.mActivity);
    }

    private void handleActionReturn() {
        ArrayList<akc> pagesStacks = AMapPageUtil.getPagesStacks();
        if (pagesStacks != null && pagesStacks.size() > 1) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.finish();
            }
        }
    }

    private void handleActionQRCode() {
        initLogSocketListener();
        initAjxInspectorHandler();
        registerInspectorListener();
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext instanceof AbstractBasePage) {
            final AbstractBasePage abstractBasePage = (AbstractBasePage) pageContext;
            Class<?> topPageClass = AMapPageUtil.getTopPageClass();
            if (topPageClass != ScanCodePage.class && topPageClass != QRCodePage.class) {
                if (topPageClass == DownloadPage.class) {
                    ToastHelper.showToast("请关闭下载页面后再打开扫码");
                    return;
                }
                kj.b(abstractBasePage.getActivity(), new String[]{"android.permission.CAMERA"}, new b() {
                    public void run() {
                        abstractBasePage.startPage(ScanCodePage.class, new PageBundle());
                    }
                });
            }
        }
    }

    private void handleActionRefresh() {
        String a = cny.a(this.mActivity);
        if (TextUtils.isEmpty(a)) {
            ToastHelper.showToast("The last scan url is null");
            return;
        }
        bid pageContext = AMapPageUtil.getPageContext();
        if (AMapPageUtil.getTopPageClass() != DownloadPage.class) {
            if ((pageContext instanceof ScanCodePage) || (pageContext instanceof QRCodePage)) {
                pageContext.finish();
            }
            if (pageContext != null) {
                pageContext.startPage(DownloadPage.class, (PageBundle) null);
                if (pageContext instanceof Ajx3Page) {
                    String ajx3Url = ((Ajx3Page) pageContext).getAjx3Url();
                    if (!TextUtils.isEmpty(ajx3Url)) {
                        int lastIndexOf = a.lastIndexOf("/");
                        int lastIndexOf2 = ajx3Url.lastIndexOf("/");
                        if (lastIndexOf > 0 && lastIndexOf2 > 0 && a.substring(lastIndexOf).equals(ajx3Url.substring(lastIndexOf2))) {
                            pageContext.finish();
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void handleOpenPage() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", "path://amap_bundle_messagebox/src/MessageBoxPage.page.js");
            pageContext.startPage(Ajx3Page.class, pageBundle);
        }
    }

    public static boolean isPerformanceOpen() {
        return AMapAppGlobal.getApplication().getSharedPreferences("ajx_ajx_debug_performance", 0).getBoolean("ajx_ajx_performance", false);
    }

    public static void setPerformanceOpen(boolean z) {
        AMapAppGlobal.getApplication().getSharedPreferences("ajx_ajx_debug_performance", 0).edit().putBoolean("ajx_ajx_performance", z).apply();
    }

    /* access modifiers changed from: private */
    public void unRegisterALCLogListener() {
        cjy.a = null;
    }

    private boolean handleDumpPageStack() {
        if (Ajx.getInstance().getPerformanceLogEnabled()) {
            ToastHelper.showToast("性能包暂时不提供诊断功能");
            return false;
        }
        bid pageContext = AMapPageFramework.getPageContext();
        if (pageContext == null) {
            return false;
        }
        Context context = pageContext.getContext();
        if (context == null) {
            return false;
        }
        a aVar = new a(context);
        aVar.a((CharSequence) "当前页面栈");
        aVar.b((CharSequence) AjxPageUtil.dumpPageStack());
        aVar.a(true);
        pageContext.showViewLayer(aVar.a());
        return true;
    }

    private void handleActionAnalyzer() {
        if (!Ajx.getInstance().getPerformanceLogEnabled()) {
            AjxAnalyzerDelegate.show();
        } else {
            ToastHelper.showToast("性能包暂时不提供诊断功能");
        }
    }
}
