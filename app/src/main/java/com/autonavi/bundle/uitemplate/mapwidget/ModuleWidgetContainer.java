package com.autonavi.bundle.uitemplate.mapwidget;

import android.support.annotation.NonNull;

public interface ModuleWidgetContainer {
    void addAjxWidget(@NonNull String str);

    void addWidget(String str);

    String getWidgetVisible(@NonNull String str);

    boolean hasWidget(@NonNull String str);

    void removeWidget(@NonNull String str);

    void setWidget(String str, String[] strArr);

    void setWidgetVisible(@NonNull String str, @NonNull String str2);
}
