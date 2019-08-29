package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.json.JSONObject;

/* renamed from: ark reason: default package */
/* compiled from: MainMapSyncManager */
public final class ark {
    public boolean a;
    public boolean b;
    private bid c;
    private MapManager d;
    private biy e = null;

    @SuppressFBWarnings({"URF_UNREAD_FIELD"})
    public ark(@NonNull bid bid) {
        this.c = bid;
        this.d = DoNotUseTool.getMapManager();
        this.e = new biy() {
            public final void updateSuccess() {
                String b = bim.aa().b((String) "201", (String) "602");
                if (!TextUtils.isEmpty(b)) {
                    try {
                        String optString = new JSONObject(b).optString("value");
                        if (!TextUtils.isEmpty(optString)) {
                            new arx().a().putStringValue("userIndividualityType", optString);
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            }
        };
        bim.aa().b(this.e);
    }

    static void a() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("cookie:");
            stringBuffer.append(abj.a().b());
            cjy.a(ALCLogLevel.P6, (String) AMapLog.GROUP_COMMON, (String) "D1", (String) "P0004", (String) ALCTtsConstant.EVENT_ID_TTS_PLAY_ERROR, stringBuffer.toString());
            AMapLog.logNormalNative(AMapLog.GROUP_BASEMAP, "P0004", ALCTtsConstant.EVENT_ID_TTS_PLAY_ERROR, stringBuffer.toString());
        } catch (Throwable unused) {
        }
    }

    public final void b() {
        if (bim.aa().l()) {
            bim.aa().j(false);
        }
        if (bim.aa().k() && bim.aa().h()) {
            bim.aa().a(DoNotUseTool.getContext().getResources().getString(R.string.sync_failagain_tip), (String) "androidamap://openFeature?featureName=Favorite&sourceApplication=tongbu", 171, (String) "1");
            bim.aa().h(false);
            bim.aa().e(false);
        }
        if (bim.aa().m() && bim.aa().h()) {
            bim.aa().a(DoNotUseTool.getContext().getResources().getString(R.string.sync_complete_tip), (String) null, 172, (String) "2");
            bim.aa().i(false);
            bim.aa().e(false);
        }
        if (bim.aa().w()) {
            bim.aa().a(DoNotUseTool.getContext().getResources().getString(R.string.sync_copyhistory_tip), (String) "amapuri://map/showmergedialog", 171, (String) "1");
        }
        if (bim.aa().i()) {
            brn brn = (brn) ank.a(brn.class);
            if (this.a) {
                if (brn != null) {
                    brn.d();
                }
            } else if (brn != null) {
                brn.e();
            }
            bim.aa().f(false);
            if (this.b) {
                boolean k = bim.aa().k((String) "103");
                awo awo = (awo) a.a.a(awo.class);
                if (awo != null) {
                    if (k) {
                        awo.e();
                    } else {
                        awo.f();
                    }
                }
            }
        }
        if (bim.aa().j()) {
            a();
            ToastHelper.showToast(DoNotUseTool.getContext().getResources().getString(R.string.sync_loginout_tip));
            bim.aa().g(false);
        }
        bim.aa().a((bis) new bis() {
            public final void a() {
                ark.a();
                ToastHelper.showToast(DoNotUseTool.getContext().getResources().getString(R.string.sync_loginout_tip));
                bim.aa().g(false);
            }
        });
        bim.aa().a((biu) new biu() {
            public final void a() {
                bim.aa().j(false);
            }
        });
        bim.aa().a((biy) new biy() {
            public final void updateSuccess() {
                if (bim.aa().h()) {
                    bim.aa().a(DoNotUseTool.getContext().getResources().getString(R.string.sync_complete_tip), (String) null, 172, (String) "2");
                    bim.aa().i(false);
                    bim.aa().e(false);
                }
            }
        });
        bim.aa().a((bit) new bit() {
            public final void showDialog() {
                bim.aa().a(DoNotUseTool.getContext().getResources().getString(R.string.sync_copyhistory_tip), (String) "amapuri://map/showmergedialog", 171, (String) "1");
            }
        });
        bim.aa().a((bix) new bix() {
            public final void a() {
                if (bim.aa().h()) {
                    bim.aa().a(DoNotUseTool.getContext().getResources().getString(R.string.sync_failagain_tip), (String) "androidamap://openFeature?featureName=Favorite&sourceApplication=tongbu", 171, (String) "1");
                    bim.aa().h(false);
                    bim.aa().e(false);
                }
            }
        });
        bim.aa().a((biw) new biw() {
            public final void a() {
                bim.aa().f(false);
                brn brn = (brn) ank.a(brn.class);
                if (ark.this.a) {
                    if (brn != null) {
                        brn.d();
                    }
                } else if (brn != null) {
                    brn.e();
                }
                if (ark.this.b) {
                    boolean k = bim.aa().k((String) "103");
                    awo awo = (awo) a.a.a(awo.class);
                    if (awo != null) {
                        if (k) {
                            awo.e();
                            return;
                        }
                        awo.f();
                    }
                }
            }
        });
    }

    public static void c() {
        bim.aa().a((biu) null);
        bim.aa().a((biy) null);
        bim.aa().a((bix) null);
        bim.aa().a((bit) null);
        bim.aa().a((bis) null);
        bim.aa().a((biw) null);
    }
}
