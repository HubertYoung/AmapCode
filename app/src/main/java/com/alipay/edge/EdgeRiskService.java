package com.alipay.edge;

import android.content.Context;
import com.alipay.edge.face.EdgeRiskResult;
import java.util.Map;

public abstract class EdgeRiskService {
    public abstract EdgeRiskResult getRiskResult(String str, Map<String, String> map, int i);

    public abstract int initialize(Context context);

    public abstract int postUserAction(String str, Map<String, String> map);

    public abstract void syncWithServerNow();

    public abstract void updateResource(String str);
}
