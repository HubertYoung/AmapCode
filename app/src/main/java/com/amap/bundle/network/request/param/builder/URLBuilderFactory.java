package com.amap.bundle.network.request.param.builder;

import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class URLBuilderFactory {
    private static final Map<Class, a> entityInfoCache = new HashMap();

    public static class a {
        public Path a;
        public Map<String, Field> b;
    }

    private URLBuilderFactory() {
    }

    public static a getEntityInfo(ParamEntity paramEntity) {
        if (paramEntity == null) {
            throw new IllegalArgumentException("entity must not be null.");
        }
        Class<?> cls = paramEntity.getClass();
        a aVar = entityInfoCache.get(cls);
        if (aVar == null) {
            aVar = new a();
            Path path = (Path) cls.getAnnotation(Path.class);
            if (path == null) {
                StringBuilder sb = new StringBuilder("类");
                sb.append(cls.getName());
                sb.append("未定义URLBuilder.Path注解!!");
                throw new IllegalArgumentException(sb.toString());
            }
            aVar.a = path;
            aVar.b = new HashMap();
            resolveAllFields(cls, aVar.b);
            entityInfoCache.put(cls, aVar);
        }
        return aVar;
    }

    public static URLBuilder build(ParamEntity paramEntity, boolean z) {
        a entityInfo = getEntityInfo(paramEntity);
        Class<? extends URLBuilder> builder = entityInfo.a.builder();
        if (builder == null) {
            builder = entityInfo.a.builder();
        }
        URLBuilder uRLBuilder = null;
        if (builder != null) {
            try {
                uRLBuilder = (URLBuilder) builder.newInstance();
            } catch (Exception e) {
                handleException("type.newInstance()", paramEntity, entityInfo, builder, e);
            }
        }
        if (uRLBuilder != null) {
            try {
                uRLBuilder.parse(entityInfo.a, entityInfo.b, paramEntity, z);
            } catch (Exception e2) {
                handleException("builder.parse()", paramEntity, entityInfo, builder, e2);
            }
        }
        return uRLBuilder;
    }

    private static void handleException(String str, ParamEntity paramEntity, a aVar, Class<? extends URLBuilder> cls, Exception exc) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        if (paramEntity.getClass() != null) {
            sb.append(paramEntity.getClass().getName());
        } else {
            sb.append("entity getClass() fail");
        }
        sb.append(", ");
        if (aVar.a.host() == null || aVar.a.url() == null) {
            sb.append(" host:");
            sb.append(aVar.a.host());
            sb.append(" url:");
            sb.append(aVar.a.url());
            sb.append(", ");
        } else {
            sb.append(aai.a(aVar.a.host(), aVar.a.url()));
            sb.append(", ");
        }
        sb.append("[Params] - ");
        for (Entry next : aVar.b.entrySet()) {
            sb.append((String) next.getKey());
            sb.append(":");
            try {
                Object obj = ((Field) next.getValue()).get(paramEntity);
                if (obj == null) {
                    sb.append("[], ");
                } else {
                    sb.append(obj.toString());
                    sb.append(", ");
                }
            } catch (Exception e) {
                StringBuilder sb2 = new StringBuilder(", [exception:");
                sb2.append(e.getClass().toString());
                sb2.append("], ");
                sb.append(sb2.toString());
            }
        }
        if (cls != null) {
            sb.append("builder type:");
            sb.append(cls.getName());
        }
        throw new RuntimeException(sb.toString(), exc);
    }

    private static void resolveAllFields(Class<? super T> cls, Map<String, Field> map) {
        while (cls != null && !cls.equals(Object.class)) {
            Field[] declaredFields = cls.getDeclaredFields();
            if (declaredFields != null) {
                for (Field field : declaredFields) {
                    String name = field.getName();
                    if (!name.startsWith("this$")) {
                        field.setAccessible(true);
                        map.put(name, field);
                    }
                }
            }
            cls = cls.getSuperclass();
        }
    }
}
