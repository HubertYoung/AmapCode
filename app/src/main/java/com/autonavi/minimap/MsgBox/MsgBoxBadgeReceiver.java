package com.autonavi.minimap.msgbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager;
import java.util.HashSet;
import java.util.List;

public class MsgBoxBadgeReceiver extends BroadcastReceiver {
    /* access modifiers changed from: private */
    public WakeLock a;

    public static class a extends com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.a {
        public final boolean a(AmapMessage amapMessage) {
            return AmapMessage.TYPE_MSG.equals(amapMessage.type) && amapMessage.isUnRead && amapMessage.isNewComing;
        }
    }

    public class b implements com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.b {
        private Context b;

        public b(Context context) {
            this.b = context;
        }

        public final void a(List<AmapMessage> list, List<btb> list2, boolean z) {
            if (MsgBoxBadgeReceiver.this.a != null && MsgBoxBadgeReceiver.this.a.isHeld()) {
                MsgBoxBadgeReceiver.this.a.release();
            }
            int i = 0;
            IMsgboxService iMsgboxService = (IMsgboxService) defpackage.esb.a.a.a(IMsgboxService.class);
            if (iMsgboxService != null) {
                i = iMsgboxService.getMsgboxStorageService().a();
            }
            int a2 = MsgBoxBadgeReceiver.a(list, list2) + i;
            fhb fhb = (fhb) defpackage.esb.a.a.a(fhb.class);
            if (fhb != null) {
                fhd d = fhb.d();
                if (a2 > 3) {
                    a2 = 3;
                }
                d.a(a2);
            }
        }
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            AMapLog.i("BroadcastCompat", "MsgBoxBadgeReceiver --- action = ".concat(String.valueOf(action)));
            if ("com.autonavi.minimap.action.Badge".equals(action)) {
                this.a = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "bundle.msgbox");
                this.a.acquire(15000);
                MessageBoxManager.getInstance().getMessages(new b(context), false, new a());
                int intExtra = intent.getIntExtra("extra_key_index", -1) + 1;
                if (intExtra > 0 && intExtra < dba.a.length) {
                    dba.a(context).a(intExtra);
                }
            }
        }
    }

    static /* synthetic */ int a(List<AmapMessage> list, List<btb> list2) {
        int i = 0;
        if (list == null || list.isEmpty()) {
            return 0;
        }
        HashSet hashSet = new HashSet();
        if (list2 != null) {
            for (btb btb : list2) {
                if ("1".equals(btb.f) && !TextUtils.isEmpty(btb.a)) {
                    hashSet.add(btb.a);
                }
            }
        }
        for (AmapMessage amapMessage : list) {
            if (AmapMessage.TYPE_MSG.equals(amapMessage.type) && amapMessage.isNewComing && amapMessage.msgType != 1) {
                if (hashSet.contains(amapMessage.category) || !TextUtils.isEmpty(amapMessage.label)) {
                    i++;
                }
            }
        }
        return i;
    }
}
