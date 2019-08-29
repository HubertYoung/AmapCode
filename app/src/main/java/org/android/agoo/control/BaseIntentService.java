package org.android.agoo.control;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.OrangeAdapter;
import com.taobao.accs.utl.Utils;
import com.taobao.agoo.TaobaoConstants;
import java.util.ArrayList;
import java.util.Iterator;
import org.android.agoo.common.Config;
import org.android.agoo.common.MsgDO;
import org.android.agoo.intent.IntentUtil;
import org.android.agoo.message.MessageService;

public abstract class BaseIntentService extends IntentService {
    private static final String TAG = "BaseIntentService";
    private static final String msgStatus = "4";
    private AgooFactory agooFactory;
    private Context mContext = null;
    private MessageService messageService;
    private Messenger messenger = new Messenger(new Handler() {
        public void handleMessage(Message message) {
            if (message != null) {
                ALog.i(BaseIntentService.TAG, "handleMessage on receive msg", "msg", message.toString());
                Intent intent = (Intent) message.getData().getParcelable("intent");
                if (intent != null) {
                    ALog.i(BaseIntentService.TAG, "handleMessage get intent success", "intent", intent.toString());
                    BaseIntentService.this.onStart(intent, 0);
                }
            }
        }
    });
    private NotifManager notifyManager;

    /* access modifiers changed from: protected */
    public abstract void onError(Context context, String str);

    /* access modifiers changed from: protected */
    public abstract void onMessage(Context context, Intent intent);

    /* access modifiers changed from: protected */
    public abstract void onRegistered(Context context, String str);

    /* access modifiers changed from: protected */
    public void onUserCommand(Context context, Intent intent) {
    }

    public BaseIntentService() {
        super("AgooIntentService");
    }

