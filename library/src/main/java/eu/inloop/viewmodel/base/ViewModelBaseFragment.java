package eu.inloop.viewmodel.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import eu.inloop.viewmodel.AbstractViewModel;
import eu.inloop.viewmodel.IView;
import eu.inloop.viewmodel.ViewModelHelper;

public abstract class ViewModelBaseFragment<T extends IView, R extends AbstractViewModel<T>> extends Fragment implements IView {

    @NonNull
    private final ViewModelHelper<T, R> mViewModelHelper = new ViewModelHelper<>();

    @CallSuper
    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModelHelper.onCreate(getActivity(), savedInstanceState, new CreateViewModelCallback() {
            @Override
            public AbstractViewModel onViewModelRequested() {
                return createViewModel();
            }
        }, getArguments());
    }

    /**
     * Call this after your view is ready - usually on the end of {@link Fragment#onViewCreated(View, Bundle)}
     *
     * @param view view
     */
    protected void setModelView(@NonNull final T view) {
        mViewModelHelper.setView(view);
    }

    @Nullable
    public abstract R createViewModel();

    @CallSuper
    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewModelHelper.onSaveInstanceState(outState);
    }

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        mViewModelHelper.onStart();
    }

    @CallSuper
    @Override
    public void onStop() {
        super.onStop();
        mViewModelHelper.onStop();
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        mViewModelHelper.onDestroyView(this);
        super.onDestroyView();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        mViewModelHelper.onDestroy(this);
        super.onDestroy();
    }

    /**
     * @see ViewModelHelper#getViewModel()
     */
    @NonNull
    @SuppressWarnings("unused")
    public R getViewModel() {
        return mViewModelHelper.getViewModel();
    }
}
