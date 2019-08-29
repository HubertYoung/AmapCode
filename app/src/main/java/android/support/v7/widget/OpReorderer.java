package android.support.v7.widget;

import java.util.List;

class OpReorderer {
    final Callback a;

    interface Callback {
        UpdateOp a(int i, int i2, int i3, Object obj);

        void a(UpdateOp updateOp);
    }

    public OpReorderer(Callback callback) {
        this.a = callback;
    }

    static void a(List<UpdateOp> list, int i, UpdateOp updateOp, int i2, UpdateOp updateOp2) {
        int i3 = updateOp.d < updateOp2.b ? -1 : 0;
        if (updateOp.b < updateOp2.b) {
            i3++;
        }
        if (updateOp2.b <= updateOp.b) {
            updateOp.b += updateOp2.d;
        }
        if (updateOp2.b <= updateOp.d) {
            updateOp.d += updateOp2.d;
        }
        updateOp2.b += i3;
        list.set(i, updateOp2);
        list.set(i2, updateOp);
    }

    static int a(List<UpdateOp> list) {
        boolean z = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (list.get(size).a != 8) {
                z = true;
            } else if (z) {
                return size;
            }
        }
        return -1;
    }
}
