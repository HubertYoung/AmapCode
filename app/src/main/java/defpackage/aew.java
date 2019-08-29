package defpackage;

import android.support.annotation.Nullable;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.minimap.controller.AppManager;

/* renamed from: aew reason: default package */
/* compiled from: InfoliteParamHelper */
public final class aew {
    public static InfoliteParam a(aem aem) {
        if (aem instanceof ael) {
            return a((ael) aem);
        }
        if (aem instanceof aen) {
            return a((aen) aem);
        }
        throw new RuntimeException("Unsupported Request Type!");
    }

    private static InfoliteParam a(aen aen) {
        InfoliteParam a = aev.a(AppManager.getInstance().getLastUserLocInfo(), aen.a, aen.b);
        a.superid = "";
        a.pagenum = aen.e;
        a.data_type = "POI";
        a.search_operate = 0;
        a.cmspoi = "1";
        a.need_magicbox = false;
        a.need_recommend = "";
        a.onlypoi = "poi";
        a.busorcar = "car";
        a.query_scene = "car";
        a.query_mode = "";
        a.qii = true;
        a.is_classify = true;
        a.need_parkinfo = true;
        a.need_codepoint = true;
        a.need_utd = false;
        a.query_acs = false;
        a.addr_poi_merge = true;
        a.specialpoi = 0;
        a.sort_rule = 0;
        a.citysuggestion = true;
        a.loc_strict = false;
        a.direct_jump = true;
        a.scenario = 2;
        a.utd_sceneid = "101000";
        a.transfer_filter_flag = "";
        a.transfer_pdheatmap = "";
        a.cluster_state = "";
        a.geoobj_adjust = "";
        if (aen.b() != null) {
            a.city = aen.b();
        }
        if (aen.c != null) {
            a.search_sceneid = aen.c;
        }
        if (aen.d != null) {
            a.superid = aen.d;
        }
        if (aen.f != null) {
            a.query_scene = aen.f;
        }
        if (aen.g != null) {
            a.utd_sceneid = aen.g;
        }
        return a;
    }

    @Nullable
    private static InfoliteParam a(ael ael) {
        InfoliteParam infoliteParam;
        int a = ael.a();
        if (a != 3) {
            switch (a) {
                case 0:
                    infoliteParam = aev.a(AppManager.getInstance().getUserLocInfo(), ael.b, ael.a);
                    break;
                case 1:
                    infoliteParam = aev.a(AppManager.getInstance().getUserLocInfo(), ael.b, ael.c);
                    break;
                default:
                    infoliteParam = null;
                    break;
            }
        } else {
            infoliteParam = aev.a(AppManager.getInstance().getUserLocInfo(), ael.b, ael.c(), ael.c);
        }
        if (infoliteParam == null) {
            return null;
        }
        infoliteParam.pagenum = ael.d;
        infoliteParam.scenario = ael.h;
        infoliteParam.pagesize = ael.k;
        if (ael.l != null) {
            infoliteParam.superid = ael.l;
        }
        if (ael.g != null) {
            infoliteParam.need_recommend = ael.g;
        }
        if (ael.c != null) {
            infoliteParam.geoobj = bcz.a(ael.c);
        }
        if (ael.e != null) {
            infoliteParam.range = ael.e;
        }
        if (ael.f != null) {
            infoliteParam.sugadcode = ael.f;
        }
        if (ael.i != null) {
            infoliteParam.isBrand = ael.i;
        }
        aeo aeo = ael.j;
        if (aeo != null) {
            if (aeo.c != null) {
                infoliteParam.transfer_nearby_bucket = aeo.c;
            }
            if (aeo.e != null) {
                infoliteParam.transfer_nearby_keyindex = aeo.e;
            }
            if (aeo.a != null) {
                infoliteParam.transfer_pdheatmap = aeo.a;
            }
            if (aeo.b != null) {
                infoliteParam.transfer_mode = aeo.b;
            }
            if (aeo.f != null) {
                infoliteParam.transparent = aeo.f;
            }
            if (aeo.d != null) {
                infoliteParam.transfer_nearby_time_opt = aeo.d;
            }
            if (aeo.g != null) {
                infoliteParam.transfer_nearby_time_opt = aeo.d;
            }
        }
        return infoliteParam;
    }
}
