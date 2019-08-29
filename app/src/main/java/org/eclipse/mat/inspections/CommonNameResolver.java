package org.eclipse.mat.inspections;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.lang.reflect.Modifier;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.extension.IClassSpecificNameResolver;
import org.eclipse.mat.snapshot.extension.Subject;
import org.eclipse.mat.snapshot.extension.Subjects;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.snapshot.model.IObjectArray;
import org.eclipse.mat.snapshot.model.IPrimitiveArray;
import org.eclipse.mat.snapshot.model.PrettyPrinter;

public class CommonNameResolver {

    @Subject("java.lang.reflect.AccessibleObject")
    public static class AccessibleObjectResolver implements IClassSpecificNameResolver {
        public String resolve(IObject iObject) throws SnapshotException {
            StringBuilder sb = new StringBuilder();
            ISnapshot snapshot = iObject.getSnapshot();
            Object resolveValue = iObject.resolveValue("modifiers");
            if (resolveValue instanceof Integer) {
                sb.append(Modifier.toString(((Integer) resolveValue).intValue()));
                if (sb.length() > 0) {
                    sb.append(' ');
                }
            }
            IObject iObject2 = (IObject) iObject.resolveValue("clazz");
            if (iObject2 == null) {
                return null;
            }
            addClassName(snapshot, iObject2.getObjectAddress(), sb);
            return sb.toString();
        }

        /* access modifiers changed from: protected */
        public void addClassName(ISnapshot iSnapshot, long j, StringBuilder sb) throws SnapshotException {
            IObject object = iSnapshot.getObject(iSnapshot.mapAddressToId(j));
            if (object instanceof IClass) {
                sb.append(((IClass) object).getName());
            }
        }
    }

    @Subject("byte[]")
    public static class ByteArrayResolver implements IClassSpecificNameResolver {
        public String resolve(IObject iObject) throws SnapshotException {
            IPrimitiveArray iPrimitiveArray = (IPrimitiveArray) iObject;
            byte[] bArr = (byte[]) iPrimitiveArray.getValueArray(0, Math.min(iPrimitiveArray.getLength(), 1024));
            if (bArr == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder(bArr.length);
            for (int i = 0; i < bArr.length; i++) {
                if (bArr[i] < 32 || bArr[i] > 126) {
                    sb.append(DjangoUtils.EXTENSION_SEPARATOR);
                } else {
                    sb.append((char) bArr[i]);
                }
            }
            return sb.toString();
        }
    }

    @Subject("char[]")
    public static class CharArrayResolver implements IClassSpecificNameResolver {
        public String resolve(IObject iObject) throws SnapshotException {
            IPrimitiveArray iPrimitiveArray = (IPrimitiveArray) iObject;
            return PrettyPrinter.arrayAsString(iPrimitiveArray, 0, iPrimitiveArray.getLength(), 1024);
        }
    }

    @Subject("java.lang.reflect.Constructor")
    public static class ConstructorResolver extends AccessibleObjectResolver {
        public String resolve(IObject iObject) throws SnapshotException {
            StringBuilder sb = new StringBuilder();
            ISnapshot snapshot = iObject.getSnapshot();
            Object resolveValue = iObject.resolveValue("modifiers");
            if (resolveValue instanceof Integer) {
                sb.append(Modifier.toString(((Integer) resolveValue).intValue()));
                if (sb.length() > 0) {
                    sb.append(' ');
                }
            }
            IObject iObject2 = (IObject) iObject.resolveValue("clazz");
            if (iObject2 == null) {
                return null;
            }
            addClassName(snapshot, iObject2.getObjectAddress(), sb);
            sb.append('(');
            IObject iObject3 = (IObject) iObject.resolveValue("parameterTypes");
            if (iObject3 instanceof IObjectArray) {
                IObjectArray iObjectArray = (IObjectArray) iObject3;
                long[] referenceArray = iObjectArray.getReferenceArray();
                for (int i = 0; i < iObjectArray.getLength(); i++) {
                    if (i > 0) {
                        sb.append(',');
                    }
                    addClassName(snapshot, referenceArray[i], sb);
                }
            }
            sb.append(')');
            return sb.toString();
        }
    }

    @Subject("java.lang.reflect.Field")
    public static class FieldResolver extends AccessibleObjectResolver {
        public String resolve(IObject iObject) throws SnapshotException {
            StringBuilder sb = new StringBuilder();
            ISnapshot snapshot = iObject.getSnapshot();
            Object resolveValue = iObject.resolveValue("modifiers");
            if (resolveValue instanceof Integer) {
                sb.append(Modifier.toString(((Integer) resolveValue).intValue()));
                if (sb.length() > 0) {
                    sb.append(' ');
                }
            }
            IObject iObject2 = (IObject) iObject.resolveValue("type");
            if (iObject2 != null) {
                addClassName(snapshot, iObject2.getObjectAddress(), sb);
                sb.append(' ');
            }
            IObject iObject3 = (IObject) iObject.resolveValue("clazz");
            if (iObject3 != null) {
                addClassName(snapshot, iObject3.getObjectAddress(), sb);
                sb.append(DjangoUtils.EXTENSION_SEPARATOR);
            }
            IObject iObject4 = (IObject) iObject.resolveValue("name");
            if (iObject4 == null) {
                return null;
            }
            sb.append(iObject4.getClassSpecificName());
            return sb.toString();
        }
    }

