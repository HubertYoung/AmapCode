package org.eclipse.mat.parser.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.ArrayLong;
import org.eclipse.mat.hprof.Messages;
import org.eclipse.mat.snapshot.model.Field;
import org.eclipse.mat.snapshot.model.IObjectArray;
import org.eclipse.mat.snapshot.model.NamedReference;
import org.eclipse.mat.snapshot.model.ObjectReference;
import org.eclipse.mat.snapshot.model.PseudoReference;
import org.eclipse.mat.util.MessageUtil;

public class ObjectArrayImpl extends AbstractArrayImpl implements IObjectArray {
    private static final long serialVersionUID = 2;

    public ObjectArrayImpl(int i, long j, ClassImpl classImpl, int i2) {
        super(i, j, classImpl, i2);
    }

    public int getUsedHeapSize() {
        try {
            return getSnapshot().getHeapSize(getObjectId());
        } catch (SnapshotException unused) {
            return doGetUsedHeapSize(this.classInstance, this.length);
        }
    }

    public static int doGetUsedHeapSize(ClassImpl classImpl, int i) {
        return alignUpTo8((classImpl.getHeapSizePerInstance() * 2) + 4 + (i * classImpl.getHeapSizePerInstance()));
    }

    public long[] getReferenceArray() {
        try {
            return this.source.getHeapObjectReader().readObjectArrayContent(this, 0, getLength());
        } catch (SnapshotException e) {
            throw new RuntimeException(e);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public long[] getReferenceArray(int i, int i2) {
        try {
            return this.source.getHeapObjectReader().readObjectArrayContent(this, i, i2);
        } catch (SnapshotException e) {
            throw new RuntimeException(e);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public ArrayLong getReferences() {
        ArrayLong arrayLong = new ArrayLong(getLength() + 1);
        arrayLong.add(this.classInstance.getObjectAddress());
        long[] referenceArray = getReferenceArray();
        for (int i = 0; i < referenceArray.length; i++) {
            if (referenceArray[i] != 0) {
                arrayLong.add(referenceArray[i]);
            }
        }
        return arrayLong;
    }

    /* access modifiers changed from: protected */
    public Field internalGetField(String str) {
        if (str.charAt(0) != '[' || str.charAt(str.length() - 1) != ']') {
            return null;
        }
        try {
            int parseInt = Integer.parseInt(str.substring(1, str.length() - 1));
            if (parseInt >= 0) {
                if (parseInt <= this.length) {
                    return new Field(str, 2, new ObjectReference(this.source, this.source.getHeapObjectReader().readObjectArrayContent(this, parseInt, 1)[0]));
                }
            }
            throw new IndexOutOfBoundsException(MessageUtil.format(Messages.ObjectArrayImpl_forArray, Integer.valueOf(parseInt), getTechnicalName()));
        } catch (SnapshotException e) {
            throw new RuntimeException(e);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public List<NamedReference> getOutboundReferences() {
        ArrayList arrayList = new ArrayList(getLength() + 1);
        arrayList.add(new PseudoReference(this.source, this.classInstance.getObjectAddress(), "<class>"));
        long[] referenceArray = getReferenceArray();
        for (int i = 0; i < referenceArray.length; i++) {
            if (referenceArray[i] != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append('[');
                sb.append(i);
                sb.append(']');
                arrayList.add(new NamedReference(this.source, referenceArray[i], sb.toString()));
            }
        }
        return arrayList;
    }
}
