package defpackage;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drive.ajx.module.ModuleRouteDriveResult;
import com.amap.bundle.drive.common.dialog.DriveDlgBaseManager.DialogId;
import com.amap.bundle.drive.navi.motornavi.page.AjxRouteMotorNaviPage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.AlertView;
import com.iflytek.tts.TtsService.TTSIntitialDlgObserver;
import com.iflytek.tts.TtsService.TtsManager;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* renamed from: no reason: default package */
/* compiled from: MotorbikeManager */
public final class no {

    /* renamed from: no$a */
    /* compiled from: MotorbikeManager */
    public interface a {
    }

    private static boolean a(final Activity activity, final PageBundle pageBundle, List<POI> list, POI poi, a aVar) {
        if (poi == null || !"我的位置".equals(poi.getName()) || !(list == null || list.size() == 0)) {
            final bid pageContext = AMapPageUtil.getPageContext();
            IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
            if (iOfflineManager == null || !iOfflineManager.isOfflineDataReady() || !iOfflineManager.isOfflineDataUnzipping()) {
                int checkHasGps = DriveUtil.checkHasGps(AMapAppGlobal.getApplication());
                if (checkHasGps != 100) {
                    com.autonavi.widget.ui.AlertView.a aVar2 = new com.autonavi.widget.ui.AlertView.a(AMapAppGlobal.getApplication());
                    aVar2.a(checkHasGps).a(R.string.settings, (defpackage.ern.a) new defpackage.ern.a() {
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
                    }).b(R.string.cancel, (defpackage.ern.a) new defpackage.ern.a() {
                        final /* synthetic */ boolean d = false;

                        public final void onClick(AlertView alertView, int i) {
                            if (pageContext != null) {
                                pageContext.dismissViewLayer(alertView);
                            }
                            no.a(activity, pageBundle, this.d, (a) null);
                        }
                    });
                    aVar2.a(true);
                    if (pageContext != null) {
                        pageContext.showViewLayer(aVar2.a());
                    }
                    return false;
                } else if (!agf.a() || agf.a(activity)) {
                    a(activity, pageBundle, false, aVar);
                    return true;
                } else {
                    com.autonavi.widget.ui.AlertView.a aVar3 = new com.autonavi.widget.ui.AlertView.a(AMapAppGlobal.getApplication());
                    aVar3.a(R.string.drive_gps_close_title).a(R.string.sure, (defpackage.ern.a) new defpackage.ern.a() {
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
                    }).b(R.string.cancel, (defpackage.ern.a) new defpackage.ern.a() {
                        final /* synthetic */ boolean c = false;

                        public final void onClick(AlertView alertView, int i) {
                            no.a(activity, pageBundle, this.c, (a) null);
                        }
                    });
                    aVar3.a(true);
                    if (pageContext != null) {
                        pageContext.showViewLayer(aVar3.a());
                    }
                    return false;
                }
            } else {
                com.autonavi.widget.ui.AlertView.a aVar4 = new com.autonavi.widget.ui.AlertView.a(AMapAppGlobal.getApplication());
                aVar4.a(R.string.offlinedata_install_msg).a(R.string.sure, (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        if (pageContext != null) {
                            pageContext.dismissViewLayer(alertView);
                        }
                    }
                });
                aVar4.a(true);
                if (pageContext != null) {
                    pageContext.showViewLayer(aVar4.a());
                }
                return false;
            }
        } else {
            ToastHelper.showLongToast(activity.getString(R.string.navi_to_cur_location));
            return false;
        }
    }

    static void a(Activity activity, PageBundle pageBundle, boolean z, a aVar) {
        final Handler handler = new Handler(Looper.getMainLooper());
        final Activity activity2 = activity;
        final boolean z2 = z;
        final a aVar2 = aVar;
        final PageBundle pageBundle2 = pageBundle;
        AnonymousClass6 r0 = new TTSIntitialDlgObserver() {
            final TTSIntitialDlgObserver a = this;

            public final void TTSIntitialType(int i, final Object obj, int i2, String str) {
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
                } else if (i == 2) {
                    handler.post(new Runnable() {
                        public final void run() {
                            PageBundle pageBundle = (PageBundle) obj;
                            Activity activity = activity2;
                            a aVar = aVar2;
                            if (ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_URL_KEY).contains("test")) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(Environment.getExternalStorageDirectory());
                                sb.append("/autonavi/gdtbtlog/");
                                File file = new File(sb.toString());
                                if (!file.exists() || !file.isDirectory()) {
                                    file.mkdirs();
                                }
                            }
                            if (activity instanceof buo) {
                                bid pageContext = AMapPageUtil.getPageContext();
                                if (pageContext != null) {
                                    if (!tf.a("agree_navi_declare_info", "motorbike_agree_navi_declare", false)) {
                                        mw mwVar = new mw(pageContext, DriveUtil.NAVI_TYPE_MOTORBIKE);
                                        if (!mwVar.a(DialogId.DLG_CHECK_NAVIGATION_PROTOCOL)) {
                                            mwVar.a(DialogId.DLG_CHECK_NAVIGATION_PROTOCOL, new com.amap.bundle.drive.common.dialog.DriveDlgBaseManager.a(aVar) {
                                                final /* synthetic */ a a;

                                                public final void a() {
                                                }

                                                {
                                                    this.a = r1;
                                                }
                                            }, new com.amap.bundle.drive.common.dialog.DriveDlgBaseManager.a(aVar, pageContext, pageBundle) {
                                                final /* synthetic */ a a;
                                                final /* synthetic */ bid b;
                                                final /* synthetic */ PageBundle c;
                                                final /* synthetic */ boolean d = false;

                                                {
                                                    this.a = r1;
                                                    this.b = r2;
                                                    this.c = r3;
                                                }

                                                public final void a() {
                                                    tf.b("agree_navi_declare_info", "motorbike_agree_navi_declare", true);
                                                    no.a(this.b, this.c, this.d);
                                                }
                                            });
                                        }
                                        return;
                                    }
                                    no.a(pageContext, pageBundle, false);
                                }
                            }
                        }
                    });
                } else {
                    if (i == 1) {
                        handler.postDelayed(new Runnable() {
                            public final void run() {
                                AnonymousClass6.this.SetObject(pageBundle2);
                                bfo bfo = (bfo) defpackage.esb.a.a.a(bfo.class);
                                if (bfo != null) {
                                    bfo.g();
                                }
                                TtsManager.getInstance().InitializeTTs(AnonymousClass6.this.a);
                            }
                        }, 300);
                    }
                }
            }
        };
        r0.SetObject(pageBundle);
        bfo bfo = (bfo) defpackage.esb.a.a.a(bfo.class);
        if (bfo != null) {
            bfo.g();
        }
        TtsManager.getInstance().InitializeTTs(r0);
    }

    private static void a(PageBundle pageBundle, POI poi, List<POI> list, POI poi2) {
        Serializable serializable;
        Serializable serializable2;
        pageBundle.putBoolean("IsSimNavi", false);
        pageBundle.putBoolean("tipNaviFlag", true);
        pageBundle.putInt("NaviMethod", 0);
        pageBundle.putInt("NaviFlags", 0);
        POI poi3 = null;
        if (poi == null) {
            serializable = null;
        } else {
            serializable = poi.clone();
        }
        pageBundle.putSerializable("StartPOI", serializable);
        pageBundle.putSerializable("GPSPosition", LocationInstrument.getInstance().getLatestPosition());
        if (list == null) {
            serializable2 = null;
        } else {
            serializable2 = new ArrayList(list);
        }
        pageBundle.putSerializable("ThrouthPOI", serializable2);
        if (poi2 != null) {
            poi3 = poi2.clone();
        }
        pageBundle.putSerializable("EndPOI", poi3);
        pageBundle.putBoolean("isOfflinePlan", false);
        pageBundle.putBoolean("isFromRouteResult", false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00bd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(android.app.Activity r12, java.lang.String r13, defpackage.no.a r14) {
        /*
            java.lang.String r0 = ""
            int r1 = defpackage.dhw.d(r0)
            int r0 = defpackage.dhw.c(r0)
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle
            r2.<init>()
            java.lang.String r3 = "jsData"
            r2.putString(r3, r13)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r4 = 0
            r5 = 0
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0078 }
            r6.<init>(r13)     // Catch:{ JSONException -> 0x0078 }
            java.lang.String r13 = "startPoi"
            java.lang.String r13 = r6.optString(r13)     // Catch:{ JSONException -> 0x0078 }
            java.lang.String r7 = "endPoi"
            java.lang.String r7 = r6.optString(r7)     // Catch:{ JSONException -> 0x0078 }
            java.lang.String r8 = "via"
            java.lang.String r6 = r6.optString(r8)     // Catch:{ JSONException -> 0x0078 }
            boolean r8 = android.text.TextUtils.isEmpty(r6)     // Catch:{ JSONException -> 0x0078 }
            if (r8 != 0) goto L_0x005c
            org.json.JSONArray r8 = new org.json.JSONArray     // Catch:{ JSONException -> 0x0058 }
            r8.<init>(r6)     // Catch:{ JSONException -> 0x0058 }
            int r6 = r8.length()     // Catch:{ JSONException -> 0x0058 }
            r9 = 0
        L_0x0042:
            if (r9 >= r6) goto L_0x005c
            java.lang.Object r10 = r8.get(r9)     // Catch:{ JSONException -> 0x0058 }
            org.json.JSONObject r10 = (org.json.JSONObject) r10     // Catch:{ JSONException -> 0x0058 }
            java.lang.String r10 = r10.toString()     // Catch:{ JSONException -> 0x0058 }
            com.autonavi.common.model.POI r10 = defpackage.bnx.a(r10)     // Catch:{ JSONException -> 0x0058 }
            r3.add(r10)     // Catch:{ JSONException -> 0x0058 }
            int r9 = r9 + 1
            goto L_0x0042
        L_0x0058:
            r6 = move-exception
            r6.printStackTrace()     // Catch:{ JSONException -> 0x0078 }
        L_0x005c:
            boolean r6 = android.text.TextUtils.isEmpty(r13)     // Catch:{ JSONException -> 0x0078 }
            if (r6 != 0) goto L_0x0067
            com.autonavi.common.model.POI r13 = defpackage.kv.a(r13)     // Catch:{ JSONException -> 0x0078 }
            goto L_0x0068
        L_0x0067:
            r13 = r5
        L_0x0068:
            boolean r6 = android.text.TextUtils.isEmpty(r7)     // Catch:{ JSONException -> 0x0073 }
            if (r6 != 0) goto L_0x007e
            com.autonavi.common.model.POI r6 = defpackage.kv.a(r7)     // Catch:{ JSONException -> 0x0073 }
            goto L_0x007f
        L_0x0073:
            r6 = move-exception
            r11 = r6
            r6 = r13
            r13 = r11
            goto L_0x007a
        L_0x0078:
            r13 = move-exception
            r6 = r5
        L_0x007a:
            r13.printStackTrace()
            r13 = r6
        L_0x007e:
            r6 = r5
        L_0x007f:
            java.lang.String r7 = "IsSimNavi"
            r2.putBoolean(r7, r4)
            java.lang.String r7 = "tipNaviFlag"
            r2.putBoolean(r7, r4)
            java.lang.String r7 = "NaviMethod"
            r2.putInt(r7, r1)
            java.lang.String r1 = "NaviFlags"
            r2.putInt(r1, r0)
            java.lang.String r0 = "StartPOI"
            if (r13 != 0) goto L_0x0099
            r13 = r5
            goto L_0x009d
        L_0x0099:
            com.autonavi.common.model.POI r13 = r13.clone()
        L_0x009d:
            r2.putSerializable(r0, r13)
            java.lang.String r13 = "GPSPosition"
            com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            com.autonavi.common.model.GeoPoint r0 = r0.getLatestPosition()
            r2.putSerializable(r13, r0)
            java.lang.String r13 = "ThrouthPOI"
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>(r3)
            r2.putSerializable(r13, r0)
            java.lang.String r13 = "EndPOI"
            if (r6 != 0) goto L_0x00bd
            r0 = r5
            goto L_0x00c1
        L_0x00bd:
            com.autonavi.common.model.POI r0 = r6.clone()
        L_0x00c1:
            r2.putSerializable(r13, r0)
            java.lang.String r13 = "isOfflinePlan"
            r2.putBoolean(r13, r4)
            java.lang.String r13 = "isFromRouteResult"
            r0 = 1
            r2.putBoolean(r13, r0)
            java.lang.String r13 = "calc_route_result"
            r2.putObject(r13, r5)
            java.lang.String r13 = "mIsMultiRoute"
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            r2.putObject(r13, r0)
            java.lang.String r13 = "navi_type"
            java.lang.String r0 = "motorbike"
            r2.putObject(r13, r0)
            boolean r12 = a(r12, r2, r3, r6, r14)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.no.a(android.app.Activity, java.lang.String, no$a):boolean");
    }

    public static void a(Activity activity, POI poi, ArrayList<POI> arrayList, POI poi2) {
        PageBundle pageBundle = new PageBundle();
        a(pageBundle, poi, (List<POI>) arrayList, poi2);
        pageBundle.putString("navi_type", DriveUtil.NAVI_TYPE_MOTORBIKE);
        a(activity, pageBundle, arrayList, poi2, null);
    }

    static void a(bid bid, PageBundle pageBundle, boolean z) {
        bck bck = (bck) defpackage.esb.a.a.a(bck.class);
        if (bck != null) {
            bck.b();
        }
        Class<AjxRouteMotorNaviPage> cls = AjxRouteMotorNaviPage.class;
        pageBundle.putString("url", ModuleRouteDriveResult.MOTOR_NAVI);
        if (z) {
            bid.finish();
            bid.startPage(cls, pageBundle);
            return;
        }
        bid.startPageForResult(cls, pageBundle, (int) MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
    }
}
