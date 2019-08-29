package com.alipay.android.phone.inside.offlinecode.engine;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class ScriptContextPlugin implements DynamicCodePlugin {
    private static final String TAG = "ScriptContextPlugin";
    Map<String, Method> methodMap = new HashMap();

    public ScriptContextPlugin() {
        Method[] methods;
        for (Method method : ScriptContext.class.getMethods()) {
            this.methodMap.put(method.getName(), method);
        }
    }

    public List<String> bridgeMethodNames() {
        return new ArrayList(this.methodMap.keySet());
    }

    public void handleEvent(DPECallEvent dPECallEvent) {
        try {
            Method method = this.methodMap.get(dPECallEvent.getMethodName());
            JSONObject param = dPECallEvent.getParam();
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("method: ");
            sb.append(dPECallEvent.getMethodName());
            sb.append(", input --> ");
            sb.append(param == null ? "" : param.toString());
            f.b((String) "[ANX]", sb.toString());
            String str = (String) method.invoke(null, new Object[]{param});
            TraceLogger f2 = LoggerFactory.f();
            StringBuilder sb2 = new StringBuilder("method: ");
            sb2.append(dPECallEvent.getMethodName());
            sb2.append(", output --> ");
            sb2.append(str);
            f2.b((String) "[ANX]", sb2.toString());
            dPECallEvent.setResult(str);
        } catch (Exception e) {
            LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "BusScriptHandleEventEx", (Throwable) e);
            LoggerFactory.f().b(TAG, (Throwable) e);
        }
    }
}
