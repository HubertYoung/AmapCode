package defpackage;

import android.text.TextUtils;
import android.util.Pair;
import com.amap.bundle.voiceservice.module.ModuleVoiceCenter;
import com.amap.bundle.voiceservice.task.VoiceTaskBean;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import java.lang.ref.WeakReference;
import java.util.List;
import org.json.JSONObject;

@BundleInterface(aia.class)
/* renamed from: ahz reason: default package */
/* compiled from: VoiceServiceImpl */
public class ahz extends esi implements aia {
    public final boolean a(IAjxContext iAjxContext, String str, int i) {
        if (iAjxContext == null) {
            return false;
        }
        ModuleVoiceCenter moduleVoiceCenter = (ModuleVoiceCenter) Ajx.getInstance().getModuleIns(iAjxContext, ModuleVoiceCenter.MODULE_NAME);
        if (moduleVoiceCenter == null) {
            return false;
        }
        moduleVoiceCenter.sendVoiceCommand(str, i);
        return true;
    }

    public final boolean a(IAjxContext iAjxContext, String str, int i, Pair<String, Object> pair) {
        if (iAjxContext == null) {
            return false;
        }
        ModuleVoiceCenter moduleVoiceCenter = (ModuleVoiceCenter) Ajx.getInstance().getModuleIns(iAjxContext, ModuleVoiceCenter.MODULE_NAME);
        if (moduleVoiceCenter == null) {
            return false;
        }
        moduleVoiceCenter.sendVoiceCommand(str, i, pair);
        return true;
    }

    public final boolean a(IAjxContext iAjxContext, String str, int i, List<Pair<String, Object>> list) {
        if (iAjxContext == null) {
            return false;
        }
        ModuleVoiceCenter moduleVoiceCenter = (ModuleVoiceCenter) Ajx.getInstance().getModuleIns(iAjxContext, ModuleVoiceCenter.MODULE_NAME);
        if (moduleVoiceCenter == null) {
            return false;
        }
        moduleVoiceCenter.sendVoiceCommand(str, i, list);
        return true;
    }

    public final JSONObject a(String str, int i, JSONObject jSONObject) {
        return air.a(str, i, jSONObject);
    }

    public final void a(int i, int i2, Pair<String, Object> pair) {
        aip.a().a(i, aid.a(i2, aid.a(pair)));
    }

    public final void a(int i, int i2, List<Pair<String, Object>> list) {
        aip.a().a(i, aid.a(i2, aid.a(list)));
    }

    public final boolean a(String str, int i) {
        for (VoiceTaskBean voiceTaskBean : aip.a().a) {
            if (voiceTaskBean != null && TextUtils.equals(str, voiceTaskBean.getMethodId()) && voiceTaskBean.getToken() != i) {
                return true;
            }
        }
        return false;
    }

    public final void a() {
        aik.a().b();
    }

    public final void b() {
        aik.a().c();
    }

    public final void a(aih aih) {
        ail.a().a = new WeakReference<>(aih);
    }
}
