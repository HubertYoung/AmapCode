package com.autonavi.bundle.searchresult.ajx;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@AjxModule("poi")
public class ModulePoi extends AbstractModule {
    public static final String FULL = "full";
    public static final String INVALID = "invalid";
    public static final String MOVE = "moving";
    private static final String TAG = "ModulePoi";
    public static final String TIPS = "tips";
    private d mListenerInfo;
    private JsFunctionCallback mSetDetailViewStateCallback;
    private JsFunctionCallback mTipsHeightChangedCallback;

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public interface a {
        void a();
    }

    public interface b {
        void a(float f, float f2);
    }

    public interface c {
        void a(int i, boolean z);

        void a(String str, boolean z);

        void b(String str, boolean z);
    }

    static class d {
        b a;
        c b;
        a c;

        d() {
        }
    }

    public ModulePoi(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("setTipsHeightChangedCallback")
    public void setTipsHeightChangedCallback(JsFunctionCallback jsFunctionCallback) {
        this.mTipsHeightChangedCallback = jsFunctionCallback;
        d dVar = this.mListenerInfo;
        if (dVar != null && dVar.c != null) {
        }
    }

    public void setTipsHeightChanged(float f) {
        if (this.mTipsHeightChangedCallback != null) {
            this.mTipsHeightChangedCallback.callback(Float.valueOf(DimensionUtils.pixelToStandardUnit(getNativeContext(), f)));
        }
    }

    @AjxMethod("setDetailViewStateCallback")
    public void setDetailViewStateCallback(JsFunctionCallback jsFunctionCallback) {
        this.mSetDetailViewStateCallback = jsFunctionCallback;
        d dVar = this.mListenerInfo;
        if (dVar != null && dVar.c != null) {
            dVar.c.a();
        }
    }

    public void setDetailViewState(String str, boolean z) {
        if (this.mSetDetailViewStateCallback != null) {
            this.mSetDetailViewStateCallback.callback(str, Boolean.valueOf(z));
            StringBuilder sb = new StringBuilder("setDetailViewState() called with: state = [");
            sb.append(str);
            sb.append("], needAnimation = [");
            sb.append(z);
            sb.append("]");
            AMapLog.d(TAG, sb.toString());
        }
    }

    @AjxMethod("registerInfo")
    public void registerInfo(float f, float f2) {
        d dVar = this.mListenerInfo;
        if (dVar != null && dVar.a != null) {
            dVar.a.a(f, (float) DimensionUtils.standardUnitToPixel(getNativeContext(), f2));
        }
    }

    @AjxMethod("stateDidChange")
    public void stateDidChange(String str) {
        d dVar = this.mListenerInfo;
        if (dVar != null && dVar.b != null) {
            dVar.b.a(str, true);
        }
    }

    @AjxMethod("stateWillChangeTo")
    public void stateWillChangeTo(String str) {
        d dVar = this.mListenerInfo;
        if (dVar != null && dVar.b != null) {
            dVar.b.b(str, true);
        }
    }

    public void topHeightChange(int i) {
        d dVar = this.mListenerInfo;
        if (dVar != null && dVar.b != null) {
            dVar.b.a(DimensionUtils.standardUnitToPixel(getNativeContext(), (float) i), true);
        }
    }

    public void setOnRegisterInfoListener(b bVar) {
        getListenerInfo().a = bVar;
    }

    public void setOnStateChangeListener(c cVar) {
        getListenerInfo().b = cVar;
    }

    public void setOnPoiCallbackInitListener(a aVar) {
        getListenerInfo().c = aVar;
    }

    private d getListenerInfo() {
        if (this.mListenerInfo == null) {
            this.mListenerInfo = new d();
        }
        return this.mListenerInfo;
    }
}
