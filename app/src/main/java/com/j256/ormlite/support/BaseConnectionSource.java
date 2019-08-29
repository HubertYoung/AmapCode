package com.j256.ormlite.support;

import com.j256.ormlite.logger.Logger;
import java.sql.SQLException;

public abstract class BaseConnectionSource implements ConnectionSource {
    private ThreadLocal<NestedConnection> specialConnection = new ThreadLocal<>();

    class NestedConnection {
        public final DatabaseConnection connection;
        private int nestedC = 1;

        public NestedConnection(DatabaseConnection connection2) {
            this.connection = connection2;
        }

        public void increment() {
            this.nestedC++;
        }

        public int decrementAndGet() {
            this.nestedC--;
            return this.nestedC;
        }
    }

    public DatabaseConnection getSpecialConnection() {
        NestedConnection currentSaved = this.specialConnection.get();
        if (currentSaved == null) {
            return null;
        }
        return currentSaved.connection;
    }

    /* access modifiers changed from: protected */
    public DatabaseConnection getSavedConnection() {
        NestedConnection nested = this.specialConnection.get();
        if (nested == null) {
            return null;
        }
        return nested.connection;
    }

    /* access modifiers changed from: protected */
    public boolean isSavedConnection(DatabaseConnection connection) {
        NestedConnection currentSaved = this.specialConnection.get();
        if (currentSaved != null && currentSaved.connection == connection) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean saveSpecial(DatabaseConnection connection) {
        NestedConnection currentSaved = this.specialConnection.get();
        if (currentSaved == null) {
            this.specialConnection.set(new NestedConnection(connection));
            return true;
        } else if (currentSaved.connection != connection) {
            throw new SQLException("trying to save connection " + connection + " but already have saved connection " + currentSaved.connection);
        } else {
            currentSaved.increment();
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean clearSpecial(DatabaseConnection connection, Logger logger) {
        NestedConnection currentSaved = this.specialConnection.get();
        if (connection == null) {
            return false;
        }
        if (currentSaved == null) {
            logger.error("no connection has been saved when clear() called");
            return false;
        } else if (currentSaved.connection == connection) {
            if (currentSaved.decrementAndGet() == 0) {
                this.specialConnection.set(null);
            }
            return true;
        } else {
            logger.error((String) "connection saved {} is not the one being cleared {}", (Object) currentSaved.connection, (Object) connection);
            return false;
        }
    }
}
