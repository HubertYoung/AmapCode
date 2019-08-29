package defpackage;

import android.text.TextUtils;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.bundle.routecommute.common.CommuteHelper;
import com.autonavi.bundle.routecommute.common.bean.NaviAddress;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;

/* renamed from: azh reason: default package */
/* compiled from: NormalRulesHandler */
public final class azh {
    public CommuteHelper a;
    public NaviAddress b;

    public azh(CommuteHelper commuteHelper) {
        this.a = commuteHelper;
    }

    static /* synthetic */ void a(azh azh, GeoPoint geoPoint, NaviAddress naviAddress, String str) {
        String g = azi.g();
        azb.a(CommuteHelper.a, "NormalRulesHandler dealAddress getBusCarPref switchCommuteType = ".concat(String.valueOf(g)));
        if (TextUtils.isEmpty(g)) {
            g = "0";
        }
        azb.a(CommuteHelper.a, "NormalRulesHandler dealAddress schemeType = ".concat(String.valueOf(str)));
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, g)) {
            str = g;
        }
        azb.a(CommuteHelper.a, "NormalRulesHandler dealAddress switchCommuteType = ".concat(String.valueOf(str)));
        azh.b = naviAddress;
        boolean z = false;
        if (TextUtils.equals(str, "0")) {
            azh.a.t();
            if (!NetworkReachability.b()) {
                azh.a.q();
                azb.a(CommuteHelper.a, "NormalRulesHandler dealDriveAddress No network, destoryDrive ");
            } else if (!azi.o()) {
                azh.a.q();
                azb.a(CommuteHelper.a, "NormalRulesHandler dealDriveAddress mapShowSwitch false destoryDrive ");
            } else if (CommuteHelper.n() == null) {
                azh.a.q();
                azb.a(CommuteHelper.a, "NormalRulesHandler dealDriveAddress getConfigBean null destoryDrive ");
            } else {
                boolean a2 = azk.a(CommuteHelper.n().carBubbleRule, naviAddress, geoPoint);
                azb.a(CommuteHelper.a, "NormalRulesHandler dealDriveAddress isWithTheRulesDriver = ".concat(String.valueOf(a2)));
                if (!a2 || (!azh.a.u() && (!azk.a() || !azk.b()))) {
                    azh.a.q();
                    return;
                }
                int a3 = azk.a(naviAddress, CommuteHelper.n().carBubbleRule, geoPoint);
                azb.a(CommuteHelper.a, "NormalRulesHandler dealDriveAddress getCommuteTipsType = ".concat(String.valueOf(a3)));
                if (azk.a(a3)) {
                    CommuteHelper commuteHelper = azh.a;
                    if (commuteHelper.b != null) {
                        bao bao = commuteHelper.b;
                        if (bao.a != null) {
                            bao.a.b();
                        }
                    }
                    if (azk.b(a3)) {
                        CommuteHelper commuteHelper2 = azh.a;
                        if (commuteHelper2.b != null) {
                            bao bao2 = commuteHelper2.b;
                            if (bao2.a != null) {
                                z = bao2.a.h();
                            }
                        }
                        if (!z) {
                            azh.a.o().a(a3, azh.a.d);
                            azb.a(CommuteHelper.a, "NormalRulesHandler dealDriveAddress  show drive tipsType = ".concat(String.valueOf(a3)));
                        }
                    }
                    return;
                }
                CommuteHelper commuteHelper3 = azh.a;
                if (commuteHelper3.b != null) {
                    commuteHelper3.b.j();
                }
                CommuteHelper commuteHelper4 = azh.a;
                if (commuteHelper4.b != null) {
                    bao bao3 = commuteHelper4.b;
                    if (bao3.a != null) {
                        z = bao3.a.g();
                    }
                }
                if (!z) {
                    azh.a.o().a(a3, azh.a.d);
                    azb.a(CommuteHelper.a, "NormalRulesHandler dealDriveAddress  show drive tipsType = ".concat(String.valueOf(a3)));
                }
            }
        } else {
            azb.a(CommuteHelper.a, "NormalRulesHandler dealBusAddress naviAddress = ".concat(String.valueOf(naviAddress)));
            azh.a.q();
            if (azh.a.u()) {
                if (!azi.o()) {
                    azh.a.t();
                    azb.a(CommuteHelper.a, "NormalRulesHandler dealBusAddress mapShowSwitch false destoryBus ");
                    return;
                }
                POI poi = null;
                String str2 = CommuteHelper.n() != null ? CommuteHelper.n().busBubbleRule : null;
                POI home = (naviAddress == null || naviAddress.home == null) ? null : naviAddress.home.getHome();
                if (!(naviAddress == null || naviAddress.company == null)) {
                    poi = naviAddress.company.getCompany();
                }
                if (!ayq.a(str2, home, poi, geoPoint, false)) {
                    azh.a.t();
                    azb.a(CommuteHelper.a, "NormalRulesHandler dealBusAddress isBusDistanceRule false destoryBus ");
                } else if (!azh.a.s()) {
                    int a4 = ayq.a(geoPoint, home, poi);
                    azh.a.r().a(a4, azh.a.d);
                    azb.a(CommuteHelper.a, "NormalRulesHandler dealAddress  show bus locatonDistance = ".concat(String.valueOf(a4)));
                }
            }
        }
    }
}
