package com.autonavi.bundle.routecommon.entity;

import android.content.res.Resources;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ExtBusPath implements Serializable {
    private static final long serialVersionUID = 3694016702260747118L;
    public double cost;
    public int distance;
    private ArrayList<axb> mBuspathList;
    public POI mFromPoi = null;
    public POI mToPoi = null;
    private ArrayList<ExBusPathSegment> segmentList;
    public int tag = -1;
    public int time;

    public static class ExBusPathSegment implements Serializable {
        private static final long serialVersionUID = 1356013160888886635L;
        private ArrayList<axb> buspathList;

        public ArrayList<axb> getBusPathList() {
            if (this.buspathList == null) {
                this.buspathList = new ArrayList<>();
            }
            return this.buspathList;
        }
    }

    private String getCost(double d) {
        if (d < 0.0d) {
            throw new IllegalArgumentException("wrong cost: ".concat(String.valueOf(d)));
        }
        StringBuilder sb = new StringBuilder();
        long round = Math.round(d);
        if (Double.compare(d, (double) round) == 0) {
            sb.append(round);
        } else {
            sb.append(d);
        }
        return sb.toString();
    }

    public String getTimeStr() {
        return lf.d(this.time * 60);
    }

    public String getDistanceStr() {
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

    public int getTagResId() {
        switch (this.tag) {
            case 0:
                return R.drawable.extbus_tag_fast;
            case 1:
                return R.drawable.extbus_tag_fee;
            default:
                return 0;
        }
    }

    public String getMainDes() {
        return getTimeStr();
    }

    public String getSubDes() {
        if (this.cost <= 0.0d) {
            return "";
        }
        StringBuilder sb = new StringBuilder("(");
        sb.append(this.cost);
        sb.append(AMapAppGlobal.getApplication().getString(R.string.starting_with_cost_rmb));
        sb.append(")");
        return sb.toString();
    }

    public SpannableString getListTitleSP() {
        String ToDBC = ToDBC(getMainDes());
        String ToDBC2 = ToDBC(getSubDes());
        StringBuilder sb = new StringBuilder();
        sb.append(ToDBC);
        sb.append(ToDBC2);
        String sb2 = sb.toString();
        SpannableString spannableString = new SpannableString(sb2);
        try {
            spannableString.setSpan(new AbsoluteSizeSpan(16, true), 0, ToDBC.length(), 33);
            spannableString.setSpan(new AbsoluteSizeSpan(13, true), ToDBC.length(), sb2.length(), 33);
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return spannableString;
    }

    public static String ToDBC(String str) {
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == 12288) {
                charArray[i] = ' ';
            } else if (charArray[i] > 65280 && charArray[i] < 65375) {
                charArray[i] = (char) (charArray[i] - 65248);
            }
        }
        return new String(charArray);
    }

    public String getTitleDes() {
        StringBuilder sb = new StringBuilder();
        Iterator<axb> it = getBusPathList().iterator();
        while (it.hasNext()) {
            axb next = it.next();
            if (next instanceof ExTrainPath) {
                if (sb.length() > 0) {
                    sb.append(" > ");
                }
                ExTrainPath exTrainPath = (ExTrainPath) next;
                sb.append(exTrainPath.trip);
                sb.append(exTrainPath.getTrainSectionDesc());
            } else if (next instanceof BusPath) {
                if (sb.length() > 0) {
                    sb.append(" > ");
                }
                sb.append(next.getPathDesc().replace(SimpleComparison.GREATER_THAN_OPERATION, " > "));
            }
        }
        return sb.toString();
    }

    public String getSubTitleDes() {
        Iterator<axb> it = getBusPathList().iterator();
        int i = 0;
        while (it.hasNext()) {
            axb next = it.next();
            if (next instanceof BusPath) {
                i += ((BusPath) next).mAllFootLength;
            }
        }
        Resources resources = AMapAppGlobal.getApplication().getResources();
        StringBuilder sb = new StringBuilder();
        sb.append(lf.d(this.time * 60));
        if (this.cost > 0.0d) {
            sb.append(Token.SEPARATOR);
            sb.append(getCost(this.cost));
            sb.append(resources.getString(R.string.rmb));
        }
        if (i > 0) {
            sb.append(Token.SEPARATOR);
            sb.append(resources.getString(R.string.walking));
            sb.append(cfe.a(i));
        }
        return sb.toString();
    }

    public ArrayList<ExBusPathSegment> getSegmentList() {
        if (this.segmentList == null) {
            this.segmentList = new ArrayList<>();
        }
        return this.segmentList;
    }

    public ArrayList<axb> getBusPathList() {
        if (this.mBuspathList == null) {
            this.mBuspathList = new ArrayList<>();
            if (this.segmentList != null && this.segmentList.size() > 0) {
                Iterator<ExBusPathSegment> it = this.segmentList.iterator();
                while (it.hasNext()) {
                    this.mBuspathList.addAll(it.next().getBusPathList());
                }
            }
        }
        return this.mBuspathList;
    }
}
