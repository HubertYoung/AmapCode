package com.ali.user.mobile.rpc.vo.mobilegw.register;

import com.ali.user.mobile.rpc.vo.mobilegw.GwCommonRes;
import java.io.Serializable;
import java.util.List;

public class RegMixRes extends GwCommonRes implements Serializable {
    public String checkCode;
    public List<CountryCodeRes> countryCodeResList;
}
