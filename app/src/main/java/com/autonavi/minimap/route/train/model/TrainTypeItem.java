package com.autonavi.minimap.route.train.model;

import com.autonavi.minimap.R;
import java.util.HashMap;
import java.util.Map;

public final class TrainTypeItem implements Comparable<TrainTypeItem> {
    private static Map<TrainType, String> d;
    private static Map<TrainType, Integer> e;
    public TrainType a;
    public String b;
    public int c;

    public enum TrainType {
        ALL,
        G,
        C,
        D,
        Z,
        T,
        K,
        OTHERS
    }

    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        TrainTypeItem trainTypeItem = (TrainTypeItem) obj;
        if (trainTypeItem != null) {
            if (this.c < trainTypeItem.c) {
                return -1;
            }
            if (this.c > trainTypeItem.c) {
                return 1;
            }
        }
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TrainTypeItem trainTypeItem = (TrainTypeItem) obj;
        if (this.c == trainTypeItem.c && this.a == trainTypeItem.a) {
            return this.b == null ? trainTypeItem.b == null : this.b.equals(trainTypeItem.b);
        }
        return false;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (this.a != null ? this.a.hashCode() : 0) * 31;
        if (this.b != null) {
            i = this.b.hashCode();
        }
        return ((hashCode + i) * 31) + this.c;
    }

    static {
        HashMap hashMap = new HashMap();
        d = hashMap;
        hashMap.put(TrainType.ALL, eay.a(R.string.tt_train_new_type_all_types));
        d.put(TrainType.G, eay.a(R.string.tt_train_new_type_high_speed_harmony_train));
        d.put(TrainType.C, eay.a(R.string.tt_train_new_type_city_train));
        d.put(TrainType.D, eay.a(R.string.tt_train_new_type_harmony_train));
        d.put(TrainType.Z, eay.a(R.string.tt_train_new_type_direct_train));
        d.put(TrainType.T, eay.a(R.string.tt_train_new_type_special_fast_train));
        d.put(TrainType.K, eay.a(R.string.tt_train_new_type_fast_train));
        d.put(TrainType.OTHERS, eay.a(R.string.tt_train_new_type_other_trains));
        HashMap hashMap2 = new HashMap();
        e = hashMap2;
        hashMap2.put(TrainType.ALL, Integer.valueOf(0));
        e.put(TrainType.G, Integer.valueOf(1));
        e.put(TrainType.C, Integer.valueOf(2));
        e.put(TrainType.D, Integer.valueOf(3));
        e.put(TrainType.Z, Integer.valueOf(4));
        e.put(TrainType.T, Integer.valueOf(5));
        e.put(TrainType.K, Integer.valueOf(6));
        e.put(TrainType.OTHERS, Integer.valueOf(100));
    }

    public TrainTypeItem(TrainType trainType) {
        this(trainType, d.get(trainType), e.get(trainType).intValue());
    }

    private TrainTypeItem(TrainType trainType, String str, int i) {
        this.a = trainType;
        this.b = str;
        this.c = i;
    }
}
