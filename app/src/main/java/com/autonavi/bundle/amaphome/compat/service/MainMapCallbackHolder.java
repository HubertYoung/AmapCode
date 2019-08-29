package com.autonavi.bundle.amaphome.compat.service;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.inter.IMainMapFeatureProvider;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MainMapCallbackHolder {
    @SuppressFBWarnings({"CO_COMPARETO_INCORRECT_FLOATING"})
    private static Comparator<a> c = new Comparator<a>() {
        public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
            a aVar = (a) obj;
            a aVar2 = (a) obj2;
            if (aVar.a < aVar2.a) {
                return -1;
            }
            return aVar.a > aVar2.a ? 1 : 0;
        }
    };
    public Map<String, List<?>> a = new HashMap();
    public Map<String, List<a>> b = new HashMap();
    private Map<String, Map<Class<?>, Float>> d = ((IMainMapFeatureProvider) bqn.a(IMainMapFeatureProvider.class)).getPriorityMap();

    @SuppressFBWarnings({"ME_MUTABLE_ENUM_FIELD"})
    public enum MethodType {
        CREATE("onCreate"),
        DESTROY("onDestroy"),
        RESUME("onResume"),
        PAUSE("onPause"),
        START("onStart"),
        STOP("onStop"),
        COVER("onCover"),
        APPEAR("onAppear"),
        NEW_INTENT("handleNewIntent"),
        BACK_PRESSED("onBackPressed"),
        CONFIGURATION_CHANGED("onConfigurationChanged"),
        WINDOW_FOCUS_CHANGED("onWindowFocusChanged"),
        SURFACE_CREATED("onMapSurfaceCreated"),
        SURFACE_DESTROY("onMapSurfaceDestroy"),
        SURFACE_CHANGED("onMapSurfaceChanged"),
        FULL_SCREEN_STATE_CHANGED("onFullScreenStateChanged"),
        RESULT("onResult"),
        INDOOR("onIndoor"),
        SCENIC("onScenic"),
        ACTIVITY_RESULT("onActivityResult"),
        ACTIVITY_START("onActivityStart"),
        ACTIVITY_STOP("onActivityStop"),
        ACTIVITY_RESUME("onActivityResume"),
        ACTIVITY_PAUSE("onActivityPause"),
        REAL_TIME_BUS_STATE_CHANGED("onRealTimeBusStateChanged");
        
        public String methodName;

        private MethodType(String str) {
            this.methodName = str;
        }
    }

    public static class a<T> {
        public float a;
        public T b;

        public a(float f, T t) {
            this.a = f;
            this.b = t;
        }
    }

    public final <T> void a(T t, MethodType methodType) {
        Map map = this.d.get(methodType.methodName);
        if (map == null || !map.containsKey(t.getClass())) {
            List list = this.a.get(methodType.methodName);
            if (list == null) {
                list = new ArrayList();
                this.a.put(methodType.methodName, list);
            }
            list.add(t);
            return;
        }
        float floatValue = ((Float) map.get(t.getClass())).floatValue();
        List list2 = this.b.get(methodType.methodName);
        if (list2 == null) {
            list2 = new ArrayList();
            this.b.put(methodType.methodName, list2);
        }
        list2.add(new a(floatValue, t));
        Collections.sort(list2, c);
    }

    @SuppressFBWarnings({"NP_BOOLEAN_RETURN_NULL"})
    public final Boolean a(MethodType methodType, boolean z, Object... objArr) {
        long currentTimeMillis = System.currentTimeMillis();
        Boolean bool = null;
        if (z) {
            List list = this.b.get(methodType.methodName);
            if (list == null || list.isEmpty()) {
                return null;
            }
            int size = list.size();
            Boolean bool2 = null;
            for (int i = 0; i < size; i++) {
                a aVar = (a) list.get(i);
                if (aVar.a > 5.0f) {
                    bool2 = a((Object) aVar.b, methodType, objArr);
                }
                if (bool2 != null && bool2.booleanValue()) {
                    return bool2;
                }
            }
            AMapLog.i("MainMapService", String.format("Dispatch %s below normal totally cost %d ms!", new Object[]{methodType.methodName, Long.valueOf(System.currentTimeMillis() - currentTimeMillis)}));
            return bool2;
        }
        List list2 = this.b.get(methodType.methodName);
        if (list2 != null && !list2.isEmpty()) {
            int size2 = list2.size();
            Boolean bool3 = null;
            for (int i2 = 0; i2 < size2; i2++) {
                a aVar2 = (a) list2.get(i2);
                if (aVar2.a <= 5.0f) {
                    bool3 = a((Object) aVar2.b, methodType, objArr);
                }
                if (bool3 != null && bool3.booleanValue()) {
                    return bool3;
                }
            }
            bool = bool3;
        }
        List list3 = this.a.get(methodType.methodName);
        if (list3 != null && !list3.isEmpty()) {
            int size3 = list3.size();
            Boolean bool4 = bool;
            for (int i3 = 0; i3 < size3; i3++) {
                bool4 = a(list3.get(i3), methodType, objArr);
                if (bool4 != null && bool4.booleanValue()) {
                    return bool4;
                }
            }
            bool = bool4;
        }
        AMapLog.i("MainMapService", String.format("Dispatch %s totally cost %d ms!", new Object[]{methodType.methodName, Long.valueOf(System.currentTimeMillis() - currentTimeMillis)}));
        return bool;
    }

    @SuppressFBWarnings({"NP_BOOLEAN_RETURN_NULL"})
    private static Boolean a(Object obj, MethodType methodType, Object... objArr) {
        if (obj == null) {
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        switch (methodType) {
            case CREATE:
                ((czu) obj).onCreate();
                break;
            case START:
                ((czz) obj).j_();
                break;
            case RESUME:
                ((czy) obj).onResume();
                break;
            case PAUSE:
                ((czy) obj).onPause();
                break;
            case STOP:
                ((czz) obj).k_();
                break;
            case DESTROY:
                ((czu) obj).onDestroy();
                break;
            case COVER:
                ((IPageStateListener) obj).onCover();
                break;
            case APPEAR:
                ((IPageStateListener) obj).onAppear();
                break;
            case SURFACE_CREATED:
                ((btx) obj).onMapSurfaceCreated();
                break;
            case SURFACE_DESTROY:
                ((btx) obj).onMapSurfaceDestroy();
                break;
            case SURFACE_CHANGED:
                ((btx) obj).onMapSurfaceChanged(objArr[0].intValue(), objArr[1].intValue());
                break;
            case WINDOW_FOCUS_CHANGED:
                objArr[0].booleanValue();
                break;
            case INDOOR:
                ((czr) obj).onIndoor(objArr[0].booleanValue());
                break;
            case SCENIC:
                ((czr) obj).onScenic(objArr[0].booleanValue());
                break;
            case FULL_SCREEN_STATE_CHANGED:
                ((czv) obj).onFullScreenStateChanged(objArr[0].booleanValue());
                break;
            case RESULT:
                return Boolean.valueOf(((czx) obj).onResult(objArr[0].intValue(), objArr[1], objArr[2]));
            case NEW_INTENT:
                return Boolean.valueOf(((czw) obj).onNewIntent(objArr[0]));
            case ACTIVITY_RESULT:
                objArr[0].intValue();
                objArr[1].intValue();
                return Boolean.FALSE;
            case ACTIVITY_START:
                ((czq) obj).onActivityStart();
                break;
            case ACTIVITY_STOP:
                ((czq) obj).onActivityStop();
                break;
            case REAL_TIME_BUS_STATE_CHANGED:
                ((dab) obj).a(objArr[0].booleanValue());
                break;
        }
        AMapLog.i("MainMapService", String.format("\nDispatch %s#%s cost %d ms!", new Object[]{obj.getClass().getSimpleName(), methodType.methodName, Long.valueOf(System.currentTimeMillis() - currentTimeMillis)}));
        return null;
    }

    public final void a(boolean z) {
        a(MethodType.FULL_SCREEN_STATE_CHANGED, false, Boolean.valueOf(z));
    }

    public final void b(boolean z) {
        a(MethodType.FULL_SCREEN_STATE_CHANGED, true, Boolean.valueOf(z));
    }
}
