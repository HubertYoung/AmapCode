package com.uc.webview.export.internal.setup;

import android.util.Pair;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.interfaces.IWaStat;

/* compiled from: ProGuard */
public final class bd extends bw {
    public final void run() {
        callbackStat(new Pair(IWaStat.SHARE_CORE_LOCATION_SETUP_TASK_RUN, null));
        ((t) ((t) ((t) ((t) ((t) setup((String) UCCore.OPTION_DEX_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_SO_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_RES_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_UCM_LIB_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_KRL_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_CFG_FILE, (Object) null);
        super.run();
    }

    public static boolean a(Object obj) {
        return obj instanceof bd;
    }
}
