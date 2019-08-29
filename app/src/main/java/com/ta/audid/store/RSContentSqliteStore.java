package com.ta.audid.store;

import com.ta.audid.Variables;

public class RSContentSqliteStore {
    private static RSContentSqliteStore mInstance;

    private RSContentSqliteStore() {
    }

    public static synchronized RSContentSqliteStore getInstance() {
        RSContentSqliteStore rSContentSqliteStore;
        synchronized (RSContentSqliteStore.class) {
            if (mInstance == null) {
                mInstance = new RSContentSqliteStore();
            }
            rSContentSqliteStore = mInstance;
        }
        return rSContentSqliteStore;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0046, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void insertStringList(java.util.List<java.lang.String> r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 == 0) goto L_0x0045
            int r0 = r4.size()     // Catch:{ all -> 0x0042 }
            if (r0 > 0) goto L_0x000a
            goto L_0x0045
        L_0x000a:
            int r0 = r3.count()     // Catch:{ all -> 0x0042 }
            r1 = 200(0xc8, float:2.8E-43)
            if (r0 <= r1) goto L_0x0017
            r0 = 100
            r3.clearOldLogByCount(r0)     // Catch:{ all -> 0x0042 }
        L_0x0017:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0042 }
            r0.<init>()     // Catch:{ all -> 0x0042 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ all -> 0x0042 }
        L_0x0020:
            boolean r1 = r4.hasNext()     // Catch:{ all -> 0x0042 }
            if (r1 == 0) goto L_0x0035
            java.lang.Object r1 = r4.next()     // Catch:{ all -> 0x0042 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0042 }
            com.ta.audid.store.RSContent r2 = new com.ta.audid.store.RSContent     // Catch:{ all -> 0x0042 }
            r2.<init>(r1)     // Catch:{ all -> 0x0042 }
            r0.add(r2)     // Catch:{ all -> 0x0042 }
            goto L_0x0020
        L_0x0035:
            com.ta.audid.Variables r4 = com.ta.audid.Variables.getInstance()     // Catch:{ all -> 0x0042 }
            com.ta.audid.db.DBMgr r4 = r4.getDbMgr()     // Catch:{ all -> 0x0042 }
            r4.insert(r0)     // Catch:{ all -> 0x0042 }
            monitor-exit(r3)
            return
        L_0x0042:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        L_0x0045:
            monitor-exit(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.store.RSContentSqliteStore.insertStringList(java.util.List):void");
    }

    private int count() {
        return Variables.getInstance().getDbMgr().count(RSContent.class);
    }

    private int clearOldLogByCount(int i) {
        String tablename = Variables.getInstance().getDbMgr().getTablename(RSContent.class);
        StringBuilder sb = new StringBuilder(" _id in ( select _id from ");
        sb.append(tablename);
        sb.append(" ORDER BY _id ASC LIMIT ");
        sb.append(i);
        sb.append(" )");
        return Variables.getInstance().getDbMgr().delete(RSContent.class, sb.toString(), null);
    }

    public synchronized int find(String str) {
        StringBuilder sb;
        sb = new StringBuilder("content = '");
        sb.append(str);
        sb.append("'");
        return Variables.getInstance().getDbMgr().count(RSContent.class, sb.toString());
    }

    public synchronized void clear() {
        Variables.getInstance().getDbMgr().clear(RSContent.class);
    }
}
