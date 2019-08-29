package org.eclipse.mat.parser.model;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.ArrayLong;
import org.eclipse.mat.snapshot.model.Field;
import org.eclipse.mat.snapshot.model.IPrimitiveArray;
import org.eclipse.mat.snapshot.model.NamedReference;
import org.eclipse.mat.snapshot.model.PseudoReference;

public class PrimitiveArrayImpl extends AbstractArrayImpl implements IPrimitiveArray {
    private static final long serialVersionUID = 2;
    private int type;

    /* access modifiers changed from: protected */
    public Field internalGetField(String str) {
        return null;
    }

    public PrimitiveArrayImpl(int i, long j, ClassImpl classImpl, int i2, int i3) {
        super(i, j, classImpl, i2);
        this.type = i3;
    }

    public int getType() {
        return this.type;
    }

    public Class<?> getComponentType() {
        return COMPONENT_TYPE[this.type];
    }

    public Object getValueAt(int i) {
        Object valueArray = getValueArray(i, 1);
        if (valueArray != null) {
            return Array.get(valueArray, 0);
        }
        return null;
    }

    public Object getValueArray() {
        try {
            return this.source.getHeapObjectReader().readPrimitiveArrayContent(this, 0, getLength());
        } catch (SnapshotException e) {
            throw new RuntimeException(e);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public Object getValueArray(int i, int i2) {
        try {
            return this.source.getHeapObjectReader().readPrimitiveArrayContent(this, i, i2);
        } catch (SnapshotException e) {
            throw new RuntimeException(e);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public ArrayLong getReferences() {
        ArrayLong arrayLong = new ArrayLong(1);
        arrayLong.add(this.classInstance.getObjectAddress());
        return arrayLong;
    }

    public List<NamedReference> getOutboundReferences() {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new PseudoReference(this.source, this.classInstance.getObjectAddress(), "<class>"));
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public StringBuffer appendFields(StringBuffer stringBuffer) {
        StringBuffer appendFields = super.appendFields(stringBuffer);
        appendFields.append(";size=");
        appendFields.append(getUsedHeapSize());
        return appendFields;
    }

    public int getUsedHeapSize() {
        try {
            return getSnapshot().getHeapSize(getObjectId());
        } catch (SnapshotException unused) {
            return doGetUsedHeapSize(this.classInstance, this.length, this.type);
        }
    }

    public static int doGetUsedHeapSize(ClassImpl classImpl, int i, int i2) {
        return alignUpTo8((classImpl.getHeapSizePerInstance() * 2) + 4 + (i * ELEMENT_SIZE[i2]));
    }
}
