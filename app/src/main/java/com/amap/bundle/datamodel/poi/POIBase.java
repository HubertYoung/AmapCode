package com.amap.bundle.datamodel.poi;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.xidea.el.impl.ReflectUtil;

public class POIBase implements POI, Cloneable {
    private static Method clone = null;
    private static HashMap<Class<?>, Field[]> deepCopyMap = new HashMap<>();
    private static final long serialVersionUID = 5188079631570437426L;
    private String adCode = "";
    private String addr = "";
    private String cityCode = "";
    private String cityName = "";
    private int distance = -100;
    private String iconURL = "";
    private String id = "";
    private String industry = "";
    private String mEndPoiExtension;
    private String mFloorNoName;
    private String mTransparent;
    private int markerType = 0;
    private String name = "";
    private String phone = "";
    private String pid = "";
    protected HashMap<String, Serializable> poiExtra = new HashMap<>();
    private GeoPoint point = new GeoPoint();
    private String type = "";
    private transient HashMap<Class<?>, POI> typeMap = new HashMap<>();

    static class SubPOIHandler implements Serializable, InvocationHandler {
        private static final long serialVersionUID = 1;
        private final Object[] a = new Object[0];
        private POIBase b;

        public SubPOIHandler(POIBase pOIBase) {
            this.b = pOIBase;
        }

        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            if (objArr == null) {
                objArr = this.a;
            }
            Class<?> declaringClass = method.getDeclaringClass();
            String name = method.getName();
            if (!declaringClass.isAssignableFrom(POI.class) && declaringClass != POI.class) {
                String simpleName = declaringClass.getSimpleName();
                String replaceAll = name.replaceAll("^(?:get|set|is)([A-Z])", "$1");
                if (!replaceAll.equals(name)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(simpleName);
                    sb.append(".");
                    sb.append(replaceAll);
                    String sb2 = sb.toString();
                    char charAt = name.charAt(0);
                    if (charAt == 'g' || charAt == 'i') {
                        Serializable serializable = this.b.poiExtra.get(sb2);
                        if (serializable == null) {
                            Class<?> returnType = method.getReturnType();
                            if (returnType.isPrimitive()) {
                                if (returnType == Boolean.TYPE) {
                                    serializable = Boolean.FALSE;
                                } else if (returnType == Character.TYPE) {
                                    serializable = Character.valueOf(0);
                                } else if (Number.class.isAssignableFrom(ReflectUtil.e(returnType))) {
                                    serializable = ReflectUtil.a((Number) Integer.valueOf(0), returnType);
                                }
                            }
                        }
                        return serializable;
                    } else if (charAt == 's') {
                        return this.b.poiExtra.put(sb2, (Serializable) objArr[0]);
                    }
                }
            }
            if ("clone".equals(name)) {
                return ((POIBase) this.b.clone()).as(declaringClass.getInterfaces()[0]);
            }
            return method.invoke(this.b, objArr);
        }
    }

    static {
        try {
            Method declaredMethod = Object.class.getDeclaredMethod("clone", new Class[0]);
            clone = declaredMethod;
            declaredMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String str) {
        this.pid = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getIndustry() {
        return this.industry;
    }

    public void setIndustry(String str) {
        this.industry = str;
    }

    public GeoPoint getPoint() {
        return this.point;
    }

    public void setPoint(GeoPoint geoPoint) {
        if (geoPoint == null) {
            this.point = new GeoPoint();
        } else {
            this.point = geoPoint;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String str) {
        this.cityName = str;
    }

    public String getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(String str) {
        this.cityCode = str;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setAddr(String str) {
        this.addr = str;
    }

    public String getAdCode() {
        return this.adCode;
    }

    public void setAdCode(String str) {
        this.adCode = str;
    }

    public String getIconURL() {
        return this.iconURL;
    }

    public void setIconURL(String str) {
        this.iconURL = str;
    }

    public int getIconId() {
        return this.markerType;
    }

    public void setIconId(int i) {
        this.markerType = i;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int i) {
        this.distance = i;
    }

    public String getIndoorFloorNoName() {
        return this.mFloorNoName;
    }

    public void setInoorFloorNoName(String str) {
        this.mFloorNoName = str;
    }

    public String getEndPoiExtension() {
        return this.mEndPoiExtension;
    }

    public void setEndPoiExtension(String str) {
        this.mEndPoiExtension = str;
    }

    public String getTransparent() {
        return this.mTransparent;
    }

    public void setTransparent(String str) {
        this.mTransparent = str;
    }

    public ArrayList<GeoPoint> getEntranceList() {
        return toGeoList("entranceList");
    }

    public void setEntranceList(ArrayList<GeoPoint> arrayList) {
        this.poiExtra.put("entranceList", arrayList);
    }

    public void setExitList(ArrayList<GeoPoint> arrayList) {
        this.poiExtra.put("exitList", arrayList);
    }

    public ArrayList<GeoPoint> getExitList() {
        return toGeoList("exitList");
    }

    public synchronized <T extends POI> T as(Class<T> cls) {
        return to(cls, this, this.typeMap);
    }

    public POI clone() {
        try {
            POIBase pOIBase = (POIBase) super.clone();
            deepClone(pOIBase);
            pOIBase.typeMap = new HashMap<>();
            for (Entry next : pOIBase.poiExtra.entrySet()) {
                Serializable serializable = (Serializable) next.getValue();
                if (serializable instanceof Cloneable) {
                    next.setValue((Serializable) clone(serializable));
                }
            }
            return pOIBase;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deepClone(Cloneable cloneable) {
        Field[] deepField;
        for (Field field : getDeepField(cloneable.getClass())) {
            try {
                Object obj = field.get(cloneable);
                if (obj instanceof Object[]) {
                    Object[] objArr = (Object[]) ((Object[]) obj).clone();
                    for (int i = 0; i < objArr.length; i++) {
                        Object obj2 = objArr[i];
                        if (obj instanceof Cloneable) {
                            objArr[i] = clone(obj2);
                        }
                    }
                    field.set(cloneable, objArr);
                } else if (obj instanceof Cloneable) {
                    field.set(cloneable, clone(obj));
                }
            } catch (Exception e) {
                if (bno.a) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Object clone(Object obj) {
        try {
            return clone.invoke(obj, new Object[0]);
        } catch (Exception e) {
            if (bno.a) {
                e.printStackTrace();
            }
            return obj;
        }
    }

    private static Field[] getDeepField(Class<?> cls) {
        Field[] declaredFields;
        Field[] deepField;
        Field[] fieldArr = deepCopyMap.get(cls);
        if (fieldArr != null) {
            return fieldArr;
        }
        Class<? super T> superclass = cls.getSuperclass();
        HashMap hashMap = new HashMap();
        if (!(superclass == null || superclass == Object.class)) {
            for (Field field : getDeepField(superclass)) {
                hashMap.put(field.getName(), field);
            }
        }
        for (Field field2 : cls.getDeclaredFields()) {
            Class<?> type2 = field2.getType();
            if (!type2.isPrimitive() && String.class != type2 && !Number.class.isAssignableFrom(type2) && type2 != Boolean.class) {
                field2.setAccessible(true);
                hashMap.put(field2.getName(), field2);
            }
        }
        Field[] fieldArr2 = (Field[]) hashMap.values().toArray(new Field[hashMap.size()]);
        deepCopyMap.put(cls, fieldArr2);
        return fieldArr2;
    }

    private static <T extends POI> T to(Class<T> cls, POIBase pOIBase, HashMap<Class<?>, POI> hashMap) {
        if (cls.isInstance(pOIBase)) {
            return pOIBase;
        }
        T t = (POI) hashMap.get(cls);
        if (t == null) {
            try {
                t = createProxyInstance(cls, pOIBase);
                hashMap.put(cls, t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return t;
    }

    private static POI createProxyInstance(Class<?> cls, POIBase pOIBase) {
        try {
            SubPOIHandler subPOIHandler = new SubPOIHandler(pOIBase);
            return (POI) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, subPOIHandler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<GeoPoint> toGeoList(String str) {
        ArrayList<GeoPoint> arrayList = (ArrayList) this.poiExtra.get(str);
        if (arrayList != null) {
            int size = arrayList.size();
            while (true) {
                int i = size - 1;
                if (size <= 0) {
                    break;
                }
                GeoPoint geoPoint = arrayList.get(i);
                if (geoPoint instanceof Map) {
                    Map map = (Map) geoPoint;
                    arrayList.set(i, new GeoPoint(((Number) map.get(DictionaryKeys.CTRLXY_X)).intValue(), ((Number) map.get(DictionaryKeys.CTRLXY_Y)).intValue()));
                }
                size = i;
            }
        }
        return arrayList;
    }

    public HashMap<String, Serializable> getPoiExtra() {
        return this.poiExtra;
    }
}
