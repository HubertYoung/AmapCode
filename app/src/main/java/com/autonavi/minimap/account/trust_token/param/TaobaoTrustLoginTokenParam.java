package com.autonavi.minimap.account.trust_token.param;

import com.amap.bundle.blutils.app.ConfigerHelper;
import java.io.Serializable;

public class TaobaoTrustLoginTokenParam implements Serializable {
    public String key = ConfigerHelper.getInstance().getAccsAppkey();
}
