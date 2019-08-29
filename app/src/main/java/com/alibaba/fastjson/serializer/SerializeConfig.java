package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.util.IdentityHashMap;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

public class SerializeConfig {
    public static final SerializeConfig globalInstance = new SerializeConfig();
    public PropertyNamingStrategy propertyNamingStrategy;
    private final IdentityHashMap<ObjectSerializer> serializers = new IdentityHashMap<>(1024);
    protected String typeKey = JSON.DEFAULT_TYPE_KEY;

    public static final SerializeConfig getGlobalInstance() {
        return globalInstance;
    }

    public ObjectSerializer registerIfNotExists(Class<?> cls) {
        return registerIfNotExists(cls, cls.getModifiers(), false, true, true, true);
    }

    public ObjectSerializer registerIfNotExists(Class<?> cls, int i, boolean z, boolean z2, boolean z3, boolean z4) {
        Class<?> cls2 = cls;
        ObjectSerializer objectSerializer = (ObjectSerializer) this.serializers.get(cls2);
        if (objectSerializer != null) {
            return objectSerializer;
        }
        JavaBeanSerializer javaBeanSerializer = new JavaBeanSerializer(cls2, i, null, z, z2, z3, z4, this.propertyNamingStrategy);
        this.serializers.put(cls2, javaBeanSerializer);
        return javaBeanSerializer;
    }

