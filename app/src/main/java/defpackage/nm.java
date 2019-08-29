package defpackage;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drive.ajx.module.ModuleRouteDriveResult;
import com.amap.bundle.drive.common.dialog.DriveDlgBaseManager;
import com.amap.bundle.drive.common.dialog.DriveDlgBaseManager.DialogId;
import com.amap.bundle.drive.cruise.page.AjxRouteCarCruisePage;
import com.amap.bundle.drive.entrance.NaviEntrancePage;
import com.amap.bundle.drive.navi.drivenavi.normal.page.AjxRouteCarNaviPage;
import com.amap.bundle.drive.navi.drivenavi.simulate.page.AjxRouteCarNaviSimulatePage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.PageContainer;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import com.iflytek.tts.TtsService.TTSIntitialDlgObserver;
import com.iflytek.tts.TtsService.TtsManager;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* renamed from: nm reason: default package */
/* compiled from: DriveManager */
public final class nm {
    static PageBundle a;
    private static mw b;

    public static void a(Activity activity, POI poi, ArrayList<POI> arrayList, POI poi2, String str) {
        PageBundle pageBundle = new PageBundle();
        a(pageBundle, 0, 0, poi, arrayList, poi2, true);
        pageBundle.putString("navi_type", str);
        a(activity, pageBundle, (List<POI>) arrayList, poi2);
    }

    public static boolean a(Activity activity, PageBundle pageBundle, List<POI> list, POI poi) {
        return a(activity, pageBundle, list, poi, null, -1);
    }

