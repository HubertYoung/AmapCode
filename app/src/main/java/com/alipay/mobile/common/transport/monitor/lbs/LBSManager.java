package com.alipay.mobile.common.transport.monitor.lbs;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;

public class LBSManager {
    private static LBSManager d = null;
    private Object a;
    private long b;
    private long c;

    public static LBSManager getInstance() {
        if (d != null) {
            return d;
        }
        synchronized (LBSManager.class) {
            try {
                if (d == null) {
                    d = new LBSManager();
                }
            }
        }
        return d;
    }

    private LBSManager() {
        this.b = System.currentTimeMillis();
        this.c = 900000;
        this.b = System.currentTimeMillis();
    }

    private synchronized Object a() {
        Object obj = null;
        synchronized (this) {
            try {
                if (System.currentTimeMillis() - this.b > this.c) {
                    this.a = null;
                }
                if (this.a != null) {
                    obj = this.a;
                } else {
                    this.b = System.currentTimeMillis();
                    Class locationManagerProxy = Class.forName("com.alipay.mobile.common.lbs.LBSLocationManagerProxy");
                    Object locationManagerProxyObject = locationManagerProxy.getMethod("getInstance", new Class[0]).invoke(locationManagerProxy, new Object[0]);
                    this.a = locationManagerProxy.getMethod("getLastKnownLocation", new Class[]{Context.class}).invoke(locationManagerProxyObject, new Object[]{TransportEnvUtil.getContext()});
                    obj = this.a;
                }
            } catch (Throwable ex) {
                LogCatUtil.error((String) "LBSManager", "getLBSLocation ex:" + ex.toString());
            }
        }
        return obj;
    }

    private static String b() {
        return "1";
    }

    private String c() {
        String locationTime = d();
        if (!TextUtils.equals("-", locationTime)) {
            return String.valueOf((System.currentTimeMillis() - Long.parseLong(locationTime)) / 1000);
        }
        return "-";
    }

