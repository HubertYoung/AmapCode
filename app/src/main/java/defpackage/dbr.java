package defpackage;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficTopic;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;
import com.autonavi.minimap.bundle.notification.UCBroadcastReceiver;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.widget.ui.AlertView;
import com.huawei.android.pushagent.PushReceiver.KEY_TYPE;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.ICallback;
import com.taobao.agoo.IRegister;
import com.taobao.agoo.TaobaoRegister;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dbr reason: default package */
/* compiled from: PushManager */
public final class dbr {
    private static fhc a;

    /* renamed from: dbr$a */
    /* compiled from: PushManager */
    public static class a implements bjf {
        String a;
        private Context b;
        private String c;
        private int d;
        private String e;
        private String f;
        private String g;
        private String h;
        private String i;
        private String j;
        private String k;
        private int l;

        public final void onProgressUpdate(long j2, long j3) {
        }

        public final void onStart(long j2, Map<String, List<String>> map, int i2) {
        }

        a(Context context, String str, String str2, int i2, String str3, String str4, String str5, String str6, String str7, String str8, int i3) {
            this.b = context;
            this.c = str7;
            this.d = i2;
            this.f = str4;
            this.e = str5;
            this.g = str6;
            this.h = str;
            this.i = str2;
            this.j = str8;
            this.k = str3;
            this.l = i3;
        }

        public final void onError(int i2, int i3) {
            a();
        }

        private void a() {
            if (this.d == 0 || this.d == 1 || this.k == null) {
                dbr.a((String) null, this.l);
                dbr.a(this.b, this.f, this.g, this.h, this.i, this.j, null, null, 0);
                return;
            }
            if (this.d == 2 && !dbr.a(this.j, this.l)) {
                dbr.a(this.b, this.f, this.g, this.h, this.i, this.j, null, null, 0);
            }
        }

        public final void onFinish(bpk bpk) {
            File file = new File(this.a);
            if (!file.exists()) {
                a();
                return;
            }
            String path = file.getPath();
            if (this.d == 0 || this.d == 1 || this.k == null) {
                dbr.a((String) null, this.l);
                dbr.a(this.b, this.f, this.g, this.h, this.i, this.j, this.e, path, 0);
            } else if (this.d == 2 && !dbr.a(this.j, this.l)) {
                dbr.a(this.b, this.f, this.g, this.h, this.i, this.j, this.e, path, 0);
            }
            file.delete();
        }
    }

    public static synchronized fhc a() {
        fhc fhc;
        synchronized (dbr.class) {
            try {
                if (a == null) {
                    final String accsAppkey = ConfigerHelper.getInstance().getAccsAppkey();
                    final Context applicationContext = AMapAppGlobal.getApplication().getApplicationContext();
                    a = new fhc() {
                        public final void a() {
                            StringBuilder sb = new StringBuilder("bindAgoo---->appKey = ");
                            sb.append(accsAppkey);
                            AMapLog.i("PushAppReceiver-->", sb.toString());
                            try {
                                TaobaoRegister.register(applicationContext, "default", ConfigerHelper.getInstance().getAccsAppkey(), "", "", new IRegister() {
                                    public final void onSuccess(String str) {
                                        AMapLog.i("PushManager", "TaobaoRegister.register-onSuccess:".concat(String.valueOf(str)));
                                    }

                                    public final void onFailure(String str, String str2) {
                                        StringBuilder sb = new StringBuilder("TaobaoRegister.register-onFailure:");
                                        sb.append(str);
                                        sb.append(",");
                                        sb.append(str2);
                                        AMapLog.i("PushManager", sb.toString());
                                    }
                                });
                                TaobaoRegister.bindAgoo(applicationContext, new ICallback() {
                                    public final void onSuccess() {
                                        AMapLog.i("PushManager", "TaobaoRegister.bindAgoo-onSuccess");
                                    }

                                    public final void onFailure(String str, String str2) {
                                        StringBuilder sb = new StringBuilder("TaobaoRegister.bindAgoo-onFailure:");
                                        sb.append(str);
                                        sb.append(",");
                                        sb.append(str2);
                                        AMapLog.i("PushManager", sb.toString());
                                    }
                                });
                            } catch (Exception unused) {
                            }
                            dbr.a(true);
                        }

                        public final void b() {
                            StringBuilder sb = new StringBuilder("unBindAgoo---->appKey = ");
                            sb.append(accsAppkey);
                            AMapLog.i("PushAppReceiver-->", sb.toString());
                            try {
                                TaobaoRegister.unbindAgoo(applicationContext, new ICallback() {
                                    public final void onSuccess() {
                                        AMapLog.i("PushManager", "TaobaoRegister.unbindAgoo-onSuccess");
                                    }

                                    public final void onFailure(String str, String str2) {
                                        StringBuilder sb = new StringBuilder("TaobaoRegister.unbindAgoo-onFailure:");
                                        sb.append(str);
                                        sb.append(",");
                                        sb.append(str2);
                                        AMapLog.i("PushManager", sb.toString());
                                    }
                                });
                            } catch (Exception unused) {
                            }
                            dbr.a(false);
                        }
                    };
                }
                fhc = a;
            }
        }
        return fhc;
    }

