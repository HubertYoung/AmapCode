package org.xidea.el.json;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.xidea.el.impl.ReflectUtil;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class JSONEncoder {
    public static final String W3C_DATE_FORMAT = "yyyy-MM-dd";
    public static final String W3C_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mmZ";
    public static final String W3C_DATE_TIME_MILLISECOND_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String W3C_DATE_TIME_SECOND_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static JSONEncoder encoder = new JSONEncoder(W3C_DATE_TIME_MILLISECOND_FORMAT, true, 64);
    private final String dateFormat;
    private final boolean ignoreClassName;
    private int index = 0;
    private final Object[] parent;

    /* access modifiers changed from: protected */
    public void reportError(String str) {
    }

    public JSONEncoder(String str, boolean z, int i) {
        this.dateFormat = str;
        this.ignoreClassName = z;
        this.parent = i > 0 ? new Object[i] : null;
    }

    public static String encode(Object obj) {
        return encoder.encode(obj, new StringBuilder()).toString();
    }

    public StringBuilder encode(Object obj, StringBuilder sb) {
        if (this.parent == null) {
            print(obj, sb);
        } else {
            synchronized (this.parent) {
                this.index = 0;
                print(obj, sb);
            }
        }
        return sb;
    }

    /* access modifiers changed from: protected */
    public void print(Object obj, StringBuilder sb) {
        if (obj == null) {
            sb.append("null");
            return;
        }
        Class<?> cls = obj.getClass();
        if (cls == Boolean.class) {
            sb.append(obj.toString());
        } else if (cls == String.class) {
            printString((String) obj, sb);
        } else if (cls == Character.class) {
            printString(obj.toString(), sb);
        } else if (Number.class.isAssignableFrom(cls)) {
            sb.append(obj.toString());
        } else if (cls == Class.class) {
            printString(((Class) obj).getName(), sb);
        } else if (!Date.class.isAssignableFrom(cls) || this.dateFormat == null) {
            if (this.parent != null) {
                if (this.index > this.parent.length) {
                    reportError("深度超出许可范围：".concat(String.valueOf(sb)));
                    return;
                } else if (checkNest(obj)) {
                    sb.append("null");
                    reportError("JSON 数据源中发现递归行为,递归数据将当null处理:".concat(String.valueOf(sb)));
                    return;
                } else {
                    Object[] objArr = this.parent;
                    int i = this.index;
                    this.index = i + 1;
                    objArr[i] = obj;
                }
            }
            try {
                if (obj instanceof Map) {
                    printMap((Map) obj, sb);
                } else if (cls.isArray()) {
                    printList(obj, sb);
                } else if (obj instanceof Iterator) {
                    printList((Iterator) obj, sb);
                } else if (obj instanceof Collection) {
                    printList(((Collection) obj).iterator(), sb);
                } else if (obj instanceof Enum) {
                    printString(((Enum) obj).name(), sb);
                } else {
                    if (!(obj instanceof URL) && !(obj instanceof URI)) {
                        if (!(obj instanceof File)) {
                            printMap(obj, sb);
                        }
                    }
                    printString(obj.toString(), sb);
                }
            } finally {
                if (this.parent != null) {
                    Object[] objArr2 = this.parent;
                    int i2 = this.index - 1;
                    this.index = i2;
                    objArr2[i2] = null;
                }
            }
        } else {
            String format = new SimpleDateFormat(this.dateFormat).format((Date) obj);
            printString(new StringBuilder(format).insert(format.length() - 2, ':').toString(), sb);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkNest(Object obj) {
        int i = this.index;
        if (!(obj instanceof Collection) && !(obj instanceof Map) && !(obj instanceof Object[])) {
            while (true) {
                int i2 = i - 1;
                if (i <= 0) {
                    break;
                } else if (obj.equals(this.parent[i2])) {
                    return true;
                } else {
                    i = i2;
                }
            }
        } else {
            while (true) {
                int i3 = i - 1;
                if (i <= 0) {
                    break;
                } else if (this.parent[i3] == obj) {
                    return true;
                } else {
                    i = i3;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void printString(String str, StringBuilder sb) {
        sb.append('\"');
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt != 13) {
                if (charAt != '\"') {
                    if (charAt != '/') {
                        if (charAt != '\\') {
                            switch (charAt) {
                                case 8:
                                    sb.append("\\b");
                                    break;
                                case 9:
                                    sb.append("\\t");
                                    break;
                                case 10:
                                    sb.append("\\n");
                                    break;
                                default:
                                    if (!Character.isISOControl(charAt)) {
                                        sb.append(charAt);
                                        break;
                                    } else {
                                        sb.append("\\u");
                                        sb.append(Integer.toHexString(charAt + 0), 1, 5);
                                        break;
                                    }
                            }
                        }
                    } else {
                        if (i > 0) {
                            int i2 = i - 1;
                            if (str.charAt(i2) == '<' && str.regionMatches(true, i2, "</script", 0, 8)) {
                                sb.append('\\');
                            }
                        }
                        sb.append(charAt);
                    }
                }
                sb.append('\\');
                sb.append(charAt);
            } else {
                sb.append("\\r");
            }
        }
        sb.append('\"');
    }

    /* access modifiers changed from: protected */
    public void printMap(Object obj, StringBuilder sb) {
        Object obj2;
        sb.append('{');
        try {
            Class<?> cls = obj.getClass();
            Set<String> keySet = getKeySet(cls);
            Map<String, Method> a = ReflectUtil.a(obj.getClass());
            Map<String, Field> b = ReflectUtil.b(cls);
            boolean z = true;
            for (String next : keySet) {
                try {
                    Method method = a.get(next);
                    if (method != null) {
                        obj2 = method.invoke(obj, new Object[0]);
                    } else {
                        Field field = b.get(next);
                        if (field != null) {
                            obj2 = field.get(obj);
                        }
                    }
                    if (z) {
                        z = false;
                    } else {
                        sb.append(',');
                    }
                    sb.append('\"');
                    sb.append(next);
                    sb.append('\"');
                    sb.append(':');
                    print(obj2, sb);
                } catch (Exception unused) {
                }
            }
            if (!this.ignoreClassName) {
                if (!z) {
                    sb.append(',');
                }
                sb.append("\"class\":");
                print(obj.getClass(), sb);
            }
        } catch (Exception unused2) {
        }
        sb.append('}');
    }

    /* access modifiers changed from: protected */
    public Set<String> getKeySet(Class<? extends Object> cls) {
        return ReflectUtil.c(cls);
    }

    /* access modifiers changed from: protected */
    public void printMap(Map<?, ?> map, StringBuilder sb) {
        Iterator<Entry<?, ?>> it = map.entrySet().iterator();
        if (it.hasNext()) {
            sb.append('{');
            while (true) {
                Entry next = it.next();
                printString(String.valueOf(next.getKey()), sb);
                sb.append(':');
                print(next.getValue(), sb);
                if (it.hasNext()) {
                    sb.append(',');
                } else {
                    sb.append('}');
                    return;
                }
            }
        } else {
            sb.append(bny.c);
        }
    }

    /* access modifiers changed from: protected */
    public void printList(Object obj, StringBuilder sb) {
        sb.append('[');
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            print(Array.get(obj, i), sb);
        }
        sb.append(']');
    }

    /* access modifiers changed from: protected */
    public void printList(Iterator<?> it, StringBuilder sb) {
        if (it.hasNext()) {
            sb.append('[');
            while (true) {
                print(it.next(), sb);
                if (it.hasNext()) {
                    sb.append(',');
                } else {
                    sb.append(']');
                    return;
                }
            }
        } else {
            sb.append("[]");
        }
    }
}
