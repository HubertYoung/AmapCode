package com.alipay.edge.face;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.edge.EdgeRiskService;
import com.alipay.edge.impl.EdgeRiskServiceImpl;
import com.taobao.accs.antibrush.CookieMgr;
import java.util.Map;

public class EdgeRiskAnalyzer {
    private static EdgeRiskAnalyzer instance;
    private EdgeRiskService edgeRiskService = null;

    public void initialize() {
    }

    private EdgeRiskAnalyzer(Context context, EdgeRiskService edgeRiskService2) {
        LoggerFactory.f().b((String) CookieMgr.KEY_SEC, "EdgeRiskAnalyzer > ".concat(String.valueOf(context)));
        this.edgeRiskService = edgeRiskService2;
    }

    public static EdgeRiskAnalyzer getInstance(Context context) {
        if (instance == null) {
            synchronized (EdgeRiskAnalyzer.class) {
                try {
                    if (instance == null) {
                        if (context == null) {
                            return null;
                        }
                        EdgeRiskServiceImpl edgeRiskServiceImpl = new EdgeRiskServiceImpl();
                        EdgeRiskAnalyzer edgeRiskAnalyzer = new EdgeRiskAnalyzer(context, edgeRiskServiceImpl);
                        edgeRiskServiceImpl.initialize(context);
                        instance = edgeRiskAnalyzer;
                    }
                }
            }
        }
        return instance;
    }

    public void postUserAction(String str, Map<String, String> map) {
        this.edgeRiskService.postUserAction(str, map);
    }

    public EdgeRiskResult getRiskResult(String str, Map<String, String> map, int i) {
        return this.edgeRiskService.getRiskResult(str, map, i);
    }
}
