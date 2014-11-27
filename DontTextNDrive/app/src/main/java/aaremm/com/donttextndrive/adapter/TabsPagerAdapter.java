package aaremm.com.donttextndrive.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import aaremm.com.donttextndrive.fragment.SettingsFragment;
import aaremm.com.donttextndrive.fragment.SwitchFragment;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return  new SwitchFragment();
            case 1:
                return  new SettingsFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
    private final String[] TITLES = { "Switch", "Settings" };

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

}