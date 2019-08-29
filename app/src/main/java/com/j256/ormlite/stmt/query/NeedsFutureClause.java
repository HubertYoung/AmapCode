package com.j256.ormlite.stmt.query;

public interface NeedsFutureClause extends Clause {
    void setMissingClause(Clause clause);
}
