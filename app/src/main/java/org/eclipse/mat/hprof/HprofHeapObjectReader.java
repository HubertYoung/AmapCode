package org.eclipse.mat.hprof;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.hprof.extension.IRuntimeEnhancer;
import org.eclipse.mat.parser.IObjectReader;
import org.eclipse.mat.parser.index.IIndexReader.IOne2LongIndex;
import org.eclipse.mat.parser.index.IndexReader.LongIndexReader;
import org.eclipse.mat.parser.model.AbstractArrayImpl;
import org.eclipse.mat.parser.model.ObjectArrayImpl;
import org.eclipse.mat.parser.model.PrimitiveArrayImpl;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.snapshot.model.IPrimitiveArray;

public class HprofHeapObjectReader implements IObjectReader {
    public static final String VERSION_PROPERTY = "hprof.version";
    private List<IRuntimeEnhancer> enhancers;
    private HprofRandomAccessParser hprofDump;
    private IOne2LongIndex o2hprof;
    private ISnapshot snapshot;

    public void open(ISnapshot iSnapshot) throws IOException {
        this.snapshot = iSnapshot;
        this.hprofDump = new HprofRandomAccessParser(new File(iSnapshot.getSnapshotInfo().getPath()), Version.valueOf((String) iSnapshot.getSnapshotInfo().getProperty(VERSION_PROPERTY)), iSnapshot.getSnapshotInfo().getIdentifierSize());
        StringBuilder sb = new StringBuilder();
        sb.append(iSnapshot.getSnapshotInfo().getPrefix());
        sb.append("o2hprof.index");
        this.o2hprof = new LongIndexReader(new File(sb.toString()));
        this.enhancers = new ArrayList();
    }

