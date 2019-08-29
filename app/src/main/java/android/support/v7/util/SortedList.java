package android.support.v7.util;

import java.util.Comparator;

public class SortedList<T> {

    public static class BatchedCallback<T2> extends Callback<T2> {
        private final Callback<T2> a;

        public int compare(T2 t2, T2 t22) {
            return this.a.compare(t2, t22);
        }
    }

    public static abstract class Callback<T2> implements Comparator<T2> {
        public abstract int compare(T2 t2, T2 t22);
    }
}
