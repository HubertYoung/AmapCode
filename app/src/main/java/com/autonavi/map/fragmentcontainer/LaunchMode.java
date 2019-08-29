package com.autonavi.map.fragmentcontainer;

public abstract class LaunchMode {

    public interface launchModeSingleInstance {
    }

    public interface launchModeSingleIntanceWithoutReuse extends launchModeSingleInstance {
    }

    public interface launchModeSingleTask {
    }

    public interface launchModeSingleTaskWithoutReuse extends launchModeSingleTask {
    }

    public interface launchModeSingleTop {
    }

    public interface launchModeSingleTopAllowDuplicate {
        int maxDuplicateCount();
    }
}
