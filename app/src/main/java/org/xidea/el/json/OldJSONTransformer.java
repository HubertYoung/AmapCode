package org.xidea.el.json;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xidea.el.json.JSONDecoder.TypeTransformer;

public class OldJSONTransformer extends JSONTransformer {
    static Field c;
    static Field d;

    public final /* bridge */ /* synthetic */ Date a(String str) {
        return super.a(str);
    }

    public final /* bridge */ /* synthetic */ TypeTransformer a(TypeTransformer typeTransformer) {
        return super.a(typeTransformer);
    }

    public final /* bridge */ /* synthetic */ void a(ClassLoader classLoader) {
        super.a(classLoader);
    }

    public final /* bridge */ /* synthetic */ Object b(Object obj, Type type) {
        return super.b(obj, type);
    }

    public final /* bridge */ /* synthetic */ Date b(String str) {
        return super.b(str);
    }

    static {
        Field[] declaredFields;
        Field[] declaredFields2;
        for (Field field : JSONObject.class.getDeclaredFields()) {
            if (Map.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                c = field;
            }
        }
        for (Field field2 : JSONArray.class.getDeclaredFields()) {
            if (List.class.isAssignableFrom(field2.getType())) {
                field2.setAccessible(true);
                d = field2;
            }
        }
        if (c == null || d == null) {
            throw new InstantiationError("JSON 实现代码 格式异常，找不到内部存储的真实List和Map");
        }
    }

    /* access modifiers changed from: protected */
    public final Object a(Object obj, Type type) throws Exception {
        if (obj instanceof JSONObject) {
            obj = c.get(obj);
        } else if (obj instanceof JSONArray) {
            obj = d.get(obj);
        } else if (obj == JSONObject.NULL) {
            obj = null;
        }
        return super.a(obj, type);
    }
}
