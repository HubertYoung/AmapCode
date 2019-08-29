package org.aspectj.runtime.reflect;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.reflect.Modifier;

class StringMaker {
    static StringMaker longStringMaker;
    static StringMaker middleStringMaker;
    static StringMaker shortStringMaker;
    int cacheOffset;
    boolean includeArgs = true;
    boolean includeEnclosingPoint = true;
    boolean includeJoinPointTypeName = true;
    boolean includeModifiers = false;
    boolean includeThrows = false;
    boolean shortKindName = true;
    boolean shortPrimaryTypeNames = false;
    boolean shortTypeNames = true;

    StringMaker() {
    }

    static {
        StringMaker stringMaker = new StringMaker();
        shortStringMaker = stringMaker;
        stringMaker.shortTypeNames = true;
        shortStringMaker.includeArgs = false;
        shortStringMaker.includeThrows = false;
        shortStringMaker.includeModifiers = false;
        shortStringMaker.shortPrimaryTypeNames = true;
        shortStringMaker.includeJoinPointTypeName = false;
        shortStringMaker.includeEnclosingPoint = false;
        shortStringMaker.cacheOffset = 0;
        StringMaker stringMaker2 = new StringMaker();
        middleStringMaker = stringMaker2;
        stringMaker2.shortTypeNames = true;
        middleStringMaker.includeArgs = true;
        middleStringMaker.includeThrows = false;
        middleStringMaker.includeModifiers = false;
        middleStringMaker.shortPrimaryTypeNames = false;
        shortStringMaker.cacheOffset = 1;
        StringMaker stringMaker3 = new StringMaker();
        longStringMaker = stringMaker3;
        stringMaker3.shortTypeNames = false;
        longStringMaker.includeArgs = true;
        longStringMaker.includeThrows = false;
        longStringMaker.includeModifiers = true;
        longStringMaker.shortPrimaryTypeNames = false;
        longStringMaker.shortKindName = false;
        longStringMaker.cacheOffset = 2;
    }

    /* access modifiers changed from: 0000 */
    public String makeKindName(String name) {
        int dash = name.lastIndexOf(45);
        return dash == -1 ? name : name.substring(dash + 1);
    }

    /* access modifiers changed from: 0000 */
    public String makeModifiersString(int modifiers) {
        if (!this.includeModifiers) {
            return "";
        }
        String str = Modifier.toString(modifiers);
        if (str.length() == 0) {
            return "";
        }
        return new StringBuffer().append(str).append(Token.SEPARATOR).toString();
    }

    /* access modifiers changed from: 0000 */
    public String stripPackageName(String name) {
        int dot = name.lastIndexOf(46);
        return dot == -1 ? name : name.substring(dot + 1);
    }

    /* access modifiers changed from: 0000 */
    public String makeTypeName(Class type, String typeName, boolean shortName) {
        if (type == null) {
            return "ANONYMOUS";
        }
        if (type.isArray()) {
            Class componentType = type.getComponentType();
            return new StringBuffer().append(makeTypeName(componentType, componentType.getName(), shortName)).append("[]").toString();
        } else if (shortName) {
            return stripPackageName(typeName).replace('$', DjangoUtils.EXTENSION_SEPARATOR);
        } else {
            return typeName.replace('$', DjangoUtils.EXTENSION_SEPARATOR);
        }
    }

    public String makeTypeName(Class type) {
        return makeTypeName(type, type.getName(), this.shortTypeNames);
    }

    public String makePrimaryTypeName(Class type, String typeName) {
        return makeTypeName(type, typeName, this.shortPrimaryTypeNames);
    }

    public void addTypeNames(StringBuffer buf, Class[] types) {
        for (int i = 0; i < types.length; i++) {
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(makeTypeName(types[i]));
        }
    }

    public void addSignature(StringBuffer buf, Class[] types) {
        if (types != null) {
            if (this.includeArgs) {
                buf.append("(");
                addTypeNames(buf, types);
                buf.append(")");
            } else if (types.length == 0) {
                buf.append("()");
            } else {
                buf.append("(..)");
            }
        }
    }

    public void addThrows(StringBuffer buf, Class[] types) {
        if (this.includeThrows && types != null && types.length != 0) {
            buf.append(" throws ");
            addTypeNames(buf, types);
        }
    }
}
