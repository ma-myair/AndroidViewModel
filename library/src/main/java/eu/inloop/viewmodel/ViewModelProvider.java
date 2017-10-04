package eu.inloop.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import java.util.HashMap;

import eu.inloop.viewmodel.base.CreateViewModelCallback;

/**
 * Create and keep this class inside your Activity. Store it
 * in {@link android.support.v4.app.FragmentActivity#onRetainCustomNonConfigurationInstance()
 * and restore in {@link android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)} before
 * calling the super implemenentation.
 */
public class ViewModelProvider {

    @NonNull
    private final HashMap<String, AbstractViewModel<? extends IView>> mViewModelCache;

    @NonNull
    public static ViewModelProvider newInstance(@NonNull final FragmentActivity activity) {
        if (activity.getLastCustomNonConfigurationInstance() == null) {
            return new ViewModelProvider();
        } else {
            return (ViewModelProvider) activity.getLastCustomNonConfigurationInstance();
        }
    }

    @SuppressWarnings({"deprecation", "unused"})
    @NonNull
    @Deprecated
    public static ViewModelProvider newInstance(@NonNull final Activity activity) {
        if (activity.getLastNonConfigurationInstance() == null) {
            return new ViewModelProvider();
        } else {
            return (ViewModelProvider) activity.getLastNonConfigurationInstance();
        }
    }

    private ViewModelProvider() {
        mViewModelCache = new HashMap<>();
    }

    public synchronized void remove(@Nullable String modeIdentifier) {
        mViewModelCache.remove(modeIdentifier);
    }

    public synchronized void removeAllViewModels() {
        mViewModelCache.clear();
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public synchronized <T extends IView> ViewModelWrapper<T> getViewModel(final String modelIdentifier,
                                                                           @NonNull final CreateViewModelCallback createViewModelCallback) {
        AbstractViewModel<T> instance = (AbstractViewModel<T>) mViewModelCache.get(modelIdentifier);
        if (instance != null) {
            return new ViewModelWrapper<>(instance, false);
        }

        try {
            instance = createViewModelCallback.onViewModelRequested();
            instance.setUniqueIdentifier(modelIdentifier);
            mViewModelCache.put(modelIdentifier, instance);
            return new ViewModelWrapper<>(instance, true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    final static class ViewModelWrapper<T extends IView> {
        @NonNull
        final AbstractViewModel<T> viewModel;
        final boolean wasCreated;

        private ViewModelWrapper(@NonNull AbstractViewModel<T> mViewModel, final boolean mWasCreated) {
            this.viewModel = mViewModel;
            this.wasCreated = mWasCreated;
        }
    }
}
