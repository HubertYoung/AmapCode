package android.support.constraint.solver.widgets;

public class ResolutionDimension extends ResolutionNode {
    float a = 0.0f;

    public final void b() {
        super.b();
        this.a = 0.0f;
    }

    public final void a(int i) {
        if (this.i == 0 || this.a != ((float) i)) {
            this.a = (float) i;
            if (this.i == 1) {
                c();
            }
            d();
        }
    }
}
