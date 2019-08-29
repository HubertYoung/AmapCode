package com.autonavi.minimap.drive.auto.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;

public class DeleteAutoConnectionFragment extends DriveBasePage<dep> {
    public RelativeLayout a;
    public AlertView b;
    public OnClickListener c = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.delete_connection_lativeLayout) {
                a aVar = new a(AMapAppGlobal.getApplication());
                a a2 = aVar.a((CharSequence) DeleteAutoConnectionFragment.this.getString(R.string.delete_auto_title));
                StringBuilder sb = new StringBuilder();
                sb.append(DeleteAutoConnectionFragment.this.getString(R.string.route_record));
                sb.append("\n");
                sb.append(DeleteAutoConnectionFragment.this.getString(R.string.download_apk));
                a2.b((CharSequence) sb.toString()).b((CharSequence) DeleteAutoConnectionFragment.this.getString(R.string.auto_delete_cancle), (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        DeleteAutoConnectionFragment.this.dismissAllViewLayers();
                    }
                }).a((CharSequence) DeleteAutoConnectionFragment.this.getString(R.string.auto_delete_sure), (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        DeleteAutoConnectionFragment.a(DeleteAutoConnectionFragment.this);
                        DeleteAutoConnectionFragment.this.setResult(ResultType.OK, new PageBundle());
                        DeleteAutoConnectionFragment.this.finish();
                    }
                });
                aVar.a(true);
                DeleteAutoConnectionFragment.this.b = aVar.a();
                DeleteAutoConnectionFragment.this.showViewLayer(DeleteAutoConnectionFragment.this.b);
            }
        }
    };
    private fbl d;

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        this.d = fbl.a(AMapPageUtil.getAppContext());
        setContentView(R.layout.delete_auto_connection);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dep(this);
    }

    static /* synthetic */ void a(DeleteAutoConnectionFragment deleteAutoConnectionFragment) {
        agb.a((String) "");
        agb.a(Boolean.TRUE);
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.stopALinkBt();
            iAutoRemoteController.stopALinkWifi();
        }
        agb.a(false);
        agb.c(false);
        deleteAutoConnectionFragment.d.c.clear();
        deleteAutoConnectionFragment.d.d.clear();
        deleteAutoConnectionFragment.d.e.clear();
        deleteAutoConnectionFragment.d.g();
        DriveUtil.delNaviHistoryList();
        new MapSharePreference(SharePreferenceName.SharedPreferences).putStringValue("amap_auto_20_apk_info", "");
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("IsShowAutoLinkTip", true);
    }
}
