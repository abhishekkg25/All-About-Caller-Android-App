package com.automata.masterabhig.allaboutcaller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MASTER ABHIG on 22-05-2018.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {
      final List<Fragment>fragmentList=new ArrayList<>();
      final List<String> fragmentTitleList=new ArrayList<>();
    public void  addFragment(Fragment fragment,String title){
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }
    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
