package defpackage;

import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.wallet.WalletRequestCallback;
import com.autonavi.minimap.R;
import com.autonavi.minimap.auth.AuthRequestHolder;
import com.autonavi.minimap.auth.param.AlipayInfoRequest;
import com.autonavi.minimap.wallet.WalletRequestHolder;
import com.autonavi.minimap.wallet.param.AmountRequest;

/* renamed from: cfk reason: default package */
/* compiled from: WalletNetManager */
public final class cfk {
    public static void a(cfu cfu, Callback<cfu> callback) {
        AmountRequest amountRequest = new AmountRequest();
        WalletRequestCallback walletRequestCallback = new WalletRequestCallback(cfu, callback);
        CompatDialog a = aav.a(amountRequest, AMapPageUtil.getAppContext().getString(R.string.wallet_search_info));
        walletRequestCallback.a = a;
        a.show();
        WalletRequestHolder.getInstance().sendAmount(amountRequest, walletRequestCallback);
    }

    public static void a(cft cft, Callback<cft> callback, String str) {
        AlipayInfoRequest alipayInfoRequest = new AlipayInfoRequest();
        if (str == null) {
            alipayInfoRequest.b = "0";
        } else {
            alipayInfoRequest.b = "1";
            alipayInfoRequest.c = str;
        }
        WalletRequestCallback walletRequestCallback = new WalletRequestCallback(cft, callback);
        CompatDialog a = aav.a(alipayInfoRequest, AMapPageUtil.getAppContext().getString(R.string.wallet_withdraw_request));
        walletRequestCallback.a = a;
        a.show();
        AuthRequestHolder.getInstance().sendAlipayInfo(alipayInfoRequest, walletRequestCallback);
    }
}
