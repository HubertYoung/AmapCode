package com.alibaba.analytics.core.db;

import android.content.Context;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.model.Log;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.TaskExecutor;
import java.io.File;
import java.util.List;

public class OldDBTransferMgr {
    protected static final String TAG = "OldDBTransferMgr";
    /* access modifiers changed from: private */
    public static String usertrackDbName = "usertrack.db";

    public static void checkAndTransfer() {
        final Context context = Variables.getInstance().getContext();
        if (context != null) {
            final File databasePath = context.getDatabasePath(usertrackDbName);
            if (databasePath.exists()) {
                TaskExecutor.getInstance().submit(new Runnable() {
                    public final void run() {
                        DBMgr dBMgr = new DBMgr(context, OldDBTransferMgr.usertrackDbName);
                        while (true) {
                            List<? extends Entity> find = dBMgr.find(Log.class, null, "time", 100);
                            if (find.size() == 0) {
                                Logger.d((String) OldDBTransferMgr.TAG, "delete old db file:", databasePath.getAbsoluteFile());
                                databasePath.delete();
                                return;
                            }
                            dBMgr.delete(find);
                            Variables.getInstance().getDbMgr().insert(find);
                        }
                    }
                });
            }
        }
    }
}
