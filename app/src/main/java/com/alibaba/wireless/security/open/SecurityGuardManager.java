package com.alibaba.wireless.security.open;

import android.content.Context;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alibaba.wireless.security.framework.ISGPluginManager;
import com.alibaba.wireless.security.open.atlasencrypt.IAtlasEncryptComponent;
import com.alibaba.wireless.security.open.datacollection.IDataCollectionComponent;
import com.alibaba.wireless.security.open.dynamicdataencrypt.IDynamicDataEncryptComponent;
import com.alibaba.wireless.security.open.dynamicdatastore.IDynamicDataStoreComponent;
import com.alibaba.wireless.security.open.initialize.IInitializeComponent;
import com.alibaba.wireless.security.open.initialize.a;
import com.alibaba.wireless.security.open.initialize.c;
import com.alibaba.wireless.security.open.maldetection.IMalDetect;
import com.alibaba.wireless.security.open.nocaptcha.INoCaptchaComponent;
import com.alibaba.wireless.security.open.opensdk.IOpenSDKComponent;
import com.alibaba.wireless.security.open.pkgvaliditycheck.IPkgValidityCheckComponent;
import com.alibaba.wireless.security.open.safetoken.ISafeTokenComponent;
import com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent;
import com.alibaba.wireless.security.open.securitybody.ISecurityBodyComponent;
import com.alibaba.wireless.security.open.simulatordetect.ISimulatorDetectComponent;
import com.alibaba.wireless.security.open.staticdataencrypt.IStaticDataEncryptComponent;
import com.alibaba.wireless.security.open.staticdatastore.IStaticDataStoreComponent;
import com.alibaba.wireless.security.open.statickeyencrypt.IStaticKeyEncryptComponent;
import com.alibaba.wireless.security.open.umid.IUMIDComponent;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class SecurityGuardManager {
    private static volatile SecurityGuardManager b;
    private static volatile IInitializeComponent c;
    private static final Object d = new Object();
    private static JSONObject e;
    private static Object g = new Object();
    private ISGPluginManager a;
    private final Map<Integer, Class> f = new HashMap<Integer, Class>() {
        {
            put(Integer.valueOf(1), ISecureSignatureComponent.class);
            put(Integer.valueOf(2), IDynamicDataStoreComponent.class);
            put(Integer.valueOf(3), IStaticDataStoreComponent.class);
            put(Integer.valueOf(5), IDataCollectionComponent.class);
            put(Integer.valueOf(6), IStaticDataEncryptComponent.class);
            put(Integer.valueOf(7), IDynamicDataEncryptComponent.class);
            put(Integer.valueOf(8), ISimulatorDetectComponent.class);
            put(Integer.valueOf(9), IStaticKeyEncryptComponent.class);
            put(Integer.valueOf(10), IOpenSDKComponent.class);
            put(Integer.valueOf(11), IUMIDComponent.class);
            put(Integer.valueOf(12), IPkgValidityCheckComponent.class);
            put(Integer.valueOf(13), IAtlasEncryptComponent.class);
            put(Integer.valueOf(14), IMalDetect.class);
            put(Integer.valueOf(15), INoCaptchaComponent.class);
            put(Integer.valueOf(16), ISafeTokenComponent.class);
            put(Integer.valueOf(17), ISecurityBodyComponent.class);
        }
    };

    private SecurityGuardManager(ISGPluginManager iSGPluginManager) {
        this.a = iSGPluginManager;
    }

    private static String getGlobalUserData() {
        String jSONObject;
        synchronized (g) {
            jSONObject = e.toString();
        }
        return jSONObject;
    }

    public static IInitializeComponent getInitializer() {
        return getInitializer(null);
    }

    public static IInitializeComponent getInitializer(String str) {
        if (c == null) {
            synchronized (d) {
                if (c == null) {
                    c = new a(str);
                }
            }
        }
        return c;
    }

    public static SecurityGuardManager getInstance(Context context) throws SecException {
        return getInstance(context, null);
    }

    public static SecurityGuardManager getInstance(Context context, String str) throws SecException {
        if (b == null) {
            synchronized (SecurityGuardManager.class) {
                if (context == null) {
                    try {
                        return null;
                    } catch (Throwable th) {
                        throw th;
                    }
                } else if (b == null && getInitializer(str).initialize(context) == 0) {
                    b = new SecurityGuardManager(((a) getInitializer(str)).a());
                }
            }
        }
        return b;
    }

    public static boolean setGlobalUserData(String str, String str2) throws SecException {
        try {
            synchronized (g) {
                if (str == null && str2 == null) {
                    e = null;
                } else {
                    if (str != null) {
                        if (str2 != null) {
                            if (e == null) {
                                e = new JSONObject();
                            }
                            e.put(str, str2);
                        }
                    }
                    throw new SecException(118);
                }
            }
            return true;
        } catch (JSONException e2) {
            throw new SecException(e2.getMessage(), 119);
        } catch (Throwable th) {
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public IComponent a(int i) {
        try {
            return (IComponent) getInterface(this.f.get(Integer.valueOf(i)));
        } catch (SecException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public IAtlasEncryptComponent getAtlasEncryptComp() {
        IAtlasEncryptComponent iAtlasEncryptComponent = (IAtlasEncryptComponent) a(13);
        if (iAtlasEncryptComponent == null) {
            new SecException(SecExceptionCode.SEC_ERROR_ATLAS_UNSUPPORTED).printStackTrace();
        }
        return iAtlasEncryptComponent;
    }

    public IDataCollectionComponent getDataCollectionComp() {
        return (IDataCollectionComponent) a(5);
    }

    public IDynamicDataEncryptComponent getDynamicDataEncryptComp() {
        return (IDynamicDataEncryptComponent) a(7);
    }

    public IDynamicDataStoreComponent getDynamicDataStoreComp() {
        return (IDynamicDataStoreComponent) a(2);
    }

    public <T> T getInterface(Class<T> cls) throws SecException {
        return this.a.getInterface(cls);
    }

    public IMalDetect getMalDetectionComp() {
        IMalDetect iMalDetect = (IMalDetect) a(14);
        if (iMalDetect == null) {
            new SecException(SecExceptionCode.SEC_ERROR_MALDETECT_UNSUPPORTED).printStackTrace();
        }
        return iMalDetect;
    }

    public INoCaptchaComponent getNoCaptchaComp() {
        INoCaptchaComponent iNoCaptchaComponent = (INoCaptchaComponent) a(15);
        if (iNoCaptchaComponent == null) {
            new SecException(1299).printStackTrace();
        }
        return iNoCaptchaComponent;
    }

    public IOpenSDKComponent getOpenSDKComp() {
        return (IOpenSDKComponent) a(10);
    }

    public IPkgValidityCheckComponent getPackageValidityCheckComp() {
        return (IPkgValidityCheckComponent) a(12);
    }

    public String getSDKVerison() {
        return c.a();
    }

    public ISGPluginManager getSGPluginManager() {
        return this.a;
    }

    public ISafeTokenComponent getSafeTokenComp() {
        ISafeTokenComponent iSafeTokenComponent = (ISafeTokenComponent) a(16);
        if (iSafeTokenComponent == null) {
            new SecException(SecExceptionCode.SEC_ERROR_SAFETOKEN_UNKNOWN_ERR).printStackTrace();
        }
        return iSafeTokenComponent;
    }

    public ISecureSignatureComponent getSecureSignatureComp() {
        return (ISecureSignatureComponent) a(1);
    }

    public ISecurityBodyComponent getSecurityBodyComp() {
        ISecurityBodyComponent iSecurityBodyComponent = (ISecurityBodyComponent) a(17);
        if (iSecurityBodyComponent == null) {
            new SecException(SecExceptionCode.SEC_ERROR_SECURITYBODY_UNSUPPORTED).printStackTrace();
        }
        return iSecurityBodyComponent;
    }

    public ISimulatorDetectComponent getSimulatorDetectComp() {
        ISimulatorDetectComponent iSimulatorDetectComponent = (ISimulatorDetectComponent) a(8);
        if (iSimulatorDetectComponent == null) {
            new SecException(SecExceptionCode.SEC_ERROR_SIMULATORDETECT_UNSUPPORTED).printStackTrace();
        }
        return iSimulatorDetectComponent;
    }

    public IStaticDataEncryptComponent getStaticDataEncryptComp() {
        return (IStaticDataEncryptComponent) a(6);
    }

    public IStaticDataStoreComponent getStaticDataStoreComp() {
        return (IStaticDataStoreComponent) a(3);
    }

    public IStaticKeyEncryptComponent getStaticKeyEncryptComp() {
        return (IStaticKeyEncryptComponent) a(9);
    }

    public IUMIDComponent getUMIDComp() {
        return (IUMIDComponent) a(11);
    }

    public Boolean isOpen() {
        return Boolean.TRUE;
    }
}
