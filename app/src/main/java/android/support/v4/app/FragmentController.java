package android.support.v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v4.util.SimpleArrayMap;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FragmentController {
    private final FragmentHostCallback<?> mHost;

    public static final FragmentController createController(FragmentHostCallback<?> fragmentHostCallback) {
        return new FragmentController(fragmentHostCallback);
    }

    private FragmentController(FragmentHostCallback<?> fragmentHostCallback) {
        this.mHost = fragmentHostCallback;
    }

    public FragmentManager getSupportFragmentManager() {
        return this.mHost.getFragmentManagerImpl();
    }

    public LoaderManager getSupportLoaderManager() {
        return this.mHost.getLoaderManagerImpl();
    }

    public int getActiveFragmentsCount() {
        ArrayList<Fragment> arrayList = this.mHost.mFragmentManager.f;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public List<Fragment> getActiveFragments(List<Fragment> list) {
        if (this.mHost.mFragmentManager.f == null) {
            return null;
        }
        if (list == null) {
            list = new ArrayList<>(getActiveFragmentsCount());
        }
        list.addAll(this.mHost.mFragmentManager.f);
        return list;
    }

    public void attachHost(Fragment fragment) {
        this.mHost.mFragmentManager.a((FragmentHostCallback) this.mHost, (FragmentContainer) this.mHost, fragment);
    }

    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return this.mHost.mFragmentManager.onCreateView(view, str, context, attributeSet);
    }

    public void noteStateNotSaved() {
        this.mHost.mFragmentManager.t = false;
    }

    public Parcelable saveAllState() {
        return this.mHost.mFragmentManager.d();
    }

    public void restoreAllState(Parcelable parcelable, List<Fragment> list) {
        this.mHost.mFragmentManager.a(parcelable, list);
    }

    public List<Fragment> retainNonConfig() {
        FragmentManagerImpl fragmentManagerImpl = this.mHost.mFragmentManager;
        ArrayList arrayList = null;
        if (fragmentManagerImpl.f != null) {
            for (int i = 0; i < fragmentManagerImpl.f.size(); i++) {
                Fragment fragment = fragmentManagerImpl.f.get(i);
                if (fragment != null && fragment.mRetainInstance) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(fragment);
                    fragment.mRetaining = true;
                    fragment.mTargetIndex = fragment.mTarget != null ? fragment.mTarget.mIndex : -1;
                    if (FragmentManagerImpl.a) {
                        new StringBuilder("retainNonConfig: keeping retained ").append(fragment);
                    }
                }
            }
        }
        return arrayList;
    }

    public void dispatchCreate() {
        this.mHost.mFragmentManager.e();
    }

    public void dispatchActivityCreated() {
        this.mHost.mFragmentManager.f();
    }

    public void dispatchStart() {
        this.mHost.mFragmentManager.g();
    }

    public void dispatchResume() {
        this.mHost.mFragmentManager.h();
    }

    public void dispatchPause() {
        this.mHost.mFragmentManager.a(4);
    }

    public void dispatchStop() {
        this.mHost.mFragmentManager.i();
    }

    public void dispatchReallyStop() {
        this.mHost.mFragmentManager.a(2);
    }

    public void dispatchDestroyView() {
        this.mHost.mFragmentManager.a(1);
    }

    public void dispatchDestroy() {
        this.mHost.mFragmentManager.j();
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        this.mHost.mFragmentManager.a(configuration);
    }

    public void dispatchLowMemory() {
        this.mHost.mFragmentManager.k();
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        return this.mHost.mFragmentManager.a(menu, menuInflater);
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        return this.mHost.mFragmentManager.a(menu);
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        return this.mHost.mFragmentManager.a(menuItem);
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        return this.mHost.mFragmentManager.b(menuItem);
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        this.mHost.mFragmentManager.b(menu);
    }

    public boolean execPendingActions() {
        return this.mHost.mFragmentManager.b();
    }

    public void doLoaderStart() {
        this.mHost.doLoaderStart();
    }

    public void doLoaderStop(boolean z) {
        this.mHost.doLoaderStop(z);
    }

    public void doLoaderRetain() {
        this.mHost.doLoaderRetain();
    }

    public void doLoaderDestroy() {
        this.mHost.doLoaderDestroy();
    }

    public void reportLoaderStart() {
        this.mHost.reportLoaderStart();
    }

    public SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        return this.mHost.retainLoaderNonConfig();
    }

    public void restoreLoaderNonConfig(SimpleArrayMap<String, LoaderManager> simpleArrayMap) {
        this.mHost.restoreLoaderNonConfig(simpleArrayMap);
    }

    public void dumpLoaders(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mHost.dumpLoaders(str, fileDescriptor, printWriter, strArr);
    }
}
