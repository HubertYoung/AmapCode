package defpackage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.Router;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.minimap.offline.service.IJsOfflineAuiServiceProxy3;
import com.autonavi.minimap.offline.uiutils.UiController;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@Router({"offlinemap"})
/* renamed from: awn reason: default package */
/* compiled from: OfflineMapRouter */
public class awn extends esk {
    private Context a = AMapPageUtil.getAppContext();

    /* access modifiers changed from: private */
    public boolean a(boolean z) {
        if (!FileUtil.iSHasSdcardPath(this.a)) {
            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.offline_data_unavalable));
            return false;
        }
        if (!z) {
            String a2 = PathManager.a().a(DirType.OFFLINE);
            if (!FileUtil.iSHasSdcardPath(this.a)) {
                ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.offline_data_unavalable));
                return false;
            }
            try {
                if (!FileUtil.getPathIsCanWrite(a2)) {
                    if (!TextUtils.isEmpty(FileUtil.canWritePath(this.a))) {
                        a();
                        return true;
                    }
                    ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.offline_data_unavalable));
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (!TextUtils.isEmpty(FileUtil.canWritePath(this.a))) {
                    a();
                    return true;
                }
                ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.offline_data_unavalable));
            }
        }
        Intent intent = new Intent();
        intent.putExtra("showMapDownload", true);
        UiController.deal(AMapPageUtil.getPageContext(), intent);
        return true;
    }

    private void a() {
        if (this.a != null) {
            final bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                a aVar = new a(pageContext.getActivity());
                aVar.a(R.string.sd_unavailable);
                aVar.a(R.string.switch_sd, (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        FileUtil.setClearDataOperation(true);
                        awn.this.a(true);
                        pageContext.dismissViewLayer(alertView);
                    }
                });
                aVar.b(R.string.cancel, (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        pageContext.dismissViewLayer(alertView);
                    }
                });
                aVar.a(true);
                AlertView a2 = aVar.a();
                pageContext.showViewLayer(a2);
                a2.startAnimation();
            }
        }
    }

    public boolean start(ese ese) {
        if (ese == null) {
            return false;
        }
        Uri uri = ese.a;
        if (uri == null) {
            return false;
        }
        String b = ese.b();
        if (TextUtils.isEmpty(b)) {
            return false;
        }
        char c = 65535;
        int hashCode = b.hashCode();
        if (hashCode != -800438727) {
            if (hashCode == 46613902 && b.equals("/home")) {
                c = 1;
            }
        } else if (b.equals("/offlineManager")) {
            c = 0;
        }
        switch (c) {
            case 0:
                if (TextUtils.equals("offlineManager", uri.getPathSegments().get(0))) {
                    String queryParameter = uri.getQueryParameter("cityAdCodes");
                    if (!TextUtils.isEmpty(queryParameter)) {
                        String[] split = queryParameter.split(",");
                        if (split != null && split.length > 0) {
                            int[] iArr = new int[split.length];
                            for (int i = 0; i < split.length; i++) {
                                if (!TextUtils.isEmpty(split[i])) {
                                    iArr[i] = Integer.parseInt(split[i]);
                                }
                            }
                            IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
                            if (iOfflineManager != null) {
                                iOfflineManager.enterAlongWayDownload(iArr);
                            }
                            return true;
                        }
                    }
                }
                return false;
            case 1:
                return a(false);
            default:
                List<String> pathSegments = uri.getPathSegments();
                if (pathSegments == null || pathSegments.size() <= 0 || !TextUtils.equals("viewMap", pathSegments.get(0))) {
                    return false;
                }
                String queryParameter2 = uri.getQueryParameter("data");
                String str = "";
                try {
                    JSONObject jSONObject = TextUtils.isEmpty(queryParameter2) ? null : new JSONObject(queryParameter2);
                    if (jSONObject != null) {
                        str = jSONObject.getString("adCode");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ((IJsOfflineAuiServiceProxy3) ank.a(IJsOfflineAuiServiceProxy3.class)).viewMap(str);
                return true;
        }
    }
}
