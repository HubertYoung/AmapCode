package defpackage;

import android.text.TextUtils;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: uz reason: default package */
/* compiled from: EnvironmentLayerHelper */
public final class uz {
    public static String a(String str) {
        String a = lo.a().a(str);
        if (TextUtils.isEmpty(a)) {
            return str;
        }
        try {
            if (new JSONObject(a).optInt(FunctionSupportConfiger.SWITCH_TAG, 1) == 0) {
                return "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void a(bty bty, Map<Integer, String> map) {
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            ArrayList<LayerItem> i = awo.i();
            if (!i.isEmpty() && bty != null) {
                Iterator<LayerItem> it = i.iterator();
                while (it.hasNext()) {
                    LayerItem next = it.next();
                    if (next != null) {
                        if (600000 == next.getLayer_id() || 600001 == next.getLayer_id() || 600002 == next.getLayer_id() || 600003 == next.getLayer_id()) {
                            map.put(Integer.valueOf(next.getLayer_id()), next.getData());
                            awo.b();
                            awo.a(next.getData());
                        }
                    }
                }
            }
        }
    }

    public static void a(bty bty, int i, Map<Integer, String> map) {
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null && bty != null) {
            for (Integer intValue : map.keySet()) {
                int intValue2 = intValue.intValue();
                if (intValue2 == i) {
                    awo.b(intValue2);
                } else {
                    awo.c(intValue2);
                }
            }
        }
    }
}
