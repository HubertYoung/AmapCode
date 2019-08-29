package android.support.constraint.solver;

public class Cache {
    Pool<ArrayRow> a = new SimplePool();
    Pool<SolverVariable> b = new SimplePool();
    SolverVariable[] c = new SolverVariable[32];
}
