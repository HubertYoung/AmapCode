package defpackage;

import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Value;
import com.autonavi.patch.PatchUtil;
import java.io.File;
import java.util.Iterator;
import org.json.JSONObject;

/* renamed from: enk reason: default package */
/* compiled from: SoHotfixFileMgr */
public final class enk {
    public enj a;
    private final String b = "zip";
    private final String c = Value.OK;
    private final String d = "crash";
    private final String e = "first-run";

    public enk(enj enj) {
        this.a = enj;
    }

    public final File a(int i) {
        File file = new File(enn.a(this.a, i), "crash");
        if (!file.isFile()) {
            return null;
        }
        return file;
    }

    public final void a(int i, long j) {
        enn.a(new File(enn.a(this.a, i), "crash"), j);
    }

    public final File b(int i) {
        File file = new File(enn.a(this.a, i), "first-run");
        if (!file.isFile()) {
            return null;
        }
        return file;
    }

    public final boolean c(int i) {
        File[] listFiles = new File(enn.b(this.a, i), "zip").listFiles();
        if (listFiles == null) {
            return false;
        }
        int i2 = 0;
        for (File file : listFiles) {
            if (file.isFile()) {
                String name = file.getName();
                int lastIndexOf = name.lastIndexOf(".patch");
                if (lastIndexOf != -1) {
                    String substring = name.substring(0, lastIndexOf);
                    if (substring.endsWith(".so")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.a.b);
                        sb.append("/");
                        sb.append(substring);
                        File file2 = new File(sb.toString());
                        if (file2.exists()) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(enn.b(this.a, i));
                            sb2.append("/");
                            sb2.append(substring);
                            if (PatchUtil.bspatch(file2.getAbsolutePath(), new File(sb2.toString()).getAbsolutePath(), file.getAbsolutePath()) != 0) {
                                return false;
                            }
                            i2++;
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
        }
        if (i2 > 0) {
            return true;
        }
        return false;
    }

    public final boolean d(int i) {
        File file = new File(new File(enn.b(this.a, i), "zip"), "md5.json");
        if (!file.isFile()) {
            return false;
        }
        String a2 = enp.a(file);
        if (a2 == null) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(a2);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                String string = jSONObject.getString(next);
                if (string == null) {
                    return false;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(enn.b(this.a, i));
                sb.append("/");
                sb.append(next);
                if (!string.equals(ens.a(new File(sb.toString())))) {
                    return false;
                }
            }
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final void a() {
        File parentFile = new File(this.a.c).getParentFile();
        if (parentFile != null) {
            File[] listFiles = parentFile.listFiles();
            if (listFiles != null) {
                String str = this.a.d;
                for (File file : listFiles) {
                    if (!str.equals(file.getName())) {
                        enp.b(file);
                    }
                }
            }
        }
    }

    public final enm b() {
        File[] listFiles = new File(this.a.c).listFiles();
        if (listFiles == null) {
            return null;
        }
        int length = listFiles.length;
        if (length == 0) {
            return null;
        }
        enm[] enmArr = new enm[length];
        int i = -1;
        int i2 = -1;
        for (int i3 = length - 1; i3 >= 0; i3--) {
            enm enm = new enm(listFiles[i3]);
            int i4 = enm.a;
            if (i4 > i) {
                i2 = i3;
                i = i4;
            }
            enmArr[i3] = enm;
        }
        enm enm2 = i2 != -1 ? enmArr[i2] : null;
        if (enm2 == null) {
            return null;
        }
        File[] listFiles2 = new File(enm2.b).listFiles();
        if (listFiles2 == null) {
            return null;
        }
        for (File file : listFiles2) {
            if (file.getPath().endsWith(".so") && file.length() != 0) {
                return enm2;
            }
        }
        return null;
    }
}
