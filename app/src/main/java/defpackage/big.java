package defpackage;

import com.autonavi.common.annotation.Ignore;
import com.autonavi.common.annotation.Name;
import com.autonavi.common.reflect.GenericInfo;
import com.autonavi.common.reflect.ReflectUtil;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: big reason: default package */
/* compiled from: BeanField */
public final class big {
    protected static Map<Class<?>, big[]> a = new ConcurrentHashMap();
    protected Field b;
    protected Method c;
    protected Method d;
    protected String e;
    protected Class<?> f;
    protected Class<?> g;
    protected Type h;
    protected WeakReference<Type> i;
    protected WeakReference<Class<?>> j;
    protected WeakReference<Type> k;

    private big(Class<?> cls, String str, Field field) {
        this.b = field;
        a(cls, str);
    }

    private big(Class<?> cls, String str, Method method, Method method2) {
        this.c = method;
        this.d = method2;
        a(cls, str);
    }

    private void a(Class<?> cls, String str) {
        Class<?> cls2;
        this.f = cls;
        this.e = str;
        if (this.b != null) {
            cls2 = this.b.getType();
            this.h = this.b.getGenericType();
        } else {
            cls2 = this.c.getReturnType();
            this.h = this.c.getGenericReturnType();
        }
        if (cls2 == Object.class && (this.h instanceof TypeVariable)) {
            Type inheritGenericType = ReflectUtil.getInheritGenericType(this.f, (TypeVariable) this.h);
            if (inheritGenericType != null) {
                this.g = ReflectUtil.getClass(inheritGenericType);
                this.h = inheritGenericType;
                return;
            }
        }
        if (this.h instanceof Class) {
            this.g = (Class) this.h;
        } else if (this.h instanceof ParameterizedType) {
            this.g = (Class) ((ParameterizedType) this.h).getRawType();
        } else {
            if (this.h instanceof TypeVariable) {
                Type genericSuperclass = this.f.getGenericSuperclass();
                if (genericSuperclass instanceof ParameterizedType) {
                    GenericInfo genericInfo = ReflectUtil.getGenericInfo(((TypeVariable) this.h).getName(), this.f.getSuperclass(), (ParameterizedType) genericSuperclass);
                    if (genericInfo.parameterizedType != null) {
                        this.h = genericInfo.parameterizedType;
                    }
                    this.g = genericInfo.rawType;
                    genericInfo.clear();
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x006b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Class<?> a(java.lang.reflect.Type r5) {
        /*
            r4 = this;
            java.lang.Class<?> r0 = r4.g
            if (r0 == 0) goto L_0x0007
            java.lang.Class<?> r5 = r4.g
            return r5
        L_0x0007:
            if (r5 == 0) goto L_0x0031
            java.lang.ref.WeakReference<java.lang.reflect.Type> r0 = r4.i
            if (r0 == 0) goto L_0x0031
            monitor-enter(r4)
            java.lang.ref.WeakReference<java.lang.reflect.Type> r0 = r4.i     // Catch:{ all -> 0x002e }
            java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x002e }
            java.lang.reflect.Type r0 = (java.lang.reflect.Type) r0     // Catch:{ all -> 0x002e }
            boolean r0 = com.autonavi.common.reflect.ReflectUtil.isAssignable(r5, r0)     // Catch:{ all -> 0x002e }
            if (r0 == 0) goto L_0x002c
            java.lang.ref.WeakReference<java.lang.Class<?>> r0 = r4.j     // Catch:{ all -> 0x002e }
            if (r0 == 0) goto L_0x002c
            java.lang.ref.WeakReference<java.lang.Class<?>> r0 = r4.j     // Catch:{ all -> 0x002e }
            java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x002e }
            java.lang.Class r0 = (java.lang.Class) r0     // Catch:{ all -> 0x002e }
            if (r0 == 0) goto L_0x002c
            monitor-exit(r4)     // Catch:{ all -> 0x002e }
            return r0
        L_0x002c:
            monitor-exit(r4)     // Catch:{ all -> 0x002e }
            goto L_0x0031
        L_0x002e:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x002e }
            throw r5
        L_0x0031:
            r0 = 0
            if (r5 == 0) goto L_0x0063
            boolean r1 = r5 instanceof java.lang.reflect.ParameterizedType
            if (r1 == 0) goto L_0x0063
            r1 = r5
            java.lang.reflect.ParameterizedType r1 = (java.lang.reflect.ParameterizedType) r1
            java.lang.Class<?> r2 = r4.f
            java.lang.reflect.Type r3 = r1.getRawType()
            java.lang.Class r3 = (java.lang.Class) r3
            boolean r2 = r2.isAssignableFrom(r3)
            if (r2 == 0) goto L_0x0063
            java.lang.reflect.Type r2 = r4.h
            boolean r2 = r2 instanceof java.lang.reflect.TypeVariable
            if (r2 == 0) goto L_0x0063
            java.lang.reflect.Type r2 = r4.h
            java.lang.reflect.TypeVariable r2 = (java.lang.reflect.TypeVariable) r2
            java.lang.String r2 = r2.getName()
            java.lang.Class<?> r3 = r4.f
            com.autonavi.common.reflect.GenericInfo r1 = com.autonavi.common.reflect.ReflectUtil.getGenericInfo(r2, r3, r1)
            java.lang.Class<?> r2 = r1.rawType
            r1.clear()
            goto L_0x0064
        L_0x0063:
            r2 = r0
        L_0x0064:
            if (r2 == 0) goto L_0x0067
            goto L_0x0069
        L_0x0067:
            java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
        L_0x0069:
            if (r5 == 0) goto L_0x009b
            monitor-enter(r4)
            java.lang.ref.WeakReference<java.lang.reflect.Type> r1 = r4.i     // Catch:{ all -> 0x0098 }
            if (r1 == 0) goto L_0x0088
            java.lang.ref.WeakReference<java.lang.reflect.Type> r1 = r4.i     // Catch:{ all -> 0x0098 }
            java.lang.Object r1 = r1.get()     // Catch:{ all -> 0x0098 }
            boolean r5 = r5.equals(r1)     // Catch:{ all -> 0x0098 }
            if (r5 != 0) goto L_0x008f
            java.lang.ref.WeakReference<java.lang.reflect.Type> r5 = r4.i     // Catch:{ all -> 0x0098 }
            r5.clear()     // Catch:{ all -> 0x0098 }
            r4.i = r0     // Catch:{ all -> 0x0098 }
            r4.k = r0     // Catch:{ all -> 0x0098 }
            r4.j = r0     // Catch:{ all -> 0x0098 }
            goto L_0x008f
        L_0x0088:
            java.lang.ref.WeakReference r0 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x0098 }
            r0.<init>(r5)     // Catch:{ all -> 0x0098 }
            r4.i = r0     // Catch:{ all -> 0x0098 }
        L_0x008f:
            java.lang.ref.WeakReference r5 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x0098 }
            r5.<init>(r2)     // Catch:{ all -> 0x0098 }
            r4.j = r5     // Catch:{ all -> 0x0098 }
            monitor-exit(r4)     // Catch:{ all -> 0x0098 }
            goto L_0x009b
        L_0x0098:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0098 }
            throw r5
        L_0x009b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.big.a(java.lang.reflect.Type):java.lang.Class");
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x006d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.reflect.Type b(java.lang.reflect.Type r5) {
        /*
            r4 = this;
            java.lang.reflect.Type r0 = r4.h
            boolean r0 = r0 instanceof java.lang.reflect.TypeVariable
            if (r0 != 0) goto L_0x0009
            java.lang.reflect.Type r5 = r4.h
            return r5
        L_0x0009:
            if (r5 == 0) goto L_0x0033
            java.lang.ref.WeakReference<java.lang.reflect.Type> r0 = r4.i
            if (r0 == 0) goto L_0x0033
            monitor-enter(r4)
            java.lang.ref.WeakReference<java.lang.reflect.Type> r0 = r4.i     // Catch:{ all -> 0x0030 }
            java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x0030 }
            java.lang.reflect.Type r0 = (java.lang.reflect.Type) r0     // Catch:{ all -> 0x0030 }
            boolean r0 = com.autonavi.common.reflect.ReflectUtil.isAssignable(r5, r0)     // Catch:{ all -> 0x0030 }
            if (r0 == 0) goto L_0x002e
            java.lang.ref.WeakReference<java.lang.reflect.Type> r0 = r4.k     // Catch:{ all -> 0x0030 }
            if (r0 == 0) goto L_0x002e
            java.lang.ref.WeakReference<java.lang.reflect.Type> r0 = r4.k     // Catch:{ all -> 0x0030 }
            java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x0030 }
            java.lang.reflect.Type r0 = (java.lang.reflect.Type) r0     // Catch:{ all -> 0x0030 }
            if (r0 == 0) goto L_0x002e
            monitor-exit(r4)     // Catch:{ all -> 0x0030 }
            return r0
        L_0x002e:
            monitor-exit(r4)     // Catch:{ all -> 0x0030 }
            goto L_0x0033
        L_0x0030:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0030 }
            throw r5
        L_0x0033:
            r0 = 0
            if (r5 == 0) goto L_0x0065
            boolean r1 = r5 instanceof java.lang.reflect.ParameterizedType
            if (r1 == 0) goto L_0x0065
            r1 = r5
            java.lang.reflect.ParameterizedType r1 = (java.lang.reflect.ParameterizedType) r1
            java.lang.Class<?> r2 = r4.f
            java.lang.reflect.Type r3 = r1.getRawType()
            java.lang.Class r3 = (java.lang.Class) r3
            boolean r2 = r2.isAssignableFrom(r3)
            if (r2 == 0) goto L_0x0065
            java.lang.reflect.Type r2 = r4.h
            boolean r2 = r2 instanceof java.lang.reflect.TypeVariable
            if (r2 == 0) goto L_0x0065
            java.lang.reflect.Type r2 = r4.h
            java.lang.reflect.TypeVariable r2 = (java.lang.reflect.TypeVariable) r2
            java.lang.String r2 = r2.getName()
            java.lang.Class<?> r3 = r4.f
            com.autonavi.common.reflect.GenericInfo r1 = com.autonavi.common.reflect.ReflectUtil.getGenericInfo(r2, r3, r1)
            java.lang.reflect.Type r2 = r1.parameterizedType
            r1.clear()
            goto L_0x0066
        L_0x0065:
            r2 = r0
        L_0x0066:
            if (r2 == 0) goto L_0x0069
            goto L_0x006b
        L_0x0069:
            java.lang.reflect.Type r2 = r4.h
        L_0x006b:
            if (r5 == 0) goto L_0x009d
            monitor-enter(r4)
            java.lang.ref.WeakReference<java.lang.reflect.Type> r1 = r4.i     // Catch:{ all -> 0x009a }
            if (r1 == 0) goto L_0x008a
            java.lang.ref.WeakReference<java.lang.reflect.Type> r1 = r4.i     // Catch:{ all -> 0x009a }
            java.lang.Object r1 = r1.get()     // Catch:{ all -> 0x009a }
            boolean r5 = r5.equals(r1)     // Catch:{ all -> 0x009a }
            if (r5 != 0) goto L_0x0091
            java.lang.ref.WeakReference<java.lang.reflect.Type> r5 = r4.i     // Catch:{ all -> 0x009a }
            r5.clear()     // Catch:{ all -> 0x009a }
            r4.i = r0     // Catch:{ all -> 0x009a }
            r4.k = r0     // Catch:{ all -> 0x009a }
            r4.j = r0     // Catch:{ all -> 0x009a }
            goto L_0x0091
        L_0x008a:
            java.lang.ref.WeakReference r0 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x009a }
            r0.<init>(r5)     // Catch:{ all -> 0x009a }
            r4.i = r0     // Catch:{ all -> 0x009a }
        L_0x0091:
            java.lang.ref.WeakReference r5 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x009a }
            r5.<init>(r2)     // Catch:{ all -> 0x009a }
            r4.k = r5     // Catch:{ all -> 0x009a }
            monitor-exit(r4)     // Catch:{ all -> 0x009a }
            goto L_0x009d
        L_0x009a:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x009a }
            throw r5
        L_0x009d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.big.b(java.lang.reflect.Type):java.lang.reflect.Type");
    }

    public final String a() {
        return this.e;
    }

    public final Object a(Object obj) throws Exception {
        if (this.b != null) {
            return this.b.get(obj);
        }
        return this.c.invoke(obj, new Object[0]);
    }

    public final void a(Object obj, Object obj2) throws Exception {
        if (this.b != null) {
            this.b.set(obj, obj2);
            return;
        }
        this.d.invoke(obj, new Object[]{obj2});
    }

    private static String a(String str, int i2) {
        char[] cArr = new char[(str.length() - i2)];
        int length = str.length();
        int i3 = i2;
        int i4 = 0;
        while (i3 < length) {
            char charAt = str.charAt(i3);
            if (i3 == i2) {
                charAt = Character.toLowerCase(charAt);
            }
            cArr[i4] = charAt;
            i3++;
            i4++;
        }
        return new String(cArr);
    }

    public static big[] a(Class<?> cls) {
        Field[] fields;
        Method[] methods;
        String str;
        big[] bigArr = a.get(cls);
        if (bigArr != null) {
            return bigArr;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Field field : cls.getFields()) {
            int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers) && ((Ignore) field.getAnnotation(Ignore.class)) == null) {
                String name = field.getName();
                Name name2 = (Name) field.getAnnotation(Name.class);
                if (name2 != null && !name2.value().isEmpty()) {
                    name = name2.value();
                }
                arrayList.add(new big(cls, name, field));
                arrayList2.add(name);
            }
        }
        ArrayList<Method> arrayList3 = new ArrayList<>();
        LinkedList linkedList = new LinkedList();
        for (Method method : cls.getMethods()) {
            int modifiers2 = method.getModifiers();
            if (!Modifier.isStatic(modifiers2) && !Modifier.isFinal(modifiers2) && ((Ignore) method.getAnnotation(Ignore.class)) == null) {
                String name3 = method.getName();
                Class[] parameterTypes = method.getParameterTypes();
                Class<?> returnType = method.getReturnType();
                if (name3.startsWith("set")) {
                    if (parameterTypes.length == 1 && returnType == Void.TYPE) {
                        arrayList3.add(method);
                    }
                }
                if (name3.startsWith("get")) {
                    if (parameterTypes.length == 0 && returnType != Void.TYPE) {
                        linkedList.add(method);
                    }
                }
                if (name3.startsWith("is") && parameterTypes.length == 0 && (returnType == Boolean.TYPE || returnType == Boolean.class)) {
                    linkedList.add(method);
                }
            }
        }
        for (Method method2 : arrayList3) {
            String a2 = a(method2.getName(), 3);
            Class<?> cls2 = method2.getParameterTypes()[0];
            Iterator it = linkedList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Method method3 = (Method) it.next();
                Class<?> returnType2 = method3.getReturnType();
                if (returnType2 == cls2) {
                    String name4 = method3.getName();
                    if (returnType2 == Boolean.TYPE || returnType2 == Boolean.class) {
                        str = a(name4, 2);
                    } else {
                        str = a(name4, 3);
                    }
                    if (a2.equals(str)) {
                        Name name5 = (Name) method3.getAnnotation(Name.class);
                        if (name5 != null && !name5.value().isEmpty()) {
                            str = name5.value();
                        }
                        if (!arrayList2.contains(str)) {
                            arrayList.add(new big(cls, str, method3, method2));
                            arrayList2.add(str);
                        }
                        linkedList.remove(method3);
                    }
                }
            }
        }
        arrayList3.clear();
        linkedList.clear();
        arrayList2.clear();
        big[] bigArr2 = new big[arrayList.size()];
        arrayList.toArray(bigArr2);
        arrayList.clear();
        a.put(cls, bigArr2);
        return bigArr2;
    }
}