    public SerializeConfig() {
        this.serializers.put(Boolean.class, BooleanCodec.instance);
        this.serializers.put(Character.class, MiscCodec.instance);
        this.serializers.put(Byte.class, IntegerCodec.instance);
        this.serializers.put(Short.class, IntegerCodec.instance);
        this.serializers.put(Integer.class, IntegerCodec.instance);
        this.serializers.put(Long.class, IntegerCodec.instance);
        this.serializers.put(Float.class, NumberCodec.instance);
        this.serializers.put(Double.class, NumberCodec.instance);
        this.serializers.put(Number.class, NumberCodec.instance);
        this.serializers.put(BigDecimal.class, BigDecimalCodec.instance);
        this.serializers.put(BigInteger.class, BigDecimalCodec.instance);
        this.serializers.put(String.class, StringCodec.instance);
        this.serializers.put(Object[].class, ArrayCodec.instance);
        this.serializers.put(Class.class, MiscCodec.instance);
        this.serializers.put(SimpleDateFormat.class, MiscCodec.instance);
        this.serializers.put(Locale.class, MiscCodec.instance);
        this.serializers.put(Currency.class, MiscCodec.instance);
        this.serializers.put(TimeZone.class, MiscCodec.instance);
        this.serializers.put(UUID.class, MiscCodec.instance);
        this.serializers.put(URI.class, MiscCodec.instance);
        this.serializers.put(URL.class, MiscCodec.instance);
        this.serializers.put(Pattern.class, MiscCodec.instance);
        this.serializers.put(Charset.class, MiscCodec.instance);
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [com.alibaba.fastjson.serializer.EnumSerializer, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v6, types: [com.alibaba.fastjson.serializer.DateCodec, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v7, types: [java.lang.Object, com.alibaba.fastjson.serializer.MiscCodec] */
    /* JADX WARNING: type inference failed for: r1v8, types: [java.lang.Object, com.alibaba.fastjson.serializer.MiscCodec] */
    /* JADX WARNING: type inference failed for: r1v9, types: [java.lang.Object, com.alibaba.fastjson.serializer.MiscCodec] */
    /* JADX WARNING: type inference failed for: r1v16, types: [java.lang.Object, com.alibaba.fastjson.serializer.MiscCodec] */
    /* JADX WARNING: type inference failed for: r1v17, types: [java.lang.Object, com.alibaba.fastjson.serializer.MiscCodec] */
    /* JADX WARNING: type inference failed for: r1v18, types: [java.lang.Object, com.alibaba.fastjson.serializer.MiscCodec] */
    /* JADX WARNING: type inference failed for: r1v19, types: [com.alibaba.fastjson.serializer.DateCodec, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v20, types: [com.alibaba.fastjson.serializer.CollectionCodec, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v21, types: [java.lang.Object, com.alibaba.fastjson.serializer.ListSerializer] */
    /* JADX WARNING: type inference failed for: r1v22, types: [com.alibaba.fastjson.serializer.CollectionCodec, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v23 */
    /* JADX WARNING: type inference failed for: r1v24, types: [com.alibaba.fastjson.serializer.MapSerializer, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v25 */
    /* JADX WARNING: type inference failed for: r1v26 */
    /* JADX WARNING: type inference failed for: r1v27 */
    /* JADX WARNING: type inference failed for: r1v28 */
    /* JADX WARNING: type inference failed for: r1v29 */
    /* JADX WARNING: type inference failed for: r1v30 */
    /* JADX WARNING: type inference failed for: r1v31 */
    /* JADX WARNING: type inference failed for: r1v32 */
    /* JADX WARNING: type inference failed for: r1v33 */
    /* JADX WARNING: type inference failed for: r1v34 */
    /* JADX WARNING: type inference failed for: r1v35 */
    /* JADX WARNING: type inference failed for: r1v36 */
    /* JADX WARNING: type inference failed for: r1v37 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x019c  */
    /* JADX WARNING: Unknown variable types count: 14 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alibaba.fastjson.serializer.ObjectSerializer get(java.lang.Class<?> r9) {
        /*
            r8 = this;
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            java.lang.Object r0 = r0.get(r9)
            com.alibaba.fastjson.serializer.ObjectSerializer r0 = (com.alibaba.fastjson.serializer.ObjectSerializer) r0
            if (r0 != 0) goto L_0x01a5
            java.lang.Class<java.util.Map> r0 = java.util.Map.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L_0x001f
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            com.alibaba.fastjson.serializer.MapSerializer r1 = new com.alibaba.fastjson.serializer.MapSerializer
            r1.<init>()
            r0.put(r9, r1)
        L_0x001c:
            r0 = r1
            goto L_0x019a
        L_0x001f:
            java.lang.Class<java.util.AbstractSequentialList> r0 = java.util.AbstractSequentialList.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L_0x002f
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            com.alibaba.fastjson.serializer.CollectionCodec r1 = com.alibaba.fastjson.serializer.CollectionCodec.instance
            r0.put(r9, r1)
            goto L_0x001c
        L_0x002f:
            java.lang.Class<java.util.List> r0 = java.util.List.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L_0x0042
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            com.alibaba.fastjson.serializer.ListSerializer r1 = new com.alibaba.fastjson.serializer.ListSerializer
            r1.<init>()
            r0.put(r9, r1)
            goto L_0x001c
        L_0x0042:
            java.lang.Class<java.util.Collection> r0 = java.util.Collection.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L_0x0052
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            com.alibaba.fastjson.serializer.CollectionCodec r1 = com.alibaba.fastjson.serializer.CollectionCodec.instance
            r0.put(r9, r1)
            goto L_0x001c
        L_0x0052:
            java.lang.Class<java.util.Date> r0 = java.util.Date.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L_0x0062
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            com.alibaba.fastjson.serializer.DateCodec r1 = com.alibaba.fastjson.serializer.DateCodec.instance
            r0.put(r9, r1)
            goto L_0x001c
        L_0x0062:
            java.lang.Class<com.alibaba.fastjson.JSONAware> r0 = com.alibaba.fastjson.JSONAware.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L_0x0072
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            com.alibaba.fastjson.serializer.MiscCodec r1 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r0.put(r9, r1)
            goto L_0x001c
        L_0x0072:
            java.lang.Class<com.alibaba.fastjson.serializer.JSONSerializable> r0 = com.alibaba.fastjson.serializer.JSONSerializable.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L_0x0082
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            com.alibaba.fastjson.serializer.MiscCodec r1 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r0.put(r9, r1)
            goto L_0x001c
        L_0x0082:
            java.lang.Class<com.alibaba.fastjson.JSONStreamAware> r0 = com.alibaba.fastjson.JSONStreamAware.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L_0x0092
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            com.alibaba.fastjson.serializer.MiscCodec r1 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r0.put(r9, r1)
            goto L_0x001c
        L_0x0092:
            boolean r0 = r9.isEnum()
            if (r0 != 0) goto L_0x018e
            java.lang.Class r0 = r9.getSuperclass()
            if (r0 == 0) goto L_0x00aa
            java.lang.Class<java.lang.Object> r1 = java.lang.Object.class
            if (r0 == r1) goto L_0x00aa
            boolean r0 = r0.isEnum()
            if (r0 == 0) goto L_0x00aa
            goto L_0x018e
        L_0x00aa:
            boolean r0 = r9.isArray()
            if (r0 == 0) goto L_0x00c5
            java.lang.Class r0 = r9.getComponentType()
            com.alibaba.fastjson.serializer.ObjectSerializer r1 = r8.get(r0)
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r2 = r8.serializers
            com.alibaba.fastjson.serializer.ArraySerializer r3 = new com.alibaba.fastjson.serializer.ArraySerializer
            r3.<init>(r0, r1)
            r2.put(r9, r3)
            r0 = r3
            goto L_0x019a
        L_0x00c5:
            java.lang.Class<java.lang.Throwable> r0 = java.lang.Throwable.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L_0x00e4
            com.alibaba.fastjson.serializer.JavaBeanSerializer r0 = new com.alibaba.fastjson.serializer.JavaBeanSerializer
            com.alibaba.fastjson.PropertyNamingStrategy r1 = r8.propertyNamingStrategy
            r0.<init>(r9, r1)
            int r1 = r0.features
            com.alibaba.fastjson.serializer.SerializerFeature r2 = com.alibaba.fastjson.serializer.SerializerFeature.WriteClassName
            int r2 = r2.mask
            r1 = r1 | r2
            r0.features = r1
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r1 = r8.serializers
            r1.put(r9, r0)
            goto L_0x019a
        L_0x00e4:
            java.lang.Class<java.util.TimeZone> r0 = java.util.TimeZone.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L_0x00f5
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            com.alibaba.fastjson.serializer.MiscCodec r1 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r0.put(r9, r1)
            goto L_0x001c
        L_0x00f5:
            java.lang.Class<java.nio.charset.Charset> r0 = java.nio.charset.Charset.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L_0x0106
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            com.alibaba.fastjson.serializer.MiscCodec r1 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r0.put(r9, r1)
            goto L_0x001c
        L_0x0106:
            java.lang.Class<java.util.Enumeration> r0 = java.util.Enumeration.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L_0x0117
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            com.alibaba.fastjson.serializer.MiscCodec r1 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r0.put(r9, r1)
            goto L_0x001c
        L_0x0117:
            java.lang.Class<java.util.Calendar> r0 = java.util.Calendar.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L_0x0128
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            com.alibaba.fastjson.serializer.DateCodec r1 = com.alibaba.fastjson.serializer.DateCodec.instance
            r0.put(r9, r1)
            goto L_0x001c
        L_0x0128:
            java.lang.Class[] r0 = r9.getInterfaces()
            int r1 = r0.length
            r2 = 0
            r3 = 0
        L_0x012f:
            r4 = 1
            if (r3 >= r1) goto L_0x015e
            r5 = r0[r3]
            java.lang.String r6 = r5.getName()
            java.lang.String r7 = "net.sf.cglib.proxy.Factory"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x015d
            java.lang.String r6 = r5.getName()
            java.lang.String r7 = "org.springframework.cglib.proxy.Factory"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x014d
            goto L_0x015d
        L_0x014d:
            java.lang.String r5 = r5.getName()
            java.lang.String r6 = "javassist.util.proxy.ProxyObject"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x015a
            goto L_0x015f
        L_0x015a:
            int r3 = r3 + 1
            goto L_0x012f
        L_0x015d:
            r2 = 1
        L_0x015e:
            r4 = 0
        L_0x015f:
            if (r2 != 0) goto L_0x0180
            if (r4 == 0) goto L_0x0164
            goto L_0x0180
        L_0x0164:
            java.lang.String r0 = r9.getName()
            java.lang.String r1 = "android.net.Uri$"
            boolean r0 = r0.startsWith(r1)
            if (r0 == 0) goto L_0x0173
            com.alibaba.fastjson.serializer.MiscCodec r0 = com.alibaba.fastjson.serializer.MiscCodec.instance
            goto L_0x017a
        L_0x0173:
            com.alibaba.fastjson.serializer.JavaBeanSerializer r0 = new com.alibaba.fastjson.serializer.JavaBeanSerializer
            com.alibaba.fastjson.PropertyNamingStrategy r1 = r8.propertyNamingStrategy
            r0.<init>(r9, r1)
        L_0x017a:
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r1 = r8.serializers
            r1.put(r9, r0)
            goto L_0x019a
        L_0x0180:
            java.lang.Class r0 = r9.getSuperclass()
            com.alibaba.fastjson.serializer.ObjectSerializer r0 = r8.get(r0)
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r1 = r8.serializers
            r1.put(r9, r0)
            return r0
        L_0x018e:
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            com.alibaba.fastjson.serializer.EnumSerializer r1 = new com.alibaba.fastjson.serializer.EnumSerializer
            r1.<init>()
            r0.put(r9, r1)
            goto L_0x001c
        L_0x019a:
            if (r0 != 0) goto L_0x01a5
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r0 = r8.serializers
            java.lang.Object r9 = r0.get(r9)
            r0 = r9
            com.alibaba.fastjson.serializer.ObjectSerializer r0 = (com.alibaba.fastjson.serializer.ObjectSerializer) r0
        L_0x01a5:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.SerializeConfig.get(java.lang.Class):com.alibaba.fastjson.serializer.ObjectSerializer");
    }

    public boolean put(Type type, ObjectSerializer objectSerializer) {
        return this.serializers.put(type, objectSerializer);
    }

    public String getTypeKey() {
        return this.typeKey;
    }

    public void setTypeKey(String str) {
        this.typeKey = str;
    }
}
