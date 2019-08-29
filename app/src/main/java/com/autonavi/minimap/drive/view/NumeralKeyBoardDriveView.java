package com.autonavi.minimap.drive.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class NumeralKeyBoardDriveView extends LinearLayout {
    /* access modifiers changed from: private */
    public static final int[] NUMERAL_LISTS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    private a<Integer> mKeyNumberListener = null;
    private GridView numeralKeyBoard = null;
    /* access modifiers changed from: private */
    public NumeralKeyBoradAdapter numeralKeyBoradAdapter = null;

    public static class NumeralKeyBoradAdapter extends BaseAdapter {
        private Context context = null;

        static class a {
            TextView a;

            private a() {
                this.a = null;
            }

            /* synthetic */ a(byte b) {
                this();
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public NumeralKeyBoradAdapter(Context context2) {
            this.context = context2;
        }

        public int getCount() {
            if (NumeralKeyBoardDriveView.NUMERAL_LISTS == null || NumeralKeyBoardDriveView.NUMERAL_LISTS.length <= 0) {
                return 0;
            }
            return NumeralKeyBoardDriveView.NUMERAL_LISTS.length;
        }

        public Integer getItem(int i) {
            if (NumeralKeyBoardDriveView.NUMERAL_LISTS == null || NumeralKeyBoardDriveView.NUMERAL_LISTS.length <= 0) {
                return null;
            }
            return Integer.valueOf(NumeralKeyBoardDriveView.NUMERAL_LISTS[i]);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2;
            a aVar;
            if (view == null) {
                aVar = new a(0);
                view2 = LayoutInflater.from(this.context).inflate(R.layout.carmag_lay_numeral_keyboard_item, null);
                aVar.a = (TextView) view2.findViewById(R.id.number_keyboard_item);
                view2.setTag(aVar);
            } else {
                view2 = view;
                aVar = (a) view.getTag();
            }
            Integer item = getItem(i);
            if (item != null) {
                aVar.a.setText(String.valueOf(item));
            }
            return view2;
        }
    }

    public interface a<T> {
        void callKeyNubmer(T t);
    }

    public void setKeyNumberListener(a<Integer> aVar) {
        this.mKeyNumberListener = aVar;
    }

    /* access modifiers changed from: private */
    public void onKeyNumberListener(Integer num) {
        if (this.mKeyNumberListener != null) {
            this.mKeyNumberListener.callKeyNubmer(num);
        }
    }

    public NumeralKeyBoardDriveView(Context context) {
        super(context);
        intiView();
    }

    public NumeralKeyBoardDriveView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        intiView();
    }

    private void intiView() {
        inflate(getContext(), R.layout.carmag_lay_numeral_keyboard, this);
        this.numeralKeyBoard = (GridView) findViewById(R.id.carmag_numeral_keyboard_view);
        this.numeralKeyBoradAdapter = new NumeralKeyBoradAdapter(getContext());
        this.numeralKeyBoard.setAdapter(this.numeralKeyBoradAdapter);
        setKeyBoardListener();
    }

    private void setKeyBoardListener() {
        this.numeralKeyBoard.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (NumeralKeyBoardDriveView.this.numeralKeyBoradAdapter != null) {
                    Integer item = NumeralKeyBoardDriveView.this.numeralKeyBoradAdapter.getItem(i);
                    if (item != null) {
                        NumeralKeyBoardDriveView.this.onKeyNumberListener(item);
                    }
                }
            }
        });
    }
}
