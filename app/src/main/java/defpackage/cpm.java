package defpackage;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.server.aos.serverkey;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cpm reason: default package */
/* compiled from: SaveUtils */
public class cpm implements coy {
    public static cpm b() {
        return (cpm) ank.a(coy.class);
    }

    public final boolean a(POI poi) {
        if (poi != null) {
            return cpf.b(a()).c(poi);
        }
        return false;
    }

    public static String a(bti bti) {
        return c(b(bti));
    }

    public static String a(String str) {
        return c(str);
    }

    public static String a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("_");
        sb.append(str2);
        return sb.toString();
    }

    public static String b(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("_last_sync_time");
        return sb.toString();
    }

    @SuppressFBWarnings({"SBSC_USE_STRINGBUFFER_CONCATENATION"})
    private static String b(bti bti) {
        String str;
        JSONObject jSONObject;
        String sb;
        int i = bti.c;
        if (i == 0) {
            asy asy = (asy) a.a.a(asy.class);
            if (asy != null) {
                return asy.c().a(bti.d(), bti.c);
            }
            return null;
        } else if (i == 1) {
            dia dia = (dia) bti.d();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(bti.d);
            sb2.append("+");
            sb2.append(bti.e);
            sb2.append("+");
            sb2.append(bti.f);
            sb2.append("+");
            sb2.append(bti.g);
            sb2.append("+");
            if (bti.p && bti.c() != null) {
                Iterator<POI> it = bti.c().iterator();
                while (it.hasNext()) {
                    POI next = it.next();
                    if (!(next == null || next.getPoint() == null)) {
                        sb2.append(next.getPoint().x);
                        sb2.append("+");
                        sb2.append(next.getPoint().y);
                        sb2.append("+");
                    }
                }
            }
            sb2.append(i);
            sb2.append("+");
            sb2.append(bti.h);
            sb2.append("+");
            sb2.append(dia.getPathStrategy());
            return sb2.toString();
        } else if (i == 2) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(bti.d);
            sb3.append("+");
            String sb4 = sb3.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb4);
            sb5.append(bti.e);
            sb5.append("+");
            String sb6 = sb5.toString();
            StringBuilder sb7 = new StringBuilder();
            sb7.append(sb6);
            sb7.append(bti.f);
            sb7.append("+");
            String sb8 = sb7.toString();
            StringBuilder sb9 = new StringBuilder();
            sb9.append(sb8);
            sb9.append(bti.g);
            sb9.append("+");
            String sb10 = sb9.toString();
            try {
                JSONArray jSONArray = new JSONObject(bti.r).getJSONArray(GirfFavoriteRoute.JSON_FIELD_ROUTE_BUS_PATH_SECTION);
                if (jSONArray == null || jSONArray.length() <= 0) {
                    StringBuilder sb11 = new StringBuilder();
                    sb11.append(sb10);
                    sb11.append(i);
                    return sb11.toString();
                }
                StringBuilder sb12 = new StringBuilder();
                sb12.append(sb10);
                sb12.append(i);
                sb12.append("+");
                String sb13 = sb12.toString();
                int i2 = 0;
                while (i2 < jSONArray.length()) {
                    try {
                        try {
                            jSONObject = jSONArray.getJSONObject(i2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            jSONObject = null;
                        }
                        if (jSONObject != null) {
                            String e2 = agd.e(jSONObject, "busid");
                            if (i2 == jSONArray.length() - 1) {
                                StringBuilder sb14 = new StringBuilder();
                                sb14.append(sb13);
                                sb14.append(e2);
                                sb = sb14.toString();
                            } else {
                                StringBuilder sb15 = new StringBuilder();
                                sb15.append(sb13);
                                sb15.append(e2);
                                sb15.append("+");
                                sb = sb15.toString();
                            }
                            sb13 = sb;
                        }
                        i2++;
                    } catch (JSONException e3) {
                        e = e3;
                        str = sb13;
                        e.printStackTrace();
                        return str;
                    }
                }
                return sb13;
            } catch (JSONException e4) {
                e = e4;
                str = sb10;
                e.printStackTrace();
                return str;
            }
        } else if (i == 3) {
            StringBuilder sb16 = new StringBuilder();
            sb16.append(bti.d);
            sb16.append("+");
            String sb17 = sb16.toString();
            StringBuilder sb18 = new StringBuilder();
            sb18.append(sb17);
            sb18.append(bti.e);
            sb18.append("+");
            String sb19 = sb18.toString();
            StringBuilder sb20 = new StringBuilder();
            sb20.append(sb19);
            sb20.append(bti.f);
            sb20.append("+");
            String sb21 = sb20.toString();
            StringBuilder sb22 = new StringBuilder();
            sb22.append(sb21);
            sb22.append(bti.g);
            sb22.append("+");
            String sb23 = sb22.toString();
            StringBuilder sb24 = new StringBuilder();
            sb24.append(sb23);
            sb24.append(i);
            sb24.append("+");
            return sb24.toString();
        } else if (i != 4) {
            return null;
        } else {
            StringBuilder sb25 = new StringBuilder();
            sb25.append(bti.d);
            sb25.append("+");
            String sb26 = sb25.toString();
            StringBuilder sb27 = new StringBuilder();
            sb27.append(sb26);
            sb27.append(bti.e);
            sb27.append("+");
            String sb28 = sb27.toString();
            StringBuilder sb29 = new StringBuilder();
            sb29.append(sb28);
            sb29.append(bti.f);
            sb29.append("+");
            String sb30 = sb29.toString();
            StringBuilder sb31 = new StringBuilder();
            sb31.append(sb30);
            sb31.append(bti.g);
            sb31.append("+");
            String sb32 = sb31.toString();
            StringBuilder sb33 = new StringBuilder();
            sb33.append(sb32);
            sb33.append(i);
            sb33.append("+");
            String sb34 = sb33.toString();
            StringBuilder sb35 = new StringBuilder();
            sb35.append(sb34);
            sb35.append(bti.h);
            return sb35.toString();
        }
    }

    private static String c(String str) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            try {
                messageDigest.reset();
                messageDigest.update(str.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException unused) {
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException unused2) {
            messageDigest = null;
        }
        if (messageDigest == null) {
            return "";
        }
        byte[] digest = messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            if (Integer.toHexString(digest[i] & 255).length() == 1) {
                stringBuffer.append("0");
                stringBuffer.append(Integer.toHexString(digest[i] & 255));
            } else {
                stringBuffer.append(Integer.toHexString(digest[i] & 255));
            }
        }
        return stringBuffer.toString();
    }

    public static String b(POI poi) {
        GeoPoint point = poi.getPoint();
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(point.x);
        sb.append("+");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(point.y);
        sb3.append("+");
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb4);
        sb5.append(poi.getName());
        return c(sb5.toString());
    }

    public final String a() {
        String str;
        try {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService == null) {
                str = "";
            } else {
                ant e = iAccountService.e();
                if (e == null) {
                    str = "";
                } else {
                    str = e.a;
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
            str = null;
        }
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str)) {
            SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences("SecurityPerson", 0);
            if (sharedPreferences != null) {
                String string = sharedPreferences.getString(Oauth2AccessToken.KEY_UID, null);
                if (string != null) {
                    str = serverkey.amapDecode(string);
                }
            }
        }
        return TextUtils.isEmpty(str) ? "public" : str;
    }
}
