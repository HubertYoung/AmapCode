package defpackage;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.amap.bundle.drive.ajx.module.ModuleHeadunit;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.ICQLayerController;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import java.lang.ref.WeakReference;
import org.json.JSONObject;

/* renamed from: dam reason: default package */
/* compiled from: AutoRemotePresenter */
public final class dam implements OnClickListener {
    public dan a;
    public boolean b = false;
    private a c = new a(this);

    /* renamed from: dam$a */
    /* compiled from: AutoRemotePresenter */
    static class a extends Handler {
        private final WeakReference<dam> a;

        public a(dam dam) {
            this.a = new WeakReference<>(dam);
        }

        public final void handleMessage(Message message) {
            dam dam = (dam) this.a.get();
            if (dam != null && message.what == 0) {
                ImageView autoRemoteView = dam.a.getAutoRemoteView();
                if (autoRemoteView != null) {
                    autoRemoteView.setClickable(true);
                    dam.a.getAutoRemoteView().setAlpha(1.0f);
                }
            }
        }
    }

    public final void onClick(View view) {
        if (view.equals(this.a.getAutoRemoteView())) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext instanceof MapBasePage) {
                ICQLayerController cQLayerController = ((MapBasePage) pageContext).getCQLayerController();
                if (cQLayerController != null) {
                    if (cQLayerController.isShowing()) {
                        vp vpVar = (vp) defpackage.esb.a.a.a(vp.class);
                        if (!(vpVar == null || !vpVar.b() || cQLayerController == null)) {
                            POI curPoi = cQLayerController.getCurPoi();
                            if (curPoi != null) {
                                vpVar.a(curPoi, (vo) new vo() {
                                    public final void onSuccess(int i) {
                                        ku.a().c(ModuleHeadunit.MODULE_NAME, "发送POI到车机  onSuccess  sendType:".concat(String.valueOf(i)));
                                        switch (i) {
                                            case 0:
                                                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_successed_by_linksdk));
                                                return;
                                            case 1:
                                                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_successed_by_aos));
                                                break;
                                        }
                                    }

                                    public final void onError(String str, String str2) {
                                        ku a2 = ku.a();
                                        StringBuilder sb = new StringBuilder("发送POI到车机  onError code:");
                                        sb.append(str);
                                        sb.append("   msg:");
                                        sb.append(str2);
                                        a2.c(ModuleHeadunit.MODULE_NAME, sb.toString());
                                        ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_failed));
                                    }
                                });
                            } else {
                                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.no_content_select));
                            }
                            a(curPoi);
                        }
                    } else {
                        ToastHelper.showToast("还没有选择要发给汽车的内容");
                        a((POI) null);
                    }
                }
            }
            this.a.setTipViewVisibility(8);
            view.setClickable(false);
            view.setAlpha(0.6f);
            this.c.sendEmptyMessageDelayed(0, 5000);
            return;
        }
        if (view.equals(this.a.getAutoRemoteTip())) {
            this.a.setTipViewVisibility(8);
        }
    }

    private static void a(POI poi) {
        try {
            JSONObject jSONObject = new JSONObject();
            if (poi != null) {
                jSONObject.put("result", 1);
            } else {
                jSONObject.put("result", 0);
            }
            vp vpVar = (vp) defpackage.esb.a.a.a(vp.class);
            if (vpVar != null) {
                if (vpVar.c()) {
                    jSONObject.put("status", "wifi");
                } else if (vpVar.d()) {
                    jSONObject.put("status", "mqtt");
                }
            }
            LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_CLICK_HEADUNIT_ICON, jSONObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final boolean a() {
        vp vpVar = (vp) defpackage.esb.a.a.a(vp.class);
        int i = 0;
        boolean z = (vpVar != null ? vpVar.b() : false) && !this.b;
        if (z) {
            MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
            if (mapSharePreference.getBooleanValue("IsShowAutoLinkTip", true)) {
                this.a.setTipViewVisibility(0);
                mapSharePreference.putBooleanValue("IsShowAutoLinkTip", false);
            }
        }
        dan dan = this.a;
        if (!z) {
            i = 8;
        }
        dan.setViewVisibility(i);
        return z;
    }
}
