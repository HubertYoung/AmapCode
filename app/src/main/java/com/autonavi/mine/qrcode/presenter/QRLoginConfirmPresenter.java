package com.autonavi.mine.qrcode.presenter;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.mine.qrcode.page.QRLoginConfirmPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.qrcode.QrCodeRequestHolder;
import com.autonavi.minimap.qrcode.param.ConfirmRequest;
import org.json.JSONException;
import org.json.JSONObject;

public final class QRLoginConfirmPresenter extends AbstractBasePresenter<QRLoginConfirmPage> {
    public String a;
    private CompatDialog b;

    class QRLoginCallback extends FalconAosPrepareResponseCallback<JSONObject> {
        private QRLoginCallback() {
        }

        /* synthetic */ QRLoginCallback(QRLoginConfirmPresenter qRLoginConfirmPresenter, byte b) {
            this();
        }

        public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
            return b(aosByteResponse);
        }

        public final /* synthetic */ void a(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            QRLoginConfirmPresenter.b(QRLoginConfirmPresenter.this);
            if (jSONObject != null) {
                if (jSONObject.optInt("code") == 1) {
                    ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.qr_login_success));
                    ((QRLoginConfirmPage) QRLoginConfirmPresenter.this.mPage).finish();
                    return;
                }
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.qr_login_failed));
                ((QRLoginConfirmPage) QRLoginConfirmPresenter.this.mPage).finish();
            }
        }

        public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
            QRLoginConfirmPresenter.b(QRLoginConfirmPresenter.this);
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.qr_login_failed));
        }

        private static JSONObject b(AosByteResponse aosByteResponse) {
            if (aosByteResponse.getResult() == null) {
                return null;
            }
            try {
                return AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse);
            } catch (JSONException unused) {
                return null;
            }
        }
    }

    public QRLoginConfirmPresenter(QRLoginConfirmPage qRLoginConfirmPage) {
        super(qRLoginConfirmPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.a = (String) ((QRLoginConfirmPage) this.mPage).getArguments().get("qrcode_id");
    }

    public final void a() {
        ConfirmRequest confirmRequest = new ConfirmRequest();
        confirmRequest.b = this.a;
        QrCodeRequestHolder.getInstance().sendConfirm(confirmRequest, new QRLoginCallback(this, 0));
    }

    static /* synthetic */ void b(QRLoginConfirmPresenter qRLoginConfirmPresenter) {
        if (qRLoginConfirmPresenter.b != null) {
            qRLoginConfirmPresenter.b.dismiss();
            qRLoginConfirmPresenter.b = null;
        }
    }
}
