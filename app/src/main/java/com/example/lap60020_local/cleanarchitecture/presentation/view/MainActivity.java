package com.example.lap60020_local.cleanarchitecture.presentation.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lap60020_local.cleanarchitecture.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PagerAdapter adapter = new MyPager(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tab);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_search) {
            Intent intent = new Intent(this,searchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyPager extends FragmentPagerAdapter {

        public MyPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new PopularFragnment();
                case 1:
                    return new TopRatedFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return getResources().getText(R.string.popularString);
                case 1:
                    return getResources().getText(R.string.topratedString);
            }
            return null;
        }
    }
}
