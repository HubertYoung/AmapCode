package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.autonavi.common.KeyValueStorage;
import com.autonavi.common.KeyValueStorage.DefaultValue;
import com.autonavi.common.KeyValueStorage.OldKey;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import org.xidea.el.Invocable;
import org.xidea.el.impl.ReflectUtil;
import org.xidea.el.json.JSONDecoder;
import org.xidea.el.json.JSONEncoder;

@DefaultValue
/* renamed from: bki reason: default package */
/* compiled from: KeyValueStorageImpl */
public class bki {
    /* access modifiers changed from: private */
    public static final JSONDecoder a = new JSONDecoder(false);
    private static final DefaultValue b = ((DefaultValue) bki.class.getAnnotation(DefaultValue.class));
    /* access modifiers changed from: private */
    public static final Object[] c = new Object[0];

    public static <T extends KeyValueStorage<?>> T a(final Class<T> cls, Context context, String str) {
        Method[] methods;
        final SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        final HashMap hashMap = new HashMap();
        for (Method method : cls.getMethods()) {
            OldKey oldKey = (OldKey) method.getAnnotation(OldKey.class);
            if (oldKey != null) {
                String a2 = a(method.getName());
                String value = oldKey.value();
                String str2 = (String) hashMap.put(a2, value);
                if (str2 != null) {
                    StringBuilder sb = new StringBuilder("conflict stroage key: ");
                    sb.append(str2);
                    sb.append(" replaced by ");
                    sb.append(value);
                    bkj.a((Object) sb.toString());
                }
            }
        }
        return (KeyValueStorage) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() {
            HashMap<String, Invocable> a = new HashMap<>();
            private Editor[] e = new Editor[1];

            public final Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
                if (objArr == null) {
                    objArr = bki.c;
                }
                String name = method.getName();
                Invocable invocable = this.a.get(name);
                if (invocable == null) {
                    invocable = bki.a(cls, method, hashMap, sharedPreferences, this.e);
                    if (invocable == null) {
                        return null;
                    }
                    this.a.put(name, invocable);
                }
                return invocable.a(obj, objArr);
            }
        });
    }

    public static Invocable a(final Class<?> cls, Method method, Map<String, String> map, final SharedPreferences sharedPreferences, final Editor[] editorArr) {
        String str;
        final Type genericReturnType = method.getGenericReturnType();
        String name = method.getName();
        Class[] parameterTypes = method.getParameterTypes();
        String a2 = a(name);
        boolean z = true;
        final boolean z2 = false;
        if (!name.equals(a2)) {
            String str2 = map.get(a2);
            if (str2 != null) {
                str = str2;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(Character.toLowerCase(a2.charAt(0)));
                sb.append(a2.substring(1));
                str = sb.toString();
            }
            char charAt = name.charAt(0);
            if (charAt != 'g') {
                if (charAt != 'i') {
                    if (parameterTypes.length == 1) {
                        if (genericReturnType == Void.TYPE) {
                            z = false;
                        }
                        final Type a3 = a((Type) parameterTypes[0]);
                        final Editor[] editorArr2 = editorArr;
                        final SharedPreferences sharedPreferences2 = sharedPreferences;
                        final String str3 = str;
                        final boolean z3 = z;
                        AnonymousClass5 r0 = new Invocable() {
                            public final Object a(Object obj, Object... objArr) throws Exception {
                                Editor editor = editorArr2[0];
                                boolean z = editor == null;
                                if (z) {
                                    editor = sharedPreferences2.edit();
                                }
                                Boolean bool = objArr[0];
                                if (a3 == Boolean.class) {
                                    editor.putBoolean(str3, bool.booleanValue());
                                } else if (a3 == String.class) {
                                    editor.putString(str3, (String) bool);
                                } else if (a3 == Integer.class) {
                                    editor.putInt(str3, ((Integer) bool).intValue());
                                } else if (a3 == Float.class) {
                                    editor.putFloat(str3, ((Float) bool).floatValue());
                                } else if (a3 == Long.class) {
                                    editor.putLong(str3, ((Long) bool).longValue());
                                } else if (a3 == Double.class) {
                                    editor.putFloat(str3, ((Double) bool).floatValue());
                                } else if (!(a3 instanceof Class) || !Enum.class.isAssignableFrom((Class) a3)) {
                                    editor.putString(str3, JSONEncoder.encode(bool));
                                } else if (bool == null) {
                                    editor.remove(str3);
                                } else {
                                    editor.putInt(str3, ((Enum) bool).ordinal());
                                }
                                if (z) {
                                    if (VERSION.SDK_INT < 9) {
                                        editor.commit();
                                    } else {
                                        editor.apply();
                                    }
                                }
                                if (z3) {
                                    return obj;
                                }
                                return null;
                            }
                        };
                        return r0;
                    }
                } else if (genericReturnType == Boolean.class || genericReturnType == Boolean.TYPE) {
                    return a(sharedPreferences, method, genericReturnType, (Class<?>[]) parameterTypes, str);
                }
            } else if (parameterTypes.length == 0) {
                return a(sharedPreferences, method, genericReturnType, (Class<?>[]) parameterTypes, str);
            }
        } else if (name.equals(ProcessInfo.SR_TO_STRING) && parameterTypes.length == 0) {
            return new Invocable() {
                String a;

                {
                    StringBuilder sb = new StringBuilder();
                    sb.append(cls.getName());
                    sb.append("&");
                    sb.append(sharedPreferences.toString());
                    this.a = sb.toString();
                }

                public final Object a(Object obj, Object... objArr) throws Exception {
                    return this.a;
                }
            };
        } else {
            if (name.equals("reset") && parameterTypes.length == 0) {
                if (genericReturnType != Void.TYPE) {
                    z2 = true;
                }
                return new Invocable() {
                    public final Object a(Object obj, Object... objArr) throws Exception {
                        boolean z = false;
                        Editor editor = editorArr[0];
                        if (editor == null) {
                            z = true;
                        }
                        int i = sharedPreferences.getInt("#version", 1);
                        if (z) {
                            editor = sharedPreferences.edit();
                        }
                        editor.clear();
                        editor.putInt("#version", i);
                        if (z) {
                            if (VERSION.SDK_INT < 9) {
                                editor.commit();
                            } else {
                                editor.apply();
                            }
                        }
                        if (z2) {
                            return obj;
                        }
                        return null;
                    }
                };
            } else if (method.getDeclaringClass() == KeyValueStorage.class) {
                return new Invocable() {
                    public final Object a(Object obj, Object... objArr) throws Exception {
                        if (genericReturnType == SharedPreferences.class) {
                            return sharedPreferences;
                        }
                        if (genericReturnType == Void.TYPE) {
                            if (editorArr[0] != null) {
                                editorArr[0].commit();
                                editorArr[0] = null;
                            }
                            return null;
                        }
                        if (editorArr[0] == null) {
                            editorArr[0] = sharedPreferences.edit();
                        }
                        return null;
                    }
                };
            }
        }
        return new Invocable() {
            public final Object a(Object obj, Object... objArr) throws Exception {
                throw new UnsupportedOperationException("您调用了 KeyValue 存储实现不支持的的方法。");
            }
        };
    }

    private static Invocable a(final SharedPreferences sharedPreferences, Method method, Type type, Class<?>[] clsArr, final String str) {
        final DefaultValue defaultValue;
        if (clsArr.length == 0) {
            defaultValue = (DefaultValue) method.getAnnotation(DefaultValue.class);
            if (defaultValue == null) {
                defaultValue = b;
            }
        } else {
            defaultValue = null;
        }
        final Type a2 = a(type);
        return new Invocable() {
            private Object e;
            private boolean f;

            public final Object a(Object obj, Object... objArr) throws Exception {
                if (a2 == SharedPreferences.class) {
                    return sharedPreferences;
                }
                Enum enumR = null;
                boolean z = false;
                if (!sharedPreferences.contains(str)) {
                    if (!this.f) {
                        if (defaultValue == null) {
                            return objArr[0];
                        }
                        double value = defaultValue.value();
                        if (!(a2 instanceof Class) || !Enum.class.isAssignableFrom((Class) a2)) {
                            String jsonValue = defaultValue.jsonValue();
                            if (jsonValue.length() == 0) {
                                jsonValue = a2 == String.class ? "\"\"" : "null";
                            }
                            if (!"null".equals(jsonValue)) {
                                Object decodeObject = bki.a.decodeObject(jsonValue, a2);
                                if (a2 != String.class && decodeObject != null && !(decodeObject instanceof Number) && !(decodeObject instanceof Boolean)) {
                                    return decodeObject;
                                }
                                this.e = decodeObject;
                            } else if (a2 == Boolean.class) {
                                if (value != 0.0d) {
                                    z = true;
                                }
                                this.e = Boolean.valueOf(z);
                            } else if (a2 == Integer.class) {
                                this.e = Integer.valueOf((int) value);
                            } else if (a2 == Long.class) {
                                this.e = Long.valueOf((long) value);
                            } else if (a2 == Float.class) {
                                this.e = Float.valueOf((float) value);
                            } else if (a2 == Double.class) {
                                this.e = Double.valueOf(value);
                            }
                        } else {
                            if (defaultValue != null) {
                                enumR = ReflectUtil.a((Object) Integer.valueOf((int) value), (Class) a2);
                            }
                            this.e = enumR;
                        }
                        this.f = true;
                    }
                    return this.e;
                } else if (a2 == Boolean.class) {
                    return Boolean.valueOf(sharedPreferences.getBoolean(str, false));
                } else {
                    if (a2 == String.class) {
                        return sharedPreferences.getString(str, null);
                    }
                    if (a2 == Integer.class) {
                        return Integer.valueOf(sharedPreferences.getInt(str, 0));
                    }
                    if (a2 == Float.class) {
                        return Float.valueOf(sharedPreferences.getFloat(str, 0.0f));
                    }
                    if (a2 == Long.class) {
                        return Long.valueOf(sharedPreferences.getLong(str, 0));
                    }
                    if (a2 == Double.class) {
                        return Double.valueOf((double) sharedPreferences.getFloat(str, 0.0f));
                    }
                    if (!(a2 instanceof Class) || !Enum.class.isAssignableFrom((Class) a2)) {
                        String string = sharedPreferences.getString(str, "");
                        if (string != null && string.length() > 0) {
                            try {
                                return bki.a.decodeObject(string, a2);
                            } catch (Exception e2) {
                                bkj.a((Object) e2);
                            }
                        }
                        return null;
                    }
                    int i = sharedPreferences.getInt(str, -1);
                    if (i < 0) {
                        return null;
                    }
                    return ReflectUtil.a((Object) Integer.valueOf(i), (Class) a2);
                }
            }
        };
    }

    private static Type a(Type type) {
        return type instanceof Class ? ReflectUtil.e((Class) type) : type;
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String replaceAll = str.replaceAll("^(?:get|set|is)([A-Z])", "$1");
        return !TextUtils.isEmpty(replaceAll) ? b(replaceAll) : replaceAll;
    }

    private static String b(String str) {
        if (Character.isLowerCase(str.charAt(0))) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toLowerCase(str.charAt(0)));
        sb.append(str.substring(1));
        return sb.toString();
    }
}
