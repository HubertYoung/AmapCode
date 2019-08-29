package com.alipay.deviceid.module.rpc.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class c {
    private int a;
    private Reader b;
    private char c;
    private boolean d;

    private c(Reader reader) {
        this.b = !reader.markSupported() ? new BufferedReader(reader) : reader;
        this.d = false;
        this.a = 0;
    }

    public c(String str) {
        this((Reader) new StringReader(str));
    }

    private String a(int i) {
        if (i == 0) {
            return "";
        }
        char[] cArr = new char[i];
        int i2 = 0;
        if (this.d) {
            this.d = false;
            cArr[0] = this.c;
            i2 = 1;
        }
        while (i2 < i) {
            try {
                int read = this.b.read(cArr, i2, i - i2);
                if (read == -1) {
                    break;
                }
                i2 += read;
            } catch (IOException e) {
                throw new JSONException((Throwable) e);
            }
        }
        this.a += i2;
        if (i2 < i) {
            throw a((String) "Substring bounds error");
        }
        this.c = cArr[i - 1];
        return new String(cArr);
    }

    public final JSONException a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(toString());
        return new JSONException(sb.toString());
    }

    public final void a() {
        if (this.d || this.a <= 0) {
            throw new JSONException((String) "Stepping back two steps is not supported");
        }
        this.a--;
        this.d = true;
    }

    public final char b() {
        if (this.d) {
            this.d = false;
            if (this.c != 0) {
                this.a++;
            }
            return this.c;
        }
        try {
            int read = this.b.read();
            if (read <= 0) {
                this.c = 0;
                return 0;
            }
            this.a++;
            this.c = (char) read;
            return this.c;
        } catch (IOException e) {
            throw new JSONException((Throwable) e);
        }
    }

    public final char c() {
        char b2;
        char b3;
        char b4;
        while (true) {
            b2 = b();
            if (b2 == '/') {
                char b5 = b();
                if (b5 != '*') {
                    if (b5 == '/') {
                        do {
                            b4 = b();
                            if (b4 == 10 || b4 == 13) {
                                break;
                            }
                        } while (b4 != 0);
                    } else {
                        a();
                        return '/';
                    }
                } else {
                    while (true) {
                        char b6 = b();
                        if (b6 != 0) {
                            if (b6 == '*') {
                                if (b() == '/') {
                                    break;
                                }
                                a();
                            }
                        } else {
                            throw a((String) "Unclosed comment");
                        }
                    }
                }
            } else if (b2 == '#') {
                do {
                    b3 = b();
                    if (b3 == 10 || b3 == 13) {
                        break;
                    }
                } while (b3 != 0);
            } else if (b2 == 0 || b2 > ' ') {
                return b2;
            }
        }
        return b2;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:55|56|57) */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:(2:41|(2:50|51)(3:47|48|49))|52|53|54) */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00c0, code lost:
        return new java.lang.Long(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00c6, code lost:
        return new java.lang.Double(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00c7, code lost:
        return r1;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:52:0x00b5 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x00bb */
    /* JADX WARNING: Missing exception handler attribute for start block: B:58:0x00c1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object d() {
        /*
            r10 = this;
            char r0 = r10.c()
            r1 = 34
            r2 = 8
            r3 = 120(0x78, float:1.68E-43)
            r4 = 16
            r5 = 2
            if (r0 == r1) goto L_0x00da
            r1 = 91
            if (r0 == r1) goto L_0x00d1
            r1 = 123(0x7b, float:1.72E-43)
            if (r0 == r1) goto L_0x00c8
            switch(r0) {
                case 39: goto L_0x00da;
                case 40: goto L_0x00d1;
                default: goto L_0x001a;
            }
        L_0x001a:
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            r6 = r0
        L_0x0020:
            r7 = 32
            if (r6 < r7) goto L_0x0034
            java.lang.String r7 = ",:]}/\\\"[{;=#"
            int r7 = r7.indexOf(r6)
            if (r7 >= 0) goto L_0x0034
            r1.append(r6)
            char r6 = r10.b()
            goto L_0x0020
        L_0x0034:
            r10.a()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = r1.trim()
            java.lang.String r6 = ""
            boolean r6 = r1.equals(r6)
            if (r6 == 0) goto L_0x004e
            java.lang.String r0 = "Missing value"
            com.alipay.deviceid.module.rpc.json.JSONException r0 = r10.a(r0)
            throw r0
        L_0x004e:
            java.lang.String r6 = "true"
            boolean r6 = r1.equalsIgnoreCase(r6)
            if (r6 == 0) goto L_0x005a
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            return r0
        L_0x005a:
            java.lang.String r6 = "false"
            boolean r6 = r1.equalsIgnoreCase(r6)
            if (r6 == 0) goto L_0x0065
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            return r0
        L_0x0065:
            java.lang.String r6 = "null"
            boolean r6 = r1.equalsIgnoreCase(r6)
            if (r6 == 0) goto L_0x0070
            java.lang.Object r0 = com.alipay.deviceid.module.rpc.json.b.a
            return r0
        L_0x0070:
            r6 = 48
            if (r0 < r6) goto L_0x0078
            r7 = 57
            if (r0 <= r7) goto L_0x0086
        L_0x0078:
            r7 = 46
            if (r0 == r7) goto L_0x0086
            r7 = 45
            if (r0 == r7) goto L_0x0086
            r7 = 43
            if (r0 != r7) goto L_0x0085
            goto L_0x0086
        L_0x0085:
            return r1
        L_0x0086:
            if (r0 != r6) goto L_0x00b5
            int r0 = r1.length()
            if (r0 <= r5) goto L_0x00ab
            r0 = 1
            char r6 = r1.charAt(r0)
            if (r6 == r3) goto L_0x009d
            char r0 = r1.charAt(r0)
            r3 = 88
            if (r0 != r3) goto L_0x00ab
        L_0x009d:
            java.lang.Integer r0 = new java.lang.Integer     // Catch:{ Exception -> 0x00b5 }
            java.lang.String r2 = r1.substring(r5)     // Catch:{ Exception -> 0x00b5 }
            int r2 = java.lang.Integer.parseInt(r2, r4)     // Catch:{ Exception -> 0x00b5 }
            r0.<init>(r2)     // Catch:{ Exception -> 0x00b5 }
            return r0
        L_0x00ab:
            java.lang.Integer r0 = new java.lang.Integer     // Catch:{ Exception -> 0x00b5 }
            int r2 = java.lang.Integer.parseInt(r1, r2)     // Catch:{ Exception -> 0x00b5 }
            r0.<init>(r2)     // Catch:{ Exception -> 0x00b5 }
            return r0
        L_0x00b5:
            java.lang.Integer r0 = new java.lang.Integer     // Catch:{ Exception -> 0x00bb }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00bb }
            return r0
        L_0x00bb:
            java.lang.Long r0 = new java.lang.Long     // Catch:{ Exception -> 0x00c1 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00c1 }
            return r0
        L_0x00c1:
            java.lang.Double r0 = new java.lang.Double     // Catch:{ Exception -> 0x00c7 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00c7 }
            return r0
        L_0x00c7:
            return r1
        L_0x00c8:
            r10.a()
            com.alipay.deviceid.module.rpc.json.b r0 = new com.alipay.deviceid.module.rpc.json.b
            r0.<init>(r10)
            return r0
        L_0x00d1:
            r10.a()
            com.alipay.deviceid.module.rpc.json.a r0 = new com.alipay.deviceid.module.rpc.json.a
            r0.<init>(r10)
            return r0
        L_0x00da:
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
        L_0x00df:
            char r6 = r10.b()
            if (r6 == 0) goto L_0x0138
            r7 = 10
            if (r6 == r7) goto L_0x0138
            r8 = 13
            if (r6 == r8) goto L_0x0138
            r9 = 92
            if (r6 == r9) goto L_0x00fc
            if (r6 != r0) goto L_0x00f8
            java.lang.String r0 = r1.toString()
            return r0
        L_0x00f8:
            r1.append(r6)
            goto L_0x00df
        L_0x00fc:
            char r6 = r10.b()
            r9 = 98
            if (r6 == r9) goto L_0x0134
            r9 = 102(0x66, float:1.43E-43)
            if (r6 == r9) goto L_0x0131
            r9 = 110(0x6e, float:1.54E-43)
            if (r6 == r9) goto L_0x012d
            r7 = 114(0x72, float:1.6E-43)
            if (r6 == r7) goto L_0x0129
            if (r6 == r3) goto L_0x0124
            switch(r6) {
                case 116: goto L_0x0121;
                case 117: goto L_0x0116;
                default: goto L_0x0115;
            }
        L_0x0115:
            goto L_0x00f8
        L_0x0116:
            r6 = 4
            java.lang.String r6 = r10.a(r6)
        L_0x011b:
            int r6 = java.lang.Integer.parseInt(r6, r4)
            char r6 = (char) r6
            goto L_0x00f8
        L_0x0121:
            r6 = 9
            goto L_0x00f8
        L_0x0124:
            java.lang.String r6 = r10.a(r5)
            goto L_0x011b
        L_0x0129:
            r1.append(r8)
            goto L_0x00df
        L_0x012d:
            r1.append(r7)
            goto L_0x00df
        L_0x0131:
            r6 = 12
            goto L_0x00f8
        L_0x0134:
            r1.append(r2)
            goto L_0x00df
        L_0x0138:
            java.lang.String r0 = "Unterminated string"
            com.alipay.deviceid.module.rpc.json.JSONException r0 = r10.a(r0)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.deviceid.module.rpc.json.c.d():java.lang.Object");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(" at character ");
        sb.append(this.a);
        return sb.toString();
    }
}
