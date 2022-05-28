package com.example.habitary;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitary.fragment.PomodoroFragment;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.example.habitary.menu.DrawerAdapter;
import com.example.habitary.menu.DrawerItem;
import com.example.habitary.menu.SimpleItem;
import com.example.habitary.menu.SpaceItem;
import com.example.habitary.fragment.CenteredTextFragment;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_HOME = 0;
    private static final int POS_TASKS = 1;
    private static final int POS_HABITS = 2;
    private static final int POS_POMODORO = 3;
    private static final int POS_ABOUT = 4;
    private static final int POS_SETTINGS = 6;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_TASKS),
                createItemFor(POS_HABITS),
                createItemFor(POS_POMODORO),
                createItemFor(POS_ABOUT),
                new SpaceItem(250),
                createItemFor(POS_SETTINGS)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_HOME);
    }

    @Override
    public void onItemSelected(int position) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (position == POS_HOME){
            CenteredTextFragment dashBoardFragment = new CenteredTextFragment();
            transaction.replace(R.id.container, dashBoardFragment);
        }
        else if (position == POS_TASKS){
            CenteredTextFragment dashBoardFragment = new CenteredTextFragment();
            transaction.replace(R.id.container, dashBoardFragment);
        }
        else if (position == POS_HABITS){
            CenteredTextFragment dashBoardFragment = new CenteredTextFragment();
            transaction.replace(R.id.container, dashBoardFragment);
        }
        else if (position == POS_POMODORO){
            PomodoroFragment pomodoroFragment = new PomodoroFragment();
            transaction.replace(R.id.container, pomodoroFragment);
        }
        else if (position == POS_ABOUT){
            CenteredTextFragment dashBoardFragment = new CenteredTextFragment();
            transaction.replace(R.id.container, dashBoardFragment);
        }
        else if (position == POS_SETTINGS){
            finish();
        }

        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }
}
