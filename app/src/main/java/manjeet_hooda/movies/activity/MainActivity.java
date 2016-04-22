package manjeet_hooda.movies.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;

import manjeet_hooda.movies.R;
import manjeet_hooda.movies.adapters.MyViewPager;
import manjeet_hooda.movies.animations.AnimationUtils;
import manjeet_hooda.movies.global.GlobalDataContainer;
import manjeet_hooda.movies.global.NoConnectionDialog;
import manjeet_hooda.movies.network.ConnectionUtil;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private MyViewPager mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!ConnectionUtil.hasDataConnection(this))
            NoConnectionDialog.showDialog(this);

        setupToolbar();
        setupTabs();
        setupPager();
        mViewPager.setCurrentItem(1);
    }

    private void setupTabs() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText(GlobalDataContainer.tabs[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(GlobalDataContainer.tabs[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(GlobalDataContainer.tabs[2]));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void setupPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new MyViewPager(getSupportFragmentManager(),GlobalDataContainer.num_tabs);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        AnimationUtils.animateToolbarDroppingDown(mToolbar);
    }

}
