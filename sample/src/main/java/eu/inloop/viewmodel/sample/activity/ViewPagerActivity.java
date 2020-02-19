package eu.inloop.viewmodel.sample.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import eu.inloop.viewmodel.base.ViewModelBaseEmptyActivity;
import eu.inloop.viewmodel.sample.R;
import eu.inloop.viewmodel.sample.fragment.PagerFragment;

public class ViewPagerActivity extends ViewModelBaseEmptyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new TestPagerAdapter(getSupportFragmentManager()));
    }

    private final static class TestPagerAdapter extends FragmentStatePagerAdapter {
        public TestPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            return PagerFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 10;
        }
    }
}
