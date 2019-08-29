package org.xidea.el.impl;

import java.util.HashMap;
import java.util.Map;
import org.xidea.el.Invocable;
import org.xidea.el.OperationStrategy;

public class OperationStrategyImpl implements OperationStrategy {
    private static final NumberArithmetic d = new NumberArithmetic();
    final Map<Class<? extends Object>, Map<String, Invocable>> a = new HashMap();
    final Map<Object, Object> b = new HashMap();
    boolean c = true;
}
