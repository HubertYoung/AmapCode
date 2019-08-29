package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;

/* renamed from: dpo reason: default package */
/* compiled from: AosResponserHelper */
public final class dpo {
    public static boolean a(dog dog) {
        return dog.c != 1;
    }

    public static boolean b(dog dog) {
        if (dog.c != 14) {
            return false;
        }
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        iAccountService.d();
        return true;
    }

    public static void a(int i, String str) {
        if (i == -1) {
            ToastHelper.showLongToast(AbstractAOSParser.ERROR_NETWORK);
        } else if (14 == i || 92 == i) {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                iAccountService.d();
            } else {
                return;
            }
        }
        ToastHelper.showLongToast(str);
    }
}
