package com.autonavi.inter.impl;

import com.autonavi.inter.IMainMapFeatureProvider;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class MainMapFeatureProviderImpl implements IMainMapFeatureProvider {
    private static final Set<Class<?>> sMainMapFeatureSet;

    static {
        HashSet hashSet = new HashSet();
        sMainMapFeatureSet = hashSet;
        hashSet.addAll(new BUSCARD_MainMapFeature_DATA());
        sMainMapFeatureSet.addAll(new FREQUENTLOCATION_MainMapFeature_DATA());
        sMainMapFeatureSet.addAll(new MSGBOX_MainMapFeature_DATA());
        sMainMapFeatureSet.addAll(new QRCODE_MainMapFeature_DATA());
        sMainMapFeatureSet.addAll(new AGROUP_MainMapFeature_DATA());
        sMainMapFeatureSet.addAll(new AMAPHOME_MainMapFeature_DATA());
        sMainMapFeatureSet.addAll(new LIFE_MainMapFeature_DATA());
        sMainMapFeatureSet.addAll(new SEARCHRESULT_MainMapFeature_DATA());
        sMainMapFeatureSet.addAll(new DRIVE_MainMapFeature_DATA());
        sMainMapFeatureSet.addAll(new TRIPGROUP_MainMapFeature_DATA());
    }

    public final Map<String, Map<Class<?>, Float>> getPriorityMap() {
        try {
            return (Map) Class.forName("com.autonavi.inter.impl.MainMapInvokePriorityImpl").getDeclaredMethod("getPriorityMap", new Class[0]).invoke(null, new Object[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public final Set<Class<?>> getMainMapFeatures() {
        return sMainMapFeatureSet;
    }
}
