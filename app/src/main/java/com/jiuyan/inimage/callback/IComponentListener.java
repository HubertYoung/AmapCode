package com.jiuyan.inimage.callback;

import com.alipay.android.hackbyte.ClassVerifier;

public interface IComponentListener<Type, Action, Param, Return> {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    Return onComponentBack(Type type, Action action, Param param);

    Return onComponentDone(Type type, Action action, Param param);

    Return onComponentEvent(Type type, Action action, Param param);
}
