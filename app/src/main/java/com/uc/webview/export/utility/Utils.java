package com.uc.webview.export.utility;

import android.opengl.GLES10;
import com.uc.webview.export.annotations.Interface;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IGlobalSettings;
import com.uc.webview.export.internal.utility.Log;
import java.util.Formatter;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

@Interface
/* compiled from: ProGuard */
public class Utils {
    private static ThreadLocal<Formatter> a = new a();
    public static boolean sWAPrintLog = false;

    private Utils() {
    }

    public static String timeFormat(int i) {
        int i2 = i / 1000;
        int i3 = i2 % 60;
        int i4 = (i2 / 60) % 60;
        int i5 = i2 / 3600;
        Formatter formatter = a.get();
        if (i5 != 0) {
            formatter.format("%d:%02d:%02d", new Object[]{Integer.valueOf(i5), Integer.valueOf(i4), Integer.valueOf(i3)});
        } else {
            formatter.format("%02d:%02d", new Object[]{Integer.valueOf(i4), Integer.valueOf(i3)});
        }
        return formatter.toString();
    }

    public static boolean checkSupportSamplerExternalOES() {
        String str;
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        new String("");
        if (egl10.eglGetCurrentContext() == EGL10.EGL_NO_CONTEXT) {
            EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            egl10.eglInitialize(eglGetDisplay, null);
            int[] iArr = new int[1];
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            egl10.eglChooseConfig(eglGetDisplay, new int[]{12339, 1, 12352, 4, 12344}, eGLConfigArr, 1, iArr);
            if (iArr[0] != 1) {
                Log.e("Utils", "eglChooseConfig failed");
                return false;
            }
            EGLSurface eglCreatePbufferSurface = egl10.eglCreatePbufferSurface(eglGetDisplay, eGLConfigArr[0], new int[]{12375, 1, 12374, 1, 12344});
            if (EGL10.EGL_NO_SURFACE == eglCreatePbufferSurface) {
                Log.e("Utils", "eglCreatePbufferSurface failed");
                return false;
            }
            EGLContext eglCreateContext = egl10.eglCreateContext(eglGetDisplay, eGLConfigArr[0], EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
            egl10.eglMakeCurrent(eglGetDisplay, eglCreatePbufferSurface, eglCreatePbufferSurface, eglCreateContext);
            str = GLES10.glGetString(7939);
            String glGetString = GLES10.glGetString(7936);
            String glGetString2 = GLES10.glGetString(7937);
            if (glGetString.toLowerCase().contains("google") && glGetString2.toLowerCase().contains("emulator")) {
                return true;
            }
            EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
            egl10.eglMakeCurrent(eglGetDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
            egl10.eglDestroyContext(eglGetDisplay, eglCreateContext);
            egl10.eglDestroySurface(eglGetDisplay, eglCreatePbufferSurface);
            egl10.eglTerminate(eglGetDisplay);
        } else {
            str = GLES10.glGetString(7939);
        }
        if (str.contains("GL_OES_EGL_image_external")) {
            return true;
        }
        Log.i("Utils", "don't support GL_OES_EGL_image_external");
        return false;
    }

    protected static void setCoreType(int i) {
        SDKFactory.invoke(10021, Integer.valueOf(i));
    }

    protected static void setProxyAddress(String str) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setStringValue("FoxyServerAddr", str);
            iGlobalSettings.setStringValue("WifiFoxyServerAddr", str);
        }
    }
}