    public IBinder onBind(Intent intent) {
        if (OrangeAdapter.isBindService() && Utils.isTarget26(this)) {
            getApplicationContext().bindService(new Intent(this, getClass()), new ServiceConnection() {
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                }

                public void onServiceDisconnected(ComponentName componentName) {
                }
            }, 1);
        }
        return this.messenger.getBinder();
    }

    public void onCreate() {
        AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
        this.notifyManager = new NotifManager();
        this.notifyManager.a(getApplicationContext());
        this.messageService = new MessageService();
        this.messageService.a(getApplicationContext());
        this.agooFactory = new AgooFactory();
        this.agooFactory.a(getApplicationContext(), this.notifyManager, this.messageService);
        super.onCreate();
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        this.mContext = getApplicationContext();
        if (intent != null) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                String agooCommand = IntentUtil.getAgooCommand(this.mContext);
                String thirdPushCommand = IntentUtil.getThirdPushCommand(this.mContext);
                StringBuilder sb = new StringBuilder("onHandleIntent,action=");
                sb.append(action);
                sb.append(",agooCommand=");
                sb.append(agooCommand);
                sb.append(",mipushCommand=");
                sb.append(thirdPushCommand);
                ALog.i(TAG, sb.toString(), new Object[0]);
                try {
                    if (TextUtils.equals(action, agooCommand)) {
                        String stringExtra = intent.getStringExtra("command");
                        StringBuilder sb2 = new StringBuilder("actionCommand --->[");
                        sb2.append(stringExtra);
                        sb2.append("]");
                        ALog.d(TAG, sb2.toString(), new Object[0]);
                        if (TextUtils.equals(stringExtra, "message_readed") || TextUtils.equals(stringExtra, "message_deleted")) {
                            onUserCommand(this.mContext, intent);
                        }
                    } else if (TextUtils.equals(action, thirdPushCommand)) {
                        String stringExtra2 = intent.getStringExtra("command");
                        String stringExtra3 = intent.getStringExtra("thirdPushId");
                        if (TextUtils.equals(stringExtra2, "mipushId_report")) {
                            this.notifyManager.a(stringExtra3, "MI_TOKEN", false);
                        } else if (TextUtils.equals(stringExtra2, "huaweipushId_report")) {
                            ALog.d(TAG, "HW_TOKEN report begin..regid=".concat(String.valueOf(stringExtra3)), new Object[0]);
                            this.notifyManager.a(stringExtra3, "HW_TOKEN", false);
                        } else if (TextUtils.equals(stringExtra2, "gcmpushId_report")) {
                            ALog.i(TAG, "GCM_TOKEN report begin..regid=".concat(String.valueOf(stringExtra3)), new Object[0]);
                            this.notifyManager.a(stringExtra3, TaobaoConstants.MESSAGE_SYSTEM_SOURCE_GCM, false);
                        }
                    } else if (action.equals("org.agoo.android.intent.action.RECEIVE")) {
                        handleRemoteMessage(this.mContext, intent);
                    } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                        handleRemovePackage(this.mContext, intent);
                    } else if (TextUtils.equals(action, "org.agoo.android.intent.action.REPORT") || TextUtils.equals(action, "android.net.conn.CONNECTIVITY_CHANGE") || TextUtils.equals(action, "android.intent.action.BOOT_COMPLETED") || TextUtils.equals(action, "android.intent.action.PACKAGE_ADDED") || TextUtils.equals(action, "android.intent.action.PACKAGE_REPLACED") || TextUtils.equals(action, "android.intent.action.USER_PRESENT") || TextUtils.equals(action, "android.intent.action.ACTION_POWER_CONNECTED") || TextUtils.equals(action, "android.intent.action.ACTION_POWER_DISCONNECTED")) {
                        try {
                            StringBuilder sb3 = new StringBuilder("is report cache msg,Config.isReportCacheMsg(mContext)=");
                            sb3.append(Config.d(this.mContext));
                            ALog.i(TAG, sb3.toString(), new Object[0]);
                            if (Config.d(this.mContext) && AdapterUtilityImpl.isNetworkConnected(this.mContext)) {
                                Config.e(this.mContext);
                                AgooFactory agooFactory2 = this.agooFactory;
                                agooFactory2.b.execute(new Runnable() {
                                    public void run() {
                                        ArrayList<MsgDO> b = AgooFactory.this.c.b();
                                        if (b != null && b.size() > 0) {
                                            ALog.e("AgooFactory", "reportCacheMsg", "size", Integer.valueOf(b.size()));
                                            Iterator<MsgDO> it = b.iterator();
                                            while (it.hasNext()) {
                                                MsgDO next = it.next();
                                                if (next != null) {
                                                    next.m = true;
                                                    NotifManager.b(next, null);
                                                }
                                            }
                                        }
                                    }
                                });
                                this.messageService.a();
                            }
                        } catch (Throwable th) {
                            ALog.e(TAG, "reportCacheMsg", th, new Object[0]);
                        }
                        long currentTimeMillis = System.currentTimeMillis();
                        if (ALog.isPrintLog(Level.I)) {
                            StringBuilder sb4 = new StringBuilder("is clear all msg=");
                            sb4.append(Config.b(this.mContext, currentTimeMillis));
                            ALog.i(TAG, sb4.toString(), new Object[0]);
                        }
                        if (Config.b(this.mContext, currentTimeMillis)) {
                            Config.a(this.mContext, currentTimeMillis);
                            this.messageService.a();
                        }
                    }
                } catch (Throwable th2) {
                    try {
                        if (ALog.isPrintLog(Level.E)) {
                            ALog.e(TAG, "onHandleIntent deal error", th2, new Object[0]);
                        }
                    } catch (Throwable th3) {
                        AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
                        throw th3;
                    }
                }
                AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
            }
        }
    }

    private final void handleRemovePackage(Context context, Intent intent) {
        if (intent != null && context != null) {
            String str = null;
            Uri data = intent.getData();
            if (data != null) {
                str = data.getSchemeSpecificPart();
            }
            if (!TextUtils.isEmpty(str)) {
                boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                if (ALog.isPrintLog(Level.D)) {
                    StringBuilder sb = new StringBuilder("handleRemovePackage---->[replacing:");
                    sb.append(booleanExtra);
                    sb.append("],uninstallPack=");
                    sb.append(str);
                    ALog.d(TAG, sb.toString(), new Object[0]);
                }
                if (!booleanExtra) {
                    NotifManager.a(str);
                }
            }
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:121:0x02db */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00b1 A[Catch:{ Throwable -> 0x01c3, Throwable -> 0x0323 }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00fd A[Catch:{ Throwable -> 0x01c3, Throwable -> 0x0323 }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0128 A[Catch:{ Throwable -> 0x01c3, Throwable -> 0x0323 }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0168  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x017a A[Catch:{ Throwable -> 0x01c3, Throwable -> 0x0323 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void handleRemoteMessage(android.content.Context r30, android.content.Intent r31) {
        /*
            r29 = this;
            r1 = r29
            r2 = r31
            java.lang.String r5 = "id"
            java.lang.String r5 = r2.getStringExtra(r5)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r6 = "body"
            java.lang.String r6 = r2.getStringExtra(r6)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r7 = "type"
            java.lang.String r7 = r2.getStringExtra(r7)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r8 = "message_source"
            java.lang.String r8 = r2.getStringExtra(r8)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r9 = "report"
            java.lang.String r9 = r2.getStringExtra(r9)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r10 = "encrypted"
            java.lang.String r10 = r2.getStringExtra(r10)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r11 = "extData"
            java.lang.String r11 = r2.getStringExtra(r11)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r12 = "oriData"
            java.lang.String r12 = r2.getStringExtra(r12)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r15 = "trace"
            r3 = -1
            long r3 = r2.getLongExtra(r15, r3)     // Catch:{ Throwable -> 0x0085 }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ Throwable -> 0x0085 }
            long r3 = r3.longValue()     // Catch:{ Throwable -> 0x0085 }
            r15 = r30
            r1.getTrace(r15, r3)     // Catch:{ Throwable -> 0x0083 }
            java.lang.String r3 = "msg_agoo_bundle"
            android.os.Bundle r3 = r2.getBundleExtra(r3)     // Catch:{ Throwable -> 0x0083 }
            if (r3 == 0) goto L_0x005a
            java.lang.String r4 = "accs_extra"
            java.io.Serializable r3 = r3.getSerializable(r4)     // Catch:{ Throwable -> 0x0083 }
            com.taobao.accs.base.TaoBaseService$ExtraInfo r3 = (com.taobao.accs.base.TaoBaseService.ExtraInfo) r3     // Catch:{ Throwable -> 0x0083 }
            goto L_0x005b
        L_0x005a:
            r3 = 0
        L_0x005b:
            java.lang.String r4 = "source"
            java.lang.String r4 = r2.getStringExtra(r4)     // Catch:{ Throwable -> 0x007d }
            boolean r16 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x0078 }
            if (r16 == 0) goto L_0x006a
            java.lang.String r16 = "oldsdk"
            goto L_0x006c
        L_0x006a:
            r16 = r4
        L_0x006c:
            java.lang.String r4 = "fromAppkey"
            java.lang.String r4 = r2.getStringExtra(r4)     // Catch:{ Throwable -> 0x0076 }
            r13 = r3
            r3 = r16
            goto L_0x00a5
        L_0x0076:
            r0 = move-exception
            goto L_0x007b
        L_0x0078:
            r0 = move-exception
            r16 = r4
        L_0x007b:
            r4 = r3
            goto L_0x0081
        L_0x007d:
            r0 = move-exception
            r4 = r3
            r16 = 0
        L_0x0081:
            r3 = r0
            goto L_0x008c
        L_0x0083:
            r0 = move-exception
            goto L_0x0088
        L_0x0085:
            r0 = move-exception
            r15 = r30
        L_0x0088:
            r3 = r0
            r4 = 0
            r16 = 0
        L_0x008c:
            java.lang.String r13 = "BaseIntentService"
            java.lang.String r14 = "_trace,t="
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r3 = r14.concat(r3)     // Catch:{ Throwable -> 0x0323 }
            r18 = r4
            r14 = 0
            java.lang.Object[] r4 = new java.lang.Object[r14]     // Catch:{ Throwable -> 0x0323 }
            com.taobao.accs.utl.ALog.e(r13, r3, r4)     // Catch:{ Throwable -> 0x0323 }
            r3 = r16
            r13 = r18
            r4 = 0
        L_0x00a5:
            com.taobao.accs.utl.ALog$Level r14 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0323 }
            boolean r14 = com.taobao.accs.utl.ALog.isPrintLog(r14)     // Catch:{ Throwable -> 0x0323 }
            r19 = r7
            r16 = 1
            if (r14 == 0) goto L_0x00fd
            java.lang.String r14 = "BaseIntentService"
            java.lang.String r7 = "handleRemoteMessage"
            r22 = r12
            r12 = 12
            java.lang.Object[] r12 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r18 = "message"
            r17 = 0
            r12[r17] = r18     // Catch:{ Throwable -> 0x0323 }
            r12[r16] = r6     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r18 = "source"
            r21 = 2
            r12[r21] = r18     // Catch:{ Throwable -> 0x0323 }
            r18 = 3
            r12[r18] = r8     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r18 = "msgId"
            r20 = 4
            r12[r20] = r18     // Catch:{ Throwable -> 0x0323 }
            r18 = 5
            r12[r18] = r5     // Catch:{ Throwable -> 0x0323 }
            r18 = 6
            java.lang.String r23 = "utdid"
            r12[r18] = r23     // Catch:{ Throwable -> 0x0323 }
            r18 = 7
            java.lang.String r23 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r30)     // Catch:{ Throwable -> 0x0323 }
            r12[r18] = r23     // Catch:{ Throwable -> 0x0323 }
            r18 = 8
            java.lang.String r23 = "fromPkg"
            r12[r18] = r23     // Catch:{ Throwable -> 0x0323 }
            r18 = 9
            r12[r18] = r3     // Catch:{ Throwable -> 0x0323 }
            r18 = 10
            java.lang.String r23 = "fromAppkey"
            r12[r18] = r23     // Catch:{ Throwable -> 0x0323 }
            r18 = 11
            r12[r18] = r4     // Catch:{ Throwable -> 0x0323 }
            com.taobao.accs.utl.ALog.i(r14, r7, r12)     // Catch:{ Throwable -> 0x0323 }
            goto L_0x00ff
        L_0x00fd:
            r22 = r12
        L_0x00ff:
            org.android.agoo.common.MsgDO r7 = new org.android.agoo.common.MsgDO     // Catch:{ Throwable -> 0x0323 }
            r7.<init>()     // Catch:{ Throwable -> 0x0323 }
            r7.a = r5     // Catch:{ Throwable -> 0x0323 }
            r7.b = r11     // Catch:{ Throwable -> 0x0323 }
            r7.e = r8     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r11 = "4"
            r7.l = r11     // Catch:{ Throwable -> 0x0323 }
            r7.j = r9     // Catch:{ Throwable -> 0x0323 }
            r7.g = r3     // Catch:{ Throwable -> 0x0323 }
            r7.h = r4     // Catch:{ Throwable -> 0x0323 }
            boolean r3 = com.taobao.accs.client.AdapterGlobalClientInfo.isFirstStartProc()     // Catch:{ Throwable -> 0x0323 }
            r7.k = r3     // Catch:{ Throwable -> 0x0323 }
            android.content.Context r3 = r1.mContext     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r3 = com.taobao.accs.utl.AdapterUtilityImpl.isNotificationEnabled(r3)     // Catch:{ Throwable -> 0x0323 }
            r7.n = r3     // Catch:{ Throwable -> 0x0323 }
            boolean r3 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x0323 }
            if (r3 != 0) goto L_0x0162
            r3 = 4
            java.lang.String r3 = java.lang.Integer.toString(r3)     // Catch:{ Throwable -> 0x0323 }
            boolean r3 = r3.equals(r10)     // Catch:{ Throwable -> 0x0323 }
            if (r3 == 0) goto L_0x014f
            java.lang.String r3 = "BaseIntentService"
            java.lang.String r4 = "message is encrypted, attemp to decrypt msg"
            r9 = 0
            java.lang.Object[] r10 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x0323 }
            com.taobao.accs.utl.ALog.i(r3, r4, r10)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r6 = org.android.agoo.control.AgooFactory.a(r6)     // Catch:{ Throwable -> 0x0323 }
            boolean r3 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x0323 }
            if (r3 == 0) goto L_0x0162
            java.lang.String r2 = "22"
            r7.d = r2     // Catch:{ Throwable -> 0x0323 }
            org.android.agoo.control.NotifManager.a(r7, r13)     // Catch:{ Throwable -> 0x0323 }
            return
        L_0x014f:
            java.lang.String r2 = "BaseIntentService"
            java.lang.String r3 = "msg encrypted flag not exist~~"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0323 }
            com.taobao.accs.utl.ALog.e(r2, r3, r4)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r2 = "24"
            r7.d = r2     // Catch:{ Throwable -> 0x0161 }
            org.android.agoo.control.NotifManager.b(r7, r13)     // Catch:{ Throwable -> 0x0161 }
            return
        L_0x0161:
            return
        L_0x0162:
            boolean r3 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x0323 }
            if (r3 == 0) goto L_0x017a
            java.lang.String r2 = "21"
            r7.d = r2     // Catch:{ Throwable -> 0x016f }
            org.android.agoo.control.NotifManager.b(r7, r13)     // Catch:{ Throwable -> 0x016f }
        L_0x016f:
            java.lang.String r2 = "BaseIntentService"
            java.lang.String r3 = "handleMessage--->[null]"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0323 }
            com.taobao.accs.utl.ALog.e(r2, r3, r4)     // Catch:{ Throwable -> 0x0323 }
            return
        L_0x017a:
            java.lang.String r3 = "body"
            r2.putExtra(r3, r6)     // Catch:{ Throwable -> 0x0323 }
            org.android.agoo.control.NotifManager.b(r7, r13)     // Catch:{ Throwable -> 0x01c3 }
            org.android.agoo.message.MessageService r3 = r1.messageService     // Catch:{ Throwable -> 0x01c3 }
            java.lang.String r4 = "0"
            r9 = r22
            r3.a(r5, r9, r4)     // Catch:{ Throwable -> 0x01c3 }
            com.taobao.accs.utl.UTMini r22 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x01c3 }
            r23 = 19999(0x4e1f, float:2.8025E-41)
            java.lang.String r24 = "Page_Push"
            java.lang.String r25 = "agoo_arrive_id"
            r26 = 0
            r27 = 0
            r3 = 2
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch:{ Throwable -> 0x01c3 }
            r3 = 0
            r9 = 0
            r4[r3] = r9     // Catch:{ Throwable -> 0x01c3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01c3 }
            java.lang.String r9 = "messageId="
            r3.<init>(r9)     // Catch:{ Throwable -> 0x01c3 }
            java.lang.String r9 = r7.a     // Catch:{ Throwable -> 0x01c3 }
            r3.append(r9)     // Catch:{ Throwable -> 0x01c3 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x01c3 }
            r4[r16] = r3     // Catch:{ Throwable -> 0x01c3 }
            r28 = r4
            r22.commitEvent(r23, r24, r25, r26, r27, r28)     // Catch:{ Throwable -> 0x01c3 }
            java.lang.String r3 = "accs"
            java.lang.String r4 = "agoo_arrive"
            java.lang.String r9 = "arrive"
            r10 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r3, r4, r9, r10)     // Catch:{ Throwable -> 0x01c3 }
            goto L_0x01df
        L_0x01c3:
            r0 = move-exception
            r3 = r0
            java.lang.String r4 = "BaseIntentService"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r10 = "report message Throwable--->t="
            r9.<init>(r10)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0323 }
            r9.append(r3)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r3 = r9.toString()     // Catch:{ Throwable -> 0x0323 }
            r9 = 0
            java.lang.Object[] r10 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x0323 }
            com.taobao.accs.utl.ALog.e(r4, r3, r10)     // Catch:{ Throwable -> 0x0323 }
        L_0x01df:
            org.android.agoo.message.MessageService r3 = r1.messageService     // Catch:{ Throwable -> 0x0323 }
            boolean r3 = r3.a(r5)     // Catch:{ Throwable -> 0x0323 }
            if (r3 == 0) goto L_0x021d
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0323 }
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)     // Catch:{ Throwable -> 0x0323 }
            if (r2 == 0) goto L_0x0211
            java.lang.String r2 = "BaseIntentService"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r4 = "handleRemoteMessage hasMessageDuplicate,messageId="
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0323 }
            r3.append(r5)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r4 = ",utdid="
            r3.append(r4)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r4 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r30)     // Catch:{ Throwable -> 0x0323 }
            r3.append(r4)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0323 }
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0323 }
            com.taobao.accs.utl.ALog.i(r2, r3, r4)     // Catch:{ Throwable -> 0x0323 }
        L_0x0211:
            java.lang.String r2 = "accs"
            java.lang.String r3 = "agoo_arrive"
            java.lang.String r4 = "arrive_dup"
            r5 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0323 }
            return
        L_0x021d:
            com.taobao.accs.utl.ALog$Level r3 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0323 }
            boolean r3 = com.taobao.accs.utl.ALog.isPrintLog(r3)     // Catch:{ Throwable -> 0x0323 }
            if (r3 == 0) goto L_0x0248
            java.lang.String r3 = "BaseIntentService"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r9 = "handleMessage--->["
            r4.<init>(r9)     // Catch:{ Throwable -> 0x0323 }
            r4.append(r6)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r9 = "],["
            r4.append(r9)     // Catch:{ Throwable -> 0x0323 }
            r4.append(r8)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r8 = "]"
            r4.append(r8)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0323 }
            r8 = 0
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x0323 }
            com.taobao.accs.utl.ALog.i(r3, r4, r9)     // Catch:{ Throwable -> 0x0323 }
        L_0x0248:
            java.lang.String r3 = "duplicate"
            java.lang.String r3 = r2.getStringExtra(r3)     // Catch:{ Throwable -> 0x0274 }
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0274 }
            if (r4 != 0) goto L_0x0298
            java.lang.String r4 = "1"
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch:{ Throwable -> 0x0274 }
            if (r3 == 0) goto L_0x0298
            int r3 = r6.hashCode()     // Catch:{ Throwable -> 0x0274 }
            org.android.agoo.message.MessageService r4 = r1.messageService     // Catch:{ Throwable -> 0x0274 }
            boolean r3 = r4.a(r5, r3)     // Catch:{ Throwable -> 0x0274 }
            if (r3 == 0) goto L_0x0298
            java.lang.String r3 = "accs"
            java.lang.String r4 = "agoo_arrive"
            java.lang.String r8 = "arrive_dupbody"
            r9 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r3, r4, r8, r9)     // Catch:{ Throwable -> 0x0274 }
            return
        L_0x0274:
            r0 = move-exception
            r3 = r0
            com.taobao.accs.utl.ALog$Level r4 = com.taobao.accs.utl.ALog.Level.E     // Catch:{ Throwable -> 0x0323 }
            boolean r4 = com.taobao.accs.utl.ALog.isPrintLog(r4)     // Catch:{ Throwable -> 0x0323 }
            if (r4 == 0) goto L_0x0298
            java.lang.String r4 = "BaseIntentService"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r9 = "hasMessageDuplicate message,e="
            r8.<init>(r9)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0323 }
            r8.append(r3)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r3 = r8.toString()     // Catch:{ Throwable -> 0x0323 }
            r8 = 0
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x0323 }
            com.taobao.accs.utl.ALog.e(r4, r3, r9)     // Catch:{ Throwable -> 0x0323 }
        L_0x0298:
            r3 = -1
            java.lang.String r4 = "notify"
            java.lang.String r4 = r2.getStringExtra(r4)     // Catch:{ Throwable -> 0x02a4 }
            int r4 = java.lang.Integer.parseInt(r4)     // Catch:{ Throwable -> 0x02a4 }
            r3 = r4
        L_0x02a4:
            java.lang.String r4 = ""
            java.lang.String r8 = "has_test"
            java.lang.String r8 = r2.getStringExtra(r8)     // Catch:{ Throwable -> 0x02d9 }
            boolean r9 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x02d9 }
            if (r9 != 0) goto L_0x02cd
            java.lang.String r9 = "1"
            boolean r8 = android.text.TextUtils.equals(r8, r9)     // Catch:{ Throwable -> 0x02d9 }
            if (r8 == 0) goto L_0x02cd
            org.android.agoo.message.MessageService r8 = r1.messageService     // Catch:{ Throwable -> 0x02d9 }
            r9 = r19
            r8.a(r5, r6, r9, r3)     // Catch:{ Throwable -> 0x02db }
            java.lang.String r8 = "accs"
            java.lang.String r10 = "agoo_arrive"
            java.lang.String r11 = "arrive_test"
            r12 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r8, r10, r11, r12)     // Catch:{ Throwable -> 0x02db }
            return
        L_0x02cd:
            r9 = r19
            java.lang.Class r8 = r29.getClass()     // Catch:{ Throwable -> 0x02db }
            java.lang.String r8 = r8.getName()     // Catch:{ Throwable -> 0x02db }
            r4 = r8
            goto L_0x02db
        L_0x02d9:
            r9 = r19
        L_0x02db:
            org.android.agoo.message.MessageService r8 = r1.messageService     // Catch:{ Throwable -> 0x0323 }
            r8.a(r5, r6, r9, r3)     // Catch:{ Throwable -> 0x0323 }
            com.taobao.accs.utl.UTMini r22 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x0323 }
            r23 = 19999(0x4e1f, float:2.8025E-41)
            java.lang.String r24 = "Page_Push"
            java.lang.String r25 = "agoo_arrive_real_id"
            r26 = 0
            r27 = 0
            r3 = 2
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Throwable -> 0x0323 }
            r5 = 0
            r6 = 0
            r3[r5] = r6     // Catch:{ Throwable -> 0x0323 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r6 = "messageId="
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r6 = r7.a     // Catch:{ Throwable -> 0x0323 }
            r5.append(r6)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0323 }
            r3[r16] = r5     // Catch:{ Throwable -> 0x0323 }
            r28 = r3
            r22.commitEvent(r23, r24, r25, r26, r27, r28)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r3 = "accs"
            java.lang.String r5 = "agoo_arrive"
            java.lang.String r6 = "arrive_real_"
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ Throwable -> 0x0323 }
            java.lang.String r4 = r6.concat(r4)     // Catch:{ Throwable -> 0x0323 }
            r6 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r3, r5, r4, r6)     // Catch:{ Throwable -> 0x0323 }
            r29.onMessage(r30, r31)     // Catch:{ Throwable -> 0x0323 }
            return
        L_0x0323:
            r0 = move-exception
            r2 = r0
            java.lang.String r3 = "accs"
            java.lang.String r4 = "agoo_arrive"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "arrive_exception"
            r5.<init>(r6)
            java.lang.String r2 = r2.toString()
            r5.append(r2)
            java.lang.String r2 = r5.toString()
            r5 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r3, r4, r2, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.control.BaseIntentService.handleRemoteMessage(android.content.Context, android.content.Intent):void");
    }

    private final String getTrace(Context context, long j) {
        String str = null;
        String str2 = TextUtils.isEmpty(null) ? "unknow" : null;
        if (TextUtils.isEmpty(null)) {
            str = "unknow";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("appkey");
        stringBuffer.append(MergeUtil.SEPARATOR_KV);
        stringBuffer.append(j);
        stringBuffer.append(MergeUtil.SEPARATOR_KV);
        stringBuffer.append(System.currentTimeMillis());
        stringBuffer.append(MergeUtil.SEPARATOR_KV);
        stringBuffer.append(str2);
        stringBuffer.append(MergeUtil.SEPARATOR_KV);
        stringBuffer.append(str);
        return stringBuffer.toString();
    }

    public static final void runIntentInService(Context context, Intent intent, String str) {
        try {
            intent.setClassName(context, str);
            context.startService(intent);
        } catch (Throwable th) {
            ALog.w(TAG, "runIntentInService", th, new Object[0]);
        }
    }
}