    public static final boolean a(final Context context) {
        boolean a2 = dbq.a();
        if (a2) {
            StringBuilder sb = new StringBuilder("bindAgoo---->devid = ");
            sb.append(UtilityImpl.getDeviceId(context));
            AMapLog.i("PushAppReceiver-->", sb.toString());
            a(true);
        } else if (b(context)) {
            final bid pageContext = AMapPageUtil.getPageContext();
            com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(context);
            aVar.a(R.string.push_dialog_title);
            aVar.b(R.string.push_dialog_message);
            aVar.a(R.string.push_dialog_button_negative, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    bim.aa().b((String) UploadConstants.STATUS_PUSH_RECEIVED, 1);
                    try {
                        TaobaoRegister.bindAgoo(context, new dbp());
                    } catch (Exception unused) {
                    }
                    dbr.a(true);
                }
            });
            aVar.b(R.string.push_dialog_button_positive, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    try {
                        StringBuilder sb = new StringBuilder("bindAgoo---->devid = ");
                        sb.append(UtilityImpl.getDeviceId(context));
                        AMapLog.i("PushAppReceiver-->", sb.toString());
                        TaobaoRegister.unbindAgoo(context, new dbp());
                    } catch (Exception unused) {
                    }
                    dbr.a(false);
                }
            });
            aVar.b = new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                }
            };
            aVar.c = new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                }
            };
            if (AMapPageUtil.isHomePage()) {
                aVar.a(false);
                AlertView a3 = aVar.a();
                pageContext.showViewLayer(a3);
                a3.startAnimation();
            }
        } else {
            try {
                TaobaoRegister.unbindAgoo(context, new dbp());
            } catch (Exception unused) {
            }
            a(false);
        }
        return a2;
    }

    static void a(boolean z) {
        if (DoNotUseTool.getActivity() != null) {
            AMapLog.i("PushManager", "settingPushState=".concat(String.valueOf(z)));
            new MapSharePreference((String) KEY_TYPE.PUSHSTATE).putBooleanValue("push_setting", z);
        }
    }

    private static void a(Context context, int i, String str) {
        dbq.a(context);
        dbq.a(context, i);
        dbq.a(context, str);
    }

    public static void a(Context context, String str, String str2, String str3) {
        a(context, str, str2, str3, 0);
    }

    public static void a(Context context, String str, String str2, String str3, int i) {
        String str4 = str3;
        if (!TextUtils.isEmpty(str3)) {
            try {
                JSONObject jSONObject = new JSONObject(str4);
                JSONObject jSONObject2 = jSONObject.getJSONObject(Constants.KEY_EXTS);
                int i2 = jSONObject2.getInt("mtype");
                int optInt = jSONObject2.optInt("box");
                if (i2 == 1) {
                    int optInt2 = jSONObject2.optInt("tpl");
                    String optString = jSONObject2.optString("subcontent");
                    String optString2 = jSONObject2.optString("img");
                    int optInt3 = jSONObject2.optInt("priority", 0);
                    String string = jSONObject.getString("title");
                    String string2 = jSONObject.getString("url");
                    String string3 = jSONObject.getString("text");
                    Context context2 = context;
                    String str5 = str;
                    String str6 = str2;
                    dbs.a(context2, str5, str6, str4, string2);
                    a(context2, str6, str5, optInt, optInt2, string, optString, string3, optString2, string2, optInt3, i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    @TargetApi(16)
    public static void a(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i) {
        Builder builder = new Builder(context);
        builder.setAutoCancel(true);
        builder.setContentTitle(str);
        if (str6 == null) {
            str6 = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(str6);
        builder.setTicker(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(str6);
        builder.setContentText(sb2.toString());
        builder.setSmallIcon(R.drawable.notification_amap);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.v3_icon));
        builder.setDefaults(-1);
        ky.a(builder, a(i));
        Intent intent = new Intent();
        if (!TextUtils.isEmpty(str5) && (str5.startsWith("androidamap") || str5.startsWith("amapuri"))) {
            intent.setClassName(context.getApplicationContext(), Constant.LAUNCHER_ACTIVITY_NAME);
            intent.setFlags(268435456);
            intent.putExtra("owner", BaseIntentDispatcher.INTENT_CALL_OWNER_UMENG_PUSH);
            intent.putExtra("com.autonavi.bundle.notification.INTENT.KEY", "INTENT.FROM.PUSH");
            intent.putExtra("key_message_id", str3);
            intent.putExtra("key_task_id", str4);
            intent.setData(Uri.parse(str5));
        }
        builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, 134217728));
        Notification build = builder.build();
        if (!TextUtils.isEmpty(str7) && VERSION.SDK_INT >= 16) {
            Rect b = ags.b(context);
            Bitmap a2 = a(str7, b.width(), b.height());
            if (a2 != null) {
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.push);
                remoteViews.setImageViewBitmap(R.id.push_img, a2);
                remoteViews.setTextViewText(R.id.push_title, str);
                remoteViews.setTextViewText(R.id.push_subtitle, str6);
                remoteViews.setTextViewText(R.id.push_content, str2);
                build.bigContentView = remoteViews;
            }
        }
        ((NotificationManager) AMapAppGlobal.getApplication().getSystemService("notification")).notify((int) System.currentTimeMillis(), build);
    }

    @TargetApi(23)
    private static void a(Context context, String str, String str2, String str3, String str4, fhe fhe, int i) {
        Builder builder = new Builder(context);
        builder.setAutoCancel(true);
        builder.setContentTitle(str);
        builder.setTicker(str2);
        builder.setContentText(str2);
        builder.setSmallIcon(17301543);
        builder.setLargeIcon(fhe.c.getBitmap());
        builder.setDefaults(-1);
        ky.a(builder, a(i));
        Intent intent = new Intent("com.autonavi.minimap.uc.action");
        intent.putExtra("message_id", str3);
        intent.putExtra("task_id", str4);
        intent.putExtra("market_package", fhe.b);
        intent.putExtra("package_recommended_app", fhe.e);
        int i2 = 0;
        builder.setContentIntent(PendingIntent.getBroadcast(context, 0, intent, 268435456));
        Notification build = builder.build();
        if (build != null) {
            try {
                Field field = Class.forName("com.android.internal.R$id").getField("right_icon");
                field.setAccessible(true);
                i2 = field.getInt(null);
            } catch (Exception unused) {
            }
            if (i2 != 0) {
                build.contentView.setViewVisibility(i2, 4);
            }
            UCBroadcastReceiver uCBroadcastReceiver = new UCBroadcastReceiver(context);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.autonavi.minimap.uc.action");
            uCBroadcastReceiver.a.registerReceiver(uCBroadcastReceiver.b, intentFilter);
            ((NotificationManager) AMapAppGlobal.getApplication().getSystemService("notification")).notify((int) System.currentTimeMillis(), build);
        }
    }

    public static boolean a(String str, int i) {
        String a2 = a(str);
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(a2)) {
            arrayList.add(a2);
        }
        IMsgboxService iMsgboxService = (IMsgboxService) defpackage.esb.a.a.a(IMsgboxService.class);
        if (iMsgboxService == null) {
            return false;
        }
        defpackage.dap.a a3 = iMsgboxService.getMsgboxStorageService().a((List<String>) arrayList);
        try {
            ((fhd) ank.a(fhd.class)).a(Math.max(0, Math.min(a3.a + i, 3)));
        } catch (Throwable unused) {
        }
        if (a3.b) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("url", str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00065", "B003", jSONObject);
        }
        return a3.b;
    }

    private static void a(Context context, String str, String str2, int i, int i2, String str3, String str4, String str5, String str6, String str7, int i3, int i4) {
        int i5 = i;
        int i6 = i2;
        String str8 = str6;
        int i7 = i4;
        if (!a(context, str7, str3, str5, str, str2, i3)) {
            String a2 = a(str7);
            if (i6 == 0 || str8 == null || VERSION.SDK_INT < 16) {
                if (i5 == 0 || i5 == 1 || a2 == null) {
                    a((String) null, i7);
                    a(context, str3, str5, str, str2, str7, null, null, i3);
                } else if (i5 == 2) {
                    String str9 = str7;
                    if (!a(str9, i7)) {
                        a(context, str3, str5, str, str2, str9, null, null, i3);
                    }
                }
            } else if (i6 == 1) {
                a aVar = new a(context, str, str2, i5, a2, str3, str4, str5, str8, str7, i7);
                a(str8, aVar);
            }
        }
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int indexOf = str.indexOf("AmapPush-");
        if (indexOf >= 0) {
            String substring = str.substring(indexOf + 9);
            if (!TextUtils.isEmpty(substring)) {
                return substring;
            }
        }
        return null;
    }

    private static Bitmap a(String str, int i, int i2) {
        if (i == 0 || i2 == 0) {
            i = TrafficTopic.NOTIFY;
        } else if (i > i2) {
            i = i2;
        }
        Options options = new Options();
        try {
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Config.ARGB_8888;
            BitmapFactory.decodeFile(str, options);
            int i3 = options.outWidth;
            int i4 = options.outHeight;
            options.inJustDecodeBounds = false;
            options.inSampleSize = (int) (((float) i3) / ((float) i));
            return Bitmap.createScaledBitmap((Bitmap) new WeakReference(BitmapFactory.decodeFile(str, options)).get(), i, (i * 3) / 5, true);
        } catch (Exception unused) {
            return null;
        }
    }

    @TargetApi(23)
    private static boolean a(Context context, String str, String str2, String str3, String str4, String str5, int i) {
        Uri parse = Uri.parse(str);
        boolean z = true;
        if (!"appstore".equalsIgnoreCase(parse.getHost().trim())) {
            z = false;
        } else if (b(parse.getQueryParameter("chanel").trim()).get(ConfigerHelper.getInstance().getChannel()).isEmpty()) {
            return true;
        } else {
            fhe a2 = a(context, parse.getQueryParameter("pkgname").trim(), parse.getQueryParameter("store").trim());
            if (a2.b == null) {
                return true;
            }
            a(context, str2, str3, str4, str5, a2, i);
        }
        return z;
    }

    @SuppressLint({"WrongConstant"})
    private static fhe a(Context context, String str, String str2) {
        int i;
        fhe fhe = new fhe();
        fhe.e = str;
        PackageManager packageManager = context.getPackageManager();
        ArrayList arrayList = new ArrayList();
        while (true) {
            if (str2.length() <= 0) {
                break;
            }
            int indexOf = str2.indexOf(",");
            if (indexOf == -1) {
                arrayList.add(str2);
                str2 = "";
            } else {
                arrayList.add(str2.substring(0, indexOf));
                str2 = str2.substring(indexOf + 1, str2.length());
            }
        }
        boolean z = false;
        for (i = 0; i < arrayList.size(); i++) {
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo((String) arrayList.get(i), 8192);
                fhe.a = applicationInfo.loadLabel(packageManager).toString();
                fhe.b = applicationInfo.packageName;
                fhe.c = (BitmapDrawable) applicationInfo.loadIcon(packageManager);
                z = true;
            } catch (Exception unused) {
            }
            if (z) {
                break;
            }
        }
        return fhe;
    }

    private static Map<String, String> b(String str) {
        HashMap hashMap = new HashMap();
        while (str.length() > 0) {
            int indexOf = str.indexOf(",");
            if (indexOf == -1) {
                hashMap.put(str, str);
                str = "";
            } else {
                String substring = str.substring(0, indexOf);
                hashMap.put(substring, substring);
                str = str.substring(indexOf + 1, str.length());
            }
        }
        return hashMap;
    }

    private static void a(String str, a aVar) {
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(b());
            sb.append("/");
            sb.append(c(str));
            String sb2 = sb.toString();
            aVar.a = sb2;
            bjg bjg = new bjg(sb2);
            bjg.setUrl(str);
            bjh.a().a(bjg, (bjf) aVar);
        }
    }

    private static String b() {
        String externalStroragePath = FileUtil.getExternalStroragePath(AMapAppGlobal.getApplication());
        if (externalStroragePath == null) {
            return null;
        }
        File file = new File(externalStroragePath, "/autonavi/pushImage");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    private static String c(String str) {
        StringBuilder sb = new StringBuilder("push_");
        sb.append(agy.a(str));
        return sb.toString();
    }

    private static NotificationChannelIds a(int i) {
        if (i == 1) {
            return NotificationChannelIds.o;
        }
        return NotificationChannelIds.n;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x005e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean b(android.content.Context r12) {
        /*
            java.lang.String r0 = "sp_push"
            r1 = 4
            android.content.SharedPreferences r0 = r12.getSharedPreferences(r0, r1)
            java.lang.String r2 = "last_versioncode"
            r3 = -1
            int r0 = r0.getInt(r2, r3)
            java.lang.String r2 = "sp_push"
            android.content.SharedPreferences r2 = r12.getSharedPreferences(r2, r1)
            java.lang.String r4 = "last_versionname"
            r5 = 0
            java.lang.String r2 = r2.getString(r4, r5)
            java.lang.String r4 = "sp_push"
            android.content.SharedPreferences r1 = r12.getSharedPreferences(r4, r1)
            java.lang.String r4 = "last_time"
            r5 = -1
            long r7 = r1.getLong(r4, r5)
            java.lang.String r1 = ""
            r4 = 0
            android.app.Application r9 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ NameNotFoundException -> 0x0056, Exception -> 0x0051 }
            android.content.pm.PackageManager r9 = r9.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0056, Exception -> 0x0051 }
            android.app.Application r10 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ NameNotFoundException -> 0x0056, Exception -> 0x0051 }
            java.lang.String r10 = r10.getPackageName()     // Catch:{ NameNotFoundException -> 0x0056, Exception -> 0x0051 }
            android.content.pm.PackageInfo r9 = r9.getPackageInfo(r10, r4)     // Catch:{ NameNotFoundException -> 0x0056, Exception -> 0x0051 }
            if (r9 == 0) goto L_0x005a
            java.lang.String r10 = r9.versionName     // Catch:{ NameNotFoundException -> 0x0056, Exception -> 0x0051 }
            int r1 = r9.versionCode     // Catch:{ NameNotFoundException -> 0x004d, Exception -> 0x0049 }
            r9 = r1
            r1 = r10
            goto L_0x005b
        L_0x0049:
            r1 = move-exception
            r9 = r1
            r1 = r10
            goto L_0x0052
        L_0x004d:
            r1 = move-exception
            r9 = r1
            r1 = r10
            goto L_0x0057
        L_0x0051:
            r9 = move-exception
        L_0x0052:
            defpackage.kf.a(r9)
            goto L_0x005a
        L_0x0056:
            r9 = move-exception
        L_0x0057:
            defpackage.kf.a(r9)
        L_0x005a:
            r9 = -1
        L_0x005b:
            r10 = 1
            if (r0 == r3) goto L_0x0092
            boolean r11 = android.text.TextUtils.isEmpty(r2)
            if (r11 != 0) goto L_0x0092
            int r5 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r5 != 0) goto L_0x0069
            goto L_0x0092
        L_0x0069:
            boolean r5 = android.text.TextUtils.isEmpty(r1)
            if (r5 != 0) goto L_0x0091
            if (r9 != r3) goto L_0x0072
            goto L_0x0091
        L_0x0072:
            if (r9 < r0) goto L_0x007e
            boolean r0 = android.text.TextUtils.equals(r2, r1)
            if (r0 != 0) goto L_0x007e
            a(r12, r9, r1)
            return r10
        L_0x007e:
            long r2 = java.lang.System.currentTimeMillis()
            long r2 = r2 - r7
            r5 = 2592000000(0x9a7ec800, double:1.280618154E-314)
            int r0 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r0 < 0) goto L_0x0090
            a(r12, r9, r1)
            return r10
        L_0x0090:
            return r4
        L_0x0091:
            return r4
        L_0x0092:
            a(r12, r9, r1)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dbr.b(android.content.Context):boolean");
    }
}
