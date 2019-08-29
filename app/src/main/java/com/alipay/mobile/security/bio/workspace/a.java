package com.alipay.mobile.security.bio.workspace;

import android.content.Context;
import com.alipay.mobile.security.bio.api.BioParameter;
import com.alipay.mobile.security.bio.service.BioAppDescription;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.utils.SignHelper;
import java.io.InputStream;
import java.util.UUID;

/* compiled from: BaseBioParameterToBioApp */
abstract class a {
    final BioTransfer a;
    protected final Context b;

    public abstract BioAppDescription toBioApp(BioParameter bioParameter);

    a(Context context, BioTransfer bioTransfer) {
        this.a = bioTransfer;
        this.b = context;
    }

    /* access modifiers changed from: protected */
    public final InputStream a() {
        return this.b.getAssets().open(BioServiceManager.getEnv().publicKeyAssetsName);
    }

    public static String getUniqueTag() {
        return SignHelper.MD5(System.currentTimeMillis() + "_" + (Math.random() * 10000.0d) + UUID.randomUUID().toString());
    }
}
