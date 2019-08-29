package anet.channel.statist;

import java.io.Serializable;

public abstract class StatObject implements Serializable {
    public boolean beforeCommit() {
        return true;
    }
}
