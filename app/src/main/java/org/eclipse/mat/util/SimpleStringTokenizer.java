package org.eclipse.mat.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class SimpleStringTokenizer implements Iterable<String> {
    /* access modifiers changed from: private */
    public char delim;
    /* access modifiers changed from: private */
    public String subject;

    public SimpleStringTokenizer(String str, char c) {
        this.subject = str;
        this.delim = c;
    }

    public final Iterator<String> iterator() {
        return new Iterator<String>() {
            int maxPosition = SimpleStringTokenizer.this.subject.length();
            int position = 0;

            public boolean hasNext() {
                return this.position < this.maxPosition;
            }

            public String next() {
                if (this.position >= this.maxPosition) {
                    throw new NoSuchElementException();
                }
                int indexOf = SimpleStringTokenizer.this.subject.indexOf(SimpleStringTokenizer.this.delim, this.position);
                if (indexOf < 0) {
                    String substring = SimpleStringTokenizer.this.subject.substring(this.position);
                    this.position = this.maxPosition;
                    return substring;
                }
                String substring2 = SimpleStringTokenizer.this.subject.substring(this.position, indexOf);
                this.position = indexOf + 1;
                return substring2;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static String[] split(String str, char c) {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = new SimpleStringTokenizer(str, c).iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().trim());
        }
        return (String[]) arrayList.toArray(new String[0]);
    }
}
