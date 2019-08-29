package org.eclipse.mat.parser.model;

import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.ArrayInt;
import org.eclipse.mat.collect.ArrayLong;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.model.Field;
import org.eclipse.mat.snapshot.model.FieldDescriptor;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.NamedReference;
import org.eclipse.mat.snapshot.model.ObjectReference;
import org.eclipse.mat.snapshot.model.PseudoReference;
import org.eclipse.mat.util.IProgressListener;
import org.eclipse.mat.util.VoidProgressListener;

public class ClassImpl extends AbstractObjectImpl implements Comparable<ClassImpl>, IClass {
    public static final String JAVA_LANG_CLASS = "java.lang.Class";
    private static final long serialVersionUID = 22;
    private Serializable cacheEntry;
    protected long classLoaderAddress;
    protected int classLoaderId = -1;
    protected FieldDescriptor[] fields;
    protected int instanceCount;
    protected int instanceSize;
    protected boolean isArrayType;
    protected String name;
    protected Field[] staticFields;
    private List<IClass> subClasses;
    protected long superClassAddress;
    protected int superClassId = -1;
    protected long totalSize;
    protected int usedHeapSize;

    public ClassImpl(long j, String str, long j2, long j3, Field[] fieldArr, FieldDescriptor[] fieldDescriptorArr) {
        super(-1, j, null);
        this.name = str;
        this.superClassAddress = j2;
        this.classLoaderAddress = j3;
        this.staticFields = fieldArr;
        this.fields = fieldDescriptorArr;
        this.instanceSize = -1;
        this.totalSize = 0;
        this.isArrayType = str.endsWith("[]");
    }

    public Serializable getCacheEntry() {
        return this.cacheEntry;
    }

    public void setCacheEntry(Serializable serializable) {
        this.cacheEntry = serializable;
    }

    public void setSuperClassIndex(int i) {
        this.superClassId = i;
    }

    public void setClassLoaderIndex(int i) {
        this.classLoaderId = i;
    }

    public int[] getObjectIds() throws UnsupportedOperationException, SnapshotException {
        try {
            return this.source.getIndexManager().c2objects().getObjectsOf(this.cacheEntry);
        } catch (IOException e) {
            throw new SnapshotException((Throwable) e);
        }
    }

    public long getRetainedHeapSizeOfObjects(boolean z, boolean z2, IProgressListener iProgressListener) throws SnapshotException {
        long j;
        long j2 = this.source.getRetainedSizeCache().get(getObjectId());
        int i = (j2 > 0 ? 1 : (j2 == 0 ? 0 : -1));
        if (i > 0 || !z) {
            return j2;
        }
        if (i < 0 && z2) {
            return j2;
        }
        if (iProgressListener == null) {
            iProgressListener = new VoidProgressListener();
        }
        ArrayInt arrayInt = new ArrayInt();
        arrayInt.add(getObjectId());
        arrayInt.addAll(getObjectIds());
        if (!z2) {
            int[] retainedSet = this.source.getRetainedSet(arrayInt.toArray(), iProgressListener);
            if (iProgressListener.isCanceled()) {
                return 0;
            }
            j = this.source.getHeapSize(retainedSet);
        } else {
            j = this.source.getMinRetainedSize(arrayInt.toArray(), iProgressListener);
            if (iProgressListener.isCanceled()) {
                return 0;
            }
        }
        if (z2) {
            j = -j;
        }
        this.source.getRetainedSizeCache().put(getObjectId(), j);
        return j;
    }

    public int getUsedHeapSize() {
        return this.usedHeapSize;
    }

    public ArrayLong getReferences() {
        ArrayLong arrayLong = new ArrayLong(this.staticFields.length);
        arrayLong.add(this.classInstance.getObjectAddress());
        if (this.superClassAddress != 0) {
            arrayLong.add(this.superClassAddress);
        }
        arrayLong.add(this.classLoaderAddress);
        for (int i = 0; i < this.staticFields.length; i++) {
            if (this.staticFields[i].getValue() instanceof ObjectReference) {
                arrayLong.add(((ObjectReference) this.staticFields[i].getValue()).getObjectAddress());
            }
        }
        return arrayLong;
    }

