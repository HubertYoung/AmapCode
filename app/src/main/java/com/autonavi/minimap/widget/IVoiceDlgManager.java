package com.autonavi.minimap.widget;

import android.content.Context;

public interface IVoiceDlgManager {

    public interface ISearchEdit {
        void onResults(String str);
    }

    void dissmissIatDialog();

    boolean isIatDialogShowing();

    void onDestory();

    void setSearchEdit(ISearchEdit iSearchEdit);

    void showIatDialog(Context context, String str, String str2);
}
