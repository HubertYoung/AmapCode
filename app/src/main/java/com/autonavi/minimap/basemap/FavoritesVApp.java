package com.autonavi.minimap.basemap;

import android.content.SharedPreferences.Editor;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.VirtualApp;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.bundle.favorites.ModuleFavorite;

@VirtualApp
public class FavoritesVApp extends esh implements bie {
    private anp a = new anp() {
        public final void onUserInfoUpdate(ant ant) {
        }

        public final void onLoginStateChanged(boolean z, boolean z2) {
            if (z) {
                brn brn = (brn) ank.a(brn.class);
                if (brn != null) {
                    brn.b();
                }
                return;
            }
            coy coy = (coy) ank.a(coy.class);
            if (coy != null) {
                String a2 = coy.a();
                Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("save_preference_file", 0).edit();
                edit.putLong("syncer_key".concat(String.valueOf(a2)), 0);
                edit.apply();
            }
        }
    };

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleFavorite.class);
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(this.a);
        }
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.b(this.a);
        }
    }
}
