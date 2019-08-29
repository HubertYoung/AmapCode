package com.autonavi.minimap.offline.util;

import android.support.annotation.NonNull;
import com.autonavi.minimap.offline.model.City;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class JsSortUtils {

    static class a implements Comparator<City> {
        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        public final /* synthetic */ int compare(Object obj, Object obj2) {
            return ((City) obj).getPinyin().compareToIgnoreCase(((City) obj2).getPinyin());
        }
    }

    static class b implements Comparator<CityInfo> {
        private b() {
        }

        /* synthetic */ b(byte b) {
            this();
        }

        public final /* synthetic */ int compare(Object obj, Object obj2) {
            return ((CityInfo) obj).pinyin.compareToIgnoreCase(((CityInfo) obj2).pinyin);
        }
    }

    private JsSortUtils() {
    }

    public static CityInfo[] sortOfPinyin(@NonNull CityInfo[] cityInfoArr) {
        Arrays.sort(cityInfoArr, new b(0));
        return cityInfoArr;
    }

    public static List<City> sortOfPinyin(@NonNull List<City> list) {
        City[] cityArr = (City[]) list.toArray(new City[list.size()]);
        Arrays.sort(cityArr, new a(0));
        return Arrays.asList(cityArr);
    }
}
