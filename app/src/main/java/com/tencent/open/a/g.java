package com.tencent.open.a;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: ProGuard */
public class g implements Iterable<String> {
    private ConcurrentLinkedQueue<String> a;
    private AtomicInteger b;

    public g() {
        this.a = null;
        this.b = null;
        this.a = new ConcurrentLinkedQueue<>();
        this.b = new AtomicInteger(0);
    }

    public int a(String str) {
        int length = str.length();
        this.a.add(str);
        return this.b.addAndGet(length);
    }

    public void a(Writer writer, char[] cArr) throws IOException {
        if (writer != null && cArr != null && cArr.length != 0) {
            int length = cArr.length;
            Iterator<String> it = iterator();
            int i = length;
            int i2 = 0;
            while (it.hasNext()) {
                String next = it.next();
                int length2 = next.length();
                int i3 = 0;
                while (length2 > 0) {
                    int i4 = i > length2 ? length2 : i;
                    int i5 = i3 + i4;
                    next.getChars(i3, i5, cArr, i2);
                    i -= i4;
                    i2 += i4;
                    length2 -= i4;
                    if (i == 0) {
                        writer.write(cArr, 0, length);
                        i = length;
                        i3 = i5;
                        i2 = 0;
                    } else {
                        i3 = i5;
                    }
                }
            }
            if (i2 > 0) {
                writer.write(cArr, 0, i2);
            }
            writer.flush();
        }
    }

    public int a() {
        return this.b.get();
    }

    public void b() {
        this.a.clear();
        this.b.set(0);
    }

    public Iterator<String> iterator() {
        return this.a.iterator();
    }
}