    public List<NamedReference> getOutboundReferences() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new PseudoReference(this.source, this.classInstance.getObjectAddress(), "<class>"));
        if (this.superClassAddress != 0) {
            linkedList.add(new PseudoReference(this.source, this.superClassAddress, "<super>"));
        }
        linkedList.add(new PseudoReference(this.source, this.classLoaderAddress, "<classloader>"));
        for (int i = 0; i < this.staticFields.length; i++) {
            if (this.staticFields[i].getValue() instanceof ObjectReference) {
                ObjectReference objectReference = (ObjectReference) this.staticFields[i].getValue();
                String name2 = this.staticFields[i].getName();
                if (name2.startsWith(SimpleComparison.LESS_THAN_OPERATION)) {
                    linkedList.add(new PseudoReference(this.source, objectReference.getObjectAddress(), name2));
                } else {
                    linkedList.add(new NamedReference(this.source, objectReference.getObjectAddress(), name2));
                }
            }
        }
        return linkedList;
    }

    public long getClassLoaderAddress() {
        return this.classLoaderAddress;
    }

    public void setClassLoaderAddress(long j) {
        this.classLoaderAddress = j;
    }

    public List<FieldDescriptor> getFieldDescriptors() {
        return Arrays.asList(this.fields);
    }

    public int getNumberOfObjects() {
        return this.instanceCount;
    }

    public int getHeapSizePerInstance() {
        return this.instanceSize;
    }

    public void setHeapSizePerInstance(int i) {
        this.instanceSize = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public List<Field> getStaticFields() {
        return Arrays.asList(this.staticFields);
    }

    public long getSuperClassAddress() {
        return this.superClassAddress;
    }

    public int getSuperClassId() {
        return this.superClassId;
    }

    public ClassImpl getSuperClass() {
        try {
            if (this.superClassAddress != 0) {
                return (ClassImpl) this.source.getObject(this.superClassId);
            }
            return null;
        } catch (SnapshotException e) {
            throw new RuntimeException(e);
        }
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public boolean hasSuperClass() {
        return this.superClassAddress != 0;
    }

    public int compareTo(ClassImpl classImpl) {
        int i = (getObjectAddress() > classImpl.getObjectAddress() ? 1 : (getObjectAddress() == classImpl.getObjectAddress() ? 0 : -1));
        if (i > 0) {
            return 1;
        }
        return i == 0 ? 0 : -1;
    }

    public void addInstance(int i) {
        this.instanceCount++;
        this.totalSize += (long) i;
    }

    public void removeInstance(int i) {
        this.instanceCount--;
        this.totalSize -= (long) i;
    }

    public List<IClass> getSubclasses() {
        return this.subClasses != null ? this.subClasses : Collections.EMPTY_LIST;
    }

    public List<IClass> getAllSubclasses() {
        if (this.subClasses == null || this.subClasses.isEmpty()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(this.subClasses.size() * 2);
        arrayList.addAll(this.subClasses);
        for (IClass allSubclasses : this.subClasses) {
            arrayList.addAll(allSubclasses.getAllSubclasses());
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public StringBuffer appendFields(StringBuffer stringBuffer) {
        StringBuffer appendFields = super.appendFields(stringBuffer);
        appendFields.append(";name=");
        appendFields.append(getName());
        return appendFields;
    }

    public boolean isArrayType() {
        return this.isArrayType;
    }

    public String getTechnicalName() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("class ");
        sb.append(getName());
        sb.append(" @ 0x");
        sb.append(Long.toHexString(getObjectAddress()));
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public Field internalGetField(String str) {
        Field[] fieldArr;
        for (Field field : this.staticFields) {
            if (field.getName().equals(str)) {
                return field;
            }
        }
        return null;
    }

    public int getClassLoaderId() {
        return this.classLoaderId;
    }

    public void addSubClass(ClassImpl classImpl) {
        if (this.subClasses == null) {
            this.subClasses = new ArrayList();
        }
        this.subClasses.add(classImpl);
    }

    public void removeSubClass(ClassImpl classImpl) {
        this.subClasses.remove(classImpl);
    }

    public void setUsedHeapSize(int i) {
        this.usedHeapSize = i;
    }

    public boolean doesExtend(String str) throws SnapshotException {
        if (str.equals(this.name)) {
            return true;
        }
        if (!hasSuperClass() || this.source == null) {
            return false;
        }
        return ((ClassImpl) this.source.getObject(this.superClassId)).doesExtend(str);
    }

    public void setSnapshot(ISnapshot iSnapshot) {
        Field[] fieldArr;
        super.setSnapshot(iSnapshot);
        for (Field field : this.staticFields) {
            if (field.getValue() instanceof ObjectReference) {
                field.setValue(new ObjectReference(iSnapshot, ((ObjectReference) field.getValue()).getObjectAddress()));
            }
        }
    }
}
