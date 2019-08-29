package org.xidea.el.impl;

import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.xiaomi.mipush.sdk.Constants;
import java.util.AbstractList;
import java.util.HashMap;
import java.util.Map;
import org.xidea.el.ExpressionToken;
import org.xidea.el.json.JSONEncoder;

public class TokenImpl extends AbstractList<Object> implements ExpressionToken {
    private static final Object[] b = new Object[0];
    private static final Map<String, Integer> g = new HashMap();
    private static final Map<Integer, String> h = new HashMap();
    String a;
    private int c = -1;
    private TokenImpl d;
    private TokenImpl e;
    private Object f;

    private static int a(int i) {
        if (i < 0) {
            return 0;
        }
        return ((i & 192) >> 6) + 1;
    }

    static {
        a(-1, "value");
        a(-2, "var");
        a(-3, "[]");
        a(-4, bny.c);
        a(96, ".");
        a(97, "()");
        a(28, "!");
        a(29, Constants.WAVE_SEPARATOR);
        a(30, "+");
        a(31, "-");
        a(88, "*");
        a(89, "/");
        a(90, "%");
        a(84, "+");
        a(85, "-");
        a(80, "<<");
        a(81, ">>");
        a(82, ">>>");
        a(332, SimpleComparison.LESS_THAN_OPERATION);
        a(333, SimpleComparison.GREATER_THAN_OPERATION);
        a(334, SimpleComparison.LESS_THAN_EQUAL_TO_OPERATION);
        a(335, SimpleComparison.GREATER_THAN_EQUAL_TO_OPERATION);
        a(4428, " in ");
        a(76, "==");
        a(77, "!=");
        a(78, "===");
        a(79, "!==");
        a(1096, "&");
        a(840, "^");
        a(584, MergeUtil.SEPARATOR_KV);
        a(328, "&&");
        a(72, com.alibaba.analytics.core.device.Constants.SEPARATOR);
        a(68, "?");
        a(69, ":");
        a(64, ",");
        a(65, ":");
        a(65534, "(");
        a(65535, ")");
    }

    public TokenImpl(Object obj) {
        this.f = obj;
    }

    public String toString() {
        if (this.a != null) {
            return this.a;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(h.get(Integer.valueOf(this.c)));
        sb.append(":");
        sb.append(JSONEncoder.encode(this));
        return sb.toString();
    }

    public Object get(int i) {
        if (this.c == 32) {
            if (i == 0) {
                return Integer.valueOf(96);
            }
            if (i == 1) {
                return this.d;
            }
            if (i == 2) {
                return this.e;
            }
            return null;
        } else if (this.c == 33) {
            if (i == 0) {
                return Integer.valueOf(97);
            }
            if (i == 1) {
                return this.d;
            }
            if (i == 2) {
                return this.e;
            }
            return null;
        } else if (i == 0) {
            return Integer.valueOf(this.c);
        } else {
            if (this.c > 0) {
                if (i < a(this.c) + 1) {
                    switch (i) {
                        case 1:
                            return this.d;
                        case 2:
                            return this.e;
                    }
                }
                return this.f;
            } else if (i == 1) {
                return this.f;
            } else {
                return null;
            }
        }
    }

    public int size() {
        boolean z;
        if (32 == this.c || 33 == this.c) {
            return 3;
        }
        int a2 = a(this.c) + 1;
        switch (this.c) {
            case -2:
            case -1:
            case 32:
            case 33:
            case 65:
                z = true;
                break;
            default:
                z = false;
                break;
        }
        if (z) {
            a2++;
        }
        return a2;
    }

    private static void a(int i, String str) {
        g.put(str, Integer.valueOf(i));
        h.put(Integer.valueOf(i), str);
    }
}
