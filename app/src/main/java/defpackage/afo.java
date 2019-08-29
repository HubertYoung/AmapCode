package defpackage;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.server.aos.serverkey;
import java.security.MessageDigest;

/* renamed from: afo reason: default package */
/* compiled from: UploadLogRequestor */
public final class afo {
    private static final char[] c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private int a = 0;
    private String b;

    public final String a() {
        return a(this.a);
    }

    private String a(int i) {
        String b2 = aaf.b(ConfigerHelper.LOG_URL_KEY);
        switch (i) {
            case 1:
                StringBuilder sb = new StringBuilder();
                sb.append(b2);
                sb.append("?product=2&type=2");
                sb.append(NetworkParam.getNetworkParam(b2));
                sb.append("&channel=");
                sb.append(serverkey.getAosChannel());
                sb.append("&sign=");
                sb.append(a(this.b));
                return sb.toString();
            case 2:
                StringBuilder sb2 = new StringBuilder();
                sb2.append(b2);
                sb2.append("basestation");
                return sb2.toString();
            case 4:
                StringBuilder sb3 = new StringBuilder();
                sb3.append(b2);
                sb3.append("?product=2&type=5");
                sb3.append(NetworkParam.getNetworkParam(b2));
                sb3.append("&channel=");
                sb3.append(serverkey.getAosChannel());
                sb3.append("&sign=");
                sb3.append(a(this.b));
                return sb3.toString();
            case 5:
                StringBuilder sb4 = new StringBuilder();
                sb4.append(b2);
                sb4.append("?product=2&type=11");
                sb4.append(NetworkParam.getNetworkParam(b2));
                sb4.append("&channel=");
                sb4.append(serverkey.getAosChannel());
                sb4.append("&sign=");
                sb4.append(a(this.b));
                return sb4.toString();
            default:
                return null;
        }
    }

    public afo(int i, String str) {
        this.a = i;
        this.b = str;
    }

    private static String a(String str) {
        byte[] digest;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(serverkey.getAosKey());
        try {
            byte[] bytes = sb.toString().replace("null", "").getBytes();
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bytes);
            char[] cArr = new char[(r0 * 2)];
            int i = 0;
            for (byte b2 : instance.digest()) {
                int i2 = i + 1;
                cArr[i] = c[(b2 >>> 4) & 15];
                i = i2 + 1;
                cArr[i2] = c[b2 & 15];
            }
            return new String(cArr);
        } catch (Exception unused) {
            return null;
        }
    }
}
