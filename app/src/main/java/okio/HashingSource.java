package okio;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashingSource extends ForwardingSource {
    private final MessageDigest messageDigest;

    public static HashingSource md5(Source source) {
        return new HashingSource(source, "MD5");
    }

    public static HashingSource sha1(Source source) {
        return new HashingSource(source, "SHA-1");
    }

    public static HashingSource sha256(Source source) {
        return new HashingSource(source, "SHA-256");
    }

    private HashingSource(Source source, String str) {
        super(source);
        try {
            this.messageDigest = MessageDigest.getInstance(str);
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    public final long read(Buffer buffer, long j) throws IOException {
        long read = super.read(buffer, j);
        if (read != -1) {
            long j2 = buffer.size - read;
            long j3 = buffer.size;
            Segment segment = buffer.head;
            while (j3 > buffer.size - read) {
                segment = segment.prev;
                j3 -= (long) (segment.limit - segment.pos);
            }
            while (j3 < buffer.size) {
                int i = (int) ((((long) segment.pos) + j2) - j3);
                this.messageDigest.update(segment.data, i, segment.limit - i);
                j2 = ((long) (segment.limit - segment.pos)) + j3;
                j3 = j2;
            }
        }
        return read;
    }

    public final ByteString hash() {
        return ByteString.of(this.messageDigest.digest());
    }
}
