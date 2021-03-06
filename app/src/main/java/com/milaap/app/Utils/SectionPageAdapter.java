package com.milaap.app.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionPageAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SectionPageAdapter";
    private final List<Fragment> mFragmentList = new ArrayList<>();

   public SectionPageAdapter(FragmentManager fm){
       super(fm);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragement(Fragment fragment){
       mFragmentList.add(fragment);
    }
}
