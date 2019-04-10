package com.hbtl.yhb.activities;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbtl.yhb.R;
import com.hbtl.yhb.application.BaseApplication;
import com.hbtl.yhb.event.BaseEvent;
import com.hbtl.yhb.interfaces.ToolbarCallBackInterface;
import com.hbtl.yhb.utils.ViewStatusManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.ButterKnife;

public abstract class BaseActivity extends RxAppCompatActivity {
    public static final int STYLE_FULL_SCREEN = 0X01;
    public static final int STYLE_UNDER_STATUS_BAR = 0X02;
    public static final int STYLE_UNDER_TITLE_BAR = 0X03;
    private Toolbar toolbar;
    private FrameLayout content;
    private TextView right_text;
    private int titlebarHeight;
    private ProgressDialog dialog;
    private AtomicInteger requestCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //activity栈showProgress
        add2Activities();
        requestCount = new AtomicInteger(0);
        super.setContentView(R.layout.activity_base);
        initCommonUi();
        //加载主视图
        add2Container(getContentId());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent baseEvent) {

    }


    private void initCommonUi() {
        titlebarHeight = getResources().getDimensionPixelOffset(R.dimen.abc_action_bar_default_height_material);
        toolbar = findViewById(R.id.toolbar);
        content = findViewById(R.id.content);
        right_text = findViewById(R.id.right_text);
        //初始化标题栏
        initToolBar(toolbar);
        setContentViewStyle(STYLE_UNDER_TITLE_BAR);
    }

    public void initToolBar(Toolbar toolbar) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    public void updateTitle(String mainTitle, String rightTitle, int textsize, ToolbarCallBackInterface toolbarCallBackInterface) {
        if (!TextUtils.isEmpty(mainTitle)) {
            getSupportActionBar().setTitle(mainTitle);
        }

        if (!TextUtils.isEmpty(rightTitle)) {
            right_text.setText(rightTitle);
            right_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, textsize);
            if (toolbarCallBackInterface != null) {
                ViewStatusManager.setViewFocusChanged(right_text);
                right_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toolbarCallBackInterface.rightTextClickCallBack();
                    }
                });

            }
        }
    }

    public void setContentViewStyle(int style) {
        switch (style) {
            case STYLE_FULL_SCREEN:
                setMainPadding(0);
                break;
//            case STYLE_UNDER_STATUS_BAR:
//                setMainPadding(navigationHeight);
//                break;
            case STYLE_UNDER_TITLE_BAR:
                setMainPadding(titlebarHeight);
                break;
        }
    }

    public void hideTitleBar() {
        toolbar.setVisibility(View.GONE);
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        content.setPadding(0, resourceId > 0 ? this.getResources().getDimensionPixelSize(resourceId) : 25, 0, 0);
        getSupportActionBar().hide();
    }

    public void showTitleBar() {
        toolbar.setVisibility(View.VISIBLE);
        getSupportActionBar().show();
    }

    public void setNavigationIcon(int icon) {
        toolbar.setNavigationIcon(icon);
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void setMainPadding(int top) {
        content.setPadding(0, top, 0, 0);
    }

    public void dissmissTitleLeftIcon() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    private void add2Container(int id) {
        if (id <= 0) return;
        View view = LayoutInflater.from(getSelfContext()).inflate(id, null, false);
        content.removeAllViews();
        content.addView(view);
        ButterKnife.bind(this);
    }

    protected abstract int getContentId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showProgress(false);
        EventBus.getDefault().unregister(this);
        removeSelfFromActivities();
    }


    public Context getSelfContext() {
        return this;
    }

    //将activty加入application集合
    private void add2Activities() {
        BaseApplication.getInstance().addActivity(this);
    }

    private void removeSelfFromActivities() {
        BaseApplication.getInstance().removeActivity(this);
    }

    public void toNewFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(fragment.getClass().getName()).commit();
    }

    public void toNewFragmentHideAndShow(Fragment from, Fragment to) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(to.getClass().getName()).hide(from).add(R.id.content, to)
                .commit();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private boolean isTopActivity() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        if (manager == null) return false;
        List<ActivityManager.RunningTaskInfo> runningTasks = manager.getRunningTasks(1);
        if (runningTasks != null && !runningTasks.isEmpty()) {
            String className = runningTasks.get(0).topActivity.getClassName();
            String packageName = runningTasks.get(0).topActivity.getPackageName();
            String name = this.getClass().getName();
            return (className.equals(name) && packageName.equals(getPackageName()));
        }
        return false;
    }

    public void showProgress(boolean progress) {
        showProgress(progress, null);
    }

    public void showProgress(boolean progress, String loadingText) {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.setMessage(TextUtils.isEmpty(loadingText) ? "加载中..." : loadingText);
        }
        if (progress) {
            if (requestCount.getAndAdd(1) <= 0) {
                requestCount.set(1);
            }
            if (!dialog.isShowing()) {
                dialog.show();
            }
        } else {
            if (requestCount.addAndGet(-1) <= 0) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }

    }

}