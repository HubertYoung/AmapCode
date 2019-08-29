package org.eclipse.mat.parser.model;

import java.io.Serializable;
import java.util.Comparator;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.ArrayLong;
import org.eclipse.mat.hprof.Messages;
import org.eclipse.mat.parser.internal.SnapshotImpl;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.model.Field;
import org.eclipse.mat.snapshot.model.GCRootInfo;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.snapshot.model.ObjectReference;
import org.eclipse.mat.snapshot.registry.ClassSpecificNameResolverRegistry;
import org.eclipse.mat.util.MessageUtil;

public abstract class AbstractObjectImpl implements Serializable, IObject {
    private static final long serialVersionUID = 2451875423035843852L;
    private long address;
    protected ClassImpl classInstance;
    private int objectId;
    protected transient SnapshotImpl source;

    public static Comparator<AbstractObjectImpl> getComparatorForClassSpecificName() {
        return null;
    }

    public static Comparator<AbstractObjectImpl> getComparatorForTechnicalName() {
        return null;
    }

    public static Comparator<AbstractObjectImpl> getComparatorForUsedHeapSize() {
        return null;
    }

    public abstract ArrayLong getReferences();

    public abstract int getUsedHeapSize();

    /* access modifiers changed from: protected */
    public abstract Field internalGetField(String str);

    public AbstractObjectImpl(int i, long j, ClassImpl classImpl) {
        this.objectId = i;
        this.address = j;
        this.classInstance = classImpl;
    }

    public long getObjectAddress() {
        return this.address;
    }

    public int getObjectId() {
        return this.objectId;
    }

    public void setObjectAddress(long j) {
        this.address = j;
    }

    public void setObjectId(int i) {
        this.objectId = i;
    }

    public ClassImpl getClazz() {
        return this.classInstance;
    }

    public long getClassAddress() {
        return this.classInstance.getObjectAddress();
    }

    public int getClassId() {
        return this.classInstance.getObjectId();
    }

    public void setClassInstance(ClassImpl classImpl) {
        this.classInstance = classImpl;
    }

    public void setSnapshot(ISnapshot iSnapshot) {
        this.source = (SnapshotImpl) iSnapshot;
    }

    public ISnapshot getSnapshot() {
        return this.source;
    }

    public long getRetainedHeapSize() {
        try {
            return this.source.getRetainedHeapSize(getObjectId());
        } catch (SnapshotException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append(getClazz().getName());
        stringBuffer.append(" [");
        appendFields(stringBuffer);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public StringBuffer appendFields(StringBuffer stringBuffer) {
        stringBuffer.append("id=0x");
        stringBuffer.append(Long.toHexString(getObjectAddress()));
        return stringBuffer;
    }

    public String getClassSpecificName() {
        return ClassSpecificNameResolverRegistry.resolve(this);
    }

    public String getTechnicalName() {
        StringBuilder sb = new StringBuilder(256);
        sb.append(getClazz().getName());
        sb.append(" @ 0x");
        sb.append(Long.toHexString(getObjectAddress()));
        return sb.toString();
    }

    public String getDisplayName() {
        String classSpecificName = getClassSpecificName();
        if (classSpecificName == null) {
            return getTechnicalName();
        }
        StringBuilder sb = new StringBuilder(256);
        sb.append(getTechnicalName());
        sb.append("  ");
        if (classSpecificName.length() <= 256) {
            sb.append(classSpecificName);
        } else {
            sb.append(classSpecificName.substring(0, 256));
            sb.append("...");
        }
        return sb.toString();
    }

    public final Object resolveValue(String str) throws SnapshotException {
        String str2;
        int indexOf = str.indexOf(46);
        if (indexOf < 0) {
            str2 = str;
        } else {
            str2 = str.substring(0, indexOf);
        }
        Field internalGetField = internalGetField(str2);
        if (internalGetField == null || internalGetField.getValue() == null) {
            return null;
        }
        if (indexOf < 0) {
            Object value = internalGetField.getValue();
            if (value instanceof ObjectReference) {
                value = ((ObjectReference) value).getObject();
            }
            return value;
        } else if (!(internalGetField.getValue() instanceof ObjectReference)) {
            throw new SnapshotException(MessageUtil.format(Messages.AbstractObjectImpl_Error_FieldIsNotReference, str2, getTechnicalName(), str.substring(indexOf + 1)));
        } else {
            ObjectReference objectReference = (ObjectReference) internalGetField.getValue();
            if (objectReference == null) {
                return null;
            }
            int objectId2 = objectReference.getObjectId();
            if (objectId2 >= 0) {
                return this.source.getObject(objectId2).resolveValue(str.substring(indexOf + 1));
            }
            throw new SnapshotException(MessageUtil.format(Messages.AbstractObjectImpl_Error_FieldContainsIllegalReference, str2, getTechnicalName(), Long.toHexString(objectReference.getObjectAddress())));
        }
    }

    public GCRootInfo[] getGCRootInfo() throws SnapshotException {
        return this.source.getGCRootInfo(getObjectId());
    }

    public boolean equals(Object obj) {
        return (obj instanceof IObject) && this.objectId == ((IObject) obj).getObjectId();
    }

    public int hashCode() {
        return this.objectId;
    }

    protected static int alignUpTo8(int i) {
        int i2 = i % 8;
        return i2 == 0 ? i : (i + 8) - i2;
    }
}
