package com.ali.user.mobile.rpc.vo.mobilegw.register;

import com.ali.user.mobile.rpc.vo.mobilegw.GwCommonRes;
import java.io.Serializable;
import java.util.List;

public class CountryCodeRes extends GwCommonRes implements Serializable {
    public List<CountryCodeInfo> countryCodeList;
    public String index;
}
