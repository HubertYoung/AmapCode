package com.alipay.mobile.common.nbnet.biz.netlib;

import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NBNetConnTaskRegister {
    private static NBNetConnTaskRegister b;
    private Map<String, List<NBNetConnectionTask>> a = new ConcurrentHashMap();

    public static final NBNetConnTaskRegister a() {
        if (b != null) {
            return b;
        }
        synchronized (NBNetConnTaskRegister.class) {
            try {
                if (b != null) {
                    NBNetConnTaskRegister nBNetConnTaskRegister = b;
                    return nBNetConnTaskRegister;
                }
                b = new NBNetConnTaskRegister();
                return b;
            }
        }
    }

    private NBNetConnTaskRegister() {
    }

    public final void a(NBNetConnectionTask nbNetConnectionTask) {
        String uriHost = nbNetConnectionTask.a().a();
        if (TextUtils.isEmpty(uriHost)) {
            NBNetLogCat.a((String) "NBNetConnTaskRegister", (String) "regiesterTask uriHost is empty.");
            return;
        }
        synchronized (this) {
            List nbNetConnectionTasks = this.a.get(uriHost);
            if (nbNetConnectionTasks == null) {
                nbNetConnectionTasks = new ArrayList(5);
                this.a.put(uriHost, nbNetConnectionTasks);
            }
            nbNetConnectionTasks.add(nbNetConnectionTask);
            NBNetLogCat.a((String) "NBNetConnTaskRegister", "regiesterTask finish. nbNetConnectionTask hashcode: " + nbNetConnectionTask.hashCode() + ", size: " + nbNetConnectionTasks.size());
        }
    }

    public final NBNetConnectionTask a(String host) {
        List nbNetConnectionTasks = this.a.get(host);
        if (nbNetConnectionTasks == null) {
            NBNetLogCat.a((String) "NBNetConnTaskRegister", (String) "getTask. nbNetConnectionTasks is null.");
            return null;
        }
        synchronized (host) {
            if (nbNetConnectionTasks.isEmpty()) {
                NBNetLogCat.a((String) "NBNetConnTaskRegister", (String) "getTask. nbNetConnectionTasks is empty.");
                return null;
            }
            try {
                NBNetConnectionTask nBNetConnectionTask = (NBNetConnectionTask) nbNetConnectionTasks.get(0);
                return nBNetConnectionTask;
            } catch (Throwable e) {
                NBNetLogCat.a("NBNetConnTaskRegister", "getTask exception", e);
                return null;
            }
        }
    }

    public final void b(NBNetConnectionTask nbNetConnectionTask) {
        NBNetRoute nbNetRoute = nbNetConnectionTask.a();
        if (nbNetRoute == null) {
            NBNetLogCat.d("NBNetConnTaskRegister", "removeTask. NBNetRoute inside NBNetConnectionTask is null.");
        } else if (TextUtils.isEmpty(nbNetRoute.a())) {
            NBNetLogCat.d("NBNetConnTaskRegister", "removeTask. UriHost inside NBNetRoute is null.");
        } else {
            List nbNetConnectionTasks = this.a.get(nbNetRoute.a());
            if (nbNetConnectionTasks == null) {
                NBNetLogCat.d("NBNetConnTaskRegister", "removeTask. UriHost is not present correpond to NBNetConnectionTask list.");
                return;
            }
            synchronized (this) {
                if (nbNetConnectionTasks.isEmpty()) {
                    NBNetLogCat.a((String) "NBNetConnTaskRegister", (String) "removeTask. nbNetConnectionTasks is empty.");
                    return;
                }
                try {
                    nbNetConnectionTasks.remove(nbNetConnectionTask);
                    NBNetLogCat.a((String) "NBNetConnTaskRegister", "removeTask finish. nbNetConnectionTask hashcode: " + nbNetConnectionTask.hashCode());
                } catch (Throwable e) {
                    NBNetLogCat.a("NBNetConnTaskRegister", "removeTask exception", e);
                }
            }
        }
    }
}
