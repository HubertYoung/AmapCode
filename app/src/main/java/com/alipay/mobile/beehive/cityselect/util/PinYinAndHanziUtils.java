package com.alipay.mobile.beehive.cityselect.util;

import com.alipay.mobile.common.utils.ChineseToPy;
import java.util.ArrayList;
import java.util.List;

public class PinYinAndHanziUtils {
    public static List<String[]> convertChinese2Pinyin(String name) {
        if (name == null || "".equals(name.trim())) {
            return null;
        }
        String[] splitedDisplayName = splitDisplayNameWithHanzi(name);
        String[] displayNamePinyin = new String[splitedDisplayName.length];
        List pinyinResult = new ArrayList();
        for (int i = 0; i < splitedDisplayName.length; i++) {
            if (isHanzi(splitedDisplayName[i])) {
                displayNamePinyin[i] = ChineseToPy.getFullPy(splitedDisplayName[i]).toLowerCase();
            } else {
                displayNamePinyin[i] = splitedDisplayName[i];
            }
        }
        pinyinResult.add(displayNamePinyin);
        if (splitedDisplayName.length <= 0 || !hasHanzi(name)) {
            return pinyinResult;
        }
        pinyinResult.add(splitedDisplayName);
        return pinyinResult;
    }

    public static List<String[]> processResult(String[][] pinyinResult) {
        List result = new ArrayList();
        for (String split : doExchange(pinyinResult)[0]) {
            result.add(split.split("##"));
        }
        return result;
    }

    private static String[][] doExchange(String[][] pinyinForHanzi) {
        int len = pinyinForHanzi.length;
        if (len < 2) {
            return pinyinForHanzi;
        }
        int len1 = pinyinForHanzi[0].length;
        int len2 = pinyinForHanzi[1].length;
        String[] temp = new String[(len1 * len2)];
        int Index = 0;
        int i = 0;
        while (i < len1) {
            int j = 0;
            int Index2 = Index;
            while (j < len2) {
                temp[Index2] = pinyinForHanzi[0][i] + "##" + pinyinForHanzi[1][j];
                j++;
                Index2++;
            }
            i++;
            Index = Index2;
        }
        String[][] newArray = new String[(len - 1)][];
        for (int i2 = 2; i2 < len; i2++) {
            newArray[i2 - 1] = pinyinForHanzi[i2];
        }
        newArray[0] = temp;
        return doExchange(newArray);
    }

    private static boolean hasHanzi(String name) {
        return name.matches(".*[\\u4e00-\\u9fa5]+.*");
    }

    public static boolean isHanzi(String name) {
        return name.matches("[\\u4e00-\\u9fa5]");
    }

    public static boolean isHanziSting(String name) {
        if (name != null) {
            for (int i = 0; i < name.length(); i++) {
                if (!isHanzi(name.substring(i, i + 1))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isAlphabet(String name) {
        return name.matches("[a-zA-Z]+");
    }

    public static boolean isHanziAndAlphabet(String name) {
        return name.matches("[\\u4e00-\\u9fa5[a-zA-Z ]]+");
    }

    public static String[] splitDisplayNameWithHanzi(String name) {
        String[] splitedResult = name.replaceAll("([\\u4e00-\\u9fa5 ])", "###$1###").split("#{3}");
        List splitedList = new ArrayList();
        for (String curStr : splitedResult) {
            if (!"".equals(curStr)) {
                splitedList.add(curStr);
            }
        }
        return (String[]) splitedList.toArray(new String[splitedList.size()]);
    }

    public static boolean isContainHanzi(String name) {
        return name.matches(".*[\\u4e00-\\u9fa5]+.*");
    }

    public static boolean isStartWithAlphabet(String firstHanzi) {
        return firstHanzi.matches("[a-zA-Z]+.*");
    }
}