    public long[] readObjectArrayContent(ObjectArrayImpl objectArrayImpl, int i, int i2) throws IOException, SnapshotException {
        Object info = objectArrayImpl.getInfo();
        if (info instanceof Offline) {
            Offline offline = (Offline) info;
            long[] jArr = (long[]) offline.getLazyReadContent();
            if (jArr != null) {
                return (long[]) fragment(objectArrayImpl, jArr, i, i2);
            }
            long[] readObjectArray = this.hprofDump.readObjectArray(offline, i, i2);
            if (i == 0 && i2 == objectArrayImpl.getLength()) {
                offline.setLazyReadContent(readObjectArray);
            }
            return readObjectArray;
        } else if (info instanceof long[]) {
            return (long[]) fragment(objectArrayImpl, info, i, i2);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Object readPrimitiveArrayContent(PrimitiveArrayImpl primitiveArrayImpl, int i, int i2) throws IOException, SnapshotException {
        Object info = primitiveArrayImpl.getInfo();
        if (info instanceof Offline) {
            Offline offline = (Offline) info;
            Object lazyReadContent = offline.getLazyReadContent();
            if (lazyReadContent != null) {
                return fragment(primitiveArrayImpl, lazyReadContent, i, i2);
            }
            Object convert = convert(primitiveArrayImpl, this.hprofDump.readPrimitiveArray(offline, i, i2));
            if (i == 0 && i2 == primitiveArrayImpl.getLength()) {
                offline.setLazyReadContent(convert);
            }
            return convert;
        } else if (!(info instanceof Raw)) {
            return fragment(primitiveArrayImpl, info, i, i2);
        } else {
            Object convert2 = convert(primitiveArrayImpl, ((Raw) info).getContent());
            primitiveArrayImpl.setInfo(convert2);
            return fragment(primitiveArrayImpl, convert2, i, i2);
        }
    }

    private Object convert(PrimitiveArrayImpl primitiveArrayImpl, byte[] bArr) {
        if (primitiveArrayImpl.getType() == 8) {
            return bArr;
        }
        int i = IPrimitiveArray.ELEMENT_SIZE[primitiveArrayImpl.getType()];
        Object newInstance = Array.newInstance(IPrimitiveArray.COMPONENT_TYPE[primitiveArrayImpl.getType()], bArr.length / i);
        int i2 = 0;
        for (int i3 = 0; i3 < bArr.length; i3 += i) {
            switch (primitiveArrayImpl.getType()) {
                case 4:
                    Array.set(newInstance, i2, Boolean.valueOf(bArr[i3] != 0));
                    break;
                case 5:
                    Array.set(newInstance, i2, Character.valueOf(readChar(bArr, i3)));
                    break;
                case 6:
                    Array.set(newInstance, i2, Float.valueOf(readFloat(bArr, i3)));
                    break;
                case 7:
                    Array.set(newInstance, i2, Double.valueOf(readDouble(bArr, i3)));
                    break;
                case 9:
                    Array.set(newInstance, i2, Short.valueOf(readShort(bArr, i3)));
                    break;
                case 10:
                    Array.set(newInstance, i2, Integer.valueOf(readInt(bArr, i3)));
                    break;
                case 11:
                    Array.set(newInstance, i2, Long.valueOf(readLong(bArr, i3)));
                    break;
            }
            i2++;
        }
        return newInstance;
    }

    private Object fragment(AbstractArrayImpl abstractArrayImpl, Object obj, int i, int i2) {
        if (i == 0 && i2 == abstractArrayImpl.getLength()) {
            return obj;
        }
        Object newInstance = Array.newInstance(obj.getClass().getComponentType(), i2);
        System.arraycopy(obj, i, newInstance, 0, i2);
        return newInstance;
    }

    public IObject read(int i, ISnapshot iSnapshot) throws SnapshotException, IOException {
        return this.hprofDump.read(i, this.o2hprof.get(i), iSnapshot);
    }

    public <A> A getAddon(Class<A> cls) throws SnapshotException {
        for (IRuntimeEnhancer addon : this.enhancers) {
            A addon2 = addon.getAddon(this.snapshot, cls);
            if (addon2 != null) {
                return addon2;
            }
        }
        return null;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:0|1|2|3|4) */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000b, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:2:0x0005 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() throws java.io.IOException {
        /*
            r1 = this;
            org.eclipse.mat.hprof.HprofRandomAccessParser r0 = r1.hprofDump     // Catch:{ IOException -> 0x0005 }
            r0.close()     // Catch:{ IOException -> 0x0005 }
        L_0x0005:
            org.eclipse.mat.parser.index.IIndexReader$IOne2LongIndex r0 = r1.o2hprof     // Catch:{ IOException -> 0x000b }
            r0.close()     // Catch:{ IOException -> 0x000b }
            return
        L_0x000b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.hprof.HprofHeapObjectReader.close():void");
    }

    private short readShort(byte[] bArr, int i) {
        return (short) (((bArr[i] & 255) << 8) + (bArr[i + 1] & 255));
    }

    private char readChar(byte[] bArr, int i) {
        return (char) (((bArr[i] & 255) << 8) + (bArr[i + 1] & 255));
    }

    private int readInt(byte[] bArr, int i) {
        return ((bArr[i] & 255) << 24) + ((bArr[i + 1] & 255) << 16) + ((bArr[i + 2] & 255) << 8) + ((bArr[i + 3] & 255) << 0);
    }

    private float readFloat(byte[] bArr, int i) {
        return Float.intBitsToFloat(readInt(bArr, i));
    }

    private long readLong(byte[] bArr, int i) {
        return ((((long) bArr[i]) & 255) << 56) + (((long) (bArr[i + 1] & 255)) << 48) + (((long) (bArr[i + 2] & 255)) << 40) + (((long) (bArr[i + 3] & 255)) << 32) + (((long) (bArr[i + 4] & 255)) << 24) + ((long) ((bArr[i + 5] & 255) << 16)) + ((long) ((bArr[i + 6] & 255) << 8)) + ((long) ((bArr[i + 7] & 255) << 0));
    }

    private double readDouble(byte[] bArr, int i) {
        return Double.longBitsToDouble(readLong(bArr, i));
    }
}
