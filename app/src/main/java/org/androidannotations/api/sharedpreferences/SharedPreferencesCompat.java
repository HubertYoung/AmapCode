package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public abstract class SharedPreferencesCompat {
    private static final Method a = a(Editor.class, "apply", new Class[0]);
    private static final Method b = a(SharedPreferences.class, "getStringSet", String.class, Set.class);
    private static final Method c = a(Editor.class, "putStringSet", String.class, Set.class);

    private SharedPreferencesCompat() {
    }

    public static void apply(Editor editor) {
        try {
            invoke(a, editor, new Object[0]);
        } catch (NoSuchMethodException e) {
            editor.commit();
        }
    }

    public static Set<String> getStringSet(SharedPreferences preferences, String key, Set<String> defValues) {
        try {
            return (Set) invoke(b, preferences, key, defValues);
        } catch (NoSuchMethodException e) {
            String serializedSet = preferences.getString(key, null);
            if (serializedSet == null) {
                return defValues;
            }
            return SetXmlSerializer.deserialize(serializedSet);
        }
    }

    public static void putStringSet(Editor editor, String key, Set<String> values) {
        try {
            invoke(c, editor, key, values);
        } catch (NoSuchMethodException e) {
            editor.putString(key, SetXmlSerializer.serialize(values));
        }
    }

    private static Method a(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static <T> T invoke(Method method, Object obj, Object... args) {
        if (method == null) {
            throw new NoSuchMethodException();
        }
        try {
            return method.invoke(obj, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new NoSuchMethodException(method.getName());
        }
    }
}
