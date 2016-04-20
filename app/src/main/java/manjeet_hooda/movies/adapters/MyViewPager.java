package manjeet_hooda.movies.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import manjeet_hooda.movies.fragment.Search;
import manjeet_hooda.movies.fragment.Upcoming;
import manjeet_hooda.movies.fragment.movies;
import manjeet_hooda.movies.global.GlobalDataContainer;

/**
 * Created by manjeet on 16/4/16.
 */
public class MyViewPager extends FragmentStatePagerAdapter {

    private movies movies_fragment;
    private Search search_fragment;
    private Upcoming upcoming_fragment;
    private int mNumOfTabs;

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    public MyViewPager(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(search_fragment == null) {
                    search_fragment = new Search();
                    return search_fragment;
                }
                return search_fragment;
            case 1:
                if (movies_fragment == null) {
                    movies_fragment = new movies();
                }
                return movies_fragment;
            case 2:
                if (upcoming_fragment == null) {
                    upcoming_fragment = new Upcoming();
                }
                return upcoming_fragment;
            default:
                return null;
        }
    }
}
