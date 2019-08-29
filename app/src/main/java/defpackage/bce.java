package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.bundle.searchresult.ajx.ModuleSearch;
import com.autonavi.bundle.searchresult.ajx.ModuleSearchNoResult;
import com.autonavi.map.search.tip.indicator.indicator.PoiBottomView;
import com.autonavi.minimap.ajx3.Ajx;

@MultipleImpl(ema.class)
/* renamed from: bce reason: default package */
/* compiled from: SearchResultAjxRegister */
public class bce implements ema {
    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleIdqPlus.class, ModulePoi.class, ModuleSearch.class, ModuleSearchNoResult.class);
    }

    public void onWidgetRegister() {
        Ajx.getInstance().registerView("poi", PoiBottomView.class);
    }
}
