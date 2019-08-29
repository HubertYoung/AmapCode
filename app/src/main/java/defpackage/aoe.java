package defpackage;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.ali.auth.third.core.model.Constants;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.db.helper.UserInfoDataHelper;
import com.autonavi.map.db.model.UserInfo;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.account.base.model.AccountProfile;
import com.autonavi.minimap.account.base.model.AccountProfileCarLogo;
import com.autonavi.minimap.account.base.model.AccountProfileProvider;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* renamed from: aoe reason: default package */
/* compiled from: AccountDataStore */
public class aoe {
    private static volatile aoe d;
    Pattern a;
    /* access modifiers changed from: private */
    public volatile File b;
    /* access modifiers changed from: private */
    public volatile ant c;

    private aoe() {
        this.a = Pattern.compile("\\d+");
        this.b = null;
        this.b = new File(FileUtil.getFilesDir(), "UserInfo875");
        if (!this.b.exists()) {
            if (f()) {
                File file = new File(FileUtil.getFilesDir(), "UserInfo");
                if (file.exists()) {
                    file.delete();
                }
                return;
            }
            g();
        } else if (!h()) {
            g();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0075, code lost:
        if (r2 != null) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0077, code lost:
        defpackage.ahe.a((java.io.Closeable) r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0083, code lost:
        if (r2 != null) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0086, code lost:
        r5.c = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0089, code lost:
        return false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x008d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean f() {
        /*
            r5 = this;
            java.io.File r0 = new java.io.File
            java.io.File r1 = com.amap.bundle.blutils.FileUtil.getFilesDir()
            java.lang.String r2 = "UserInfo"
            r0.<init>(r1, r2)
            r1 = 0
            boolean r2 = r0.exists()     // Catch:{ Exception -> 0x007e, all -> 0x007b }
            if (r2 == 0) goto L_0x0074
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x007e, all -> 0x007b }
            r2.<init>(r0)     // Catch:{ Exception -> 0x007e, all -> 0x007b }
            byte[] r0 = defpackage.ahe.a(r2)     // Catch:{ Exception -> 0x0072 }
            if (r0 == 0) goto L_0x0075
            int r3 = r0.length     // Catch:{ Exception -> 0x0072 }
            if (r3 <= 0) goto L_0x0075
            java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x0072 }
            java.lang.String r4 = "utf-8"
            r3.<init>(r0, r4)     // Catch:{ Exception -> 0x0072 }
            java.lang.String r0 = com.autonavi.server.aos.serverkey.amapDecodeV2(r3)     // Catch:{ Exception -> 0x0072 }
            ant r0 = defpackage.apg.a(r0)     // Catch:{ Exception -> 0x0072 }
            r5.c = r0     // Catch:{ Exception -> 0x0072 }
            ant r0 = r5.c     // Catch:{ Exception -> 0x0072 }
            if (r0 == 0) goto L_0x0068
            ant r0 = r5.c     // Catch:{ Exception -> 0x0072 }
            java.lang.String r0 = r0.e     // Catch:{ Exception -> 0x0072 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0072 }
            if (r0 == 0) goto L_0x0068
            com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ Exception -> 0x0072 }
            java.lang.String r3 = "spNameAccount"
            r0.<init>(r3)     // Catch:{ Exception -> 0x0072 }
            java.lang.String r3 = "userNick"
            java.lang.String r4 = ""
            java.lang.String r3 = r0.getStringValue(r3, r4)     // Catch:{ Exception -> 0x0072 }
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x0072 }
            if (r4 != 0) goto L_0x0062
            ant r4 = r5.c     // Catch:{ Exception -> 0x0072 }
            java.lang.String r3 = com.autonavi.server.aos.serverkey.amapDecodeV2(r3)     // Catch:{ Exception -> 0x0072 }
            r4.e = r3     // Catch:{ Exception -> 0x0072 }
            java.lang.String r3 = "userNick"
            r0.remove(r3)     // Catch:{ Exception -> 0x0072 }
            goto L_0x0068
        L_0x0062:
            ant r0 = r5.c     // Catch:{ Exception -> 0x0072 }
            java.lang.String r3 = ""
            r0.e = r3     // Catch:{ Exception -> 0x0072 }
        L_0x0068:
            ant r0 = r5.c     // Catch:{ Exception -> 0x0072 }
            r5.a(r0)     // Catch:{ Exception -> 0x0072 }
            defpackage.ahe.a(r2)
            r0 = 1
            return r0
        L_0x0072:
            r0 = move-exception
            goto L_0x0080
        L_0x0074:
            r2 = r1
        L_0x0075:
            if (r2 == 0) goto L_0x0086
        L_0x0077:
            defpackage.ahe.a(r2)
            goto L_0x0086
        L_0x007b:
            r0 = move-exception
            r2 = r1
            goto L_0x008b
        L_0x007e:
            r0 = move-exception
            r2 = r1
        L_0x0080:
            r0.printStackTrace()     // Catch:{ all -> 0x008a }
            if (r2 == 0) goto L_0x0086
            goto L_0x0077
        L_0x0086:
            r5.c = r1
            r0 = 0
            return r0
        L_0x008a:
            r0 = move-exception
        L_0x008b:
            if (r2 == 0) goto L_0x0090
            defpackage.ahe.a(r2)
        L_0x0090:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aoe.f():boolean");
    }

    private void g() {
        UserInfo a2 = apf.a(UserInfoDataHelper.getInstance().getCurrentUserInfo());
        if (a2 != null) {
            this.c = a(a2);
        }
        ahn.b().execute(new Runnable() {
            /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a9, code lost:
                if (r1 != null) goto L_0x00ab;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
                defpackage.ahe.a((java.io.Closeable) r1);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b5, code lost:
                if (r1 != null) goto L_0x00ab;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:47:0x00c0, code lost:
                r1 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:51:0x00c4, code lost:
                throw r1;
             */
            /* JADX WARNING: Removed duplicated region for block: B:46:0x00bc A[Catch:{ all -> 0x00c0 }] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void run() {
                /*
                    r7 = this;
                    aoe r0 = defpackage.aoe.this
                    monitor-enter(r0)
                    r1 = 0
                    aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00b1 }
                    java.io.File r2 = r2.b     // Catch:{ Exception -> 0x00b1 }
                    if (r2 == 0) goto L_0x00a9
                    aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00b1 }
                    java.io.File r2 = r2.b     // Catch:{ Exception -> 0x00b1 }
                    boolean r2 = r2.exists()     // Catch:{ Exception -> 0x00b1 }
                    if (r2 == 0) goto L_0x0031
                    aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00b1 }
                    java.io.File r2 = r2.b     // Catch:{ Exception -> 0x00b1 }
                    long r2 = r2.length()     // Catch:{ Exception -> 0x00b1 }
                    r4 = 0
                    int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                    if (r2 <= 0) goto L_0x0031
                    aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00b1 }
                    java.io.File r2 = r2.b     // Catch:{ Exception -> 0x00b1 }
                    r2.delete()     // Catch:{ Exception -> 0x00b1 }
                L_0x0031:
                    aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00b1 }
                    java.io.File r2 = r2.b     // Catch:{ Exception -> 0x00b1 }
                    r2.createNewFile()     // Catch:{ Exception -> 0x00b1 }
                    aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00b1 }
                    ant r2 = r2.c     // Catch:{ Exception -> 0x00b1 }
                    java.lang.String r2 = defpackage.apg.b(r2)     // Catch:{ Exception -> 0x00b1 }
                    boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x00b1 }
                    if (r3 != 0) goto L_0x007c
                    java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x00b1 }
                    byte[] r2 = r2.getBytes()     // Catch:{ Exception -> 0x00b1 }
                    r4 = 0
                    byte[] r2 = android.util.Base64.encode(r2, r4)     // Catch:{ Exception -> 0x00b1 }
                    r3.<init>(r2)     // Catch:{ Exception -> 0x00b1 }
                    java.lang.String r2 = com.autonavi.server.aos.serverkey.amapEncodeV2(r3)     // Catch:{ Exception -> 0x00b1 }
                    java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00b1 }
                    aoe r4 = defpackage.aoe.this     // Catch:{ Exception -> 0x00b1 }
                    java.io.File r4 = r4.b     // Catch:{ Exception -> 0x00b1 }
                    r3.<init>(r4)     // Catch:{ Exception -> 0x00b1 }
                    java.lang.String r1 = "utf-8"
                    byte[] r1 = r2.getBytes(r1)     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
                    r3.write(r1)     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
                    r3.flush()     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
                    r1 = r3
                    goto L_0x0097
                L_0x0076:
                    r2 = move-exception
                    r1 = r3
                    goto L_0x00ba
                L_0x0079:
                    r2 = move-exception
                    r1 = r3
                    goto L_0x00b2
                L_0x007c:
                    java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00b1 }
                    aoe r3 = defpackage.aoe.this     // Catch:{ Exception -> 0x00b1 }
                    java.io.File r3 = r3.b     // Catch:{ Exception -> 0x00b1 }
                    r2.<init>(r3)     // Catch:{ Exception -> 0x00b1 }
                    java.lang.String r1 = "DB_NO_DATA"
                    java.lang.String r3 = "utf-8"
                    byte[] r1 = r1.getBytes(r3)     // Catch:{ Exception -> 0x00a4, all -> 0x009f }
                    r2.write(r1)     // Catch:{ Exception -> 0x00a4, all -> 0x009f }
                    r2.flush()     // Catch:{ Exception -> 0x00a4, all -> 0x009f }
                    r1 = r2
                L_0x0097:
                    com.autonavi.map.db.helper.UserInfoDataHelper r2 = com.autonavi.map.db.helper.UserInfoDataHelper.getInstance()     // Catch:{ Exception -> 0x00b1 }
                    r2.clear()     // Catch:{ Exception -> 0x00b1 }
                    goto L_0x00a9
                L_0x009f:
                    r1 = move-exception
                    r6 = r2
                    r2 = r1
                    r1 = r6
                    goto L_0x00ba
                L_0x00a4:
                    r1 = move-exception
                    r6 = r2
                    r2 = r1
                    r1 = r6
                    goto L_0x00b2
                L_0x00a9:
                    if (r1 == 0) goto L_0x00b8
                L_0x00ab:
                    defpackage.ahe.a(r1)     // Catch:{ all -> 0x00c0 }
                    goto L_0x00b8
                L_0x00af:
                    r2 = move-exception
                    goto L_0x00ba
                L_0x00b1:
                    r2 = move-exception
                L_0x00b2:
                    r2.printStackTrace()     // Catch:{ all -> 0x00af }
                    if (r1 == 0) goto L_0x00b8
                    goto L_0x00ab
                L_0x00b8:
                    monitor-exit(r0)     // Catch:{ all -> 0x00c0 }
                    return
                L_0x00ba:
                    if (r1 == 0) goto L_0x00c2
                    defpackage.ahe.a(r1)     // Catch:{ all -> 0x00c0 }
                    goto L_0x00c2
                L_0x00c0:
                    r1 = move-exception
                    goto L_0x00c3
                L_0x00c2:
                    throw r2     // Catch:{ all -> 0x00c0 }
                L_0x00c3:
                    monitor-exit(r0)     // Catch:{ all -> 0x00c0 }
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: defpackage.aoe.AnonymousClass1.run():void");
            }
        });
    }

    public static aoe a() {
        if (d == null) {
            synchronized (aoe.class) {
                try {
                    if (d == null) {
                        d = new aoe();
                    }
                }
            }
        }
        return d;
    }

    private static ant a(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        ant ant = new ant();
        ant.a = userInfo.uid;
        ant.b = userInfo.avatar;
        ant.c = userInfo.username;
        ant.d = userInfo.birthday;
        ant.e = userInfo.nick;
        ant.f = userInfo.sex == null ? "" : userInfo.sex.toString();
        ant.g = userInfo.email;
        ant.h = userInfo.bindingmobile;
        ant.i = userInfo.sinaname;
        ant.m = userInfo.taobaoid;
        ant.l = userInfo.taobaoname;
        ant.k = userInfo.toptoken;
        ant.o = userInfo.qqid;
        ant.n = userInfo.qqname;
        ant.q = userInfo.wxid;
        ant.p = userInfo.wxname;
        ant.t = userInfo.alipayid;
        ant.s = userInfo.alipayname;
        String a2 = apa.a(userInfo.alipaytoken);
        if (a2 != null) {
            ant.r.put(a2, userInfo.alipaytoken);
        }
        ant.v = userInfo.source;
        if (userInfo.repwd == null || !userInfo.repwd.booleanValue()) {
            ant.w = "0";
        } else {
            ant.w = "1";
        }
        ant.x = userInfo.logoid;
        return ant;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0046, code lost:
        if (r2 != null) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        defpackage.ahe.a((java.io.Closeable) r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0054, code lost:
        if (r2 != null) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r6.c = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x005a, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0062, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0066, code lost:
        throw r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x005e A[SYNTHETIC, Splitter:B:37:0x005e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean h() {
        /*
            r6 = this;
            monitor-enter(r6)
            r0 = 0
            r1 = 0
            java.io.File r2 = r6.b     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            if (r2 == 0) goto L_0x0045
            java.io.File r2 = r6.b     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            boolean r2 = r2.exists()     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            if (r2 == 0) goto L_0x0045
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            java.io.File r3 = r6.b     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            r2.<init>(r3)     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            byte[] r3 = defpackage.ahe.a(r2)     // Catch:{ Exception -> 0x0043 }
            if (r3 == 0) goto L_0x0046
            int r4 = r3.length     // Catch:{ Exception -> 0x0043 }
            if (r4 <= 0) goto L_0x0046
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x0043 }
            java.lang.String r5 = "utf-8"
            r4.<init>(r3, r5)     // Catch:{ Exception -> 0x0043 }
            java.lang.String r3 = com.autonavi.server.aos.serverkey.amapDecodeV2(r4)     // Catch:{ Exception -> 0x0043 }
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x0043 }
            byte[] r3 = r3.getBytes()     // Catch:{ Exception -> 0x0043 }
            byte[] r3 = android.util.Base64.decode(r3, r0)     // Catch:{ Exception -> 0x0043 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x0043 }
            ant r3 = defpackage.apg.a(r4)     // Catch:{ Exception -> 0x0043 }
            r6.c = r3     // Catch:{ Exception -> 0x0043 }
            defpackage.ahe.a(r2)     // Catch:{ all -> 0x0062 }
            r0 = 1
            monitor-exit(r6)
            return r0
        L_0x0043:
            r3 = move-exception
            goto L_0x0051
        L_0x0045:
            r2 = r1
        L_0x0046:
            if (r2 == 0) goto L_0x0057
        L_0x0048:
            defpackage.ahe.a(r2)     // Catch:{ all -> 0x0062 }
            goto L_0x0057
        L_0x004c:
            r0 = move-exception
            r2 = r1
            goto L_0x005c
        L_0x004f:
            r3 = move-exception
            r2 = r1
        L_0x0051:
            r3.printStackTrace()     // Catch:{ all -> 0x005b }
            if (r2 == 0) goto L_0x0057
            goto L_0x0048
        L_0x0057:
            r6.c = r1     // Catch:{ all -> 0x0062 }
            monitor-exit(r6)
            return r0
        L_0x005b:
            r0 = move-exception
        L_0x005c:
            if (r2 == 0) goto L_0x0064
            defpackage.ahe.a(r2)     // Catch:{ all -> 0x0062 }
            goto L_0x0064
        L_0x0062:
            r0 = move-exception
            goto L_0x0065
        L_0x0064:
            throw r0     // Catch:{ all -> 0x0062 }
        L_0x0065:
            monitor-exit(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aoe.h():boolean");
    }

    @Nullable
    public final synchronized ant b() {
        try {
            if (this.c == null) {
                return null;
            }
            return new ant(this.c);
        }
    }

    public final synchronized void c() {
        d();
        try {
            JSONObject jSONObject = new JSONObject();
            String b2 = abj.a(AMapPageUtil.getAppContext()).b();
            String printStackTraceString = DebugLog.getPrintStackTraceString(new Throwable());
            jSONObject.put(Constants.COOKIES, b2);
            jSONObject.put("stack", printStackTraceString);
            cjy.a(ALCLogLevel.P4, (String) AMapLog.GROUP_BASEMAP, (String) "D1", (String) "P0014", (String) ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, jSONObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        abj a2 = abj.a(AMapPageUtil.getAppContext());
        boolean z = false;
        Editor edit = a2.b.edit();
        if (a2.a.containsKey("sessionid")) {
            AMapLog.d("CookieStore", "remove cookie: ".concat(String.valueOf("sessionid")));
            a2.a.remove("sessionid");
            edit.remove("cookie_".concat(String.valueOf("sessionid")));
            z = true;
        }
        if (z) {
            edit.putString("names", TextUtils.join(",", a2.a.keySet()));
            if (VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
        ajp.a((Context) AMapAppGlobal.getApplication());
        try {
            if (this.b != null && this.b.length() > 0) {
                this.b.delete();
                this.b.createNewFile();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.c = null;
        UserInfoDataHelper.getInstance().clear();
    }

    public final synchronized boolean d() {
        String str;
        str = "";
        try {
            if (this.c != null) {
                str = this.c.a;
            }
        }
        return !TextUtils.isEmpty(str);
    }

    public final synchronized String e() {
        String str;
        str = "";
        if (this.c != null) {
            str = this.c.a;
        }
        return str;
    }

    public final synchronized void a(chm chm, int i) {
        if (this.c == null || i == aox.f) {
            this.c = new ant();
        }
        if (!TextUtils.isEmpty(chm.c) && !"-1".equals(chm.c)) {
            this.c.w = chm.c;
        }
        this.c.A = chm.i;
        this.c.B = chm.j;
        AccountProfile accountProfile = chm.d;
        ant ant = this.c;
        if (!TextUtils.isEmpty(accountProfile.uid)) {
            ant.a = accountProfile.uid;
        }
        if (!TextUtils.isEmpty(accountProfile.username)) {
            ant.c = accountProfile.username;
        }
        if (!TextUtils.isEmpty(accountProfile.nickname)) {
            ant.e = accountProfile.nickname;
        }
        if (!TextUtils.isEmpty(accountProfile.birthday)) {
            ant.d = accountProfile.birthday;
        }
        String str = accountProfile.avatar;
        if (!TextUtils.isEmpty(str)) {
            ant.b = str;
        }
        if (!TextUtils.isEmpty(accountProfile.email)) {
            ant.g = accountProfile.email;
        }
        if (!TextUtils.isEmpty(accountProfile.mobile)) {
            ant.h = accountProfile.mobile;
        }
        if (accountProfile.gender != 0) {
            ant.f = String.valueOf(accountProfile.gender);
        }
        if (!TextUtils.isEmpty(accountProfile.source)) {
            ant.v = accountProfile.source;
        }
        if (accountProfile.providers != null) {
            ant.p = null;
            ant.q = null;
            ant.n = null;
            ant.o = null;
            ant.i = null;
            ant.j = null;
            ant.l = null;
            ant.m = null;
            ant.s = null;
            ant.t = null;
            ant.r.clear();
            ArrayList<AccountProfileProvider> arrayList = accountProfile.providers;
            if (arrayList.size() > 0) {
                for (AccountProfileProvider next : arrayList) {
                    switch (next.provider) {
                        case 1:
                            if (!TextUtils.isEmpty(next.authUsername)) {
                                ant.i = next.authUsername;
                            }
                            if (TextUtils.isEmpty(next.authId)) {
                                break;
                            } else {
                                ant.j = next.authId;
                                break;
                            }
                        case 2:
                            if (!TextUtils.isEmpty(next.authUsername)) {
                                ant.l = next.authUsername;
                            }
                            if (!TextUtils.isEmpty(next.authId)) {
                                String str2 = next.authId;
                                ant.m = String.valueOf(Long.valueOf(this.a.matcher(str2).matches() ? Long.parseLong(str2) : 0));
                            } else {
                                ant.m = "";
                            }
                            if (TextUtils.isEmpty(next.authToken)) {
                                break;
                            } else {
                                ant.k = next.authToken;
                                break;
                            }
                        case 6:
                            if (!TextUtils.isEmpty(next.authId)) {
                                ant.o = next.authId;
                            }
                            if (TextUtils.isEmpty(next.authUsername)) {
                                break;
                            } else {
                                ant.n = next.authUsername;
                                break;
                            }
                        case 8:
                            if (!TextUtils.isEmpty(next.authId)) {
                                ant.q = next.authId;
                            }
                            if (TextUtils.isEmpty(next.authUsername)) {
                                break;
                            } else {
                                ant.p = next.authUsername;
                                break;
                            }
                        case 9:
                            if (!TextUtils.isEmpty(next.authId)) {
                                ant.t = next.authId;
                            }
                            if (!TextUtils.isEmpty(next.authUsername)) {
                                ant.s = next.authUsername;
                            }
                            if (!next.alipayTokenMap.isEmpty()) {
                                ant.r.putAll(next.alipayTokenMap);
                            }
                            if (TextUtils.isEmpty(next.aUserId)) {
                                break;
                            } else {
                                ant.u = next.aUserId;
                                break;
                            }
                    }
                }
            }
        }
        if (accountProfile.memberLevel > 0) {
            ant.y = String.valueOf(accountProfile.memberLevel);
        }
        ant.z = String.valueOf(accountProfile.checkinCount);
        AccountProfileCarLogo accountProfileCarLogo = accountProfile.carLogo;
        if (!(accountProfileCarLogo == null || accountProfileCarLogo.id == 0)) {
            ant.x = String.valueOf(accountProfileCarLogo.id);
        }
        if (accountProfile.carLoginFlag != -1) {
            ant.C = String.valueOf(accountProfile.carLoginFlag);
        }
        a(this.c);
    }

    public final synchronized void a(ant ant) {
        if (ant != null) {
            a.a.a(d(), !TextUtils.isEmpty(ant.a));
            this.c = ant;
            ahm.a(new Runnable() {
                /* JADX WARNING: Code restructure failed: missing block: B:28:0x00bd, code lost:
                    if (r1 != null) goto L_0x00bf;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
                    defpackage.ahe.a((java.io.Closeable) r1);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c9, code lost:
                    if (r1 != null) goto L_0x00bf;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d4, code lost:
                    r1 = move-exception;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d8, code lost:
                    throw r1;
                 */
                /* JADX WARNING: Removed duplicated region for block: B:40:0x00d0 A[Catch:{ all -> 0x00d4 }] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void run() {
                    /*
                        r7 = this;
                        aoe r0 = defpackage.aoe.this
                        monitor-enter(r0)
                        r1 = 0
                        aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00c5 }
                        java.io.File r2 = r2.b     // Catch:{ Exception -> 0x00c5 }
                        if (r2 == 0) goto L_0x00bd
                        aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00c5 }
                        java.io.File r2 = r2.b     // Catch:{ Exception -> 0x00c5 }
                        boolean r2 = r2.exists()     // Catch:{ Exception -> 0x00c5 }
                        if (r2 == 0) goto L_0x0031
                        aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00c5 }
                        java.io.File r2 = r2.b     // Catch:{ Exception -> 0x00c5 }
                        long r2 = r2.length()     // Catch:{ Exception -> 0x00c5 }
                        r4 = 0
                        int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                        if (r2 <= 0) goto L_0x0031
                        aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00c5 }
                        java.io.File r2 = r2.b     // Catch:{ Exception -> 0x00c5 }
                        r2.delete()     // Catch:{ Exception -> 0x00c5 }
                    L_0x0031:
                        aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00c5 }
                        java.io.File r2 = r2.b     // Catch:{ Exception -> 0x00c5 }
                        r2.createNewFile()     // Catch:{ Exception -> 0x00c5 }
                        aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00c5 }
                        ant r2 = r2.c     // Catch:{ Exception -> 0x00c5 }
                        java.lang.String r2 = defpackage.apg.b(r2)     // Catch:{ Exception -> 0x00c5 }
                        boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x00c5 }
                        if (r3 != 0) goto L_0x007b
                        java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x00c5 }
                        byte[] r2 = r2.getBytes()     // Catch:{ Exception -> 0x00c5 }
                        r4 = 0
                        byte[] r2 = android.util.Base64.encode(r2, r4)     // Catch:{ Exception -> 0x00c5 }
                        r3.<init>(r2)     // Catch:{ Exception -> 0x00c5 }
                        java.lang.String r2 = com.autonavi.server.aos.serverkey.amapEncodeV2(r3)     // Catch:{ Exception -> 0x00c5 }
                        java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00c5 }
                        aoe r4 = defpackage.aoe.this     // Catch:{ Exception -> 0x00c5 }
                        java.io.File r4 = r4.b     // Catch:{ Exception -> 0x00c5 }
                        r3.<init>(r4)     // Catch:{ Exception -> 0x00c5 }
                        java.lang.String r1 = "utf-8"
                        byte[] r1 = r2.getBytes(r1)     // Catch:{ Exception -> 0x0078, all -> 0x0075 }
                        r3.write(r1)     // Catch:{ Exception -> 0x0078, all -> 0x0075 }
                        r3.flush()     // Catch:{ Exception -> 0x0078, all -> 0x0075 }
                        r1 = r3
                        goto L_0x007b
                    L_0x0075:
                        r2 = move-exception
                        r1 = r3
                        goto L_0x00ce
                    L_0x0078:
                        r2 = move-exception
                        r1 = r3
                        goto L_0x00c6
                    L_0x007b:
                        aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00c5 }
                        ant r2 = r2.c     // Catch:{ Exception -> 0x00c5 }
                        if (r2 == 0) goto L_0x00bd
                        aoe r2 = defpackage.aoe.this     // Catch:{ Exception -> 0x00c5 }
                        ant r2 = r2.c     // Catch:{ Exception -> 0x00c5 }
                        java.lang.String r2 = r2.C     // Catch:{ Exception -> 0x00c5 }
                        boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x00c5 }
                        if (r2 != 0) goto L_0x00bd
                        com.amap.bundle.mapstorage.MapSharePreference r2 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ Exception -> 0x00c5 }
                        com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r3 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ Exception -> 0x00c5 }
                        r2.<init>(r3)     // Catch:{ Exception -> 0x00c5 }
                        android.content.SharedPreferences$Editor r3 = r2.edit()     // Catch:{ Exception -> 0x00c5 }
                        java.lang.String r4 = "car_login_flag"
                        aoe r5 = defpackage.aoe.this     // Catch:{ Exception -> 0x00c5 }
                        ant r5 = r5.c     // Catch:{ Exception -> 0x00c5 }
                        java.lang.String r5 = r5.C     // Catch:{ Exception -> 0x00c5 }
                        int r5 = java.lang.Integer.parseInt(r5)     // Catch:{ Exception -> 0x00c5 }
                        r3.putInt(r4, r5)     // Catch:{ Exception -> 0x00c5 }
                        java.lang.String r4 = "car_login_update_flag"
                        java.lang.String r5 = "car_login_update_flag"
                        r6 = 1
                        boolean r2 = r2.getBooleanValue(r5, r6)     // Catch:{ Exception -> 0x00c5 }
                        r2 = r2 ^ r6
                        r3.putBoolean(r4, r2)     // Catch:{ Exception -> 0x00c5 }
                        r3.commit()     // Catch:{ Exception -> 0x00c5 }
                    L_0x00bd:
                        if (r1 == 0) goto L_0x00cc
                    L_0x00bf:
                        defpackage.ahe.a(r1)     // Catch:{ all -> 0x00d4 }
                        goto L_0x00cc
                    L_0x00c3:
                        r2 = move-exception
                        goto L_0x00ce
                    L_0x00c5:
                        r2 = move-exception
                    L_0x00c6:
                        r2.printStackTrace()     // Catch:{ all -> 0x00c3 }
                        if (r1 == 0) goto L_0x00cc
                        goto L_0x00bf
                    L_0x00cc:
                        monitor-exit(r0)     // Catch:{ all -> 0x00d4 }
                        return
                    L_0x00ce:
                        if (r1 == 0) goto L_0x00d6
                        defpackage.ahe.a(r1)     // Catch:{ all -> 0x00d4 }
                        goto L_0x00d6
                    L_0x00d4:
                        r1 = move-exception
                        goto L_0x00d7
                    L_0x00d6:
                        throw r2     // Catch:{ all -> 0x00d4 }
                    L_0x00d7:
                        monitor-exit(r0)     // Catch:{ all -> 0x00d4 }
                        throw r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: defpackage.aoe.AnonymousClass2.run():void");
                }
            });
        }
    }
}
