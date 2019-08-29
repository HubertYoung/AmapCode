package com.alipay.mobile.security.bio.service.local.language;

import com.alipay.mobile.security.bio.service.local.LocalService;
import com.alipay.mobile.security.bio.utils.BioLog;
import java.lang.reflect.Method;

public class LanguageService extends LocalService {
    /* JADX INFO: finally extract failed */
    public int getCurrentLanguage() {
        int i = Language.UNKNOWN.value;
        try {
            Class<?> cls = Class.forName("com.alipay.mobile.framework.locale.LocaleHelper");
            Method method = cls.getMethod("getInstance", new Class[0]);
            method.setAccessible(true);
            Object invoke = method.invoke(cls, new Object[0]);
            Method method2 = cls.getMethod("getCurrentLanguage", new Class[0]);
            method2.setAccessible(true);
            int intValue = ((Integer) method2.invoke(invoke, new Object[0])).intValue();
            BioLog.e("getCurrentLanguage() : language=" + intValue);
            return intValue;
        } catch (Exception e) {
            BioLog.e("Failed to getCurrentLanguage() : " + e.getMessage());
            BioLog.e("getCurrentLanguage() : language=" + i);
            return i;
        } catch (Throwable th) {
            BioLog.e("getCurrentLanguage() : language=" + i);
            throw th;
        }
    }
}
