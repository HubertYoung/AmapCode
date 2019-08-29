package com.autonavi.widget.ui.route;

public abstract class AbsRouteInputPresenter<T> implements eru {

    public enum Tab {
        TAB_DRIVE(true),
        TAB_BUS(false),
        TAB_WALK(false),
        TAB_CYCLE(false),
        TAB_TRAIN(false);
        
        private boolean mHasPass;

        private Tab(boolean z) {
            this.mHasPass = false;
            this.mHasPass = z;
        }

        public final boolean hasPass() {
            return this.mHasPass;
        }
    }
}
