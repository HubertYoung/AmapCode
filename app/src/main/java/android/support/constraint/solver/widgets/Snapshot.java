package android.support.constraint.solver.widgets;

import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import java.util.ArrayList;

public class Snapshot {
    int a;
    int b;
    int c;
    int d;
    ArrayList<Connection> e = new ArrayList<>();

    static class Connection {
        ConstraintAnchor a;
        ConstraintAnchor b;
        int c;
        Strength d;
        int e;

        public Connection(ConstraintAnchor constraintAnchor) {
            this.a = constraintAnchor;
            this.b = constraintAnchor.d;
            this.c = constraintAnchor.b();
            this.d = constraintAnchor.g;
            this.e = constraintAnchor.h;
        }
    }

    public Snapshot(ConstraintWidget constraintWidget) {
        this.a = constraintWidget.l();
        this.b = constraintWidget.m();
        this.c = constraintWidget.n();
        this.d = constraintWidget.o();
        ArrayList<ConstraintAnchor> w = constraintWidget.w();
        int size = w.size();
        for (int i = 0; i < size; i++) {
            this.e.add(new Connection(w.get(i)));
        }
    }

    public final void a(ConstraintWidget constraintWidget) {
        constraintWidget.c(this.a);
        constraintWidget.d(this.b);
        constraintWidget.e(this.c);
        constraintWidget.f(this.d);
        int size = this.e.size();
        for (int i = 0; i < size; i++) {
            Connection connection = this.e.get(i);
            constraintWidget.a(connection.a.c).a(connection.b, connection.c, connection.d, connection.e);
        }
    }
}
