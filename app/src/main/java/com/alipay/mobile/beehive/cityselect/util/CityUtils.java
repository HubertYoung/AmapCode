package com.alipay.mobile.beehive.cityselect.util;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CityUtils {
    public static List<CityVO> loadCityListFromLocal(Context context, int resId, int fillMainLand) {
        List cityList = new ArrayList();
        Set overseaPrefixes = getOverseaCityPrefix(context);
        for (String line : context.getResources().getStringArray(resId)) {
            CityVO cityVO = new CityVO();
            String[] bits = line.split(",");
            if (bits.length >= 2) {
                cityVO.adCode = bits[0];
                cityVO.city = bits[1];
                if (bits.length >= 3) {
                    cityVO.pinyin = bits[2];
                }
                if (TextUtils.isEmpty(cityVO.adCode) || cityVO.adCode.length() <= 1 || !overseaPrefixes.contains(cityVO.adCode.substring(0, 2))) {
                    cityVO.isMainLand = true;
                } else {
                    cityVO.isMainLand = false;
                }
                if (fillMainLand == 0 || ((fillMainLand == 1 && cityVO.isMainLand) || (fillMainLand == 2 && !cityVO.isMainLand))) {
                    cityList.add(cityVO);
                }
            }
        }
        return cityList;
    }

    public static Set<String> getOverseaCityPrefix(Context context) {
        Set result = new HashSet();
        String[] prefixes = context.getResources().getStringArray(R.array.oversea_cities_adcode_prefix);
        if (prefixes != null) {
            result.addAll(Arrays.asList(prefixes));
        }
        return result;
    }
}