    public static boolean a(final Activity activity, PageBundle pageBundle, List<POI> list, POI poi, b bVar, int i) {
        if (poi != null) {
            if (!"我的位置".equals(poi.getName()) || !(list == null || list.size() == 0)) {
                String type = poi.getType();
                if (type != null) {
                    if (type.startsWith("1509")) {
                        poi.getPoiExtra().put(DriveUtil.POI_EXTRA_KEY_INT_PARKING, Integer.valueOf(3));
                    }
                    if (type.startsWith("0101")) {
                        poi.getPoiExtra().put(DriveUtil.POI_EXTRA_KEY_INT_PARKING, Integer.valueOf(5));
                    }
                }
                POI pOIHome = DriveUtil.getPOIHome();
                if (pOIHome != null) {
                    float a2 = cfe.a(pOIHome.getPoint(), poi.getPoint());
                    if (a2 >= 0.0f && a2 <= 100.0f) {
                        poi.getPoiExtra().put(DriveUtil.POI_EXTRA_KEY_INT_PARKING, Integer.valueOf(1));
                    }
                }
                POI pOICompany = DriveUtil.getPOICompany();
                if (pOICompany != null) {
                    float a3 = cfe.a(pOICompany.getPoint(), poi.getPoint());
                    if (a3 >= 0.0f && a3 <= 100.0f) {
                        poi.getPoiExtra().put(DriveUtil.POI_EXTRA_KEY_INT_PARKING, Integer.valueOf(2));
                    }
                }
            } else {
                ToastHelper.showLongToast(activity.getString(R.string.navi_to_cur_location));
                sa.a(i, 10005);
                d.a.a(i, 10009, (String) null);
                return false;
            }
        }
        final bid pageContext = AMapPageUtil.getPageContext();
        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
        if (iOfflineManager == null || !iOfflineManager.isOfflineDataReady() || !iOfflineManager.isOfflineDataUnzipping()) {
            int checkHasGps = DriveUtil.checkHasGps(AMapAppGlobal.getApplication());
            if (checkHasGps != 100) {
                a aVar = new a(AMapAppGlobal.getApplication());
                aVar.a(checkHasGps).a(R.string.settings, (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        if (pageContext != null) {
                            pageContext.dismissViewLayer(alertView);
                        }
                        try {
                            Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                            intent.setFlags(268435456);
                            activity.startActivityForResult(intent, 4098);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastHelper.showLongToast(activity.getString(R.string.autonavi_dlg_open_setting_failed));
                        }
                    }
                }).b(R.string.cancel, (a) new a(pageContext, activity, pageBundle, false) {
                    final /* synthetic */ bid a;
                    final /* synthetic */ Activity b;
                    final /* synthetic */ PageBundle c;
                    final /* synthetic */ boolean d = false;

                    {
                        this.a = r1;
                        this.b = r2;
                        this.c = r3;
                    }

                    public final void onClick(AlertView alertView, int i) {
                        if (this.a != null) {
                            this.a.dismissViewLayer(alertView);
                        }
                        bfo bfo = (bfo) a.a.a(bfo.class);
                        if (bfo != null) {
                            bfo.g();
                        }
                        nm.a(this.c, this.d, (b) null, -1);
                    }
                });
                aVar.a(true);
                if (pageContext != null) {
                    pageContext.showViewLayer(aVar.a());
                }
                sa.a(i, 10037);
                d.a.a(i, 10037, (String) null);
                return false;
            } else if (!agf.a() || agf.a(activity)) {
                a(pageBundle, false, bVar, i);
                return true;
            } else {
                a aVar2 = new a(AMapAppGlobal.getApplication());
                aVar2.a(R.string.drive_gps_close_title).a(R.string.sure, (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        if (pageContext != null) {
                            pageContext.dismissViewLayer(alertView);
                        }
                        try {
                            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                            intent.setFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
                            intent.putExtra("extra_pkgname", "com.autonavi.minimap");
                            activity.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                            ToastHelper.showToast(activity.getString(R.string.autonavi_dlg_open_setting_failed));
                        } catch (SecurityException e2) {
                            e2.printStackTrace();
                            ToastHelper.showToast(activity.getString(R.string.autonavi_dlg_open_setting_failed));
                        }
                    }
                }).b(R.string.cancel, (a) new a(activity, pageBundle, false) {
                    final /* synthetic */ Activity a;
                    final /* synthetic */ PageBundle b;
                    final /* synthetic */ boolean c = false;

                    {
                        this.a = r1;
                        this.b = r2;
                    }

                    public final void onClick(AlertView alertView, int i) {
                        bfo bfo = (bfo) a.a.a(bfo.class);
                        if (bfo != null) {
                            bfo.g();
                        }
                        nm.a(this.b, this.c, (b) null, -1);
                    }
                });
                aVar2.a(true);
                if (pageContext != null) {
                    pageContext.showViewLayer(aVar2.a());
                }
                sa.a(i, 10037);
                return false;
            }
        } else {
            a aVar3 = new a(AMapAppGlobal.getApplication());
            aVar3.a(R.string.offlinedata_install_msg).a(R.string.sure, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    if (pageContext != null) {
                        pageContext.dismissViewLayer(alertView);
                    }
                }
            });
            aVar3.a(true);
            if (pageContext != null) {
                pageContext.showViewLayer(aVar3.a());
            }
            sa.a(i, 10036);
            return false;
        }
    }

    static void a(PageBundle pageBundle, boolean z, b bVar, int i) {
        final Handler handler = new Handler(Looper.getMainLooper());
        final int i2 = i;
        final boolean z2 = z;
        final b bVar2 = bVar;
        final PageBundle pageBundle2 = pageBundle;
        AnonymousClass9 r0 = new TTSIntitialDlgObserver() {
            final TTSIntitialDlgObserver a = this;

            public final void TTSIntitialType(int i, final Object obj, int i2, String str) {
                if (i == 3) {
                    handler.post(new Runnable() {
                        public final void run() {
                            sa.a(i2, 10036);
                            ToastHelper.showToast("请先下载语音包");
                            IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
                            if (iVoicePackageManager != null) {
                                Intent intent = new Intent();
                                intent.putExtra(IVoicePackageManager.ENTRANCE_VOICE_SQUARE, true);
                                intent.putExtra(IVoicePackageManager.SHOW_TTS_FROM_KEY, 0);
                                iVoicePackageManager.deal(AMapPageUtil.getPageContext(), intent);
                            }
                        }
                    });
                } else if (i == 2) {
                    handler.post(new Runnable() {
                        public final void run() {
                            PageBundle pageBundle = (PageBundle) obj;
                            boolean z = z2;
                            b bVar = bVar2;
                            int i = i2;
                            String string = pageBundle != null ? pageBundle.getString("voiceRequestRouteMethod") : "";
                            if (pageBundle == null || !pageBundle.getBoolean("isFromRouteResult", false)) {
                                AnonymousClass11 r1 = new nn(string, i, pageBundle, z, bVar) {
                                    final /* synthetic */ String a;
                                    final /* synthetic */ int b;
                                    final /* synthetic */ PageBundle c;
                                    final /* synthetic */ boolean d = false;
                                    final /* synthetic */ boolean e;
                                    final /* synthetic */ b f;

                                    {
                                        this.a = r1;
                                        this.b = r2;
                                        this.c = r3;
                                        this.e = r4;
                                        this.f = r5;
                                    }

                                    public final void a(String str) {
                                        if (!TextUtils.isEmpty(this.a)) {
                                            if (TextUtils.equals(str, DriveUtil.NAVI_TYPE_TRUCK)) {
                                                DriveUtil.putTruckRoutingChoice(this.a);
                                            } else {
                                                DriveUtil.putLastRoutingChoice(this.a);
                                            }
                                        }
                                        if (TextUtils.equals(str, DriveUtil.NAVI_TYPE_TRUCK)) {
                                            StringBuilder sb = new StringBuilder(" 跳转到货车 tokenID： ");
                                            sb.append(this.b);
                                            tq.b("NaviMonitor", "MIT startNavi", sb.toString());
                                            d.a.a(this.b, 10000, (String) "");
                                        }
                                        this.c.putString("navi_type", str);
                                        String truckRoutingChoice = TextUtils.equals(str, DriveUtil.NAVI_TYPE_TRUCK) ? DriveUtil.getTruckRoutingChoice() : DriveUtil.getLastRoutingChoice();
                                        if (this.c.getInt("NaviMethod") == 0 && this.c.getInt("NaviFlags") == 0) {
                                            this.c.putInt("NaviMethod", dhw.d(truckRoutingChoice));
                                            this.c.putInt("NaviFlags", dhw.c(truckRoutingChoice));
                                        }
                                        nm.a(this.c, this.d, this.e, this.f, this.b);
                                    }
                                };
                                String truckCarPlateNumber = DriveUtil.getTruckCarPlateNumber();
                                PageBundle pageBundle2 = new PageBundle();
                                if (TextUtils.isEmpty(truckCarPlateNumber)) {
                                    r1.a("car");
                                    return;
                                }
                                String string2 = pageBundle.getString("navi_type");
                                if (!TextUtils.isEmpty(string2)) {
                                    r1.a(string2);
                                    return;
                                }
                                if (AMapAppGlobal.getTopActivity() instanceof buo) {
                                    bid pageContext = AMapPageUtil.getPageContext();
                                    if (pageContext != null) {
                                        pageBundle2.putObject("callback", r1);
                                        pageBundle2.putInt("mit_voice_tokenid", i);
                                        pageContext.startPage(NaviEntrancePage.class, pageBundle2);
                                        sa.a(i, 10038);
                                    }
                                }
                                return;
                            }
                            if (!TextUtils.isEmpty(string)) {
                                DriveUtil.putLastRoutingChoice(string);
                            }
                            nm.a(pageBundle, false, z, bVar, i);
                        }
                    });
                } else {
                    if (i == 1) {
                        handler.postDelayed(new Runnable() {
                            public final void run() {
                                sa.a(i2, 10036);
                                AnonymousClass9.this.SetObject(pageBundle2);
                                bfo bfo = (bfo) a.a.a(bfo.class);
                                if (bfo != null) {
                                    bfo.g();
                                }
                                TtsManager.getInstance().InitializeTTs(AnonymousClass9.this.a);
                            }
                        }, 300);
                    }
                }
            }
        };
        r0.SetObject(pageBundle);
        bfo bfo = (bfo) a.a.a(bfo.class);
        if (bfo != null) {
            bfo.g();
        }
        TtsManager.getInstance().InitializeTTs(r0);
    }

    public static void a() {
        final Handler handler = new Handler(Looper.getMainLooper());
        final bid pageContext = AMapPageUtil.getPageContext();
        if ((pageContext != null ? pageContext.getActivity() : null) != null) {
            AnonymousClass10 r2 = new TTSIntitialDlgObserver() {
                public final void TTSIntitialType(int i, Object obj, int i2, String str) {
                    if (i == 2) {
                        handler.post(new Runnable() {
                            public final void run() {
                                pageContext.startPage(AjxRouteCarCruisePage.class, new PageBundle());
                            }
                        });
                        return;
                    }
                    if (i == 3) {
                        handler.post(new Runnable() {
                            public final void run() {
                                ToastHelper.showToast("请先下载语音包");
                                IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
                                if (iVoicePackageManager != null) {
                                    Intent intent = new Intent();
                                    intent.putExtra(IVoicePackageManager.ENTRANCE_VOICE_SQUARE, true);
                                    intent.putExtra(IVoicePackageManager.SHOW_TTS_FROM_KEY, 0);
                                    iVoicePackageManager.deal(AMapPageUtil.getPageContext(), intent);
                                }
                            }
                        });
                    }
                }
            };
            bfo bfo = (bfo) a.a.a(bfo.class);
            if (bfo != null) {
                bfo.g();
            }
            if (TtsManager.getInstance().getTtsInitState() == 2) {
                pageContext.startPage(AjxRouteCarCruisePage.class, new PageBundle());
            } else {
                TtsManager.getInstance().InitializeTTs(r2);
            }
        }
    }

    public static void a(PageBundle pageBundle, int i, int i2, POI poi, List<POI> list, POI poi2, boolean z) {
        Serializable serializable;
        pageBundle.putBoolean("IsSimNavi", false);
        pageBundle.putBoolean("tipNaviFlag", z);
        pageBundle.putInt("NaviMethod", i);
        pageBundle.putInt("NaviFlags", i2);
        POI poi3 = null;
        pageBundle.putSerializable("StartPOI", poi == null ? null : poi.clone());
        pageBundle.putSerializable("GPSPosition", LocationInstrument.getInstance().getLatestPosition());
        if (list == null) {
            serializable = null;
        } else {
            serializable = new ArrayList(list);
        }
        pageBundle.putSerializable("ThrouthPOI", serializable);
        if (poi2 != null) {
            poi3 = poi2.clone();
        }
        pageBundle.putSerializable("EndPOI", poi3);
        pageBundle.putBoolean("isOfflinePlan", false);
        pageBundle.putBoolean("isFromRouteResult", false);
    }

    static void a(PageBundle pageBundle, boolean z, boolean z2, final b bVar, int i) {
        if (ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_URL_KEY).contains("test")) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory());
            sb.append("/autonavi/gdtbtlog/");
            File file = new File(sb.toString());
            if (!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
        }
        if (AMapAppGlobal.getTopActivity() instanceof buo) {
            final bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null) {
                sa.a(i, (int) SDKFactory.getCoreType);
                return;
            }
            pageBundle.putInt("voice_tokenid", i);
            if (!z2) {
                final String string = pageBundle.getString("navi_type", "car");
                boolean a2 = tf.a();
                boolean a3 = tf.a("agree_navi_declare_info", "truck_agree_navi_declare", false);
                if ((!a2 && !TextUtils.equals(string, DriveUtil.NAVI_TYPE_TRUCK)) || (!a3 && TextUtils.equals(string, DriveUtil.NAVI_TYPE_TRUCK))) {
                    if (b == null) {
                        b = new mw(pageContext, string);
                    } else {
                        b.a(pageContext);
                    }
                    b.e = string;
                    if (b.a(DialogId.DLG_CHECK_NAVIGATION_PROTOCOL)) {
                        a = pageBundle;
                    } else {
                        a = null;
                        mw mwVar = b;
                        DialogId dialogId = DialogId.DLG_CHECK_NAVIGATION_PROTOCOL;
                        final b bVar2 = bVar;
                        final boolean z3 = z;
                        final PageBundle pageBundle2 = pageBundle;
                        AnonymousClass3 r1 = new DriveDlgBaseManager.a() {
                            public final void a() {
                                if (TextUtils.equals(string, DriveUtil.NAVI_TYPE_TRUCK)) {
                                    tf.b("agree_navi_declare_info", "truck_agree_navi_declare", true);
                                } else {
                                    tf.b("agree_navi_declare_info", "agree_navi_declare", true);
                                }
                                if (bVar2 != null) {
                                    bVar2.a(true);
                                }
                                if (nm.a != null) {
                                    nm.a(pageContext, nm.a, z3, false, bVar2);
                                    nm.a = null;
                                    return;
                                }
                                nm.a(pageContext, pageBundle2, z3, false, bVar2);
                            }
                        };
                        mwVar.a(dialogId, new DriveDlgBaseManager.a() {
                            public final void a() {
                                if (bVar != null) {
                                    bVar.a(false);
                                }
                                bid bid = pageContext;
                                if (bid instanceof AbstractBasePage) {
                                    PageContainer pageContainer = ((AbstractBasePage) bid).getPageContainer();
                                    if (pageContainer instanceof axd) {
                                        IRouteUI routeInputUI = ((axd) pageContainer).getRouteInputUI();
                                        if (routeInputUI != null) {
                                            routeInputUI.s();
                                        }
                                    }
                                }
                            }
                        }, r1);
                    }
                    sa.a(i, (int) UCMPackageInfo.expectCreateDirFile2P);
                    return;
                }
            }
            a(pageContext, pageBundle, z, z2, bVar);
        }
    }

    public static boolean a(Activity activity, String str, b bVar) {
        return a(activity, str, bVar, (String) "car");
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00cc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(android.app.Activity r14, java.lang.String r15, defpackage.ms.b r16, java.lang.String r17) {
        /*
            r1 = r15
            java.lang.String r2 = ""
            int r3 = defpackage.dhw.d(r2)
            int r2 = defpackage.dhw.c(r2)
            com.autonavi.common.PageBundle r5 = new com.autonavi.common.PageBundle
            r5.<init>()
            java.lang.String r4 = "jsData"
            r5.putString(r4, r1)
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r4 = 0
            r7 = 0
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0084 }
            r8.<init>(r1)     // Catch:{ JSONException -> 0x0084 }
            java.lang.String r1 = "startPoi"
            java.lang.String r1 = r8.optString(r1)     // Catch:{ JSONException -> 0x0084 }
            java.lang.String r9 = "endPoi"
            java.lang.String r9 = r8.optString(r9)     // Catch:{ JSONException -> 0x0084 }
            java.lang.String r10 = "via"
            java.lang.String r10 = r8.optString(r10)     // Catch:{ JSONException -> 0x0084 }
            boolean r11 = android.text.TextUtils.isEmpty(r10)     // Catch:{ JSONException -> 0x0084 }
            if (r11 != 0) goto L_0x005e
            org.json.JSONArray r11 = new org.json.JSONArray     // Catch:{ JSONException -> 0x0059 }
            r11.<init>(r10)     // Catch:{ JSONException -> 0x0059 }
            int r10 = r11.length()     // Catch:{ JSONException -> 0x0059 }
            r12 = 0
        L_0x0043:
            if (r12 >= r10) goto L_0x005e
            java.lang.Object r13 = r11.get(r12)     // Catch:{ JSONException -> 0x0059 }
            org.json.JSONObject r13 = (org.json.JSONObject) r13     // Catch:{ JSONException -> 0x0059 }
            java.lang.String r13 = r13.toString()     // Catch:{ JSONException -> 0x0059 }
            com.autonavi.common.model.POI r13 = defpackage.bnx.a(r13)     // Catch:{ JSONException -> 0x0059 }
            r6.add(r13)     // Catch:{ JSONException -> 0x0059 }
            int r12 = r12 + 1
            goto L_0x0043
        L_0x0059:
            r0 = move-exception
            r10 = r0
            r10.printStackTrace()     // Catch:{ JSONException -> 0x0084 }
        L_0x005e:
            boolean r10 = android.text.TextUtils.isEmpty(r1)     // Catch:{ JSONException -> 0x0084 }
            if (r10 != 0) goto L_0x0069
            com.autonavi.common.model.POI r1 = defpackage.kv.a(r1)     // Catch:{ JSONException -> 0x0084 }
            goto L_0x006a
        L_0x0069:
            r1 = r7
        L_0x006a:
            boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ JSONException -> 0x007f }
            if (r10 != 0) goto L_0x0075
            com.autonavi.common.model.POI r9 = defpackage.kv.a(r9)     // Catch:{ JSONException -> 0x007f }
            goto L_0x0076
        L_0x0075:
            r9 = r7
        L_0x0076:
            int r8 = defpackage.sa.a(r8)     // Catch:{ JSONException -> 0x007c }
            r10 = r8
            goto L_0x008e
        L_0x007c:
            r0 = move-exception
            r8 = r1
            goto L_0x0082
        L_0x007f:
            r0 = move-exception
            r8 = r1
            r9 = r7
        L_0x0082:
            r1 = r0
            goto L_0x0088
        L_0x0084:
            r0 = move-exception
            r1 = r0
            r8 = r7
            r9 = r8
        L_0x0088:
            r1.printStackTrace()
            r1 = -1
            r1 = r8
            r10 = -1
        L_0x008e:
            java.lang.String r8 = "IsSimNavi"
            r5.putBoolean(r8, r4)
            java.lang.String r8 = "tipNaviFlag"
            r5.putBoolean(r8, r4)
            java.lang.String r8 = "NaviMethod"
            r5.putInt(r8, r3)
            java.lang.String r3 = "NaviFlags"
            r5.putInt(r3, r2)
            java.lang.String r2 = "StartPOI"
            if (r1 != 0) goto L_0x00a8
            r1 = r7
            goto L_0x00ac
        L_0x00a8:
            com.autonavi.common.model.POI r1 = r1.clone()
        L_0x00ac:
            r5.putSerializable(r2, r1)
            java.lang.String r1 = "GPSPosition"
            com.autonavi.sdk.location.LocationInstrument r2 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            com.autonavi.common.model.GeoPoint r2 = r2.getLatestPosition()
            r5.putSerializable(r1, r2)
            java.lang.String r1 = "ThrouthPOI"
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>(r6)
            r5.putSerializable(r1, r2)
            java.lang.String r1 = "EndPOI"
            if (r9 != 0) goto L_0x00cc
            r2 = r7
            goto L_0x00d0
        L_0x00cc:
            com.autonavi.common.model.POI r2 = r9.clone()
        L_0x00d0:
            r5.putSerializable(r1, r2)
            java.lang.String r1 = "isOfflinePlan"
            r5.putBoolean(r1, r4)
            java.lang.String r1 = "isFromRouteResult"
            r2 = 1
            r5.putBoolean(r1, r2)
            java.lang.String r1 = "calc_route_result"
            r5.putObject(r1, r7)
            java.lang.String r1 = "mIsMultiRoute"
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            r5.putObject(r1, r2)
            java.lang.String r1 = "navi_type"
            r2 = r17
            r5.putObject(r1, r2)
            r4 = r14
            r7 = r9
            r8 = r16
            r9 = r10
            boolean r1 = a(r4, r5, r6, r7, r8, r9)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.nm.a(android.app.Activity, java.lang.String, ms$b, java.lang.String):boolean");
    }

    /* access modifiers changed from: private */
    public static void a(bid bid, PageBundle pageBundle, boolean z, boolean z2, b bVar) {
        Class cls;
        bck bck = (bck) a.a.a(bck.class);
        if (bck != null) {
            bck.b();
        }
        if (z2) {
            cls = AjxRouteCarNaviSimulatePage.class;
            if (TextUtils.equals(pageBundle.getString("navi_type"), "car")) {
                pageBundle.putString("url", ModuleRouteDriveResult.CAR_MOCK_NAVI);
            }
        } else {
            if (bVar instanceof a) {
                ((a) bVar).a();
            }
            cls = AjxRouteCarNaviPage.class;
            pageBundle.putString("url", ModuleRouteDriveResult.CAR_NAVI);
        }
        if (z) {
            bid.finish();
            bid.startPage(cls, pageBundle);
            return;
        }
        bid.startPageForResult(cls, pageBundle, (int) MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
    }
}
