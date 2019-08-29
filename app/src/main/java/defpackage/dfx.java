package defpackage;

import android.text.TextUtils;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.minimap.drive.inter.impl.VoicePackageManagerImpl;
import com.autonavi.minimap.drive.navi.navitts.download.NaviTtsErrorType;
import com.autonavi.minimap.drive.navi.navitts_dependencies.DriveOfflineSDK;
import com.autonavi.minimap.offline.model.data.NaviTtsConstant;
import com.iflytek.tts.TtsService.TtsManager;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dfx reason: default package */
/* compiled from: UsingVoiceManager */
public final class dfx {
    private static dfx a;
    private String b;
    private String c;

    private dfx() {
    }

    public static synchronized dfx a() {
        dfx dfx;
        synchronized (dfx.class) {
            try {
                if (a == null) {
                    a = new dfx();
                }
                dfx = a;
            }
        }
        return dfx;
    }

    public final synchronized String b() {
        if (TextUtils.isEmpty(this.b)) {
            this.b = dfo.c();
            if (TextUtils.isEmpty(this.b)) {
                this.b = "linzhilingyuyin";
            }
        }
        return this.b;
    }

    private dgl e() {
        try {
            if (TextUtils.isEmpty(this.c)) {
                this.c = dfo.d();
            }
            String str = this.c;
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
            tw twVar = new tw();
            twVar.c = jSONObject.optString("name");
            twVar.l = jSONObject.optString("name2");
            twVar.f = jSONObject.optString("subname");
            twVar.n = jSONObject.optString("desc");
            twVar.o = jSONObject.optInt("type");
            twVar.g = (long) jSONObject.optInt("dataSize");
            twVar.k = jSONObject.optString("image");
            ua uaVar = new ua();
            uaVar.b = twVar.f;
            uaVar.f = jSONObject.optString("dataPath");
            return new dgl(twVar, uaVar);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final synchronized void a(String str, String str2) {
        this.b = str;
        this.c = str2;
        dfo.a(str);
        dfo.b(str2);
    }

    public final synchronized dgl c() {
        dgl dgl;
        dgl = null;
        try {
            String b2 = b();
            if (!TextUtils.isEmpty(b2)) {
                if (NaviTtsConstant.DEFAULT_VOICE_SUBNAME.equals(b2)) {
                    b2 = "nvzhongyin";
                }
                DriveOfflineSDK.e();
                dgl = DriveOfflineSDK.a(b2);
                if (dgl == null) {
                    dgl = e();
                }
            }
        }
        return dgl;
    }

    public static void a(dgl dgl, dgx dgx) {
        String str;
        dhf dhf = (dhf) ank.a(dhf.class);
        if (dhf != null) {
            dhf.d();
            dfm dfm = (dfm) ank.a(dfm.class);
            IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
            if (dfm != null && iVoicePackageManager != null) {
                VoicePackageManagerImpl voicePackageManagerImpl = (VoicePackageManagerImpl) iVoicePackageManager;
                if (dgl == null) {
                    dgx.a(false);
                } else {
                    voicePackageManagerImpl.a = false;
                    String str2 = dgl.a.l;
                    if (NaviTtsConstant.DONGBEIHUA_SUBNAME.equals(str2)) {
                        str = "东北那噶话设置成功";
                    } else if (NaviTtsConstant.HENANHUA_SUBNAME_1.equals(str2) || NaviTtsConstant.HENANHUA_SUBNAME_2.equals(str2)) {
                        str = "河兰话设置成功";
                    } else if (NaviTtsConstant.HUNANHUA_SUBNAME.equals(str2)) {
                        str = "弗南话设置成功";
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append("设置成功");
                        str = sb.toString();
                    }
                    ahl.a(new a(dgl, str, dgx) {
                        final /* synthetic */ dgl a;
                        final /* synthetic */ String b;
                        final /* synthetic */ dgx c;

                        {
                            this.a = r2;
                            this.b = r3;
                            this.c = r4;
                        }

                        public final Object doBackground() throws Exception {
                            synchronized (VoicePackageManagerImpl.b) {
                                ku a2 = ku.a();
                                StringBuilder sb = new StringBuilder("VoicePackageManagerImpl   setCurrentTtsFileBySubname   info.getDataPath():");
                                sb.append(this.a.a());
                                sb.append("     info.getName():");
                                sb.append(this.a.a.c);
                                a2.c("PlaySoundUtils", sb.toString());
                                boolean currentTtsFile = TtsManager.getInstance().setCurrentTtsFile(this.a.a(), VoicePackageManagerImpl.this.getPlayType(this.a.a.c));
                                ku.a().c("PlaySoundUtils", "VoicePackageManagerImpl   setCurrentTtsFileBySubname   setCurrentTtsFile success:".concat(String.valueOf(currentTtsFile)));
                                if (currentTtsFile) {
                                    dfx.a().a(this.a);
                                    if (VoicePackageManagerImpl.this.d != null) {
                                        TtsManager.getInstance().TTS_Txt(VoicePackageManagerImpl.this.d.c().getApplicationContext(), this.b);
                                        VoicePackageManagerImpl.this.d.a(this.b);
                                    }
                                } else {
                                    DriveOfflineSDK.e();
                                    if (DriveOfflineSDK.a(this.a)) {
                                        DriveOfflineSDK.e().a(this.a, NaviTtsErrorType.MD5_ERROR);
                                    }
                                    VoicePackageManagerImpl.b(VoicePackageManagerImpl.this);
                                    dfx.a().a((String) "linzhilingyuyin", (String) null);
                                }
                                if (this.c != null) {
                                    this.c.a(currentTtsFile);
                                }
                            }
                            return null;
                        }
                    });
                }
                dfm.b(iVoicePackageManager.getPlayType(dgl.a.c));
            }
        }
    }

    public static dgl d() {
        StringBuilder sb = new StringBuilder();
        sb.append(dgu.a().d());
        sb.append("/lzl.irf");
        String sb2 = sb.toString();
        if (TextUtils.isEmpty(sb2)) {
            return null;
        }
        File file = new File(sb2);
        if (!file.exists()) {
            return null;
        }
        tw b2 = dgm.a().b((String) "linzhilingyuyin");
        ua uaVar = new ua();
        uaVar.b = "linzhilingyuyin";
        uaVar.c = 4;
        uaVar.d = file.length();
        uaVar.e = file.length();
        uaVar.f = "/lzl.irf";
        dgl dgl = new dgl(b2, uaVar);
        dgl.a(4);
        return dgl;
    }

    public final void a(dgl dgl) {
        if (dgl != null) {
            try {
                a(dgl.a.f, a.a(dgl));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
