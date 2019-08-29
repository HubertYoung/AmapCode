package com.autonavi.bundle.routecommon.entity;

import android.content.res.Resources;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ExTrainPath implements axb, Serializable {
    private static final long serialVersionUID = 8403677117204327415L;
    private ArrayList<AlterTrain> alterList;
    public int distance;
    public int endX;
    public int endY;
    public String id;
    private POI mShareFromPoi = null;
    private POI mShareToPoi = null;
    public int[] mXs;
    public int[] mYs;
    public double minPrice;
    public String name;
    private ArrayList<Price> priceList;
    public String sad;
    public String scord;
    public int sin;
    public String sint;
    public String spoiid;
    public String sst;
    public String sstid;
    public int startX;
    public int startY;
    private ArrayList<Station> stationList;
    public String tad;
    public String tcord;
    public int time;
    public int tou;
    public String tout;
    public String tpoiid;
    public String trip;
    public String tst;
    public String tstid;
    public int type;
    public String viaint;
    public String viast;
    public String viastcord;
    public String viastid;
    public String viawait;

    public static class AlterTrain implements Serializable {
        private static final long serialVersionUID = -8213502625031372383L;
        public String id;
        public String name;

        public String getSimpleName() {
            if (this.name != null) {
                return this.name.contains("(") ? this.name.substring(0, this.name.indexOf("(")) : "";
            }
            return "";
        }
    }

    public static class Price implements Serializable {
        private static final long serialVersionUID = 5950652264211756238L;
        public int type;
        public double value;

        public String getType() {
            Resources resources = AMapAppGlobal.getApplication().getResources();
            int i = this.type;
            if (i == 0) {
                return "";
            }
            switch (i) {
                case 3:
                    return resources.getString(R.string.price_dongwo);
                case 4:
                    return resources.getString(R.string.price_yirenruanbao);
                case 5:
                    return resources.getString(R.string.price_sightseeing);
                case 6:
                    return resources.getString(R.string.price_tedengruanzuo);
                case 7:
                    return resources.getString(R.string.price_first_class);
                case 8:
                    return resources.getString(R.string.price_second_class1);
                case 9:
                    return resources.getString(R.string.price_ultimate_class);
                case 10:
                    return resources.getString(R.string.price_yingzuo);
                case 11:
                    return resources.getString(R.string.price_ruanzuo);
                case 12:
                    return resources.getString(R.string.price_ruanzuoyidengzuo);
                case 13:
                    return resources.getString(R.string.price_ruanzuoerdengzuo);
                case 14:
                    return resources.getString(R.string.price_woxishangpu);
                case 15:
                    return resources.getString(R.string.price_woxizhongpu);
                case 16:
                    return resources.getString(R.string.price_woxixiapu);
                case 17:
                    return resources.getString(R.string.price_ruanwoshangpu);
                case 18:
                    return resources.getString(R.string.price_ruanwoxiapu);
                case 19:
                    return resources.getString(R.string.price_gaoji_ruanwoshangpu);
                case 20:
                    return resources.getString(R.string.price_gaoji_ruanwoxiapu);
                case 21:
                    return resources.getString(R.string.price_business_class1);
                case 22:
                    return resources.getString(R.string.price_zuoxi);
                case 23:
                    return resources.getString(R.string.price_woxishangpu);
                case 24:
                    return resources.getString(R.string.price_woxizhongpu);
                case 25:
                    return resources.getString(R.string.price_woxixiapu);
                default:
                    switch (i) {
                        case 30:
                            return resources.getString(R.string.price_economy_class);
                        case 31:
                            return resources.getString(R.string.price_business_class2);
                        default:
                            switch (i) {
                                case 40:
                                    return resources.getString(R.string.price_economy_class);
                                case 41:
                                    return resources.getString(R.string.price_third_class);
                                case 42:
                                    return resources.getString(R.string.price_second_class2);
                                case 43:
                                    return resources.getString(R.string.price_deluxe_class);
                                default:
                                    return "";
                            }
                    }
            }
        }

        public String toString() {
            if (this.value == 0.0d) {
                return "";
            }
            double floor = this.value - Math.floor(this.value);
            Resources resources = AMapAppGlobal.getApplication().getResources();
            if (floor > 0.0d) {
                StringBuilder sb = new StringBuilder();
                sb.append(getType());
                sb.append(this.value);
                sb.append(resources.getString(R.string.rmb));
                return sb.toString();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getType());
            sb2.append((int) this.value);
            sb2.append(resources.getString(R.string.rmb));
            return sb2.toString();
        }
    }

    public static class Station implements Serializable {
        private static final long serialVersionUID = -5800221358725940191L;
        public String id;
        public double lat;
        public double lon;
        public String name;
        public String time;
        public int wait;
        public int x;
        public int y;

        public String getStationDes() {
            Resources resources = AMapAppGlobal.getApplication().getResources();
            StringBuilder sb = new StringBuilder();
            sb.append(ExTrainPath.get24HourTimeStr(this.time));
            sb.append(Token.SEPARATOR);
            sb.append(resources.getString(R.string.to));
            sb.append(Token.SEPARATOR);
            sb.append(this.name);
            sb.append(Token.SEPARATOR);
            sb.append(resources.getString(R.string.stop));
            sb.append(this.wait);
            sb.append(resources.getString(R.string.minute));
            return sb.toString();
        }
    }

    public POI getShareFromPOI() {
        if (this.mShareFromPoi == null) {
            this.mShareFromPoi = POIFactory.createPOI(getTrainStartStation(), new GeoPoint(this.startX, this.startY));
            this.mShareFromPoi.setId(this.spoiid);
        }
        return this.mShareFromPoi;
    }

    public POI getShareToPOI() {
        if (this.mShareToPoi == null) {
            this.mShareToPoi = POIFactory.createPOI(getTrainEndStation(), new GeoPoint(this.endX, this.endY));
            this.mShareToPoi.setId(this.tpoiid);
        }
        return this.mShareToPoi;
    }

    public ArrayList<Price> getPriceList() {
        if (this.priceList == null) {
            this.priceList = new ArrayList<>();
        }
        return this.priceList;
    }

    public ArrayList<AlterTrain> getAlterList() {
        if (this.alterList == null) {
            this.alterList = new ArrayList<>();
        }
        return this.alterList;
    }

    public void setAlterList(ArrayList<AlterTrain> arrayList) {
        this.alterList = arrayList;
    }

    public ArrayList<Station> getStationList() {
        if (this.stationList == null) {
            this.stationList = new ArrayList<>();
        }
        return this.stationList;
    }

    public String getPathName() {
        StringBuilder sb = new StringBuilder();
        sb.append(getType());
        sb.append(this.name);
        return sb.toString();
    }

    public String getStartEndName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.sst);
        sb.append("-");
        sb.append(this.tst);
        return sb.toString();
    }

    public String getUpStationDes() {
        StringBuilder sb = new StringBuilder();
        sb.append(get24HourTimeStr(this.sint));
        sb.append(Token.SEPARATOR);
        sb.append(this.sst);
        sb.append(Token.SEPARATOR);
        sb.append(AMapAppGlobal.getApplication().getString(R.string.get_on));
        return sb.toString();
    }

    public String getDownStationDes() {
        StringBuilder sb = new StringBuilder();
        sb.append(get24HourTimeStr(this.tout));
        sb.append(Token.SEPARATOR);
        sb.append(AMapAppGlobal.getApplication().getString(R.string.arrive));
        sb.append(Token.SEPARATOR);
        sb.append(this.tst);
        return sb.toString();
    }

    public String getType() {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        switch (this.type) {
            case 2010:
                return resources.getString(R.string.railway_regular_puke);
            case 2011:
                return resources.getString(R.string.railway_G_gaotie);
            case 2012:
                return resources.getString(R.string.railway_D_dongche);
            case 2013:
                return resources.getString(R.string.railway_C_chengji);
            case 2014:
                return resources.getString(R.string.railway_Z_zhidatekuai);
            case 2015:
                return resources.getString(R.string.railway_T_tekuai);
            case 2016:
                return resources.getString(R.string.railway_K_linke);
            case 2017:
                return resources.getString(R.string.railway_L_Y_jiaoquxian);
            case 2018:
                return resources.getString(R.string.railway_S_jiaoquxian);
            default:
                return "";
        }
    }

    public String getPriceStr() {
        StringBuilder sb = new StringBuilder();
        if (this.priceList != null && this.priceList.size() > 0) {
            Iterator<Price> it = this.priceList.iterator();
            while (it.hasNext()) {
                Price next = it.next();
                if (next.value > 0.0d) {
                    sb.append(next.toString());
                    sb.append("  ");
                }
            }
        }
        if (sb.length() == 0) {
            return "";
        }
        return sb.toString();
    }

    public String getDestDesc() {
        return this.tst;
    }

    public String getCostDesc() {
        StringBuilder sb = new StringBuilder();
        if (this.distance > 0) {
            sb.append("(");
            sb.append(getLengDesc());
            sb.append("/");
        }
        if (sb.length() > 0) {
            sb.append(lf.a(this.time * 60));
        } else {
            sb.append("(");
            sb.append(lf.a(this.time * 60));
        }
        if (this.minPrice > 0.0d) {
            double floor = this.minPrice - Math.floor(this.minPrice);
            Resources resources = AMapAppGlobal.getApplication().getResources();
            if (floor > 0.0d) {
                sb.append("/");
                sb.append(this.minPrice);
                sb.append(resources.getString(R.string.rmb));
            } else {
                sb.append("/");
                sb.append((int) this.minPrice);
                sb.append(resources.getString(R.string.rmb));
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public String getPathDesc() {
        StringBuilder sb = new StringBuilder();
        Resources resources = AMapAppGlobal.getApplication().getResources();
        sb.append(this.name);
        if (this.alterList != null && this.alterList.size() > 1) {
            sb.append(resources.getString(R.string.wait));
            sb.append(this.alterList.size());
            sb.append(resources.getString(R.string.gecheci));
        }
        return sb.toString();
    }

    public int getPathIcon() {
        return R.drawable.direction_bus_list_train;
    }

    public String getEndStationTime() {
        String[] split = this.tout.split(":");
        if (split.length == 2 && !TextUtils.isEmpty(split[0]) && TextUtils.isDigitsOnly(split[0])) {
            int parseInt = Integer.parseInt(split[0]);
            int i = parseInt / 24;
            if (i > 0) {
                Resources resources = AMapAppGlobal.getApplication().getResources();
                StringBuilder sb = new StringBuilder();
                sb.append(resources.getString(R.string.num_count));
                sb.append(i + 1);
                sb.append(resources.getString(R.string.day));
                sb.append("  (");
                sb.append(parseInt % 24);
                sb.append(":");
                sb.append(split[1]);
                sb.append(")");
                return sb.toString();
            }
        }
        return this.tout;
    }

    public String getStartDesc() {
        return this.sst;
    }

    public String getMainDesc() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.trip);
        sb.append("(");
        sb.append(this.stationList.size() + 1);
        sb.append(AMapPageUtil.getAppContext().getString(R.string.bus_stop));
        sb.append(")");
        return sb.toString();
    }

    public String getSubDesc() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.sst);
        sb.append("→");
        sb.append(this.tst);
        return sb.toString();
    }

    public static String get24HourTimeStr(String str) {
        String sb;
        if (str == null) {
            return str;
        }
        try {
            String[] split = str.split(":");
            int parseInt = Integer.parseInt(split[0]) % 24;
            if (parseInt > 9) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(parseInt % 24);
                sb2.append(":");
                sb2.append(split[1]);
                sb = sb2.toString();
            } else {
                StringBuilder sb3 = new StringBuilder("0");
                sb3.append(parseInt % 24);
                sb3.append(":");
                sb3.append(split[1]);
                sb = sb3.toString();
            }
            return sb;
        } catch (Exception e) {
            kf.a((Throwable) e);
            return str;
        }
    }

    public int getCostTime() {
        return this.time * 60;
    }

    public int getCostDistance() {
        return this.distance;
    }

    public double getCostFee() {
        return this.minPrice;
    }

    private String getLengDesc() {
        if (this.distance < 1000) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.distance);
            sb.append("米");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.distance / 1000);
        sb2.append("公里");
        return sb2.toString();
    }

    public SpannableString getPathSpannableDesc() {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (this.alterList == null || this.alterList.size() <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.trip);
            sb.append("  ");
            sb.append(getTrainSectionDesc());
            int length = this.trip != null ? this.trip.length() : 0;
            SpannableString spannableString = new SpannableString(sb);
            spannableString.setSpan(new StyleSpan(1), 0, length, 33);
            spannableString.setSpan(new StyleSpan(1), length + 2, sb.length(), 33);
            return spannableString;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.trip);
        sb2.append("  ");
        sb2.append(getTrainSectionDesc());
        int length2 = sb2.length();
        sb2.append(Token.SEPARATOR);
        sb2.append(resources.getString(R.string.wait));
        sb2.append(this.alterList.size() + 1);
        sb2.append(resources.getString(R.string.gecheci));
        SpannableString spannableString2 = new SpannableString(sb2);
        int length3 = this.trip != null ? this.trip.length() : 0;
        spannableString2.setSpan(new StyleSpan(1), 0, length3, 33);
        spannableString2.setSpan(new StyleSpan(1), length3 + 2, length2, 33);
        spannableString2.setSpan(new ForegroundColorSpan(resources.getColor(R.color.f_c_4)), length2, spannableString2.length(), 33);
        spannableString2.setSpan(new AbsoluteSizeSpan(resources.getDimensionPixelSize(R.dimen.f_s_13)), length2, spannableString2.length(), 33);
        sb2.append(spannableString2);
        return spannableString2;
    }

    public String getTrainSectionDesc() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(this.sst);
        sb.append("-");
        sb.append(this.tst);
        sb.append(")");
        return sb.toString();
    }

    public String getTrainCostTime() {
        return lf.d(this.time * 60);
    }

    public String getTrainStartStation() {
        return formatTrainStationName(this.sst);
    }

    public String getTrainEndStation() {
        return formatTrainStationName(this.tst);
    }

    private static String formatTrainStationName(String str) {
        if (TextUtils.isEmpty(str) || str.endsWith("站")) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("站");
        return sb.toString();
    }
}
