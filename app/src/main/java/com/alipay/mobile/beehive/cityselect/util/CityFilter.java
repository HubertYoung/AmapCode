package com.alipay.mobile.beehive.cityselect.util;

import android.text.TextUtils;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CityFilter {
    public static List<CityVO> getMatchedCityList(String inputedStr, List<CityVO> cityList) {
        if (cityList == null) {
            return null;
        }
        List contacterSearched = new ArrayList();
        if (TextUtils.isDigitsOnly(inputedStr)) {
            return contacterSearched;
        }
        for (CityVO cityVO : cityList) {
            String pinyin = cityVO.pinyin;
            String hanzi = cityVO.city;
            String enName = cityVO.enName;
            if (PinYinAndHanziUtils.isHanziSting(inputedStr)) {
                if (hanzi != null && hanzi.contains(inputedStr)) {
                    contacterSearched.add(cityVO);
                }
            } else if (PinYinAndHanziUtils.isAlphabet(inputedStr)) {
                if (pinyin != null && pinyin.toLowerCase().contains(inputedStr.toLowerCase())) {
                    contacterSearched.add(cityVO);
                } else if (enName == null || !enName.toLowerCase().contains(inputedStr.toLowerCase())) {
                    List abbreviation = cityVO.abbreviation;
                    if (abbreviation != null && !abbreviation.isEmpty()) {
                        Iterator<String> it = abbreviation.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (it.next().toLowerCase().startsWith(inputedStr.toLowerCase())) {
                                    contacterSearched.add(cityVO);
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                } else {
                    contacterSearched.add(cityVO);
                }
            }
        }
        return contacterSearched;
    }

    public static boolean isHanzi(String name) {
        return name.matches("[\\u4e00-\\u9fa5]");
    }
}
