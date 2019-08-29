package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import com.alipay.sdk.util.h;
import java.util.ArrayList;
import java.util.HashMap;

public class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "LocalBroadcastManager";
    private static LocalBroadcastManager mInstance;
    private static final Object mLock = new Object();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<>();
    private final Context mAppContext;
    private final Handler mHandler;
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<>();
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap<>();

    static class BroadcastRecord {
        final Intent a;
        final ArrayList<ReceiverRecord> b;

        BroadcastRecord(Intent intent, ArrayList<ReceiverRecord> arrayList) {
            this.a = intent;
            this.b = arrayList;
        }
    }

    static class ReceiverRecord {
        final IntentFilter a;
        final BroadcastReceiver b;
        boolean c;

        ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.a = intentFilter;
            this.b = broadcastReceiver;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Receiver{");
            sb.append(this.b);
            sb.append(" filter=");
            sb.append(this.a);
            sb.append(h.d);
            return sb.toString();
        }
    }

    public static LocalBroadcastManager getInstance(Context context) {
        LocalBroadcastManager localBroadcastManager;
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new LocalBroadcastManager(context.getApplicationContext());
            }
            localBroadcastManager = mInstance;
        }
        return localBroadcastManager;
    }

    private LocalBroadcastManager(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    super.handleMessage(message);
                } else {
                    LocalBroadcastManager.this.executePendingBroadcasts();
                }
            }
        };
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        synchronized (this.mReceivers) {
            ReceiverRecord receiverRecord = new ReceiverRecord(intentFilter, broadcastReceiver);
            ArrayList arrayList = this.mReceivers.get(broadcastReceiver);
            if (arrayList == null) {
                arrayList = new ArrayList(1);
                this.mReceivers.put(broadcastReceiver, arrayList);
            }
            arrayList.add(intentFilter);
            for (int i = 0; i < intentFilter.countActions(); i++) {
                String action = intentFilter.getAction(i);
                ArrayList arrayList2 = this.mActions.get(action);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList(1);
                    this.mActions.put(action, arrayList2);
                }
                arrayList2.add(receiverRecord);
            }
        }
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        synchronized (this.mReceivers) {
            ArrayList remove = this.mReceivers.remove(broadcastReceiver);
            if (remove != null) {
                for (int i = 0; i < remove.size(); i++) {
                    IntentFilter intentFilter = (IntentFilter) remove.get(i);
                    for (int i2 = 0; i2 < intentFilter.countActions(); i2++) {
                        String action = intentFilter.getAction(i2);
                        ArrayList arrayList = this.mActions.get(action);
                        if (arrayList != null) {
                            int i3 = 0;
                            while (i3 < arrayList.size()) {
                                if (((ReceiverRecord) arrayList.get(i3)).b == broadcastReceiver) {
                                    arrayList.remove(i3);
                                    i3--;
                                }
                                i3++;
                            }
                            if (arrayList.size() <= 0) {
                                this.mActions.remove(action);
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x010f, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean sendBroadcast(android.content.Intent r25) {
        /*
            r24 = this;
            r1 = r24
            r2 = r25
            java.util.HashMap<android.content.BroadcastReceiver, java.util.ArrayList<android.content.IntentFilter>> r3 = r1.mReceivers
            monitor-enter(r3)
            java.lang.String r11 = r25.getAction()     // Catch:{ all -> 0x0113 }
            android.content.Context r4 = r1.mAppContext     // Catch:{ all -> 0x0113 }
            android.content.ContentResolver r4 = r4.getContentResolver()     // Catch:{ all -> 0x0113 }
            java.lang.String r12 = r2.resolveTypeIfNeeded(r4)     // Catch:{ all -> 0x0113 }
            android.net.Uri r13 = r25.getData()     // Catch:{ all -> 0x0113 }
            java.lang.String r14 = r25.getScheme()     // Catch:{ all -> 0x0113 }
            java.util.Set r15 = r25.getCategories()     // Catch:{ all -> 0x0113 }
            int r4 = r25.getFlags()     // Catch:{ all -> 0x0113 }
            r4 = r4 & 8
            r10 = 0
            if (r4 == 0) goto L_0x002d
            r16 = 1
            goto L_0x002f
        L_0x002d:
            r16 = 0
        L_0x002f:
            if (r16 == 0) goto L_0x004b
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0113 }
            java.lang.String r5 = "Resolving type "
            r4.<init>(r5)     // Catch:{ all -> 0x0113 }
            r4.append(r12)     // Catch:{ all -> 0x0113 }
            java.lang.String r5 = " scheme "
            r4.append(r5)     // Catch:{ all -> 0x0113 }
            r4.append(r14)     // Catch:{ all -> 0x0113 }
            java.lang.String r5 = " of intent "
            r4.append(r5)     // Catch:{ all -> 0x0113 }
            r4.append(r2)     // Catch:{ all -> 0x0113 }
        L_0x004b:
            java.util.HashMap<java.lang.String, java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord>> r4 = r1.mActions     // Catch:{ all -> 0x0113 }
            java.lang.String r5 = r25.getAction()     // Catch:{ all -> 0x0113 }
            java.lang.Object r4 = r4.get(r5)     // Catch:{ all -> 0x0113 }
            r8 = r4
            java.util.ArrayList r8 = (java.util.ArrayList) r8     // Catch:{ all -> 0x0113 }
            if (r8 == 0) goto L_0x0110
            if (r16 == 0) goto L_0x0066
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0113 }
            java.lang.String r5 = "Action list: "
            r4.<init>(r5)     // Catch:{ all -> 0x0113 }
            r4.append(r8)     // Catch:{ all -> 0x0113 }
        L_0x0066:
            r4 = 0
            r6 = r4
            r7 = 0
        L_0x0069:
            int r4 = r8.size()     // Catch:{ all -> 0x0113 }
            if (r7 >= r4) goto L_0x00e0
            java.lang.Object r4 = r8.get(r7)     // Catch:{ all -> 0x0113 }
            r5 = r4
            android.support.v4.content.LocalBroadcastManager$ReceiverRecord r5 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r5     // Catch:{ all -> 0x0113 }
            if (r16 == 0) goto L_0x0084
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0113 }
            java.lang.String r9 = "Matching against filter "
            r4.<init>(r9)     // Catch:{ all -> 0x0113 }
            android.content.IntentFilter r9 = r5.a     // Catch:{ all -> 0x0113 }
            r4.append(r9)     // Catch:{ all -> 0x0113 }
        L_0x0084:
            boolean r4 = r5.c     // Catch:{ all -> 0x0113 }
            if (r4 == 0) goto L_0x0095
            r19 = r7
            r20 = r8
            r18 = r11
            r21 = r12
            r22 = r13
            r13 = 1
            r11 = r6
            goto L_0x00d3
        L_0x0095:
            android.content.IntentFilter r4 = r5.a     // Catch:{ all -> 0x0113 }
            java.lang.String r17 = "LocalBroadcastManager"
            r9 = r5
            r5 = r11
            r18 = r11
            r11 = r6
            r6 = r12
            r19 = r7
            r7 = r14
            r20 = r8
            r8 = r13
            r21 = r12
            r22 = r13
            r13 = 1
            r12 = r9
            r9 = r15
            r10 = r17
            int r4 = r4.match(r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x0113 }
            if (r4 < 0) goto L_0x00d3
            if (r16 == 0) goto L_0x00c4
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0113 }
            java.lang.String r6 = "  Filter matched!  match=0x"
            r5.<init>(r6)     // Catch:{ all -> 0x0113 }
            java.lang.String r4 = java.lang.Integer.toHexString(r4)     // Catch:{ all -> 0x0113 }
            r5.append(r4)     // Catch:{ all -> 0x0113 }
        L_0x00c4:
            if (r11 != 0) goto L_0x00cc
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ all -> 0x0113 }
            r6.<init>()     // Catch:{ all -> 0x0113 }
            goto L_0x00cd
        L_0x00cc:
            r6 = r11
        L_0x00cd:
            r6.add(r12)     // Catch:{ all -> 0x0113 }
            r12.c = r13     // Catch:{ all -> 0x0113 }
            goto L_0x00d4
        L_0x00d3:
            r6 = r11
        L_0x00d4:
            int r7 = r19 + 1
            r11 = r18
            r8 = r20
            r12 = r21
            r13 = r22
            r10 = 0
            goto L_0x0069
        L_0x00e0:
            r11 = r6
            r13 = 1
            if (r11 == 0) goto L_0x0110
            r4 = 0
        L_0x00e5:
            int r5 = r11.size()     // Catch:{ all -> 0x0113 }
            if (r4 >= r5) goto L_0x00f7
            java.lang.Object r5 = r11.get(r4)     // Catch:{ all -> 0x0113 }
            android.support.v4.content.LocalBroadcastManager$ReceiverRecord r5 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r5     // Catch:{ all -> 0x0113 }
            r6 = 0
            r5.c = r6     // Catch:{ all -> 0x0113 }
            int r4 = r4 + 1
            goto L_0x00e5
        L_0x00f7:
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r4 = r1.mPendingBroadcasts     // Catch:{ all -> 0x0113 }
            android.support.v4.content.LocalBroadcastManager$BroadcastRecord r5 = new android.support.v4.content.LocalBroadcastManager$BroadcastRecord     // Catch:{ all -> 0x0113 }
            r5.<init>(r2, r11)     // Catch:{ all -> 0x0113 }
            r4.add(r5)     // Catch:{ all -> 0x0113 }
            android.os.Handler r2 = r1.mHandler     // Catch:{ all -> 0x0113 }
            boolean r2 = r2.hasMessages(r13)     // Catch:{ all -> 0x0113 }
            if (r2 != 0) goto L_0x010e
            android.os.Handler r2 = r1.mHandler     // Catch:{ all -> 0x0113 }
            r2.sendEmptyMessage(r13)     // Catch:{ all -> 0x0113 }
        L_0x010e:
            monitor-exit(r3)     // Catch:{ all -> 0x0113 }
            return r13
        L_0x0110:
            r6 = 0
            monitor-exit(r3)     // Catch:{ all -> 0x0113 }
            return r6
        L_0x0113:
            r0 = move-exception
            r2 = r0
            monitor-exit(r3)     // Catch:{ all -> 0x0113 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.LocalBroadcastManager.sendBroadcast(android.content.Intent):boolean");
    }

    public void sendBroadcastSync(Intent intent) {
        if (sendBroadcast(intent)) {
            executePendingBroadcasts();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        if (r2 >= r1.length) goto L_0x0000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        r3 = r1[r2];
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0028, code lost:
        if (r4 >= r3.b.size()) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002a, code lost:
        r3.b.get(r4).b.onReceive(r8.mAppContext, r3.a);
        r4 = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003e, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001a, code lost:
        r2 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void executePendingBroadcasts() {
        /*
            r8 = this;
        L_0x0000:
            java.util.HashMap<android.content.BroadcastReceiver, java.util.ArrayList<android.content.IntentFilter>> r0 = r8.mReceivers
            monitor-enter(r0)
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r1 = r8.mPendingBroadcasts     // Catch:{ all -> 0x0041 }
            int r1 = r1.size()     // Catch:{ all -> 0x0041 }
            if (r1 > 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x0041 }
            return
        L_0x000d:
            android.support.v4.content.LocalBroadcastManager$BroadcastRecord[] r1 = new android.support.v4.content.LocalBroadcastManager.BroadcastRecord[r1]     // Catch:{ all -> 0x0041 }
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r2 = r8.mPendingBroadcasts     // Catch:{ all -> 0x0041 }
            r2.toArray(r1)     // Catch:{ all -> 0x0041 }
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r2 = r8.mPendingBroadcasts     // Catch:{ all -> 0x0041 }
            r2.clear()     // Catch:{ all -> 0x0041 }
            monitor-exit(r0)     // Catch:{ all -> 0x0041 }
            r0 = 0
            r2 = 0
        L_0x001c:
            int r3 = r1.length
            if (r2 >= r3) goto L_0x0000
            r3 = r1[r2]
            r4 = 0
        L_0x0022:
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord> r5 = r3.b
            int r5 = r5.size()
            if (r4 >= r5) goto L_0x003e
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord> r5 = r3.b
            java.lang.Object r5 = r5.get(r4)
            android.support.v4.content.LocalBroadcastManager$ReceiverRecord r5 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r5
            android.content.BroadcastReceiver r5 = r5.b
            android.content.Context r6 = r8.mAppContext
            android.content.Intent r7 = r3.a
            r5.onReceive(r6, r7)
            int r4 = r4 + 1
            goto L_0x0022
        L_0x003e:
            int r2 = r2 + 1
            goto L_0x001c
        L_0x0041:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0041 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.LocalBroadcastManager.executePendingBroadcasts():void");
    }
}
