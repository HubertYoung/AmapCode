package com.autonavi.minimap.ajx3;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.mobile.beehive.photo.view.RemotePhotoGridView;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.device.DisplayType;
import com.autonavi.ae.AEUtil;
import com.autonavi.ae.ajx.AjxBLUIThread;
import com.autonavi.ae.ajx.AjxOverlayHelper;
import com.autonavi.ae.ajx.AjxOverlayHelper.INativeMapViewCallback;
import com.autonavi.ae.ajx.AjxOverlayHelper.IResourceConfigGetter;
import com.autonavi.ae.ajx.AjxOverlayHelper.ISnapShotGetter;
import com.autonavi.ae.ajx.AjxOverlayHelper.ITextureConfigGetter;
import com.autonavi.ae.ajx.AjxResourceConfig;
import com.autonavi.ae.ajx.AjxTextureConfig;
import com.autonavi.ae.ajx.tbt.CAjxBLBinaryCenter;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.inter.IMultipleServiceLoader;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.AjxConfig.Builder;
import com.autonavi.minimap.ajx3.ApplicationLifeCycle.APPLifeCycle;
import com.autonavi.minimap.ajx3.action.IActionLog;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.BaseJsServiceContextObserver;
import com.autonavi.minimap.ajx3.core.CloudConfig;
import com.autonavi.minimap.ajx3.core.JsEngineInstance;
import com.autonavi.minimap.ajx3.debug.AjxDebugConstant;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.Ajx3HttpLoadAction;
import com.autonavi.minimap.ajx3.loader.Ajx3LoaderExecutor;
import com.autonavi.minimap.ajx3.loader.Ajx3LruCache;
import com.autonavi.minimap.ajx3.loader.AjxMemoryDataPool.IDataPoolDelegate;
import com.autonavi.minimap.ajx3.loader.IAjxImageLoader;
import com.autonavi.minimap.ajx3.modules.IModuleWhiteList;
import com.autonavi.minimap.ajx3.modules.ModuleLog;
import com.autonavi.minimap.ajx3.modules.falcon.AjxModuleInfo;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.platform.ackor.IAjxFileReadListener;
import com.autonavi.minimap.ajx3.upgrade.Ajx3ActionLogUtil;
import com.autonavi.minimap.ajx3.upgrade.Ajx3ConfigConst;
import com.autonavi.minimap.ajx3.upgrade.Ajx3SpUtil;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager;
import com.autonavi.minimap.ajx3.util.AjxALCLog.ALCInterface;
import com.autonavi.minimap.ajx3.util.AjxDebugUtils;
import com.autonavi.minimap.ajx3.util.FileUtil;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.ModuleWhiteList;
import com.autonavi.minimap.ajx3.util.OpenThirdAppUtil;
import com.autonavi.minimap.ajx3.util.PathUtils;
import com.autonavi.minimap.ajx3.util.ToastUtils;
import com.autonavi.minimap.ajx3.views.Ajx3CircleChart;
import com.autonavi.minimap.ajx3.views.Ajx3LineChart;
import com.autonavi.minimap.ajx3.views.Ajx3LoadingView;
import com.autonavi.minimap.ajx3.views.Ajx3NavBar;
import com.autonavi.minimap.ajx3.views.Ajx3OfflineLabel;
import com.autonavi.minimap.ajx3.views.Ajx3RatingBar;
import com.autonavi.minimap.ajx3.views.Ajx3Switch;
import com.autonavi.minimap.ajx3.views.AmapAjxView.BackCallback;
import com.autonavi.minimap.ajx3.views.DefaultAjxViewSizeProvider;
import com.autonavi.minimap.ajx3.widget.AjxViewSizeProvider;
import com.autonavi.minimap.ajx3.widget.barchart.Ajx3Barchart;
import com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie;
import com.autonavi.minimap.ajx3.widget.scale.Ajx3ScaleView;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class AjxInit {
    private static final String SO_NAME = "ajx_v3";
    public static final String TAG = "AjxInit";
    private static AjxConfig ajxConfig = null;
    private static boolean fireworksStart = false;
    private static AjxBLUIThread sAjxBLUIThread = new AjxBLUIThread();
    public static boolean sIsAjxEngineInited = false;

    private static void loadAuiEngine() {
        System.loadLibrary(SO_NAME);
    }

    private static boolean loadEngineFromSdCard(String str) {
        if (ToastUtils.getContext() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(ToastUtils.getContext().getFilesDir().toString());
            sb.append(File.separator);
            sb.append(FileUtil.getSoFileName(str));
            String sb2 = sb.toString();
            File file = new File("/sdcard/", FileUtil.getSoFileName(str));
            if (file.exists()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("/sdcard/");
                sb3.append(FileUtil.getSoFileName(str));
                if (!FileUtil.copyFile(sb2, sb3.toString())) {
                    return false;
                }
                file.delete();
                try {
                    System.load(sb2);
                    StringBuilder sb4 = new StringBuilder("加载");
                    sb4.append("/sdcard/");
                    sb4.append("libajx_v3.so ");
                    ToastUtils.showToast(sb4.toString(), 1);
                    StringBuilder sb5 = new StringBuilder("警告:load libajx_v3.so from ");
                    sb5.append("/sdcard/");
                    sb5.append(" !!!");
                    LogHelper.addEngineLoge(sb5.toString());
                    return true;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    File file2 = new File(sb2);
                    if (file2.exists()) {
                        file2.delete();
                    }
                    return false;
                }
            } else if (!new File(sb2).exists()) {
                return false;
            } else {
                System.load(sb2);
                ToastUtils.showToast("加载".concat(String.valueOf(sb2)), 1);
                StringBuilder sb6 = new StringBuilder("警告:load libajx_v3.so from ");
                sb6.append(sb2);
                sb6.append(" !!!");
                LogHelper.addEngineLoge(sb6.toString());
                return true;
            }
        } else {
            LogHelper.addEngineLoge("警告:ToastUtils 尚未初始化 !!!");
            return false;
        }
    }

    public static void initAjxBLEnvironment(final bty bty) {
        if (bty != null) {
            Ajx.getInstance().setAjxOverlayFactory(AjxOverlayHelper.getOverlayFactory(AjxOverlayHelper.createAjxOverlayHelper()));
            Ajx.getInstance().setMapViewControl(AjxOverlayHelper.getMapViewControl(0));
            AjxOverlayHelper.setMainEngineID(bty.ad());
            Ajx.getInstance().setAjxNodeControl(AjxOverlayHelper.getAjxNodeControl());
            Ajx.getInstance().setBusinessControl(AjxOverlayHelper.getBusinessControl());
            Ajx.getInstance().setTbtControl(AjxOverlayHelper.getTBTControl());
            AjxOverlayHelper.setTextureConfigGetter(new ITextureConfigGetter() {
                public final AjxTextureConfig getAjxTextureConfig(String str, boolean z) {
                    AMapLog.e(AjxInit.TAG, "路径=".concat(String.valueOf(str)));
                    return AjxInit.createTextureConfig(bty.d(), str, z);
                }
            });
            AjxOverlayHelper.setResourceConfigGetter(new IResourceConfigGetter() {
                public final AjxResourceConfig getAjxResourceConfig(String str) {
                    AMapLog.e(AjxInit.TAG, "getAjxResourceConfig 路径=".concat(String.valueOf(str)));
                    return AjxInit.createTextureConfig(bty.d(), str);
                }
            });
            AjxOverlayHelper.setSnapShotGetter(new ISnapShotGetter() {
                public final AjxTextureConfig getSnapShotData(long j, long j2) {
                    AMapLog.e("js", "overlay getSnapShotData context");
                    IAjxContext ajxContext = Ajx.getInstance().getAjxContext(j);
                    if (ajxContext == null || ajxContext.hasDestroy()) {
                        AMapLog.e("js", "error:overlay ajxContext null");
                        return null;
                    }
                    Bitmap syncNodeSnapshot = ajxContext.getDomTree().getAjxNodeManager().syncNodeSnapshot(j2);
                    if (syncNodeSnapshot == null) {
                        AMapLog.e("js", "error:overlay getSnapShotData bmp is null!");
                        return null;
                    }
                    ByteBuffer allocate = ByteBuffer.allocate(syncNodeSnapshot.getByteCount());
                    syncNodeSnapshot.copyPixelsToBuffer(allocate);
                    AjxTextureConfig ajxTextureConfig = new AjxTextureConfig();
                    ajxTextureConfig.data = allocate.array();
                    ajxTextureConfig.height = (float) syncNodeSnapshot.getHeight();
                    ajxTextureConfig.width = (float) syncNodeSnapshot.getWidth();
                    ajxTextureConfig.imgType = 0;
                    return ajxTextureConfig;
                }
            });
            AjxOverlayHelper.setNativeMapViewCallBack(new INativeMapViewCallback() {
                public final int createEagleEyeMapView(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, float f) {
                    return -1;
                }

                public final void destroyEagleEyeMapView(int i) {
                }

                public final long getAnimationObserver(int i) {
                    akq c = bty.c();
                    akq.b("getAnimationObserver: ".concat(String.valueOf(i)));
                    return c.d.getAnimationObserver(i);
                }
            });
            AjxOverlayHelper.setUiWorker(sAjxBLUIThread);
            sAjxBLUIThread.setStop(false);
        }
    }

    public static void destroy() {
        sAjxBLUIThread.setStop(true);
    }

    public static void updateAjxInfo(String str) {
        if (ajxConfig != null) {
            ajxConfig.getAjxFileInfo().updateAjxFileInfo(str);
        }
    }

    public static void initAuiEngine(Context context) {
        String str;
        String str2;
        final Context applicationContext = context.getApplicationContext();
        loadAuiEngine();
        Ajx3UpgradeManager.getInstance().init(applicationContext);
        boolean isAjx3DebugOpen = AjxDebugUtils.isAjx3DebugOpen();
        AEUtil.isAjx3Debug = isAjx3DebugOpen;
        AjxFileInfo.isDebug = isAjx3DebugOpen;
        AjxDebugConstant.UIAUTOMATOR_ALLOWED = false;
        boolean z = true;
        if (isAjx3DebugOpen) {
            str2 = getDebugBaseJsPath();
            str = "path://ajx_page.txt";
            LogHelper.init(true, applicationContext);
        } else {
            str2 = null;
            str = JsEngineInstance.VALUE_PAGE_CONFIG_PATH_DEFAULT;
        }
        Ajx3LoaderExecutor ajx3LoaderExecutor = new Ajx3LoaderExecutor();
        Builder imageLoader = new Builder().setActionLog(new IActionLog() {
            public final void actionLogV2(String str, String str2, JSONObject jSONObject) {
                LogManager.actionLogV2(str, str2, jSONObject);
            }
        }).setAjxPageConfigPath(str).setModuleConfigPath("asset://ajx_module.txt").setAppVersion(a.a().a).setBuildType(ConfigerHelper.getInstance().getNetCondition()).setBaseJsPath("path://base.js").setDebugBaseJsPath(str2).setLruCache(new Ajx3LruCache(ImageLoader.a(applicationContext).f)).setHttpLoadAction(new Ajx3HttpLoadAction(ajx3LoaderExecutor)).setAjxFileInfo(new AjxFileInfo(Ajx3ConfigConst.AJX_FILE_BASE_DIR, Ajx3UpgradeManager.getInstance().getDiffDir(), Ajx3ConfigConst.AJX_FILE_DEFAULT_BASE_JS, Ajx3UpgradeManager.getInstance().getFileInfoMap())).setImageLoader(ajx3LoaderExecutor);
        if (agp.a(context) != DisplayType.CUTOUT) {
            z = false;
        }
        ajxConfig = imageLoader.setScreenIsCutout(z).setJsRuntimeExceptionListener(new IJsRuntimeExceptionListener() {
            public final void onRuntimeException(IAjxContext iAjxContext, int i, String str, String str2) {
                if (iAjxContext != null) {
                    iAjxContext.setRuntimeException(str2);
                }
                if (iAjxContext != null) {
                    try {
                        ModuleLog moduleLog = (ModuleLog) Ajx.getInstance().getModuleIns(iAjxContext, ModuleLog.MODULE_NAME);
                        if (moduleLog != null) {
                            moduleLog.transJsErrorMsg(i, str2, str);
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        }).setAjxFRListener(new IAjxFileReadListener() {
            public final void onOpenFailed(int i, String str) {
                Ajx3UpgradeManager.getInstance().reInitBundleInfo();
                if (AEUtil.isAjx3Debug) {
                    StringBuilder sb = new StringBuilder("errorCode = ");
                    sb.append(i);
                    sb.append(" , message: ");
                    sb.append(str);
                    AjxInit.showErrorMsg("Error: open ajx error! ", sb.toString());
                    return;
                }
                Ajx3ActionLogUtil.actionLogOpenAjxType(i, str);
            }

            public final void onOpenFailed(int i) {
                Ajx3UpgradeManager.getInstance().reInitBundleInfo();
                if (AEUtil.isAjx3Debug) {
                    AjxInit.showErrorMsg("Error: open ajx error! ", "errorCode = ".concat(String.valueOf(i)));
                } else {
                    Ajx3ActionLogUtil.actionLogOpenAjxType(i);
                }
            }

            public final void onReadFailed(String str, String str2) {
                if (AEUtil.isAjx3Debug) {
                    StringBuilder sb = new StringBuilder("filename = ");
                    sb.append(str);
                    sb.append(" \n ");
                    sb.append(str2);
                    AjxInit.showErrorMsg("Error: load ajx resource error!", sb.toString());
                    return;
                }
                Ajx3ActionLogUtil.actionLogReadFailed(str, str2);
                com.autonavi.minimap.ajx3.log.LogManager.jsErrorLog("Error: load ajx resource error: ".concat(String.valueOf(str)));
            }
        }).setDataPoolDelegate(new IDataPoolDelegate() {
            public final byte[] getDataBytes(long j) {
                return CAjxBLBinaryCenter.getBinaryDataBytes((int) j);
            }
        }).setCloudConfig(new CloudConfig() {
            public final String getConfig(final String str) {
                String a = lo.a().a(str);
                if (a != null) {
                    Ajx3SpUtil.setCloudConfig(applicationContext, str, a);
                } else {
                    a = Ajx3SpUtil.getCloudConfig(applicationContext, str);
                }
                lo.a().a(str, (lp) new lp() {
                    public void onConfigCallBack(int i) {
                    }

                    public void onConfigResultCallBack(int i, String str) {
                        Ajx3SpUtil.setCloudConfig(applicationContext, str, str);
                    }
                });
                return a;
            }
        }).setJsServiceContextObserver(new BaseJsServiceContextObserver() {
            /* access modifiers changed from: private */
            public HashMap<String, Ajx3ViewLayer> layers = new HashMap<>();
            private bid mainPage = null;

            public final void onOpen(String str, String str2, Object obj, String str3) {
                AjxInit.onJsServiceOpenPage(str, str2, obj, str3);
            }

            public final void onAddLayer(String str, final String str2, Object obj) {
                final bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null && AMapPageUtil.isHomePage()) {
                    this.mainPage = pageContext;
                    if (this.layers.get(str2) == null) {
                        Ajx3ViewLayer ajx3ViewLayer = new Ajx3ViewLayer(pageContext.getContext(), str, str2, obj);
                        ajx3ViewLayer.getView().setBackCallBack(new BackCallback() {
                            public void back(Object obj, String str) {
                                Ajx3ViewLayer ajx3ViewLayer = (Ajx3ViewLayer) AnonymousClass7.this.layers.remove(str2);
                                if (ajx3ViewLayer != null) {
                                    ajx3ViewLayer.destroy();
                                    pageContext.dismissViewLayer(ajx3ViewLayer);
                                }
                            }
                        });
                        pageContext.showViewLayer(ajx3ViewLayer);
                        this.layers.put(str2, ajx3ViewLayer);
                    }
                }
            }

            public final void onRemoveLayer(String str) {
                Ajx3ViewLayer remove = this.layers.remove(str);
                if (remove != null) {
                    remove.destroy();
                    if (this.mainPage != null) {
                        this.mainPage.dismissViewLayer(remove);
                    }
                }
            }
        }).setModuleWhiteList(new IModuleWhiteList() {
            public final String getModuleClassName(String str) {
                return ModuleWhiteList.getClassName(str);
            }

            public final boolean hasRegistered(String str, Class cls) {
                return ModuleWhiteList.check(str, cls);
            }

            public final Object[] getNewModule(String str) {
                return AjxModuleInfo.getModule(str);
            }
        }).setALCInterface(new ALCInterface() {
            public final void fatal(String str, String str2, String str3) {
                AMapLog.fatal(str, str2, str3);
            }

            public final void error(String str, String str2, String str3) {
                AMapLog.error(str, str2, str3);
            }

            public final void warning(String str, String str2, String str3) {
                AMapLog.warning(str, str2, str3);
            }

            public final void info(String str, String str2, String str3) {
                AMapLog.info(str, str2, str3);
            }

            public final void debug(String str, String str2, String str3) {
                AMapLog.debug(str, str2, str3);
            }

            public final void trace(String str, String str2, String str3) {
                AMapLog.trace(str, str2, str3);
            }

            public final void performance(String str, String str2, String str3) {
                AMapLog.performance(str, str2, str3);
            }
        }).build();
        Ajx.init(applicationContext, ajxConfig);
        Ajx.getInstance().registerView("circlechart", Ajx3CircleChart.class);
        Ajx.getInstance().registerView("linechart", Ajx3LineChart.class);
        Ajx.getInstance().registerView(FunctionSupportConfiger.SWITCH_TAG, Ajx3Switch.class);
        Ajx.getInstance().registerView(RemotePhotoGridView.LOADING_TAG, Ajx3LoadingView.class);
        Ajx.getInstance().registerView("navbar", Ajx3NavBar.class);
        Ajx.getInstance().registerView("offlinelabel", Ajx3OfflineLabel.class);
        Ajx.getInstance().registerView("rating", Ajx3RatingBar.class);
        Ajx.getInstance().registerView("barchart", Ajx3Barchart.class);
        Ajx.getInstance().registerView("lottie", Ajx3Lottie.class);
        Ajx.getInstance().registerView("scaleline", Ajx3ScaleView.class);
        Ajx.getInstance().registerCustomTypeface("Oswald-Regular", "font/regular.ttf");
        AjxViewSizeProvider.register(DefaultAjxViewSizeProvider.getInstance(context));
        List<Class<? extends T>> loadServices = ((IMultipleServiceLoader) bqn.a(IMultipleServiceLoader.class)).loadServices(ema.class);
        if (loadServices != null) {
            for (Class newInstance : loadServices) {
                try {
                    ema ema = (ema) newInstance.newInstance();
                    ema.onModuleRegister();
                    ema.onWidgetRegister();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Ajx.getInstance().setEagleEyeEnable(false);
            Ajx.getInstance().registerActionListener(new Ajx3ActionListener());
        }
    }

    public static void onEngineInitialized() {
        Ajx.getInstance().onAppLifeCycle(APPLifeCycle.APP_ON_AJX_REGISTER, "");
        startFireworksService(AMapPageUtil.getAppContext());
    }

    public static boolean startFireworksService(Context context) {
        if (sIsAjxEngineInited && !fireworksStart) {
            if (AjxFileInfo.isFileExists(Ajx.getInstance().lookupLoader("path://amap_bundle_activity_fireworks/src/pages/FireworksService.page.js").processingPath(PictureParams.make(null, "path://amap_bundle_activity_fireworks/src/pages/FireworksService.page.js", false)))) {
                Ajx.getInstance().startService("path://amap_bundle_activity_fireworks/src/pages/FireworksService.page.js", "path://amap_bundle_activity_fireworks/src/pages/FireworksService.page.js");
                fireworksStart = true;
            }
        }
        return fireworksStart;
    }

    /* access modifiers changed from: private */
    public static void onJsServiceOpenPage(String str, String str2, Object obj, String str3) {
        if (!TextUtils.isEmpty(str)) {
            ku a = ku.a();
            StringBuilder sb = new StringBuilder("onOpen url:");
            sb.append(str);
            sb.append("\ndata:");
            sb.append(obj);
            a.c("auiLog", sb.toString());
            if (str.startsWith("amapuri") || str.startsWith("androidamap")) {
                onCustomActon(str, obj);
            } else if (str.startsWith("appurl")) {
                OpenThirdAppUtil.openThirdApp((String) obj);
            } else {
                startPage(str, str2, obj, str3);
            }
        }
    }

    private static void onCustomActon(@NonNull String str, @Nullable Object obj) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(str));
        intent.putExtra("owner", BaseIntentDispatcher.INTENT_CALL_FROMOWNER);
        if (obj != null) {
            intent.putExtra("ajxData", String.valueOf(obj));
        }
        DoNotUseTool.startScheme(intent);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0037 A[Catch:{ Exception -> 0x004a }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004c A[SYNTHETIC, Splitter:B:24:0x004c] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x009a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void startPage(java.lang.String r5, java.lang.String r6, java.lang.Object r7, java.lang.String r8) {
        /*
            bid r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            r1 = 0
            if (r7 == 0) goto L_0x0049
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x0049 }
            r3 = r7
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Exception -> 0x0049 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0049 }
            java.lang.String r3 = "isDialogPage"
            boolean r3 = r2.has(r3)     // Catch:{ Exception -> 0x0049 }
            if (r3 == 0) goto L_0x002e
            java.lang.String r3 = "isDialogPage"
            java.lang.String r3 = r2.getString(r3)     // Catch:{ Exception -> 0x0049 }
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x0049 }
            if (r4 != 0) goto L_0x002e
            boolean r3 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r3)     // Catch:{ Exception -> 0x0049 }
            if (r3 == 0) goto L_0x002e
            r3 = 1
            goto L_0x002f
        L_0x002e:
            r3 = 0
        L_0x002f:
            java.lang.String r4 = "clearStack"
            boolean r4 = r2.has(r4)     // Catch:{ Exception -> 0x004a }
            if (r4 == 0) goto L_0x004a
            java.lang.String r4 = "clearStack"
            java.lang.Object r2 = r2.get(r4)     // Catch:{ Exception -> 0x004a }
            java.lang.String r4 = "1"
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x004a }
            boolean r2 = r4.equalsIgnoreCase(r2)     // Catch:{ Exception -> 0x004a }
            r1 = r2
            goto L_0x004a
        L_0x0049:
            r3 = 0
        L_0x004a:
            if (r1 == 0) goto L_0x007b
            esb r1 = defpackage.esb.a.a     // Catch:{ Exception -> 0x0065 }
            java.lang.Class<apr> r2 = defpackage.apr.class
            esc r1 = r1.a(r2)     // Catch:{ Exception -> 0x0065 }
            apr r1 = (defpackage.apr) r1     // Catch:{ Exception -> 0x0065 }
            if (r1 == 0) goto L_0x0061
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()     // Catch:{ Exception -> 0x0065 }
            r1.b(r2)     // Catch:{ Exception -> 0x0065 }
        L_0x0061:
            com.autonavi.minimap.widget.ConfirmDlgLifeCircle.removeAll()     // Catch:{ Exception -> 0x0065 }
            goto L_0x007b
        L_0x0065:
            r1 = move-exception
            java.lang.String r2 = "IntentController"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r1 = r1.getMessage()
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            com.amap.bundle.logs.AMapLog.e(r2, r1)
        L_0x007b:
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            com.autonavi.common.PageBundle r5 = com.autonavi.minimap.ajx3.util.AjxPageUtil.makePageBundle(r1, r5)
            java.lang.String r1 = "jsData"
            r5.putObject(r1, r7)
            java.lang.String r7 = "pageId"
            r5.putString(r7, r8)
            java.lang.String r7 = "env"
            r5.putString(r7, r6)
            if (r3 == 0) goto L_0x009a
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3DialogPage> r6 = com.autonavi.minimap.ajx3.Ajx3DialogPage.class
            r0.startPage(r6, r5)
            return
        L_0x009a:
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r6 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r0.startPage(r6, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.AjxInit.startPage(java.lang.String, java.lang.String, java.lang.Object, java.lang.String):void");
    }

    public static void showErrorMsg(final String str, final String str2) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            Activity activity = DoNotUseTool.getActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    public final void run() {
                        AjxInit.showErrorMsg(str, str2);
                    }
                });
            }
            return;
        }
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            String str3 = str == null ? "Error:js run exception!" : str;
            if (pageContext.getContext() != null) {
                a aVar = new a(pageContext.getContext());
                aVar.a((CharSequence) str3);
                aVar.b((CharSequence) str2.contains("minimap/files/js/") ? str2.substring(str2.indexOf("minimap/files/js/") + 17) : str2);
                aVar.a(17039370, (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        bid pageContext = AMapPageUtil.getPageContext();
                        if (pageContext != null) {
                            pageContext.dismissViewLayer(alertView);
                        }
                    }
                });
                aVar.c = new a() {
                    public final void onClick(AlertView alertView, int i) {
                        bid pageContext = AMapPageUtil.getPageContext();
                        if (pageContext != null) {
                            pageContext.dismissViewLayer(alertView);
                        }
                    }
                };
                aVar.b((CharSequence) "复制", (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        bid pageContext = AMapPageUtil.getPageContext();
                        if (pageContext != null) {
                            ((ClipboardManager) pageContext.getContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(str, str2));
                        }
                    }
                });
                AlertView a = aVar.a();
                pageContext.showViewLayer(a);
                a.startAnimation();
            }
        }
    }

    private static String getDebugBaseJsPath() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            StringBuilder sb = new StringBuilder();
            sb.append(a.a);
            sb.append("base.js");
            String sb2 = sb.toString();
            if (new File(sb2).exists()) {
                return sb2;
            }
        }
        return "asset://ajx_file_base/";
    }

    /* access modifiers changed from: private */
    public static AjxTextureConfig createTextureConfig(Context context, String str, boolean z) {
        float f;
        float f2;
        float f3;
        PictureParams pictureParams = new PictureParams();
        pictureParams.url = str;
        pictureParams.isNeedScale = !z;
        pictureParams.patchIndex = PathUtils.getPatchIndexByUrl(str);
        IAjxImageLoader lookupLoader = Ajx.getInstance().lookupLoader(str);
        byte[] loadImage = lookupLoader.loadImage(pictureParams);
        if (loadImage == null || loadImage.length <= 0) {
            return null;
        }
        int i = 2;
        if (z) {
            float[] readImageSize = lookupLoader.readImageSize(pictureParams);
            f = readImageSize[0];
            f3 = readImageSize[1];
            f2 = context.getResources().getDisplayMetrics().density / readImageSize[2];
        } else {
            f = (float) pictureParams.bitmapWidth;
            f3 = (float) pictureParams.bitmapHeight;
            f2 = 1.0f;
            i = 0;
        }
        AjxTextureConfig ajxTextureConfig = new AjxTextureConfig();
        ajxTextureConfig.data = loadImage;
        ajxTextureConfig.height = f3;
        ajxTextureConfig.width = f;
        ajxTextureConfig.imgType = i;
        ajxTextureConfig.scaleFactor = f2;
        return ajxTextureConfig;
    }

    /* access modifiers changed from: private */
    public static AjxResourceConfig createTextureConfig(Context context, String str) {
        PictureParams pictureParams = new PictureParams();
        pictureParams.url = str;
        pictureParams.patchIndex = PathUtils.getPatchIndexByUrl(str);
        byte[] loadImage = Ajx.getInstance().lookupLoader(str).loadImage(pictureParams);
        if (loadImage == null || loadImage.length <= 0) {
            return null;
        }
        AjxResourceConfig ajxResourceConfig = new AjxResourceConfig();
        ajxResourceConfig.data = loadImage;
        return ajxResourceConfig;
    }
}
