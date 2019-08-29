package com.alipay.mobile.securitycommon.aliauth;

import android.os.Bundle;

interface AuthWorker {
    AliAuthResult autoLogin(Bundle bundle);
}
