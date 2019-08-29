package com.alipay.mobile.beehive.cityselect.util;

import android.text.TextUtils;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.Comparator;

public class CityComparator implements Comparator<CityVO> {
    public int compare(CityVO s1, CityVO s2) {
        if (TextUtils.isEmpty(s1.pinyin)) {
            s1.pinyin = MetaRecord.LOG_SEPARATOR;
        }
        if (TextUtils.isEmpty(s2.pinyin)) {
            s2.pinyin = MetaRecord.LOG_SEPARATOR;
        }
        return s1.pinyin.substring(0, 1).toUpperCase().compareTo(s2.pinyin.substring(0, 1).toUpperCase());
    }
}
