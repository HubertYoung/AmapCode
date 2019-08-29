package org.eclipse.mat.hprof;

import java.lang.ref.SoftReference;

class ArrayDescription {

    static class Offline extends ArrayDescription {
        int arraySize;
        int elementSize;
        boolean isPrimitive;
        SoftReference<Object> lazyReadContent = new SoftReference<>(null);
        long position;

        public Offline(boolean z, long j, int i, int i2) {
            this.isPrimitive = z;
            this.position = j;
            this.elementSize = i;
            this.arraySize = i2;
        }

        public boolean isPrimitive() {
            return this.isPrimitive;
        }

        public long getPosition() {
            return this.position;
        }

        public int getArraySize() {
            return this.arraySize;
        }

        public int getElementSize() {
            return this.elementSize;
        }

        public Object getLazyReadContent() {
            return this.lazyReadContent.get();
        }

        public void setLazyReadContent(Object obj) {
            this.lazyReadContent = new SoftReference<>(obj);
        }
    }

    static class Raw extends ArrayDescription {
        byte[] content;

        public Raw(byte[] bArr) {
            this.content = bArr;
        }

        public byte[] getContent() {
            return this.content;
        }
    }

    ArrayDescription() {
    }
}
