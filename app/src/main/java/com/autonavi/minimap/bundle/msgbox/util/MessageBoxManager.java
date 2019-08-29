package com.autonavi.minimap.bundle.msgbox.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.map.db.model.Msgbox;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.Builder;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService.MainMapUIUpdater;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService.UIUpdater;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.network.DisplayLogCallback;
import com.autonavi.minimap.bundle.msgbox.network.DisplayLogParam;
import com.autonavi.minimap.bundle.msgbox.network.MessageBoxCallback;
import com.autonavi.minimap.bundle.msgbox.page.MsgboxPage;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class MessageBoxManager {
    public static final String SP_KEY_MSG_BOX_CATEGORY_VERSION = "msg_box_category_version";
    public static final String SP_KEY_MSG_BOX_OLDEST_MESSAGE_TIME = "msg_box_oldest_message_time";
    public static final String SP_NAME_MessageBox = "MessageBox";
    public static final String SP_NAME_PUSH_MSG = "PUSH_MSG";
    private static final Set<String> a = new HashSet();
    private static long b = -1;
    private final ReentrantLock c;
    private e d;
    private WeakReference<MessageBoxCallback> e;
    private volatile long f;
    private AmapMessage g;
    private boolean h;

    public static abstract class a {
        public abstract boolean a(AmapMessage amapMessage);
    }

    public interface b {
        void a(List<AmapMessage> list, List<btb> list2, boolean z);
    }

    public class c extends a {
        public c() {
        }

        public final boolean a(AmapMessage amapMessage) {
            if (amapMessage.isUnRead || (AmapMessage.TYPE_ACTIVITY.equals(amapMessage.type) && amapMessage.tag == 7 && "1".equals(amapMessage.isEnable))) {
                return amapMessage.expireAt == 0 ? true : true;
            }
            return false;
        }
    }

    public static class d implements b {
        private MainMapUIUpdater a;

        public d(MainMapUIUpdater mainMapUIUpdater) {
            this.a = mainMapUIUpdater;
        }

        /* JADX WARNING: Removed duplicated region for block: B:87:0x018d  */
        /* JADX WARNING: Removed duplicated region for block: B:90:0x0199  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(java.util.List<com.autonavi.minimap.bundle.msgbox.entity.AmapMessage> r12, java.util.List<defpackage.btb> r13, boolean r14) {
            /*
                r11 = this;
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                java.util.ArrayList r7 = new java.util.ArrayList
                r7.<init>()
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>()
                java.util.HashMap r2 = new java.util.HashMap
                r2.<init>()
                java.util.HashMap r3 = new java.util.HashMap
                r3.<init>()
                r4 = 0
                r5 = 0
            L_0x001b:
                int r6 = r13.size()
                if (r5 >= r6) goto L_0x0040
                java.lang.Object r6 = r13.get(r5)
                btb r6 = (defpackage.btb) r6
                java.lang.String r8 = r6.a
                r3.put(r8, r6)
                java.lang.String r8 = "1"
                java.lang.String r9 = r6.f
                boolean r8 = r8.equals(r9)
                if (r8 == 0) goto L_0x003d
                java.lang.String r8 = r6.a
                java.lang.String r6 = r6.a
                r2.put(r8, r6)
            L_0x003d:
                int r5 = r5 + 1
                goto L_0x001b
            L_0x0040:
                int r13 = r12.size()
                if (r13 != 0) goto L_0x0058
                com.autonavi.minimap.bundle.msgbox.api.IMsgboxService$MainMapUIUpdater r12 = r11.a
                if (r12 == 0) goto L_0x0057
                r7.clear()
                com.autonavi.minimap.bundle.msgbox.api.IMsgboxService$MainMapUIUpdater r1 = r11.a
                r2 = 0
                r3 = 1
                r4 = 0
                r6 = 0
                r5 = r7
                r1.updateUI(r2, r3, r4, r5, r6)
            L_0x0057:
                return
            L_0x0058:
                java.util.Iterator r13 = r12.iterator()
            L_0x005c:
                boolean r3 = r13.hasNext()
                if (r3 == 0) goto L_0x009d
                java.lang.Object r3 = r13.next()
                com.autonavi.minimap.bundle.msgbox.entity.AmapMessage r3 = (com.autonavi.minimap.bundle.msgbox.entity.AmapMessage) r3
                java.lang.String r5 = "type_msg"
                java.lang.String r6 = r3.type
                boolean r5 = r5.equals(r6)
                if (r5 == 0) goto L_0x005c
                boolean r5 = r3.isNewComing
                if (r5 == 0) goto L_0x005c
                boolean r5 = r3.isADMsg()
                if (r5 != 0) goto L_0x005c
                java.lang.String r5 = r3.category
                java.lang.Object r5 = r2.get(r5)
                java.lang.String r5 = (java.lang.String) r5
                boolean r5 = android.text.TextUtils.isEmpty(r5)
                if (r5 == 0) goto L_0x0097
                java.lang.String r5 = r3.label
                boolean r5 = android.text.TextUtils.isEmpty(r5)
                if (r5 != 0) goto L_0x009a
                r7.add(r3)
                goto L_0x009a
            L_0x0097:
                r7.add(r3)
            L_0x009a:
                int r4 = r4 + 1
                goto L_0x005c
            L_0x009d:
                java.util.Iterator r12 = r12.iterator()
            L_0x00a1:
                boolean r13 = r12.hasNext()
                if (r13 == 0) goto L_0x00e9
                java.lang.Object r13 = r12.next()
                com.autonavi.minimap.bundle.msgbox.entity.AmapMessage r13 = (com.autonavi.minimap.bundle.msgbox.entity.AmapMessage) r13
                long r2 = java.lang.System.currentTimeMillis()
                long r5 = r13.expireAt
                r8 = 0
                int r5 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
                if (r5 == 0) goto L_0x00bf
                long r5 = r13.expireAt
                int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
                if (r2 > 0) goto L_0x00a1
            L_0x00bf:
                java.lang.String r2 = "type_activity"
                java.lang.String r3 = r13.type
                boolean r2 = r2.equals(r3)
                if (r2 == 0) goto L_0x00dd
                int r2 = r13.tag
                r3 = 7
                if (r2 != r3) goto L_0x00dd
                java.lang.String r2 = "1"
                java.lang.String r3 = r13.isEnable
                boolean r2 = r2.equals(r3)
                if (r2 == 0) goto L_0x00dd
                r1.add(r13)
                goto L_0x00a1
            L_0x00dd:
                int r2 = r13.page
                if (r2 > 0) goto L_0x00a1
                boolean r2 = r13.showOnMap
                if (r2 == 0) goto L_0x00a1
                r0.add(r13)
                goto L_0x00a1
            L_0x00e9:
                int r12 = r1.size()
                r13 = 0
                if (r12 <= 0) goto L_0x0106
                dbd r12 = new dbd
                r12.<init>()
                java.util.Collections.sort(r1, r12)
                int r12 = r1.size()
                int r12 = r12 + -1
                java.lang.Object r12 = r1.get(r12)
                com.autonavi.minimap.bundle.msgbox.entity.AmapMessage r12 = (com.autonavi.minimap.bundle.msgbox.entity.AmapMessage) r12
                r6 = r12
                goto L_0x0107
            L_0x0106:
                r6 = r13
            L_0x0107:
                int r12 = r0.size()
                if (r12 != 0) goto L_0x011b
                com.autonavi.minimap.bundle.msgbox.api.IMsgboxService$MainMapUIUpdater r12 = r11.a
                if (r12 == 0) goto L_0x01a5
                com.autonavi.minimap.bundle.msgbox.api.IMsgboxService$MainMapUIUpdater r1 = r11.a
                r2 = 0
                r3 = 1
                r5 = r7
                r1.updateUI(r2, r3, r4, r5, r6)
                goto L_0x01a5
            L_0x011b:
                java.util.ArrayList r12 = new java.util.ArrayList
                r12.<init>()
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>()
                java.util.ArrayList r2 = new java.util.ArrayList
                r2.<init>()
                java.util.Iterator r0 = r0.iterator()
            L_0x012e:
                boolean r3 = r0.hasNext()
                r5 = 300(0x12c, float:4.2E-43)
                r8 = 100
                r9 = 500(0x1f4, float:7.0E-43)
                if (r3 == 0) goto L_0x0162
                java.lang.Object r3 = r0.next()
                com.autonavi.minimap.bundle.msgbox.entity.AmapMessage r3 = (com.autonavi.minimap.bundle.msgbox.entity.AmapMessage) r3
                int r10 = r3.priority
                if (r10 > r9) goto L_0x012e
                int r10 = r3.priority
                if (r10 > r8) goto L_0x014c
                r12.add(r3)
                goto L_0x012e
            L_0x014c:
                int r8 = r3.priority
                if (r8 > r5) goto L_0x015a
                boolean r5 = r3.isADDisplay()
                if (r5 != 0) goto L_0x012e
                r1.add(r3)
                goto L_0x012e
            L_0x015a:
                int r5 = r3.priority
                if (r5 > r9) goto L_0x012e
                r2.add(r3)
                goto L_0x012e
            L_0x0162:
                boolean r0 = r12.isEmpty()
                if (r0 != 0) goto L_0x016e
                java.util.List r12 = defpackage.dbf.a(r12, r8)
            L_0x016c:
                r2 = r12
                goto L_0x018b
            L_0x016e:
                boolean r12 = r1.isEmpty()
                if (r12 != 0) goto L_0x017f
                java.util.List r12 = defpackage.dbf.a(r1)
                if (r12 != 0) goto L_0x016c
                java.util.List r12 = defpackage.dbf.a(r1, r5)
                goto L_0x016c
            L_0x017f:
                boolean r12 = r2.isEmpty()
                if (r12 != 0) goto L_0x018a
                java.util.List r12 = defpackage.dbf.a(r2, r9)
                goto L_0x016c
            L_0x018a:
                r2 = r13
            L_0x018b:
                if (r2 == 0) goto L_0x0199
                com.autonavi.minimap.bundle.msgbox.api.IMsgboxService$MainMapUIUpdater r12 = r11.a
                if (r12 == 0) goto L_0x01a5
                com.autonavi.minimap.bundle.msgbox.api.IMsgboxService$MainMapUIUpdater r1 = r11.a
                r3 = 0
                r5 = r7
                r1.updateUI(r2, r3, r4, r5, r6)
                goto L_0x01a5
            L_0x0199:
                com.autonavi.minimap.bundle.msgbox.api.IMsgboxService$MainMapUIUpdater r12 = r11.a
                if (r12 == 0) goto L_0x01a5
                com.autonavi.minimap.bundle.msgbox.api.IMsgboxService$MainMapUIUpdater r1 = r11.a
                r2 = 0
                r3 = 1
                r5 = r7
                r1.updateUI(r2, r3, r4, r5, r6)
            L_0x01a5:
                if (r14 == 0) goto L_0x01c5
                org.json.JSONObject r12 = new org.json.JSONObject
                r12.<init>()
                java.lang.String r13 = "name"
                int r14 = r7.size()     // Catch:{ JSONException -> 0x01be }
                if (r14 <= 0) goto L_0x01b8
                java.lang.String r14 = "有红点"
                goto L_0x01bb
            L_0x01b8:
                java.lang.String r14 = "无红点"
            L_0x01bb:
                r12.put(r13, r14)     // Catch:{ JSONException -> 0x01be }
            L_0x01be:
                java.lang.String r13 = "P00376"
                java.lang.String r14 = "B001"
                com.amap.bundle.statistics.LogManager.actionLogV2(r13, r14, r12)
            L_0x01c5:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.d.a(java.util.List, java.util.List, boolean):void");
        }
    }

    static class e extends Thread {
        final Set<das> a = new HashSet();
        private final ReentrantLock b = new ReentrantLock();
        private final AtomicBoolean c = new AtomicBoolean(true);
        private final AtomicBoolean d = new AtomicBoolean(false);

        public e(Set<das> set, boolean z) {
            this.a.addAll(set);
            this.d.set(z);
        }

        /* JADX INFO: finally extract failed */
        public final void run() {
            ArrayList arrayList = new ArrayList();
            AMapAppGlobal.getApplication();
            dbc b2 = dbc.b();
            List<Msgbox> c2 = b2.c();
            ArrayList arrayList2 = new ArrayList();
            for (Msgbox convertToAmapMessage : c2) {
                arrayList2.add(AmapMessage.convertToAmapMessage(convertToAmapMessage));
            }
            if (arrayList2.size() > 0) {
                arrayList.addAll(arrayList2);
            }
            try {
                this.b.lock();
                if (!this.d.get()) {
                    this.c.set(false);
                }
                this.b.unlock();
                List<btb> d2 = b2.d();
                try {
                    this.b.lock();
                    this.c.set(false);
                    this.b.unlock();
                    for (das next : this.a) {
                        ArrayList arrayList3 = new ArrayList();
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            AmapMessage amapMessage = (AmapMessage) it.next();
                            a aVar = next.b;
                            if (aVar != null && aVar.a(amapMessage)) {
                                arrayList3.add(amapMessage.clone());
                            }
                        }
                        if (next.a != null) {
                            next.a.a(arrayList3, d2, false);
                        }
                    }
                } catch (Throwable th) {
                    this.b.unlock();
                    throw th;
                }
            } catch (Throwable th2) {
                this.b.unlock();
                throw th2;
            }
        }

        public final boolean a(Set<das> set, boolean z) {
            try {
                this.b.lock();
                if (this.c.get()) {
                    this.a.addAll(set);
                    if (z) {
                        this.d.set(true);
                    }
                    return true;
                }
                this.b.unlock();
                return false;
            } finally {
                this.b.unlock();
            }
        }
    }

    static class f {
        static MessageBoxManager a = new MessageBoxManager(0);
    }

    /* synthetic */ MessageBoxManager(byte b2) {
        this();
    }

    private MessageBoxManager() {
        this.c = new ReentrantLock();
        this.f = 0;
        this.g = null;
        this.h = true;
    }

    public static MessageBoxManager getInstance() {
        return f.a;
    }

    public void fetchMessage(int i, boolean z, UIUpdater uIUpdater) {
        if (i != 0) {
            switch (i) {
                case 1:
                    getMessages(new daz(uIUpdater), z, new day());
                    return;
                case 2:
                    getMessages(new dbh(uIUpdater), true, new dbg());
                    return;
                case 3:
                    getMessages(new dbj(uIUpdater), true, new dbi());
                    return;
                case 4:
                    getMessages(new dbl(uIUpdater), true, new dbk());
                    break;
            }
        }
    }

    public void fetchMessageFromMainMap(int i, boolean z, MainMapUIUpdater mainMapUIUpdater) {
        if (i == 0) {
            getMessages(new d(mainMapUIUpdater), z, false, new c(), "1");
        }
    }

    public void reset() {
        this.g = null;
        this.h = false;
    }

    public AmapMessage getCurDispBubbleMsg() {
        return this.g;
    }

    public void setCurDispBubbleMsg(AmapMessage amapMessage) {
        this.g = amapMessage;
    }

    public void getMessagesOnAppResume(boolean z, MainMapUIUpdater mainMapUIUpdater) {
        if (z) {
            this.h = true;
        } else if (this.h) {
            this.h = false;
            getMessages(new d(mainMapUIUpdater), false, false, new c(), "2");
        }
    }

    public void getMessageInBackground(Activity activity, boolean z) {
        if (z) {
            dba.a((Context) activity).a(0);
        } else {
            dba.a((Context) activity).a();
        }
    }

    public void getMessages(b bVar, boolean z, a aVar) {
        getMessages(bVar, z, false, aVar);
    }

    public void getMessages(b bVar, boolean z, boolean z2, a aVar) {
        getMessages(bVar, z, z2, aVar, null);
    }

    public void getMessages(b bVar, boolean z, boolean z2, a aVar, String str) {
        if (bVar != null) {
            das das = new das(bVar, aVar);
            HashSet hashSet = new HashSet();
            hashSet.add(das);
            if (z) {
                retrieveLocalMessages(hashSet, z2);
                return;
            }
            retrieveRemoteMessages(hashSet, str);
        }
    }

    public void removeMessages(AmapMessage[] amapMessageArr) {
        if (amapMessageArr != null && amapMessageArr.length > 0) {
            ArrayList arrayList = new ArrayList();
            for (AmapMessage amapMessage : amapMessageArr) {
                arrayList.add(amapMessage.id);
            }
            AMapAppGlobal.getApplication().getApplicationContext();
            dbc.b().b((String[]) arrayList.toArray(new String[arrayList.size()]));
        }
    }

    public void removeMessages(String... strArr) {
        if (strArr != null && strArr.length > 0) {
            AMapAppGlobal.getApplication().getApplicationContext();
            dbc.b().b(strArr);
        }
    }

    public void setMessageShown(String... strArr) {
        if (strArr != null && strArr.length > 0) {
            AMapAppGlobal.getApplication().getApplicationContext();
            dbc.b().a("hasShown", true, strArr);
        }
    }

    public void setBoxMsgDisplay(String str) {
        if (!TextUtils.isEmpty(str)) {
            AMapAppGlobal.getApplication().getApplicationContext();
            dbc.b().a("boxDisplay", true, str);
        }
    }

    public void setBarMsgDisplay(String str) {
        if (!TextUtils.isEmpty(str)) {
            AMapAppGlobal.getApplication().getApplicationContext();
            dbc.b().a("mesDisplay", true, str);
        }
    }

    public void setNewComingConfirmed(ArrayList<AmapMessage> arrayList) {
        if (arrayList != null) {
            ArrayList arrayList2 = new ArrayList();
            Iterator<AmapMessage> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(it.next().id);
            }
            if (arrayList2.size() > 0) {
                AMapAppGlobal.getApplication().getApplicationContext();
                dbc.b().a("isNewComing", false, (String[]) arrayList2.toArray(new String[arrayList2.size()]));
            }
        }
    }

    public void setRead(AmapMessage... amapMessageArr) {
        if (amapMessageArr != null) {
            ArrayList arrayList = new ArrayList();
            for (AmapMessage amapMessage : amapMessageArr) {
                arrayList.add(amapMessage.id);
            }
            setRead((String[]) arrayList.toArray(new String[arrayList.size()]));
        }
    }

    public void setMessageHasReadByMsgInfo(String str) {
        if (!TextUtils.isEmpty(str)) {
            AMapAppGlobal.getApplication().getApplicationContext();
            dbc.a.execute(new Runnable(str) {
                final /* synthetic */ String a;

                {
                    this.a = r2;
                }

                public final void run() {
                    dbc.a(dbc.this, this.a);
                }
            });
        }
    }

    public void setRead(String... strArr) {
        AMapAppGlobal.getApplication().getApplicationContext();
        dbc.b().a(strArr);
    }

    public synchronized void setReadSync(String... strArr) {
        AMapAppGlobal.getApplication().getApplicationContext();
        dbc.b().a(strArr);
    }

    public void setSubRead(String... strArr) {
        AMapAppGlobal.getApplication().getApplicationContext();
        dbc.b().a("sub_unread", false, strArr);
    }

    public void setShowOnMap(AmapMessage amapMessage) {
        AMapAppGlobal.getApplication().getApplicationContext();
        dbc.b().a("showOnMap", false, amapMessage.id);
    }

    public synchronized void setMsgsShowOnMapSync(String... strArr) {
        AMapAppGlobal.getApplication().getApplicationContext();
        dbc.b().a("showOnMap", false, strArr);
    }

    public void executeAction(final AmapMessage amapMessage) {
        long currentTimeMillis = System.currentTimeMillis();
        this.c.lock();
        if (amapMessage != null) {
            try {
                if (currentTimeMillis - this.f < 500) {
                    this.c.unlock();
                    return;
                }
                final boolean z = false;
                if (amapMessage.id.equalsIgnoreCase(AmapMessage.TOKEN_UPDATE_APP)) {
                    if (!TextUtils.isEmpty(amapMessage.version) && NetworkParam.getDiv().equals(amapMessage.version)) {
                        z = true;
                    }
                    Activity topActivity = AMapAppGlobal.getTopActivity();
                    if (topActivity != null) {
                        Builder builder = new Builder(topActivity);
                        builder.setTitle(z ? R.string.msgbox_is_new_version : R.string.msgbox_update_new_version);
                        builder.setPositiveButton(R.string.alert_button_confirm, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                            public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                                if (!z) {
                                    JSONObject jSONObject = new JSONObject();
                                    try {
                                        jSONObject.put("remoteId", amapMessage.id);
                                        jSONObject.put("category", amapMessage.id);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(amapMessage.actionUri));
                                    intent.setFlags(268435456);
                                    DoNotUseTool.startScheme(intent);
                                }
                            }
                        });
                        builder.setNegativeButton(R.string.cancle, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                            public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                            }
                        });
                        AMapPageUtil.startAlertDialogPage(builder);
                    }
                } else if (amapMessage.id.equalsIgnoreCase(AmapMessage.TOKEN_DOWNLOAD_SEAR_MAP)) {
                    Intent intent = new Intent();
                    intent.putExtra("showMapDownload", true);
                    IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
                    if (iOfflineManager != null) {
                        iOfflineManager.deal(AMapPageUtil.getPageContext(), intent);
                    }
                } else if (amapMessage.id.equalsIgnoreCase(AmapMessage.TOKEN_TAOBAO_LOGIN)) {
                    IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                    if (iAccountService != null) {
                        z = iAccountService.a();
                    }
                    if (z) {
                        amapMessage.actionUri = "androidamap://openFeature?featureName=GoldCoin&sourceApplication=amap";
                    } else {
                        amapMessage.actionUri = "androidamap://openFeature?featureName=User&sourceApplication=amap";
                    }
                    Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(amapMessage.actionUri));
                    intent2.putExtra("owner", "banner");
                    DoNotUseTool.startScheme(intent2);
                } else if (!TextUtils.isEmpty(amapMessage.actionUri)) {
                    Intent intent3 = new Intent("android.intent.action.VIEW", Uri.parse(amapMessage.actionUri));
                    intent3.putExtra("owner", "banner");
                    DoNotUseTool.startScheme(intent3);
                }
            } catch (Throwable th) {
                this.c.unlock();
                throw th;
            }
        }
        this.c.unlock();
    }

    public void executeBtnAction(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        this.c.lock();
        try {
            if (currentTimeMillis - this.f >= 500) {
                if (!TextUtils.isEmpty(str)) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                    intent.putExtra("owner", "banner");
                    DoNotUseTool.startScheme(intent);
                }
                this.c.unlock();
            }
        } finally {
            this.c.unlock();
        }
    }

    public synchronized void retrieveLocalMessages(Set<das> set, boolean z) {
        if (this.d == null || !this.d.a(set, z)) {
            this.d = new e(set, z);
            this.d.start();
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:16|17|18|19|20|21) */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00a2, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x0095 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void retrieveRemoteMessages(java.util.Set<defpackage.das> r9, java.lang.String r10) {
        /*
            r8 = this;
            monitor-enter(r8)
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ all -> 0x00a3 }
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ all -> 0x00a3 }
            java.lang.String r2 = "MessageBox"
            r1.<init>(r2)     // Catch:{ all -> 0x00a3 }
            java.lang.String r2 = "cursor"
            java.lang.String r3 = ""
            java.lang.String r2 = r1.getStringValue(r2, r3)     // Catch:{ all -> 0x00a3 }
            java.lang.String r3 = "msg_box_category_version"
            java.lang.String r4 = ""
            java.lang.String r1 = r1.getStringValue(r3, r4)     // Catch:{ all -> 0x00a3 }
            com.autonavi.sdk.location.LocationInstrument r3 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ all -> 0x00a3 }
            r4 = 5
            com.autonavi.common.model.GeoPoint r3 = r3.getLatestPosition(r4)     // Catch:{ all -> 0x00a3 }
            java.lang.String r4 = ""
            java.lang.String r5 = ""
            java.lang.String r6 = ""
            if (r3 == 0) goto L_0x0045
            int r4 = r3.getAdCode()     // Catch:{ all -> 0x00a3 }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x00a3 }
            double r5 = r3.getLatitude()     // Catch:{ all -> 0x00a3 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x00a3 }
            double r6 = r3.getLongitude()     // Catch:{ all -> 0x00a3 }
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x00a3 }
        L_0x0045:
            com.autonavi.minimap.MsgBox.param.PullRequest r3 = new com.autonavi.minimap.MsgBox.param.PullRequest     // Catch:{ all -> 0x00a3 }
            r3.<init>()     // Catch:{ all -> 0x00a3 }
            java.lang.String r0 = com.amap.bundle.network.request.param.NetworkParam.getDeviceToken(r0)     // Catch:{ all -> 0x00a3 }
            r3.g = r0     // Catch:{ all -> 0x00a3 }
            java.lang.String r0 = "100"
            r3.b = r0     // Catch:{ all -> 0x00a3 }
            r3.c = r2     // Catch:{ all -> 0x00a3 }
            r3.d = r4     // Catch:{ all -> 0x00a3 }
            r3.h = r1     // Catch:{ all -> 0x00a3 }
            r3.i = r5     // Catch:{ all -> 0x00a3 }
            r3.j = r6     // Catch:{ all -> 0x00a3 }
            boolean r0 = android.text.TextUtils.isEmpty(r10)     // Catch:{ all -> 0x00a3 }
            if (r0 != 0) goto L_0x0066
            r3.k = r10     // Catch:{ all -> 0x00a3 }
        L_0x0066:
            com.autonavi.minimap.bundle.msgbox.network.MessageBoxCallback r0 = new com.autonavi.minimap.bundle.msgbox.network.MessageBoxCallback     // Catch:{ all -> 0x00a3 }
            r0.<init>(r9)     // Catch:{ all -> 0x00a3 }
            r8.a()     // Catch:{ all -> 0x00a3 }
            java.lang.ref.WeakReference r9 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x00a3 }
            r9.<init>(r0)     // Catch:{ all -> 0x00a3 }
            r8.e = r9     // Catch:{ all -> 0x00a3 }
            com.autonavi.minimap.MsgBox.MsgBoxRequestHolder r9 = com.autonavi.minimap.MsgBox.MsgBoxRequestHolder.getInstance()     // Catch:{ all -> 0x00a3 }
            r9.sendPull(r3, r0)     // Catch:{ all -> 0x00a3 }
            boolean r9 = com.autonavi.minimap.ajx3.AjxInit.sIsAjxEngineInited     // Catch:{ all -> 0x00a3 }
            if (r9 != 0) goto L_0x0082
            monitor-exit(r8)
            return
        L_0x0082:
            java.lang.String r9 = "3"
            boolean r9 = r9.equals(r10)     // Catch:{ all -> 0x00a3 }
            if (r9 != 0) goto L_0x00a1
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ all -> 0x00a3 }
            r9.<init>()     // Catch:{ all -> 0x00a3 }
            java.lang.String r0 = "scene"
            r9.put(r0, r10)     // Catch:{ JSONException -> 0x0095 }
        L_0x0095:
            com.autonavi.minimap.ajx3.Ajx r10 = com.autonavi.minimap.ajx3.Ajx.getInstance()     // Catch:{ all -> 0x00a3 }
            java.lang.String r0 = "amap_bundle_messagebox_MessageBoxRedDotIconUpdateService"
            java.lang.String r1 = "path://amap_bundle_messagebox/src/service/MessageBoxRedDotIconUpdateService.js"
            r2 = 0
            r10.startService(r0, r1, r9, r2)     // Catch:{ all -> 0x00a3 }
        L_0x00a1:
            monitor-exit(r8)
            return
        L_0x00a3:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.retrieveRemoteMessages(java.util.Set, java.lang.String):void");
    }

    public synchronized void reportDisplayLog(String str, int i, int i2) {
        DisplayLogParam displayLogParam = new DisplayLogParam();
        displayLogParam.msg_id = str;
        displayLogParam.tag = i;
        displayLogParam.operateType = i2;
        AosGetRequest a2 = aax.a(displayLogParam);
        DisplayLogCallback displayLogCallback = new DisplayLogCallback(str, i, true);
        yq.a();
        yq.a((AosRequest) a2, (AosResponseCallback<T>) displayLogCallback);
    }

    public synchronized void reportDisplayLogIgnoreError(String str, int i, int i2) {
        DisplayLogParam displayLogParam = new DisplayLogParam();
        displayLogParam.msg_id = str;
        displayLogParam.tag = i;
        displayLogParam.operateType = i2;
        AosGetRequest a2 = aax.a(displayLogParam);
        DisplayLogCallback displayLogCallback = new DisplayLogCallback(str, i, false);
        yq.a();
        yq.a((AosRequest) a2, (AosResponseCallback<T>) displayLogCallback);
    }

    public void handlePush(String str) {
        if (!TextUtils.isEmpty(str)) {
            int indexOf = str.indexOf("AmapPush-");
            if (indexOf >= 0) {
                final String substring = str.substring(indexOf + 9);
                if (!TextUtils.isEmpty(substring)) {
                    MapSharePreference mapSharePreference = new MapSharePreference((String) SP_NAME_PUSH_MSG);
                    mapSharePreference.edit().putLong(substring, System.currentTimeMillis());
                    mapSharePreference.edit().apply();
                    ahm.a(new Runnable() {
                        public final void run() {
                            AMapAppGlobal.getApplication().getApplicationContext();
                            List<Msgbox> c = dbc.b().c();
                            if (c != null && c.size() != 0) {
                                for (Msgbox convertToAmapMessage : c) {
                                    AmapMessage convertToAmapMessage2 = AmapMessage.convertToAmapMessage(convertToAmapMessage);
                                    if (!TextUtils.isEmpty(convertToAmapMessage2.pushMsgId) && !TextUtils.isEmpty(substring) && substring.equals(convertToAmapMessage2.pushMsgId) && AmapMessage.TYPE_MSG.equals(convertToAmapMessage2.type)) {
                                        MessageBoxManager.this.setReadSync(convertToAmapMessage2.id);
                                        return;
                                    }
                                }
                                if (!TextUtils.isEmpty(substring)) {
                                    MessageBoxManager.this.setReadSync(substring);
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    public ArrayList<AmapMessage> getAllLocalMessages() {
        AMapAppGlobal.getApplication().getApplicationContext();
        List<Msgbox> c2 = dbc.b().c();
        ArrayList<AmapMessage> arrayList = new ArrayList<>();
        for (Msgbox convertToAmapMessage : c2) {
            arrayList.add(AmapMessage.convertToAmapMessage(convertToAmapMessage));
        }
        return arrayList;
    }

    public boolean shrinkMessages(List<AmapMessage> list) {
        MapSharePreference mapSharePreference;
        boolean z;
        long j;
        boolean z2 = false;
        if (list == null || list.isEmpty()) {
            return false;
        }
        if (a.size() <= 0) {
            a.add(AmapMessage.TOKEN_UPDATE_APP);
            a.add(AmapMessage.TOKEN_DOWNLOAD_SEAR_MAP);
            a.add(AmapMessage.TOKEN_TAOBAO_LOGIN);
            a.add(AmapMessage.TOKEN_CLOUD_SYNC_DIALOG);
        }
        MapSharePreference mapSharePreference2 = null;
        if (b < 0) {
            mapSharePreference2 = new MapSharePreference((String) SP_NAME_MessageBox);
            b = mapSharePreference2.getLongValue(SP_KEY_MSG_BOX_OLDEST_MESSAGE_TIME, -1);
        }
        if (b < 0) {
            Collections.sort(list, new dbd());
            int i = 0;
            while (true) {
                if (i >= list.size()) {
                    break;
                }
                AmapMessage amapMessage = list.get(i);
                if (!a.contains(amapMessage.id)) {
                    b = amapMessage.createdTime;
                    break;
                }
                i++;
            }
            if (b < 0) {
                b = 0;
            }
            if (mapSharePreference2 == null) {
                mapSharePreference2 = new MapSharePreference((String) SP_NAME_MessageBox);
            }
            mapSharePreference2.putLongValue(SP_KEY_MSG_BOX_OLDEST_MESSAGE_TIME, b);
            mapSharePreference = mapSharePreference2;
            z = true;
        } else {
            mapSharePreference = mapSharePreference2;
            z = false;
        }
        long currentTimeMillis = System.currentTimeMillis() - 2592000000L;
        MapSharePreference mapSharePreference3 = new MapSharePreference((String) SP_NAME_PUSH_MSG);
        Map<String, ?> all = mapSharePreference3.sharedPrefs().getAll();
        if (all != null && all.size() > 0) {
            for (String next : all.keySet()) {
                try {
                    j = mapSharePreference3.getLongValue(next, -1);
                } catch (Exception unused) {
                    j = -1;
                }
                if (j < currentTimeMillis) {
                    mapSharePreference3.remove(next);
                }
            }
        }
        if (!z) {
            Collections.sort(list, new dbd());
        }
        ArrayList arrayList = new ArrayList();
        int size = list.size() - 200;
        int i2 = 0;
        while (true) {
            if (i2 >= list.size()) {
                break;
            }
            AmapMessage amapMessage2 = list.get(i2);
            if (!a.contains(amapMessage2.id)) {
                if (size <= 0) {
                    if (amapMessage2.createdTime >= currentTimeMillis) {
                        b = amapMessage2.createdTime;
                        z2 = true;
                        break;
                    }
                    list.remove(amapMessage2);
                    arrayList.add(amapMessage2.id);
                    i2--;
                } else {
                    list.remove(amapMessage2);
                    arrayList.add(amapMessage2.id);
                    i2--;
                    size--;
                }
            }
            i2++;
        }
        if (arrayList.size() > 0) {
            AMapAppGlobal.getApplication().getApplicationContext();
            dbc.b().b((String[]) arrayList.toArray(new String[arrayList.size()]));
        }
        if (!z2 || b > System.currentTimeMillis()) {
            b = System.currentTimeMillis();
        }
        if (mapSharePreference == null) {
            mapSharePreference = new MapSharePreference((String) SP_NAME_MessageBox);
        }
        mapSharePreference.putLongValue(SP_KEY_MSG_BOX_OLDEST_MESSAGE_TIME, b);
        return true;
    }

    public void jumpToMainPage() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", "path://amap_bundle_messagebox/src/MessageBoxPage.page.js");
            pageBundle.putLong("startTime", System.currentTimeMillis());
            pageContext.startPage(MsgboxPage.class, pageBundle);
        }
    }

    public void destroy() {
        if (this.d != null) {
            this.d.a.clear();
            this.d = null;
        }
        a();
    }

    private void a() {
        if (this.e != null) {
            MessageBoxCallback messageBoxCallback = (MessageBoxCallback) this.e.get();
            if (messageBoxCallback != null) {
                messageBoxCallback.a.clear();
            }
        }
    }
}
