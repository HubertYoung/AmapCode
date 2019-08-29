package defpackage;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.alipay.android.phone.wallet.CodeBuilder;
import com.alipay.android.phone.wallet.minizxing.BarcodeFormat;
import com.alipay.android.phone.wallet.minizxing.ErrorCorrectionLevel;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.qrscan.page.QRScanPage;
import com.autonavi.minimap.bundle.qrscan.scanner.AliPayScanUtils;
import org.json.JSONException;
import org.json.JSONObject;

@BundleInterface(awt.class)
/* renamed from: dbv reason: default package */
/* compiled from: QRScanImpl */
public class dbv implements awt {
    public final void b(String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("qrcode_id", str);
        AMapPageUtil.getPageContext().startPage((String) "amap.basemap.action.qr_login_page", pageBundle);
    }

    public final String a(Bitmap bitmap) {
        return AliPayScanUtils.scanSyncQRUri(bitmap);
    }

    public final String b(Bitmap bitmap) {
        return AliPayScanUtils.scanQRUriAndMaplatformSync(bitmap);
    }

    public final void a(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "others";
        }
        final JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("firepage", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final bid pageContext = AMapPageUtil.getPageContext();
        kj.a(pageContext.getActivity(), new String[]{"android.permission.CAMERA"}, (b) new b() {
            public final void run() {
                if (!dbz.a()) {
                    ToastHelper.showToast(pageContext.getContext().getString(R.string.camera_permission_failed));
                    return;
                }
                String jSONObject = jSONObject.toString();
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("url", "path://amap_bundle_tripgroup/src/share_bike/ShareBikeScanQRCode.page.js");
                if (!TextUtils.isEmpty(jSONObject)) {
                    pageBundle.putString("jsData", jSONObject);
                }
                AMapPageUtil.getPageContext().startPage(QRScanPage.class, pageBundle);
            }

            public final void reject() {
                super.reject();
                ToastHelper.showToast(pageContext.getContext().getString(R.string.camera_permission_failed));
            }
        });
    }

    public final Bitmap a(String str, int i) {
        return new CodeBuilder(str, BarcodeFormat.QR_CODE).setErrorCorrectionLevel(ErrorCorrectionLevel.L).setForceNoPadding().createBitmap(i, i);
    }
}
