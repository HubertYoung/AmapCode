package defpackage;

import android.app.Application;
import android.content.Context;
import com.autonavi.minimap.app.init.Process;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* renamed from: ckx reason: default package */
/* compiled from: InitScheduler */
public final class ckx {
    private Map<Process, List<cky>> a = new HashMap();
    private Map<Process, List<cky>> b = new HashMap();

    public final ckx a(cky cky, Process... processArr) {
        a(this.a, cky, processArr);
        return this;
    }

    public final ckx b(cky cky, Process... processArr) {
        a(this.b, cky, processArr);
        return this;
    }

    private static void a(Map<Process, List<cky>> map, cky cky, Process... processArr) {
        if (processArr == null || processArr.length == 0) {
            processArr = new Process[]{Process.MAIN};
        }
        for (Process process : processArr) {
            List list = map.get(process);
            if (list == null) {
                list = new LinkedList();
                map.put(process, list);
            }
            list.add(cky);
        }
    }

    public final void a(Application application) {
        Process a2 = a((Context) application);
        List<cky> list = this.a.get(a2);
        StringBuilder sb = new StringBuilder("start in ");
        sb.append(a2);
        sb.append(", mInOrderMap =  ");
        sb.append(list);
        if (list != null) {
            for (cky b2 : list) {
                b2.b(application);
            }
        }
        List<cky> list2 = this.b.get(a2);
        StringBuilder sb2 = new StringBuilder("start in ");
        sb2.append(a2);
        sb2.append(", mNoOrderMap =  ");
        sb2.append(list2);
        if (list2 != null) {
            for (cky b3 : list2) {
                b3.b(application);
            }
        }
    }

    private static Process a(Context context) {
        Process[] values;
        for (Process process : Process.values()) {
            if (process == Process.MAIN && ahs.a(context)) {
                return Process.MAIN;
            }
            if (ahs.a(process.name, context)) {
                return process;
            }
        }
        return Process.OTHER;
    }
}
