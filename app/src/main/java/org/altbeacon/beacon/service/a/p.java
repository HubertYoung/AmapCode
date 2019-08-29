package org.altbeacon.beacon.service.a;

import android.support.annotation.NonNull;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

/* compiled from: DistinctPacketDetector */
public final class p {
    @NonNull
    private final Set<ByteBuffer> a = new HashSet();

    public final void a() {
        this.a.clear();
    }

    public final boolean a(@NonNull String originMacAddress, @NonNull byte[] scanRecord) {
        byte[] macBytes = originMacAddress.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(macBytes.length + scanRecord.length);
        buffer.put(macBytes);
        buffer.put(scanRecord);
        buffer.rewind();
        if (this.a.size() == 1000) {
            return this.a.contains(buffer);
        }
        return this.a.add(buffer);
    }
}
