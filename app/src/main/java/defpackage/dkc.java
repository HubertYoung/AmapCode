package defpackage;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import java.util.HashMap;
import java.util.Map;

/* renamed from: dkc reason: default package */
/* compiled from: VoiceUtil */
public final class dkc {
    public static final Map<String, Integer> a;
    public static final dka[] b = {new dka(AMapAppGlobal.getApplication().getString(R.string.nv_caption_nvstart), AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_nvstart), AMapAppGlobal.getApplication().getString(R.string.nv_guide_string_nvstart)), new dka(AMapAppGlobal.getApplication().getString(R.string.nv_caption_nvdone_), AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_nvdone_), AMapAppGlobal.getApplication().getString(R.string.nv_guide_string_nvdone_)), new dka(AMapAppGlobal.getApplication().getString(R.string.nv_caption_rdcmera), AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_rdcmera), AMapAppGlobal.getApplication().getString(R.string.nv_guide_string_rdcmera)), new dka(AMapAppGlobal.getApplication().getString(R.string.nv_caption_svczone), AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_svczone), AMapAppGlobal.getApplication().getString(R.string.nv_guide_string_svczone)), new dka(AMapAppGlobal.getApplication().getString(R.string.nv_caption_zigzag_), AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_zigzag_), AMapAppGlobal.getApplication().getString(R.string.nv_guide_string_zigzag_)), new dka(AMapAppGlobal.getApplication().getString(R.string.nv_caption_acprone), AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_acprone), AMapAppGlobal.getApplication().getString(R.string.nv_guide_string_acprone)), new dka(AMapAppGlobal.getApplication().getString(R.string.nv_caption_railway), AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_railway), AMapAppGlobal.getApplication().getString(R.string.nv_guide_string_railway)), new dka(AMapAppGlobal.getApplication().getString(R.string.nv_caption_dvation), AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_dvation), AMapAppGlobal.getApplication().getString(R.string.nv_guide_string_dvation))};

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put(AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_nvstart), Integer.valueOf(0));
        a.put(AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_nvdone_), Integer.valueOf(1));
        a.put(AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_rdcmera), Integer.valueOf(2));
        a.put(AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_svczone), Integer.valueOf(3));
        a.put(AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_zigzag_), Integer.valueOf(4));
        a.put(AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_acprone), Integer.valueOf(5));
        a.put(AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_railway), Integer.valueOf(6));
        a.put(AMapAppGlobal.getApplication().getString(R.string.nv_feature_string_dvation), Integer.valueOf(7));
    }

    public static String a(String str, int i) {
        StringBuilder sb = new StringBuilder("/autonavi/900_960");
        StringBuilder sb2 = new StringBuilder("/");
        sb2.append(str);
        sb2.append("/");
        sb.append(sb2.toString());
        sb.append("MyGoodVoice");
        sb.append(b[i].b);
        sb.append("Seq0.spx");
        return sb.toString();
    }
}
