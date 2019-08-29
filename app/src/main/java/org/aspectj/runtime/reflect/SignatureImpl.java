package org.aspectj.runtime.reflect;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import java.lang.ref.SoftReference;
import java.util.StringTokenizer;
import org.aspectj.lang.Signature;

abstract class SignatureImpl implements Signature {
    static Class[] EMPTY_CLASS_ARRAY = new Class[0];
    static String[] EMPTY_STRING_ARRAY = new String[0];
    static final String INNER_SEP = ":";
    static final char SEP = '-';
    private static boolean a = true;
    private String b;
    Class declaringType;
    String declaringTypeName;
    ClassLoader lookupClassLoader = null;
    int modifiers = -1;
    String name;
    Cache stringCache;

    interface Cache {
        String get(int i);

        void set(int i, String str);
    }

    final class CacheImpl implements Cache {
        private SoftReference a;

        public CacheImpl() {
            b();
        }

        public final String get(int cacheOffset) {
            String[] cachedArray = a();
            if (cachedArray == null) {
                return null;
            }
            return cachedArray[cacheOffset];
        }

        public final void set(int cacheOffset, String result) {
            String[] cachedArray = a();
            if (cachedArray == null) {
                cachedArray = b();
            }
            cachedArray[cacheOffset] = result;
        }

        private String[] a() {
            return (String[]) this.a.get();
        }

        private String[] b() {
            String[] array = new String[3];
            this.a = new SoftReference(array);
            return array;
        }
    }

    /* access modifiers changed from: protected */
    public abstract String createToString(StringMaker stringMaker);

    SignatureImpl(int modifiers2, String name2, Class declaringType2) {
        this.modifiers = modifiers2;
        this.name = name2;
        this.declaringType = declaringType2;
    }

    /* access modifiers changed from: 0000 */
    public String toString(StringMaker sm) {
        String result = null;
        if (a) {
            if (this.stringCache == null) {
                try {
                    this.stringCache = new CacheImpl();
                } catch (Throwable th) {
                    a = false;
                }
            } else {
                result = this.stringCache.get(sm.cacheOffset);
            }
        }
        if (result == null) {
            result = createToString(sm);
        }
        if (a) {
            this.stringCache.set(sm.cacheOffset, result);
        }
        return result;
    }

    public final String toString() {
        return toString(StringMaker.middleStringMaker);
    }

    public final String toShortString() {
        return toString(StringMaker.shortStringMaker);
    }

    public final String toLongString() {
        return toString(StringMaker.longStringMaker);
    }

    public int getModifiers() {
        if (this.modifiers == -1) {
            this.modifiers = extractInt(0);
        }
        return this.modifiers;
    }

    public String getName() {
        if (this.name == null) {
            this.name = extractString(1);
        }
        return this.name;
    }

    public Class getDeclaringType() {
        if (this.declaringType == null) {
            this.declaringType = extractType(2);
        }
        return this.declaringType;
    }

    public String getDeclaringTypeName() {
        if (this.declaringTypeName == null) {
            this.declaringTypeName = getDeclaringType().getName();
        }
        return this.declaringTypeName;
    }

    /* access modifiers changed from: 0000 */
    public String fullTypeName(Class type) {
        if (type == null) {
            return "ANONYMOUS";
        }
        if (type.isArray()) {
            return new StringBuffer().append(fullTypeName(type.getComponentType())).append("[]").toString();
        }
        return type.getName().replace('$', DjangoUtils.EXTENSION_SEPARATOR);
    }

    /* access modifiers changed from: 0000 */
    public String stripPackageName(String name2) {
        int dot = name2.lastIndexOf(46);
        return dot == -1 ? name2 : name2.substring(dot + 1);
    }

    /* access modifiers changed from: 0000 */
    public String shortTypeName(Class type) {
        if (type == null) {
            return "ANONYMOUS";
        }
        if (type.isArray()) {
            return new StringBuffer().append(shortTypeName(type.getComponentType())).append("[]").toString();
        }
        return stripPackageName(type.getName()).replace('$', DjangoUtils.EXTENSION_SEPARATOR);
    }

    /* access modifiers changed from: 0000 */
    public void addFullTypeNames(StringBuffer buf, Class[] types) {
        for (int i = 0; i < types.length; i++) {
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(fullTypeName(types[i]));
        }
    }

    /* access modifiers changed from: 0000 */
    public void addShortTypeNames(StringBuffer buf, Class[] types) {
        for (int i = 0; i < types.length; i++) {
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(shortTypeName(types[i]));
        }
    }

    /* access modifiers changed from: 0000 */
    public void addTypeArray(StringBuffer buf, Class[] types) {
        addFullTypeNames(buf, types);
    }

    public void setLookupClassLoader(ClassLoader loader) {
        this.lookupClassLoader = loader;
    }

    private ClassLoader a() {
        if (this.lookupClassLoader == null) {
            this.lookupClassLoader = getClass().getClassLoader();
        }
        return this.lookupClassLoader;
    }

    public SignatureImpl(String stringRep) {
        this.b = stringRep;
    }

    /* access modifiers changed from: 0000 */
    public String extractString(int n) {
        int startIndex = 0;
        int endIndex = this.b.indexOf(45);
        while (true) {
            int n2 = n;
            n = n2 - 1;
            if (n2 <= 0) {
                break;
            }
            startIndex = endIndex + 1;
            endIndex = this.b.indexOf(45, startIndex);
        }
        if (endIndex == -1) {
            endIndex = this.b.length();
        }
        return this.b.substring(startIndex, endIndex);
    }

    /* access modifiers changed from: 0000 */
    public int extractInt(int n) {
        return Integer.parseInt(extractString(n), 16);
    }

    /* access modifiers changed from: 0000 */
    public Class extractType(int n) {
        return Factory.makeClass(extractString(n), a());
    }

    /* access modifiers changed from: 0000 */
    public String[] extractStrings(int n) {
        StringTokenizer st = new StringTokenizer(extractString(n), ":");
        int N = st.countTokens();
        String[] ret = new String[N];
        for (int i = 0; i < N; i++) {
            ret[i] = st.nextToken();
        }
        return ret;
    }

    /* access modifiers changed from: 0000 */
    public Class[] extractTypes(int n) {
        StringTokenizer st = new StringTokenizer(extractString(n), ":");
        int N = st.countTokens();
        Class[] ret = new Class[N];
        for (int i = 0; i < N; i++) {
            ret[i] = Factory.makeClass(st.nextToken(), a());
        }
        return ret;
    }

    static void setUseCache(boolean b2) {
        a = b2;
    }

    static boolean getUseCache() {
        return a;
    }
}
