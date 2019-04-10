package com.hbtl.yhb.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hbtl.yhb.activities.BaseActivity;
import com.hbtl.yhb.interfaces.ToolbarCallBackInterface;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tiano on 2019/1/21.
 */
public abstract class BaseFragmentWraper extends RxFragment {


    private BaseActivity baseActivity;
    private Unbinder unbinder;
    private FragmentActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View parentView = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, parentView);
        return parentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            baseActivity = (BaseActivity) activity;
        }
    }

    public abstract @LayoutRes
    int getLayoutResId();

    public void setTitle(String title) {
        if (baseActivity != null) {
            baseActivity.setTitle(title);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    public FragmentActivity getSupportActivity() {
        return activity;
    }

    public Context getSelfContext() {
        return activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }


    public void toNewFragment(Fragment fragment) {
        if (baseActivity != null) {
            baseActivity.toNewFragment(fragment);
        }
    }


    public void toNewFragmentHideAndShow(Fragment from, Fragment to) {
        if (baseActivity != null) {
            baseActivity.toNewFragmentHideAndShow(from, to);
        }
    }

    public void showToast(String message) {
        Toast.makeText(baseActivity, message, Toast.LENGTH_SHORT).show();
    }

    public void updateTitle(String mainTitle, String rightTitle, int textsize, ToolbarCallBackInterface toolbarCallBackInterface) {
        if (baseActivity!=null){
            baseActivity.updateTitle(mainTitle,rightTitle,textsize,toolbarCallBackInterface);
        }
    }
}
