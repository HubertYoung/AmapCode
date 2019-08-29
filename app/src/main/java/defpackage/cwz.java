package defpackage;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.bundle.evaluate.delegate.StatisticAdapter.BatExpandableListAdapter;
import com.autonavi.minimap.bundle.evaluate.delegate.StatisticAdapter.BatListAdapter;
import com.autonavi.minimap.bundle.evaluate.delegate.StatisticExpandableListListener.StatisticChildClickListener;
import com.autonavi.minimap.bundle.evaluate.delegate.StatisticExpandableListListener.StatisticGroupClickListener;
import com.autonavi.minimap.bundle.evaluate.delegate.StatisticOnItemClickListener;
import com.autonavi.minimap.bundle.evaluate.handle.DelegateComponent$1;
import com.autonavi.minimap.bundle.evaluate.handle.DelegateComponent$10;
import com.autonavi.minimap.bundle.evaluate.handle.DelegateComponent$11;
import com.autonavi.minimap.bundle.evaluate.handle.DelegateComponent$5;
import com.autonavi.minimap.bundle.evaluate.handle.DelegateComponent$6;
import com.autonavi.minimap.bundle.evaluate.handle.DelegateComponent$7;
import com.autonavi.minimap.bundle.evaluate.handle.DelegateComponent$8;
import com.autonavi.minimap.bundle.evaluate.handle.DelegateComponent$9;
import com.autonavi.minimap.evaluate.draugorp.StatisticOnItemTouchListener;
import com.iflytek.tts.TtsService.Tts;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: cwz reason: default package */
/* compiled from: DelegateComponent */
public class cwz {
    private static cwz a;
    private static Field b;
    /* access modifiers changed from: private */
    public static Field c;
    private static Field d;
    private static Field e;
    private static Field f;
    private static Field g;
    private static Field h;
    private static Field i;
    private static Field j;
    private static Field k;
    private static Field l;

    private cwz() {
    }

