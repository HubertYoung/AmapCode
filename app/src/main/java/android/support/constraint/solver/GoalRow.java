package android.support.constraint.solver;

public class GoalRow extends ArrayRow {
    public GoalRow(Cache cache) {
        super(cache);
    }

    public final void c(SolverVariable solverVariable) {
        super.c(solverVariable);
        solverVariable.j--;
    }
}
