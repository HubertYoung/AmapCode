package com.autonavi.minimap.ajx3.modules;

import android.util.SparseArray;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Module {
    private Class clazz;
    private int curFieldID;
    private int curMethodID;
    private SparseArray<Method> fieldGetMethods;
    private SparseArray<Method> fieldSetMethods;
    private SparseArray<Field> fields;
    private SparseArray<Method> methods;
    private String name;
    private boolean newModule;

    Module(String str, Class cls) {
        this(str, cls, false);
    }

    Module(String str, Class cls, boolean z) {
        this.name = str;
        this.clazz = cls;
        this.newModule = z;
        this.methods = new SparseArray<>();
        this.fields = new SparseArray<>();
        this.fieldSetMethods = new SparseArray<>();
        this.fieldGetMethods = new SparseArray<>();
    }

    /* access modifiers changed from: 0000 */
    public Object createInstance(IAjxContext iAjxContext) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return this.clazz.getConstructor(new Class[]{IAjxContext.class}).newInstance(new Object[]{iAjxContext});
    }

    /* access modifiers changed from: 0000 */
    public int addMethod(Method method) {
        int i = this.curMethodID;
        this.methods.put(i, method);
        this.curMethodID++;
        return i;
    }

    /* access modifiers changed from: 0000 */
    public Method getMethod(int i) {
        return this.methods.get(i);
    }

    /* access modifiers changed from: 0000 */
    public int addField(Field field) {
        int i = this.curFieldID;
        this.fields.put(i, field);
        this.curFieldID++;
        return i;
    }

    /* access modifiers changed from: 0000 */
    public Field getField(int i) {
        return this.fields.get(i);
    }

    /* access modifiers changed from: 0000 */
    public Class getClazz() {
        return this.clazz;
    }

    /* access modifiers changed from: 0000 */
    public void addMethod(String str) {
        if (isNewModule()) {
            Method[] methods2 = this.clazz.getMethods();
            if (methods2 != null && methods2.length > 0) {
                for (Method method : methods2) {
                    if (method.getName().equals(str)) {
                        addMethod(method);
                        return;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void addField(String str) {
        Method method;
        if (isNewModule()) {
            int i = this.curFieldID;
            Method[] methods2 = this.clazz.getMethods();
            Method method2 = null;
            if (methods2 != null && methods2.length > 0) {
                method = null;
                for (Method method3 : methods2) {
                    String lowerCase = method3.getName().toLowerCase();
                    str = str.toLowerCase();
                    if (lowerCase.equals("set".concat(String.valueOf(str)))) {
                        method2 = method3;
                    } else if (lowerCase.equals("get".concat(String.valueOf(str)))) {
                        method = method3;
                    }
                    if (method2 != null && method != null) {
                        break;
                    }
                }
            } else {
                method = null;
            }
            this.fieldSetMethods.put(i, method2);
            this.fieldGetMethods.put(i, method);
            this.curFieldID++;
        }
    }

    /* access modifiers changed from: 0000 */
    public Method getFieldGetMethod(int i) {
        return this.fieldGetMethods.get(i);
    }

    /* access modifiers changed from: 0000 */
    public Method getFieldSetMethod(int i) {
        return this.fieldSetMethods.get(i);
    }

    /* access modifiers changed from: 0000 */
    public boolean isNewModule() {
        return this.newModule;
    }
}
