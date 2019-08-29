package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;

/* renamed from: ve reason: default package */
/* compiled from: EyrieSoundManager */
public final class ve {
    public afx a = ((afx) defpackage.esb.a.a.a(afx.class));

    /* renamed from: ve$a */
    /* compiled from: EyrieSoundManager */
    public static class a {
        /* access modifiers changed from: private */
        public static ve a = new ve();
    }

    public static void a(int i) {
        int i2;
        a("   ITTSPlayer   playRing  type:".concat(String.valueOf(i)));
        switch (i) {
            case 1:
                i2 = R.raw.autoreroute;
                break;
            case 2:
                i2 = R.raw.navi_traffic_event;
                break;
            default:
                switch (i) {
                    case 100:
                        i2 = R.raw.navi_warning;
                        break;
                    case 101:
                        i2 = R.raw.camera;
                        break;
                    case 102:
                        i2 = R.raw.edog_dingdong;
                        break;
                    case 103:
                        i2 = R.raw.speedover;
                        break;
                    default:
                        i2 = 0;
                        break;
                }
        }
        if (i2 != 0) {
            PlaySoundUtils.getInstance().playNaviWarningSound(AMapAppGlobal.getApplication(), i2);
            return;
        }
        String concat = "playRing type ".concat(String.valueOf(i));
        AMapLog.d("EyrieSoundManager", concat);
        ku.a().c("EyrieSoundManager", concat);
    }

    public final void a(String str, int i, int i2) {
        StringBuilder sb = new StringBuilder("playTTS      type:");
        sb.append(i);
        sb.append("    ");
        sb.append(str);
        a(sb.toString());
        int i3 = 6;
        boolean z = false;
        if (i != 6) {
            if (i != 99) {
                switch (i) {
                    case 3:
                        i3 = 4;
                        break;
                    case 4:
                        i3 = 5;
                        break;
                    default:
                        switch (i) {
                            case 9:
                                i3 = 0;
                                break;
                            case 10:
                                i3 = 2;
                                break;
                            case 11:
                                i3 = 3;
                                break;
                            case 12:
                                i3 = 7;
                                break;
                            default:
                                i3 = -1;
                                break;
                        }
                }
            } else {
                i3 = 1;
            }
            z = true;
        }
        StringBuilder sb2 = new StringBuilder("playTTS isPlayTBTSound:");
        sb2.append(z);
        sb2.append(", transformedType:");
        sb2.append(i3);
        sb2.append(", isUsingCustomSound ");
        sb2.append(a());
        a(sb2.toString());
        if (a() && i3 != -1) {
            String b = b(i3);
            a("playTTS      spxFileName:".concat(String.valueOf(b)));
            if (!TextUtils.isEmpty(b)) {
                PlaySoundUtils.getInstance().playSound(i2, PlaySoundUtils.FILE_PLAY_PREFIX.concat(String.valueOf(b)));
                return;
            }
            z = true;
        }
        if (z) {
            PlaySoundUtils.getInstance().playSound(i2, str);
        }
    }

    private String b(int i) {
        if (this.a == null) {
            return "";
        }
        return this.a.a(i);
    }

    static void a(String str) {
        if (bno.a) {
            AMapLog.debug(ALCTtsConstant.GROUP_NAME, "android", "EyrieSoundManager   ".concat(String.valueOf(str)));
        }
    }

    public final boolean a() {
        return this.a != null && this.a.a();
    }
}
