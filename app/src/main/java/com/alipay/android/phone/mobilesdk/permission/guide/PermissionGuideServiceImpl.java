package com.alipay.android.phone.mobilesdk.permission.guide;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilesdk.permission.b.j;
import com.alipay.android.phone.mobilesdk.permission.guide.a.c;
import com.alipay.android.phone.mobilesdk.permission.guide.info.b;
import com.alipay.android.phone.mobilesdk.permission.guide.provider.DataProvider;
import com.alipay.android.phone.mobilesdk.permission.sdk.PGDelegatorResult;
import com.alipay.android.phone.mobilesdk.permission.sdk.PermissionGuideSdk;
import com.alipay.android.phone.mobilesdk.permission.sdk.PermissionGuideSdk.DefaultGuideConfigure;
import com.alipay.android.phone.mobilesdk.permission.utils.e;
import com.alipay.android.phone.mobilesdk.permission.utils.h;
import com.alipay.mobile.antui.dialog.AUImageDialog;
import com.alipay.mobile.antui.dialog.AUImageDialog.OnItemClickListener;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.mobileappcommon.biz.rpc.pginfo.model.PgTemplateInfoPB;
import com.alipay.sdk.packet.d;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONObject;

public class PermissionGuideServiceImpl extends PermissionGuideService {
    private static final DateFormat c = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    /* access modifiers changed from: private */
    public c a;
    /* access modifiers changed from: private */
    public j b;
    private AtomicInteger d;

    static class a {
        public String a = "";
        public String b = "";
        public String c = "";
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.d = new AtomicInteger(1);
        this.a = c.a();
        this.b = new j("PermissionGuide", com.alipay.android.phone.mobilesdk.permission.b.a.a().b());
        b.a();
    }

    @Deprecated
    public boolean startPermissionGuide(String bizType, PermissionType[] permissions, PermissionGuideCallback callback) {
        return startPermissionGuide(bizType, permissions, callback, false);
    }

    @Deprecated
    public boolean startPermissionGuide(String bizType, PermissionType[] permissions, PermissionGuideCallback callback, boolean force) {
        Activity activity = null;
        WeakReference activityWeakReference = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity();
        if (activityWeakReference != null) {
            activity = (Activity) activityWeakReference.get();
        }
        LoggerFactory.getTraceLogger().debug("Permissions", "getTopActivity=" + activity);
        return startPermissionGuide(activity, bizType, permissions, callback);
    }

    public boolean startPermissionGuide(Activity activity, String bizType, PermissionType[] permissions, PermissionGuideCallback callback) {
        return startPermissionGuide(activity, bizType, permissions, callback, false);
    }

    public boolean startPermissionGuide(Activity activity, String bizType, PermissionType[] permissions, PermissionGuideCallback callback, boolean force) {
        return startPermissionGuide(activity, bizType, permissions, callback, force, false);
    }

