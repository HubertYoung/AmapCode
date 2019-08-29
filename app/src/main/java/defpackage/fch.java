package defpackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/* renamed from: fch reason: default package */
/* compiled from: MessageReaderWriter */
public final class fch {
    private static String d = "MessageReaderWriter";
    Socket a;
    InputStream b;
    OutputStream c;

    fch(Socket socket) throws IOException {
        this.a = socket;
        this.b = this.a.getInputStream();
        this.c = this.a.getOutputStream();
    }

    fch(OutputStream outputStream, InputStream inputStream) throws IOException {
        this.b = inputStream;
        this.c = outputStream;
    }

    private static fbx a(JSONObject jSONObject) throws JSONException {
        int i;
        try {
            i = jSONObject.getInt(fcu.S);
        } catch (JSONException e) {
            e.printStackTrace();
            i = 0;
        }
        fbx fbx = new fbx(jSONObject.getString(fcu.s), jSONObject.getString(fcu.r), jSONObject.getString(fcu.E), jSONObject.getBoolean(fcu.M), jSONObject.getBoolean(fcu.P), jSONObject.getBoolean(fcu.R), i);
        return fbx;
    }

    private static fcj b(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray = jSONObject.getJSONArray(fcu.t);
        new StringBuilder("found jason array=").append(jSONArray.length());
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(Long.valueOf(jSONArray.getLong(i)));
        }
        return new fcj(arrayList);
    }

    private static fbp c(JSONObject jSONObject) throws JSONException {
        long j;
        try {
            j = jSONObject.getLong(fcu.C);
        } catch (JSONException e) {
            e.printStackTrace();
            j = 0;
        }
        fbp fbp = new fbp(jSONObject.getInt(fcu.v), jSONObject.getString(fcu.x), jSONObject.getInt(fcu.y), jSONObject.getInt(fcu.B), j);
        return fbp;
    }

    private static fbt d(JSONObject jSONObject) throws JSONException {
        int i;
        try {
            i = jSONObject.getInt(fcu.w);
        } catch (JSONException e) {
            e.printStackTrace();
            i = 0;
        }
        return new fbt(jSONObject.getInt(fcu.v), jSONObject.getBoolean(fcu.H), i);
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [fcu] */
    /* JADX WARNING: type inference failed for: r0v1 */
    /* JADX WARNING: type inference failed for: r1v6, types: [fcr] */
    /* JADX WARNING: type inference failed for: r0v6, types: [fbx] */
    /* JADX WARNING: type inference failed for: r0v7, types: [fbz] */
    /* JADX WARNING: type inference failed for: r0v8, types: [fcj] */
    /* JADX WARNING: type inference failed for: r0v9, types: [fcq] */
    /* JADX WARNING: type inference failed for: r1v15, types: [fcl] */
    /* JADX WARNING: type inference failed for: r0v11, types: [fbp] */
    /* JADX WARNING: type inference failed for: r0v12, types: [fcg] */
    /* JADX WARNING: type inference failed for: r0v13, types: [fbt] */
    /* JADX WARNING: type inference failed for: r0v14, types: [fcp] */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r1v18, types: [fcr] */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r0v17 */
    /* JADX WARNING: type inference failed for: r0v18 */
    /* JADX WARNING: type inference failed for: r0v19 */
    /* JADX WARNING: type inference failed for: r1v19, types: [fcl] */
    /* JADX WARNING: type inference failed for: r0v20 */
    /* JADX WARNING: type inference failed for: r0v21 */
    /* JADX WARNING: type inference failed for: r0v22 */
    /* JADX WARNING: type inference failed for: r0v23 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v15
      assigns: [fcr, fcl]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], fcu, fcr, fcl]
      mth insns count: 76
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 12 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static defpackage.fcu a(java.lang.String r11) {
        /*
            org.json.JSONTokener r0 = new org.json.JSONTokener     // Catch:{ JSONException -> 0x00b9 }
            r0.<init>(r11)     // Catch:{ JSONException -> 0x00b9 }
            java.lang.Object r11 = r0.nextValue()     // Catch:{ JSONException -> 0x00b9 }
            org.json.JSONObject r11 = (org.json.JSONObject) r11     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r0 = defpackage.fcu.m     // Catch:{ JSONException -> 0x00b9 }
            int r0 = r11.getInt(r0)     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r1 = defpackage.fcu.l     // Catch:{ JSONException -> 0x00b9 }
            int r1 = r11.getInt(r1)     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r2 = "category="
            java.lang.String r3 = java.lang.String.valueOf(r1)     // Catch:{ JSONException -> 0x00b9 }
            r2.concat(r3)     // Catch:{ JSONException -> 0x00b9 }
            r2 = 1
            if (r1 != r2) goto L_0x009a
            switch(r0) {
                case 2: goto L_0x0095;
                case 3: goto L_0x008f;
                case 4: goto L_0x008a;
                case 5: goto L_0x007e;
                case 6: goto L_0x0026;
                case 7: goto L_0x0053;
                case 8: goto L_0x004e;
                case 9: goto L_0x0047;
                case 10: goto L_0x0041;
                case 11: goto L_0x0028;
                default: goto L_0x0026;
            }     // Catch:{ JSONException -> 0x00b9 }
        L_0x0026:
            goto L_0x00b9
        L_0x0028:
            fcp r0 = new fcp     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r1 = defpackage.fcu.M     // Catch:{ JSONException -> 0x00b9 }
            boolean r1 = r11.getBoolean(r1)     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r2 = defpackage.fcu.P     // Catch:{ JSONException -> 0x00b9 }
            boolean r2 = r11.getBoolean(r2)     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r3 = defpackage.fcu.R     // Catch:{ JSONException -> 0x00b9 }
            boolean r11 = r11.getBoolean(r3)     // Catch:{ JSONException -> 0x00b9 }
            r0.<init>(r1, r2, r11)     // Catch:{ JSONException -> 0x00b9 }
            goto L_0x00ba
        L_0x0041:
            fbt r0 = d(r11)     // Catch:{ JSONException -> 0x00b9 }
            goto L_0x00ba
        L_0x0047:
            fcg r0 = new fcg     // Catch:{ JSONException -> 0x00b9 }
            r0.<init>()     // Catch:{ JSONException -> 0x00b9 }
            goto L_0x00ba
        L_0x004e:
            fbp r0 = c(r11)     // Catch:{ JSONException -> 0x00b9 }
            goto L_0x00ba
        L_0x0053:
            fcl r0 = new fcl     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r1 = defpackage.fcu.k     // Catch:{ JSONException -> 0x00b9 }
            long r2 = r11.getLong(r1)     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r1 = defpackage.fcu.j     // Catch:{ JSONException -> 0x00b9 }
            long r4 = r11.getLong(r1)     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r1 = defpackage.fcu.v     // Catch:{ JSONException -> 0x00b9 }
            int r6 = r11.getInt(r1)     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r1 = defpackage.fcu.z     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r7 = r11.getString(r1)     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r1 = defpackage.fcu.D     // Catch:{ JSONException -> 0x00b9 }
            long r8 = r11.getLong(r1)     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r1 = defpackage.fcu.A     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r10 = r11.getString(r1)     // Catch:{ JSONException -> 0x00b9 }
            r1 = r0
            r1.<init>(r2, r4, r6, r7, r8, r10)     // Catch:{ JSONException -> 0x00b9 }
            goto L_0x00ba
        L_0x007e:
            fcq r0 = new fcq     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r1 = defpackage.fcu.t     // Catch:{ JSONException -> 0x00b9 }
            long r1 = r11.getLong(r1)     // Catch:{ JSONException -> 0x00b9 }
            r0.<init>(r1)     // Catch:{ JSONException -> 0x00b9 }
            goto L_0x00ba
        L_0x008a:
            fcj r0 = b(r11)     // Catch:{ JSONException -> 0x00b9 }
            goto L_0x00ba
        L_0x008f:
            fbz r0 = new fbz     // Catch:{ JSONException -> 0x00b9 }
            r0.<init>()     // Catch:{ JSONException -> 0x00b9 }
            goto L_0x00ba
        L_0x0095:
            fbx r0 = a(r11)     // Catch:{ JSONException -> 0x00b9 }
            goto L_0x00ba
        L_0x009a:
            fcr r0 = new fcr     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r1 = defpackage.fcu.k     // Catch:{ JSONException -> 0x00b9 }
            long r2 = r11.getLong(r1)     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r1 = defpackage.fcu.j     // Catch:{ JSONException -> 0x00b9 }
            long r4 = r11.getLong(r1)     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r1 = defpackage.fcu.m     // Catch:{ JSONException -> 0x00b9 }
            int r6 = r11.getInt(r1)     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r1 = defpackage.fcu.O     // Catch:{ JSONException -> 0x00b9 }
            java.lang.String r7 = r11.getString(r1)     // Catch:{ JSONException -> 0x00b9 }
            r1 = r0
            r1.<init>(r2, r4, r6, r7)     // Catch:{ JSONException -> 0x00b9 }
            goto L_0x00ba
        L_0x00b9:
            r0 = 0
        L_0x00ba:
            if (r0 == 0) goto L_0x00bf
            r11 = 2
            r0.U = r11
        L_0x00bf:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fch.a(java.lang.String):fcu");
    }

    static void a(byte[] bArr) {
        StringBuilder sb = new StringBuilder("0x");
        sb.append(bArr[0]);
        sb.append(" 0x");
        sb.append(bArr[1]);
        sb.append(" 0x");
        sb.append(bArr[2]);
        sb.append(" 0x");
        sb.append(bArr[3]);
        sb.append(" 0x");
        sb.append(bArr[4]);
        sb.append(" 0x");
        sb.append(bArr[5]);
        sb.append(" 0x");
        sb.append(bArr[6]);
        sb.append(" 0x");
        sb.append(bArr[7]);
        sb.append(" 0x");
        sb.append(bArr[8]);
        sb.append(" 0x");
        sb.append(bArr[9]);
    }

    static int b(byte[] bArr) {
        return (bArr[6] & 255) + 0 + ((bArr[7] & 255) << 8) + ((bArr[8] & 255) << 16) + ((bArr[9] & 255) << 24);
    }

    static String a(fbw fbw) {
        try {
            return new JSONStringer().object().key(fcu.i).value(1).key(fcu.l).value(1).key(fcu.m).value((long) fbw.T).key(fcu.n).value(fbw.a).key(fcu.o).value(fbw.c).key(fcu.p).value(fbw.d).key(fcu.q).value(fbw.e).key(fcu.r).value(fbw.b).key(fcu.E).value(fbw.f).key(fcu.G).value(fbw.g).key(fcu.Q).value((long) fbw.h).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fcj fcj) {
        JSONStringer jSONStringer = new JSONStringer();
        try {
            new StringBuilder("size=").append(fcj.a.size());
            JSONArray jSONArray = new JSONArray(fcj.a);
            new StringBuilder("json array size=").append(jSONArray.length());
            return jSONStringer.object().key(fcu.i).value(1).key(fcu.l).value(1).key(fcu.m).value((long) fcj.T).key(fcu.t).value(jSONArray).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fcq fcq) {
        try {
            return new JSONStringer().object().key(fcu.i).value(1).key(fcu.l).value(1).key(fcu.m).value((long) fcq.T).key(fcu.t).value(fcq.a).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fbz fbz) {
        try {
            return new JSONStringer().object().key(fcu.i).value(1).key(fcu.l).value(1).key(fcu.m).value((long) fbz.T).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fcl fcl) {
        try {
            return new JSONStringer().object().key(fcu.i).value(1).key(fcu.l).value(1).key(fcu.m).value((long) fcl.T).key(fcu.z).value(fcl.a).key(fcu.k).value(fcl.b).key(fcu.j).value(fcl.c).key(fcu.v).value((long) fcl.d).key(fcu.D).value(fcl.e).key(fcu.A).value(fcl.f).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fbp fbp) {
        try {
            return new JSONStringer().object().key(fcu.i).value(1).key(fcu.l).value(1).key(fcu.m).value((long) fbp.T).key(fcu.x).value(fbp.c).key(fcu.y).value((long) fbp.b).key(fcu.B).value((long) fbp.d).key(fcu.v).value((long) fbp.a).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fcf fcf) {
        try {
            return new JSONStringer().object().key(fcu.i).value(1).key(fcu.l).value(1).key(fcu.m).value((long) fcf.T).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fbt fbt) {
        try {
            return new JSONStringer().object().key(fcu.i).value(1).key(fcu.l).value(1).key(fcu.m).value((long) fbt.T).key(fcu.H).value(fbt.b).key(fcu.v).value((long) fbt.a).key(fcu.w).value((long) fbt.c).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fck fck) {
        try {
            return new JSONStringer().object().key(fcu.i).value(1).key(fcu.l).value(1).key(fcu.m).value((long) fck.T).key(fcu.N).value(fck.a).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fcm fcm) {
        try {
            return new JSONStringer().object().key(fcu.i).value(1).key(fcu.l).value(1).key(fcu.m).value((long) fcm.T).key(fcu.F).value(fcm.a).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fbq fbq) {
        try {
            return new JSONStringer().object().key(fcu.i).value(1).key(fcu.l).value(1).key(fcu.m).value((long) fbq.T).key(fcu.p).value(fbq.b).key(fcu.q).value(fbq.a).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fco fco) {
        try {
            return new JSONStringer().object().key(fcu.i).value(1).key(fcu.l).value(1).key(fcu.m).value((long) fco.T).key(fcu.J).value(fco.a).key(fcu.K).value(fco.b).key(fcu.L).value(fco.c).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fbx fbx) {
        try {
            return new JSONStringer().object().key(fcu.i).value(2).key(fcu.l).value(1).key(fcu.m).value((long) fbx.T).key(fcu.E).value(fbx.c).key(fcu.s).value(fbx.a).key(fcu.M).value(fbx.d).key(fcu.r).value(fbx.b).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fcp fcp) {
        try {
            return new JSONStringer().object().key(fcu.i).value(2).key(fcu.l).value(1).key(fcu.m).value((long) fcp.T).key(fcu.M).value(fcp.a).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fcn fcn) {
        try {
            return new JSONStringer().object().key(fcu.i).value(1).key(fcu.l).value(1).key(fcu.m).value((long) fcn.T).key(fcu.I).value(fcn.a).key(fcu.J).value(fcn.b).key(fcu.K).value(fcn.c).key(fcu.L).value(fcn.d).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(fcr fcr) {
        try {
            return new JSONStringer().object().key(fcu.i).value(1).key(fcu.l).value(2).key(fcu.m).value((long) fcr.d).key(fcu.k).value(fcr.a).key(fcu.j).value(fcr.b).key(fcu.O).value(fcr.c).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
