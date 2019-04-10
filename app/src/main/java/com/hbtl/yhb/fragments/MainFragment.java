package com.hbtl.yhb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hbtl.yhb.R;

import butterknife.BindView;
import cn.bertsir.zbar.QRActivity;
import cn.bertsir.zbar.QrManager;

public class MainFragment extends BaseFragmentWraper {
    @BindView(R.id.scan)
    TextView scan;

    @Override
    public int getLayoutResId() {
        return R.layout.main_fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        return super.onCreateView(inflater, container, state);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QrManager.getInstance().startScan(getSupportActivity(), new QrManager.OnScanResultCallback() {
                    @Override
                    public void onScanSuccess(String result, int type) {

                    }
                });
            }
        });
    }
}
