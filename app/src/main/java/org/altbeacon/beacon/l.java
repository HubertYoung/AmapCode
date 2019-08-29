package org.altbeacon.beacon;

import android.annotation.TargetApi;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;

/* compiled from: Identifier */
public final class l implements Serializable, Comparable<l> {
    private static final Pattern a = Pattern.compile("^0x[0-9A-Fa-f]*$");
    private static final Pattern b = Pattern.compile("^[0-9A-Fa-f]*$");
    private static final Pattern c = Pattern.compile("^0|[1-9][0-9]*$");
    private static final Pattern d = Pattern.compile("^[0-9A-Fa-f]{8}-?[0-9A-Fa-f]{4}-?[0-9A-Fa-f]{4}-?[0-9A-Fa-f]{4}-?[0-9A-Fa-f]{12}$");
    private final byte[] e;

    public static l a(String stringValue) {
        return b(stringValue);
    }

    private static l b(String stringValue) {
        if (stringValue == null) {
            throw new NullPointerException("Identifiers cannot be constructed from null pointers but \"stringValue\" is null.");
        } else if (a.matcher(stringValue).matches()) {
            return c(stringValue.substring(2));
        } else {
            if (d.matcher(stringValue).matches()) {
                return c(stringValue.replace("-", ""));
            }
            if (c.matcher(stringValue).matches()) {
                try {
                    return a(Integer.valueOf(stringValue).intValue());
                } catch (Throwable t) {
                    throw new IllegalArgumentException("Unable to parse Identifier in decimal format.", t);
                }
            } else if (b.matcher(stringValue).matches()) {
                return c(stringValue);
            } else {
                throw new IllegalArgumentException("Unable to parse Identifier.");
            }
        }
    }

    private static l c(String identifierString) {
        String str = (identifierString.length() % 2 == 0 ? "" : "0") + identifierString.toUpperCase();
        byte[] result = new byte[(str.length() / 2)];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16) & 255);
        }
        return new l(result);
    }

    private static l a(int intValue) {
        if (intValue < 0 || intValue > 65535) {
            throw new IllegalArgumentException("Identifiers can only be constructed from integers between 0 and 65535 (inclusive).");
        }
        return new l(new byte[]{(byte) (intValue >> 8), (byte) intValue});
    }

    @TargetApi(9)
    public static l a(byte[] bytes, int start, int end, boolean littleEndian) {
        if (bytes == null) {
            throw new NullPointerException("Identifiers cannot be constructed from null pointers but \"bytes\" is null.");
        } else if (start < 0 || start > bytes.length) {
            throw new ArrayIndexOutOfBoundsException("start < 0 || start > bytes.length");
        } else if (end > bytes.length) {
            throw new ArrayIndexOutOfBoundsException("end > bytes.length");
        } else if (start > end) {
            throw new IllegalArgumentException("start > end");
        } else {
            byte[] byteRange = Arrays.copyOfRange(bytes, start, end);
            if (littleEndian) {
                a(byteRange);
            }
            return new l(byteRange);
        }
    }

    private l(byte[] value) {
        if (value == null) {
            throw new NullPointerException("Identifiers cannot be constructed from null pointers but \"value\" is null.");
        }
        this.e = value;
    }

    public final String toString() {
        if (this.e.length == 2) {
            return Integer.toString(a());
        }
        if (this.e.length == 16) {
            return b().toString();
        }
        return c();
    }

    public final int a() {
        if (this.e.length > 2) {
            throw new UnsupportedOperationException("Only supported for Identifiers with max byte length of 2");
        }
        int result = 0;
        for (int i = 0; i < this.e.length; i++) {
            result |= (this.e[i] & 255) << (((this.e.length - i) - 1) * 8);
        }
        return result;
    }

    private static void a(byte[] bytes) {
        for (int i = 0; i < bytes.length / 2; i++) {
            int mirroredIndex = (bytes.length - i) - 1;
            byte tmp = bytes[i];
            bytes[i] = bytes[mirroredIndex];
            bytes[mirroredIndex] = tmp;
        }
    }

    public final int hashCode() {
        return Arrays.hashCode(this.e);
    }

    public final boolean equals(Object that) {
        if (!(that instanceof l)) {
            return false;
        }
        return Arrays.equals(this.e, ((l) that).e);
    }

    private String c() {
        StringBuilder sb = new StringBuilder((this.e.length * 2) + 2);
        sb.append("0x");
        for (byte item : this.e) {
            sb.append(String.format("%02x", new Object[]{Byte.valueOf(item)}));
        }
        return sb.toString();
    }

    public final UUID b() {
        if (this.e.length != 16) {
            throw new UnsupportedOperationException("Only Identifiers backed by a byte array with length of exactly 16 can be UUIDs.");
        }
        LongBuffer buf = ByteBuffer.wrap(this.e).asLongBuffer();
        return new UUID(buf.get(), buf.get());
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public int compareTo(l that) {
        if (this.e.length == that.e.length) {
            int i = 0;
            while (i < this.e.length) {
                if (this.e[i] == that.e[i]) {
                    i++;
                } else if (this.e[i] >= that.e[i]) {
                    return 1;
                } else {
                    return -1;
                }
            }
            return 0;
        } else if (this.e.length < that.e.length) {
            return -1;
        } else {
            return 1;
        }
    }
}
