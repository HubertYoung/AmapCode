package org.eclipse.mat.parser.model;

import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.ArrayLong;
import org.eclipse.mat.collect.HashMapIntObject;
import org.eclipse.mat.collect.IteratorInt;
import org.eclipse.mat.parser.internal.SnapshotImpl;
import org.eclipse.mat.snapshot.model.Field;
import org.eclipse.mat.snapshot.model.GCRootInfo;
import org.eclipse.mat.snapshot.model.IInstance;
import org.eclipse.mat.snapshot.model.NamedReference;
import org.eclipse.mat.snapshot.model.ObjectReference;
import org.eclipse.mat.snapshot.model.PseudoReference;
import org.eclipse.mat.snapshot.model.ThreadToLocalReference;

public class InstanceImpl extends AbstractObjectImpl implements IInstance {
    private static final long serialVersionUID = 1;
    private volatile List<Field> fields;
    private volatile Map<String, Field> name2field;

    public InstanceImpl(int i, long j, ClassImpl classImpl, List<Field> list) {
        super(i, j, classImpl);
        this.fields = list;
    }

    public long getObjectAddress() {
        try {
            long objectAddress = super.getObjectAddress();
            if (objectAddress != Long.MIN_VALUE) {
                return objectAddress;
            }
            long mapIdToAddress = this.source.mapIdToAddress(getObjectId());
            setObjectAddress(mapIdToAddress);
            return mapIdToAddress;
        } catch (SnapshotException e) {
            throw new RuntimeException(e);
        }
    }

    public int getObjectId() {
        try {
            int objectId = super.getObjectId();
            if (objectId >= 0) {
                return objectId;
            }
            int mapAddressToId = this.source.mapAddressToId(getObjectAddress());
            setObjectId(mapAddressToId);
            return mapAddressToId;
        } catch (SnapshotException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Field> getFields() {
        if (this.fields == null) {
            readFully();
        }
        return this.fields;
    }

    public Field getField(String str) {
        return internalGetField(str);
    }

    /* access modifiers changed from: protected */
    public void setFields(List<Field> list) {
        this.fields = list;
    }

    /* access modifiers changed from: protected */
    public synchronized void readFully() {
        if (this.fields == null) {
            try {
                InstanceImpl instanceImpl = (InstanceImpl) this.source.getHeapObjectReader().read(getObjectId(), this.source);
                setObjectAddress(instanceImpl.getObjectAddress());
                this.fields = instanceImpl.fields;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SnapshotException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public int getUsedHeapSize() {
        try {
            return getSnapshot().getHeapSize(getObjectId());
        } catch (SnapshotException unused) {
            return this.classInstance.getHeapSizePerInstance();
        }
    }

    public ArrayLong getReferences() {
        List<Field> fields2 = getFields();
        ArrayLong arrayLong = new ArrayLong(fields2.size() + 1);
        arrayLong.add(this.classInstance.getObjectAddress());
        HashMapIntObject<HashMapIntObject<XGCRootInfo[]>> rootsPerThread = this.source.getRootsPerThread();
        if (rootsPerThread != null) {
            HashMapIntObject hashMapIntObject = (HashMapIntObject) rootsPerThread.get(getObjectId());
            if (hashMapIntObject != null) {
                IteratorInt keys = hashMapIntObject.keys();
                while (keys.hasNext()) {
                    arrayLong.add(((GCRootInfo[]) hashMapIntObject.get(keys.next()))[0].getObjectAddress());
                }
            }
        }
        for (Field next : fields2) {
            if (next.getValue() instanceof ObjectReference) {
                arrayLong.add(((ObjectReference) next.getValue()).getObjectAddress());
            }
        }
        return arrayLong;
    }

    public List<NamedReference> getOutboundReferences() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PseudoReference(this.source, this.classInstance.getObjectAddress(), "<class>"));
        HashMapIntObject<HashMapIntObject<XGCRootInfo[]>> rootsPerThread = this.source.getRootsPerThread();
        if (rootsPerThread != null) {
            HashMapIntObject hashMapIntObject = (HashMapIntObject) rootsPerThread.get(getObjectId());
            if (hashMapIntObject != null) {
                IteratorInt keys = hashMapIntObject.keys();
                while (keys.hasNext()) {
                    int next = keys.next();
                    GCRootInfo[] gCRootInfoArr = (GCRootInfo[]) hashMapIntObject.get(next);
                    SnapshotImpl snapshotImpl = this.source;
                    long objectAddress = gCRootInfoArr[0].getObjectAddress();
                    StringBuilder sb = new StringBuilder(SimpleComparison.LESS_THAN_OPERATION);
                    sb.append(GCRootInfo.getTypeSetAsString(gCRootInfoArr));
                    sb.append(SimpleComparison.GREATER_THAN_OPERATION);
                    ThreadToLocalReference threadToLocalReference = new ThreadToLocalReference(snapshotImpl, objectAddress, sb.toString(), next, gCRootInfoArr);
                    arrayList.add(threadToLocalReference);
                }
            }
        }
        for (Field next2 : getFields()) {
            if (next2.getValue() instanceof ObjectReference) {
                arrayList.add(new NamedReference(this.source, ((ObjectReference) next2.getValue()).getObjectAddress(), next2.getName()));
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public Field internalGetField(String str) {
        if (this.name2field == null) {
            List<Field> fields2 = getFields();
            HashMap hashMap = new HashMap(fields2.size());
            for (Field next : fields2) {
                hashMap.put(next.getName(), next);
            }
            this.name2field = hashMap;
        }
        return this.name2field.get(str);
    }
}
