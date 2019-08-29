package com.autonavi.minimap.bundle.msgbox.impl;

import android.app.Activity;
import android.os.AsyncTask.Status;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService.UIUpdater;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService.a;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager;
import java.util.Iterator;

@BundleInterface(IMsgboxService.class)
public class MsgboxService extends esi implements IMsgboxService {
    public void notifyOfflineMapInformed() {
    }

    public void reset() {
        MessageBoxManager.getInstance().reset();
    }

    public void handlePush(String str) {
        MessageBoxManager.getInstance().handlePush(str);
    }

    public void setRead(String... strArr) {
        MessageBoxManager.getInstance().setRead(strArr);
    }

    public void fetchMessage(int i, boolean z, UIUpdater uIUpdater) {
        MessageBoxManager.getInstance().fetchMessage(i, z, uIUpdater);
    }

    public void setSubRead(String... strArr) {
        MessageBoxManager.getInstance().setSubRead(strArr);
    }

    public int getNewComingUnRead_MsgNumFromDB() {
        Iterator<AmapMessage> it = MessageBoxManager.getInstance().getAllLocalMessages().iterator();
        int i = 0;
        while (it.hasNext()) {
            AmapMessage next = it.next();
            if (AmapMessage.TYPE_MSG.equals(next.type) && next.isUnRead && next.isNewComing) {
                i++;
            }
        }
        return i;
    }

    public void getMessageSize(a aVar, int i) {
        dax a = dax.a();
        if (a.a == null || a.a.getStatus() == Status.FINISHED) {
            a.a = new a(i);
            a.a.a(aVar);
            a.a.execute(new Integer[]{Integer.valueOf(0)});
            return;
        }
        a.a.a(aVar);
    }

    public dap getMsgboxStorageService() {
        AMapAppGlobal.getApplication();
        return dbc.b();
    }

    public void executeAction(AmapMessage amapMessage) {
        MessageBoxManager.getInstance().executeAction(amapMessage);
    }

    public void getMessageInBackground(Activity activity, boolean z) {
        MessageBoxManager.getInstance().getMessageInBackground(activity, z);
    }

    public void jumpToMainPage() {
        MessageBoxManager.getInstance().jumpToMainPage();
    }
}
