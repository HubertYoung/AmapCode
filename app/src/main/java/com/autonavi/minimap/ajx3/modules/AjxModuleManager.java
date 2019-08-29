package com.autonavi.minimap.ajx3.modules;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.autonavi.minimap.ajx3.AjxEngineProvider;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsContextRef;
import com.autonavi.minimap.ajx3.exception.IllegalModuleException;
import com.autonavi.minimap.ajx3.util.ArrayUtils;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class AjxModuleManager {
    static long count;
    /* access modifiers changed from: private */
    public AjxEngineProvider mAjxEngineProvider;
    private ModuleGroupOperator moduleGroupOperator = new ModuleGroupOperator();

    static final class ModuleGroup {
        final IAjxContext ajxContext;
        final ConcurrentHashMap<String, Object> moduleInstances = new ConcurrentHashMap<>();

        ModuleGroup(IAjxContext iAjxContext) {
            this.ajxContext = iAjxContext;
        }

        /* access modifiers changed from: 0000 */
        public final Object getModuleInstance(@NonNull String str) {
            return this.moduleInstances.get(str);
        }

        /* access modifiers changed from: 0000 */
        public final void putModuleInstance(@NonNull String str, @NonNull Object obj) {
            this.moduleInstances.put(str, obj);
        }

        /* access modifiers changed from: 0000 */
        public final void onContextDestroy() {
            for (Entry<String, Object> value : this.moduleInstances.entrySet()) {
                Object value2 = value.getValue();
                if (value2 instanceof AbstractModule) {
                    ((AbstractModule) value2).onModuleDestroy();
                }
            }
        }
    }

    final class ModuleGroupOperator {
        final ConcurrentHashMap<IAjxContext, ModuleGroup> groups;
        final Map<String, Module> moduleRegistered;

        private ModuleGroupOperator() {
            this.moduleRegistered = new HashMap();
            this.groups = new ConcurrentHashMap<>();
        }

        /* access modifiers changed from: 0000 */
        public final boolean isRegistered(String str) {
            return this.moduleRegistered.containsKey(str);
        }

        /* access modifiers changed from: 0000 */
        public final void recordModule(String str, Module module) {
            this.moduleRegistered.put(str, module);
        }

        /* access modifiers changed from: 0000 */
        public final Object getModuleIns(@NonNull IAjxContext iAjxContext, @NonNull String str) throws Exception {
            return getModuleInsInternal(iAjxContext, str);
        }

        /* access modifiers changed from: 0000 */
        public final Object getModuleInsInternal(@NonNull IAjxContext iAjxContext, @NonNull String str) throws Exception {
            ModuleGroup moduleGroup = this.groups.get(iAjxContext);
            if (moduleGroup == null) {
                moduleGroup = new ModuleGroup(iAjxContext);
                this.groups.putIfAbsent(iAjxContext, moduleGroup);
            }
            Object moduleInstance = moduleGroup.getModuleInstance(str);
            if (moduleInstance != null) {
                return moduleInstance;
            }
            Object createInstance = tryGetModule(str).createInstance(iAjxContext);
            moduleGroup.putModuleInstance(str, createInstance);
            return createInstance;
        }

        /* access modifiers changed from: 0000 */
        public final void onContextDestroy(@NonNull JsContextRef jsContextRef) {
            for (Entry next : this.groups.entrySet()) {
                IAjxContext iAjxContext = (IAjxContext) next.getKey();
                if (iAjxContext != null && iAjxContext.getJsContext().shadow() == jsContextRef.shadow()) {
                    ((ModuleGroup) next.getValue()).onContextDestroy();
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final void removeGroup(@NonNull long j) {
            Iterator<IAjxContext> it = this.groups.keySet().iterator();
            while (it.hasNext()) {
                IAjxContext next = it.next();
                if (next == null) {
                    it.remove();
                } else if (next.getJsContext().shadow() == j) {
                    it.remove();
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final void removeAllGroup() {
            this.groups.clear();
        }

        /* access modifiers changed from: private */
        public Module tryGetModule(@NonNull String str) throws Exception {
            Module module = this.moduleRegistered.get(str);
            if (module == null) {
                IModuleWhiteList iModuleWhiteList = AjxModuleManager.this.mAjxEngineProvider.getAjxConfig().getIModuleWhiteList();
                if (iModuleWhiteList != null) {
                    String moduleClassName = iModuleWhiteList.getModuleClassName(str);
                    if (!TextUtils.isEmpty(moduleClassName)) {
                        AjxModuleManager.this.registerModule(Class.forName(moduleClassName), false);
                        module = this.moduleRegistered.get(str);
                    }
                    if (module == null) {
                        Object[] newModule = iModuleWhiteList.getNewModule(str);
                        if (newModule != null && newModule.length == 3) {
                            AjxModuleManager.this.registerModule(str, newModule[0].toString(), (String[]) newModule[1], (String[]) newModule[2]);
                            module = this.moduleRegistered.get(str);
                        }
                    }
                }
                if (module == null) {
                    StringBuilder sb = new StringBuilder("No such Module [");
                    sb.append(str);
                    sb.append("] in ModuleWhiteList !");
                    throw new NullPointerException(sb.toString());
                }
            }
            return module;
        }

        /* access modifiers changed from: private */
        public boolean removeModule(String str) {
            return this.moduleRegistered.remove(str) != null;
        }
    }

    static class MyComparator implements Serializable, Comparator<Member> {
        private static final long serialVersionUID = 1;

        private MyComparator() {
        }

        public int compare(Member member, Member member2) {
            return hashCode(member) < hashCode(member2) ? -1 : 1;
        }

        public int hashCode(Member member) {
            return member.getName().hashCode() ^ member.getDeclaringClass().getName().hashCode();
        }
    }

    public AjxModuleManager(@NonNull AjxEngineProvider ajxEngineProvider) {
        this.mAjxEngineProvider = ajxEngineProvider;
    }

    public boolean registerModule(@NonNull Class<?> cls, boolean z) throws Exception {
        if (!cls.isAnnotationPresent(AjxModule.class)) {
            StringBuilder sb = new StringBuilder();
            sb.append(cls);
            sb.append(" not annotation present AjxModule");
            throw new IllegalModuleException(sb.toString());
        }
        AjxModule ajxModule = (AjxModule) cls.getAnnotation(AjxModule.class);
        String value = ajxModule.value();
        boolean isInUiThread = ajxModule.isInUiThread();
        if (TextUtils.isEmpty(value)) {
            value = cls.getSimpleName();
        }
        if (this.moduleGroupOperator.isRegistered(value)) {
            if (!this.moduleGroupOperator.tryGetModule(value).getClazz().isAssignableFrom(cls)) {
                return false;
            }
            this.moduleGroupOperator.removeModule(value);
        }
        Module module = new Module(value, cls);
        Method[] methods = cls.getMethods();
        Arrays.sort(methods, new MyComparator());
        StringBuilder sb2 = null;
        for (Method method : methods) {
            if (method.isAnnotationPresent(AjxMethod.class)) {
                AjxMethod ajxMethod = (AjxMethod) method.getAnnotation(AjxMethod.class);
                String value2 = ajxMethod.value();
                if (TextUtils.isEmpty(value2)) {
                    value2 = method.getName();
                }
                String invokeMode = ajxMethod.invokeMode();
                if (sb2 == null) {
                    sb2 = new StringBuilder();
                    sb2.append("{\"methods\":{");
                } else {
                    sb2.append(",");
                }
                int addMethod = module.addMethod(method);
                sb2.append("\"");
                sb2.append(value2);
                sb2.append("\":{\"type\":\"remote\",\"methodID\":");
                sb2.append(addMethod);
                sb2.append(",\"invoke_mode\":");
                StringBuilder sb3 = new StringBuilder("\"");
                sb3.append(invokeMode);
                sb3.append("\"");
                sb2.append(sb3.toString());
                sb2.append(h.d);
            }
        }
        if (sb2 != null) {
            sb2.append(" }");
        }
        Field[] fields = cls.getFields();
        Arrays.sort(fields, new MyComparator());
        boolean z2 = false;
        for (Field field : fields) {
            if (field.isAnnotationPresent(AjxField.class)) {
                String value3 = ((AjxField) field.getAnnotation(AjxField.class)).value();
                if (TextUtils.isEmpty(value3)) {
                    value3 = field.getName();
                }
                if (sb2 == null) {
                    sb2 = new StringBuilder("{\"fields\":{");
                } else if (!z2) {
                    sb2.append(",\"fields\":{");
                } else {
                    sb2.append(",");
                }
                int addField = module.addField(field);
                sb2.append("\"");
                sb2.append(value3);
                sb2.append("\":{\"type\":\"remote\",\"fieldID\":");
                sb2.append(addField);
                sb2.append(" }");
                z2 = true;
            }
        }
        if (sb2 == null) {
            return false;
        }
        if (z2) {
            sb2.append("}}");
        } else {
            sb2.append(h.d);
        }
        this.moduleGroupOperator.recordModule(value, module);
        if (z) {
            this.mAjxEngineProvider.registerJsModule(value, sb2.toString(), isInUiThread);
        }
        return true;
    }

    public Object onMethodCall(IAjxContext iAjxContext, String str, int i, Object... objArr) throws Exception {
        int i2;
        Method method = this.moduleGroupOperator.tryGetModule(str).getMethod(i);
        if (objArr == null) {
            i2 = 0;
        } else {
            i2 = objArr.length;
        }
        Class[] parameterTypes = method.getParameterTypes();
        if (i2 > parameterTypes.length) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("的");
            sb.append(method.getName());
            sb.append("方法需要");
            sb.append(parameterTypes.length);
            sb.append("个参数, 但是从js传递过来");
            sb.append(i2);
            sb.append("个参数, 请检查一下代码。");
            throw new RuntimeException(sb.toString());
        }
        for (int i3 = 0; i3 < i2; i3++) {
            objArr[i3] = jsValue2JavaType(parameterTypes[i3], objArr[i3]);
        }
        if (i2 < parameterTypes.length) {
            Object[] objArr2 = new Object[parameterTypes.length];
            if (objArr != null) {
                System.arraycopy(objArr, 0, objArr2, 0, i2);
            }
            while (i2 < parameterTypes.length) {
                objArr2[i2] = null;
                i2++;
            }
            objArr = objArr2;
        }
        return method.invoke(this.moduleGroupOperator.getModuleIns(iAjxContext, str), objArr);
    }

    public Object onGetField(IAjxContext iAjxContext, String str, int i) throws Exception {
        Module access$100 = this.moduleGroupOperator.tryGetModule(str);
        if (!access$100.isNewModule()) {
            return javaValue2JsValue(access$100.getField(i).get(this.moduleGroupOperator.getModuleIns(iAjxContext, str)));
        }
        return javaValue2JsValue(access$100.getFieldGetMethod(i).invoke(this.moduleGroupOperator.getModuleIns(iAjxContext, str), new Object[0]));
    }

    public void onSetField(IAjxContext iAjxContext, String str, int i, Object obj) throws Exception {
        Module access$100 = this.moduleGroupOperator.tryGetModule(str);
        if (access$100.isNewModule()) {
            Object moduleIns = this.moduleGroupOperator.getModuleIns(iAjxContext, str);
            Method fieldSetMethod = access$100.getFieldSetMethod(i);
            fieldSetMethod.invoke(moduleIns, new Object[]{jsValue2JavaType(fieldSetMethod.getParameterTypes()[0], obj)});
            return;
        }
        Field field = access$100.getField(i);
        field.set(this.moduleGroupOperator.getModuleIns(iAjxContext, str), jsValue2JavaType(field.getType(), obj));
    }

    public void onContextDestroy(JsContextRef jsContextRef) {
        this.moduleGroupOperator.onContextDestroy(jsContextRef);
    }

    public void clearContextModules(long j) {
        this.moduleGroupOperator.removeGroup(j);
    }

    public void clearAllContextModules() {
        this.moduleGroupOperator.removeAllGroup();
    }

    public Object getModuleIns(@NonNull IAjxContext iAjxContext, @NonNull String str) throws Exception {
        return this.moduleGroupOperator.getModuleIns(iAjxContext, str);
    }

    private Object jsValue2JavaType(Class<?> cls, Object obj) {
        if (obj == null) {
            if (cls.isPrimitive()) {
                if (Integer.TYPE == cls || Double.TYPE == cls || Long.TYPE == cls || Short.TYPE == cls || Float.TYPE == cls || Byte.TYPE == cls) {
                    return Integer.valueOf(0);
                }
                if (cls == Boolean.TYPE) {
                    return Boolean.FALSE;
                }
            }
            return null;
        }
        Class<?> cls2 = obj.getClass();
        if (cls2 == cls || cls.isAssignableFrom(cls2)) {
            return obj;
        }
        if (cls.isArray() && cls2.isArray()) {
            return jsArray2JavaArray(cls, obj);
        }
        if (cls == Integer.TYPE || cls == Integer.class) {
            return Integer.valueOf(((Number) obj).intValue());
        }
        if (cls == Float.TYPE || cls == Float.class) {
            return Float.valueOf(((Number) obj).floatValue());
        }
        if (cls == Boolean.TYPE || cls == Boolean.class || cls == Double.TYPE || cls == Double.class) {
            return obj;
        }
        if (cls == Long.TYPE || cls == Long.class) {
            return Long.valueOf(((Number) obj).longValue());
        }
        if (cls == Byte.TYPE || cls == Byte.class) {
            return Byte.valueOf(((Number) obj).byteValue());
        }
        if (cls == Character.TYPE || cls == Character.class) {
            return Character.valueOf((char) ((Number) obj).intValue());
        }
        if (CharSequence.class.isAssignableFrom(cls)) {
            return obj.toString();
        }
        StringBuilder sb = new StringBuilder("Invalid Parameters. ");
        sb.append(obj);
        sb.append(" cann't cast to class ");
        sb.append(cls.getName());
        throw new RuntimeException(sb.toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x007e A[LOOP:0: B:40:0x007b->B:42:0x007e, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x008f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.Object jsArray2JavaArray(java.lang.Class<?> r5, java.lang.Object r6) {
        /*
            r4 = this;
            java.lang.Class r5 = r5.getComponentType()
            java.lang.Object[] r6 = (java.lang.Object[]) r6
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 0
            if (r5 != r0) goto L_0x0011
            int r0 = r6.length
            java.lang.String[] r0 = new java.lang.String[r0]
        L_0x000e:
            r2 = 0
            goto L_0x007b
        L_0x0011:
            java.lang.Class r0 = java.lang.Integer.TYPE
            if (r5 == r0) goto L_0x0077
            java.lang.Class<java.lang.Integer> r0 = java.lang.Integer.class
            if (r5 != r0) goto L_0x001b
            goto L_0x0077
        L_0x001b:
            java.lang.Class r0 = java.lang.Float.TYPE
            if (r5 == r0) goto L_0x0072
            java.lang.Class<java.lang.Float> r0 = java.lang.Float.class
            if (r5 != r0) goto L_0x0024
            goto L_0x0072
        L_0x0024:
            java.lang.Class r0 = java.lang.Boolean.TYPE
            if (r5 == r0) goto L_0x006d
            java.lang.Class<java.lang.Boolean> r0 = java.lang.Boolean.class
            if (r5 != r0) goto L_0x002d
            goto L_0x006d
        L_0x002d:
            java.lang.Class r0 = java.lang.Double.TYPE
            if (r5 == r0) goto L_0x0068
            java.lang.Class<java.lang.Double> r0 = java.lang.Double.class
            if (r5 != r0) goto L_0x0036
            goto L_0x0068
        L_0x0036:
            java.lang.Class r0 = java.lang.Long.TYPE
            if (r5 == r0) goto L_0x0063
            java.lang.Class<java.lang.Long> r0 = java.lang.Long.class
            if (r5 != r0) goto L_0x003f
            goto L_0x0063
        L_0x003f:
            java.lang.Class r0 = java.lang.Byte.TYPE
            if (r5 == r0) goto L_0x005e
            java.lang.Class<java.lang.Byte> r0 = java.lang.Byte.class
            if (r5 != r0) goto L_0x0048
            goto L_0x005e
        L_0x0048:
            java.lang.Class r0 = java.lang.Character.TYPE
            if (r5 == r0) goto L_0x0059
            java.lang.Class<java.lang.Character> r0 = java.lang.Character.class
            if (r5 != r0) goto L_0x0051
            goto L_0x0059
        L_0x0051:
            int r0 = r6.length
            java.lang.Object r0 = java.lang.reflect.Array.newInstance(r5, r0)
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            goto L_0x000e
        L_0x0059:
            int r0 = r6.length
            java.lang.Character[] r0 = new java.lang.Character[r0]
            r2 = 7
            goto L_0x007b
        L_0x005e:
            int r0 = r6.length
            java.lang.Byte[] r0 = new java.lang.Byte[r0]
            r2 = 6
            goto L_0x007b
        L_0x0063:
            int r0 = r6.length
            java.lang.Long[] r0 = new java.lang.Long[r0]
            r2 = 5
            goto L_0x007b
        L_0x0068:
            int r0 = r6.length
            java.lang.Double[] r0 = new java.lang.Double[r0]
            r2 = 4
            goto L_0x007b
        L_0x006d:
            int r0 = r6.length
            java.lang.Boolean[] r0 = new java.lang.Boolean[r0]
            r2 = 3
            goto L_0x007b
        L_0x0072:
            int r0 = r6.length
            java.lang.Float[] r0 = new java.lang.Float[r0]
            r2 = 2
            goto L_0x007b
        L_0x0077:
            int r0 = r6.length
            java.lang.Integer[] r0 = new java.lang.Integer[r0]
            r2 = 1
        L_0x007b:
            int r3 = r0.length
            if (r1 >= r3) goto L_0x0089
            r3 = r6[r1]
            java.lang.Object r3 = r4.jsValue2JavaType(r5, r3)
            r0[r1] = r3
            int r1 = r1 + 1
            goto L_0x007b
        L_0x0089:
            boolean r5 = r5.isPrimitive()
            if (r5 == 0) goto L_0x00c4
            switch(r2) {
                case 1: goto L_0x00bd;
                case 2: goto L_0x00b6;
                case 3: goto L_0x00af;
                case 4: goto L_0x00a8;
                case 5: goto L_0x00a1;
                case 6: goto L_0x009a;
                case 7: goto L_0x0093;
                default: goto L_0x0092;
            }
        L_0x0092:
            goto L_0x00c4
        L_0x0093:
            java.lang.Character[] r0 = (java.lang.Character[]) r0
            char[] r5 = com.autonavi.minimap.ajx3.util.ArrayUtils.toPrimary(r0)
            return r5
        L_0x009a:
            java.lang.Byte[] r0 = (java.lang.Byte[]) r0
            byte[] r5 = com.autonavi.minimap.ajx3.util.ArrayUtils.toPrimary(r0)
            return r5
        L_0x00a1:
            java.lang.Long[] r0 = (java.lang.Long[]) r0
            long[] r5 = com.autonavi.minimap.ajx3.util.ArrayUtils.toPrimary(r0)
            return r5
        L_0x00a8:
            java.lang.Double[] r0 = (java.lang.Double[]) r0
            double[] r5 = com.autonavi.minimap.ajx3.util.ArrayUtils.toPrimary(r0)
            return r5
        L_0x00af:
            java.lang.Boolean[] r0 = (java.lang.Boolean[]) r0
            boolean[] r5 = com.autonavi.minimap.ajx3.util.ArrayUtils.toPrimary(r0)
            return r5
        L_0x00b6:
            java.lang.Float[] r0 = (java.lang.Float[]) r0
            float[] r5 = com.autonavi.minimap.ajx3.util.ArrayUtils.toPrimary(r0)
            return r5
        L_0x00bd:
            java.lang.Integer[] r0 = (java.lang.Integer[]) r0
            int[] r5 = com.autonavi.minimap.ajx3.util.ArrayUtils.toPrimary(r0)
            return r5
        L_0x00c4:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.AjxModuleManager.jsArray2JavaArray(java.lang.Class, java.lang.Object):java.lang.Object");
    }

    private Object javaValue2JsValue(Object obj) {
        return (obj == null || !obj.getClass().isArray()) ? obj : javaArray2JsArray(obj);
    }

    private Object javaArray2JsArray(@NonNull Object obj) {
        Object obj2;
        Class<?> cls = obj.getClass();
        if (Object[].class.isAssignableFrom(cls)) {
            return obj;
        }
        Class<?> componentType = cls.getComponentType();
        if (componentType == Integer.TYPE) {
            obj2 = ArrayUtils.toObject((int[]) obj);
        } else if (componentType == Double.TYPE) {
            obj2 = ArrayUtils.toObject((double[]) obj);
        } else if (componentType == Boolean.TYPE) {
            obj2 = ArrayUtils.toObject((boolean[]) obj);
        } else if (componentType == Float.TYPE) {
            obj2 = ArrayUtils.toObject((float[]) obj);
        } else if (componentType == Long.TYPE) {
            obj2 = ArrayUtils.toObject((long[]) obj);
        } else if (componentType == Byte.TYPE) {
            obj2 = ArrayUtils.toObject((byte[]) obj);
        } else if (componentType == Character.TYPE) {
            obj2 = ArrayUtils.toObject((char[]) obj);
        } else if (componentType == Short.TYPE) {
            obj2 = ArrayUtils.toObject((short[]) obj);
        } else {
            throw new RuntimeException("Can't find valid Array Type.");
        }
        return obj2;
    }

    /* access modifiers changed from: private */
    public void registerModule(String str, String str2, String[] strArr, String[] strArr2) throws Exception {
        Module module = new Module(str, Class.forName(str2), true);
        if (strArr != null && strArr.length > 0) {
            for (String addMethod : strArr) {
                module.addMethod(addMethod);
            }
        }
        if (strArr2 != null && strArr2.length > 0) {
            for (String addField : strArr2) {
                module.addField(addField);
            }
        }
        this.moduleGroupOperator.recordModule(str, module);
    }
}
