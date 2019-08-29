package com.ali.user.mobile.register.region;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.register.ui.AliUserRegisterChoiceRegionActivity;
import com.ali.user.mobile.rpc.vo.mobilegw.register.CountryCodeInfo;
import com.ali.user.mobile.rpc.vo.mobilegw.register.CountryCodeRes;
import com.ali.user.mobile.rpc.vo.mobilegw.register.RegMixRes;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.service.UserRegisterService;
import com.ali.user.mobile.utils.ResourceUtil;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionChoice {
    private static RegionChoice a;
    private final UserRegisterService b = AliuserLoginContext.e();
    private RegionViewModel c;

    public interface RegionCallback {
        void a();

        void a(RegionInfo regionInfo);

        void a(String str);

        void b();
    }

    private RegionChoice() {
    }

    public static RegionChoice a() {
        if (a == null) {
            synchronized (RegionChoice.class) {
                try {
                    if (a == null) {
                        a = new RegionChoice();
                    }
                }
            }
        }
        return a;
    }

    public final void a(Context context, RegionCallback regionCallback) throws RpcException {
        RegionCallback regionCallback2 = regionCallback;
        if (c()) {
            b(context, regionCallback);
            return;
        }
        if (regionCallback2 != null) {
            regionCallback.a();
        }
        RegMixRes a2 = this.b.a();
        if (regionCallback2 != null) {
            regionCallback.b();
        }
        if (a2 != null && a2.resultStatus == 200 && a2.countryCodeResList != null) {
            List<CountryCodeRes> list = a2.countryCodeResList;
            ArrayList arrayList = new ArrayList();
            HashMap hashMap = new HashMap();
            ArrayList arrayList2 = new ArrayList();
            int i = 0;
            int i2 = 0;
            while (i < list.size()) {
                CountryCodeRes countryCodeRes = list.get(i);
                List<CountryCodeInfo> list2 = countryCodeRes.countryCodeList;
                int i3 = i2;
                for (int i4 = 0; i4 < list2.size(); i4++) {
                    CountryCodeInfo countryCodeInfo = list2.get(i4);
                    RegionInfo regionInfo = new RegionInfo();
                    if (MetaRecord.LOG_SEPARATOR.equals(countryCodeRes.index)) {
                        regionInfo.mCharacter = ResourceUtil.a(R.string.D);
                        countryCodeRes.index = "★";
                    } else {
                        regionInfo.mCharacter = countryCodeRes.index;
                    }
                    if (i4 == 0) {
                        regionInfo.isDisplayLetter = true;
                        hashMap.put(countryCodeRes.index, Integer.valueOf(i3));
                        arrayList2.add(countryCodeRes.index);
                    } else {
                        regionInfo.isDisplayLetter = false;
                    }
                    regionInfo.mRegionName = countryCodeInfo.country;
                    StringBuilder sb = new StringBuilder();
                    sb.append(countryCodeInfo.countryCode);
                    regionInfo.mRegionNumber = sb.toString();
                    regionInfo.mDomain = countryCodeInfo.domain;
                    regionInfo.mRegularExpression = countryCodeInfo.regular;
                    i3++;
                    arrayList.add(regionInfo);
                }
                i++;
                i2 = i3;
            }
            RegionViewModel regionViewModel = new RegionViewModel();
            regionViewModel.a((List<RegionInfo>) arrayList);
            regionViewModel.b(arrayList2);
            regionViewModel.a((Map<String, Integer>) hashMap);
            this.c = regionViewModel;
            if (c()) {
                b(context, regionCallback);
            }
        } else if (regionCallback2 != null) {
            if (a2 != null) {
                regionCallback2.a(a2.memo);
                return;
            }
            regionCallback2.a((String) "system error");
        }
    }

    private static void b(Context context, RegionCallback regionCallback) {
        AliUserRegisterChoiceRegionActivity.setRegionCallback(regionCallback);
        Intent intent = new Intent(context, AliUserRegisterChoiceRegionActivity.class);
        boolean z = context instanceof Activity;
        if (!z) {
            intent.addFlags(268435456);
        }
        if (z) {
            ((Activity) context).overridePendingTransition(R.anim.a, 0);
        }
        context.startActivity(intent);
    }

    private boolean c() {
        return this.c != null && !this.c.a();
    }

    public final RegionViewModel b() {
        RegionViewModel regionViewModel = this.c;
        this.c = null;
        return regionViewModel;
    }
}
