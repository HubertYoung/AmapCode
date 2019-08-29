package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.accountopenauth.MCAccountStatusEnum;
import com.alipay.android.phone.inside.api.model.accountopenauth.McAccountStatusChangeModel;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.service.InsideOperationService;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.minimap.account.bind.BindRequestHolder;
import com.autonavi.minimap.account.login.LoginRequestHolder;
import com.autonavi.minimap.account.login.param.LoginAlipayParam;
import com.autonavi.minimap.ajx3.Ajx;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import mtopsdk.mtop.intf.Mtop;
import org.json.JSONException;

/* renamed from: aok reason: default package */
/* compiled from: NewAlipayHandler */
public final class aok extends aor {
    WeakReference<bid> a;
    public boolean b;
    public boolean c;
    public String d;
    private String l = "NewAlipayHandler";
    private anq m;
    private String n;
    private List<String> o;
    private String p;
    private List<String> q = new ArrayList();

    public aok() {
        this.q.add("aplogin");
    }

    public final void a(List<String> list) {
        if (list != null && list.size() != 0) {
            this.q = list;
        }
    }

    public final void a(String str, List<String> list, String str2) {
        this.n = str;
        this.o = list;
        this.p = str2;
        this.b = true;
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        int i = this.f;
        if (i != 99) {
            switch (i) {
                case 0:
                    f();
                    return;
                case 1:
                    f();
                    return;
                case 2:
                    a(aos.a, this.h, this.i, this.j, this.e);
                    return;
            }
        } else {
            f();
        }
    }

    private void f() {
        aol.a().a(c(), b(), d(), this.b, this.c, (a) new a() {
            public final void a() {
                if (aok.this.f == 2 || aok.this.f == 99 || aok.this.f == 1) {
                    a.a.b();
                    return;
                }
                if (aok.this.k && aok.this.f == 0) {
                    a.a.a();
                }
            }

            public final void a(boolean z, aoh aoh) {
                if (z) {
                    aok.a(aok.this, aoh);
                    return;
                }
                if (aok.this.e != null) {
                    aox aox = aok.this.e;
                    StringBuilder sb = new StringBuilder("AlipayAuthResult resultStatus:");
                    sb.append(aoh.a);
                    aox.a(new Exception(sb.toString()));
                }
            }
        });
    }

    @NonNull
    public static String c() {
        String a2 = aod.a();
        if (TextUtils.equals(a2, "online")) {
            return "http://openapi.alipay.com/gateway.do";
        }
        if (TextUtils.equals(a2, "test")) {
            return "http://openapi.test.alipay.net/gateway.do";
        }
        if (TextUtils.equals(a2, "dev")) {
            return "http://openapi.stable.alipay.net/gateway.do";
        }
        return "http://openapi.alipay.com/gateway.do";
    }

