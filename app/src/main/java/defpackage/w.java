package defpackage;

import com.taobao.analysis.FlowCenter;

/* renamed from: w reason: default package */
/* compiled from: DefaultNetworkAnalysis */
public final class w implements ak {
    private boolean a;

    public w() {
        try {
            Class.forName("com.taobao.analysis.FlowCenter");
            this.a = true;
        } catch (Exception unused) {
            this.a = false;
            cl.d("DefaultNetworkAnalysis", "no NWNetworkAnalysisSDK sdk", null, new Object[0]);
        }
    }

    public final void a(aj ajVar) {
        if (this.a) {
            FlowCenter.getInstance().commitFlow(m.a(), ajVar.a, ajVar.b, ajVar.c, ajVar.d, ajVar.e);
        }
    }
}
