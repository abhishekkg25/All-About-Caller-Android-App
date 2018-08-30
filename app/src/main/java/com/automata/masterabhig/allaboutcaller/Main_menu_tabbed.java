package com.automata.masterabhig.allaboutcaller;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class Main_menu_tabbed extends AppCompatActivity {
 SectionsPageAdapter sectionsPageAdapter;
 ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_tabbed);
       // getSupportActionBar().hide();
sectionsPageAdapter=new SectionsPageAdapter(getSupportFragmentManager());
viewPager=(ViewPager)findViewById(R.id.container);
setUpViewPager(viewPager);
TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
tabLayout.setupWithViewPager(viewPager);


    }
void setUpViewPager(ViewPager viewPager){
      SectionsPageAdapter  sectionsPageAdapter1=new SectionsPageAdapter(getSupportFragmentManager());
        sectionsPageAdapter1.addFragment(new MenuFragmant(),"Menu");
        sectionsPageAdapter1.addFragment(new SearchFragmant(),"Search");
        viewPager.setAdapter(sectionsPageAdapter1);
}
}
