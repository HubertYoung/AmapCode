package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.schoolbus.page.SchoolBusAjx3Page;
import com.amap.bundle.schoolbus.request.SchoolbusCheckRoleRequest$1;
import com.amap.bundle.schoolbus.router.SchoolbusRouter$2;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.annotation.Router;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.schoolbus.SchoolBusRequestHolder;
import com.autonavi.minimap.schoolbus.param.CheckRoleRequest;
import com.autonavi.widget.ui.LoadingViewBL;
import org.json.JSONException;
import org.json.JSONObject;

@Router({"schoolbus"})
/* renamed from: adr reason: default package */
/* compiled from: SchoolbusRouter */
public class adr extends esk {
    /* access modifiers changed from: private */
    public volatile boolean a = false;
    private long b = 0;
    /* access modifiers changed from: private */
    public adt c;
    /* access modifiers changed from: private */
    public adn d;

    public static bid a() {
        return AMapPageUtil.getPageContext();
    }

    public boolean start(ese ese) {
        Uri uri = ese.a;
        if (uri == null) {
            return false;
        }
        String host = uri.getHost();
        if (TextUtils.isEmpty(host) || !host.equals("schoolbus")) {
            return false;
        }
        if (!AMapPageUtil.getTopPageClass().equals(SchoolBusAjx3Page.class) || !((SchoolBusAjx3Page) AMapPageUtil.getPageContext()).getAjx3Url().equals("path://amap_bundle_schoolbus/src/pages/SchoolbusDetail.page.js")) {
            if (System.currentTimeMillis() - this.b < 1000) {
                this.b = System.currentTimeMillis();
            } else {
                this.b = System.currentTimeMillis();
                IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                if (iAccountService != null && iAccountService.a()) {
                    if (this.d != null) {
                        this.d.a = true;
                    }
                    this.d = new adn();
                    startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
                    if (this.c == null) {
                        this.c = new adt(AMapPageUtil.getPageContext(), new OnClickListener() {
                            public final void onClick(View view) {
                                adr.this.c.a();
                                if (adr.this.d != null) {
                                    adr.this.d.a = true;
                                }
                            }
                        });
                    }
                    adt adt = this.c;
                    if (!(adt.b == null || adt.b.getContext() == null)) {
                        if (adt.a == null) {
                            adt.a = new LoadingViewBL(adt.b.getContext(), 3);
                            adt.a.setLoadingText("加载中");
                            adt.a.setOnCloseClickListener(adt.c);
                        }
                        if (!adt.a.isShown()) {
                            adt.b.showViewLayer(adt.a);
                        }
                    }
                    this.a = false;
                    adn adn = this.d;
                    SchoolbusRouter$2 schoolbusRouter$2 = new SchoolbusRouter$2(this, uri);
                    SchoolBusRequestHolder.getInstance().sendCheckRole(new CheckRoleRequest(), new SchoolbusCheckRoleRequest$1(adn, schoolbusRouter$2));
                } else if (iAccountService != null) {
                    iAccountService.a(AMapPageUtil.getPageContext(), (anq) new anq() {
                        public final void loginOrBindCancel() {
                        }

                        public final void onComplete(boolean z) {
                        }
                    });
                }
            }
        }
        return true;
    }

    public static /* synthetic */ void c(adr adr) {
        if (adr.c != null) {
            adr.c.a();
        }
    }

    public static /* synthetic */ void a(adr adr, int i) {
        PageBundle pageBundle = new PageBundle();
        if (i == 1) {
            pageBundle.putString("url", "path://amap_bundle_schoolbus/src/pages/SchoolbusRoutsList.page.js");
        } else if (i == 2 || i == 3) {
            pageBundle.putString("url", "path://amap_bundle_schoolbus/src/pages/SchoolbusDetail.page.js");
        } else {
            pageBundle.putString("url", "path://amap_bundle_schoolbus/src/pages/SchoolbusError.page.js");
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("role", String.valueOf(i));
            jSONObject.put("NM_MANAGER_SCHEME_PATH", "schoolbus/index");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pageBundle.putString("jsData", jSONObject.toString());
        cde suspendManager = DoNotUseTool.getSuspendManager();
        if (suspendManager != null) {
            suspendManager.d().f();
        }
        new MapSharePreference(SharePreferenceName.SharedPreferences).putIntValue("reset_school_bus_traffic_state", 1);
        adr.startPage(SchoolBusAjx3Page.class, pageBundle);
        if (i == 1 || i == 2 || i == 3) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("type", String.valueOf(i));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            LogManager.actionLogV2("P00368", "D002", jSONObject2);
        }
    }
}
