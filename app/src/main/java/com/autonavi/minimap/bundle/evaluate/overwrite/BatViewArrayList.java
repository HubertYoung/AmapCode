package com.autonavi.minimap.bundle.evaluate.overwrite;

import android.annotation.SuppressLint;
import android.view.View;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class BatViewArrayList extends ArrayList<View> implements a {
    private Class mDecorCls = Class.forName("com.android.internal.policy.impl.PhoneWindow$DecorView");
    private Field mOpaField = this.mDecorCls.getDeclaredField("mDefaultOpacity");

    @SuppressLint({"PrivateApi"})
    protected BatViewArrayList(ArrayList arrayList) throws ClassNotFoundException, NoSuchFieldException {
        this.mOpaField.setAccessible(true);
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (next instanceof View) {
                    super.add((View) next);
                }
            }
        }
    }

    public boolean add(View view) {
        if (view == null || this.mDecorCls == null || this.mOpaField == null) {
            return super.add(null);
        }
        if ((view.getTag() instanceof String) && "EvAluAtE".equals((String) view.getTag())) {
            return super.add(view);
        }
        try {
            if (!this.mDecorCls.isInstance(view)) {
                a(view);
            } else if (((Integer) this.mOpaField.get(view)).intValue() != -1) {
                a(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.add(view);
    }
}
