package eu.inloop.viewmodel.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import eu.inloop.viewmodel.base.ViewModelBaseFragment;
import eu.inloop.viewmodel.sample.R;
import eu.inloop.viewmodel.sample.viewmodel.PageModel;
import eu.inloop.viewmodel.sample.viewmodel.view.IPageView;

public class PagerFragment extends ViewModelBaseFragment<IPageView, PageModel> {

    public static PagerFragment newInstance(int position) {
        final Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        final PagerFragment fragment = new PagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.text)).setText(Integer.toString(getArguments().getInt("position")));
    }

    @Nullable
    @Override
    public PageModel createViewModel() {
        return new PageModel();
    }
}
