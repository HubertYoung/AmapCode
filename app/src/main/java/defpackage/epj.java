package defpackage;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/* renamed from: epj reason: default package */
/* compiled from: Table */
public class epj {
    public static final ThreadLocal<Charset> a = new ThreadLocal<Charset>() {
        /* access modifiers changed from: protected */
        public final /* synthetic */ Object initialValue() {
            return Charset.forName("UTF-8");
        }
    };
    private static final ThreadLocal<CharsetDecoder> b = new ThreadLocal<CharsetDecoder>() {
        /* access modifiers changed from: protected */
        public final /* synthetic */ Object initialValue() {
            return Charset.forName("UTF-8").newDecoder();
        }
    };
    private static final ThreadLocal<CharBuffer> c = new ThreadLocal<>();
}
