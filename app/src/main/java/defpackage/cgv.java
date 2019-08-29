package defpackage;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.mine.qrcode.VerifyParam;
import com.autonavi.mine.qrcode.page.QRLoginConfirmPage;
import com.autonavi.minimap.intent.BaseIntentDispatcher;

/* renamed from: cgv reason: default package */
/* compiled from: QRCodeScan */
public class cgv implements dtc {
    private static final String a = "cgv";

    @SuppressLint({"MissingPermission"})
    public final void a(AbstractBasePage abstractBasePage, int i, Intent intent) {
        if (i == -1 && intent != null) {
            String stringExtra = intent.getStringExtra("qr_uri");
            TextUtils.isEmpty(stringExtra);
            Uri parse = Uri.parse(stringExtra);
            String scheme = parse.getScheme();
            if (!TextUtils.isEmpty(scheme) && (scheme.equals("http") || scheme.equals("https"))) {
                String queryParameter = parse.getQueryParameter("amap_qrcode_type");
                String queryParameter2 = parse.getQueryParameter("amap_qrcode_id");
                if (TextUtils.isEmpty(queryParameter) || TextUtils.isEmpty(queryParameter2) || !queryParameter.equals("1")) {
                    VerifyParam verifyParam = new VerifyParam();
                    verifyParam.redirect_url = stringExtra.trim();
                    String url = aax.a(verifyParam).buildHttpRequest().getUrl();
                    if (!TextUtils.isEmpty(url)) {
                        aja aja = new aja(url);
                        aja.b = new ajf() {
                            public final boolean f() {
                                return true;
                            }
                        };
                        aix aix = (aix) a.a.a(aix.class);
                        if (aix != null) {
                            aix.a(AMapPageUtil.getPageContext(), aja);
                        }
                        return;
                    }
                    return;
                }
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("qrcode_id", queryParameter2);
                abstractBasePage.startPage(QRLoginConfirmPage.class, pageBundle);
            } else if (!TextUtils.isEmpty(scheme) && scheme.equals("androidamap")) {
                Intent intent2 = new Intent();
                intent2.setData(parse);
                intent2.putExtra("owner", BaseIntentDispatcher.INTENT_CALL_FROMOWNER);
                DoNotUseTool.startScheme(intent2);
            } else if (!TextUtils.isEmpty(scheme) && scheme.equals("amaplink")) {
                String substring = stringExtra.substring(stringExtra.lastIndexOf("?") + 1, stringExtra.length());
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
                if (iAutoRemoteController != null) {
                    if (defaultAdapter == null || !defaultAdapter.isEnabled()) {
                        iAutoRemoteController.promptToEnableBluetoothBeforePairing(substring);
                    } else if (iAutoRemoteController.isParied(substring)) {
                        iAutoRemoteController.doConnectBt(substring);
                    } else {
                        iAutoRemoteController.pairDevice(substring);
                    }
                }
            }
        }
    }
}