    public static cwz a() {
        if (a == null) {
            synchronized (cwz.class) {
                if (a == null) {
                    cwz cwz = new cwz();
                    a = cwz;
                    try {
                        if (c == null) {
                            Field declaredField = View.class.getDeclaredField("mAccessibilityDelegate");
                            c = declaredField;
                            declaredField.setAccessible(true);
                        }
                        if (b == null) {
                            Field declaredField2 = ViewGroup.class.getDeclaredField("mOnHierarchyChangeListener");
                            b = declaredField2;
                            declaredField2.setAccessible(true);
                        }
                    } catch (NoSuchFieldException e2) {
                        e2.printStackTrace();
                    }
                    if (e == null || f == null) {
                        try {
                            Class<?> cls = Class.forName("android.view.View$ListenerInfo");
                            Field declaredField3 = View.class.getDeclaredField("mListenerInfo");
                            g = declaredField3;
                            declaredField3.setAccessible(true);
                            Field declaredField4 = cls.getDeclaredField("mOnTouchListener");
                            e = declaredField4;
                            declaredField4.setAccessible(true);
                            Field declaredField5 = cls.getDeclaredField("mOnClickListener");
                            f = declaredField5;
                            declaredField5.setAccessible(true);
                        } catch (NoSuchFieldException e3) {
                            e3.printStackTrace();
                        } catch (ClassNotFoundException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (j == null) {
                        try {
                            Field declaredField6 = CompoundButton.class.getDeclaredField("mOnCheckedChangeListener");
                            j = declaredField6;
                            declaredField6.setAccessible(true);
                        } catch (NoSuchFieldException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (d == null) {
                        try {
                            Field declaredField7 = AdapterView.class.getDeclaredField("mOnItemClickListener");
                            d = declaredField7;
                            declaredField7.setAccessible(true);
                        } catch (NoSuchFieldException e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (k == null) {
                        try {
                            Field declaredField8 = RecyclerView.class.getDeclaredField("mOnItemTouchListeners");
                            k = declaredField8;
                            declaredField8.setAccessible(true);
                        } catch (NoSuchFieldException e7) {
                            e7.printStackTrace();
                        }
                    }
                    if (l == null) {
                        try {
                            Field declaredField9 = GestureDetector.class.getDeclaredField("mListener");
                            l = declaredField9;
                            declaredField9.setAccessible(true);
                        } catch (NoSuchFieldException e8) {
                            e8.printStackTrace();
                        }
                    }
                    if (h == null) {
                        try {
                            Field declaredField10 = ExpandableListView.class.getDeclaredField("mOnGroupClickListener");
                            h = declaredField10;
                            declaredField10.setAccessible(true);
                        } catch (NoSuchFieldException e9) {
                            e9.printStackTrace();
                        }
                    }
                    if (i == null) {
                        try {
                            Field declaredField11 = ExpandableListView.class.getDeclaredField("mOnChildClickListener");
                            i = declaredField11;
                            declaredField11.setAccessible(true);
                        } catch (NoSuchFieldException e10) {
                            e10.printStackTrace();
                        }
                    }
                    try {
                        Class<?> cls2 = Class.forName("android.view.WindowManagerGlobal");
                        Field declaredField12 = cls2.getDeclaredField("mViews");
                        if (declaredField12.getType().toString().endsWith("ArrayList")) {
                            Object invoke = cls2.getDeclaredMethod("getInstance", new Class[0]).invoke(cls2, new Object[0]);
                            declaredField12.setAccessible(true);
                            Object obj = declaredField12.get(invoke);
                            if (obj instanceof ArrayList) {
                                declaredField12.set(invoke, new DelegateComponent$1(cwz, (ArrayList) obj));
                            }
                        }
                    } catch (ClassNotFoundException e11) {
                        e11.printStackTrace();
                    } catch (NoSuchMethodException e12) {
                        e12.printStackTrace();
                    } catch (IllegalAccessException e13) {
                        e13.printStackTrace();
                    } catch (InvocationTargetException e14) {
                        e14.printStackTrace();
                    } catch (NoSuchFieldException e15) {
                        e15.printStackTrace();
                    }
                }
            }
        }
        return a;
    }

    /* access modifiers changed from: private */
    public void a(View view) {
        if (view != null && !(view instanceof ViewExtension)) {
            c(view);
            b(view);
        }
    }

    private void b(final View view) {
        if (e != null && view != null) {
            String simpleName = view.getClass().getSimpleName();
            if (simpleName.equals("GPSButtonNewMainPage") || simpleName.equals("GPSButton") || simpleName.equals("DIYEntryView")) {
                try {
                    Object obj = g.get(view);
                    if (obj != null) {
                        final OnTouchListener onTouchListener = (OnTouchListener) e.get(obj);
                        if (onTouchListener != null && !(onTouchListener instanceof cwx)) {
                            view.setOnTouchListener(new cwx() {
                                public final boolean a(View view, MotionEvent motionEvent) {
                                    Handler handler = cxi.a().a;
                                    if (handler != null && motionEvent.getAction() == 1) {
                                        Message obtainMessage = handler.obtainMessage();
                                        obtainMessage.what = 256;
                                        obtainMessage.obj = view;
                                        Bundle bundle = new Bundle();
                                        cxk.a();
                                        bundle.putString("_view_name", cxk.c());
                                        obtainMessage.setData(bundle);
                                        obtainMessage.arg1 = Tts.TTS_STATE_DESTROY;
                                        handler.sendMessage(obtainMessage);
                                    }
                                    return onTouchListener.onTouch(view, motionEvent);
                                }
                            });
                        }
                    }
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private boolean a(ExpandableListView expandableListView, Context context) {
        boolean z = false;
        if (expandableListView == null || context == null) {
            return false;
        }
        try {
            OnGroupClickListener onGroupClickListener = (OnGroupClickListener) h.get(expandableListView);
            OnChildClickListener onChildClickListener = (OnChildClickListener) i.get(expandableListView);
            if (!(onGroupClickListener == null && onChildClickListener == null)) {
                z = true;
            }
            if (onGroupClickListener != null && !(onGroupClickListener instanceof StatisticGroupClickListener)) {
                expandableListView.setOnGroupClickListener(new DelegateComponent$5(this, onGroupClickListener));
            }
            if (onChildClickListener != null && !(onChildClickListener instanceof StatisticChildClickListener)) {
                expandableListView.setOnChildClickListener(new DelegateComponent$6(this, onChildClickListener));
            }
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return z;
    }

    private boolean a(AdapterView adapterView, Context context) {
        boolean z = false;
        if (d == null || adapterView == null) {
            return false;
        }
        if (adapterView instanceof ExpandableListView) {
            return a((ExpandableListView) adapterView, context);
        }
        try {
            OnItemClickListener onItemClickListener = (OnItemClickListener) d.get(adapterView);
            if (onItemClickListener != null) {
                z = true;
            }
            if (onItemClickListener != null && !(onItemClickListener instanceof StatisticOnItemClickListener)) {
                adapterView.setOnItemClickListener(new DelegateComponent$7(this, onItemClickListener));
            }
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return z;
    }

    private void c(View view) {
        try {
            if (c != null) {
                if (view != null) {
                    AccessibilityDelegate accessibilityDelegate = (AccessibilityDelegate) c.get(view);
                    if (!(accessibilityDelegate instanceof cwv)) {
                        view.setAccessibilityDelegate(new cwv(accessibilityDelegate) {
                            public final void a(View view, int i) {
                                Handler handler = cxi.a().a;
                                if (handler != null) {
                                    Message obtainMessage = handler.obtainMessage();
                                    obtainMessage.what = 256;
                                    obtainMessage.obj = view;
                                    Bundle bundle = new Bundle();
                                    cxk.a();
                                    bundle.putString("_view_name", cxk.c());
                                    obtainMessage.setData(bundle);
                                    if (i == 1) {
                                        obtainMessage.arg1 = 257;
                                        handler.sendMessage(obtainMessage);
                                    } else if (i == 8192) {
                                        obtainMessage.arg1 = Tts.TTS_STATE_CREATED;
                                        handler.sendMessage(obtainMessage);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    static boolean a(Object obj, Class cls) throws IllegalAccessException {
        if (obj == null) {
            return true;
        }
        if (OnClickListener.class != cls) {
            return OnCheckedChangeListener.class != cls || j == null || !(obj instanceof CompoundButton) || !(j.get(obj) instanceof OnCheckedChangeListener);
        }
        if (f != null && (obj instanceof View)) {
            Object obj2 = g.get(obj);
            return obj2 == null || !(f.get(obj2) instanceof OnClickListener);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void a(ViewGroup viewGroup, final Context context) {
        boolean z;
        if (viewGroup != null && context != null) {
            if (!(b == null || viewGroup == null || context == null)) {
                try {
                    final OnHierarchyChangeListener onHierarchyChangeListener = (OnHierarchyChangeListener) b.get(viewGroup);
                    if (!(viewGroup instanceof RadioGroup) && !(onHierarchyChangeListener instanceof cww)) {
                        viewGroup.setOnHierarchyChangeListener(new cww() {
                            public final void a(View view, View view2) {
                                if (view2 instanceof ViewGroup) {
                                    cwz.this.a((ViewGroup) view2, context);
                                } else {
                                    cwz.this.a(view2);
                                }
                                if (onHierarchyChangeListener != null) {
                                    onHierarchyChangeListener.onChildViewAdded(view, view2);
                                }
                            }

                            public final void b(View view, View view2) {
                                if (onHierarchyChangeListener != null) {
                                    onHierarchyChangeListener.onChildViewRemoved(view, view2);
                                }
                            }
                        });
                    }
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                }
            }
            if (viewGroup instanceof AdapterView) {
                AdapterView adapterView = (AdapterView) viewGroup;
                if (!(adapterView == null || context == null || a(adapterView, context))) {
                    cxk.a();
                    if (!cxk.c().startsWith("SearchMorePage")) {
                        Adapter adapter = adapterView.getAdapter();
                        if (adapter != null) {
                            if (adapter instanceof HeaderViewListAdapter) {
                                HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                                if (!(headerViewListAdapter.getWrappedAdapter() instanceof BatListAdapter) && !(adapterView instanceof ExpandableListView)) {
                                    adapterView.setAdapter(new DelegateComponent$8(this, headerViewListAdapter.getWrappedAdapter(), context));
                                    return;
                                }
                            } else if (adapter instanceof ExpandableListAdapter) {
                                if (!(adapter instanceof BatExpandableListAdapter) && (adapterView instanceof ExpandableListView)) {
                                    ((ExpandableListView) adapterView).setAdapter(new DelegateComponent$9(this, (ExpandableListAdapter) adapter, context));
                                    return;
                                }
                            } else if (adapter instanceof ListAdapter) {
                                if ((adapterView instanceof ExpandableListView) && !(adapter instanceof BatExpandableListAdapter)) {
                                    ((ExpandableListView) adapterView).setAdapter(new DelegateComponent$10(this, (ExpandableListAdapter) adapter, context));
                                }
                                if (!(adapter instanceof BatListAdapter)) {
                                    adapterView.setAdapter(new DelegateComponent$11(this, (ListAdapter) adapter, context));
                                }
                            }
                        }
                    }
                }
                return;
            }
            if (viewGroup instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) viewGroup;
                if (!(k == null || recyclerView == null || !recyclerView.getClass().getSimpleName().equals("DragRecyclerView"))) {
                    try {
                        if (k != null) {
                            Iterator it = ((ArrayList) k.get(recyclerView)).iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (((OnItemTouchListener) it.next()) instanceof StatisticOnItemTouchListener) {
                                        z = true;
                                        break;
                                    }
                                } else {
                                    z = false;
                                    break;
                                }
                            }
                            if (!z) {
                                recyclerView.addOnItemTouchListener(new StatisticOnItemTouchListener() {
                                    public final boolean a(RecyclerView recyclerView, MotionEvent motionEvent) {
                                        Handler handler = cxi.a().a;
                                        if (handler != null) {
                                            View findChildViewUnder = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                                            if (findChildViewUnder != null) {
                                                Message obtainMessage = handler.obtainMessage();
                                                obtainMessage.what = 256;
                                                obtainMessage.obj = findChildViewUnder;
                                                Bundle bundle = new Bundle();
                                                cxk.a();
                                                bundle.putString("_view_name", cxk.c());
                                                obtainMessage.setData(bundle);
                                                if (motionEvent.getAction() == 1) {
                                                    obtainMessage.arg1 = Tts.TTS_STATE_INVALID_DATA;
                                                }
                                                handler.sendMessage(obtainMessage);
                                            }
                                        }
                                        return false;
                                    }
                                });
                            }
                        }
                    } catch (IllegalAccessException e3) {
                        e3.printStackTrace();
                    } catch (AbstractMethodError e4) {
                        e4.printStackTrace();
                    }
                }
            }
            if (!(viewGroup instanceof ViewExtension)) {
                a(viewGroup);
            }
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt instanceof ViewGroup) {
                    a((ViewGroup) childAt, context);
                } else if (!(childAt instanceof ViewExtension)) {
                    a(childAt);
                }
            }
        }
    }

    static /* synthetic */ void b(cwz cwz, ViewGroup viewGroup, Context context) {
        if (viewGroup != null && context != null) {
            cwz.a(viewGroup, context);
        }
    }
}
