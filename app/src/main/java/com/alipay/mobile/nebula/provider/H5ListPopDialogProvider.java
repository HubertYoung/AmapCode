package com.alipay.mobile.nebula.provider;

import android.app.Activity;
import java.util.ArrayList;

public interface H5ListPopDialogProvider {

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    void createDialog(Activity activity, ArrayList<String> arrayList);

    void disMissDialog();

    void setOnItemClickListener(OnItemClickListener onItemClickListener);

    void showDialog();
}
