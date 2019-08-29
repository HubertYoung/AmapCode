package com.autonavi.minimap.route.train.model;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class TrainPlanBaseInfoItem implements Serializable {
    private int cheapestSeatRemain;
    public int group;
    private boolean isAfterSorted;
    public boolean isNeedSwitch = true;
    public boolean isTrainArrivalAtLastStation;
    public boolean isTrainDepartureAtFirstStation;
    private float minPriceAvailed;
    public ArrayList<eiw> seatsRemainedList;
    public int sortTag;
    public int ticketTypeForStudent;
    public int totalTimeCost;
    public String trainArrivalName;
    public String trainArrivalStationAdcode;
    public GeoPoint trainArrivalStationGeoPoint;
    public String trainArrivalStationId;
    public String trainArrivalTime;
    public long trainArrivalTimeToCompare;
    public String trainDepartureName;
    public String trainDepartureStationAdcode;
    public GeoPoint trainDepartureStationGeoPoint;
    public String trainDepartureStationId;
    public String trainDepartureTime;
    public long trainDepartureTimeToCompare;
    public int trainDistance;
    public String trainName;
    public String trainPlanId;
    public int trainRunningTime;
    public int trainTicketRemainOfAllSeatType;
    public int trainType;
    public String trip;
    public ArrayList<a> viaStationInfos = new ArrayList<>();

    public static class a {
        public String a;
        public Double b;
        public Double c;
        public String d;
        public String e;
        public String f;

        public a() {
        }

        public a(String str, String str2, String str3, String str4) {
            this.a = str;
            this.d = str2;
            this.e = str3;
            this.f = str4;
        }
    }

    private void sortSeatByPriceIgnoreRemain() {
    }

    public String getCheapestSeatRemainMountTip(Context context) {
        return "";
    }

    public boolean isContainsTicketTypeForStudent() {
        Iterator<eiw> it = this.seatsRemainedList.iterator();
        while (it.hasNext()) {
            if (it.next().d == 2) {
                return true;
            }
        }
        return false;
    }

    public TrainPlanBaseInfoItem() {
    }

    public TrainPlanBaseInfoItem(String str, String str2, String str3, String str4, int i, int i2, String str5, ArrayList<eiw> arrayList) {
        this.trainDepartureTime = str;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            this.trainDepartureTimeToCompare = simpleDateFormat.parse(str).getTime();
            this.trainArrivalTimeToCompare = simpleDateFormat.parse(str).getTime();
        } catch (Exception unused) {
            this.trainDepartureTimeToCompare = 0;
            this.trainArrivalTimeToCompare = 0;
        }
        this.trainArrivalTime = str2;
        this.trainDepartureName = str3;
        this.trainArrivalName = str4;
        this.trainDistance = i;
        this.trainRunningTime = i2;
        this.trip = str5;
        this.seatsRemainedList = arrayList;
        this.isAfterSorted = false;
        sortSeatsByPriceAndRemained();
    }

    public boolean sortSeatsByPriceAndRemained() {
        if (this.seatsRemainedList == null) {
            return false;
        }
        if (!this.isAfterSorted) {
            sortSeatByPriceIgnoreRemain();
            this.seatsRemainedList = ejw.a(this.seatsRemainedList);
        }
        float f = 1000000.0f;
        Iterator<eiw> it = this.seatsRemainedList.iterator();
        int i = 0;
        while (it.hasNext()) {
            eiw next = it.next();
            float f2 = next.c;
            int i2 = next.b;
            if (f2 < f && i2 > 0) {
                i = i2;
                f = f2;
            }
        }
        if (i == 0) {
            this.minPriceAvailed = 0.0f;
            this.cheapestSeatRemain = 0;
        } else {
            this.minPriceAvailed = f;
            this.cheapestSeatRemain = i;
        }
        this.isAfterSorted = true;
        return true;
    }

    public String getMinPrice(Context context) {
        if (context == null || !this.isAfterSorted) {
            sortSeatsByPriceAndRemained();
        }
        if (this.minPriceAvailed == 0.0f) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.minPriceAvailed - ((float) ((int) this.minPriceAvailed)) == 0.0f ? (float) ((int) this.minPriceAvailed) : this.minPriceAvailed);
        return sb.toString();
    }

    public String getFormatedArrivalTime() {
        String str;
        if (TextUtils.isEmpty(this.trainArrivalTime)) {
            return "";
        }
        String[] split = this.trainArrivalTime.split(":");
        try {
            int parseInt = Integer.parseInt(split[0]) % 24;
            if (parseInt < 10) {
                StringBuilder sb = new StringBuilder("0");
                sb.append(parseInt);
                sb.append(":");
                sb.append(split[1]);
                str = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(parseInt);
                sb2.append(":");
                sb2.append(split[1]);
                str = sb2.toString();
            }
            return str;
        } catch (NumberFormatException unused) {
            return "";
        }
    }

    public SpannableStringBuilder getMinPriceTip(Context context) {
        if (getSeatTypeSize() == 0) {
            return new SpannableStringBuilder("");
        }
        if (!this.isAfterSorted) {
            sortSeatByPriceIgnoreRemain();
            this.seatsRemainedList = ejw.a(this.seatsRemainedList);
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(lh.a(context, R.string.train_ticket_rmb));
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(agn.a(context, 12.0f)), 0, 1, 33);
        spannableStringBuilder.append(getMinPriceWithoutSeat(context));
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append(lh.a(context, R.string.train_ticket_beyond));
        spannableStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.f_c_3)), length, spannableStringBuilder.length(), 33);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(agn.a(context, 12.0f)), length, spannableStringBuilder.length(), 33);
        if (this.trainTicketRemainOfAllSeatType != 0) {
            return spannableStringBuilder;
        }
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(spannableStringBuilder);
        spannableStringBuilder2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.f_c_3)), 0, spannableStringBuilder.length(), 33);
        return new SpannableStringBuilder(spannableStringBuilder2);
    }

    public String getMinPriceWithoutSeat(Context context) {
        if (this.seatsRemainedList == null || this.seatsRemainedList.size() <= 0) {
            return "";
        }
        float f = this.seatsRemainedList.get(0).c;
        Iterator<eiw> it = this.seatsRemainedList.iterator();
        while (it.hasNext()) {
            float f2 = it.next().c;
            if (f2 < f) {
                f = f2;
            }
        }
        int i = (int) f;
        if (f - ((float) i) != 0.0f) {
            return String.valueOf(f);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        return sb.toString();
    }

    public int getCheapestSeatRemainMount(Context context) {
        if (context == null || !this.isAfterSorted) {
            sortSeatsByPriceAndRemained();
        }
        return this.cheapestSeatRemain;
    }

    public SpannableStringBuilder getCheapestSeatItemDescription(Context context) {
        String str;
        if (context == null) {
            return new SpannableStringBuilder("");
        }
        if (!this.isAfterSorted) {
            sortSeatsByPriceAndRemained();
        }
        int cheapestSeatRemainMount = getCheapestSeatRemainMount(context);
        if (this.trainTicketRemainOfAllSeatType < 0) {
            return new SpannableStringBuilder("");
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("");
        if (cheapestSeatRemainMount != 0) {
            if (cheapestSeatRemainMount > 20) {
                str = Token.SEPARATOR;
            } else {
                StringBuilder sb = new StringBuilder("仅剩");
                sb.append(this.cheapestSeatRemain);
                sb.append("张");
                str = sb.toString();
            }
            spannableStringBuilder.append(str);
        } else {
            spannableStringBuilder.append("已售罄");
            spannableStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.f_c_3)), 0, spannableStringBuilder.length(), 33);
        }
        return spannableStringBuilder;
    }

    public SpannableStringBuilder getAlltypeSeatItemDescription(Context context) {
        String str;
        if (context == null) {
            return new SpannableStringBuilder("");
        }
        if (!this.isAfterSorted) {
            sortSeatsByPriceAndRemained();
        }
        int i = this.trainTicketRemainOfAllSeatType;
        if (this.trainTicketRemainOfAllSeatType < 0) {
            return new SpannableStringBuilder("");
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("");
        if (i != 0) {
            if (i > 20) {
                str = Token.SEPARATOR;
            } else {
                StringBuilder sb = new StringBuilder("仅剩");
                sb.append(this.trainTicketRemainOfAllSeatType);
                sb.append("张");
                str = sb.toString();
            }
            spannableStringBuilder.append(str);
        } else {
            spannableStringBuilder.append("已售罄");
            spannableStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.f_c_3)), 0, spannableStringBuilder.length(), 33);
        }
        return spannableStringBuilder;
    }

    public SpannableStringBuilder getLeveledSeatDescription(Context context, int i) {
        int i2;
        if (context == null || this.seatsRemainedList == null || i >= this.seatsRemainedList.size()) {
            return new SpannableStringBuilder("");
        }
        String str = "";
        Iterator<eiw> it = this.seatsRemainedList.iterator();
        int i3 = 0;
        while (true) {
            if (!it.hasNext()) {
                i2 = 0;
                break;
            }
            eiw next = it.next();
            if (i3 == i) {
                str = next.a;
                i2 = next.b;
                break;
            }
            i3++;
        }
        if (i2 > 0) {
            return getSeatSpannableString(context, str, i2);
        }
        if (i2 == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(i2);
            sb.append("张");
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(sb.toString());
            spannableStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.f_c_3)), 0, spannableStringBuilder.length(), 33);
            return spannableStringBuilder;
        } else if (i2 == -1) {
            SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(str);
            int length = spannableStringBuilder2.length();
            spannableStringBuilder2.append("有票");
            spannableStringBuilder2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.f_c_12)), length, spannableStringBuilder2.length(), 33);
            return spannableStringBuilder2;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("--张");
            return new SpannableStringBuilder(sb2.toString());
        }
    }

    private SpannableStringBuilder getSeatSpannableString(Context context, String str, int i) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append(String.valueOf(i));
        spannableStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.f_c_12)), length, spannableStringBuilder.length(), 33);
        spannableStringBuilder.append(lh.a(context, R.string.train_ticket_unit));
        return spannableStringBuilder;
    }

    public int getSeatTypeSize() {
        return this.seatsRemainedList.size();
    }

    public a getNewViaStationInfo() {
        return new a();
    }

    public void addViaStationInfo(String str, String str2, String str3, String str4) {
        this.viaStationInfos.add(new a(str, str2, str3, str4));
    }

    public String getFormatRunningTime(Context context) {
        if (this.trainRunningTime < 60) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.trainRunningTime);
            sb.append("分钟");
            return sb.toString();
        } else if (this.trainRunningTime % 60 == 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.trainRunningTime / 60);
            sb2.append("小时整");
            return sb2.toString();
        } else {
            int i = this.trainRunningTime % 60;
            String concat = i < 10 ? "0".concat(String.valueOf(i)) : String.valueOf(i);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.trainRunningTime / 60);
            sb3.append("时");
            sb3.append(concat);
            sb3.append("分");
            return sb3.toString();
        }
    }
}
