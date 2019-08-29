package com.alibaba.analytics.core.store;

import android.content.Context;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.model.Log;
import com.alibaba.analytics.utils.Logger;
import java.util.List;

class LogSqliteStore implements ILogStore {
    private static final String TAG = "UTSqliteLogStore";
    String countSql = "SELECT count(*) FROM %s";
    String deleteSql = "DELETE FROM  %s where _id in ( select _id from %s  ORDER BY priority ASC ,  _id ASC LIMIT %d )";
    String querySql = "SELECT * FROM %s ORDER BY %s ASC LIMIT %d";

    LogSqliteStore(Context context) {
    }

    public synchronized boolean insert(List<Log> list) {
        Variables.getInstance().getDbMgr().insert(list);
        return true;
    }

    public synchronized int delete(List<Log> list) {
        return Variables.getInstance().getDbMgr().delete(list);
    }

    public synchronized List<Log> get(int i) {
        return Variables.getInstance().getDbMgr().find(Log.class, null, "priority DESC , time DESC ", i);
    }

    public synchronized int count() {
        return Variables.getInstance().getDbMgr().count(Log.class);
    }

    public synchronized void clear() {
        Variables.getInstance().getDbMgr().clear(Log.class);
    }

    public synchronized int clearOldLogByField(String str, String str2) {
        StringBuilder sb;
        Logger.d();
        sb = new StringBuilder();
        sb.append(str);
        sb.append("< ?");
        return Variables.getInstance().getDbMgr().delete(Log.class, sb.toString(), new String[]{str2});
    }

    public int clearOldLogByCount(int i) {
        Logger.d();
        String tablename = Variables.getInstance().getDbMgr().getTablename(Log.class);
        StringBuilder sb = new StringBuilder(" _id in ( select _id from ");
        sb.append(tablename);
        sb.append("  ORDER BY priority ASC , _id ASC LIMIT ");
        sb.append(i);
        sb.append(" )");
        return Variables.getInstance().getDbMgr().delete(Log.class, sb.toString(), null);
    }

    public double getDbFileSize() {
        return Variables.getInstance().getDbMgr().getDbFileSize();
    }

    public synchronized void update(List<Log> list) {
        Variables.getInstance().getDbMgr().update(list);
    }

    public synchronized void updateLogPriority(List<Log> list) {
        Variables.getInstance().getDbMgr().updateLogPriority(list);
    }
}
