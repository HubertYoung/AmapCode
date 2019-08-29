package com.ta.audid.store;

import com.ta.audid.Variables;
import com.ta.audid.db.Entity;
import com.ta.audid.utils.UtdidLogger;
import java.util.ArrayList;
import java.util.List;

public class UtdidContentSqliteStore {
    public static final int MAX_LOG_COUNT = 4;
    private static UtdidContentSqliteStore mInstance;

    private UtdidContentSqliteStore() {
    }

    public static synchronized UtdidContentSqliteStore getInstance() {
        UtdidContentSqliteStore utdidContentSqliteStore;
        synchronized (UtdidContentSqliteStore.class) {
            try {
                if (mInstance == null) {
                    mInstance = new UtdidContentSqliteStore();
                }
                utdidContentSqliteStore = mInstance;
            }
        }
        return utdidContentSqliteStore;
    }

    public synchronized void insertStringList(List<String> list) {
        UtdidLogger.d();
        if (list != null) {
            if (list.size() > 0) {
                UtdidLogger.d((String) "", "logs", Integer.valueOf(list.size()));
                if (count() > 4) {
                    clearOldLogByCount(2);
                }
                ArrayList arrayList = new ArrayList();
                for (String utdidContent : list) {
                    arrayList.add(new UtdidContent(utdidContent));
                }
                Variables.getInstance().getDbMgr().insert((List<? extends Entity>) arrayList);
                return;
            }
        }
        UtdidLogger.d((String) "", "logs is empty");
    }

    private int count() {
        return Variables.getInstance().getDbMgr().count(UtdidContent.class);
    }

    private int clearOldLogByCount(int i) {
        String tablename = Variables.getInstance().getDbMgr().getTablename(UtdidContent.class);
        StringBuilder sb = new StringBuilder(" _id in ( select _id from ");
        sb.append(tablename);
        sb.append(" ORDER BY _id ASC LIMIT ");
        sb.append(i);
        sb.append(" )");
        return Variables.getInstance().getDbMgr().delete(UtdidContent.class, sb.toString(), null);
    }

    public synchronized int delete(List<UtdidContent> list) {
        return Variables.getInstance().getDbMgr().delete(list);
    }

    public synchronized List<UtdidContent> get(int i) {
        return Variables.getInstance().getDbMgr().find(UtdidContent.class, null, "priority DESC , time DESC ", i);
    }

    public synchronized void clear() {
        Variables.getInstance().getDbMgr().clear(UtdidContent.class);
    }
}