    public boolean startPermissionGuide(Activity activity, String bizType, PermissionType[] permissions, PermissionGuideCallback callback, boolean force, boolean ignoreDelegator) {
        LoggerFactory.getTraceLogger().debug("Permissions", "startPermissionGuide(bizType=" + bizType + ", permissions=" + h.a((T[]) permissions) + ", force=" + force + ", ignoreDelegator=" + ignoreDelegator + ")");
        if (permissions == null) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", (String) "startPermissionGuide(null == permissions)");
            if (callback != null) {
                callback.onPermissionGuideResult(null, null);
            }
            return false;
        }
        int length = permissions.length;
        if (length == 0) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", (String) "startPermissionGuide(0==permissions.length)");
            if (callback != null) {
                callback.onPermissionGuideResult(permissions, new PermissionGuideResult[0]);
            }
            return false;
        }
        if (!ignoreDelegator) {
            PGDelegatorResult pgResult = a(LoggerFactory.getLogContext().getApplicationContext(), bizType, permissions, force);
            if (pgResult != null) {
                if (TextUtils.isEmpty(pgResult.bizType) || pgResult.permissions == null) {
                    LoggerFactory.getTraceLogger().error((String) "Permissions", (String) "Call permisson guide delegator and found result is invalid!");
                } else {
                    bizType = pgResult.bizType;
                    permissions = pgResult.permissions;
                    force = pgResult.force;
                    LoggerFactory.getTraceLogger().debug("Permissions", "After delegator, startPermissionGuide(bizType=" + bizType + ", permissions=" + h.a((T[]) permissions) + ", force=" + force + ", ignoreDelegator=" + ignoreDelegator + ")");
                }
            }
        }
        PermissionGuideResult[] results = new PermissionGuideResult[length];
        if (activity == null) {
            if (callback != null) {
                for (int index = 0; index < length; index++) {
                    results[index] = PermissionGuideResult.ERROR;
                    b.a(bizType, permissions[index].name(), (String) "4");
                    d.a(bizType, permissions[index].name(), "EXCEPTION");
                }
                callback.onPermissionGuideResult(permissions, results);
            }
            return false;
        }
        boolean bRet = false;
        Context context = LauncherApplicationAgent.getInstance().getBaseContext();
        SparseArray allTemplateInfos = new SparseArray();
        for (int index2 = 0; index2 < length; index2++) {
            String fileName = e.a(bizType, permissions[index2].name());
            PgTemplateInfoPB pgTemplateInfo = c.a(context, fileName);
            if (pgTemplateInfo == null) {
                LoggerFactory.getTraceLogger().verbose("Permissions", "Failed to getPgTemplateInfo by :" + fileName + ", using the inside stuffs.");
            }
            allTemplateInfos.put(index2, pgTemplateInfo);
        }
        c multiTemplate = null;
        if (length > 1) {
            HashMap hashMap = new HashMap(length);
            String[] permissionNames = new String[length];
            for (int index3 = 0; index3 < length; index3++) {
                String permissionName = permissions[index3].name();
                permissionNames[index3] = permissionName;
                hashMap.put(permissionName, allTemplateInfos.get(index3));
            }
            List combinations = e.a(permissionNames);
            if (!combinations.isEmpty()) {
                Iterator<String[]> it = combinations.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    String[] combination = it.next();
                    String fileName2 = e.a(bizType, combination);
                    PgTemplateInfoPB pgTemplateInfo2 = c.a(context, fileName2);
                    if (pgTemplateInfo2 == null) {
                        LoggerFactory.getTraceLogger().verbose("Permissions", "Failed to getPgTemplateInfo by :" + fileName2);
                    } else {
                        String permission_combination = e.a(combination);
                        boolean valid = true;
                        int length2 = combination.length;
                        int i = 0;
                        while (true) {
                            if (i >= length2) {
                                break;
                            }
                            if (((PgTemplateInfoPB) hashMap.get(combination[i])) == null) {
                                valid = false;
                                break;
                            }
                            i++;
                        }
                        if (!valid) {
                            LoggerFactory.getTraceLogger().debug("Permissions", permission_combination + " is invalid.");
                        } else if (a(bizType, context, pgTemplateInfo2, permission_combination, force)) {
                            multiTemplate = new c();
                            for (String permissionName2 : combination) {
                                int index4 = e.a(permissions, permissionName2);
                                allTemplateInfos.remove(index4);
                                multiTemplate.a.put(index4, (PgTemplateInfoPB) hashMap.get(permissionName2));
                            }
                            multiTemplate.b = pgTemplateInfo2;
                            bRet = true;
                        } else {
                            HashMap map = new HashMap();
                            for (String permissionName3 : combination) {
                                int index5 = e.a(permissions, permissionName3);
                                allTemplateInfos.remove(index5);
                                results[index5] = PermissionGuideResult.STRATEGY_MISMATCH;
                                map.put(permissionName3, "3");
                            }
                            b.a(bizType, permission_combination, map);
                        }
                    }
                }
            }
        }
        SparseArray sparseArray = new SparseArray(length);
        int N = allTemplateInfos.size();
        for (int i2 = 0; i2 < N; i2++) {
            int index6 = allTemplateInfos.keyAt(i2);
            PgTemplateInfoPB pgTemplateInfo3 = (PgTemplateInfoPB) allTemplateInfos.valueAt(i2);
            PermissionType permission = permissions[index6];
            String permissionName4 = permission.name();
            if (pgTemplateInfo3 == null) {
                LoggerFactory.getTraceLogger().warn((String) "Permissions", "Can't find PgTemplateInfoPB by [bizType=" + bizType + ", permission=" + permission + "], use the inside stuff.");
                if (a(bizType, permissionName4, force)) {
                    sparseArray.put(index6, null);
                    bRet = true;
                } else {
                    results[index6] = PermissionGuideResult.STRATEGY_MISMATCH;
                    b.a(bizType, permissionName4, (String) "3");
                }
            } else if (a(bizType, context, pgTemplateInfo3, permissionName4, force)) {
                sparseArray.put(index6, pgTemplateInfo3);
                bRet = true;
            } else {
                results[index6] = PermissionGuideResult.STRATEGY_MISMATCH;
                b.a(bizType, permissionName4, (String) "3");
            }
        }
        a(activity, bizType, permissions, callback, results, multiTemplate, sparseArray);
        return bRet;
    }

    /* JADX WARNING: type inference failed for: r10v0, types: [com.alipay.android.phone.mobilesdk.permission.guide.PermissionType[], java.io.Serializable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.alipay.android.phone.mobilesdk.permission.sdk.PGDelegatorResult a(android.content.Context r8, java.lang.String r9, com.alipay.android.phone.mobilesdk.permission.guide.PermissionType[] r10, boolean r11) {
        /*
            r4 = 0
            com.alipay.mobile.common.logging.api.ProcessInfo r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()     // Catch:{ Throwable -> 0x0044 }
            boolean r3 = r3.isMainProcess()     // Catch:{ Throwable -> 0x0044 }
            if (r3 == 0) goto L_0x0014
            java.util.List r3 = com.alipay.android.phone.mobilesdk.permission.sdk.PermissionGuideSdk.getDelegators()     // Catch:{ Throwable -> 0x0044 }
            com.alipay.android.phone.mobilesdk.permission.sdk.PGDelegatorResult r3 = com.alipay.android.phone.mobilesdk.permission.utils.c.a(r3, r9, r10, r11)     // Catch:{ Throwable -> 0x0044 }
        L_0x0013:
            return r3
        L_0x0014:
            android.os.Bundle r1 = new android.os.Bundle     // Catch:{ Throwable -> 0x0044 }
            r1.<init>()     // Catch:{ Throwable -> 0x0044 }
            java.lang.String r3 = "bizType"
            r1.putString(r3, r9)     // Catch:{ Throwable -> 0x0044 }
            java.lang.String r3 = "permissionTypes"
            r1.putSerializable(r3, r10)     // Catch:{ Throwable -> 0x0044 }
            java.lang.String r3 = "force"
            r1.putBoolean(r3, r11)     // Catch:{ Throwable -> 0x0044 }
            android.content.ContentResolver r3 = r8.getContentResolver()     // Catch:{ Throwable -> 0x0044 }
            android.net.Uri r5 = com.alipay.android.phone.mobilesdk.permission.guide.provider.DataProvider.a(r8)     // Catch:{ Throwable -> 0x0044 }
            java.lang.String r6 = "overridePermissionGuideBehavior"
            r7 = 0
            android.os.Bundle r0 = r3.call(r5, r6, r7, r1)     // Catch:{ Throwable -> 0x0044 }
            if (r0 != 0) goto L_0x003b
            r3 = r4
            goto L_0x0013
        L_0x003b:
            java.lang.String r3 = "pgDelegatorResult"
            java.io.Serializable r3 = r0.getSerializable(r3)     // Catch:{ Throwable -> 0x0044 }
            com.alipay.android.phone.mobilesdk.permission.sdk.PGDelegatorResult r3 = (com.alipay.android.phone.mobilesdk.permission.sdk.PGDelegatorResult) r3     // Catch:{ Throwable -> 0x0044 }
            goto L_0x0013
        L_0x0044:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = "Permissions"
            java.lang.String r6 = "Call overridePermissionGuideBehavior error"
            r3.error(r5, r6, r2)
            r3 = r4
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilesdk.permission.guide.PermissionGuideServiceImpl.a(android.content.Context, java.lang.String, com.alipay.android.phone.mobilesdk.permission.guide.PermissionType[], boolean):com.alipay.android.phone.mobilesdk.permission.sdk.PGDelegatorResult");
    }

    private boolean a(String bizType, Context context, PgTemplateInfoPB pgTemplateInfo, String permissionName, boolean force) {
        String today;
        boolean show = true;
        if (!force) {
            if (pgTemplateInfo.pgFatigueLevel == null || !pgTemplateInfo.pgFatigueLevel.equals(Integer.valueOf(1))) {
                show = true;
            } else if (pgTemplateInfo.pgPriority == null || !pgTemplateInfo.pgPriority.equals(Integer.valueOf(1))) {
                show = a(bizType, permissionName, false);
            } else {
                synchronized (c) {
                    today = c.format(new Date());
                }
                if (TextUtils.isEmpty(pgTemplateInfo.pgTime) || !pgTemplateInfo.pgTime.contains(today)) {
                    LoggerFactory.getTraceLogger().debug("Permissions", "expected date: " + pgTemplateInfo.pgTime + ", today =>" + today + " : " + PermissionGuideResult.STRATEGY_MISMATCH);
                    show = false;
                } else {
                    LoggerFactory.getTraceLogger().debug("Permissions", "expected date: " + pgTemplateInfo.pgTime + ", today =>" + today);
                    String key = pgTemplateInfo.mobilePgTemplateCode + today;
                    show = com.alipay.android.phone.mobilesdk.permission.guide.a.a.b(context, key);
                    if (show) {
                        com.alipay.android.phone.mobilesdk.permission.guide.a.a.c(context, key);
                    } else {
                        LoggerFactory.getTraceLogger().debug("Permissions", "STRICT_PRIORITY : " + key + ", => shouldShow=false : " + PermissionGuideResult.STRATEGY_MISMATCH);
                    }
                }
            }
            LoggerFactory.getTraceLogger().info("Permissions", "isShow(" + permissionName + "):" + show);
        }
        return show;
    }

    public PermissionStatus checkPermissionStatus(String permission) {
        return checkPermissionStatus(permission, false);
    }

    public PermissionStatus checkPermissionStatus(String permissionName, boolean ignoreDelegator) {
        PermissionStatus status = PermissionStatus.NONE;
        try {
            PermissionType permission = PermissionType.valueOf(permissionName);
            Context context = LoggerFactory.getLogContext().getApplicationContext();
            if (!ignoreDelegator) {
                PermissionStatus delegatorStatus = a(context, permission);
                if (delegatorStatus != null) {
                    return delegatorStatus;
                }
            }
            switch (permission) {
                case LBSSERVICE:
                    status = b.c();
                    break;
                case SELFSTARTING:
                    status = b.e();
                    break;
                case NOTIFICATION:
                    status = b.d();
                    break;
                case BACKGROUNDER:
                    status = b.g();
                    break;
                case ADDRESSBOOK:
                    status = b.a(context);
                    break;
                case MICROPHONE:
                    status = b.c(context);
                    break;
                case SHORTCUT:
                    status = b.f();
                    break;
                case SHINFO:
                    status = b.d(context);
                    break;
                case CAMERA:
                    status = b.b(context);
                    break;
                case LBS:
                    status = b.b();
                    break;
            }
            LoggerFactory.getTraceLogger().debug("Permissions", "checkPermissionStatus: " + permissionName + " => " + permission + ", status=" + status);
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
        }
        return status;
    }

    private static PermissionStatus a(Context context, PermissionType permission) {
        try {
            if (LoggerFactory.getProcessInfo().isMainProcess()) {
                return com.alipay.android.phone.mobilesdk.permission.utils.c.a(PermissionGuideSdk.getDelegators(), permission);
            }
            ContentResolver resolver = context.getContentResolver();
            Bundle params = new Bundle();
            params.putSerializable("permissionType", permission);
            Bundle result = resolver.call(DataProvider.a(context), "checkPermissionStatus", null, params);
            if (result == null) {
                return null;
            }
            return (PermissionStatus) result.getSerializable("permissionStatus");
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error("Permissions", "Call callCheckPermissionStatusFromDelegator error", tr);
            return null;
        }
    }

    private boolean a(String bizType, String permissionName, boolean force) {
        long now = System.currentTimeMillis();
        LoggerFactory.getTraceLogger().debug("Permissions", "fatigue(" + bizType + "_" + permissionName + ") now=" + now + ", force=" + force);
        if (force) {
            return true;
        }
        com.alipay.android.phone.mobilesdk.permission.guide.a.c.a result = this.a.b(permissionName, bizType);
        if (result == null) {
            LoggerFactory.getTraceLogger().error((String) "Permissions", (String) "Can't get permission guide info");
            return false;
        }
        long pgFatigue = result.a;
        long duration = now - result.d;
        if (duration <= TimeUnit.MINUTES.toMillis(pgFatigue)) {
            LoggerFactory.getTraceLogger().debug("Permissions", "fatigue(" + bizType + "_" + permissionName + ") = false: duration(" + duration + "ms) <= PGFatigue(" + pgFatigue + " minutes)");
            return false;
        }
        long samePGFatigue = result.b;
        long duration2 = now - result.e;
        if (duration2 <= TimeUnit.MINUTES.toMillis(samePGFatigue)) {
            LoggerFactory.getTraceLogger().debug("Permissions", "fatigue(" + bizType + "_" + permissionName + ") = false: duration(" + duration2 + "ms) <= SamePGFatigue(" + samePGFatigue + " minutes)");
            return false;
        }
        long differencePGFatigue = result.c;
        long duration3 = now - result.f;
        if (duration3 <= TimeUnit.MINUTES.toMillis(differencePGFatigue)) {
            LoggerFactory.getTraceLogger().debug("Permissions", "fatigue(" + bizType + "_" + permissionName + ") = false: duration(" + duration3 + "ms) <= DifferencePGFatigue(" + differencePGFatigue + " minutes)");
            return false;
        }
        LoggerFactory.getTraceLogger().debug("Permissions", "fatigue(" + bizType + "_" + permissionName + ") = true: PGFatigue(" + pgFatigue + " minutes) & SamePGFatigue(" + samePGFatigue + " minutes) & DifferencePGFatigue(" + differencePGFatigue + " minutes)");
        return true;
    }

    private void a(Activity activity, String bizType, PermissionType[] permissions, PermissionGuideCallback callback, PermissionGuideResult[] results, c multiTemplate, SparseArray<PgTemplateInfoPB> singleTemplates) {
        LoggerFactory.getTraceLogger().debug("Permissions", "showPermissionGuide(" + bizType + "," + h.a((T[]) permissions) + "," + callback + "," + h.a((T[]) results) + ")");
        int N = singleTemplates.size();
        for (int i = 0; i < N; i++) {
            final int index = singleTemplates.keyAt(i);
            final PgTemplateInfoPB pgTemplateInfo = singleTemplates.get(index);
            final Activity activity2 = activity;
            final String str = bizType;
            final PermissionType[] permissionTypeArr = permissions;
            final PermissionGuideResult[] permissionGuideResultArr = results;
            this.b.a(new Runnable() {
                public final void run() {
                    PermissionGuideServiceImpl.this.a(activity2, str, permissionTypeArr[index], permissionGuideResultArr, index, pgTemplateInfo);
                }
            }, bizType + "_" + permissions[index]);
        }
        if (multiTemplate != null) {
            PgTemplateInfoPB pgTemplateInfo2 = multiTemplate.b;
            final Activity activity3 = activity;
            final String str2 = bizType;
            final PermissionGuideResult[] permissionGuideResultArr2 = results;
            final c cVar = multiTemplate;
            this.b.a(new Runnable() {
                public final void run() {
                    PermissionGuideServiceImpl.this.a(activity3, str2, permissionGuideResultArr2, cVar);
                }
            }, pgTemplateInfo2.pgCode + "_" + pgTemplateInfo2.pgCategory);
        }
        final PermissionType[] permissionTypeArr2 = permissions;
        final PermissionGuideResult[] permissionGuideResultArr3 = results;
        final PermissionGuideCallback permissionGuideCallback = callback;
        this.b.a((Runnable) new Runnable() {
            public final void run() {
                LoggerFactory.getTraceLogger().info("Permissions", "PermissionGuideCallback.onPermissionGuideResult(permissions=" + h.a((T[]) permissionTypeArr2) + ", results=" + h.a((T[]) permissionGuideResultArr3) + ")");
                if (permissionGuideCallback != null) {
                    permissionGuideCallback.onPermissionGuideResult(permissionTypeArr2, permissionGuideResultArr3);
                }
                LoggerFactory.getTraceLogger().debug("Permissions", "TransactionPipeline.stop()=" + PermissionGuideServiceImpl.this.b.c());
            }
        });
        LoggerFactory.getTraceLogger().debug("Permissions", "TransactionPipeline.start()=" + this.b.b());
        this.b.d();
    }

    /* access modifiers changed from: private */
    public void a(Activity activity, String bizType, PermissionGuideResult[] results, c multiTemplate) {
        final c cVar = multiTemplate;
        final Activity activity2 = activity;
        final String str = bizType;
        final PermissionGuideResult[] permissionGuideResultArr = results;
        new Handler(Looper.getMainLooper()).postAtFrontOfQueue(new Runnable() {
            public final void run() {
                PgTemplateInfoPB pgTemplateInfo = cVar.b;
                SparseArray<PgTemplateInfoPB> sparseArray = cVar.a;
                AUImageDialog dialog = AUImageDialog.getInstance(activity2);
                dialog.setTitle(pgTemplateInfo.pgContentTitle);
                dialog.setSubTitle(pgTemplateInfo.pgContent);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCanceledOnTouch(false);
                dialog.setCancelable(false);
                final Context context = LauncherApplicationAgent.getInstance().getBaseContext();
                ImageView imageView = dialog.getLogoImageView();
                String imgUrl = pgTemplateInfo.imgUrl;
                if (TextUtils.isEmpty(imgUrl)) {
                    imageView.setVisibility(8);
                    String actionContent = pgTemplateInfo.pgActionContent;
                    if (TextUtils.isEmpty(actionContent)) {
                        LoggerFactory.getTraceLogger().warn((String) "Permissions", (String) "pgTemplateInfo.imgUrl && pgTemplateInfo.pgActionContent are all empty.");
                    } else {
                        dialog.setSubTitle(actionContent);
                    }
                } else {
                    imageView.setBackgroundColor(0);
                    imageView.getLayoutParams().height = DensityUtil.dip2px(context, 130.0f);
                    imageView.getLayoutParams().width = DensityUtil.dip2px(context, 162.0f);
                    ((MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MultimediaImageService.class.getName())).loadOriginalImage(imgUrl, imageView, null, null, "antbasic_permissions");
                }
                int size = sparseArray.size();
                String[] permissionNames = new String[size];
                final HashMap behaviorLogMap = new HashMap();
                for (int i = 0; i < size; i++) {
                    permissionNames[i] = sparseArray.valueAt(i).pgCategory;
                    behaviorLogMap.put(permissionNames[i], "0");
                }
                final String permissionName = e.a(permissionNames);
                List buttonListInfo = new ArrayList(size);
                int N = sparseArray.size();
                for (int i2 = 0; i2 < N; i2++) {
                    buttonListInfo.add(sparseArray.valueAt(i2).pgContentPgSubTitle);
                }
                final SparseArray<PgTemplateInfoPB> sparseArray2 = sparseArray;
                dialog.setButtonListInfo(buttonListInfo, new OnItemClickListener() {
                    public final void onItemClick(int i) {
                        int index = sparseArray2.keyAt(i);
                        PgTemplateInfoPB singleTemplateInfo = (PgTemplateInfoPB) sparseArray2.valueAt(i);
                        Intent intent = e.a(context, singleTemplateInfo.pgAction);
                        String permissionName = singleTemplateInfo.pgCategory;
                        if (intent == null) {
                            LoggerFactory.getTraceLogger().warn((String) "Permissions", (String) "Don't show PermissionGuideDialog: intent==null");
                            if (TextUtils.isEmpty(singleTemplateInfo.pgAction)) {
                                behaviorLogMap.put(permissionName, "1");
                            } else {
                                behaviorLogMap.put(permissionName, "6");
                            }
                        } else if (1 == singleTemplateInfo.mobileJumpPage.intValue()) {
                            PermissionGuideServiceImpl.this.a(activity2, str, permissionGuideResultArr, index, singleTemplateInfo.mobileTemplateconfigImgurl, intent, permissionName, behaviorLogMap);
                        } else {
                            LoggerFactory.getTraceLogger().warn((String) "Permissions", "Don't show PermissionGuideDialog: singleTemplateInfo.mobileJumpPage=" + singleTemplateInfo.mobileJumpPage);
                            try {
                                activity2.startActivity(intent);
                                behaviorLogMap.put(permissionName, "2");
                            } catch (Throwable e) {
                                LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
                                behaviorLogMap.put(permissionName, "6");
                            }
                        }
                    }
                });
                dialog.setCloseButtonVisibility(0);
                dialog.setCloseBtnClickListener(new OnClickListener() {
                    public final void onClick(View v) {
                        b.a(str, permissionName, behaviorLogMap);
                        LoggerFactory.getTraceLogger().debug("Permissions", "AUImageDialog.Close(MultiTemplate) = > TransactionPipeline.nextTransaction()=" + PermissionGuideServiceImpl.this.b.d());
                    }
                });
                dialog.showWithoutAnim();
                PermissionGuideServiceImpl.this.a(context, str, permissionName);
                d.a(str, permissionName, pgTemplateInfo.mobilePgTemplateCode);
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(Activity activity, String bizType, PermissionType permission, PermissionGuideResult[] results, int index, PgTemplateInfoPB pgTemplateInfo) {
        final PgTemplateInfoPB pgTemplateInfoPB = pgTemplateInfo;
        final PermissionType permissionType = permission;
        final Activity activity2 = activity;
        final PermissionGuideResult[] permissionGuideResultArr = results;
        final int i = index;
        final String str = bizType;
        new Handler(Looper.getMainLooper()).postAtFrontOfQueue(new Runnable() {
            public final void run() {
                String content;
                String uri;
                DefaultGuideConfigure guideConfigure = PermissionGuideSdk.getDefaultGuideConfigure();
                String title = pgTemplateInfoPB == null ? guideConfigure.getDefaultGuideTitle(permissionType) : pgTemplateInfoPB.pgContentTitle;
                if (pgTemplateInfoPB == null) {
                    content = guideConfigure.getDefaultGuideContent(permissionType);
                } else {
                    content = pgTemplateInfoPB.pgContent;
                }
                final AUImageDialog dialog = AUImageDialog.getInstance(activity2);
                dialog.setTitle(title);
                dialog.setSubTitle(content);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCanceledOnTouch(false);
                dialog.setCancelable(false);
                Context context = LauncherApplicationAgent.getInstance().getBaseContext();
                ImageView imageView = dialog.getLogoImageView();
                if (pgTemplateInfoPB == null) {
                    imageView.setVisibility(8);
                } else {
                    String imgUrl = pgTemplateInfoPB.imgUrl;
                    if (TextUtils.isEmpty(imgUrl)) {
                        imageView.setVisibility(8);
                        String actionContent = pgTemplateInfoPB.pgActionContent;
                        if (TextUtils.isEmpty(actionContent)) {
                            LoggerFactory.getTraceLogger().warn((String) "Permissions", (String) "pgTemplateInfo.imgUrl && pgTemplateInfo.pgActionContent are all empty.");
                        } else {
                            dialog.setSubTitle(actionContent);
                        }
                    } else {
                        imageView.setBackgroundColor(0);
                        imageView.getLayoutParams().height = DensityUtil.dip2px(context, 130.0f);
                        imageView.getLayoutParams().width = DensityUtil.dip2px(context, 162.0f);
                        ((MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MultimediaImageService.class.getName())).loadOriginalImage(imgUrl, imageView, null, null, "antbasic_permissions");
                    }
                    String actionContent2 = pgTemplateInfoPB.pgActionContent;
                    if (!TextUtils.isEmpty(actionContent2)) {
                        dialog.setSubTitle(actionContent2);
                    }
                }
                if (pgTemplateInfoPB == null) {
                    uri = null;
                } else {
                    uri = pgTemplateInfoPB.pgAction;
                }
                Intent intent = e.a(context, uri);
                if (intent == null) {
                    intent = e.a(context);
                }
                final String permissionName = permissionType.name();
                if (intent == null) {
                    dialog.setConfirmBtnText(PermissionGuideSdk.getDefaultGuideConfigure().getTextForConfirm());
                    final String str = uri;
                    dialog.setOnConfirmBtnClick(new OnClickListener() {
                        public final void onClick(View v) {
                            permissionGuideResultArr[i] = PermissionGuideResult.GO_TO_SET;
                            LoggerFactory.getTraceLogger().debug("Permissions", "AUImageDialog.Confirm(SingleTemplate) = > TransactionPipeline.nextTransaction()=" + PermissionGuideServiceImpl.this.b.d());
                            dialog.dismissWithoutAnim();
                            if (TextUtils.isEmpty(str)) {
                                b.a(str, permissionName, (String) "1");
                            } else {
                                b.a(str, permissionName, (String) "6");
                            }
                            PermissionGuideServiceImpl.this.a(activity2, permissionType);
                        }
                    });
                } else {
                    dialog.setConfirmBtnText(PermissionGuideSdk.getDefaultGuideConfigure().getTextForGoToSettings());
                    final Intent finalIntent = intent;
                    dialog.setOnConfirmBtnClick(new OnClickListener() {
                        public final void onClick(View v) {
                            if (pgTemplateInfoPB == null || 1 != pgTemplateInfoPB.mobileJumpPage.intValue()) {
                                permissionGuideResultArr[i] = PermissionGuideResult.GO_TO_SET;
                                try {
                                    activity2.startActivity(finalIntent);
                                    b.a(str, permissionName, (String) "2");
                                } catch (Throwable e) {
                                    LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
                                    b.a(str, permissionName, (String) "6");
                                }
                                LoggerFactory.getTraceLogger().debug("Permissions", "AUImageDialog.Confirm(SingleTemplate) = > TransactionPipeline.nextTransaction()=" + PermissionGuideServiceImpl.this.b.d());
                            } else {
                                PermissionGuideServiceImpl.this.a(activity2, str, permissionGuideResultArr, i, pgTemplateInfoPB.mobileTemplateconfigImgurl, finalIntent, permissionName, null);
                            }
                            dialog.dismissWithoutAnim();
                        }
                    });
                }
                dialog.setCloseButtonVisibility(0);
                dialog.setCloseBtnClickListener(new OnClickListener() {
                    public final void onClick(View v) {
                        permissionGuideResultArr[i] = PermissionGuideResult.CANCEL_TO_SET;
                        LoggerFactory.getTraceLogger().debug("Permissions", "AUImageDialog.Close(SingleTemplate) = > TransactionPipeline.nextTransaction()=" + PermissionGuideServiceImpl.this.b.d());
                        b.a(str, permissionName, (String) "0");
                    }
                });
                dialog.showWithoutAnim();
                PermissionGuideServiceImpl.this.a(context, str, permissionName);
                d.a(str, permissionName, pgTemplateInfoPB == null ? "LOCAL_TEMPLATECODE" : pgTemplateInfoPB.mobilePgTemplateCode);
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(Activity activity, PermissionType permission) {
        Intent intent;
        if (PermissionType.LBS != permission && PermissionType.LBSSERVICE != permission) {
            LoggerFactory.getTraceLogger().info("Permissions", "permissionType=" + permission.name());
        } else if (b()) {
            Map modelMap = a();
            if (modelMap == null || modelMap.isEmpty()) {
                LoggerFactory.getTraceLogger().info("Permissions", "map is empty");
                return;
            }
            a model = modelMap.get(Build.MODEL);
            String pathType = Build.MODEL;
            if (model == null) {
                model = modelMap.get("default");
                pathType = "default";
            }
            if (model != null) {
                Behavor behavor = new Behavor();
                behavor.setBehaviourPro("PermissionGuide");
                behavor.setSeedID("JUMP_PATH");
                behavor.setParam1(Build.MODEL);
                behavor.setParam2(LoggerFactory.getDeviceProperty().getRomVersion());
                behavor.setParam3(pathType);
                String path = "";
                String lbsType = RPCDataItems.LBSINFO;
                try {
                    Context context = LauncherApplicationAgent.getInstance().getBaseContext();
                    if (PermissionType.LBS == permission) {
                        path = model.b;
                        intent = e.a(context, model.b);
                    } else {
                        path = model.c;
                        lbsType = "LBSSERVICE";
                        intent = e.a(context, model.c);
                    }
                    behavor.addExtParam("lbstype", lbsType);
                    behavor.addExtParam("path", path);
                    behavor.addExtParam("turnsuccess", "1");
                    activity.startActivity(intent);
                } catch (Throwable th) {
                    behavor.addExtParam("turnsuccess", "0");
                    LoggerFactory.getTraceLogger().info("Permissions", "turn error");
                } finally {
                    r10 = "Permissions";
                    r11 = "finally coming";
                    LoggerFactory.getTraceLogger().info(r10, r11);
                    a(behavor, lbsType, path);
                }
            }
        }
    }

    private void a(final Behavor behavor, final String lbsType, final String path) {
        ((TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName())).acquireExecutor(ScheduleType.NORMAL).execute(new Runnable() {
            public final void run() {
                LoggerFactory.getBehavorLogger().event(null, behavor);
                StringBuilder message = new StringBuilder("PermissionGuide");
                message.append(", seedID: JUMP_PATH");
                message.append(", device: ").append(Build.MODEL);
                message.append(", romversion: ").append(behavor.getParam2());
                message.append(", pathType: ").append(behavor.getParam3());
                message.append(", lbstype: ").append(lbsType);
                message.append(", path: ").append(path);
                LoggerFactory.getTraceLogger().info("Permissions", message.toString());
            }
        });
    }

    private static Map<String, a> a() {
        String value = f.a((String) "permission_guide_path_list");
        LoggerFactory.getTraceLogger().info("Permissions", "initPathModelMapFromConfigValue, value=" + value);
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        Map pathMap = new HashMap();
        try {
            JSONArray array = new JSONArray(value);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                if (object != null) {
                    a model = new a();
                    model.a = object.getString(d.n);
                    model.b = object.getString("appPath");
                    model.c = object.getString("gpsPath");
                    if (!TextUtils.isEmpty(model.a)) {
                        pathMap.put(model.a, model);
                    }
                }
            }
            return pathMap;
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().info("Permissions", "initPathModelMapFromConfigValue, e.msg=" + th.getMessage());
            return pathMap;
        }
    }

    private static boolean b() {
        String value = f.a((String) "permission_guide_path_list_flag");
        LoggerFactory.getTraceLogger().info("Permissions", "getDefaultPermissionPathFlag, value=" + value);
        if (TextUtils.isEmpty(value)) {
            return true;
        }
        try {
            if (Integer.parseInt(value) == 0) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().info("Permissions", "getDefaultPermissionPathFlag, th=" + th);
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void a(Activity activity, String bizType, PermissionGuideResult[] results, int index, String imgUrl, Intent intent, String permissionName, HashMap<String, String> behaviorLogMap) {
        final com.alipay.android.phone.mobilesdk.permission.guide.b.a dialog = new com.alipay.android.phone.mobilesdk.permission.guide.b.a(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.a(imgUrl);
        final PermissionGuideResult[] permissionGuideResultArr = results;
        final int i = index;
        final Activity activity2 = activity;
        final Intent intent2 = intent;
        final HashMap<String, String> hashMap = behaviorLogMap;
        final String str = bizType;
        final String str2 = permissionName;
        dialog.a((OnClickListener) new OnClickListener() {
            public final void onClick(View v) {
                permissionGuideResultArr[i] = PermissionGuideResult.GO_TO_SET;
                try {
                    activity2.startActivity(intent2);
                    if (hashMap == null) {
                        b.a(str, str2, (String) "2");
                    } else {
                        hashMap.put(str2, "2");
                    }
                } catch (Throwable e2) {
                    LoggerFactory.getTraceLogger().warn((String) "Permissions", e2);
                    if (hashMap == null) {
                        b.a(str, str2, (String) "6");
                    } else {
                        hashMap.put(str2, "6");
                    }
                }
                LoggerFactory.getTraceLogger().debug("Permissions", "PermissionGuideDialog.Confirm() = > TransactionPipeline.nextTransaction()=" + PermissionGuideServiceImpl.this.b.d());
                dialog.dismiss();
            }
        });
        final HashMap<String, String> hashMap2 = behaviorLogMap;
        final PermissionGuideResult[] permissionGuideResultArr2 = results;
        final int i2 = index;
        final String str3 = bizType;
        final String str4 = permissionName;
        dialog.b(new OnClickListener() {
            public final void onClick(View v) {
                if (hashMap2 == null) {
                    permissionGuideResultArr2[i2] = PermissionGuideResult.CANCEL_TO_SET;
                    b.a(str3, str4, (String) "7");
                } else if (permissionGuideResultArr2[i2] != PermissionGuideResult.GO_TO_SET) {
                    permissionGuideResultArr2[i2] = PermissionGuideResult.CANCEL_TO_SET;
                    hashMap2.put(str4, "0");
                }
                LoggerFactory.getTraceLogger().debug("Permissions", "PermissionGuideDialog.Close() = > TransactionPipeline.nextTransaction()=" + PermissionGuideServiceImpl.this.b.d());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /* access modifiers changed from: private */
    public void a(Context context, String bizType, String... permissions) {
        final long now = System.currentTimeMillis();
        final String[] strArr = permissions;
        final String str = bizType;
        final Context context2 = context;
        com.alipay.android.phone.mobilesdk.permission.b.a.a().a("Permissions", new Runnable() {
            public final void run() {
                com.alipay.android.phone.mobilesdk.permission.guide.a.d[] records = new com.alipay.android.phone.mobilesdk.permission.guide.a.d[strArr.length];
                int N = strArr.length;
                for (int i = 0; i < N; i++) {
                    com.alipay.android.phone.mobilesdk.permission.guide.a.d record = new com.alipay.android.phone.mobilesdk.permission.guide.a.d();
                    record.b = str;
                    record.c = strArr[i];
                    record.d = Long.valueOf(now);
                    records[i] = record;
                }
                PermissionGuideServiceImpl.this.a.a(context2, records);
            }
        }, "addRecord_" + bizType + "_" + h.a(permissions, "-"));
    }

    public boolean[] isPermissionGuideValid(String bizType, PermissionType[] permissions) {
        boolean[] results = null;
        if (permissions != null) {
            int length = permissions.length;
            if (length > 0) {
                Context context = LauncherApplicationAgent.getInstance().getApplicationContext();
                results = new boolean[length];
                if (length > 1) {
                    String[] permissionNames = new String[length];
                    for (int index = 0; index < length; index++) {
                        permissionNames[index] = permissions[index].name();
                    }
                    List combinations = e.a(permissionNames);
                    if (!combinations.isEmpty()) {
                        Iterator<String[]> it = combinations.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            String[] combination = it.next();
                            if (c.a(context, bizType, e.a(combination))) {
                                for (String name : combination) {
                                    results[e.a(permissions, name)] = true;
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < length; i++) {
                    if (!results[i]) {
                        results[i] = c.a(context, bizType, permissions[i].name());
                    }
                }
            }
        }
        return results;
    }

    public boolean hasPermission(String permission) {
        return b.a(LoggerFactory.getLogContext().getApplicationContext(), permission);
    }

    public boolean hasPermission(Context context, String permission) {
        return b.a(context, permission);
    }

    public void requestPermission(Activity activity, String[] permissions) {
        requestPermission(activity, permissions, true);
    }

    public void requestPermission(Activity activity, String[] permissions, boolean hasOnRequestPermissionsResultMethod) {
        b.a(activity, permissions, this.d.incrementAndGet(), hasOnRequestPermissionsResultMethod);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        b.a(requestCode, permissions, grantResults);
    }

    public boolean hasPermissionGuidePath(String bizCode, PermissionType type) {
        Context context = LoggerFactory.getLogContext().getApplicationContext();
        if (context == null) {
            LoggerFactory.getTraceLogger().info("Permissions", "hasPermissionSettingPath, context == null");
            return false;
        }
        PgTemplateInfoPB pgTemplateInfo = c.a(context, e.a(bizCode, type.name()));
        if (pgTemplateInfo == null) {
            LoggerFactory.getTraceLogger().info("Permissions", "hasPermissionSettingPath, pgTemplateInfo is null");
            return false;
        } else if (e.a(context, pgTemplateInfo.pgAction) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void startPermissionPathActivity(String bizCode, PermissionType type) {
        Activity activity = null;
        WeakReference reference = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity();
        if (reference != null) {
            activity = (Activity) reference.get();
        }
        startPermissionPathActivity(activity, bizCode, type);
    }

    public void startPermissionPathActivity(Activity activity, String bizCode, PermissionType type) {
        if (activity == null || activity.isFinishing()) {
            LoggerFactory.getTraceLogger().info("Permissions", "turnToPermissionSettingPath, activity is null");
            return;
        }
        PgTemplateInfoPB pgTemplateInfo = c.a((Context) activity, e.a(bizCode, type.name()));
        if (pgTemplateInfo == null) {
            LoggerFactory.getTraceLogger().info("Permissions", "turnToPermissionSettingPath, pgTemplateInfo is null");
            return;
        }
        Intent intent = e.a((Context) activity, pgTemplateInfo.pgAction);
        if (intent != null) {
            try {
                activity.startActivity(intent);
            } catch (Throwable th) {
                LoggerFactory.getTraceLogger().info("Permissions", "turnToPermissionSettingPath, startActivity error");
            }
        }
    }

    public String getPermissionGuideContent(String bizCode, PermissionType type) {
        Context context = LoggerFactory.getLogContext().getApplicationContext();
        if (context == null) {
            LoggerFactory.getTraceLogger().info("Permissions", "getPermissionGuideInfoStr, context == null");
            return null;
        }
        PgTemplateInfoPB pgTemplateInfo = c.a(context, e.a(bizCode, type.name()));
        if (pgTemplateInfo != null) {
            return pgTemplateInfo.pgActionContent;
        }
        LoggerFactory.getTraceLogger().info("Permissions", "getPermissionGuideInfoStr, pgTemplateInfo is null");
        return PermissionGuideSdk.getDefaultGuideConfigure().getDefaultGuideContent(type);
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