    private static String a(Map<String, String> map, boolean z) {
        map.remove("sign");
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList arrayList = new ArrayList(map.keySet());
        Collections.sort(arrayList);
        int i = 0;
        while (i < arrayList.size()) {
            String str = (String) arrayList.get(i);
            String str2 = map.get(str);
            if (z) {
                try {
                    str2 = URLEncoder.encode(str2, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(i == 0 ? "" : "&");
            sb.append(str);
            sb.append("=");
            sb.append(str2);
            stringBuffer.append(sb.toString());
            i++;
        }
        return stringBuffer.toString();
    }

    private void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            a.a.b(this.m);
            a(str, 1, 0, 0, new aox(aox.e) {
                public final void a(chm chm) {
                    super.a(chm);
                    if (aok.this.a != null && ((bid) aok.this.a.get()) != null) {
                        if (chm == null) {
                            a.a.b(false);
                            return;
                        }
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put((String) "accountType", (Object) AccountType.Alipay.name());
                            jSONObject.put((String) "isBind", (Object) "1");
                            jSONObject.put((String) "responseData", (Object) chm.toJson().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Ajx.getInstance().startService("basemap_account_DefaultThirdPartAccountBindService", "path://amap_bundle_account/src/service/DefaultThirdPartAccountBindService.js", jSONObject.toString(), null);
                    }
                }
            });
        }
    }

    public final void a(bid bid, anq anq, String str) {
        this.f = 99;
        this.a = new WeakReference<>(bid);
        this.m = anq;
        if (!TextUtils.isEmpty(str)) {
            aos.a = str;
            a(str);
            return;
        }
        a();
    }

    private String g() {
        if (this.q == null || this.q.size() == 0) {
            return "";
        }
        Iterator<String> it = this.q.iterator();
        String next = it.next();
        if (!it.hasNext()) {
            return next == null ? "" : next;
        }
        StringBuffer stringBuffer = new StringBuffer(256);
        if (next != null) {
            stringBuffer.append(next);
        }
        while (it.hasNext()) {
            stringBuffer.append("|||");
            String next2 = it.next();
            if (next2 != null) {
                stringBuffer.append(next2);
            }
        }
        new StringBuilder("getScope: ").append(stringBuffer.toString());
        return stringBuffer.toString();
    }

    private void a(String str, int i, int i2, int i3, final aox aox) {
        AMapLog.e("accountTAG", String.format("AlipayHandler doAlipayBind. authCode: %s, type: %s, replaceType: %s, updateMode: %s", new Object[]{str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)}));
        final String e = e();
        chn chn = new chn();
        chn.b = str;
        chn.e = i;
        chn.f = i2;
        chn.g = i3;
        if ("test".equals(ConfigerHelper.getInstance().getNetCondition())) {
            String a2 = aod.a();
            if (TextUtils.equals(a2, "test")) {
                chn.a = "sit";
            } else if (TextUtils.equals(a2, "dev")) {
                chn.a = "dev";
            }
        }
        chn.h = g();
        BindRequestHolder.getInstance().sendBindAlipay(chn, new aox(aox.e) {
            public final void a(chm chm) {
                super.a(chm);
                MapSharePreference mapSharePreference = new MapSharePreference((String) "AlipayClearCookies");
                if (!mapSharePreference.getBooleanValue("isReplaceAlipay", false)) {
                    mapSharePreference.putBooleanValue("isReplaceAlipay", true);
                }
                String e = aok.e();
                if (!TextUtils.isEmpty(e) && !TextUtils.equals(e, e)) {
                    aok.a(MCAccountStatusEnum.MC_CHANGE_BIND_ALIPAY);
                    Mtop.a((Context) AMapAppGlobal.getApplication()).c();
                }
                aox.a(chm);
            }

            public final void a(Exception exc) {
                super.a(exc);
                aox.a(exc);
            }
        });
        apd.a();
    }

    public static void a(final MCAccountStatusEnum mCAccountStatusEnum) {
        ahm.a(new Runnable() {
            public final void run() {
                McAccountStatusChangeModel mcAccountStatusChangeModel = new McAccountStatusChangeModel();
                mcAccountStatusChangeModel.setMcAccountStatus(mCAccountStatusEnum);
                mcAccountStatusChangeModel.setDeleteAliLoginCookie("NO");
                try {
                    OperationResult startAction = InsideOperationService.getInstance().startAction((Context) AMapAppGlobal.getApplication(), (BaseModel<T>) mcAccountStatusChangeModel);
                    StringBuilder sb = new StringBuilder("notifyStatusChange, status: ");
                    sb.append(mCAccountStatusEnum);
                    sb.append(", result: ");
                    sb.append(startAction.toJsonString());
                    AMapLog.d("accountTAG", sb.toString());
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    public final String b() {
        String a2 = aod.a();
        String str = "2016011801101621";
        String str2 = "http://id.amap.com/index/alipay";
        if (TextUtils.equals(a2, "test")) {
            str = "2018120560078037";
            str2 = "http://120.55.138.220:80/plan-vision-pay/oauth/merchant_oauth_callback.html";
        } else if (TextUtils.equals(a2, "dev")) {
            str = "2016091801246878";
            str2 = "http://120.55.138.220:80/plan-vision-pay/oauth/merchant_oauth_callback.html";
        }
        HashMap hashMap = new HashMap();
        hashMap.put("app_id", str);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "auth_type", (Object) "MY_PASS_OAUTH");
        JSONArray jSONArray = new JSONArray();
        if (!TextUtils.isEmpty(this.n)) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put((String) "app_id", (Object) this.n);
            JSONArray jSONArray2 = new JSONArray();
            if (this.o != null) {
                jSONArray2.addAll(this.o);
            }
            jSONObject2.put((String) "scopes", (Object) jSONArray2);
            jSONObject2.put((String) "return_url", (Object) this.p);
            jSONArray.add(jSONObject2);
        }
        jSONObject.put((String) "combineOauth", (Object) jSONArray);
        jSONObject.put((String) "is_mobile", (Object) "true");
        JSONArray jSONArray3 = new JSONArray();
        if (this.q != null) {
            jSONArray3.addAll(this.q);
        }
        jSONObject.put((String) "scopes", (Object) jSONArray3);
        jSONObject.put((String) "state", (Object) UUID.randomUUID().toString().replace("-", ""));
        String str3 = this.d;
        if (TextUtils.isEmpty(str3)) {
            str3 = "AMAP";
        }
        jSONObject.put((String) "origin", (Object) str3);
        hashMap.put("biz_content", jSONObject.toJSONString());
        hashMap.put("charset", "UTF-8");
        hashMap.put("method", "alipay.user.info.auth");
        hashMap.put("return_url", str2);
        hashMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
        hashMap.put("version", "1.0");
        hashMap.put("sign_type", "RSA");
        String a3 = a((Map<String, String>) hashMap, false);
        String str4 = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMguYBEHR99slsyl\nV9xtPyDueJsro5yhRdIYBCE2OvQMfuA2b/nvZlrdUmuOcv+bXN3ujv5pZb9PB2jF\n700q3i5F/qqhiLYl0xtzejg2D8e445zoAHuDT2tL1kjNDmXC4dxFFGZZ+yMxzCnO\ny2Y/twryuGJF0n66c3chw+Oi4ejlAgMBAAECgYBT8KZV3aC0vlsJmzeZdbHoBDdM\nkeL8dd/KNkndB1l3Jpo5OHqB6nIYHgBGm6f7KNGrOjJ52gZRTzlDJOSwjg41yTWg\n26NmUHrlukrsHCv2ndoeJGBh6X9RZRkJxgGXWZ0NOt6badtRhoOoCe7DqTX94ZBQ\nAFkcYxP8p8n17IdoAQJBAOivgYsPErzX41M3O+QnSpzNp5jzfr3qG7pZdUdzlA4Y\n5f0oJpwe+kGTcPgcXiD3kpHX+HmlYDYQmU4tAjoPRuUCQQDcPR2eLmzZpC6tpYyp\nxYLI2qCw9hsTWvGSHfVH7FrKpRId0XR0Mf+6YEoLJo3AZ0xDpIcd31Pksimqk6HO\ny/oBAkEAj2847M7C3zQ5xp9ixPbPkK9ZY/idpVZ99zaUDBKcLsB8bbzlaBHUdL39\nwoRCJhJXAJ5gZiRilZFP35fxKncmXQJAIIQ1d0FLeOawrZqfpgEvShBdYUM0xCrN\nN9GMgU34KastfZGLLAylwRKuW+8ZRqr5q5MDD/oFHOLhG/ooDaw4AQJBAMtZD/dU\n7abQL5iSXzjQPVdNl5XxHSi4bGvPRycrbdrlV7IN0/J4aDih2ok/jexh+2SB8t82\nAhV3eKzeM+GQzM8=\n";
        String a4 = aod.a();
        if (TextUtils.equals(a4, "test")) {
            str4 = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKK0PXoLKnBkgtOl0kvyc9X2tUUdh/lRZr9RE1frjr2ZtAulZ+Moz9VJZFew1UZIzeK0478obY/DjHmD3GMfqJoTguVqJ2MEg+mJ8hJKWelvKLgfFBNliAw+/9O6Jah9Q3mRzCD8pABDEHY7BM54W7aLcuGpIIOa/qShO8dbXn+FAgMBAAECgYA8+nQ380taiDEIBZPFZv7G6AmT97doV3u8pDQttVjv8lUqMDm5RyhtdW4n91xXVR3ko4rfr9UwFkflmufUNp9HU9bHIVQS+HWLsPv9GypdTSNNp+nDn4JExUtAakJxZmGhCu/WjHIUzCoBCn6viernVC2L37NL1N4zrR73lSCk2QJBAPb/UOmtSx+PnA/mimqnFMMP3SX6cQmnynz9+63JlLjXD8rowRD2Z03U41Qfy+RED3yANZXCrE1V6vghYVmASYsCQQCoomZpeNxAKuUJZp+VaWi4WQeMW1KCK3aljaKLMZ57yb5Bsu+P3odyBk1AvYIPvdajAJiiikRdIDmi58dqfN0vAkEAjFX8LwjbCg+aaB5gvsA3t6ynxhBJcWb4UZQtD0zdRzhKLMuaBn05rKssjnuSaRuSgPaHe5OkOjx6yIiOuz98iQJAXIDpSMYhm5lsFiITPDScWzOLLnUR55HL/biaB1zqoODj2so7G2JoTiYiznamF9h9GuFC2TablbINq80U2NcxxQJBAMhw06Ha/U7qTjtAmr2qAuWSWvHU4ANu2h0RxYlKTpmWgO0f47jCOQhdC3T/RK7f38c7q8uPyi35eZ7S1e/PznY=";
        } else if (TextUtils.equals(a4, "dev")) {
            str4 = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCm95R12lb4Hu4Lk0sTJtdGxXJ63fgDo1bMztA80fkHprsp87G2WZ4Rv/kK0kFMzFdbxmWV8+Er2sQZ7SAVdmwhjW6EtlTJAzRT59FFXQ7ffVlQaunxx6p/6PnijOtGbilo96z/8n2R1gce0s09XHRbH9tvWraMkpAhUCHCyf+0ZYHIWZgFZfab3qog0Mcw6BAeTpDdql6hqEmVHsyBFy8yxcHoGAFI/94UB/xvqQiSMG2jAYxSu9CjOsh0/oyWFbEqcHgTxHX7pIRUrN0rf6zqtUsLQf+UaRKE0Uq3Zn19D+IRLOBlZ/vLRqb2eNyeYeAbVgMbW4eI+cuzkuNqISnxAgMBAAECggEAJV/aHZ9oRFY4FuM7tOfG3JKqE5LIR5gyf9nzhwnBYtMRpkxkhVr+JR8B0khKUbSUAXkhmDVlO/nWV69atTMy9TfBe3eM9wn+lqGXmJ1CDQj0CypDf9mf8s3l0a6Vo8hZAKQgV5KiRRjRszagtpGRgixZZE27+y97j9luFTo+QNTzQi4fRvPVc4RNDFKYcz4QWxNcIOEBi7xKMgSGktPGqcsZjvnGH157tv479Grx+TZxEIbat/SLRjD0S2NrLgDx+CWCkv/AMTJvoxCchUpLWwZi5QnLiOV9TXRqGn+RUF2ZbnV0uUKpG1cuMERHyqYFEpw8qK5tsXwCwlbqO4DpgQKBgQDb5eznaIXkQsd5moYX9DMtxTLzefC/zIcm5ugcc+ecB8xpwuUQgKW1lf2k8sdxZZoV65FWHq+hKV2wgBbMrOkUV9Im0nXK6YYBqOiZtCP3n07qwOEE5FFmKkO3DJ8iBwKtIbJ8kceotqZyNnDMqKApPUHvjqd5mMOW5fQyqtKeSQKBgQDCYQWZ8XTQOL3VLWeapfWiAV8Ox3zZoMfTGbtEUqr+iAZN0DEAd9wKl1g5fxB0uVYeouIJntK3eReCeBGfAlX4jw8SCkwic3uB6W7DT5dynDxJZ8nY5DeI17+ZD5Q6+CWtEHG2vUPvTFKy5H65qwlfC5ER9oohsaOd/TsSkWROaQKBgCUR1aYNEMUyHL57Ni/Dkv0cSUKSQ+uRZxc/xdFGGL1M80DBAiyOA2FhL6km5EhRgHBBjfaepazddFXUwgMvAvvS8jJpOEJEq7qL5upCW+3ahUs9yLEybCZ06YVqM0lhNSpKi/RD/wyJ/fUzCED4DEfnc74WplTxU8eUbF4+PdNBAoGAXh/LLZNdhGKlke/tplZMzokpdaelzmBrws5H/zqksKI/ozh4MgjYVYyZ3SWpW0xP5n/rQstUsCGD/9qSddQUu0rS+mJgIaKYIP1fdFY7OPVswALxHATO24XVspF3ruJwpBA9cEbP+bWUqim5L8EhxZN9SRyAIPa7CwsPqtsanlkCgYAqgcbPu0cvszFa0m62lTotw7qE8puBPPBuxM2EClwBbWiHxs7nFRH+c96+P/W2q8LK/AW1Wluus97YlSozL50uSkZAxtbWDSilZpXq9kYbK9zWS5Ngn+H6rpFfY5ANp0WCM7fw2JzwKvHGUxt59EmFLelTYwu/hxIY4uILWdIGGw==";
        }
        String a5 = ape.a(a3, str4);
        try {
            a5 = URLEncoder.encode(a5, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(a((Map<String, String>) hashMap, true));
        sb.append("&sign=");
        sb.append(a5);
        return sb.toString();
    }

    @Nullable
    public static String d() {
        ant ant;
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            ant = null;
        } else {
            ant = iAccountService.e();
        }
        if (ant == null || TextUtils.isEmpty(ant.h)) {
            return null;
        }
        return ant.h;
    }

    static String e() {
        ant ant;
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            ant = null;
        } else {
            ant = iAccountService.e();
        }
        if (ant != null) {
            return ant.u;
        }
        return null;
    }

    static /* synthetic */ void a(aok aok, aoh aoh) {
        String str = aoh.b;
        aos.a = str;
        int i = aok.f;
        if (i != 99) {
            switch (i) {
                case 0:
                    aox aox = aok.e;
                    LoginAlipayParam loginAlipayParam = new LoginAlipayParam();
                    loginAlipayParam.code = str;
                    loginAlipayParam.autoValue = 1;
                    loginAlipayParam.limit_login = aok.g;
                    if ("test".equals(ConfigerHelper.getInstance().getNetCondition())) {
                        String a2 = aod.a();
                        if (TextUtils.equals(a2, "test")) {
                            loginAlipayParam.env = "sit";
                        } else if (TextUtils.equals(a2, "dev")) {
                            loginAlipayParam.env = "dev";
                        }
                    }
                    loginAlipayParam.scope = aok.g();
                    LoginRequestHolder.getInstance().sendLoginAlipay(loginAlipayParam, aox);
                    apd.a();
                    return;
                case 1:
                case 2:
                    aok.a(str, aok.h, aok.i, aok.j, aok.e);
                    return;
            }
        } else {
            aok.a(str);
        }
    }
}
