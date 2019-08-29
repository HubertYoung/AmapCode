package com.xiaomi.stats;

final class d {

    static class a {
        com.xiaomi.push.thrift.a a;
        String b;

        a() {
        }
    }

    /* JADX WARNING: type inference failed for: r4v1, types: [java.lang.Throwable, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r4v7, types: [java.lang.Throwable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.xiaomi.stats.d.a a(java.lang.Exception r4) {
        /*
            e(r4)
            boolean r0 = r4 instanceof com.xiaomi.smack.l
            if (r0 == 0) goto L_0x0014
            r0 = r4
            com.xiaomi.smack.l r0 = (com.xiaomi.smack.l) r0
            java.lang.Throwable r1 = r0.a()
            if (r1 == 0) goto L_0x0014
            java.lang.Throwable r4 = r0.a()
        L_0x0014:
            com.xiaomi.stats.d$a r0 = new com.xiaomi.stats.d$a
            r0.<init>()
            java.lang.String r1 = r4.getMessage()
            java.lang.Throwable r2 = r4.getCause()
            if (r2 == 0) goto L_0x002b
            java.lang.Throwable r1 = r4.getCause()
            java.lang.String r1 = r1.getMessage()
        L_0x002b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.Class r3 = r4.getClass()
            java.lang.String r3 = r3.getSimpleName()
            r2.append(r3)
            java.lang.String r3 = ":"
            r2.append(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            int r4 = com.xiaomi.smack.c.a(r4)
            if (r4 == 0) goto L_0x005a
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.GSLB_REQUEST_SUCCESS
            int r2 = r2.a()
            int r2 = r2 + r4
            com.xiaomi.push.thrift.a r4 = com.xiaomi.push.thrift.a.a(r2)
            r0.a = r4
        L_0x005a:
            com.xiaomi.push.thrift.a r4 = r0.a
            if (r4 != 0) goto L_0x0062
            com.xiaomi.push.thrift.a r4 = com.xiaomi.push.thrift.a.GSLB_TCP_ERR_OTHER
            r0.a = r4
        L_0x0062:
            com.xiaomi.push.thrift.a r4 = r0.a
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.GSLB_TCP_ERR_OTHER
            if (r4 != r2) goto L_0x006a
            r0.b = r1
        L_0x006a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stats.d.a(java.lang.Exception):com.xiaomi.stats.d$a");
    }

    /* JADX WARNING: type inference failed for: r5v1, types: [java.lang.Throwable, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r5v10, types: [java.lang.Throwable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.xiaomi.stats.d.a b(java.lang.Exception r5) {
        /*
            e(r5)
            boolean r0 = r5 instanceof com.xiaomi.smack.l
            if (r0 == 0) goto L_0x0014
            r0 = r5
            com.xiaomi.smack.l r0 = (com.xiaomi.smack.l) r0
            java.lang.Throwable r1 = r0.a()
            if (r1 == 0) goto L_0x0014
            java.lang.Throwable r5 = r0.a()
        L_0x0014:
            com.xiaomi.stats.d$a r0 = new com.xiaomi.stats.d$a
            r0.<init>()
            java.lang.String r1 = r5.getMessage()
            java.lang.Throwable r2 = r5.getCause()
            if (r2 == 0) goto L_0x002b
            java.lang.Throwable r1 = r5.getCause()
            java.lang.String r1 = r1.getMessage()
        L_0x002b:
            int r2 = com.xiaomi.smack.c.a(r5)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r4 = r5.getClass()
            java.lang.String r4 = r4.getSimpleName()
            r3.append(r4)
            java.lang.String r4 = ":"
            r3.append(r4)
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            if (r2 == 0) goto L_0x006d
            com.xiaomi.push.thrift.a r3 = com.xiaomi.push.thrift.a.CONN_SUCCESS
            int r3 = r3.a()
            int r3 = r3 + r2
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.a(r3)
            r0.a = r2
            com.xiaomi.push.thrift.a r2 = r0.a
            com.xiaomi.push.thrift.a r3 = com.xiaomi.push.thrift.a.CONN_BOSH_ERR
            if (r2 != r3) goto L_0x0071
            java.lang.Throwable r5 = r5.getCause()
            if (r5 == 0) goto L_0x0071
            boolean r5 = r5 instanceof java.net.UnknownHostException
            if (r5 == 0) goto L_0x0071
            com.xiaomi.push.thrift.a r5 = com.xiaomi.push.thrift.a.CONN_BOSH_UNKNOWNHOST
            goto L_0x006f
        L_0x006d:
            com.xiaomi.push.thrift.a r5 = com.xiaomi.push.thrift.a.CONN_XMPP_ERR
        L_0x006f:
            r0.a = r5
        L_0x0071:
            com.xiaomi.push.thrift.a r5 = r0.a
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.CONN_TCP_ERR_OTHER
            if (r5 == r2) goto L_0x0083
            com.xiaomi.push.thrift.a r5 = r0.a
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.CONN_XMPP_ERR
            if (r5 == r2) goto L_0x0083
            com.xiaomi.push.thrift.a r5 = r0.a
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.CONN_BOSH_ERR
            if (r5 != r2) goto L_0x0085
        L_0x0083:
            r0.b = r1
        L_0x0085:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stats.d.b(java.lang.Exception):com.xiaomi.stats.d$a");
    }

    /* JADX WARNING: type inference failed for: r4v1, types: [java.lang.Throwable, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r4v6, types: [java.lang.Throwable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.xiaomi.stats.d.a c(java.lang.Exception r4) {
        /*
            e(r4)
            boolean r0 = r4 instanceof com.xiaomi.smack.l
            if (r0 == 0) goto L_0x0014
            r0 = r4
            com.xiaomi.smack.l r0 = (com.xiaomi.smack.l) r0
            java.lang.Throwable r1 = r0.a()
            if (r1 == 0) goto L_0x0014
            java.lang.Throwable r4 = r0.a()
        L_0x0014:
            com.xiaomi.stats.d$a r0 = new com.xiaomi.stats.d$a
            r0.<init>()
            java.lang.String r1 = r4.getMessage()
            java.lang.Throwable r2 = r4.getCause()
            if (r2 == 0) goto L_0x002b
            java.lang.Throwable r1 = r4.getCause()
            java.lang.String r1 = r1.getMessage()
        L_0x002b:
            int r2 = com.xiaomi.smack.c.a(r4)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r4 = r4.getClass()
            java.lang.String r4 = r4.getSimpleName()
            r3.append(r4)
            java.lang.String r4 = ":"
            r3.append(r4)
            r3.append(r1)
            java.lang.String r4 = r3.toString()
            r3 = 105(0x69, float:1.47E-43)
            if (r2 == r3) goto L_0x0077
            r3 = 199(0xc7, float:2.79E-43)
            if (r2 == r3) goto L_0x0074
            r3 = 499(0x1f3, float:6.99E-43)
            if (r2 == r3) goto L_0x0065
            switch(r2) {
                case 109: goto L_0x0062;
                case 110: goto L_0x005f;
                default: goto L_0x005a;
            }
        L_0x005a:
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.BIND_XMPP_ERR
        L_0x005c:
            r0.a = r1
            goto L_0x007a
        L_0x005f:
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.BIND_TCP_BROKEN_PIPE
            goto L_0x005c
        L_0x0062:
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.BIND_TCP_CONNRESET
            goto L_0x005c
        L_0x0065:
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.BIND_BOSH_ERR
            r0.a = r2
            java.lang.String r2 = "Terminal binding condition encountered: item-not-found"
            boolean r1 = r1.startsWith(r2)
            if (r1 == 0) goto L_0x007a
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.BIND_BOSH_ITEM_NOT_FOUND
            goto L_0x005c
        L_0x0074:
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.BIND_TCP_ERR
            goto L_0x005c
        L_0x0077:
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.BIND_TCP_READ_TIMEOUT
            goto L_0x005c
        L_0x007a:
            com.xiaomi.push.thrift.a r1 = r0.a
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.BIND_TCP_ERR
            if (r1 == r2) goto L_0x008c
            com.xiaomi.push.thrift.a r1 = r0.a
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.BIND_XMPP_ERR
            if (r1 == r2) goto L_0x008c
            com.xiaomi.push.thrift.a r1 = r0.a
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.BIND_BOSH_ERR
            if (r1 != r2) goto L_0x008e
        L_0x008c:
            r0.b = r4
        L_0x008e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stats.d.c(java.lang.Exception):com.xiaomi.stats.d$a");
    }

    /* JADX WARNING: type inference failed for: r4v1, types: [java.lang.Throwable, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r4v6, types: [java.lang.Throwable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.xiaomi.stats.d.a d(java.lang.Exception r4) {
        /*
            e(r4)
            boolean r0 = r4 instanceof com.xiaomi.smack.l
            if (r0 == 0) goto L_0x0014
            r0 = r4
            com.xiaomi.smack.l r0 = (com.xiaomi.smack.l) r0
            java.lang.Throwable r1 = r0.a()
            if (r1 == 0) goto L_0x0014
            java.lang.Throwable r4 = r0.a()
        L_0x0014:
            com.xiaomi.stats.d$a r0 = new com.xiaomi.stats.d$a
            r0.<init>()
            java.lang.String r1 = r4.getMessage()
            int r2 = com.xiaomi.smack.c.a(r4)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r4 = r4.getClass()
            java.lang.String r4 = r4.getSimpleName()
            r3.append(r4)
            java.lang.String r4 = ":"
            r3.append(r4)
            r3.append(r1)
            java.lang.String r4 = r3.toString()
            r3 = 105(0x69, float:1.47E-43)
            if (r2 == r3) goto L_0x0069
            r3 = 199(0xc7, float:2.79E-43)
            if (r2 == r3) goto L_0x0066
            r3 = 499(0x1f3, float:6.99E-43)
            if (r2 == r3) goto L_0x0057
            switch(r2) {
                case 109: goto L_0x0054;
                case 110: goto L_0x0051;
                default: goto L_0x004c;
            }
        L_0x004c:
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.CHANNEL_XMPPEXCEPTION
        L_0x004e:
            r0.a = r1
            goto L_0x006c
        L_0x0051:
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.CHANNEL_TCP_BROKEN_PIPE
            goto L_0x004e
        L_0x0054:
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.CHANNEL_TCP_CONNRESET
            goto L_0x004e
        L_0x0057:
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.CHANNEL_BOSH_EXCEPTION
            r0.a = r2
            java.lang.String r2 = "Terminal binding condition encountered: item-not-found"
            boolean r1 = r1.startsWith(r2)
            if (r1 == 0) goto L_0x006c
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.CHANNEL_BOSH_ITEMNOTFIND
            goto L_0x004e
        L_0x0066:
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.CHANNEL_TCP_ERR
            goto L_0x004e
        L_0x0069:
            com.xiaomi.push.thrift.a r1 = com.xiaomi.push.thrift.a.CHANNEL_TCP_READTIMEOUT
            goto L_0x004e
        L_0x006c:
            com.xiaomi.push.thrift.a r1 = r0.a
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.CHANNEL_TCP_ERR
            if (r1 == r2) goto L_0x007e
            com.xiaomi.push.thrift.a r1 = r0.a
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.CHANNEL_XMPPEXCEPTION
            if (r1 == r2) goto L_0x007e
            com.xiaomi.push.thrift.a r1 = r0.a
            com.xiaomi.push.thrift.a r2 = com.xiaomi.push.thrift.a.CHANNEL_BOSH_EXCEPTION
            if (r1 != r2) goto L_0x0080
        L_0x007e:
            r0.b = r4
        L_0x0080:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stats.d.d(java.lang.Exception):com.xiaomi.stats.d$a");
    }

    private static void e(Exception exc) {
        if (exc == null) {
            throw new NullPointerException();
        }
    }
}
