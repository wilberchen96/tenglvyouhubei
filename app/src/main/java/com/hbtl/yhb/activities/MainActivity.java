package com.hbtl.yhb.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.hbtl.yhb.R;
import com.hbtl.yhb.adapters.MainFragmentAdapter;
import com.hbtl.yhb.application.BaseApplication;
import com.hbtl.yhb.fragments.DataFragment;
import com.hbtl.yhb.fragments.MainFragment;
import com.hbtl.yhb.interfaces.ToolbarCallBackInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.bnv_bottom_activity)
    BottomNavigationView bnv_bottom_activity;

    private long exitTime = 0;
    private List<Fragment> lists;
    private MainFragmentAdapter adapter;
    private MenuItem lastItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragements();
        initViewpager();
        initClick();
        initCommonUi();

    }


    //首页Fragment集合
    private void initFragements() {
        Fragment fragment1 = new MainFragment();
        Fragment fragment2 = new DataFragment();
        if (lists == null) {
            lists = new ArrayList<>();
        }
        lists.add(fragment1);
        lists.add(fragment2);
    }

    //初始化viewpager
    private void initViewpager() {
        if (lists != null && lists.size() > 0) {
            if (adapter == null) {
                adapter = new MainFragmentAdapter(getSupportFragmentManager(), lists);
            }
            viewpager.setAdapter(adapter);
            viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    bnv_bottom_activity.setSelectedItemId(bnv_bottom_activity.getMenu().getItem(i).getItemId());
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
            adapter.notifyDataSetChanged();
        }
    }

    private void initClick() {
        bnv_bottom_activity.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (lastItem != item) { // 判断当前点击是否为item自身
                    lastItem = item;
                    //  resetToDefaultIconAndText();//重置到默认不选中图片
                    if (viewpager != null) {
                        switch (item.getItemId()) {
                            case R.id.menus_main:
                                viewpager.setCurrentItem(0);
                                item.setIcon(R.drawable.main_icon_1);
                                break;
                            case R.id.menus_orders:
                                viewpager.setCurrentItem(1);
                                item.setIcon(R.drawable.main_icon_2);
                                break;
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出腾旅商服", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            moveTaskToBack(true);
            BaseApplication.getInstance().exitApp();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    System.exit(0);
                }
            }, 200);
        }
    }

    private void initCommonUi() {
        dissmissTitleLeftIcon();
        updateTitle("景区专用", "退出登陆", 14, new ToolbarCallBackInterface() {
            @Override
            public void rightTextClickCallBack() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getSelfContext());
                builder.setMessage("是否退出登录")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               //退出登陆
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.setCancelable(false);
                alert.show();

            }
        });
    }
    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }
}
