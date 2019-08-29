package com.coloros.mcssdk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.util.List;

public class PushService extends Service implements esz {
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        List<ete> a = eso.a(getApplicationContext(), intent);
        List<est> list = esl.a().b;
        if (a == null || a.size() == 0 || list == null || list.size() == 0) {
            return super.onStartCommand(intent, i, i2);
        }
        for (ete next : a) {
            if (next != null) {
                for (est next2 : list) {
                    if (next2 != null) {
                        try {
                            getApplicationContext();
                            next2.a(next, this);
                        } catch (Exception e) {
                            StringBuilder sb = new StringBuilder("process Exception:");
                            sb.append(e.getMessage());
                            esx.b(sb.toString());
                        }
                    }
                }
            }
        }
        return super.onStartCommand(intent, i, i2);
    }

    public final void a(etd etd) {
        if (esl.a().g != null) {
            switch (etd.d) {
                case 12289:
                    if (etd.f == 0) {
                        esl.a().f = etd.e;
                    }
                    esl.a().g.a(etd.f, etd.e);
                    return;
                case 12290:
                    esl.a();
                    return;
                case 12292:
                    esl.a();
                    etd.a(etd.e, "alias", "aliasId", "aliasName");
                    return;
                case 12293:
                    esl.a();
                    etd.a(etd.e, "alias", "aliasId", "aliasName");
                    return;
                case 12294:
                    esl.a();
                    etd.a(etd.e, "alias", "aliasId", "aliasName");
                    return;
                case 12295:
                    esl.a();
                    etd.a(etd.e, "tags", "tagId", "tagName");
                    return;
                case 12296:
                    esl.a();
                    etd.a(etd.e, "tags", "tagId", "tagName");
                    return;
                case 12297:
                    esl.a();
                    etd.a(etd.e, "tags", "tagId", "tagName");
                    return;
                case 12298:
                    esl.a();
                    return;
                case 12301:
                    esl.a();
                    etd.a(etd.e, "tags", "accountId", "accountName");
                    return;
                case 12302:
                    esl.a();
                    etd.a(etd.e, "tags", "accountId", "accountName");
                    return;
                case 12303:
                    esl.a();
                    etd.a(etd.e, "tags", "accountId", "accountName");
                    return;
                case 12306:
                    esl.a();
                    esy.a(etd.e);
                    return;
                case 12309:
                    esl.a();
                    esy.a(etd.e);
                    break;
            }
        }
    }
}
