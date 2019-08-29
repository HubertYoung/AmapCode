package com.autonavi.bundle.carlogo;

import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.annotation.VirtualApp;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.carlogo.ajx.ModuleUser;
import com.autonavi.minimap.account.base.model.AccountProfileCarLogo;
import com.autonavi.minimap.account.login.LoginRequestHolder;
import com.autonavi.minimap.account.login.param.ProfileGetParam;
import com.autonavi.minimap.ajx3.Ajx;

@VirtualApp
public class CarLogoVApp extends esh implements bie {
    private static String a = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_PASSPORT_URL_KEY);
    private anp b = new anp() {
        public final void onLoginStateChanged(boolean z, boolean z2) {
            if (z2) {
                CarLogoVApp.a(CarLogoVApp.this);
            }
        }

        public final void onUserInfoUpdate(ant ant) {
            CarLogoVApp.a(CarLogoVApp.this);
        }
    };

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleUser.class);
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(this.b);
        }
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.b(this.b);
        }
    }

    static /* synthetic */ void a(CarLogoVApp carLogoVApp) {
        ProfileGetParam profileGetParam = new ProfileGetParam();
        profileGetParam.mode = 159;
        LoginRequestHolder.getInstance().sendProfileGet(profileGetParam, new dko<chm>() {
            public final void a(Exception exc) {
            }

            public final /* synthetic */ void a(dkm dkm) {
                chm chm = (chm) dkm;
                if (chm != null && chm.d != null && chm.d.carLogo != null) {
                    AccountProfileCarLogo accountProfileCarLogo = chm.d.carLogo;
                    StringBuilder sb = new StringBuilder();
                    sb.append(accountProfileCarLogo.id);
                    String sb2 = sb.toString();
                    String str = accountProfileCarLogo.normalLogo;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(accountProfileCarLogo.id);
                    ati.a(sb2, str, "1", "normalType", new ath(sb3.toString(), "normalType", "1"));
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(accountProfileCarLogo.id);
                    String sb5 = sb4.toString();
                    String str2 = accountProfileCarLogo.weakLogo;
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(accountProfileCarLogo.id);
                    ati.a(sb5, str2, "1", "weakType", new ath(sb6.toString(), "weakType", "1"));
                }
            }
        });
    }
}