    private String d() {
        try {
            Object lbsLocationObject = a();
            if (lbsLocationObject != null) {
                String locationtime = String.valueOf(lbsLocationObject.getClass().getMethod("getLocationtime", new Class[0]).invoke(lbsLocationObject, new Object[0]));
                if (!TextUtils.isEmpty(locationtime)) {
                    return locationtime;
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "LBSManager", "getLocationtime,ex:" + ex.toString());
        }
        return "-";
    }

    private String e() {
        try {
            Object lbsLocationObject = a();
            if (lbsLocationObject != null) {
                String lat = String.valueOf(lbsLocationObject.getClass().getMethod("getLatitude", new Class[0]).invoke(lbsLocationObject, new Object[0]));
                if (!TextUtils.isEmpty(lat)) {
                    return lat;
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "LBSManager", "getLatitude,ex:" + ex.toString());
        }
        return "-";
    }

    private String f() {
        try {
            Object lbsLocationObject = a();
            if (lbsLocationObject != null) {
                String lng = String.valueOf(lbsLocationObject.getClass().getMethod("getLongitude", new Class[0]).invoke(lbsLocationObject, new Object[0]));
                if (!TextUtils.isEmpty(lng)) {
                    return lng;
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "LBSManager", "getLongitude,ex:" + ex.toString());
        }
        return "-";
    }

    private String g() {
        try {
            Object lbsLocationObject = a();
            if (lbsLocationObject != null) {
                String cityCode = String.valueOf(lbsLocationObject.getClass().getMethod("getCityCode", new Class[0]).invoke(lbsLocationObject, new Object[0]));
                if (!TextUtils.isEmpty(cityCode)) {
                    return cityCode;
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "LBSManager", "getCityCode,ex:" + ex.toString());
        }
        return "-";
    }

    private String h() {
        try {
            Object lbsLocationObject = a();
            if (lbsLocationObject != null) {
                String adCode = String.valueOf(lbsLocationObject.getClass().getMethod("getAdCode", new Class[0]).invoke(lbsLocationObject, new Object[0]));
                if (!TextUtils.isEmpty(adCode)) {
                    return adCode;
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "LBSManager", "getAdCode,ex:" + ex.toString());
        }
        return "-";
    }

    private String i() {
        try {
            Object lbsLocationObject = a();
            if (lbsLocationObject != null) {
                String accuracy = String.valueOf(lbsLocationObject.getClass().getMethod("getAccuracy", new Class[0]).invoke(lbsLocationObject, new Object[0]));
                if (!TextUtils.isEmpty(accuracy)) {
                    return accuracy;
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "LBSManager", "getAccuracy,ex:" + ex.toString());
        }
        return "-";
    }

    private String j() {
        try {
            Object lbsLocationObject = a();
            if (lbsLocationObject != null) {
                String country = String.valueOf(lbsLocationObject.getClass().getMethod("getCountry", new Class[0]).invoke(lbsLocationObject, new Object[0]));
                if (!TextUtils.isEmpty(country)) {
                    return country;
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "LBSManager", "getCountry,ex:" + ex.toString());
        }
        return "-";
    }

    private String k() {
        try {
            Object lbsLocationObject = a();
            if (lbsLocationObject != null) {
                String province = String.valueOf(lbsLocationObject.getClass().getMethod("getProvince", new Class[0]).invoke(lbsLocationObject, new Object[0]));
                if (!TextUtils.isEmpty(province)) {
                    return province;
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "LBSManager", "getProvince,ex:" + ex.toString());
        }
        return "-";
    }

    private String l() {
        try {
            Object lbsLocationObject = a();
            if (lbsLocationObject != null) {
                String city = String.valueOf(lbsLocationObject.getClass().getMethod("getCity", new Class[0]).invoke(lbsLocationObject, new Object[0]));
                if (!TextUtils.isEmpty(city)) {
                    return city;
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "LBSManager", "getCity,ex:" + ex.toString());
        }
        return "-";
    }

    private String m() {
        try {
            Object lbsLocationObject = a();
            if (lbsLocationObject != null) {
                String district = String.valueOf(lbsLocationObject.getClass().getMethod("getDistrict", new Class[0]).invoke(lbsLocationObject, new Object[0]));
                if (!TextUtils.isEmpty(district)) {
                    return district;
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "LBSManager", "getDistrict,ex:" + ex.toString());
        }
        return "-";
    }

    private String n() {
        try {
            Object lbsLocationObject = a();
            if (lbsLocationObject != null) {
                String aoiname = String.valueOf(lbsLocationObject.getClass().getMethod("getAoiname", new Class[0]).invoke(lbsLocationObject, new Object[0]));
                if (!TextUtils.isEmpty(aoiname) && !"null".equalsIgnoreCase(aoiname)) {
                    return aoiname;
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "LBSManager", "getAoiname,ex:" + ex.toString());
        }
        return "-";
    }

    private String o() {
        if (a() == null) {
            LogCatUtil.debug("LBSManager", "getLBSLocation is null,return");
            return "";
        }
        StringBuilder lbsStr = new StringBuilder();
        lbsStr.append(b()).append("_").append(c()).append("_");
        lbsStr.append(e()).append("_").append(f()).append("_");
        lbsStr.append(g()).append("_").append(h()).append("_").append(i()).append("_");
        return lbsStr.toString();
    }

    private String p() {
        if (a() == null) {
            LogCatUtil.debug("LBSManager", "getLBSLocation is null,return");
            return "";
        }
        StringBuilder lbsStrExt = new StringBuilder();
        lbsStrExt.append(j()).append("_");
        lbsStrExt.append(k()).append("_").append(l()).append("_");
        lbsStrExt.append(m()).append("_").append(n());
        return lbsStrExt.toString();
    }

    public String getReportLBSInfo() {
        try {
            if (!MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.LBS_DUMP))) {
                LogCatUtil.debug("LBSManager", "lbsDump off");
                return "";
            }
            String lbsLevel = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.LBS_LEVEL);
            if (TextUtils.equals(lbsLevel, "1")) {
                return o();
            }
            if (TextUtils.equals(lbsLevel, "2")) {
                return o() + p();
            }
            return "";
        } catch (Throwable ex) {
            LogCatUtil.error((String) "LBSManager", "getReportLBSInfo ex:" + ex.toString());
        }
    }
}
