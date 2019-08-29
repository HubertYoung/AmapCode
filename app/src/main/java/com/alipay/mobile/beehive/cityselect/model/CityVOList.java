package com.alipay.mobile.beehive.cityselect.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CityVOList implements Serializable {
    private static final long serialVersionUID = -5478039060469109739L;
    public List<CityVO> cityList;

    public CityVOList() {
    }

    public CityVOList(List<CityVO> cityList2) {
        this.cityList = cityList2;
    }

    public CityVOList clone() {
        CityVOList cloned = new CityVOList();
        if (this.cityList != null) {
            cloned.cityList = new ArrayList();
            for (CityVO cityVO : this.cityList) {
                cloned.cityList.add((CityVO) cityVO.clone());
            }
        }
        return cloned;
    }
}
