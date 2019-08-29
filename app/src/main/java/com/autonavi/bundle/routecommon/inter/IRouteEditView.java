package com.autonavi.bundle.routecommon.inter;

import android.text.Editable;
import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface IRouteEditView {

    @Retention(RetentionPolicy.SOURCE)
    public @interface ClickableWidget {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EditWidget {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImageWidget {
    }

    public enum State {
        PRE_EDIT,
        EDIT,
        SUMMARY
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TextWidget {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewWidget {
    }

    public interface a {
        void a(int i, View view, boolean z);
    }

    public interface b {
        boolean a(int i);
    }

    public interface c {
        void a(View view, int i);
    }

    public interface d {
        void a(int i, Editable editable);
    }
}
