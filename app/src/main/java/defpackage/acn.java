package defpackage;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.planhome.page.AbstractPlanHomePage;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.route.ajx.module.ModuleValues;
import com.autonavi.minimap.route.train.stationlist.Station;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONObject;

/* renamed from: acn reason: default package */
/* compiled from: PlanStartResultProxy */
public final class acn {
    private AbstractPlanHomePage a;

    public acn(AbstractPlanHomePage abstractPlanHomePage) {
        this.a = abstractPlanHomePage;
    }

    public final boolean a(IRouteHeaderEvent iRouteHeaderEvent, RouteType routeType, PageBundle pageBundle) {
        if (routeType == RouteType.TRAIN) {
            if (iRouteHeaderEvent == IRouteHeaderEvent.START_CLICK || iRouteHeaderEvent == IRouteHeaderEvent.END_CLICK) {
                bdo bdo = (bdo) a.a.a(bdo.class);
                if (bdo != null) {
                    if (iRouteHeaderEvent == IRouteHeaderEvent.START_CLICK) {
                        bdo.a(this.a, 1008);
                    } else if (iRouteHeaderEvent == IRouteHeaderEvent.END_CLICK) {
                        bdo.a(this.a, 1009);
                    }
                }
                return true;
            }
        } else if (routeType == RouteType.AIRTICKET) {
            if (iRouteHeaderEvent == IRouteHeaderEvent.START_CLICK || iRouteHeaderEvent == IRouteHeaderEvent.END_CLICK) {
                apl apl = (apl) a.a.a(apl.class);
                if (apl != null) {
                    if (pageBundle == null) {
                        pageBundle = new PageBundle();
                    }
                    pageBundle.putString("url", ModuleValues.URL_AIR_TICKET_CITY_CHOOSE);
                    if (iRouteHeaderEvent == IRouteHeaderEvent.START_CLICK) {
                        apl.a().a(pageBundle, 1012);
                    } else if (iRouteHeaderEvent == IRouteHeaderEvent.END_CLICK) {
                        apl.a().a(pageBundle, 1013);
                    }
                }
                return true;
            }
        } else if (routeType == RouteType.COACH && (iRouteHeaderEvent == IRouteHeaderEvent.START_CLICK || iRouteHeaderEvent == IRouteHeaderEvent.END_CLICK)) {
            atw atw = (atw) a.a.a(atw.class);
            if (atw != null) {
                if (iRouteHeaderEvent == IRouteHeaderEvent.START_CLICK) {
                    atw.a(this.a);
                } else if (iRouteHeaderEvent == IRouteHeaderEvent.END_CLICK) {
                    String str = "";
                    POI b = ade.a().b(true);
                    if (b != null) {
                        str = b.getAdCode();
                        if (TextUtils.isEmpty(str)) {
                            str = "";
                            if (b != null) {
                                li a2 = li.a();
                                if (a2 != null) {
                                    GeoPoint point = b.getPoint();
                                    if (point != null) {
                                        lj b2 = a2.b(point.x, point.y);
                                        if (b2 != null) {
                                            str = String.valueOf(b2.j);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    atw.a(this.a, str);
                }
            }
            return true;
        }
        return false;
    }

    public static boolean a(int i, ResultType resultType, PageBundle pageBundle) {
        if (ResultType.OK != resultType) {
            return false;
        }
        RouteType b = adf.a().b();
        if (b == RouteType.TRAIN) {
            if (i == 1008 || i == 1009) {
                if (pageBundle.containsKey("key_city") && pageBundle.getObject("key_city") != null && (pageBundle.getObject("key_city") instanceof Station)) {
                    Station station = (Station) pageBundle.getObject("key_city");
                    POI createPOI = POIFactory.createPOI(station.name, new GeoPoint(station.lon, station.lat));
                    createPOI.setId(station.poiid);
                    if (i == 1008) {
                        if (!a(createPOI, b())) {
                            ade.a().a(createPOI);
                        }
                    } else if (i == 1009 && !a(createPOI, a())) {
                        ade.a().b(createPOI);
                    }
                }
                return true;
            }
        } else if (b == RouteType.AIRTICKET) {
            if (i == 1012 || i == 1013) {
                if (pageBundle.containsKey(ModuleHistory.AJX_BACK_RETURN_DATA_KEY)) {
                    try {
                        JSONObject jSONObject = new JSONObject(pageBundle.getObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY).toString()).getJSONObject("data");
                        POI createPOI2 = POIFactory.createPOI(jSONObject.getString("name"), new GeoPoint(jSONObject.getDouble(LocationParams.PARA_FLP_AUTONAVI_LON), jSONObject.getDouble("lat")));
                        createPOI2.setId(jSONObject.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
                        createPOI2.getPoiExtra().put("IATA_CODE", jSONObject.optString("citycode"));
                        if (i == 1012) {
                            if (!a(createPOI2, b())) {
                                ade.a().a(createPOI2);
                            }
                        } else if (i == 1013 && !a(createPOI2, a())) {
                            ade.a().b(createPOI2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        } else if (b == RouteType.COACH && ((i == 1010 || i == 1011) && pageBundle.containsKey("key_city") && pageBundle.getObject("key_city") != null && (pageBundle.getObject("key_city") instanceof Station))) {
            Station station2 = (Station) pageBundle.getObject("key_city");
            POI createPOI3 = POIFactory.createPOI(station2.name, new GeoPoint(station2.lon, station2.lat));
            createPOI3.setId(station2.poiid);
            createPOI3.setAdCode(station2.valueOfA1());
            if (i == 1010) {
                if (!a(createPOI3, b())) {
                    ade.a().a(createPOI3);
                    atw atw = (atw) a.a.a(atw.class);
                    if (atw != null) {
                        atw.a().a();
                        atw.a().a(ade.a().b(true), ade.a().d(true));
                    }
                }
            } else if (i == 1011 && !a(createPOI3, a())) {
                ade.a().b(createPOI3);
                atw atw2 = (atw) a.a.a(atw.class);
                if (atw2 != null) {
                    atw2.a().b();
                    atw2.a().a(ade.a().b(true), ade.a().d(true));
                }
            }
        }
        return false;
    }

    private static POI a() {
        POI b = ade.a().b(true);
        if (b != null) {
            b.setName(acq.a().h());
        }
        return b;
    }

    private static POI b() {
        POI d = ade.a().d(true);
        if (d != null) {
            d.setName(acq.a().i());
        }
        return d;
    }

    private static boolean a(POI poi, POI poi2) {
        if (poi == null || poi2 == null || (!TextUtils.equals(poi.getName(), poi2.getName()) && !bnx.a(poi, poi2))) {
            return false;
        }
        ToastHelper.showToast("起点与终点不能相同");
        return true;
    }
}
