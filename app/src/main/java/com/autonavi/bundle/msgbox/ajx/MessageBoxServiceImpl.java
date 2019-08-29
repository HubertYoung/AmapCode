package com.autonavi.bundle.msgbox.ajx;

import android.os.Handler;
import android.os.Looper;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.a;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.b;
import java.util.ArrayList;
import java.util.List;

public class MessageBoxServiceImpl implements IMessageBoxService {
    private Handler a = new Handler(Looper.getMainLooper());
    private a b = new a() {
        public final boolean a(AmapMessage amapMessage) {
            return AmapMessage.TYPE_MSG.equals(amapMessage.type);
        }
    };

    public final void a(String... strArr) {
        MessageBoxManager.getInstance().setRead(strArr);
    }

    public final void b(String... strArr) {
        MessageBoxManager.getInstance().setMessageShown(strArr);
    }

    public final void a(String str) {
        MessageBoxManager.getInstance().setBoxMsgDisplay(str);
    }

    public final void a(final awm awm, String str) {
        MessageBoxManager.getInstance().getMessages(new b() {
            final /* synthetic */ boolean b = false;

            public final void a(List<AmapMessage> list, final List<btb> list2, boolean z) {
                if (awm != null) {
                    final ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    for (int i = 0; i < list.size(); i++) {
                        AmapMessage amapMessage = list.get(i);
                        if (amapMessage.isNewComing) {
                            arrayList2.add(amapMessage);
                        }
                        arrayList.add(amapMessage);
                    }
                    MessageBoxServiceImpl.this.a.post(new Runnable() {
                        public final void run() {
                            awm awm = awm;
                            ArrayList arrayList = arrayList;
                            List list = list2;
                            if (!AnonymousClass2.this.b) {
                                NetworkReachability.b();
                            }
                            awm.a(arrayList, list);
                        }
                    });
                    MessageBoxManager.getInstance().setNewComingConfirmed(arrayList2);
                }
            }
        }, false, true, this.b, str);
    }
}