    @Subject("java.lang.reflect.Method")
    public static class MethodResolver extends AccessibleObjectResolver {
        public String resolve(IObject iObject) throws SnapshotException {
            StringBuilder sb = new StringBuilder();
            ISnapshot snapshot = iObject.getSnapshot();
            Object resolveValue = iObject.resolveValue("modifiers");
            if (resolveValue instanceof Integer) {
                sb.append(Modifier.toString(((Integer) resolveValue).intValue()));
                if (sb.length() > 0) {
                    sb.append(' ');
                }
            }
            IObject iObject2 = (IObject) iObject.resolveValue("returnType");
            if (iObject2 != null) {
                addClassName(snapshot, iObject2.getObjectAddress(), sb);
                sb.append(' ');
            }
            IObject iObject3 = (IObject) iObject.resolveValue("clazz");
            if (iObject3 != null) {
                addClassName(snapshot, iObject3.getObjectAddress(), sb);
                sb.append(DjangoUtils.EXTENSION_SEPARATOR);
            }
            IObject iObject4 = (IObject) iObject.resolveValue("name");
            if (iObject4 == null) {
                return null;
            }
            sb.append(iObject4.getClassSpecificName());
            sb.append('(');
            IObject iObject5 = (IObject) iObject.resolveValue("parameterTypes");
            if (iObject5 instanceof IObjectArray) {
                IObjectArray iObjectArray = (IObjectArray) iObject5;
                long[] referenceArray = iObjectArray.getReferenceArray();
                for (int i = 0; i < iObjectArray.getLength(); i++) {
                    if (i > 0) {
                        sb.append(',');
                    }
                    addClassName(snapshot, referenceArray[i], sb);
                }
            }
            sb.append(')');
            return sb.toString();
        }
    }

    @Subjects({"java.lang.StringBuffer", "java.lang.StringBuilder"})
    public static class StringBufferResolver implements IClassSpecificNameResolver {
        public String resolve(IObject iObject) throws SnapshotException {
            Integer num = (Integer) iObject.resolveValue(NewHtcHomeBadger.COUNT);
            if (num == null) {
                return null;
            }
            if (num.intValue() == 0) {
                return "";
            }
            IPrimitiveArray iPrimitiveArray = (IPrimitiveArray) iObject.resolveValue("value");
            if (iPrimitiveArray == null) {
                return null;
            }
            return PrettyPrinter.arrayAsString(iPrimitiveArray, 0, num.intValue(), 1024);
        }
    }

    @Subject("java.lang.String")
    public static class StringResolver implements IClassSpecificNameResolver {
        public String resolve(IObject iObject) throws SnapshotException {
            return PrettyPrinter.objectAsString(iObject, 1024);
        }
    }

    @Subject("java.lang.ThreadGroup")
    public static class ThreadGroupResolver implements IClassSpecificNameResolver {
        public String resolve(IObject iObject) throws SnapshotException {
            IObject iObject2 = (IObject) iObject.resolveValue("name");
            if (iObject2 == null) {
                return null;
            }
            return iObject2.getClassSpecificName();
        }
    }

    @Subject("java.lang.Thread")
    public static class ThreadResolver implements IClassSpecificNameResolver {
        public String resolve(IObject iObject) throws SnapshotException {
            IObject iObject2 = (IObject) iObject.resolveValue("name");
            if (iObject2 != null) {
                return iObject2.getClassSpecificName();
            }
            return null;
        }
    }

    @Subject("java.net.URL")
    public static class URLResolver implements IClassSpecificNameResolver {
        public String resolve(IObject iObject) throws SnapshotException {
            StringBuilder sb = new StringBuilder();
            sb.append(((IObject) iObject.resolveValue("protocol")).getClassSpecificName());
            sb.append(":");
            IObject iObject2 = (IObject) iObject.resolveValue("authority");
            if (iObject2 != null) {
                sb.append("//");
                sb.append(iObject2.getClassSpecificName());
            }
            IObject iObject3 = (IObject) iObject.resolveValue("path");
            if (iObject3 != null) {
                sb.append(iObject3.getClassSpecificName());
            }
            IObject iObject4 = (IObject) iObject.resolveValue("query");
            if (iObject4 != null) {
                sb.append("?");
                sb.append(iObject4.getClassSpecificName());
            }
            IObject iObject5 = (IObject) iObject.resolveValue("ref");
            if (iObject5 != null) {
                sb.append(MetaRecord.LOG_SEPARATOR);
                sb.append(iObject5.getClassSpecificName());
            }
            return sb.toString();
        }
    }

    @Subjects({"java.lang.Byte", "java.lang.Character", "java.lang.Short", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Boolean"})
    public static class ValueResolver implements IClassSpecificNameResolver {
        public String resolve(IObject iObject) throws SnapshotException {
            return String.valueOf(iObject.resolveValue("value"));
        }
    }
}
