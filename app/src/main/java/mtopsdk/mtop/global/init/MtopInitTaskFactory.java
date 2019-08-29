package mtopsdk.mtop.global.init;

import java.lang.reflect.Constructor;
import mtopsdk.common.util.TBSdkLog;

public class MtopInitTaskFactory {
    private static final String INNER_MTOP_INIT_TASK = "mtopsdk.mtop.global.init.InnerMtopInitTask";
    private static final String OPEN_MTOP_INIT_TASK = "mtopsdk.mtop.global.init.OpenMtopInitTask";
    private static final String PRODUCT_MTOP_INIT_TASK = "mtopsdk.mtop.global.init.ProductMtopInitTask";
    private static final String TAG = "mtopsdk.MtopInitTaskFactory";

    public static IMtopInitTask getMtopInitTask(String str) {
        IMtopInitTask iMtopInitTask;
        if (str == null) {
            return null;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 2432586) {
            if (hashCode != 69817910) {
                if (hashCode == 408508623 && str.equals("PRODUCT")) {
                    c = 2;
                }
            } else if (str.equals("INNER")) {
                c = 0;
            }
        } else if (str.equals("OPEN")) {
            c = 1;
        }
        switch (c) {
            case 0:
                iMtopInitTask = newInstance(INNER_MTOP_INIT_TASK);
                break;
            case 1:
                iMtopInitTask = newInstance(OPEN_MTOP_INIT_TASK);
                break;
            case 2:
                iMtopInitTask = newInstance(PRODUCT_MTOP_INIT_TASK);
                break;
            default:
                iMtopInitTask = newInstance(INNER_MTOP_INIT_TASK);
                break;
        }
        return iMtopInitTask;
    }

    private static IMtopInitTask newInstance(String str) {
        try {
            Constructor<?> declaredConstructor = Class.forName(str).getDeclaredConstructor(new Class[0]);
            declaredConstructor.setAccessible(true);
            return (IMtopInitTask) declaredConstructor.newInstance(new Object[0]);
        } catch (Throwable unused) {
            TBSdkLog.d(TAG, "reflect IMtopInitTask instance error.clazzName=".concat(String.valueOf(str)));
            return null;
        }
    }
}
