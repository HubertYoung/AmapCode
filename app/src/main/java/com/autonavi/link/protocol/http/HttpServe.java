package com.autonavi.link.protocol.http;

import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.link.protocol.http.NanoHTTPD.IHTTPSession;
import com.autonavi.link.protocol.http.NanoHTTPD.Response;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpServe {
    private Map<String, Method> methodMap;

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface HttpPath {
        String path() default "";
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface HttpServeType {
        String serve() default "";
    }

    public Response invoke(IHTTPSession iHTTPSession) {
        checkInit();
        Uri parse = Uri.parse(iHTTPSession.getUri());
        if (parse == null) {
            return null;
        }
        Method method = this.methodMap.get(parse.getPath());
        if (method != null) {
            try {
                return (Response) method.invoke(this, new Object[]{iHTTPSession});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void checkInit() {
        Method[] declaredMethods;
        if (this.methodMap == null) {
            synchronized (HttpServe.class) {
                if (this.methodMap == null) {
                    this.methodMap = new HashMap();
                    for (Method method : getClass().getDeclaredMethods()) {
                        if (method != null) {
                            HttpPath httpPath = (HttpPath) method.getAnnotation(HttpPath.class);
                            if (httpPath != null && !TextUtils.isEmpty(httpPath.path()) && Response.class.isAssignableFrom(method.getReturnType())) {
                                this.methodMap.put(httpPath.path(), method);
                                method.setAccessible(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
