package com.j256.ormlite.misc;

import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;

public abstract class BaseDaoEnabled<T, ID> {
    protected transient Dao<T, ID> dao;

    public int create() {
        checkForDao();
        return this.dao.create(this);
    }

    public int refresh() {
        checkForDao();
        return this.dao.refresh(this);
    }

    public int update() {
        checkForDao();
        return this.dao.update(this);
    }

    public int updateId(ID newId) {
        checkForDao();
        return this.dao.updateId(this, newId);
    }

    public int delete() {
        checkForDao();
        return this.dao.delete(this);
    }

    public String objectToString() {
        try {
            checkForDao();
            return this.dao.objectToString(this);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public ID extractId() {
        checkForDao();
        return this.dao.extractId(this);
    }

    public boolean objectsEqual(T other) {
        checkForDao();
        return this.dao.objectsEqual(this, other);
    }

    public void setDao(Dao<T, ID> dao2) {
        this.dao = dao2;
    }

    public Dao<T, ID> getDao() {
        return this.dao;
    }

    private void checkForDao() {
        if (this.dao == null) {
            throw new SQLException("Dao has not been set on " + getClass() + " object: " + this);
        }
    }
}
