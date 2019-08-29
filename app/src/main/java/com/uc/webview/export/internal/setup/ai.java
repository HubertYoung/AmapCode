package com.uc.webview.export.internal.setup;

import android.util.Pair;
import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.utility.SetupTask;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/* compiled from: ProGuard */
final class ai implements ValueCallback<SetupTask> {
    LinkedList<Pair<String, HashMap<String, String>>> a = new LinkedList<>();
    final /* synthetic */ ValueCallback b;
    final /* synthetic */ af c;

    ai(af afVar, ValueCallback valueCallback) {
        this.c = afVar;
        this.b = valueCallback;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        SetupTask setupTask = (SetupTask) obj;
        if (setupTask.getStat().second == null) {
            WaStat.stat((String) setupTask.getStat().first);
            return;
        }
        this.a.add(setupTask.getStat());
        boolean z = false;
        boolean z2 = SetupTask.getTotalLoadedUCM() != null;
        boolean z3 = z2 && SetupTask.getTotalLoadedUCM().coreType == 2;
        if (z2 && SetupTask.getTotalLoadedUCM().coreType != 2) {
            z = true;
        }
        if (((String) setupTask.getStat().first).equals(IWaStat.SETUP_TOTAL_EXCEPTION) || z3 || Log.sPrintLog) {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                if (this.b == null) {
                    WaStat.statAKV(pair);
                } else {
                    setupTask.mStat = pair;
                    this.b.onReceiveValue(setupTask);
                }
            }
            this.a.clear();
            return;
        }
        if (z) {
            this.a.clear();
        }
    }
}
