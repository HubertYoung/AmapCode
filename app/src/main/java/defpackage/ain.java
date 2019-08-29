package defpackage;

import android.text.TextUtils;
import com.amap.bundle.voiceservice.dispatch.IVoiceDispatchMethod;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* renamed from: ain reason: default package */
/* compiled from: ProtocolSceneManager */
public final class ain {
    public static boolean a(int i, Class cls, String str, String str2) {
        Object a = ank.a(cls);
        if (a == null) {
            return false;
        }
        Method[] declaredMethods = a.getClass().getDeclaredMethods();
        if (declaredMethods == null || declaredMethods.length == 0) {
            return false;
        }
        for (Method method : declaredMethods) {
            IVoiceDispatchMethod iVoiceDispatchMethod = (IVoiceDispatchMethod) method.getAnnotation(IVoiceDispatchMethod.class);
            if (iVoiceDispatchMethod != null && TextUtils.equals(iVoiceDispatchMethod.methodName(), str)) {
                try {
                    method.invoke(a, new Object[]{Integer.valueOf(i), str2});
                    return true;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return false;
    }
}
